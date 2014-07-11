package gui;
import java.math.BigInteger;

/**
 * Das Interface dient der Darstellung der Berechnungen der 
 * einzelnen Beweisteilnehmer in der GUI 
 * 
 */
public interface GUIObserver {
	
	/* PROVER */
	
	public void show_P_secret(BigInteger w, BigInteger h);
	
	public void show_P_commitment(BigInteger s, BigInteger a);
	
	public void show_P_commitment(BigInteger s, BigInteger a, BigInteger t, int d, BigInteger b);
	
	public void show_P_response(int c, BigInteger z);
	
	public void show_P_response(int c, BigInteger z, int d, BigInteger y);
	
	
	/* VERIFIER */
	
	public void show_V_challenge(BigInteger a, int c);
	
	public void show_V_challenge(BigInteger a, BigInteger b, int c);
	
	public void show_V_verify(int c, BigInteger z, boolean accept, double prob);
	
	public void show_V_verify(int c,BigInteger z, int d, BigInteger t, boolean accept1, boolean accept2, double prob);
	
	
	/* SIMULATOR */
	
	public void show_S_round(BigInteger z, BigInteger a, int c, boolean addToTranscript);
	

}
