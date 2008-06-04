/*
 * Frecuencia.java
 *
 * Created on 5 de mayo de 2008, 11:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package principal;
/**
 * Clase para representar el info de la Cola de Prioridad.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 */
public class Frecuencia implements Comparable
{
    private int indice;      // indice del vector que representa al árbol donde está este valor
    private int frecuencia;  // frecuencia que tiene este signo

    /**
     *  Constructor por defecto. Coloca el índice en -1 y deja frecuencia en cero
     */
    public Frecuencia()
    {
       indice = -1;
    }
    
    /**
     *  Constructor. Coloca el índice en i y deja frecuencia en f
     */
    public Frecuencia (int f, int i)
    {
       indice = i;
       frecuencia = f;
    }

    /**
     *  Acceso al índice
     *  @return el valor del índice
     */
    public int getIndice() 
    {
        return indice;
    }
    
    /**
     *  Modifica el indice
     *  @param i el nuevo indice
     */
    public void setIndice(int i) 
    {
        indice = i;
    }

    /**
     *  Acceso a la frecuencia
     *  @return el valor de la frecuencia
     */
    public int  getFrecuencia() 
    { 
        return frecuencia; 
    }
    
    /**
     *  Modifica la frecuencia
     *  @param x la nueva frecuencia
     */
    public void setFrecuencia(int x) 
    { 
        frecuencia = x; 
    }

    /**
     *  Definición del método pedido por Comparable 
     *  @return Compara las frecuencias: retorna 0 si eran iguales, >0 si la primera era mayor, o <0 en caso contrario
     */
    public int compareTo(Object x)
    {
        Frecuencia p = (Frecuencia)x;
        return frecuencia - p.frecuencia;
    }
    
    /**
     *  Obtiene la representación como String del objeto
     *  @return Un string con la representación del objeto
     */
    public String toString()
    {
        return "Frecuencia: " + frecuencia + " Indice: " + indice;
    }
}
