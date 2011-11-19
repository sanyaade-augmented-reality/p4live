package p4sketch;

import p4control.Beats;
import p4control.Volume;
import p4live.*;

import processing.core.*;


public class CubicGrid extends Sketch{

float boxSize = 5;
float margin = boxSize*8;
float depth = boxSize*8;
int boxFill;


public CubicGrid(PApplet arg0, int arg1 ,int arg2) {
	super(arg0,arg1,arg2);
}

public void draw() {
	background(0);
    tint(255,255);  
    
    noStroke();
    //stroke(128);
    /*if (Beats.kick>0.5)
      stroke(Beats.kick*255,Beats.snare*255,Beats.hat*255);
    else
      noStroke();*/
    
 /*   objX = Beats.kick*PI;
    objY = Beats.hat*PI;
    realX += ((objX - realX) * .08);
    realY += ((objY - realY) * .08);*/
  
  // Center and spin grid
  translate(OutputWindow.getWidth()/2, OutputWindow.getHeight()/2);// -depth+cameraZ);
  
  //rotateY( realX+ OutputWindow.frameCount()*0.01f);
  //rotateX( realY+ OutputWindow.frameCount()*0.01f);

  // Build grid using multiple translations 
  for (float i =- depth/2; i <= depth/2; i += boxSize){
    pushMatrix();
    for (float j =- margin; j <= margin; j += boxSize){
      pushMatrix();
      for (float k =- margin; k <= margin; k += boxSize){
        // Base fill color on counter values, abs function 
        // ensures values stay within legal range 
        //boxFill = color(p.abs(i+Beats.kick*200), p.abs(j+Beats.snare*200), p.abs(k+Beats.hat*200), 50);
        boxFill = color(p.abs(i), p.abs(j), p.abs(k), Volume.level*255);
        pushMatrix();
        translate(k, j, i);
        fill(boxFill);
        box(boxSize*Volume.level, boxSize*Volume.level, boxSize*Volume.level);
        popMatrix();
      }
      popMatrix();
    }
    popMatrix();
  }
}

private int getTrans(float a, float b, float c){
	int x = (int)p.map(a, -depth/2, depth/2, 0, 15);
	int y = (int)p.map(b, -margin, margin, 0, 15);
	int z = (int)p.map(c, -margin, margin, 0, 15);
	
	if (celda16(x,y,z)){
		p.println("255");
		return 255;
	}
	else 
		return 50;
}

private boolean celda16(int a, int b, int c){
	for (int i=0;i<EventsMidi.midiState.size();i++){
		if(EventsMidi.midiState.get(i).state){
			int channel = EventsMidi.midiState.get(i).channel;
			int pitch = EventsMidi.midiState.get(i).pitch;
			int velocity = EventsMidi.midiState.get(i).velocity;
			if (a == channel && b==(pitch % 16) && b==(velocity%16))
				return true;
		}
	}
	return false;
}
}