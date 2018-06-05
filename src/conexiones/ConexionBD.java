package conexiones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import archivos.Archivo;
import archivos.Diccionario;

public class ConexionBD 
{
	private final String stringconexion = "jdbc:oracle:thin:@localhost:1521:xe";
	// metodo para acceder a la base de datos
	public Connection obtenerConeccion()
	{
		Connection conexion = null;
		try {
			//enrutamos el driver de oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//intenta la conexion con un user y una pass que creamos en la BD
			conexion = DriverManager.getConnection(stringconexion, "alumno", "123456");
			
		}
		//manejamos las excepciones
		catch (ClassNotFoundException e) 
		{			
			System.out.println("No se encuentra el driver de la base de datos ");
		}
		catch (SQLException e) 
		{			
			System.out.println("Error de conexion ");
		}
		return conexion;
	}
	public static void main(String[] args) 
	{
//		if(new ConexionBD().obtenerConeccion() != null)
//			System.out.println("Conexion exitosa");
//		else
//			System.out.println("Conexion fallida");
//		new ConexionBD().cargarTabla("C:/Users/SantoTomas/Desktop/Interfaz Grafica\\dicc.txt");
//		System.out.println(new ConexionBD().obtenerUltimoId());
		ConexionBD cbd = new ConexionBD();
		//cbd.cargarTabla("C:/Users/SantoTomas/Desktop/Diccionario GUI/dicc.txt");
		System.out.println(cbd.obtenerUltimoId());
//		cbd.insertarTraduccion("cabeza","lonco");
//		System.out.println(cbd.obtenerUltimoId());
	}
	private void cargarTabla(String rutaArchivo)
	{
		int indice = 1;
		archivos.Diccionario diccionario = new Diccionario(rutaArchivo);
		String[] espaniol = diccionario.getPalabrasEspañol();
		String[] mapudungun = diccionario.getPalabrasMapudungun();
		int traducciones = Archivo.obtenerNumeroDeLineas();
		//adquirimos la conexion con la base de datos
		Connection conexion = obtenerConeccion();
		try {
			Statement instruccion = conexion.createStatement();
			//realizamos un for para hacer los inserts a la BD
			for(int i=0;i<traducciones;i++)
			{
				
				String consulta = "INSERT INTO traduccion VALUES (" +
						+(indice++)+",'"+espaniol[i]+"','"+mapudungun[i]+"')";
				//ejecutamos la consulta
				instruccion.execute(consulta);				
			}
			
		} catch (SQLException e) 
		{		
			e.printStackTrace();
			System.out.println("Error al crear o ejecutar la instruccion ");
		}
		
	}
	private int obtenerUltimoId()
	{
		//adquirimos la conexion con la base de datos
		Connection conexion = obtenerConeccion();
		int id =0;
		try {
			Statement instruccion = conexion.createStatement();
			//realizamos un for para hacer los inserts a la BD
			String consulta = ("SELECT MAX(ID_TRADUCCION) AS max_id FROM traduccion");
				//ejecutamos la consulta
			ResultSet resultado = instruccion.executeQuery(consulta);				
			if(resultado.next())
					id = resultado.getInt("max_id");				
			
		} catch (SQLException e) 
		{		
			e.printStackTrace();
			System.out.println("Error al realizar Select");
		}
		return id;
		
	}
	public void insertarTraduccion(String espaniol, String mapudungun)
	{
		Connection conexion = null;
		String consulta = "INSERT INTO traduccion VALUES ("+(obtenerUltimoId()+1)+",'"+espaniol+"','"+mapudungun+"')";
		try {
			conexion = obtenerConeccion();
			Statement instruccion = conexion.createStatement();
			instruccion.execute(consulta);
		} catch (SQLException e) {
			System.out.println("Error de conexion ");
		}
	}
	public void eliminarTraduccion (String traduccion){
		String consulta = "SELECT id_traduccion FROM traduccion"+"WHERE espaniol LIKE '%"+traduccion+"%'"
				+"OR mapudungun LIKE '%"+traduccion+"%'";
	}
}
