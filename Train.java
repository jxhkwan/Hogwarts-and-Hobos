/*CPS-731 A2 Train Class-Renders the Train and determines when the train is changed and drawn
 * Author:Jonathan Kwan
 * 12/5/2012
 * v 2.0.1 Initial Build(v2)
 * v 2.0.2 Initial variables and constructor
 * v 2.0.3 Added spacing and y variables to constructor for graphics
 * v 2.0.4 Added ObjectDraw library for ActiveObject
 * v 2.0.5 Bug 1:Null Pointer Exception in ActiveObject.java:239 -Line doesn't actually exist
 * v 2.1.5 Fixed 1: Removed and Used Thread instead, does the same thing
 * v 2.2.5 Added Colt library from CERN for using Probability Distribution Functions
 * v 2.2.6 Swtch statement to decide which distribution to initialize
 * v 2.3.6 Added moveTrain() to determine the interval before the next train
 * for use by track.java
 * v 2.3.7 Bug 1:Trains not rendering correctly, too fast and flickers
 * v 2.4.7 Added logic to determine, when to redraw the train, 
 * determine x value of train
 * v 2.4.8 Added use of robot to send keypress events to Main 
 * Game Thread
 * v 2.4.9 Changed from Left key to scroll lock,problems in the JDE workspace
 * v 2.5.9 Determine how to round up, and full length needed for the train
 * to travel entirely off the screen, recalculated formula for train drawing logic 
 * trains location will be updated 60 times each second due to monitor freq: 60 mHz
 * v 2.6.0 moved train drwaing logic to drawTrain(), since keypress sent by Robot
 * is detected properly by main thread, and due to changes in Track.java
 * v 2.6.1 Fixed 1:Added println to help in debugging, left there for addition overhead
 * v 2.7.1 Stable Build
 * v 2.7.2 Adde GetX() method for scoring purposes
 * v 2.7.3 Added Player variable
 * v 2.7.4 Added new keypress sent when the train hits a user
 * v 2.7.5 Added boolean value bump to stop the damaged from repeating after user is hit car 
 * resets when train goes of the screen
 * v 2.8.5 Stable Build
 */
package hogwartsNHobos;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import cern.jet.random.*;
import cern.jet.random.engine.RandomEngine;

public class Train{
  private BufferedImage image;
	//Only instantiated when need by constructor
	private Poisson p;
	private Normal n;
	private char dist;
	//X Y Pos of train, y is constant
	private int x;
	private final int y;
	private final int spacing;
	private int tSched;
	private int tDur;
	private Player pl;
	private boolean bumped;
	public Train(int l0, int l1 ,int y,Track.Distribution d,int s,Player pl){
		//Creating the distribution by case
		this.pl = pl;
		switch(d){
			case Poisson:
				p = new Poisson(l0,RandomEngine.makeDefault());
				dist = 'p';
				break;
			case Normal:
				n = new Normal(l0,1.0,RandomEngine.makeDefault());
				dist = 'n';
		}
		try {                
		      image = ImageIO.read(new File("C://Resources//train.gif"));
		}catch (IOException ex) {
		            // handle exception...
				System.out.println("Image not found!!");
		}
		//Initial setup of y
		x = 1024;
		this.y = y;
		spacing = s;
		tDur = l1;
		tSched = l0;
	}
	//Return the X Variable
	public int getX(){
		return x;
	}
	//Does Exactly what it's suppose to do
	public int moveTrain(){
		
		if(dist == 'p')
			tSched = p.nextInt();
		else
			tSched = (int) n.nextDouble();
		return tSched;
	}
	//Values based around Image sizes and and frame size
	//4-5 Pages of Diagrams and Math
	public void drawTrain(){
		Robot robot;
		try {
			robot = new Robot();
			int cycles = 60*tDur;
			System.out.println("Train is Here");
			for(int i=0; i<=cycles;i++){
				robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
				//round up,otherwise won't print properly
				//formula found on google
				if(x <= pl.GetSpacing() && y == pl.GetY()&& bumped){
					bumped = false;
					robot.keyPress(KeyEvent.VK_INSERT);
				}
				x -= 1792/cycles+0.5;
			}
			bumped = true;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Train is Leaving");
		x = 1024;
	}
	public void draw(Graphics g){
		g.drawImage(image,x,y,768,spacing, null);
    }
}
