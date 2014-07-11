package gui;

import javax.swing.*;

import praktikum.GroupParam;
import praktikum.MiddleMan;
import praktikum.Prover;
import praktikum.Verifier;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
//import java.util.ResourceBundle;

/**
 * Testprogramm für den Man-In-The-Middle Angriff und die Implementierungen
 * von Aufgabe 6.
 * 
 */

public class ZK_Attack extends JFrame implements WindowListener, ActionListener,GUIObserver {

	static final long serialVersionUID=1;
	
	static GroupParam group;

	

	private Container pane;

	private int WIDTH = 800;
	private int HEIGHT = 900;

	private GridBagLayout GL;
	private GridBagLayout GL2;
	private GridBagLayout GL3;
	private GridBagLayout GL4;
	private GridBagLayout GL5;
	private GridBagLayout GL6;

	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel_bob;
	private JPanel panel_prot;
	private JPanel panel_proof;

	private JScrollPane jsa;
	private JScrollPane jsb;
	private JScrollPane jsc;

	private JLabel labelA;
	private JLabel labelB;
	private JLabel labelC;
	private JLabel label_transcript;
	private JLabel label_prot;
	private JLabel label_proof;
	private JLabel label_group;
	private JLabel label_p;
	private JLabel label_q;
	private JLabel label_g;

	private JTextArea text_p;
	private JTextArea text_q;
	private JTextArea text_g;
	private JTextArea textA;
	private JTextArea textB;
	private JTextArea textC;
	private JTextArea inA;
	private JTextArea inB;
	private JTextArea inC;
	private JTextArea inA2;
	private JTextArea inB2;
	private JTextArea inC2;
	private JTextArea text_transcript;

	private JButton button_initial;
	private JButton button_complete;
	private JButton button_round;
	private JButton button_change;
	private ButtonGroup buttons;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private ButtonGroup buttons2;
	private JRadioButton norm;
	private JRadioButton ext;

	// Konstruktor
	public ZK_Attack() 
	{

		super("Zero-Knowledge Beweis  ||  Man-In-The-Middle Angriff");
		this.setSize(WIDTH, HEIGHT);
		this.addWindowListener(this);

		pane = this.getContentPane();
		GL = new GridBagLayout();
		pane.setLayout(GL);

		initDialog();

	}

	public void initDialog() {

		GridBagConstraints GB = new GridBagConstraints();

		GL2 = new GridBagLayout();
		panel1 = new JPanel();
		panel1.setLayout(GL2);

		GL3 = new GridBagLayout();
		panel2 = new JPanel();
		panel2.setLayout(GL3);

		GL4 = new GridBagLayout();
		panel_bob = new JPanel();
		panel_bob.setLayout(GL4);

		GL5 = new GridBagLayout();
		panel_prot = new JPanel();
		panel_prot.setLayout(GL5);

		GL6 = new GridBagLayout();
		panel_proof = new JPanel();
		panel_proof.setLayout(GL6);

		label_group = new JLabel("öffentlich:");
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
		panel1.add(label_group);
		GL2.setConstraints(label_group, GB);

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

		// Panel Protokoll auswählen
		label_prot = new JLabel("PROTOKOLL: ");
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
		GL5.setConstraints(label_prot, GB);
		panel_prot.add(label_prot);

		norm = new JRadioButton("normal");
		ext = new JRadioButton("erweitert");
		norm.setBackground(new Color(190, 195, 220));
		ext.setBackground(new Color(190, 195, 220));

		norm.setSelected(true);

		buttons2 = new ButtonGroup();
		buttons2.add(norm);
		buttons2.add(ext);

		GB.gridx = 1;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		GL5.setConstraints(norm, GB);
		panel_prot.add(norm);

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
		GB.anchor = GridBagConstraints.WEST;
		GL5.setConstraints(ext, GB);
		panel_prot.add(ext);

		button_initial = new JButton("Initialisieren");
		GB.gridx = 4;
		GB.gridy = 0;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 12;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		GL5.setConstraints(button_initial, GB);
		panel_prot.add(button_initial);

		panel_prot.setBorder(BorderFactory.createEtchedBorder());
		panel_prot.setBackground(new Color(190, 195, 220));
		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 3;
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

		// ## Panel2 - Beweisteil
		labelA = new JLabel("ALICE");
		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 5, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.NORTH;
		GL3.setConstraints(labelA, GB);
		panel2.add(labelA);

		labelB = new JLabel("BOB");
		GB.gridx = 1;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 5, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.NORTH;
		GL3.setConstraints(labelB, GB);
		panel2.add(labelB);

		labelC = new JLabel("CAROL");
		GB.gridx = 2;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 5, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.NORTH;
		GL3.setConstraints(labelC, GB);
		panel2.add(labelC);

		inA = new JTextArea(2, 6);
		inA.setEditable(false);
		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inA, GB);
		panel2.add(inA);

