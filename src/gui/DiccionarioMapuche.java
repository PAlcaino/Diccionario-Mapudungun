package gui;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class DiccionarioMapuche extends JFrame implements ActionListener, ItemListener
{
	//Declaramos variables de instancia
	private Container contenedor;
	
	private boolean direccionTraduccion;
	
	private JPanel traducir;
	private JLabel palabraPorTraducir;
	private JTextField campoTraducir;
	
	private JLabel palabraYaTraducida;
	private JTextArea campoTraduccion;
	
	private JPanel traduccion;
	private JPanel botones;
	private JButton botonTraducir;
	private JButton limpiar;
	
	private JPanel checkBox;
	private ButtonGroup cambiarIdioma;
	private JRadioButton fuenteEspañol;
	private JRadioButton fuenteMapudungun;
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	
	public DiccionarioMapuche()
	{
		//Constructor de la clase padre
		super("Diccionario Mapudungun - Español V2.0");
		
		contenedor=getContentPane();
		contenedor.setLayout(gridbag);
		
		traducir=new JPanel();
		traducir.setLayout(new FlowLayout());
		
		palabraPorTraducir=new JLabel("Palabra a traducir:");
		traducir.add(palabraPorTraducir);
		campoTraducir=new JTextField(30);
		traducir.add(campoTraducir);
				
				
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(traducir,gbc);
		contenedor.add(traducir);
		
		palabraYaTraducida=new JLabel("Traducciï¿½n:");
		gbc.weightx = 0;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(palabraYaTraducida,gbc);
		contenedor.add(palabraYaTraducida);
		
		traduccion=new JPanel();
		campoTraduccion=new JTextArea(15,50);
		traduccion.add(new JScrollPane(campoTraduccion,
        	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
		gbc.weightx = 0;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints( traduccion,gbc );
		contenedor.add(traduccion);
		
		checkBox=new JPanel();
		checkBox.setLayout(new BorderLayout());
		
		cambiarIdioma=new ButtonGroup();
		fuenteMapudungun=new JRadioButton("Mapudungun - Español");
		cambiarIdioma.add(fuenteMapudungun);
		fuenteEspañol=new JRadioButton("Español - Mapudungun");
		cambiarIdioma.add(fuenteEspañol);
		fuenteMapudungun.setSelected(true);
		checkBox.add(fuenteMapudungun,BorderLayout.NORTH);
		checkBox.add(fuenteEspañol,BorderLayout.CENTER);
		
		gbc.weightx = 0;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints( checkBox,gbc );
		contenedor.add(checkBox);
		
		botones=new JPanel();
		botones.setLayout(new FlowLayout(0,80,0));
		
		botonTraducir=new JButton("Traducir");
		botones.add(botonTraducir);
		limpiar=new JButton("Limpiar");
		botones.add(limpiar);
		
		gbc.weightx = 100;
		gbc.weighty = 10;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.SOUTH;
		gridbag.setConstraints( botones,gbc );
		contenedor.add(botones);
		
		insets();

		botonTraducir.addActionListener(this);
		limpiar.addActionListener(this);
		
		fuenteMapudungun.addItemListener(this);
		fuenteEspañol.addItemListener(this);
		
		direccionTraduccion=true;
		
		try
		{
			BufferedReader e=new BufferedReader(new FileReader("dicc.txt"));
//			Traduccion inicial=new Traduccion("abtao","fin de la tierra o del continente");
//			avl=new AVL(inicial);
//			Traduccion siguiente;
			String palabra;
			
			//Lectura de archivo de 667 lineas
			for(int i=1;i<667;i++)
			{
				palabra=e.readLine();
				if(palabra!=null)
				{	
					StringTokenizer t=new StringTokenizer(palabra,":");
					String palabraMapuche=t.nextToken();
					String palabraEspañol=t.nextToken();
// 					siguiente=new Traduccion(palabraMapuche,palabraEspañsol);
//					avl.insertar(avl,siguiente);
				}
			}
		}
		
		catch(Exception Error)
		{
			System.out.println(" "+Error);
		}
		
		setSize(640,480);
		setVisible(true);
	}
	
	public Insets insets()
	{
    	return new Insets( 40,20,40,40 );
    }
    
    public void itemStateChanged(ItemEvent ie)
    {
    	if(ie.getSource()==fuenteEspañol)
    		direccionTraduccion=false;
    		
    	else
    		direccionTraduccion=true;
    }

	public void actionPerformed(ActionEvent ae)
	{
		String terminoInicial=null;
		String terminoFinal=null;
		
		if(ae.getSource()==botonTraducir && direccionTraduccion)
		{
			terminoInicial=campoTraducir.getText();
			
			if(terminoInicial!=null)
			{
				//String traduc=avl.buscarMapuche(avl,terminoInicial);
				campoTraduccion.setText("");
				//campoTraduccion.setText(traduc);
			}
		}
		else
			if(ae.getSource()==botonTraducir && !direccionTraduccion)
			{
				terminoFinal=campoTraducir.getText();

				if(terminoFinal!=null)
				{
//					String encontradosMapuche[]=avl.buscarCastellano(avl,terminoFinal);
//					String encontradosEspaï¿½ol[]=avl.getArregloEspaï¿½ol();
					
//					if(//encontradosMapuche[0]==null)
//						campoTraduccion.setText("Palabra no encontrada");
//						
//					else
//					{
//						campoTraduccion.setText("");
//						for(int i=0;encontradosMapuche[i]!=null;i++)
//							campoTraduccion.append(encontradosMapuche[i]+'\t'+"="+'\t'+encontradosEspaï¿½ol[i]+'\n');	
//					}
				}
			}
			
		if(ae.getSource()==limpiar)
		{
			campoTraducir.setText("");
			campoTraduccion.setText("");
		}
			
	}
	
	public static void main( String args[] )
   	{
      		DiccionarioMapuche aplicacion = new DiccionarioMapuche();
      		aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   	}
}