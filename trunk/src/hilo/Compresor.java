package hilo;

import java.io.*;
import principal.*;

/**
 *
 * @author Administrador
 */
public class Compresor
{
    private EstadosHilo estadoHilo = null;
    private static final String ext = ".cmp";  // extencion
    
    public Compresor()
    {
        this.estadoHilo = new EstadosHilo();;
    }
    
    public Compresor(EstadosHilo hilo)
    {
        this.estadoHilo = hilo;
    }
    
    /**
     *  Comprime un archivo usando un Arbol de Huffman para determinar el codigo
     *  de bit de cada signo. Genera un archivo comprimido con el mismo nombre
     *  que el original, pero con extension ".cmp".
     *  Retorna el archivo comprimido
     *  @param fileName el archivo a comprimir
     */
    public File comprimir (String fileName)
    {
        System.out.println("Estoy por comprimir ^^");
        try
        {  
            //obtengo el nombre del archivo, sin la extensi'n
            String nombre = fileName.substring( 0, fileName.indexOf(".") );
            
            // abro los archivos
            File f2Comprimido = new File(nombre + Compresor.ext);
            RandomAccessFile fuente = new RandomAccessFile (fileName, "r");
            
            // cuento cu'ntas veces aparece cada byte en el archivo
            int i;
            byte car;

            int c[] = new int[256];  // un vector de contadores
            for(i=0; i<256; i++) { c[i] = 0; }
            
            // contamos los signos...
            while(fuente.getFilePointer() < fuente.length())
            {
                 car = fuente.readByte();            // leo un byte del archivo...
                 short sc = (short) (car & 0x00FF);  // ... lo convierto a short para evitar problemas de desborde 
                 c[ sc ]++;   // cuento ese byte!!!
            }
                       
            // cuento cu'ntos signos diferentes hay...
            int cantSignos = 0;
            for(i = 0; i < 256; i++) 
            { 
                if( c[i] != 0 ) { cantSignos++; }
            }
            
            //pregunta si debe seguir o cortar
            if(estadoHilo.isTerminado() == true)
            {return null;}
            
            // creamos el Arbol con lugar para esa cantidad de signos
            ArbolHuffman ht = new ArbolHuffman(cantSignos); 
            
            // inicializamos el arbol de Huffman con los signos y sus frecuencias
            int ind = 0;
            for(i = 0;  i < 256;  i++)
            {
                  if( c[i] != 0 )
                  {
                      ht.setNodo((byte)i , c[i], ind);
                      ind++;
                  }
            }
            
            // armamos el 'rbol y obtenenos el c'digo Huffman de cada signo
            ht.codificar();
            
            //pregunta si debe seguir o cortar
            if(estadoHilo.isTerminado()) // verifico si devuelve TRUE
            {
                fuente.close();
                fuente = null;
                return null;
            }
            
            // cantidad de bytes del archivo fuente
            long tArch = fuente.length();  
            
            // guardo en el archivo comprimido informaci'n para el descompresor...
            RandomAccessFile comprimido = new RandomAccessFile (f2Comprimido, "rw");
            
            // ...empiezo guardando el nombre y la extensi'n del original...
            comprimido.setLength(0);
            comprimido.writeUTF(fileName);
            
            // ... luego guardo la longitud en bytes del archivo original...
            comprimido.writeLong(tArch);
            
            // ... la cantidad de s'mbolos (o sea, la cantidad de hojas del 'rbol)...
            comprimido.writeInt(cantSignos);
            
            // ... ahora la tabla de s'mbolos tal como est' en el arbol...
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = ht.getSigno(i);
                comprimido.writeByte(signo);
            }

            // ... ahora el vector que representa al 'rbol...
            NodoHuffman a[] = ht.getArbol();
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del 'rbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, guardar todos sus datos...
                comprimido.writeInt( a[i].getFrecuencia() );
                comprimido.writeInt( a[i].getPadre() );
                comprimido.writeBoolean( a[i].isLeft() );
                comprimido.writeInt( a[i].getIzquierdo());
                comprimido.writeInt( a[i].getDerecho());
            }
            
            //pregunta si debe seguir o cortar
            if(estadoHilo.isTerminado())
            {
                comprimido.close();
                comprimido = null;
                fuente.close();
                fuente = null;
                return null;
            }
            
            // comienza fase de compresión (por fin...)
            short mascara = 0x0080;  // el valor 0000 0000 1000 0000
            short salida  = 0x0000;  // el valor 0000 0000 0000 0000
            int bit = 0;             // en qu' bit vamos?           
            
            fuente.seek(0);   // vuelvo el fp al principio
            while(fuente.getFilePointer() < fuente.length())
            {
                car = fuente.readByte();
                
                // codigo Huffman del caracter tomado
                CodigoHuffman hc = ht.getCodigo(car);
                byte []v = hc.getCodigo();
                int  ini = hc.getStartPos();

                for(i = ini; i < CodigoHuffman.MAXBITS; i++)
                {
                    if(v[i] == 1)
                    {
                        // si era 1, lo bajamos al byte de salida (si era cero, ni modo...)
                        salida = (short)(salida | mascara);
                    }   
                    mascara = (short) (mascara >>> 1);  // corremos el uno a la derecha, rellenando con ceros a la izquierda...
                    bit++;
                    if (bit == 8)
                    {
                        //se llena el byte de salida...
                        comprimido.writeByte( (byte)salida ); // graba el menos significativo!!! 
                        bit = 0;
                        mascara = 0x0080;
                        salida  = 0x0000;
                    }
                }
                
                //pregunta si debe seguir o cortar
                if(estadoHilo.isTerminado())
                {
                    comprimido.close();
                    comprimido = null;
                    fuente.close();
                    fuente = null;
                    return null;
                }
            } // Fin del while...

