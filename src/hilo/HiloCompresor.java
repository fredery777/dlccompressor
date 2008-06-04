package hilo;

import java.io.*;
import principal.*;
/**
 * Clase que maneja el hilo que lanza la compresi�n o descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class HiloCompresor
{
    private HiloGestor hiloGestor = null;
    private Thread hilo = null;
    
    public HiloCompresor()
    {
        hiloGestor = new HiloGestor();
        hilo = new Thread(hiloGestor);
    }
    
    public void ejecutar(String origen, String accion)
    {
        if(!hilo.isAlive())
        {
            System.out.println("Procesando archivos...");
            hiloGestor.setOrigen(origen);
            hilo = new Thread(hiloGestor);
            hilo.start();
        }
        else
        {
            System.out.println("Espere, comprimiendo...");
        }
    }
    
    public void finEjecucion()
    {
        System.out.println("Fin de compresi�n!");
    }
    
    public void Terminar()
    {
        hiloGestor.Terminar();
    }
}
