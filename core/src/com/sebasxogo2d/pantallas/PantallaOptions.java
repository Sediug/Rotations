package com.sebasxogo2d.pantallas;

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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Sebas.AssetsXogo;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;

/**
 * The screen where the user can change the settings of the game.
 * Implements the interface Screen to use the render, resize and the lifecycle methods.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class PantallaOptions implements Screen {
	
	/**
         * Var to store a instance of the main class
         */
	private MeuXogoGame meuxogogame;

	/**
         * An Orthographic Camera in 2 dimensions 
         */
	private OrthographicCamera camara2d;

	/**
         * SpriteBach to draw the textures in the screem
         */
	private SpriteBatch batch;

	/**
         * If the user changes something in the settings
         */
	private boolean changes;
	
	/**
         * Preferences to load the saved settings
         */ 
	private Preferences prefs;

	
	// User Interface properties.

	/**
	 * The stage handles the viewport and distributes input events.
	 */
  	private Stage stage;

  	/**
  	 * The skin to use for this user interface.
  	 *
	 * The Skin class stores resources for UI widgets to use. It is a convenient container 
	 * for texture regions, ninepatches, fonts, colors, etc. Skin also provides convenient 
	 * conversions, such as retrieving a texture region as a ninepatch, sprite, or drawable.
	 */
  	private Skin skin = MeuXogoGame.skin;

  	/**
  	 * Select boxes to select the language, dificult level, etc.
  	 */
  	private SelectBox<String> lista, sound, languaje;

  	/**
  	 * Diferent text to use in the UI
  	 */
  	private TextField left, right;

  	/**
  	 * The id's of the selected items in the select boxes
  	 */
  	private int idLista, idSound, idLanguaje;

  	/**
  	 * Settings saved as a preferences.
  	 */
  	private String nivel, prefLeft, prefRight, estado; // Preferencias
  	
  	// Ui sizes and positions

  	private final float xSBox = (float) (Gdx.graphics.getWidth()/1.7);
  	private final float yLista = (float) (Gdx.graphics.getHeight()/1.33);
  	private final float ySound = (float) (Gdx.graphics.getHeight()/1.59);
  	private final float yLang = (float) (Gdx.graphics.getHeight()/2);
  	private final float xLeft = (float) (Gdx.graphics.getWidth()/6);
  	private final float xRigth = (float) (Gdx.graphics.getWidth()/3.05);
  	private final float yText = (float) (Gdx.graphics.getHeight()/3.2);
  	private final float backX = (float) (Gdx.graphics.getWidth()/6);
  	private final float saveX = (float) (Gdx.graphics.getWidth()/2.4);
  	private final float restoreX = (float) (Gdx.graphics.getWidth()/1.5);
  	private final float buttonsY = Gdx.graphics.getHeight()/6; 
  	private final float anchoSB = Gdx.graphics.getHeight()/6, alto = Gdx.graphics.getHeight()/20;
	private final float ANCHO = Gdx.graphics.getHeight()/9, ALTO = Gdx.graphics.getHeight()/25;
	
	/**
	 * Volume of the sound in the PC version and Web version
	 */
	private final float VOLUMENSON = 0.2f;

	/**
	 * Volume of the sound in the ANDROID version
	 */
	private final float VOLUMENSONANDROID = 1f;


  	/**
         * Constructor which receive an object of the main class, initialize and create objects
         * for the properties of this class. 
         *
         * @param xogo A instance of the main class
         * @author Sebastián Cabanas
         */
	public PantallaOptions(MeuXogoGame xogo) {
		
		this.meuxogogame = xogo;

		camara2d = new OrthographicCamera();
        	batch = new SpriteBatch();
        	changes = false;
        
	        // Use the preferences loaded in the man class.
	        prefs = meuxogogame.getPrefs();
	        
	        //User interface
	        stage = new Stage ()
	        {
	            @Override
	            public boolean keyDown (int keyCode) // Back android.
	            {
		            if (keyCode == Keys.BACK) {
		            	playSonBack();
		            	meuxogogame.setComprobarSound(false);
		            	meuxogogame.setScreen(new Presentacion(meuxogogame)); // Back to main screen    
		            }
		            
		            return super.keyDown(keyCode);
	            }
	    	};

	    	// Load the Options panel
	        cargarPanelOptions(); 
	
	        // Load the preferences to set in the Options panel.
	        cargarPrefs();
	}
	
	/**
     	 * Method who play a sound when the user choose to go back. This method calls to the method
     	 * of the main class 'sonBack()' (which load this sound) and set the volume to the value 
     	 * assigned on the property VOLUMENSON. 
     	 *
     	 * The method 'sonBack()' return an object of the class Sound, this class have the method 
     	 * setVolume() where you can set the volume and indicatea to play the sound.
     	 *
    	 * @author Sebastián Cabanas
     	 */
	public void playSonBack () 
	{
		// It works only when the sound is enabled and the version of the App is PC or WEB
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), VOLUMENSON);
		}
			
		// Otherwise it works when the sound is enabled and the version of the app is ANDROID
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), VOLUMENSONANDROID);
		}

	}
	
	
	
	/**
     	 * Method who play a sound when the user click in a button (Play, Options...). This method calls to the method
     	 * of the main class 'sonSelect()' (which load this sound) and set the volume to the value 
     	 * assigned on the property VOLUMENSON. 
     	 *
     	 * The method 'sonSelect()' return an object of the class Sound, this class have the method 
     	 * setVolume() where you can set the volume and indicatea to play the sound.
     	 *
    	 * @author Sebastián Cabanas
     	 */

	public void playSonSelect() 
	{
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), VOLUMENSON);
		}

		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), VOLUMENSONANDROID);
		}
	}
	
	/**
	 * Method which create all the element of the options panel using the classes of
	 * the UI. 
	 *
	 * @author Sebastián Cabanas
	 */
	private void cargarPanelOptions() {

		// Select Box with dificulty
		lista = new SelectBox<String>(skin) ;
        	lista.setItems("Easy","Normal", "Kappa");
		lista.setBounds(xSBox,yLista, anchoSB, alto);
        	lista.setPosition(xSBox,yLista);
        
	        // Select box which allows the user to choose if want the sound enabled or not
	        sound = new SelectBox<String>(skin) ;
	        sound.setItems("Enabled", "Disabled");
		sound.setBounds(xSBox,ySound, anchoSB, alto);
	 	sound.setPosition(xSBox,ySound);
        
	        // Select box which allows the user to choose the language
	        languaje = new SelectBox<String>(skin) ;
	        languaje.setItems("English");
		languaje.setBounds(xSBox,yLang, anchoSB, alto);
        	languaje.setPosition(xSBox,yLang);
        
	        // Text field which allows the user to input a new Key for move the paddle to the left
	        // (PC version)
	        left = new TextField("", skin);
		left.setBounds(xLeft,yText, alto, alto);
        
	        // Text field which allows the user to input a new Key for move the paddle to the right
	        // (PC version)
	        right = new TextField("", skin);
	        right.setBounds(xRigth,yText, alto, alto);
        
	        // Button to save the changes
	        TextButton save = new TextButton("Save",skin);
	        save.setBounds(saveX, buttonsY,ANCHO, ALTO);
	        save.scaleBy(2);
	        
	        // Listener to the button 'Save'. 
	        save.addListener(
	        	new InputListener() 
	        	{
		            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		            {
		            	gardarDatos(); // Save the changes
		            	playSonSelect(); // Play the sound of when you click a button
		            	changes = false;// All changes saved, then there is any not changes to save.
		            	cargarPrefs(); // Load the preferences
		                return true;
		            }
	        	}
	        );
	        
	        // Button for back to the main screen
	        TextButton back = new TextButton("Back",skin);
	        back.setBounds(backX, buttonsY,ANCHO, ALTO);
	        back.scaleBy(2);
	
	        // Listener to the button 'Back'
	        back.addListener(
	        	new InputListener() 
	        	{
		            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		            {
		            	playSonBack();
		            	meuxogogame.setComprobarSound(false);
		            	meuxogogame.setScreen(new Presentacion(meuxogogame));
		                return true;
		            }
	        	}
	        );
	        
	        // Button for restore the settings to default 
	        TextButton restore = new TextButton("Restore",skin);
	        restore.setBounds(restoreX, buttonsY,ANCHO, ALTO);
	        restore.scaleBy(2);
	
	        // Listener to the button 'Restore Default'
	        restore.addListener(
	        	new InputListener() 
	        	{
		            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		            {
		            	// We will back to the default settings and then allow the user to save this settings
		            	changes = true; 
		            	setToDefault();
		                return true;
		            }
	        	}
	        );
        
	        // Button for show the credits screen
	        TextButton credits = new TextButton("Credits",skin);
	        credits.setBounds(saveX, buttonsY-Gdx.graphics.getHeight()/12,ANCHO, ALTO);
	        credits.scaleBy(2);
	
	        // Listener to the button 'Credits'
	        credits.addListener(
	        	new InputListener() 
	        	{
		            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		            {
		            	playSonSelect();
		            	meuxogogame.setScreen(new Creditos(meuxogogame));
		                return true;
		            }
	        	}
	        );
	        
	        // Add all actors to the stage. Stage handles the viewport and distributes input events
	        stage.addActor(credits);
	        stage.addActor(back);
	        stage.addActor(save);
	        stage.addActor(restore);
	        stage.addActor(sound);
	        stage.addActor(lista);
	        stage.addActor(languaje);
	        stage.addActor(left);
	        stage.addActor(right);
        
	}

	/**
	 * Method which check if there are some changes from the saved settings. 
	 *
	 * @author Sebastián Cabanas
	 */

	public void checkIn () 
	{
		idLista = lista.getSelectedIndex();
		idSound = sound.getSelectedIndex();
		idLanguaje = languaje.getSelectedIndex();
		
		if(!lista.getSelected().equalsIgnoreCase(nivel) || !sound.getSelected().equalsIgnoreCase(estado))
			changes = true; // There is something diferent from the saved settings. 
		
		// Only allows the user to input one single key
		if(!left.getText().equalsIgnoreCase(""))left.setText(left.getText().substring(0, 1));
		if(!right.getText().equalsIgnoreCase(""))right.setText(right.getText().substring(0, 1));
	}


	
	@Override
	public void render (float delta) 
	{

		// Draw a diferent textures and background if there is some changes to save or not.
		batch.begin();
			if(changes)
				batch.draw(AssetsXogo.textureOptions,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
			else 
				batch.draw(AssetsXogo.textureOptions2,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
		batch.end();
		
		// Check if there is some changes in comparison with the saved settings
		checkIn();
		
		
		// Draw the user interface
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	

	@Override
	public void resize (int width, int height) 
	{
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
	        camara2d.update();
	       
	        batch.setProjectionMatrix(camara2d.combined);
	        batch.enableBlending();
	        
	        stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show () 
	{
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide () 
	{
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause () 
	{
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void resume () 
	{
		 Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose () 
	{
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}

	/**
	 * Method which load the saved preferences of the application. 
	 *
	 * @author Sebastián Cabanas
	 */

	public void cargarPrefs ()
	{

		// Level, default Easy
		if(prefs.getString("nivel","pEasy").equalsIgnoreCase("pNormal")) 
			nivel = "Normal";
		else if (prefs.getString("nivel","pEasy").equalsIgnoreCase("pKappa")) 
			nivel = "Kappa";
		else 
			nivel = "Easy";
				
		lista.setSelected(nivel);
		
		// Sound, default false
		if(prefs.getBoolean("soundState", false))
			estado = "Enabled";
		else 
			estado = "Disabled";
		
		sound.setSelected(estado);
		
		//Controls to move the paddle, if there it's something diferent than nothing then change it
		prefLeft = prefs.getString("controisLeft", "");
		prefRight = prefs.getString("controisRight", "");
		
		if(!prefLeft.equalsIgnoreCase(""))left.setText(prefLeft);
		if(!prefRight.equalsIgnoreCase(""))right.setText(prefRight);
		
	}

	/**
	 * Method which save the settings in the preference file. 
	 *
	 * @author Sebastián Cabanas
	 */

	public void gardarDatos ()
	{

		switch (idLista) 
		{
				
			case 0:
				prefs.putString("nivel","pEasy");
				break;
				
			case 1:
				prefs.putString("nivel","pNormal");
				break;
			
			case 2:
				prefs.putString("nivel","pKappa");
				break;
				
    	}

    	switch (idSound) 
    	{
			case 0:
				meuxogogame.setSoundState(true);// Enabled
				prefs.putBoolean("soundState", true);
				break;
				
			case 1:
				meuxogogame.setSoundState(false);// Disabled
				prefs.putBoolean("soundState", false);
				break;
		}
    	
  
    	prefs.putString("controisLeft", left.getText());
    	prefs.putString("controisRight", right.getText());
    	
    	
    	prefs.flush();
	}

	/**
	 * Method which set all settings to default.
	 *
	 * @author Sebastián Cabanas
	 */

	public void setToDefault() {
		lista.setSelected("Easy");
	    	sound.setSelected("Disabled");
	    	left.setText("");
	    	right.setText("");
	    	
	    	prefs.putString("controisLeft", "left");
	    	prefs.putString("controisRight", "right");
	    	prefs.flush();
	}
}
