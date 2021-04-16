package isi.boat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	
	Main main = new Main();

	private int[] boatxcoordinates = new int[750];
	private int[] boatycoordinates = new int[750];

	private int[] fuelxpos = { 125 };
	private int[] fuelypos = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
			525, 550, 575, 600, 625 };
	private ImageIcon fuelImage;
	private Random random = new Random();
	private int xpos = random.nextInt(1);
	private int ypos = random.nextInt(23);

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean fast = false;
	private boolean slow = false;

	private ImageIcon titleImage;
	private ImageIcon leftDirection;
	private ImageIcon rightDirection;
	private ImageIcon upDirection;
	private ImageIcon downDirection;

	private int moves = 0;

	private int fuel = 1000;
	private String status = "OFF";

	private int boatArea = 1;

	private Timer timer;
	private int delay = 500;
	private int speed = 0;
	private int acceleartion = 10;

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(getDelay(), this);
		timer.start();
	}

	public void paint(Graphics g) {

		if (moves == 0) {
			status = "OFF";
			boatxcoordinates[0] = 125;
			boatycoordinates[0] = 300;
		} else if (fuel <= 100 && fuel >= 10) {
			status = "LOW FUEL";
		} else if (fuel <= 0) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("No Fuel Left", 300, 300);
			status = "OFF";
		} else {
			status = "RUNNING";
		}

		// drawing title border
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 852, 55);

		// draw title Image
		titleImage = new ImageIcon("title.jpg");
		titleImage.paintIcon(this, g, 25, 11);

		// draw border for game play
		g.setColor(Color.WHITE);
		g.drawRect(14, 74, 871, 577);

		// draw back ground for the game play
		g.setColor(Color.CYAN);
		g.fillRect(125, 75, 760, 575);

		// draw sea shore
		g.setColor(Color.ORANGE);
		g.fillRect(15, 75, 125, 575);

		if (moves == 0) {
			g.setColor(Color.BLUE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press Arrow Key To Start", 300, 300);
		}

		// draw score
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Fuel : " + fuel, 650, 50);

		// draw controls
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("CONTROLS : " + "UP to NORTH" + ", DOWN to SOUTH", 30, 30);
		g.drawString(" LEFT to EAST" + ", RIGHT to WEST", 110, 50);
		g.drawString("'F' to Speed UP", 535, 30);
		g.drawString("'S' to Slow Down", 535, 50);
		g.drawString("Status : " + status, 650, 30);

		// draw score
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Speed : " + speed + " Knots", 750, 50);

		if (speed > 250 && speed < 300) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Overspeeding, Please Slow Down", 300, 300);
		} else if (speed >= 300) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Engines Failed", 300, 250);
			reset(g);
		} else if(speed < 50 && speed > 0) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Please Maintain Min Speed : 50Kn", 300, 300);
		} else if (speed == 0 && moves!=0) {
			status = "OFF";
			reset(g);
		}

		for (int i = 0; i < boatArea; i++) {

			if (i == 0 && right) {
				rightDirection = new ImageIcon("boat_right.png");
				rightDirection.paintIcon(this, g, boatxcoordinates[i], boatycoordinates[i]);
			}
			if (i == 0 && left) {
				leftDirection = new ImageIcon("boat_left.png");
				leftDirection.paintIcon(this, g, boatxcoordinates[i], boatycoordinates[i]);
			}
			if (i == 0 && up) {
				upDirection = new ImageIcon("boat_up.png");
				upDirection.paintIcon(this, g, boatxcoordinates[i], boatycoordinates[i]);
			}
			if (i == 0 && down) {
				downDirection = new ImageIcon("boat_down.png");
				downDirection.paintIcon(this, g, boatxcoordinates[i], boatycoordinates[i]);
			}

		}
		fuelImage = new ImageIcon("fuel.png");
		fuelImage.paintIcon(this, g, fuelxpos[xpos], fuelypos[ypos]);

		if (fuelxpos[xpos] == boatxcoordinates[0] && fuelypos[ypos] == boatycoordinates[0]) {
			fuel = 1000;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xpos = random.nextInt(1);
			ypos = random.nextInt(23);
		}

		g.dispose();
	}

	public void reset(Graphics g) {
		right = false;
		left = false;
		up = false;
		down = false;
		speed = 0;
		delay = 500;
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 50));
		g.drawString("Game Over", 300, 300);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Space to RESTART", 350, 340);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			speed = 0;
			fuel = 1000;
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			fast = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			slow = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(speed == 0 ) {
				speed = 50;
			}
			moves++;
			right = true;
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
			up = false;
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}
			left = false;
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			if (!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		timer.setDelay(getDelay());
		timer.start();
		fuel--;

		if (fast && delay > 0) {
			speed = speed + acceleartion;
			setDelay(delay - 10);
			fast = false;
		}
		if (slow) {
			speed = speed - acceleartion;
			setDelay(delay + 10);
			slow = false;
		}
		if (right) {
			for (int r = boatArea; r >= 0; r--) {
				if (r == 0) {
					boatxcoordinates[r] = boatxcoordinates[r] + 25;
				} else {
					boatxcoordinates[r] = boatxcoordinates[r - 1];
				}
				if (boatxcoordinates[r] > 800) {
					main.print("Western Limit, Moving South");
					right = false;
					down = true;
				}
			}
			repaint();
		}

		if (left) {
			for (int r = boatArea; r >= 0; r--) {
				if (r == 0) {
					boatxcoordinates[r] = boatxcoordinates[r] - 25;
				} else {
					boatxcoordinates[r] = boatxcoordinates[r - 1];
				}
				if (boatxcoordinates[r] < 150) {
					main.print("Eastern Limit, Moving North");
					left = false;
					up = true;
				}
			}
			repaint();
		}

		if (up) {
			for (int r = boatArea; r >= 0; r--) {
				if (r == 0) {
					boatycoordinates[r] = boatycoordinates[r] - 25;
				} else {
					boatycoordinates[r] = boatycoordinates[r - 1];
				}
				if (boatycoordinates[r] < 125) {
					main.print("Northern Limit, Moving West");
					up = false;
					right = true;
				}
			}
			repaint();
		}

		if (down) {
			for (int r = boatArea; r >= 0; r--) {
				if (r == 0) {
					boatycoordinates[r] = boatycoordinates[r] + 25;
				} else {
					boatycoordinates[r] = boatycoordinates[r - 1];
				}
				if (boatycoordinates[r] > 575) {
					main.print("Southern Limit, Moving East");
					down = false;
					left = true;
				}
			}
			repaint();
		}

	}
}
