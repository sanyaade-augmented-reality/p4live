/**
 * 
 */
package p4live;
import p4live.P4live;
import processing.core.PApplet;
import controlP5.*;

/**
 * @author lot
 *
 */
public class Control {
	protected static ControlP5 controlP5;
	protected static PApplet p;
	protected ControlGroup group;
	protected String groupName="";
	
	Control(){}
	
	Control(PApplet parent){
		p=parent;
		controlP5 = new ControlP5(p);
	}
	
	public ControlP5 getControlP5(){
		return controlP5;
	}
	
	public void controlEvent(ControlEvent theEvent) {
		  if(theEvent.isGroup()) {
		    p.println("got an event from group "+theEvent.group().name()+", isOpen? "+theEvent.group().isOpen());  
		  } else if (theEvent.isController()){
		    p.println("got something from a controller "+theEvent.controller().name());
		  }
		}  
	
	public void setGroupPreferences(){
		group = (ControlGroup)controlP5.group(groupName); 
		if(group != null){
			group.setBackgroundColor(p.color(255, 100));
			group.setBackgroundHeight(150);
			setPreferences(); // for load  preferences of each instance
		}
	}
	
	public void setPreferences(){
		group = (ControlGroup)controlP5.group(groupName);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);	
	}
}
