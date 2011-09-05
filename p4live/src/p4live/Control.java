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
	static ControlP5 controlP5;
	static PApplet p;
	
	int xPos; // X coordenate of Panel
	int yPos; // Y coordenate of Panel
	//int xSize;
	//int ySize;

	Control(){}
	
	Control(PApplet parent){
		p=parent;
		controlP5 = new ControlP5(p);
	}
	
	
	public void controlEvent(ControlEvent theEvent) {
		  if(theEvent.isGroup()) {
		    p.println("got an event from group "+theEvent.group().name()+", isOpen? "+theEvent.group().isOpen());  
		  } else if (theEvent.isController()){
		    p.println("got something from a controller "+theEvent.controller().name());
		  }
		}  
}
