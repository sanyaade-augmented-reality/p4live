package p4control;

import p4live.Events;
import controlP5.ControlBehavior;
import controlP5.Slider;
import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.analysis.BeatDetect;

public class Beats extends Control{
	private BeatDetect beats; 					// The BeatDetect main object
	private BeatListener bl; 					// A beat listener for the beat object
	
	public static float kick;
	public static float snare;
	public static float hat;
		
	public Beats(){
		groupName = "Beats";
		defaultHeight = 130;
		defaultWidth = 150;
		defaultX = 72;
		defaultY = defaultY+10;
		
		beats = new BeatDetect(in.bufferSize(), in.sampleRate());
		bl = new BeatListener(beats, in);
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addSlider("kick", 0,1,0,10,10,20,100).setGroup(group).plugTo(this);
		controlP5.addSlider("snare",0,1,0,40,10,20,100).setGroup(group).plugTo(this);
		controlP5.addSlider("hat",  0,1,0,70,10,20,100).setGroup(group).plugTo(this);		
		controlP5.addSlider("Sensitivity",10,10000,128,110,10,10,100).setGroup(group).plugTo(this);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(defaultWidth);
		group.setBackgroundHeight(defaultHeight);
		
		Slider kick = (Slider)controlP5.controller("kick");
		kick.setBehavior(new kickUpdate());
		kick.setColorForeground(p.color(255,0,0));
		kick.valueLabel().style().padding(-2, 0, 0, -22);
		
		Slider snare = (Slider)controlP5.controller("snare");
		snare.setBehavior(new snareUpdate());
		snare.setColorForeground(p.color(0,255,0));
		snare.valueLabel().style().padding(-2, 0, 0, -22);

		Slider hat = (Slider)controlP5.controller("hat");
		hat.setBehavior(new hatUpdate());
		hat.setColorForeground(p.color(0,0,255));
		hat.valueLabel().style().padding(-2, 0, 0, -22);
		
		Slider sensitivity = (Slider)controlP5.controller("Sensitivity");
		sensitivity.setDecimalPrecision(1);
		sensitivity.setMin(10);
		sensitivity.setMax(10000);
		sensitivity.captionLabel().style().setPaddingLeft(-15);
	}
	
	public void setSensitivity(float v){
		int s=(int)p.map(v,0,1,10,10000);
		Sensitivity(s);
	}
	
	private void Sensitivity(int v){
		beats.setSensitivity(v); 
	}
	
	private class kickUpdate extends ControlBehavior {
		  public kickUpdate() {  }
		  public void update() {
			  if(beats.isKick()){
				  setValue(1);
				  Events.kick();
			  }
			  else
				  setValue(p.constrain((float) (value * 0.9), 0, 1));
		  }
		}
	
	private class snareUpdate extends ControlBehavior {
		  public snareUpdate() {  }
		  public void update() {
			  if(beats.isSnare()){
				  setValue(1);
				  Events.snare();
			  }
			  else
				  setValue(p.constrain((float) (value * 0.9), 0, 1));
		  }
		}
	private class hatUpdate extends ControlBehavior {
		  public hatUpdate() {  }
		  public void update() {
			  if(beats.isHat()){
				  setValue(1);
				  Events.hat();
			  }
			  else
				  setValue(p.constrain((float) (value * 0.9), 0, 1));
		  }
		}
	private class BeatListener implements AudioListener
	{
	  private BeatDetect beat;
	  private AudioInput source;
	  
		BeatListener(BeatDetect beat, AudioInput source) {
			this.source = source;
			this.source.addListener(this);
			this.beat = beat;
		}
	  
		public void samples(float[] samps) {
			beat.detect(source.mix);
		}

		public void samples(float[] sampsL, float[] sampsR) {
			beat.detect(source.mix);
		}
	}
}
