package archivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Archivo 
{
	private static String rutaArchivo;

	public Archivo(String rutaArchivo) 
	{
		this.rutaArchivo = rutaArchivo;
	}
	
	public static File representarArchivo()
	{
		if(!rutaArchivo.equals(null))
			return new File(rutaArchivo);
		else
			return null;
	}
	
	public static FileReader leerArchivo()
	{
		if(representarArchivo() != null)
			try 
			{
				return new FileReader(representarArchivo());
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("No se puede leer el archivo");
			}
		return null;
	}
	
	public static int obtenerNumeroDeLineas()
	{
		BufferedReader memoria = new BufferedReader(leerArchivo());
		int contador = 0;
		try 
		{
			while(memoria.readLine()!=null)
				contador++;
		} 
		catch (IOException e) 
		{
			System.out.println("Error al leer el archivo");
		}
		return contador;
	}
}
