/*CPS-731 A2 Track Class - Track, Responsible for calculating the times on which the train is coming
 * Author:Jonathan Kwan
 * 11/27/12-
 * v 0.0.1 Initial Build
 * v 0.0.2 Comments + Accesor Method for Train
 * 12/3/12 
 * v 0.1.2 Changed to 1 Hobo Per track
 * v 0.2.2 Removed Train Class
 * 12/4/2012
 * v 0.3.2 added Draw method to display track
 * v 0.3.3 removed tNum variable..not needed
 * v 0.3.4 removed HNum from constructor
 * v 0.3.5 Bug 1: Problems with rendering of track by y variable
 * v 0.4.5 Fixed 1: Problem Caused in GameComponent, height of window 
 * declared as 1024 instead of 700
 * v 0.4.6 Bug 2: Picture display issues
 * v 0.5.6 Fixed 2: Added a picture height variable to class and constructor
 * v 0.6.6 Added spacing varaible
 * to properly display tracks
 * v 0.7.6 Stable Build
 * v 0.7.7 Added Method for Y position of Track
 * to use in drawing player sprite
 * 12/5/2012
 * v 0.7.8 Added thisHobo for access by game logic
 * v 0.8.8 Readded Train Class
 * v 0.8.9 Added ontrack boolean value to decide 
 * whether the train should be displayed
 * v 0.9.0 Passed spacing value to train class for graphics usage
 * v 0.9.1 Added inheritance for Thread
 * v 0.9.2 Timing Issues Using Thread, removed
 * v 1.0.2 Implements Runnable added to take advantage of 
 * run() method running asynchronously
 * v 1.1.2 Setup run method to sleep for the time the next train is due in
 * v 1.2.2 Error of setup of run() method fixed
 * v 1.2.3 Added NewThread() method to properly setup run method
 * v 1.2.4 Added OnTrack() to determine if the user is on the track, and whether the train should be painted
 * v 1.2.5 Added ThisTrain() For access purposes by the main game thread
 *12/6/2012
 * v.1.3.6 Added infinite for loop,taken from GameComponent for logic reasons
 * to ensure that the Train's are randomly generated during the life of the game
 * v 1.3.7 Moved drawTrain() call to loop inside run for graphics issues
 * v 1.3.8 Bug 1:Trains not rendering correctly, too fast and flickers
 * v 1.3.9 Fix 1: Added Some println statements for Console Testing, left
 * there since the additional time taking, made graphics render better
 * v 1.4.9 Stable Build
 * v 1.5.2 Added Player variable and Player for access by Train
 * v 1.5.3 Added boolean message value for displaying Hobo value
 * v 1.6.3 Use robot to send message to component to repaint();
 * v 1.6.4 Added a 1s wait so user can see message, doesn't matter since, 
 * there's a 50-50 chance of the information being useful
 * v 1.6.5 Stable Build
 * v 1.7.5 Final
 */
package hogwartsNHobos;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Track implements Runnable{
  private Hobo tHobo;
	public BufferedImage image;
	private final int y;
	private final int space;
	private Train tTrack;
	private boolean onTrack;
	private final Player p;
	private boolean message;
	public enum Distribution {Poisson,Normal};
	//Constructs a track with the given: track number, time intervals and the number of hobos
	//Implementation pending hobos may be done some where else
	public Track(int y, int tNum,int spc, int l0, int l1,Distribution type,Player p){
		tHobo = new Hobo(tNum);
		this.y = y;
		space = spc;
		this.p = p;
		//Added for Drawing the image of the track
		try {                
		      image = ImageIO.read(new File("C://Resources//track.jpg"));
		}catch (IOException ex) {
		            // handle exception...
				System.out.println("Image not found!!");
		}
		//Setup the train for the track
		tTrack = new Train(l0,l1,y,type,spc,p);
		onTrack = false;
	}
	public int yPos(){
		return y;
	}
	public Hobo ThisHobo(){
		return tHobo;
	}
	public Train ThisTrain(){
		return tTrack;
	}
	public void setOn(boolean change){
		onTrack = change;
	}
	public void NewThread(){
		Thread t = new Thread(this,"Train track");
		t.start();
	}
	public boolean onTrack(){
		return onTrack;
	}
	public Player getPlayer(){
		return p;
	}
	public void run(){
		for(;;){
			message = false;
			try {
				TimeUnit.SECONDS.sleep(tTrack.moveTrain());
			}catch (InterruptedException e) {
				System.out.println("Train Timer Error");
			}
			message = true;
			Robot robot;
			try {
				robot = new Robot();
				//The keylistener for this only refreshes!!
				robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
				Thread.sleep(1500);
			} catch (AWTException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tTrack.drawTrain();
		}
	}
	public void draw(Graphics g){
		g.drawImage(image, 0, y ,1024,space, null);
		if(message)
			tHobo.draw(g, tHobo.Quote(), y, space);
	}
}
