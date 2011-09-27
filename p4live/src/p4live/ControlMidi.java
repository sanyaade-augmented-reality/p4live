package p4live;

import controlP5.CColor;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.DropdownList;
import controlP5.Slider;
import controlP5.Toggle;
import themidibus.MidiBus;


public class ControlMidi extends Control{

	//DropdownList ddl1;
	MidiBus busA;
	//MidiBus busB;
	
	ControlMidi(){
		groupName = "Midi";
		defaultX = 300;
		defaultY = 400;
		defaultWidth = 200;
		startMIDI();
		buildInterface();
		setPreferences();
	}	
	
	private void startMIDI(){
		p.println("* Initializing MIDI...");
		String[] available_inputs = MidiBus.availableInputs(); //Returns an array of available input devices
		if (available_inputs.length>0){
			p.println("Detected MIDI inputs:");
			for(int i = 0;i < available_inputs.length;i++)
				p.println("["+i+"] \""+available_inputs[i]+"\"");
			busA = new MidiBus(p, 0, 0);
			}
		else
			p.println("No MIDI inputs detected");
	}
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		//ddl1 = controlP5.addDropdownList("Midi_Bus",100,100,100,120).setGroup(group);
		controlP5.addToggle("Internal_Control", true,100, 100, 10, 10).setGroup(group);
		controlP5.addToggle("External_Sound",false,  100,240, 10,10).setGroup(group);
		controlP5.addButton("Map",128,100,0,30,20).setGroup(group);
		controlP5.addButton("Load",128,100,0,30,20).setGroup(group);
		controlP5.addButton("Save",128,100,0,30,20).setGroup(group);
	}
	
	public void pingComunication(){
		Toggle ping = (Toggle)controlP5.controller("Internal_Control");
		ping.changeValue(1);
	}
	
	class TimedEvent extends ControlBehavior {
	  long myTime;
	  int interval = 500;

	  public TimedEvent() { reset(); }
	  void reset() { myTime = p.millis(); }

	  public void update() {
	    if(p.millis()>myTime+interval){
	    	setValue(0); reset(); }
	  }
	}
/*	void customize(DropdownList ddl) {
		  ddl.setBackgroundColor(color(190));
		  ddl.setItemHeight(20);
		  ddl.setBarHeight(15);
		  ddl.captionLabel().set("pulldown");
		  ddl.captionLabel().style().marginTop = 3;
		  ddl.captionLabel().style().marginLeft = 3;
		  ddl.valueLabel().style().marginTop = 3;
		  for(int i=0;i<40;i++) {
		    ddl.addItem("item "+i,i);
		  }
		  ddl.scroll(0);
		  ddl.setColorBackground(color(60));
		  ddl.setColorActive(color(255,128));
		}*/
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(120);
		group.setBackgroundHeight(140);
		
		Toggle t = (Toggle)controlP5.controller("Internal_Control");
		t.setBehavior(new TimedEvent());
		//DropdownList ddl1 = (DropdownList)controlP5.controller("Midi_Bus");
	/*	Slider vol = (Slider)controlP5.controller("vol");
		//kick.setColorForeground(p.color(255,0,0));
		vol.setSize(20,100);
		vol.setMin(0);
		vol.setMax(1);
		vol.setLabelVisible(false);
		//kick.lock();
*/
	}
	

	
}
