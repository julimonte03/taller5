package aed;

import java.lang.reflect.Array;
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

    public boolean pertenece(T elem) {
        return perteneceRecursivo(raiz, elem);
    }
    
    private boolean perteneceRecursivo(Nodo actual, T elem) {
        if (actual == null) return false; 

        if(actual.valor == elem) return true;
        
        int comparacion = elem.compareTo(actual.valor);
        
        if (comparacion == 0) {
            return true; 
        } else if (comparacion < 0) {
            return perteneceRecursivo(actual.izq, elem); 
        } else {
            return perteneceRecursivo(actual.der, elem); 
        }
    }
    

    public void eliminar(T elem) {
        Nodo actual = raiz;
        Nodo padre = null;
        boolean esHijoIzq = false;
    
        if (actual == null) return; // El árbol está vacío
    
        // Encontrar el nodo a eliminar
        while (actual != null && !actual.valor.equals(elem)) { // Comparar usando equals
            padre = actual;
            if (elem.compareTo(actual.valor) > 0) {
                actual = actual.der;
                esHijoIzq = false; // Marcar que no es hijo izquierdo
            } else {
                actual = actual.izq;
                esHijoIzq = true; // Marcar que es hijo izquierdo
            }
        }
    
        if (actual == null) return; // No se encontró el nodo a eliminar
    
        // Caso 1: Nodo sin hijos
        if (actual.izq == null && actual.der == null) {
            if (actual == raiz) {
                raiz = null; // El nodo a eliminar es la raíz
            } else if (esHijoIzq) {
                padre.izq = null; // Eliminar referencia en el padre (hijo izquierdo)
            } else {
                padre.der = null; // Eliminar referencia en el padre (hijo derecho)
            }
        }
        // Caso 2: Nodo con solo un hijo (hijo derecho)
        else if (actual.izq == null) {
            if (actual == raiz) {
                raiz = actual.der; // El nodo a eliminar es la raíz
            } else if (esHijoIzq) {
                padre.izq = actual.der; // Eliminar referencia en el padre (hijo izquierdo)
            } else {
                padre.der = actual.der; // Eliminar referencia en el padre (hijo derecho)
            }
        }
        // Caso 2: Nodo con solo un hijo (hijo izquierdo)
        else if (actual.der == null) {
            if (actual == raiz) {
                raiz = actual.izq; // El nodo a eliminar es la raíz
            } else if (esHijoIzq) {
                padre.izq = actual.izq; // Eliminar referencia en el padre (hijo izquierdo)
            } else {
                padre.der = actual.izq; // Eliminar referencia en el padre (hijo derecho)
            }
        }
        // Caso 3: Nodo con dos hijos
        else {
            Nodo sucesor = getSucesor(actual); // Encontrar sucesor
    
            if (actual == raiz) {
                raiz = sucesor; // Reemplazar la raíz
            } else if (esHijoIzq) {
                padre.izq = sucesor; // Eliminar referencia en el padre (hijo izquierdo)
            } else {
                padre.der = sucesor; // Eliminar referencia en el padre (hijo derecho)
            }
    
            sucesor.izq = actual.izq; // Conectar el hijo izquierdo del nodo eliminado con el sucesor
        }
    
        cardinal--; // Decrementar el cardinal después de eliminar el nodo
    }
    
    // Método para encontrar el sucesor (mínimo nodo en el subárbol derecho)
    private Nodo getSucesor(Nodo nodo) {
        Nodo sucesorPadre = nodo;
        Nodo sucesor = nodo;
        Nodo actual = nodo.der;
    
        while (actual != null) {
            sucesorPadre = sucesor;
            sucesor = actual;
            actual = actual.izq; // Ir al mínimo del subárbol derecho
        }
    
        if (sucesor != nodo.der) {
            sucesorPadre.izq = sucesor.der; // Ajustar el puntero del padre del sucesor
            sucesor.der = nodo.der; // Conectar el hijo derecho del nodo eliminado con el sucesor
        }
    
        return sucesor;
    }    

    public String toString() {
        return "{" + inOrden(raiz) + "}";
    }
    
    private String inOrden(Nodo nodo) {
        if (nodo == null) {
            return "";
        }
        String izquierda = inOrden(nodo.izq);
        String valorActual = nodo.valor.toString(); 
        String derecha = inOrden(nodo.der);
        String resultado = "";
        if (!izquierda.isEmpty()) {
            resultado += izquierda + ",";
        }
        resultado += valorActual;
        if (!derecha.isEmpty()) {
            resultado += "," + derecha;
        }
        return resultado;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Stack<Nodo> pila;
    
        public ABB_Iterador() {
            pila = new Stack<>();
            
            Nodo actual = raiz;
            while (actual != null) {
                pila.push(actual);
                actual = actual.izq;
            }
        }
    
        public boolean haySiguiente() {            
            return !pila.isEmpty();
        }
    
        public T siguiente() {
            
            Nodo nodo = pila.pop(); 
            T result = nodo.valor; 
            
            if (nodo.der != null) {
                Nodo actual = nodo.der;
                while (actual != null) {
                    pila.push(actual);
                    actual = actual.izq;
                }
            }
            
            return result; 
        }
    }
    
    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }
}

