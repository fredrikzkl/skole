package oblig2;

//////////////////interface Beholder  //////////////////////////////


import java.util.Iterator;

public interface Beholder<T> extends Iterable<T>  // ny versjon
{
public boolean leggInn(T t);       // legger inn t i beholderen
public boolean inneholder(T t);    // sjekker om den inneholder t
public boolean fjern(T t);         // fjerner t fra beholderen
public int antall();               // returnerer antallet i beholderen
public boolean tom();              // sjekker om beholderen er tom
public void nullstill();           // tømmer beholderen
public Iterator<T> iterator();     // returnerer en iterator
}