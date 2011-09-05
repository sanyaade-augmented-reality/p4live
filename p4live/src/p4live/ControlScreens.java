package p4live;

import processing.core.PApplet;
import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.Radio;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class ControlScreens extends Control{

	int txtWinWidth=-1;
	int txtWinHeight=-1;
	boolean Output_Active = false;
	
	ControlScreens(){
		
		buildWidget();
	}
	
	
	void buildWidget(){
		  ControlGroup cs = controlP5.addGroup("Control Screens",600,400);
		  controlP5.addToggle("Output_Active",100,100,20,20).setGroup(cs);
		  
		  //controlP5.addToggle("toggleValue",true,100,240,100,20).setMode(ControlP5.SWITCH);
		  
		  cs.setBackgroundColor(p.color(255,100));
		  cs.setBackgroundHeight(150);

	}
	
	/**
	 * Detect the number of screens and ajust the size of visual window
	 */
	private void detectDisplays(){
	      GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	      GraphicsDevice devices[] = environment.getScreenDevices();

	       if(devices.length>1 ){ //we have a 2nd display/projector
	           //learn the true dimensions of the secondary display
	    	   txtWinWidth =devices[1].getDisplayMode().getWidth();
	    	   txtWinHeight = devices[1].getDisplayMode().getHeight();
	           //println("-> Adjusting visual window size to "+widthVisual+" x "+heightVisual+" (size of 2ndary display)");
	   			//frame.setBounds(devices[0].getDisplayMode().getWidth(), 0, widthVisual, heightVisual);
	       }else{ //no 2nd screen but make it fullscreen anyway
	    	   txtWinWidth =devices[0].getDisplayMode().getWidth();
	    	   txtWinHeight = devices[0].getDisplayMode().getHeight();
	       }
	       //aspect = (float)(widthVisual)/(float)heightVisual;	//Set aspect ratio with new resolution
	}

}
