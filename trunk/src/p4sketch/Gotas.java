package p4sketch;

import java.util.ArrayList;

import p4control.Volume;
import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import processing.core.PImage;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Gotas extends Sketch {	

ArrayList balls;
PImage img;	
	public Gotas(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		img = p.loadImage(p.dataPath("Gotas/gota.png"));
		lights();
		parameter[0] = 0;
		parameter[1] = 0;
		parameter[2] = 0;	
		  // Create an empty ArrayList
		  balls = new ArrayList();		  
	}
	
	public void draw(){
		background(0);
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
		if (channel == P4Constants.ACOMPA1){
			int color = color(channel*10,channel*20,0);			
			int x = (int) p.map(pitch, 0, 128, 0, OutputWindow.getWidth());			
			balls.add(new Ball(x, 10, 10,color));
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
	    gravity = 0.1f;
	    //gravity = p.map(tempW, 1, 15, 0.3f, 0.1f);
	    //gravity = parameter[0];
	    color = c;
	  }
	  
	    void move() {
	    // Add gravity to speed
	    gravity = Volume.level;	
	    speed = speed + gravity;
	    // Add speed to y location
	    y = y + speed;
	    // If square reaches the bottom
	    // Reverse speed
	  }
	  
	  boolean finished() {
	    // Balls fade out
	    life--;
	   // life = life -10;
	    if (y > OutputWindow.getWidth() && life<0) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	  
	  void display() {
	    // Display the circle
	    //fill(color,life);
	    //stroke(0,life);
		pushMatrix();
		translate(0,0,w);
		//tint(color,200);
		image(img,x,y);
	    //ellipse(x,y,w,w);

	    popMatrix();
	  }
	}  
				


	
}