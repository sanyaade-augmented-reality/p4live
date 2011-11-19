package p4sketch;
//This sketch is part of an ongoing research for the Mediascapes program at SCI-Arc.
//
//While researching cyclone systems the question arose to what extend a cyclone-based 
//dynamic system can generate form and spatial experience. 
//While they are rotating in a cyclonic system, particles get interconnected by lines
//and shapes. 

//By changing the controllable variables 1, 2 and 3 different spatial environments are 
//created. Min Distance = treshold when particles get interconnected. 

import p4control.Beats;
import p4control.Volume;
import p4live.OutputWindow;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Cyclone extends Sketch {
	int ballNum = 300; // number of particles (balls)
	Ball[] balls = new Ball[ballNum]; // array for particles (balls)
	float var1, var2, var3;
	float minDist;
	public Cyclone(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		  lights();
		  // fill array for particles (balls)
		  for (int i = 0; i < balls.length; i++){
		    balls[i] = new Ball();
		  }
	}

	public void draw(){
	  background(0);
	  var1 = Beats.kick*5;
	  var2 = Beats.snare*5;
	  var2 = Beats.hat*5;
	  minDist = Volume.level*150;
	  pushMatrix();
	  translate(OutputWindow.getWidth()/2, 0, 0); // translate drawing to the centre of the screen
	  for (int i = 0; i < balls.length; i++) {
	    balls[i].display();	  
	  }
	  popMatrix();
	}
	

class Ball {
  float deg = p.random(360); // p.random starting position
  float r = p.random(10, 400); // p.random radius
  float heightBall = p.random(225, 525); // p.random y position
  float rnd = p.random(-0.5f);
  int Color = color(p.random(255), p.random(10, 120), 100); // p.random color
  float xPositions;
  float zPositions;
  
  // coordinates for fixed particle
  float x = r * p.cos(p.radians(deg));
  float y = heightBall;
  float z = r * p.sin(p.radians(deg));
  float d;
  
  void Ball() {
  }
  
  void display() {
    stroke(255);
    noFill();
  
    // coordinates for fixed particle 
    float x = r * p.cos(p.radians(deg));
    float y = heightBall;
    float z = r * p.sin(p.radians(deg));
  
    deg += (1/r*200); // make it rotate
    r += rnd; // make it spiral inwards
    heightBall += 0.5; // make it spiral downwards
    
    if (r < 10) r = 400;
    if (heightBall > 525) heightBall = 225; 
    
    // x and z positions
    xPositions = r * p.cos(p.radians(deg));
    zPositions = r * p.sin(p.radians(deg)); 
    
    pushMatrix();
    rotateX(p.radians(-35));
    point(xPositions, heightBall, zPositions);
    
    for (int i = 0; i < balls.length; i++) {
    
      // measure distance between fixed particle and moving particles
      float d = p.dist(x, y, z, balls[i].x, balls[i].y, balls[i].z);
      if (d < minDist) {
        // draw a line between fixed particle and moving particles
        stroke(255, 40);
        line (x, y, z, balls[i].x, balls[i].y, balls[i].z);
        
        // draw a shape between fixed particle and moving particles
        beginShape();
        fill(Color, 30);
        noStroke();
        
        vertex(x, y, z);
        vertex(balls[i].x, balls[i].y, balls[i].z);
        vertex(balls[i].x*var1, balls[i].y*var2, balls[i].z*var3);
        // multiply the third vertex coordinates by controllable variables
        endShape(CLOSE);
      } 
    }   
    popMatrix();    
  }  
}
}