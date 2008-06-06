package grafica;

/**
 * Interfaz gr�fica para realizar la compresi�ny descompresi�n
 * de archivos mediante el algoritmo de Huffman
 * @author  Morales, Gustavo - Rold�n, Marco - Senn, Anal�a
 * @version Junio de 2008
 */
import java.io.*;
import javax.swing.*;
import principal.*;
import hilo.*;

public class VentanaInicio extends javax.swing.JFrame
{
    private JFileChooser jfd = new JFileChooser();
    private File f;
    private int porcentaje;
    private int pCompletado;
    private boolean terminar;
    private HiloCompresor hiloCompresor = null;
    Object[] opciones = {"Si", "No"};
    
    /** Creates new form VentanaInicial */
    public VentanaInicio()
    {
        initComponents();
        hiloCompresor = new HiloCompresor();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()
    {
        btnArchComprimir = new javax.swing.JButton();
        btnDescomprimir = new javax.swing.JButton();
        jpbProgreso = new javax.swing.JProgressBar();
        btnComprimir = new javax.swing.JButton();
        btnDetener = new javax.swing.JButton();
        txtRutaDescomprimir = new javax.swing.JTextField();
        lblRutaDescomprimir = new javax.swing.JLabel();
        lblRutaComprimir = new javax.swing.JLabel();
        txtRutaComprimir = new javax.swing.JTextField();
        btnArchDescomprimir = new javax.swing.JButton();
        mnuMenu = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        salir = new javax.swing.JMenuItem();
        mnuOpCompresion = new javax.swing.JMenu();
        mnuArchComprimir = new javax.swing.JMenuItem();
        mnuComprimir = new javax.swing.JMenuItem();
        mnuOpDescompresion = new javax.swing.JMenu();
        mnuArchDescomprimir = new javax.swing.JMenuItem();
        mnuDescomprimir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Uso de la clase File");
        setBackground(new java.awt.Color(204, 204, 255));
        setResizable(false);
        getContentPane().setLayout(null);
        jpbProgreso.setStringPainted(true);
        btnDetener.setEnabled(false);

        btnArchComprimir.setText("Archivo a comprimir");
        btnArchComprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchComprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnArchComprimir);
        btnArchComprimir.setBounds(10, 10, 150, 23);

        btnDescomprimir.setText("Descomprimir");
        btnDescomprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescomprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnDescomprimir);
        btnDescomprimir.setBounds(170, 110, 130, 23);
        getContentPane().add(jpbProgreso);
        jpbProgreso.setBounds(50, 250, 210, 40);

