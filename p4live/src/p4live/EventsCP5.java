/**
 * 
 */
package p4live;

import java.util.Hashtable;
import controlP5.Controller;
import processing.core.PApplet;

/**
 * @author lot
 * All events are events.
 */
public class EventsCP5 extends Events{
	private Hashtable<String, Integer> tableEvents;
	
	EventsCP5(PApplet parent){
		p = parent;
		desc="Events of library ControlP5";
		tableEvents  = new Hashtable<String, Integer>(); 
		//loadDefaultActions();
	}
	
	public void captureP5Event(Controller c) {
		//actions.execute(c);		
		float  value=-1;
		if ( (c.min() == 0) && (c.max() == 0)){
			value = c.value();
		}else{
			value = p.norm(c.value(), c.min(), c.max());
		}
		captureP5Event(c.name(),value);
	}
	private void captureP5Event(String name, float value){
		//p.println("got "+value+" from a controller "+ name);
		   Integer n = tableEvents.get(name);
		   if (n != null) {
		     //System.out.println("action = " + n);
		     actions.execute(n, value);
		   }
	}

	
	// Name of all controller events
	public void loadDefaultActions(){
		tableEvents.put("Fullscreen", 1);
		tableEvents.put("OutputWindow", 2);
		tableEvents.put("BeatSensitivity", 3);
		tableEvents.put("bpmVelocity", 4);
		
	}	
}
