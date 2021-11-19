package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command {
	private GameWorld gameworld;
	public SoundCommand(GameWorld target) {
		super("Sound");
		gameworld=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(gameworld.getSound())
			gameworld.setSound(false);
		else
			gameworld.setSound(true);
		gameworld.changeBGSound();
		System.out.print("\n Sound modified to: "+ gameworld.getSound());
	}

}

