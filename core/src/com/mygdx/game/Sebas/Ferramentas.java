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

/**
 * This class is to to provide tools to test the application
 *
 * @author Sebastián Cabanas 
 */

public class Ferramentas 
{
	
	// Name of the application to test
	private static final String LOG = "XOGO2D";
	       
	/**
	 * Method which print a log into the console log, showing the class where the log
	 * is called, the method and a message to show.
	 * 
	 * @param clase The class where this method is called.
	 * @param metodo The method where this method is called.
	 * @param mensaxe The message to show.
	 * @author Sebastian Cabanas
	 */
	public static void imprimirLog (String clase, String metodo, String mensaxe)
	{
		Gdx.app.log(LOG, clase + ":"+metodo+":"+mensaxe);
	}
	
}
