// Navn: Nguyen Chu
// Studentnummer: s169954
// Klasse: 3AA

package algdat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Oblig1 
{	
	// hjelpemetoder
	public static void bytt(int[] a, int i, int j)
	{
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void bytt(char[] a, int i, int j)
	{
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static int søkUsortert(int[] a, int verdi)
	  {
	    for (int i = 0; i < a.length; i++)
	      if (verdi == a[i]) return i;
	    return -1;   
	  }
	
	// For en tabell med lengde n, vil det forekomme n-1 sammenligninger av tabellverdier.
	/* Ekstraspørsmål: Færrest ombyttinger blir det når det minste tallet kommer først og tallene kommer i stigende rekkefølge. 
	Flest ombyttinger har vi når det største tallet ligger på a[0], for da blir det a.length-1 ombyttinger.
	I gjennomsnitt blir det (1/2)+(2/3)+(3/4)+(4/5) + (n-1/n) ombyttinger for n elementer. */
	public static int maks(int[] a)
	{
		if (a.length < 1)
		      throw new java.util.NoSuchElementException("a er tom");
		
		for (int i = 1; i < a.length; i++)
		{
			if (a[i-1] > a[i])
			{
				bytt(a, i, i-1);
			}
		}
	return	a[a.length-1];
	}
	
	// Dersom tabellen har lengden a, vil den ha (n-1)! sammenligninger av tabellverdier.
		public static void sortering(int[] a)
		{
			for (int i = 0; i < a.length; i++)
			{
				for (int j = i + 1; j < a.length; j++)
				{
					if (a[i] > a[j])
					{
						bytt(a, i, j);
					}
				}
			}
		}
		
		// Metoden har orden n, det vil si lineær orden i det verste tilfellet.
		
		public static int antallUlikeSortert(int[] a)
		{
			for (int i = 1; i < a.length; i++)
			{
				if (a[i] < a[i-1])
					throw new IllegalStateException("Tabellen er ikke sortert!");
			}
			int antallUlike = 1;
			if (a.length == 0) antallUlike = 0;
			else
			for (int j = 1; j < a.length; j++)
			{
				if (a[j] != a[j-1]) antallUlike++;
			}
			return antallUlike;
		}
		
		// I det verste tilfellet vil metoden ha en kvadratisk orden
		public static int antallUlikeUsortert(int[] a)
		{
			if(a.length < 1) return 0;
			int max = a[0];
			int antall = 1;
			for (int i = 0; i < a.length; i++)
			{
				if (a[i] > max) max = a[i];
			}
			
			for (int i = 0; i < max; i++)
			{
				for (int j = 0; j < a.length; j++)
				{
					if (a[j] == i)
					{
						antall++;
						break;
					}
				}
			}
			return antall;
		}
		
		public static void rotasjon(char[] a)
		{
			int n = a.length;
			if (n < 2) return;
			
			char[] b = new char[a.length];
			b[0] = a[a.length-1];
			for (int i = 1; i < a.length; i++)
			{
				b[i] = a[i-1];
			}
			
			for (int i = 0; i < a.length; i++)
			{
				a[i] = b[i];
			}
		}
		
		public static void rotasjon(char[] a, int k)
		{
			if (a.length < 2) return;
			
			k %= a.length;
			if (k < 0) k += a.length;
			
			int v = 0, h = a.length - 1;
			while (v < h) bytt(a, v++, h--);
			
			v = 0; h = k - 1;
		    while (v < h) bytt(a,v++,h--);  
		    
		    v = k; h = a.length - 1;
		    while (v < h) bytt(a,v++,h--);  
		}
		
		public static String toString(int[] a, char v, char h, String mellomrom)
		{
			StringBuilder sb = new StringBuilder();
			sb.append(v);
			if(a.length != 0)
			{
				for(int i = 0; i < a.length-1; i++)
				{
					sb.append(a[i]).append(mellomrom);
				}
				sb.append(a[a.length - 1]);
			}
			sb.append(h);
			return sb.toString();
		}
		
		public static int[] tredjeMinst(int[] a)
		{
			if(a.length < 3)
				throw new IllegalArgumentException("a.length må være lengre enn 3!");
			
			int index = 0;
			int index2 = 0;
			int index3 = 0;
			int minimum = Integer.MAX_VALUE;
			int minimum2 = Integer.MAX_VALUE;
			int minimum3 = Integer.MAX_VALUE;
			
			for(int i = 0; i < a.length; i++)
			{
				if(a[i] < minimum3){
					if(a[i] < minimum2){
						if(a[i] < minimum){
							minimum3 = minimum2;
							index3 = index2;
							minimum2 = minimum;
							index2 = index;
							minimum = a[i];
							index = i;
							continue;
						}
						minimum3 = minimum2;
						index3 = index2;
						minimum2 = a[i];
						index2 = i;
						continue;
					}
					minimum3 = a[i];
					index3 = i;
				}
			}
			return new int[] {index, index2, index3};
		}
		
		public static void tredjeMinstTest()
		{
			int[] a = {8,3,5,7,9,6,10,2,1,4};
			
			if(tredjeMinst(a)[0] != 8 && tredjeMinst(a)[1] != 7 && tredjeMinst(a)[2] != 1)
				System.out.println("Gir feil posisjon for tre minste verdier");
			
			a = new int[0];
			boolean unntak = false;
			
			try
			{
				tredjeMinst(a);
			}
			catch(Exception e)
			{
				unntak = true;
				if(!(e instanceof java.util.NoSuchElementException))
					System.out.println("Feil unntak for en tom tabell!");
			}
			
			if(!unntak)
				System.out.println("Det skal kastes unntak for en tom tabell");
		}
		
		public static int[] kMinst(int[] a, int k)
		{
			if (k < 1 || k > a.length)
				throw new IllegalArgumentException("k kan ikke være mindre enn 1 eller større enn n.length!");
		
			int[] verdier = new int[k];
			int[] hjelpeTabell = new int[a.length];
			
			for (int i = 0; i < a.length; i++)
			{	
				hjelpeTabell[i] = a[i];
			}

			sortering(hjelpeTabell);

			for(int i = 0; i < k; i++)
			{
				verdier[i] = hjelpeTabell[i];
			}
			
			return verdier;
			
		}
		
		public static int[] bokstavfrekvens(String url) throws IOException
		{
			InputStream inn =
				      new BufferedInputStream((new URL(url)).openStream());
			
			char[] alfabet = new char[] {'a','b','c','d','e','f','g','h','i','j','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z','æ','ø','å'};
			
			int[] antall = new int[alfabet.length];
			
			int i;
			
			while((i = inn.read()) != -1)
			{
				for(int j = 0; j < alfabet.length; j++)
				{
					if(alfabet[j] == (char) i)
						antall[j] += 1;
					else if(Character.toLowerCase(alfabet[j]) == (char) i)
						antall[j] += 1;
				}
			}
			
			return antall;
			
		}
}