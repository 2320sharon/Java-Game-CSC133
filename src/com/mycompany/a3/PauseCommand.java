package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {
	private Game game;
	public PauseCommand(Game target) {
		super("Pause");
		this.game=target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		game.pause();
		System.out.print("\n Pause occurred");
	}

}

