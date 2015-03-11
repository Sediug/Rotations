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

/**
 * @author Sebastian Cabanas 
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


public class MeuXogoGame extends Game {
	SpriteBatch batch;
	Texture img;
	
  	//Graficos User Interface
  	public static AssetManager assetManager;
    public static Skin skin;
    private boolean  comprobarSon;
    private boolean soundState;//Estado true -> enabled false ->disabled
    private Preferences prefs;
    public final String arquivoPreferencias = "Preferencias.xml";
    
    //Son cliks
    private Sound sonSelect, sonBack;
    
	@Override
	public void create() {
		//Cargar as texturas do xogo.
		AssetsXogo.cargarTexturas();
		
		//Cargar User interface.
		assetManager = new AssetManager();
        assetManager.load("uiskin.atlas",TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas = assetManager.get("uiskin.atlas", TextureAtlas.class);
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas); // Cargamos os estilos
        
        comprobarSon = true; //Cando inicia o xogo preguntar si queren habilitar son ou non e despois non volver preguntar.
        soundState = false; // antes de arrancar a app que non soe a música a menos que lle de na Yes no dialog
        
        prefs = Gdx.app.getPreferences(arquivoPreferencias);
        
	    sonSelect = AssetsXogo.sonSelect;
	    sonBack = AssetsXogo.sonBack;
	    
		//Asignar a pantalla a que vai chamar a nosa clase principal (esta clase).
		setScreen(new Presentacion(this));	
		
	}
	
	//Set e get para o estado de sonido, encendido/apagado.
	public boolean getSoundState(){
		return soundState;
	}
		
	public void setSoundState(boolean estado){
		soundState = estado;
	}
	
	public boolean getComprobarSound(){
		return comprobarSon;
	}
	
	public void setComprobarSound(boolean estado){
		comprobarSon = estado;
	}
	
	public Preferences getPrefs(){
		return prefs;
	}
	
	public Sound sonSelect() {			
		return sonSelect;
			
	}
	public Sound sonBack() {		
		return sonBack;
	}
	@Override
	public void dispose() {
		
		//Liberar as texturas do xogo.
		AssetsXogo.liberarTexturas();
		
		assetManager.dispose();
		skin.dispose();
		
		super.dispose();
	}
}
