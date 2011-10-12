package p4live;

import processing.core.PApplet;
import processing.core.PImage;
import controlP5.Button;
import controlP5.ControlBehavior;
import controlP5.ControlP5;
import controlP5.ControlP5XMLElement;
import controlP5.Controller;
import controlP5.DropdownList;
import controlP5.Knob;
import controlP5.Slider;
import controlP5.Tab;
import controlP5.Toggle;
import themidibus.MidiBus;


public class ControlSketch extends Control{
	private DropdownList ddl1;
	private Sketch sketch;
	private ImageSketch imageSketch;
	
	ControlSketch(String s, int dx, int dy){
		groupName = s;
		defaultX = dx;
		defaultY = dy;
		defaultWidth = 200;
		defaultHeight= 450;
		sketch = new TestScreen(p, defaultHeight, defaultHeight);
		imageSketch = new ImageSketch(controlP5,groupName+"Preview",10,10,180,130);
		controlP5.register(imageSketch);
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		imageSketch.setGroup(group);

		controlP5.addSlider(groupName+"Alpha", 0, 1, 1, 130, 230, 40, 200).setGroup(group);
		controlP5.addTextlabel(groupName+"Params", "Params", 10,220).setGroup(group);
		controlP5.addKnob(groupName+"P1", 0, 1, 0.5f, 10, 230, 20).setGroup(group);
		controlP5.addKnob(groupName+"P2", 0, 1, 0.5f, 50, 230, 20).setGroup(group);
		controlP5.addKnob(groupName+"P3", 0, 1, 0.5f, 90, 230, 20).setGroup(group);

		controlP5.addTextlabel(groupName+"Camera", "Camera", 10,260).setGroup(group);		
		controlP5.addKnob(groupName+"CX", 0, 1, 0.5f, 10, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CY", 0, 1, 0.5f, 50, 270, 20).setGroup(group);
		controlP5.addKnob(groupName+"CZ", 0, 1, 0.5f, 90, 270, 20).setGroup(group);
		
		controlP5.addTextlabel(groupName+"Zoom", "Zoom", 10,300).setGroup(group);
		controlP5.addKnob(groupName+"ZX", 0, 1, 0.5f, 10, 310, 20).setGroup(group);
		controlP5.addKnob(groupName+"ZY", 0, 1, 0.5f, 50, 310, 20).setGroup(group);
		controlP5.addKnob(groupName+"ZZ", 0, 1, 0.5f, 90, 310, 20).setGroup(group);
		
		controlP5.addKnob(groupName+"FOV", 0, 360, 40, 10, 350, 60).setGroup(group);
		
		controlP5.addToggle(groupName+"Pause",   false, 80, 350, 15, 15).setGroup(group);
		controlP5.addButton(groupName+"Reset",0, 80,390,30,20).setGroup(group);
		controlP5.addButton(groupName+"Close",   0, 10, 420,40,20).setGroup(group);
		controlP5.addButton(groupName+"OutClose",0, 60, 420,50,20).setGroup(group);
		
		ddl1 = controlP5.addDropdownList(groupName+"Select_Sketch",60,213,110,10);
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
		
		Knob k = (Knob) controlP5.controller(groupName+"CX");
		k.setLabelVisible(true);
		k.setLabel("X");
		
		Slider s = (Slider) controlP5.controller(groupName+"Alpha");
		s.setLabel("Alpha");

		Toggle t = (Toggle) controlP5.controller(groupName+"Pause");
		t.setLabel("Pause");

		Button b1 = (Button) controlP5.controller(groupName+"Reset");
		b1.setLabel("Reset");
		
		Button b2 = (Button) controlP5.controller(groupName+"Close");
		b2.setLabel("Close");
		
		Button b3 = (Button) controlP5.controller(groupName+"OutClose");
		b3.setLabel("Out&Close");
		
	}
}

class ImageSketch extends Controller {
	PImage _myImage;

	  ImageSketch(ControlP5 theControlP5, String theName, int theX, int theY, int theWidth, int theHeight) {
	    // the super class Controller needs to be initialized with the below parameters
	    super(theControlP5,  (Tab)(theControlP5.getTab("default")), theName, theX, theY, theWidth, theHeight);
	    _myImage = null;
	    this.setCaptionLabel("Preview");
	  }

	  // overwrite the updateInternalEvents method to handle mouse and key inputs.
	  public void updateInternalEvents(PApplet p) {
	  }

	  // overwrite the draw method for the controller's visual representation.
	  public void draw(PApplet p) {
	    // use pushMatrix and popMatrix when drawing
	    // the controller.
	    p.pushMatrix();
	    p.translate(position().x(), position().y());
	    // draw the background of the controller.	    
	    /*if(getIsInside()) {
	      p.fill(150);
	    } 
	    else {
	      p.fill(100);
	    }*/
	    p.fill(0);
	    p.rect(0,0,width,height);

	    // draw the caption- and value-label of the controller
	    // they are generated automatically by the super class
	    captionLabel().draw(p, 0, height + 4);

	    p.popMatrix();
	  } 

	  public void setValue(float theValue) {
	    // setValue is usually called from within updateInternalEvents
	    // in case of changes, updates. the update of values or 
	    // visual elements is done here.

	    // broadcast triggers a ControlEvent, updates are made to the sketch, 
	    // controlEvent(ControlEvent) is called.
	    // the parameter (FLOAT or STRING) indicates the type of 
	    // value and the type of methods to call in the main sketch.
	    //broadcast(FLOAT);
	  }

	  // needs to be implemented since it is an abstract method in controlP5.Controller
	  // nothing needs to be set since this method is only relevant for saving 
	  // controller settings and only applies to (most) default Controllers.
	  public void addToXMLElement(ControlP5XMLElement theElement) {
	  }
	}
