package com.start;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.*;
public class Agent  {

	int x,y;
	int actions[]={0,1,2,3};
	int previous_state;
	int current_state;
	double Q[][];
	int total_states=0;
	int total_actions=4;
	Board b;
	Ql q;
	Cell c;
	double reward;
	final double gama=0.75;
	final double alpha=0.75;
	int current_action;
	int ran[]={0,0,0,0,0,1,1,1,1,1};
	static int ptr=0;
	
	
	 void shuffleAction()
		{
		    int index, temp;
		    Random random = new Random();
		    for (int i = actions.length - 1; i > 0; i--)
		    {
		        index = random.nextInt(i + 1);
		        temp = actions[index];
		        actions[index] = actions[i];
		        actions[i] = temp;
		    }
		}
	  void shuffleArray()
	{
	    int index, temp;
	    Random random = new Random();
	    for (int i = ran.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = ran[index];
	        ran[index] = ran[i];
	        ran[i] = temp;
	    }
	}
	
	
	void printQ()
	{
		for(int i=0;i<total_states;i++)
		{
			System.out.print(i+"  ");
			for(int j=0;j<total_actions;j++)
			{
				System.out.print(Q[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	
	void setToInitialPos(Cell c)
	{
		x=c.x;
		y=c.y;
		previous_state=c.state;
		current_state=c.state;
		current_action=0;
		reward=0;
		this.c=c;
		
	}
 
	
void paint(Cell prev,Cell next)
{
	//b.c[prev.y][prev.x].setFill(Color.WHITESMOKE);
	b.c[next.y][next.x].setFill(Color.RED);
}
	
	void changeState(Cell prev,Cell next)
	{
		System.out.println("yes"+x+"  "+y);
		b.c[y][x].setFill(Color.WHITESMOKE);
		previous_state=current_state;
		current_state=next.state;
		x=next.x;
		y=next.y;
		reward=next.reward;
		c=next;
		System.out.println("Paint");
		b.c[y][x].setFill(Color.RED);
		
		
		
	}
	
	boolean checkIfStateisChanged()
	{
		if (previous_state!=current_state)
			return true;
		else
			return false;
	}
	
	
	int getRandomAction()
	{
		
		shuffleAction();
		current_action=actions[0];
		
		return(current_action);
	}
	
	int getGreedyAction(Cell c)
	{
		int action=0;
		double max=-100000.0;
		int s=c.state;
		int same=0;
		for (int i=0;i<4;i++)
		{
			if(max==Q[s][i])
				same++;
			System.out.println(Q[s][i]);
			if(Q[s][i]>=max)
			{
				max=Q[s][i];
				action=i;
				System.out.println("Greedy Action   "+action);
			}
		}
		System.out.println(same);
		if (same==3)
		{
			System.out.println("Rand"+same);
			//System.out.println();
			return (getRandomAction());
		}
		else
		{
		current_action=action;
		
		return action;
		}
	}
	
	
	
	int choosePolicy()
	{
		for(int i=0;i<10;i++)
		{
		System.out.print(ran[i]);
		}
		System.out.println("ptr  "+ptr);
		if(Agent.ptr==9)
		{
			int temp=Agent.ptr;
			Agent.ptr=0;
			int ret=ran[temp];
			//Collections.shuffle(Arrays.asList(ran));
		shuffleArray();
			return(ret);
		}
		else
		{
			int t=ran[Agent.ptr];
			Agent.ptr++;
			return(t);
		}
		
	}
	
	int chooseAction(int policy,Cell c)
	{
		if (policy==0)
		{
			System.out.println("Random Policy");
			return(getRandomAction());
		}
		else
		{
			System.out.println("Greedy Policy");
			return(getGreedyAction(c));
		}
	}
	
	double retMaxActionReward(int state)
	{
		double  max=0;
		for(int i=0;i<4;i++)
		{
			if(Q[state][i]>=max)
				max=Q[state][i];
		}
		return max;
	}
	int updateQ()
	{
		double newQ=retMaxActionReward(current_state);
		double diffinQ=newQ-Q[previous_state][current_action];
		double temp=reward+(gama*diffinQ);
		Q[previous_state][current_action]=Q[previous_state][current_action]+(alpha*temp);
		
		
		return 0;
	}
	
	void initializeQ()
	{
		total_states=q.returnTotalStates();
		Q=new double[total_states][total_actions];
		//System.out.println("Total states");
		
		for (int i=0;i<total_states;i++)
		{
			for(int j=0;j<total_actions;j++)
			{
				//System.out.println("State "+i);
				Q[i][j]=0;
			}
		}
	}
	
	Agent()
	{
		//total_states= q.returnTotalStates();
		
		
	}
	
	void initialize_game(Board bo)
	{
		//System.out.println("Init");
		 q=new Ql();
		b=bo;
		c=bo.c[1][13];
		current_state=c.state;
		previous_state=c.state;
		y=c.y;
		x=c.x;
		bo.c[1][13].setFill(Color.RED);
		//System.out.println(c.state);
		//System.out.println("do");
		//System.out.println(b);
		initializeQ();
	}
	
	/*public static void main(String[] args) {
		Agent a=new Agent();
		a.initialize_game();
		System.out.println("here");
		a.q.startgame();
	}*/

}
