package com.smaillywar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.smaillywar.data.DatosSesion;
import com.smaillywar.entity.Tarea;
import com.smaillywar.style.Style;
import com.smaillywar.view.AgregarTareaView;
import com.smaillywar.view.ListarTareasView;
import com.smaillywar.view.MenuLogueadoView;

public class MenuLogueadoController {
	
	private MenuLogueadoView view;
	private JFrame ventana;
	
	public MenuLogueadoController (JFrame ventana) {
		this.ventana = ventana;
		
		setView();
	}
	
	private void setView() {
		view = new MenuLogueadoView();
		
		ventana.setContentPane(view);
		ventana.setTitle("Inicio");
		ventana.revalidate();
		addListeners();
	}
	
	private void limpiarPantalla() {
		view.getPanelPrincipalDerecho().removeAll();
		view.repaint();
		view.revalidate();
		view.setBotonActual(0);
	}
	
	private void addListeners() {
		view.addListenerBotonAgregarTarea(new ListenerBtnAgregarTarea());
		view.addListenerBotonListarTareas(new ListenerBtnListarTareas());
		view.addListenerBotonCerrarSesion(new ListenerBtnCerrarSesion());
	}
	
	private class ListenerBtnAgregarTarea implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AgregarTareaView atv = new AgregarTareaView(view.getPanelPrincipalDerecho());
			view.setBotonActual(1);
			ventana.setTitle("Agregar tarea");
			ventana.repaint();
			ventana.revalidate();
			
