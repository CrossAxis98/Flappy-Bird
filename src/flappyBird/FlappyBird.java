package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class FlappyBird implements ActionListener, MouseListener, KeyListener
{
	public int ticks, yMotion, score;
	
	public static FlappyBird flappyBird;
	
	public static int WIDTH = 800, HEIGHT = 800;
	
	public Renderer renderer;
	
	public Rectangle fBird;
	
	public boolean gameOver, started;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	
	
	
	FlappyBird()
	{
		JFrame jframe = new JFrame();
		
		renderer = new Renderer();
		
		Timer timer = new Timer(20, this);
		
		rand = new Random();
		
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setTitle("Flappy Bird");
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		
		
		timer.start();
		
		fBird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
		
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		
	}
	
	public void addColumn(boolean old)
	{
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		
		if(old)
		{
		columns.add(new Rectangle(WIDTH+width+columns.size() *300, HEIGHT - height - 120,width, height));
		
		columns.add(new Rectangle(WIDTH + width + (columns.size()-1)*300, 0 , width , HEIGHT - height - space));

		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, HEIGHT - height - 120,width, height));
			
			columns.add(new Rectangle(columns.get(columns.size()-1).x , 0 , width , HEIGHT - height - space));
		}
		
	}
	
	
	
	
	public void repaint(Graphics g) {
		
		g.setColor(Color.cyan);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(Color.GREEN);
		g.fillRect(0, HEIGHT-120, WIDTH, 120);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, HEIGHT-120, WIDTH, 20);
		
		g.setColor(Color.red);
		g.fillRect(fBird.x, fBird.y, fBird.width, fBird.height);
		
		for(Rectangle column : columns )
		{
			paintColumn(g, column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		if(!started)
		{
			g.drawString("Click to START", 75, HEIGHT/2 - 50);
			//started = true;
		}
		
		if(gameOver)
		{
			g.drawString("Game Over!", 100, HEIGHT/2 - 50);
		
		}
		
		if(!gameOver )
		{
			g.drawString(String.valueOf(score), WIDTH/2 -25, 100);
			
		}
		
	}
	
	public void jump()
	{
		if(gameOver)
		{
			fBird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			
			columns.clear();
			yMotion = 0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
			
			
		}
		
		if(!started)
		{
			started = true;
		}
		else if (!gameOver)
		{
			if(yMotion > 0)
			{
				yMotion = 0;
			}
			yMotion -= 10;	
		}
	}
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		
		int speed = 10;
		
		ticks ++ ;
		
		if(started)
		{
		for(int i=0; i < columns.size(); i++)
		{
			Rectangle column = columns.get(i);
			column.x -= speed;
		}
		
		if(ticks%2 == 0 && yMotion < 15)
		{
			yMotion += 2;
			
		}
		
		for(int i=0; i < columns.size(); i++)
		{
			Rectangle column = columns.get(i);

			if(column.x + column.width < 0)
			{
				columns.remove(column);
				
				if(column.y==0)
				{
				addColumn(false);
				}
			}
		}
		
		fBird.y += yMotion;
		
		for(Rectangle column : columns)
		{
			
			if(column.y==0 && fBird.x + fBird.width / 2 > column.x + column.width/2 - 10 && fBird.x + fBird.width /2 < column.x + column.width/2 + 10)
			{
				score++;
			}
			if(column.intersects(fBird))
			{
				gameOver = true;
			}
		}
		
		if(fBird.y > HEIGHT - 120 || fBird.y < 0)
		{
			gameOver = true;
		}
		
		}
		
		renderer.repaint();
		
	}
	
	public void paintColumn(Graphics g, Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	
	
	public static void main (String [] args)
	{
		flappyBird = new FlappyBird();
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			jump();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	
	



}
