package com.smaillywar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.view.MenuView;

public class MenuController {

	private JFrame ventana;
	private MenuView view;
	
	public MenuController(JFrame ventana) {
		this.ventana = ventana;
		
		setView();
	}
	
	private void setView() {
		if (DatosSesion.getCuentaLogueada() != null) {
			new MenuLogueadoController(ventana);
		} else {
			this.view = new MenuView();
			
			ventana.setContentPane(view);
			ventana.setTitle("Menu");
			ventana.revalidate();
			
			view.addListenerBtnIniciarSesion(new ListenerBtnIniciarSesion());
			view.addListenerBtnCrearCuenta(new ListenerBtnCrearCuenta());
		}
	}
	
	private class ListenerBtnIniciarSesion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new IniciarSesionController(ventana);
		}
	}
	
	private class ListenerBtnCrearCuenta implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new CrearCuentaController(ventana);
		}
	}
}
