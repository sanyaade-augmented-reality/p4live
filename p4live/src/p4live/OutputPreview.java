package p4live;

import codeanticode.glgraphics.GLGraphicsOffScreen;
import codeanticode.glgraphics.GLTextureWindow;
import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.ControlP5XMLElement;
import controlP5.Controller;
import controlP5.Tab;

public class OutputPreview extends Controller {
	PApplet p;
	

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