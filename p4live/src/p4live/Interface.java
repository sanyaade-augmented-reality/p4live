/**
 * 
 */
package p4live;
import java.util.ArrayList;

import controlP5.ControlEvent;
import controlP5.ControllerInterface;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Interface {
	
	private static PApplet p;
	private static ArrayList Controls;
	private Control c;
	
	Interface(PApplet parent){
		p = parent;
		c = new Control(p);
		Controls = new ArrayList();

		buildInterface();
		//loadInterface();
	}

	private void buildInterface(){
		Controls.add(new ControlScreens());//0
		Controls.add(new ControlBeat());//1
		Controls.add(new ControlVolume());//2
		Controls.add(new ControlFFT());//3
		Controls.add(new ControlMidi());//4
	}
	
	public void loadInterface(){
		c.getControlP5().setFilePath("data/controlp5.xml");
		p.println("loading: "+c.getControlP5().filePath());

	//	c.getControlP5().setAutoInitialization(true);
	    c.getControlP5().load("controlP5.xml");
	    setPreferences();
	}
	
	public void setPreferences(){
	    for(int k = 0;k<Controls.size();k++){
	    	((Control)Controls.get(k)).setGroupPreferences();
	    }
	}

	public void saveInterface(){
	    // save the current state/setup of all 
	    // controllers available.
		c.getControlP5().setFilePath("data/controlp5.xml");		
	    c.getControlP5().save();
	}
	
	public void printControls(){
		ControllerInterface[] ci;
		//c.getControlP5().
		ci =  c.getControlP5().getControllerList();
		p.println("elem: "+ ci.length);
		for (int k=0;k<ci.length;k++){
			p.println(ci[k].stringValue());
		}
	}
	
	//esta aqui porque output window esta dentro de controlscreens
	public static void setFullScreen(boolean estado){
		((ControlScreens)Controls.get(0)).setFullScreen(estado); 
	}
	
	public static void pingMidi(){
		((ControlMidi)Controls.get(4)).pingComunication();
	}
	
}