            if (bit != 0) 
            {
                // grabar el 'ltimo byte que estaba incompleto
                comprimido.writeByte( (byte)salida );  // graba el menos significativo!!!
            }
            comprimido.close();
            comprimido = null;
            fuente.close();
            fuente = null;
            
            return f2Comprimido;
        }
        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return null;
    }

    
    /**
     *  Descomprime
     *  Retorna el archivo descomprimido
     *  @param fileName el archivo a descomprimir
     */
    public File descomprimir(String fileName)
    {
        try
        {
            //int pos = fileName.getAbsolutePath().indexOf(".");
            //if(pos == -1)
            //{ return null;//throw new Exception ("El archivo no parece un archivo comprimido...");
            //}
            //String ext = fileName.substring( pos + 1 );
            String auxext = fileName.substring(fileName.length() - 4, fileName.length());
            //if( ext.compareTo("cmp") != 0 )
            if(false == auxext.equalsIgnoreCase(Compresor.ext))
            { return null;// throw new Exception ("El archivo no tiene la extensi'n "+ Comp.ext +"...");
            }
            
            // abro el archivo comprimido...
            //File f1 = new File( fileName );
            RandomAccessFile comprimido = new RandomAccessFile(fileName, "r");    
            
            // ... y recupero el nombre del archivo original
            String original = comprimido.readUTF();
            
            // creo el archivo con el nombre del original
            File f2Nuevo = new File(original);
            if( f2Nuevo.exists() ) { f2Nuevo.delete(); }
            RandomAccessFile nuevo = new RandomAccessFile(f2Nuevo, "rw");
            nuevo.setLength(0);
            
            //pregunta si debe seguir o cortar
               if(estadoHilo.isTerminado() == true)
                {
                    comprimido.close();
                    comprimido = null;
                    nuevo.close();
                    nuevo = null;
                    return null;
                }
            
            // y ahora, recupero todos los datos que el compresor dej' adelante...
            
            // ... empezando por el tama'o del archivo original...
            long tArch = comprimido.readLong();
            
            // ... la cantidad de signos de la tabla (o sea, la cantidad de hojas)...
            int cantSignos = comprimido.readInt();
            
            // ...creo de nuevo el 'rbol en memoria...
            ArbolHuffman ht = new ArbolHuffman(cantSignos);
            
            // ... y recupero uno a uno los signos originales, guard'ndolos de nuevo en el 'rbol...
            int i;
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = comprimido.readByte();
                ht.setSigno(signo, i);
            }
            
            // ...ahora le toca al vector del 'rbol...
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del 'rbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, recuperar todos sus datos y volver a armar el 'rbol...
                int f  = comprimido.readInt();           // frecuencia
                int padre = comprimido.readInt();        // padre
                boolean left = comprimido.readBoolean(); // es izquierdo?
                int hi = comprimido.readInt();           // hijo izquierdo
                int hd = comprimido.readInt();           // hijo derecho
                NodoHuffman nh = new NodoHuffman( f, padre, left, hi, hd );
                ht.setNodo( nh, i );
            }
            
            // y habiendo llegado ac', el descompresor vuelve a pedir que se creen los c'digos de Huffman
            ht.obtenerCodigos();
                       
            // de ac' saco el vector que representa al 'rbol y el 'ndice de la raiz...
            NodoHuffman []v2 = ht.getArbol();
            int raiz =  v2.length - 1;  // la raiz est' en la 'ltima casilla del vector!!!!
            
            // comienza la fase de descompresi'n
            short aux;                     // auxiliar para desenmascarar
            short mascara;
            int bit, nodo = raiz;          // comenzamos desde la raiz y vamos bajando
            long cantBytes = 0;            // cu'ntos bytes llevo grabados??
            
            //pregunta si debe seguir o cortar
            if(estadoHilo.isTerminado())
                {
                    comprimido.close();
                    comprimido = null;
                    nuevo.close();
                    nuevo = null;
                    return null;
                }
            
            
            // leo byte por byte el archivo comprimido
            while(comprimido.getFilePointer() < comprimido.length())
            {
                byte  car = comprimido.readByte();
                short sCar = (short) (car & 0x00FF);  // guardo el byte en un short, pero con todo el primer byte en cero 
                mascara = 0x0080;
                for(bit = 0; bit < 8 && cantBytes != tArch; bit++)
                {
                    aux = (short)(sCar & mascara);
                    if(aux == mascara)
                    {
                        // el bit en la posici'n "bit" era un uno...
                        nodo = v2[nodo].getDerecho();    
                    }
                    else 
                    {
                        // era un cero...
                        nodo = v2[nodo].getIzquierdo();
                    }
                    mascara = (short)(mascara >>> 1);  // corremos el 1 a la derecha y rellenamos con ceros a la izquierda...

                    if (v2[nodo].getIzquierdo() == -1 && v2[nodo].getDerecho() == -1)
                    {
                        // llegamos a una hoja... grabar el signo que est' en ella
                        byte sal = ht.getSigno(nodo);
                        nuevo.writeByte(sal);
                        cantBytes++;

                        // volver a la raiz
                        nodo = raiz;
                    }
                }
                
                //pregunta si debe seguir o cortar
                if(estadoHilo.isTerminado() == true)
                {
                    comprimido.close();
                    comprimido = null;
                    nuevo.close();
                    nuevo = null;
                    return null;
                }
            } // while
            nuevo.close();
            nuevo = null;
            comprimido.close();
            comprimido = null;
            
            return f2Nuevo;
        }
        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return null;
    }
    
    public static String getExtencion()
    {
        return ext;
    }
    
    public EstadosHilo getEstadosHilo()
    {
        return this.estadoHilo;
    }
}
