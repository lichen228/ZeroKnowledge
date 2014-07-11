package praktikum;

import gui.GUIObserver;

import java.math.BigInteger;
import java.util.Random;

/**
 * Diese Klasse enthält alle Methoden des Verifizierers für einen Zero-Knowledge
 * Beweis der Kenntnis eines diskreten Logarithmus
 * 
 */

public class Verifier {

	/** Systemparameter  */
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;

	/** öffentlicher Schlüssel des Beweisers (für einfachen Beweis) */
	private BigInteger x;
	/** öffentlicher Schlüssel des Beweisers (für erweiterten (OR) Beweis) */
	private BigInteger x_A;

	/** Commitment  */
	private BigInteger a;
	/** Response  */
	private BigInteger z;
	/** Challenge  */
	private int c;
	/** Challenge für OR-Beweis */
	private int C;

	/** Schlüsselpaar des Verifizierers  */
	private BigInteger x_B;
	private BigInteger w_B;

	/** Commitment, Challenge und Reponse des 2.Beweisteiles im OR-Protokoll  */
	private BigInteger b, y;
	private int d;

	/** Fehlerwahrscheinlichkeit  d.h. Wahrscheinlichkeit mit der 
	 * der Beweiser das Geheimnis nicht kennt */
	private double errorProb = 1;

	/** Verhalten des Verifizierers bei einem Beweis **/  
	/** accept >> 0 = unsicher, 1 = akzeptiert, -1 = abgelehnt **/
	private int accept = 0;

	/** Protokolltyp (extended = OR-Protokoll) */
	private boolean extended = false;

	private Random rand;

	/*-------------------------------------
	 -----Konstruktoren -------------------- 
	 ---------------------------------------*/

	public Verifier() {};

	public Verifier(GroupParam group, int seed) 
	{
		this.p = group.p;
		this.q = group.q;
		this.g = group.g;

		rand = new Random(seed);
	}

	public Verifier(GroupParam group) 
	{
		this.p = group.p;
		this.q = group.q;
		this.g = group.g;

		rand = new Random();
	}

	/*-------------------------------------
	 ----- Hilfsmethoden ------------------- 
	 ---------------------------------------*/

	/** GUI in der die Berechnungen dargestellt werden sollen */
	private GUIObserver gui;

	/**
	 * Legt GUI-Observer zum Darstellen gesendeter Nachrichten fest.
	 * 
	 * @param o GUI
	 */
	public void addO(GUIObserver o) 
	{
		gui = o;
	}

	/**
	 * Legt fest, ob für den Zero-Knowledge Beweis das einfache Protokoll oder
	 * das erweiterte OR-Protokoll zum Einsatz kommt.
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

	/**
	 * Diese Methode erzeugt ein Schlüsselpaar für den Verifizier, mit w_B als geheimen und x_B als
	 * öffentlichen Schlüssel.
	 */
	public void createKey() 
	{
		do {
			w_B = new BigInteger(q.bitLength(), 200, rand);
			this.w_B = w_B.remainder(q);
			this.x_B = g.modPow(w_B, p);
		} while (!(w_B.compareTo(BigInteger.ONE) > 0));
	}

	/**
	 * Gibt öffentlichen Schlüssel x_B des Verifizierers zurück
	 * 
	 * @return  öffentlicher Schlüssel x_B
	 */
	public BigInteger getPublicKey() 
	{
		return this.x_B;
	}
	
	/**
	 * Gibt Zustand des Verifizierers zurück, d.h. ob der Beweis akzeptiert
	 * wurde. 
	 * 
	 * @return 0 = unsicher, 1= akzeptiert, -1 = abgelehnt.
	 */
	public int hasAccepted()
	{
		return accept;
	}

	/**
	 * Legt Behauptung des Beweisers (meist dessen öffentlicher Schlüssel) fest,
	 * und setzt alle Werte des Verifizierungsvorganges zurück.
	 * 
	 * @param assertion  Behauptung des Beweisers
	 */
	public void toVerify(BigInteger assertion) 
	{
		this.x_A = this.x = assertion;
		this.reset();
	}

	/**
	 * Legt Commitment-Wert fest. Wird für einfache Protokollform benötigt.
	 * 
	 * @param commitment Commitment a
	 */
	public void setCommitment(BigInteger commitment) 
	{
		this.a = commitment;
	}

