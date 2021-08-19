package com.smaillywar.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.style.CustomJButton;
import com.smaillywar.style.Style;

public class AgregarTareaView {

	private Style style;
	
	private JLabel panelPrincipal;
	
	private JLabel mensajeNombreUsuario;
	private JLabel tituloLabel;
	private JLabel nombreLabel;
	private JLabel descripcionLabel;
	private JLabel fechaLimiteLabel;
	private JLabel horaLimiteLabel;
	private JTextField nombreTF;
	private JTextField descripcionTF;
	private JTextField horaLimiteTF;
	
	private UtilDateModel modeloFecha;
	private JDatePanelImpl panelFecha;
	private JDatePickerImpl selectorFecha;
	
	private CustomJButton agregarBtn;
	
	public AgregarTareaView(JLabel panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
		panelPrincipal.removeAll();
		style = new Style();
		
		inicializarComponentes();
		ubicarComponentes();
	}
	
	private void inicializarComponentes() {
		mensajeNombreUsuario = new JLabel("BIENVENIDO " + DatosSesion.getCuentaLogueada().getUsuario().toUpperCase());
		tituloLabel = new JLabel("AGREGAR TAREA");
		nombreLabel = new JLabel("NOMBRE:");
		descripcionLabel = new JLabel("DESCRIPCIÓN:");
		fechaLimiteLabel = new JLabel("FECHA LÍMITE:");
		horaLimiteLabel = new JLabel("HORA LÍMITE (HH:mm):");
		
		nombreTF = new JTextField(20);
		descripcionTF = new JTextField(20);
		horaLimiteTF = new JTextField(20);
		horaLimiteTF.setText("00:00");
		
		modeloFecha = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		panelFecha = new JDatePanelImpl(modeloFecha, p);
		selectorFecha = new JDatePickerImpl(panelFecha, new DateLabelFormatter());
		
		selectorFecha.setPreferredSize(new Dimension(255, 25));
		for( Component c : selectorFecha.getComponents()){
			c.setBackground(Color.WHITE);
			c.setForeground(Color.BLACK);
			c.setFont(new Font("Serif", Font.PLAIN, 15));
		}
		
		agregarBtn = new CustomJButton("AGREGAR");
		
		style.setEstiloHeaderLabel(mensajeNombreUsuario);
		style.setEstiloTituloLabel(tituloLabel);
		style.setEstiloLabel(nombreLabel);
		style.setEstiloLabel(descripcionLabel);
		style.setEstiloLabel(fechaLimiteLabel);
		style.setEstiloLabel(horaLimiteLabel);
		
		style.setEstiloTextField(nombreTF);
		style.setEstiloTextField(descripcionTF);
		style.setEstiloTextField(horaLimiteTF);
		
		style.setEstiloCustomBtnChico(agregarBtn);
	}
	
	private void ubicarComponentes() {
		GridBagConstraints cons = new GridBagConstraints();
		int separacionLabelTF = 15;
		int separacionCampos = 20;
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets.set(0, 0, separacionCampos*3, 0);
		cons.gridwidth = 2;
		panelPrincipal.add(mensajeNombreUsuario, cons);
		
		cons.gridy = 1;
		cons.insets.set(0, 0, separacionCampos*3/2, 0);
		panelPrincipal.add(tituloLabel, cons);
		
		cons.gridwidth = 1;
		cons.anchor = GridBagConstraints.EAST;
		cons.gridy = 2;
		cons.insets.set(0, 0, separacionCampos, separacionLabelTF);
		panelPrincipal.add(nombreLabel, cons);
		cons.gridy = 3;
		panelPrincipal.add(descripcionLabel, cons);
		cons.gridy = 4;
		panelPrincipal.add(fechaLimiteLabel, cons);
		cons.gridy = 5;
		cons.insets.set(0, 0, separacionCampos*3/2, separacionLabelTF);
		panelPrincipal.add(horaLimiteLabel, cons);
		
		cons.anchor = GridBagConstraints.WEST;
		cons.gridx = 1;
		cons.gridy = 2;
		cons.insets.set(0, 0, separacionCampos, 0);
		panelPrincipal.add(nombreTF, cons);
		cons.gridy = 3;
		panelPrincipal.add(descripcionTF, cons);
		cons.gridy = 4;
		panelPrincipal.add(selectorFecha, cons);
		cons.gridy = 5;
		cons.insets.set(0, 0, separacionCampos*3/2, 0);
		panelPrincipal.add(horaLimiteTF, cons);
		
		
		cons.anchor = GridBagConstraints.CENTER;
		cons.gridx = 0;
		cons.gridy = 6;
		cons.insets.set(0, 0, 0, 0);
		cons.gridwidth = 2;
		panelPrincipal.add(agregarBtn, cons);
	}
	
	public JButton getBotonAgregar() {
		return agregarBtn;
	}
	
	public void addListenerBotonAgregar(ActionListener listener) {
		agregarBtn.addActionListener(listener);
	}
	
	public String getNombre() {
		return nombreTF.getText();
	}
	
	public String getDescripcion() {
		return descripcionTF.getText();
	}
	
	public String getFechaLimite() {
		if (selectorFecha.getModel().getValue() == null)
			return null;
		Date fecha = (Date) selectorFecha.getModel().getValue();
		return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
	}

	public String getHoraLimite() {
		return horaLimiteTF.getText();
	}
	
	private class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 6829968490420826848L;
		
		private String datePattern = "dd MMMM yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
