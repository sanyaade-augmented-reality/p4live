/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amoros 
http://p4live.feenelcaos.org

P4VJ is free software:
you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License.

P4VJ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with P4Live.  If not, see <http://www.gnu.org/licenses/>.
 */
package p4live;

import p4control.Mixer;
import p4sketch.BlackScreen;
import p4sketch.Sketch;
import p4sketch.TestScreen;
import processing.core.PApplet;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import codeanticode.glgraphics.GLGraphicsOffScreen;
import codeanticode.glgraphics.GLTexture;
import codeanticode.glgraphics.GLTextureWindow;

/**
 * @author lot
 *
 */
public class OutputWindow {
	private PApplet p;
	private GraphicsEnvironment environment;

	// Creating an offscreen canvas to do some drawing on it.
	private static Sketch testSketch;
	private static Sketch blackScreen;
	private static GLTextureWindow texWin;

	private int windowX = 0;
	private int windowY = 0;
	private static int windowWidth = 400;
	private static int windowHeight = 400;
	private static int screenWidth = -1;
	private static int screenHeight = -1;
	
	private boolean active = false;
	private boolean test = false;
	private static boolean fullscreen = false;
	
	public OutputWindow(PApplet parent){
		p = parent;
		detectDisplays();		
		
		//layerOutput = Interface.sketch(1);
		//layerOutput = new GLTexture(p,getWidth(),getHeight());
		testSketch = new TestScreen(p,getWidth(),getHeight());
		blackScreen = new BlackScreen(p,getWidth(),getHeight());
		
		buildTextureWindow(windowX,windowY,windowWidth,windowHeight);
	}
	
	private void buildTextureWindow(int x, int y, int w, int h) {
		// Additional arguments
		// * title string
		// * visible/not visible (first boolean argument)
		// * with/out borders (second boolean argument)
		// * resizable/fixed size (third boolean argument)
		texWin = new GLTextureWindow(p, "Window Output", x, y, w, h, true,	true, true);
		// Attaching the offscreen texture to the window.
		texWin.setTexture(Mixer.layerOutput);
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
		active = true;
		texWin.show();
	}

	public void disableOutput() {
		active = false;
		texWin.hide();
	}

	public void enableFullscreen() {
		fullscreen = true;
		texWin.hide();
		texWin = null;
		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, screenWidth, screenHeight, true,	false,false);
		blackScreen = null;
		blackScreen = new BlackScreen(p, screenWidth, screenHeight);
		texWin.setTexture(Mixer.layerOutput);
	}

	public void disableFullscreen() {
		fullscreen = false;
		texWin.hide();
		texWin.delete();
		texWin = null;
		texWin = new GLTextureWindow(p, "Window Output", windowX, windowY, windowWidth, windowHeight, true,	true,true);
		blackScreen = null;
		blackScreen = new BlackScreen(p, screenWidth, screenHeight);
		texWin.setTexture(blackScreen.getTexture());
	}	
	
	public void enableTest() {
		test = true;
		testSketch = null;
		testSketch = new TestScreen(p,getWidth(),getHeight());
		testSketch.begin();
		testSketch.draw();
		testSketch.end();
		texWin.setTexture(testSketch.getTexture());
	}

	//Cambiar
	public void disableTest() {
		test = false;
		texWin.setTexture(Mixer.layerOutput);
	}

	/**
	 * @return the windowWidth
	 */
	public static int getWidth() {
		if (fullscreen)
			return screenWidth;
		else
			return windowWidth;
		//return texWin.getWidth();
	}	
	
	/**
	 * @return the windowHeight
	 */
	public static int getHeight() {
		if (fullscreen)
			return screenHeight;
		else
			return windowHeight;
	//return texWin.getHeight();
	}
	
	public static int frameCount(){//Borrar y avisar a andres
		try{
			return texWin.frameCount;
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public static float frameRate(){
		try{
			return texWin.frameRate;
		}
		catch(Exception e){
			return 0;
		}
	}

	public void update() {
		
		if (test == true){
			testSketch.begin();
			testSketch.draw();
			testSketch.end();
		}		
	}
}
