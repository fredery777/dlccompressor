package hilo;

import java.io.*; 

/**
 * Clase que maneja el hilo que lanza la compresión o descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class HiloCompresor
{
    private HiloGestor hiloGestor = null;
    private Thread hilo = null;
    
    public HiloCompresor()
    {
        this.hiloGestor = new HiloGestor();
        this.hilo = new Thread(hiloGestor);
    }
    
    public void ejecutar(String origen)
    {
        if(!hilo.isAlive()) // comprueba si el hilo sigue vivo
        {
            System.out.println("Iniciando compresión...");
            System.out.println("Ejecutar TRUe de HiloCompresor...");
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
        System.out.println("Fin de compresión!");
    }
    
    public void Terminar()
    {
        hiloGestor.Terminar();
    }
}
