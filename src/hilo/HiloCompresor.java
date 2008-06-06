package hilo;

import java.io.*;
import principal.*;
import javax.swing.*;

/**
 * Clase que maneja el hilo que lanza la compresi�n o descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class HiloCompresor
{
    private EstadosHilo estadosHilo = null;
    private Thread hilo = null;
    
    /**
      * Llama al constructor por defecto
      */
    public HiloCompresor()
    {
        estadosHilo = new EstadosHilo();
        hilo = new Thread(estadosHilo);
    }
    
    /**
      * Si no hay ning�n hilo corriendo permite la compresi�n o descompresi�n
      * de un archivo, sino informa que se debe terminar primero de procesar
      * el archivo anterior
      */
    public void ejecutar(String archivo, String accion, JProgressBar barra, JButton boton)
    {
        if(!hilo.isAlive())
        {
            estadosHilo.setArchivo(archivo, accion, barra, boton);
            hilo = new Thread(estadosHilo);
            hilo.start();
        }
        else
        {
            System.out.println("Espere, comprimiendo...");
        }
    }
    
    public void setTerminar()
    {
        estadosHilo.setTerminado();
    }
}
