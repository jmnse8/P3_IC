package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Datos {
	private String nombreColumna[];
	private ArrayList<Individuo> matriz;
	private ArrayList<String> clases;
	
	public Datos() {
		
	}

	public boolean cargaDatos(File fichero) {
		matriz = new ArrayList<Individuo>();
		clases = new ArrayList<String>();
		int cont = 1;
		try {
			InputStream inFileArchivoNom = new FileInputStream(fichero);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inFileArchivoNom));
			while (reader.ready()) {
				Individuo ind = new Individuo();
				ind.setNombre("x" + cont);
				String linea = reader.readLine();
				String[] lineaDividida = linea.split(",");
				for (int i = 0; i < lineaDividida.length; i++) {
					if(i != lineaDividida.length - 1) {
						ind.meteNumero(Double.parseDouble(lineaDividida[i]));
					}
					else {
						ind.setClase(lineaDividida[i]);
						if(!clases.contains(lineaDividida[i])) {
							clases.add(lineaDividida[i]);
						}
					}
				}
				cont++;
				matriz.add(ind);
			}
			reader.close();
			
			nombreColumna = new String[matriz.size() + 1];
			nombreColumna[0] = "Clases";
			for (int i = 1; i < nombreColumna.length; i++) {
				nombreColumna[i] = matriz.get(i - 1).getNombre();
			}
		}
		catch(IOException e) {
			return false;
		}
		return true;
	}

	public ArrayList<Individuo> getCentroAleatorio() {
		ArrayList<Individuo> centrosNuevos = new ArrayList<Individuo>();
		for (int i = 0; i < clases.size(); i++) {
			ArrayList<Double> vector = new ArrayList<Double>();
			for (int j = 0; j < matriz.get(0).getNumeros().size(); j++) {
				vector.add((Math.floor( Math.random() * 1000)) / 100);
			}
			centrosNuevos.add(new Individuo(vector));
		}
		return centrosNuevos;
	}

	public String[] getNombreColumna() {
		return nombreColumna;
	}

	public ArrayList<Individuo> getMatriz() {
		return matriz;
	}

	public ArrayList<String> getClases() {
		return clases;
	}

	
}
