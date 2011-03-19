/*
 * GrafoIO.java
 *
 * Provee leer() e imprimir() para objetos del tipo "Grafo".
 *
 * La lectura se hace linea a linea, de un "archivo" con el siguiente
 * formato:
 *
 *    nombre_nodo [ = id_numerico] : [lista_de_adyacentes separados por espacios]
 *
 * Los elementos de la lista de adyacentes pueden llevar un costo, a continuacion
 * del signo =.
 *
 * Ejemplos:
 *    A = 0 : 1 5 2
 *    # El nodo B no tiene adyacentes.
 *    B = 1 :
 *    # C no tiene id_num explicito, por lo que se usa uno mas que el previo.
 *    # C tambien referencia un nodo aun no procesado (4).
 *    C : 0 4 1
 *
 * Las lineas que inician con # se saltan (se consideran comentarios).
 * Tambien se saltan las lineas en blanco.
 *
 * (c) Juan Segovia Silvero.
 */


import java.io.*;
import java.util.*;

public class GrafoIO
{
   public static int leer (Grafo G, InputStream fp) throws Exception
   /*
    * nombre_nodo: id_numerico: lista de id de adyacentes, separados por " "
    *
    * Retorna la cantidad de vertices leidos. (Nodo==vertice).
    *
    * OJO: Sea que se lean o no con exito lineas, se hace G.init().
    *
    * XXX ESTE CODIGO ES UN POCO FEO.
    */
   {
      BufferedReader in = new BufferedReader (new InputStreamReader (fp));
      String linea = "";
      String nombre_nodo;
      int num_nodo;
      int vecino;
      int p, q, w;
      int cant = 0;
      double costo;

      /*
       * OJO: Se destruye el contenido actual de G.
       */
      G.init();
      while ( true ) {
         linea = in.readLine();
         if ( linea == null )
            break;
         /*
          * Ignorar las lineas en blanco y las que inicien con #.
          */
         if ( linea.length() == 0 || linea.charAt(0) == '#' )
              continue;

         num_nodo = 0;
         p = linea.indexOf (':');
         if ( p < 0 )
            continue;

         /*
          * El numero de nodo es opcional. Si existe, debe aparecer luego del
          * signo = y antes del signo :
          */
         nombre_nodo = linea.substring (0, p).trim();
         q = nombre_nodo.indexOf ('=');
         if ( q > 0 ) {
            num_nodo = Integer.parseInt (nombre_nodo.substring (q+1).trim());
            nombre_nodo = nombre_nodo.substring (0, q).trim();
         }
         if ( num_nodo == 0 )
            num_nodo = cant;

         ++cant;

         /*
          * Hagamos que siempre haya separador para facilitar lo que sigue.
          */
         linea = linea + " ";

         while ( p > 0 ) {
            ++p;
            while ( p < linea.length() && linea.charAt (p) == ' ' )
              ++p;
            q = linea.indexOf (' ', p);
            if ( q < 0 )
               break;

            costo = 0.0;
            /*
             * Ver si hay costo. Debe aparecer a la derecha de p, pero
             * antes de q, que es el espacio.
             */
            w = linea.indexOf ('=', p);
            if ( w > 0 && w < q ) {
               try {
                  costo  = Double.parseDouble (linea.substring (w+1, q).trim());
               } catch (NumberFormatException e) {
                  System.err.print ("\nEn la linea:\n  " + linea
                                    + "\nel costo es erroneo: "
                                    + linea.substring (w+1, q).trim());
               }
               vecino = Integer.parseInt (linea.substring (p, w).trim());
            } else {
               vecino = Integer.parseInt (linea.substring (p, q).trim());
            }
            G.unir (num_nodo, vecino, costo);
            p = q;
         }
         /*
          * Si no tenia lista de adyacencias, nunca se llamo a unir() y
          * por lo tanto el nodo no esta creado.
          */
         if ( G.cantVertices() <= num_nodo )
            G.agregarVert (nombre_nodo, num_nodo);
         else
            G.vertice (num_nodo).nombrar (nombre_nodo);
      }
      return cant;
   }

  
    public static void imprimir (Grafo G) {
	
        for (int k=0; k < G.cantVertices(); k++) {
            Vertice v = G.vertice (k);
            ListIterator AdyItr =  v.lista_ady.listIterator(0);
            Arista a=null;

            if ( v == null ) {
                continue;
            }
			
            System.out.print ("\n" + v + " [" + k + "]:");			
			
            while ( AdyItr.hasNext() ) {
                a = (Arista) AdyItr.next();
				
                if ( a != null ) {
                    System.out.print (" " + a.to + " (" + a + ")");
                }
            }		
			
        }
    }
}
