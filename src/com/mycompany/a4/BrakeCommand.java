package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command {
	private GameWorld gameworld;
	public BrakeCommand(GameWorld target) {
		super("Brake");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameworld.brake();
		System.out.print("\n Brake occurred");
	}

}

