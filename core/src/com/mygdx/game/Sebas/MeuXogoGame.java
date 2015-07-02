package com.mygdx.game.Sebas;

/*
 * #################################################################################
 #
 #
 #    This program is free software: you can redistribute it and/or modify
 #    it under the terms of the GNU General Public License as published by
 #    the Free Software Foundation, either version 3 of the License, or
 #    (at your option) any later version.
 #
 #    This program is distributed in the hope that it will be useful,
 #    but WITHOUT ANY WARRANTY; without even the implied warranty of
 #    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 #    GNU General Public License for more details.
 #
 #    You should have received a copy of the GNU General Public License
 #    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 #
 #
 #    
 #################################################################################
 * 
 * */



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sebasxogo2d.pantallas.Presentacion;

/**
 * This class is the main class of the game, it inherits from the class
 * Game of the libgdx API. You need to load the assets on this class (create 
 * method) and dispose it in the dispose() method.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 */

public class MeuXogoGame extends Game {
	
	
  	// Graphics of user interface
	public static AssetManager assetManager;
	public static Skin skin;

	// Sound state controllers
	private boolean comprobarSon;
	private boolean soundState;//Estado true -> enabled false ->disabled

	// Preferences of the game
	private Preferences prefs;
	public final String arquivoPreferencias = "Preferencias.xml";
	    
	// Cliks sounds
	private Sound sonSelect, sonBack;
    
    /**
     * Method which is called when the user turn the application on. It's override 
     * from the same method of the father class. 
     * 
     * @author Sebastián Cabanas
     */
	@Override
	public void create () 
	{
		// Load the assets of the game
		AssetsXogo.cargarTexturas();
		
		/*
		 * Load User Interface (custom buttons, etc...) using the AssetManager class
		 * and an Atlas of textures.
		 */
		assetManager = new AssetManager();
        assetManager.load("uiskin.atlas",TextureAtlas.class);
        assetManager.finishLoading();

        // Load the styles and the textures in the atlas file.
        TextureAtlas atlas = assetManager.get("uiskin.atlas", TextureAtlas.class);
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas); 
        
        /*
         * 'comprobarSon' works only when the user turn on the application and it's only to
         * show a dialog with a question to the user. It makes that the application ask to 
         * the user if he wants to have the sound enabled or not.
         */ 
        comprobarSon = true; 

        /* 
        * When the user turn on the application disabled the sound before the user choose
        * if he wants the sound enabled or not. Default disabled
        */
        soundState = false; 
        
        // Load the preferences file
        prefs = Gdx.app.getPreferences(arquivoPreferencias);
	        
	    // Set the value of the sounds loaded in AssetsXogo class to properties of this class.
		sonSelect = AssetsXogo.sonSelect;
	    sonBack = AssetsXogo.sonBack;
	    
		// Set the screen to call when the application is turned on.
		setScreen(new Presentacion(this));	
		
	}
	

	/**
	 * Get the value of the property 'soundState'. It may be true (Enabled) or 
	 * false (Disabled).
	 * 
	 * @return State of the sound in the game, it may be true(enabled) or false (disabled)
	 * @author Sebastián Cabanas
	 */

	public boolean getSoundState ()
	{
		return soundState;
	}
		
	/**
	 * Set the value of the property 'soundState'. It can be true (Enabled) or 
	 * false (Disabled).
	 * 
	 * @param estado The new state to set into soundState. 
	 * @author Sebastián Cabanas
	 */

	public void setSoundState (boolean estado)
	{
		soundState = estado;
	}
	

	/**
	 * Get the value of the property 'comprobarSound'. This property is used to 
	 * indicate when the application needs to show a dialog asking to user if he
	 * want to have the sound Enabled or Disabled.
	 * 
	 * @return True if the application needs to shows the dialog or false otherwhise.
	 * @author Sebastián Cabanas
	 */

	public boolean getComprobarSound ()
	{
		return comprobarSon;
	}
	
	/**
	 * Set the value of the property 'comprobarSound'. This property is used to 
	 * indicate when the application needs to show a dialog asking to user if he
	 * want to have the sound Enabled or Disabled.
	 * 
	 * @param estado The new state to set into comprobarSound. 
	 * @author Sebastián Cabanas
	 */

	public void setComprobarSound (boolean estado)
	{
		comprobarSon = estado;
	}
	

	/**
	 * Get an object of the class Preferences which allow the developer to have access
	 * to the preferences file.
	 * 
	 * @return Object of the class Preferences 
	 * @author Sebastián Cabanas
	 */

	public Preferences getPrefs ()
	{
		return prefs;
	}
	

	/**
	 * Get an object of the class Sound which contains the loaded sound to use when the
	 * user click in a button 
	 * 
	 * @return Object of the class Sound 
	 * @author Sebastián Cabanas
	 */
	public Sound sonSelect () 
	{			
		return sonSelect;		
	}

	/**
	 * Get an object of the class Sound which contains the loaded sound to use when the
	 * user click in the back button 
	 * 
	 * @return Object of the class Sound 
	 * @author Sebastián Cabanas
	 */
	public Sound sonBack () 
	{		
		return sonBack;
	}

	/**
     * Method which is called when the user turn the application off. It's override 
     * from the same method of the father class. Dispose all the assets here.
     * 
     * @author Sebastián Cabanas
     */
	@Override
	public void dispose () 
	{	
		// Dispose the assets of the game
		AssetsXogo.liberarTexturas();
		
		// Dispose the user interface
		assetManager.dispose();
		skin.dispose();
		
		super.dispose();
	}
}
