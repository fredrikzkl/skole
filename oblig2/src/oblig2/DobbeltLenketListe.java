// Nguyen Chu
// s169954
// 3AA

package oblig2;

import java.util.*;

import javax.naming.directory.NoSuchAttributeException;

public class DobbeltLenketListe<T> implements Liste<T>
{
  private static final class Node<T>       // en indre nodeklasse
  {
    private T verdi;
    private Node<T> forrige, neste;

    private Node(T verdi, Node<T> forrige, Node<T> neste)   // konstruktør
    {
      this.verdi = verdi;
      this.forrige = forrige;
      this.neste = neste;
    }
  }

  private Node<T> hode;         // peker til den første i listen
  private Node<T> hale;         // peker til den siste i listen
  private int antall;           // antall noder i listen
  private int antallEndringer;  // antall endringer i listen
  
  public static <T> int maks(Liste<T> liste, Comparator<? super T> c)
  {
  	if(liste.tom())
  		throw new NoSuchElementException("Listen er tom!");
  	
  	Iterator<T>it = liste.iterator();
  	int m = 0;
  	T maksverdi = it.next();
  	
  	for(int i = 1; it.hasNext(); i++)
  	{
  		T verdi = it.next();
  		if(c.compare(verdi,maksverdi) > 0)
  		{
  			m = i;
  			maksverdi = verdi;
  		}
  	}
  	return m;
  }
  
  private void indeksKontroll(int indeks)
  {
    if (indeks < 0 )
      throw new IndexOutOfBoundsException("Indeks " +
         indeks + " er negativ!");
    else if (indeks >= antall)
      throw new IndexOutOfBoundsException("Indeks " +
          indeks + " >= antall(" + antall + ") noder!");
  }
  
  private static <T> void nullSjekk(T verdi)
  {
	  if(verdi == null) throw
	  	new NullPointerException("Null-verdier er ikke tillatt!");
  }
  
  private Node<T> finnNode(int indeks)
  {
	  Node<T>p;
	  
	  if(indeks <= antall / 2)
	  {
		  p = hode;
		  for(int i = 0; i < indeks; i++)
		  {
			  p = p.neste;
		  }
	  }
	  else
	  {
		  p = hale;
		  for(int i = antall - 1;i > indeks;i--)
		  {
			  p = p.forrige;
		  }
	  }
	  
	  return p;
  }

  public DobbeltLenketListe()  // konstruktør
  {
    hode = hale = null;
    antall = 0;
    antallEndringer = 0;
  }
  
  public boolean tom()
  {
    return antall == 0;
  }
  
  public int antall()
  {
    return antall;
  }
  
  public void nullstill()
  {
	  Node<T>p = hode;
	  
	  while(p != null)
	  {
		  Node<T>q = p.neste;
		  
		  p.neste = null;
		  p.forrige = null;
		  p.verdi = null;
		  
		  p = q;
	  }
	  
	  antall = 0;
	  antallEndringer++;
	  hode = hale = null;
  }
  
  public boolean inneholder(T verdi)
  {
	  return indeksTil(verdi) != -1;
  }
  
  public void leggInn(int indeks, T verdi)
  {
	  nullSjekk(verdi);
	  
	  if(indeks < 0)
	  {
		  throw new IndexOutOfBoundsException("Indeks kan ikke være negativ!");
	  }
	  else if(indeks > antall)
	  {
		  throw new IndexOutOfBoundsException("Indeks kan ikke være større enn antall!");
	  }
	  else if(antall == 0)
	  {
		  hode = hale = new Node<>(verdi,null,null);
	  }
	  else if(indeks == 0)
	  {
		  hode = hode.forrige = new Node<>(verdi,null,hode);
	  }
	  else if(indeks == antall)
	  {
		  hale = hale.neste = new Node<>(verdi,hale,null);
	  }
	  else
	  {
		  Node<T>p = finnNode(indeks);
		  p.forrige = p.forrige.neste = new Node<>(verdi,p.forrige,p);
	  }
	  
	  antall++;
	  antallEndringer++;
  }
  
  public boolean leggInn(T verdi)
  {
	  nullSjekk(verdi);
	  
	  if(antall == 0)
	  {
		  hode = hale = new Node<>(verdi,null,null);
	  }
	  else
	  {
		  hale = hale.neste = new Node<>(verdi,hale,null);
	  }
	  
	  antall++;
	  antallEndringer++;
	  
	  return true;
  }
  
  public T hent(int indeks)
  {
	  indeksKontroll(indeks);
	  
	  return finnNode(indeks).verdi;
  }
  
  public int indeksTil(T verdi)
  {
	  if (verdi == null) return -1;
	  
	  Node<T>p = hode;
	  
	  for(int i = 0; i < antall; i++, p = p.neste)
	  {
		  if(p.verdi.equals(verdi))
		  {
			  return i;
		  }
	  }
	  
    return -1;  // foreløpig kode
  }
  
  public T oppdater(int indeks, T nyverdi)
  {
	  indeksKontroll(indeks);
	  nullSjekk(nyverdi);
	  
	  Node<T>p = finnNode(indeks);
	  
	  T gammelverdi = p.verdi;
	  p.verdi = nyverdi;
	  
	  antallEndringer++;
	  return gammelverdi;
  }
  
