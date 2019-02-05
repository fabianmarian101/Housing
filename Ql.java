package com.start;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JSlider;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.internal.dynalink.beans.StaticClass;
public final class Ql extends Application {

    long lastNanoTime =  System.nanoTime() ;
    static int policytimer=0;
    static long speed=10000000;
	Board b;

	Cell setInitialPos()
	{
		//b.c[16].agent=1;
		return b.c[1][13]; 
	}
	
	
	
	
	Board returnBoard()
	{
	
		b=new Board();
		System.out.println("Board Created");
		return b;
	}
	
	int returnTotalStates()
	{
		return (225);
	}
	
	Board giveBoard()
	{
		
		return b;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
	    //Button move = new Button("move");
		System.out.println("Started");
		primaryStage.setTitle("My First Application");
		primaryStage.show();
		//StackPane root=new StackPane();
        HBox root = new HBox();
		root.setAlignment(Pos. TOP_LEFT);
		 b=returnBoard();
		
		
		Agent a=new Agent();
		a.initialize_game(b);
		//root.getChildren().add(a.b);
		//root.getChildren().add(slider);
		final Slider betSlider = new Slider(10000000,900000000,100);
        betSlider.valueProperty().addListener(new ChangeListener() {

            
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
               speed=(long)betSlider.getValue();

            }
        });
        root.getChildren().addAll(a.b,betSlider);
		//root.setAlignment(betSlider,Pos. TOP_RIGHT);
		//root.getChildren().add(betSlider);

		Scene scene=new Scene(root, 700, 700);
		
		primaryStage.setScene(scene);
		System.out.println("Finish");
		
