package hilo;

import java.io.*;
import principal.*;

/**
 * Clase que maneja los procesos de compresi�n y descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class HiloGestor implements Runnable
{
    private Compresor compresor = null;
    private String origen = null;
    private boolean terminar = false;
    
    public HiloGestor()
    {
        compresor = new Compresor();
    }
    
    public void setOrigen(String orig)
    {
        origen = orig;
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