  public T fjern(int indeks)
  {
	  indeksKontroll(indeks);
	  
	  Node<T>p = hode;
	  
	  if (antall == 1)
	  {
		  hode = hale = null;
	  }
	  else if(indeks == 0)
	  {
		  hode = hode.neste;
		  hode.forrige = null;
	  }
	  else if(indeks == antall - 1)
	  {
		  p = hale;
		  hale = hale.forrige;
		  hale.neste = null;
	  }
	  else
	  {
		  p = finnNode(indeks);
		  p.forrige.neste = p.neste;
		  p.neste.forrige = p.forrige;
	  }
	  
	  T verdi = p.verdi;
	  p.verdi = null;
	  p.forrige = p.neste = null;
	  
	  antall--;
	  antallEndringer++;
	  
	  return verdi;
  }
  
  public boolean fjern(T verdi)
  {
	  if(verdi == null) return false;
	  
	  Node<T>p = hode;
	  
	  while(p != null)
	  {
		  if(p.verdi.equals(verdi)) break;
		  p = p.neste;
	  }
	  
	  if(p == null)
	  {
		  return false;
	  }
	  else if(antall == 1)
	  {
		  hode = hale = null;
	  }
	  else if(p == hode)
	  {
		  hode = hode.neste;
		  hode.forrige = null;
	  }
	  else if(p == hale)
	  {
		  hale = hale.forrige;
		  hale.neste = null;
	  }
	  else
	  {
		  p.forrige.neste = p.neste;
		  p.neste.forrige = p.forrige;
	  }
	  
	  p.verdi = null;
	  p.forrige = p.neste = null;
	  
	  antall--;
	  antallEndringer++;
	  
	  return true;
  }
  
  private class DobbeltLenketListeIterator implements Iterator<T>
  {
    private Node<T> p;
    private boolean fjernOK;
    private int forventetAntallEndringer;

    private DobbeltLenketListeIterator()
    {
      p = hode;         // p starter på den første i listen
      fjernOK = false;  // blir sann når next() kalles
      forventetAntallEndringer = antallEndringer;  // teller endringer
    }
    
    private DobbeltLenketListeIterator(int indeks)
    {
    	p = hode;
    	
    	for(int i = 0; i < indeks; i++)
    		p = p.neste;
    	fjernOK = false;
    	forventetAntallEndringer = antallEndringer;
    }
    
    public boolean hasNext()
    {
    	return p != null;
    }
    
    public T next()
    {
    	if(p == null)
    		throw new NoSuchElementException("Ikke flere igjen i listen!");
    	
    	if(forventetAntallEndringer != antallEndringer)
    		throw new ConcurrentModificationException("Listen har hatt endringer!");
    	
    	fjernOK = true;
    	
    	T verdi = p.verdi;
    	p = p.neste;
    	
    	return verdi;
    }
    
    public void remove()
    {
    	if(!fjernOK)
    		throw new IllegalStateException("Ikke OK å fjerne verdi!");
    	
    	if(forventetAntallEndringer != antallEndringer)
    		throw new ConcurrentModificationException("Listen har hatt endringer!");
    	
    	fjernOK = false;
    	Node<T>q = hode;
    	
    	if(antall == 1)
    	{
    		hode = hale = null;
    	}
    	else if(p == null)
    	{
    		q = hale;
    		hale = hale.forrige;
    		hale.neste = null;
    	}
    	else if(p.forrige == hode)
    	{
    		hode = hode.neste;
    		hode.forrige = null;
    	}
    	else
    	{
    		q = p.forrige;
    		q.forrige.neste = q.neste;
    		q. neste.forrige = q.forrige;
    	}
    	
    	q.verdi = null;
    	q. forrige = q.neste = null;
    	
    	antall--;
    	antallEndringer++;
    	forventetAntallEndringer++;
    }
    

    
  } // class DobbeltLenketListeIterator  

  public Iterator<T> iterator()
  {
    return new DobbeltLenketListeIterator();
  }
  
  public Iterator<T> iterator(int indeks)
  {
	  indeksKontroll(indeks);
	  return new DobbeltLenketListeIterator(indeks);
  }
  
  public String toString()
  {
	  StringBuilder s = new StringBuilder();
	  s.append('[');
	  
	  if(!tom())
	  {
		  s.append(hode.verdi);
		  
		  for(Node<T>p = hode.neste; p != null; p = p.neste)
		  {
			  s.append(',').append(' ').append(p.verdi);
		  }
	  }
	  
	  s.append(']');
	  return s.toString();
  }
  
  public String omvendtString()
  {
    StringBuilder s = new StringBuilder();
    s.append('[');
    
    if(!tom())
    {
    	s.append(hale.verdi);
    	
    	for(Node<T>p = hale.forrige;p != null;p = p.forrige)
    	{
    		s.append(',').append(' ').append(p.verdi);
    	}
    }
    
    s.append(']');
    return s.toString();
  }

} // class DobbeltLenketListe