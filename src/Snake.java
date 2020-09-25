import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;
import java.awt.Button;
import java.awt.Panel;
public class Snake
{
	public static void main(String args[])
	{
		JFrame frame=new JFrame("Snake");
		SnakeWorkSpace ob=new SnakeWorkSpace();
		frame.setBounds(250,20,690,700);
		frame.setBackground(Color.green);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(ob);
		ob.setLayout(null);
		
		
	}
}
class SnakeWorkSpace extends JPanel implements ActionListener, KeyListener
{
	public int W=650;
	public int H=600;
	public Image tp,bg,head,body,fpg,apple;
	Timer t;
	public int speed=120;
	public int HighScore;
	public int Score=0;
	public boolean FrontPage=true;
	public boolean right=true;
	public boolean down=false;
	public boolean left=false;
	public boolean up=false;
	public int count=0;
	public int size=3;
	public int x[]=new int[1000];
	public int y[]=new int[1000];
	public boolean gameover=false;
	public Button easy,medium,hard;
	public Random rand=new Random();
	public int[] xLengthOfApple=new int[1000];
	public int[] yLengthOfApple=new int[1000];
	public int posx=rand.nextInt(43);
	public int posy=rand.nextInt(40);
	public int q,r;
	public Panel panel;
	public Button exitbtn,restartbtn,menu,back,cont,strt;
	public SnakeWorkSpace()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);
		t=new Timer(speed,this);
		t.start();	
		setHighScore();
		
		//Start Button for starting the GamePlay as well as
		strt = new Button("New Game");
		strt.setBounds(275, 375, 140, 40);
		strt.setFont(new Font("Tahoma",Font.BOLD,14));
		strt.setBackground(new Color(102,0,102));
		strt.setForeground(Color.WHITE);
		strt.setVisible(true);
		strt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				FrontPage=false;
				strt.setVisible(false);
				repaint();
			}
		});
		add(strt);
		
		cont = new Button("Continue");
		cont.setBounds(275, 420, 140, 40);
		cont.setFont(new Font("Tahoma",Font.BOLD,14));
		cont.setBackground(new Color(102,0,102));
		cont.setForeground(Color.WHITE);
		cont.setVisible(true);
		cont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				FrontPage=false;
				repaint();
			}
		});
		add(cont);
		
		panel = new Panel();
		panel.setBackground(Color.BLACK);
		panel.setForeground(Color.WHITE);
		panel.setBounds(225, 313, 206, 108);
		panel.setVisible(false);
		add(panel);
		panel.setLayout(null);
		
		
		
		restartbtn = new Button("Restart");
		restartbtn.setForeground(Color.BLACK);
		restartbtn.setBackground(Color.WHITE);
		restartbtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		restartbtn.setBounds(71, 0, 80, 22);
		restartbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				gameover=false;
				count=0;
				right=true;
				left=up=down=false;
				panel.setVisible(false);
			}
		});
		panel.add(restartbtn);
		
		menu = new Button("Main Menu");
		menu.setForeground(Color.BLACK);
		menu.setBackground(Color.WHITE);
		menu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menu.setBounds(71, 29, 80, 22);
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				count=0;			
				panel.setVisible(false);
				FrontPage=true;
				gameover=false;
				repaint();
			}
		});
		panel.add(menu);
		
		exitbtn = new Button("Exit");
		exitbtn.setBackground(Color.WHITE);
		exitbtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		exitbtn.setForeground(Color.BLACK);
		exitbtn.setBounds(71, 57, 80, 22);
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		panel.add(exitbtn);
		
		
		/*easy = new Button("Easy");
		easy.setForeground(Color.BLACK);
		easy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		easy.setBackground(Color.WHITE);
		easy.setBounds(1, 29, 64, 22);
		panel.add(easy);
		
		medium = new Button("Medium");
		medium.setForeground(Color.BLACK);
		medium.setFont(new Font("Tahoma", Font.PLAIN, 14));
		medium.setBackground(Color.WHITE);
		medium.setBounds(1, 57, 64, 22);
		panel.add(medium);
		
		hard = new Button("Hard");
		hard.setForeground(Color.BLACK);
		hard.setFont(new Font("Tahoma", Font.PLAIN, 14));
		hard.setBackground(Color.WHITE);
		hard.setBounds(1, 85, 64, 22);
		panel.add(hard);*/
		
		back = new Button("Main Menu");
		back.setForeground(Color.WHITE);
		back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		back.setBackground(Color.BLACK);
		back.setBounds(590, 1, 80, 22);
		back.setVisible(false);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				FrontPage=true;
				t.stop();
				back.setVisible(false);
				repaint();
			}
		});
		add(back);
		
		
	}
	
	public void paint(Graphics g)
	{
		if(count==0)
		{
			Score=0;
			size=3;
			x[0]=45;
			x[1]=x[0]-15;
			x[2]=x[1]-15;
			y[2]=y[1]=y[0]=100;
			
			q=1;
			r=1;
			xLengthOfApple[0]=15;
			yLengthOfApple[0]=55;
			while(q<=43)
			{
				xLengthOfApple[q]=xLengthOfApple[q-1]+15;
				q++;
			}
			while(r<=40)
			{
				yLengthOfApple[r]=yLengthOfApple[r-1]+15;
				r++;
			}
		}
		if(FrontPage) 
		{
			//IMAGEICON FOR THE FRONT IMAGE
			ImageIcon fnt=new ImageIcon(getClass().getResource("/image/Front.png"));
			fpg=fnt.getImage();
			g.drawImage(fpg,0,0,this);
			
			strt.setVisible(true);
			cont.setVisible(true);
		}
		else if(!FrontPage && !gameover)
		{
			strt.setVisible(false);
			cont.setVisible(false);
			back.setVisible(true);
			//BackGround
			g.setColor(Color.GRAY);
			g.fillRect(0,0,690,700);
			
			//TITLE IMAGE
			ImageIcon title=new ImageIcon(getClass().getResource("/image/TitlePic.png"));
			tp=title.getImage();
			g.drawImage(tp, 0, 1, this);
		
			//BORDER OF MAIN GROUND
			g.setColor(Color.WHITE);
			g.drawRect(11, 55, W+1, H+1);
		
			//MAIN GROUND
			g.setColor(Color.BLACK);
			g.fillRect(12, 56, W, H);
		
			//HIGH SCORES AND SCORES
			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma",Font.BOLD,14));
			g.drawString("High Score: "+HighScore, 10, 20);
			g.drawString("Score: "+Score, 10, 40);
			
			ImageIcon rh=new ImageIcon(getClass().getResource("/image/RightHead15.png"));
			ImageIcon lh=new ImageIcon(getClass().getResource("/image/LeftHead15.png"));
			ImageIcon uh=new ImageIcon(getClass().getResource("/image/UpHead15.png"));
			ImageIcon dh=new ImageIcon(getClass().getResource("/image/DownHead15.png"));
			ImageIcon b=new ImageIcon(getClass().getResource("/image/Body15.png"));
			ImageIcon ap=new ImageIcon(getClass().getResource("/image/apple.png"));
			body=b.getImage();
			for(int j=0;j<size;j++)
			{
				if(j==0 && right)
				{
					head=rh.getImage();
					g.drawImage(head, x[j], y[j], this);
				}
				if(j==0 && left)
				{
					head=lh.getImage();
					g.drawImage(head, x[j], y[j], this);
				}
				if(j==0 && up)
				{
					head=uh.getImage();
					g.drawImage(head, x[j], y[j], this);
				}
				if(j==0 && down)
				{
					head=dh.getImage();
					g.drawImage(head, x[j], y[j], this);
				}
				if(j!=0)
				{
					g.drawImage(body, x[j], y[j], this);
				}
			}
			apple=ap.getImage();
			if(xLengthOfApple[posx]==x[0] && yLengthOfApple[posy]==y[0])
			{
				size++;
				Score++;
				locationOfNewApple();
			}
			g.drawImage(apple, xLengthOfApple[posx], yLengthOfApple[posy], this);		
		}
		for(int c=3;c<size;c++)
		{
			if(x[0]==x[c] && y[0]==y[c])
			{
				gameover=true;
				setHighScore();
				GameOver(g);
			}
		}
		if(x[0]>W || x[0]<15 || y[0]>H+45 || y[0]<45)
		{
			gameover=true;
			setHighScore();
			GameOver(g);
		}
		
	}
	
	public void GameOver(Graphics g2)
	{
		
		panel.setVisible(true);
		back.setVisible(false);
		right=left=up=down=false;
		g2.setColor(Color.GRAY);
		g2.fillRect(0,0,690,700);
		g2.setColor(Color.BLACK);
		g2.fillRect(10, 10, 650, 640);
		g2.setFont(new Font("Tahoma",Font.BOLD,26));
		g2.setColor(Color.WHITE);
		g2.drawString("GAMEOVER", 250, 250);
		g2.setFont(new Font("Times New Roman",Font.BOLD,14));
		g2.drawString("SCORE => "+Score, 250,270);
		g2.drawString("HIGHSCORE => "+HighScore, 250,290);
		
	}
	
	public void locationOfNewApple()
	{
		posx=rand.nextInt(43);
		posy=rand.nextInt(40);
		for(int i=0;i<size;i++){
			if(xLengthOfApple[posx]==x[i] && yLengthOfApple[posy]==y[i])
			{
				locationOfNewApple();
			}
		}
	}
	
	public void setHighScore()
	{
		try {
		BufferedReader in=new BufferedReader(new FileReader("HighScore.txt"));
		String s=in.readLine();
		in.close();
		
		HighScore=Integer.parseInt(s);
		if(Score>HighScore)
			HighScore=Score;
		s=Integer.toString(HighScore);

		BufferedWriter out=new BufferedWriter(new FileWriter("HighScore.txt"));
		out.write(s);
		out.close();
		}catch(IOException e) {}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_SPACE && !FrontPage)
		{
			t.stop();
		}
		if(key==KeyEvent.VK_RIGHT && !left && !FrontPage)
		{
			t.start();
			count+=1;
			right=true;
			up=down=false;
		}
		if(key==KeyEvent.VK_LEFT && !right && !FrontPage)
		{
			t.start();
			count+=1;
			left=true;
			up=down=false;
		}
		if(key==KeyEvent.VK_UP && !down && !FrontPage)
		{
			t.start();
			count+=1;
			up=true;
			right=left=false;
		}
		if(key==KeyEvent.VK_DOWN && !up && !FrontPage)
		{
			t.start();
			count+=1;
			down=true;
			right=left=false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		t.start();
		if(!FrontPage && right) {
			for(int k=size-1;k>=0;k--)
			{
				y[k+1]=y[k];
			}
			for(int k=size;k>=0;k--)
			{
				if(k==0)
					x[k]+=15;
				else
					x[k]=x[k-1];
			}
			repaint();
		}
		if(!FrontPage && left) {
			for(int k=size-1;k>=0;k--)
			{
				y[k+1]=y[k];
			}
			for(int k=size;k>=0;k--)
			{
				if(k==0)
					x[k]-=15;
				else
					x[k]=x[k-1];
			}
			repaint();
		}
		if(!FrontPage && down) {
			for(int k=size-1;k>=0;k--)
			{
				x[k+1]=x[k];
			}
			for(int k=size;k>=0;k--)
			{
				if(k==0)
					y[k]+=15;
				else
					y[k]=y[k-1];
			}
			repaint();
		}
		if(!FrontPage && up) {
			for(int k=size-1;k>=0;k--)
			{
				x[k+1]=x[k];
			}
			for(int k=size;k>=0;k--)
			{
				if(k==0)
					y[k]-=15;
				else
					y[k]=y[k-1];
			}
			repaint();
		}
	}
}