package com.mygdx.game.Sebas.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Sebas.MeuXogoGame;



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rotations 1.5.1";
		 
		 
		config.width = 480;
        config.height = 800;

        
		config.resizable=false;
        config.addIcon("favico.bmp", FileType.Internal);
        
		new LwjglApplication(new MeuXogoGame(), config);
	}
}
