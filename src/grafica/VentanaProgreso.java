package grafica;


import java.awt.*;
import javax.swing.*;

/**
 * Clase que muestra una barra con el progreso de la compresión o descompresión
 * de un archivo
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class VentanaProgreso extends JDialog
{
    private JPanel jContentPane = null;
    private JProgressBar jProgressBar = null;
    private String nombre;
	
    public VentanaProgreso()
    {
        super();
        initialize();
    }
	
    public VentanaProgreso(String nom) 
    {
        super();
        nombre = nom;
        initialize();
    }

    private void initialize()
    {
        if(nombre != null) this.setTitle(nombre);
        else this.setTitle("Progreso...");
        this.setSize(300, 104);
        this.setContentPane(getJContentPane());
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        int heightScreen = dim.height;
        int widthScreen = dim.width;
        int heightWindow = this.getHeight();
        int widthWindow = this.getWidth();
        this.setLocation(((widthScreen/2)-(widthWindow/2)),((heightScreen/2)-(heightWindow/2)));
    }

    private javax.swing.JPanel getJContentPane() 
    {
        if(jContentPane == null)
        {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJProgressBar(), null);
        }
        return jContentPane;
    }
	
    public JProgressBar getJProgressBar() 
    {
        if (jProgressBar == null)
        {
            jProgressBar = new JProgressBar();
            jProgressBar.setOpaque(true);
            jProgressBar.setBounds(49, 13, 190, 44);
            jProgressBar.setName("");
            jProgressBar.setStringPainted(true);
        }
        return jProgressBar;
    }
	
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nom)
    {
        nombre = nom;
    }
 }