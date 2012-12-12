/*CPS-731 A2 Player Class - Keeps Track of the Player's Life and Selected Track
 * Handling movement will be done in the GUI logic
 * Author:Jonathan Kwan
 * 11/14/12
 * v0.0.1 Player Default Constructor and Variables
 * v 0.0.2 Additional Constructor for Custom Health, Starting track
 * v 0.1.2 Constructors and Variables done
 * v 0.2.2 Getters and Setters for Variables added 
 * v 0.3.2 Methods for decreasing the health, switching selected track and default
 *  and custom methods for scoring
 * v 0.4.2 Stable Build
 * 12/4/2012
 * v 0.5.2 Removed Default Constructor
 * v 0.6.2 Stable Build
 * v 0.6.3 Added draw method
 * v 0.6.4 Display the Player Name and score on the topleft Corner
 * v 0.6.5 Changed font, style and size for visibility
 * v 0.6.6 Added an Image to Represent the Player
 * v 0.6.7 Added a function to set they x and y coords of the player sprite
 * v 0.7.7 Stable Build
 * v 0.7.8 Added spacing variable to make character sprite fit on track
 * v 0.7.9 Used Spacing to make character sprite appear same Height as track
 * v 0.8.0 added getTrack() for use by keylisteners
 * 12/5/2012
 * v 0.8.1 Split, playername and score into 2 lines
 * v 0.9.1 Stable Build 
 * 12/6/2012
 * v 0.9.2 Added GetX() method...for scoring purposes
 * v 0.9.3 Changed signature of damaged() to take a custom damage amount
 * v 0.9.4 Modified draw to print user's health
 * v 0.9.5 Added setScore() for scoring usage
 * v 0.9.6 Adde GetY() for socring usage
 * v 0.9.7 Changed getx() to getspacing()
 * v 1.0.7 Stable Build
 * v 1.1.7 Final Build
 */
package hogwartsNHobos;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
  private int score;
	private int health;
	private int trackSelected;
	private int spacing;
	private String name;
	private BufferedImage image;
	private int x, y;
	
	//Overloaded Constructor Allowing for customization of health and/or
	//starting track due to difficulty etc...
	public Player(String pname,int sHealth, int sTrack, int s){
		name = pname;
		spacing = s;
		if(sHealth != 0)
			health = sHealth;
		else
			health = 100;
		
		if(sTrack >= 0)
			trackSelected = sTrack;
		else
			trackSelected = 0;
		
		score = 0;
		//Added for Drawing the image of the player
		try {                
				image = ImageIO.read(new File("C://Resources//player.png"));
		}catch (IOException ex) {
				 // handle exception...
				System.out.println("Image not found!!");
		}
	}
	//Returns a true or false after the damage counter is decremented
	//on a return of false, the game is over
	public boolean Damaged(int damage)
	{	
		health -= damage;
		if(health == 0 )
			return false;
		else
			return true;
	}
	//Returns true or false depending on the track you
	//chose, if false, you are currently on that track
	public boolean ChangeTrack(int newTrack){
		if(newTrack != trackSelected){
			trackSelected = newTrack;
			return true;
		}
		else
			return false;
	}
	
	public void setPost(int x ,int y){
		this.x = x;
		this.y = y;
	}
	public int GetSpacing(){
		return spacing;
	}
	public int GetY(){
		return y;
	}
	//Return the Players Name
	public String PlayerName(){
		return name;
	}
	//Get the Players Score
	public int PlayerScore(){
		return score;
	}
	//Increments the players score
	public void Score(){
		score++;
	}
	//Incrementes the players score by a certain amount
	//bonus, other scoring factors
	public void Score(int s){
		score = score + s;
	}
	public void setScore(int s){
		score = s;
	}
	public int getTrack(){
		return trackSelected;
	}
	public void draw(Graphics g){
		g.setFont(new Font("Times New Roman",Font.BOLD,22));
		g.drawString("Player: "+ name + " 	Health:" + health, 0, 20);
		g.drawString("Score: "+score, 0, 40);
		g.drawImage(image, x, y ,spacing,spacing, null); // see javadoc for more info on the parameters
    }
}
