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
	private TestScreen testSketch;
	private BlackScreen blackScreen;
	
	private GLTextureWindow texWin;

	private int windowX = 0;
	private int windowY = 0;
	private int windowWidth = 400;
	private int windowHeight = 400;
	private int screenWidth = -1;
	private int screenHeight = -1;
	
	private boolean Output_Active = false;
	private boolean Output_Fullscreen = false;
	
	OutputWindow(PApplet parent){
		p = parent;
		detectDisplays();		
		
		// Creating an offscreen canvas to do some drawing on it.
		//layerOutput = new GLGraphicsOffScreen(p, w, h);
		testSketch = new TestScreen(p, windowWidth, windowHeight);
		blackScreen = new BlackScreen(p, windowWidth, windowHeight);
		
		/*testSketch.beginDraw();
		testSketch.draw();
		testSketch.endDraw();*/
		
		blackScreen.beginDraw();
		//blackScreen.draw();
		blackScreen.endDraw();
		
		buildTextureWindow(windowX,windowY,windowWidth,windowHeight);
	}
	
	private void buildTextureWindow(int x, int y, int w, int h) {
		// Additional arguments
		// * title string
		// * visible/not visible (first boolean argument)
		// * with/out borders (second boolean argument)
		// * resizable/fixed size (third boolean argument)
		texWin = new GLTextureWindow(p, "Window Output", x, y, w, h, true,	false, true);
		// Attaching the offscreen texture to the window.
		texWin.setTexture(blackScreen.getTexture());
	}
	
	/**
	 * Detect the number of screens and adjust the size of visual window
	 */
	private void detectDisplays() {
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice devices[] = environment.getScreenDevices();

		if (devices.length > 1) { // we have a 2nd display/projector
			// learn the true dimensions of the secondary display
			screenWidth = devices[1].getDisplayMode().getWidth();
			screenHeight = devices[1].getDisplayMode().getHeight();
			windowX = windowWidth;
			// println("-> Adjusting visual window size to "+widthVisual+" x "+heightVisual+" (size of 2ndary display)");
			// frame.setBounds(devices[0].getDisplayMode().getWidth(), 0,
			// widthVisual, heightVisual);
		} else { // no 2nd screen but make it fullscreen anyway
			screenWidth = devices[0].getDisplayMode().getWidth();
			screenHeight = devices[0].getDisplayMode().getHeight();
		}
		// aspect = (float)(widthVisual)/(float)heightVisual; //Set aspect ratio
		// with new resolution
	}
	
	public void enableOutput() {
		texWin.show();
	}

	public void disableOutput() {
		texWin.hide();
	}

	public void enableFullscreen() {
		//setWindowSize(screenWidth,screenHeight);
		texWin.hide();
		texWin = null;
		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, screenWidth, screenHeight, true,	false,false);
		texWin.setTexture(blackScreen.getTexture());
	}

	public void disableFullscreen() {
		//setWindowSize(windowWidth,windowHeight);
		texWin.hide();
		texWin = null;
		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, windowWidth, windowHeight, true,	true,true);
		texWin.setTexture(blackScreen.getTexture());
	}	
	
	public void enableTest() {
		testSketch.beginDraw();
		testSketch.draw();
		testSketch.endDraw();
		texWin.setTexture(testSketch.getTexture());
	}

	//Cambiar
	public void disableTest() {
		texWin.setTexture(blackScreen.getTexture());
	}

	
	/*public void setWindowSize(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
		texWin.hide();
		texWin = null;
		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, windowWidth, windowHeight, true,	true, true);
		// Attaching the offscreen texture to the window.
		texWin.setTexture(blackScreen.getTexture());
	}*/

	/**
	 * @return the windowWidth
	 */
	public int getWindowWidth() {
		if (texWin!=null)
			return texWin.getWidth();
		else
			return -1;
	}	
	
	/**
	 * @return the windowHeight
	 */
	public int getWindowHeight() {
		if (texWin!=null)
			return texWin.getHeight();
		else
			return -1;
	}
}
