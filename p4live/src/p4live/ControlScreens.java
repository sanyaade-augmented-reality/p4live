package p4live;

import controlP5.ControlGroup;
import controlP5.Toggle;
import p4live.OutputWindow;

public class ControlScreens extends Control {

	OutputWindow ow;

	ControlScreens() {
		groupName="Control Screens";
		defaultX = 400;
		defaultY = 200;
		defaultWidth = 400;
		//Set Output window
		ow = new OutputWindow(p); 
		build();
		setPreferences();
	}


	private void build() {
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		
		// OutputPreview(ControlP5 theControlP5, String theName, int theX, int
		// theY, int theWidth, int theHeight) {
		//OutputPreview op = new OutputPreview(controlP5, "Output Preview", 0,0, 400,400);
		//op.setGroup(cs);
		//controlP5.register(op);
	
		controlP5.addToggle("OutputWindow", true,100, 100, 20, 20).setGroup(group);
		controlP5.addToggle("Fullscreen",false, 30,200, 20,20).setGroup(group);//setMode(controlP5.SWITCH)
		controlP5.addToggle("TestSketch",false, 50,120, 20,20).setGroup(group);//setMode(controlP5.SWITCH)
		
		controlP5.addTextlabel("windowLine1","P4live:          1024 x 768 @ " + p.frameRate + " FPS", 100,100).setGroup(group);
		if (ow!=null){
		controlP5.addTextlabel("windowLine2","Output:          "+ow.getWindowWidth()+" x "+ow.getWindowHeight()+" @ "+" FPS", 100, 100)
				.setGroup(group);
		}
	}

	public void setPreferences(){
		group = (ControlGroup)controlP5.group(groupName);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);	
		controlP5.controller("OutputWindow").plugTo(this);
		controlP5.controller("Fullscreen").plugTo(this);
		controlP5.controller("TestSketch").plugTo(this);
	}
	
	public void OutputWindow(int value){
		boolean b = (value != 0);
		if (b)
			ow.enableOutput();
		else
			ow.disableOutput();
	}
	
	public void Fullscreen(int value){
		boolean b = (value != 0);
		if (b)
			ow.enableFullscreen();
		else
			ow.disableFullscreen();
	}
	
	public void TestSketch(int value){
		boolean b = (value != 0);
		if (b)
			ow.enableTest();
		else
			ow.disableTest();	
	}
}
