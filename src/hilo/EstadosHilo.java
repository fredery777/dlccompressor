package hilo;

/**
 * Clase que define los estados del hilo que maneja la compresi�n y
 * descompresi�n de archivos
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class EstadosHilo extends Thread //implements Runneable
{
    private boolean termino = false;
    
    // saque el run
    
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
