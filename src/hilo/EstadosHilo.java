package hilo;

import principal.*;
import javax.swing.*;

/**
 * Clase que define los estados del hilo que maneja la compresión y
 * descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class EstadosHilo implements Runnable
{
    private JProgressBar barra;
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
        if(accion.compareTo("comp")==0) compresor.comprimir(archivo, barra);
        else compresor.descomprimir(archivo, barra);
    }
    
    public void setArchivo(String arch, String acc, JProgressBar barraProgreso)
    {
        archivo = arch;
        accion = acc;
        barra = barraProgreso;
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
