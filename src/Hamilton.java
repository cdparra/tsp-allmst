/*
 * Hamilton.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 * C.I.: 2.045.856
 *
 * Fecha: 07 - 06 - 2005 
 * Modificado: 12 - 06 - 2005
 *
 * Hamilton.java contiene los métodos que resuelven
 * el problema del ciclo simple de costo mínimo a partir  
 * de un vertice dado. La Lista de Métodos es la siguiente:
 *   - VecinoMasProx ---> Algoritmo Voraz que resuelve
 *                       el problema aproximadamente.
 *
 *   - VecinoMasProxVAtras ---> Es el mismo algoritmo anterior
 *                        pero con la optimización de la vuelta atras
 *                        para que cuando desde un vertice ya no hay opciones
 *                        el programa no termine, sino que vuelva atrás
 *                        y explore otros caminos.
 *
 *    - VecinoMasProxFBruta ---> El mismo Algoritmo pero que explora todas las
 *                        posibilidades y elige la menor.
 */
 
import java.util.*;
 
public class Hamilton {
    /*
     * El vector solucion contenerá la sucesión de Vertices a seguir
     * que forma el ciclo hamiltoniano de costo mínimo
     * solucion [i] es el i-ésima vertice en la sucesión.
     */
    Grafo G;
    Vertice [] solucion;        // Ciclo Solucion
    int [] solucionFB;          // Ciclo Solucion usado en el algoritmo de FuerzaBruta
    int [] solucionFBMin;       // Ciclo Solucion final para el algoritmo de FuerzaBruta
    double costo;               // Costo del Ciclo Mínimo.
    int cantVert;
    Pila Anteriores;            // Pila auxiliar que sirve para realizar la
                                // vuelta Atras.				
	
    public Hamilton (Grafo G,int cantVertices) {
        this.G = G;
        this.cantVert = cantVertices;
        solucion = new Vertice [cantVertices+1];
        solucionFB = new int [cantVertices+1];
        solucionFBMin = new int [cantVertices+1];
        costo = 0.0;
        Anteriores = new Pila();
    }
	
    /*
     * Este método resuelve el problema mediante un algoritmo ávido
     * 
     */
    public boolean VecinoMasProx (int verticeInicio) {
        int verticeEnCurso;              // Vertice procesado en la actual iteración
        int verticeAnterior;             // Vertice anterior en el ciclo

        int cantVert = G.cantVertices(); 
        Vertice v;
        Arista a = null;

        verticeEnCurso = verticeInicio;
        int k = 0;
 
        for (int i = 0 ; i< cantVert ; i++){

            if ( k == cantVert-1) {
                a = G.arista(verticeEnCurso,verticeInicio);

                if (a!=null) {
                    solucion[k++] = G.vertice(verticeEnCurso);
                    solucion[k] = G.vertice(verticeInicio);
                    costo += a.costo;					
                    return true;
                } else {
                    return false;
                }					
            }
			
            verticeAnterior = verticeEnCurso;
            v = G.vertice(verticeAnterior);
            v.visitado = true;
            a = Busca(verticeEnCurso);
            			
            /* Cuando desde el vertice actual ya no hay camino válido, 
             * el programa termina retornando falso para denotar que
             * no se encontro un ciclo Hamiltoniano Minimo.
             * Este es el principal problema de este algoritmo
             * Porque solo funciona bien cuando el Grafo es completo
             * Cuando no lo es, puede que no encuentre ningún ciclo
             * aunque si haya alguno.
             */
            if (a == null) {
                break;
            }
			
            verticeEnCurso = a.to;

            costo += a.costo;
            solucion [k++] = v;
        }
		
        return false;
    }

