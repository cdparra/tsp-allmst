/**
 * @author Cristhian Parra 
 * 
 * La Clase Pila implementa una estructura de Datos PILA.
 *  
 */

public class Pila {

	Nodo tope;
	
	/** 
	 * Inicializa la pila con un tope vacío.
	 *
	 */
	public Pila() {
		tope = new Nodo();
	}
	
	
	/**
	 * Añade un elemento a la pila.
	 * 
	 * @param dato
	 */
	public void apilar(int dato){
		
		/*
		 * Dependiendo de si está vacía o no, envía el modo
		 * de carga al método "cargar" de la clase Nodo.
		 * 
		 */
		if (esvacia()){
			tope = tope.cargar(dato,tope);
		} else {
			tope=tope.cargar(dato,this.tope);
		}
	}

	/**
	 * Saca el elemento tope de la Pila.
	 * 
	 * @return String
	 */
	public int desapilar(){
		int elemento=-1;
	
		if (!esvacia()) {
			elemento=tope.get_dato();
			Nodo n = tope.get_anterior();
			if (n==null){
				tope.set_anterior(null);				
			} else {
				tope = n;
			}				
		}	
		return elemento;
	}
	
	/**
	 * Determina si la Pila está vacía o no examinando si el 
	 * dato del Nodo Tope es o no una cadena vacía.
	 * @return boolean
	 */
	public boolean esvacia(){
		if (tope.vacio()){
			return true;
		} else { 
			return false;
		}
	}		
	
	public void imprimir() {
	      Pila aux = new Pila();
	      while (!esvacia()) {
		      int j = desapilar();
			  System.out.print(""+j+"  ");
			  aux.apilar(j);
		  }
			  this.tope = aux.tope;
		  
	}
}
