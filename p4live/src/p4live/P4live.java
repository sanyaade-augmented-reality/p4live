package p4live;

import processing.core.PApplet;
import codeanticode.glgraphics.*;



public class P4live extends PApplet {
	Interface i;

	
	public void setup() {
		size(1024, 768, GLConstants.GLGRAPHICS);
		frameRate(30);
		i = new Interface(this);
	}

	public void draw() {
		background(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { p4live.P4live.class.getName() });
		//sketchPath(), sketchFile(), savePath(), dataPath(), and createPath() 
	}
	
	public void keyPressed() {
		  if(key=='s') {
			  i.saveInterface();
		  }
		  if(key=='l') {
			  i.loadInterface();
		  }
		  
		  if(key=='p') {
			  i.printControls();
		  }

		  
	}
	
	

}
