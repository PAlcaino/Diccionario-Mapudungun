/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import archivos.Diccionario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import conexiones.ConexionBD;

public class InterfazGrafica extends JFrame implements ActionListener
{
	//declaramos los paneles
    private JPanel panelPrincipal;
    private JPanel panelNorte;
    private JPanel panelCentro;
    private JPanel panelSur;
    private JPanel panelIdioma;
    
    //definimos seccion de diccionario
    private JTextField texto;
    private JTextArea traduccion;
    private JLabel etiquetaTexto;
    private JLabel etiquetaTraduccion;
    private JButton botonLimpiar;
    private JButton botonTraducir;
    private JRadioButton traduccionEspañol;
    private JRadioButton traduccionMapudungun;
    private ButtonGroup direccionTraduccion;
    
    //definimos valores del menu
    private JMenuBar barraMenu;
    private JMenu menuOpciones;
    private JMenuItem menuArchivo;
    private JMenuItem menuLimpiar;
    private JMenuItem menuSalir;
   
    //declaramos seccion de la carga de archivo, que proviene de un txt con las traducciones
    private JFileChooser selectorDeArchivo;
    private JButton botonCargarArchivo;
    private String rutaArchivo;
    private Diccionario diccionario;
    
    
    private ConexionBD coneccion;
    
    public InterfazGrafica()
    {
        coneccion = new ConexionBD ();
        
        //inicializamos los paneles
        panelPrincipal = new JPanel();
        panelNorte = new JPanel();
        panelSur = new JPanel();
        panelIdioma = new JPanel();
        panelCentro = new JPanel();
        
        //inicializamos etiquetas y textarea
        texto = new JTextField();
        traduccion = new JTextArea(20, 40);
        etiquetaTexto = new JLabel("Palabra:");
        etiquetaTraduccion = new JLabel("Traducción:");
        new JLabel("Diccionario Mapudungun - Español");
     
        //inicializamos menu 
        barraMenu = new JMenuBar();             
        menuOpciones = new JMenu("Opciones");             
        menuArchivo = new JMenuItem("Cargar Archivo");             
        menuLimpiar = new JMenuItem("Limpiar");               
        menuSalir = new JMenuItem("Salir");
        
        //asignamos los actionListener de los menus
        menuSalir.addActionListener(this);
        menuArchivo.addActionListener(this);
        menuLimpiar.addActionListener(this);
        
        //agregamos los menus donde corresponde
        setJMenuBar(barraMenu); 
        barraMenu.add(menuOpciones);  
        menuOpciones.add(menuArchivo);
        menuOpciones.add(menuLimpiar);
        menuOpciones.add(menuSalir);
        
        
        //inicializamos los botones y los dejamos desactivados menos el de archivo, 
        //para obligar a cargar el diccionario antes de usar otra funcion
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setEnabled(false);
        botonTraducir = new JButton("Traducir");
        botonTraducir.setEnabled(false);
        botonCargarArchivo = new JButton("Archivo");
        selectorDeArchivo = new JFileChooser();
        
        //agregamos funcion de action listener
        botonLimpiar.addActionListener(this);
        botonTraducir.addActionListener(this);
        botonCargarArchivo.addActionListener(this);
        
        //inicializamos y configuramos los radio button
        traduccionEspañol = new JRadioButton("Español - Mapundungun");
        traduccionMapudungun = new JRadioButton("Mapundungun - Español");
        direccionTraduccion = new ButtonGroup();
        direccionTraduccion.add(traduccionEspañol);
        direccionTraduccion.add(traduccionMapudungun);
        traduccionEspañol.setSelected(true);
        
        
        //configuramos los distintos paneles Norte Centro y Sur
        panelIdioma.setLayout(new BorderLayout(5, 5));
        panelIdioma.add(traduccionEspañol, BorderLayout.NORTH);
        panelIdioma.add(traduccionMapudungun, BorderLayout.SOUTH);
        
        panelNorte.setLayout(new GridLayout(2,1,10,10));
        panelNorte.add(etiquetaTexto);
        panelNorte.add(texto);
        
        
        panelCentro.setLayout(new GridLayout(2, 1));
        panelCentro.add(etiquetaTraduccion);
        panelCentro.add(new JScrollPane(traduccion,	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        
        panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSur.add(panelIdioma);
        panelSur.add(botonCargarArchivo);
        panelSur.add(botonLimpiar);
        panelSur.add(botonTraducir);
        
        
        // agregamos los paneles Norte, Centro y Sur al panel principal
        panelPrincipal.setLayout(new BorderLayout(10, 10));        
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        //configuramos el frame principal
        this.add(panelPrincipal);
        this.setLocation(500, 300);
        this.getContentPane();
        this.setSize(480,380);
        this.setTitle("Diccionario");
        //this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new InterfazGrafica().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String corregirRutaArchivo(String rutaArchivo)
    {
    	rutaArchivo = rutaArchivo.replaceAll("\\\\","\\\\\\\\");
    	
    	return rutaArchivo;
    }
    public void cargarArchivo() {
    	selectorDeArchivo.showOpenDialog(this);
    	rutaArchivo = selectorDeArchivo.getSelectedFile().getAbsolutePath();
    	rutaArchivo = corregirRutaArchivo(rutaArchivo);
    	diccionario = new Diccionario(rutaArchivo);
    	if(rutaArchivo != "") {
    		botonTraducir.setEnabled(true);
    		botonLimpiar.setEnabled(true);
    	}
    }

    @Override
public void actionPerformed(ActionEvent e) 
    {
        
        if(e.getSource() == botonCargarArchivo || e.getSource() == menuArchivo)
        {
        	cargarArchivo();
        	
        }
        if(e.getSource() == botonLimpiar || e.getSource() == menuLimpiar)
        {
            texto.setText("");
            traduccion.setText("");
        }
        if(e.getSource() == botonTraducir)
        {
        	traduccion.setText("");
            if(traduccionEspañol.isSelected() && !texto.getText().trim().equals(""))
            {   
                String palabra = texto.getText();
                String[] traducciones = diccionario.traducirAMapudungun(palabra);
                for(int i = 0; i<traducciones.length;i++)
                	traduccion.append(traducciones[i]+"\n");
                
            }
            else if (traduccionMapudungun.isSelected() && !texto.getText().trim().equals(""))
            {
                String palabra = texto.getText();
                String unaTraduccion = diccionario.traducirAEspañol(palabra);
                traduccion.append(unaTraduccion);
            }
            else
            	traduccion.append("Palabra no encontrada");
        }
        if(e.getSource() == menuSalir)
        	System.exit(0);
    }
    
   
}
    

