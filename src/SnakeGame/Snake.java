package SnakeGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextField;
import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
public class Snake
{
	public static JFrame frame;
	public static Panel panel;
	public static void main(String args[])
	{
		String siz,pos,applePos;
		frame=new JFrame("Snake");
		final SnakeWorkSpace ob=new SnakeWorkSpace();
		frame.setBounds(250,20,680,700);
		frame.setBackground(Color.green);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(ob);
		ob.setLayout(null);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				ob.StoreCurrentInfo();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		panel = new Panel();
		panel.setBounds(1, 325, 260, 335);
		panel.setBackground(Color.DARK_GRAY);
		panel.setVisible(false);
		ob.add(panel);
		panel.setLayout(null);
	}
	public Snake(boolean b)
	{		
		panel.setVisible(b);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 20, 220, 300);
		panel.add(scrollPane);
		scrollPane.setVisible(true);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		try
		{
			BufferedReader r=new BufferedReader(new FileReader("Scores.txt"));
			textArea.read(r, null);
			textArea.requestFocus();
		}
		catch(Exception exc){}
	}
}
class appleLoc
{
	int xPos,yPos;
	public appleLoc(int aa,int bb)
	{
		xPos=aa;
		yPos=bb;
	}
}
class SnakeWorkSpace extends JPanel implements ActionListener, KeyListener
{
	