        btnComprimir.setText("Comprimir");
        btnComprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnComprimir);
        btnComprimir.setBounds(170, 10, 130, 23);

        btnDetener.setText("Detener proceso");
        btnDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerActionPerformed(evt);
            }
        });
        getContentPane().add(btnDetener);
        btnDetener.setBounds(90, 200, 130, 23);

        txtRutaDescomprimir.setEditable(false);
        getContentPane().add(txtRutaDescomprimir);
        txtRutaDescomprimir.setBounds(10, 160, 290, 20);

        lblRutaDescomprimir.setText("Archivo a descomprimir:");
        getContentPane().add(lblRutaDescomprimir);
        lblRutaDescomprimir.setBounds(10, 140, 290, 20);

        lblRutaComprimir.setText("Archivo a comprimir:");
        getContentPane().add(lblRutaComprimir);
        lblRutaComprimir.setBounds(10, 40, 290, 20);

        txtRutaComprimir.setEditable(false);
        getContentPane().add(txtRutaComprimir);
        txtRutaComprimir.setBounds(10, 60, 290, 20);

        btnArchDescomprimir.setText("Archivo a descomprimir");
        btnArchDescomprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchDescomprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnArchDescomprimir);
        btnArchDescomprimir.setBounds(10, 110, 150, 23);

        mnuArchivo.setText("Archivo");

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        mnuArchivo.add(salir);

        mnuMenu.add(mnuArchivo);

        mnuOpCompresion.setText("Compresi�n");

        mnuArchComprimir.setText("Seleccionar archivo...");
        mnuArchComprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuArchComprimirActionPerformed(evt);
            }
        });
        mnuOpCompresion.add(mnuArchComprimir);

        mnuComprimir.setText("Comprimir");
        mnuComprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuComprimirActionPerformed(evt);
            }
        });
        mnuOpCompresion.add(mnuComprimir);

        mnuMenu.add(mnuOpCompresion);

        mnuOpDescompresion.setText("Descompresi�n");

        mnuArchDescomprimir.setText("Seleccionar archivo...");
        mnuArchDescomprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuArchDescomprimirActionPerformed(evt);
            }
        });
        mnuOpDescompresion.add(mnuArchDescomprimir);

        mnuDescomprimir.setText("Descomprimir");
        mnuDescomprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDescomprimirActionPerformed(evt);
            }
        });
        mnuOpDescompresion.add(mnuDescomprimir);

        mnuMenu.add(mnuOpDescompresion);

        setJMenuBar(mnuMenu);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-319)/2, (screenSize.height-355)/2, 319, 355);
    }// </editor-fold>                        

    private void mnuArchComprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mnuArchComprimirActionPerformed
        limpiar();
        seleccionar("comp");
    }//GEN-LAST:event_mnuArchComprimirActionPerformed

    private void mnuComprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mnuComprimirActionPerformed
        chequearComprimir();
        limpiar();
    }//GEN-LAST:event_mnuComprimirActionPerformed

    private void mnuArchDescomprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mnuArchDescomprimirActionPerformed
        limpiar();
        seleccionar("descomp");
    }//GEN-LAST:event_mnuArchDescomprimirActionPerformed

    private void mnuDescomprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_mnuDescomprimirActionPerformed
        chequearDescomprimir();
        limpiar();
    }//GEN-LAST:event_mnuDescomprimirActionPerformed

    private void btnArchComprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchComprimirActionPerformed
        limpiar();
        seleccionar("comp");
    }//GEN-LAST:event_btnArchComprimirActionPerformed

    private void btnComprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_btnComprimirActionPerformed
        chequearComprimir();
        limpiar();
    }//GEN-LAST:event_btnComprimirActionPerformed

    private void btnArchDescomprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_btnArchDescomprimirActionPerformed
        limpiar();
        seleccionar("descomp"); 
    }//GEN-LAST:event_btnArchDescomprimirActionPerformed

    private void btnDescomprimirActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_btnDescomprimirActionPerformed
        chequearDescomprimir();
        limpiar();
    }//GEN-LAST:event_btnDescomprimirActionPerformed

    private void btnDetenerActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_btnDetenerActionPerformed
        terminar();
    }//GEN-LAST:event_btnDetenerActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) 
    {//GEN-FIRST:event_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed
    
    /**
      * Selecciona el archivo a comprimir o descomprimir
      */
    private void seleccionar(String opcion)
    {
        this.setTitle("Seleccione el archivo: ");
        
        int r = jfd.showOpenDialog(this);
        if(r == JFileChooser.APPROVE_OPTION)
        {
            f = jfd.getSelectedFile();
            if (opcion.compareTo("comp") == 0)
            {
                txtRutaComprimir.setText(f.getAbsolutePath());
            }
            else
            {
                txtRutaDescomprimir.setText(f.getAbsolutePath());
            }
        }
    }
    
    /**
      * Setea los componentes a su estado inicial
      */
    private void limpiar ()
    {
        txtRutaComprimir.setText("");
        txtRutaDescomprimir.setText("");
        jpbProgreso.setValue(0);
        jpbProgreso.setString("");
    }
    
    /**
      * Llama al metodo comprimir, verificando previamente que el usuario haya
      * seleccionado un archivo
      */
    public void chequearComprimir()
    {
        if(txtRutaComprimir.getText().trim().length() == 0 )
        {
            JOptionPane.showMessageDialog(this, "Seleccione antes un archivo para comprimir", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            btnDetener.setEnabled(true);
            ejecutar(txtRutaComprimir.getText(), "comp");
        }
    }
    
    /**
      * Llama al metodo descomprimir, verificando previamente que el usuario
      * haya seleccionado un archivo
      */
    public void chequearDescomprimir()
    {
        if(txtRutaDescomprimir.getText().trim().length() == 0 )
        {
            JOptionPane.showMessageDialog(this, "Seleccione antes un archivo para descomprimir", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else 
        {
            if (existencia(txtRutaDescomprimir.getText()))
            {
                int op = JOptionPane.showOptionDialog(this, "Desea sobreescribir el archivo?", "Sobreescrir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                if (op == 0)
                {
                    btnDetener.setEnabled(true);
                    ejecutar(txtRutaDescomprimir.getText(), "descomp");
                }
                else JOptionPane.showMessageDialog(this, "Descompresion cancelada por el usuario", "Info",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                btnDetener.setEnabled(true);
                ejecutar(txtRutaDescomprimir.getText(), "descomp");
            }
        }
    }
    
    /**
      * @return retorna la existencia del archivo descomprimido en el disco
      */
    public boolean existencia(String comprimido)
    {
        try
        {
            int pos = comprimido.indexOf(".");
            if(pos == -1) throw new Exception ("El archivo no parece un archivo comprimido...");
            String ext = comprimido.substring( pos + 1 );
            if( ext.compareTo("huffman") != 0 ) throw new Exception ("El archivo no tiene la extensi�n huffman...");
            
            File f = new File(comprimido);
            RandomAccessFile RAF = new RandomAccessFile(f, "r");    
            String descomprimido = RAF.readUTF();
            RAF.close();
            f = new File(descomprimido);
            if(f.exists())
            { 
                return true;
            }
            return false;
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(this, "Error al procesar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "El archivo no parece estar comprimido", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    /**
      * Llama al metodo ejecutar de la clase HiloCompresor
      */
    public void ejecutar(String archivo, String accion)
    {
        terminar = false;
        hiloCompresor.ejecutar(archivo, accion, jpbProgreso, btnDetener);
    }
    
    /**
      * Llama al metodo terminar de la clase HiloCompresor
      */
    public void terminar()
    {
        terminar = true;
        hiloCompresor.Terminar();
        btnDetener.setEnabled(false);
        limpiar();
        JOptionPane.showMessageDialog(this, "Proceso detenido por el usuario", "Error",JOptionPane.ERROR_MESSAGE);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArchComprimir;
    private javax.swing.JButton btnArchDescomprimir;
    private javax.swing.JButton btnComprimir;
    private javax.swing.JButton btnDescomprimir;
    private javax.swing.JButton btnDetener;
    private javax.swing.JProgressBar jpbProgreso;
    private javax.swing.JLabel lblProgreso;
    private javax.swing.JLabel lblRutaComprimir;
    private javax.swing.JLabel lblRutaDescomprimir;
    private javax.swing.JMenuItem mnuArchComprimir;
    private javax.swing.JMenuItem mnuArchDescomprimir;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenuItem mnuComprimir;
    private javax.swing.JMenuItem mnuDescomprimir;
    private javax.swing.JMenuBar mnuMenu;
    private javax.swing.JMenu mnuOpCompresion;
    private javax.swing.JMenu mnuOpDescompresion;
    private javax.swing.JMenuItem salir;
    private javax.swing.JTextField txtRutaComprimir;
    private javax.swing.JTextField txtRutaDescomprimir;
    // End of variables declaration//GEN-END:variables
}
