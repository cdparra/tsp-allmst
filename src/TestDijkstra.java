public class TestDijkstra {
   
   public static void main (String args[])
   {
      int k, q;
      Grafo G = new GrafoNoDirigido();

      try {
         GrafoIO.leer (G, System.in);
         GrafoIO.imprimir(G);
         System.out.println ("\nVector de Distancias Mínimas desde el vértice 0= ");
		 double [] distancias = G.vectorDistMinimas(4);
		 
		 for (int i=0; i<distancias.length; i++) {
		    Vertice v = G.vertice(i);
		    System.out.println("Al vertice "+ v.nombre() + " = " + distancias[i]);
		 }
		 
		 String [] caminos = G.caminosMinimos(4);
		 System.out.println ("\nCaminos Minimos desde el vertice 1 = ");
		 for (int i = 0 ; i < caminos.length; i++) {
		     System.out.println ("From 0 to " + i + caminos[i]);
	     }
      } catch (Exception e) {
         System.out.println ("\n\nError: " + e.getMessage());
         e.printStackTrace(System.out);
      }
      System.out.println();
   }
}
