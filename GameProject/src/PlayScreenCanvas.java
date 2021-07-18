import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class PlayScreenCanvas extends Canvas
{
    private Image img;
    private Graphics buffer;
    private Bar bar;
    private Stage st;
    Ball ball;
    BallThread bThread;
    NumberThread numThread;    
    ItemDefinition itemDef;
        
    private final int TOP;
    private final int BOTTOM;
    private final int LEFT;
    private final int RIGHT;
    private final String startPush = "START ��ư�� ��������";
    private final String[] countNum = {"3", "2", "1", "GO!"};
    private String str;
    private boolean ballThreadStart;
    
    private Color[] brickColor = {Color.cyan, Color.red, Color.blue, Color.black, Color.white, Color.orange,
    								Color.darkGray, Color.magenta, Color.pink, Color.yellow};    
    private int brokenBricks=0;
    private int row;
    private int col;
    
    private int userScore = 0;
    private String scoreStr;
    private String brickNumStr;
    private final String heartStr = "��";
    private int heartNum = 3;
    private boolean gameOverChk;
    private int ballSpeed;
    	
	/*������*/
	public PlayScreenCanvas(JPanel playScreenCenter)
	{
		setSize(600, 500);
		
		brickNumStr = "0";
		ballSpeed = 10;
		scoreStr = null;
		str = startPush;
		ballThreadStart = false;
		gameOverChk = false;
		itemDef = new ItemDefinition();
		img = null;
		buffer = null;
		bar = new Bar();
		ball = new Ball();
		st = new Stage(8,9);
		row = 8;
		col = 9;
		//bThread = new BallThread();	
		numThread = new NumberThread();		
		
		setBackground(Color.lightGray);		
		
		brickInit();
		
		Insets insets = playScreenCenter.getInsets();
		
		TOP = insets.top;
		LEFT = insets.left;
		BOTTOM = 440 - insets.bottom;
		RIGHT = 510 - insets.right;
		
				
	}		
	/*���� �ʱ�ȭ*/
	public void brickInit()
	{
		st.stageTwo();		
	}
	/*�� �ӵ� ����*/
	public void setBallSpeed(int speed)
	{
		if(speed == 0)
			ballSpeed = 3;
		else if(speed == 1)
			ballSpeed = 10;
		else
			ballSpeed = 100;
	}
	
	public void paint(Graphics g)
    {
		if(img == null)
    		img = createImage(600, 500);
    	if( buffer == null )
    		buffer = img.getGraphics();
		
		/*�� ���� ����*/
    	buffer.clearRect(0, 0, 600,500);	    	
    	    	
    	/*����*/
    	for(int i=0; i<st.brick.length; i++)
    	{
    		for(int j=0; j<st.brick[i].length; j++)
    		{
    			if(st.temp[i][j] == st.brick[i][j])
    			{
    				st.brick[i][j].HEIGHT = st.brick[i][j].WIDTH = 0;
    				st.brick[i][j].x = st.brick[i][j].y = 700;
    			}
    			else
    			{
    				//buffer.setColor(brickColor[(int)(Math.random()*10)]);	//���� ����
    				buffer.setColor(brickColor[i]);    				
    				buffer.fillRect(st.brick[i][j].x, st.brick[i][j].y, 
    								st.brick[i][j].WIDTH, st.brick[i][j].HEIGHT);  
    			}
    		}
    	}    	
    	
    	/*��*/
    	buffer.setColor(Color.blue);
    	buffer.fillOval(ball.x, ball.y, ball.SIZE, ball.SIZE);    	

    	/*��*/
    	buffer.setColor(Color.black);
    	if(bar.x <= RIGHT-bar.WIDTH)
    		buffer.fillRect(bar.x, 400, bar.WIDTH, bar.HEIGHT);
    	else
    		buffer.fillRect(RIGHT-bar.WIDTH, 400, bar.WIDTH, bar.HEIGHT);
    	
    	/*�� �����尡 ���۵Ǳ� �� ī��Ʈ*/
    	if(ballThreadStart == false)
    	{
    		buffer.setFont(new Font("SanSerif", Font.BOLD, 25));
    		if(str == startPush)
    			buffer.drawString(str, 130, 300);
    		else
    			buffer.drawString(str, 239, 300);
    	}  	
    	
    	buffer.setFont(new Font("SanSerif", Font.BOLD, 15));
    	brickNumStr = String.format(" %d / %d ", brokenBricks, row*col);
    	buffer.drawString(brickNumStr, 10, BOTTOM-20);
    	scoreStr = String.format("SCORE : %d", userScore);
    	buffer.drawString(scoreStr, 10, BOTTOM-5);
    	
    	//���� ǥ��
    	for(int i=0; i<heartNum; i++)
    	{
    		buffer.setFont(new Font("SanSerif", Font.PLAIN, 25));
    		buffer.drawString(heartStr, RIGHT-70+i*20, BOTTOM-5);
    	}
    	
    	//GAME OVER
    	if(gameOverChk == true)
    	{
    		buffer.setFont(new Font("SanSerif", Font.PLAIN, 40));
    		buffer.drawString("GAME OVER", 135, BOTTOM-120);
    	}    		
    	
    	/*����ȭ�鿡 �׷��� �׸� ����*/
    	g.drawImage(img, 0, 0, this);
    }

	public void update(Graphics g)
	{
		paint(g);
	}
	public void playScreenEventSet()
	{
		/*�� �����̱�*/
		addMouseMotionListener(
				new MouseMotionAdapter()
				{
					public void mouseMoved(MouseEvent e)
					{
						bar.x = e.getX();
						bar.y = e.getY();
						repaint();
					}
				}
		);
	}
	public void playScreenThreadStop()
	{
		bThread.interrupt();
		numThread.interrupt();
	}
	public void playScreenThreadSet()
	{
		numThread.start();		
	}
	public void threadSleep(int mill)
	{
		try
		{
			bThread.sleep(mill);
		}
		catch(InterruptedException e){}
	}
	class BallThread extends Thread
	{
		public void run()
		{
			ballThreadStart = true;
			while(true)
			{
				if(heartNum == 0)
				{
					gameOverChk = true;
					repaint();
					break;
				}
				if(brokenBricks == row*col)
				{
					st = new Stage(row, col);
					brickInit();
					ball.x = 239;
					ball.y = 375;
					brokenBricks = 0;
				}
				/*�� �̵� ����*/
				ball.x += ball.xStep;
				ball.y += ball.yStep;
				
				/*�� ����*/
				if(ball.x <= LEFT)
				{
					ball.x = LEFT;
					ball.xStep = -ball.xStep;
				}
				/*�� ������*/
				if(ball.x >= RIGHT-ball.SIZE)
				{
					ball.x = RIGHT-ball.SIZE;
					ball.xStep = -ball.xStep;
				}
				/*�� ����*/
				if(ball.y <= TOP)
				{
					ball.y = TOP;
					ball.yStep = -ball.yStep;
				}
				/*�� �Ʒ���*/
				if(ball.y >= BOTTOM-ball.SIZE)
				{
					ball.y = BOTTOM-ball.SIZE;
					ball.yStep = -ball.yStep;
				}
				/*�� ����*/
				if(((ball.y+ball.SIZE/2)-400)>=0
						&& (((ball.x+ball.SIZE/2)>=bar.x) && ((ball.x+ball.SIZE/2)<=(bar.x+bar.WIDTH))))
				{
					ball.y = 400-ball.SIZE;
					ball.yStep = -ball.yStep;
				}
				//�ٴڿ� �������ٸ�
				if((ball.y+ball.SIZE/2)<BOTTOM && (ball.y+ball.SIZE/2)>=BOTTOM-(ball.SIZE/2))
				{
					heartNum-=1;					
					ball.x = 239;	//�� ���� ��ġ
					ball.y = 375;
					bar.x = bar.y = 200;	//�� ���� ��ġ��
					threadSleep(500);
				}
					
				/*���� �浹*/
				OuterLoop:				
				for(int i=0; i<st.brick.length; i++)
				{
					for(int j=0; j<st.brick[i].length; j++)
					{
						/*��� �Ʒ���*/
						if((ball.y+ball.SIZE/2)>(st.brick[i][j].y+st.brick[i][j].HEIGHT) &&
								(ball.y+ball.SIZE/2)<= st.brick[i][j].y+st.brick[i][j].HEIGHT+12	
							&& (ball.x+ball.SIZE/2)>st.brick[i][j].x-12 && 
									(ball.x+ball.SIZE/2)<=(st.brick[i][j].x+st.brick[i][j].WIDTH+12))
						{
							st.temp[i][j] = st.brick[i][j];
							ball.y = st.brick[i][j].y+st.brick[i][j].HEIGHT;
							ball.yStep = -ball.yStep;
							brokenBricks++;
							userScore += 8;
							
							break OuterLoop;
							
						}
						/*��� ����*/						
						else if((ball.y+ball.SIZE/2)<st.brick[i][j].y && 
								(ball.y+ball.SIZE/2)>=(st.brick[i][j].y-12) &&
							    (ball.x+ball.SIZE/2)>=st.brick[i][j].x-12 && 
								(ball.x+ball.SIZE/2)<(st.brick[i][j].x+st.brick[i][j].WIDTH+12))
						{
							st.temp[i][j] = st.brick[i][j];
							ball.y = st.brick[i][j].y-ball.SIZE;
							ball.yStep = -ball.yStep;
							brokenBricks++;
							userScore += 15;
							
							break OuterLoop;							
						}	
						
						/*��� ������*/ 
						if((ball.x+ball.SIZE/2)>(st.brick[i][j].x+st.brick[i][j].WIDTH) && 
								(ball.x+ball.SIZE/2)<=(st.brick[i][j].x+st.brick[i][j].WIDTH+12)
							&& (ball.y+ball.SIZE/2)>=st.brick[i][j].y-12 && 
									((ball.y+ball.SIZE/2)<st.brick[i][j].y+st.brick[i][j].HEIGHT+12))
						{
							st.temp[i][j] = st.brick[i][j];
							ball.x = st.brick[i][j].x+st.brick[i][j].WIDTH;
							ball.xStep = -ball.xStep;
							brokenBricks++;
							userScore += 11;
							
							break OuterLoop;
							
						}
						/*��� ����*/						
						else if((ball.x+ball.SIZE/2)<st.brick[i][j].x && 
								(ball.x+ball.SIZE/2)>=(st.brick[i][j].x-12) && 
								(ball.y+ball.SIZE/2)>st.brick[i][j].y-12 && 
								(ball.y+ball.SIZE/2)<=(st.brick[i][j].y+st.brick[i][j].HEIGHT+12))
						{
							st.temp[i][j] = st.brick[i][j];
							ball.x = st.brick[i][j].x-ball.SIZE;
							ball.xStep = -ball.xStep;
							brokenBricks++;
							userScore += 13;
							
							break OuterLoop;							
						}						
					}
				}											
				repaint();
				threadSleep(ballSpeed);				
			}
		}
	}	
	class NumberThread extends Thread
	{
		public void run()
		{
			for(int i=0; i<countNum.length; i++)
			{
				str = countNum[i];
				repaint();
				try
				{
					sleep(1000);
				}
				catch(InterruptedException e){}
			}
			bThread = new BallThread();	
			bThread.start();
			playScreenEventSet();
		}
	}	
}

