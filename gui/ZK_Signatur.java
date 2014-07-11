package gui;

import javax.swing.*;

import praktikum.GroupParam;
import praktikum.NIProver;
import praktikum.NIVerifier;
import praktikum.Signatur;

import java.awt.*;
import java.awt.event.*;

/**
 * Testprogramm für das Erstellen und Verifizieren von digitalen Signaturen 
 * auf Basis eines nicht-interaktiven Zero-Knowledge Beweises der Kenntnis 
 * eines diskreten Logarithmus. (Aufgabe 5)
 * 
 */

public class ZK_Signatur extends JFrame implements WindowListener,ActionListener {

	static final long serialVersionUID=1;
	
	private Container pane;

	private int WIDTH = 400;
	private int HEIGHT = 400;

	private GridBagLayout GL;

	private JLabel label1;
	private JLabel label2;

	private JTextField text1;
	private JTextArea text2;
	private JTextArea text3;

	private JButton button_sign;
	private JButton button_test;
	private JButton button_reset;

	public ZK_Signatur() 
	{
		super("Nicht-interaktive Zero-Knowledge Signatur");
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

		label1 = new JLabel("zu signierende Nachricht:");

		GB.gridx = 0;
		GB.gridy = 0;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(label1, GB);
		pane.add(label1);

		text1 = new JTextField(60);
		JScrollPane js = new JScrollPane(text1);
		js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		GB.gridx = 0;
		GB.gridy = 1;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 20;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(js, GB);
		pane.add(js);

		button_sign = new JButton("Signieren");

		GB.gridx = 0;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.5;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL.setConstraints(button_sign, GB);
		pane.add(button_sign);

		button_reset = new JButton("Reset");

		GB.gridx = 1;
		GB.gridy = 2;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.5;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.WEST;
		GL.setConstraints(button_reset, GB);
		pane.add(button_reset);

		label2 = new JLabel("Signatur");

		GB.gridx = 0;
		GB.gridy = 3;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 0, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.HORIZONTAL;
		GB.anchor = GridBagConstraints.NORTH;
		GL.setConstraints(label2, GB);
		pane.add(label2);

		text2 = new JTextArea(5, 60);
		text2.setEditable(false);
		JScrollPane js2 = new JScrollPane(text2);
		js2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		GB.gridx = 0;
		GB.gridy = 4;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 20;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.SOUTH;
		GL.setConstraints(js2, GB);
		pane.add(js2);

		button_test = new JButton("Verifzieren");

		GB.gridx = 0;
		GB.gridy = 5;
		GB.gridwidth = 1;
		GB.gridheight = 1;
		GB.weightx = 0.5;
		GB.weighty = 0.1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 0;
		GB.fill = GridBagConstraints.NONE;
		GB.anchor = GridBagConstraints.CENTER;
		GL.setConstraints(button_test, GB);
		pane.add(button_test);
		button_test.setEnabled(false);

		text3 = new JTextArea(2, 60);
		text3.setEditable(false);
		JScrollPane js3 = new JScrollPane(text3);
		js3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		js3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		GB.gridx = 0;
		GB.gridy = 6;
		GB.gridwidth = 2;
		GB.gridheight = 1;
		GB.weightx = 1;
		GB.weighty = 1;
		GB.insets = new Insets(10, 10, 10, 10);
		GB.ipadx = 0;
		GB.ipady = 20;
		GB.fill = GridBagConstraints.BOTH;
		GB.anchor = GridBagConstraints.SOUTH;
		GL.setConstraints(js3, GB);
		pane.add(js3);

		button_sign.addActionListener(this);
		button_test.addActionListener(this);
		button_reset.addActionListener(this);

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
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object == button_reset)
		{
			message = null;
			text1.setText(null);
			text2.setText(null);
			text3.setText(null);
			button_test.setEnabled(false);

		} 
		else if (object == button_sign) 
		{
			startSign();
			button_test.setEnabled(true);
		}
		else if (object == button_test) 
		{
			startVerify();
		}
	}

	/*-----------------------------------
	 --  WindowListener Methoden --------
	 ------------------------------------*/
	public void windowActivated(WindowEvent event) {}
	public void windowClosed(WindowEvent event) {}
	public void windowClosing(WindowEvent event) {
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent event) {}
	public void windowDeiconified(WindowEvent event) {}
	public void windowIconified(WindowEvent event) {}
	public void windowOpened(WindowEvent event) {}

	
	/*-----------------------------------
	 --  MAIN Methode ------------------
	 ------------------------------------*/
	public static void main(String args[]) 
	{
		try {
			group = new GroupParam(16);
			ZK_Signatur fenster = new ZK_Signatur();
			fenster.setVisible(true);

		} catch (Exception e) {
			System.out.println("errror: " + e);
			System.exit(1);
		}
	}

	private NIProver prover;
	private NIVerifier verifier;

	private Signatur signature;

	private static GroupParam group;
	
	private boolean init = true;

	/* zu signierende Nachricht */
	private String message;

	/* Signatur erstellen */
	public void startSign() 
	{
		/* Anzahl der Iterationen */
		int k=64;

		text2.setText(null);
		text3.setText(null);
		try {
	
			if (init)
			{
				prover = new NIProver(group,k);
				verifier = new NIVerifier(group,k);
				text2.setTabSize(2);
				text3.setTabSize(2);
			}
			init=false;
			message = new String(text1.getText());

			/* Beweiser signiert Nachricht */
			signature = prover.sign(message);

			
			text2.append("\n\t Challenges: \t" + (signature.challenges).toString()+ "\n");
			text2.append("\n\t Antworten:  \t" + (signature.responses).toString() + "\t\n");

		} catch (Exception e) {
			System.out.println("Exception in ZK_Signatur.startSign().. " + e);
		}
	}

	/* Signatur verifizieren */
	public void startVerify() {
		text3.setText(null);
		try {
			boolean accept = verifier.verify(message, signature, prover.getPublicKey());
			if (accept) {
				text3.append("\n\t Signatur KORREKT ! \n");
			} 
			else {
				text3.append("\n\t Signatur NICHT KORREKT ! \n");
			}
		} catch (Exception e) {
			System.out.println("Exception in ZK_Signatur.startVerify().. " + e);
		}
	}
}
