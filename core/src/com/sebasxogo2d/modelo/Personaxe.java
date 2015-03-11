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
 * @author Sebastian Cabanas 
 * */

public abstract class Personaxe {
 
        /**
         * Constructor por defecto
         */
        public Personaxe(){
               
        }
        /**
         * Instancia unha personaxe
         *
         * @param posicion
         * @param tamanho
         * @param velocidade_max
         */
        public Personaxe(Vector2 posicion, Vector2 tamano, float velocidade_max) {
                this.posicion = posicion;
                this.tamano = tamano;
                this.velocidade_max = velocidade_max;
        }
 
        /**
         * Velocidade que toma cando se move.
         */
        public float velocidade_max;
        /**
         * Velocidade actual
         */
        protected float velocidade = 0;
        /**
         * Posicion
         */
        protected Vector2 posicion;
        /**
         * Tamanho
         */
        protected Vector2 tamano;
 
        /**
         * Devolve a posicion
         * @return posicion
         */
        public Vector2 getPosicion() {
                return posicion;
        }
 
        /**
         * Modifica a posicion
         * @param posicion: a nova posicion
         */
        public void setPosicion(Vector2 posicion) {
                this.posicion = posicion;
        }
 
        /**
         * Modifica a posicion
         *
         * @param x: nova posicion x
         * @param y: nova posicion y
         */
        public void setPosicion(float x, float y) {
                posicion.x = x;
                posicion.y = y;
        }
 
        /**
         * Modifica a velocidade
         * @param velocidade: a nova velocidade
         */
        public void setVelocidade(float velocidade) {
                this.velocidade = velocidade;
        }
 
        /**
         * Devolve a velocidade
         * @return velocidade
         */
        public float getVelocidade() {
                return velocidade;
        }
 
        /**
         * Devolve o tamanho
         * @return tamano
         */
        public Vector2 getTamano() {
                return tamano;
        }
        
 
        /**
         * Modifica o tamano
         *
         * @param width: novo tamano de ancho
         * @param height: novo tamano de alto
         */
        public void setTamano(float width, float height) {
                this.tamano.set(width,height);
        }
       
        /**
         * Actualiza a posicion en funcion da velocidade
         * @param delta: tempo entre unha chamada e a seguinte
         */
        public abstract void update(float delta);
}