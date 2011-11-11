/**
 * 
 */
package p4live;

import java.util.Hashtable;
import p4live.P4Constants;
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
	//private static EventsCP5 ecp5=null;
	private static EventsMidi emidi=null;
	
	Events(){}
	
	Events(PApplet parent){
		p = parent;
		actions = new Actions(p);
		//ecp5 = new EventsCP5(p);
		emidi = new EventsMidi(p);
	}
			
	public String description(){ return desc;}

	public static void kick(){
		Interface.event(P4Constants.KICK);
	}

	public static void snare(){
		Interface.event(P4Constants.SNARE);
	}
	
	public static void hat(){
		Interface.event(P4Constants.HAT);
	}
	
	public static void beat(){
		Interface.event(P4Constants.BEAT);		
	}
	
	
	public void event(int e){
		
	}
}
