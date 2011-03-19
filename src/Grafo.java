/*
 * Grafo.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 *
 * Fecha: 07 - 05 - 2005
 *
 * -- NOTA -- Sencillamente uso la implementación proveída por 
 *            el Profesor con algunas modificaciones
 *
 * -- Modificaciones al original
 *
 * - El original Implementaba una representacion sencilla de 
 *   grafos dirigidos que, esencialmente, usaba matriz de adyacencia.
 *   La implementación actual es de un Grafo dirigido con lista 
 *   de adyacencias
 *
 */

import java.util.*;

public class Grafo {
    public Vector lista_vert;
	public int numAristas = 0;        // Cantidad de aristas del Grafo

    public Grafo() {
        init();
    }

    public Grafo clonar() {
	    Grafo G = new Grafo();
	    G.lista_vert = (Vector)lista_vert.clone();
		G.numAristas = numAristas;
		return G;
	}

    /*
     * Reinicializa el grafo, perdiendose lo que pudiera contener.
     */
    public void init() {
        lista_vert = new Vector();
    }

    public int cantVertices() {
        return lista_vert.size();
    }

    /*
     * Version simplifada de unir(from, to, Arista a) para el caso en
     * que solo se tiene costo como atributo, que suele ser el caso.
     */
    public int unir (int from, int to, double costo) {
        unir (from, to, new Arista (from, to, costo));
		return ++numAristas;
    }

    /*
     * Anota que hay conexion entre los vertices identificados con "from" y "to",
     * conteniendo "a" los atributos de esta union (arista).
     */
    public void unir (int from, int to, Arista a) {
        /*
         * Si los vertices referenciados no existen, se instancian con nombre
         * null.
         */
        if ( from >= cantVertices() ) {
            lista_vert.setSize (from+1);
		}

        if ( to >= cantVertices() ) {
            lista_vert.setSize (to+1);
		}

        if ( vertice (from) == null ) {
            lista_vert.set (from, new Vertice (null));
		}

        if ( vertice (to) == null ) {
            lista_vert.set (to, new Vertice (null));
		}

        vertice(from).unir (a);
    }

    /*
     * Remueve la arista que une a los verticces (from, to).
     * Lo hace asignando null en la posicion apropiada de la lista
     * de adyacencias del nodo from.
     */
    public void separar (int from, int to) {
        if ( cantVertices() > from ) {
            vertice (from).separar(to);
		}
	
    }
    
    /*
     * Retorna un objeto de tipo Vertice. Existe solo para evitar que
     * el acceso a lista_vert se llene de casts.
     */
    public Vertice vertice (int k)
    {
        return (Vertice) lista_vert.elementAt (k);
    }

    /*
     * Retorna la arista (from, to), si existe.
     * Retorna null si
     * - no existe el vertice from o no existe el vertice to.
	 * - existen los dos vértices pero "to" no aparece entre los
	 *   adyacentes de "from".
     */
    public Arista arista (int from, int to) {
        Arista a = null;
        Vertice v;
        int cantVert = cantVertices();

        if ( from < cantVert && to < cantVert ) {
            v = vertice (from);
            a = v.adyacente(to);
        }        
        return a;
    }

    /*
     * Version simplificada de arista (from, to), util cuando el
     * unico atributo de una arista es "costo".
     */
    public double costo (int from, int to) {
        Arista a = arista (from, to);
        double r = 0.0;

        if ( a != null )
            r = a.costo;

        return r;
    }

    public int agregarVert (String id) {
        lista_vert.add (new Vertice (id));

        return cantVertices() - 1;
    }

    public void agregarVert (String id, int pos) {
        if ( pos >= cantVertices() )
            lista_vert.setSize (pos+1);

            lista_vert.set (pos, new Vertice (id));
    }
	
	public void Dijkstra ( int s ) {
        Heap vertices;
        Vertice actual = vertice(s);
        Vertice adyacente;
        Arista a;
        ListIterator AdyItr;
		
        int v = cantVertices();
        vertices = new Heap(v,0);
        actual.distMin = 0.0;

        // Cargamos los vértices en el Heap
        for (int i=0; i<v; i++) {
		    Vertice vertex = vertice (i);
			Cmp vertexMin = (Vertice) vertex;
			vertices.insertar(vertexMin);
		}
		while (v > 0) {
		    do {			
			    actual = (Vertice) vertices.extract_val_extremo();
			} while (actual !=null && actual.visitado);
			
			if (actual == null) {
			    break;
			}
			actual.visitado = true;
			v--;
			
			AdyItr =  actual.lista_ady.listIterator(0);
			
            while ( AdyItr.hasNext() ) {
                a = (Arista) AdyItr.next();
				adyacente = vertice (a.to);
				
				if ( !adyacente.visitado && 
                         adyacente.distMin > actual.distMin + a.costo) {

                    adyacente.distMin = actual.distMin + a.costo;
					adyacente.ultimoCaminoMin = a.from;
					
                }                
            }	

            // Cargamos "de nuevo" los vértices en el Heap
            for (int i=0; i<v; i++) {
		        Vertice vertex = vertice (i);
			    Cmp vertexMin = (Vertice) vertex;
			    vertices.insertar(vertexMin);
		    }	
		}		
	}
	
	public double [] vectorDistMinimas (int s) {
	    Dijkstra (s);
		
		double [] distancias = new double [cantVertices()];
		
		for( int i = 0; i < cantVertices(); i++ ) {
		    distancias[i] = (vertice(i)).distMin;
		}
		return distancias;
	}
	
	
	public String [] caminosMinimos (int s) {
	    Dijkstra (s);
		
		String [] caminos = new String [cantVertices()];
		
        for( int i = 0; i < cantVertices(); i++ ) {
		    caminos [i] = construirCamino (i);
		}
		return caminos;
	}
	
	public String construirCamino (int i) {
	    Vertice v = vertice (i);
		String anteriorString = "";
        int anteriorEnCamino = v.ultimoCaminoMin;
		
		if (anteriorEnCamino >=0) {
            anteriorString = construirCamino(anteriorEnCamino);
	    }		
        return anteriorString + v.nombre();
	}	
}
