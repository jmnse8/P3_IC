package modelo.algoritmos;

import java.util.ArrayList;

import modelo.Datos;
import modelo.Individuo;

public class AlgoritmoLloyd {

	private ArrayList<Individuo> centros;
	
	public ArrayList<Object> ejecutaAlgoritmo(Datos datos, ArrayList<Individuo> centro, Double tolerancia, Double razonAprendizaje) {
		ArrayList<Object> solucion = new ArrayList<Object>();
		
		centros = new ArrayList<Individuo>(datos.getClases().size());
		String centrosSol = "";
		
		lloyd(datos, centro, tolerancia, razonAprendizaje);

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

	private void lloyd(Datos datos, ArrayList<Individuo> centro, Double tolerancia, Double rznApr) {
		ArrayList<Individuo> centrosAnt = centro;
		ArrayList<Individuo> centrosNuev = centro;
		
		for(Individuo ind: datos.getMatriz()) {
			centrosNuev = calculaCentros(centrosNuev, ind, rznApr);
		}
		while(!salir(tolerancia, centrosAnt, centrosNuev)) {
			centrosAnt = centrosNuev;
			for(Individuo ind: datos.getMatriz()) {
				centrosNuev = calculaCentros(centrosNuev, ind, rznApr);
			}
		}
		
		centros = centrosNuev;
	}

	private boolean salir(Double tolerancia, ArrayList<Individuo> centrosAnt, ArrayList<Individuo> centrosNuev) {
		for (int i = 0; i < centrosAnt.size(); i++) {
			if (centrosNuev.get(i).calcularD(centrosAnt.get(i), false) >= tolerancia) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<Individuo> calculaCentros(ArrayList<Individuo> centrosC, Individuo ind, Double rznApr) {
		ArrayList<Individuo> centrosNuevos = centrosC;
		int centroCambiar = calcularCentroCambiar(centrosC, ind);
		centrosNuevos.set(centroCambiar, calculaCentroElegido(centrosC, ind, rznApr, centroCambiar));
		return centrosNuevos;
	}

	private Individuo calculaCentroElegido(ArrayList<Individuo> centrosC, Individuo ind, Double rznApr, int centroCambiar) {
		ArrayList<Double> sol = new ArrayList<Double>();
		for(int i = 0; i < ind.getNumeros().size(); i++) {
			sol.add(centrosC.get(centroCambiar).getNumeros().get(i) + (rznApr * (ind.getNumeros().get(i) - centrosC.get(centroCambiar).getNumeros().get(i))));
		}
		Individuo indCalculado = new Individuo(sol);
		return indCalculado;
	}

	private int calcularCentroCambiar(ArrayList<Individuo> centrosC, Individuo ind) {
		int mejorClase = 0;
		double dist = 0.0;
		double distMejor = Double.MAX_VALUE;
		for(int i = 0; i < centrosC.size(); i++) {
			dist = Math.abs(ind.calcularD(centrosC.get(i), true));
			if (dist < distMejor) {
				distMejor = dist;
				mejorClase = i;
			}
		}
		return mejorClase;
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
}
