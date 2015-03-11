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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsXogo {

	/*
	 * Pola perda do contexto gráfico cando saímos do xogo en Android, imos
	 * definir as texturas de clase (static) e imos crear dous métodos de clase:
	 * un para cargar as texturas e outro para liberalas.
	 * 
	 * Dependendo do xogo podemos ter varios arquivos Assets se que por exemplo,
	 * podemos cargar os gráficos en función do nivel do xogo, facendo unha
	 * división lóxica e creando un arquivo para o protagonista, outro para os
	 * inimigos,...
	 */

	// Animacion bola
	public static Texture textureBola;
	public static TextureRegion[][] tmpBola;
	public static Animation animBola;

	// Paleta
	public static Texture texturePaleta;

	// Fondo
	public static Texture textureFondo;
	public static Texture textureOptions, textureOptions2;

	// Vidas
	public static Texture vidas;

	// Botons
	public static Texture play, quit, hightscores, options, back;
	public static Texture exit, pause, soundOn, soundOff;

	// Game Over
	public static Texture over;

	// Música de fondo. Sons
	public static Music musica;
	public static Music musicaP;
	public static Sound sonSelect, sonBack;

	// Monte.
	private static TextureAtlas aMonte;
	public static TextureRegion monte1, monte2, monte3, monte4, monte5, monte6,
			monte7, monte8, monte9, monte10, monte11, monte12;
	public static TextureRegion[] framesAnimMonte;
	public static Animation animMonte;

	// 321GO Levels etc
	public static Texture _1, _2, _3, go, level, _4, _5, _6, _7;

	// Gesture
	public static Texture izquierdo, derecho;

	/**
	 * Metodo encargado de cargar todas as texturas
	 */
	public static void cargarTexturas() {

		// Textura anim bola
		textureBola = new Texture(Gdx.files.internal("sprites.png"));

		// Texturas monte.
		aMonte = new TextureAtlas("monte.atlas");
		monte1 = aMonte.findRegion("monte1");
		monte2 = aMonte.findRegion("monte2");
		monte3 = aMonte.findRegion("monte3");
		monte4 = aMonte.findRegion("monte4");
		monte5 = aMonte.findRegion("monte5");
		monte6 = aMonte.findRegion("monte6");
		monte7 = aMonte.findRegion("monte7");
		monte8 = aMonte.findRegion("monte8");
		monte9 = aMonte.findRegion("monte9");
		monte10 = aMonte.findRegion("monte10");
		monte11 = aMonte.findRegion("monte11");
		monte12 = aMonte.findRegion("monte12");
		cargarArrayAnim();

		// Textura fondo
		textureFondo = new Texture(Gdx.files.internal("bg.png"));

		// Textura paleta de rebote.
		texturePaleta = new Texture(Gdx.files.internal("paleta.png"));

		// Textura corazon vidas
		vidas = new Texture(Gdx.files.internal("Hearts_01_64x64_017.png"));

		// Texturas botons inicio
		play = new Texture(Gdx.files.internal("Play_Blue.png"));
		quit = new Texture(Gdx.files.internal("Quit_blue.png"));
		hightscores = new Texture(Gdx.files.internal("HighScores_blue.png"));
		options = new Texture(Gdx.files.internal("Options_blue.png"));

		// Texturas botons PantallaXogo.
		exit = new Texture(Gdx.files.internal("exit.png"));
		pause = new Texture(Gdx.files.internal("pause.png"));
		soundOn = new Texture(Gdx.files.internal("SoundOn.png"));
		soundOff = new Texture(Gdx.files.internal("SoundOff.png"));
		back = new Texture(Gdx.files.internal("back.png"));

		// Texturas fondo Options
		textureOptions = new Texture(Gdx.files.internal("PantallaOptions.png"));
		textureOptions2 = new Texture(
				Gdx.files.internal("PantallaOptions2.png"));

		// Textura Game Over.
		over = new Texture(Gdx.files.internal("gameover.png"));

		// Crear as animacions.
		crearAnimacions();

		// Musica de fondo do xogo
		musica = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		musicaP = Gdx.audio.newMusic(Gdx.files.internal("sound.ogg"));

		// Sonidos plins
		sonSelect = Gdx.audio.newSound(Gdx.files.internal("MENU A_Select.ogg"));
		sonBack = Gdx.audio.newSound(Gdx.files.internal("MENU A - Back.ogg"));

		// Textura 3 2 1 GO!
		_3 = new Texture(Gdx.files.internal("321go/3.png"));
		_2 = new Texture(Gdx.files.internal("321go/2.png"));
		_1 = new Texture(Gdx.files.internal("321go/1.png"));
		go = new Texture(Gdx.files.internal("321go/go.png"));
		level = new Texture(Gdx.files.internal("321go/level.png"));
		_4 = new Texture(Gdx.files.internal("321go/4.png"));
		_5 = new Texture(Gdx.files.internal("321go/5.png"));
		_6 = new Texture(Gdx.files.internal("321go/6.png"));
		_7 = new Texture(Gdx.files.internal("321go/7.png"));

		// Textura izquierda y derecha click
		izquierdo = new Texture(Gdx.files.internal("irquierda2.png"));
		derecho = new Texture(Gdx.files.internal("derecha2.png"));

	}

	private static void cargarArrayAnim() {
		framesAnimMonte = new TextureRegion[12];
		framesAnimMonte[0] = monte1;
		framesAnimMonte[1] = monte2;
		framesAnimMonte[2] = monte3;
		framesAnimMonte[3] = monte4;
		framesAnimMonte[4] = monte5;
		framesAnimMonte[5] = monte6;
		framesAnimMonte[6] = monte7;
		framesAnimMonte[7] = monte8;
		framesAnimMonte[8] = monte9;
		framesAnimMonte[9] = monte10;
		framesAnimMonte[10] = monte11;
		framesAnimMonte[11] = monte12;
	}

	private static void crearAnimacions() {

		// Bola
		int num_columnas;
		int num_filas;
		TextureRegion[] framesanimacion;

		// Animacion bola.
		tmpBola = TextureRegion.split(textureBola, 21, 21);

		num_columnas = 12;
		num_filas = 1;
		framesanimacion = new TextureRegion[num_columnas * num_filas];

		for (int fila = 0; fila < num_filas; fila++) {
			for (int col = 0; col < num_columnas; col++) {
				framesanimacion[fila * num_columnas + col] = tmpBola[fila][col];
			}
		}

		animBola = new Animation(1f, framesanimacion);

		// Anim Monte
		animMonte = new Animation(0.01f, framesAnimMonte);
	}

	/**
	 * Metodo encargado de liberar todas as texturas
	 */
	public static void liberarTexturas() {
		textureBola.dispose();
		textureFondo.dispose();
		texturePaleta.dispose();
		hightscores.dispose();
		vidas.dispose();
		play.dispose();
		quit.dispose();
		exit.dispose();
		pause.dispose();
		over.dispose();
		soundOff.dispose();
		soundOn.dispose();
		musica.dispose();
		options.dispose();
		textureOptions.dispose();
		textureOptions2.dispose();
		aMonte.dispose();
		sonBack.dispose();
		sonSelect.dispose();
		_3.dispose();
		_2.dispose();
		_1.dispose();
		go.dispose();
		izquierdo.dispose();
		derecho.dispose();
	}
}
