package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz; 
    private int cardinal;

    private class Nodo {

        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;
        Nodo (T v){
            valor = v;
            izq = null;
            der = null;
            padre = null;
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){

        Nodo minimo = raiz;
        Nodo hijoMenor = raiz.izq;

        while(hijoMenor != null){
            minimo = hijoMenor;
            hijoMenor = hijoMenor.izq;
        }
        
        return minimo.valor;
    }

    public T maximo(){
        
        Nodo maximo = raiz;
        Nodo hijoMayor = raiz.der;

        while (hijoMayor != null) {
            maximo = hijoMayor;
            hijoMayor = hijoMayor.der;
        }

        return maximo.valor;
    }
    public void insertar(T elem) {
        if (raiz == null) {
            raiz = new Nodo(elem);
            cardinal++;
            return;
        }
    
        Nodo actual = raiz;
        Nodo padre = null;
    
        while (actual != null) {
            padre = actual;
            if (elem.compareTo(actual.valor) < 0) {
                actual = actual.izq;
            } else if (elem.compareTo(actual.valor) > 0) {
                actual = actual.der;
            } else {
                return;
            }
        }
    
        Nodo nuevo = new Nodo(elem);
        nuevo.padre = padre; 
    
        if (elem.compareTo(padre.valor) < 0) {
            padre.izq = nuevo;
        } else {
            padre.der = nuevo;
        }
    
        cardinal++;
    }    

    public boolean pertenece(T elem){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void eliminar(T elem){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
