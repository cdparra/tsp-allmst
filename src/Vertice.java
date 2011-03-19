/* 
 * Vertice.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 *
 * Fecha: 07 - 05 - 2005
 * 
 * -- NOTA -- Sencillamente uso la implementación proveída por el 
 *            Profesor con algunas modificaciones. Las Moficaciones 
 *            se indican con algún comentario.
 *
 * -- Desde aquí comentarios del Profesor
 * Vertice de un Grafo.
 * Cada vertice tiene un nombre (id), un indicador booleano de 
 * visitado y una lista de vertices adyacentes, implementada a 
 * traves de un Vector.
 *
 * Los adyacentes que se almacenan en el objeto Vector son indices 
 * y no punteros.
 *
 * Notese que a proposito no se proveen demasiados servicios aqui, 
 * enfantizando en cambio los servicios de la clase Grafo.
 * 
 * El agregado con respecto al original Vertice.java es tan solo 
 * los datos del ultimo vertice en el camino minimo
 * desde un inicio a este vertice y el peso de dicho
 * camino minimo.
 */

import java.util.*;

public class Vertice implements Cmp{

    public String id;
    public LinkedList lista_ady;        // Originalmente era un Vector
    public boolean visitado;
	
    /* 
     * Los siguientes son atributos auxiliares para el càlculo de
     * caminos Mímimos. 
     * 
     */
    public double distMin = Double.POSITIVE_INFINITY;  // inicialmente un valor muy grande
    public int ultimoCaminoMin = -1;                   // id del ultimo vertice en el camino min.


    public Vertice (String id) {
        nombrar (id);
        lista_ady = new LinkedList();
        visitado = false;
    }

   /*
    * Pone "a" como dato en la lista de adyacendias de este vértice. 
    * En el fuente original esto se insertaba en la k-ésima posición 
    * de un vector puesto que se utilizaba una matriz de adyacencias.
    */   
    public void unir ( Arista a ) {
        ListIterator Litr = lista_ady.listIterator(0);
        int posInsercion = 0;
				
        while (Litr.hasNext()) {
            Arista ari = (Arista) Litr.next();
			
            if (a.to < ari.to) {
                break;
            }		  
            posInsercion++;			
        }
		
        lista_ady.add(posInsercion,a);
    }
	
   /*
    * Elimina la Arista que cuyo destino es to
    */   
    public void separar ( int to) {
	    ListIterator Litr = lista_ady.listIterator(0);
		
        while (Litr.hasNext()) {
            Arista a = (Arista) Litr.next();
			
            if (a.to == to) {
                Litr.remove();
                break;
            }		    
        }
    }

    /* Retorna la arista que une este vertice con "to" si existe 
	 * entre sus adyacentes, o null si no existe.
	 */
    public Arista adyacente(int to) {
        ListIterator AdyItr =  lista_ady.listIterator(0);
        Arista a=null;
			
        while ( AdyItr.hasNext() ) {
            a = (Arista) AdyItr.next();
            if (a.to == to){
                return a;
            }
        }		
	
	    return null;
    }

    public void nombrar (String id) { 
        this.id = id; 
	}
   
    public String nombre() { 
        return id; 
	}

    public int cantVecinos() { 
	    return lista_ady.size(); 
	}

    public String toString() {
        return id;
    }
			
	public int comparar (Cmp v) {
	    Vertice vert = (Vertice) v;
		return Double.compare(this.distMin,vert.distMin);
	}
}
