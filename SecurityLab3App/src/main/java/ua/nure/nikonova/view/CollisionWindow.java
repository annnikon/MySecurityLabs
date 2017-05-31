package ua.nure.nikonova.view;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ua.nure.nikonova.MyHash;
import ua.nure.nikonova.MySignature;

public class CollisionWindow extends Application {
	String inputString;
	String lenString;
	
    public static void main(String[] args) {
	Application.launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
	primaryStage.setTitle("БПіД - Генерація колізії");

	Group root = new Group();
	final Scene scene = new Scene(root, 700, 400);
	final Button btn = new Button("Згенерувати колізію");
	TextArea editor = new TextArea();
	TextField charField  = new TextField();
	TextField lenField= new TextField();
	editor.setText(	"Програма запущена. \n");
	charField.setFocusTraversable(true);
	lenField.setFocusTraversable(true);
	Label label1 = new Label("Введіть перший символ строки: ");
	label1.setLayoutY(10);
	label1.setLayoutX(10);
	charField.setLayoutX(300);
	charField.setLayoutY(10);
	Label label2 =  new Label("Введіть довжину строки (від 2): ");
	label2.setLayoutY(100);
	label2.setLayoutX(10);
	lenField.setLayoutX(300);
	lenField.setLayoutY(100);
	btn.setLayoutX(150);
	btn.setLayoutY(180);
	
	editor.setLayoutX(10);
	editor.setLayoutY(250);
	
	editor.setEditable(false);
	
	btn.setOnAction(new EventHandler<ActionEvent>() {
	    @SuppressWarnings("deprecation")
		public void handle(ActionEvent event) {
	    editor.setText("Генерую...\n");
		StringBuilder steps = new StringBuilder();
		inputString = charField.getText();
		lenString=lenField.getText();
		if(inputString==null || lenString==null) {
			editor.setText("\nСпочатку введіть значення. \n");
			
		}
		int length=0;
		try {
			length= Integer.parseInt(lenString);
		}
		catch(Exception e) {
			editor.setText("\nНекоректне значення довжини. \n");
			length=2;
		}
		
		CollisionGenerator generator = new CollisionGenerator(inputString.charAt(0),length);
		String s1= generator.getFirstString();
		String s2 = generator.getSecondString();
		editor.setText("Колізія для: "+s1+ " i: "+s2+"\n\n");
		MyHash myHash1 = new MyHash(s1);
		MyHash myHash2 = new MyHash(s2);
		
		editor.setText(editor.getText()+myHash1.getSteps());
		editor.setText(editor.getText()+myHash2.getSteps());
		
		
		
	    }
	});
	
		
	scene.getStylesheets().add(ua.nure.nikonova.view.MainWindow.class.getResource("style.css").toExternalForm());
	btn.getStyleClass().add("cssbutton");
	charField.getStyleClass().add("csseditor");
	lenField.getStyleClass().add("csseditor");
	label1.getStyleClass().add("csslabel");
	label2.getStyleClass().add("csslabel");
	editor.getStyleClass().add("csseditor");
	
	editor.setEditable(false);
	root.getChildren().add(btn);
	root.getChildren().add(label1);
	root.getChildren().add(label2);
	root.getChildren().add(charField);
	root.getChildren().add(lenField);
	root.getChildren().add(editor);
	
	primaryStage.setScene(scene);
	  
	primaryStage.show();
    }

}