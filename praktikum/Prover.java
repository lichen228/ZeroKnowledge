package praktikum;

import gui.GUIObserver;

import java.math.BigInteger;
import java.util.Random;

/**
 * Diese Klasse enthält alle Methoden des Beweisers für einen Zero-Knowledge
 * Beweis der Kenntnis eines diskreten Logarithmus
 * 
 */

public class Prover {

	/** Systemparameter * */
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;

	/** geheimer Schlüssel des Beweisers (für einfachen Beweis) **/
	private BigInteger w;
	/** öffentlicher Schlüssel des Beweisers (für einfachen Beweis) **/
	private BigInteger x;

	/** geheimer Schlüssel des Beweisers (für OR-Beweis) **/
	private BigInteger w_A;
	/** öffentlicher Schlüssel des Beweisers (für OR-Beweis) **/
	private BigInteger x_A;

	/** öffentlicher Schlüssel des Verifizierers (für OR-Beweis) **/
	private BigInteger x_B;
	/** muliplikative Inverse von x_B **/
	private BigInteger inv_x_B;

	/** Zufallswert im 1.Beweisschritt **/
	private BigInteger s;
	/** Commitment * */
	private BigInteger a;
	/** Challenge * */
	private int c;
	/** Challenge des Verifizierers für OR-Beweis */
	private int C;
	/** Response * */
	private BigInteger z;

	/** Challenge der Simulation **/
	private int d;
	/** Commitment+Response der Simulation **/
	private BigInteger b, y;

	/** zu überzeugender Verifizierer */
	private Verifier myVerifier;
	
	/** Zufallszahlengenerator **/
	private Random rand;

	/** Protokolltyp (extended = OR-Protokoll) */
	private boolean extended = false;

	/*-------------------------------------
	 ----- Konstruktor ------------------- 
	 ---------------------------------------*/

	public Prover(GroupParam group, int seed) 
	{
		this.rand = new Random(seed);

		this.p = group.p;
		this.q = group.q;
		this.g = group.g;

		/* Beweiser wählt Geheimnis 1<w<q */
		do {
			w = new BigInteger(q.bitLength(), 200, rand);
			w = w_A = w.remainder(q);
			x = x_A = g.modPow(w, p);
		} while (!(w.compareTo(BigInteger.ONE) > 0));

	}

	/*-------------------------------------
	 ----- Hilfsmethoden ------------------- 
	 ---------------------------------------*/

	/**
	 * Die Methode toProof legt den gewünschten Verifizierer fest und
	 * übermittelt an diesen die zu beweisende Behauptung (meist der öffentliche
	 * Schlüssel x)
	 * 
	 * @param verifier der gewünschte Verifizierer
	 * @param assertion die zu beweisende Behauptung
	 */
	public void toProof(Verifier verifier, BigInteger assertion) 
	{
		this.myVerifier = verifier;
		myVerifier.toVerify(assertion);

		/*
		 * im or-Beweis wird der öffentliche Schlüssel des Verifizierers
		 * benötigt
		 */
		if (extended) {
			this.x_B = myVerifier.getPublicKey();
			System.out.println(myVerifier);
			/* Berechnung des multiplikativen Inversen von x_B */
			this.inv_x_B = x_B.modPow(p.subtract(BigInteger.valueOf(2)), p);
		}
	}

	/**
	 * Gibt öffentlichen Schlüssel x des Beweisers zurück.
	 * 
	 * @return öffentlicher Schlüssel x
	 */
	public BigInteger getPublicKey() 
	{
		return this.x;
	}

	/**
	 * Legt fest, ob für den Zero-Knowledge Beweis das einfache Protokoll oder
	 * der erweiterte OR-Beweis zum Einsatz kommt.
	 * 
	 * @param type Protokoll-Variante
	 */
	public void setProtocolType(String type) 
	{
		if (type.equals("extended")) {
			extended = true;
		} else {
			extended = false;
		}
	}

	private GUIObserver gui;

	/**
	 * Legt GUI-Observer zum Darstellen gesendeter Nachrichten fest.
	 * 
	 * @param o GUI
	 */
	public void addO(GUIObserver o) 
	{
		gui = o;
		gui.show_P_secret(this.w, this.x);
	}

	/*-------------------------------------
	 ----- Beweismethoden ------------------- 
	 ---------------------------------------*/

	/**
	 * Die Methode proof() führt eine Beweisrunde des Zero-Knowledge Beweises
	 * zur Kenntnis eines diskreten Logarithmus durch.
	 * 
	 */
	public void proof() 
	{
		if (!extended) {
			/* 1.Schritt - Berechnen und Senden des Commitments */
			this.sendCommitment();

			/* 2. Schritt - Verifier liefert Challenge */
			c = myVerifier.getChallenge();

			/* 3.Schritt - Berechnen und Senden der Antwort */
			this.sendResponse();
		} 
		else 
		{
			/* 1.Schritt - Berechnen und Senden der Commitments */
			this.or_sendCommitment();

			/* 2. Schritt - Verifier liefert Challenge */
			C = myVerifier.getChallenge();

			/* 3.Schritt - Berechnen und Senden der Antworten */
			this.or_sendResponse();
		}
	}

	/*********************************************
	 ****	Protokoll EINFACHE Form       ******** 
	 ****	AUFGABE 3				      ******** 
	 *********************************************/

