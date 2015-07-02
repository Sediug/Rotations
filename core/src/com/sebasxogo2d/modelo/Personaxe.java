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
 * A character with a position in the screen, a speed and a size.
 *
 * @author Sebastián Cabanas 
 * @version 1.5
 * */

public abstract class Personaxe 
{

        /**
         * Max. Speed when it's moving
         */
        public float velocidade_max;

        /**
         * Speed
         */
        protected float velocidade = 0;

        /**
         * Position
         */
        protected Vector2 posicion;

        /**
         * Size of the character
         */
        protected Vector2 tamano;
 

        /**
         * Default constructor
         */
        public Personaxe () {}

        /**
         * Instance a new character with a size, position in the screen and max. speed
         *
         * @param posicion The position in the screen
         * @param tamanho The size of the character
         * @param velocidade_max Max. Speed
         */
        public Personaxe (Vector2 posicion, Vector2 tamano, float velocidade_max) 
        {
                this.posicion = posicion;
                this.tamano = tamano;
                this.velocidade_max = velocidade_max;
        }
 
       
 
        /**
         * Get the position of the character
         * @return A Vector2 object with the position
         */
        public Vector2 getPosicion () 
        {
                return posicion;
        }
 
        /**
         * Set a new position to the character using a Vector2 object
         * @param posicion The new position
         */
        public void setPosicion (Vector2 posicion) 
        {
                this.posicion = posicion;
        }
 

        /**
         * Set a new position to the character using the coordinates x and y
         *
         * @param x New position in the axis X
         * @param y new position in the axis Y
         */
        public void setPosicion (float x, float y) 
        {
                posicion.x = x;
                posicion.y = y;
        }
 

        /**
         * Set the speed of the character
         * @param velocidade The speed of the character
         */
        public void setVelocidade (float velocidade) 
        {
                this.velocidade = velocidade;
        }
 

        /**
         * Get the speed of the character 
         * @return velocidade The speed
         */
        public float getVelocidade () 
        {
                return velocidade;
        }
 

        /**
         * Get the size of the character
         * @return tamano Size
         */
        public Vector2 getTamano () 
        {
                return tamano;
        }
        
 
        /**
         * Set the size of the character
         *
         * @param width The new width of the character
         * @param height The new height of the character
         */
        public void setTamano(float width, float height) 
        {
                this.tamano.set(width,height);
        }
       
        /**
         * Updates the position depending on the speed
         * @param delta Time between a call and another call to the update.
         */
        public abstract void update(float delta);
}