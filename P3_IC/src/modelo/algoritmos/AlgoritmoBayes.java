package modelo.algoritmos;

import java.util.ArrayList;

import Jama.Matrix;
import modelo.Datos;
import modelo.Individuo;

public class AlgoritmoBayes {

	private ArrayList<Double[][]> matricesU;
	private ArrayList<String[]> nombresColumnas;

	private ArrayList<Individuo> medias;

	public ArrayList<Object> ejecutaAlgoritmo(Datos datos) {
		ArrayList<Object> solucion = new ArrayList<Object>();

		ArrayList<String[][]> datosMatrices = new ArrayList<String[][]>();// new String[numFilas][numColumnas];
		String centrosSol = "";

		medias = new ArrayList<Individuo>();
		matricesU = new ArrayList<Double[][]>();
		nombresColumnas = new ArrayList<String[]>();
		int numTam = datos.getMatriz().get(0).getNumeros().size();

		bayes(datos);

		for (int x = 0; x < datos.getClases().size(); x++) {
			datosMatrices.add(new String[numTam][numTam]);
			for (int i = 0; i < numTam; i++) {
				String[] ncol = new String[numTam];
				for (int j = 0; j < numTam; j++) {
					ncol[j] = "" + j;
					datosMatrices.get(x)[i][j] = "" + String.format("%.3f", matricesU.get(x)[i][j]);
				}
				nombresColumnas.add(ncol);
			}
		}
		solucion.add(datosMatrices);

		solucion.add(nombresColumnas);

		for (Individuo ind : medias) {
			centrosSol += "|" + ind.getClase();
			for (Double d : ind.getNumeros()) {
				centrosSol += " " + String.format("%.3f", d);
			}
			centrosSol += " |\n";
		}

		solucion.add(centrosSol);
		return solucion;
	}

	public String comprueba(Individuo ind, Datos datos) {//5.1,3.5,1.4,0.2,*,6.9,3.1,4.9,1.5,*,5.0,3.4,1.5,0.2,*
		int mejorClase = 0;
		double dist = 0.0;
		double distMejor = -1;
		for (int i = 0; i < medias.size(); i++) {
			dist = calculaP(ind, i, datos);
			if (dist > distMejor) {
				distMejor = dist;
				mejorClase = i;
			}
		}
		return datos.getClases().get(mejorClase);
	}

	private double calculaP(Individuo ind, int i, Datos datos) {
		int numTam = datos.getMatriz().get(0).getNumeros().size();
		double[][] xMenosMedia = new double[numTam][1];

		Matrix mC = new Matrix(cambiaADouble(matricesU.get(i).clone(), numTam));
		
		// calculo xi menos la media
		for (int p = 0; p < numTam; p++) {
			xMenosMedia[p][0] = (ind.getNumeros().get(p) - this.medias.get(i).getNumeros().get(p));
		}
		Matrix xMM = Matrix.constructWithCopy(xMenosMedia);
		
		int d = numTam;
		Matrix cInversa = mC.inverse();
		double aux1 =  (((xMM).transpose()).times(cInversa)).times(xMM).get(0, 0); // 1 x 1
		double aux2 = aux1 * -0.5;
		double f = Math.exp(aux2);
		double sol = f * 1.0/(Math.pow(2*Math.PI, d/2)*Math.pow(mC.det(), 0.5));
		return sol;
	}

	private void bayes(Datos datos) {

		// Calculo medias
		ArrayList<AyudaMedia> medias = new ArrayList<>();
		for (String cl : datos.getClases()) {
			medias.add(new AyudaMedia(cl, datos.getMatriz().get(0).getNumeros().size()));
		}
		for (Individuo i : datos.getMatriz()) {
			for (AyudaMedia me : medias) {
				if (me.getClase().equals(i.getClase())) {
					me.sumaNum(i.getNumeros());
				}
			}
		}

		int numTam = datos.getMatriz().get(0).getNumeros().size();

		for (AyudaMedia aM : medias) {
			this.medias.add(new Individuo(aM.getMedia()));
		}

		// Calculo matrices
		for (int i = 0; i < datos.getClases().size(); i++) {

			Double[][] aux = new Double[numTam][numTam];
			for (int m = 0; m < numTam; m++) {// inicializo a cero la matriz
				for (int mn = 0; mn < numTam; mn++) {
					aux[m][mn] = 0.0;
				}
			}
			int n = 0;

			for (Individuo ind : datos.getMatriz()) {
				if (datos.getClases().get(i).equals(ind.getClase())) {
					n++;
					ArrayList<Double> xMenosMedia = new ArrayList<Double>();
					// calculo xi menos la media
					for (int p = 0; p < numTam; p++) {
						xMenosMedia.add(ind.getNumeros().get(p) - this.medias.get(i).getNumeros().get(p));
					}

					// Calculo matriz
					for (int m = 0; m < numTam; m++) {
						for (int mn = 0; mn < numTam; mn++) {
							aux[m][mn] += (xMenosMedia.get(m) * xMenosMedia.get(mn));
						}
					}
				}
			}
			// divido cada uno entre n
			for (int m = 0; m < numTam; m++) {
				for (int mn = 0; mn < numTam; mn++) {
					aux[m][mn] = aux[m][mn] / n;
				}
			}
			matricesU.add(aux);
		}
	}

	private class AyudaMedia {
		ArrayList<Double> suma;
		int cont;
		String clase;

		public AyudaMedia(String clase, int size) {
			suma = new ArrayList<Double>();
			for (int i = 0; i < size; i++) {
				suma.add(0.0);
			}
			cont = 0;
			this.clase = clase;
		}

		public void sumaNum(ArrayList<Double> arrayList) {
			// suma += arrayList;
			for (int i = 0; i < arrayList.size(); i++) {
				suma.set(i, suma.get(i) + arrayList.get(i));
			}
			cont++;
		}

		public ArrayList<Double> getMedia() {
			ArrayList<Double> aux = new ArrayList<Double>();
			for (int i = 0; i < suma.size(); i++) {
				aux.add(suma.get(i) / cont);
			}
			return aux;
		}

		public String getClase() {
			return clase;
		}
	}

	private double[][] cambiaADouble(Double[][] clone, int numTam) {
		double[][] aux = new double[numTam][numTam];
		for (int ii = 0; ii < numTam; ii++) {
			for (int j = 0; j < numTam; j++) {
				aux[ii][j] = clone[ii][j];
			}
		}
		return aux;
	}
}
