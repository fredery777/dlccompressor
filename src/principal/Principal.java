/*
 * Principal.java
 *
 * Created on 5 de mayo de 2008, 11:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package principal;

/**
 * Contiene el main para testear el arbol de Huffman.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2007
 */
public class Principal
{
    public static void main (String args[])
    {
             Compresor compresor = new Compresor();
             int op;
      
             System.out.println("Prueba de Compresión y Descompresión");
             
             do
             {
                 System.out.println("1. Fase de compresión...");
                 System.out.println("2. Fase de descompresión...");
                 System.out.println("3. Salir");
                 System.out.print("\nIngrese opción: ");
                 op = Consola.readInt();
                 
                 switch(op)
                 {
                     case 1: compresor.comprimir("pacman.c");
                             System.out.println("Hecho...");
                             break;
                             
                     case 2: compresor.descomprimir("pacman.cmp");
                             System.out.println("Hecho...");
                             break;
                             
                     case 3: ;
                 }
             }
             while(op != 3);
    }
}
