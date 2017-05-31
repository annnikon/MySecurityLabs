package ua.nure.nikonova.view;

import java.io.File;

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

public class HashWindow extends Application {
	String inputString;
	
    public static void main(String[] args) {
	Application.launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
	primaryStage.setTitle("БПіД - Власна реалізація хешування");

	Group root = new Group();
	final Scene scene = new Scene(root, 600, 640);
	final Button btn = new Button();
	TextArea editor = new TextArea();
	TextField input = new TextField();
	editor.setText(	"Програма запущена. \n");
	input.setFocusTraversable(true);
	Label label = new Label("Введіть значення: ");
	label.setLayoutY(10);
	
	input.setLayoutX(200);
	input.setLayoutY(10);
	btn.setLayoutX(80);
	btn.setLayoutY(80);
	editor.setLayoutX(20);
	editor.setLayoutY(200);
	editor.setEditable(false);
	btn.setText("Згенерувати хеш");
	btn.setOnAction(new EventHandler<ActionEvent>() {
	    @SuppressWarnings("deprecation")
		public void handle(ActionEvent event) {
		StringBuilder steps = new StringBuilder();
		inputString = input.getText();
		if(inputString==null) {
			editor.setText("\nСпочатку введіть значення. \n");
			return;
		}
		MyHash myHash = new MyHash(inputString);
		editor.setText(myHash.getSteps());
		
		
	    }
	});
	
	final DoubleBinding db = scene.widthProperty().subtract(150);
	
	db.addListener(new javafx.beans.value.ChangeListener<  Number>() {
	    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
		btn.setLayoutX(db.getValue());
	    }
	});
	
	scene.getStylesheets().add(ua.nure.nikonova.view.MainWindow.class.getResource("style.css").toExternalForm());
	btn.getStyleClass().add("cssbutton");
	input.getStyleClass().add("csseditor");
	label.getStyleClass().add("csslabel");
	editor.getStyleClass().add("csseditor");
	
	editor.setEditable(false);
	root.getChildren().add(btn);
	root.getChildren().add(label);
	root.getChildren().add(input);
	root.getChildren().add(editor);
	
	primaryStage.setScene(scene);
	  
	primaryStage.show();
    }

}