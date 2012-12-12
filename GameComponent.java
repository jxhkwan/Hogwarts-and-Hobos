/*CPS-731 A2 GameComponent Class - Object Responsible for Game Play
 * Author:Jonathan Kwan
 * 12/3/2012 
 * v 0.0.1 Started Constructor, initial Player and Settings Set up
 * v 0.0.2 Constructor signature completed
 * v 0.1.0 Variables for Player, and tracks created
 * v 0.1.1 Add Dialog Box to get the user's name in short
 * v 0.1.2.Dialog Box handles an empty string, user cancelling or improper string
 * 12/4/2012
 * v 0.1.3 Added the starting track value to constructor
 * v 0.1.4 changed Enum type to String
 * v 0.1.5 Removed Numtracks variable, duplicate
 * v 0.2.5 Moved Name Code to startGame class
 * v 0.2.6 Added more variables for storing user settings
 * v 0.3.6 Changed Constructor to merely storing user settings
 * v 0.4.6 Added Method to change stored values
 * v 0.5.6 Added null pointer check incase user hasn't called startGame()
 * v 0.6.6 Finished spacing calculations for displaying the train
 * v 0.6.7 Bug 1:Spacing Calculations are off
 * v 0.7.7 Fixed 1: Number of spaces are # of tracks* 2 + 1
 * v 0.7.8 Bug 2: After first picture, spacing is off
 * v 0.8.8 Fixed 2: Added value for spacing in track.java
 * passed over during instantiation
 * v 0.8.9 removed null pointer check, shouldn't matter as long
 * as user calls startGame() afterwards
 * v 0.9.9 Stable Build
 * v 1.0.0 Draws the player on selected track
 * v 1.0.1 Moved Player drawing statement for rendering correctly
 * v 1.0.2 Added KeyListener on arrow keys
 * 12/5/2012
 * v 1.0.3 Added calls to change visibility of trains on tracks
 * v 1.1.2 Only the train on the players's track is visible
 * v 1.1.3 Bug 1:Trains not rendering correctly, too fast and flickers
 * v 1.1.4 Attempt 1 Fix 1: Added option for DoubleBuffer
 * v 1.2.5 Added inheritance for Thread
 * v 1.2.6 Timing Issues Using Thread, removed
 * v 1.3.6 Implements Runnable added to take advantage of 
 * run() method running asynchronously, due to blocking method calls
 * preventing the component from showing up properly
 * v 1.3.7 Error of setup of run() method fixed
 * v 1.3.8 Added GameNewThread() method to properly setup run method
 * v 1.4.8 Added Keylistener to listen for Keypress event from Train
 * signifying a redraw
 * v 1.4.9 Initially Used Left Arrow event, caused problems, in JDE
 * changed to scroll lock
 * v 1.5.9 Added Infinite for loop to run() to make sure Tracks 
 * ran asynch to generate trains
 * v 1.6.9 Problem with synchronization, not displaying window
 * v 1.7.9 For Loop removed, after crashing from spawing too many Threads
 * to handle, moved to Track.java
 * v 1.8.9 Attempt 2 Fix 1:Change paintComponent to paint(), override update()
 * v 1.9.0 Attempt 3 Fix 1: Moved Train drawing logic to update()
 * to stop the tracks from being redrawn everytime
 * 12/6/2012
 * v 1.9.1 Bug 2: Trains drawn on all Tracks
 * v 2.0.1 Bug 2 Fixed: update() override not even being called, move
 * Train drawing logic to paint(), Also due to addition of onTrack() accessor
 * in Track.java
 * v 2.1.1 Bug 1 Fixed: Added Print Statements for debugging purposes,added
 * enough buffering to make trains viewable
 * v 2.2.1 Stable Build
 * v 2.3.1 Added Infinite Loop at end of run function
 * v 2.3.2 Added Dialog Box for Quiting the game (Q)
 * v 2.3.3 Added Dialog Box When the Player runs out of health 
 * v 2.3.4 Added option to play again by going back to start menu when you die
 * v 2.4.4 Added A Pause Option (P)
 * v 2.5.4 Readded Infinite Loop to end of run, to catch when the user is hit by a train...
 * v 2.6.4 Infinite Loop removed, move to keylistener for insert
 * v 2.7.4 Readded infinite loop to change the score, sleep for 1 sec to increment by a point
 * v 2.8.4 Stable Build
 * v 2.8.5 Added variable for stun time
 * v 2.9.5 Stable Build
 * v 3.0.5 Final
 */
