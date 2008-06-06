package hilo;

import principal.*;
import javax.swing.*;

/**
 * Clase que define los estados del hilo que maneja la compresi�n y
 * descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class EstadosHilo implements Runnable
{
    private JProgressBar barra;
    private JButton boton;
    
    private Compresor compresor = null;
    private String archivo = null;
    private String accion = null;
    private boolean termino = false;
    
    /**
      * Llama al constructor por defecto
      */
    public EstadosHilo()
    {
        compresor = new Compresor(this);
    }
    
    /**
      * El metodo comprime o descomprime en base Llama al m�todo runmetodo terminar de la clase HiloCompresor
      */
    public void run()
    {
        termino = false;
        if(accion.compareTo("comp")==0) compresor.comprimir(archivo, barra, boton);
        else compresor.descomprimir(archivo, barra, boton);
    }
    
    public void setArchivo(String arch, String acc, JProgressBar barraProgreso, JButton botonDetener)
    {
        archivo = arch;
        accion = acc;
        
        barra = barraProgreso;
        boton = botonDetener;
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
