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

/**
 * @author Sebastian Cabanas 
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

public class Creditos implements Screen, InputProcessor {
	
	private MeuXogoGame meuxogogame;
	private OrthographicCamera camara2d;
	private SpriteBatch batch;
	private Texture fondo;
	private StringBuilder creditos;
	private BitmapFont bmf;
	
	
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
		creditos.append("to me numerous graphics and sounds which\n");
		creditos.append("I could use to develop the game\n");
	}
	
	
	@Override
	public void render (float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
        	Gdx.gl.glClear(GL20.GL_COLOR_CLEAR_VALUE);

		batch.begin();
		 	bmf.setColor(Color.WHITE);
	        	bmf.setScale(1);
			batch.draw(fondo,0,0,camara2d.viewportWidth,camara2d.viewportHeight);
			bmf.drawMultiLine(batch, creditos, 0, camara2d.viewportHeight-100);
		batch.end();
	}

	@Override
	public void resize(int width, int height) 
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
	public void pause () {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void resume () {
		 Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose () {
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
	}

	@Override
	public boolean keyDown (int keycode) 
	{
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
