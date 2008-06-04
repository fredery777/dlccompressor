package hilo;

import principal.*;

/**
 * Clase que define los estados del hilo que maneja la compresi�n y
 * descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class EstadosHilo implements Runnable
{
    private Compresor compresor = null;
    private String origen = null;
    private boolean termino = false;
    
    public EstadosHilo()
    {
        compresor = new Compresor(this);
    }
    
    public void run()
    {
        termino = false;
        compresor.comprimir(origen);
    }
    
    public void setArchivo(String arch)
    {
        origen = arch;
    }
    
    public boolean isTerminado()
    {
        return this.termino;
    }
    
    public void setTerminado()
    {
        this.termino = true;
    }
    
    public void setActivo()
    {
        this.termino = false;
    }
}
