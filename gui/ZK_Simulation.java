package gui;

import java.math.BigInteger;
import javax.swing.*;

import praktikum.GroupParam;
import praktikum.Simulator;
import praktikum.Verifier;

import java.awt.*;
import java.awt.event.*;

/**
 * Diese Klasse implementiert die GUI für die Simulation eines Zero-Knowledge Beweises.
 * Wird von ZK_Beweis gestartet.
 * 
 */

public class ZK_Simulation extends JFrame implements WindowListener,ActionListener, GUIObserver {

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
	private JLabel label_simulator;
	private JLabel label_transcript;
	private JLabel label_verifier;
	private JLabel label_public;
	private JLabel label_p;
	private JLabel label_q;
	private JLabel label_g;
	private JLabel label_panelSim;

	private JTextArea text_p;
	private JTextArea text_q;
	private JTextArea text_g;
	private JTextArea text_simulator;
	private JTextArea text_verifier;
	private JTextArea inS;
	private JTextArea inV;
	private JTextArea text_transcript;

	private JButton button_round;
	private JButton button_complete;


	/*-----------------------------------
	 --  Konstruktor --------------------
	 ------------------------------------*/
	public ZK_Simulation(BigInteger x) 
	{
		super("Simulator für x = " + x);
		this.setSize(WIDTH, HEIGHT);
		this.addWindowListener(this);

		pane = this.getContentPane();
		GL = new GridBagLayout();
		pane.setLayout(GL);
		
		initDialog();
	}
	
	
	public void initDialog()
	{
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

		// Teilpanel "öffentlich hinzufügen
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 3;
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

		// ## Panel2
		label_simulator = new JLabel("SIMULATOR");
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
		GL3.setConstraints(label_simulator, GB);
		panel2.add(label_simulator);

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

		inS = new JTextArea(2, 6);
		inS.setEditable(false);
		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.5;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		// GB.fill = GridBagConstraints.BOTH;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inS, GB);
		panel2.add(inS);

		inV = new JTextArea(2, 6);
		inV.setEditable(false);
		GB.gridx = 1;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.5;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		// GB.fill = GridBagConstraints.BOTH;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inV, GB);
		panel2.add(inV);

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

		text_simulator = new JTextArea(13, 30);
		text_simulator.setEditable(false);
		JScrollPane jsp = new JScrollPane(text_simulator);
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

		// Beweisteil hinzufügen
		GB.gridx = 0;
		GB.gridy = 2;
		GB.gridwidth = 4;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(panel2, GB);
		pane.add(panel2);

		label_panelSim = new JLabel("S I M U L A T I O N : ");
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 2;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 20;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.VERTICAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL6.setConstraints(label_panelSim, GB);
		panel_prot.add(label_panelSim);

		button_round = new JButton("Runde");
		GB.gridx = 2;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL6.setConstraints(button_round, GB);
		panel_prot.add(button_round);

		button_complete = new JButton("Komplett");
		GB.gridx = 3;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL6.setConstraints(button_complete, GB);
		panel_prot.add(button_complete);

		panel_prot.setBackground(new Color(222, 222, 229));
		GB.gridx = 0;
		GB.gridy = 3;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 40;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		GL.setConstraints(panel_prot, GB);
		pane.add(panel_prot);

		// ### Buttons

		label_transcript = new JLabel("Transkript ");
		GB.gridx = 0;
		GB.gridy = 4;
		GB.gridwidth = 4;
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

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		button_round.addActionListener(this);
		button_complete.addActionListener(this);

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
	 --  ActionListener Methoden ----------
	 ------------------------------------*/

	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();

		if (object == button_round) 
		{
			this.simulationRound();
		} 
		else if (object == button_complete) 
		{
			this.simulationComplete();
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

	
	
	

	private Simulator simulator;

	private Verifier verifier;

	/* Anzahl der durchgeführten Simulationsrunden */
	private int round;

	

	/* Simulator und Verifier mit öffentlichen Werten des Beweisers initialisieren */
	public void initialisiere(GroupParam group, BigInteger x, int length) 
	{
		simulator = new Simulator(group, x, length);
		verifier = new Verifier(group);

		verifier.addO(this);
		simulator.addO(this);

		/* zu simulierende Behauptung an Verifizierer übergeben */
		verifier.toVerify(x);
		simulator.setVerifier(verifier);

		inS.setText("  Simuliere Beweis für x = " + x
				+ "\n  Länge des Transkripts:  " + length + " Runden");
		inV.setText(" Öffentlicher Schlüssel von Alice: x = " + x
				+ " \n Kennt Beweiser w , so dass g^w = x ?");

		/* öffentliche Systemparameter ausgeben */
		text_p.append(group.p.toString());
		text_q.append(group.q.toString());
		text_g.append(group.g.toString());

		/* Rundenanzahl zurücksetzten */
		round = 1;

	}

	/**
	 * Die Methode startet solange Simulationsrunden bis der Simulator in den
	 * Zustand complete=TRUE übergeht.
	 */
	public void simulationComplete() 
	{
		while (!simulator.complete) 
		{
			simulationRound();
		}
		button_complete.setEnabled(false);
	}

	/**
	 * Die Methode startet eine einzelne Simulationsrunde.
	 * 
	 */
	public void simulationRound() {
		text_simulator.append(" +++++++  Runde " + round + "  ++++++++"+ "\n\n");
		text_verifier.append(" +++++++  Runde " + round + "  ++++++++"+ "\n\n\n\n");

		if (simulator.simulate()) {
			text_transcript.append(simulator.getView());
		}

		round++;
	}

	/*
	 * ----------------------------------------------------- 
	 * ---- GUI Ausgaben ----------------------------------- 
	 * -----------------------------------------------------
	 */

	private BigInteger v_a, v_z;

	private int v_c;

	private boolean v_accept;

	private double v_prob;

	public void show_S_round(BigInteger z, BigInteger a, int c, boolean accept) {
		/* erster Schritt Simulator */
		if (c == 0) {
			text_simulator.append(" wähle z = " + z
					+ "   rate Challenge c' = 0 \n sende a = g^z = " + a
					+ " \n\n");
		} else {
			text_simulator.append(" wähle z = " + z
					+ "   rate Challenge c' = 1 \n sende a = (g^z) / x = " + a
					+ "\n\n");
		}

		/* Verifizierer aufrufen */
		text_verifier.append("  a = " + v_a + "    sende Challenge c = " + v_c
				+ "\n\n");

		/* Simulator überprüft Challenge */
		if (accept) {
			text_simulator.append(" Challenge richtig geraten >> sende z = "
					+ z + "\n\n\n\n");

			String acc = "FALSE";
			if (v_accept) {
				acc = "TRUE";
			}

			if (v_c == 0) {
				text_verifier.append("  z = " + v_z
						+ "  überprüfe ob g^z = a  >> " + acc);
			} else {
				text_verifier.append("  z = " + v_z
						+ "  überprüfe ob g^z = a*x  >> " + acc);
			}

			text_verifier.append("\n  >>> überzeugt: " + v_prob * 100
					+ " %\n\n");
		} else {
			text_simulator.append(" Challenge falsch geraten, Runde abbrechen."
					+ "\n\n");
			text_verifier.append("\n");
		}

	}

	public void show_V_challenge(BigInteger a, int c) {
		this.v_a = a;
		this.v_c = c;
	}

	public void show_V_verify(int c, BigInteger z, boolean accept, double prob) {
		this.v_z = z;
		this.v_accept = accept;
		this.v_prob = prob;
	}

	public void show_S_commitment(BigInteger z, BigInteger a, int c) {
		if (c == 0)
			text_simulator.append(" wähle z = " + z
					+ "   rate Challenge c' = 0 \n sende a = g^z = " + a
					+ " \n\n");
		else
			text_simulator.append(" wähle z = " + z
					+ "   rate Challenge c' = 1 \n sende a = (g^z) / x = " + a
					+ "\n\n");
	}

	public void show_S_response(BigInteger z, boolean accept) {
		if (accept)
			text_simulator.append(" Challenge richtig geraten >> sende z = "
					+ z + "\n\n\n\n");
		else {
			text_simulator.append(" Challenge falsch geraten, Runde abbrechen."
					+ "\n\n");
			text_verifier.append("\n");
		}
	}

	public void show_P_secret(BigInteger w, BigInteger h) {}
	
	public void show_P_commitment(BigInteger s, BigInteger a) {}

	public void show_P_commitment(BigInteger s, BigInteger a, BigInteger t,	int d, BigInteger b) {	}

	public void show_P_response(int c, BigInteger z) {}
	
	public void show_P_response(int c, BigInteger z, int d, BigInteger y) {}

	public void show_V_verify(int c, BigInteger z, int d, BigInteger t,	boolean accept1, boolean accept2, double prob) {}

	public void show_V_challenge(BigInteger a, BigInteger b, int c) {}

}
