package hilo;

import java.io.*;
import principal.*;
/**
 * Clase que maneja el hilo que lanza la compresión o descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
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
        System.out.println("Fin de compresión!");
    }
    
    public void Terminar()
    {
        //hiloGestor.Terminar();
        estadosHilo.setTerminado();
    }
}
