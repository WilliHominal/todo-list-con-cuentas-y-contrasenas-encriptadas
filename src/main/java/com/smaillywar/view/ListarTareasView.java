package com.smaillywar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.style.CustomJButton;
import com.smaillywar.style.Style;

public class ListarTareasView {

	private Style style;
	
	private JLabel panelPrincipal;
	
	private JLabel mensajeNombreUsuarioLabel;
	private JLabel tituloLabel;

	private CustomJButton cambiarTareasVisibles;
	
	private JTable tareasTable;
	private JScrollPane tareasTableScrollPane;
	private DefaultTableModel modelTareasTable;
	
	private JLabel infoBajoTablaLabel;
	private CustomJButton guardarCambiosBtn;
	private CustomJButton eliminarTareaBtn;
	
	Set<String> tareasModificadas;
	Set<String> tareasConCambioDeEstado;
	
	public ListarTareasView(JLabel panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
		panelPrincipal.removeAll();
		style = new Style();
		
		inicializarComponentes();
		ubicarComponentes();
	}
	
	private void inicializarComponentes() {
		mensajeNombreUsuarioLabel = new JLabel("BIENVENIDO " + DatosSesion.getCuentaLogueada().getUsuario().toUpperCase());
		tituloLabel = new JLabel("LISTA DE TAREAS");
		
		cambiarTareasVisibles = new CustomJButton("Ver tareas pendientes");
		
		tareasTable = new JTable();
		tareasTableScrollPane = new JScrollPane(tareasTable);
		cargarTabla();
		
		infoBajoTablaLabel = new JLabel("* Doble click en una celda para modificar su valor (nombre/descripción/estado/fecha límite)");
		guardarCambiosBtn = new CustomJButton("GUARDAR CAMBIOS");
		eliminarTareaBtn = new CustomJButton("ELIMINAR TAREA");
		
		tareasModificadas = new HashSet<String>();
		tareasConCambioDeEstado = new HashSet<String>();
		
		style.setEstiloHeaderLabel(mensajeNombreUsuarioLabel);
		style.setEstiloTituloLabel(tituloLabel);
		style.setEstiloCustomBtnChico(cambiarTareasVisibles);
		style.setEstiloLabel(infoBajoTablaLabel);
		infoBajoTablaLabel.setForeground(Color.BLACK);
		style.setEstiloCustomBtnChico(guardarCambiosBtn);
		style.setEstiloCustomBtnChico(eliminarTareaBtn);
		cambiarTareasVisibles.setPreferredSize(new Dimension(cambiarTareasVisibles.getPreferredSize().width+30,cambiarTareasVisibles.getPreferredSize().height));
		guardarCambiosBtn.setPreferredSize(new Dimension(guardarCambiosBtn.getPreferredSize().width+30,guardarCambiosBtn.getPreferredSize().height));
		eliminarTareaBtn.setPreferredSize(new Dimension(eliminarTareaBtn.getPreferredSize().width+30,eliminarTareaBtn.getPreferredSize().height));
	}
	
	private void cargarTabla() {
		String[] columnas = {"ID", "NOMBRE", "DESCRIPCION", "REALIZADA", "FECHA INICIO", "FECHA FIN"};
		DefaultTableModel tableModel = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 7365551733085502818L;

			@Override
			public Class<?> getColumnClass(int col) {
				if (col == 3) return Boolean.class;
				return String.class;
			}

			@Override
			public boolean isCellEditable(int fila, int col) {
				if (col == 0 || col == 4) return false;
				return true;
			}
		};
		
		modelTareasTable = tableModel;		
		tareasTable.setModel(modelTareasTable);
		
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment( JLabel.CENTER );
		
		tareasTable.getColumnModel().getColumn(0).setCellRenderer(centrado);
		tareasTable.getColumnModel().getColumn(1).setCellRenderer(centrado);
		tareasTable.getColumnModel().getColumn(2).setCellRenderer(centrado);
		tareasTable.getColumnModel().getColumn(4).setCellRenderer(centrado);
		tareasTable.getColumnModel().getColumn(5).setCellRenderer(centrado);
		
		tareasTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		tareasTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		tareasTable.getColumnModel().getColumn(2).setPreferredWidth(350);
		tareasTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		tareasTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		tareasTable.getColumnModel().getColumn(5).setPreferredWidth(150);
		
		style.setEstiloTabla(tareasTable, tareasTableScrollPane);
		
		tareasTableScrollPane.setPreferredSize(new Dimension(1000, 500));
		
