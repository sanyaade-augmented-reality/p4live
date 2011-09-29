/**
 * 
 */
package p4live;

import java.util.ArrayList;
import java.util.Hashtable;
import processing.core.PApplet;
import themidibus.MidiBus;

/**
 * @author lot
 * All events are events.
 */
public class EventsMidi extends Events{
	Hashtable<Integer, Hashtable<Integer, Integer>> tableEvents;
	
	EventsMidi(PApplet parent){
		p = parent;
		desc="Events of library MidiBus";
		tableEvents  = new Hashtable<Integer, Hashtable<Integer, Integer>>(); 
		//loadDefaultActions();
	}
	
	public void captureMIDIEvent(int channel, int number, int v, String bus_name) {
		p.println("Controller Change: " + channel);
		float  value=-1;
		value = p.norm(v, 0, 127);
		
		//Hashtable t;
	//	t = tableEvents.get(channel);
		
		actions.execute(getAction(channel,number), value);
	}
	
	private int getAction(int channel, int number){
		int action=2;
		//for (int k=0;k<)
		return action;
	}
	
	// Name of all controller events
	public void loadDefaultActions(){
		Hashtable channel0 = new Hashtable<Integer,Integer>();
		Hashtable channel1 = new Hashtable<Integer,Integer>();
		//channel0.put(cc number, action);
		
		//Input Control MIDI Actions
		channel0.put(43, 2);
		tableEvents.put(0,channel0);
		
		//Midi input sound
		channel1.put(44, 1);
		tableEvents.put(1,channel1);
	}
		
}
