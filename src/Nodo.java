/**
 * Nodo.java
 *
 * Trabajo Práctico Nro. 2 
 * Algoritmos y Estruturas de Datos III
 * Autor: Cristhian Daniel Parra
 * C.I.: 2.045.856
 *
 * Fecha: 12 - 06 - 2005 
 * 
 * La Clase Nodo implementa el uso de un Nodo con los atributos
 * de "dato" y "anterior". Es un Nodo que contiene solo Nros. Positivos.
 * 
 * Esta clase es utilizada luego para implementar una pila.
 *  
 */
public class Nodo {

	private int dato;
	private Nodo anterior;

	/** 
	 * Constructor del Nodo. 
	 * 
	 * Inicializamos el Nodo con un numero negativo.
	 *
	 */
	public Nodo() {
	    dato = -1;
		anterior=null;
	}
	
	/** 
	 * Inicializamos el Nodo a una entero "c"
	 * 
	 */
	public Nodo(int c, Nodo n){
		dato = c;
		anterior = n;
	}
			
	/** 
	 * El método "vacío" verifica si el dato del nodo es un
	 * entero negativo. Si es así, el retorna TRUE.
	 *  
	 */
	public boolean vacio(){
		if (anterior==null && dato == -1) {
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * "get_dato" retorna el dato que se encuentra en el Nodo
	 * 
	 */
	public int get_dato(){
		return dato;
	}
	
	/**
	 * "set_dato" inicializa el dato que se encuentra en el Nodo
	 * @return String
	 */
	public void set_dato(int c){
		dato = c;
	}
	/**
	 * "set_anterior" inicializa el nodo anterior
	 * que se encuentra en el Nodo
	 * @return String
	 */
	public void set_anterior(Nodo n){
		anterior = n;
	}
		
	/**
	 * El Método "get_anterior" retorna el Nodo anterior para que para 
	 * que en la clase pila, al desapilar, lo hagamos haciendo
	 * que el tope referencie al anterior.
	 * @return Nodo
	 */
	public Nodo get_anterior(){
		return anterior;
	}	
	
	/**
	 * Creamos un Nuevo Nodo y le pasamos el dato correspondiente
	 * y el nodo al que debe apuntar como anterior.  
	 * 
	 * @param String dato
	 * @param Nodo ant
	 * @return Nodo
	 */		
	public Nodo cargar(int dato,Nodo ant){
		Nodo aux = new Nodo(dato,ant);
		return aux;
	}	
}
