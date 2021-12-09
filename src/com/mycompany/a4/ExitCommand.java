package com.mycompany.a4;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
	private GameWorld gameworld;
	public ExitCommand(GameWorld target) {
		super("Exit");
		gameworld= target;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		boolean response =Dialog.show("Quit?", "Are you sure you want to quit?","OK", "Cancel");
		if(response)
		{
			Display.getInstance().exitApplication();
		}
	}

}

