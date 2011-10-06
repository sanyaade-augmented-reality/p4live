package p4live;

import controlP5.Button;
import controlP5.ControlBehavior;
import controlP5.Knob;
import controlP5.Slider;

public class ControlBPM extends Control{
	
	private int actualMS=1;
	private int lastBeat=1;
	private int nextBeat=1;
	private int lastTap=0;

	ControlBPM(){
		groupName = "BPM";
		defaultHeight = 140;
		defaultWidth = 200;
		defaultX = 200;
		defaultY = 300;
		build();
		setPreferences();
	}	
	
	private void build() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addKnob("bpm",0,1,140,100,160,100).setGroup(group);	
		controlP5.addSlider("bpmVelocity",10,200,120,20,100,20,100).setGroup(group);
		controlP5.addButton("Tap",100,100,0,20,20).setGroup(group);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(defaultWidth);
		group.setBackgroundHeight(defaultHeight);
		
		Knob bpm = (Knob)controlP5.controller("bpm");
		bpm.setBehavior(new SoundUpdate());
				
		Slider bpmV = (Slider)controlP5.controller("bpmVelocity");
		bpmV.setDecimalPrecision(1);
		bpmV.plugTo(this);
		
		Button tap = (Button)controlP5.controller("Tap");
		tap.plugTo(this);
		bpmVelocity(120);
	}
	
	public void bpmVelocity(float bpm){
		actualMS = (int) ((60/bpm)*1000);
		lastBeat = p.millis();
		nextBeat = lastBeat + actualMS;
		}
	
	public float getBPM(){
		Slider bpmVelocity = (Slider)controlP5.controller("bpmVelocity");	
		return bpmVelocity.value();
	}

	public void Tap(int v) {
		int time = p.millis() - lastTap;
		lastTap = p.millis();
		float bpmAux = 60000 / time;
		controlP5.controller("bpmVelocity").setValue(bpmAux);
	}
	
	private class SoundUpdate extends ControlBehavior {
		  public SoundUpdate() {  }
		  public void update() { 
				float timeLapsed = p.millis();
				setValue( p.norm(timeLapsed, lastBeat, nextBeat));
				if (timeLapsed > nextBeat) {
					lastBeat = p.millis();
					nextBeat = lastBeat + actualMS;
					this.controller().setColorBackground(p.color(255,0,10));
				}
				else
					this.controller().setColorBackground(p.color(128,0,30));
				
		  }
		}
}
