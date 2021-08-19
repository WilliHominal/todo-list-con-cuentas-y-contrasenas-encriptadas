package com.smaillywar.entity;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
	
	private int id;

	private String usuario;
	
	private String contrasena;
	
	private List<Tarea> tareas;
	
	public Cuenta() {
		tareas = new ArrayList<>();
	}

	public Cuenta(String usuario, String contrasena) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.tareas = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", usuario=" + usuario + ", contrasena=" + contrasena + ", tareas=" + tareas + "]";
	}
	
	public void addTarea(Tarea tarea) {
		tarea.setId(tareas.size());
		tareas.add(tarea);
	}
}
