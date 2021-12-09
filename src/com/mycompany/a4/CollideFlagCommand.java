//package com.mycompany.a3;
//
//import com.codename1.ui.Command;
//import com.codename1.ui.Dialog;
//import com.codename1.ui.TextField;
//import com.codename1.ui.events.ActionEvent;
//
//public class CollideFlagCommand extends Command {
//	private GameWorld gameworld;
//	
//	
//	public CollideFlagCommand(GameWorld target) {
//		super("Collide with Flag");
//		gameworld=target;
//	}
//	
//	@Override
//	public void actionPerformed(ActionEvent ev) {
////		//Create a text field for the user to enter 1 through 9
////		TextField input = new TextField();
////		//Display a Dialog box to get answer
////		Dialog.show("Enter Flag Number between 1-9",input, new Command("Ok"));
////		//Pass answer to collideFoodStation()
//		try
//		{
////			int flagNum = Integer.parseInt(input.getText());
//			gameworld.collideFlag(flagNum);
//			System.out.print("\n '' Collided with flag station: "+flagNum );
//		}
//		catch(NumberFormatException nFE)
//		{
//			Dialog.show("Invalid Input","Invalid Input. Must be a number between 1-9",new Command("Ok"));
//		}
//		
//	}
//
//}
