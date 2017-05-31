package ua.nure.nikonova.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileFrame extends JFrame{
	
	
	File file;
	public FileFrame(){
		
		setBounds(0,0,500,500);
		JFileChooser dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        dialog.setApproveButtonText("Обрати");//выбрать название для кнопки согласия
        dialog.setDialogTitle("Оберіть файл");// выбрать название
        dialog.setDialogType(JFileChooser.OPEN_DIALOG);// выбрать тип диалога Open или Save
        dialog.showOpenDialog(this);
        file =dialog.getSelectedFile();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
		
	}
	
	public File getSelectedFile() {
		return file;
	}
	
}
