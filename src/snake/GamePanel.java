package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	
	// declare everything that we are going to use with this program
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75; // for a timer
	// two arrays x[], y[]. These arrays are going to hold all of the coordinates for all the body parts
	final int x[] = new int[GAME_UNITS]; 
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6; // body parts start from 6
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	//constructor
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		// when we finish to create the game panel, we are going to start the game
		startGame();
	}
	
	// start game
	public void startGame() {
		// to create a new apple on the screen. populate the game with an apple, so we can score a point 
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			/* add the grid lines
			for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.setColor(Color.gray);
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}*/
			
			// let's draw the apple on the screen
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// draw the head of the snake, and draw the body
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

				}
			} 
			
			// write the scroe
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			// put the "Game Over" in the center of the screen
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

			
		} else {
			gameOver(g);
		}
	}
	
	/* Generate a new apple whenever the method is called. */
	public void newApple() {
		appleX =random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY =random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	
	/* move the snake */
	public void move() {
		// for loop to iterate through all the body parts of the snake
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		// to change the direction
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	/*examine the coordinates of the snake and the coordinates of the apple*/
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// checks if head collides with body
		for(int i=bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// checks if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		
		//checks if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		// checks if head touches top border
		if(y[0] < 0) {
			running = false;
		}
				
		//checks if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		// stop the timer
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		// put the "Game Over" in the center of the screen
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

		
		// game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		// put the "Game Over" in the center of the screen
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check if the game is running
		if(running) {
			move(); // make the snack move 
			checkApple(); // check if we ran into the apple
			checkCollisions();
		}
		// the game is no longer running. call repaint()
		repaint();
		
	}
	
	/* Inner class */
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
