package p4live;

import p4live.ControlMidi.TimedEvent;
import controlP5.CColor;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Knob;
import controlP5.Slider;

public class ControlBPM extends Control{
	
	private int actualMS=1;
	private int lastBeat=1;
	private int nextBeat=1;
	private int actualBeat=1;

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
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(defaultWidth);
		group.setBackgroundHeight(defaultHeight);
		
		Knob bpm = (Knob)controlP5.controller("bpm");
		bpm.setBehavior(new SoundUpdate());
		//bpm.setMin(0);
		//bpm.setMax(1);
				
		Slider bpmV = (Slider)controlP5.controller("bpmVelocity");
		bpmV.setDecimalPrecision(0);
		//bpmV.setMax(200);
		//bpmV.setMin(10);
		//bpmV.setMax(200);
		//bpmVelocity.setDefaultValue(140);
		//bpmVelocity.setValue(140);		
		setBPM(120);
	}
	
	public float getBPM(){
		Slider bpmVelocity = (Slider)controlP5.controller("bpmVelocity");	
		return bpmVelocity.value();
	}
	
	public void setBPM(float bpm) {
		//Slider bpmVelocity = (Slider)controlP5.controller("bpmVelocity");
		//bpmVelocity.setValue(bpm);
		//actualMS = (int) (bpm * 16.67);
		actualMS = (int) ((60/bpm)*1000);
		
		p.println(actualMS);
		
		syncBPM();
	}
		
	//#############
	
	//reset BPM
	public void syncBPM() {
		//nextBeat = actualMS;
		lastBeat = p.millis();
		nextBeat = lastBeat + actualMS;	}
	
	/**
	 * Tap BPM
	 */
	/*public void tapBPM() {
		int time = p.millis() - lastMillis;
		lastMillis = p.millis();
		float bpmAux = time * 0.06f;
		//p.println("BPMAux: "+bpmAux);
		setBPM(bpmAux);
	}*/

	/**
	 * Update the level of BPM
	 */
	/*void updateBPM() {
		timeLapsed = p.millis() - tapBPM;
		beat = nextBeat - timeLapsed;
		BPMLevel = p.map(beat, 0, actualMS, 0, 1);
		if (beat < 0) {
			actualBeat++;
			nextBeat = actualMS * actualBeat;
			p4vj.events.event(p4vj.events._BPM_);
		}
	}*/
	
	private class SoundUpdate extends ControlBehavior {
		  public SoundUpdate() {  }
		  public void update() { 
				//float timeLapsed = p.millis() - lastBeat;
				float timeLapsed = p.millis();// - lastBeat;
				//float beat = nextBeat - timeLapsed;
				setValue( p.norm(timeLapsed, lastBeat, nextBeat));
				if (timeLapsed > nextBeat) {
					//actualBeat++;
					lastBeat = p.millis();
					nextBeat = lastBeat + actualMS;
					p.println("***********************Beat!");
				}
				//p.println("BPM: "+getBPM()+" lB: " + lastBeat + " nB: " + nextBeat + " TIME: "+actualMS +" tL:" + timeLapsed);
		  }
		}
}
