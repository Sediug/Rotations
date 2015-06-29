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
 * Define todo o que forma o noso xogo e definiremos o tamanho da nosa pantalla ou mundo (o xogo).
 *  
 */
public class Mundo {
	
    public static final int MUNDO_ANCHO=300;//Relacion 0.6 respecto 600x1000
    public static final int MUNDO_ALTO=500;
    
    
    private Bola bola;
    private Vector2 posicionBola = new Vector2(100,20); 
    private Vector2 tamanoBola =  new Vector2(10,10);
    private float velocidadeBola= 100;
    
    
    
    public Mundo(){
    	bola = new Bola(posicionBola, tamanoBola, velocidadeBola);
    	
    }
   
    
}
