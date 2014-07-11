package praktikum;

import java.math.BigInteger;
import java.util.*;
import java.security.*;

/**
 * Diese Klasse implementiert das Verifizieren digitaler Signaturen auf Basis 
 * eines nicht-interaktiven Zero-Knowledge Beweises der Kenntnis eines 
 * diskreten Logaritmus.
 */

public class NIVerifier {

	/** öffentliche Systemparameter */
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;

	/** Anzahl der Iterationen */
	private int k;

	/** Konstruktor */
	public NIVerifier(GroupParam group, int k) 
	{
		this.p = group.p;
		this.q = group.q;
		this.g = group.g;
		this.k = k;
	}

	/**
	 * Die Methode überprüft die Gültigkeit einer Signatur zu einer
	 * gegebenen Nachricht und öffentlichen Schlüssel des Beweisers/Signierers
	 * 
	 * Hinweis: Die Anzahl der Iterationen ist durch k gegegeben.
	 * 			
	 * 			Nach dem Berechnen des Hashwertes liegen die Challenges als Bitstring 
	 * 			vor. Für die Umwandlung eines char-Wertes in die entsprechene 
	 * 			int-Darstellung steht die Methode charToInt(char) zur Verfügung. 
	 * 
	 * Zu berechnende Werte:
	 * 			b_list	Commitments
	 * 			accept	true, wenn Signatur gültig ist, sonst false
	 * 
	 * @param message signierte Nachricht
	 * @param sig Signatur 
	 * @param x öffentlicher Schlüssel des Beweisers
	 * @return 'true' wenn die Signatur gültig ist, sonst 'false'
	 */
	public boolean verify(String message, Signatur sig, BigInteger x) {

		/** Liste der zu berechneten Commitments */
		List<BigInteger> b_list = new ArrayList<BigInteger>();

		/** wird Signatur akzeptiert */
		boolean accept = false;

		try {
			// ********* HIER IMPLEMENTIEREN ************************

			/** multiplikatives Inverse von x berechnen */
			
			BigInteger inv_x = x.modPow(BigInteger.valueOf(-1), p);
			
			/** zugrundeliegende Commitments berechnen und zu b_list hinzufügen*/
			
			BigInteger b;
			for(int i=0; i<k; i++) {
				char cc = sig.challenges.charAt(i);
				int ci = charToInt(cc);
				b = g.modPow(sig.responses.get(i), p);
				
				if(ci==1) {
					b = b.multiply(inv_x).mod(p);
				}
				
				b_list.add(b);
			}
			
			
			
			
			/*** Hashwert von Nachricht und allen Commitments berechnen ***/
			String hash_input = message.concat(b_list.toString());

			MessageDigest md = MessageDigest.getInstance("SHA");
			byte digest[] = md.digest(hash_input.getBytes());

			/*** Hashwert in BitString umwandeln ***/
			String hash_value = toBitString(digest);

			
			/** Testen, ob Signatur korrekt ist */
			
			if(hash_value.equals(sig.challenges)) {
				accept=true;
			}
			

			// ********* BIS HIER IMPLEMENTIEREN *********************

		} catch (Exception e) {
			System.out.println("NIVerifier - verify: " + e);
		}

		return accept;
	}

	/**
	 * Diese Methode wandelt ein ByteArray in einen BitString um. Führende
	 * Nullen werden beibehalten.
	 * 
	 * @param hash_value
	 *            ByteArray
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
	 * @param bit_char   Zahl als char
	 * @return Zahl als int
	 */
	public int charToInt(char bit_char) 
	{
		return (int) (bit_char - '0');
	}

}
