package ies.jandula.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Parser
{
    
    private int profundidadMaxima = 0;
    private  Map<Integer,List<String>> rutasMap;
    private List<String> listRutas;
    private List<String> listPDF;
    
    public Parser()
    {
    	rutasMap = new TreeMap<Integer, List<String>>();
    	this.listRutas= new ArrayList<String>();
    }
    public Map<Integer,List<String>> calcularProfundidad(String args, File file, int profundidadActual)
    {
    	file=new File(args);
    			
        if (file.isDirectory()) 
        {
            profundidadActual++;
            this.rutasMap=calcularRutaMasLarga(args, profundidadActual);
         
            File[] files = file.listFiles();
            if (files != null) 
            {
                for (File f : files) 
                {
                    String nextArgs = f.getAbsolutePath();
                    calcularProfundidad(nextArgs, f, profundidadActual);
                }
            }
        }        
        
        return rutasMap;
    }
    public Map<Integer,List<String>> calcularRutaMasLarga(String args, int profundidadActual)
    {
    	
        if(profundidadActual > profundidadMaxima && profundidadActual>1)
        {
        	
        	rutasMap.clear();
        	listRutas.clear();
            // Limpiamos la lista y el mapa de rutas anteriores
            listRutas.add(args);
            profundidadMaxima = profundidadActual;//Sera la nueva profundidad maxima
            
            rutasMap.put(profundidadMaxima, listRutas);
        }
        else if(profundidadActual == profundidadMaxima)
        {
            listRutas.add(args); 
        }
       
        
        return rutasMap;
    }
    
    
    public void mostrarMap(Map<Integer,List<String>> rutas)
    {
    	for(Entry<Integer,List<String>> entry: rutas.entrySet())
		{
			System.out.println(entry.getKey());
			for(String ruta:entry.getValue())
			{
				System.out.println(ruta);
			}
		}
    }
    
    public Map<Integer,List<String>> eliminarRutasDuplicadas(Map<Integer,List<String>> rutasMap)
    {
    	Map<Integer, List<String>> rutasSinDuplicados = new HashMap<Integer, List<String>>();
    		
    	for(Map.Entry<Integer, List<String>> entry:rutasMap.entrySet())
    	{
    		Integer profundidad = entry.getKey();//Obtener profundidad
    		List<String>listRepetidas=entry.getValue();//Obtener lista
    		if(!rutasSinDuplicados.containsValue(listRepetidas))//Si la lista no esta en el mapa
    		{
    			rutasSinDuplicados.put(profundidad, listRepetidas);//La introduzco en el nuevo mapa
    		}
    	}
    	
		return rutasSinDuplicados;
    	
    }
	
    public Entry<Integer,List<String>> obtenerClaveMaxima(Map<Integer,List<String>> rutasMap)
    {
    	Entry<Integer, List<String>> entradaMaxima = null;
    		
    	for(Map.Entry<Integer, List<String>> entry:rutasMap.entrySet())
    	{
    		
    		if(entradaMaxima==null||entry.getKey()>entradaMaxima.getKey())//Si la clave de la entrada es mayor a la entrada maxima
    		{
    			entradaMaxima=entry;//La entrada se covierte en la nueva entrada maxima
    		}    		
    	}
    	
		return entradaMaxima;    	
    }
    
    public List<String> obtenerPDF(String directoryPath) {
        List<String> listPDF = new ArrayList<>();
        File directory = new File(directoryPath);
        
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    buscarPDFRecursivo(file, listPDF);
                }
            }
        }
        
        return listPDF;
    }

    private void buscarPDFRecursivo(File file, List<String> listPDF) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    buscarPDFRecursivo(f, listPDF);
                }
            }
        } else {
            if (file.getName().endsWith(".pdf")) {
                listPDF.add(file.getAbsolutePath());
            }
        }
    }

}
