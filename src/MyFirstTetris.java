import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFirstTetris extends JPanel implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//檢查遊戲結束
	boolean gameover=false;
	//鍵盤事件
	boolean leftPressed=false;
	boolean rightPressed=false;
	boolean downPressed=false;
	boolean upPressed=false;
	//二值Map
	int[][] ColorMap = new int[10][20];
	int[][] occupy = new int[10][20];
	int[][] Noccupy = new int[10][10];
	int[][] NColorMap = new int[10][10];
 	//方塊組種類 、旋轉模式 、 第幾個方塊
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
	//記分板必涮
    int Score = 0;
    int Line = 0;
    double Level = 1;
    int NextX = 300;
    int NextY = 120;
    JLabel ScoreLabel = new JLabel();
    JLabel LineLabel = new JLabel(); 
    JLabel LevelLabel = new JLabel(); 
    JLabel NextLabel = new JLabel();
    
	//功能 : 視窗初始化
	public void WindowInit()
	{
		//視窗參數 + 背景顏色
		this.setLayout(null); 
		this.setPreferredSize(new Dimension(480,480));
		this.setBackground(Color.BLACK);
		
		//初始化地圖
		for(int i = 0 ; i<10 ; i++)
		{
			for(int j = 0 ; j<20 ; j++)
			{
				occupy[i][j] = 0 ;
			}
		}
		
	}
	//功能: 記分板初始化
	public void ScoreBoardInit()
	{
		//初始化記分板
		//分數資運
    	ScoreLabel.setText("Score : " + Score);
    	ScoreLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	ScoreLabel.setForeground(Color.white);
    	ScoreLabel.setBounds(300,10,400,30);
  	    this.add(ScoreLabel);
  	    //消除行數
  	    LineLabel.setText("Line : " + Line);
    	LineLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	LineLabel.setForeground(Color.white);
    	LineLabel.setBounds(300,40,200,30);
  	    this.add(LineLabel);
  	    //等級
  	    LevelLabel.setText("Level : " + (int)Level);
    	LevelLabel.setFont(new Font("Serif", Font.BOLD, 18));
    	LevelLabel.setForeground(Color.white);
    	LevelLabel.setBounds(300,80,200,30);
  	    this.add(LevelLabel);
  	    //下一組
  	    NextLabel.setText("Next");
    	NextLabel.setFont(new Font("Serif", Font.BOLD, 20));
    	NextLabel.setForeground(Color.white);
    	NextLabel.setBounds(300,140,200,30);
  	    this.add(NextLabel);
	}
	
	
	
	 //鍵盤事件 
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
	
	/*---------繪圖功能----------*/  
	//功能 : 繪製方格 (作為block的基礎,搭配DrawBlock)
	public void DrawCube(int x , int y , int ColorSeed)
	{
		occupy[x][y] = 1;
		ColorMap[x][y]= ColorSeed;
		
	}
	//功能: 繪製方格組
	public void DrawCubeSet(int x , int y , int[] Xmod , int[] Ymod , int ColorSeed)
	{
		for(int d = 0 ; d < 4 ; d++)
		{
			DrawCube(x+Xmod[d],y+Ymod[d],ColorSeed);
		}
	}
	//功能: 繪製下一個方塊
	public void DrawNextCube(int x , int y , int ColorSeed)
	{
		Noccupy[x][y] = 1;
		NColorMap[x][y]= ColorSeed;
	}
	//功能: 繪製下一組方塊組
	public void DrawNextCubeSet(int x , int y , int[] Xmod , int[] Ymod , int ColorSeed)
	{
		for(int d = 0 ; d < 4 ; d++)
		{
			DrawNextCube(x+Xmod[d],y+Ymod[d],ColorSeed);
		}
	}
	//功能: 消除方塊
	public void EraseCube(int x , int y)
	{
		occupy[x][y] = 0;
	}
    //    消除方塊(閃爍版本) 
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
	
	//功能: 清除方塊組
	public void EraseCubeSet(int x , int y , int[] Xmod , int[] Ymod)
	{
		for(int i = 0 ; i < 4 ; i++)
		{
			EraseCube(x+Xmod[i],y+Ymod[i]);
		}
	}
	//功能: 刷新下一個方塊組
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
	//功能 : 繪圖
	public void paint(Graphics g)
	{    
		super.paint(g);
		for(int n = 0;n<6;n++)
		{
			for(int m = 0;m<6;m++)
			{
				if(Noccupy[n][m]==0)
				{
					//繪製網格
				    g.setColor(Color.BLACK);
				    g.fillRect(300+n*24,200+m*24,24,24);
				}
				else
				{
					//色彩選擇
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
				    //繪製方塊
				    //繪製方塊
				    g.setColor(Color.WHITE);
				    g.fillRect(300+n*24,200+m*24,24,24);
				    g.setColor(SelectColor);
				    g.fillRect(300+n*24+1,200+m*24+1,22,22);
				}
			}
		}
		//掃描地圖並且繪製
		for(int x = 0 ; x<10;x++)
		{
			for(int y = 0 ; y<20 ; y++)
			{
	
				if(occupy[x][y]==0)
				{	
					//繪製網格
				    g.setColor(Color.GRAY);
				    g.fillRect(x*24,y*24,24,24);
				    g.setColor(Color.BLACK);
				    g.fillRect(x*24+1,y*24+1,22,22);
				
				}
				else
				{
					//色彩選擇
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
				    //繪製方塊
				    //繪製方塊
				    g.setColor(Color.WHITE);
				    g.fillRect(x*24,y*24,24,24);
				    g.setColor(SelectColor);
				    g.fillRect(x*24+1,y*24+1,22,22);
				}
			
			}
		}

	}
	
	/*---------MAP演算----------*/
	//功能: 隨機選取方塊組 與 旋轉模型
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
	
	//功能 : 掉落 (運作中方塊的 根x 與 根y)
	public void Falling(int Set , int Mod , int ColorSeed)
	{
		boolean Stop=false;
		int Timer =0;
		int Xpos=5;
	    int Ypos=0;
        int delay = 50;
        int[] XDrawArry = Xmod[Set][Mod];
        int[] YDrawArry = Ymod[Set][Mod];

		//先清除上一個frame的方塊
		//在map上先下降一格
		DrawCubeSet(Xpos,Ypos, XDrawArry,YDrawArry,ColorSeed);
	    repaint();
		while(!Stop)
		{
		  try { Thread.sleep(delay); } catch (Exception ignore) {}
		  EraseCubeSet(Xpos,Ypos,XDrawArry,YDrawArry);
		  //左右方向
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
		  //一段時間就下降一格
          if(Timer%21-Level==0)Ypos++;
          //如果下降後不合法就回去
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
	//功能: 檢查障礙物
	public boolean MoveCheck (int x , int y , int Set , int Mod)
	{
		for(int k = 0 ; k<4 ; k++)
		{
			int Xcube = x+Xmod[Set][Mod][k];
			int Ycube = y+Ymod[Set][Mod][k];
			//邊界檢查
			if(Xcube>=10)return false;
			if(Xcube<0)return false;
			if(Ycube>=20)return false;
			if(Ycube<0)return false;
			//碰撞檢查(在Falling功能中左右方向鍵按下後傳入預先在map上移動過的位置給此函數檢查)
			if(occupy[Xcube][Ycube]==1)return false;
			
		}
		
		return true;
	}
	//功能: 遊戲結束檢查
	public boolean GameOverCheck()
	{
		for(int x=0 ; x<10 ; x++)
		{   
	        if(occupy[x][0]==1)return true;
	        
		}
		return false;
	}
	//功能: 檢查消行
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
			//如果橫排10格都填滿就在消行陣列中設定該行為1
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
	//功能: 消行(搭配檢查消行)
	public void Delete(int[] DeleteRows)
	{
	for(int t = 0; t<=5;t++)
	{
		//從上往下檢查
		for(int Row = 0; Row<20 ;Row++)
		{
			//如果遇到填滿的橫行 就 消去
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
	//功能: 向下補滿(搭配檢查消行)
	public void Drop(int[] DropRows)
	{
	   //由上往下檢查
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
	
	/*---------記分板----------*/
	//功能: 顯示GAMEOVER
     public void GameOverShow()
	 {
	    JLabel GameOverLabel = new JLabel("GAME OVER");
	    GameOverLabel.setFont(new Font("Serif", Font.BOLD, 20));
	    GameOverLabel.setForeground(Color.white);
	    GameOverLabel.setBounds(300,400,200,30);
	    add(GameOverLabel);
	    repaint();
	 }
    //功能: 積分
 	public void AddScore(int Combo)
 	{
 		for(int i = 0;i<Combo;i++)
 		{
 			Score = Score + 100*Combo;
 		}
 		
 		ScoreLabel.setText("Score : " + Score);
 	}
    //功能: 積行
 	public void AddLine()
 	{
 		Line = Line + 1;
 		LineLabel.setText("Line : " + Line);
 	}
    //功能: 提升難度
 	public void CheckLevelUp()
 	{   
 		Level = (int)(Math.floor(Score/1000))+1;
 		LevelLabel.setText("Level : " + (int)Level);
 		
 		
 	}
 	//功能: 顯示下一個
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
