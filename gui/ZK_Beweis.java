package gui;

import javax.swing.*;

import praktikum.GroupParam;
import praktikum.Prover;
import praktikum.Verifier;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.Random;

/**
 * Testprogramm für den Zero-Knowledge Beweis der Kenntnis eines diskreten 
 * Logarithmus (Aufgabe 3a) und dessen Simulation (Aufgabe 3b)
 * 
 */

public class ZK_Beweis extends JFrame implements WindowListener,ActionListener,GUIObserver {

	static final long serialVersionUID=1;
	
	private Container pane;

	private int WIDTH = 400;
	private int HEIGHT = 400;

	private GridBagLayout GL;
	private GridBagLayout GL2;
	private GridBagLayout GL3;
	private GridBagLayout GL6;

	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel_prot;
	private JLabel label_prover;
	private JLabel label_transcript;
	private JLabel label_verifier;
	private JLabel label_public;
	private JLabel label_p;
	private JLabel label_q;
	private JLabel label_g;
	private JLabel label_proof;

	private JTextArea text_p;
	private JTextArea text_q;
	private JTextArea text_g;
	private JTextArea text_prover;
	private JTextArea text_verifier;
	private JTextArea text_transcript;

	private JButton initial;
	private JButton proof_complete;
	private JButton proof_round;
	private JButton simulate;

	private JTextArea inP;
	private JTextArea inV;

	// Konstruktor
	public ZK_Beweis() {

		super("Zero-Knowledge Beweis der Kenntis eines diskreten Logarithmus");
		this.setSize(WIDTH, HEIGHT);
		this.addWindowListener(this);

		pane = this.getContentPane();
		GL = new GridBagLayout();
		pane.setLayout(GL);
		
		initDialog();
	
	}
	
	public void initDialog(){
		
		GridBagConstraints GB = new GridBagConstraints();

		GL2 = new GridBagLayout();
		panel1 = new JPanel();
		panel1.setLayout(GL2);

		GL3 = new GridBagLayout();
		panel2 = new JPanel();
		panel2.setLayout(GL3);

		GL6 = new GridBagLayout();
		panel_prot = new JPanel();
		panel_prot.setLayout(GL6);

		label_public = new JLabel("öffentlich:");
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 3;
		GB.gridheight = 1;
		GB.weightx = 0;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		panel1.add(label_public);
		GL2.setConstraints(label_public, GB);

		label_p = new JLabel("p:");
		GB.gridx = 3;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		panel1.add(label_p);
		GL2.setConstraints(label_p, GB);

		text_p = new JTextArea(1, 6);
		text_p.setEditable(false);
		GB.gridx = 4;
		GB.gridy = 0;
		GB.gridwidth = 3;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		panel1.add(text_p);
		GL2.setConstraints(text_p, GB);

		text_p.append(group.p.toString());

		label_q = new JLabel("q:");
		GB.gridx = 7;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		panel1.add(label_q);
		GL2.setConstraints(label_q, GB);

		text_q = new JTextArea(1, 6);
		text_q.setEditable(false);
		GB.gridx = 8;
		GB.gridy = 0;
		GB.gridwidth = 3;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		panel1.add(text_q);
		GL2.setConstraints(text_q, GB);

		text_q.append(group.q.toString());

		label_g = new JLabel("g:");
		GB.gridx = 11;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		panel1.add(label_g);
		GL2.setConstraints(label_g, GB);

		text_g = new JTextArea(1, 6);
		text_g.setEditable(false);
		GB.gridx = 12;
		GB.gridy = 0;
		GB.gridwidth = 3;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		panel1.add(text_g);
		GL2.setConstraints(text_g, GB);

		text_g.append(group.g.toString());

		// Teilpanel "öffentlich" hinzufügen
		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 4;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(panel1, GB);
		pane.add(panel1);

		// ## Panel2 ####
		label_prover = new JLabel("ALICE");
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.NORTH;
		GL3.setConstraints(label_prover, GB);
		panel2.add(label_prover);

		label_verifier = new JLabel("BOB");
		GB.gridx = 1;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.NORTH;
		GL3.setConstraints(label_verifier, GB);
		panel2.add(label_verifier);

		inP = new JTextArea(4, 6);
		inP.setEditable(false);
		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.5;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inP, GB);
		panel2.add(inP);

		inV = new JTextArea(4, 6);
		inV.setEditable(false);
		GB.gridx = 1;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.5;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		// GB.fill = GridBagConstraints.BOTH;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inV, GB);
		panel2.add(inV);

