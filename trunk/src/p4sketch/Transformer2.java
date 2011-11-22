package p4sketch;

import p4control.Volume;
import p4control.Beats;
import p4live.EventsMidi;
import p4live.P4Constants;
import p4sketch.ProcessingOriginal.Ball;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;
import processing.opengl.*;
import codeanticode.glgraphics.*;

/* 
Daniel C. Young
Fuzzball #2
10/22/2011
Modified by lot for P4live
*/

public class Transformer2 extends Sketch {

	float easing=0.8f;
	float easeX=0.0f;

	public Transformer2(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		  smooth();
		  colorMode(HSB, 100);

	}

	public void draw(){
	  fill(15,30);
	  noStroke();
	  rect(0,0,width,height);
	  
	  // replace mouseX with eased value
	 // easeX += (p.mouseX-easeX) * easing;
	  
drawState();
	  
	}
	
	private void drawState() {
		for (int i=0;i<EventsMidi.midiState.size();i++){
			if(EventsMidi.midiState.get(i).state){
				int chan= EventsMidi.midiState.get(i).channel;
				int pit = EventsMidi.midiState.get(i).pitch;
				int vel = EventsMidi.midiState.get(i).velocity;
			    if (chan==P4Constants.RIFF1 || chan==P4Constants.RIFF2 || chan==P4Constants.RIFF3){
			    int x = ( chan * pit *vel)%width;
			    int y = (chan * pit * vel)%height;
				  for (int k=0;k<Volume.level*30;k++) {
					    // map size, noise, and hue to mouseX  
					    /*float syz = p.map(Beats.kick,0,1,0,100);
					    float nyz = p.map(Beats.snare,0,1,0,10);
					    float clr = p.map(Beats.hat,0,1,1,6.5f);*/
					    
					    float syz = p.map(chan,1,16,0,500);
					    float nyz = p.map(pit,0,127,0,300);
					    float clr = p.map(vel,0,127,1,10);
					    
					    pushMatrix();	  
					    translate(x, y);
					    drawSpiral(k*syz, k*0.2f, nyz+k, clr*k);
					    popMatrix();
				  }
			}
			}
		}
	}

	void drawSpiral(float _endAng, float _angStep, float _noise, float _hue) {
	  float px,py,x,y;
	  float seed = p.random(10);  // initial radius
	  px=py = 0.0f;

	  for (float ang=0; ang<_endAng; ang+=_angStep) {
	    // adjust for different results
	    _noise += 0.5;  
	    seed += 0.2;

	    // apply offset to initial radius
	    float rad = seed + (p.noise(_noise)*100) - 50;

	    x = rad*p.cos(p.radians(ang));
	    y = rad*p.sin(p.radians(ang));

	    // avoid drawing the first time
	    if (px != 0.0) {
	      stroke(_hue,200,200,60);
	      strokeWeight(0.25f);
	      line(px, py, x, y);
	    }
	    px = x;
	    py = y;
	  }

	}



}