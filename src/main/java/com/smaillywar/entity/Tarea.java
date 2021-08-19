package com.smaillywar.entity;

import java.time.LocalDateTime;

public class Tarea {
	
	private int id;
	
	private String nombre;
	
	private String descripcion;
	
	private boolean realizada;
	
	private LocalDateTime fechaCreacion;
	
	private LocalDateTime fechaFinalizacion;
	
	private LocalDateTime fechaLimite;
	
	public Tarea() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public LocalDateTime getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(LocalDateTime fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", realizada=" + realizada
				+ ", fechaCreacion=" + fechaCreacion + ", fechaFinalizacion=" + fechaFinalizacion + ", fechaLimite="
				+ fechaLimite + "]";
	}

}
