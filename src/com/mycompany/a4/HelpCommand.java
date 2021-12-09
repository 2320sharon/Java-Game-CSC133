package com.mycompany.a4;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command {
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		String help="\'a': "+"Accelerate"
	+"\n \'b\': "+"Brake"
	+"\n \'l\': "+"Turn Left"
	+"\n \'r\': "+"Turn Right"
	+"\n \'f\': "+"Simulate collision with food station"
	+"\n \'g\': "+"Simulate collision with spider"
	+"\n \'t\': "+"Tick the game's clock";
		
		Dialog.show("About", help, new Command("ok"));
	}

}

