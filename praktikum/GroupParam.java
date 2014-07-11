package praktikum;
import java.math.BigInteger;
import java.util.Random;

/**
 * Diese Klasse implementiert die Generierung der öffentlichen Systemparameter p,q,g 
 * für einen Zero-Knowledge Beweis auf Basis diskreter Logarithmen.
 * 
 */

public class GroupParam {
	/** Primzahl p * */
	public BigInteger p;

	/** Primzahl q, ist Primteiler von p-1 * */
	public BigInteger q;

	/** Generator der eindeutigen Unterguppe in Zp* mit Ordnung q * */
	public BigInteger g;

	public GroupParam(int k) {
		Random rand = new Random();
		int trials;
		BigInteger i;

		/* Wahl der Primzahlen p,q mit q|(p-1) */
		do {
			/* Primzahl q wählen */
			this.q = new BigInteger(k, 200, rand);
			trials = 0;
			do {
				i = new BigInteger(5, rand);
				/* p berechnen */
				p = (q.multiply(i)).add(BigInteger.valueOf(1));
				trials++;
				System.out.println("i: " + i + "  p: " + p);
				/* Test ob p prim */
			} while (!(p.isProbablePrime(200)) && (trials < 100));

		} while (!(p.isProbablePrime(200)));

		/* Generator g>1 erzeugen */
		BigInteger g1;
		do {
			/* Hilfswert g1 ist Element der multiplikativen Gruppe Zp* */
			g1 = new BigInteger(p.bitLength(), 200, rand);
			g1 = g1.remainder(p);

			/* g1^((p-1)/q) erzeugt eindeutige Untergruppe der Ordnung q */
			BigInteger order = (p.subtract(BigInteger.valueOf(1))).divide(q);
			this.g = g1.modPow(order, p);
		} while (g.compareTo(BigInteger.ONE) == 0);

	}
}