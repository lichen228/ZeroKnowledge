package praktikum;

import gui.GUIObserver;

import java.math.BigInteger;

/**
 * Diese Klasse implementiert einen möglichen Man-In-The-Middle Angriff.
 * Gegenüber einem Beweiser erscheint MiddleMan als Verifier V und kann  
 * bei einem Angriff (attack=true) alle erhalten Daten in einem weiteren 
 * Beweis verwenden, in dem er dann als Beweiser P auftritt.
 */

public class MiddleMan extends Verifier {

	/** Beweiser in einem Man-In-The-Middle Angriff **/
	private Prover P;

	/** Verifizierer **/
	private Verifier V;

	/** Verifizierer der getäuscht werden soll **/
	private Verifier victim;

	//public String behave;
	
	private boolean attack;

	/*-------------------------------------
	 -----Konstruktoren -------------------- 
	 ---------------------------------------*/
	public MiddleMan(GroupParam group, Verifier victim) {
		super();
		this.V = new Verifier(group, 12);
		this.P = new Prover(group, 34);
		this.victim = victim;
	}

	public BigInteger getPublicKey() {
		return this.V.getPublicKey();
	}

	public void setProtocolType(String type) {
		this.P.setProtocolType(type);
		this.V.setProtocolType(type);
	}

	GUIObserver gui;

	public void addO(GUIObserver o) {
		gui = o;
		V.addO(o);
		P.addO(o);
	}

	/*public void setBehave(String behave) {
		this.behave = behave;
	}*/
	
	public void setBehave(boolean attack) {
		this.attack = attack;
	}

	public void toVerify(BigInteger assertion) {
		if (!attack) {
			this.V.toVerify(assertion);
		} else {
			victim.toVerify(assertion);
		}
	}

	public int getChallenge() {
		int c;
		if (!attack) {
			c = this.V.getChallenge();
		} else {
			c = victim.getChallenge();
		}

		return c;
	}

	public void setCommitment(BigInteger commitment) {
		if (!attack) {
			this.V.setCommitment(commitment);
		} else {
			victim.setCommitment(commitment);
		}

	}

	public void setCommitments(BigInteger commitment1, BigInteger commitment2) {
		if (!attack) {
			this.V.setCommitments(commitment1, commitment2);
		} else {
			victim.setCommitments(commitment1, commitment2);
		}
	}

	public void setResponse(BigInteger response) {
		if (!attack) {
			this.V.setResponse(response);
		} else {
			victim.setResponse(response);
		}
	}

	public void setResponses(BigInteger response1, BigInteger response2, int d) {
		
		if (!attack) {
			V.setResponses(response1, response2, d);
		} else {
			victim.setResponses(response1, response2, d);
		}
		
	}

	public void createKey() {
		this.V.createKey();
	}

	public int hasAccepted() {
		int returnVal;
		if (!attack) {
			returnVal = this.V.hasAccepted();
		} else {
			returnVal = victim.hasAccepted();
		}

		return returnVal;
	}

	public void toProof(Verifier verifier, BigInteger assertion) {
		this.P.toProof(verifier, assertion);
	}

	public void verify() {
		this.V.verify();
	}

	public void or_verify() {
		this.V.or_verify();
	}

	public String getView() {
		String returnVal;
		if (!attack) {
			returnVal = this.V.getView();
		} else {
			returnVal = victim.getView();
		}
		return returnVal;
	}

}
