package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TurnLeftCommand extends Command {
	private GameWorld gameworld;
	
	
	public TurnLeftCommand(GameWorld target) {
		super("Turn Left");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameworld.turn('l');
		System.out.print("\n 'l' entered and turn left occured");
	}

}

