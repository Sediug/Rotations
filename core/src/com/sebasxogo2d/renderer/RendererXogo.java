package com.sebasxogo2d.renderer;

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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Sebas.AssetsXogo;
import com.sebasxogo2d.modelo.Mundo;
import com.sebasxogo2d.pantallas.PantallaXogo;


public class RendererXogo {
	
	/*Esta clase e a que vai a debuxar todos os elementos graficos do xogo. 
	*/
	private final boolean ANDROID = Gdx.app.getType().name().equalsIgnoreCase("android");
	
	//Variables 
	public static OrthographicCamera camara2d;
	private SpriteBatch batch;
	private float crono;
	private BitmapFont bitMapFont;
	
	//Puntuacion
	private int puntuacionTotal;
	private int level;
	private int pEasy;
	private int pNormal;
	private int pKappa;
	
	// Vidas
	private float alturaVidas = 483f , tamanoVidas = 15f;
	private int vidas;

	// Bola
	private float velocidadeBola;
	private int cambioXbola = 1, cambioYbola = 1;//Sumatorios que o multiplicar por -1 cambian direccion e sentido.
	private int cambioX2bola = 1, cambioY2bola = 1;
	private int pointParaBola2 = 1000;
	private boolean isBola2Enabled;
	private final float altoBola = 40f  ,anchoBola = 40f;
	
	
	// Paleta
	public static float altoPaleta = 12f;
	public static float anchoPaleta = 46f;
	
	// Botons
	public static final float Y_BOTONS = 466f;
	public static final float X_SOUND = 208f, X_PAUSE = 239f, X_EXIT =270f;
	public static final float ANCHO_BOTONS = 27f, ALTO_BOTTONS = 30f;
	private boolean son;

	// XY Bolas, pala.
	public float posXbola = 100, posYbola = 100;
	public float posXpala, posYpala;
	public float posX2bola = 150, posY2bola = 390;

	// Boolean pintar Game Over.
	private boolean gameOver = false;
	
	//Colisions
	private Rectangle paleta, rectBola, rectBola2, norte, oeste, este;
	private final float ANCHO_COLISION = 5f;
	
	//Sonido
	private Sound plin;
	
	//Correndo a app.
	private boolean running;
	private final float ANCHO_RUN = 80, ANCHO_RUN_GO = 170;
	private final float ALTO_RUN = 47, ALTO_RUN_GO=55;
	
	//Levels
	private boolean subirLevels; 
	private int seg, idLevel;
	
	//Botons mover.
	private Rectangle izquierdo;
	private Rectangle derecho;
	private boolean move;
	
	// Constructor sin param
    public RendererXogo(Mundo mundo){
    	
    	inicioBola();//Direccion aleatorio no eixo das X.
    	inicioBola2();
    	
		batch = new SpriteBatch();
		camara2d = new OrthographicCamera();

		bitMapFont = new BitmapFont();
		bitMapFont.setColor(Color.BLACK);
		bitMapFont.setScale(1f, 1f);
		
		vidas = 5;
		gameOver = false;
		son = true;
		
		
		paleta = new Rectangle();
    	rectBola = new Rectangle();
    	rectBola2 = new Rectangle();
    	
    	//Espacions de colision, fin da pantalla de xogo.
    	norte = new Rectangle(0,Mundo.MUNDO_ALTO - ANCHO_COLISION,Mundo.MUNDO_ANCHO,ANCHO_COLISION);//De 0x, y= alto -5 a largoPantalla
    	oeste = new Rectangle(0,0,ANCHO_COLISION,Mundo.MUNDO_ALTO);
    	este = new Rectangle(Mundo.MUNDO_ANCHO - ANCHO_COLISION,0, ANCHO_COLISION, Mundo.MUNDO_ALTO);
    	
    	
    	plin = Gdx.audio.newSound(Gdx.files.internal("MENU B_Back.wav"));
    	
    	//Puntuacion a 0 o iniciar o xogo.
    	puntuacionTotal = 0;

    	//Nivel seleccionado.
    	
    	level = PantallaXogo.levelInteger;
    	asignarVelocidade(level);
    	
    	//Empezar a funcionar a app cando fagan click na pantalla.
    	running = false;
    	
    	//Puntos
    	pEasy = 34;
    	pNormal = 82;
    	pKappa = 139;
    	
    	//Levels
    	isBola2Enabled = false;
    	subirLevels= false;
    	idLevel = 2;
    	velocidadeBola = 400f;
    	
    	//Izquierdo y Derecho
    	izquierdo = new Rectangle(0, 50,100,100);
    	derecho = new Rectangle(260, 50,100,100);
    	move = true;
    }
    
