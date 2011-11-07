/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amoros 
http://p4live.feenelcaos.org

P4Live is free software:
you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License.

P4VJ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with P4Live.  If not, see <http://www.gnu.org/licenses/>.
 */
package p4live;

import processing.core.PApplet;

//Import Libraries
import controlP5.ControlEvent;
import controlP5.Controller;
import codeanticode.glgraphics.*;
 
//import P4Sketchs.

import p4control.Midi;
import p4control.Mixer;
import p4live.P4Constants;


public class P4live extends PApplet {
	public static Interface i;
	private static Events events;
	public static String p4Folder;
	//Events events[];
	//private boolean active=false;
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { p4live.P4live.class.getName() });
		//sketchPath(), sketchFile(), savePath(), dataPath(), and createPath() 
	}
	
	public void setup() {
		size(1024, 768, GLConstants.GLGRAPHICS);
		frameRate(30);	
		p4Folder = "/Users/lot/Documents/workspace/p4live";
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
		//i.pingMidi();
		Midi.pingComunication();
		events.captureMidiEvent(channel,pitch,velocity,bus_name);
	}

	void noteOff(int channel, int pitch, int velocity, String bus_name) {
		Midi.pingComunication();
		events.captureMidiEvent(channel,pitch,velocity,bus_name);
	}

	public void controllerChange(int channel, int number, int value, String bus_name) {
		Midi.pingComunication();
		events.captureMidiEvent(channel,number,value,bus_name);
	}

	public void controlEvent(ControlEvent theEvent) {
		  if (theEvent.isGroup()) {
			switch(theEvent.group().id()){
			case 1:
				Mixer.setCurrentFilter((int)theEvent.group().getValue());
				break;
			case 100:
				i.setSketch(1, (int)theEvent.group().getValue());
				break;
			case 200:
				i.setSketch(2, (int)theEvent.group().getValue());
				break;
			case 300:
				i.setSketch(3, (int)theEvent.group().getValue());
				break;
			}			
		    // check if the Event was triggered from a ControlGroup
		    //println(theEvent.group().getValue()+" from "+theEvent.group());
		  } /*else if(theEvent.isController()) {
		    println("* Warning: controller not mapped: " + theEvent.controller().name());
		  }*/
		}
}
