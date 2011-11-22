package p4sketch;

import java.util.ArrayList;

import p4control.Beats;
import p4control.Control;
import p4control.Volume;
import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import p4control.Wave;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class WaveBoca extends Sketch {	
	
	private float[] f;
	private boolean boca1 = false;
	private boolean boca2 = false;
	private int y1;
	private int y2;
	
	public WaveBoca(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		smooth();
		
		y1 = height/3;
		y2 = 2*(height/3);	
	}
	
	public void draw(){
		background(0);
		f = Wave.getWave();
		
		stroke(247);
		
		if (boca1)
		for (int i = 0; i < f.length-1 ; i++) {
			int x = (int) p.map(i, 0, f.length, 0, width);
			line(x, y1+f[i] , x + 1, y1 + f[i + 1] );			
		}		

		if (boca2)
		for (int i = 0; i < f.length-1 ; i++) {
			int x = (int) p.map(i, 0, f.length, 0, width);
			line(x, y2+f[i] , x + 1, y2 + f[i + 1] );			
		}		

	
	}
	
	
	  public void noteOn(int channel, int pitch, int velocity){
		  if (channel == P4Constants.RIFF2)
				boca1 = true;
		  if (channel == P4Constants.RIFF3)
				boca2 = true;
	  }
	  
	  public void noteOff(int channel, int pitch, int velocity){
		  if (channel == P4Constants.RIFF1)
				boca1 = false;
		  if (channel == P4Constants.RIFF2)
				boca2 = false;
	  }


	
}