    public Rectangle getIz(){
    	return izquierdo;
    }
    public Rectangle getDerecho(){
    	return derecho;
    }
    
    public void inicioBola(){
    	double aleatorio =  Math.random();
    	if(aleatorio < 0.5){
    		aleatorio = -1;
    	}
    	else aleatorio = 1;
    	cambioXbola = (int) aleatorio;
    	

    }
    public void inicioBola2(){
    	double aleatorio =  Math.random();
    	if(aleatorio < 0.5){
    		aleatorio = -1;
    	}
    	else aleatorio = 1;
    	cambioX2bola = (int) aleatorio;
    }

    public void setRuning(boolean estado){
    	running = estado;
    }
    
	private void asignarVelocidade(int nivel) {
		switch (nivel) {
		case 1:
			velocidadeBola = 250f;
			break;
			
		case 2:
			velocidadeBola = 350f;
			break;
			
		case 3:
			velocidadeBola = 500f;
			break;
		}
	}
    
    //Setters e Getters
    public int getVidas(){
    	return vidas;
    }
    
    public void setOver(boolean over){
    	 gameOver = over;//Pintar Game Over.
    }
    
    public boolean getSoundState(){
    	return son;
    }
    public void setSoundState(boolean estado){
    	son = estado;
    }
    
    public void changeSound (){
    	if(son){
    		son = false;// Invertir estado.
    	}
    	else{
    		son = true;
    	}
    }
    
    public void setCambioBolaY(int cambio){
    	this.cambioYbola = cambio;
    }
    
    public int getCambioBolaY(){
    	return cambioYbola;
    }
    public void setCambioBolaY2(int cambio){
    	this.cambioY2bola = cambio;
    }
    
    public int getCambioBolaY2(){
    	return cambioY2bola;
    }
    
    public Vector2 getPosBola(){
    	return new Vector2(posXbola,posYbola);
    }
    
    public Vector2 getPosBola2(){
    	return new Vector2(posX2bola,posY2bola);
    }
    
	public void setPosicionPala(float f){
		posXpala = f;
	}
	
	public Vector2 getPosicionPala(){
		return new Vector2(posXpala,posYpala);
	}
    
	public void setCambioBolaX2(int cambio){
    	this.cambioX2bola = cambio;
    }
    
    public int getCambioBolaX2(){
    	return cambioX2bola;
    }
    
	public void setCambioBolaX(int cambio){
    	this.cambioXbola = cambio;
    }
    
    public int getCambioBolaX(){
    	return cambioXbola;
    }
	
    public int getPuntuacionTotal(){
    	return puntuacionTotal;
    }
    
    public float getCrono(){
    	return crono;
    }
    
    public void setVelocidade(float velocidade){
    	velocidadeBola = velocidade;
    }
    
    public float getVelocidade(){
    	return velocidadeBola;
    }
	
    public void addVelocidadeBola(float aumento){
    	velocidadeBola += aumento;
    }
    
    public void addPuntos(int puntos, int dificultad){
    	switch (dificultad) {
			case 1:
				pEasy += puntos;
				break;
	
			case 2:
				pNormal += puntos;
				break;
			case 3:
				pKappa += puntos;
				break;
		}
    }
    
