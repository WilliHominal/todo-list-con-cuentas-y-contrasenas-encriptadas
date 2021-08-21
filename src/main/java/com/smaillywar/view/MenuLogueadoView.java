package com.smaillywar.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.style.CustomJButton;
import com.smaillywar.style.Style;

public class MenuLogueadoView extends JLabel {
	private static final long serialVersionUID = -4351819806784338248L;
	
	private Style style;
	
	private JLabel nombreUsuarioLabel;
	
	// DIVISION PANEL IZQ Y DER + FONDOS
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JLabel panelPrincipalIzquierdo;
	private JLabel panelPrincipalDerecho;
	
	// BOTONES IZQ
	private CustomJButton agregarTareaBtn;
	private CustomJButton administrarTareasBtn;
	private CustomJButton cerrarSesionBtn;
	
	public MenuLogueadoView() {
		inicializarComponentes();
		ubicarComponentes();
	}
	
	private void inicializarComponentes() {
		style = new Style();
		
		panelPrincipalIzquierdo = style.setFondo("FondoIzq", 576*DatosSesion.getResolucionPantalla().width/1920, DatosSesion.getResolucionPantalla().height);
		panelPrincipalDerecho = style.setFondo("FondoDer", 1344*DatosSesion.getResolucionPantalla().width/1920, DatosSesion.getResolucionPantalla().height);
		
		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new GridBagLayout());
		panelIzquierdo.setPreferredSize(new Dimension(576*DatosSesion.getResolucionPantalla().width/1920, DatosSesion.getResolucionPantalla().height));
		panelDerecho = new JPanel();
		panelDerecho.setLayout(new GridBagLayout());
		panelDerecho.setPreferredSize(new Dimension(1344*DatosSesion.getResolucionPantalla().width/1920, DatosSesion.getResolucionPantalla().height));
		
		nombreUsuarioLabel = new JLabel("BIENVENIDO USUARIO " + DatosSesion.getCuentaLogueada().getUsuario().toUpperCase());
		
		agregarTareaBtn = new CustomJButton("AGREGAR TAREA");
		administrarTareasBtn = new CustomJButton("GESTIONAR TAREAS");
		cerrarSesionBtn = new CustomJButton("CERRAR SESIÓN");
		
		style.setEstiloLabel(nombreUsuarioLabel);
		style.setEstiloCustomBtn(agregarTareaBtn);
		style.setEstiloCustomBtn(administrarTareasBtn);
		style.setEstiloCustomBtn(cerrarSesionBtn);
	}
	
	private void ubicarComponentes() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		int espacioEntreBotones = 55*DatosSesion.getResolucionPantalla().height/1080;
		
		cons.insets.set(0, 0, espacioEntreBotones, 0);
		panelPrincipalIzquierdo.add(agregarTareaBtn, cons);
		
		cons.gridy = 1;
		panelPrincipalIzquierdo.add(administrarTareasBtn, cons);
		
		cons.gridy = 2;
		cons.insets.set(0, 0, 0, 0);
		panelPrincipalIzquierdo.add(cerrarSesionBtn, cons);
		
		//
		cons = new GridBagConstraints();
		add(panelPrincipalIzquierdo, cons);
		cons.gridx = 1;
		add(panelPrincipalDerecho, cons);
	}
	
	public JLabel getPanelPrincipalDerecho () {
		return panelPrincipalDerecho;
	}
	
	public void setBotonActual(int btnActual) {
		switch(btnActual) {
		case 1:
			agregarTareaBtn.setEnabled(false);
			administrarTareasBtn.setEnabled(true);
			break;
		case 2:
			agregarTareaBtn.setEnabled(true);
			administrarTareasBtn.setEnabled(false);
			break;
		case 3:
			agregarTareaBtn.setEnabled(true);
			administrarTareasBtn.setEnabled(true);
			break;
		case 4:
			agregarTareaBtn.setEnabled(true);
			administrarTareasBtn.setEnabled(true);
			break;
		default:
			agregarTareaBtn.setEnabled(true);
			administrarTareasBtn.setEnabled(true);
		}
	}
	
	public void addListenerBotonAgregarTarea(ActionListener listener) {
		agregarTareaBtn.addActionListener(listener);
	}
	
	public void addListenerBotonListarTareas(ActionListener listener) {
		administrarTareasBtn.addActionListener(listener);
	}
	
	public void addListenerBotonCerrarSesion(ActionListener listener) {
		cerrarSesionBtn.addActionListener(listener);
	}
}