		int g=0;
		//Thread th=new Thread();
		//th.start();
		new AnimationTimer() {
			
			@Override
			public void handle(long num) {
				int flag=0;
				int counter=0;
				int state=0;
				int f=0;
				long temp=lastNanoTime+speed;
				if(num>temp)
				{
					lastNanoTime=num;
					policytimer++;
					a.printQ();
					int policy=a.choosePolicy();
					System.out.println(policytimer);
					if(policytimer>8000)
						policy=1;
					System.out.println("Choosed policy  "+policy);
						int action=a.chooseAction(policy, a.c);
						System.out.println("Action "+action);
						System.out.println("Agent "+a.c.y+" "+a.c.x);
						Cell cc=a.b.applyAction(action, a.c, a.b);
						
						a.changeState(a.c, cc);
						
						if(f==0&&policytimer>8000)
						{
							state=a.current_state;
							f=1;
						}
						if(state==a.current_state)
							counter++;
						else
						state=a.current_state;
						if(counter==10)
						{
							flag=1;
							counter=0;
							state=0;
							policytimer=0;
							f=0;
						}
						//boolean b=a.checkIfStateisChanged();
						//if (b)
						//{
							boolean t=a.b.checkGoalStateReached(a.c);
							if(t)
							{
								a.updateQ();
								Random r=new Random();
								//if(flag==0)
								//a.setToInitialPos(a.b.c[r.nextInt(14)][r.nextInt(14)]);
								//else
									a.setToInitialPos(a.b.c[1][13]);
								a.b.c[14][6].setFill(Color.GREEN);
							}
							else
								a.updateQ();
						//}
					
					//th.sleep(200);
					if(g>100000)
						this.stop();
					
				}
				
			}
		}.start();
		
	}
	
	void startgame()
	{
		System.out.println("launch");
		launch("");
		System.out.println("hello");
		//return b;
	}
	public static void main(String args[])
	{
		launch(args);
	}

}

 class Cell extends Rectangle
{
	 int agent;
	 int x,y;
	 int state;
	 double reward;
	public Cell(int y,int x,int state,double reward)
	{
		super(30,30);
		this.x=x;
		setX(x);
		setY(y);
		this.y=y;
		this.state=state;
		this.reward=reward;
		agent=0;
		//setFill(Color.WHITESMOKE);
		//setStroke(Color.BLACK);
		
	}
}
 
 class Board extends Parent
 {
	 ArrayList maze=new ArrayList();
	  
	 int x=15;
	 int y=15;
	 Cell c[][]=new Cell[x][y];
	public Board()
	{
		int state=0;
		VBox rows=new VBox();
		
		for(int i=0;i<y;i++)
		{
			HBox row=new HBox();
			for(int j=0;j<x;j++)
			{
				//String stat=(i+1)+""+(j+1);
				//int temp=Integer.parseInt(stat);
				//System.out.println(i+"   "+j);
				if (state==(x*y)-9)
				{
					c[i][j]=new Cell(i, j, state,10.0);
					c[i][j].setFill(Color.GREENYELLOW);
					c[i][j].setStroke(Color.BLACK);
					row.getChildren().add(c[i][j]);
				

				}
				else
				{
				 c[i][j]=new Cell(i,j,state,-0.5);
				 c[i][j].setFill(Color.WHITESMOKE);
				 c[i][j].setStroke(Color.BLACK);
				c[i][j].setOnMouseClicked(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						
						//System.out.println(arg0.getTarget());
						Rectangle r=(Rectangle) arg0.getTarget();
						System.out.println(r);
						int ip=(int)r.getX();
						
						int jp=(int)r.getY();
						
						//System.out.println(r.getLayoutX());
						c[jp][ip].setFill(Color.BLACK);
						c[jp][ip].reward=-1;
						maze.add(c[jp][ip]);
						//System.exit(0);
						
					
					}
				});
				row.getChildren().add(c[i][j]);
	
				}
				state++;
				
				
			}
			rows.getChildren().add(row);
		}
		getChildren().add(rows);
    }
	
	boolean checkMaze(int i,int j)
	{
		Iterator it=maze.iterator();
		while(it.hasNext())
		{
			Cell c=(Cell)it.next();
			if(c.y==i && c.x==j)
				return true;
			
		}
		return false;
	}
	
	boolean checkBoundary(int i,int j)
	{
		System.out.println("boundary "+i+" "+j);
		
	 	if (i<0||i>14||j>14||j<0)
	 		return false;
	 	else
	 		return true;
	}
	
	
	boolean checkGoalStateReached(Cell c)
	{
		if(c.state==216)
		{
			return true;
		}
		return false;
	}
	
	Cell applyAction(int action,Cell c,Board b)
	{
		int xx=c.x;
		int yy=c.y;
		if(action==3)//move UP
		{
			if (!checkBoundary(c.y,c.x))
			return c;
			else
			{
				if(c.y==14)
					return c;
				else
				{
				int t=c.y+1;
				if(checkMaze(t,xx))
					return c;
				else
				{
				System.out.println(y);
				System.out.println(x);
				return b.c[t][xx];
				}
				}
			}
			
		}
		if (action==2)
		{
			if (!checkBoundary(c.y,c.x))
				return c;
				else
				{
					if(c.y==0)
						return c;
					else
					{
					int t=c.y-1;
					if(checkMaze(t,xx))
						return c;
					else
					{
					System.out.println(y);
					System.out.println(x);
					return b.c[t][xx];
					}
					}
				}
				
		}
		
		if (action==1)
		{
			if (!checkBoundary(c.y,c.x))
				return c;
				else
				{
					if(c.x==0)
						return c;
					else
					{
					int t=c.x-1;
					if(checkMaze(yy, t))
						return c;
					else
					{
					System.out.println(y);
					System.out.println(x);
					return b.c[yy][t];
					}
					}
				}
		}
		
		if(action==0)
		{
			if (!checkBoundary(c.y,c.x))
				return c;
				else
				{
					if(c.x==14)
						return c;
					else
					{
					int t=c.x+1;
					if(checkMaze(yy,t))
						return c;
					else
					{
					System.out.println(y);
					System.out.println(x);
					return b.c[yy][t];
					}
					}
				}
		}
		
		return null;
		}
		
		
	}
 
