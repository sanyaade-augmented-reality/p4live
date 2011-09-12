package p4live;

import controlP5.CColor;
import controlP5.ControlGroup;
import controlP5.Slider;

public class ControlFFT extends Control{
	
	ControlFFT(){
		groupName="FFT Analisys";
		build();
		setPreferences();
	}	
	
	private void build() {		
		group = controlP5.addGroup(groupName, 200, 100, 200);
		
	}
	
	/*public void setPreferences(){
		group = (ControlGroup)controlP5.group("Beat Analisys");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		group.setWidth(290);
		group.setBackgroundHeight(120);	
		
		Slider kick = (Slider)controlP5.controller("kick");
		kick.setColorForeground(p.color(255,0,0));
		kick.setSize(20,100);
		kick.setMin(0);
		kick.setMax(1);
		kick.setLabelVisible(false);
		//kick.lock();
		
		
		Slider kickS = (Slider)controlP5.controller("kickSensitivity");
		kickS.setColorForeground(p.color(120,0,0));
		kickS.setColorActive(p.color(180,0,0));
		kickS.setDecimalPrecision(0);
		kickS.setMin(1);
		kickS.setMax(10000);

		
	}*/
}
