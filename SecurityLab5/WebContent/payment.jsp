<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  
	<title>1</title>
	<meta charset="utf-8">
	<style>
   <%@include file='style.css' %>
</style>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>	
	<script type="text/javascript" src="https://code.jquery.com/ui/1.12.0-rc.1/jquery-ui.min.js"></script>
	
	 <script>
  $(function() {
    $( ".item" ).draggable();
    
  });
  </script>
</head>
<body> <center>
<h2>світ іграшок</h2>
<p>Привіт! Це інтернет-магазин іграшок. Перетягни іграшку до кошика й натисни "Оплатити"</p>
${ form }


  <div id="item_container">
       
      
       <div class="item" id="i2">
          <img src="C:\Users\User\Desktop\SecurityLab5\WebContent\1.png"/>
           <label class="title">Вантажівка</label>
           <label class="price">1 грн</label>
       </div>
       <div class="item" id="i3">
           <img src="C:\Users\User\Desktop\SecurityLab5\WebContent\3.png"/>
           <label class="title">Хом'як, що говорить</label>
           <label class="price">1 грн</label>
       </div>
        <div class="item" id="i4">
           <img src="C:\Users\User\Desktop\SecurityLab5\WebContent\4.png"/>
           <label class="title">Ведмедик Тедді</label>
           <label class="price">1 грн</label>
       </div>
        <div class="item" id="i5">
           <img src="C:\Users\User\Desktop\SecurityLab5\WebContent\5.png"/>
           <label class="title">лялька-карапуз</label>
           <label class="price">1 грн</label>
       </div>
        <div class="item" id="i7">
           <img src="C:\Users\User\Desktop\SecurityLab5\WebContent\7.png"/>
           <label class="title">Їжачок</label>
           <label class="price">1 грн</label>
       </div>
       
<div>
       <div class="clear"></div>
   
   <div id="cart_container">
    <div id="cart_title">
        <span>Твій кошик</span>
        <div class="clear"></div>
    </div>
    <div id="cart_toolbar"> 
        <div id="cart_items">тягни іграшку сюди...</div>
    </div>
    <div id="navigate" >
        <div id="nav_left">
            <a href="" id="btn_clear">Очистити кошик</a>
        </div>
        <div id="nav_right">
            <span class="sptext">
                <label>Іграшок </label><label class="count" id="citem">0</label>
            </span>
            <span class="sptext">
                <label>Ціна </label><label class="count" id="cprice">0 грн</label>
            </span>
        </div>
        <div class="clear"></div>
       
    </div>
	</div>
</div>

</center>


</body>
</html>


<script type="text/javascript">
var total_items = 0;
var total_price = 0;
$(document).ready(function() {

    $(".item").draggable({
        revert: true           
    });    

    
    $("#cart_items").droppable({
        accept: ".item",
        activeClass: "drop-active",
        hoverClass: "drop-hover",
        drop: function(event, ui) {
            var item = ui.draggable.html();
            var itemid = ui.draggable.attr("id");
            var html = '<div class="item icart">';
            html = html + '<div class="divrm">';
            html = html + '<a  class="remove '+itemid+'">&times;</a>';
            html = html + '<div/>'+item+'</div>';
            $("#cart_items").append(html);

            // Обновление общего количества
            total_items++;
            $("#citem").html(total_items);

            // Обновление общей цены
            var price = parseInt(ui.draggable.find(".price").html().replace(" грн", ""));
            total_price = total_price + price;
            $("#cprice").html(total_price + " грн");

            // Расширяем корзину
            if (total_items > 0) {
                $("#cart_items").animate({width: "+=200"}, 'slow');
            }
        }
    });
});

$("#b").click(function() {
	if(total_items>0){
alert('Замовлення '+total_items+ ' іграшок на суму '+total_price+' грн прийняте. Чекай подарунків і будь чемним!');
}
else alert('Не соромся, вибери в кошик іграшку! ');

});

$("#btn_clear").click(function() {
    $("#cart_items").fadeOut("2000", function() {
       $(this).html("").fadeIn("fast").css({left:0});
    });
    $("#citem").html("0");
    $("#cprice").html("0 грн");
    total_items = 0;
    total_price = 0;
    return false;
});


function remove(el) {
	
	var price = parseInt(el.draggable.find(".price").html().replace(" грн", ""));
	alert(el);

       // Обновляем общее количество
        total_items--;
        $("#citem").html(total_items);

        // Обновляем общую цену
        
        total_price = total_price - price;
        $("#cprice").html(total_price + " грн");
        $(el).closest("div").parent().remove(); 
    }

 $("body").on("click", "a.remove", function() {

 			total_items--;
            $("#citem").html(total_items);

            // Обновление общей цены
          var price = parseInt($(this).parent().parent().find(".price").html().replace(" грн", ""));
            total_price = total_price - price;
            $("#cprice").html(total_price + " грн");

            // Расширяем корзину
            if (total_items > 0) {
                $("#cart_items").animate({width: "-=200"}, 'slow');
            }

    $(this).closest("div").parent().remove(); 
});  
   

</script>

