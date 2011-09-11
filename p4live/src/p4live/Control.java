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
	
	//protected int xPos; // X coordenate of Panel
	//protected int yPos; // Y coordenate of Panel

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
	
	//borrrar
	/*public void setGroupPreferences(){
		//for (int k=0;k<controlP5.)
		//group = (ControlGroup)controlP5.group("Control Screens"); // controller("Beat Analisys");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		setPreferences();
	}*/
	
	public void setPreferences(){
		group = (ControlGroup)controlP5.group("Control Screens");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		
	}
}
