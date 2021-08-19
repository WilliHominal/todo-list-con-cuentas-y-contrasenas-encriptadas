package com.smaillywar.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smaillywar.controller.MenuController;
import com.smaillywar.data.DatosSesion;
import com.smaillywar.entity.Cuenta;

public class AppPrincipal {

	private static JFrame ventana;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventana = new JFrame();
					setOpcionesVentana();
					addListenerVentana();
					//TODO BORRAR CUENTA DE PRUEBA
					DatosSesion.addCuenta(new Cuenta("123123", new BCryptPasswordEncoder().encode("123123")));
					new MenuController(ventana);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void addListenerVentana() {
		ventana.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	ventana.dispose();
				System.exit(0);
		    }
		});
	}
	
	private static void setOpcionesVentana() {
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
}
