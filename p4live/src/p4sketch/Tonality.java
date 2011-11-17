package p4sketch;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Tonality extends Sketch {
	float pitch = 60;
	float played = 0;
	float vel = 100;
	int[] dup = new int[128];
	boolean newNote = false;

	public Tonality(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		// make color dodecaphonic ;)  
		  //colorMode(HSB, 12);
		  background(0);
		 // smooth();
 
	}
	
	public void noteOn(int channel, int pit, int velocity){
		pitch = pit;
		newNote=true;
	}

	public void draw() {
		
	if (newNote){	
		// fade last note  
		  fill(played%12, 12, 4);
		  rect(played * 10,  dup[(int)played] * 10 , 8, 10);
		  
		// show new note   
		  fill(pitch%12, 12, 12);
		  rect(pitch * 10,  dup[(int)pitch] * 10 + 10, 8, 10);
		// remember hit  
		  dup[(int)pitch]++;
	
		played=pitch;
		newNote = false;
	}
	}
}