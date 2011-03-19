/* 
 * TestHamilton.java
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

public class TestHamilton {
   
   public static void main (String args[])
   {
      int k, q;
      Grafo G = new GrafoNoDirigido();
	  Hamilton Ham;

      try {
         GrafoIO.leer (G, System.in);
         GrafoIO.imprimir(G);
		 Ham = new Hamilton (G,G.cantVertices());
		 int opcion;
		 
		 if (args [0] != ""){
		    opcion = Integer.parseInt(args[0]);
		 } else {
		    opcion = 1;
		 }
		 
		 boolean respuesta = false;
		 
		 if ( opcion == 1 ) {
		     respuesta = Ham.HamiltonFuerzaBruta(Integer.parseInt(args [1]));
		 } else if (opcion == 2) {
		     respuesta = Ham.VecinoMasProx(Integer.parseInt(args [1]));
		 } else if (opcion == 3){
		     respuesta = Ham.VecinoMasProxVAtras(Integer.parseInt(args [1]));
		 }
		 
		 if (respuesta) {
		     if (opcion == 1) {
                 System.out.println ("\nSolucion del Ciclo Mínimo para el Grafo introducido...");
		         Ham.imprimirFB();
			 } else {
                 System.out.println ("\nSolucion Aproximada del Ciclo Mínimo para el Grafo introducido...");
		         Ham.imprimir();
			 }
		 } else {
		     System.out.println ("\nNo se encontró ningún ciclo Hamiltoniano...");
		 }
		 
		 
      } catch (Exception e) {
         System.out.println ("\n\nError: " + e.getMessage());
         e.printStackTrace(System.out);
      }
      System.out.println();
   }
}
