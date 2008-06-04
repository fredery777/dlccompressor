package hilo;

import java.io.*;

/**
 * Clase que maneja los procesos de compresión y descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class HiloGestor implements Runnable
{
    private Empaquetador emp = null;
    private Compresor compresor = null;
    private boolean terminar = false;
    
    public HiloGestor()
    {
        this.emp = new Empaquetador();    //se le puede enviar un parametro HILO
        this.compresor = new Compresor();
    }
    
    private File Aorigen = null;
    private File Adestino = null;
    private File AnuevoNombre = null;
    private File AD2 = null;
    
    public void set(String origen, String nuevoNombre, String destino, String D2)
    {
        set(new File(origen), new File(nuevoNombre), new File(destino), new File(D2));
    }
    public void set(File origen, File nuevoNombre, File destino, File D2)
    {
        this.Aorigen = origen;
        this.Adestino = destino;
        this.AnuevoNombre = nuevoNombre;
        this.AD2 = D2;
    }
    
    public boolean isTerminado()
    {
        return this.terminar;
    }
    
    public void Terminar()
    {
        this.terminar = true;
    }
    
    public void Vivir()
    {
        this.terminar = false;
    }
    
    public void run()
    {
        terminar = false;
        // antes mandaba un mensaje si era NULL
    }

    /*private info Ejecutar(String origen, String nuevoNombre, String destino, String D2)
    {
        return Ejecutar(new File(origen), new File(nuevoNombre), new File(destino), new File(D2));
    }*/
    
    /*private Info Ejecutar(File origen, File nuevoNombre, File destino, File D2)
    {
        Info info = new Info();
        info.info = origen.getAbsolutePath();
        
        if(origen != null)
        {
            if(origen.isDirectory())//existe y es un directorio
            {
                nuevoNombre = new File(nuevoNombre.getAbsolutePath() + Comp.getExtencion());//agrega la extenci'on
                if(this.terminar == true) {return info;}
                
                gm.EnviarMensaje("Comprimiendo carpeta a: " + nuevoNombre);
                info = this.Comprimir(origen, nuevoNombre);
            }
            else
            {
                if(origen.isFile())
                {
                    String aux = origen.getAbsolutePath().substring(origen.getAbsolutePath().length() - 4, origen.getAbsolutePath().length());
                    if(aux.equalsIgnoreCase(Comp.getExtencion()))
                    {
                        //es un comprimido
                        if(this.terminar == true) {return info;}
                        gm.EnviarMensaje("Descomprimiendo extenci'on: " + aux);
                        info.codigo = this.DescomprimirCarpeta(origen, destino);
                        
                    }
                    else
                    {
                        //lo tengo que comprimir
                        if(this.terminar == true) {return info;}
                        nuevoNombre = new File(nuevoNombre.getAbsolutePath() + Comp.getExtencion());//agrega la extenci'on
                        gm.EnviarMensaje("Comprimiendo archivo a: " + nuevoNombre);
                        info = this.Comprimir(origen, nuevoNombre);
                    }
                    
                    
                }
                else
                {
                    gm.EnviarMensaje("La direcci'on seleccionada no es una carpeta o archivo existente");
                    info.codigo = Info.ErrorSintaxisInt;
                }
                
            }
            
            return info;
        }
        gm.EnviarMensaje("Alguno de lo par'ametros no es v'alido");
        info.codigo = Info.ErrorNullInt;
        return info;
    }*/
    
    
    /**
     * Comprime un directorio o un archivo
     */
    /*private void Comprimir(File origen, File nuevoNombre) // antes INFO
    {
        File f = emp.Empaquetar(origen);
        if(f != null)
        {
            System.out.println("Archivo empaquetado: " + f.getAbsolutePath());
            //if(this.terminar) {return info;}
            
            File fc = compresor.comprimir(f);
            if(fc != null)
            {
                System.out.println("Archivo Comprimido: " + fc.getAbsolutePath());
                // if(this.terminar) {return info;}
                
                if(nuevoNombre != null)
                {
                    if(nuevoNombre.isDirectory())
                    {
                        System.out.println("El nuevo nombre pertenece a un directorio existente");
                        System.out.println("Archivo queda: " + fc.getAbsolutePath());
                    }
                    else
                    {
                        if(nuevoNombre.exists()) nuevoNombre.delete();
                        if(fc.renameTo(nuevoNombre)) System.out.println("Archivo Renombrado: " + nuevoNombre.getAbsolutePath());
                    }
                }
                
            }
            else
            {
                System.out.println("error al comprimir");
            }
        }
        else
        {
            System.out.println("No se pudo empaquetar");
        }
    }
    */
    
    /*private int DescomprimirCarpeta(File origen, File destino)
    {
        File f = this.compresor.descomprimir(origen);
        if(f != null)
        {
            System.out.println("Archivo descomprimido: " + f.getAbsolutePath());
            // if(this.terminar) {return Info.ListoInt;}
            int b = this.emp.Desempaquetar(f, destino);
            if(b > 0)
            {
                System.out.println("Archivo desempaquetado en: " + destino.getAbsolutePath());
            }
            if(f.exists())
            {
                f.deleteOnExit();//lo marco para que se borre
            }
            return b;
        }
        System.out.println("Archivo null: ");
        return 0;
    }*/
}
