/*
 * Created on 27-abr-2005
 *
 */

/**
 * @author Cristhian Parra
 *
 */
public class Heap {
    public int tamanyo;
    public Cmp [] monticulo;
    int max_min; // indicador de si es Heap_min (0) o Heap_max (1)
	
    public Heap () {
        this.tamanyo = 10;
        this.monticulo = new Cmp [tamanyo+1];
        this.max_min = 0; // Heap_min es el predeterminado
    }

    public Heap (int tam) {
        this.tamanyo = tam;
        this.monticulo = new Cmp [tam + 1];
        this.max_min = 0; // Heap_min es el predeterminado
    }

    public Heap (int tam, int tipo) {
        this.tamanyo = 0;
        this.monticulo = new Cmp [tam + 1];
        this.max_min = tipo;
    }

    public Heap (int tipo, Cmp [] A) {
        int cero=A[0]!=null?0:1;
        this.tamanyo = A.length;
        
        if (cero!=0) {
            this.monticulo = new Cmp [this.tamanyo];
        } else  {
			this.monticulo = new Cmp [this.tamanyo+1];
        }

        this.max_min = tipo;
        build_heap(A); 
    }
	
    public void heapify(int i){
        int izq = izquierdo(i);
        int der = derecho(i);
        int extremo = i;
        boolean heap_prop = true; // Indica si la propiedad del Monticulo se cumple o no

        if (izq <=this.tamanyo) {
            heap_prop = heap_propiedad(i,izq);
        }
			
        if (!heap_prop) {
            extremo = izq;		
        }

        if (der<=this.tamanyo) {
            heap_prop = heap_propiedad (extremo,der);

            if (!heap_prop) {
                extremo = der;
            }
        }
			
        if (extremo != i) {
            Cmp tmp = this.monticulo[i];
            this.monticulo[i] = this.monticulo[extremo];
            this.monticulo[extremo] = tmp;
            heapify(extremo);
        }
    }

    public void build_heap(Cmp [] A){
		
        if (A[0]==null){
            this.tamanyo = A.length-1;
		    this.monticulo = A;
        } else {
            for (int i = 1;i<this.monticulo.length;i++) {
                this.monticulo[i]=A[i-1];
            }
        }
		     
        for(int i = (tamanyo)/2;i>0;i-- ) {
            heapify(i);
        }		
    }

    public Cmp extract_val_extremo(){
        if (this.tamanyo < 1) {
            return null;
        }

        Cmp extremo = this.monticulo[1];
        this.monticulo[1] = this.monticulo[this.tamanyo];
        this.tamanyo--;
		
        heapify(1);		
        return extremo;		
    }
	
    public boolean insertar(Cmp key){
        if (this.tamanyo < 1) {
            this.monticulo[1] = key;
            this.tamanyo++;
            return true;
        }
	
        this.tamanyo++;		

        // Si el nuevo tamaÃ±o excede la capacidad mÃ¡xima actual
        // del monticulo, redimensionamos el mismo.
        if (this.tamanyo >= this.monticulo.length) {
            Cmp [] tmp = this.monticulo;
            this.monticulo = new Cmp [this.tamanyo*2+ 1];

            for(int j = 1; j< this.tamanyo; j++)
                this.monticulo[j] = tmp[j];
        }
        
        int i=this.tamanyo;		
		
        // buscamos la posicion correspondiente para insertar el nuevo elemento
        while(i>1 && !heap_propiedad(padre(i),key)) {
            this.monticulo[i] = this.monticulo[padre(i)];
            i = padre(i);
        }
		
        this.monticulo[i] = key;
        return true;
	}

    // Ordenamiento Interno con Heapsort
    // Aquí sentidoOrd indica el sentido de la ordenación 
    // de manera opuesta que como lo hace max_min
    //        0 -> Heap_min -> Ordenación Descendente
    //        1 -> Heap_max -> Ordenación Ascendente
    public static void heapsort (Cmp [] A, int sentidoOrd) {
        Heap H = new Heap(sentidoOrd,A);
        H.build_heap(A);

        for(int i = H.tamanyo; i >=2; i--) {
            Cmp tmp = H.monticulo[1];
            H.monticulo[1] = H.monticulo[i];
            H.monticulo[i]= tmp;
            H.tamanyo--;
            H.heapify(1);
        }		
    }
	
    public void imprimir_mas_extremos_que(Cmp key) {
        privImprimirMasQue(key,1);
    }
	
    private void privImprimirMasQue(Cmp key, int nivel) {
        if (heap_propiedad(nivel,key)) {
            System.out.println(monticulo[nivel].toString());
            if (izquierdo(nivel)<=this.tamanyo) {
				privImprimirMasQue(key,izquierdo(nivel));
            }

            if (derecho(nivel)<=this.tamanyo) {
                privImprimirMasQue(key,derecho(nivel));
            }
        }
    }

    public void imprimirHeapArbol() {
        privImprimirHArbol(1,0);
    }
		
    private void privImprimirHArbol(int nodo,int nivel) {
        if (nodo<=this.tamanyo) {
            privImprimirHArbol(derecho(nodo),nivel+1);

            for(int i = 1; i<=nivel; i++) {
                System.out.print("   ");
            }

            System.out.println(monticulo[nodo].toString());
            privImprimirHArbol(izquierdo(nodo),nivel+1);
        }
    }
			
    // Verifica si se cumple la propiedad del heap para la posicion i y la posicion j
    // donde i es el padre y j el hijo.
    public boolean heap_propiedad(int i,int j){
        boolean secumple = false;
        int comparacion = this.monticulo[i].comparar(this.monticulo[j]); 

        if ( this.max_min == 0 ) {

            if (comparacion <= 0) {
                secumple = true;
            }
        } else if (this.max_min == 1) {
            
            if (comparacion >= 0) {
                secumple = true;
            }
        }
		
        return secumple;
    }

    // Verifica si se cumple la propiedad del heap para la posicion i y la posicion j
    // donde i es el padre y j el hijo.
    public boolean heap_propiedad(int i,Cmp nuevo){
        boolean secumple = false;
        int comparacion = this.monticulo[i].comparar(nuevo); 

        if ( this.max_min == 0 ) {
            if (comparacion <= 0) {
                secumple = true;
            }
        } else if (this.max_min == 1) {
            if (comparacion >= 0) {
                secumple = true;
            }
        }
        return secumple;
    }
	
    // Devuelve la posición del hijo izquierdo para una posición dada i.
    public int izquierdo (int i) {
        return 2*i;
    }
    
    // Devuelve la posición del hijo derecho para una posición dada i.
    public int derecho (int i) {
        return 2*i + 1;
    }

    // Devuelve la posición del padre para una posición dada i
    public int padre (int i) {
        return i/2;
    }
}
