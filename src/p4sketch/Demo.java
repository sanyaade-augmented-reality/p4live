package p4sketch;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Demo extends Sketch {
	public Demo(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
	}
	
	public void draw(){
		background(0);
		text("Processing 4 live",30,30);
	}
}