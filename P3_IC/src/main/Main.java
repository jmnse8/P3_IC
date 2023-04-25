package main;


import com.formdev.flatlaf.FlatIntelliJLaf;

import presentacion.mainFrame.MainFrame;
public class Main {

	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
		MainFrame.getInstance();
	}

}
