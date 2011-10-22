/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amoros 
http://p4live.feenelcaos.org

P4VJ is free software:
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

import codeanticode.glgraphics.GLTexture;
import processing.core.PImage;
import controlP5.Button;
import controlP5.ControlBehavior;
import controlP5.ControlEvent;
import controlP5.DropdownList;
import controlP5.Knob;
import controlP5.Slider;
import controlP5.Toggle;
import themidibus.MidiBus;

public class SketchControl extends Control{
	private DropdownList ddl1;
	private Sketch sketch;
	private ControllerTexture imageSketch;
	
	SketchControl(String s, int dx, int dy){
		groupName = s;
		defaultX = dx;
		defaultY = dy;
		defaultWidth = 200;
		defaultHeight= 450;
		if (groupName.equals("Sketch1")){
			sketch = new Demo(p, p4live.OutputWindow.getWidth(), p4live.OutputWindow.getHeight());
			P4live.println("TS");
		}
		if (groupName.equals("Sketch2")){
			sketch = new TestScreen(p, p4live.OutputWindow.getWidth(), p4live.OutputWindow.getHeight());
			P4live.println("Demo");
		}
		if (groupName.equals("Sketch3")){
			sketch = new BlackScreen(p, p4live.OutputWindow.getWidth(), p4live.OutputWindow.getHeight());
			P4live.println("black");
		}
		
		sketch.beginDraw();
		sketch.draw();
		sketch.endDraw();
		imageSketch = new ControllerTexture(controlP5,groupName+"Preview",10,10,180,180);
		controlP5.register(imageSketch);
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		imageSketch.setGroup(group);

		controlP5.addSlider(groupName+"Alpha", 0, 1, 1, 130, 230, 40, 200).setGroup(group);
		controlP5.addTextlabel(groupName+"Params", "Params", 10,220).setGroup(group);
		controlP5.addKnob(groupName+"P1", 0, 1, 0, 10, 230, 20).setGroup(group);
		controlP5.addKnob(groupName+"P2", 0, 1, 0, 50, 230, 20).setGroup(group);
		controlP5.addKnob(groupName+"P3", 0, 1, 0, 90, 230, 20).setGroup(group);

		controlP5.addTextlabel(groupName+"CameraPosition", "Camera Position", 10,260).setGroup(group);		
		controlP5.addKnob(groupName+"CX", 0, 1, 0.5f, 10, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CY", 0, 1, 0.5f, 50, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CZ", 0, 1, 0.5f, 90, 270, 20).setGroup(group);
		
		controlP5.addTextlabel(groupName+"CameraRotation", "Camera Rotation", 10,300).setGroup(group);
		controlP5.addKnob(groupName+"RX", 0, 1, 0.5f, 10, 310, 20).setGroup(group);
		controlP5.addKnob(groupName+"RY", 0, 1, 0.5f, 50, 310, 20).setGroup(group);
		controlP5.addKnob(groupName+"RZ", 0, 1, 0.5f, 90, 310, 20).setGroup(group);
		
		controlP5.addKnob(groupName+"FOV", 0, 360, 40, 10, 350, 60).setGroup(group);
		
		controlP5.addToggle(groupName+"Pause",   false, 80, 350, 15, 15).setGroup(group);
		controlP5.addButton(groupName+"Reset",0, 80,390,30,20).setGroup(group);
		controlP5.addButton(groupName+"Close",   0, 10, 420,40,20).setGroup(group);
		controlP5.addButton(groupName+"OutClose",0, 60, 420,50,20).setGroup(group);
		
		ddl1 = controlP5.addDropdownList(groupName+"Select_Sketch",60,213,110,10);
		ddl1.setId(1000);
		ddl1.setGroup(group);
		ddl1.setLabel("Select_Sketch");

		  for(int i=0;i<12;i++) {
			    ddl1.addItem("Sketch "+i,i);
			  }
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);
		
		//ONLY FOR STATIC CHANELS
		if (groupName.equals("Sketch1") ){
			controlP5.controller(groupName+"P1").setId(102);
			controlP5.controller(groupName+"P2").setId(103);
			controlP5.controller(groupName+"P3").setId(104);
			controlP5.controller(groupName+"CX").setId(105);
			controlP5.controller(groupName+"CY").setId(106);
			controlP5.controller(groupName+"CZ").setId(107);
			controlP5.controller(groupName+"Alpha").setId(101);
			controlP5.controller(groupName+"FOV").setId(111);			
			controlP5.controller(groupName+"Pause").setId(112);
			controlP5.controller(groupName+"Reset").setId(113);
			controlP5.controller(groupName+"Close").setId(114);
			controlP5.controller(groupName+"OutClose").setId(115);
		}
		if (groupName.equals("Sketch2") ){
			/*controlP5.controller(groupName+"P1").setId(202);
			controlP5.controller(groupName+"P2").setId(203);
			controlP5.controller(groupName+"P3").setId(204);
			controlP5.controller(groupName+"CX").setId(205);
			controlP5.controller(groupName+"CY").setId(206);
			controlP5.controller(groupName+"CZ").setId(207);
			controlP5.controller(groupName+"Alpha").setId(201);
			controlP5.controller(groupName+"Pause").setId(212);
			controlP5.controller(groupName+"Reset").setId(213);
			controlP5.controller(groupName+"Close").setId(214);
			controlP5.controller(groupName+"OutClose").setId(215);*/
		}
		
