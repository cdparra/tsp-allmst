/* Arista.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 *
 * Fecha: 07 - 05 - 2005
 * 
 * -- NOTA -- Sencillamente uso la implementación proveída por el Profesor con 
 *            algunas modificaciones
 *
 * -- Desde aquí comentarios del Profesor
 *
 * Clase base para almacenar datos sobre las aristas de grafos.
 * Siendo "costo" (cualquiera sea el significado de tal cosa) el
 * atributo que suele asociarse a una arista, este es el unico
 * atributo previsto aqui, además los identificadores de los
 * vertices que conecta.
 *
 * El costo se inicializa a 0.0
 */

public class Arista implements Cmp {

    public double costo = 0.0;        
    public int from;
	public int to;

    public Arista (int from, int to, double c) {
        this.costo = c;
        this.from = from;
        this.to = to;
    }

    public String toString() {
        return "costo =" + costo;
    }
   
    public int comparar ( Cmp ari ) {
	    Arista a = (Arista) ari;
		
        if (this.costo < a.costo) {
		    return -1;
		} else if (this.costo > a.costo) {
		    return 1;
		}
		
		return 0;		
    }
}