	public int W=650;
	public int H=600;
	public Image tp,bg,head,body,fpg,apple;
	Timer t;
	public TextField textField;
	public int speed=90;
	public int HighScore;
	public int Score=0;
	public boolean FrontPage=true;
	public boolean right=false;
	public boolean down=false;
	public boolean left=false;
	public boolean up=false;
	public int count=0;
	public int size=3;
	public int x[]=new int[10000];
	public int y[]=new int[10000];
	public boolean gameover=false;
	public Button easy,medium,hard,level,Quit;
	public Random rand=new Random();
	public int totalLoc=1720;
	public int Position;
	public int q,r,sco=0;
	public Panel panel,panel2;
	public static Button save,sc,restartbtn,menu,back,cont,strt,scrbd;
	public boolean Esy;
	public boolean Mdum;
	public boolean Hrd;
	public appleLoc[] Loc;
	String lv="Easy";
	public String s;
	public boolean now=true;
	public boolean scoreboard=false;
	public boolean zx=true;
	public Button done;
	public int flag;
        public AudioPlayer play;
        public AudioStream music;
        public AudioData data;
        public ContinuousAudioDataStream loop=null;
        public int ok=0;
        public int lvlno=0;
        Clip clipMainTheme;
	public void continu()
	{
		try
		{
			int p,s;
			BufferedReader nest=new BufferedReader(new FileReader("ContinueOrNot.txt"));
			p=Integer.parseInt(nest.readLine());
			s=Integer.parseInt(nest.readLine());
			if(p==0 && s==3)
				cont.setEnabled(false);
			else
				cont.setEnabled(true);
			nest.close();
		}catch(Exception e) {}
	}
	public SnakeWorkSpace()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);
		continu();
                setMusic();
		try
		{
			BufferedReader ne=new BufferedReader(new FileReader("Direction.txt"));
			int k1=Integer.parseInt(ne.readLine());
			int k2=Integer.parseInt(ne.readLine());
			if(k2==1) {
                                speed=90;
				totalLoc=1720;
                                Esy=true;
				Mdum=Hrd=false;
			}
			if(k2==2) {
                                speed=75;
				totalLoc=1660;
                                Mdum=true;
				Esy=Hrd=false;
			}
			if(k2==3)
			{
                                speed=65;
                                totalLoc=1592;
				Hrd=true;
				Esy=Mdum=false;
			}
			ne.close();
		}catch(Exception e) {}
		
		t=new Timer(speed,this);
		t.setDelay(speed);
		t.start();
		//run(speed);
		setHighScore();
		
		
		
		//Start Button for starting the GamePlay as well as
		strt = new Button("New Game");
		strt.setBounds(275, 325, 140, 40);
		strt.setFont(new Font("Tahoma",Font.BOLD,16));
		//strt.setBackground(new Color(102,0,102));
		strt.setBackground(Color.RED);
                strt.setForeground(Color.WHITE);
		strt.setVisible(true);
		strt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				right=true;
				left=up=down=false;
				Position=rand.nextInt(totalLoc);
				cont.setEnabled(true);
				panel2.setVisible(false);
				FrontPage=false;
				strt.setVisible(false);
				count=0;
                                MainTheme();
				setHighScore();
				repaint();
				
			}
		});
		add(strt);
		try
		{
			BufferedReader ne=new BufferedReader(new FileReader("Available.txt"));
			flag=Integer.parseInt(ne.readLine());
			ne.close();
		}catch(Exception e) {}
		cont = new Button("Continue");
		cont.setBounds(275, 366, 140, 40);
		cont.setFont(new Font("Tahoma",Font.BOLD,16));
		cont.setBackground(Color.RED);
		cont.setForeground(Color.WHITE);
		if(flag==1)
			cont.setEnabled(false);
		else cont.setEnabled(true);
		cont.setVisible(true);
		cont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ok=ok+1;
                                ButtonClickSound();
                                if(now && ok==2)
                                {
                                    MainTheme();
                                    ok=0;
                                }
				int c,l;
				if(now && flag==0)
				{
					try{
						BufferedReader collect=new BufferedReader(new FileReader("Store.txt"));
						BufferedReader collect2=new BufferedReader(new FileReader("Direction.txt"));
						Position=Integer.parseInt(collect.readLine());
						size=Integer.parseInt(collect.readLine());
						for(int j=0;j<size;j++)
						{
							x[j]=Integer.parseInt(collect.readLine());
						}
						for(int j=0;j<size;j++)
						{
							y[j]=Integer.parseInt(collect.readLine());
						}
						collect.close();
						c=Integer.parseInt(collect2.readLine());
						if(c==1)
						{
							right=true;
							left=up=down=false;
						}
						if(c==2)
						{
							left=true;
							right=up=down=false;
						}
						if(c==3)
						{
							up=true;
							left=right=down=false;
						}
						if(c==4)
						{
							down=true;
							left=up=right=false;
						}
						l=Integer.parseInt(collect2.readLine());
						if(l==1)
						{
							Esy=true;
							Mdum=Hrd=false;
							speed=90;
							t.setDelay(speed);
						}
						if(l==2)
						{
							Mdum=true;
							Esy=Hrd=false;
							speed=75;
							t.setDelay(speed);
						}
						if(l==3)
						{
							Hrd=true;
							Mdum=Esy=false;
							speed=65;
							t.setDelay(speed);
						}
						collect2.close();
					}catch(Exception ex) {}
					count=1;
					Score=size-3;
					repaint();
					now=false;
                                        t.stop();
				}
				else {
                                        MainTheme();
					panel2.setVisible(false);
					FrontPage=false;
					repaint();
				}
				setHighScore();
			}
		});
		add(cont);
		
		panel = new Panel();
		panel.setBackground(Color.BLACK);
		panel.setForeground(Color.WHITE);
		panel.setBounds(225, 313, 205, 145);
		panel.setVisible(false);
		add(panel);
		panel.setLayout(null);
		
		
		restartbtn = new Button("Restart");
		restartbtn.setForeground(Color.BLACK);
		restartbtn.setBackground(Color.WHITE);
		restartbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		restartbtn.setBounds(30, 0, 120, 30);
		restartbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				gameover=false;
				count=0;
                                MainTheme();
				Position=rand.nextInt(totalLoc);
				right=true;
				left=up=down=false;
				panel.setVisible(false);
			}
		});
		panel.add(restartbtn);
		
		menu = new Button("Main Menu");
		menu.setForeground(Color.BLACK);
		menu.setBackground(Color.WHITE);
		menu.setFont(new Font("Tahoma", Font.BOLD, 14));
		menu.setBounds(30, 31, 120, 30);
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				cont.setEnabled(false);
				count=0;
				panel.setVisible(false);
				FrontPage=true;
				gameover=false;
                                setMusic();
				repaint();
			}
		});
		panel.add(menu);
		
		textField=new TextField();
		textField.setBounds(30, 93, 200, 50);
		textField.setBackground(Color.white);
		textField.setForeground(Color.black);
		textField.setVisible(false);
		textField.setFont(new Font("Tahoma",Font.BOLD,16));
		panel.add(textField);
		
		save = new Button("Save");
		save.setBackground(Color.WHITE);
		save.setFont(new Font("Tahoma", Font.BOLD, 14));
		save.setForeground(Color.BLACK);
		save.setBounds(30, 62, 120, 30);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				textField.setVisible(true);
				save.setVisible(false);
				done.setVisible(true);
			}
		});
		panel.add(save);
		
		done = new Button("Done");
		done.setBackground(Color.WHITE);
		done.setFont(new Font("Tahoma", Font.BOLD, 14));
		done.setForeground(Color.BLACK);
		done.setBounds(30, 62, 120, 30);
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				String str;
				try
				{
					BufferedWriter out=new BufferedWriter(new FileWriter("Scores.txt",true));
					out.newLine();
					str=textField.getText();
					if(Esy && str!="")
						out.write(str+" "+Score+" (Easy)");
					else if(Mdum && str!="")
						out.write(str+" "+Score+" (Medium)");
					else if(Hrd && str!="")
						out.write(str+" "+Score+" (Hard)");
					out.close();
				}
				catch(Exception ec) {}
				done.setVisible(false);
				save.setVisible(true);
				cont.setEnabled(false);
				count=0;	
				textField.setText(null);
				panel.setVisible(false);
				textField.setVisible(false);
				FrontPage=true;
				gameover=false;
				repaint();
			}
		});
		panel.add(done);
		
		level = new Button("Level");
		level.setFont(new Font("Tahoma", Font.BOLD, 16));
		level.setForeground(Color.WHITE);
		level.setBackground(Color.RED);
		level.setBounds(275, 407, 140, 40);
		level.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
                                lvlno=lvlno+1;
                                if(lvlno==1)
                                    panel2.setVisible(true);
                                else if(lvlno==2)
                                {
                                    panel2.setVisible(false);
                                    lvlno=0;
                                }
			}
		});
		add(level);
		
		panel2 = new Panel();
		panel2.setBounds(420, 468, 92, 93);
		panel2.setVisible(false);
		add(panel2);
		panel2.setLayout(null);
		
		easy = new Button("Easy");
		easy.setBounds(0, 0, 92, 31);
		easy.setForeground(Color.WHITE);
		easy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		easy.setBackground(Color.RED);
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				count=0;
				cont.setEnabled(false);
				panel2.setVisible(false);
				speed=90;
				t.setDelay(speed);
				Esy=true;
				Mdum=Hrd=false;
				totalLoc=1720;
				Position=rand.nextInt(totalLoc);
			}
		});
		panel2.add(easy);
		
		
		medium = new Button("Medium");
		medium.setForeground(Color.WHITE);
		medium.setFont(new Font("Tahoma", Font.PLAIN, 14));
		medium.setBackground(Color.RED);
		medium.setBounds(0, 31, 92, 31);
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				cont.setEnabled(false);
				count=0;
				panel2.setVisible(false);
				Esy=Hrd=false;
				Mdum=true;
				speed=75;
				t.setDelay(speed);
				t.start();
				totalLoc=1660;
				Position=rand.nextInt(totalLoc);
			}
		});
		panel2.add(medium);
		
		hard = new Button("Hard");
		hard.setForeground(Color.WHITE);
		hard.setFont(new Font("Tahoma", Font.PLAIN, 14));
		hard.setBackground(Color.RED);
		hard.setBounds(0, 62, 92, 31);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				Hrd=true;
				Esy=Mdum=false;
				cont.setEnabled(false);
				count=0;
				panel2.setVisible(false);
				speed=65;
				t.setDelay(speed);
				t.start();
				totalLoc=1592;
				Position=rand.nextInt(totalLoc);
			}
		});
		panel2.add(hard);
		
		back = new Button("Main Menu");
		back.setForeground(Color.WHITE);
		back.setFont(new Font("Tahoma", Font.PLAIN, 14));
		back.setBackground(Color.BLACK);
		back.setBounds(590, 1, 80, 22);
		back.setVisible(false);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				FrontPage=true;
				t.stop();
                                clipMainTheme.stop();
				back.setVisible(false);
                                setMusic();
				repaint();
			}
		});
		add(back);
		
		sc = new Button("Score Board");
		sc.setBounds(275, 448, 140, 40);
		sc.setBackground(Color.RED);
		sc.setForeground(Color.WHITE);
		sc.setVisible(true);
		sc.setFont(new Font("Tahoma",Font.BOLD,16));
		sc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				scrbd.setVisible(true);
				easy.setEnabled(false);
				medium.setEnabled(false);
				hard.setEnabled(false);
				strt.setEnabled(false);
				cont.setEnabled(false);
				level.setEnabled(false);
				Snake ob=new Snake(true);
				sc.setVisible(false);
				
			}
		});
		add(sc);
		
		scrbd = new Button("Back");
		scrbd.setBounds(275, 448, 140, 40);
		scrbd.setBackground(Color.RED);
		scrbd.setForeground(Color.WHITE);
		scrbd.setVisible(false);
		scrbd.setFont(new Font("Tahoma",Font.BOLD,16));
		scrbd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				easy.setEnabled(true);
				medium.setEnabled(true);
				hard.setEnabled(true);
				strt.setEnabled(true);
				cont.setEnabled(true);
				level.setEnabled(true);
				sc.setVisible(true);
				scrbd.setVisible(false);
				Snake ob=new Snake(false);
			}
		});
		add(scrbd);
		
		Quit = new Button("Exit");
		Quit.setBounds(275, 489, 140, 40);
		Quit.setBackground(Color.RED);
		Quit.setForeground(Color.WHITE);
		Quit.setVisible(true);
		Quit.setFont(new Font("Tahoma",Font.BOLD,16));
		Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
                                ButtonClickSound();
				System.exit(0);
			}
		});
		add(Quit);
	}
        public void MainTheme()
        {
            try
            {
                AudioInputStream mainThm=AudioSystem.getAudioInputStream(new File("MainThemeSong.wav"));
                clipMainTheme=AudioSystem.getClip();
                clipMainTheme.open(mainThm);
                clipMainTheme.loop(Clip.LOOP_CONTINUOUSLY);
            }catch(Exception e){}
        }
        public void setMusic()
        {
            play=AudioPlayer.player;
            try{
                music=new AudioStream(new FileInputStream("start.wav"));
                data=music.getData();
                loop=new ContinuousAudioDataStream(data);
                play.start(loop);
            }catch(Exception e){}
        }
        public void EatingAppleSound()
        {
            Clip clip;
            try{
                AudioInputStream sound=AudioSystem.getAudioInputStream(new File("EatApple.wav"));
                clip=AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            }catch(Exception er){}
        }
        public void SnakeDeathGameOver()
        {
            Clip clip;
            try
            {
                AudioInputStream sound=AudioSystem.getAudioInputStream(new File("SnakeDeath.wav"));
                clip=AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            }catch(Exception e){}
        }
        public void ButtonClickSound()
        {
            Clip clip;
            try
            {
                AudioInputStream sound=AudioSystem.getAudioInputStream(new File("ClickButton.wav"));
                clip=AudioSystem.getClip();
                clip.open(sound);
                clip.start();
            }catch(Exception e){}
        }
	public void StoreCurrentInfo()
	{
		if(!gameover) {
		try
		{
			BufferedWriter w=new BufferedWriter(new FileWriter("Store.txt"));
			BufferedWriter w2=new BufferedWriter(new FileWriter("Direction.txt"));
			BufferedWriter w3=new BufferedWriter(new FileWriter("Available.txt"));
			w.write(Integer.toString(Position));
			w.newLine();
			w.write(Integer.toString(size));
			w.newLine();
			for(int i=0;i<size;i++)
			{
				w.write(Integer.toString(x[i]));
				w.newLine();
			}
			for(int i=0;i<size;i++)
			{
				w.write(Integer.toString(y[i]));
				w.newLine();
			}
			w.close();
			if(right)
				w2.write(Integer.toString(1));
			if(left)
				w2.write(Integer.toString(2));
			if(up)
				w2.write(Integer.toString(3));
			if(down)
				w2.write(Integer.toString(4));
			w2.newLine();
			if(Esy)
			{
				w2.write(Integer.toString(1));
			}
			if(Mdum)
			{
				w2.write(Integer.toString(2));
			}
			if(Hrd)
			{
				w2.write(Integer.toString(3));
			}
			w2.close();
			w3.write(Integer.toString(0));
			w3.close();
		}catch(Exception e) {}
		}
		else
		{
			try
			{
				BufferedWriter wx=new BufferedWriter(new FileWriter("Available.txt"));
				wx.write(Integer.toString(1));
				wx.close();
			}catch(Exception e) {}
		}
	}
	public SnakeWorkSpace(boolean scoreboard)
	{
		this.scoreboard=scoreboard;
		strt.setVisible(scoreboard);
		cont.setVisible(scoreboard);
		sc.setVisible(scoreboard);
	}
	public void setAppleLocation()
	{
		if(Esy && !Mdum && !Hrd)
		{
			int k=0;
			Loc=new appleLoc[totalLoc+2];
			for(int i=15;i<=645;i+=15)
			{
				for(int j=55;j<=640;j+=15)
				{
					Loc[k]=new appleLoc(i,j);
					k++;
				}
			}
		}
		if(Mdum && !Esy && !Hrd)
		{
			int k=0;
			Loc=new appleLoc[totalLoc];
			for(int j=55;j<=640;j+=15)
			{
				for(int i=15;i<=645;i+=15)
				{
					if(((j<280 || j>295) || (i<225 || i>435)) && j<=295)
		            {
		                Loc[k]=new appleLoc(i,j);
		                k++;
		            }
		            else if(((j<370 || j>385) || (i<225 || i>435)) && j>295)
		            {
		                Loc[k]=new appleLoc(i,j);
		                k++;
		            }
				}
			}
		}
		if(Hrd && !Esy && !Mdum)
		{
			int k=0;
			Loc=new appleLoc[totalLoc];
			for(int j=55;j<=640;j+=15)
		    {
		        for(int i=15;i<=645;i+=15)
		        {
		            if(((i>=15 && i<=105) || (i>=270 && i<=360) || (i>=525 && i<=645)) && (j>=175 && j<=190)){

		                Loc[k]=new appleLoc(i,j);
		                k++;
		            }
		            else if(((i>=15 && i<=105) || (i>=270 && i<=360) || (i>=525 && i<=645)) && (j>=490 && j<=505)){

		                Loc[k]=new appleLoc(i,j); 
		                k++;
		            }
		            else if(((i>=15 && i<=105) || (i>=150 && i<=480) || (i>=525 && i<=645)) && (j>=205 && j<=280))
		            {
		            	Loc[k]=new appleLoc(i,j);
		                k++;
		            }
		            else if(((i>=15 && i<=105) || (i>=150 && i<=480) || (i>=525 && i<=645)) && (j>=400 && j<=475))
		            {
		            	Loc[k]=new appleLoc(i,j);
		                k++;
		            }
		            else if((j>=55 && j<175) || (j>505 && j<=640) || (j>=295 && j<400))
		            {
		            	Loc[k]=new appleLoc(i,j);
		                k++;
		            }
		        }
		    }
		}
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
			right=true;
			left=up=down=false;
			setAppleLocation();
		}
		if(FrontPage) 
		{
			//IMAGEICON FOR THE FRONT IMAGE
			ImageIcon fnt=new ImageIcon(getClass().getResource("/image/FrontPic.png"));
			fpg=fnt.getImage();
			g.drawImage(fpg,0,0,this);
			
			strt.setVisible(true);
			cont.setVisible(true);
			level.setVisible(true);
			sc.setVisible(true);
			Quit.setVisible(true);
		}
		else if(!FrontPage && !gameover)
		{
                        play.stop(loop);
			sc.setVisible(false);
			level.setVisible(false);
			strt.setVisible(false);
			cont.setVisible(false);
			back.setVisible(true);
			Quit.setVisible(false);
			//BackGround
			g.setColor(Color.ORANGE);
			g.fillRect(0,0,690,700);
			
			//TITLE IMAGE
			ImageIcon title=new ImageIcon(getClass().getResource("/image/TitlePic.png"));
			tp=title.getImage();
			g.drawImage(tp, 0, 1, this);
		
			//BORDER OF MAIN GROUND
			g.setColor(Color.WHITE);
			g.drawRect(11, 55, W+1, H+1);
		
			//MAIN GROUND
			//g.setColor(Color.BLACK);
			//g.fillRect(12, 56, W, H);
			ImageIcon grnd=new ImageIcon(getClass().getResource("/image/GroundNew.png"));
			bg=grnd.getImage();
			g.drawImage(bg, 12, 56, this);
			if(Esy)
			{
				lv="Easy";
				speed=120;
			}
			if(Mdum)
			{
				lv="Medium";
				MediumLevel(g);
			}
			if(Hrd)
			{
				lv="Hard";
				HardLevel(g);
			}
			//HIGH SCORES AND SCORES
			g.setColor(Color.BLACK);
			g.setFont(new Font("Tahoma",Font.BOLD,14));
			g.drawString("High Score: "+HighScore, 10, 15);
			g.drawString("Score: "+Score, 10, 30);
			g.drawString("Level: "+lv, 10, 45);
			
			ImageIcon rh=new ImageIcon(getClass().getResource("/image/RightHeadBlack15.png"));
			ImageIcon lh=new ImageIcon(getClass().getResource("/image/LeftHeadBlack15.png"));
			ImageIcon uh=new ImageIcon(getClass().getResource("/image/UpHeadBlack15.png"));
			ImageIcon dh=new ImageIcon(getClass().getResource("/image/DownHeadBlack15.png"));
			ImageIcon b=new ImageIcon(getClass().getResource("/image/BlackBody15.png"));
			ImageIcon ap=new ImageIcon(getClass().getResource("/image/app.png"));
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
			if(Loc[Position].xPos==x[0] && Loc[Position].yPos==y[0])
			{
                                EatingAppleSound();
				size++;
				Score++;
				locationOfNewApple();
			}
			g.drawImage(apple, Loc[Position].xPos, Loc[Position].yPos, this);		
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
		if(Mdum && ((y[0]>=280 && y[0]<=295 && x[0]>=225 && x[0]<=450) || (y[0]>=370 && y[0]<=385 && x[0]>=225 && x[0]<=450)))
		{
			gameover=true;
			setHighScore();
			GameOver(g);
		}
		if(Hrd)
		{
			if((y[0]>=175 && y[0]<=190 && x[0]>=120 && x[0]<=255) || (y[0]>=175 && y[0]<=190 && x[0]>=375 && x[0]<=520))
			{
				gameover=true;
				setHighScore();
				GameOver(g);
			}
			if((y[0]>=490 && y[0]<=505 && x[0]>=120 && x[0]<=255) || (y[0]>=490 && y[0]<=505 && x[0]>=375 && x[0]<=520))
			{
				gameover=true;
				setHighScore();
				GameOver(g);
			}
			if((y[0]>=175 && y[0]<=280 && x[0]>=120 && x[0]<=135) || (y[0]>=175 && y[0]<=280 && x[0]>=495 && x[0]<=510))
			{
				gameover=true;
				setHighScore();
				GameOver(g);
			}
			if((y[0]>=400 && y[0]<=505 && x[0]>=120 && x[0]<=135) || (y[0]>=400 && y[0]<=505 && x[0]>=495 && x[0]<=510))
			{
				gameover=true;
				setHighScore();
				GameOver(g);
			}
		}
		
	}
	
	public void MediumLevel(Graphics g2)
	{
		g2.setColor(Color.WHITE);
		g2.fillRect(225, 280, 240, 30);
		
		g2.setColor(Color.WHITE);
		g2.fillRect(225, 370, 240, 30);
	}
	public void HardLevel(Graphics g2)
	{
		g2.setColor(Color.WHITE);
		
		g2.fillRect(120, 175, 150, 30);
		g2.fillRect(375, 175, 150, 30);
		
		g2.fillRect(120, 175, 30, 120);
		g2.fillRect(495, 175, 30, 120);

		g2.fillRect(120, 490, 150, 30);
		g2.fillRect(375, 490, 150, 30);
		
		g2.fillRect(120, 400, 30, 120);
		g2.fillRect(495, 400, 30, 120);
	}
	public void GameOver(Graphics g2)
	{
                clipMainTheme.stop();
		SnakeDeathGameOver();
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
		Position=rand.nextInt(totalLoc);
		for(int i=0;i<size;i++){
			if(Loc[Position].xPos==x[i] && Loc[Position].xPos==y[i])
			{
				locationOfNewApple();
			}
		}
	}
	
	public void setHighScore()
	{
		
		try {
		BufferedReader in;
		if(Esy) {
		in=new BufferedReader(new FileReader("HighScore.txt"));
		s=in.readLine();
		in.close();
		}
		if(Mdum)
		{
			in=new BufferedReader(new FileReader("MediumHighScore.txt"));
			s=in.readLine();
			in.close();
		}
		if(Hrd)
		{
			in=new BufferedReader(new FileReader("HardHighScore.txt"));
			s=in.readLine();
			in.close();
		}
		
		HighScore=Integer.parseInt(s);
		if(Score>HighScore)
			HighScore=Score;
		s=Integer.toString(HighScore);
		if(Esy) {
			BufferedWriter out=new BufferedWriter(new FileWriter("HighScore.txt"));
			out.write(s);
			out.close();
		}
		if(Mdum) {
			BufferedWriter out=new BufferedWriter(new FileWriter("MediumHighScore.txt"));
			out.write(s);
			out.close();
		}
		if(Hrd)
		{
			BufferedWriter out=new BufferedWriter(new FileWriter("HardHighScore.txt"));
			out.write(s);
			out.close();
		}
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
			count+=1;
			right=true;
			up=down=false;
                        t.start();
		}
		if(key==KeyEvent.VK_LEFT && !right && !FrontPage)
		{
			count+=1;
			left=true;
			up=down=false;
                        t.start();
		}
		if(key==KeyEvent.VK_UP && !down && !FrontPage)
		{
			count+=1;
			up=true;
			right=left=false;
                        t.start();
		}
		if(key==KeyEvent.VK_DOWN && !up && !FrontPage)
		{
			count+=1;
			down=true;
			right=left=false;
                        t.start();
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