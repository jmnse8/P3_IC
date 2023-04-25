package modelo.algoritmos;

import java.util.ArrayList;

import modelo.Datos;
import modelo.Individuo;

public class AlgoritmoKMedias {

	private Double[][] matrizU;
	private int filasMatrizU;
	private int columnasMatrizU;

	private ArrayList<Individuo> centros;

	public ArrayList<Object> ejecutaAlgoritmo(Datos datos, ArrayList<Individuo> centro, Double tolerancia, Integer pesoExponencial) {
		ArrayList<Object> solucion = new ArrayList<Object>();
		int numFilas = datos.getClases().size(), numColumnas = datos.getMatriz().size() + 1;

		String datosMatriz[][] = new String[numFilas][numColumnas];
		String centrosSol = "";

		filasMatrizU = numFilas;
		columnasMatrizU = numColumnas - 1;
		matrizU = new Double[filasMatrizU][columnasMatrizU];
		centros = new ArrayList<Individuo>(numFilas);

		kMedias(datos, centro, tolerancia, pesoExponencial);

		for (int i = 0; i < numFilas; i++) {
			datosMatriz[i][0] = datos.getClases().get(i);
			for (int j = 1; j < numColumnas; j++) {
				//System.out.println( i + " " + j +" " +matrizU[i][j - 1]);
				datosMatriz[i][j] = "" + String.format("%.3f", matrizU[i][j - 1]);
			}
		}

		solucion.add(datosMatriz);

		for (Individuo ind : centros) {
			centrosSol += "|" + ind.getClase();
			for (Double d : ind.getNumeros()) {
				centrosSol += " " + String.format("%.3f",d);
			}
			centrosSol += " |\n";
		}

		solucion.add(centrosSol);
		return solucion;
	}

	public String comprueba(Individuo ind, Datos datos) {
		int mejorClase = 0;
		double dist = 0.0;
		double distMejor = Double.MAX_VALUE;
		for(int i = 0; i < centros.size(); i++) {
			dist = Math.abs(ind.calcularD(centros.get(i), true));
			if (dist < distMejor) {
				distMejor = dist;
				mejorClase = i;
			}
		}
		return datos.getClases().get(mejorClase);
	}

	private void kMedias(Datos datos, ArrayList<Individuo> centro, Double tolerancia, Integer pesoExponencial) {
		ArrayList<Individuo> centrosAnt = centro;
		ArrayList<Individuo> centrosNuev = null;
		
		calculaMatriz(centrosAnt, datos, pesoExponencial);
		centrosNuev = calculaCentros(centrosAnt, datos, pesoExponencial);

		while (!salir(tolerancia, centrosAnt, centrosNuev)) {
			centrosAnt = centrosNuev;
			calculaMatriz(centrosAnt, datos, pesoExponencial);
			centrosNuev = calculaCentros(centrosAnt, datos, pesoExponencial);
		}
		centros = centrosNuev;
	}

	private ArrayList<Individuo> calculaCentros(ArrayList<Individuo> centrosAnt, Datos datos, Integer pesoExponencial) {
		ArrayList<Individuo> centrosNuevos = new ArrayList<Individuo>();
		for (int i = 0; i < filasMatrizU; i++) {
			
			ArrayList<Double> vector = new ArrayList<Double>();
			for(int x = 0; x < datos.getMatriz().get(0).getNumeros().size(); x++) {
				vector.add(0.0);
			}
			Double denominador = 0.0;
			
			for (int j = 0; j < columnasMatrizU; j++) {
				Double aux = Math.pow( matrizU[i][j], pesoExponencial);
				for(int x = 0; x < datos.getMatriz().get(0).getNumeros().size(); x++) {
					vector.set(x, vector.get(x) + (aux * datos.getMatriz().get(j).getNumeros().get(x)));
				}
				denominador += aux;
			}
			
			for(int x = 0; x < datos.getMatriz().get(0).getNumeros().size(); x++) {
				vector.set(x, vector.get(x) / denominador);
			}
			
			centrosNuevos.add(new Individuo(vector));
		}

		return centrosNuevos;
	}

	private void calculaMatriz(ArrayList<Individuo> centrosAnt, Datos datos, Integer pesoExponencial) {
		matrizU = new Double[filasMatrizU][columnasMatrizU];
		for (int j = 0; j < columnasMatrizU; j++) {
			Double numeradores[] = new Double[filasMatrizU];
			Double denominador = 0.0;
			for (int i = 0; i < filasMatrizU; i++) {
				double d = datos.getMatriz().get(j).calcularD(centrosAnt.get(i), true);
				numeradores[i] = Math.pow(1 / d, 1 / (pesoExponencial - 1));
				denominador += numeradores[i];
			}
			for (int i = 0; i < filasMatrizU; i++) {
				matrizU[i][j] = numeradores[i] / denominador;
			}
		}

	}

	private boolean salir(Double tolerancia, ArrayList<Individuo> centrosAnt, ArrayList<Individuo> centrosNuev) {
		for (int i = 0; i < centrosAnt.size(); i++) {
			if (centrosNuev.get(i).calcularD(centrosAnt.get(i), false) >= tolerancia) {
				return false;
			}
		}
		return true;
	}

	
}
