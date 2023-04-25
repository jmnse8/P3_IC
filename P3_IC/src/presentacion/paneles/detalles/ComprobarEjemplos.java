package presentacion.paneles.detalles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controller.Controller;
import modelo.Individuo;

public class ComprobarEjemplos extends JPanel {

	private static final long serialVersionUID = 1L;

	private String tipoPadre;

	private JButton comprobar;
	private JTextArea input;
	private JTextArea output;

	public ComprobarEjemplos(String tipoPadre) {
		this.tipoPadre = tipoPadre;
		iniGUI();
	}

	private void iniGUI() {
		setLayout(null);// 393
		setBounds(0, 500, 786, 200);

		JPanel panelInput = new JPanel();
		panelInput.setLayout(null);
		panelInput.setBounds(0, 0, 393, 165);
		panelInput.setBorder(new TitledBorder("Meter ejemplo "));

		JPanel panelOutput = new JPanel();
		panelOutput.setLayout(null);
		panelOutput.setBounds(393, 0, 393, 165);
		panelOutput.setBorder(new TitledBorder("Resultado "));

		input = new JTextArea();

		JScrollPane spI = new JScrollPane(input);
		spI.setBounds(10, 25, 373, 130);
		panelInput.add(spI);

		output = new JTextArea();
		JScrollPane spO = new JScrollPane(output);
		spO.setBounds(10, 25, 373, 130);
		panelOutput.add(spO);

		add(panelInput);
		add(panelOutput);

		comprobar = new JButton("Comprobar ejemplo");
		comprobar.setBounds(311, 165, 164, 30);
		comprobarListener();
		add(comprobar);

	}

	private void comprobarListener() {
		comprobar.addActionListener(new ActionListener() {//5.1,3.5,1.4,0.2,*,6.9,3.1,4.9,1.5,*,5.0,3.4,1.5,0.2,*
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sol = "";
				switch (tipoPadre) {
				case "KMedias":
					for(Individuo ind: getInd()) {
						sol += Controller.getInstance().compruebaKMedias(ind) + "\n";
					}
					output.setText(sol);
					break;
				case "Bayes":
					for(Individuo ind: getInd()) {
						sol += Controller.getInstance().compruebaBayes(ind) + "\n";
					}
					output.setText(sol);
					break;
				case "Lloyd":
					for(Individuo ind: getInd()) {
						sol += Controller.getInstance().compruebaLloyd(ind) + "\n";
					}
					output.setText(sol);
					break;
				default:
					JOptionPane.showMessageDialog(null, "Algo ha fallado en la aplicación", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				}

			}
		});
	}
	
	private ArrayList<Individuo> getInd() {
		ArrayList<Individuo> centrosTexto = new ArrayList<Individuo>();
		String[] linea = input.getText().split(",");
		ArrayList<Double> vector = new ArrayList<Double>();
		for(int i = 0; i < linea.length; i++) {
			if(linea[i].equals("*")) {
				centrosTexto.add(new Individuo(vector));
				vector = new ArrayList<Double>();
			}
			else {
				vector.add(Double.parseDouble(linea[i]));
			}
		}
		return centrosTexto;
	}
}
