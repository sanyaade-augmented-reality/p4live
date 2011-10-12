package p4live;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class BlackScreen extends GLGraphicsOffScreen {

	public BlackScreen(PApplet arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	
	public void draw(){
		background(0);
	}

}
