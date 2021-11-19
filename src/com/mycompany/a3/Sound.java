package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	public Sound(String filename) {
		while(m==null)
		{
			if (Display.getInstance().getCurrent() == null) {
				System.out.println("Error: Create sound objects after calling show()!");
				System.exit(0);
			}
			try {
				System.out.println("\n In the sound object");
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+filename);
//				System.out.println("\n InputStream: "+ is);
				m = MediaManager.createMedia(is,"audio/wav");
//				System.out.println("\n MediaManager: "+ m);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public void play() {
		//re-start the sound from the beginning
		m.setTime(0);
		m.play();
	}
}