	/**
	 * Die Methode implementiert den ersten Beweisschritt des Zero-Knowledge
	 * Beweises, d.h. berechnet das Commitment a und übermittelt es an den
	 * Verifizierer.
	 * 
	 * gegebene Werte: Systemparameter (BigInteger) p,q,g
	 * 
	 * Zu berechnende Werte: (BigInteger) s,a
	 */
	public void sendCommitment() 
	{
		try {
			// ********* HIER IMPLEMENTIEREN *********************

			Random rnd = new Random();
			do {
			    s = new BigInteger(q.bitLength(), rnd);
			} while (s.compareTo(q) >= 0);
			
			a = g.modPow(s, p);

			// ********* BIS HIER IMPLEMENTIEREN ******************

			/* Commitment an Verifizierer übergeben */
			myVerifier.setCommitment(a);

			/* GUI-Ausgabe */
			gui.show_P_commitment(s, a);

		} catch (Exception e) {
			System.out.println("Exception in Prover.sendCommitment() .." + e);
		}
	}

	/**
	 * Diese Methode implementiert den dritten Beweisschritt. In Abhängigkeit
	 * der zuvor bestimmten Werte s,a und der Challenge c wird der Antwortwert z
	 * berechnet und an den Verifizierer übergeben. 
	 * Das Schlüsselpaar des Beweisers liegt als (BigInteger) x,w vor.
	 * 
	 * Gegebene Werte:	Systemparameter (BigInteger) p,q,g
	 * 					(BigInteger) s,a,x,w
	 * 					(int) c	
	 * 	
	 * Zu berechnende Werte: (BigInteger) z
	 */
	public void sendResponse() 
	{
		try {

			// ********* HIER IMPLEMENTIEREN *********************

			if(c==0) {
				z = s;
			}
			else {
				z = s.add(w);
			}
			

			// ********* BIS HIER IMPLEMENTIEREN ******************

			/* Response wird an Verifizierer übergeben */
			myVerifier.setResponse(z);

			/* GUI-Ausgabe */
			gui.show_P_response(c, z);
		} catch (Exception e) {
			System.out.println("Exception in Prover.sendResponse() .." + e);
		}
	}

	/*********************************************
	 ****	Protokoll ERWEITERTE (OR) Form  ****** 
	 ****	AUFGABE 6				      ******** 
	 *********************************************/

	/**
	 * Die Methode implementiert den ersten Beweisschritt der OR-Version des
	 * Zero-Knowledge Beweises. Das Commitment a des echten Beweises wird
	 * berechnet und eine Beweisrunde (b,d,y) für den öffentlichen Schlüssel x_B
	 * des Verifizierers simuliert.
	 * 
	 * Hinweis: Das multiplikative Inverse von x_B liegt bereits als inv_x_B vor.
	 * 
	 * Gegebene Werte:	Systemparameter (BigInteger) p,q,g
	 * 					(BigInteger) inv_x_B
	 * 
	 * Zu berechnende Werte: 
	 * 		s,a (des "echten" Beweises) 
	 * 		b,d,y (des simulierten Beweises)
	 */
	public void or_sendCommitment() 
	{
		try {
			// ********* HIER IMPLEMENTIEREN *********************

			/* Zufallswert und Commitment für den normalen Beweis bestimmen */
			
			Random rnd = new Random();
			do {
			    s = new BigInteger(q.bitLength(), rnd);
			} while (s.compareTo(q) >= 0);
			
			a = g.modPow(s, p);
			

			/* Simulation einer Beweisrunde zum öffentlichen Schlüssel x_B */
			
			do {
			    y = new BigInteger(q.bitLength(), rnd);
			} while (y.compareTo(q) >= 0);
			d = rnd.nextInt(2);
			
			b = g.modPow(y, p);
			if(d==1) {
				b = b.multiply(inv_x_B).mod(p);
			}
			
			// ********* BIS HIER IMPLEMENTIEREN ******************

			/* Commitment an Verifizierer übergeben */
			myVerifier.setCommitments(a, b);

			/* GUI-Ausgabe */
			gui.show_P_commitment(s, a, y, d, b);

		} catch (Exception e) {
			System.out.println("Exception in Prover.or_sendCommitment() .." + e);
		}

	}

	/**
	 * Diese Methode implementiert den dritten Beweisschritt der OR-Version des
	 * Zero-Knowledge Beweises. In Abhängigkeit der zuvor bestimmten Werte s, a
	 * und der Challenge C wird der Antwortwert z berechnet und zusammen mit den
	 * Werten y,d der Simulation an den Verifizierer übergeben.
	 *
	 * Gegebene Werte:	Systemparameter (BigInteger) p,q,g
	 * 					(BigInteger) s,a,x_A,w_A
	 * 					(int) C,d	
	 * 
	 * Zu berechnende Werte: z
	 */
	public void or_sendResponse() 
	{
		try {
			// ********* HIER IMPLEMENTIEREN *********************

			/* Challenge c als XOR-Summe der Challenge C des Verifizierers und d berechnen */
			
			c = C^d;
			
			/* Antwort z für "echten" Beweis berechnen */
				
			if(c==0) {
				z = s;
			}
			else {
				z = s.add(w_A).mod(p);
			}
			
			
			// ********* BIS HIER IMPLEMENTIEREN ******************

			/* Antworten z,y und Challenge d an Verifizierer übergeben */
			myVerifier.setResponses(z, y, d);

			/* GUI-Ausgabe */
			gui.show_P_response(c, z, d, y);

		} catch (Exception e) {
			System.out.println("Exception in Prover.or_sendResponse() .." + e);
		}
	}

}
