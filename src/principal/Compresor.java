package principal;

import hilo.*;
import java.io.*;

/**
 * Un compresor de archivos basado en un Arbol de Huffman.
 * @author Ing. Valerio Fritelli
 * @author Morales, Gustavo - Roldán, Marco - Senn, Analía
 * @version Junio de 2008
 */
public class Compresor
{
    private ArbolHuffman ht;
    private RandomAccessFile fuente;
    private RandomAccessFile comprimido;
    private RandomAccessFile nuevo;
    private int cantSignos;
    private boolean termino;
    
    private EstadosHilo estadoHilo = null;
    
    public Compresor()
    {
        estadoHilo = new EstadosHilo();
    }
    
    public Compresor(EstadosHilo eHilo)
    {
        estadoHilo = eHilo;
    }
    
    /**
     *  Comprime un archivo usando un Arbol de Huffman para determinar el codigo
     *  de bit de cada signo. Genera un archivo comprimido con el mismo nombre
     *  que el original, pero con extension ".huffman".
     *  Retorna el archivo comprimido
     *  @param fileName el archivo a comprimir
     */
    public String comprimir(String fileName)
    {
        try
        {  
            //obtengo el nombre del archivo, sin la extensión
            String nombre = fileName.substring( 0, fileName.indexOf(".") );
            
            // abro los archivos
            File f1 = new File(fileName);
            File f2 = new File(nombre + ".huffman");
            
            fuente = new RandomAccessFile (f1, "r");
            comprimido = new RandomAccessFile (f2, "rw");
            
            // cuento cuántas veces aparece cada byte en el archivo
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
                       
            // cuento cuántos signos diferentes hay...
            cantSignos = 0;
            for(i = 0; i < 256; i++) 
            { 
                if( c[i] != 0 ) { cantSignos++; }
            }
            
            // El hilo sigue vivo?
            if(estadoHilo.isTerminado())
            {
                fuente.close();
                return fileName;
            }
            
            // creamos el Arbol con lugar para esa cantidad de signos
            ht = new ArbolHuffman(cantSignos); 
            
            // inicializamos el árbol de Huffman con los signos y sus frecuencias
            int ind = 0;
            for(i = 0;  i < 256;  i++)
            {
                  if( c[i] != 0 )
                  {
                      ht.setNodo((byte)i , c[i], ind);
                      ind++;
                  }
            }
            
            // armamos el árbol y obtenenos el código Huffman de cada signo
            ht.codificar();
            
            // El hilo sigue vivo?
            if(estadoHilo.isTerminado())
            {
                fuente.close();
                return fileName;
            }
            
            // cantidad de bytes del archivo fuente
            long tArch = fuente.length();  
            
            // guardo en el archivo comprimido información para el descompresor...
            
            // ...empiezo guardando el nombre y la extensi'n del original...
            //comprimido.setLength(0);
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

            // ... ahora el vector que representa al árbol...
            NodoHuffman a[] = ht.getArbol();
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del árbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, guardar todos sus datos...
                comprimido.writeInt( a[i].getFrecuencia() );
                comprimido.writeInt( a[i].getPadre() );
                comprimido.writeBoolean( a[i].isLeft() );
                comprimido.writeInt( a[i].getIzquierdo());
                comprimido.writeInt( a[i].getDerecho());
            }
            
            // El hilo sigue vivo?
            if(estadoHilo.isTerminado())
            {
                comprimido.close();
                fuente.close();
                return fileName;
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
                
                // El hilo sigue vivo?
                if(estadoHilo.isTerminado())
                {
                    comprimido.close();
                    fuente.close();
                    return fileName;
                }
            }

            if (bit != 0) 
            {
                // grabar el último byte que estaba incompleto
                comprimido.writeByte( (byte)salida );  // graba el menos significativo!!!
            }
            comprimido.close();
            fuente.close();
            
            return nombre;
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
     *  Descomprime un archivo usando un Arbol de Huffman para determinar el
     *  codigo de bit de cada signo. Genera el archivo original.
     *  Retorna el archivo descomprimido
     *  @param fileName el archivo a descomprimir
     */
    public String descomprimir(String fileName)
    {
        try
        {
            int pos = fileName.indexOf(".");
            if(pos == -1) return "El archivo no parece un archivo comprimido...";
            
            String ext = fileName.substring( pos + 1 );
            //String auxext = fileName.substring(fileName.length()-4, fileName.length());
            if( ext.compareTo("huffman") != 0 ) return "El archivo no parece un archivo comprimido...";
            
            // abro el archivo comprimido...
            File f1 = new File(fileName);
            comprimido = new RandomAccessFile(f1, "r");    
            
            // ... y recupero el nombre del archivo original
            String original = comprimido.readUTF();
            
            // creo el archivo con el nombre del original
            File f2 = new File(original);
            if(f2.exists())
            { 
                f2.delete();  // agregar código y preguntar si borrar o no
            }
            nuevo = new RandomAccessFile(f2, "rw");
            
            // el hilo sigue vivo?
               if(estadoHilo.isTerminado())
                {
                    comprimido.close();
                    nuevo.close();
                    return fileName;
                }
            
            // y ahora, recupero todos los datos que el compresor dej' adelante...
            
            // ... empezando por el tamaño del archivo original...
            long tArch = comprimido.readLong();
            
            // ... la cantidad de signos de la tabla (o sea, la cantidad de hojas)...
            cantSignos = comprimido.readInt();
            
            // ...creo de nuevo el árbol en memoria...
            ht = new ArbolHuffman(cantSignos);
            
            // ... y recupero uno a uno los signos originales, guardándolos de nuevo en el arbol...
            int i;
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = comprimido.readByte();
                ht.setSigno(signo, i);
            }
            
            // ...ahora le toca al vector del árbol...
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
            
            // y habiendo llegado acá, el descompresor vuelve a pedir que se creen los códigos de Huffman
            ht.obtenerCodigos();
                       
            // de acá saco el vector que representa al árbol y el índice de la raiz...
            NodoHuffman []v2 = ht.getArbol();
            int raiz =  v2.length - 1;  // la raiz esta en la última casilla del vector!!!!
            
            // comienza la fase de descompresión
            short aux;                     // auxiliar para desenmascarar
            short mascara;
            int bit, nodo = raiz;          // comenzamos desde la raiz y vamos bajando
            long cantBytes = 0;            // cu'ntos bytes llevo grabados??
            
            // el hilo sigue vivo?
            if(estadoHilo.isTerminado())
            {
                comprimido.close();
                nuevo.close();
                return fileName;
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
                        // el bit en la posición "bit" era un uno...
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
                        // llegamos a una hoja... grabar el signo que está en ella
                        byte sal = ht.getSigno(nodo);
                        nuevo.writeByte(sal);
                        cantBytes++;

                        // volver a la raiz
                        nodo = raiz;
                    }
                }
                
                // el hilo sigue vivo?
                if(estadoHilo.isTerminado())
                {
                    comprimido.close();
                    nuevo.close();
                    return fileName;
                }
            }
            nuevo.close();
            comprimido.close();
            return original;
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
}
