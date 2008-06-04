package hilo;

/**
 * Clase que define los estados del hilo que maneja la compresión y
 * descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
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
