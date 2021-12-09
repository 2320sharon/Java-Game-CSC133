package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Image;
import com.codename1.ui.plaf.Border;

public class CustomButton extends Button {

	public CustomButton() {
		getDisabledStyle().setBgTransparency(255);
		getDisabledStyle().setFgColor(ColorUtil.BLUE);
		getDisabledStyle().setBgColor(ColorUtil.WHITE);
		getDisabledStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));//give button black border
		getDisabledStyle().setPadding(5,5,5,5);
		getDisabledStyle().setMargin(RIGHT, 4);
		
	    getUnselectedStyle().setBgTransparency(255);//make the button background opaque
		getUnselectedStyle().setBgColor(ColorUtil.BLUE);//make the button background blue
		getUnselectedStyle().setFgColor(ColorUtil.WHITE);//make the button foreground white
		getUnselectedStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));//give button black border
		getAllStyles().setPadding(5,5,5,5);
		getAllStyles().setMargin(RIGHT, 4);
		
	}

	public CustomButton(String text) {
		super(text);
		
		getDisabledStyle().setBgTransparency(255);
		getDisabledStyle().setFgColor(ColorUtil.BLUE);
		getDisabledStyle().setBgColor(ColorUtil.WHITE);
		getDisabledStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));//give button black border
		getDisabledStyle().setPadding(5,5,5,5);
		getDisabledStyle().setMargin(RIGHT, 4);
		
	    getUnselectedStyle().setBgTransparency(255);//make the button background opaque
		getUnselectedStyle().setBgColor(ColorUtil.BLUE);//make the button background blue
		getUnselectedStyle().setFgColor(ColorUtil.WHITE);//make the button foreground white
		getUnselectedStyle().setBorder(Border.createLineBorder(2,ColorUtil.BLACK));//give button black border
		getAllStyles().setPadding(5,5,5,5);
		getAllStyles().setMargin(RIGHT, 4);
		
	}

}
