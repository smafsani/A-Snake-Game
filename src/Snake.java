import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.Button;
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
	}
}
class SnakeWorkSpace extends JPanel implements ActionListener, KeyListener
{
	public int W=650;
	public int H=600;
	public Image tp,bg,head,body,fpg;
	Timer t;
	public int speed = 120;
	public int HighScore=0;
	public int Score=0;
	public boolean FrontPage=true;
	public boolean right=true;
	public boolean down=false;
	public boolean left=false;
	public boolean up=false;
	public int count=0;
	public int size=3;
	public int xlength[]=new int[1000];
	public int ylength[]=new int[1000];
	public boolean gameover=false;
	public Button easy,medium,hard;
	public SnakeWorkSpace()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);
		t=new Timer(speed,this);
		t.start();	
		
		//Start Button for starting the GamePlay as well as
		Button strt = new Button("Start");
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
			xlength[0]=60;
			xlength[1]=xlength[0]-20;
			xlength[2]=xlength[1]-20;
			ylength[2]=ylength[1]=ylength[0]=100;
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
			
			ImageIcon h=new ImageIcon(getClass().getResource("/image/RightHead20.png"));
			ImageIcon b=new ImageIcon(getClass().getResource("/image/Body20.png"));
			head=h.getImage();
			body=b.getImage();
			for(int j=0;j<size;j++)
			{
				if(j==0 && right)
				{
					g.drawImage(head, xlength[j], ylength[j], this);
				}
				if(j!=0)
				{
					g.drawImage(body, xlength[j], ylength[j], this);
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}