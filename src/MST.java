/*
 * MST.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 * C.I.: 2.045.856
 *
 * Fecha: 12 - 06 - 2005 
 *
 * AllMst.java contiene los métodos que resuelven
 * el problema del árbol de recubrimiento de costo mínimo
 *
 *   - MSTPrim ---> Usando el Algoritmo de Prim, genera un 
 *                  árbol de recubrimiento de costo mínimo
 *
 *   - imprimir_MST_Prim ---> imprime el resultado
 *
 */

import java.util.*;

public class MST {

    Grafo G;                    // El Grafo sobre el cual trabajamos
    public int cantVert;        // cantidad total de vertices del Grafo 
    public int padres[];        // vector de padres que representa a un àrbol
                                // recubrimiento de costo mínimo.
   
    public MST ( Grafo G ) {
        this.G = G;
		/*
		 * Nos aseguramos de desmarcar los vertices del grafo por si 
		 * fueron marcados en algun método anterior
		 */
        for (int i= 0; i<G.cantVertices(); i++) {
            this.G.vertice(i).visitado = false;
        }
        
        cantVert = this.G.cantVertices();
	    padres = new int[cantVert];
    }

    /*
     * MSTPrim es el algoritmo visto en clase. La única modificación
     * se da por el uso de Grafo con lista de adyacencia y no Matriz 
     * de adyacencia
     *
     */
    public void MSTPrim ( int inicio )
    {
        Heap HeapAristas = new Heap(cantVert*cantVert,0);   // HeapMin de Aristas del Grafo
	    double [] distancias = new double [cantVert];      
        Vertice v = G.vertice(inicio);
		Arista ariAuxiliar;

        for (int i= 0 ; i<cantVert; i++) {
            distancias[i] = Double.POSITIVE_INFINITY;
        }        

        distancias [inicio] = 0.0;
		
        /* Construimos el HeapMin con las Aristas del Grafo.
		 * El número máximo de aristas que podrían haber es igual
		 * cuadrado de la cantidad de vertices (cuando es completo)
		 *
		 * Puesto que ese el numero máximo de Aristas que podrían haber, 
		 * el costo de esta seccion esta en O(n*n*log n)
		 */
	    for( int i=0 ; i<cantVert ; i++) {
	        v = G.vertice(i);			
			ListIterator Litr = v.lista_ady.listIterator();
				
            while (Litr.hasNext()) {			
	            ariAuxiliar = (Arista) Litr.next();
                double costo = Double.POSITIVE_INFINITY;
		        int from;
		        int to;
		        
				if (ariAuxiliar==null) {	
                    continue;
                }
		
		        from  = ariAuxiliar.from;
		        to = ariAuxiliar.to;
		        HeapAristas.insertar ( new Arista(from,to,costo) );	
            }
         }
	    	
	    /*
	     * Insertamos nuestra arista de especial de inicio con
		 * con un costo cero para que sea la primera en salir del
		 * montículo.
		 *
	     */
	    HeapAristas.insertar(new Arista(inicio,inicio,0));
	    padres[inicio] = inicio;
	
        /* Bloque Fundamental donde se genera el arbol propiamente dicho
         * Su costo estaría en (si el grafo es completo):
         *  O (|V|( log |E| + |V-1| log|E|))
         */
	    for(int k=0 ; k<cantVert; k++) {
	        int u = ((Arista)HeapAristas.extract_val_extremo()).to;
	        G.vertice(u).visitado=true;
		    ListIterator Litr = G.vertice(u).lista_ady.listIterator();
				
            while (Litr.hasNext()) {			
                ariAuxiliar = (Arista)Litr.next();
	
                // Este "if" podemos eliminarlo pues la lista de adyacencias 
                // no contiene huecos en este caso
                if(ariAuxiliar != null) {
                    int w = ariAuxiliar.to;
                    if(!G.vertice(w).visitado && 
                            G.arista(u,w).costo < distancias[w]) {
		               padres[w]=u;
                       distancias[w]=G.arista(u,w).costo;
                       HeapAristas.insertar(new Arista(u,w,distancias[w]));
                    }
                }		
            }	     
        }
    }
	
    public void imprimir_MST_Prim() {
        int k;
        double costo = 0.0;

        System.out.println("\nUn Árbol de Recubrimiento Mínimo sobre G es...");

        for(k=0; k<cantVert;k++) {
            if ( padres[k]!= k ) {
                System.out.print("\nDe nodo " + padres[k] + " a " + k + " costo = " 
                                              + G.arista(padres[k],k).costo);
                costo = costo + G.arista(padres[k],k).costo;
            }
        }

        System.out.println("\n\nCosto Total = " + costo);
    }
}
