package hilo;

import java.io.*;

/**
 * Clase que invoca al contructor de la ventana principal del compresor
 * @author  Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class Empaquetador 
{        
    private EstadosHilo hilo = null;
    
    public Empaquetador()
    {
        this.hilo = new EstadosHilo();
    }
    
    public Empaquetador(EstadosHilo hilo)
    {
        this.hilo = hilo;
    }
    
    public EstadosHilo getEstadoHilo()
    {
        return this.hilo;
    }
    
    /**
     * Crea el árbol de directorios correspondientes al archivo o directorio recivido
     */
    public boolean crearDirectorios(File f)
    {
        if(f == null) return false;        
        
        //extraigo la ruta del directorio
        String aux = f.getAbsolutePath();
        boolean b = false;//indica si ley'o un '.' punto
        for(int i = aux.length()-1; i >= 0; i--)
        {
            if(aux.charAt(i) == '\\')
            {
                //si antes vino un '.' entonces se paso un archivo por parámetro
                if(b == true) aux = aux.substring(0, i);
                break;
            }
            if(aux.charAt(i) == '.') b = true;
        }
        //creo el o los directorios
        File directorio = new File(aux);
        return directorio.mkdirs();
    }
    
    /**
     * Crea el 'arbo de directorios corespondintes al archivo o directorio recivido
     */
    public boolean crearDirectorios(String s)
    {
        return crearDirectorios(new File(s));
    }
    
    /**
     * Toma un nombre de un archivo y retorna la ruta del directorio correspondiente
     */
    public String getDirectorio(String file)
    {
        return getDirectorio(new File(file));
    }
    
    /**
     * Toma un nombre de un archivo y retorna la ruta del directorio correspondiente
     */
    public String getDirectorio(File file)
    {
        String aux = file.getAbsolutePath();
        
        boolean b = false;//indica si ley'o un '.' punto
        for(int i = aux.length()-1; i >= 0; i--)
        {
            if(aux.charAt(i) == '\\')
            {
                if(b == true)//si antes vino un '.' punto entonses me han pasado un archivo
                { aux = aux.substring(0, i); }
                break;
            }
            if(aux.charAt(i) == '.')
            { b = true; }
        }
        return aux;
    }
    
    /**
     * Toma la direcci'on de un directorio o archivo y genera un archivo con todos los
     * archivos que tenga dentro.
     *  Retorna le archivo empaquetado
     */
    public File Empaquetar(File dire)
    {
        if(dire.isDirectory() || dire.isFile())
        {
            //pregunta si debe seguir o cortar
            if(hilo.isMorir() == true)//if(this.morir == true)
            { return null; }
            
            File f = null;
            RandomAccessFile aux = null;
            try{
                //f = File.createTempFile("gtemp", null);
                f = File.createTempFile("gtemp", null, dire.getParentFile());
                //f = new File(dire.getAbsolutePath()+"G\\holas.txt");
                //crearDirectorios(f);
                f.deleteOnExit();
                aux = new RandomAccessFile(f, "rw");
            }
            catch(IOException io)
            {
                GestorMensajes.getEx().EnviarMensaje("Empaquetar: Error al crear el archivo temporal ", io);
                return null;
            }
            
            File fileBase = dire;
            if(dire.isFile())
            { fileBase = dire.getParentFile(); }
            int b = 0;
            try{
                b = addFile(aux, dire, fileBase);//fileBase es un directorio
            }
            catch(Exception ex)
            {
                GestorMensajes.getEx().EnviarMensaje("Exception inesperada: Llamada a addFile: ", ex);
                return null;
            }
            try {
                aux.close();
            } catch (Exception exception) {}
            
            if(b > 0)
            { return f; }
            else
            { return null; }
        }
        
        return null;
    }
    
    /**
     * Coloca los bytes de los archivos de base en aux. se llama recurcivamente
     * long fin del archivo
     * phat name
     * contenido
     */
    private int addFile(RandomAccessFile aux, File dire, File fileBase)
    {
        //pregunta si debe seguir o cortar
            if(hilo.isMorir() == true)//if(this.morir == true)
            { return Info.CancelInt; }
        
        int info = 0;
        if(dire != null && dire.isDirectory())
        {
            File[] v = dire.listFiles();
            
            if(v != null)
            {
                File[] restantesDires = new File[v.length];//para los directorios
                
                for(int i = 0, rdInd = 0; i < v.length; i++)
                {
                    try{
                        if(v[i].isFile())
                        {
                            long inicio = aux.length();
                            aux.seek(inicio + 8);//#$ salta el espacio de un long reservado para el fin del tamaño
                            //otra opci'on es escrivir un long
                            //aux.seek(inicio);
                            //aux.writeLong(0);
                            
                            //ahora gravo la dirección relativa a dire
                            String a = "";
                            //a = v[i].getAbsolutePath().replaceFirst(fileBase.getAbsolutePath(), "");
                            a = v[i].getAbsolutePath();
                            a = a.substring(fileBase.getAbsolutePath().length());
                            
                            aux.writeBytes(a + '\r' + '\n');//a + fin de l'inea
                            
                            //grabo el contenido del archivo
                            RandomAccessFile rr = new RandomAccessFile(v[i], "rw");
                            
                            try{
                                rr.seek(0);
                                while(true)
                                {
                                    aux.writeByte(rr.readByte());
                                }
                            }catch(EOFException eof)
                            {}//fin del archivo
                            
                            
                            //vuelvo al inicio y grabo donde termina el archivo
                            long fin = aux.getFilePointer();
                            aux.seek(inicio);
                            aux.writeLong(fin);
                            aux.seek(fin);
                            
                            try{
                            rr.close();
                            }catch(IOException ioex){}
                            
                        }
                        else
                        {
                            if(v[i].isDirectory())
                            {
                                restantesDires[rdInd] = v[i];//guardo el directorio
                                rdInd++;
                            }
                        }
                    }
                    catch(IOException io)
                    {
                        GestorMensajes.getEx().EnviarMensaje("addFile: Error dentro del ciclo for: ", io);
                    }
                    
                    //pregunta si debe seguir o cortar
                    if(hilo.isMorir() == true)//if(this.morir == true)
                    { return Info.CancelInt; }
                }//for
                
                v = null;
                //llamo recurcivamente para el tratamiento de los directorios
                for(int i = 0; i < restantesDires.length; i++)
                {
                    //pregunta si debe seguir o cortar
                    if(hilo.isMorir() == true)//if(this.morir == true)
                    { return Info.CancelInt; }
                    
                    if(restantesDires[i] != null)
                    {
                        //llamada recurciva
                        info = addFile(aux, restantesDires[i], fileBase);
                        if(info <= 0)
                        {
                            GestorMensajes.getEx().EnviarMensaje("addFile: Error : " + info);
                            return info;
                        }
                    }
                }
            }
            
            info = Info.OKCreadoInt;
        }//if(dire.isDirectory())
        else
        {
            if(dire != null && dire.isFile())
            {
                try{
                    long inicio = aux.length();
                    aux.seek(inicio + 8);//#$ salta el espacio de un long reservado para el fin del tamaño
                    //otra opci'on es escrivir un long
                    //aux.seek(inicio);
                    //aux.writeLong(0);
                    
                    //ahora gravo la dirección relativa a dire
                    String a = "";
                    //a = dire.getAbsolutePath().replaceFirst(fileBase.getAbsolutePath(), "");
                    a = dire.getAbsolutePath();
                    a = a.substring(fileBase.getAbsolutePath().length());
                    
                    aux.writeBytes(a + '\r' + '\n');//a + fin de l'inea
                    
                    //grabo el contenido del archivo
                    RandomAccessFile rr = new RandomAccessFile(dire, "rw");
                    
                    try{
                        rr.seek(0);
                        while(true)
                        {
                            aux.writeByte(rr.readByte());
                        }
                    }catch(EOFException eof)
                    {}//fin del archivo
                    
                    //vuelvo al inicio y grabo donde termina el archivo
                    long fin = aux.getFilePointer();
                    aux.seek(inicio);
                    aux.writeLong(fin);
                    aux.seek(fin);
                    
                    try{
                    rr.close();
                    }catch(IOException ioex){}
                    info = Info.OKCreadoInt;
                }
                catch(IOException io)
                {
                    GestorMensajes.getEx().EnviarMensaje("addFile: Error al empaquetar un solo archivo: ", io);
                    return Info.ERRORInt;
                }
            }//if(dire != null && dire.isFile())
            
        }
        return info;
    }
    
    /**
     * Desempaqueta un archivo y crea los correspondientes archivos desde la direcci'on base sobreescribiendo
     * Sin preguntar.
     *  Asume que direBase es un directorio que si no existe lo crea.
     * long fin del archivo
     * phat name
     * contenido
     */
    public int Desempaquetar(File paquete, File direBase)
    {
        //pregunta si debe seguir o cortar
            if(hilo.isMorir() == true)//if(this.morir == true)
            { return Info.CancelInt; }
            
        if(paquete != null && paquete.isFile() && direBase != null)
        {
            direBase.mkdirs();//crea el 'arbol de directorios
            
            RandomAccessFile paq = null;
            try{
                paq = new RandomAccessFile(paquete, "rw");
            }
            catch(IOException io)
            {
                GestorMensajes.getEx().EnviarMensaje("Desempaquetar: Error al crear un RandomAccessFile ", io);
                return Info.ERRORInt;
            }
            
            while(true)//podria haber puesto (paq.getFilePointer() < paq.length())
            {
                try{
                    //EOFException por fin de archivo y termina el ciclo
                    long fin = paq.readLong();//lee donde termina el archivo
                    
                    File nombre = new File(direBase.getAbsolutePath() + paq.readLine());//arma el nombre
                    //this.crearDirectorios(nombre);//crea el 'arbol de directorios
                    nombre.getParentFile().mkdirs();//sube un nivel y crea el 'arbol de directorios
                    
                    try{
                        //creo el nuevo archivo
                        RandomAccessFile rr = new RandomAccessFile(nombre, "rw");
                        rr.setLength(0);//corto el archivo
                        
                        while(paq.getFilePointer() < fin)
                        {
                            rr.writeByte(paq.readByte());
                        }
                        
                        rr.close();
                    }
                    catch(IOException io2)
                    {
                        //error con el archivo pero intento con el resto
                        paq.seek(fin);//me muevo al final del archivo empaquetado en paq
                    }
                    nombre = null;
                }
                catch(EOFException eof)
                {
                    //fin del archivo paq
                    break;
                }
                catch(IOException io)
                {
                    GestorMensajes.getEx().EnviarMensaje("Desempaquetar: Dentro del for ", io);
                    try{
                        paq.close();
                    }catch(IOException ioex){}
                    return Info.ERRORInt;
                }
                
                //pregunta si debe seguir o cortar
                if(hilo.isMorir() == true)//if(this.morir == true)
                { return Info.CancelInt; }
            }//while
            
            try{
                paq.close();
            }catch(IOException ioex){}
            return Info.OKCreadoInt;
        }
        
        return Info.ErrorNullInt;
    }
}
