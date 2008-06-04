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
                //si antes vino un '.' punto entonses me han pasado un archivo
                if(b == true) aux = aux.substring(0, i);
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
     * Retorna le archivo empaquetado
     */
    public File Empaquetar(File dire)
    {
        if(dire.isDirectory() || dire.isFile())
        {
            //pregunta si debe seguir o cortar
            if(hilo.isTerminado()) return null;
            
            File f = null;
            RandomAccessFile aux = null;
            try
            {
                f = File.createTempFile("temp", null, dire.getParentFile());
                f.deleteOnExit();
                aux = new RandomAccessFile(f, "rw");
            }
            catch(IOException e)
            {
                System.out.println("Empaquetar: Error al crear el archivo temporal " + e.getMessage());
                return null;
            }
            
            File fileBase = dire;
            if(dire.isFile()) fileBase = dire.getParentFile();
            int b = 0;
            try
            {
                b = addFile(aux, dire, fileBase);//fileBase es un directorio
            }
            catch(Exception e)
            {
                System.out.println("Exception inesperada" + e.getMessage());
                return null;
            }
            try 
            {
                aux.close();
            }
            catch (Exception e)
            {
                System.out.println("Error al cerrar el archivo" + e.getMessage());
            }
            if(b > 0) return f;
            else return null;
        }
        return null;
    }
    
    /**
     * Coloca los bytes de los archivos de base en aux. se llama recurcivamente
     * long fin del archivo
     * phat name
     * contenido
     */
    private int addFile(RandomAccessFile aux, File dire, File fileBase) // int antes
    {
        if(dire != null && dire.isDirectory())
        {
            File[] v = dire.listFiles();
            
            if(v != null)
            {
                File[] restantesDires = new File[v.length];//para los directorios
                
                for(int i = 0, rdInd = 0; i < v.length; i++)
                {
                    try
                    {
                        if(v[i].isFile())
                        {
                            long inicio = aux.length();
                            aux.seek(inicio + 8); // salta el espacio de un long reservado para el fin del tamaño
                            
                            //ahora grabo la dirección relativa a dire
                            String a = "";
                            //a = v[i].getAbsolutePath().replaceFirst(fileBase.getAbsolutePath(), "");
                            a = v[i].getAbsolutePath();
                            a = a.substring(fileBase.getAbsolutePath().length());
                            
                            aux.writeBytes(a + '\r' + '\n'); //a + fin de l'inea
                            
                            //grabo el contenido del archivo
                            RandomAccessFile rr = new RandomAccessFile(v[i], "rw");
                            
                            try
                            {
                                rr.seek(0);
                                while(true) aux.writeByte(rr.readByte());
                            }
                            catch(EOFException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                            }//fin del archivo
                            
                            
                            //vuelvo al inicio y grabo donde termina el archivo
                            long fin = aux.getFilePointer();
                            aux.seek(inicio);
                            aux.writeLong(fin);
                            aux.seek(fin);
                            
                            try
                            {
                                rr.close();
                            }
                            catch(IOException e)
                            {
                                System.out.println("Error: " + e.getMessage());
                            }
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
                    catch(IOException e)
                    {
                        System.out.println("Error en el ciclo FOR : " + e.getMessage());
                    }
                    
                    //pregunta si debe seguir o cortar
                    // if(hilo.isTerminado()) return Info.CancelInt; // lo mismo de arriba
                } 
                v = null;
                //llamo recurcivamente para el tratamiento de los directorios
            }
        } // if(dire.isDirectory())
        else
        {
            if(dire != null && dire.isFile())
            {
                try
                {
                    long inicio = aux.length();
                    aux.seek(inicio + 8);//#$ salta el espacio de un long reservado para el fin del tamaño
                    
                    //ahora gravo la dirección relativa a dire
                    String a = "";
                    a = dire.getAbsolutePath();
                    a = a.substring(fileBase.getAbsolutePath().length());
                    
                    aux.writeBytes(a + '\r' + '\n');//a + fin de l'inea
                    
                    //grabo el contenido del archivo
                    RandomAccessFile rr = new RandomAccessFile(dire, "rw");
                    
                    try
                    {
                        rr.seek(0);
                        while(true) aux.writeByte(rr.readByte());
                    }
                    catch(EOFException e)
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                    
                    // vuelvo al inicio y grabo donde termina el archivo
                    long fin = aux.getFilePointer();
                    aux.seek(inicio);
                    aux.writeLong(fin);
                    aux.seek(fin);
                    
                    try
                    {
                        rr.close();
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                catch(IOException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
            }//if(dire != null && dire.isFile())
        }
        return 1;
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
        // if(hilo.isTerminado()) return Info.CancelInt;
            
        if(paquete != null && paquete.isFile() && direBase != null)
        {
            direBase.mkdirs();//crea el 'arbol de directorios
            
            RandomAccessFile paq = null;
            try
            {
                paq = new RandomAccessFile(paquete, "rw");
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
            
            while(true)
            {
                try
                {
                    //EOFException por fin de archivo y termina el ciclo
                    long fin = paq.readLong();//lee donde termina el archivo
                    
                    File nombre = new File(direBase.getAbsolutePath() + paq.readLine());//arma el nombre
                    nombre.getParentFile().mkdirs();//sube un nivel y crea el 'arbol de directorios
                    
                    try
                    {
                        //creo el nuevo archivo
                        RandomAccessFile rr = new RandomAccessFile(nombre, "rw");
                        rr.setLength(0);//corto el archivo
                        
                        while(paq.getFilePointer() < fin) rr.writeByte(paq.readByte());
                        
                        rr.close();
                    }
                    catch(IOException e)
                    {
                        //error con el archivo pero intento con el resto
                        paq.seek(fin);
                        //me muevo al final del archivo empaquetado en paq
                        System.out.println("Error: " + e.getMessage());
                    }
                    nombre = null;
                }
                catch(EOFException e)
                {
                    System.out.println("Error: " + e.getMessage());
                    //fin del archivo paq
                    break;
                }
                catch(IOException e)
                {
                    System.out.println("Desempaquetar: Dentro del FOR " + e.getMessage());
                    try
                    {
                        paq.close();
                    }
                    catch(IOException ee)
                    {
                        System.out.println("Error: " + ee.getMessage());
                    }
                }
                
                //pregunta si debe seguir o cortar
                // if(hilo.isTerminado Morir() == true) return Info.CancelInt;
            }//while
            
            try
            {
                paq.close();
            }
            catch(IOException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return 1;
    }
    
}
