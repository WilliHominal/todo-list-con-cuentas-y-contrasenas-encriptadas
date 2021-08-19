package com.smaillywar.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smaillywar.style.CustomJButton;
import com.smaillywar.style.Style;

public class MenuView extends JPanel {
	private static final long serialVersionUID = -770806064626922466L;

	private JLabel panelPrincipal;
	
	private Style style;
	
	private CustomJButton iniciarSesionBtn;
	private CustomJButton crearCuentaBtn;
	
	public MenuView() {
		inicializarComponentes();
		ubicarComponentes();
	}
	
	private void inicializarComponentes() {
		style = new Style();
		panelPrincipal = style.setFondo("Fondo");
		
		iniciarSesionBtn = new CustomJButton("INICIAR SESIÓN");
		crearCuentaBtn = new CustomJButton("CREAR CUENTA");
		
		style.setEstiloCustomBtn(iniciarSesionBtn);
		style.setEstiloCustomBtn(crearCuentaBtn);
	}
	
	private void ubicarComponentes() {
		setLayout(new GridBagLayout());
		add(panelPrincipal);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		cons.insets.set(0, 0, 75, 0);
		panelPrincipal.add(iniciarSesionBtn, cons);
		
		cons.gridy = 1;
		cons.insets.set(0, 0, 0, 0);
		panelPrincipal.add(crearCuentaBtn, cons);
	}
	
	public void addListenerBtnIniciarSesion(ActionListener listener){
		iniciarSesionBtn.addActionListener(listener);
	}
	
	public void addListenerBtnCrearCuenta(ActionListener listener) {
		crearCuentaBtn.addActionListener(listener);
	}
}
