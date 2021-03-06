/*
 * Copyright (c) 2012-2014, Ing. Gabriel Barrera <gmbarrera@gmail.com>
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above 
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES 
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR 
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES 
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN 
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF 
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package ia.battle.gui;

import ia.battle.camp.BattleField;
import ia.battle.camp.BattleFieldListener;
import ia.battle.camp.ConcreteWarriorManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.battle.enemy.ConcreteEnemyManager;
import ia.battle.gui.components.FightButton;
import ia.battle.sound.DefaultSoundPlayer;
import ia.battle.sound.SoundPlayer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BattleFieldSetup extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = 693518024717393345L;
	private static final String SETTINGS_FILE = "Battlefield jar files.txt";
	private JLabel title;
	private FightButton startFight;
	private ClassFinder finderWarriorManager1, finderWarriorManager2;

	private Frame frame;
	private boolean inFight;
	private SoundPlayer soundPlayer;
		
	public BattleFieldSetup() {

		title = new JLabel("IA - Battle Field Agents", JLabel.CENTER);
		title.setBorder(BorderFactory.createEtchedBorder());

		this.add(title, BorderLayout.NORTH);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
/*

		finderWarriorManager1 = new ClassFinder(
				"Seleccione el .jar del Warrior Manager 1");
		panel.add(finderWarriorManager1);

		finderWarriorManager2 = new ClassFinder(
				"Seleccione el .jar del Warrior Manager 2");
		panel.add(finderWarriorManager2);


		loadJarSelection();

		startFight = new FightButton(finderWarriorManager1,
				finderWarriorManager2);
		startFight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				figthClicked();
			}
		});
		startFight.updateEnabledStatus();
*/
		this.add(panel);
		JButton startButton =  new JButton("Start Fight!");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				figthClicked();
			}
		});
		//soundPlayer = new DefaultSoundPlayer();
		this.add(startButton, BorderLayout.SOUTH);
	}

	protected void figthClicked() {

	//	saveJarSelection();
		
		/****** Modificado para poder debuguear ************/

			WarriorManager wm1 = new ConcreteWarriorManager();
			BattleField.getInstance().addWarriorManager(wm1);

			WarriorManager wm2 = new ConcreteEnemyManager();
			BattleField.getInstance().addWarriorManager(wm2);

			BattleField.getInstance().addListener(new BattleFieldListener() {

				@Override
				public void startFight() {

					inFight = true;

					if (frame != null) {
						frame.dispose();
						frame = null;
					}

					frame = new Frame(BattleField.getInstance(), 16, 16);
					frame.setSize(1024, 720);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.setVisible(true);

					frame.addWindowListener(new WindowListener() {

						@Override
						public void windowActivated(WindowEvent arg0) {
						}

						@Override
						public void windowClosed(WindowEvent arg0) {
						}

						@Override
						public void windowClosing(WindowEvent arg0) {

							if (JOptionPane.showConfirmDialog(frame,
									"Está seguro de finalizar la batalla?",
									"Confirme Acción",
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
								inFight = false;
								frame.dispose();
							}
						}

						@Override
						public void windowDeactivated(WindowEvent arg0) {
						}

						@Override
						public void windowDeiconified(WindowEvent arg0) {
						}

						@Override
						public void windowIconified(WindowEvent arg0) {
						}

						@Override
						public void windowOpened(WindowEvent arg0) {
						}

					});
				}

				public void tickLapsed(long tick) {

				}

				public void turnLapsed(long tick, int turnNumber,
						Warrior warrior) {
					frame.repaint();
				}

				public boolean continueFighting() {
					return inFight;
				}

				public void warriorAttacked(Warrior attacked, Warrior attacker,
						int damage) {
					//soundPlayer.playAttack();
				}

				@Override
				public void warriorKilled(Warrior killed) {
					//soundPlayer.playBotKilled();
				}
				
				@Override
				public void warriorMoved(Warrior warrior, FieldCell from,
						FieldCell to) {

				}

				@Override
				public void figthFinished(WarriorManager winner) {
					inFight = false;
					if (winner != null) {
						System.out.println("The winner is " + winner.getName());
						JOptionPane.showMessageDialog(null, "The winner is "
								+ winner.getName());
					}
				}

				@Override
				public void worldChanged(FieldCell oldCell, FieldCell newCell) {
					// TODO Auto-generated method stub
					
				}

				
			});

			//startFight.setEnabled(false);

			new Thread(new Runnable() {
				@Override
				public void run() {

					BattleField.getInstance().fight();
					BattleField.getInstance().showResult();

					//startFight.setEnabled(true);
				}
			}).start();

		
	}

	private void saveJarSelection() {

		URL urlJar1 = finderWarriorManager1.getSelectedJarFile();
		URL urlJar2 = finderWarriorManager2.getSelectedJarFile();

		String className1 = finderWarriorManager1.getSelectedClassName();
		String className2 = finderWarriorManager2.getSelectedClassName();

		try {

			FileWriter fw = new FileWriter(SETTINGS_FILE);
			fw.write(urlJar1.toString() + "\n");
			fw.write(className1.toString() + "\n");
			fw.write(urlJar2.toString() + "\n");
			fw.write(className2.toString() + "\n");
			fw.close();

		} catch (Exception ex) {
			System.err.println("Error grabando: " + ex);
		}
	}

	private void loadJarSelection() {

		try {

			File f = new File(SETTINGS_FILE);

			if (f.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(f));

				String url1 = br.readLine();
				String class1 = br.readLine();
				String url2 = br.readLine();
				String class2 = br.readLine();

				br.close();

				finderWarriorManager1.getSelectedJarFile(url1);
				finderWarriorManager2.getSelectedJarFile(url2);

				finderWarriorManager1.getSelectedClassName(class1);
				finderWarriorManager2.getSelectedClassName(class2);
			}

		} catch (Exception ex) {
			System.err.println("Error leyendo: " + ex);
		}
	}

	public static void main(String[] args) {
		SwingConsole.run(new BattleFieldSetup(), 600, 400, true, "IA");
	}
}
