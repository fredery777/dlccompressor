/*
 * Nodo.java
 *
 * Created on 5 de mayo de 2008, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package principal;
/**
 *  Clase para representar un nodo genérico para una lista genérica.
 *  @author Ing. Valerio Frittelli
 *  @version Abril de 2004
 */
public class Nodo
{
   private Comparable info;
   private Nodo next;
   
   /**
    *  Constructor por defecto. 
    */
   public Nodo ( )
   {
   }
   
   /**
    *  Crea un nodo incializando todos los atributos en base a parámetros 
    */
   public Nodo (Comparable x, Nodo p)
   {
     info = x;
     next = p;
   }
   
   /**
    *  Accede a la dirección del sucesor
    *  @return la dirección del nodo sucesor
    */
   public Nodo getNext()
   {
     return next;
   }
   
   /**
    * Cambia la dirección del sucesor
    * @param p dirección del nuevo sucesor
    */
   public void setNext(Nodo p)
   {
     next = p;
   }
   
   /**
    *  Accede al valor del info
    *  @return el valor del info
    */
   public Comparable getInfo()
   {
     return info;
   }
   
   /**
    * Cambia el valor del info
    * @param p nuevo valor del info
    */
   public void setInfo(Comparable p)
   {
     info = p;
   }

   /**
    * Convierte el contenido del nodo en String
    * @return el contenido del nodo convertido en String
    */
   public String toString()
   {
     return info.toString();   
   }
}
