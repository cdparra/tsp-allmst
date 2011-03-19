/*
 * GrafoNoDirigido.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 *
 * Fecha: 07 - 05 - 2005
 *
 * Esta clase implementa un grafo no dirigido de una manera muy simple
 * y ahorrativa en código. 
 *
 * Hacemos que la clase herede de la clase Grafo.java y solo 
 * reescribimos el método "unir", haciendo que cuando una arista se 
 * a un vertice dado, se una también al vertice destino.
 *
 * En realidad, es en esencia un grafo dirigido con enlaces dobles. 
 * No me parece que sea una manera adecuada de implementarlo, pero
 * como el tiempo apremia, nos permitimos esta implementación.
 *
 */

import java.util.*;

public class GrafoNoDirigido extends Grafo {
    
	
    public Grafo clonar() {
        Grafo G = new GrafoNoDirigido();
        G.lista_vert = (Vector)lista_vert.clone();
       	G.numAristas = numAristas;
        return G;
    }	
    /*
     * Version simplifada de unir(from, to, Arista a) para el caso en
     * que solo se tiene costo como atributo, que suele ser el caso.
     */
    public int unir (int from, int to, double costo) {
        unir (from, to, new Arista (from, to, costo));
        unir (to,from,new Arista (to,from,costo));
        return ++numAristas;
    }

    /*
     * Remueve la arista que une a los verticces (from, to).
     * Lo hace asignando null en la posicion apropiada de la lista
     * de adyacencias del nodo from.
     */
    public void separar (int from, int to) {
        if ( cantVertices() > from ) {
            vertice (from).separar(to);
            vertice (to).separar(from);
        }
    }
}
