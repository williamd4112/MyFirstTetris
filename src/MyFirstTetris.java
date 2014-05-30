import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFirstTetris extends JPanel implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�ˬd�C������
	boolean gameover=false;
	//��L�ƥ�
	boolean leftPressed=false;
	boolean rightPressed=false;
	boolean downPressed=false;
	boolean upPressed=false;
	//�G��Map
	int[][] ColorMap = new int[10][20];
	int[][] occupy = new int[10][20];
	int[][] Noccupy = new int[10][10];
	int[][] NColorMap = new int[10][10];
 	//����պ��� �B����Ҧ� �B �ĴX�Ӥ��
    int[][][] Xmod =
    {
            { {0,0,1,2}, {0,0,0,1}, {2,0,1,2}, {0,1,1,1} },  
            { {0,0,1,1}, {1,2,0,1}, {0,0,1,1}, {1,2,0,1} },  
            { {1,1,0,0}, {0,1,1,2}, {1,1,0,0}, {0,1,1,2} },  
            { {0,1,2,2}, {0,1,0,0}, {0,0,1,2}, {1,1,0,1} },  
            { {1,0,1,2}, {1,0,1,1}, {0,1,1,2}, {0,0,1,0} },  
            { {0,1,0,1}, {0,1,0,1}, {0,1,0,1}, {0,1,0,1} },  
            { {0,1,2,3}, {0,0,0,0}, {0,1,2,3}, {0,0,0,0} }   
    };
      
    int[][][] Ymod = 
    {
            { {0,1,0,0}, {0,1,2,2}, {0,1,1,1}, {0,0,1,2} },  
            { {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  
            { {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  
            { {0,0,0,1}, {0,0,1,2}, {0,1,1,1}, {0,1,2,2} },  
            { {0,1,1,1}, {0,1,1,2}, {0,0,1,0}, {0,1,1,2} },  
            { {0,0,1,1}, {0,0,1,1}, {0,0,1,1}, {0,0,1,1} },  
            { {0,0,0,0}, {0,1,2,3}, {0,0,0,0}, {0,1,2,3} }  
    };
	//�O���O���R
    int Score = 0;
    int Line = 0;
    double Level = 1;
    int NextX = 300;
    int NextY = 120;
    JLabel ScoreLabel = new JLabel();
    JLabel LineLabel = new JLabel(); 
    JLabel LevelLabel = new JLabel(); 
    JLabel NextLabel = new JLabel();
    
	//�\�� : ������l��
	public void WindowInit()
	{
		//�����Ѽ� + �I���C��
		this.setLayout(null); 
		this.setPreferredSize(new Dimension(480,480));
		this.setBackground(Color.BLACK);
		
		//��l�Ʀa��
		for(int i = 0 ; i<10 ; i++)
		{
			for(int j = 0 ; j<20 ; j++)
			{
				occupy[i][j] = 0 ;
			}
		}
		
	}
	//�\��: �O���O��l��
	public void ScoreBoardInit()
	{
		//��l�ưO���O
		//���Ƹ�B
    	ScoreLabel.setText("Score : " + Score);
    	ScoreLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	ScoreLabel.setForeground(Color.white);
    	ScoreLabel.setBounds(300,10,400,30);
  	    this.add(ScoreLabel);
  	    //�������
  	    LineLabel.setText("Line : " + Line);
    	LineLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	LineLabel.setForeground(Color.white);
    	LineLabel.setBounds(300,40,200,30);
  	    this.add(LineLabel);
  	    //����
  	    LevelLabel.setText("Level : " + (int)Level);
    	LevelLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	LevelLabel.setForeground(Color.white);
    	LevelLabel.setBounds(300,80,200,30);
  	    this.add(LevelLabel);
  	    //�U�@��
  	    NextLabel.setText("Next");
    	NextLabel.setFont(new Font("Serif", Font.BOLD, 20));
    	NextLabel.setForeground(Color.white);
    	NextLabel.setBounds(300,140,200,30);
  	    this.add(NextLabel);
	}
	
	
	
	 //��L�ƥ� 
	  public void keyPressed(KeyEvent event)
	  {
	    if (event.getKeyCode()==37) 
	    {
	      leftPressed=true;
	    }
	    if (event.getKeyCode()==39) 
	    {
	      rightPressed=true;
	    }
	    if (event.getKeyCode()==40) 
	    {
	      downPressed=true;
	    }
	    if (event.getKeyCode()==38)
	    {
	      upPressed=true;
	    }
	 
	  }

	  public void keyReleased(KeyEvent event)
	  {

	    if (event.getKeyCode()==37) 
	    {
	      leftPressed=false;
	    }
	    if (event.getKeyCode()==39)
	    {
	      rightPressed=false;
	    }
	    if (event.getKeyCode()==40) 
	    {
	      downPressed=false;
	    }
	    if (event.getKeyCode()==38)
	    {
	      upPressed=false;
	    }
	 
	  }
	  
	  public void keyTyped(KeyEvent event)
	  {

	  }
	
	/*---------ø�ϥ\��----------*/  
	//�\�� : ø�s��� (�@��block����¦,�f�tDrawBlock)
	public void DrawCube(int x , int y , int ColorSeed)
	{
		occupy[x][y] = 1;
		ColorMap[x][y]= ColorSeed;
		
	}
	//�\��: ø�s����
	public void DrawCubeSet(int x , int y , int[] Xmod , int[] Ymod , int ColorSeed)
	{
		for(int d = 0 ; d < 4 ; d++)
		{
			DrawCube(x+Xmod[d],y+Ymod[d],ColorSeed);
		}
	}
	//�\��: ø�s�U�@�Ӥ��
	public void DrawNextCube(int x , int y , int ColorSeed)
	{
		Noccupy[x][y] = 1;
		NColorMap[x][y]= ColorSeed;
	}
	//�\��: ø�s�U�@�դ����
	public void DrawNextCubeSet(int x , int y , int[] Xmod , int[] Ymod , int ColorSeed)
	{
		for(int d = 0 ; d < 4 ; d++)
		{
			DrawNextCube(x+Xmod[d],y+Ymod[d],ColorSeed);
		}
	}
	//�\��: �������
	public void EraseCube(int x , int y)
	{
		occupy[x][y] = 0;
	}
    //    �������(�{�{����) 
	public void EraseCube(int x , int y,boolean blink)
	{
		if(blink)
		{
			int Timer = 0;
		    while(Timer<=1500)
		    {
		    	if(Timer%30==0)occupy[x][y] = 1 - occupy[x][y];
				repaint();
				Timer++;
		    }
		}
	}
	
	//�\��: �M�������
	public void EraseCubeSet(int x , int y , int[] Xmod , int[] Ymod)
	{
		for(int i = 0 ; i < 4 ; i++)
		{
			EraseCube(x+Xmod[i],y+Ymod[i]);
		}
	}
	//�\��: ��s�U�@�Ӥ����
	public void RefreshNext()
	{
		for(int x = 0;x<6;x++)
		{
			for(int y = 0 ; y<6 ; y++)
			{
				Noccupy[x][y] = 0;
				NColorMap[x][y] = 0;
			}
			
		}
	}
	//�\�� : ø��
	public void paint(Graphics g)
	{    
		super.paint(g);
		for(int n = 0;n<6;n++)
		{
			for(int m = 0;m<6;m++)
			{
				if(Noccupy[n][m]==0)
				{
					//ø�s����
				    g.setColor(Color.BLACK);
				    g.fillRect(300+n*24,200+m*24,24,24);
				}
				else
				{
					//��m���
					Color SelectColor = new Color(0, 0, 0);
					switch(NColorMap[n][m])
					{
					  case 1:
						   SelectColor = new Color(0, 240, 240);
						   break;
					  case 2:
						   SelectColor = new Color(0, 0, 240);
						   break;
					  case 3:
						   SelectColor = new Color(240, 160, 0);
						   break;
					  case 4:
						   SelectColor = new Color(240, 240, 0);
						   break;
					  case 5:
						   SelectColor = new Color(0, 240, 0);
						   break;
					  case 6:
						   SelectColor = new Color(160, 0, 240);
						   break;
					  case 7:
						   SelectColor = new Color(240, 0, 0); 
						   break;
					}
				    //ø�s���
				    //ø�s���
				    g.setColor(Color.WHITE);
				    g.fillRect(300+n*24,200+m*24,24,24);
				    g.setColor(SelectColor);
				    g.fillRect(300+n*24+1,200+m*24+1,22,22);
				}
			}
		}
		//���y�a�ϨåBø�s
		for(int x = 0 ; x<10;x++)
		{
			for(int y = 0 ; y<20 ; y++)
			{
	
				if(occupy[x][y]==0)
				{	
					//ø�s����
				    g.setColor(Color.GRAY);
				    g.fillRect(x*24,y*24,24,24);
				    g.setColor(Color.BLACK);
				    g.fillRect(x*24+1,y*24+1,22,22);
				
				}
				else
				{
					//��m���
					Color SelectColor = new Color(0, 0, 0);
					switch(ColorMap[x][y])
					{
					  case 1:
						   SelectColor = new Color(0, 240, 240);
						   break;
					  case 2:
						   SelectColor = new Color(0, 0, 240);
						   break;
					  case 3:
						   SelectColor = new Color(240, 160, 0);
						   break;
					  case 4:
						   SelectColor = new Color(240, 240, 0);
						   break;
					  case 5:
						   SelectColor = new Color(0, 240, 0);
						   break;
					  case 6:
						   SelectColor = new Color(160, 0, 240);
						   break;
					  case 7:
						   SelectColor = new Color(240, 0, 0); 
						   break;
					}
				    //ø�s���
				    //ø�s���
				    g.setColor(Color.WHITE);
				    g.fillRect(x*24,y*24,24,24);
				    g.setColor(SelectColor);
				    g.fillRect(x*24+1,y*24+1,22,22);
				}
			
			}
		}

	}
	
	/*---------MAP�t��----------*/
	//�\��: �H���������� �P ����ҫ�
	public int Random(String item)
	{
		int Select;
		if(item == "Set")
		{
		 Select=(int)(Math.random()*7);
		 return Select;
		}
		if(item == "Mod")
		{
		 Select =(int)(Math.random()*4);
		 return Select;
		}
		return 0;
		
	}
	
	//�\�� : ���� (�B�@������� ��x �P ��y)
	public void Falling(int Set , int Mod , int ColorSeed)
	{
		boolean Stop=false;
		int Timer =0;
		int Xpos=5;
	    int Ypos=0;
        int delay = 50;
        int[] XDrawArry = Xmod[Set][Mod];
        int[] YDrawArry = Ymod[Set][Mod];

		//���M���W�@��frame�����
		//�bmap�W���U���@��
		DrawCubeSet(Xpos,Ypos, XDrawArry,YDrawArry,ColorSeed);
	    repaint();
		while(!Stop)
		{
		  try { Thread.sleep(delay); } catch (Exception ignore) {}
		  EraseCubeSet(Xpos,Ypos,XDrawArry,YDrawArry);
		  //���k��V
		  if(leftPressed && MoveCheck(Xpos-1,Ypos,Set,Mod) ) Xpos--;
		  if(rightPressed && MoveCheck(Xpos+1,Ypos,Set,Mod) ) Xpos++;
		  if(downPressed && MoveCheck(Xpos,Ypos+1,Set,Mod) ) Ypos++;
		  if(upPressed && MoveCheck(Xpos,Ypos,Set,(Mod+1)%4) ) 
		  {
		     Mod = (Mod+1)%4;
		     XDrawArry = Xmod[Set][Mod];
		     YDrawArry = Ymod[Set][Mod];
		     upPressed = false;
		  }
		  //�@�q�ɶ��N�U���@��
          if(Timer%21-Level==0)Ypos++;
          //�p�G�U���ᤣ�X�k�N�^�h
          if(!MoveCheck(Xpos,Ypos,Set,Mod))
          {
        	  Ypos--;
        	  Stop = true;
          }	  
		  Timer++;
		  DrawCubeSet(Xpos,Ypos,XDrawArry,YDrawArry,ColorSeed);
	      repaint();
		}
		

		  
	}
	//�\��: �ˬd��ê��
	public boolean MoveCheck (int x , int y , int Set , int Mod)
	{
		for(int k = 0 ; k<4 ; k++)
		{
			int Xcube = x+Xmod[Set][Mod][k];
			int Ycube = y+Ymod[Set][Mod][k];
			//����ˬd
			if(Xcube>=10)return false;
			if(Xcube<0)return false;
			if(Ycube>=20)return false;
			if(Ycube<0)return false;
			//�I���ˬd(�bFalling�\�त���k��V����U��ǤJ�w���bmap�W���ʹL����m��������ˬd)
			if(occupy[Xcube][Ycube]==1)return false;
			
		}
		
		return true;
	}
	//�\��: �C�������ˬd
	public boolean GameOverCheck()
	{
		for(int x=0 ; x<10 ; x++)
		{   
	        if(occupy[x][0]==1)return true;
	        
		}
		return false;
	}
	//�\��: �ˬd����
	public void CheckCompletion()
	{
		int[] CheckRows = new int[20];
		int Combo = 0;
		for(int y=0;y<20;y++)
		{   
			int Check=0; 
			for(int x=0;x<10;x++)
			{  
				if(occupy[x][y]==1)Check++;
			}
			//�p�G���10�泣�񺡴N�b����}�C���]�w�Ӧ欰1
			if(Check==10)
			{    
				CheckRows[y] = 1;
				Combo++;
				AddLine();
				
			}	
		}
		AddScore(Combo);
		Delete(CheckRows);
		Drop(CheckRows);
		
	}
	//�\��: ����(�f�t�ˬd����)
	public void Delete(int[] DeleteRows)
	{
	for(int t = 0; t<=5;t++)
	{
		//�q�W���U�ˬd
		for(int Row = 0; Row<20 ;Row++)
		{
			//�p�G�J��񺡪���� �N ���h
			if(DeleteRows[Row]==1)
			{
				
				for(int x=0;x<10;x++)
				{
					EraseCube(x,Row,true);
				   	
				}
			     
			}
		}
		try { Thread.sleep(100); } catch (Exception ignore) {}
	}
	}
	//�\��: �V�U�ɺ�(�f�t�ˬd����)
	public void Drop(int[] DropRows)
	{
	   //�ѤW���U�ˬd
		for(int Row = 0 ; Row<20;Row++)
		{
			if(DropRows[Row]==1)
			{
				for(int y = Row ; y>0;y--)
				{
					for(int x =0;x<10;x++)
					{
						occupy[x][y] = occupy[x][y-1];
						ColorMap[x][y] = ColorMap[x][y-1];				
				}
				}
			}
		}
	}
	
	/*---------�O���O----------*/
	//�\��: ���GAMEOVER
     public void GameOverShow()
	 {
	    JLabel GameOverLabel = new JLabel("GAME OVER");
	    GameOverLabel.setFont(new Font("Serif", Font.BOLD, 20));
	    GameOverLabel.setForeground(Color.white);
	    GameOverLabel.setBounds(300,400,200,30);
	    add(GameOverLabel);
	    repaint();
	 }
    //�\��: �n��
 	public void AddScore(int Combo)
 	{
 		for(int i = 0;i<Combo;i++)
 		{
 			Score = Score + 100*Combo;
 		}
 		
 		ScoreLabel.setText("Score : " + Score);
 	}
    //�\��: �n��
 	public void AddLine()
 	{
 		Line = Line + 1;
 		LineLabel.setText("Line : " + Line);
 	}
    //�\��: ��������
 	public void CheckLevelUp()
 	{   
 		Level = (int)(Math.floor(Score/1000))+1;
 		LevelLabel.setText("Level : " + (int)Level);
 		
 		
 	}
 	//�\��: ��ܤU�@��
 	public void ShowNext(int Set , int Mod , int ColorSeed)
 	{
 		int[] XDrawArry = Xmod[Set][Mod];
 	    int[] YDrawArry = Ymod[Set][Mod];
 		DrawNextCubeSet(1,0, XDrawArry,YDrawArry,ColorSeed);
 	}
	 
    
 	//Main
	public static void main (String[] args)
	{
		boolean First = true;
		JFrame window = new JFrame("etris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		MyFirstTetris tetris = new MyFirstTetris();
		tetris.WindowInit();
		window.add(tetris);
	    window.pack();
	    window.setResizable(false);
	    window.pack();
		window.setVisible(true);
		window.addKeyListener(tetris); 
		
		try { Thread.sleep(1000); } catch (Exception ignore) {}
		
		tetris.gameover = false;
		tetris.ScoreBoardInit();
		int NextSet = tetris.Random("Set");
		int NextMod = tetris.Random("Mod");
		while(!tetris.gameover)
		{	
			int Set ;
			int Mod ;
			if(First)
			{	
			   Set = tetris.Random("Set");
			   Mod = tetris.Random("Mod");
			}
			else
			{
				Set = NextSet;
				Mod = NextMod;
				NextSet = tetris.Random("Set");
			    NextMod = tetris.Random("Mod");
			}
			tetris.ShowNext(NextSet,NextMod,NextSet+1);
			tetris.Falling(Set,Mod,Set+1);
			tetris.CheckCompletion();
			tetris.RefreshNext();
			tetris.CheckLevelUp();
			tetris.gameover = tetris.GameOverCheck();
			First = false;
		}
		
		tetris.GameOverShow();
	}
	 
}
