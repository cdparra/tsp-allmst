/*
 * PruebaMST.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 * C.I.: 2.045.856
 *
 * Fecha: 12 - 06 - 2005 
 * Pequeño Programa que lee un grafo desde la línea de comandos
 * y prueba con él los algoritmos de las clases MST.java y AllMst.java
 *
 */

public class PruebaMST {	
	public static void main(String [] args) throws Exception {
        Grafo G = new GrafoNoDirigido();
        MST soloUnMST;
		AllMst todosLosMST;
		
	    GrafoIO.leer(G,System.in);
        System.out.println("\nGeneración de un Arbol de Recubrimiento Mìnimo:");
	    soloUnMST = new MST(G);
	    soloUnMST.MSTPrim(Integer.parseInt(args[0]));
	    soloUnMST.imprimir_MST_Prim();
	  	
        System.out.println("\nGeneración de 'todos' Arbol de Recubrimiento Mìnimo:");
		todosLosMST = new AllMst(G);
		todosLosMST.AllMSTPrim();		
    }
}
