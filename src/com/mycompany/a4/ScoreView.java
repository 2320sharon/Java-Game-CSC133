package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

/*
 * Displays the current state of the game it prints the following:
 * - the sound level
 * - the number of lives
 * - the number of clock ticks
 * - the highest flag reached by the player
 * - Ant Food Level
 * - Ant Health Level
 */
public class ScoreView extends Container implements Observer {

	private Label timeLabel;
	private Label livesLabel;
	private Label lastFlagLabel;
	private Label foodLabel;
	private Label healthLabel;
	private Label soundLabel;
	
	public ScoreView() {
		//Create horizontal container to hold labels
		setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		timeLabel = new Label("Time: 0");
		timeLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		timeLabel.getAllStyles().setPadding(3,3,3,3);
		timeLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(timeLabel);
		
		livesLabel = new Label("Lives Left: 3");
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		livesLabel.getAllStyles().setPadding(3,3,3,3);
		livesLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(livesLabel);
		
		lastFlagLabel = new Label("Last Flag Reached: 1");
		lastFlagLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		lastFlagLabel.getAllStyles().setPadding(3,3,3,3);
		lastFlagLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(lastFlagLabel);
		
		foodLabel = new Label("Food Level: 25");
		foodLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		foodLabel.getAllStyles().setPadding(3,3,3,3);
		foodLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(foodLabel);
		
		healthLabel = new Label("Health Level: 10");
		healthLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		healthLabel.getAllStyles().setPadding(3,3,3,3);
		healthLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(healthLabel);
		
		soundLabel = new Label("Sound: OFF");
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLUE);//make the label foreground blue
		soundLabel.getAllStyles().setPadding(3,3,3,3);
		soundLabel.getAllStyles().setMargin(RIGHT, 2);
		addComponent(soundLabel);
		
		getAllStyles().setPadding(Component.LEFT, 200);
		getAllStyles().setPadding(Component.RIGHT,0);
			
	}

	@Override
	public void update(Observable observable, Object data) {
		//updates labels from the game/ant state data	
		if (observable instanceof GameWorld)
		{
			GameWorld gameworld= (GameWorld)observable;
			livesLabel.setText("Lives Left: "+gameworld.getLives());
			timeLabel.setText("Time: "+gameworld.getClock());
			
			//update all the state related to the ant, flag, foodlvl and healthlvl
			Ant antPlayer = Ant.getAnt();
			if(antPlayer != null)
			{
				lastFlagLabel.setText("Last Flag Reached: "+antPlayer.getLastFlagReached()); 
				foodLabel.setText("Food Level: "+antPlayer.getFoodLevel());
				healthLabel.setText("Health Level:  "+antPlayer.getHealthLevel()); 
			}
			
			String soundString= (gameworld.getSound()) ? "ON" : "OFF";
			 soundLabel.setText("Sound: "+soundString); 
		}
		//forces the scoreview to update
		revalidate();
	}
	

}