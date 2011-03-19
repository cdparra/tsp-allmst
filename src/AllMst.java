/*
 * AllMst.java
 *
 * Trabajo Pr�ctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 * C.I.: 2.045.856
 *
 * Fecha: 12 - 06 - 2005 
 *
 * AllMst.java contiene los m�todos que resuelven
 * el problema de imprimir "todos" los �rboles de 
 * recubrimiento de costo m�nimo
 *
 *   - AllMSTPrim ---> Realiza el algoritmo de Prim en cada v�rtice
 *                     produciendo para cada uno un MST que se guarda
 *                     en una lista enlazada si y solo si no existe
 *                     en la misma.
 *
 */

import java.util.*;

public class AllMst {

    MST arbol;                 // El MST generado en cada iteraci�n
    public LinkedList bosque;  // bosque de �rboles de recubrimiento m�nimo
   
    public AllMst ( Grafo G ) {
        arbol = new MST(G);
        bosque = new LinkedList();	    
    }
    
	/*
	 * AllMSTPrim es un algoritmo de fuerza bruta que genera todos
	 * los �rboles de recubrimiento de costo m�nimo en un grafo dado.
	 */
    public void AllMSTPrim () {
        
        for (int i = 0 ; i < arbol.cantVert ; i++ ) {
            arbol.MSTPrim(i);
            if (!yaExisteElArbol(arbol.padres)) {
                bosque.add(arbol);
                arbol.imprimir_MST_Prim();
            }            
            MST arbolAuxiliar = new MST(arbol.G);
            arbol = arbolAuxiliar;
        }
    }

    /*
	 * "yaExisteElArbol" retorna verdadero cuando el arbol generado
	 * ya se encuentra en el bosque de �rboles m�nimos.
	 *
	 */
    public boolean yaExisteElArbol(int [] padres) { 
        ListIterator Litr = bosque.listIterator();
        int i = 0;
		
        while (Litr.hasNext()) {
            int [] p = ((MST)Litr.next()).padres;

            for (i = 0 ; i < p.length; i++ ) {			
                if (padres[i] != i) {
                   /*
				    * Verificamos si el nodo (i,padre [i]) se encuentra en p
				    * o si se encuentra el nodo (padre[i],i) entendiendose que 
					* el primer vertice de la tupla siempre es el indice del
					* vector.
				    */
				   if (!(padres[i] == p[i] || p[padres[i]] == i)) {
                        break;
                    }
                }
            }
            if (i >= padres.length - 1) {
                return true;
            } 
		
            continue;            
        }
        return false;
    }
}
