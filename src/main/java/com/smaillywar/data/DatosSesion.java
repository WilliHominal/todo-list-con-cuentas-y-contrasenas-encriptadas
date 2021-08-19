package com.smaillywar.data;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import com.smaillywar.entity.Cuenta;
import com.smaillywar.entity.Tarea;

public class DatosSesion {

	public static Dimension tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static List<Cuenta> cuentas = new ArrayList<>();
	
	private static Cuenta cuentaLogueada = null;
	
	public static void addCuenta(Cuenta cuenta) {
		cuenta.setId(cuentas.size());
		cuentas.add(cuenta);
	}
	
	public static List<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public static Cuenta getCuenta (String nombre) {
		Cuenta cuentaEncontrada = null;
		for (Cuenta c : cuentas) {
			if (c.getUsuario().equals(nombre))
				cuentaEncontrada = c;
		}
		return cuentaEncontrada;
	}
	
	public static Cuenta getCuentaLogueada() {
		return cuentaLogueada;
	}
	
	public static void setCuentaLogueada(Cuenta cuenta) {
		cuentaLogueada = cuenta;
	}
	
	public static Tarea getTareaCuentaLogueada(int id) {
		List<Tarea> tareas = cuentaLogueada.getTareas();
		for (Tarea t : tareas) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
}
