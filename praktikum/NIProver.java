package praktikum;

import java.math.BigInteger;
import java.util.Random;
import java.util.*;
import java.security.*;

/**
 * Diese Klasse implementiert das Erstellen digitaler Signaturen auf Basis 
 * eines nicht-interaktiven Zero-Knowledge Beweises der Kenntnis eines 
 * diskreten Logaritmus.
 */

public class NIProver {

	/** öffentliche Systemparameter  */
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;

	/** Geheimer Schlüssel  */
	private BigInteger w;

	/** Öffentlicher Schlüssel  */
	private BigInteger x;

	/** Anzahl der Iterationen  */
	private int k;

	private Random rand;

	/** Konstruktor  */
	public NIProver(GroupParam group, int k) 
	{
		this.rand = new Random();
		this.p = group.p;
		this.q = group.q;
		this.g = group.g;
		this.k = k;

		/* Beweiser wählt Geheimnis 1<w<q */
		do {
			w = new BigInteger(q.bitLength(), 200, rand);
			this.w = w.remainder(q);
			this.x = g.modPow(w, p);
		} while (!(w.compareTo(BigInteger.ONE) > 0));

	}

	public BigInteger getPublicKey() {
		return this.x;
	}

	/**
	 * Diese Methode implementiert die Signierfunktion auf Basis eines 
	 * nicht-interaktiven Zero-Knowledge Beweises der Kenntnis eines 
	 * diskreten Logarithmus.
	 * 
	 * Hinweis: Die Anzahl der Iterationen ist durch (int) k gegegeben.
	 * 
	 * 			Das Schlüsselpaar liegt wie gewohnt als (BigInteger) x,w vor. 
	 * 			
	 * 			Nach dem Berechnen des Hashwertes liegen die Challenges als Bitstring 
	 * 			vor. Für die Umwandlung eines char-Wertes in die entsprechene 
	 * 			int-Darstellung steht die Methode charToInt(char) zur Verfügung. 
	 * 
	 * Zu berechnende Werte 
	 * 		s_list	k Zufallswerte 
	 * 		a_list	k Commitments
	 * 		z_list	k Antworten
	 * 
	 * 
	 * @param message zu Signierende Nachricht
	 * @return Signatur, bestehend aus den Challenges und Antworten des Beweises
	 */
	public Signatur sign(String message) 
	{

		/** Liste der Zufallswerte s_1 bis s_k im ersten Signierschritt */
		List<BigInteger> s_list = new ArrayList<BigInteger>();

		/** Liste der Commitments a_1 bis a_k */
		List<BigInteger> a_list = new ArrayList<BigInteger>();

		/** Liste der Antworten z_1 bis z_k */
		List<BigInteger> z_list = new ArrayList<BigInteger>();
		
		/** zu erstellende Signatur */
		Signatur sig = null;

		try {

			// ********* HIER IMPLEMENTIEREN *********************
			
			/** Zufallswerte und Commitments in der Anzahl der Iterationen k bestimmen */
			
			BigInteger s,a;
	
			for(int i=0; i<k; i++) {
				Random rnd = new Random();
				do {
				    s = new BigInteger(q.bitLength(), rnd);
				} while (s.compareTo(q) >= 0);
				a = g.modPow(s, p);
				
				s_list.add(s);
				a_list.add(a);
			}

			
			/*** Input der Hashfunktion ergibt sich aus Nachricht und allen Commitments ***/
			String hash_input = message.concat(a_list.toString());

			/**** Hashwert berechnen ***/
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte digest[] = md.digest(hash_input.getBytes());

			/**** Hashwert in BitString umwandeln ***/ 
			String challenges = toBitString(digest);

						
			/** Berechnen der Antworten z_1 bis z_k, zu schreiben in die z_list */
			
			for(int i=0; i<k; i++) {
				char cc = challenges.charAt(i);
				int ci = charToInt(cc);
				if(ci==0) {
					z_list.add(s_list.get(i));
				}
				else {
					z_list.add(s_list.get(i).add(w).mod(p));
				}
			}
			
			// ********* BIS HIER IMPLEMENTIEREN *********************

			/** * Signatur ergibt sich aus Challenges + Liste aller Antworten ** */
			sig = new Signatur(challenges, z_list);

		} catch (Exception e) {
			System.out.println("NIProver - sign: " + e);
		}

		return sig;
	}

	/**
	 * Diese Methode wandelt ein ByteArray in einen BitString um. 
	 * Führende Nullen werden beibehalten.
	 * 
	 * @param hash_value ByteArray
	 * @return BitString
	 */
	public String toBitString(byte[] hash_value) 
	{
		char[] chars = new char[8];
		byte value;
		String challenges = new String();
		for (int i = 0; i < k / 8; i++) 
		{
			value = hash_value[i];
			int mask = 1;
			for (int j = 0; j < 8; j++) 
			{
				chars[7 - j] = (value & mask) != 0 ? '1' : '0';
				mask <<= 1;
			}
			challenges = challenges.concat(new String(chars));
		}
		return challenges;
	}

	/**
	 * Wandelt Zahl in char-Darstellung in entsprechenden int-Wert um.
	 * 
	 * @param bit_char Zahl als char
	 * @return Zahl als int
	 */
	public int charToInt(char bit_char) 
	{
		int bit_int = (int) (bit_char - '0');
		return bit_int;
	}

}
