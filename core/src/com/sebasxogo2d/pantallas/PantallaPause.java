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
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;

/**
 * The screen which it's shows when the game is on pause.
 * Implements the interface Screen to use the render, resize and the lifecycle methods.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class PantallaPause implements Screen, InputProcessor {
	
	/**
         * Var to store an instance of the main class
         */
	private MeuXogoGame meuxogogame;

	/**
         * Var to store an instance of the game which is currently playing
         */
	private PantallaXogo pantallaXogo;

	/**
         * An Orthographic Camera in 2 dimensions 
         */
	private OrthographicCamera camara2d;

	/**
         * SpriteBach to draw the textures in the screem
         */
	private SpriteBatch batch;

	/**
         * Texture of the background
         */
	private static Texture fondo;
	
	
	/**
         * Constructor which receive an object of the main class and the object who represents the currently
         * game plaring, initialize and create objects for the properties of this class. 
         *
         * @param xogo A instance of the main class
         * @author Sebastián Cabanas
         */
	public PantallaPause (MeuXogoGame xogo, PantallaXogo pantallaXogo)
	{
		this.meuxogogame = xogo;
		this.pantallaXogo = pantallaXogo;
		
		camara2d = new OrthographicCamera();
        batch = new SpriteBatch();
        fondo = new Texture(Gdx.files.internal("PantallaPause.png"));
	}
	
	@Override
	public void render (float delta) 
	{
		batch.begin();
			// Draw the background
			batch.draw(fondo,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
		
		batch.end();
	}

	@Override
	public void resize (int width, int height) 
	{
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
        camara2d.update();
       
        batch.setProjectionMatrix(camara2d.combined);
        batch.disableBlending();
	}

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
	public void pause () 
	{
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void resume () 
	{
		 Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose () 
	{
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
	}

	@Override
	public boolean keyDown (int keycode) 
	{
		//Android back key. Back to the main screen
		if (keycode == Keys.BACK) 
		{
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
		// When the user touch the screen back to the game.
		meuxogogame.setScreen(pantallaXogo);
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
