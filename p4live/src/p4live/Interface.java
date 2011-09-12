/**
 * 
 */
package p4live;
import java.util.ArrayList;

import controlP5.ControllerInterface;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Interface {
	
	private PApplet p;
	private ArrayList Controls;
	private Control c;
	
	Interface(PApplet parent){
		p = parent;
		c = new Control(p);
		Controls = new ArrayList();

		buildInterface();
		//loadInterface();
	}

	private void buildInterface(){
		Controls.add(new ControlScreens());
		Controls.add(new ControlBeat());
		Controls.add(new ControlVolume());
		Controls.add(new ControlFFT());
	}
	
	public void loadInterface(){
		c.getControlP5().setFilePath("data/controlp5.xml");
		p.println("loading: "+c.getControlP5().filePath());

		c.getControlP5().setAutoInitialization(true);
	    c.getControlP5().load("controlP5.xml");
	    

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
	
}
