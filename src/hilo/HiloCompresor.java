package hilo;

import java.io.*;
import principal.*;
import javax.swing.*;

/**
 * Clase que maneja el hilo que lanza la compresión o descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
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
      * Si no hay ningún hilo corriendo permite la compresión o descompresión
      * de un archivo, sino informa que se debe terminar primero de procesar
      * el archivo anterior
      */
    public boolean ejecutar(String archivo, String accion, JProgressBar barra, JButton boton)
    {
        if(!hilo.isAlive())
        {
            estadosHilo.setArchivo(archivo, accion, barra, boton);
            hilo = new Thread(estadosHilo);
            hilo.start();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
      * Manda a setear que el hilo debe terminar
      */
    public void setTerminar()
    {
        estadosHilo.setTerminado();
    }
}
