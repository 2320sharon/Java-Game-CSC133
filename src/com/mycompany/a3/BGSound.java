package com.mycompany.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

//This class is used to play the background music
public class BGSound implements Runnable{
	private Media m;
	public BGSound(String filename) {
		while(m==null)
		{		if (Display.getInstance().getCurrent() == null) {
			System.out.println("Error: Create sound objects after calling show()!");
			System.exit(0);
		}
			try {
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+filename);
				m=MediaManager.createMedia(is,"audio/wav",this);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public void play() {m.play();}//continue playing the sound from where it was left off
	
	public void pause() {m.pause();}//stop playing the sound
	
	@Override
	public void run() {
		//re-start the sound from the beginning
		m.setTime(0);
		m.play();
	}
}



