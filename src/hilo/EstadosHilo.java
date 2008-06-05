package hilo;

import grafica.*;
import principal.*;

/**
 * Clase que define los estados del hilo que maneja la compresión y
 * descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class EstadosHilo implements Runnable
{
    private VentanaProgreso progreso = null;
    private Compresor compresor = null;
    private String archivo = null;
    private String accion = null;
    private boolean termino = false;
    
    public EstadosHilo()
    {
        compresor = new Compresor(this);
    }
    
    public void run()
    {
        termino = false;
        if(accion.compareTo("comp")==0) compresor.comprimir(archivo, progreso);
        else compresor.descomprimir(archivo, progreso);
    }
    
    public void setArchivo(String arch, String acc, VentanaProgreso prog)
    {
        archivo = arch;
        accion = acc;
        progreso = prog;
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
