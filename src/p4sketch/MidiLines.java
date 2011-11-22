package p4sketch;

import java.util.ArrayList;

import p4live.EventsMidi;
import p4live.OutputWindow;
import p4sketch.ProcessingOriginal.Ball;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class MidiLines extends Sketch {
	float x = 0;  //increments the circle
	int y = 0;
	int frameRt = 30;
	int tempo = 100; //beats per minute
	int beats = 32; //number of beats per revolution
	int backgroundColor=0;
	int count = 0;
	
	CircleLine clineList;
	
	public MidiLines(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);		
		clineList = new CircleLine(frameRt, tempo, beats);
		  colorMode(RGB,255);
		  background(backgroundColor);
		  smooth();
	}

	public void draw() {
	  noStroke();
	  //draw a semi-transparent rectangle every 4 frames
	  if (count % 8 == 0){
	    fill(backgroundColor,10);
	    rect(0,0,OutputWindow.getWidth(),OutputWindow.getHeight());
	  }
	  x = clineList.drawLines(x);  //drawing function
	  count++;
	}

	/*
	Jack Kirkpatrick
	Description: The cline class holds pitch and velocity information
	that are input from a MIDI device. It also handles the drawing of
	lines around the circle.
	*/

	public class CircleLine {
	  float circleTime, multiplier, factor;

	  public CircleLine(int frameRt, int tempo, int beats) {
	    circleTime = ((2*PI)/beats)/(frameRt/(tempo/60));
	    //the amount that the lines spiral in, 0 for no spiral. 0.0005 for small spiral
	    multiplier=0.0000f;
	    factor=5f;
	  }
	  
	  float drawLines(float x) {
		  for (int i=0;i<EventsMidi.midiState.size();i++){
				if(EventsMidi.midiState.get(i).state){
				      stroke((EventsMidi.midiState.get(i).channel)*50,25,EventsMidi.midiState.get(i).velocity*50,150);
				      strokeWeight(4);//velocityList.get(i)/32);
				      line(p.sin(x)*EventsMidi.midiState.get(i).pitch*factor+(OutputWindow.getWidth()/2),
				        p.cos(x)*EventsMidi.midiState.get(i).pitch*factor+(OutputWindow.getWidth()/2),
				        OutputWindow.getWidth()/2,OutputWindow.getHeight()/2);
				}
			} 
		  x+=circleTime;
		  factor-=multiplier;
		  return x;
	  }
/*
	//adds the pitch and velocity to the arrays, or if they already exist, deletes them.
	  public void inSwitch(int vel, int pit){
	    if(!clineExists(pit)){
	      pitchList.add(pit);
	      velocityList.add(vel);
	    }
	  }

	//delete the velocity and pitch from the array
	  void clineDelete(int pitch){
	    for (int i=0;i<pitchList.size();i++){
	      if(pitchList.get(i) == pitch){
	        pitchList.remove(i);
	        velocityList.remove(i);
	      }
	    }
	  }

	//returns true if the pitch exists in the ArrayList
	  boolean clineExists(int pitch){
	    for (int i=0;i<pitchList.size();i++){
	      if(pitchList.get(i) == pitch){
	        return true;
	      }
	    }
	    return false;
	  }*/
	  




	}
	
}