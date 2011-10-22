package p4live;

//Import processing
import processing.core.PApplet;

//Import Libraries
import controlP5.ControlEvent;
import controlP5.Controller;
import codeanticode.glgraphics.*;
//import themidibus.*; 

import p4live.P4Constants;


public class P4live extends PApplet {
	public static Interface i;
	private static Events events;
	//Events events[];
	//private boolean active=false;
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { p4live.P4live.class.getName() });
		//sketchPath(), sketchFile(), savePath(), dataPath(), and createPath() 
	}
	
	public void setup() {
		size(1024, 768, GLConstants.GLGRAPHICS);
		frameRate(30);	
		//solo objetos a los que haya que acceder
		events = new Events(this);
		i = new Interface(this);					//last step build interface
	}

	public void draw() {
		background(0);
		text(mouseX + " , " + mouseY,mouseX,mouseY);
		i.update();
	}
	
	public void keyPressed() {
		  if(key=='s') {
			  i.saveInterface();
		  }
		  if(key=='l') {
			  i.loadInterface();
		  }
		  if(key=='r') {
			  i.printControls();
		  }
		  if(key=='p') {
			  i.setPreferences();
		  }
		  /*
		  if (key == 'a'){
			  active = true;
			  println("active");
		  }*/
	}
	
	/////////// EVENTS

	public void noteOn(int channel, int pitch, int velocity, String bus_name) {
		i.pingMidi();
		events.captureMidiEvent(channel,pitch,velocity,bus_name);
	}

	void noteOff(int channel, int pitch, int velocity, String bus_name) {
		i.pingMidi();
		events.captureMidiEvent(channel,pitch,velocity,bus_name);
	}

	public void controllerChange(int channel, int number, int value, String bus_name) {
		i.pingMidi();
		events.captureMidiEvent(channel,number,value,bus_name);
	}

}
