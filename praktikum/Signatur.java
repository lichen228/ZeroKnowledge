package praktikum;
import java.math.BigInteger;
import java.util.*;

/**
 * Diese Klasse stellt die Datenstruktur einer Signatur auf 
 * Basis eines nicht-interaktiven Zero-Knowledge Beweises dar.
 */

public class Signatur {
	
	public List<BigInteger> responses;
	public String challenges;
	
	public Signatur (String challenges, List<BigInteger> responses)
	{
		this.challenges = challenges;
		this.responses= responses;
	}
}
