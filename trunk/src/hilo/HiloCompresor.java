package hilo;

import java.io.*;
import principal.*;
import grafica.*;

/**
 * Clase que maneja el hilo que lanza la compresi�n o descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class HiloCompresor
{
    private EstadosHilo estadosHilo = null;
    private Thread hilo = null;
    
    public HiloCompresor()
    {
        estadosHilo = new EstadosHilo();
        hilo = new Thread(estadosHilo);
    }
    
    public void ejecutar(String archivo, String accion, VentanaProgreso progreso)
    {
        if(!hilo.isAlive())
        {
            System.out.println("Procesando archivos...");
            estadosHilo.setArchivo(archivo, accion, progreso);
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
        estadosHilo.setTerminado();
    }
}