		tareasTable.getModel().addTableModelListener(
			new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					if (e.getColumn() >= 0) tareasModificadas.add(Integer.toString(e.getLastRow()));
					if (e.getColumn() == 3) tareasConCambioDeEstado.add(Integer.toString(e.getLastRow()));
				}
			}
		);
	}

	public Set<String> getListaFilasModificadas(){
		return tareasModificadas;
	}
	
	public Set<String> getListaTareasConCambioDeEstado(){
		return tareasConCambioDeEstado;
	}
	
	public void actualizarListasTareasModificadas(int filaBorrada) {
		actualizarLista(tareasModificadas, filaBorrada);
		actualizarLista(tareasConCambioDeEstado, filaBorrada);
	}
	
	public void actualizarLista(Set<String> lista, int filaBorrada) {
		for (String filaModificadaString : lista) {
			int filaModificada = Integer.parseInt(filaModificadaString);
			int filaAnterior = filaModificada-1;
			String filaAnteriorString = Integer.toString(filaAnterior);
			if (filaModificada == filaBorrada) {
				lista.remove(filaModificadaString);
			} else if (filaModificada > filaBorrada) {
				lista.remove(filaModificadaString);
				lista.add(filaAnteriorString);
			}
		}
	}
	
	public int getFilaSeleccionada() {
		return tareasTable.getSelectedRow();
	}
	
	public int removerFila(int numFila) {
		int id = getIdFila(numFila);
		((DefaultTableModel)tareasTable.getModel()).removeRow(numFila);
		return id;
	}
	
	public int getIdFila(int numFila) {
		return Integer.valueOf((String)tareasTable.getValueAt(numFila, 0));
	}
	
	public String getNombreFila(int numFila) {
		return (String)tareasTable.getValueAt(numFila, 1);
	}
	
	public String getDescripcionFila(int numFila) {
		return (String)tareasTable.getValueAt(numFila, 2);
	}
	
	public Boolean getRealizadoFila(int numFila) {
		return (Boolean)tareasTable.getValueAt(numFila, 3);
	}
	
	public String getFechaLimiteFila(int numFila) {
		return (String)tareasTable.getValueAt(numFila, 5);
	}
	
	public void cargarFila(String id, String nombre, String descripcion, Boolean estado, String fechaInicio, String fechaFin) {
		modelTareasTable.addRow(new Object[] {id, nombre, descripcion, estado, fechaInicio, fechaFin});
	}
	
	public void vaciarTabla() {
		int tam = modelTareasTable.getRowCount();
		
		for (int i=0; i<tam; i++)
			modelTareasTable.removeRow(0);
	}
	
	private void ubicarComponentes() {
		GridBagConstraints cons = new GridBagConstraints();
		int separacionCampos = 20;
		
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets.set(0, 0, separacionCampos*3, 0);
		cons.gridwidth = 2;
		panelPrincipal.add(mensajeNombreUsuarioLabel, cons);
		
		cons.gridy = 1;
		cons.insets.set(0, 0, separacionCampos*3/2, 0);
		panelPrincipal.add(tituloLabel, cons);
		
		cons.gridy = 2;
		cons.insets.set(0, 0, separacionCampos, 0);
		panelPrincipal.add(cambiarTareasVisibles, cons);
		
		cons.gridy = 3;
		panelPrincipal.add(tareasTableScrollPane, cons);
		
		cons.gridy = 4;
		cons.anchor = GridBagConstraints.WEST;
		panelPrincipal.add(eliminarTareaBtn, cons);
		
		cons.gridwidth = 1;
		cons.gridx = 1;
		cons.anchor = GridBagConstraints.EAST;
		panelPrincipal.add(guardarCambiosBtn, cons);
		
		cons.gridy = 5;
		cons.gridx = 0;
		cons.insets.set(0, 0, 0, 0);
		cons.anchor = GridBagConstraints.WEST;
		cons.gridwidth = 2;
		panelPrincipal.add(infoBajoTablaLabel, cons);
		
	}
	
	public String getCambiarTareasVisiblesBtnText() {
		return cambiarTareasVisibles.getText();
	}
	
	public void setCambiarTareasVisiblesBtnText(String texto) {
		cambiarTareasVisibles.setText(texto);
	}
	
	public void addListenerBtnCambiarTareasVisibles(ActionListener listener) {
		cambiarTareasVisibles.addActionListener(listener);
	}
	
	public void setTituloLabel(String titulo) {
		tituloLabel.setText(titulo);
	}

	public JTable getTabla() {
		return tareasTable;
	}

	public void addListenerBtnGuardarCambios(ActionListener listener) {
		guardarCambiosBtn.addActionListener(listener);
	}

	public void addListenerBotonEliminarTarea(ActionListener listener) {
		eliminarTareaBtn.addActionListener(listener);
	}
}
