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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class is used to load all the assets of the game (textures, sounds...) and dispose it.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class AssetsXogo 
{

	// Create the properties to load the assets.

	// Ball animation 
	public static Texture textureBola;
	public static TextureRegion[][] tmpBola;
	public static Animation animBola;

	// Padle texture
	public static Texture texturePaleta;

	// Backgrounds
	public static Texture textureFondo;
	public static Texture textureOptions, textureOptions2;

	// Lives
	public static Texture vidas;

	// Buttons
	public static Texture play, quit, hightscores, options, back;
	public static Texture exit, pause, soundOn, soundOff;

	// Game Over
	public static Texture over;

	// Sound and background music
	public static Music musica;
	public static Music musicaP;
	public static Sound sonSelect, sonBack;

	// Head animation using a TextureAtlas and sub regions.
	private static TextureAtlas aMonte;
	public static TextureRegion monte1, monte2, monte3, monte4, monte5, monte6,
								monte7, monte8, monte9, monte10, monte11, monte12;
	public static TextureRegion[] framesAnimMonte;
	public static Animation animMonte;

	// 321 GO Levels etc
	public static Texture _1, _2, _3, go, level, _4, _5, _6, _7;

	// Gesture
	public static Texture izquierdo, derecho;

	/**
	 * Method wich load the textures. When the game is turned on then you need to load
	 * all the textures that you will need to use.
	 *
	 * @author Sebastián Cabanas
	 */
	public static void cargarTexturas() 
	{

		// Texture anim ball
		textureBola = new Texture(Gdx.files.internal("sprites.png"));

		// Head textures
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

		// Background textures
		textureFondo = new Texture(Gdx.files.internal("bg.png"));

		// Padle texture
		texturePaleta = new Texture(Gdx.files.internal("paleta.png"));

		// Hearts texture (lives)
		vidas = new Texture(Gdx.files.internal("Hearts_01_64x64_017.png"));

		// Main screen button textures
		play = new Texture(Gdx.files.internal("Play_Blue.png"));
		quit = new Texture(Gdx.files.internal("Quit_blue.png"));
		hightscores = new Texture(Gdx.files.internal("HighScores_blue.png"));
		options = new Texture(Gdx.files.internal("Options_blue.png"));

		// Game button textures
		exit = new Texture(Gdx.files.internal("exit.png"));
		pause = new Texture(Gdx.files.internal("pause.png"));
		soundOn = new Texture(Gdx.files.internal("SoundOn.png"));
		soundOff = new Texture(Gdx.files.internal("SoundOff.png"));
		back = new Texture(Gdx.files.internal("back.png"));

		// Options screen textures
		textureOptions = new Texture(Gdx.files.internal("PantallaOptions.png"));
		textureOptions2 = new Texture(Gdx.files.internal("PantallaOptions2.png"));

		// Game Over texture.
		over = new Texture(Gdx.files.internal("gameover.png"));

		// Background music (Game screen and Main screen)
		musica = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		musicaP = Gdx.audio.newMusic(Gdx.files.internal("sound.ogg"));

		// Miscellaneous sounds
		sonSelect = Gdx.audio.newSound(Gdx.files.internal("MENU A_Select.ogg"));
		sonBack = Gdx.audio.newSound(Gdx.files.internal("MENU A - Back.ogg"));

		// Texture of -> 3 2 1 GO (e.g. Level 1, Level 2... )!
		_3 = new Texture(Gdx.files.internal("321go/3.png"));
		_2 = new Texture(Gdx.files.internal("321go/2.png"));
		_1 = new Texture(Gdx.files.internal("321go/1.png"));
		go = new Texture(Gdx.files.internal("321go/go.png"));
		level = new Texture(Gdx.files.internal("321go/level.png"));
		_4 = new Texture(Gdx.files.internal("321go/4.png"));
		_5 = new Texture(Gdx.files.internal("321go/5.png"));
		_6 = new Texture(Gdx.files.internal("321go/6.png"));
		_7 = new Texture(Gdx.files.internal("321go/7.png"));

		// Gesture textures to move the padle
		izquierdo = new Texture(Gdx.files.internal("irquierda2.png"));
		derecho = new Texture(Gdx.files.internal("derecha2.png"));

		// Create the animations
		crearAnimacions();

	}

	/**
	 * Method that create and array of TextureRegion and add all the textures
	 * of the head (ball) into the array, saved in the property franesAnimMonte.
	 *
	 * @author Sebastián Cabanas
	 */
	private static void cargarArrayAnim () 
	{
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

	/**
	 * Method that generate the animations of the ball and the head. We can use a head
	 * or a ball in the game (used as a animated ball).
	 *
	 * @author Sebastián Cabanas
	 */
	private static void crearAnimacions () 
	{

		/* 
		 * Using an sprite with diferent printed balls. We are geting each part
		 * of the sprite and adding it into a TextureRegion array.
		 */
		int num_columnas; // columns 
		int num_filas; // rows
		TextureRegion[] framesanimacion;

		/* 
		 * TmpBola is an Matrix with 2 dimensions ([][]), we are adding each part 
		 * of the sprite with the ball textures. 
		 * Height: 21 and Width: 21
		 */
		tmpBola = TextureRegion.split(textureBola, 21, 21);

		/*
		 * Now we need to transform the matrix of 2 dimensions into an array of a 
		 * single dimension to work with it.
		 * 12 columns and 1 row, we have 12 balls in the sprite.
		 */
		num_columnas = 12;
		num_filas = 1;
		framesanimacion = new TextureRegion[num_columnas * num_filas];

		for (int fila = 0; fila < num_filas; fila++) {
			for (int col = 0; col < num_columnas; col++) {
				framesanimacion[fila * num_columnas + col] = tmpBola[fila][col];
			}
		}

		/**
		 * Create the animations of the ball and the head.
		 * The first parameter define how much time will wait the program to change
		 * from a texture to the next texture and the second is the array of Textures.
		 */
		animBola = new Animation(1f, framesanimacion);

		animMonte = new Animation(0.01f, framesAnimMonte);
	}

	/**
	 * Method which dispose all the assets of the game. When the user turn off the game
	 * we need to dispose all textures and sounds.
	 *
	 *@author Sebastián Cabanas
	 */

	public static void liberarTexturas () 
	{
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
