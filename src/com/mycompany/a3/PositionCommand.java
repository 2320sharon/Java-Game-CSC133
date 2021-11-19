package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command {
	private GameWorld gameworld;
	public PositionCommand(GameWorld target) {
		super("Position");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameworld.position();
		System.out.print("\n Position Command Acitiviated");
	}

}

