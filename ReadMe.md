Komplexpraktikum Kryptographie und Datensicherheit
====
Versuch 10: Zero-Knowledge-Verfahren (03.07.)
----

####1. Aufgabe1
  
Zero-Knowledge-Beweis

Prover:
```java
    public void sendCommitment() 
    {
        try {
            Random rnd = new Random();
            do {
                s = new BigInteger(q.bitLength(), rnd);
            } while (s.compareTo(q) >= 0);
            
            a = g.modPow(s, p);

            /* Commitment an Verifizierer übergeben */
            myVerifier.setCommitment(a);

            /* GUI-Ausgabe */
            gui.show_P_commitment(s, a);

        } catch (Exception e) {
            System.out.println("Exception in Prover.sendCommitment() .." + e);
        }
    }
```
```java
    public void sendResponse() 
    {
        try {

            if(c==0) {
                z = s;
            }
            else {
                z = s.add(w);
            }

            /* Response wird an Verifizierer übergeben */
            myVerifier.setResponse(z);

            /* GUI-Ausgabe */
            gui.show_P_response(c, z);
        } catch (Exception e) {
            System.out.println("Exception in Prover.sendResponse() .." + e);
        }
    }
```
Verifier:
```java
    public void verify() 
    {
        try {
            boolean acceptRound = false;

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
                        
            // GUI Ausgabe
            gui.show_V_verify(c, z, acceptRound, 1 - errorProb);
        } catch (Exception e) {
            System.out.println("Exception in Verifier.verify() .." + e);
        }

    }
```
####2. Aufgabe2

Nicht-interaktive Zero-Knowledge

Prover:
```java
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

            /** * Signatur ergibt sich aus Challenges + Liste aller Antworten ** */
            sig = new Signatur(challenges, z_list);

        } catch (Exception e) {
            System.out.println("NIProver - sign: " + e);
        }

        return sig;
    }
```
Verifier:
```java
    public boolean verify(String message, Signatur sig, BigInteger x) {

        /** Liste der zu berechneten Commitments */
        List<BigInteger> b_list = new ArrayList<BigInteger>();

        /** wird Signatur akzeptiert */
        boolean accept = false;

        try {
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
            
        } catch (Exception e) {
            System.out.println("NIVerifier - verify: " + e);
        }

        return accept;
    }
```
####3. Aufgabe3
  
Man-In-The-Middle-Angriffe und OR-Version

Prover
```java
    public void or_sendCommitment() 
    {
        try {
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
            
            /* Commitment an Verifizierer übergeben */
            myVerifier.setCommitments(a, b);

            /* GUI-Ausgabe */
            gui.show_P_commitment(s, a, y, d, b);

        } catch (Exception e) {
            System.out.println("Exception in Prover.or_sendCommitment() .." + e);
        }

    }
```
```java
    public void or_sendResponse() 
    {
        try {

            /* Challenge c als XOR-Summe der Challenge C des Verifizierers und d berechnen */
            
            c = C^d;
            
            /* Antwort z für "echten" Beweis berechnen */
                
            if(c==0) {
                z = s;
            }
            else {
                z = s.add(w_A).mod(p);
            }
            
            /* Antworten z,y und Challenge d an Verifizierer übergeben */
            myVerifier.setResponses(z, y, d);

            /* GUI-Ausgabe */
            gui.show_P_response(c, z, d, y);

        } catch (Exception e) {
            System.out.println("Exception in Prover.or_sendResponse() .." + e);
        }
    }
```
Verifier:
```java
    public void or_verify() 
    {
        try {
            boolean acceptRound1 = false;
            boolean acceptRound2 = false;
            
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
            
            // GUI-Ausgabe
            gui.show_V_verify(c, z, d, y, acceptRound1, acceptRound2,1 - errorProb);
        } catch (Exception e) {
            System.out.println("Exception in Verifier.or_verify() .." + e);
        }
    }
```
