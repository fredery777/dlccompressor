package hilo;

import java.io.*; //File

/**
 * Clase que maneja el hilo que lanza la compresión o descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class HiloCompresor
{
    private HiloGestor g = null;
    
    private Thread t = null;
    
    public HiloCompresor()
    {
        g = new HiloGestor(this);
        t = new Thread(g);
    }
    
    public HiloCompresor()
    {
        g = new HiloGestor(this);
        t = new Thread(g);
    }
    
    public void Ejecutar(String origen, String nuevoNombre, String destino, String D2)
    {
        Ejecutar(new File(origen), new File(nuevoNombre), new File(destino), new File(D2));
    }
    
    public void Ejecutar(File origen, File nuevoNombre, File destino, File D2)
    {
        if(t.isAlive() == false) // comprueba si el hilo sigue vivo
        {
            System.out.println("Iniciando compresión...");
            g.set(origen, nuevoNombre, destino, D2);
            t = new Thread(g);
            t.start();
        }
        else
        {
            System.out.println("Espere, comprimiendo...");
        }
    }
    
    public void finEjecucion()
    {
        System.out.println("Fin de compresión!");
        if(this.fe != null)
        {
            this.fe.finEjecucion(info);
        }
    }
    
    public void Terminar()
    {
        g.Morir();
    }
}
