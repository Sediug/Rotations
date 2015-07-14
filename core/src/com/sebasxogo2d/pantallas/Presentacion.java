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
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Sebas.AssetsXogo;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;

/**
 * Main Screen of the game which it's shows when the user enter in the application.
 * Implements the interface Screen to use the render, resize and the lifecycle methods.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class Presentacion implements Screen, InputProcessor{

	private MeuXogoGame meuxogogame;
	private OrthographicCamera camara2d;
	private SpriteBatch batch;
	private static Texture fondo;


	private final float ANCHOBUTTON = 190f, ALTOBUTTON = 50f;
	private final float X = 50f, quitY = 85f, optionsY = 140f, scoreY = 195f, playY = 250f;
	
	private Vector3 vecTemporal;
	private Rectangle dedo;
	private Rectangle boton;
	private Music son;
	private final float volumenSon = 0.2f;
	private final float volumenSonAndroid = 1f;
	 
	private boolean escoitarClicks;
	
	
	//UI
  	private Stage stage;
  	private Skin skin = MeuXogoGame.skin;
  	
  	private Preferences prefs;

  	private Rectangle buttonYes, buttonNo;
  	private boolean sair;
  	
	
	public Presentacion (MeuXogoGame xogo) 
	{
		this.meuxogogame = xogo;
		
		camara2d = new OrthographicCamera();
        	batch = new SpriteBatch();
        	fondo = new Texture(Gdx.files.internal("PantallaPrincipal2.png"));
	    
        	prefs = meuxogogame.getPrefs();
	    
		vecTemporal = new Vector3();
		dedo = new Rectangle();
		boton = new Rectangle();
		
	    	son = AssetsXogo.musicaP;
	    	
        	cargarStage();
            
		escoitarClicks = true;
	    
		//Rectangulo que representa posicion e tamanho dos botons dos dialogs
		buttonYes = new Rectangle(120f, 215f, 30f, 25f);
		buttonNo = new Rectangle(159f, 215f, 30, 25);
	    
	    	sair = false;
	    
		//Preguntar se quere son activado ou non
        	if(meuxogogame.getComprobarSound()) avisarSonido();
        
	}

	
	//Carga o estage asignandolle que se en android pulsan back chame a avisar sair.
	private void cargarStage () 
	{
		escoitarClicks = false;
    		sair = true;
		Gdx.input.setCatchBackKey(true);
            	stage = new Stage()
	            	{
	                    @Override
	                    public boolean keyDown(int keyCode) {
	                    	if (keyCode == Keys.BACK) {
	                            avisarSair();
	                    	}
	                    	return super.keyDown(keyCode);
	                    }
            		};
            
        	Gdx.input.setInputProcessor(stage);
	}
	
	
	private void avisarSair() 
	{
		new Dialog("Exit game", skin, "dialog") 
		{ 
			protected void result (Object object) 
			{
                		if ((Boolean) object)
                		{
                     			Gdx.app.exit();
                		}
			}	
			
		}
		.text("Are you sure to exit the game?")
		.button("Yes", true)
		.button("No", false)
		.key(Keys.ENTER, true)
        	.key(Keys.ESCAPE, false)
		.show(stage);

	}
	
	

	private void avisarSonido () 
	{
		escoitarClicks = false; // Mentres miro esto non quero que se active ningún outro boton.
		new Dialog("Sound state", skin, "dialog") 
		{ 
			protected void result (Object object) {}
		}
		.text("Enable the game sound?") 
		.button("Yes", true)
		.button("No", false)
		.key(Keys.ENTER, true)
		.key(Keys.ESCAPE, false).show(stage);
		

	}
    
	@Override
	public void render (float delta) 
	{
		
		batch.begin();
			batch.draw(fondo,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
			debuxarBotons();
		batch.end();
		
		//User interface
		stage.act(Gdx.graphics.getDeltaTime());
        	stage.draw();
	}

	private void debuxarBotons () 
	{
		batch.draw(AssetsXogo.play, X, playY, ANCHOBUTTON, ALTOBUTTON);
		batch.draw(AssetsXogo.quit, X, quitY, ANCHOBUTTON, ALTOBUTTON);
		batch.draw(AssetsXogo.hightscores, X, scoreY, ANCHOBUTTON, ALTOBUTTON);
		batch.draw(AssetsXogo.options, X, optionsY, ANCHOBUTTON, ALTOBUTTON);
	}
	
	private void playMusica () 
	{
		son.play();
		son.setVolume(volumenSon);
		if(Gdx.app.getType().name().equalsIgnoreCase("android"))son.setVolume(volumenSonAndroid);
	}

	@Override
	public void resize (int width, int height) 
	{
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
        	camara2d.update();
       
        	batch.setProjectionMatrix(camara2d.combined);
        	batch.enableBlending();
	}
	
	@Override
	public void show () 
	{
		Gdx.input.setInputProcessor(this);
		if(meuxogogame.getSoundState())playMusica(); 
	}


	@Override
	public void hide () 
	{
		// TODO Auto-generated method stub
		son.stop();
	}

	@Override
	public void pause () 
	{
		Gdx.input.setInputProcessor(null);
		son.pause();
	}

	@Override
	public void resume () 
	{
		Gdx.input.setInputProcessor(this);
		if(meuxogogame.getSoundState())playMusica();
	}

	@Override
	public void dispose () 
	{
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		fondo.dispose();
		son.dispose();
		
		stage.dispose();
		skin.dispose();
	}

	@Override
	public boolean keyDown (int keycode) 
	{
		if (keycode == Keys.BACK) {
			playSonBack();
			escoitarClicks = false;
        		sair = true;
        		avisarSair();
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
		
		
		vecTemporal.set(screenX,screenY,0);
        	camara2d.unproject(vecTemporal);
        
        	dedo.set(vecTemporal.x,vecTemporal.y,2, 2);
        	boton.set(X, playY, ANCHOBUTTON, ALTOBUTTON);
        	
        	if(meuxogogame.getComprobarSound()) 
        		dialogYesNoSound();
        
	        if (Intersector.overlaps(dedo, boton) && escoitarClicks)
	        {
	        	playSonSelect();
	        	meuxogogame.setScreen(new PantallaXogo(meuxogogame));
	        }
        
	        boton.set(X, scoreY, ANCHOBUTTON, ALTOBUTTON);
	        
	        if (Intersector.overlaps(dedo, boton)&& escoitarClicks) // Interseccion dedo co boton.
	        {
	        	playSonSelect();
	        	meuxogogame.setScreen(new PantallaMarcadores(meuxogogame));
	        }
	        
	        boton.set(X, optionsY, ANCHOBUTTON, ALTOBUTTON);
	        
	        if (Intersector.overlaps(dedo, boton)&& escoitarClicks)
	        {// Interseccion dedo co boton.
	        	
	        	playSonSelect();
	        	meuxogogame.setScreen(new PantallaOptions(meuxogogame));
	        }
	        
	        //Quit 
	        boton.set(X, quitY, ANCHOBUTTON, ALTOBUTTON);
	        
	        if (Intersector.overlaps(dedo, boton))
	        {
	        	// Ponher a falso para que so escoite os clicks de Yes,No. Se non está esto sempre 
	        	//pillaría antes os clicks a highscore etc porque os comproba antes.
	        	playSonBack();
	        	escoitarClicks = false;
	        	sair = true;
	        	avisarSair();//Dialog exit
	        }
	        
	        if(!escoitarClicks && sair) 
	        	dialogYesNoSair();
	        
        
		return false;
	}


	public void playSonBack () {
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSon);
		}
			
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSonAndroid);
		}
	}
	
	public void playSonSelect () {
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), volumenSon);
		}
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android"))
		{
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), volumenSonAndroid);
		}
	}



	private void dialogYesNoSair () 
	{
		
	        //Comprobar Yes
	        if (Intersector.overlaps(dedo, buttonYes) ){//Yes
	        	Gdx.app.exit();	  	
	        }
	        
	        //Comprobar No.
	        if(Intersector.overlaps(dedo, buttonNo)){//No
	        	meuxogogame.setScreen(new Presentacion(meuxogogame));	
	        }
        
	}
	
	private void dialogYesNoSound () 
	{
		
		//Comprobar Yes  
	        if(Intersector.overlaps(dedo, buttonYes) )
	        {
	        	 meuxogogame.setSoundState(true);//Enabled / yes 
	        	 meuxogogame.setComprobarSound(false);//Comprobado. A false
	        	 prefs.putBoolean("soundState", true);
	        	 
	        	 meuxogogame.setScreen(new Presentacion(meuxogogame));
	        }
        
	        //Comprobar No.
	        if(Intersector.overlaps(dedo, buttonNo))
	        {
	        	meuxogogame.setSoundState(false);//Disabled / No 
	        	meuxogogame.setComprobarSound(false);//Comprobado.
	        	prefs.putBoolean("soundState", false);
	        	
	        	meuxogogame.setScreen(new Presentacion(meuxogogame));
	        }
	        
	        prefs.flush();
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
