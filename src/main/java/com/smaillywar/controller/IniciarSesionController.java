package com.smaillywar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.entity.Cuenta;
import com.smaillywar.style.Style;
import com.smaillywar.view.IniciarSesionView;

public class IniciarSesionController {

	private IniciarSesionView view;
	private JFrame ventana;
	
	public IniciarSesionController (JFrame ventana) {
		this.ventana = ventana;
		
		setView();
	}
	
	private void setView() {
		view = new IniciarSesionView();
		ventana.setContentPane(view);
		ventana.setTitle("Iniciar Sesión");
		ventana.revalidate();
		
		addListeners();
	}
	
	private void addListeners() {
		view.addListenerBtnCancelar(new ListenerBtnCancelar());
		view.addListenerBtnIniciarSesion(new ListenerBtnIniciarSesion());
	}
	
	private class ListenerBtnCancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new MenuController(ventana);
		}
	}
	
	private class ListenerBtnIniciarSesion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!view.getUsuario().matches(Style.regexDatoCuenta())) {
				JOptionPane.showMessageDialog(ventana, "Usuario no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!view.getContrasena().matches(Style.regexDatoCuenta())) {
				JOptionPane.showMessageDialog(ventana, "Contraseña no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Cuenta cuenta = DatosSesion.getCuenta(view.getUsuario());
			
			if (cuenta == null) {
				JOptionPane.showMessageDialog(ventana, "Usuario no encontrado.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!new BCryptPasswordEncoder().matches(view.getContrasena(), cuenta.getContrasena())) {
				JOptionPane.showMessageDialog(ventana, "Contraseña incorrecta.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			DatosSesion.setCuentaLogueada(cuenta);
			
			new MenuLogueadoController(ventana);
		}
	}
}
