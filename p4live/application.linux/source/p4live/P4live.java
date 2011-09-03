package p4live;

import processing.core.PApplet;
import codeanticode.glgraphics.*;


public class P4live extends PApplet {

	public void setup() {
		//size(640, 480, GLConstants.GLGRAPHICS);
		size(640, 480);

	}

	public void draw() {
		background(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { p4live.P4live.class.getName() });
	}
}
