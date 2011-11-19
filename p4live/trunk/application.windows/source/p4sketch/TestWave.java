package p4sketch;

import java.util.ArrayList;

import p4control.Control;
import p4control.Volume;
import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import p4control.Wave;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestWave extends Sketch {	
	
	private float[] f;
	
	public TestWave(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		smooth();
	}
	
	public void draw(){
		background(0);
		text("Test Wave",30,30);
		f = Wave.getWave();
		
		stroke(247);
		
		for (int i = 0; i < f.length-1 ; i++) {
			int x = (int) p.map(i, 0, f.length, 0, width);
			int y = (int) p.map(Volume.level, 0, 1, 0, height);
			line(x, y+f[i] , x + 1, y + f[i + 1] );
			
		}
		
	}
	
}