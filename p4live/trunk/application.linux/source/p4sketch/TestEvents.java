package p4sketch;

import p4control.Beats;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestEvents extends Sketch {
	public TestEvents(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
	}
	
	public void draw(){
		background(0);
		stroke(128);
		fill(Beats.kick*255,0,0);
		ellipse(100,OutputWindow.getHeight()/2,20,20);
		fill(0,Beats.snare*255,0);
		ellipse(200,OutputWindow.getHeight()/2,20,20);
		fill(0,0,Beats.hat*255);
		ellipse(300,OutputWindow.getHeight()/2,20,20);
	}
	
	public void event(int e){
		begin();
		switch (e){
		case P4Constants.KICK:
			fill(255,0,0);
			ellipse(100,100+OutputWindow.getHeight()/2,20,20);
			break;
		case P4Constants.SNARE:
			fill(0,255,0);
			ellipse(200,100+OutputWindow.getHeight()/2,20,20);
			break;
		case P4Constants.HAT:
			fill(0,0,255);
			ellipse(300,100+OutputWindow.getHeight()/2,20,20);
			break;
		case P4Constants.BEAT:
			fill(255,255,255);
			ellipse(OutputWindow.getWidth()/2,OutputWindow.getHeight()/2,100,100);
			break;
			
		}
		end();
	}
}