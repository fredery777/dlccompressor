package grafica;

/**
 * Clase que invoca al contructor de la ventana principal del compresor
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
public class Principal 
{
    public static void main(String[] args)
    {        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                VentanaInicio ven = new grafica.VentanaInicio();
                ven.setVisible(true);
            }
        });
    }
}