package p4live;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestScreens extends GLGraphicsOffScreen {

	public TestScreens(PApplet arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	
	public void draw(){
		fill(100);
		background(128);
		text("TEST SCREENS",100,100);
	}

}
