// Nguyen Chu
// s169954
// 3AA
// Oblig2 AlgDat

package oblig2_ikke_godkjent;

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
	private static final class Node<T> 
{

private T verdi;
private Node<T> forrige, neste;

private Node(T verdi, Node<T> forrige, Node<T> neste)
{
	this.verdi = verdi;
	this.forrige = forrige;
	this.neste = neste;
}

private Node<T> hode;    
private Node<T> hale;    
private int antall;      
private int modAntall;  

private void indeksKontroll(int indeks)
{
	if (indeks < 0)
	{
		throw new IndexOutOfBoundsException("Indeks " + indeks + " er negativ!");
	}
	else if (indeks >= antall)
	{
		throw new IndexOutOfBoundsException("Indeks " + indeks + " >= antall(" + antall + ") noder!");
	}
}

private static <T> void nullSjekk(T verdi)
{
	if (verdi == null) 
		throw new NullPointerException("Ikke tillatt med null-verdier!");
}

private Node<T> finnNode(int indeks)
{
	Node<T> p;

	if (indeks <= antall / 2)
	{
		p = hode;
		for (int i = 0; i < indeks; i++)
		{
			p = p.neste;
		}
	}
	else
	{
		p = hale;
		for (int i = antall - 1; i > indeks; i--)
		{
			p = p.forrige;
		}
	}
	return p;
}

public void DobbeltLenketListe()
{
	hode = hale = null;
	antall = 0;
	modAntall = 0;
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
	Node<T> p = hode;

	while (p != null)
	{
		Node<T> q = p.neste;

		p.neste = null;     
		p.forrige = null;   
		p.verdi = null;    

		p = q;
	}
	antall = 0;
	modAntall++;
	hode = hale = null;
	}

public boolean inneholder(T verdi)
{
	return indeksTil(verdi) != -1;
}

public void leggInn(int indeks, T verdi)
{
	nullSjekk(verdi);

	if (indeks < 0)
	{
		throw new IndexOutOfBoundsException("Indeks " + indeks + " er negativ!");
	}
	else if (indeks > antall)
	{
		throw new IndexOutOfBoundsException("Indeks " + indeks + " > antall(" + antall + ") noder!");
	}
	else if (antall == 0)  
	{
		hode = hale = new Node<>(verdi, null, null);
	}
	else if (indeks == 0)  
	{
		hode = hode.forrige = new Node<>(verdi, null, hode);
	}
	else if (indeks == antall)  
	{
		hale = hale.neste = new Node<>(verdi, hale, null);
	}
	else
	{
		Node<T> p = finnNode(indeks);  
		p.forrige = p.forrige.neste = new Node<>(verdi, p.forrige, p);
	}
	
	antall++;      
	modAntall++;   
}

public boolean leggInn(T verdi)
{
	nullSjekk(verdi);

	if (antall == 0)  
	{
		hode = hale = new Node<>(verdi, null, null);
	}
	else  
	{
		hale = hale.neste = new Node<>(verdi, hale, null);
	}

	antall++;      
	modAntall++;   
	
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
	
	Node<T> p = hode;
	
	for (int i = 0; i < antall; i++, p = p.neste)
	{
		if (p.verdi.equals(verdi))
		{
			return i;
		}
	}
	return -1;
}
	
public T oppdater(int indeks, T nyverdi)
{
	indeksKontroll(indeks);
	nullSjekk(nyverdi);
	
	Node<T> p = finnNode(indeks);
	
	T gammelverdi = p.verdi;
	p.verdi = nyverdi;

	modAntall++;   
	return gammelverdi;
}

public T fjern(int indeks)
{
	indeksKontroll(indeks);
	
	Node<T> p = hode;
	
	if (antall == 1) 
	{
		hode = hale = null;
	}
	else if (indeks == 0)  
	{
		hode = hode.neste;
		hode.forrige = null;
	}
	else if (indeks == antall - 1)  
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
	modAntall++;   
	
	return verdi;
}

public boolean fjern(T verdi)
{
	if (verdi == null) return false;
	
	Node<T> p = hode;
	
	while (p != null)  
	{
		if (p.verdi.equals(verdi)) break;
		p = p.neste;
	}

	if (p == null)
	{
		return false;        
	}
	else if (antall == 1)  
	{
		hode = hale = null;
	}
	else if (p == hode)    
	{
		hode = hode.neste;
		hode.forrige = null;
	}
	else if (p == hale)    
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
	modAntall++;   
	
	return true;   
}

public void forskyv(int k)
{
	if (antall < 2) return;
	k %= antall;
	if (k < 0) k += antall;
	
	if (k == 0) return;



	Node<T> p = finnNode(antall - k);
	
	hale.neste = hode;
	hode.forrige = hale;
	hode = p;
	hale = p.forrige;
	hale.neste = null;
	hode.forrige = null;
}

private class DobbeltLenketListeIterator implements Iterator<T>
{
	private Node<T> p;
	private boolean fjernOK;
	private int iteratorModAntall;

	private DobbeltLenketListeIterator()
	{
		p = hode;           
		fjernOK = false;    
		iteratorModAntall = modAntall;    
	}
	
	private DobbeltLenketListeIterator(int indeks)
	{
		p = hode;          
		for (int i = 0; i < indeks; i++)
		p = p.neste;      
		fjernOK = false;    
		iteratorModAntall = modAntall;   
	}
	
	public boolean hasNext()
	{
		return p != null;  
	}
	
	public T next()
	{
		if (p == null) throw
		new NoSuchElementException("Ingen flere verdier i listen!");
		
		if (iteratorModAntall != modAntall) throw
		new ConcurrentModificationException("Listen har blitt endret!");
		
		fjernOK = true;
		
		T verdi = p.verdi;       // tar vare på verdien i p
		p = p.neste;             // flytter p til neste
		
		return verdi;
	}
	
	public void remove()
	{
		if (!fjernOK) throw
		new IllegalStateException("Kan ikke fjerne en verdi nå!");
		
		if (iteratorModAntall != modAntall) throw
		new ConcurrentModificationException("Listen har blitt endret!");
		
		fjernOK = false;
		Node<T> q = hode;
		
		if (antall == 1)    
		{
			hode = hale = null;
		}
		else if (p == null)  
		{
			q = hale;
			hale = hale.forrige;
			hale.neste = null;
		}
		else if (p.forrige == hode)  
		{
			hode = hode.neste;
			hode.forrige = null;
		}
		else
		{
			q = p.forrige;  
			q.forrige.neste = q.neste;
			q.neste.forrige = q.forrige;
		}
		
		q.verdi = null;              
		q.forrige = q.neste = null;  
		
		antall--;             
		modAntall++;          
		iteratorModAntall++;  
		}
	
	} 
	
	public Iterator<T> iterator()
	{
		return new DobbeltLenketListeIterator();
	}
	
	public Iterator<T> iterator(int indeks)
	{
		indeksKontroll(indeks);
		return new DobbeltLenketListeIterator(indeks);
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append('[');
	
		if (!tom())
		{
			s.append(hode.verdi);
		
			for (Node<T> p = hode.neste; p != null; p = p.neste)
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
		
		if (!tom())
		{
			s.append(hale.verdi);
		
			for (Node<T> p = hale.forrige; p != null; p = p.forrige)
			{
				s.append(',').append(' ').append(p.verdi);
			}
		}
		
		s.append(']');
		return s.toString();
		}
	
	} 
}