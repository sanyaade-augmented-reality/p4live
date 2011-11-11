package p4control;

import p4live.EventsMidi;
import controlP5.CColor;
import controlP5.ControlBehavior;
import controlP5.DropdownList;
import controlP5.Toggle;
import themidibus.MidiBus;

public class Midi extends Control{
	private MidiBus busA;
	private MidiBus busB;
	private DropdownList soundSelector;
	private DropdownList controlSelector;
	private static boolean maping = false;
	private CColor def;
	
	public Midi(){
		groupName = "Midi";
		defaultX = 0;
		defaultY = 460;
		defaultWidth = 198;
		startMIDI();
		buildInterface();
		setPreferences();
	}	
	
	public static boolean isMaping(){
		return maping;
	}
	
	public static void startMap(){
		maping = true;
		int col = p.color(200,0,0);
		int colAct = p.color(255,0,0);
		controlP5.controller("Map").setColorActive(colAct);		
		controlP5.controller("Map").setColorForeground(col);
		controlP5.controller("Map").setColorBackground(col);
		p.println("Start Maping");
	}

	public static void endMap(){
		maping = false;
		CColor col = controlP5.controller("Load").getColor();	
		controlP5.controller("Map").setColor(col);
		p.println("End Maping");
		EventsMidi.restartMaping();
	}
	
	
	private void startMIDI(){
		p.println("* Initializing MIDI...");
		String[] available_inputs = MidiBus.availableInputs(); //Returns an array of available input devices
		if (available_inputs.length>0){
			p.println("Detected MIDI inputs:");
			for(int i = 0;i < available_inputs.length;i++)
				p.println("["+i+"] \""+available_inputs[i]+"\"");
			busA = new MidiBus(p, 1, 1);
			busB = new MidiBus(p, 0, 0);			
			}
		else
			p.println("[Warning] No MIDI inputs detected");
	}
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addToggle("Control", false, 10, 10, 10, 10).setGroup(group).plugTo(this);
		controlP5.addToggle("Sound",   false, 10, 40, 10, 10).setGroup(group).plugTo(this);
		controlP5.addButton("Map",0, 10,70,30,20).setGroup(group).plugTo(this);
		controlP5.addButton("Load",0,80,70,30,20).setGroup(group).plugTo(this);
		controlP5.addButton("Save",0,120,70,30,20).setGroup(group).plugTo(this);
		soundSelector = controlP5.addDropdownList("Sound_Channel",30,50,100,50);
		soundSelector.setGroup(group);
		soundSelector.setId(3);
		controlSelector = controlP5.addDropdownList("Control_Channel",30,20,100,70);
		controlSelector.setGroup(group);
		controlSelector.setId(2);

		  for(int i=0;i<12;i++) {
			    controlSelector.addItem("Channel "+i,i);
			  }
		  for(int i=0;i<12;i++) {
			    soundSelector.addItem("Channel "+i,i);
			  }
	}
	
	private void Map(){
		if (maping){
			endMap();
		}
		else{
			startMap();
		}
	}
	
	private void Load(){
		EventsMidi.loadMaping();
	}
	
	private void Save(){
		EventsMidi.saveMaping();
	}
	
	public static void pingControl(){
		Toggle ping = (Toggle)controlP5.controller("Control");
		ping.changeValue(1);	
	}

	public static void pingSound(){
		Toggle ping = (Toggle)controlP5.controller("Sound");
		ping.changeValue(1);	
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);
		
		Toggle control = (Toggle)controlP5.controller("Control");
		control.setBehavior(new TimedEvent());

		Toggle sound = (Toggle)controlP5.controller("Sound");
		sound.setBehavior(new TimedEvent());		
	}
	
	class TimedEvent extends ControlBehavior {
	  long myTime;
	  int interval = 300;

	  public TimedEvent() { reset(); }
	  void reset() { myTime = p.millis(); }

	  public void update() {
	    if(p.millis()>myTime+interval){
	    	setValue(0); reset(); }
	  }
	}
}
