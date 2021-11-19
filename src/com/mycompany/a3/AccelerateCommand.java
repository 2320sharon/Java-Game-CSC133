package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
	private GameWorld gameworld;
	
	
	public AccelerateCommand(GameWorld target) {
		super("Accelerate");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gameworld.accelerate();
		System.out.print("\n 'a' entered and accelerate occurred");
	}

}
