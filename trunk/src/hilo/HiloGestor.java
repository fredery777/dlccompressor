package hilo;

/**
 * Clase que maneja los procesos de compresión y descompresión de archivos
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class HiloGestor implements Runnable
{
    private Empaquetador emp = null;
    private Compresor compresor = null;
    //private Info infoGestor = new Info();
    private boolean morir = false;
    
    //Creado por Gabriel Enrique Peressotti, hola.
    private HiloGestor()
    {
        this.emp = new Empaquetador(this);
        this.compresor = new Compresor(this);
    }
    
    public HiloGestor(GestorMensajes gm, FinEjecucion fe)
    {
        this.emp = new Empaquetador(this);
        this.compresor = new Comp(this);
        this.finEjecucion = fe;
        if(gm != null)
        { this.gm = gm; }
        else
        { this.gm = GestorMensajes.getGM(); }
    }
    
    
    
    private File Aorigen = null;
    private File Adestino = null;
    private File AnuevoNombre = null;
    private File AD2 = null;
    
    public void set(String origen, String nuevoNombre, String destino, String D2)
    { set(new File(origen), new File(nuevoNombre), new File(destino), new File(D2)); }
    public void set(File origen, File nuevoNombre, File destino, File D2)
    {
        this.Aorigen = origen;
        this.Adestino = destino;
        this.AnuevoNombre = nuevoNombre;
        this.AD2 = D2;
    }
    
    
    public boolean isTerminado()
    {
        return this.morir;
    }
    public void Terminar()
    {
        this.morir = true;
    }
    public void Vivir()
    {
        this.morir = false;
    }
    
    
    public void run()
    {
        morir = false;
        if(finEjecucion != null) finEjecucion.finEjecucion(infoGestor);
    }
    
    
    private Info Ejecutar(String origen, String nuevoNombre, String destino, String D2)
    {
        return Ejecutar(new File(origen), new File(nuevoNombre), new File(destino), new File(D2));
    }
    
    private Info Ejecutar(File origen, File nuevoNombre, File destino, File D2)
    {
        Info info = new Info();
        info.info = origen.getAbsolutePath();
        
        if(origen != null)
        {
            if(origen.isDirectory())//existe y es un directorio
            {
                nuevoNombre = new File(nuevoNombre.getAbsolutePath() + Comp.getExtencion());//agrega la extenci'on
                if(this.morir == true) {return info;}
                
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
                        if(this.morir == true) {return info;}
                        gm.EnviarMensaje("Descomprimiendo extenci'on: " + aux);
                        info.codigo = this.DescomprimirCarpeta(origen, destino);
                        
                    }
                    else
                    {
                        //lo tengo que comprimir
                        if(this.morir == true) {return info;}
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
    }
    
    
    /**
     * Comprime un directorio o un archivo
     */
    private Info Comprimir(File origen, File nuevoNombre)
    {
        Info info = new Info();
        info.info = nuevoNombre.getAbsolutePath();
        
        File f = emp.Empaquetar(origen);
        if(f != null)
        {
            gm.EnviarMensaje("Archivo empaquetado: " + f.getAbsolutePath());
            if(this.morir == true) {return info;}
            
            File fc = compresor.comprimir(f);
            if(fc != null)
            {
                gm.EnviarMensaje("Archivo Comprimido: " + fc.getAbsolutePath());
                info.codigo = Info.OKCreadoInt;
                info.info = fc.getAbsolutePath();
                if(this.morir == true) {return info;}
                
                if(nuevoNombre != null)
                {
                    if(nuevoNombre.isDirectory())
                    {
                        gm.EnviarMensaje("El nuevo nombre pertenece a un directorio existente");
                        gm.EnviarMensaje("Archivo queda: " + fc.getAbsolutePath());
                    }
                    else
                    {
                        if(nuevoNombre.exists())
                        { nuevoNombre.delete(); }
                        if(fc.renameTo(nuevoNombre))
                        {
                            gm.EnviarMensaje("Archivo Renombrado: " + nuevoNombre.getAbsolutePath());
                            info.info = nuevoNombre.getAbsolutePath();
                        }
                    }
                }
                
            }
            else
            {
                gm.EnviarMensaje("error al comprimir");
                info.codigo = Info.ERRORInt;
            }
        }
        else
        {
            gm.EnviarMensaje("No se pudo empaquetar");
            info.codigo = Info.CancelInt;
        }
        return info;
    }
    
    
    private int DescomprimirCarpeta(File origen, File destino)
    {
        File f = this.compresor.descomprimir(origen);
        if(f != null)
        {
            gm.EnviarMensaje("Archivo descomprimido: " + f.getAbsolutePath());
            if(this.morir == true) {return Info.ListoInt;}
            int b = this.emp.Desempaquetar(f, destino);
            if(b > 0)
            {
                gm.EnviarMensaje("Archivo desempaquetado en: " + destino.getAbsolutePath());
            }
            
            if(f.exists())
            {
                f.deleteOnExit();//lo marco para que se borre
            }
            
            return b;
        }
        gm.EnviarMensaje("Archivo null: ");
        return Info.ERRORInt;
    }
    
    
    
    
}
