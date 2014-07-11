package praktikum;

import gui.GUIObserver;

import java.math.BigInteger;
import java.util.Random;

/**
 * Diese Klasse implementiert die Black-Box Simulation eines 
 * Zero-Knowledge Beweises der Kenntnis eines diskreten Logaritmus.
 */
public class Simulator {

	/** Systemparameter **/
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;

	/** öffentlicher Schlüssel des zu simulierenden Beweisers **/
	private BigInteger x;
	/** multiplikatives Inverses von x **/
	private BigInteger inv_x;

	/** Commitment **/
	private BigInteger a;
	/** geratene Challenge **/
	private int c_strich;
	/** Challenge des Verifizierers **/
	private int c;
	/** Response **/
	private BigInteger z;

	/** Verifizierer **/
	private Verifier myVerifier;
	
	/** Zufallszahlengenerator */
	private Random rand;

	/** Laenge k des zu simulierenden Transkriptes **/
	private int k;
	/** Laenge des bereits simulierten Transkriptes **/
	private int n;
	
	/** Gibt an, ob Simulation abgeschlossen ist **/
	public boolean complete;
	
	

	/*-------------------------------------
	 ----- Konstruktor ------------------- 
	 ---------------------------------------*/
	public Simulator(GroupParam group, BigInteger h, int k) 
	{
		this.rand = new Random();

		this.p = group.p;
		this.q = group.q;
		this.g = group.g;
		this.x = h;
		this.k = k;
		n = 0;
		complete = false;
		
		
		inv_x = x.modPow(p.subtract(BigInteger.valueOf(2)), p);

		

	}

	/*-------------------------------------
	 ----- Hilfsmethoden ------------------- 
	 ---------------------------------------*/
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
	 * Legt Verifizierer für Black-Box-Simulation fest.
	 * 
	 * @param verifier	Verifizierer
	 */
	public void setVerifier(Verifier verifier) 
	{
		this.myVerifier = verifier;
	}

	/**
	 * Gibt Trankript der Beweisrunde zurück.
	 * 
	 * @return Transkript als String
	 */
	public String getView()
	{
		String returnVal = ("   (   " + a + " \t " + c + " \t " + z + "   )\n");
		return returnVal;
	}

	/*-------------------------------------
	 ----- Simulation ------------------- 
	 ---------------------------------------*/

	/**
	 * Die Methode implementiert die Simulation EINER Beweisrunde für gegebenes
	 * x. Die Länge des zu simulierenden Transkriptes ist dabei durch k
	 * gegeben.
	 * 
	 * Für den Zugriff auf den Verifizierer stehen folgende Methoden zur
	 * Verfügung: 
	 * 		setCommitment(BigInteger a) 
	 * 		getChallenge()
	 * 		setResponse(BigInteger z) 
	 * 
	 * gegebene Werte: 	Systemparameter (BigInteger) p,q,g
	 * 				   	(BigInteger) x, inv_x
	 * 					(int) k
	 * 
	 * zu berechnende Werte: 
	 * 		a,c_strich,z            Commitment, Challenge, Response
	 * 		n 						Länge des bereits simulierten Transkriptes
	 * 		addToTranskript 		auf true zu setzen, wenn die Runde dem Transkript hinzugefügt werden soll;
	 *                              auf false, wenn nicht.
	 * 		complete 				auf true setzen, wenn die komplette Simulation abgeschlossen ist;
	 *                              auf false, wenn dies noch nicht der Fall ist.
	 */
	public boolean simulate() 
	{
		boolean addToTranscript=false;

		// ********* HIER IMPLEMENTIEREN *********************

		/* z, c_strich, a berechnen */		
		
		do {
			Random rnd = new Random();
			c_strich = rnd.nextInt(2);

			do {
				z = new BigInteger(q.bitLength(), rnd);
			} while (z.compareTo(q) >= 0);

			a = g.modPow(z, p).multiply(inv_x.modPow(BigInteger.valueOf(c_strich), p)).mod(p);

			/***
			 * Commitment a an Verifizierer übergeben, und Challenge liefern
			 * lassen
			 ***/
			myVerifier.setCommitment(a);
			c = myVerifier.getChallenge();
		} while (c != c_strich);

		/* addToTranskript bestimmen */

		addToTranscript = true;
		myVerifier.setResponse(z);

		n++;
		if (n > k) {
			complete = true;
		}
		
		/* Bedingung für complete= true nicht vergessen ! */

		// ********* BIS HIER IMPLEMENTIEREN ******************

		gui.show_S_round(z, a, c_strich, addToTranscript);

		return addToTranscript;

	}

}
