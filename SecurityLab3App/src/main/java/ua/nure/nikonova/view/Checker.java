package ua.nure.nikonova.view;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ua.nure.nikonova.MySignature;

public class Checker {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static  void main(String[]args) {
		new Checker();
	}
	
	JButton button;
	JButton checkButton;

	JButton shaButton;
	JTextArea editor;
	File file;
	String checksum;
	public Checker() {
		
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		
		frame.setSize(500,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		button = new JButton("������ ���� ��� ��������");
		shaButton = new JButton("������ ���� � ����������� �����");
		checkButton=new JButton("���������!");
		checkButton.setEnabled(false);
		
		 editor = new JTextArea();
		editor.setText(	"����� ��� �������� ���������. \n");
	
	
	
	
		
			
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectFile();
					
				}
				
			});
			
		
			shaButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					rememberChecksum();
					
				}

				
				
			});
			 
			checkButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					check();
					
				}

				
				
			});

			  frame.add(shaButton);
			  frame.add(button);
			  frame.add(checkButton);
			  frame.add(editor);
			  frame.setVisible(true);
		
		
	}
	private void check() {
		if(file==null) return;
		try{
		// TODO Auto-generated method stub
		String realSha = MySignature.getHash(file);
		editor.setText(editor.getText()+"\n�������� ����� �����:"+realSha);
		if(realSha.equals(checksum)) {
			editor.setText(editor.getText()+"\n----����²��� ��������!-----\n");
		}
		else {
			editor.setText(editor.getText()+"\n----�����! ϲ������!----\n");
		}
		}
		catch(Exception e) {
			editor.setText(editor.getText()+"\n������� �������:"+e);
		}
	}
	private void rememberChecksum() {
		FileFrame ff = new FileFrame();
		File fileCheck = ff.getSelectedFile();
		if(fileCheck==null) {
			editor.setText(editor.getText()+"\n���������� ���� �� �������.");
		}
		else {
			editor.setText(editor.getText()+"\n���� � ����������� �����: "+fileCheck.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(fileCheck)))
					{
				checksum = br.readLine();
				editor.setText(editor.getText()+"\n���������� ����: "+checksum);
				checkButton.setEnabled(true);
					}
			catch(IOException e) {
				editor.setText(editor.getText()+"\n������� �� ��� ������� ���������� ����: "+e);
			}
			
		}
	}
	private void selectFile() {
		FileFrame ff = new FileFrame();
		file = ff.getSelectedFile();
		if(file==null) {
			editor.setText(editor.getText()+"\n���� �� ������.");
			
		}
		else {
			editor.setText(editor.getText()+"\n���� ��� ��������: "+
					file.getName());
			checkButton.setEnabled(true);
		}
		
	}

}