		initial = new JButton("Initialisieren");
		GB.gridx = 0;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		// GB.fill = GridBagConstraints.VERTICAL;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(initial, GB);
		panel2.add(initial);
		initial.setBackground(new Color(190, 195, 220));

		text_verifier = new JTextArea(13, 30);
		text_verifier.setEditable(false);
		JScrollPane jsv = new JScrollPane(text_verifier);
		jsv.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		GB.gridx = 1;
		GB.gridy = 3;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(0, 0, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(jsv, GB);
		panel2.add(jsv);

		text_prover = new JTextArea(13, 30);
		text_prover.setEditable(false);
		JScrollPane jsp = new JScrollPane(text_prover);
		jsp.setVerticalScrollBar(jsv.getVerticalScrollBar());
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		GB.gridx = 0;
		GB.gridy = 3;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(0, 10, 10, 0);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(jsp, GB);
		panel2.add(jsp);

		panel2.setBackground(new Color(222, 222, 229));

		// Beweisteil Panel2 hinzufügen
		GB.gridx = 0;
		GB.gridy = 2;
		GB.gridwidth = 4;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(panel2, GB);
		pane.add(panel2);

		// Panel Buttons
		label_proof = new JLabel("B E W E I S : ");
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.NORTH;
		GL6.setConstraints(label_proof, GB);
		panel_prot.add(label_proof);

		proof_round = new JButton("Runde");
		proof_round.setEnabled(false);
		GB.gridx = 1;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		// GB.fill = GridBagConstraints.VERTICAL;
		GB.fill = GridBagConstraints.VERTICAL;
		GB.anchor = GridBagConstraints.WEST;
		GL6.setConstraints(initial, GB);
		panel_prot.add(proof_round);

		proof_complete = new JButton("Komplett");
		proof_complete.setEnabled(false);
		GB.gridx = 2;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL6.setConstraints(proof_complete, GB);
		panel_prot.add(proof_complete);

		panel_prot.setBackground(new Color(222, 222, 229));
		GB.gridx = 0;
		GB.gridy = 3;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(panel_prot, GB);
		pane.add(panel_prot);

		simulate = new JButton("Simulation");
		simulate.setBackground(new Color(244, 201, 161));
		simulate.setEnabled(false);
		GB.gridx = 2;
		GB.gridy = 3;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL.setConstraints(simulate, GB);
		pane.add(simulate);

		label_transcript = new JLabel("  Transkript");
		GB.gridx = 0;
		GB.gridy = 4;
		GB.gridwidth = 3;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(label_transcript, GB);
		pane.add(label_transcript);

		text_transcript = new JTextArea(10, 40);
		text_transcript.setEditable(false);
		JScrollPane js2 = new JScrollPane(text_transcript);
		js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		GB.gridx = 0;
		GB.gridy = 5;
		GB.gridwidth = 4;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.SOUTH;
		GL.setConstraints(js2, GB);
		pane.add(js2);

		initial.addActionListener(this);
		proof_round.addActionListener(this);
		proof_complete.addActionListener(this);
		simulate.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	/*-----------------------------------
	 --  ActionListener Methoden --------
	 ------------------------------------*/

	public void actionPerformed(ActionEvent e) 
	{
		Object object = e.getSource();

		if (object == initial) 
		{
			text_prover.setText(null);
			text_verifier.setText(null);

			this.initialise();

			/* Buttons aktivieren */
			proof_complete.setEnabled(true);
			proof_round.setEnabled(true);
			simulate.setEnabled(true);
		} 
		else if (object == proof_round) 
		{
			this.proofRound();
		} 
		else if (object == proof_complete) 
		{
			this.proofComplete();
		}
		else if (object == simulate) 
		{
			try {
				/* Simulation in extra Fenster starten */
				ZK_Simulation simu = new ZK_Simulation(prover.getPublicKey());
				simu.setLocationRelativeTo(null);
				int roundsToSimulate = 17; // Standardwert
				if(round > 1) {
					// simuliere soviele Runden, wie vorher auch echt bewiesen wurden.
					roundsToSimulate = round;
				}
				simu.initialisiere(group, prover.getPublicKey(), roundsToSimulate - 1);
				simu.setVisible(true);
			} catch (Exception e2) {
				System.out.println(e2);
				System.exit(1);
			}
		}
	}

	/*-----------------------------------
	 --  WindowListener Methoden --------
	 ------------------------------------*/
	public void windowActivated(WindowEvent event) {}
	public void windowClosed(WindowEvent event) {}
	public void windowClosing(WindowEvent event) {}
	public void windowDeactivated(WindowEvent event) {}
	public void windowDeiconified(WindowEvent event) {}
	public void windowIconified(WindowEvent event) {}
	public void windowOpened(WindowEvent event) {}

	/*-----------------------------------
	 --  MAIN Methode -------------------
	 ------------------------------------*/
	
	private static GroupParam group;

	private Prover prover;
	private Verifier verifier;
	
	private int round;


	public static void main(String args[]) 
	{
		try {
			/* Systemparameter erzeugen */
			group = new GroupParam(9);
			ZK_Beweis fenster = new ZK_Beweis();
			fenster.setVisible(true);

		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public void initialise() 
	{
	
		Random rand = new Random();
		this.prover = new Prover(group, rand.nextInt());
		this.verifier = new Verifier(group, 123);

		/* GUI Oberfläche Observer hinzufügen */
		verifier.addO(this);
		prover.addO(this);

		/* Verifizierer und zu beweisende Behauptung wird festgelegt */
		prover.toProof(verifier, prover.getPublicKey());

		inP.append("\n\n  Ich kenne w, so dass g^w = " + prover.getPublicKey()+ " !");
		inV.setText("  Öffentlicher Schlüssel von Alice: x = "+ prover.getPublicKey() + " \n\n");
		inV.append("  Kennt Beweiser w, so dass g^w = " + prover.getPublicKey()	+ " ?");
		inV.append("\n  --> Dann ist Beweiser = Alice");

		text_transcript.append("  -------  Beweistranskript für x = "
				+ prover.getPublicKey() + "  ------\n");

		/* Rundenzähler zurücksetzten */
		round = 1;
	}

	
	public void proofComplete() 
	{
		/*
		 * solange sich Verifizierer im Zustand 0 befinden, wird Beweis
		 * fortgesetzt
		 */
		while (verifier.hasAccepted() == 0) {
			proofRound();
		}
		proof_complete.setEnabled(false);
	}


	public void proofRound() 
	{
		text_prover.append(" +++++++  Runde " + round + "  ++++++++" + "\n\n");
		text_verifier.append(" +++++++  Runde " + round + "  ++++++++" + "\n\n\n");

		/* Beweiser startet Beweisrunde */
		prover.proof();

		text_transcript.append(verifier.getView());
		round++;
	}

	
	/*
	 * ----------------------------------------------------- 
	 * ---- GUI Ausgaben ----------------------------------- 
	 * -----------------------------------------------------
	 */

	public void show_P_secret(BigInteger w, BigInteger h) {
		inP.setText(" SCHLÜSSEL >> Geheim: w = " + w + "     Öffentlich: x = "+ h);
	}

	public void show_P_commitment(BigInteger s, BigInteger a) {
		text_prover.append("  wähle s = " + s + "   sende a = g^s = " + a+ "\n\n");

	}

	public void show_P_response(int c, BigInteger z) {
		if (c == 0)
			text_prover.append("  c= " + c + " >> sende z = s = " + z + "\n\n\n\n");
		else
			text_prover.append("  c= " + c + " >> sende z = s + w = " + z + "\n\n\n\n");

	}

	public void show_V_challenge(BigInteger a, int c) {
		text_verifier.append("  a = " + a + "    sende Challenge c = " + c + "\n\n");

	}

	public void show_V_verify(int c, BigInteger z, boolean accept, double prob) {
		String acc = "FALSE";
		if (accept)
			acc = "TRUE";
		if (c == 0) {
			text_verifier.append("  z = " + z + "  überprüfe ob g^z = a  >> "+ acc);
		} else {
			text_verifier.append("  z = " + z + "  überprüfe ob g^z = a*x  >> "	+ acc);
		}

		text_verifier.append("\n  >>> überzeugt: " + prob * 100 + " %\n\n");

	}

	
	public void show_S_round(BigInteger z, BigInteger a, int c,	boolean addToTranscript) {}

	public void show_P_commitment(BigInteger s, BigInteger a, BigInteger t,	int d, BigInteger b) {}

	public void show_P_response(int c, BigInteger z, int d, BigInteger y) {}

	public void show_V_verify(int c, BigInteger z, int d, BigInteger t,	boolean accept1, boolean accept2, double prob) {}

	public void show_V_challenge(BigInteger a, BigInteger b, int c) {}

}
