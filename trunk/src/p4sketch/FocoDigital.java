package p4sketch;

import java.awt.Point;

import p4control.Beats;
import p4control.Volume;
import p4live.EventsMidi;
import p4live.P4Constants;
import p4sketch.ProcessingOriginal.Ball;
import processing.core.PApplet;


public class FocoDigital extends Sketch{
	
	  public FocoDigital(PApplet arg0, int arg1 ,int arg2) {
		  super(arg0,arg1,arg2);
		  colorMode(HSB,127);
	   }
	   
	   public void draw() {
		 fill(0,110);
		 rect(0,0,width,height);
	     lights();

	    // int innerCol = p.color(255,200,200,200);
	     //int outerCol = p.color(0,30,0,10);

	     drawState();    
	   }
	   
	   private void drawState() {
			for (int i=0;i<EventsMidi.midiState.size();i++){
				if(EventsMidi.midiState.get(i).state){
					int chan= EventsMidi.midiState.get(i).channel;
					int pit = EventsMidi.midiState.get(i).pitch;
					int vel = EventsMidi.midiState.get(i).velocity;
				    
					if (chan == P4Constants.ACOMPA2){
				    int x = (int) p.map(chan, 1, 16, 50, width-50); 
				    int y = (int) p.map(pit, 0, 127, 50, height-50); 
				    
				     int innerCol = p.color(pit,vel,200,200);
				     int outerCol = p.color(pit-vel,vel-pit,10,10);
					drawGradientDisc(x, y, Beats.kick *200, Beats.kick*200, innerCol, outerCol);	
					}
				}
			}
		}
	   
	   /**
	    * 
	    * @param x Coordenate X
	    * @param y Coordenate X
	    * @param radiusX
	    * @param radiusY
	    * @param innerCol color one
	    * @param outerCol color two
	    */
	   void drawGradientDisc(float x,float y, float radiusX, float radiusY, int innerCol, int outerCol) {
		   noStroke();
		   beginShape(TRIANGLE_STRIP);
		   for(float theta=0; theta<TWO_PI; theta+=TWO_PI/36) {
		     fill(innerCol);
		     vertex(x,y);
		     fill(outerCol);
		     vertex(x+radiusX*p.cos(theta),y+radiusY*p.sin(theta));
		   }
		   endShape();
	   }
}
