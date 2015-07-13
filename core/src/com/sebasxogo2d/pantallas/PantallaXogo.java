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
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Sebas.AssetsXogo;
import com.mygdx.game.Sebas.MeuXogoGame;
import com.sebasxogo2d.modelo.Mundo;
import com.sebasxogo2d.renderer.RendererXogo;

/**
 * The Game Screen.
 * Implements the interface Screen to use the render, resize and the lifecycle methods.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class PantallaXogo implements Screen, InputProcessor {

	
	private final boolean ANDROID = Gdx.app.getType().name().equalsIgnoreCase("android");
	// Variables da clase.
	private boolean pause = false;
	private boolean finxogo = false;
	private int tempo_Nivel = 30;// 15 - 30 - 45 - 60
	private final int aumentoVelocidade = 50;
	
	// Cronometro
	private float crono;
	private int segCambioNivel;//O segundo no que se aumentou velocidade e puntos. (Para que non se execute + de unha vez nese segundo)

	// private boolean sair = false;
	private boolean moverPala = true;

	private MeuXogoGame meuxogogame;
	private RendererXogo rendererxogo;
	private Mundo meuMundo;

	private Vector3 vecTemporal;
	private Rectangle dedo, sound, pauseBtn, exit;

	// Sons
	private Music sonFondo;
	private boolean soundState;
	private final float volumen = 0.2f;
	private final float volumenAndoid = 1f;

	// Preferencias a gardar.
	private Preferences prefs;
	public static String puntuacion1 = "puntuacion1";
	public static String puntuacion2 = "puntuacion2";
	public static String puntuacion3 = "puntuacion3";
	public static String puntuacion4 = "puntuacion4";
	public static String puntuacion5 = "puntuacion5";
	public static String nivel = "nivel", level;
	public static int levelInteger;
	private int p1, p2, p3, p4, p5;// Para obter as puntuacións gardadas.
	private String n1, n2, n3, n4; // Para obter os nomes asociados as puntuacións.
	
	// UI
	private Stage stage;
	private Skin skin = MeuXogoGame.skin;
	private TextField marcador;
	private Label labTexto;
	private TextField texto;
	private Button save;
	private float anchoMarcador;
	
	//Tamanhos UI
	private final float ANCHO_NOMBRE = (float) (Gdx.graphics.getWidth()/3), ALTO_NOMBRE = Gdx.graphics.getHeight()/20;
	private final float	ANCHO_SAVE = (float) (Gdx.graphics.getWidth()/5.3),ALTO_SAVE = Gdx.graphics.getHeight()/23;
	private final float ANCHO_LAB = Gdx.graphics.getWidth()/23, ALTO_LAB = Gdx.graphics.getHeight()/30;
	private final float nombreX =(float) (Gdx.graphics.getWidth()/3) ,nombreY = (float) (Gdx.graphics.getHeight()/2.3); 
	private final float saveX = (float) (Gdx.graphics.getWidth()/2.45), saveY = (float) (Gdx.graphics.getHeight()/2.8);
	private final float labNombreX = (float) (Gdx.graphics.getWidth()/2.3), labNombreY = (float) (Gdx.graphics.getHeight()/2);
	private final float MARCADOR_X = (float) (Gdx.graphics.getWidth() / 2.3);
	private final float MARCADOR_Y = (float) (Gdx.graphics.getHeight()*0.93);
	private final float MARCADOR_ALTO = Gdx.graphics.getHeight()/19;
	
	private int id = 1;//Para ampliar ancho marcador. Entendese dentro do metodo.
	private int idMove; //mover dereita ou esquerda.
	private String teclaIz, teclaDer;

	public PantallaXogo(MeuXogoGame meuxogogame) {

		meuMundo = new Mundo();

		this.meuxogogame = meuxogogame;
		rendererxogo = new RendererXogo(meuMundo);

		vecTemporal = new Vector3();

		dedo = new Rectangle();
		sound = new Rectangle();
		pauseBtn = new Rectangle();
		exit = new Rectangle();

		soundState = meuxogogame.getSoundState();
		sonFondo = AssetsXogo.musica;

		prefs = meuxogogame.getPrefs();
		obterPrefs();
	
		// User interface
		anchoMarcador = 27f;
		stage = new Stage();
		marcador();
		nombre();
		gardarBoton();
		labNombre();
		
		segCambioNivel = 0;
		
		
	}

	private void obterPrefs() {
		// Obter puntuacións actuales.
		p1 = prefs.getInteger(puntuacion1, 0);
		p2 = prefs.getInteger(puntuacion2, 0);
		p3 = prefs.getInteger(puntuacion3, 0);
		p4 = prefs.getInteger(puntuacion4, 0);
		p5 = prefs.getInteger(puntuacion5, 0);

		// Obter nomes actuales.
		n1 = prefs.getString("name1", "");
		n2 = prefs.getString("name2", "");
		n3 = prefs.getString("name3", "");
		n4 = prefs.getString("name4", "");
		
		// Obter level seleccionado.
		level = prefs.getString(nivel, "pEasy");
		
		// Obter controis.
		teclaIz = prefs.getString("controisLeft","left");
		teclaDer = prefs.getString("controisRight","right");
		
		prefs.flush();
		asignarLevel();
		
	}

	public void asignarLevel() {
		if(level.equalsIgnoreCase("pEasy")){
			levelInteger = 1;
		}else if (level.equalsIgnoreCase("pNormal")){
			levelInteger = 2;
		}else{
			levelInteger = 3;
		}
	}

	private void gardarBoton() {
		save = new TextButton("Save", skin);
		save.setBounds(saveX, saveY, ANCHO_SAVE, ALTO_SAVE);
		save.setVisible(false);
		
		save.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				// Gardamos mellores puntuacions no hide()
				meuxogogame.setComprobarSound(false);
				meuxogogame.setScreen(new Presentacion(meuxogogame));
			}
		});
		stage.addActor(save);
	}

	private void nombre() {
		texto = new TextField("", skin);
		texto.setBounds(nombreX, nombreY, ANCHO_NOMBRE, ALTO_NOMBRE);
		texto.setVisible(false);
		//texto.
		stage.addActor(texto);
	}

	private void marcador() {
		marcador = new TextField("0", skin);
		marcador.setColor(Color.RED);
		marcador.setBounds(MARCADOR_X,MARCADOR_Y, anchoMarcador, MARCADOR_ALTO);
		stage.addActor(marcador);
	}
	private void labNombre() {
		labTexto = new Label("Type a nickname please: ", skin);
		labTexto.setColor(Color.DARK_GRAY);
		labTexto.setFontScale(1);
		labTexto.setBounds(labNombreX, labNombreY, ANCHO_LAB, ALTO_LAB);
		labTexto.setAlignment(Align.center);
		labTexto.setVisible(false);
		stage.addActor(labTexto);
	}

	private void cargarElementosGraficos() {
		marcador.setText("" + rendererxogo.getPuntuacionTotal());
	}

	private void play_musica() {
		sonFondo.setVolume(volumen);
		if (ANDROID)sonFondo.setVolume(volumenAndoid);
		sonFondo.setLooping(true);
		sonFondo.play();
	}

	private void actualizarPuntuacions() {
		// Puntuación da partida.
		int puntosPartida = rendererxogo.getPuntuacionTotal();
		String nome = texto.getText();
		if (nome.length()>8)nome = nome.substring(0, 8);


		// Si a puntuacion de esta partida supera a partida gardada actualmente
		// actualizamos a puntuación na posición que sexa.
		if (puntosPartida > p1) {
			prefs.putInteger(puntuacion1, puntosPartida);
			prefs.putString("name1", nome);

			// Si algunha e menor asigno a puntuacion gardada na var de
			// puntuacion actual para que se garde no seguinte.
			puntosPartida = p1;
			nome = n1;
		}
		if (puntosPartida > p2) {
			prefs.putInteger(puntuacion2, puntosPartida);
			prefs.putString("name2", nome);

			puntosPartida = p2;
			nome = n2;
		}
		if (puntosPartida > p3) {
			prefs.putInteger(puntuacion3, puntosPartida);
			prefs.putString("name3", nome);

			puntosPartida = p3;
			nome = n3;
		}
		if (puntosPartida > p4) {
			prefs.putInteger(puntuacion4, puntosPartida);
			prefs.putString("name4", nome);

			puntosPartida = p4;
			nome = n4;
		}
		if (puntosPartida > p5) {
			prefs.putInteger(puntuacion5, puntosPartida);
			prefs.putString("name5", nome);

		}

		prefs.flush();
	}

	@Override
	public void render(float delta) {
		crono = rendererxogo.getCrono();
		rendererxogo.render(delta);
		soundState = meuxogogame.getSoundState();
		rendererxogo.setSoundState(soundState);// Pasolle o render o estado de
												// son	
		controlarCrono();
		ampliarMarcador();
		moverPala();

		if (!soundState)
			sonFondo.pause();
		else
			play_musica();

		if (pause) {
			meuxogogame.setScreen(new PantallaPause(meuxogogame, this));
			return;
		}

		if (rendererxogo.getVidas() <= 0) {// Numero de vidas menor que 0 fin do
											// xogo.
			finxogo = true;
		}

		if (finxogo) {
			rendererxogo.setOver(true);
			texto.setVisible(true);
			save.setVisible(true);
			labTexto.setVisible(true);
			Gdx.input.setInputProcessor(stage);
		}

		// User interface
		cargarElementosGraficos();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void moverPala() {
		if (moverPala)rendererxogo.moverPala(1, claves(teclaIz), claves(teclaDer));
		
		switch (idMove) {
			case 2:
				rendererxogo.moverPala(2);	
				break;
	
			case 3:
				rendererxogo.moverPala(3);	
				break;
		}
	}

	public void controlarCrono() {

		// Mostrado o 3 2 1 GO! toca empezar.
		if(crono > 3.5)rendererxogo.setRuning(true);
		
		
		// Cada (tempo_Nivel)segundos aumentar velocidade e puntuacion. Sacar 2 bolas
		if((int)crono == tempo_Nivel && (int) crono != segCambioNivel){
			rendererxogo.addVelocidadeBola(aumentoVelocidade);
			rendererxogo.addPuntos(10, levelInteger);
			segCambioNivel = (int) crono;// Gardamos ultima vez que se aumentou para non volver aumentar no mismo segundo.
			tempo_Nivel += tempo_Nivel;// Ir de tempo_Nivel en TNivel segundos. 15 - 30 - 45 -60 etc
			rendererxogo.setBola2(true);
			rendererxogo.setLevelUP(true);
		} 
	}

	private void ampliarMarcador() {
		
		if (rendererxogo.getPuntuacionTotal() > 99 && id == 1) {
			anchoMarcador += 10;
			marcador.setWidth(anchoMarcador);
			id++;
		}

		if (rendererxogo.getPuntuacionTotal() > 999  && id == 2) {
			anchoMarcador += 10;
			marcador.setWidth(anchoMarcador);
			id++;
		}

		if (rendererxogo.getPuntuacionTotal() > 99999 && id == 3) {
			anchoMarcador += 10;
			marcador.setWidth(anchoMarcador);
			id++;
		}
	}

	@Override
	public void resize(int width, int height) {
		rendererxogo.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
		pause = false;

		if (soundState)
			play_musica();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		sonFondo.stop();
		actualizarPuntuacions();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		if (!finxogo) {
			pause = true;
		}
		sonFondo.pause();

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
		pause = false;
		if (soundState)
			play_musica();
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		rendererxogo.dispose();
		sonFondo.dispose();

		skin.dispose();
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.ENTER) {
			if (finxogo) {
				meuxogogame.setComprobarSound(false);
				meuxogogame.setScreen(new Presentacion(meuxogogame));
			}
		}
		
		if (keycode == Keys.BACK) {//Android pulsan a tecla de volver.
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
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		idMove = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touch(screenX, screenY);
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

	private void touch(int screenX, int screenY) {
		moverPala = true;// Se clickean nos botons de arriba que non se mova a
							// pala (sonido, pause, exit), pero por defecto que
							// si se mova.

		Vector3 temporal = new Vector3(screenX, screenY, 0);
		RendererXogo.camara2d.unproject(temporal);

		vecTemporal.set(screenX, screenY, 0);
		RendererXogo.camara2d.unproject(vecTemporal);

		dedo.set(vecTemporal.x, vecTemporal.y, 2, 2);

		// Referencia os botons do render.
		sound.set(RendererXogo.X_SOUND, RendererXogo.Y_BOTONS,
				RendererXogo.ANCHO_BOTONS, RendererXogo.ALTO_BOTTONS);
		pauseBtn.set(RendererXogo.X_PAUSE, RendererXogo.Y_BOTONS,
				RendererXogo.ANCHO_BOTONS, RendererXogo.ALTO_BOTTONS);
		exit.set(RendererXogo.X_EXIT, RendererXogo.Y_BOTONS,
				RendererXogo.ANCHO_BOTONS, RendererXogo.ALTO_BOTTONS);

		comprobarBoton(sound, 1);
		comprobarBoton(pauseBtn, 2);
		comprobarBoton(exit, 3);
		
		if (Intersector.overlaps(dedo, rendererxogo.getIz())){
			idMove = 2;
		}
		
		
		if (Intersector.overlaps(dedo, rendererxogo.getDerecho())){
			idMove = 3;
		}
		


	}

	private void comprobarBoton(Rectangle boton, int id) {
		switch (id) {
		case 1:// Boton sonido
			if (Intersector.overlaps(dedo, boton)) {
				changeSound();
				moverPala = false;
			}
			break;

		case 2:// Boton pause
			if (Intersector.overlaps(dedo, boton)) {
				pause = true;
				moverPala = false;
			}
			break;

		case 3:// Boton exit
			if (Intersector.overlaps(dedo, boton)) {
				meuxogogame.setComprobarSound(false);// So se debe comprobar a
														// primeira vez cando
														// entramos, ademáis
														// esto so afecta o
														// listener
				meuxogogame.setScreen(new Presentacion(meuxogogame));
			}
			break;
		}
	}

	public void changeSound() {
		if (soundState) {
			meuxogogame.setSoundState(false);// Invertir estado.
			prefs.putBoolean("soundState", false);
		} else {
			meuxogogame.setSoundState(true);
			prefs.putBoolean("soundState", true);
		}
		
		prefs.flush();
	}
	
	public int claves(String key){
		if(key.equalsIgnoreCase("a"))return Keys.A;
		if(key.equalsIgnoreCase("q"))return Keys.Q;
		if(key.equalsIgnoreCase("w"))return Keys.W;
		if(key.equalsIgnoreCase("e"))return Keys.E;
		if(key.equalsIgnoreCase("r"))return Keys.R;
		if(key.equalsIgnoreCase("t"))return Keys.T;
		if(key.equalsIgnoreCase("y"))return Keys.Y;
		if(key.equalsIgnoreCase("u"))return Keys.U;
		if(key.equalsIgnoreCase("i"))return Keys.I;
		if(key.equalsIgnoreCase("o"))return Keys.O;
		if(key.equalsIgnoreCase("p"))return Keys.P;
		if(key.equalsIgnoreCase("s"))return Keys.S;
		if(key.equalsIgnoreCase("d"))return Keys.D;
		if(key.equalsIgnoreCase("f"))return Keys.F;
		if(key.equalsIgnoreCase("g"))return Keys.G;
		if(key.equalsIgnoreCase("h"))return Keys.H;
		if(key.equalsIgnoreCase("y"))return Keys.Y;
		if(key.equalsIgnoreCase("j"))return Keys.J;
		if(key.equalsIgnoreCase("k"))return Keys.K;
		if(key.equalsIgnoreCase("l"))return Keys.L;
		if(key.equalsIgnoreCase("z"))return Keys.Z;
		if(key.equalsIgnoreCase("x"))return Keys.X;
		if(key.equalsIgnoreCase("c"))return Keys.C;
		if(key.equalsIgnoreCase("v"))return Keys.V;
		if(key.equalsIgnoreCase("b"))return Keys.B;
		if(key.equalsIgnoreCase("n"))return Keys.N;
		if(key.equalsIgnoreCase("m"))return Keys.M;
		if(key.equalsIgnoreCase("left"))return Keys.LEFT;
		if(key.equalsIgnoreCase("right"))return Keys.RIGHT;
		return 0;
	}
}