	/**
	 * Legt Commitment-Werte fest. Wird für OR-Protokollform benötigt.
	 * 
	 * @param commitment1 1.Commitment (a)
	 * @param commitment2 2.Commitment (b)
	 */
	public void setCommitments(BigInteger commitment1, BigInteger commitment2) 
	{
		this.a = commitment1;
		this.b = commitment2;
	}

	/**
	 * Legt Antwort fest und ruft Methode zum Verifizieren der Beweisrunde auf.
	 * 
	 * @param response Antwort (z)
	 * 
	 */
	public void setResponse(BigInteger response) 
	{
		this.z = response;
		this.verify();
	}

	/**
	 * Legt Antworten in einem OR-Beweis fest und ruft Methode zur der
	 * Beweirunde auf.
	 * 
	 * @param response1 1.Antwort (z)
	 * @param response2 2.Antwort (y)
	 * @param d Challenge des Beweisers (d)
	 * 
	 */
	public void setResponses(BigInteger response1, BigInteger response2, int d) 
	{
		this.z = response1;
		this.y = response2;
		this.d = d;
		this.or_verify();
	}

	/**
	 * Die Methode setzt Zustandswerte des Verifizierers zurück, wird für
	 * Simulation eines Beweises benötigt.
	 */
	public void reset() 
	{
		errorProb = 1;
		accept = 0;
	}

	/**
	 * Gibt Trankript der Beweisrunde zurück.
	 * 
	 * @return Transkript als String
	 */
	public String getView() 
	{
		String returnVal;

		if (!extended) {
			returnVal = ("   (   " + a + " \t " + c + " \t " + z + "   )\n");
		}

		else {
			returnVal = ("   (   " + a + " , " + b + " \t " + c + " , " + d
					+ " \t " + z + " , " + y + "   )\n");
		}

		return returnVal;
	}

	/*-------------------------------------
	 ----- Beweismethoden ------------------- 
	 ---------------------------------------*/

	/**
	 * Die Methode getChallenge gibt eine Challenge c=0 oder c=1 zurück
	 * 
	 * @return  Challenge c
	 */
	public int getChallenge() 
	{
		if (extended) {
			this.C = rand.nextInt(2);
			gui.show_V_challenge(a, b, C);
			return C;
		} else {
			this.c = rand.nextInt(2);
			gui.show_V_challenge(a, c);
			return c;
		}
	}

	/*******************************************
	 ****	Protokoll EINFACHE Form		******** 
	 ****	AUFGABE 3					******** 
	 *******************************************/
	/**
	 * Diese Methode verifiziert die Antwort des Beweisers und bestimmt
	 * daraufhin die Wahrscheinlichkeit, dass der Beweiser das behauptete
	 * Geheimnis kennt.
	 * 
	 * gegeben sind: 
	 * 		a 	das Commitment des Beweisers 
	 * 		c 	die gesendete Challenge 
	 * 		z	die Antwort des Beweisers 
	 * 		x 	der öffentliche Schlüssel des Beweisers
	 * 		errorProb 	bisherige Wahrscheinlichkeit , mit der 
	 * 					der Beweiser das Geheimnis NICHT kennt
	 * 
	 * Zu berechnende Werte: 
	 * 		errorProb: Wahrscheinlichkeit, mit der der Beweiser das Geheimnis NICHT kennt
	 * 		acceptRound: Wird die Beweisrunde akzeptiert?
	 *
	 *		accept: Verhalten des Verifizierers 
	 *				0 = unsicher, d.h. weitere Beweisrunden nötig 
	 *				1 = Beweis wird akzeptiert 
	 *				-1 = Beweis wird abgelehnt
	 */
	public void verify() 
	{
		try {
			boolean acceptRound = false;
			

			// ********* HIER IMPLEMENTIEREN *********************

			accept = 0;

			if (g.modPow(z, p).equals(a.multiply(x.modPow(BigInteger.valueOf(c), p)).mod(p))) {
				acceptRound = true;
				errorProb /= 2;
			} else {
				acceptRound = false;
				accept = -1;
				errorProb = 1;
			}

			if (accept == 0 && errorProb < 0.00001) {
				accept = 1;
			}
			
			
			
			/* Bedingungen für accept = 1 und accept = -1 nicht vergessen ! */
						
			// ********* BIS HIER IMPLEMENTIEREN ******************

			// GUI Ausgabe
			gui.show_V_verify(c, z, acceptRound, 1 - errorProb);
		} catch (Exception e) {
			System.out.println("Exception in Verifier.verify() .." + e);
		}

	}
	/*********************************************
	 ****	Protokoll ERWEITERTE (OR) Form  ****** 
	 ****	AUFGABE 3		********************** 
	 *********************************************/

