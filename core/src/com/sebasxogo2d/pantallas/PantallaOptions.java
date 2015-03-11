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

public class PantallaOptions implements Screen {
	
	private MeuXogoGame meuxogogame;
	private OrthographicCamera camara2d;
	private SpriteBatch batch;
	private boolean changes;//Cambiar fondo si hay cambios sen gardar.
	
	//Preferencias 
	private Preferences prefs;

	
	//UI
  	private Stage stage;
  	private Skin skin = MeuXogoGame.skin;
  	private SelectBox<String> lista, sound, languaje;
  	private TextField left, right;
  	private int idLista, idSound, idLanguaje;
  	private String nivel, prefLeft, prefRight, estado; // Preferencias
  	
  	//Ui pos // tamaño
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
	
	// Volumen
	private final float volumenSon = 0.2f;
	private final float volumenSonAndroid = 1f;
  	
	public PantallaOptions(MeuXogoGame xogo) {
		
		this.meuxogogame = xogo;
		
		camara2d = new OrthographicCamera();
        batch = new SpriteBatch();
        changes = false;
        
        //Preferencias / Puntuacions
        prefs = meuxogogame.getPrefs();
        
        //User interface
        stage = new Stage(){
            @Override
            public boolean keyDown(int keyCode) {// Back android.
            if (keyCode == Keys.BACK) {
            	playSonBack();
            	meuxogogame.setComprobarSound(false);
            	meuxogogame.setScreen(new Presentacion(meuxogogame));    
            }
            return super.keyDown(keyCode);
            }
    };
        cargarPanelOptions(); 
        cargarPrefs();
	}
	
	public void playSonBack() {
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSon);
		}
			
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonBack().setVolume(meuxogogame.sonBack().play(), volumenSonAndroid);
		}
	}
	
	public void playSonSelect() {
		if(meuxogogame.getSoundState() && !Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), volumenSon);
		}
		else if (meuxogogame.getSoundState() && Gdx.app.getType().name().equalsIgnoreCase("android")){
			meuxogogame.sonSelect().setVolume(meuxogogame.sonSelect().play(), volumenSonAndroid);
		}
	}
	private void cargarPanelOptions() {

		lista = new SelectBox<String>(skin) ;
        lista.setItems("Easy","Normal", "Kappa");
		lista.setBounds(xSBox,yLista, anchoSB, alto);
        lista.setPosition(xSBox,yLista);
        
        sound = new SelectBox<String>(skin) ;
        sound.setItems("Enabled", "Disabled");
		sound.setBounds(xSBox,ySound, anchoSB, alto);
        sound.setPosition(xSBox,ySound);
        
        languaje = new SelectBox<String>(skin) ;
        languaje.setItems("English");
		languaje.setBounds(xSBox,yLang, anchoSB, alto);
        languaje.setPosition(xSBox,yLang);
        
        left = new TextField("", skin);
		left.setBounds(xLeft,yText, alto, alto);
        
        right = new TextField("", skin);
        right.setBounds(xRigth,yText, alto, alto);
        
        TextButton save = new TextButton("Save",skin);
        save.setBounds(saveX, buttonsY,ANCHO, ALTO);
        save.scaleBy(2);
        save.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	gardarDatos();
            	playSonSelect();
            	changes = false;//Cambios gardados, por tanto non hay cambios sen gardar.
            	cargarPrefs();
                return true;
            }
        });
        
        TextButton back = new TextButton("Back",skin);
        back.setBounds(backX, buttonsY,ANCHO, ALTO);
        back.scaleBy(2);
        back.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSonBack();
            	meuxogogame.setComprobarSound(false);
            	meuxogogame.setScreen(new Presentacion(meuxogogame));
                return true;
            }
        });
        
        TextButton restore = new TextButton("Restore",skin);
        restore.setBounds(restoreX, buttonsY,ANCHO, ALTO);
        restore.scaleBy(2);
        restore.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	changes = true;
            	setToDefault();
                return true;
            }
        });
        
        TextButton credits = new TextButton("Credits",skin);
        credits.setBounds(saveX, buttonsY-Gdx.graphics.getHeight()/12,ANCHO, ALTO);
        credits.scaleBy(2);
        credits.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	playSonSelect();
            	meuxogogame.setScreen(new Creditos(meuxogogame));
                return true;
            }
        });
        
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
	public void listeners() {
		idLista = lista.getSelectedIndex();
		idSound = sound.getSelectedIndex();
		idLanguaje = languaje.getSelectedIndex();
		
		if(!lista.getSelected().equalsIgnoreCase(nivel) || !sound.getSelected().equalsIgnoreCase(estado))changes = true; //Hubo cambios.  
		
		// SO facer o subString cando escribiron algo, o subString é para que non poidan poñer máis de unha letra.
		if(!left.getText().equalsIgnoreCase(""))left.setText(left.getText().substring(0, 1));
		if(!right.getText().equalsIgnoreCase(""))right.setText(right.getText().substring(0, 1));
	}


	
	@Override
	public void render(float delta) {
		batch.begin();
			if(changes)batch.draw(AssetsXogo.textureOptions,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
			else batch.draw(AssetsXogo.textureOptions2,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
		batch.end();
		
		
		listeners();
		
		
		//User interface
		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        

	}
	
	public void asco(){
		while(true){
			System.out.println("Asco!!");
		}
	}


	@Override
	public void resize(int width, int height) {
		camara2d.setToOrtho(false, Mundo.MUNDO_ANCHO, Mundo.MUNDO_ALTO);
        camara2d.update();
       
        batch.setProjectionMatrix(camara2d.combined);
        batch.enableBlending();
        
        stage.getViewport().update(width, height, true);
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
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
		 Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
	public void cargarPrefs(){
		//Lista
		if(prefs.getString("nivel","pEasy").equalsIgnoreCase("pNormal")) nivel = "Normal";
		else if (prefs.getString("nivel","pEasy").equalsIgnoreCase("pKappa")) nivel = "Kappa";
		else nivel = "Easy";
				
		lista.setSelected(nivel);
		
		//Sound
		if(prefs.getBoolean("soundState", false))estado = "Enabled";
		else estado = "Disabled";
		
		sound.setSelected(estado);
		
		//Controis. Cargalos e se son distintos de nada asignalos os InputText left/right
		prefLeft = prefs.getString("controisLeft", "");
		prefRight = prefs.getString("controisRight", "");
		
		if(!prefLeft.equalsIgnoreCase(""))left.setText(prefLeft);
		if(!prefRight.equalsIgnoreCase(""))right.setText(prefRight);
		
	}

	public void gardarDatos() {
		switch (idLista) {
				
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
    	switch (idSound) {
			case 0:
				meuxogogame.setSoundState(true);// Enabled
				prefs.putBoolean("soundState", true);
				break;
			case 1:
				meuxogogame.setSoundState(false);// Disabled
				prefs.putBoolean("soundState", false);
				break;
		}
    	
    	// So garda os cambios nas letras se introduciron unha a dereita e outra a esquerda. 1 soa non a garda.
    	prefs.putString("controisLeft", left.getText());
    	prefs.putString("controisRight", right.getText());
    	
    	
    	prefs.flush();
	}
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
