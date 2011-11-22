	//NEBULA
	//Matt Schroeter
	//December 1st, 2008
	//matthanns.com


package p4sketch;

import p4control.Beats;
import p4live.OutputWindow;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class NebulaDefault extends Sketch {	
	float depth = 400;
	
	public NebulaDefault(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		smooth();
	}

	public void draw(){
	 background(15, 15, 15);
	 noStroke();
	 
	 translate(OutputWindow.getWidth()/2, OutputWindow.getHeight()/2, -depth/2);	 	 
	// translate(width/10, height/10, depth/2);
	 
	 for(int i=0; i<2; i++) {
		 float r = p.random(100);
		 directionalLight(2, 83, 115, // Color
		 1, 10, 0); // The x-, y-, z-axis direction'
		 directionalLight(3, 115, 140, // Color
		 10, 10, 0); // The x-, y-, z-axis direction'
	 }

	 for(int i=0; i<10; i++) {
	   float r = p.random(20); 
	 
	 //rotateX(OutputWindow.frameCount()*PI/1000);
	   rotateX(OutputWindow.frameCount()*PI/1000);
	 
	 //alt effect
	 //rotateY(OutputWindow.frameCount()*PI/1000);
	   //rotateY(OutputWindow.frameCount()*PI/100*Beats.hat);
	 
	 for (int y = -2; y < 2; y++) {
	 for (int x = -2; x < 2; x++) {
	 for (int z = -2; z < 2; z++) {

	 pushMatrix();
	 translate(400*x, 300*y, 300*z);
	 box(50*Beats.kick, 5*Beats.snare, 100*Beats.hat);
	 popMatrix();
	 
	 pushMatrix();
	 translate(400*x, 300*y, 50*z);
	 box(100*Beats.snare, 90*Beats.kick, 50*Beats.hat);
	 popMatrix();
	 
	 pushMatrix();
	 translate(400*x, 10*y, 50*z);
	 box(100*Beats.hat, 5*Beats.snare, 5*Beats.kick);
	 popMatrix();

	 pushMatrix();
	 rotateY(OutputWindow.frameCount()*PI/400);
	 translate(100*x, 300*y, 300*z);
	 box(60*Beats.kick, 40*Beats.snare, 20*Beats.hat);
	 popMatrix();
	 
	      }
	     }
	    }
	   }
	  }

	
	
}