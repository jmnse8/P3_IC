package modelo;

import java.util.ArrayList;

public class Individuo {
	private ArrayList<Double> vector;
	private String clase;
	private String nombre;

	public Individuo() {
		vector = new ArrayList<Double>();
		clase = "";
	}

	public Individuo(ArrayList<Double> vector) {
		this.vector = vector;
		clase = "";
		nombre = "";
	}

	public void meteNumero(Double num) {
		vector.add(num);
	}

	public ArrayList<Double> getNumeros() {
		return vector;
	}

	public void setNumeros(ArrayList<Double> numeros) {
		this.vector = numeros;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Double calcularD(Individuo individuoDer, boolean exp) {
		Double distancia = 0.0;
		for (int i = 0; i < this.getNumeros().size(); i++) {
			distancia += Math.pow(this.getNumeros().get(i) - individuoDer.getNumeros().get(i), 2);
		}
		if (!exp)
			distancia = Math.sqrt(distancia);
		return distancia;
	}
}
