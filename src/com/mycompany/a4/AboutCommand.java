package com.mycompany.a4;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	public AboutCommand() {
		super("About");
	}
	
	@Override
	//Display Dialog box (Sharon Fitzpatrick,  CSC 133, Version 2 of On Target)
	public void actionPerformed(ActionEvent ev) {
		String about="Sharon Fitzpatrick \n CSC 133 \n Version 2 of On Target ";
		Dialog.show("About", about,"Ok", null);
	}

}

