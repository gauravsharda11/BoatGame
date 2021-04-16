package isi.boat;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	
	String message = "";
	static JFrame obj ;
	public static void main(String[] args) {
		
		obj = new JFrame();
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(10, 10, 905, 700);
		obj.setBackground(Color.DARK_GRAY);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		
	}
	
	public synchronized void print(String message) {
		JOptionPane.showMessageDialog(obj, message);
	}
}