    /*
     * El Método Busca es el que efectivamente Busca al Vecino 
     * más pròximo y retorna la arista que lo une al verticeEnCurso
     *
     */
    public Arista Busca(int verticeEnCurso) {
        Vertice Vact= G.vertice(verticeEnCurso);
        Arista mejorArista = null;
        ListIterator Litr = Vact.lista_ady.listIterator(0);
        double costoMin = Double.POSITIVE_INFINITY;
									
        /* Busca entre los adyacentes a verticeEnCurso 
         * el que esté a la menor distancia.
         *  
         */		 
        while (Litr.hasNext()) {
            Arista ariAuxiliar = (Arista) Litr.next();
            Vertice posible = G.vertice(ariAuxiliar.to);

            if (posible.visitado) {
                continue;
            } else if (ariAuxiliar.costo < costoMin) {
                costoMin = ariAuxiliar.costo;
                mejorArista = ariAuxiliar;
            }			
        }
		
        return mejorArista;		
    }

    /*
     * El Siguiente es el mismo algoritmo anterior pero con 
     * el agregado de que si en un vértice no encuentra más c
     * caminos, vuelve para atrás para explorar otros camino
     * 
     * De esta manera nos aseguramos que, aunque el grafo no
     * esté completo, si existe algun ciclo Hamiltoniano, lo 
     * encontraremos y seuirá siendo aproximado al mìnimo òptimo.
     *
     */
	 
    public boolean VecinoMasProxVAtras (int verticeInicio) {
        int verticeEnCurso;
        int verticeAnterior;
        int cantVert = G.cantVertices();

        /*
         * En este vector de Strings, guardaremos en cada posición, los vértices 
         * que localmente ya visitó ese vértice, de tal forma que no los 
         * visite de nuevo en su búsqueda
         *
         * Opté por una cadena no porque optimize la eficiencia
         * del algoritmo, sino porque lo simplifica.
         *
         * 
         * No me gusta del código, el hecho que las lineas 181-190
         */
        String [] vertsLocsNoValidos = new String [cantVert];	

        for (int i=0; i<cantVert; i++) {
            vertsLocsNoValidos [i] = "";    // Inicializamos vertsLocsNoValidos
        }
		
        Vertice v;
        Arista a = null;		
        verticeEnCurso = verticeInicio;
        int k = 0;
		 
        while ( k < cantVert ) {
     
            if ( k == cantVert-1) {
                a = G.arista(verticeEnCurso,verticeInicio);
                if (a!=null) {
                    solucion[k++] = G.vertice(verticeEnCurso);
                	solucion[k] = G.vertice(verticeInicio);
                	costo += a.costo;					
                	return true;
                } else {
                    if (Anteriores.esvacia()) {
                       return false;
                    }  					 
                 
                    int noValidoLocal = Anteriores.desapilar();				 
                    G.vertice(noValidoLocal).visitado = false;       // Para el global no está visitado
                    verticeEnCurso = Anteriores.desapilar();	
                    costo-=G.arista(verticeEnCurso,noValidoLocal).costo;
                    vertsLocsNoValidos[verticeEnCurso] +=  (","+ noValidoLocal);
                    vertsLocsNoValidos[noValidoLocal] =  "";
                    k--;
                    continue;
                }	
            }
			
            verticeAnterior = verticeEnCurso;
            Anteriores.apilar(verticeAnterior);
		    v = G.vertice(verticeAnterior);
            v.visitado = true;
            a = BuscaVAtras(verticeEnCurso,vertsLocsNoValidos[verticeEnCurso]);
					
            // Seccion que hace la Vuelta Atrás.
            if (a == null) {

                if (Anteriores.esvacia()) {
                    return false;
                }  					 

                int noValidoLocal = Anteriores.desapilar();				 
                G.vertice(noValidoLocal).visitado = false;       // Para el global no está visitado
				 
                verticeEnCurso = Anteriores.desapilar();
                if (verticeEnCurso < 0 ){
                    return false;
                }

                costo -= G.arista(verticeEnCurso,noValidoLocal).costo;
 
                vertsLocsNoValidos[verticeEnCurso] +=  (","+ noValidoLocal);
                vertsLocsNoValidos[noValidoLocal] =  "";
                k--;
                continue;
            }
			
            verticeEnCurso = a.to;			
            costo += a.costo;
            solucion [k++] = v;
        }
        return true;
    }

