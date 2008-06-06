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
      * El metodo comprime o descomprime en base a la variable "accion"
      */
    public void run()
    {
        termino = false;
        if(accion.compareTo("comp")==0) compresor.comprimir(archivo, barra, boton);
        else compresor.descomprimir(archivo, barra, boton);
    }
    
    /**
      * El metodo setea valores necesarios para otras clases
      */
    public void setArchivo(String arch, String acc, JProgressBar barraProgreso, JButton botonDetener)
    {
        archivo = arch;
        accion = acc;
        
        barra = barraProgreso;
        boton = botonDetener;
    }
    
    /**
      * @return si termino o no el hilo
      */
    public boolean getTerminado()
    {
        return this.termino;
    }
    
    /**
      * @param setea como terminado el hilo
      */
    public void setTerminado()
    {
        this.termino = true;
    }
}
