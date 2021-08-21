package com.smaillywar.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.smaillywar.data.DatosSesion;

public class Style {
	
	private Color COLOR_FONDO_BOTONES = new Color(121, 44, 130);
	private static Font FONT_SELECTOR_FECHA;
	private static Font FONT_DEFAULT;
	private static Font FONT_HEADER;
	private static Font FONT_TITULO;
	private static Font FONT_TABLA_HEADER;
	private static Font FONT_TABLA_FILAS;
	
	public static void setFontsSizes() {
		int scaler = DatosSesion.getResolucionPantalla().getHeight()<1080 ? 2 : 0;
		FONT_SELECTOR_FECHA = new Font("Serif", Font.PLAIN, 15 - scaler);
		FONT_DEFAULT = new Font("Serif", Font.BOLD, 16 - scaler);
		FONT_HEADER = new Font("Serif", Font.BOLD, 24 - scaler);
		FONT_TITULO = new Font("Serif", Font.BOLD, 20 - scaler);
		FONT_TABLA_HEADER = new Font("Serif", Font.PLAIN, 17 - 2*scaler);
		FONT_TABLA_FILAS = new Font("Serif", Font.PLAIN, 15 - scaler);
	}
	
	public static Font getFontSelectorFecha() {
		return FONT_SELECTOR_FECHA;
	}
	
	public JLabel setFondo(String nombreFondo, int ancho, int alto) {
		JLabel panelPrincipal = null;
		try {
			BufferedImage fondo = ImageIO.read(getClass().getResource("/" + nombreFondo + ".jpg"));
			BufferedImage bf = scale(fondo, ancho, alto);
			ImageIcon io = new ImageIcon(bf);
			panelPrincipal = new JLabel(io);
			panelPrincipal.setLayout(new GridBagLayout());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return panelPrincipal;
	}
	
	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
	
	public static String regexDatoCuenta() {
		return "^[\\p{L}\\d\\<\\>\\(\\)\\[\\]\\{\\}\\^\\-\\=\\$\\!\\?\\*\\+\\.\\\\.]{6,}$";
	}
	
	public static String regexDatoTarea() {
		return "^[\\p{L}\\d\\<\\>\\(\\)\\[\\]\\{\\}\\^\\-\\=\\$\\!\\?\\*\\+\\.\\\\\\s,]+$";
	}
	
	public static String regexDatoHora() {
		return "^(([01][0-9])|([2][0-3]))[:][0-5][0-9]$";
	}
	public static String regexDatoFecha() {
		return "^(([0-2][0-9])|([3][01]))[\\-](([0][0-9])|([1][0-2]))[\\-][0-9]{4}[\\s](([01][0-9])|([2][0-3]))[:][0-5][0-9]$";
	}
	
	public static DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	}
	
	public void setEstiloBtn(JButton btn) {
		btn.setPreferredSize(new Dimension(200, 50));
		btn.setBackground(COLOR_FONDO_BOTONES);
		btn.setForeground(Color.WHITE);
		btn.setFont(FONT_DEFAULT);
	}
	
	public void setEstiloCustomBtnChicoAlargado(CustomJButton btn) {
		this.setEstiloCustomBtn(btn);
		btn.setPreferredSize(new Dimension(200, 40));
	}
	
	public void setEstiloCustomBtnChico(CustomJButton btn) {
		this.setEstiloCustomBtn(btn);
		btn.setPreferredSize(new Dimension(170, 40));
	}
	
	public void setEstiloCustomBtn(CustomJButton btn) {
		this.setEstiloBtn(btn);
		btn.setHoverBackgroundColor(btn.getBackground().brighter());
		btn.setPressedBackgroundColor(btn.getBackground().darker());
	}
	
	public void setEstiloLabel(JLabel label) {
		label.setForeground(COLOR_FONDO_BOTONES);
		label.setFont(FONT_DEFAULT);
	}
	
