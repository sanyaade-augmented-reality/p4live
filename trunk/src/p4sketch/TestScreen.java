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
		
		//Draw Corners of window
		noStroke();		
		fill(255,0,0);
		sphere(30);
		pushMatrix();
		translate(OutputWindow.getWidth(), 0);
		sphere(30);
		popMatrix();
		pushMatrix();
		translate(0, OutputWindow.getHeight());
		sphere(30);
		popMatrix();
		pushMatrix();
		translate(OutputWindow.getWidth(), OutputWindow.getHeight());
		sphere(30);
		popMatrix();
		
		//Draw dinamic bars
		fill(255);		
		int x = OutputWindow.frameCount() % OutputWindow.getWidth();
		//int x = 5;
		rect(x, 0,  10, OutputWindow.getWidth());			
		for (int k=0;k<OutputWindow.getWidth();k=k+20){
			rect(x+k, 0,  10, OutputWindow.getWidth());
			rect(x-k, 0,  10, OutputWindow.getWidth());
		}
		
		pushMatrix();
		//Rectangle for show information
		translate(OutputWindow.getWidth()/5, OutputWindow.getHeight()/5, 10);	
		rect(0,0,2*(OutputWindow.getWidth()/3),2*(OutputWindow.getHeight()/3));
		
		fill(0);
		//translate(0, 0, 10);
		
		text("  Sketch Test",  10,20);
		text("  Window width:" +OutputWindow.getWidth(), 10,30);
		text("  Window height:"+OutputWindow.getHeight(), 10,40);
		text("  FrameCount:"+ OutputWindow.frameCount(),  10,50);

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
