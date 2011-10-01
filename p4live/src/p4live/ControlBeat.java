package p4live;

import processing.core.PFont;
import controlP5.ControlBehavior;
import controlP5.ControlFont;
import controlP5.ControlGroup;
import controlP5.Slider;
import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.analysis.BeatDetect;

public class ControlBeat extends Control{
	BeatDetect beats; 					// The BeatDetect main object
	BeatListener bl; 					// A beat listener for the beat object
	//ControlFont font;
	//PFont pfont;
	
	ControlBeat(){
		groupName = "Beat";
		defaultHeight = 140;
		defaultWidth = 200;
		defaultX = 400;
		defaultY = 200;
		
		//PFont pfont = p.createFont("Times",20,true); // use true/false for smooth/no-smooth
		//font = new ControlFont(pfont);

		beats = new BeatDetect(in.bufferSize(), in.sampleRate());
		bl = new BeatListener(beats, in);
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		controlP5.addSlider("kick",0,200,188,20,100,10,100).setGroup(group);
		controlP5.addSlider("snare",0,200,128,20,100,10,100).setGroup(group);
		controlP5.addSlider("hat",0,200,128,20,100,10,100).setGroup(group);		
		controlP5.addSlider("Sensitivity",0,200,128,20,100,10,100).setGroup(group);
	}
	
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setWidth(defaultWidth);
		group.setBackgroundHeight(defaultHeight);
		
		Slider kick = (Slider)controlP5.controller("kick");
		kick.setBehavior(new kickUpdate());
		kick.setColorForeground(p.color(255,0,0));
		kick.setSize(20,100);
		kick.setMin(0);
		kick.setMax(1);
		//kick.label().
		//kick.setLabelVisible(false);
		//kick.lock();
		
		Slider snare = (Slider)controlP5.controller("snare");
		snare.setBehavior(new snareUpdate());
		snare.setColorForeground(p.color(0,255,0));
		snare.setSize(20,100);
		snare.setMin(0);
		snare.setMax(1);
		snare.captionLabel().style().marginLeft = 0;
		snare.captionLabel().style().paddingLeft = 0;
		//snare.setLabelVisible(false);
		//snare.lock();

		Slider hat = (Slider)controlP5.controller("hat");
		hat.setBehavior(new hatUpdate());
		hat.setColorForeground(p.color(0,0,255));
		hat.setSize(20,100);
		hat.setMin(0);
		hat.setMax(1);
		//hat.setLabelVisible(false);
		//hat.lock();
		
		Slider sensitivity = (Slider)controlP5.controller("Sensitivity");
		//kickS.setColorForeground(p.color(120,0,0));
		//kickS.setColorActive(p.color(180,0,0));
		sensitivity.setDecimalPrecision(0);
		sensitivity.setMin(10);
		sensitivity.setMax(10000);
	}
	
	private class kickUpdate extends ControlBehavior {
		  public kickUpdate() {  }
		  public void update() {
			  if(beats.isKick())
				  setValue(1);
			  else
				  setValue(p.constrain((float) (value * 0.9), 0, 1));
		  }
		}
	
	private class snareUpdate extends ControlBehavior {
		  public snareUpdate() {  }
		  public void update() {
			  if(beats.isSnare())
				  setValue(1);
			  else
				  setValue(p.constrain((float) (value * 0.9), 0, 1));
		  }
		}
	private class hatUpdate extends ControlBehavior {
		  public hatUpdate() {  }
		  public void update() {
			  if(beats.isHat())
				  setValue(1);
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
