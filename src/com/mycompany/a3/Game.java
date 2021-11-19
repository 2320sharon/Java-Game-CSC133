package com.mycompany.a3;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.mycompany.a3.GameWorld;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;
import com.codename1.ui.Form;


public class Game extends Form implements Runnable{
	private GameWorld gw;
	private MapView mv; 
	private ScoreView sv;
	private int elapsedTime;
	private boolean isPaused;
	private UITimer timer;

	
	//Buttons
	CustomButton pauseButton;
	CustomButton positionButton;
	CustomButton leftButton;
	CustomButton rightButton;
	CustomButton brakeButton;
	CustomButton accelerateButton;
	
	//toolbar
	Toolbar titleBar;
	
	//Commands
	AccelerateCommand accelerateCmd;
	TurnLeftCommand leftCmd;
	TurnRightCommand rightCmd;
	BrakeCommand brakeCmd;
	PositionCommand positionCmd;
	PauseCommand pauseCmd;

	private void setPaused(boolean pause) {
		isPaused=pause;
	}
	
	private boolean getPaused()
	{
		return isPaused;
	}
	
	public Game(){
		gw = new GameWorld(); 
		mv = new MapView(this,gw); 
		sv = new ScoreView(); 
		gw.addObserver(mv); 
		gw.addObserver(sv);
		
		//Set the inital state of the game to be in play
		this.setPaused(false);

		// code here to create Command objects for each command,
		accelerateCmd = new AccelerateCommand(gw);
		leftCmd = new TurnLeftCommand(gw);
		rightCmd = new TurnRightCommand(gw);
		brakeCmd = new BrakeCommand(gw);
		positionCmd= new PositionCommand(gw);
		pauseCmd= new PauseCommand(this);

		// add commands to key listeners
		addKeyListener('a',accelerateCmd);
		addKeyListener('l',leftCmd);
		addKeyListener('r',rightCmd);
		addKeyListener('b',brakeCmd);
		
		//Create BorderLayout for the main form
		this.setLayout(new BorderLayout());
		
		//Create titlebar where sidemenu, title , and Help menu will go
		titleBar= new Toolbar();
		setToolbar(titleBar);
		
		Label title = new Label("On Target Game");
		title.getAllStyles().setFgColor(ColorUtil.BLACK);
		titleBar.setTitleComponent(title);
		
		//Side Menu
		//Accelerate -> button
		//Sound -> Checkbox OFF by default
		//About -> Display Dialog box (Sharon Fitzpatrick,  CSC 133, Version 2 of On Target)
		//Exit -> Exit Prompts Dialog
		titleBar.addCommandToSideMenu(accelerateCmd);	//Add accerlate to the Side Menu
		
		add(BorderLayout.CENTER,mv);
		
		SoundCommand soundCmd = new SoundCommand(gw);
		CheckBox soundCheckBox = new CheckBox("Sound");
		soundCheckBox.setCommand(soundCmd);
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getAllStyles().setBgColor(ColorUtil.GRAY);
		titleBar.addComponentToSideMenu(soundCheckBox);		//Add sound checkbox to the Side Menu
		
		AboutCommand aboutCmd = new AboutCommand();
		titleBar.addCommandToSideMenu(aboutCmd);		//Add about to the Side Menu
		ExitCommand exitCmd = new ExitCommand(gw);
		titleBar.addCommandToSideMenu(exitCmd);			//Add exit to the Side Menu
		
		//Add the help command to the right side of the title bar
		HelpCommand helpCmd = new HelpCommand(); //Displays a dialogbox shows the keys to play the game
		titleBar.addCommandToRightBar(helpCmd);
		
		accelerateButton = new CustomButton("Accelerate");
		accelerateButton.setCommand(accelerateCmd);
		
		leftButton = new CustomButton("Left");
		leftButton.setCommand(leftCmd);
		
		rightButton = new CustomButton("Right");
		rightButton.setCommand(rightCmd);
		
		brakeButton = new CustomButton("Brake");
		brakeButton.setCommand(brakeCmd);
		
		//Create vertical container to hold buttons accelerate and left
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		leftContainer.add(accelerateButton);
		leftContainer.add(leftButton);
		
		leftContainer.getAllStyles().setPadding(Component.TOP,100);
		leftContainer.getUnselectedStyle().setBorder(Border.createLineBorder(1,ColorUtil.BLACK));//give container black border
		
		//Create vertical container to hold buttons brake and right
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		rightContainer.add(brakeButton);
		rightContainer.add(rightButton);
				
		rightContainer.getAllStyles().setPadding(Component.TOP,100);
		rightContainer.getUnselectedStyle().setBorder(Border.createLineBorder(1,ColorUtil.BLACK));//give container black border
		
		//Create SOUTH  container to hold buttons position and pause
		Container bottomContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		
		//Create the position and pause button
		
		positionButton = new CustomButton("Position");
		positionButton.setCommand(positionCmd);
		
		pauseButton = new CustomButton("Pause");
		pauseButton.setCommand(pauseCmd);

		bottomContainer.add(positionButton);
		//When the game initially loads the position button is not enabled
		positionButton.setEnabled(false);
		bottomContainer.add(pauseButton);
						
		bottomContainer.getAllStyles().setPadding(Component.LEFT,600);
		bottomContainer.getAllStyles().setPadding(Component.RIGHT,0);
		bottomContainer.getUnselectedStyle().setBorder(Border.createLineBorder(1,ColorUtil.BLACK));//give container black border
		
		//Add leftContainer to the WEST side of the main form
		add(BorderLayout.WEST,leftContainer);
		//Add rightContainer to the EAST side of the main form
		add(BorderLayout.EAST,rightContainer);
		//Add bottomContainer to the SOUTH side of the main form
		add(BorderLayout.SOUTH,bottomContainer);
		//Add scoreview and mapview to the NORTH and CENTER sides of the form
		add(BorderLayout.NORTH,sv);
		
		show();	
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		gw.init(); // initialize world

		//You can only call create sounds after the container is shown to screen
		gw.createSounds();
		revalidate();			//fix the GUI
		if(gw.getSound())
			gw.changeBGSound(true);		//play or pause the BGSound
		else
			gw.changeBGSound(false);
		
		//Set the elapsed time to 20msecs
		elapsedTime=20;
		//Create an instance of the UITimer and attach the game to it
		timer= new UITimer(this);
		
		//Start the timer, tell it to tick repeatedly every 20msec, and attach to game instance
		//@TODO uncomment this
		timer.schedule(elapsedTime, true, this);
	}
	  

