package p4control;

import controlP5.CColor;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;

public class Volume extends Control{
	
	public static float level;
	
	public Volume(){
		groupName = "Volume";	
		defaultY = defaultY+10;
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addSlider("level",  0, 1,0,10,10,20,100).setGroup(group).plugTo(this);
		controlP5.addSlider("Gain",0,10,1,40,10,10,100).setGroup(group).plugTo(this);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(70);
		group.setBackgroundHeight(130);
		
		Slider vol = (Slider)controlP5.controller("level");
		vol.setBehavior(new SoundUpdate());
		//kick.setColorForeground(p.color(255,0,0));
		//vol.setSize(20,100);
		//vol.setMin(0);
		//vol.setMax(1);
		vol.setLabelVisible(false);
		//vol.captionLabel().setVisible(true);
		//kick.lock();
		
		Slider gain = (Slider)controlP5.controller("Gain");
		//kickS.setColorForeground(p.color(120,0,0));
		//kickS.setColorActive(p.color(180,0,0));
		gain.setDecimalPrecision(1);
		gain.setMin(1);
		gain.setMax(10);
	}
	
	public float getVolume(){
		Slider volume = (Slider)controlP5.controller("level");
		return volume.value();
	}
		
	private class SoundUpdate extends ControlBehavior {
		  public SoundUpdate() {  }
		  public void update() {
			  setValue(in.mix.level() *  ((Slider)controlP5.controller("Gain")).value());  
		  }
		}
}
