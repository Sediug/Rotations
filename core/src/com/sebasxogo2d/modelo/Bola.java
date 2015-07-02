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
 * A ball which moves around the screem. Inherits from the class 'Personaxe' 
 * (character of the game)
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public class Bola extends Personaxe {
	
	/**
	 * Constructor which sets the values in the parameters to the properties 
	 * inherited from the father class. 
	 *
	 * @param posicion A Vector2 object which indicates what is the position of the ball
	 * @param tamano A Vector2 object which indicates the size of the ball. Vector2(width,height)
	 * @param velocidade_max Max. speed of the ball (moving around the screem). It increases as 
	 * levels rise
	 */
	public Bola(Vector2 posicion, Vector2 tamano, float velocidade_max) {
        super(posicion, tamano, velocidade_max);
	}
	
	@Override
	public void update(float delta) {
		
	}

}
