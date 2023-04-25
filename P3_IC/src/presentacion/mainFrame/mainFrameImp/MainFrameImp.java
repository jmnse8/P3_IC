package presentacion.mainFrame.mainFrameImp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controller.Controller;
import presentacion.mainFrame.MainFrame;
import presentacion.paneles.PanelBayes;
import presentacion.paneles.PanelKMedias;
import presentacion.paneles.PanelLloyd;

public class MainFrameImp extends MainFrame{

	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton meteFichero;
	private JTextField rutaFichero;
	
	private JTabbedPane tabbedPane;
	private PanelBayes pB;
	private PanelKMedias pKM;
	private PanelLloyd pL;
	
	public MainFrameImp() {
		iniGUI();
	}

	private void iniGUI() {
		setVisible(true);
		setTitle("Práctica 3");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(new Dimension(800,800));
		setLocationRelativeTo(null);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		rutaFichero = new JTextField("Seleccionar fichero para con los datos.");
		rutaFichero.setEditable(false);
		rutaFichero.setBackground(Color.white);
		toolBar.add(rutaFichero);
		
		toolBar.addSeparator();

		meteFichero = new JButton();
		meteFichero.setText("Cargar fichero");
		meteFicheroListener();
		toolBar.add(meteFichero);

	
		add(toolBar, BorderLayout.PAGE_START);
		
		tabbedPane = new JTabbedPane();
		
		pB = new PanelBayes();
		pKM = new PanelKMedias();
		pL = new PanelLloyd();
		
		tabbedPane.addTab("K-Medias", pKM);
		tabbedPane.addTab("Bayes", pB);
		tabbedPane.addTab("Lloyd", pL);
		
		
		add(tabbedPane, BorderLayout.CENTER);
		
		repaint();
		revalidate();
	}
	
	private void meteFicheroListener() {
		meteFichero.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(fileChooser);
				if (fileChooser.getSelectedFile() != null) {
					File fichero = fileChooser.getSelectedFile();
					rutaFichero.setEditable(true);
					rutaFichero.setText(fichero.getAbsolutePath());
					rutaFichero.setEditable(false);
					if(!Controller.getInstance().meteFichero(fichero))
						 JOptionPane.showMessageDialog(null, "Ha ocurrido algún error al cargar el fichero.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