	public void setEstiloTituloLabel(JLabel label) {
		label.setForeground(COLOR_FONDO_BOTONES);
		label.setFont(FONT_TITULO);
	}
	
	public void setEstiloHeaderLabel(JLabel label) {
		label.setForeground(Color.WHITE);
		label.setFont(FONT_HEADER);
	}
	
	public void setEstiloTextField(JTextField tf) {
		tf.setBackground(COLOR_FONDO_BOTONES.brighter());
		tf.setForeground(Color.WHITE);
		tf.setFont(FONT_DEFAULT);
		tf.setBorder(BorderFactory.createCompoundBorder(tf.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
	}
	
	public void setEstiloTabla(JTable tabla, JScrollPane scrollPane) {
		Color letraTabla = Color.BLACK;
		Color fondoTabla = new Color(215, 229, 252);
		Font fontHeader = FONT_TABLA_HEADER;
		Font fontFilas = FONT_TABLA_FILAS;
		
		scrollPane.setBackground(fondoTabla);
		scrollPane.setEnabled(true);
		scrollPane.getVerticalScrollBar().setBackground(fondoTabla);
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
	            @Override
	            protected void configureScrollBarColors() {
	                this.thumbColor = fondoTabla.darker();
	            }
	    });
    	scrollPane.getViewport().setBackground(fondoTabla);	
    	scrollPane.setForeground(letraTabla);
    	
    	JTableHeader th = tabla.getTableHeader();
		th.setFont(fontHeader);
		th.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		tabla.setFont(fontFilas);
		
		RowHeightCellRenderer dynRow = new RowHeightCellRenderer();
		tabla.getColumnModel().getColumn(1).setCellRenderer(dynRow);
		tabla.getColumnModel().getColumn(2).setCellRenderer(dynRow);
		
    	tabla.setFillsViewportHeight(true);
    	tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tabla.setForeground(letraTabla);
    	tabla.setBackground(fondoTabla);
    	th.setBackground(fondoTabla);
    	th.setForeground(letraTabla);
    	th.setResizingAllowed(false);
    	th.setReorderingAllowed(false);
	}
	
	public void setAlturaFilaTabla(JTable tabla, int fila) {
		int largoNombre = ((String)tabla.getModel().getValueAt(fila, 1)).length();
		int largoDesc = ((String)tabla.getModel().getValueAt(fila, 2)).length();
		final int MAX_LINEA_DESCRIPCION = DatosSesion.getResolucionPantalla().width < 1920 ? 42 : 50;
		final int MAX_LINEA_NOMBRE = DatosSesion.getResolucionPantalla().width < 1920 ? 21 : 26;
		int cantLineasNombre = 1 + largoNombre / MAX_LINEA_NOMBRE;
		int cantLineasDesc = 1 + largoDesc / MAX_LINEA_DESCRIPCION;
		int mayor = cantLineasNombre > cantLineasDesc ? cantLineasNombre : cantLineasDesc;
		tabla.setRowHeight(fila, 16*mayor);
	}
	
	public class RowHeightCellRenderer extends JTextArea implements TableCellRenderer {
	    private static final long serialVersionUID = 1L;

	    public RowHeightCellRenderer() {
	        setLineWrap(true);
	        setWrapStyleWord(true);
	    }
	    public Component getTableCellRendererComponent (JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column ) {
	        this.setText((String) value);

	        if(isSelected) {
	            this.setBackground(table.getSelectionBackground());
	            this.setForeground(table.getSelectionForeground());
	        } else {
	            this.setBackground(table.getBackground());
	            this.setForeground(table.getForeground());
	        }

	        final int columnWidth = table.getColumnModel().getColumn(column).getWidth();
	        final int rowHeight = table.getRowHeight(row);
	        this.setSize(columnWidth, rowHeight);
	        
	        table.getColumnModel().setColumnMargin(5);
	        setAlturaFilaTabla(table, row);
	        
	        this.validate();
	        return this;
	    }
	}
}
