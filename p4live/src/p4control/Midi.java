package p4control;

import controlP5.ControlBehavior;
import controlP5.DropdownList;
import controlP5.Toggle;
import themidibus.MidiBus;


public class Midi extends Control{
	private MidiBus busA;
	//MidiBus busB;
	private DropdownList ddl2;
	private DropdownList ddl1;
	
	public Midi(){
		groupName = "Midi";
		defaultX = 0;
		defaultY = 310;
		defaultWidth = 198;
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
			p.println("[Warning] No MIDI inputs detected");
	}
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addToggle("Control", false, 10, 10, 10, 10).setGroup(group);
		controlP5.addToggle("Sound",   false, 10, 40, 10, 10).setGroup(group);
		controlP5.addButton("Map",0, 10,70,30,20).setGroup(group);
		controlP5.addButton("Load",0,80,70,30,20).setGroup(group);
		controlP5.addButton("Save",0,120,70,30,20).setGroup(group);
		ddl2 = controlP5.addDropdownList("Sound_Channel",30,50,100,50);
		ddl2.setGroup(group);
		ddl1 = controlP5.addDropdownList("Control_Channel",30,20,100,70);
		ddl1.setGroup(group);

		  for(int i=0;i<12;i++) {
			    ddl1.addItem("Channel "+i,i);
			  }
		  for(int i=0;i<12;i++) {
			    ddl2.addItem("Channel "+i,i);
			  }
	}
	
	public static void pingComunication(){
		Toggle ping = (Toggle)controlP5.controller("Control");
		ping.changeValue(1);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);
		
		Toggle t = (Toggle)controlP5.controller("Control");
		t.setBehavior(new TimedEvent());
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
}
