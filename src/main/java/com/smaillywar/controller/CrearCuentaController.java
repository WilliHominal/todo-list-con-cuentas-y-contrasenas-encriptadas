package com.smaillywar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.entity.Cuenta;
import com.smaillywar.style.Style;
import com.smaillywar.view.CrearCuentaView;

public class CrearCuentaController {

	private CrearCuentaView view;
	private JFrame ventana;
	
	public CrearCuentaController (JFrame ventana) {
		this.ventana = ventana;
		
		setView();
	}
	
	private void setView() {
		view = new CrearCuentaView();
		ventana.setContentPane(view);
		ventana.setTitle("Crear Cuenta");
		ventana.revalidate();
		
		addListeners();
	}
	
	private void addListeners() {
		view.addListenerBtnCancelar(new ListenerBtnCancelar());
		view.addListenerBtnCrearCuenta(new ListenerBtnCrearCuenta());
	}
	
	private class ListenerBtnCancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new MenuController(ventana);
		}
	}
	
	private class ListenerBtnCrearCuenta implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Cuenta cuenta = new Cuenta();
			String usuario = view.getUsuario();
			
			if (!usuario.matches(Style.regexDatoCuenta())) {
				JOptionPane.showMessageDialog(ventana, "Nombre de usuario no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (DatosSesion.getCuenta(usuario) != null) {
				JOptionPane.showMessageDialog(ventana, "El nombre de usuario ya está en uso.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!view.getContrasena().matches(Style.regexDatoCuenta())) {
				JOptionPane.showMessageDialog(ventana, "Contraseña no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			cuenta.setUsuario(usuario);
			cuenta.setContrasena(new BCryptPasswordEncoder().encode(view.getContrasena()));
			
			DatosSesion.addCuenta(cuenta);
			JOptionPane.showConfirmDialog(ventana, "Cuenta creada con éxito.", "OK", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
			new MenuController(ventana);
		}
	}
}
