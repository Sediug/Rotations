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
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;


/**
 * The screen of the credits of the application. 
 * Implements the interface Screen to use the render, resize and the lifecycle methods 
 * and the interface InputProcessor to have listeners of events like KeyUp, touchDragged
 * and others.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class Creditos implements Screen, InputProcessor {
	
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
     * Texture for background
     */
	private Texture fondo;

	/**
     * A string which have all the text of the credits
     */
	private StringBuilder creditos;

	/**
     * The font to write in the screen
     */
	private BitmapFont bmf;
	
	
	/**
     * Constructor which receive an object of the main class, initialize and create objects
     * for the properties of this class. 
     *
     * @param xogo A instance of the main class
     * @author Sebastián Cabanas
     */
	public Creditos (MeuXogoGame xogo)
	{
		this.meuxogogame = xogo;

		camara2d = new OrthographicCamera();
	    	batch = new SpriteBatch();
	    	fondo = new Texture(Gdx.files.internal("PantallaPrincipal2.png"));
	 
	    	bmf = new BitmapFont();
	    	creditos = new StringBuilder();
	    	encherCreditos();
       
	}


	/**
     * Write the text of the credits screen into the property creditos.
     *
     * @author Sebastián Cabanas
     */
	public void encherCreditos () 
	{
		creditos.append("Created and edited by Sebastian Cabanas \n\n");
		creditos.append("              Sound\n");
		creditos.append("Some of the sounds in this project were created \n");
		creditos.append("by David McKee (ViRiX) soundcloud.com/virix\n\n");
		creditos.append("             Graphics\n");
		creditos.append("NathanLovatoArt\n");
		creditos.append("Nicolae Berbece\n");
		creditos.append("Hawkadium  --- http://hawkadium.blogspot.ca/ \n");
		creditos.append("GameArtForge\n\n");
		creditos.append("Expecial thanks to OpenGameArt.org for give\n");
		creditos.append("me numerous graphics and sounds which\n");
		creditos.append("I could use to develop the game\n");
	}
	
	/**
	 * Method who render the textures of this screen.
	 *
	 * @param delta 
	 */
	@Override
	public void render (float delta) 
	{
		// Default drawing. Empty screem
		Gdx.gl.glClearColor(0, 0, 0, 1);
        	Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

        	// Set the color and scale to the the Font and draw the background and text.
		batch.begin();
		 	bmf.setColor(Color.WHITE);
	        	bmf.setScale(1);
			batch.draw(fondo,0,0,camara2d.viewportWidth,camara2d.viewportHeight);
			bmf.drawMultiLine(batch, creditos, 0, camara2d.viewportHeight-100);
		batch.end();
	}

	/**
     * Update the size on each update.
     *
     * @param width
     * @param heigt
     */
	@Override
	public void resize(int width, int height) 
	{
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
        	camara2d.update();
       
        	batch.setProjectionMatrix(camara2d.combined);
        	batch.disableBlending();
	}
	
	// Lifecycle Methods
	@Override
	public void show () 
	{
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide () 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void pause () {
		// Set the listener to null, on pause
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void resume () {
		// Set a listener to input. Keys, mouse, etc.
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose () {
		// Set the listener to null
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
	}

	@Override
	public boolean keyDown (int keycode) 
	{	
		// When the user use the key BACK on Android set the Options Screen.

		if (keycode == Keys.BACK) //Android pulsan a tecla de volver.
		{
	        	meuxogogame.setComprobarSound(false);
	        	meuxogogame.setScreen(new PantallaOptions(meuxogogame));
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode) 
	{
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) 
	{
		// When the user touch the screen bach to the Options Screen
		meuxogogame.setScreen(new PantallaOptions(meuxogogame));
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
