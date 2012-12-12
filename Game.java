/*CPS-731 A2 Game Class - The window in which the game is played
 * Author:Jonathan Kwan
 * 12/2/2012 v 0.0.1 Basic Frame Finished
 * v 0.1.0 Start Menu Layout 
 * v 0.1.1 Window Sizing, Buttons Finished
 * v 0.1.3 Started Background Image
 * v 0.1.4 Added ImagePanel.java to get a JPanel with Images
 * v 0.1.5 Started Frames for Settings and a How to Play Page
 * v 0.2.0 Start Menu Layout Finished
 * v 0.2.1 MouseListeners Added for Quit Button
 * v 0.2.2 MouseListeners Added for Settings and How TO Button
 * v 0.2.3 MouseListeners Added for Main Menu Button
 * v 0.3.0 How to and Settings Page Started
 * v 0.4.0 Default Game Values
 * v 0.5.0 Default Values and Settings Page Finished
 * v 0.6.0 Save and Reset Options Added to Settings
 * v 0.7.0 Use 1 variable for Tracks and Hobo => 1 Hobo/Track
 * v 0.9.0 Save and Reset functionality done
 * v 1.0.0 Game Window Created
 * v 1.0.1 Game Start Button Created
 * v 1.1.1 Work on game window
 * v 1.2.1 Tied in Game Component
 * v 2.2.1 Stable Build
 * v 2.3.1 Bug 1: with PaintComponent not being called.....
 * v 2.3.2 Fixed 1: moving setup options for game JFrame
 * v 2.4.2 Stable Build
 * 12/5/2012
 * v 2.4.3 Changed Survival Distribution to Normal Distribution
 * 12/6/2012
 * v 2.5.3, added damage value, added to settings page
 * v 2.5.4 added stun time value
 * v 2.6.4 Add Error checking for Settings, resets to default if outside of bounds
 * v 2.7.4 Stable Build
 * v 2.8.4 Final
*/
package hogwartsNHobos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Game {

  //Static-Do Not Change, only one instance of these at a time
	private static int numTrackHobo = 3;
	private static int meanTrainTime = 3;
	private static int trainDuration = 5;
	private static String distType = "Poisson";
	private static int trackStart = 1;
	private static int healthStart = 100;
	private static int damage = 5;
	private static long stun = 2500;
	
	public static void main(String[] args) {
		
		//The basic frames of the program - Do not touch!!!
		final JFrame frame = new JFrame();
		final JFrame game = new JFrame();
		final JFrame settings = new JFrame();
		final JFrame howTo = new JFrame();
		
		//For the Initial Frame
		final int FRAME_WIDTH = 1024;
		final int FRAME_HEIGHT = 700;
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Hogwarts and Hobos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//Adds Background Image for Start Page
		ImagePanel image = new ImagePanel("background.jpg");
		
		//Panel for holding the buttons to quit and such
		//Top to down layout Do Not Touch!!!
		image.setLayout(new BoxLayout(image, BoxLayout.PAGE_AXIS));
		
		//Creates the buttons for starting the game!!!
		//Adjusting Settings, How to Play...
		//and quitting the program - Do not touch!!!!!
		JButton  start = new JButton("Start Game");
		JButton instr = new JButton("How to Play");
		JButton	set = new JButton("Settings");
		JButton quit = new JButton("Quit");
		
		//Button Aligment-Centered Do not Remove!!
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		instr.setAlignmentX(Component.CENTER_ALIGNMENT);
		set.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Internal Class for Usage on clicking the quit button
		class quitter implements MouseListener 
		{
			public void mouseClicked(MouseEvent arg0)
			{
				System.exit(0);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		
		 MouseListener l1= new quitter();
		 quit.addMouseListener(l1);
		 
		 //Internal Class For switching to the how-to play frame
		 class switcher1 implements MouseListener
		{
			public void mouseClicked(MouseEvent arg0) 
			{
					frame.setVisible(false);
					howTo.setVisible(true);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		 
		 MouseListener l2= new switcher1();
		 instr.addMouseListener(l2);
		 
		 class switcher2 implements MouseListener
			{
				public void mouseClicked(MouseEvent arg0) 
				{
					frame.setVisible(false);
					settings.setVisible(true);
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			}
		 
		 MouseListener l3= new switcher2();
		 set.addMouseListener(l3);
		
		 game.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		 game.setTitle("Hogwarts and Hobos-Game");
		 game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 game.setLayout(new BorderLayout());
		 
		 //Code for Setting up Game Component
		 final GameComponent component = new GameComponent(numTrackHobo,meanTrainTime,
				 trainDuration,healthStart,trackStart,distType,frame,game,damage,stun);
		 game.add(component);
		 
		 class sGame implements MouseListener
			{
				public void mouseClicked(MouseEvent arg0) 
				{
					frame.setVisible(false);
					//Remodifies settings just in case user changed it 					
					component.setSettings(numTrackHobo,meanTrainTime,trainDuration,healthStart,trackStart,distType,damage,stun);
					component.startGame();
					game.setVisible(true);
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			}
			MouseListener sG= new sGame();
			start.addMouseListener(sG);
		 
		//Add the Buttons to the Panel
		//Add the Panel to the Frame at the Top
		//Do not Touch!!!!
		image.add(Box.createRigidArea(new Dimension(0,590)));
		image.add(start);
		image.add(Box.createRigidArea(new Dimension(0,20)));
		image.add(instr);
		image.add(Box.createRigidArea(new Dimension(0,20)));
		image.add(set);
		image.add(Box.createRigidArea(new Dimension(0,20)));
		image.add(quit);
		image.add(Box.createRigidArea(new Dimension(0,15)));
		frame.add(image,BorderLayout.SOUTH);
		
		//Main Menu Button Share by Settings and   How to Page
		//and MouseListener
		JButton  main = new JButton("Main Menu");
		JButton  main2 = new JButton("Main Menu");
		
		class switcher3 implements MouseListener
		{
			public void mouseClicked(MouseEvent arg0) 
			{
				howTo.setVisible(false);
				settings.setVisible(false);
				frame.setVisible(true);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		MouseListener l4= new switcher3();
		main.addMouseListener(l4);
		MouseListener l5= new switcher3();
		main2.addMouseListener(l5);
		
		//Frame Sizes setup for the Settings Page
		settings.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		settings.setTitle("Hogwarts and Hobos - Settings");
		settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		settings.setLayout(new BorderLayout());
		
		//Adds Background Image for Page
		ImagePanel sImage = new ImagePanel("settings.jpg");
		
		//Panel for holding the buttons to return to main menu
		//Top to down layout Do Not Touch!!!
		sImage.setLayout(new BoxLayout(sImage, BoxLayout.PAGE_AXIS));
		main.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Reset and Save Buttons for Settings Page
		JButton reset = new JButton("Reset");
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton save = new JButton("Save");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Labels for Input Fields and label fields
		//for Settings page
		JLabel tHNum = new JLabel("Number of Tracks/Hobos (3-5): ");
		JLabel btNum = new JLabel("Time between Trains(secs)(1-5): ");
		JLabel durNum= new JLabel("Duration of Trains(secs)(5-10): ");
		JLabel dist = new JLabel("Probability Distribution: ");
		JLabel tStart = new JLabel("Track to Start On(0-5): ");
		JLabel iHealth = new JLabel("Starting Health(1-10000000): ");
		JLabel dmg = new JLabel("Damage Per Hit(1-1000000: ");
		JLabel stn = new JLabel("Stun Time(ms 1000ms = 1s)1-3000: ");
		final JTextField nTrH = new JTextField(Integer.toString(numTrackHobo),1);
		final JTextField mTT = new JTextField(Integer.toString(meanTrainTime),1);
		final JTextField tD = new JTextField(Integer.toString(trainDuration),1);
		final JTextField tS = new JTextField(Integer.toString(trackStart),1);
		final JTextField iH = new JTextField(Integer.toString(healthStart),7);
		final JTextField dg = new JTextField(Integer.toString(damage),4);
		final JTextField st = new JTextField(Long.toString(stun),5);
		
		final Choice destChoice=new Choice();
		destChoice.add("Poisson");
		destChoice.add("Normal");
		JPanel labels = new JPanel(new GridLayout(0,1,1,1));
        labels.setBorder(new EmptyBorder(0,0,0,0));
        labels.add(tHNum);
        labels.add(btNum);
        labels.add(durNum);
        labels.add(dist);
        labels.add(tStart);
        labels.add(iHealth);
        labels.add(dmg);
        labels.add(stn);
        JPanel fields = new JPanel(new GridLayout(0,1,1,1));
        fields.setBorder(new EmptyBorder(0,0,0,0));
        fields.add(nTrH);
        fields.add(mTT);
        fields.add(tD);
        fields.add(destChoice);
        fields.add(tS);
        fields.add(iH);
        fields.add(dg);
        fields.add(st);
        JPanel labelFields = new JPanel(new BorderLayout(2,2));
        labelFields.setBorder(new TitledBorder("Settings"));
		labelFields.add(labels,BorderLayout.WEST);
		labelFields.add(fields,BorderLayout.CENTER);
		
		//For Resetting the Settings
		class reset implements MouseListener
		{
			public void mouseClicked(MouseEvent arg0) 
			{
				nTrH.setText("3");
				mTT.setText("3");
				tD.setText("5");
				destChoice.select(0);
				tS.setText("1");
				iH.setText("100");
				dg.setText("5");
				st.setText("2500");
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		MouseListener l6= new reset();
		reset.addMouseListener(l6);
		
		//For Resetting the Settings
		class save implements MouseListener
		{
			public void mouseClicked(MouseEvent arg0) 
			{
				numTrackHobo = Integer.parseInt(nTrH.getText());
				if(numTrackHobo <3 || numTrackHobo>5)
					nTrH.setText("3");
				meanTrainTime = Integer.parseInt(mTT.getText());
				if(meanTrainTime <1 || meanTrainTime>5)
					mTT.setText("3");
				trainDuration = Integer.parseInt(tD.getText());
				if(trainDuration <5 || trainDuration>10)
					tD.setText("5");
				distType = destChoice.getSelectedItem();
				trackStart = Integer.parseInt(tS.getText());
				if(trackStart < 0 || trackStart>Integer.parseInt(nTrH.getText()))
					tS.setText("1");
				healthStart = Integer.parseInt(iH.getText());
				if(healthStart <1 || healthStart>10000000)
				iH.setText("100");
				damage = Integer.parseInt(dg.getText());
				if(damage <1 || damage>1000000)
				dg.setText("5");
				stun = Long.parseLong(st.getText());
				if(stun <1 || stun>3000)
					st.setText("2500");
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		}
		MouseListener l7= new save();
		save.addMouseListener(l7);
		
		//Add the Textboxes to the page
		//Add the main menu to the Panel
		//Add the Panel to the Frame at the Top
		//Do not Touch!!!!
		sImage.add(Box.createRigidArea(new Dimension(0,178)));
		sImage.add(labelFields,BorderLayout.WEST);
		sImage.add(Box.createRigidArea(new Dimension(0,260)));
		sImage.add(save);
		sImage.add(Box.createRigidArea(new Dimension(0,20)));
		sImage.add(reset);
		sImage.add(Box.createRigidArea(new Dimension(0,20)));
		sImage.add(main);
		sImage.add(Box.createRigidArea(new Dimension(0,20)));
		settings.add(sImage,BorderLayout.SOUTH);
		
		//Frame Sizes setup for How To Page
		howTo.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		howTo.setTitle("Hogwarts and Hobos - How To");
		howTo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		howTo.setLayout(new BorderLayout());
		
		//Adds Background Image for Page
		ImagePanel hImage = new ImagePanel("howTo.jpg");
		
		//Panel for holding the buttons to return to main menu
		//Top to down layout Do Not Touch!!!
		hImage.setLayout(new BoxLayout(hImage, BoxLayout.PAGE_AXIS));
		main2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Add the main menu to the Panel
		//Add the Panel to the Frame at the Top
		//Do not Touch!!!!
		hImage.add(Box.createRigidArea(new Dimension(0,720)));
		hImage.add(main2);
		hImage.add(Box.createRigidArea(new Dimension(0,20)));
		howTo.add(hImage,BorderLayout.SOUTH);
		
		//Set visibility of Frame after setup done - Do not move!!!
		frame.setVisible(true);
	}
}
