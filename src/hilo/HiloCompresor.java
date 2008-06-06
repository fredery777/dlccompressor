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
    
    public HiloCompresor()
    {
        estadosHilo = new EstadosHilo();
        hilo = new Thread(estadosHilo);
    }
    
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
    
    public void Terminar()
    {
        estadosHilo.setTerminado();
    }
}
