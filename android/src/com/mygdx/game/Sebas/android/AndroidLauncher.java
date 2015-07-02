package com.mygdx.game.Sebas.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Sebas.MeuXogoGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		// Don't use Acelerometer or Compass, user Wakelock on Android devices.
	        config.useAccelerometer= false;
	        config.useCompass=false;
	        config.useWakelock=true;
        	
        	// Call the main class of the game when the user turn on the game on an Android device
		initialize(new MeuXogoGame(), config);
	}
}
