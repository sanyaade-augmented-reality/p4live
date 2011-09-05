package p4live;

import processing.core.PApplet;
import codeanticode.glgraphics.*;



public class P4live extends PApplet {


	
	public void setup() {
		size(1024, 768, GLConstants.GLGRAPHICS);
		Interface i;
		i = new Interface(this);
	}

	public void draw() {
		background(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { p4live.P4live.class.getName() });
	}
	


}
