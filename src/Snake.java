import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;
import java.awt.Button;
public class Snake
{
	public static void main(String args[])
	{
		JFrame frame=new JFrame("Snake");
		SnakeWorkSpace ob=new SnakeWorkSpace();
		frame.setBounds(250,20,680,700);
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
	public int HighScore=0;
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
	public SnakeWorkSpace()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);
		t=new Timer(speed,this);
		t.start();	
		
		//Start Button for starting the GamePlay as well as
		final Button strt = new Button("Start");
		strt.setBounds(300, 435, 70, 22);
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
	}
	
	public void paint(Graphics g)
	{
		if(count==0)
		{
			Score=0;
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
		}
		else
		{
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
		
	}
	
	public void locationOfNewApple()
	{
		posx=rand.nextInt(43);
		posy=rand.nextInt(40);
		if(xLengthOfApple[posx]==x[posx] && yLengthOfApple[posy]==y[posy])
		{
			locationOfNewApple();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_RIGHT && !left && !FrontPage)
		{
			count+=1;
			right=true;
			up=down=false;
		}
		if(key==KeyEvent.VK_LEFT && !right && !FrontPage)
		{
			count+=1;
			left=true;
			up=down=false;
		}
		if(key==KeyEvent.VK_UP && !down && !FrontPage)
		{
			count+=1;
			up=true;
			right=left=false;
		}
		if(key==KeyEvent.VK_DOWN && !up && !FrontPage)
		{
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
				if(x[k]>W)
					x[k]=15;
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
				if(x[k]<15)
					x[k]=W-5;
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
				if(y[k]>H+45)
					y[k]=55;
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
				if(y[k]<45)
					y[k]=H+40;
			}
			repaint();
		}
	}
}