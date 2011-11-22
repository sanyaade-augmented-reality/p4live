/**
 * 
 */
package p4control;
import p4live.P4live;
import processing.core.PApplet;
import controlP5.*;
import controlP5.Controller;
import ddf.minim.*;
import de.looksgood.ani.Ani;

/**
 * @author lot
 *
 */
public class Control {
	protected static PApplet p;
	protected ControlGroup group;
	protected String groupName="";
	protected static ControlP5 controlP5;	
	protected static Minim minim;
	protected static AudioInput in;
	
	protected int defaultWidth=100;
	protected int defaultHeight=100;
	protected int defaultX=0;
	protected int defaultY=0;
	
	Control(){}
	
	public Control(PApplet parent){
		p=parent;
		controlP5 = new ControlP5(p);
		//controlP5.DEBUG = true;
		minim = new Minim(p);
		//minim.debugOn();	  
		// get a line in from Minim, default bit depth is 16
		in = minim.getLineIn(Minim.STEREO);
		
	}
	
	public static void changeController(String controller, float value){
		Controller c = controlP5.controller(controller);
		float v = p.map(value, 0, 1, c.getMin(), c.getMax());
		c.setValue(v);
	}
	
	public ControlP5 getControlP5(){
		return controlP5;
	}
	
	private void buildInterface() {	
		p.println("[Warning] ControlP5 Interface not programmed in module: "+groupName);
	}
	public void setGroupPreferences(){
		group = (ControlGroup)controlP5.group(groupName); 
		if(group != null){
			group.setBackgroundColor(p.color(255, 100));
			group.setBackgroundHeight(defaultHeight);
			group.setWidth(defaultWidth);
			setPreferences(); // for load  preferences of each instance
		}
	}
	
	public void setPreferences(){
		group = (ControlGroup)controlP5.group(groupName);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);
	}
	
	public void stop()
	{
	  // always close Minim audio classes when you are done with them
	  in.close();
	  minim.stop();
	  
	  p.stop();
	}
	
	/*public class MyControlListener implements ControlListener {
		  int col;
		  public void controlEvent(ControlEvent theEvent) {
		    p.println("i got an event from mySlider, " +
		            "changing background color to "+
		            theEvent.controller().value());
		    col = (int)theEvent.controller().value();
		  }

		}*/
	
}
