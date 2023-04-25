package presentacion.paneles;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controller.Controller;
import modelo.Individuo;
import presentacion.paneles.detalles.ComprobarEjemplos;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class PanelKMedias extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField meterCentro;
	private JLabel meterCentroInfo;
	private JRadioButton centroAleatorio;
	private JLabel centroAleatorioInfo;
	private JTextField meterTolerancia;
	private JLabel meterToleranciaInfo;
	private JTextField meterPesoExp;
	private JLabel meterPesoInfo;
	private JButton ejecutarB;

	private JPanel panelTabla;
	private JTextArea centrosObtenidos;

	private ComprobarEjemplos compEjPanel;

	public PanelKMedias() {
		super();
		iniGUI();
	}

	private void iniGUI() {// x=0,y=33,width=786,height=695

		setLayout(null);

		iniMeterDatos();
		
		iniSolucion();

		compEjPanel = new ComprobarEjemplos("KMedias");
		add(compEjPanel);

	}

	private void iniMeterDatos() {
		JPanel meterDatos = new JPanel();
		meterDatos.setLayout(null);
		meterDatos.setBounds(0, 0, 786, 200);// 393

		JLabel nombre = new JLabel("Algoritmo K-Medias");
		nombre.setFont(new Font("Arial", Font.BOLD, 24));
		nombre.setBounds(278, 5, 230, 40);
		meterDatos.add(nombre);

		JSeparator separador = new JSeparator();
		separador.setBounds(193, 50, 400, 2);
		meterDatos.add(separador);

		meterCentroInfo = new JLabel("Inicializa un centro personalizado: ");
		meterCentroInfo.setBounds(30, 60, 220, 30);
		meterDatos.add(meterCentroInfo);

		meterCentro = new JTextField("4.6,3.0,4.0,0.0,*,6.8,3.4,4.6,0.7,*");
		meterCentro.setBounds(260, 60, 496, 30);
		meterDatos.add(meterCentro);

		meterToleranciaInfo = new JLabel("Personaliza la tolerancia: ");
		meterToleranciaInfo.setBounds(30, 110, 160, 30);
		meterDatos.add(meterToleranciaInfo);

		meterTolerancia = new JTextField("0.01");
		meterTolerancia.setBounds(200, 110, 188, 30);
		meterDatos.add(meterTolerancia);

		meterPesoInfo = new JLabel("Personaliza el peso exponencial: ");
		meterPesoInfo.setBounds(398, 110, 210, 30);
		meterDatos.add(meterPesoInfo);

		meterPesoExp = new JTextField("2");
		meterPesoExp.setBounds(618, 110, 138, 30);
		meterDatos.add(meterPesoExp);
		
		centroAleatorioInfo = new JLabel("Centro aleatorio: ");
		centroAleatorioInfo.setBounds(258, 150, 110, 30);
		meterDatos.add(centroAleatorioInfo);

		centroAleatorio = new JRadioButton();
		centroAleatorio.setBounds(378, 150, 30, 30);
		meterDatos.add(centroAleatorio);

		ejecutarB = new JButton("EJECUTAR");
		ejecutarBListener();
		ejecutarB.setBounds(428, 150, 100, 30);
		meterDatos.add(ejecutarB);

		JSeparator separador2 = new JSeparator();
		separador2.setBounds(193, 195, 400, 2);
		meterDatos.add(separador2);

		add(meterDatos);
	}

	private void iniSolucion() {
		JPanel solucionPanel = new JPanel();
		solucionPanel.setLayout(null);
		solucionPanel.setBounds(0, 200, 786, 300);

		panelTabla = new JPanel();
		panelTabla.setLayout(null);
		panelTabla.setBounds(0, 0, 786, 200);
		panelTabla.setBorder(new TitledBorder("Matriz de grados de pertenencia "));

		JTable matrizU = new JTable();

		JScrollPane spTabla = new JScrollPane(matrizU);
		spTabla.setBounds(10, 25, 766, 165);
		panelTabla.add(spTabla);

		solucionPanel.add(panelTabla);

		JPanel panelCentros = new JPanel();
		panelCentros.setLayout(null);
		panelCentros.setBounds(0, 200, 786, 100);
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
					Double tolerancia = Double.parseDouble(meterTolerancia.getText());
					Integer pesoExponencial = Integer.parseInt(meterPesoExp.getText());
					
					ArrayList<Individuo> centros = (centroAleatorio.isSelected()) ? Controller.getInstance().getDatos().getCentroAleatorio() : generaCentroTexto(meterCentro.getText());
					
					ArrayList<Object> solucion = Controller.getInstance().ejecutaKMedias(centros, tolerancia, pesoExponencial);
					
					panelTabla.removeAll();
					
					String datosMatriz[][] = (String[][]) solucion.get(0);
					String nombreColumna[] = Controller.getInstance().getDatos().getNombreColumna();
					
					JTable matrizU = new JTable(datosMatriz, nombreColumna);
					matrizU.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					matrizU.setShowVerticalLines(true);

					JScrollPane spTabla = new JScrollPane(matrizU);
					spTabla.setBounds(10, 25, 766, 165);
					panelTabla.add(spTabla);
					
					centrosObtenidos.setText((String) solucion.get(1));
					
					repaint();
					revalidate();
				}catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Algo ha fallado en el algoritmo K-Medias", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				
			}

			private ArrayList<Individuo> generaCentroTexto(String text) {
				ArrayList<Individuo> centrosTexto = new ArrayList<Individuo>();
				String[] linea = text.split(",");
				ArrayList<Double> vector = new ArrayList<Double>();
				for(int i = 0; i < linea.length; i++) {
					if(linea[i].equals("*")) {
						centrosTexto.add(new Individuo(vector));
						vector = new ArrayList<Double>();
					}
					else {
						vector.add(Double.parseDouble(linea[i]));
					}
				}//4.6,3.0,4.0,0.0,*,6.8,3.4,4.6,0.7,*
				return centrosTexto;
			}
		});
	}
}
