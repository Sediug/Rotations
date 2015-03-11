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
 * @author Sebastian Cabanas 
 * */

public class PantallaMarcadores implements Screen, InputProcessor {
	
	private MeuXogoGame meuxogogame;
	private OrthographicCamera camara2d;
	private SpriteBatch batch;
	private static Texture fondo;
	private Rectangle dedo, boton;
	
	private final float ANCHOBUTTON = 190f, ALTOBUTTON = 50f;
	private final float X = 50f, Y = 110f;
	
	//Preferencias / Puntuacions
	private Preferences prefs;
	private int puntuacion1, puntuacion2, puntuacion3, puntuacion4, puntuacion5;
	private String name1, name2, name3, name4, name5;
	
	private BitmapFont bitMapFont;
	
	//Posicion puntuacions.
	private final float xP1 = 92f, yP1 = 400f;
	private final float xP2 = 92f, yP2 = 355f;
	private final float xP3 = 92f, yP3 = 315f;
	private final float xP4 = 92f, yP4 = 270f;
	private final float xP5 = 92f, yP5 = 225f;
	
	// Volumen
	private final float volumenSon = 0.2f;
	private final float volumenSonAndroid = 1f;
	
	public PantallaMarcadores(MeuXogoGame xogo){
		this.meuxogogame = xogo;
		
		camara2d = new OrthographicCamera();
        batch = new SpriteBatch();
        fondo = new Texture(Gdx.files.internal("PantallaScore.png"));
        
        dedo = new Rectangle();
        boton = new Rectangle();
        
        //Preferencias / Puntuacions
        prefs = meuxogogame.getPrefs();
        cargarPuntActuales();
        
        //Opcións de escritura.
        bitMapFont = new BitmapFont();
		bitMapFont.setColor(Color.BLUE);
	    bitMapFont.setScale(1.5f);
	}
	public void playSonBack() {
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSon);
		}
			
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSonAndroid);
		}
	}
	

	private void cargarPuntActuales() {
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
	
	@Override
	public void render(float delta) {
		batch.begin();
		
			batch.draw(fondo,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
			batch.draw(AssetsXogo.back, X, Y, ANCHOBUTTON, ALTOBUTTON);
			
			//Pintar puntuación.
		    pintarPuntuacions();
		    
		batch.end();

	}

	private void pintarPuntuacions() {
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

	@Override
	public void resize(int width, int height) {
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
        camara2d.update();
       
        batch.setProjectionMatrix(camara2d.combined);
        batch.enableBlending();
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void resume() {
		 Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {//Android pulsan a tecla de volver.
			playSonBack();
        	meuxogogame.setComprobarSound(false);
        	meuxogogame.setScreen(new Presentacion(meuxogogame));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		Vector3 vecTemporal = new Vector3();
		
		vecTemporal.set(screenX,screenY,0);
        camara2d.unproject(vecTemporal);

        
        dedo.set(vecTemporal.x,vecTemporal.y,2, 2);
        boton.set(X, Y, ANCHOBUTTON, ALTOBUTTON);
        
        
        if (Intersector.overlaps(dedo, boton)){// Interceccion dedo co boton.
        	playSonBack();
        	meuxogogame.setComprobarSound(false);
        	meuxogogame.setScreen(new Presentacion(meuxogogame));
        }
        
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
