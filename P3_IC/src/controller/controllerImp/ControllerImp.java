package controller.controllerImp;

import java.io.File;
import java.util.ArrayList;

import controller.Controller;
import modelo.Datos;
import modelo.Individuo;
import modelo.algoritmos.AlgoritmoBayes;
import modelo.algoritmos.AlgoritmoKMedias;
import modelo.algoritmos.AlgoritmoLloyd;

public class ControllerImp extends Controller{
	private Datos datos;
	
	private AlgoritmoKMedias algKMedias;
	private AlgoritmoBayes algBayes;
	private AlgoritmoLloyd algLloyd;
	
	public ControllerImp() {
		datos = new Datos();
		algKMedias = new AlgoritmoKMedias();
		algBayes = new AlgoritmoBayes();
		algLloyd = new AlgoritmoLloyd();
	}

	@Override
	public boolean meteFichero(File fichero) {
		datos = new Datos();
		return datos.cargaDatos(fichero);
	}
	
	@Override
	public String compruebaKMedias(Individuo ind) {
		return algKMedias.comprueba(ind, datos);
	}

	@Override
	public String compruebaBayes(Individuo ind) {
		return algBayes.comprueba(ind, datos);
	}

	@Override
	public String compruebaLloyd(Individuo ind) {
		return algLloyd.comprueba(ind, datos);
	}

	@Override
	public ArrayList<Object> ejecutaKMedias(ArrayList<Individuo> centro, Double tolerancia, Integer pesoExponencial) {
		algKMedias = new AlgoritmoKMedias();
		return algKMedias.ejecutaAlgoritmo(datos, centro, tolerancia, pesoExponencial);
	}
	
	@Override
	public ArrayList<Object> ejecutaLloyd(ArrayList<Individuo> centros, Double tolerancia, Double razonAprendizaje) {
		algLloyd = new AlgoritmoLloyd();
		return algLloyd.ejecutaAlgoritmo(datos, centros, tolerancia, razonAprendizaje);
	}
	
	@Override
	public ArrayList<Object> ejecutaBayes() {
		algBayes = new AlgoritmoBayes();
		return algBayes.ejecutaAlgoritmo(datos);
	}

	@Override
	public Datos getDatos() {
		return datos;
	}
}
