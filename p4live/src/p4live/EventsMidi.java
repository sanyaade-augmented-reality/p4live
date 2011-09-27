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
	ArrayList tableEvents;
	
	EventsMidi(PApplet parent){
		p = parent;
		desc="Events of library MidiBus";
		tableEvents  = new ArrayList<Integer[]>(); 
		loadDefaultActions();
	}
	
	public void captureMIDIEvent(int channel, int number, int v, String bus_name) {
		p.println("Controller Change: " + channel);
		float  value=-1;
		value = p.norm(v, 0, 127);
		actions.execute(getAction(channel,number), value);
	}
	
	private int getAction(int channel, int number){
		int action=2;
		//for (int k=0;k<)
		return action;
	}
	
	// Name of all controller events
	public void loadDefaultActions(){
		int lineEvent[] = new int[3];
		lineEvent[0] = 0;
		lineEvent[1] = 43;
		lineEvent[2] = 2;
	
		tableEvents.add(lineEvent);
		//tableEvents.put("Fullscreen", 1);
	//	tableEvents.put("OutputWindow", 2);
	}
		
}
