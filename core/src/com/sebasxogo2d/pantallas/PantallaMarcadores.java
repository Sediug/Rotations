package com.sebasxogo2d.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Sebas.AssetsXogo;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;

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

/**
 * The screen with the saved scores and the name of who obtain that score.
 * Implements the interface Screen to use the render, resize and the lifecycle methods 
 * and the interface InputProcessor to have listeners of events like KeyUp, touchDragged
 * and others.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class PantallaMarcadores implements Screen, InputProcessor 
{
	/*
         * Var to store a instance of the main class
         */
	private MeuXogoGame meuxogogame;

     	/*
     	 * An Orthographic Camera in 2 dimensions 
     	 */
	private OrthographicCamera camara2d;

	/*
     	 * SpriteBach to draw the textures in the screem
     	 */
	private SpriteBatch batch;

	/*
     	 * Texture for background
    	 */
	private static Texture fondo;

	/*
     	 * Rectangles which represents the finger when it touch the screen and the button back, 
     	 * this restangles are used to check the intersection between the finger and the button
     	 */
	private Rectangle dedo, boton;
	
	/*
	 * ANCHOBUTTON and ALTOBUTTON are the size of the button back and X/Y are the position 
	 */
	private final float ANCHOBUTTON = 190f, ALTOBUTTON = 50f;
	private final float X = 50f, Y = 110f;
	
	/*
	 * Preferences of the user to load the scores
	 */
	private Preferences prefs;

	/*
	 * The variables 'puntuacionX' are the scores saved
	 */
	private int puntuacion1, puntuacion2, puntuacion3, puntuacion4, puntuacion5;

	/*
	 * Names of the users who have the biggest scores
	 */
	private String name1, name2, name3, name4, name5;
	
	/*
	 * The bmf to set the size and style of the text drawed in the screen
	 */
	private BitmapFont bitMapFont;
	
	// Position in the screen of the scores printed, with the cordinates X and Y
	private final float xP1 = 92f, yP1 = 400f;
	private final float xP2 = 92f, yP2 = 355f;
	private final float xP3 = 92f, yP3 = 315f;
	private final float xP4 = 92f, yP4 = 270f;
	private final float xP5 = 92f, yP5 = 225f;
	
	/*
	 * Volume of the sound in the PC version and Web version
	 */
	private final float VOLUMENSON = 0.2f;

	/*
	 * Volume of the sound in the Android version
	 */
	private final float VOLUMENSON_ANDROID = 1f;
	
	/**
     	 * Constructor which receive an object of the main class, initialize and create objects
    	 * for the properties of this class. 
     	 *
     	 * @param xogo A instance of the main class
     	 * @author Sebastián Cabanas
    	  */

	public PantallaMarcadores (MeuXogoGame xogo)
	{
		this.meuxogogame = xogo;
		
		camara2d = new OrthographicCamera();
	        batch = new SpriteBatch();
	        fondo = new Texture(Gdx.files.internal("PantallaScore.png"));
	        dedo = new Rectangle();
	        boton = new Rectangle();
	        
	        // Preferences to load the Scores
	        prefs = meuxogogame.getPrefs();
	        cargarPuntActuales();
	        
	        // Write settings.
	        bitMapFont = new BitmapFont();
		bitMapFont.setColor(Color.BLUE);
    		bitMapFont.setScale(1.5f);
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
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), VOLUMENSON_ANDROID);
		}
	}
	

	/**
	 * Method which load the highest scores and the name of who got that score. Save this values
	 * in the properties of this class.
	 *
	 * @author Sebastián Cabanas
	 */

	private void cargarPuntActuales ()
	{
		puntuacion1 = prefs.getInteger(PantallaXogo.puntuacion1,0);
		puntuacion2 = prefs.getInteger(PantallaXogo.puntuacion2,0);
		puntuacion3 = prefs.getInteger(PantallaXogo.puntuacion3,0);
		puntuacion4 = prefs.getInteger(PantallaXogo.puntuacion4,0);
		puntuacion5 = prefs.getInteger(PantallaXogo.puntuacion5,0);
		
		name1 = prefs.getString("name1","");
		name2 = prefs.getString("name2","");
		name3 = prefs.getString("name3","");
		name4 = prefs.getString("name4","");
		name5 = prefs.getString("name5","");
	}
	

	/**
	 * Method who render the textures of this screen.
	 *
	 * @param delta 
	 */

	@Override
	public void render (float delta) 
	{
		batch.begin();
			// Draw the background and the button back.
			batch.draw(fondo,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
			batch.draw(AssetsXogo.back, X, Y, ANCHOBUTTON, ALTOBUTTON);
			
			// Call to the method which print the scores
			pintarPuntuacions();
		batch.end();

	}


	/**
	 * Method which draw the scores and the names in the screen.
	 *
	 * @author Sebastián Cabanas
	 */

	private void pintarPuntuacions () 
	{
		bitMapFont.draw(batch, ""+puntuacion1, xP1, yP1);
		bitMapFont.draw(batch, ""+puntuacion2, xP2, yP2);
		bitMapFont.draw(batch, ""+puntuacion3, xP3, yP3);
		bitMapFont.draw(batch, ""+puntuacion4, xP4, yP4);
		bitMapFont.draw(batch, ""+puntuacion5, xP5, yP5);
		
		bitMapFont.draw(batch, name1, xP1+100, yP1);
		bitMapFont.draw(batch, name2, xP2+100, yP2);
		bitMapFont.draw(batch, name3, xP3+100, yP3);
		bitMapFont.draw(batch, name4, xP4+100, yP4);
		bitMapFont.draw(batch, name5, xP5+100, yP5);
	}

	
	/**
    	 * Update the size on each update.
     	 *
     	 * @param width
     	 * @param heigt
     	 */

	@Override
	public void resize (int width, int height) 
	{
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
		camara2d.update();
		       
		batch.setProjectionMatrix(camara2d.combined);
		batch.enableBlending();
	}

	// Lifecycle Methods

	@Override
	public void show () 
	{
		// Set a listener to input. Keys, mouse, etc.
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide () 
	{
		// Set the listener to null
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause () 
	{
		// Set the listener to null
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void resume () 
	{
		// Set a listener to input. Keys, mouse, etc.
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose () 
	{
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
	}

	// Gesture Methods
	@Override
	public boolean keyDown (int keycode) 
	{
		// When the user use the key BACK on Android call the Main Screen.

		if (keycode == Keys.BACK) 
		{
			playSonBack();
	        meuxogogame.setComprobarSound(false);
	        meuxogogame.setScreen(new Presentacion(meuxogogame));
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped (char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) 
	{
		// Translate the coordinates of the screen to the camera2d coordinates
		Vector3 vecTemporal = new Vector3();
		vecTemporal.set(screenX,screenY,0);
        	camara2d.unproject(vecTemporal);

	        // Set the size and coordinates of finger and the button back.
	        dedo.set(vecTemporal.x,vecTemporal.y,2, 2);
	    	boton.set(X, Y, ANCHOBUTTON, ALTOBUTTON);
	        
	        /*
	         * If there is an intersection between the finger who touch the screen and the 
	         * button ( BACK ). When it happens then play the sound BACK and call the main
	         * screen.
	         */
	        if (Intersector.overlaps(dedo, boton)) 
	        {
	        	playSonBack();
	        	meuxogogame.setComprobarSound(false);
	        	meuxogogame.setScreen(new Presentacion(meuxogogame));
	        }
        
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled (int amount) 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