    public void setBola2(boolean estado){
    	if(puntuacionTotal > pointParaBola2){
    		isBola2Enabled = estado;
    	}	
    }
    
    public void setLevelUP(boolean estado){
    	subirLevels = estado;
    }
    
	/**
     * Debuxa todos os elementos graficos da pantalla
     * @param delta: tempo que pasa entre un frame e o seguinte.
     */   
    public void render(float delta){
    	Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		crono+=Gdx.graphics.getDeltaTime();
		
		if(running){
			if(subirLevels){//Cando subes level non moves bola.				
				if(crono > seg+3){
					idLevel++;
					subirLevels = false;	
				}

			}else{
				seg = (int) crono;
				moverBola(Gdx.graphics.getDeltaTime() * velocidadeBola);
			}
			
		}
		
		batch.begin();
			if(running){//Cando xa clikearon a pantalla
				
				if(!gameOver){//Si non sucede game over pintamos todo e se non pintamos solo GAME OVER.
					debuxarFondo();
					comprobarColision(delta);
					debuxarAnimacions();
					debuxarPala();
					debuxarVidas();	
					debuxarBotons(son);
					debuxarLevelUP();
					botonesDireccion();
					
				}else{//Perder --> vidas < 5
					debuxarFondo();
					fin();//Solo o pinta se perdemos.
				}	
				
			}else{ //Esperando a que fagan click na pantalla.
				debuxarFondo();
				debuxarBotons(son);
				debuxarPala();
				debuxarVidas();
				comprobarColision(delta);
				botonesDireccion();
				run();
			}
		batch.end();

		
    }

	public void botonesDireccion() {
		if(ANDROID)batch.draw(AssetsXogo.izquierdo, 0, 50,40,40);
		if(ANDROID)batch.draw(AssetsXogo.derecho, camara2d.viewportWidth-40, 50,40,40);
	}

