/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hilo;

/**
 *
 * @author Administrador
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