package hogwartsNHobos;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameComponent extends JComponent implements Runnable{
  private Player p;
	private Track[] tracks;
	private int totalTracks;
	private int trainFreq;
	private int trainDur;
	private int playerLife;
	private int trackStart;
	private String trainDist;
	private final int SCREEN_HEIGHT = 700;
	private final JFrame starting;
	private final JFrame game;
	private int damage;
	private long stun;
	public GameComponent(int tHobo,int tFreq,int tDur,int health,int trackS,String dist,JFrame Starting,JFrame g,int damage,long stun){
		totalTracks = tHobo;
		trainFreq = tFreq;
		trainDur = tDur;
		playerLife = health;
		trackStart = trackS;
		trainDist = dist;
		this.starting = Starting;
		game = g;
		this.damage = damage;
		this.stun = stun;
	}
	//Same Signature as Constructor, due to possible change of settings by user
	public void setSettings(int tHobo,int tFreq,int tDur,int health,int trackS,String dist,int damage,long stun){
		totalTracks = tHobo;
		trainFreq = tFreq;
		trainDur = tDur;
		playerLife = health;
		trackStart = trackS;
		trainDist = dist;
		this.damage = damage;
		this.stun = stun;
	}
	public void startGame(){
		Boolean run = true;
		String name;
		//Accepts the name of the passenger and
		//asks for another one if the name is not
		//an alphabetical string
		do{
			name = JOptionPane.showInputDialog("Player Name");
			//This accounts for the user for not entering in a
			//name or pressing cancel when starting the program
			if(name == null)
				System.exit(0);
			if(name.equals(""))
				name = "1234";
			if(!name.matches("[a-zA-z]*"))
				JOptionPane.showMessageDialog(null, "Invalid Name:Please Try Again","Error",JOptionPane.ERROR_MESSAGE);
			else
				run = false;
		}while(run);
		
		final int spacing = SCREEN_HEIGHT/(totalTracks*2+1);
		
		//Setup Player Settings, array to setup tracks
		p = new Player(name,playerLife,trackStart-1,spacing);
		tracks = new Track[totalTracks];
		
		//Tracks setup...determine the spacing of each track
		for(int i=0; i < totalTracks;i++){
			if(trainDist.equals("Poisson"))
				tracks[i] = new Track((((i*2)+1)*spacing),i,spacing,trainFreq,trainDur,Track.Distribution.Poisson,p);
			else
				tracks[i] = new Track((((i*2)+1)*spacing),i,spacing,trainFreq,trainDur,Track.Distribution.Normal,p);
		}
		//Draw Player on Initial Setup
		p.setPost(0,tracks[trackStart].yPos());
		tracks[trackStart].setOn(true);
		//KeyListener to Move Character
		//Must have this statement to capture properly
		this.setFocusable(true);
		class MKeyListener implements KeyListener {
			public void keyPressed(KeyEvent event) 
			{
				int ch = event.getKeyCode();
				switch(ch) { 
				case KeyEvent.VK_UP:
					if(p.getTrack()>0 && p.ChangeTrack(p.getTrack()-1)){
						tracks[p.getTrack()+1].setOn(false);
						p.setPost(0,tracks[p.getTrack()].yPos());
						tracks[p.getTrack()].setOn(true);
						repaint();
					}
	            break;
				case KeyEvent.VK_DOWN:
					if(p.getTrack()<tracks.length-1 && p.ChangeTrack(p.getTrack()+1)){
						tracks[p.getTrack()-1].setOn(false);
						p.setPost(0,tracks[p.getTrack()].yPos());
						tracks[p.getTrack()].setOn(true);
						repaint();
					}
					break;
				//Added Case in case Someone wants to quit
				case KeyEvent.VK_Q:
					int response = JOptionPane.showConfirmDialog(null,"Do you want to quit?", "Game Over!",
							JOptionPane.YES_NO_OPTION);
					switch(response){
						case JOptionPane.YES_OPTION:
							System.exit(0);
							;
							break;
						case JOptionPane.NO_OPTION:
					}
					break;
				case KeyEvent.VK_P:
					int response2 = JOptionPane.showConfirmDialog(null,"Do you want to continue?", "Paused",
							JOptionPane.YES_NO_OPTION);
					switch(response2){
						case JOptionPane.YES_OPTION:
							break;
						case JOptionPane.NO_OPTION:
							System.exit(0);
					}
					break;
				case KeyEvent.VK_INSERT:
					if(!p.Damaged(damage)){
							int response3 = JOptionPane.showConfirmDialog(null,p.PlayerName()+ " ,you have died! \n You Have a " +
							"High Score of: " + p.PlayerScore() +"\n Do you want to play again?", "Game Over!",JOptionPane.YES_NO_OPTION);
							repaint();
							switch(response3){
								case JOptionPane.YES_OPTION:
									game.setVisible(false);
									starting.setVisible(true);
									break;
								case JOptionPane.NO_OPTION:
									System.exit(0);
							}
						}
						else{
							repaint();
							try {
								Thread.sleep(stun);
							} 
							catch (InterruptedException e) {
								System.out.println("DamageRecoveryErr");
							}
						}
				}
			}
			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
		}
		MKeyListener kListen = new MKeyListener();
		//Needed in order to attach the listener from inside the component
		//and since the variables are only accessible in this glass as part of game logic
		this.addKeyListener(kListen);
		this.setDoubleBuffered(true);
		
		//Experimental
		GameNewThread();
	}
	public void GameNewThread(){
		Thread t = new Thread(this,"GameLoop");
		t.start();
	}
	//This Must Be running in an infinite loop
	public void run(){
		System.out.println("GameThreadStarted");
		//Added just in case user wants to play again
		p.setScore(0);
		class MKeyListener2 implements KeyListener {
			public void keyPressed(KeyEvent event) 
			{
				int ch = event.getKeyCode();
				if(ch == KeyEvent.VK_SCROLL_LOCK){
					repaint();
				}
			}
			public void keyReleased(KeyEvent arg0){};
			public void keyTyped(KeyEvent arg0){};
		}
		MKeyListener2 kListen2 = new MKeyListener2();
		this.addKeyListener(kListen2);
		for(int i=0;i < totalTracks;i++){
			tracks[i].NewThread();
		}
		System.out.println("GameThreadSetupDone");
		for(;;){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.Score();
		}
	}
	//Draw each track
	@Override
	public void paint(Graphics g)
	{
		for(int i=0;i < totalTracks;i++){
				tracks[i].draw(g);
		}
		for(int i=0;i < totalTracks;i++){
			if(tracks[i].onTrack())
				tracks[i].ThisTrain().draw(g);
		}
		p.draw(g);
	}
}
