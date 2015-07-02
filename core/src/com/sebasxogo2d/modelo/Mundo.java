package com.sebasxogo2d.modelo;

import com.badlogic.gdx.math.Vector2;

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
 * The world where the game is played. In the beginning I was thinking to do other kind
 * of game, It's the reason of this class.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */
public class Mundo {
	
    /**
     * The width (MUNDO_ANCHO) and the height (MUNDO_ALTO) of the world (screen)
     */
    public static final int MUNDO_ANCHO=300;
    public static final int MUNDO_ALTO=500;
    
    // A ball
    private Bola bola;
    
    
    /**
     * Instance a new world to the game with a size and a ball.
     *
     */
    public Mundo()
    {
        Vector2 posicionBola = new Vector2(100,20);
        Vector2 tamanoBola =  new Vector2(10,10);
        float velocidadeBola= 100;
    	bola = new Bola(posicionBola, tamanoBola, velocidadeBola);	
    }
   
    
}
