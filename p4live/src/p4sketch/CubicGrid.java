package p4sketch;

import p4control.Beats;
import p4control.Volume;
import p4live.OutputWindow;
import p4live.*;

import processing.core.*;

/**
 * Cubic Grid 
 * by Ira Greenberg. 
 * 
 * Modified by lot for sound reactive
 */

public class CubicGrid extends Sketch{

float boxSize = 60;
float margin = boxSize*10;
float depth = boxSize*4;
int boxFill;

float realX;
float realY;
float objX;
float objY;

public CubicGrid(PApplet arg0, int arg1 ,int arg2) {
	super(arg0,arg1,arg2);
}

public void draw() {
    //colorMode(RGB,255);  
    tint(255,255);  
    //noStroke();
    
    if (Beats.kick>0.5)
      stroke(Beats.kick*255,Beats.snare*255,Beats.hat*255);
    else
      noStroke();
    
 
    objX = Beats.kick*PI;
    objY = Beats.hat*PI;
    realX += ((objX - realX) * .08);
    realY += ((objY - realY) * .08);

  
  // Center and spin grid
  translate(OutputWindow.getWidth()/2, OutputWindow.getHeight()/2, -depth+cameraZ);
  
  rotateY( realX+ OutputWindow.frameCount()*0.01f);
  rotateX( realY+ OutputWindow.frameCount()*0.01f);

  // Build grid using multiple translations 
  for (float i =- depth/2; i <= depth/2; i += boxSize){
    pushMatrix();
    for (float j =- margin; j <= margin; j += boxSize){
      pushMatrix();
      for (float k =- margin; k <= margin; k += boxSize){
        // Base fill color on counter values, abs function 
        // ensures values stay within legal range
        boxFill = color(p4live.P4live.abs(i+Beats.kick*200), p4live.P4live.abs(j+Beats.snare*200), p4live.P4live.abs(k+Beats.hat*200), 50);
        pushMatrix();
        if (i>0)
          translate(k, j, i+100*Beats.kick);
        else
          translate(k+100*Beats.kick, j, i);
        fill(boxFill);
        box(boxSize*Volume.level, boxSize*Volume.level, boxSize*Volume.level);
        popMatrix();
      }
      popMatrix();
    }
    popMatrix();
  }
}

}