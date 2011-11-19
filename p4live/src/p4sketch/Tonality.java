package p4sketch;

import java.util.ArrayList;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Tonality extends Sketch {
	float pitch = 60;
	float played = 0;
	float velocity = 100;
	int[] dup = new int[128];
	boolean newNote = false;
	
	ArrayList<Integer> pitchs = new ArrayList<Integer>();
	ArrayList<Integer> vels = new ArrayList<Integer>();
	
	public Tonality(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		// make color dodecaphonic ;)  
		  colorMode(HSB, 12);
		  background(0);
		  smooth(); 
	}
	
	public void noteOn(int channel, int pit, int vel){
		pitchs.add(pit);
		vels.add(vel);
		pitch = pit;
		velocity = vel;
		newNote=true;
	}

	public void draw() {
		if (pitchs.size()>0){	
			pitch = pitchs.get(0);
			velocity = vels.get(0);
			pitchs.remove(0);
			vels.remove(0);
			
			int xPitch = (int) p.map(pitch, 0, 127, 0, width);
			int xPlayed = (int) p.map(played, 0, 127, 0, width);
			
			// fade last note  
			  fill(played%12, 12, 4);
			  rect(xPlayed,  dup[(int)played] * 10 , 10, 10);
			  
			// show new note   
			  fill(pitch%12, 12, 12);
			  rect(xPitch,  dup[(int)pitch] * 10 + 10, 10, 10);
			  
			// remember hit  
			  dup[(int)pitch]++;
			  
			  if (dup[(int)pitch]*10 > height )
				  dup[(int)pitch] = 0;
		
			played=pitch;
			newNote = false;
		}
	}
}