	//Pause the game
	public void pause()
	{
		//Change the value of paused
		this.setPaused(!getPaused());
		
		if(this.getPaused() == true)
		{
			//Turn off all sound
//			gw.setSound(false);
			gw.changeBGSound(false);
			//Stop the timer
			timer.cancel();
			//Change the pauseButton's label to play
			pauseButton.setText("Play");
			
			//Disable all buttons related to play
			brakeButton.setEnabled(false);
			rightButton.setEnabled(false);
			leftButton.setEnabled(false);
			accelerateButton.setEnabled(false);
			titleBar.setEnabled(false);
			
			//enable the position button
			positionButton.setEnabled(true);
			
			removeKeyListener('a', accelerateCmd);
			removeKeyListener('b', brakeCmd);
			removeKeyListener('r', rightCmd);
			removeKeyListener('l', leftCmd);
			
			//mapview is now selectable
			mv.setSelectAllowed(true);		
		}
		else	//game is in playmode
		{
			//Allow the background to play if sound was previously set to true
			if (gw.getSound())
				gw.changeBGSound(true);
			
			//Change the pauseButton's label to play
			pauseButton.setText("Pause");
			
			//resume the timer
			timer.schedule(elapsedTime, true, this);
			
			//Enable all the buttons and keyListeners
			brakeButton.setEnabled(true);
			rightButton.setEnabled(true);
			leftButton.setEnabled(true);
			accelerateButton.setEnabled(true);
			titleBar.setEnabled(true);
			addKeyListener('a',accelerateCmd);
			addKeyListener('l',leftCmd);
			addKeyListener('r',rightCmd);
			addKeyListener('b',brakeCmd);
			
			//disable the position button
			positionButton.setEnabled(false);
			//mapview is not longer selectable
			mv.setPlayMode(true);				//resets all the objects to unselected
			mv.setSelectAllowed(false);		
		}

		
	}

	@Override
	//Called each time the UITimer ticks simulates 20msecs passing
	public void run() {
		gw.tick(elapsedTime);
		
	}
}


