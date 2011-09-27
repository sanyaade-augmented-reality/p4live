/**
 * 
 */
package p4live;
import java.util.ArrayList;

import controlP5.ControlEvent;
import controlP5.Controller;
import controlP5.ControllerInterface;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Reaction {	
	private PApplet p;
	private Events events;
	private EventsCP5 ecp5=null;
	private EventsMidi emidi=null;
	
	Reaction(PApplet parent){
		p = parent;
		buildEventSystem();
	}

	private void buildEventSystem(){
		events = new Events(p);
		ecp5 = new EventsCP5(p);
		emidi = new EventsMidi(p);
	}
	
	public void captureP5Event(Controller c) {
		ecp5.captureP5Event(c);
	}

	/*public void captureP5Event(Controller c) {
		ecp5.captureP5Event(c);
	}*/
	
/*	
	public void setPreferences(){
	    for(int k = 0;k<events.size();k++){
	    	((Control)events.get(k)).setGroupPreferences();
	    }
	}*/
/*
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
	
*/
	
}
