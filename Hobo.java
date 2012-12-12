/*CPS-731 A2 hobo Class - Will Allow Hobos to give messages about the track they are on
 * using a predefined set of quotes and a RNG 
 * Author:Jonathan Kwan
 * 11/14/12- v 0.0.1 Initial Build
 * v 0.0.2 Array of Quotes added, dependant on value passed in
 * v 0.0.3 Use of RNG to give a message, may be wrong though!!
 * v 0.0.4 random quote selection method
 * 12/2/2010 v 0.1.3  Changed to only 3 Messages Per Hobo
 *  v 0.2.3 will only have 1 of 3 quotes being displayed
 *  v 0.2.4 Added System time as seed for RNG
 *  v 0.3.4 Finished Quotes
 *  v 0.3.5 Changed Constructor to take track number to 
 *  associate Hobo with track number
 *  v 0.4.5 Stable Build
 *  v 0.5.5 change number of quotes to 6,half meaning full, half blank
 *  v 0.6.5 Added draw method to print Hobo messages
 *  v 0.7.5 Stable Build
 *  v 0.8.5 Final
 */
package hogwartsNHobos;

import java.awt.Graphics;
import java.util.Random;
public class Hobo {
  private String[] quotes;
	private Random rng;
	private final int numQuotes = 6;
	public Hobo(int track){
		//Ensure Seed number is always different 
		//ensures unique sequence everytime
		rng = new Random(System.currentTimeMillis());
		quotes = new String[numQuotes];
		quotes[0]="Train is coming on track "+ track +" !";
		quotes[1]="";
		quotes[2]="Track "+ track +" is empty!";
		quotes[3]="";
		quotes[4]="Train is coming soon on track " + track +" !";
		quotes[5]= "";
	}
	public String Quote(){
		int n = rng.nextInt(quotes.length-1);
		return quotes[n];
	}
	public void draw(Graphics g,String q,int y, int space){
		g.drawString(q, y-space, 100);
	}
		
}