    public Arista BuscaVAtras(int verticeEnCurso, String noValidos) {
        Vertice Vact= G.vertice(verticeEnCurso);
        Arista mejorArista = null;
        ListIterator Litr = Vact.lista_ady.listIterator(0);
        double costoMin = Double.POSITIVE_INFINITY;
											
        while (Litr.hasNext()) {
            Arista ariAuxiliar = (Arista) Litr.next();
            Vertice posible = G.vertice(ariAuxiliar.to);
			
            if (posible.visitado || (noValidos.indexOf(""+ariAuxiliar.to)>=0 && noValidos!="")) {
                continue;
            } else if (ariAuxiliar.costo < costoMin) {
                costoMin = ariAuxiliar.costo;
                mejorArista = ariAuxiliar;
            }			
        }
        return mejorArista;		
    }
	
    /*
     * Este método resuelve el problema mediante un algoritmo
     * de Vuelta Atrás que analiza todas las posibilidades.
     * Por lo tanto es de Fuerza Bruta	 
     *
     * Para que el método funcione, el índice del vector de los
     * vértices deben empezar en 1, por ello le sumamos uno al 
     * inicio
     */
    public boolean HamiltonFuerzaBruta (int verticeInicio) {
        solucionFB[1] = verticeInicio+1;
        solucionFBMin[0] = verticeInicio;
		
        for (int i=2 ; i< solucionFB.length ; i++ ) {
            solucionFB[i] = 0;
        }
		
        solucionFBMin[cantVert] = verticeInicio;
        costo = Double.POSITIVE_INFINITY;
        FuerzaBruta(2);
		
        if (costo < Double.POSITIVE_INFINITY) {
            return true;
        }
		
        return false;
    }
    
    private void FuerzaBruta (int k) {

        do {            
            NuevoVertice(k);
 			
            if (k==cantVert && solucionFB[k]!=0 ) {
			
                double costoActual = CalcularCosto();

                if (costoActual < costo) {
                    costo = costoActual;
                    
                    for (int i = 2; i < solucionFB.length ; i++ ) {
                        solucionFBMin[i-1] = solucionFB[i]-1;
                    }
                }
            } else if (solucionFB[k]!=0){
                FuerzaBruta (k+1);
            }
        } while (solucionFB[k]!=0 );
    }


    public void NuevoVertice(int k ) {
        boolean vuelta=false;

        do {
            solucionFB[k] = (solucionFB[k]+1) % (cantVert+1);

            if ( (solucionFB[k] !=0 && G.arista(solucionFB[k-1]-1,solucionFB[k]-1)!= null)) {    
				int j = 0; 
	
                while (j <= k-1 && solucionFB[j]!=solucionFB[k] ) {
                    j++;
                }
				
                if (j==k && solucionFB[cantVert] != 0) {
                    vuelta = (G.arista(solucionFB[cantVert]-1,solucionFB[1]-1)!=null);
                }

                if ( j==k && ((k<cantVert) || 
                                ((k==cantVert) && vuelta))) {
                    break;
                }
            }
        } while (solucionFB[k] !=0);
    }
	
    public double CalcularCosto () {
        double c = 0.0;
        int i;
        
        for (i = 1 ; i < solucionFB.length-1 ; i++ ) {
            c += G.arista(solucionFB[i]-1,solucionFB[i+1]-1).costo;            
        }
		
        c +=  G.arista(solucionFB[i]-1,solucionFB[1]-1).costo;
        return c;
    }

    /*
	 * Rutina Sencilla que imprime el ciclo de costo mínimo hallado.
	 *
	 */
    public void imprimir() {
        
        System.out.print(""+solucion[0].toString());

        for( int i=1 ; i < solucion.length ; i++ ) {
            System.out.print(" -> "+solucion[i].toString());
        }
        System.out.println("\nCosto del Ciclo Mínimo Aproximado: " + costo);
    }		
	
    public void imprimirFB() {
        
        System.out.print(""+G.vertice(solucionFBMin[0]).nombre());
	
        for( int i=1 ; i < solucion.length ; i++ ) {
            System.out.print(" -> "+G.vertice(solucionFBMin[i]).nombre());
        }
        System.out.println("\nCosto del Ciclo con costo Mínimo: " + costo);
    }		
}