		inB = new JTextArea(2, 6);
		inB.setEditable(false);
		GB.gridx = 1;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inB, GB);
		panel2.add(inB);

		inC = new JTextArea(2, 6);
		inC.setEditable(false);
		GB.gridx = 2;
		GB.gridy = 1;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inC, GB);
		panel2.add(inC);

		inA2 = new JTextArea(1, 6);
		inA2.setEditable(false);
		GB.gridx = 0;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inA2, GB);
		panel2.add(inA2);

		inB2 = new JTextArea(1, 6);
		inB2.setEditable(false);
		GB.gridx = 1;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inB2, GB);
		panel2.add(inB2);

		inC2 = new JTextArea(1, 6);
		inC2.setEditable(false);
		GB.gridx = 2;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(inC2, GB);
		panel2.add(inC2);

		// BOB GOOD EVIL

		rb1 = new JRadioButton("gut");
		rb2 = new JRadioButton("böse");

		rb1.setSelected(true);

		buttons = new ButtonGroup();
		buttons.add(rb1);
		buttons.add(rb2);

		GB.gridx = 0;
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
		GL4.setConstraints(rb1, GB);
		panel_bob.add(rb1);

		GB.gridx = 1;
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
		GL4.setConstraints(rb2, GB);
		panel_bob.add(rb2);

		button_change = new JButton("ändern");
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
		GL4.setConstraints(button_change, GB);
		panel_bob.add(button_change);

		rb1.setEnabled(false);
		rb2.setEnabled(false);
		button_change.setEnabled(false);

		rb1.setBackground(new Color(222, 222, 229));
		rb2.setBackground(new Color(222, 222, 229));
		button_change.setBackground(new Color(222, 222, 229));

		panel_bob.setBackground(new Color(222, 222, 229));

		// Teilpanel panel_bob binzufügen
		GB.gridx = 1;
		GB.gridy = 3;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 0.1;
		GB.insets = new Insets(0, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(panel_bob, GB);
		panel2.add(panel_bob);

		textB = new JTextArea(15, 28);
		textB.setEditable(false);
		jsb = new JScrollPane(textB);
		jsb.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		GB.gridx = 1;
		GB.gridy = 4;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 1;
		GB.insets = new Insets(0, 0, 10, 0);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(jsb, GB);
		panel2.add(jsb);

		textA = new JTextArea(15, 28);
		textA.setEditable(false);
		jsa = new JScrollPane(textA);

		jsa.setVerticalScrollBar(jsb.getVerticalScrollBar());
		jsa.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsa.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		GB.gridx = 0;
		GB.gridy = 4;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 1;
		GB.insets = new Insets(0, 10, 10, 0);
		GB.ipadx = 0;
		GB.ipady = 00;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(jsa, GB);
		panel2.add(jsa);

		textC = new JTextArea(15, 28);
		textC.setEditable(false);
		jsc = new JScrollPane(textC);
		jsc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// jsc.setVerticalScrollBar(jsv.getVerticalScrollBar());
		textC.setBackground(new Color(222, 222, 229));

		GB.gridx = 2;
		GB.gridy = 4;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.33;
		GB.weighty = 1;
		GB.insets = new Insets(0, 0, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.CENTER;
		GL3.setConstraints(jsc, GB);
		panel2.add(jsc);

		panel2.setBackground(new Color(222, 222, 229));

		// Beweisteil hinzufügen
		GB.gridx = 0;
		GB.gridy = 3;
		GB.gridwidth = 3;
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

		// Beweis-Button Panel

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
		panel_proof.add(label_proof);

		button_round = new JButton("Runde");
		button_round.setEnabled(false);
		GB.gridx = 1;
		GB.gridy = 0;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.VERTICAL;
		GB.anchor = GridBagConstraints.WEST;
		GL6.setConstraints(button_round, GB);
		panel_proof.add(button_round);

		button_complete = new JButton("Komplett");
		button_complete.setEnabled(false);
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
		GL6.setConstraints(button_complete, GB);
		panel_proof.add(button_complete);

		panel_proof.setBackground(new Color(222, 222, 229));
		GB.gridx = 0;
		GB.gridy = 4;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 150;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		GL.setConstraints(panel_proof, GB);
		pane.add(panel_proof);

		// Panel Transkript

		label_transcript = new JLabel("   Transkript");

		GB.gridx = 0;
		GB.gridy = 5;
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
		
		text_transcript.setTabSize(14);
		GB.gridx = 0;
		GB.gridy = 6;
		GB.gridwidth = 3;
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

		button_change.addActionListener(this);
		button_initial.addActionListener(this);
		button_round.addActionListener(this);
		button_complete.addActionListener(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		System.out.println(this.getWIDTH() + "  height: " + this.getHEIGHT());
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

	private boolean initAll = true;

	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();

		/*----initialisierem ----*/
		if (object == button_initial) {
			/* normales Protokoll */
			if (norm.isSelected()) {
				if (!extended) {
					initAll = true;
				}

				extended = false;
				protocolType = "normal";

			}
			/* OR-Protokoll */
			else {
				if (extended) {
					initAll = true;
				}
				extended = true;
				protocolType = "erweitert";

			}

			/* Textfelder zurücksetzten */
			textA.setText(null);
			textB.setText(null);
			textC.setText(null);

			if (!attack) {
				text_transcript.append("  ------- Transkript für "
						+ protocolType
						+ "es Protokoll,  Bob = gut ---------------------\n");
			} else {
				text_transcript.append("  ------- Transkript für "
						+ protocolType
						+ "es Protokoll,  Bob = böse ---------------------\n");
			}

			/* Buttons aktualisieren */
			rb1.setEnabled(true);
			rb2.setEnabled(true);
			button_change.setEnabled(true);
			button_complete.setEnabled(true);
			button_round.setEnabled(true);

			/* Methode zum initialisieren der Teilnehmer aufrufen */
			this.initialise();
			if (initAll) {
				initAll = false;
			}

		}
		/* --- Beweisrunde ---- */
		else if (object == button_round) {
			this.proofRound();
		}
		/* ---- kompletter Beweis ---- */
		else if (object == button_complete) {
			this.proofComplete();
		}
		/* ----- Verhalten von Bob soll sich ändern ---- */
		else if (object == button_change) {
			/* BOB = GOOD */
			if (rb1.isSelected()) {
				/* Zustand hat sich geändert */
				if (attack) {
					textA.setText(null);
					textB.setText(null);
					textC.setText(null);

					textC.setBackground(new Color(222, 222, 229));

					text_transcript
							.append("  ------- Transkript für "
									+ protocolType
									+ "es Protokoll,  Bob = gut ---------------------\n");

					jsb.setVerticalScrollBar(jsb.createVerticalScrollBar());
					jsa.setVerticalScrollBar(jsb.getVerticalScrollBar());

					
					bob.setBehave(false);
					attack = false;

					/* Methode zum Initialisieren der Teilnehmer aufrufen */
					this.initialise();
				}

			}
			/* BOB = EVIl */
			else {
				if (!attack) 
				{
					textA.setText(null);
					textB.setText(null);
					textC.setText(null);

					textC.setBackground(new Color(255, 255, 255));

					text_transcript
							.append("  ------- Transkript für "
									+ protocolType
									+ "es Protokoll,  Bob = böse ---------------------\n");

					jsb.setVerticalScrollBar(jsc.getVerticalScrollBar());
					jsa.setVerticalScrollBar(jsb.getVerticalScrollBar());

					bob.setBehave(true);
					attack = true;

					/* Methode zum Initialisieren der Teilnehmer aufrufen */
					this.initialise();
				}
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

	private Prover alice;

	private Verifier carol;

	private MiddleMan bob;

	/* extended = true -> OR-Beweis */
	private boolean extended;

	private String protocolType = "normal";

	// private static ResourceBundle messages;
	private boolean attack = false;
	
	/* Anzahl der durchgeführten Beweisrunden */
	private int round;


	/*-----------------------------------
	 --  MAIN Methode ------------------
	 ------------------------------------*/
	public static void main(String args[]) 
	{
		try {
			/* Systemparameter erzeugen */
			group = new GroupParam(9);
			ZK_Attack fenster = new ZK_Attack();
			fenster.setVisible(true);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public void initialise() 
	{
		/* komplett initialisieren */
		if (initAll) 
		{
			
			alice = new Prover(group, 123);
			carol = new Verifier(group, 456);
			bob = new MiddleMan(group, carol);

			bob.setBehave(attack);
			
			bob.addO(this);
			alice.addO(this);
			carol.addO(this);

			inA.setTabSize(5);
			inB.setTabSize(4);
			inC.setTabSize(4);

			/* öffentliche Schlüssel der Verifizierer werden nur einmal erzeugt */
			carol.createKey();
			bob.createKey();
		}

		textA.setText(null);
		textB.setText(null);
		textC.setText(null);

		button_complete.setEnabled(true);
		round = 1;
		/* ERWEITERTES PROTOKOLL */
		if (extended) 
		{
			alice.setProtocolType("extended");
			bob.setProtocolType("extended");
			carol.setProtocolType("extended");

			/* Schlüssel anzeigen */
			inB.setText("\t  öffentlicher Schlüssel  xB = "
					+ bob.getPublicKey());
			inC.setText("\t  öffentlicher Schlüssel  xC = "
					+ carol.getPublicKey());

			/* Alice initialisiert Beweis gegenüber Bob */
			alice.toProof(bob, alice.getPublicKey());

			/* BOB=GOOD */
			if (!attack) 
			{
				inC2.setText(null);
				inA2.setText("\t Ich bin ALICE !\t ------->>");
				inB2.setText("\t Ist Beweiser = ALICE ?");
			}

			/* BOB=EVIL */
			else 
			{
				/* Bob initialisiert den selben Beweis mit Carol */
				bob.toProof(carol, alice.getPublicKey());

				inA2.setText("\t Ich bin ALICE !\t ------->>");
				inB2.setText("\t Ich bin ALICE !\t ------->>");
				inC2.setText("\t Ist Beweiser = ALICE ?");
			}
		}

		/* NORMALES PROTOKOLL */
		else 
		{
			alice.setProtocolType("normal");
			bob.setProtocolType("normal");
			carol.setProtocolType("normal");

			inB.setText(null);
			inC.setText(null);

			/* Alice initialisiert Beweis gegenüber Bob */
			alice.toProof(bob, alice.getPublicKey());

			// BOB=GOOD
			if (!attack) 
			{
				inC2.setText(null);
				inA2.setText("\t Ich bin ALICE !\t ------->>");
				inB2.setText("\t Ist Beweiser = ALICE ?");
			}

			// BOB=EVIL
			else 
			{
				/* Bob initialisiert den selben Beweis mit Carol */
				bob.toProof(carol, alice.getPublicKey());

				inA2.setText("\t Ich bin ALICE !\t ------->>");
				inB2.setText("\t Ich bin ALICE !\t ------->>");
				inC2.setText("\t Ist Beweiser = ALICE ?");

			}
		}
	}

	public void proofComplete() 
	{
		/* solange Bob weder den Beweis akzeptiert noch abgelehnt hat, 
		 * werden Beweisrunden durchgeführt */
		while (bob.hasAccepted() == 0) {
			proofRound();
		}
		button_complete.setEnabled(false);

	}


	
	public void proofRound() 
	{
		this.GUI_round();
	
		/* Alice führt Beweisrunde mit Bob durch */
		alice.proof();
	
		/* Sicht von Bob wird zum Transkript hinzugefügt */
		text_transcript.append(bob.getView());
		
		round++;
	}
	
	
	
	

	/***************************************************************************
	 * ************* GUI -Ausgaben **********************
	 **************************************************************************/
	public void GUI_round() {
		textA.append("  +++++++++  Runde " + round + "  ++++++++++++++++++++"
				+ "\n\n");
		textB.append("  +++++++++  Runde " + round + "  ++++++++++++++++++++"
				+ "\n\n\n");

		if (attack) {
			textC.append("  +++++++++  Runde " + round
					+ "  ++++++++++++++++++++" + "\n\n\n\n");
		}

	}

	public void show_P_secret(BigInteger w, BigInteger h) {
		inA.setText("\t  Schlüssel >> w = " + w + "  xA = " + h);
	}

	public void show_P_commitment(BigInteger s, BigInteger a) {
		textA.append("  wähle s = " + s + "   sende a = g^s = " + a + "\n\n");

		if (attack) {
			textB.append("    -------------------   a = " + a
					+ " weiterleiten   ---------->>\n\n");
			textA.append("\n\n");
		}
	}

	public void show_P_commitment(BigInteger s, BigInteger a, BigInteger t,
			int d, BigInteger b) {
		textA.append("  wähle s = " + s + "   sende a = " + a + "\n");
		textA.append("  wähle y = " + t + ", d = " + d + "  sende b = " + b
				+ "\n\n");
		textB.append("\n");

		if (attack) {
			textB.append("    ----------   a = " + a + " , b = " + b
					+ "  weiterleiten   --------->>\n\n");
			textA.append("\n\n");
			textC.append("\n");
		}
	}

	public void show_P_response(int c, BigInteger z) {
		if (c == 0) {
			textA.append("  c= " + c + " >> sende z = s = " + z + "\n\n\n");
		} else {
			textA.append("  c= " + c + " >> sende z = s + w = " + z + "\n\n\n");
		}

		textA.append("\n");

		if (attack) {
			textB.append("    ------------------   z = " + z
					+ " weiterleiten   ------------->>\n\n\n\n");
			textA.append("\n");
		}

	}

	public void show_P_response(int c, BigInteger z, int d, BigInteger y) {

		textA.append("  c = " + c + " >> sende z = " + z + " , d = " + d
				+ ", y = " + y + " \n\n\n\n");
		textA.append("\n");
		if (attack) {
			textB.append("    -------   z = " + z + " , d = " + d + ", y = "
					+ y + " weiterleiten  ------->>\n\n\n\n\n");
			textA.append("\n");
		}

	}

	public void show_V_challenge(BigInteger a, int c) {
		if (!attack) {
			textB.append("  a = " + a + "    sende Challenge c = " + c
							+ "\n\n");
		} else {
			textB.append("   <<----------------   c = " + c
					+ " weiterleiten  --------------------\n\n");
			textC.append("  a = " + a + "    sende Challenge c = " + c
							+ "\n\n");
		}

	}

	public void show_V_challenge(BigInteger a, BigInteger b, int c) {
		if (!attack) {
			textB.append("  a = " + a + " , b = " + b
					+ "  sende Challenge C = " + c + "\n\n");
		} else {
			textB.append("   <<------------------   C = " + c
					+ " weiterleiten  --------------------\n\n");
			textC.append("  a = " + a + ", b = " + b + "  sende Challenge C = "
					+ c + "\n\n");
		}

	}

	public void show_V_verify(int c, BigInteger z, boolean accept, double prob) {
		String acc = "FALSE";
		if (accept)
			acc = "TRUE";

		if (!attack) {
			if (c == 0) {
				textB.append("  z = " + z + "  überprüfe ob g^z = a  >> "
								+ acc);
			} else {
				textB.append("  z = " + z + "  überprüfe ob g^z = a*x  >> "
						+ acc);
			}

			textB.append("\n  >> Beweiser = Alice: " + prob * 100 + " %\n\n");
			
		} else {

			if (c == 0) {
				textC.append("\n\n  z = " + z + "  überprüfe ob g^z = a  >> "
						+ acc);
			} else {
				textC.append("\n\n  z = " + z + "  überprüfe ob g^z = a*x  >> "
						+ acc);
			}

			textC.append("\n  >> Beweiser = Alice: " + prob * 100 + " %\n\n");
			
		}

	}

	public void show_V_verify(int c, BigInteger z, int d, BigInteger t,
			boolean accept1, boolean accept2, double prob) {
		String acc = "FALSE";
		if (accept1) {
			acc = "TRUE";
		}

		if (!attack) {
			if (c == 0) {
				textB.append("  z = " + z + "  überprüfe ob g^z = a  >> " + acc
						+ "\n");
			} else {
				textB.append("  z = " + z + "  überprüfe ob g^z = a*x  >> "
						+ acc + "\n");
			}

			if (!accept2) {
				acc = "FALSE";
			}
			if (d == 0) {
				textB
						.append("  y = " + t + "  überprüfe ob g^y = b  >> "
								+ acc);
			} else {
				textB.append("  y = " + t + "  überprüfe ob g^y = b*xB  >> "
						+ acc);
			}

			textB.append("\n  >> Beweiser = Alice: " + prob * 100 + " %\n\n");

		} else {
			textC.append("\n\n");
			if (c == 0) {
				textC.append("  z = " + z + "  überprüfe ob g^z = a  >> " + acc
						+ "\n");
			} else {
				textC.append("  z = " + z + "  überprüfe ob g^z = a*x  >> "
						+ acc + "\n");
			}
			if (!accept2) {
				acc = "FALSE";
			}
			if (d == 0) {
				textC.append("  y = " + t + "  überprüfe ob g^y = b  >> "
								+ acc);
			} else {
				textC.append("  y = " + t + "  überprüfe ob g^y = b*xC  >> "
						+ acc);
			}

			textC.append("\n  >> Beweiser = Alice: " + prob * 100 + " %\n\n");
			
		}
	}

		
	public void show_S_round(BigInteger z, BigInteger a, int c, boolean accept) {}

}
