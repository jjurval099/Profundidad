package ies.jandula.launch;

import java.io.File;
import java.util.List;
import java.util.Map;

import ies.jandula.parser.Parser;

public class Launcher 
{

    public static void main(String[] args) 
    {
        // Verificar que se ha pasado un argumento 
        if (args.length < 1) 
        {
            System.out.println("Por favor, proporciona la ruta de un directorio.");
            return;
        }

        // Obtener la ruta del directorio desde los argumentos
        String directorioRuta = args[0];
        File directorio = new File(directorioRuta);

        // Verificar que el directorio existe y es un directorio válido
        if (!directorio.exists() || !directorio.isDirectory()) 
        {
            System.out.println("La ruta proporcionada no es un directorio válido.");
        }

        // Crear una instancia de Parser
        Parser parser = new Parser();

        // Calcular la profundidad del directorio
        Map<Integer, List<String>> rutasMap = parser.calcularProfundidad(directorioRuta, directorio, 0);

        // Mostrar el mapa de rutas
        System.out.println("Mapa de rutas calculado:");
        parser.mostrarMap(rutasMap);
        
    }
}