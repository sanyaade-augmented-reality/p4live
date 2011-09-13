/**
 * 
 */
package p4live;

import processing.core.PApplet;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import codeanticode.glgraphics.GLGraphicsOffScreen;
import codeanticode.glgraphics.GLTextureWindow;

/**
 * @author lot
 *
 */
public class OutputWindow {
	private PApplet p;
	private GraphicsEnvironment environment;

	// Creating an offscreen canvas to do some drawing on it.
	private GLGraphicsOffScreen layerOutput;
	private GLTextureWindow texWin;

	private int windowX = 0;
	private int windowY = 0;
	private int windowWidth = -1;
	private int windowHeight = -1;

	private boolean Output_Active = false;
	private boolean Output_Fullscreen = false;
	
	OutputWindow(PApplet parent){
		p = parent;
		detectDisplays();		
		//buildTextureWindow(windowX,windowY,windowWidth,windowHeight);
		setWindowWidth(400);
		setWindowHeight(400);
		buildTextureWindow(windowX,windowY,400,400);
	}
	
	private void buildTextureWindow(int x, int y, int w, int h) {
		// Additional arguments
		// * title string
		// * visible/not visible (first boolean argument)
		// * with/out borders (second boolean argument)
		// * resizable/fixed size (third boolean argument)
		texWin = new GLTextureWindow(p, "Window Output", x, y, w, h, true,	true, true);
		
		// Creating an offscreen canvas to do some drawing on it.
		layerOutput = new GLGraphicsOffScreen(p, w, h);

		// Attaching the offscreen texture to the window.
		texWin.setTexture(layerOutput.getTexture());
	}
	
	/**
	 * Detect the number of screens and ajust the size of visual window
	 */
	private void detectDisplays() {
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice devices[] = environment.getScreenDevices();

		if (devices.length > 1) { // we have a 2nd display/projector
			// learn the true dimensions of the secondary display
			windowWidth = devices[1].getDisplayMode().getWidth();
			windowHeight = devices[1].getDisplayMode().getHeight();
			windowX = windowWidth;
			// println("-> Adjusting visual window size to "+widthVisual+" x "+heightVisual+" (size of 2ndary display)");
			// frame.setBounds(devices[0].getDisplayMode().getWidth(), 0,
			// widthVisual, heightVisual);
		} else { // no 2nd screen but make it fullscreen anyway
			windowWidth = devices[0].getDisplayMode().getWidth();
			windowHeight = devices[0].getDisplayMode().getHeight();
		}
		// aspect = (float)(widthVisual)/(float)heightVisual; //Set aspect ratio
		// with new resolution
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
	
	/**
	 * @return the windowWidth
	 */
	public int getWindowWidth() {
		return windowWidth;
	}

	/**
	 * @param windowWidth the windowWidth to set
	 */
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
//		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, windowWidth, windowHeight, true,	true, true);
		
		// Creating an offscreen canvas to do some drawing on it.
		//layerOutput = new GLGraphicsOffScreen(p, windowWidth, windowHeight);

		// Attaching the offscreen texture to the window.
	//	texWin.setTexture(layerOutput.getTexture());
	}

	/**
	 * @return the windowHeight
	 */
	public int getWindowHeight() {
		return windowHeight;
	}

	/**
	 * @param windowHeight the windowHeight to set
	 */
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
		//texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, windowWidth, windowHeight, true,	true, true);
		//texWin.setTexture(layerOutput.getTexture());
	}


}
