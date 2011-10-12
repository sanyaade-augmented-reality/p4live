package p4live;


import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestScreen extends Sketch {

	public TestScreen(PApplet arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	
	public void draw(){
		fill(100);
		background(128);
		text("TEST SCREENS",100,100);
		
		lights();
		//p.translate(p4vj.widthVisual/2, p4vj.heightVisual/2, p4vj.zoomZ);
		/*p.rotateX(p4vj.rotX);
		p.rotateY(p4vj.rotY);
		p.rotateZ(p4vj.rotZ);
		p.rectMode(p.CORNER);
		p.pushMatrix();
		p.translate(-p4vj.widthVisual/2, -p4vj.heightVisual/2);*/
		

		//Draw Corners of window
		noStroke();		
		fill(255,0,0);
		sphere(50);
		pushMatrix();
		translate(p4live.Screen.width(), 0);
		sphere(50);
		popMatrix();
		pushMatrix();
		translate(0, p4live.Screen.height());
		sphere(50);
		popMatrix();
		pushMatrix();
		translate(p4live.Screen.width(), p4live.Screen.height());
		sphere(50);
		popMatrix();
		
		fill(255);		
		//int x = this. % p4live.Screen.width();
		int x = 5;
		
		rect(x, 0,  10, p4live.Screen.width());	
		for (int k=0;k<p4live.Screen.width();k=k+20){
			rect(x+k, 0,  10, p4live.Screen.width());
			rect(x-k, 0,  10, p4live.Screen.width());
		}
		//Rectangle for show information
		rect(p4live.Screen.width()/3,p4live.Screen.height()/3,p4live.Screen.width()/3,p4live.Screen.height()/3);
		
		fill(0);
		translate(0, 0,10);
		//textFont(p4vj.fontSS14,30);
		text("  Size Window Test",  p4live.Screen.width()/3,p4live.Screen.width()/3);
		text("  Window width:"+p4live.Screen.width(),    p4live.Screen.width()/3,p4live.Screen.width()/3+30);
		text("  Window height:"+p4live.Screen.height(),  p4live.Screen.width()/3,p4live.Screen.width()/3+60);
	}

}
