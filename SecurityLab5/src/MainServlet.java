import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.liqpay.LiqPay;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private static final String PUBLIC_KEY = "i10438341367";
    private static final String PRIVATE_KEY = "K8M8aLQKUdXBwUk4gIzM1Z5ahRtpZ1SgdsJrgGQy";

	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        Map<String, String> params = new HashMap<>();
	        params.put("amount", "1");
	        params.put("currency", "UAH");
	        params.put("description", "Оплатити покупку іграшки: ");
	        params.put("order_id", "1");
	        params.put("sandbox", "1"); // enable the testing environment and card will NOT charged. If not set will be used property isCnbSandbox()
	        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
	        String html = liqpay.cnb_form(params);
	        req.getServletContext().setAttribute("form", html);
	        resp.sendRedirect("payment.jsp");
	    }
}
