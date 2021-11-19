package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TurnRightCommand extends Command {
	private GameWorld gameworld;
	
	
	public TurnRightCommand(GameWorld target) {
		super("Turn Right");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameworld.turn('r');
		System.out.print("\n 'r' entered and turn right occured");
	}

}

