package p4sketch;

import p4live.OutputWindow;
import p4live.P4live;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestScreen extends Sketch {

	public TestScreen(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
	}
	
	public void draw(){
		background(0);
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
		translate(p4live.OutputWindow.getWidth(), 0);
		sphere(50);
		popMatrix();
		pushMatrix();
		translate(0, p4live.OutputWindow.getHeight());
		sphere(50);
		popMatrix();
		pushMatrix();
		translate(p4live.OutputWindow.getWidth(), p4live.OutputWindow.getHeight());
		sphere(50);
		popMatrix();
		
		//Draw dinamic bars
		fill(255);		
		int x = p4live.OutputWindow.frameCount() % OutputWindow.getWidth();
		//int x = 5;
		rect(x, 0,  10, p4live.OutputWindow.getWidth());			
		for (int k=0;k<p4live.OutputWindow.getWidth();k=k+20){
			rect(x+k, 0,  10, p4live.OutputWindow.getWidth());
			rect(x-k, 0,  10, p4live.OutputWindow.getWidth());
		}
		
		pushMatrix();
		//Rectangle for show information
		translate(p4live.OutputWindow.getWidth()/5, p4live.OutputWindow.getHeight()/5, 10);
		
		rect(0,0,2*(p4live.OutputWindow.getWidth()/3),2*(p4live.OutputWindow.getHeight()/3));
		
		fill(0);
		//translate(0, 0, 10);
		
		text("  Sketch Test",  10,20);
		text("  Window width:" +p4live.OutputWindow.getWidth(), 10,30);
		text("  Window height:"+p4live.OutputWindow.getHeight(), 10,40);
		text("  FrameCount:"+p4live.OutputWindow.frameCount(),  10,50);

		text("  Parameter 1: "+parameter[0],  10,60);
		text("  Parameter 2: "+parameter[1],  10,70);
		text("  Parameter 3: "+parameter[2],  10,80);

		text("  Camera X: "+cameraX,  10,90);
		text("  Camera Y: "+cameraY,  10,100);
		text("  Camera Z: "+cameraZ,  10,110);
		text("  Rotation X: "+rotX,  10,120);
		text("  Rotation Y: "+rotY,  10,130);
		text("  Rotation Z: "+rotZ,  10,140);

		

		
		popMatrix();
	}
}
