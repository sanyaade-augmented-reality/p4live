package p4sketch;

import java.util.ArrayList;

import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class BolasCaen extends Sketch {	
	
ArrayList balls;
	
	public BolasCaen(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		p = arg0;
		lights();
		parameter[0] = 0;
		parameter[1] = 0;
		parameter[2] = 0;	
		  // Create an empty ArrayList
		  balls = new ArrayList();	  
	}
	
	public void draw(){
		background(0);
		text("Bye bye Morgan",50,50);

		 for (int i = balls.size()-1; i >= 0; i--) { 
			    // An ArrayList doesn't know what it is storing so we have to cast the object coming out
			    Ball ball = (Ball) balls.get(i);
			    ball.move();
			    ball.display();
			    if (ball.finished()) {
			      // Items can be deleted with remove()
			      balls.remove(i);
			    }		    
			  }  
		
	}
	
	public void noteOn(int channel, int pitch, int velocity){
		int color;
		
		switch(channel){	
		case P4Constants.BOMBO:
			color = color(50,pitch,pitch);
			color = color(234,43,43);
			//color = E51515;
			//color inside = #CC6600;
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.CAJA:
			//color = color(pitch,50,pitch);
			color = color(22,222,22);			
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.CHARLES:
			//color = color(pitch,50,100);
			color = color(254,254,14);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.PERCUSION1:
			//color = color(30,50,pitch);
			color = color(255,160,15);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.PERCUSION2:
			//color = color(30,pitch,50);
			color = color(220,130,2);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.PERCUSION3:
			color = color(pitch,50,200);
			color = color(175,128,60);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.BAJO1:
			color = color(pitch,100,200);
			color = color(60,60,160);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.BAJO2:
			color = color(200,100,pitch);
			color = color(60,200,222);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;				
		case P4Constants.ACOMPA1:
			color = color(100);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;				
		case P4Constants.ACOMPA2:
			color = color(200);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;				
		case P4Constants.RIFF1:
			color = color(210,210,60);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.RIFF2:
			color = color(145,5,145);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;
		case P4Constants.RIFF3:
			color = color(175,60,185);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;				
		case P4Constants.SOUNDS:
			color = color(pitch,100,velocity);
			balls.add(new Ball(p.random(width), p.random(height), channel*10,color));
		break;			
		default:
			p.println("* Warning Note ON unmpaped");
			break;
	}
	}
	
	// Simple bouncing ball class

	class Ball {
	  
	  float x;
	  float y;
	  float speed;
	  float gravity;
	  float w;
	  float life = 255;
	  int color = 0;
	  
	  Ball(float tempX, float tempY, float tempW, int c) {
	    x = tempX;
	    y = tempY;
	    
	    w = p.map(tempW, 1, 15, 10, 50);
	    
	    speed = 0;
	    //gravity = 0.1f;
	    gravity = p.map(tempW, 1, 15, 0.3f, 0.1f);
	    gravity = parameter[0];
	    color = c;
	  }
	  
	    void move() {
	    // Add gravity to speed
	    speed = speed + gravity;
	    // Add speed to y location
	    y = y + speed;
	    // If square reaches the bottom
	    // Reverse speed
	    if (y > height) {
	      // Dampening
	      speed = speed * -0.8f;
	      y = height;
	    }
	  }
	  
	  boolean finished() {
	    // Balls fade out
	    life--;
	   // life = life -10;
	    if (life < 0) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	  
	  void display() {
	    // Display the circle
	    fill(color,life);
	    stroke(0,life);
		pushMatrix();
		translate(0,0,w);

	    ellipse(x,y,w,w);

	    popMatrix();
	  }
	}  
				


	
}