		controlP5.controller(groupName+"Alpha").setLabel("Alpha");
		controlP5.controller(groupName+"Alpha").plugTo(this);

		controlP5.controller(groupName+"FOV").setLabel("FOV");
		controlP5.controller(groupName+"FOV").plugTo(this);

		controlP5.controller(groupName+"Pause").setLabel("Pause");
		controlP5.controller(groupName+"Pause").plugTo(this);

		controlP5.controller(groupName+"Reset").setLabel("Reset");
		controlP5.controller(groupName+"Reset").plugTo(this);
		
		controlP5.controller(groupName+"Close").setLabel("Close");
		controlP5.controller(groupName+"Close").plugTo(this);

		controlP5.controller(groupName+"OutClose").setLabel("Out&Close");
		controlP5.controller(groupName+"OutClose").plugTo(this);

		
		controlP5.controller(groupName+"P1").plugTo(this);
		controlP5.controller(groupName+"P1").setId(2);
		controlP5.controller(groupName+"P2").plugTo(this);
		controlP5.controller(groupName+"P3").plugTo(this);
		
		controlP5.controller(groupName+"CX").plugTo(this);
		controlP5.controller(groupName+"CY").plugTo(this);
		controlP5.controller(groupName+"CZ").plugTo(this);
		
		controlP5.controller(groupName+"RX").plugTo(this);
		controlP5.controller(groupName+"RY").plugTo(this);
		controlP5.controller(groupName+"RZ").plugTo(this);
	}
	
	// a slider event will change the value of textfield textA
	public void sliderA(int theValue) {
	//  ((Textfield)controlP5.controller("textA")).setValue(""+theValue);
	}
	
	public void controlEvent(ControlEvent theEvent) {
		  p.println("got a control event from controller with id "+theEvent.controller().id());
		  p.println("got a control event from controller with name "+theEvent.controller().label());
		  
		  switch(theEvent.controller().id()){
		  case 113:
			  ((Knob) controlP5.controller("Sketch1CX")).setValue(0.5f);
			  ((Knob) controlP5.controller("Sketch1CY")).setValue(0.5f);
			  ((Knob) controlP5.controller("Sketch1CZ")).setValue(0.5f);
			  ((Knob) controlP5.controller("Sketch1RX")).setValue(0.5f);
			  ((Knob) controlP5.controller("Sketch1RY")).setValue(0.5f);
			  ((Knob) controlP5.controller("Sketch1RZ")).setValue(0.5f);
			  break;
		  case 101:
			  imageSketch.setAlpha(theEvent.controller().value());
			  break;
		  case 111:
			  sketch.setFov(theEvent.controller().value());
			  break;
		  case 115:
			  ((Slider) controlP5.controller("Sketch1Alpha")).setValue(0);
			  break;
		  }
		  
		  if (theEvent.controller().label().equals(groupName+"CX"))
			  sketch.setCameraX(theEvent.controller().value());
		  
		  if (theEvent.controller().label().equals(groupName+"CY"))
			  sketch.setCameraY(theEvent.controller().value());
		  
		  if (theEvent.controller().label().equals(groupName+"CZ"))
			  sketch.setCameraZ(theEvent.controller().value());
		  
		  if (theEvent.controller().label().equals(groupName+"RX"))
			  sketch.setRotationX(theEvent.controller().value());
		  
		  if (theEvent.controller().label().equals(groupName+"RY"))
			  sketch.setRotationY(theEvent.controller().value());
		  
		  if (theEvent.controller().label().equals(groupName+"RZ"))
			  sketch.setRotationZ(theEvent.controller().value());		
		  
		  if (theEvent.controller().label().equals("Reset")){
			  ((Knob) controlP5.controller(groupName+"CX")).setValue(0.5f);
		  }
		  
		  //setValue(p.constrain((float) (value * 0.9), 0, 1));
	}
	
	public GLTexture getTexture(){
		return sketch.getTexture();
	}
	
	public void update(){
		sketch.begin();
		sketch.draw();
		sketch.end();
	}
}