			atv.addListenerBotonAgregar(new ListenerAgregarBtnAceptar(atv));
		}
	}
	
	private class ListenerAgregarBtnAceptar implements ActionListener {
		private AgregarTareaView atv;
		private ListenerAgregarBtnAceptar(AgregarTareaView atv) {
			this.atv = atv;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String fl = atv.getFechaLimite();
			
			String nombre = atv.getNombre();
			String descripcion = atv.getDescripcion();
			
			String horaLimite = atv.getHoraLimite();
			
			if (!nombre.matches(Style.regexDatoTarea())) {
				JOptionPane.showMessageDialog(ventana, "Nombre no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!descripcion.matches(Style.regexDatoTarea())) {
				JOptionPane.showMessageDialog(ventana, "Descripción no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (!horaLimite.matches(Style.regexDatoHora())) {
				JOptionPane.showMessageDialog(ventana, "Hora límite no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Tarea tarea = new Tarea();
			
			tarea.setNombre(nombre);
			tarea.setDescripcion(descripcion);
			
			String tempFechaLimite = LocalDateTime.now().format(Style.getDateTimeFormatter());
			tarea.setFechaCreacion(LocalDateTime.parse(tempFechaLimite, Style.getDateTimeFormatter()));
			tarea.setRealizada(false);
			
			if (fl != null) {
				LocalDateTime fechaLimite = LocalDateTime.parse(fl + " " + horaLimite, Style.getDateTimeFormatter());
				if (fechaLimite.isBefore(LocalDateTime.now())) {
					{
						JOptionPane.showMessageDialog(ventana, "Fecha no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				tarea.setFechaLimite(fechaLimite);
			}
			
			DatosSesion.getCuentaLogueada().addTarea(tarea);
			
			limpiarPantalla();
			JOptionPane.showConfirmDialog(ventana, "Tarea agregada con éxito.", "OK", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class ListenerBtnListarTareas implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			cargarPantallaListaTareas();
		}
	}
	
	private void cargarPantallaListaTareas() {
		ListarTareasView ltv = new ListarTareasView(view.getPanelPrincipalDerecho());
		view.setBotonActual(2);
		ventana.setTitle("Lista de tareas");
		
		ltv.vaciarTabla();
		
		List<Tarea> tareas = DatosSesion.getCuentaLogueada().getTareas();
		for (Tarea tarea : tareas) cargarTareaEnTabla(ltv, tarea);
		
		ventana.repaint();
		ventana.revalidate();
		
		ltv.addListenerBtnCambiarTareasVisibles(new ListenerBtnCambiarTareasVisibles(ltv));
		ltv.addListenerBtnGuardarCambios(new ListenerBtnGuardarCambios(ltv));
		ltv.addListenerBotonEliminarTarea(new ListenerBtnEliminarTarea(ltv));
	}
	
	private void cargarTareaEnTabla(ListarTareasView ltv, Tarea tarea) {
		ltv.cargarFila(
				Integer.toString(tarea.getId()),
				tarea.getNombre(),
				tarea.getDescripcion(),
				tarea.isRealizada() ? Boolean.TRUE : Boolean.FALSE,
				tarea.getFechaCreacion().format(Style.getDateTimeFormatter()),
				tarea.isRealizada() ?
						(tarea.getFechaFinalizacion() == null) ? "-" : tarea.getFechaFinalizacion().format(Style.getDateTimeFormatter()) : 
						(tarea.getFechaLimite() == null) ? "-" : tarea.getFechaLimite().format(Style.getDateTimeFormatter()));
	}
	
	private List<Tarea> filtrarTareas(String tipoFiltro, List<Tarea> lista){
		List<Tarea> listaAux = new ArrayList<>();
		boolean pendientes = tipoFiltro.equals("PENDIENTES") ? true : false;
		
		for (Tarea t : lista) {
			if (pendientes) {
				if (!t.isRealizada())
					listaAux.add(t);
			} else {
				if (t.isRealizada()) {
					listaAux.add(t);
				}
			}
		}
		
		return listaAux;
	}
	
	private class ListenerBtnCambiarTareasVisibles implements ActionListener {
		private ListarTareasView ltv;
		private List<Tarea> tareas;
		private ListenerBtnCambiarTareasVisibles (ListarTareasView ltv) {
			this.ltv = ltv;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ltv.vaciarTabla();
			String opcion = ltv.getCambiarTareasVisiblesBtnText();
			
			switch(opcion) {
				case "Ver tareas pendientes":
					ltv.setCambiarTareasVisiblesBtnText("Ver tareas realizadas");
					tareas = filtrarTareas("PENDIENTES", DatosSesion.getCuentaLogueada().getTareas());
					ltv.setTituloLabel("LISTA DE TAREAS PENDIENTES");
					break;
				case "Ver tareas realizadas":
					ltv.setCambiarTareasVisiblesBtnText("Ver todas las tareas");
					tareas = filtrarTareas("COMPLETADAS", DatosSesion.getCuentaLogueada().getTareas());
					ltv.setTituloLabel("LISTA DE TAREAS REALIZADAS");
					break;
				case "Ver todas las tareas":
					ltv.setCambiarTareasVisiblesBtnText("Ver tareas pendientes");
					tareas = DatosSesion.getCuentaLogueada().getTareas();
					ltv.setTituloLabel("LISTA DE TAREAS");
					break;
			}
			
			for (Tarea tarea : tareas) cargarTareaEnTabla(ltv, tarea);
		}
	}
	
	private class ListenerBtnGuardarCambios implements ActionListener {
		private ListarTareasView ltv;
		private ListenerBtnGuardarCambios (ListarTareasView ltv) {
			this.ltv = ltv;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Set<String> filasModificadas = ltv.getListaFilasModificadas();
			Set<String> filasConCambioEstado = ltv.getListaTareasConCambioDeEstado();
			
			filasModificadas.removeAll(filasConCambioEstado);
			
			for (String numFilaString : filasModificadas) {
				int numFila = Integer.valueOf(numFilaString);
				
				String nombre = ltv.getNombreFila(numFila);
				String descripcion = ltv.getDescripcionFila(numFila);
				Boolean realizada = ltv.getRealizadoFila(numFila);
				String fecha = ltv.getFechaLimiteFila(numFila);
				
				if (!nombre.matches(Style.regexDatoTarea())){
					JOptionPane.showMessageDialog(ventana, "Nombre de la tarea " + ltv.getIdFila(numFila) + " no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!descripcion.matches(Style.regexDatoTarea())){
					JOptionPane.showMessageDialog(ventana, "Descripción de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!fecha.matches(Style.regexDatoFecha())){
					if (!fecha.equals("-")) {
						JOptionPane.showMessageDialog(ventana, "Fecha límite de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				LocalDateTime fechaLimite = null;
				
				if (!fecha.equals("-")) {
					try {
						fechaLimite = LocalDateTime.parse(fecha, Style.getDateTimeFormatter());
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(ventana, "Error al convertir la fecha límite.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (realizada) {
						if (fechaLimite.isAfter(LocalDateTime.now())) {
							JOptionPane.showMessageDialog(ventana, "Fecha de finalización de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						if (fechaLimite.isBefore(LocalDateTime.now())) {
							JOptionPane.showMessageDialog(ventana, "Fecha límite de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				
				Tarea tarea = DatosSesion.getTareaCuentaLogueada(ltv.getIdFila(numFila));
				
				tarea.setNombre(nombre);
				tarea.setDescripcion(descripcion);
				tarea.setRealizada(realizada);
				if (realizada) tarea.setFechaFinalizacion(fechaLimite);
				else tarea.setFechaLimite(fechaLimite);
			}
			
			for (String numFilaString : filasConCambioEstado) {
				int numFila = Integer.valueOf(numFilaString);
				
				String nombre = ltv.getNombreFila(numFila);
				String descripcion = ltv.getDescripcionFila(numFila);
				Boolean realizada = ltv.getRealizadoFila(numFila);
				String fecha = ltv.getFechaLimiteFila(numFila);
				
				if (!nombre.matches(Style.regexDatoTarea())){
					JOptionPane.showMessageDialog(ventana, "Nombre de la tarea " + ltv.getIdFila(numFila) + " no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!descripcion.matches(Style.regexDatoTarea())){
					JOptionPane.showMessageDialog(ventana, "Descripción de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!fecha.matches(Style.regexDatoFecha())){
					if (!fecha.equals("-")) {
						JOptionPane.showMessageDialog(ventana, "Fecha límite de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				LocalDateTime fechaLimite = null;
				
				Tarea tarea = DatosSesion.getTareaCuentaLogueada(ltv.getIdFila(numFila));
				
				if (!fecha.equals("-")) {
					try {
						fechaLimite = LocalDateTime.parse(fecha, Style.getDateTimeFormatter());
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(ventana, "Error al convertir la fecha límite.", "ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					//si no estaba realizada y ahora si
					if (realizada) {
						if (fechaLimite.isBefore(LocalDateTime.now())) {
							JOptionPane.showMessageDialog(ventana, "Fecha límite de la tarea " + ltv.getIdFila(numFila) + " no válida.", "ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}
						tarea.setFechaLimite(fechaLimite);
						tarea.setFechaFinalizacion(LocalDateTime.now());
					}
					
					//si estaba como realizada y ahora no
					if (!realizada) {
						if (fechaLimite.isAfter(LocalDateTime.now())) {
							tarea.setFechaLimite(fechaLimite);
							tarea.setFechaFinalizacion(null);
						} else {
							tarea.setFechaLimite(null);
							tarea.setFechaFinalizacion(null);
						}
					}
				} else {
					//si no estaba realizada y ahora si
					if (realizada) {
						tarea.setFechaLimite(null);
						tarea.setFechaFinalizacion(LocalDateTime.now());
					} else {
						//si estaba como realizada y ahora no
						tarea.setFechaLimite(null);
						tarea.setFechaFinalizacion(null);
					}
				}
				
				tarea.setNombre(nombre);
				tarea.setDescripcion(descripcion);
				tarea.setRealizada(realizada);
			}
			
			limpiarPantalla();
			cargarPantallaListaTareas();
			
			JOptionPane.showConfirmDialog(ventana, "Cambios guardados con éxito.", "OK", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class ListenerBtnEliminarTarea implements ActionListener {
		private ListarTareasView ltv;
		private ListenerBtnEliminarTarea(ListarTareasView ltv) {
			this.ltv = ltv;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int fila = ltv.getFilaSeleccionada();
			
			if (fila == -1) {
				JOptionPane.showMessageDialog(ventana, "Selecciona la tarea a eliminar.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int id = ltv.removerFila(fila);
			
			JOptionPane.showConfirmDialog(ventana, "Tarea " + id + " borrada con éxito.", "OK", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			Tarea tarea = DatosSesion.getTareaCuentaLogueada(id);
			DatosSesion.getCuentaLogueada().getTareas().remove(tarea);
			
			ltv.actualizarListasTareasModificadas(fila);
		}
	}
	
	private class ListenerBtnCerrarSesion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DatosSesion.setCuentaLogueada(null);
			new MenuController(ventana);
		}
	}
	
	//TODO ACTUALIZAR SOPORTE PARA PANTALLAS DE OTRA RESOLUCION
	//TODO ACTUALIZAR README
}
