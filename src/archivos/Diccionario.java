package archivos;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.StringTokenizer;

public class Diccionario 
{
	private final String[] palabrasEspañol;
	private final String[] palabrasMapudungun;
	private final Archivo archivo;
	
	public Diccionario(String rutaArchivo) 
	{
		archivo = new Archivo(rutaArchivo);
		//llamamos al metodo de archivos para obtener el numero de lineas del mismo 
		int tamaño = archivo.obtenerNumeroDeLineas();
		//con el numero de lineas del archivo inicializamos los arreglos
		palabrasEspañol = new String[tamaño];
		palabrasMapudungun = new String[tamaño];
		//luego llanamos estos con el metodo llenarArreglos()
		llenarArreglos();
	}

	private void llenarArreglos()
	{
		BufferedReader memoria = 
				new BufferedReader(archivo.leerArchivo());

		int indice = 0;
		try 
		{
			String linea = memoria.readLine();
			while(linea!=null)
			{

				StringTokenizer st = new StringTokenizer(linea,":");
				palabrasMapudungun[indice] = st.nextToken(); //mapu
                palabrasEspañol[indice] = st.nextToken(); //espa
				indice++;
				linea = memoria.readLine();
			}
		}
		catch (IOException e) 
		{
			System.out.println("Error al leer el archivo");
		}
	}

	public String[] getPalabrasEspañol() {
		return palabrasEspañol;
	}

	public String[] getPalabrasMapudungun() {
		return palabrasMapudungun;
	}
        
    public String[] traducirAMapudungun(String palabra)
    {
    	palabra = limpiarAcentos(palabra);
        int tamaño = obtenerTamaño(palabra);
        if (tamaño>0)
        {
        		
        	int indice = 0;
        	String palabras[] = new String[tamaño];
            	for(int i=0; i<palabrasEspañol.length;i++)
            		if(palabrasEspañol[i].toLowerCase().contains(palabra.toLowerCase()))
            			palabras[indice++] = palabrasMapudungun[i]+" = "+palabrasEspañol[i];
            	return palabras;
        }
        return new String[]{"Palabra no encontrada"} ;
     }
        
     public String traducirAEspañol(String palabra)
     {
    	palabra = limpiarAcentos(palabra); 
        for(int i=0; i<palabrasMapudungun.length;i++)
            if(palabra.equalsIgnoreCase(palabrasMapudungun[i]))   
                    return palabrasEspañol[i];
            return "Palabra no encontrada";
        }
	public int obtenerTamaño(String palabra) {
	    int tamaño = 0;
	    for(int i=0; i<palabrasEspañol.length;i++)
	        if(palabrasEspañol[i].toLowerCase().contains(palabra.toLowerCase())) 
	        	tamaño ++;
		return tamaño;
	}
	public static String limpiarAcentos(String cadena) {
		String limpio =null;
		if (cadena !=null) {
		    String valor = cadena;
		    valor = valor.toUpperCase();
		    // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
		    limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
		    // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
		    limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
		    // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
		    limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
		}
		return limpio;
	}
}




