/*
33This file is part of P4Live :: Processing 4 Live 
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
package p4control;

import p4live.ControllerTexture;
import p4live.OutputWindow;
import p4sketch.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import codeanticode.glgraphics.GLTexture;
import processing.core.PApplet;
import processing.core.PImage;
import controlP5.Button;
import controlP5.ControlBehavior;
import controlP5.ControlEvent;
import controlP5.DropdownList;
import controlP5.Knob;
import controlP5.Slider;
import controlP5.Toggle;
import de.looksgood.ani.Ani;
import themidibus.MidiBus;

public class Channel extends Control{
	private static String sketchPath="/Users/lot/Documents/workspace/p4live/src/p4sketch";
	private static ArrayList<String> Sketchs = new ArrayList<String>();
	private DropdownList sketchSelector;
	private Sketch sketch;
	private ControllerTexture imageController;
	private int channel;
	private int delayCamera = 2;
	
	private static float Sketch1Alpha=1;
	private static float Sketch2Alpha=1;
	private static float Sketch3Alpha=1;
	
	private static float Sketch1CX=0.5f;
	private static float Sketch1CY=0.5f;
	private static float Sketch1CZ=0.5f;
	private static float Sketch2CX=0.5f;
	private static float Sketch2CY=0.5f;
	private static float Sketch2CZ=0.5f;
	private static float Sketch3CX=0.5f;
	private static float Sketch3CY=0.5f;
	private static float Sketch3CZ=0.5f;

	private static float Sketch1RX=0.5f;
	private static float Sketch1RY=0.5f;
	private static float Sketch1RZ=0.5f;
	private static float Sketch2RX=0.5f;
	private static float Sketch2RY=0.5f;
	private static float Sketch2RZ=0.5f;
	private static float Sketch3RX=0.5f;
	private static float Sketch3RY=0.5f;
	private static float Sketch3RZ=0.5f;
	
	public Channel(int c, int dx, int dy){
		String s;
		channel = c;
		s = "Sketch"+channel;
		groupName = s;
		defaultX = dx;
		defaultY = dy;
		defaultWidth = 200;
		defaultHeight= 460;
		
		if (channel == 1)
			loadVisuals();
		
		switch(channel){
			case 1:
				setSketch("TestScreen");
				break;
			case 2: 	
				sketch = new _Select_Sketch(p, OutputWindow.getWidth(), OutputWindow.getHeight());
				break;
			case 3:
				sketch = new _Select_Sketch(p, OutputWindow.getWidth(), OutputWindow.getHeight());
				break;
		}		
		sketch.begin();
		sketch.draw();
		sketch.end();
		imageController = new ControllerTexture(controlP5,groupName+"Preview",10,10,180,180);
		imageController.setChannel(channel);
		controlP5.register(imageController);
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		imageController.setGroup(group);

		controlP5.addSlider(groupName+"Alpha", 0, 1, 1, 130, 224, 40, 200).setGroup(group);
		controlP5.addTextlabel(groupName+"Params", "Params", 10,210).setGroup(group);
		controlP5.addKnob(groupName+"P1", 0, 1, 0, 10, 219, 20).setGroup(group);
		controlP5.addKnob(groupName+"P2", 0, 1, 0, 50, 219, 20).setGroup(group);
		controlP5.addKnob(groupName+"P3", 0, 1, 0, 90, 219, 20).setGroup(group);

		controlP5.addTextlabel(groupName+"CameraPosition", "Camera Position ", 10,260).setGroup(group);		
		controlP5.addKnob(groupName+"CX", 0, 1, 0.5f, 10, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CY", 0, 1, 0.5f, 50, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CZ", 0, 1, 0.5f, 90, 270, 20).setGroup(group);
		
		controlP5.addTextlabel(groupName+"CameraRotation", "Camera Rotation ", 10,305).setGroup(group);
		controlP5.addKnob(groupName+"RX", 0, 1, 0.5f, 10, 315, 20).setGroup(group);
		controlP5.addKnob(groupName+"RY", 0, 1, 0.5f, 50, 315, 20).setGroup(group);
		controlP5.addKnob(groupName+"RZ", 0, 1, 0.5f, 90, 315, 20).setGroup(group);
		
		controlP5.addKnob(groupName+"FOV", 0, 1, 40, 10, 350, 60).setGroup(group);
		
		controlP5.addToggle(groupName+"Pause",   false, 80, 350, 15, 15).setGroup(group);
		controlP5.addButton(groupName+"Reset",0, 80,390,30,20).setGroup(group);
		controlP5.addButton(groupName+"Close",   0, 10, 430,40,20).setGroup(group);
		controlP5.addButton(groupName+"OutClose",0, 60, 430,50,20).setGroup(group);
		
		sketchSelector = controlP5.addDropdownList(groupName+"Select_Sketch",55,204,130,250);
		
		sketchSelector.setGroup(group);
		sketchSelector.setLabel("Select Sketch");
		
		  for(int i=0;i<Sketchs.size();i++) {
			    sketchSelector.addItem(Sketchs.get(i),i);
			  }
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);
		
		sketchSelector.setId(channel*100);
		controlP5.controller(groupName+"Alpha").setId(channel*100+1);	
		controlP5.controller(groupName+"P1").setId(channel*100+2);		
		controlP5.controller(groupName+"P2").setId(channel*100+3);
		controlP5.controller(groupName+"P3").setId(channel*100+4);
		controlP5.controller(groupName+"CX").setId(channel*100+5);
		controlP5.controller(groupName+"CY").setId(channel*100+6);
		controlP5.controller(groupName+"CZ").setId(channel*100+7);
		controlP5.controller(groupName+"RX").setId(channel*100+8);
		controlP5.controller(groupName+"RY").setId(channel*100+9);
		controlP5.controller(groupName+"RZ").setId(channel*100+10);		
		controlP5.controller(groupName+"FOV").setId(channel*100+11);			
		controlP5.controller(groupName+"Pause").setId(channel*100+12);
		controlP5.controller(groupName+"Reset").setId(channel*100+13);
		controlP5.controller(groupName+"Close").setId(channel*100+14);
		controlP5.controller(groupName+"OutClose").setId(channel*100+15);
		
		controlP5.controller(groupName+"P1").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"P2").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"P3").captionLabel().style().marginLeft = 16;
		
		controlP5.controller(groupName+"P1").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"P2").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"P3").captionLabel().style().marginTop = -3;		
		
		controlP5.controller(groupName+"CX").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"CY").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"CZ").captionLabel().style().marginLeft = 16;

		controlP5.controller(groupName+"CX").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"CY").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"CZ").captionLabel().style().marginTop = -3;
		
		controlP5.controller(groupName+"RX").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"RY").captionLabel().style().marginLeft = 16;
		controlP5.controller(groupName+"RZ").captionLabel().style().marginLeft = 16;
		
		controlP5.controller(groupName+"RX").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"RY").captionLabel().style().marginTop = -3;
		controlP5.controller(groupName+"RZ").captionLabel().style().marginTop = -3;

		controlP5.controller(groupName+"Alpha").plugTo(this);
		controlP5.controller(groupName+"Alpha").setAutoUpdate(true);
		controlP5.controller(groupName+"Alpha").setLabel("Alpha");

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
		controlP5.controller(groupName+"P1").setCaptionLabel("P1");
		
		controlP5.controller(groupName+"P2").plugTo(this);
		controlP5.controller(groupName+"P2").setCaptionLabel("P2");
		
		controlP5.controller(groupName+"P3").plugTo(this);
		controlP5.controller(groupName+"P3").setCaptionLabel("P3");
		
		controlP5.controller(groupName+"CX").plugTo(this);
		controlP5.controller(groupName+"CX").setAutoUpdate(true);
		controlP5.controller(groupName+"CX").setCaptionLabel("CX");
		
		controlP5.controller(groupName+"CY").plugTo(this);
		controlP5.controller(groupName+"CY").setAutoUpdate(true);
		controlP5.controller(groupName+"CY").setCaptionLabel("CY");
		
		controlP5.controller(groupName+"CZ").plugTo(this);
		controlP5.controller(groupName+"CZ").setAutoUpdate(true);
		controlP5.controller(groupName+"CZ").setCaptionLabel("CZ");		
		
		controlP5.controller(groupName+"RX").plugTo(this);
		controlP5.controller(groupName+"RX").setAutoUpdate(true);
		controlP5.controller(groupName+"RX").setCaptionLabel("RX");

		controlP5.controller(groupName+"RY").plugTo(this);
		controlP5.controller(groupName+"RY").setAutoUpdate(true);
		controlP5.controller(groupName+"RY").setCaptionLabel("RY");

		controlP5.controller(groupName+"RZ").plugTo(this);
		controlP5.controller(groupName+"RZ").setAutoUpdate(true);
		controlP5.controller(groupName+"RZ").setCaptionLabel("RZ");
	}
	
	public void event(int e){
		sketch.event(e);
	}
	
	public void controlEvent(ControlEvent theEvent) {		
		  int channel = p.floor(theEvent.controller().id() / 100);
		  //p.println("got a control event from controller with id "+theEvent.controller().id());
		  //p.println("got a control event from controller with name "+theEvent.controller().label());
		  //p.println("got a control event from controller with channel "+channel);
		  
		  if (theEvent.isGroup()) {
			    // check if the Event was triggered from a ControlGroup
			    p.println(theEvent.group().value()+" from "+theEvent.group());
		  }
		  
		  switch(theEvent.controller().id()){
		  case 100:
		  case 200:
		  case 300:			  
			  p.println("SELECT");
			  break;
		  case 101:
		  case 201:
		  case 301:			  
			  //imageController.setAlpha(theEvent.controller().value());
			  //setAlpha()
			  //p.println(Sketch1Alpha + " "+Sketch2Alpha + " "+Sketch3Alpha  );
			  break;
		  case 102:
		  case 202:
		  case 302:
			  sketch.setParameter(1,theEvent.controller().value());
			  break;
		  case 103:
		  case 203:
		  case 303:			  
			  sketch.setParameter(2,theEvent.controller().value());
			  break;
		  case 104:
		  case 204:
		  case 304:
			  sketch.setParameter(3,theEvent.controller().value());
			  break;
		  case 105:
		  case 205:
		  case 305:
			  sketch.setCameraX(theEvent.controller().value());
			  break;
		  case 106:
		  case 206:
		  case 306:
			  sketch.setCameraY(theEvent.controller().value());
			  break;	
		  case 107:
		  case 207:
		  case 307:
			  sketch.setCameraZ(theEvent.controller().value());
			  break;
		  case 108:
		  case 208:
		  case 308:
			  sketch.setSpeedX(theEvent.controller().value());
			  //sketch.setRotationX(theEvent.controller().value());
			  break;
		  case 109:
		  case 209:
		  case 309:
			  sketch.setSpeedY(theEvent.controller().value());
			  //sketch.setRotationY(theEvent.controller().value());
			  break;			  
		  case 110:
		  case 210:
		  case 310:
			  sketch.setSpeedZ(theEvent.controller().value());
			  //sketch.setRotationZ(theEvent.controller().value());
			  break;
		  case 111:
		  case 211:
		  case 311:	  
			  sketch.setFov(theEvent.controller().value());
			  break;
		  case 112:
		  case 212:
		  case 312:	  
			  //Pause
			  break;
			  
		  case 113:
		  case 213:
		  case 313:	//reset
			  Ani.to(this, delayCamera, "Sketch"+channel+"CX", 0.5f);
			  Ani.to(this, delayCamera, "Sketch"+channel+"CY", 0.5f);
			  Ani.to(this, delayCamera, "Sketch"+channel+"CZ", 0.5f);
			  Ani.to(this, delayCamera, "Sketch"+channel+"RX", 0.5f);
			  Ani.to(this, delayCamera, "Sketch"+channel+"RY", 0.5f);
			  Ani.to(this, delayCamera, "Sketch"+channel+"RZ", 0.5f);
			  sketch.resetCamera();
			  break;
		  case 114:
		  case 214:
		  case 314:
			  clearSketch();
			  break;
		  case 115:
		  case 215:
		  case 315:
			  //Ani.to(this, delayCamera, "Sketch"+channel+"Alpha", 0);
			  Ani a = new Ani(this, delayCamera, "Sketch"+channel+"Alpha", 0, Ani.CIRC_OUT, "onEnd:clearSketch");  
			  break;
		  default:
			  p.println("Warning. Unmaped event");
			  break;
		  }		  
	}
	
	public GLTexture getTexture(){
		return sketch.getTexture();
	}
	
	public void update(){
		sketch.begin();
		sketch.draw();
		sketch.end();
	}
	
	/**
	 * Sets a sketch in this channel
	 * 
	 * @param sName int identifier of the Sketch
	 */
	public void setSketch(int sName) {
		setSketch(Sketchs.get(sName));
	}
	
	/**
	 * Sets a sketch in this channel
	 * 
	 * @param sName Name of the Sketch
	 */
	public void setSketch(String sName) {
		try {
			sketch = null;
			Object [] args = new Object[3];
			args[0] = p;
			args[1] = OutputWindow.getWidth();
			args[2] = OutputWindow.getHeight();
			
			Class c = Class.forName("p4sketch."+sName);
			Constructor cons = c.getConstructor(PApplet.class, int.class, int.class);
			sketch = (Sketch) cons.newInstance(args);	
		} catch (Exception e) {
			p.println("* Error loading sketch: " + sName + "Exception: " +e);
			sketchSelector.setLabel("Select Skech");
		}
	}
	
	public void clearSketch(){
		sketchSelector.setValue(0);
	}
	
	public void resetSketch(){
		int index = (int)sketchSelector.getValue();
		setSketch(index);
	}
		
	public float getAlpha(){
		switch(channel){
		case 1:
			return Sketch1Alpha;
		case 2:
			return Sketch2Alpha;
		case 3:
			return Sketch3Alpha;
		default:
			return 0;
		}
		
	}
	
	/**
	 * Load all the Sketches from sketch folder 
	 */
	void loadVisuals() {
		p.println();
		p.println("Available Sketches in:");
		p.println(sketchPath);
		p.println("------------------------");
		// String[] filenames = listFileNames(sketchPath);

		String name = "";

		File[] files = listFiles(sketchPath);
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			name = f.getName();
			if (name.endsWith(".java")) {
				name = name.replace(".java", "");
				p.println(name + " \t\t " + f.length() + " bytes");
				Sketchs.add(name);
			}
		}
		p.println();
		String msj = "* Loaded " + Sketchs.size() + " sketchs.";
		p.println(msj);
	}
	
	/**
	 * This function returns all the files in a directory as an array of File objects
	 * his is useful if you want more info about the file
	 * @param dir Directory for search
	 * @return Array of File Objects with all the files in directory
	 */
	public File[] listFiles(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			return files;
		} else {
			// If it's not a directory
			return null;
		}
	}

	/*public void noteOn(int channel2, int pitch, int velocity) {
		sketch.noteOn(channel2, pitch, velocity);		
	}

	public void noteOff(int channel2, int pitch, int velocity) {
		sketch.noteOff(channel2, pitch, velocity);
	}*/
}
