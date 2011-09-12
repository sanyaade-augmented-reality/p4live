package p4live;

import controlP5.ControlGroup;
import controlP5.Slider;

public class ControlBeat extends Control{
	
	ControlBeat(){
		groupName = "Beat Analisys";
		build();
		setPreferences();
	}	
	
	private void build() {		
		group = controlP5.addGroup(groupName, 200, 100, 200);
		
		controlP5.addSlider("kick",0,200,188,20,100,10,100).setGroup(group);
		controlP5.addSlider("kickSensitivity",0,200,128,20,100,10,100).setGroup(group);

		controlP5.addSlider("snare",0,200,128,20,100,10,100).setGroup(group);
		controlP5.addSlider("snareSensitivity",0,200,128,20,100,10,100).setGroup(group);

		controlP5.addSlider("hat",0,200,128,20,100,10,100).setGroup(group);		
		controlP5.addSlider("hatSensitivity",0,200,128,20,100,10,100).setGroup(group);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		group.setWidth(260);
		group.setBackgroundHeight(140);
		
		Slider kick = (Slider)controlP5.controller("kick");
		kick.setColorForeground(p.color(255,0,0));
		kick.setSize(20,100);
		kick.setMin(0);
		kick.setMax(1);
		kick.setLabelVisible(false);
		//kick.lock();
		
		Slider snare = (Slider)controlP5.controller("snare");
		snare.setColorForeground(p.color(0,255,0));
		snare.setSize(20,100);
		snare.setMin(0);
		snare.setMax(1);
		snare.setLabelVisible(false);
		//snare.lock();

		Slider hat = (Slider)controlP5.controller("hat");
		hat.setColorForeground(p.color(0,0,255));
		hat.setSize(20,100);
		hat.setMin(0);
		hat.setMax(1);
		hat.setLabelVisible(false);
		//hat.lock();
		
		Slider kickS = (Slider)controlP5.controller("kickSensitivity");
		kickS.setColorForeground(p.color(120,0,0));
		kickS.setColorActive(p.color(180,0,0));
		kickS.setDecimalPrecision(0);
		kickS.setMin(1);
		kickS.setMax(10000);

		Slider snareS = (Slider)controlP5.controller("snareSensitivity");
		snareS.setColorForeground(p.color(0,120,0));
		snareS.setColorActive(p.color(0,180,0));
		snareS.setDecimalPrecision(0);
		snareS.setMin(1);
		snareS.setMax(10000);
		
		Slider hatS = (Slider)controlP5.controller("hatSensitivity");
		hatS.setColorForeground(p.color(0,0,120));
		hatS.setColorActive(p.color(0,0,180));
		hatS.setDecimalPrecision(0);
		hatS.setMin(1);
		hatS.setMax(10000);
	}
}
