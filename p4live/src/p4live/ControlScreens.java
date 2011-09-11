package p4live;

import processing.core.PApplet;
import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.ControlP5XMLElement;
import controlP5.Controller;
import controlP5.Tab;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import processing.opengl.*;
import codeanticode.glgraphics.*;

public class ControlScreens extends Control {

	private int secondScreenWidth = -1;
	private int secondScreenHeight = -1;
	private boolean Output_Active = false;
	private boolean Output_Fullscreen = false;
	private GLTextureWindow texWin;
	private GraphicsEnvironment environment;

	// Creating an ofscreen canvas to do some drawing on it.
	private GLGraphicsOffScreen layerOutput;

	ControlScreens() {
		detectDisplays();
		build();
		setPreferences();
	}

	private void enableOutput() {
		texWin.show();
	}

	private void disableOutput() {
		texWin.hide();
	}

	private void enableFullScreen() {
		texWin.setOverride(true);
	}

	private void disableFullScreen() {
		texWin.setOverride(false);
	}

	private void build() {
		// ControlGroup(ControlP5 theControlP5, ControllerGroup theParent,
		// java.lang.String theName, int theX, int theY, int theW, int theH)

		group = controlP5.addGroup("Control Screens", 100, 100, 500);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);

		
		// OutputPreview(ControlP5 theControlP5, String theName, int theX, int
		// theY, int theWidth, int theHeight) {
		//OutputPreview op = new OutputPreview(controlP5, "Output Preview", 0,0, 400,400);
		//op.setGroup(cs);
		//controlP5.register(op);

		controlP5.addToggle("Output_Active", 100, 100, 20, 20).setGroup(group);
		//controlP5.addToggle("toggleValue",true,100,240,100,20).setMode(ControlP5.SWITCH);
		controlP5.addTextlabel("windowLine1","P4live:          1024 x 768 @ " + p.frameRate + " FPS", 100,100).setGroup(group);
		controlP5.addTextlabel("windowLine2","Video Output: "+secondScreenWidth+" x "+secondScreenHeight+" @ "+" FPS", 100, 100)
				.setGroup(group);
		
	}

	public void setPreferences(){
		group = (ControlGroup)controlP5.group("Control Screens");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		
	}
	
	/**
	 * Detect the number of screens and ajust the size of visual window
	 */
	private void detectDisplays() {
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice devices[] = environment.getScreenDevices();
		int xWindow = 0;
		int yWindow = 0;

		if (devices.length > 1) { // we have a 2nd display/projector
			// learn the true dimensions of the secondary display
			secondScreenWidth = devices[1].getDisplayMode().getWidth();
			secondScreenHeight = devices[1].getDisplayMode().getHeight();
			xWindow = secondScreenWidth;
			// println("-> Adjusting visual window size to "+widthVisual+" x "+heightVisual+" (size of 2ndary display)");
			// frame.setBounds(devices[0].getDisplayMode().getWidth(), 0,
			// widthVisual, heightVisual);
		} else { // no 2nd screen but make it fullscreen anyway
			secondScreenWidth = devices[0].getDisplayMode().getWidth();
			secondScreenHeight = devices[0].getDisplayMode().getHeight();
		}
		// aspect = (float)(widthVisual)/(float)heightVisual; //Set aspect ratio
		// with new resolution

		// Setting window values
		// buildTextureWindow(xWindow,yWindow,secondScreenWidth,secondScreenHeight);
		buildTextureWindow(xWindow, yWindow, 400, 400); // borrar
	}

	private void buildTextureWindow(int x, int y, int w, int h) {
		// Additional arguments
		// * title string
		// * visible/not visible (first boolean argument)
		// * with/out borders (second boolean argument)
		// * resizable/fixed size (third boolean argument)
		texWin = new GLTextureWindow(p, "Window Output", x, y, w, h, true,
				true, true);

		// Creating an ofscreen canvas to do some drawing on it.
		layerOutput = new GLGraphicsOffScreen(p, w, h);

		// Attaching the offscreen texture to the window.
		texWin.setTexture(layerOutput.getTexture());
	}

}

class OutputPreview extends Controller {

	// 4 fields for the 2D controller-handle
	int cWidth = 10, cHeight = 10;
	float cX, cY;

	OutputPreview(ControlP5 theControlP5, String theName, int theX, int theY,
			int theWidth, int theHeight) {

		super(theControlP5, (Tab) (theControlP5.getTab("default")), theName,
				theX, theY, theWidth, theWidth);
		// the Controller class provides a field to store values in an
		// float array format. for this controller, 2 floats are required.
		_myArrayValue = new float[2];
	}

	// overwrite the updateInternalEvents method to handle mouse and key inputs.
	/*
	 * public void updateInternalEvents(PApplet theApplet) { if(getIsInside()) {
	 * if(isMousePressed && !controlP5.keyHandler.isAltDown) { cX =
	 * p.constrain(p.mouseX-position.x(),0,width-cWidth); cY =
	 * p.constrain(p.mouseY-position.y(),0,height-cHeight); setValue(0); } } }
	 */

	// overwrite the draw method for the controller's visual representation.
	public void draw(PApplet theApplet) {
		// use pushMatrix and popMatrix when drawing
		// the controller.
		theApplet.pushMatrix();
		theApplet.translate(position().x(), position().y());

		/*
		 * // draw the background of the controller. if(getIsInside()) {
		 * theApplet.fill(150); } else { theApplet.fill(100); }
		 * p.rect(0,0,width,height);
		 * 
		 * // draw the controller-handle p.fill(255);
		 * p.rect(cX,cY,cWidth,cHeight);
		 */
		// p.rect(cX,cY,cWidth,cHeight);
		// p.image(layerOutput.getTexture(),0,0);

		// draw the caption- and value-label of the controller
		// they are generated automatically by the super class
		captionLabel().draw(theApplet, 0, height + 4);
		valueLabel().draw(theApplet, 40, height + 4);

		theApplet.popMatrix();
	}

	public void setValue(float theValue) {
		// setValue is usually called from within updateInternalEvents
		// in case of changes, updates. the update of values or
		// visual elements is done here.
		_myArrayValue[0] = cX / ((float) (width - cWidth) / (float) width);
		_myArrayValue[1] = cY / ((float) (height - cHeight) / (float) height);
		// update the value label.
		valueLabel().set(
				adjustValue(_myArrayValue[0], 0) + " / "
						+ adjustValue(_myArrayValue[1], 0));

		// broadcast triggers a ControlEvent, updates are made to the sketch,
		// controlEvent(ControlEvent) is called.
		// the parameter (FLOAT or STRING) indicates the type of
		// value and the type of methods to call in the main sketch.
		broadcast(FLOAT);
	}

	// needs to be implemented since it is an abstract method in
	// controlP5.Controller
	// nothing needs to be set since this method is only relevant for saving
	// controller settings and only applies to (most) default Controllers.
	public void addToXMLElement(ControlP5XMLElement theElement) {
	}
}
