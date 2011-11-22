package p4sketch;

import processing.core.PApplet;
import toxi.color.ColorGradient;
import toxi.color.ColorList;
import toxi.color.TColor;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class ADNOndas extends Sketch {
	
	Particle[] particles;
	 ColorGradient grad=new ColorGradient();
	 ColorList listColors;
	int[] colors = {0xB8E4C6, 0xC5E5BC, 0xD2E7B0, 0xDFE9A3, 0xECEB98 };
/*			        0xCFF09E, 0xA8DBA8, 0x79BD9A, 0x3B8686, 0x0B486B,
			        0x446B7F, 0x708695, 0x9BA2AC, 0xC7BDC2, 0xF3D8D8,
			        0x84DB9C, 0xC3DE91, 0xE8DC91, 0xF2D3D4, 0xECC2F2};		*/
	
	public ADNOndas(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
		  smooth();
		    noStroke();
		    
		    //strokeWeight(0);
		    //fill(0,100);
		  particles = new Particle [16];
		  for (int i= 0; i< particles.length; i++)
			  particles[i]=new Particle(i*0.2f,0);	
		  
		  TColor c1 = TColor.newHex("B8E4C6");
		  TColor c2 = TColor.newHex("C5E5BC");
		  TColor c3 = TColor.newHex("D2E7B0");
		  TColor c4 = TColor.newHex("DFE9A3");
		  TColor c5 = TColor.newHex("ECEB98");
		  
		  grad.addColorAt(0,c1);
		  grad.addColorAt((width/4),c2);
		  grad.addColorAt(2*(width/4),c3);
		  grad.addColorAt(3*(width/4),c4);
		  grad.addColorAt(width,c5);
		  listColors=grad.calcGradient(0,width);
	}
	
	public void draw() {
	 background(0);
	  //display and oscillate the particles
	  for ( int i = 0;i<particles.length ; i++) {
	    particles [i].oscillate();
	  }
	}
	
	public void noteOn(int channel, int pitch, int velocity){
		particles[channel].diameter = pitch; //=new Particle(50,50);
		//particles[channel].theta = pitch;
	}
	
	public void noteOff(int channel, int pitch, int velocity){
		particles[channel].diameter = 0;//=new Particle(0,0);
	}

	class Particle {
		  float x, y;
		  public float diameter;
		public float theta = 0.1f;

		// particle sizes are wave intensity Decibels(Db)
		//particles speeds are wave frequencies Hertzs(Hz)
		  Particle (float hz, float db) {   
		   theta=hz;
		   diameter=db;
		  }
		  
		  
		void oscillate(){
		  // Increment theta (try different values for " angular velocity " here)
		  theta += theta/100;
		  float x = theta;
		  
		   // A for loop is used to draw all the points along a sine wave (scaled to the pixel dimension of the window).
		  int space = 50;
		  for (int i = 0; i < width; i=i+space) {
		    // Calculate y value based off of sine function
		    float y = p.sin(x)*height/3;
		    // Draw an ellipse
		    pushMatrix();
		    translate(0,0,-y);

		    //stroke(128);
		    TColor c = listColors.get(i);
		    fill(c.toARGB());
		    ellipse(i,y + height/2,diameter,diameter);	    
		    popMatrix();
		    //p.println(y);
		    // Move along x-axis
		    x += 0.4;
		  }
		}
		}
	
}