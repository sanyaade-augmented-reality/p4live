package p4live;

import p4live.ControlMidi.TimedEvent;
import controlP5.CColor;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;

public class ControlVolume extends Control{
	
	ControlVolume(){
		groupName = "Volume";
		build();
		setPreferences();
	}	
	
	private void build() {		
		group = controlP5.addGroup(groupName, 200, 100, 200);
		controlP5.addSlider("vol",0,200,188,20,100,10,100).setGroup(group);
		controlP5.addSlider("Level",0,200,128,20,100,10,100).setGroup(group);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(80);
		group.setBackgroundHeight(140);
		
		Slider vol = (Slider)controlP5.controller("vol");
		vol.setBehavior(new SoundUpdate());
		//kick.setColorForeground(p.color(255,0,0));
		vol.setSize(20,100);
		vol.setMin(0);
		vol.setMax(1);
		vol.setLabelVisible(false);
		//kick.lock();
		
		Slider level = (Slider)controlP5.controller("Level");
		//kickS.setColorForeground(p.color(120,0,0));
		//kickS.setColorActive(p.color(180,0,0));
		level.setDecimalPrecision(1);
		level.setMin(1);
		level.setMax(10);
	}
	
	public float getVolume(){
		Slider volume = (Slider)controlP5.controller("Sensitivity");
		return in.mix.level()*volume.value();
	}
		
	private class SoundUpdate extends ControlBehavior {
		  public SoundUpdate() {  }
		  public void update() {
			  setValue(in.mix.level());  
		  }
		}
}
