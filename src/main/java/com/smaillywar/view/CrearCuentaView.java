package com.smaillywar.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.style.CustomJButton;
import com.smaillywar.style.Style;

public class CrearCuentaView extends JPanel {
	private static final long serialVersionUID = -9002960068849049064L;
	
	private JLabel panelPrincipal;
	private Style style;
	
	private JLabel usuarioLabel;
	private JLabel contrasenaLabel;
	
	private JTextField usuarioTF;
	private JPasswordField contrasenaTF;
	
	private CustomJButton crearCuentaBtn;
	private CustomJButton cancelarBtn;
	
	public CrearCuentaView() {
		inicializarComponentes();
		ubicarComponentes();
	}
	
	private void inicializarComponentes() {
		style = new Style();
		panelPrincipal = style.setFondo("Fondo", DatosSesion.getResolucionPantalla().width, DatosSesion.getResolucionPantalla().height);
		
		usuarioLabel = new JLabel("USUARIO:");
		contrasenaLabel = new JLabel("CONTRASEÑA:");
		
		usuarioTF = new JTextField(20);
		contrasenaTF = new JPasswordField(20);
		
		crearCuentaBtn = new CustomJButton("CREAR CUENTA");
		cancelarBtn = new CustomJButton("CANCELAR");
		
		style.setEstiloLabel(usuarioLabel);
		style.setEstiloLabel(contrasenaLabel);
		style.setEstiloTextField(usuarioTF);
		style.setEstiloTextField(contrasenaTF);
		style.setEstiloCustomBtnChico(crearCuentaBtn);
		style.setEstiloCustomBtnChico(cancelarBtn);
	}
	
	private void ubicarComponentes() {
		setLayout(new GridBagLayout());
		add(panelPrincipal);
		
		GridBagConstraints cons = new GridBagConstraints();
		
		int separacionCampos = 25*DatosSesion.getResolucionPantalla().height/1080;
		int separacionLabelTF = 10*DatosSesion.getResolucionPantalla().width/1920;
		
		cons.insets.set(0, 0, separacionCampos, separacionLabelTF);
		cons.anchor = GridBagConstraints.EAST;
		panelPrincipal.add(usuarioLabel, cons);
		
		cons.gridx = 1;
		cons.insets.set(0, 0, separacionCampos, 0);
		cons.anchor = GridBagConstraints.WEST;
		panelPrincipal.add(usuarioTF, cons);
		
		cons.gridy = 1;
		cons.insets.set(0, 0, 2*separacionCampos, 0);
		panelPrincipal.add(contrasenaTF, cons);
		
		cons.gridx = 0;
		cons.insets.set(0, 0, 2*separacionCampos, separacionLabelTF);
		cons.anchor = GridBagConstraints.EAST;
		panelPrincipal.add(contrasenaLabel, cons);
		
		cons.gridy = 2;
		cons.insets.set(0, 0, 0, 0);
		cons.anchor = GridBagConstraints.WEST;
		panelPrincipal.add(crearCuentaBtn, cons);
		
		cons.gridx = 1;
		cons.anchor = GridBagConstraints.EAST;
		panelPrincipal.add(cancelarBtn, cons);
	}
	
	public String getUsuario() {
		return usuarioTF.getText();
	}
	
	public String getContrasena() {
		return String.valueOf(contrasenaTF.getPassword());
	}
	
	public void addListenerBtnCrearCuenta(ActionListener listener) {
		crearCuentaBtn.addActionListener(listener);
	}
	
	public void addListenerBtnCancelar(ActionListener listener) {
		cancelarBtn.addActionListener(listener);
	}
}
