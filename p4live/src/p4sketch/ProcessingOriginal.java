package p4sketch;

import java.util.ArrayList;

import p4control.Beats;
import p4control.Volume;
import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import p4sketch.BolasCaen.Ball;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class ProcessingOriginal extends Sketch {
	  /* 
	  PROCESSINGJS.COM HEADER ANIMATION  
	  MIT License - F1lT3R/Hyper-Metrix
	  Native Processing Compatible 
	  */  

	  // Set number of circles
	  private int count = 20;
	  // Set maximum and minimum circle size
	  private int maxSize = 50;
	  private int minSize = 5;
	  private NoteState n;
	  	  
	  private ArrayList<Ball> noteBalls = new ArrayList();
	  // Set size of dot in circle center
	 // int ds = 2;
	
	public ProcessingOriginal(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);	
		noteBalls = new ArrayList<Ball>();
	    strokeWeight(1);

	}

	public void noteOn(int channel, int pitch, int velocity){
		NoteState note = new NoteState();
		note.channel = channel;
		note.pitch = pitch;
		note.velocity = velocity;

		int index = EventsMidi.midiState.indexOf(note);

		if (index == -1){
		      float x=p.random(OutputWindow.getWidth()); // X 
		      float y=p.random(OutputWindow.getHeight()); // Y
		      float radius=channel*30; // Radius        
		      float speedX=p.random(-.5f,.5f); // X Speed
		      float speedY=p.random(-.5f,.5f); // Y Speed 
		      int color = p.color(64,128,187,100);
		      Ball b = new Ball(x,y, speedX,speedY,radius,color);
		      noteBalls.add(b);	
		}		
	}
	
	private void drawState() {
		for (int i=0;i<EventsMidi.midiState.size();i++){
			if(EventsMidi.midiState.get(i).state){
				Ball b = noteBalls.get(i);      
				b.draw();
				
			    fill(0,0,0,255);
			    // and set line color to turquoise.
			    stroke(64,128,128,255);
			    
				for (int k=0;k<EventsMidi.midiState.size();k++){
					if(EventsMidi.midiState.get(k).state){
				    	Ball b2 = noteBalls.get(k);
					    // If the circles are close...
					    if( p.dist(b.x,b.y,b2.x,b2.y) < b.radius*5){
					        // Stroke a line from current circle to adjacent circle
					        line(b.x,b.y,b2.x,b2.y);
					     }
					}
				}
			}
		}
	}

	  public void draw(){
	    background(0);
	    drawState();
	  }
	
		class Ball {		  
			  float x;
			  float y;
			  float speedX;
			  float speedY;			  
			  float gravity;
			  float radius;
			  int color;
			  
			  Ball(float tempX, float tempY,float sx,float sy, float tempW, int c) {
			    x = tempX;
			    y = tempY;
			    speedX = sx;
			    speedY = sy;
			    radius = tempW;
			    //gravity = 0.1f;
			    color = c;
			  }
			  
			   public void move() {
				   x+=speedX;
				   y+=speedY;
				   
				   int diam = (int) (radius/2);
				    /* Wrap edges of canvas so circles leave the top
				    and re-enter the bottom, etc... */ 
				    if( x < -diam      ){ x = OutputWindow.getWidth()+diam;  } 
				    if( x > OutputWindow.getWidth()+diam ){ x = -diam;       }
				    if( y < 0-diam     ){ y = OutputWindow.getHeight()+diam; }
				    if( y > OutputWindow.getHeight()+diam){ y = -diam;       }
			  }
			  			  
			  void draw() {
			    // Display the circle			    
				pushMatrix();
				translate(0,0,-radius/10);
			
			    fill(color);
			    stroke(0);
			    ellipse(x,y,radius,radius);

				translate(0,0,1);
				noStroke();
				fill(0,0,0,255);
				float ds = Volume.level*5; 
				rect(x-ds,y-ds,ds*2,ds*2);

			    popMatrix();
			    move();
			  }
			}  

}