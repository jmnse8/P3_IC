package controller;

import java.io.File;
import java.util.ArrayList;

import controller.controllerImp.ControllerImp;
import modelo.Datos;
import modelo.Individuo;

public abstract class Controller {

	private static Controller instance;

	public synchronized static Controller getInstance() {
		if (instance == null)
			instance = new ControllerImp();
		return instance;
	}
	
	public abstract boolean meteFichero(File fichero);
	public abstract String compruebaKMedias(Individuo ind);
	public abstract String compruebaBayes(Individuo ind);
	public abstract String compruebaLloyd(Individuo ind);
	public abstract ArrayList<Object> ejecutaKMedias(ArrayList<Individuo> centro, Double tolerancia, Integer pesoExponencial);
	public abstract ArrayList<Object> ejecutaLloyd(ArrayList<Individuo> centros, Double tolerancia, Double razonAprendizaje);
	public abstract Datos getDatos();
	public abstract ArrayList<Object> ejecutaBayes();
}
