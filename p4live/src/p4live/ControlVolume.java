package p4live;

import controlP5.CColor;
import controlP5.ControlGroup;
import controlP5.Slider;


public class ControlVolume extends Control{
	
	ControlVolume(){
		build();
		setPreferences();
	}	
	
	private void build() {		
		group = controlP5.addGroup("Volume", 200, 100, 200);
		
		controlP5.addSlider("vol",0,200,188,20,100,10,100).setGroup(group);
		controlP5.addSlider("volume",0,200,128,20,100,10,100).setGroup(group);
	}
	
	public void setPreferences(){
		group = (ControlGroup)controlP5.group("Volume");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		group.setWidth(80);
		group.setBackgroundHeight(120);	
		
		Slider vol = (Slider)controlP5.controller("vol");
		//kick.setColorForeground(p.color(255,0,0));
		vol.setSize(20,100);
		vol.setMin(0);
		vol.setMax(1);
		vol.setLabelVisible(false);
		//kick.lock();
		
		Slider volume = (Slider)controlP5.controller("volume");
		//kickS.setColorForeground(p.color(120,0,0));
		//kickS.setColorActive(p.color(180,0,0));
		//kickS.setDecimalPrecision(0);
		volume.setMin(1);
		volume.setMax(10);
		
		/*


public float getVolume()
    Returns the current volume. If a volume control is not available, this returns 0. Note that the volume is not the same thing as the level() of an AudioBuffer!

setVolume	
		 
		 */
	}
}
