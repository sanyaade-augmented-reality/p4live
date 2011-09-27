/**
 * 
 */
package p4live;

import java.util.Hashtable;

import controlP5.ControlEvent;
import controlP5.Controller;
import processing.core.PApplet;

/**
 * @author lot
 * All events are events.
 */
public class Events {
	protected static PApplet p;
	protected String desc = "";
	protected static Actions actions;
	private static EventsCP5 ecp5=null;
	private static EventsMidi emidi=null;
	
	Events(){}
	
	Events(PApplet parent){
		p = parent;
		actions = new Actions(p);
		ecp5 = new EventsCP5(p);
		emidi = new EventsMidi(p);
	}
			
	public String description(){ return desc;}

	public void captureP5Event(Controller c) {
		ecp5.captureP5Event(c);
	}

	public void captureMidiEvent(int channel, int number, int value, String bus_name) {
		emidi.captureMIDIEvent(channel, number, value, bus_name);
	}
	
}