	/**
	 * Diese Methode verifiziert die Antworten des Beweisers und bestimmt
	 * daraufhin die Wahrscheinlichkeit, dass der Beweiser das behauptete
	 * Geheimnis kennt.
	 * 
	 * Der Verifizierer überpüft zunächst, ob der erste Teil des Beweises
	 * (a,c,z) eine gültige Beweisrunde für die Behauptung x_A des Beweisers ist.
	 * Der zweite Teil (b,d,y) muss eine gültige Runde für den Beweis des
	 * EIGENEN Schlüssels x_B ergeben.
	 * 
	 * gegeben sind: 
	 * 		a 	das 1. Commitment des Beweisers 
	 * 		b 	das 2. Commitment des Beweisers 
	 * 		z 	die 1.  Antwort des Beweisers 
	 * 		y 	die 2. Antwort des Beweisers 
	 * 		C	die an den Beweiser gesendete Challenge 
	 * 		d 	die vom Beweiser gewählte Challenge
	 * 		x_A	der öffentliche Schlüssel des Beweisers 
	 * 		x_B	der eigene öffentliche Schlüssel
	 * 		errorProb 	bisherige Wahrscheinlichkeit , mit der 
	 * 					der Beweiser das Geheimnis NICHT kennt
	 * 
	 * Zu berechnende Werte: 
	 * 		errorProb: Wahrscheinlichkeit, dass der Beweiser das Geheimnis NICHT kennt 
	 * 		acceptRound1: true, wenn der erste Teil (a,c,z) der Beweisrunde akzeptiert wird, sonst false. 
	 * 		acceptRound2: true, wenn der zweite Teil (b,d,y) der Beweisrunde akzeptiert wird, sonst false.
	 * 		
	 * 		accept: Verhalten des Verifizierers 
	 * 				0 = unsicher, d.h. weitere Beweisrunden nötig 
	 * 				1 = Beweis wird akzeptiert 
	 * 		   	   -1 = Beweis wird abgelehnt
	 */
	public void or_verify() 
	{
		try {
			boolean acceptRound1 = false;
			boolean acceptRound2 = false;
			
			// *********  HIER IMPLEMENTIEREN *********************

			/* tatsächliche Challenge c berechnen */
			
			c = C^d;
			
			
			/* ersten Teil des Beweises, d.h. echten Beweisteil überprüfen */
			/* -> acceptRound1 bestimmen */ 
			
			if (g.modPow(z, p).equals(a.multiply(x_A.modPow(BigInteger.valueOf(c), p)).mod(p))) {
				acceptRound1 = true;
			} else {
				acceptRound1 = false;
				accept = -1;
			}
			
			
			/* zweiten Teil des Beweises, d.h. Simulation überprüfen */
			/* -> acceptRound2 bestimmen */
			
			if (g.modPow(y, p).equals(b.multiply(x_B.modPow(BigInteger.valueOf(d), p)).mod(p))) {
				acceptRound2 = true;
			} else {
				acceptRound2 = false;
				accept = -1;
			}
			
			/* -> errorProb berechnen */
			
			if(acceptRound1&&acceptRound2) {
				errorProb /= 2;
			}
			else {
				errorProb = 1;
			}
			
			/* feststellen, ob der Beweis akzeptiert, abgelehnt oder noch unsicher ist
			 *  und "accept" entsprechend setzen ! */

			if (accept == 0 && errorProb < 0.00001) {
				accept = 1;
			}
			
			// ********* BIS HIER IMPLEMENTIEREN ******************

			// GUI-Ausgabe
			gui.show_V_verify(c, z, d, y, acceptRound1, acceptRound2,1 - errorProb);
		} catch (Exception e) {
			System.out.println("Exception in Verifier.or_verify() .." + e);
		}
	}

}
