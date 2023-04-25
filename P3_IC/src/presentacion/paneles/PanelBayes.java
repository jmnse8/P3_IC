package presentacion.paneles;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controller.Controller;
import presentacion.paneles.detalles.ComprobarEjemplos;

public class PanelBayes extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private ComprobarEjemplos compEjPanel;

	private JButton ejecutarB;
	
	private JPanel panelTabla;
	private JTextArea centrosObtenidos;
	
	public PanelBayes() {
		super();
		iniGUI();
	}

	private void iniGUI() {
		setLayout(null);

		iniMeterDatos();
		
		iniSolucion();

		compEjPanel = new ComprobarEjemplos("Bayes");
		add(compEjPanel);
	}
	
	private void iniMeterDatos() {
		JPanel meterDatos = new JPanel();
		meterDatos.setLayout(null);
		meterDatos.setBounds(0, 0, 786, 120);// 393

		JLabel nombre = new JLabel("Algoritmo de Bayes");
		nombre.setFont(new Font("Arial", Font.BOLD, 24));
		nombre.setBounds(278, 5, 230, 40);
		meterDatos.add(nombre);
		
		JSeparator separador = new JSeparator();
		separador.setBounds(193, 50, 400, 2);
		meterDatos.add(separador);
		
		ejecutarB = new JButton("EJECUTAR");
		ejecutarBListener();
		ejecutarB.setBounds(293, 70, 200, 30);
		meterDatos.add(ejecutarB);
		
		JSeparator separador2 = new JSeparator();
		separador2.setBounds(193, 118, 400, 2);
		meterDatos.add(separador2);

		
		add(meterDatos);
	}
	
	private void iniSolucion() {
		JPanel solucionPanel = new JPanel();
		solucionPanel.setLayout(null);
		solucionPanel.setBounds(0, 140, 786, 360);//200

		panelTabla = new JPanel();
		panelTabla.setLayout(null);
		panelTabla.setBounds(0, 0, 786, 260);//200
		panelTabla.setBorder(new TitledBorder("Matriz de grados de pertenencia "));

		JTable matrizU = new JTable();

		JScrollPane spTabla = new JScrollPane(matrizU);
		spTabla.setBounds(10, 25, 766, 225);
		panelTabla.add(spTabla);

		solucionPanel.add(panelTabla);

		JPanel panelCentros = new JPanel();
		panelCentros.setLayout(null);
		panelCentros.setBounds(0, 260, 786, 100);
		panelCentros.setBorder(new TitledBorder("Centros obtenidos "));

		centrosObtenidos = new JTextArea();

		JScrollPane spCentros = new JScrollPane(centrosObtenidos);
		spCentros.setBounds(10, 25, 766, 65);
		panelCentros.add(spCentros);

		solucionPanel.add(panelCentros);

		add(solucionPanel);
	}
	
	private void ejecutarBListener() {
		ejecutarB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					ArrayList<Object> solucion = Controller.getInstance().ejecutaBayes();
					
					panelTabla.removeAll();
					
					@SuppressWarnings("unchecked")
					List<String[][]> matrices = (List<String[][]>) solucion.get(0);
					@SuppressWarnings("unchecked")
					List<String[]> nombresColumnas = (List<String[]>) solucion.get(1);
					
					JPanel panelTablas = new JPanel();
					for(int i = 0; i < matrices.size(); i++) {
						JTable matrizU = new JTable(matrices.get(i), nombresColumnas.get(i));
						matrizU.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						matrizU.setShowVerticalLines(true);
						
						JScrollPane spTabla = new JScrollPane(matrizU);
						panelTablas.add(spTabla);
					}
					
					JScrollPane general = new JScrollPane(panelTablas);
					general.setBounds(10, 25, 766, 225);
					panelTabla.add(general);
					
					centrosObtenidos.setText((String) solucion.get(2));
					
					repaint();
					revalidate();
				}catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Algo ha fallado en el algoritmo de Bayes", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
