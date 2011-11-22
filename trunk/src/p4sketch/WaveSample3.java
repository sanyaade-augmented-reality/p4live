package p4sketch;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class WaveSample3 extends Sketch {
	final int W = 287;
	final int H = 70;
	final int zoom = 1;
	final int Speed = 5;
	final float A = 10;
	float[][][] u = new float[W][H][3];

	public WaveSample3(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
	    colorMode(HSB, 255);
	  for(int i=0; i<W; i++){
	    for(int j=0; j<H; j++){
	      for(int k=0; k<3; k++){
	        u[i][j][k] = 100*p.sin(i/(W)*PI)*p.sin(j/(H)*PI);
	        //u[i][j][k] = (i==W/2 && j==H/2 && k==2)? 255 : 0;        
	       // u[i][j][k] = 0;
	      }
	    }
	  }

	}

	public void draw(){
	  //background(160,200,125);
	  background(0);
	  translate(0,250);
	  noStroke();
	  scale(5);

	  for(int i=0; i<W-1; i++){
	    for(int j=0; j<H-1; j++){
	      fill(160,200,p.map(u[i][j][0],-255,255,-55,200) + 55);

	      beginShape();
	      vertex(i,j, u[i][j][0]*0.1f);
	      vertex(i+1,j, u[i+1][j][0]*0.1f);
	      vertex(i+1,j+1, u[i+1][j+1][0]*0.1f);
	      vertex(i,j+1, u[i][j+1][0]*0.1f);
	      endShape();
	    }
	  }
	  
	  for(int i=0; i<Speed; i++){
	    calc();
	  }
	}

	void calc(){
	    float B = 1/A;
	  
	  for(int i=0; i<W; i++){
	    for(int j=0; j<H; j++){
	      if(i==W-1 || j==H-1){
	        u[i][j][0] = 0;
	      }else if(i==0 || j==0){
	        u[i][j][0] = 0;
	      }else{
	        u[i][j][0] = B * ( -A*u[i][j][2] + 2*(A-2)*u[i][j][1] + u[i-1][j][1] + u[i+1][j][1] + u[i][j-1][1] + u[i][j+1][1]) * 0.99f;
	      }
	    }
	  }
	  
	   for(int i=0; i<W; i++){
	     for(int j=0; j<H; j++){
	        u[i][j][2] = u[i][j][1];
	        u[i][j][1] = u[i][j][0];
	     }
	  }
	}

	public void noteOn(int channel, int pitch, int velocity){
		int x = (int) p.map(channel, 1, 16, 0, W-1);
		int y = (int) p.map(pitch, 0, 127, 0, H-1);
		u[x][y][2] = 255;
	}
	
	public void mousePressed(){
	  u[p.constrain(p.mouseX/zoom,0,W-1)][p.constrain(p.mouseY/zoom,0,H-1)][2] = 255;
	}
/*
	void mouseDragged(){
	  u[constrain(mouseX/zoom,0,W-1)][constrain(mouseY/zoom,0,H-1)][2] = 100;
	}*/
}