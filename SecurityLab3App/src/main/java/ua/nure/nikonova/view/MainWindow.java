package ua.nure.nikonova.view;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import ua.nure.nikonova.MySignature;
import javafx.beans.binding.*;
import javafx.beans.value.*;
public class MainWindow extends Application {
	String fileName="";
	
    public static void main(String[] args) {
	Application.launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) {
	primaryStage.setTitle("БПіД - Електронний підпис");

	Group root = new Group();
	final Scene scene = new Scene(root, 500, 400);
	final Button btn = new Button();
	Button runCheck = new Button("Запустити сервіс перевірки");
	TextArea editor = new TextArea();
	editor.setText(	"Програма запущена. \n");
	Label label = new Label("Отримати електронний підпис для файлу:");
	btn.setLayoutX(80);
	btn.setLayoutY(80);
	runCheck.setLayoutX(280);
	runCheck.setLayoutY(80);
	editor.setLayoutX(20);
	editor.setLayoutY(200);
	btn.setText("Оберіть файл");
	btn.setOnAction(new EventHandler<ActionEvent>() {
	    @SuppressWarnings("deprecation")
		public void handle(ActionEvent event) {
		FileFrame ff = new FileFrame();
		
		File selected = ff.getSelectedFile();
		StringBuilder steps = new StringBuilder();
		if(selected==null) steps.append("\nПомилка: файл не вибрано.");
		else {
			steps.append("\nОбрано файл: "+ selected.getName());
			try {
				steps.append("\nХеш для файлу: "+MySignature.getHash(selected));
				}
			
		
		catch(Exception e) {
			steps.append("\nВиникла помилка пiд час електронного пiдпису: "+e);
		}
			
		}
		editor.setText(editor.getText()+steps.toString());
		
		
	    }
	});
	runCheck.setOnAction(e -> runCheck());

	
	scene.getStylesheets().add(ua.nure.nikonova.view.MainWindow.class.getResource("style.css").toExternalForm());
	btn.getStyleClass().add("cssbutton");
	runCheck.getStyleClass().add("cssbutton");
	label.getStyleClass().add("csslabel");
	editor.getStyleClass().add("csseditor");
	
	editor.setEditable(false);
	root.getChildren().add(btn);
	root.getChildren().add(label);
	root.getChildren().add(runCheck);
	root.getChildren().add(editor);
	
	primaryStage.setScene(scene);
	  
	primaryStage.show();
    }


	private void runCheck() {
		
				new Checker();
		
	}
}