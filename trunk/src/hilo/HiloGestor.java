package hilo;

import java.io.*;

/**
 * Clase que maneja los procesos de compresión y descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class HiloGestor implements Runnable
{
    private Empaquetador empaquetador = null;
    private Compresor compresor = null;
    private String origen = null;
    private boolean terminar = false;
    
    public HiloGestor()
    {
        this.empaquetador = new Empaquetador();    //se le puede enviar un parametro HILO
        this.compresor = new Compresor();
    }
    
    public void setOrigen(String origen)
    {
        System.out.println("HiloGestor origen: " + origen);
        this.origen = origen;
    }
    
    public boolean isTerminado()
    {
        return this.terminar;
    }
    
    public void Terminar()
    {
        this.terminar = true;
    }
    
    public void Vivir()
    {
        this.terminar = false;
    }
    
    public void run()
    {
        terminar = false;
        compresor.comprimir(origen);
    }
}
