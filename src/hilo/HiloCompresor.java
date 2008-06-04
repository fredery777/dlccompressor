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
    private EstadosHilo estadosHilo = null;
    //private HiloGestor hiloGestor = null;
    private Thread hilo = null;
    
    public HiloCompresor()
    {
        // hiloGestor = new HiloGestor();
        estadosHilo = new EstadosHilo();
        //hilo = new Thread(hiloGestor);
        hilo = new Thread(estadosHilo);
    }
    
    public void ejecutar(String archivo, String accion)
    {
        if(!hilo.isAlive())
        {
            System.out.println("Procesando archivos...");
            //hiloGestor.setArchivo(origen);
            estadosHilo.setArchivo(archivo);
            //hilo = new Thread(hiloGestor);
            hilo = new Thread(estadosHilo);
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
        //hiloGestor.Terminar();
        estadosHilo.setTerminado();
    }
}
