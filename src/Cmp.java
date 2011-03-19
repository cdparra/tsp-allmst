/*
 *
 * Cmp.java
 *
 * ALGORITMOS Y ESTRUCTURAS DE DATOS III
 * Prof. Juan Segovia Silvero
 *
 * Cmp será utilizada como clase base para todos los datos
 * que necesiten ser "ordenados" o comparados con respecto
 * a otros datos de igual tipo.
 *
 * Cmp viene de "comparable".
 *
 * Obs para el Profesor: Mantego tu mismo  comentario puesto que yo no hice esta interfaz
 * sin embargo la uso en fuentes.
 */

interface Cmp
{
   /*
    * comparar() debe producir:
    * 0      cuando this == c2
    * < 0    cuando this <  c2
    * > 0    cuando this >  c2[B
    *
    * La comparación no es de las referencias, sino de los
    * valores. Vea un ejemplo en PruebaListaOrd.java
    */
   public int comparar (Cmp c2);
}