	public void debuxarLevelUP() {
		float centrarX = 25;
		float centrarY = camara2d.viewportHeight/2 +30;

		if(subirLevels){
			batch.draw(AssetsXogo.level, centrarX, centrarY,ANCHO_RUN_GO,ALTO_RUN_GO);
			switch (idLevel) {
				case 1:
					batch.draw(AssetsXogo._1, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 2:
					batch.draw(AssetsXogo._2, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 3:
					batch.draw(AssetsXogo._3, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 4:
					batch.draw(AssetsXogo._4, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 5:
					batch.draw(AssetsXogo._5, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 6:
					batch.draw(AssetsXogo._6, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
				case 7:
					batch.draw(AssetsXogo._7, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
					break;
			default:
				batch.draw(AssetsXogo._7, centrarX + ANCHO_RUN + 100, centrarY,ANCHO_RUN_GO/2,ALTO_RUN_GO);
				break;
			}
			
		}
	}

	private void run() {
		float centrarX = camara2d.viewportWidth/2 - ANCHO_RUN/2;
		float centrarXGo = camara2d.viewportWidth/2 - ANCHO_RUN_GO/2;
		float centrarY = camara2d.viewportHeight/2;
		switch ((int)crono) {
			case 0:
				batch.draw(AssetsXogo._3, centrarX, centrarY,ANCHO_RUN,ALTO_RUN);
				break;
	
			case 1:
				batch.draw(AssetsXogo._2, centrarX, centrarY,ANCHO_RUN,ALTO_RUN);
				break;
			case 2:
				batch.draw(AssetsXogo._1, centrarX, centrarY,ANCHO_RUN,ALTO_RUN);
				break;
			case 3:
				batch.draw(AssetsXogo.go, centrarXGo, centrarY,ANCHO_RUN_GO,ALTO_RUN_GO);
				break;
		}
		
	}

    public void resize(int width, int height) {
    	camara2d.setToOrtho(false,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);//Asignamos o ancho e o alto do mundo (xogo) e que se conte dende abaizo esquerda (false).
        
    	posXpala = camara2d.viewportWidth/2;
        posYpala = 25f;
        
        
    	camara2d.update();
        batch.setProjectionMatrix(camara2d.combined);
    }
    
    public void dispose(){
    	//Borramos do buffer da grafica o que xa non vaiamos usar.
    	batch.dispose();
    	bitMapFont.dispose();
    }
    
   
    //Debuxar elementos.
    private void fin(){
    	batch.draw(AssetsXogo.over, 25, camara2d.viewportHeight/2 +50f,250,47);	
    }
    
	private void debuxarBotons(boolean sound) {
		if(sound){
			batch.draw(AssetsXogo.soundOn, X_SOUND, Y_BOTONS, ANCHO_BOTONS, ALTO_BOTTONS);
		}else{
			batch.draw(AssetsXogo.soundOff, X_SOUND, Y_BOTONS, ANCHO_BOTONS, ALTO_BOTTONS);
		}
		batch.draw(AssetsXogo.pause, X_PAUSE, Y_BOTONS, ANCHO_BOTONS, ALTO_BOTTONS);
		batch.draw(AssetsXogo.exit, X_EXIT, Y_BOTONS, ANCHO_BOTONS, ALTO_BOTTONS);
	}
    
	private void debuxarVidas() {
		switch (vidas) {
			case 1 :
				batch.draw(AssetsXogo.vidas, 6 + tamanoVidas*4, alturaVidas, tamanoVidas, tamanoVidas);
				break;
				
			case 2:
				batch.draw(AssetsXogo.vidas, 5 + tamanoVidas*3, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 6 + tamanoVidas*4, alturaVidas, tamanoVidas, tamanoVidas);		
				break;
				
			case 3:
				batch.draw(AssetsXogo.vidas, 4 + tamanoVidas*2, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 5 + tamanoVidas*3, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 6 + tamanoVidas*4, alturaVidas, tamanoVidas, tamanoVidas);				
				break;
				
			case 4:
				batch.draw(AssetsXogo.vidas, 3 + tamanoVidas, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 4 + tamanoVidas*2, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 5 + tamanoVidas*3, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 6 + tamanoVidas*4, alturaVidas, tamanoVidas, tamanoVidas);
				break;
			
			case 5:
				batch.draw(AssetsXogo.vidas, 2, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 3 + tamanoVidas, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 4 + tamanoVidas*2, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 5 + tamanoVidas*3, alturaVidas, tamanoVidas, tamanoVidas);
				batch.draw(AssetsXogo.vidas, 6 + tamanoVidas*4, alturaVidas, tamanoVidas, tamanoVidas);
				break;
			default:
				//Non se ve nada
				break;
		}
			
		
	}
	
    private void debuxarFondo(){
        batch.draw(AssetsXogo.textureFondo,0,0,Mundo.MUNDO_ANCHO,Mundo.MUNDO_ALTO);
    }

	private void debuxarPala() {
		batch.draw(AssetsXogo.texturePaleta, posXpala, posYpala, anchoPaleta, altoPaleta);
	}

    private void debuxarAnimacions() {
		batch.draw(AssetsXogo.animMonte.getKeyFrame(crono, true),posXbola, posYbola, anchoBola, altoBola);
		if(puntuacionTotal > pointParaBola2){
			batch.draw(AssetsXogo.animMonte.getKeyFrame(crono, true),posX2bola, posY2bola, anchoBola, altoBola);
		}
	}
    
    private void moverBola(float delta){
    	posXbola = cambioXbola * delta + posXbola;
    	posYbola = cambioYbola * delta + posYbola;
    	
    	if(puntuacionTotal > pointParaBola2){//Segunda bola
			posX2bola = cambioX2bola * delta + posX2bola;
			posY2bola = cambioY2bola * delta + posY2bola;
    	}
    	
    	//Comprobar que perdemos vida.
    	if(posYbola <= -20){
			vidas = vidas -1;
			reinicio();
		}
    	if( posY2bola <= -20){
    		vidas = vidas -1;
			reinicioB2();
    	}
    	
    	excesoLimite();
    	excesoLimiteB2();
    	
    }

	private boolean excesoLimite() {
		
		//??Si en algun momento pasa dos limites da pantalla por algun motivo. -1 Para que colisione e cambie direccion. 
    	if(posXbola < oeste.x ){
    		//anchoBola -= factorCompresion;
    		posXbola = oeste.x;
    		invertirDireccionX();
    		return true;
    	}
    	else if(posXbola > este.x - anchoBola){
    		//anchoBola -= factorCompresion;
    		posXbola = este.x - anchoBola ;
    		invertirDireccionX();
    		return true;
    	}
    	
    	if(posYbola > norte.y - altoBola){
    		posYbola = norte.y - altoBola -10;
    		invertirDireccionY();
    		return true;
    	}
    	
    	return false;
	}
	
	private boolean excesoLimiteB2() {
		
		//??Si en algun momento pasa dos limites da pantalla por algun motivo. -1 Para que colisione e cambie direccion. 
    	if(posX2bola < oeste.x ){
    		//ancho2Bola -= factorCompresion;
    		posX2bola = oeste.x;
    		invertirDireccionX2();
    		return true;
    	}
    	else if(posX2bola > este.x - anchoBola){
    		//ancho2Bola -= factorCompresion;
    		posX2bola = este.x - anchoBola ;
    		invertirDireccionX2();
    		return true;
    	}
    	
    	if(posY2bola > norte.y - altoBola){
    		posY2bola = norte.y - altoBola -10;
    		invertirDireccionY2();
    		return true;
    	}
    	
    	return false;
	}

	private void invertirDireccionY() {
		setCambioBolaY(getCambioBolaY() * -1);
	}

	private void invertirDireccionX() {
		setCambioBolaX(getCambioBolaX() * -1);
	}
	
	private void invertirDireccionY2() {
		setCambioBolaY2(getCambioBolaY2() * -1);
	}

	private void invertirDireccionX2() {
		setCambioBolaX2(getCambioBolaX2() * -1);
	}
    	
    private void reinicio(){
    	posXbola = camara2d.viewportWidth/2;
    	posYbola = camara2d.viewportHeight/2;
    	
    	cambioYbola = cambioYbola * -1 ;
    	
    }
    
    private void reinicioB2(){
    	if(puntuacionTotal > pointParaBola2){
    		posX2bola = camara2d.viewportWidth/2;
    		posY2bola = camara2d.viewportHeight-100;
    		
    		cambioY2bola = cambioY2bola * -1;
    	}
    }
    
    
    private void comprobarColision(float delta){
    	//Representacion rectangular da bola.
		rectBola.set(getPosBola().x,getPosBola().y,anchoBola, altoBola);
		rectBola2.set(getPosBola2().x,getPosBola2().y,anchoBola, altoBola);
		

	
		//Referencia a paleta.
		paleta.set(getPosicionPala().x,getPosicionPala().y,RendererXogo.anchoPaleta, RendererXogo.altoPaleta);
		
		//Bola vs Paleta.
		Rectangle bolaPal = new Rectangle(getPosBola().x + anchoBola/2 - 3f, getPosBola().y, 3f, altoBola);
		Rectangle bolaPal2 = new Rectangle(getPosBola2().x + anchoBola/2 - 3f, getPosBola2().y, 3f, altoBola);
		
		//Comprobar colision entre bolas.
		if(isBola2Enabled){	
			if(Intersector.overlaps(bolaPal, bolaPal2)){
				invertirDireccionY();
				invertirDireccionX();
				
				invertirDireccionX2();
				invertirDireccionY2();
			}
		}
		
		//Comprobar	colision coa paleta.
		if (Intersector.overlaps(paleta, bolaPal)){
			if(posYbola < paleta.y + paleta.height){
	    		posYbola = paleta.y + paleta.height +1;
	    		invertirDireccionY();
	    		
			}else invertirDireccionY();
			
			if(son) plin.play(0.5f);
			
			//Sumar puntuación
			switch (level) {
			case 1:
				puntuacionTotal += pEasy;
				break;
			case 2:
				puntuacionTotal += pNormal;
				break;
			case 3:
				puntuacionTotal += pKappa;
				break;
				
			default:
				puntuacionTotal += pEasy;
				break;
			}
			
		}
		//Comprobar	colision coa paleta.
		if (Intersector.overlaps(paleta, bolaPal2)){
			if(posY2bola < paleta.y + paleta.height){
	    		posY2bola = paleta.y + paleta.height +1;
	    		invertirDireccionY2();
	    		
			}else invertirDireccionY2();
			
			if(son) plin.play(0.5f);
			
			//Sumar puntuación
			switch (level) {
			case 1:
				puntuacionTotal += pEasy;
				break;
			case 2:
				puntuacionTotal += pNormal;
				break;
			case 3:
				puntuacionTotal += pKappa;
				break;
				
			default:
				puntuacionTotal += pEasy;
				break;
			}
			
		}

		if(excesoLimite()){

			
			//Comprobar colisions coa parte superior, parte dereita e parte esquerda da pantalla.
			if(Intersector.overlaps(norte, rectBola)){
				invertirDireccionY();
			}
			
			if(Intersector.overlaps(norte, rectBola2)){
				invertirDireccionY2();
			}
			
			
			if(Intersector.overlaps(oeste, rectBola) || Intersector.overlaps(este, rectBola)){
				invertirDireccionX();
			}
			if(Intersector.overlaps(oeste, rectBola2) || Intersector.overlaps(este, rectBola2)){
				invertirDireccionX2();
			}
			
			//reiniciarTamanoBola();// Estou facendo que a bola reduza o ancho cando bate os lados. Toca reiniciar.
		}else {	
			//reiniciarTamanoBola();
		}	
		
		rangoPaleta();
    }

	private void rangoPaleta() {
		if (paleta.x < oeste.x){
			posXpala = oeste.x;
			 
		}
		if (paleta.x > Mundo.MUNDO_ANCHO - anchoPaleta ){
			posXpala = Mundo.MUNDO_ANCHO - anchoPaleta ;
		}
	}

	/*private void reiniciarTamanoBola() {
		//Definir un tempo no que se ve a bola deformada e despois ponhela o estado base 25x25.
		if(posXbola > oeste.x + 20f && posXbola < este.x -20f){
			anchoBola = anchoIncialBola;
			altoBola = altoInicialBola;
		}
	}*/
    
    
    public void moverPala(int id){
    	if(id == 2){
    		setPosicionPala(getPosicionPala().x -= 1000 * Gdx.graphics.getDeltaTime());
    	}else if(id == 3){
    		setPosicionPala(getPosicionPala().x += 1000 * Gdx.graphics.getDeltaTime());
    	}
    	
    }
    public void moverPala(int id, int keyLeft, int keyRight){
    	
    	if(Gdx.input.isKeyPressed(keyLeft)) {
    		move = false;
    		setPosicionPala(getPosicionPala().x -= 500 * Gdx.graphics.getDeltaTime());
    	}
    	
        if(Gdx.input.isKeyPressed(keyRight)){
        	move= false;
        	setPosicionPala(getPosicionPala().x += 500 * Gdx.graphics.getDeltaTime());
        }
        
        //Por defecto.
        if(move){
        	if(Gdx.input.isKeyPressed(Keys.LEFT))setPosicionPala(getPosicionPala().x -= 500 * Gdx.graphics.getDeltaTime());
            if(Gdx.input.isKeyPressed(Keys.RIGHT))setPosicionPala(getPosicionPala().x += 500 * Gdx.graphics.getDeltaTime());
        }
        
    }
    
	
    
	
}
