package hilo;

import java.io.*; //File

/**
 * Clase que maneja el hilo que lanza la compresi�n o descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
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
            System.out.println("Iniciando compresi�n...");
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
        System.out.println("Fin de compresi�n!");
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
