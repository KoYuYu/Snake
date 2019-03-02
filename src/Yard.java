import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private boolean flag = true;
	PaintThread PaintThread = new PaintThread();
	private boolean gameOver = false;
	
	public static final int ROWS = 45;
	public static final int COLS = 45;
	public static final int BLOCK_SIZE = 15;
	
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	private Font fontGameOver = new Font("Verdana", Font.BOLD, 50);
	
	//launch the program. set the location and size.
	public void launch() {
		this.setLocation(200, 200);
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		this.addKeyListener(new KeyMonitor());
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		
		new Thread(PaintThread).start();
		
	}
	
	public static void main(String[]args) {
		new Yard().launch();
	}
	
	public void stop() {
		gameOver = true;
		//flag = false;
	}
	
	@Override
	//paint the graphic.
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		g.setColor(Color.DARK_GRAY);
		//draw the line
		for(int i=1; i<ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE*i, COLS * BLOCK_SIZE, BLOCK_SIZE*i);
		}
		for(int i=1; i<COLS; i++) {
			g.drawLine(BLOCK_SIZE*i, 0, BLOCK_SIZE*i, ROWS * BLOCK_SIZE);
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("score:" + score, 10, 60);
		g.setColor(c);
		
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
		if(gameOver) {
			g.setColor(Color.RED);
			g.setFont(fontGameOver );
			g.drawString("game over", 150, 150);
			
			PaintThread.gameOver();
		}
	}
	
	//not blink
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(BLOCK_SIZE*COLS, BLOCK_SIZE*ROWS);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0,0, null);
	}

	
	private class PaintThread implements Runnable {
		private boolean running = true;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(running) {
				repaint();
				try {
					Thread.sleep(100);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void gameOver() {
			running = false;
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}

	public int getScore() {
		score += score;
		return 0;
	}



	public void setScore(int i) {
		score += score;
		
	}
}
