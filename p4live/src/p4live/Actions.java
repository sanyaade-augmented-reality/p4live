/**
 * 
 */
package p4live;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Actions {
	PApplet p;
	
	Actions(PApplet parent){
		p = parent;
	}

	public static void setAlpha(){}
	public static void tapBPM(){}
	public static void resetCam(){}
	public static void pauseCam(){}
	public static void rotX(){}
	public static void rotY(){}
	public static void rotZ(){}
	public static void zoom(){}	
	public static void modeColor(){}
	public static void saveFrame(){}
	public static void setSketch(){}
	public static void delSketch(){}
	public static void fov(){}
	public static void volumeSensitivity(){}
	public static void backgroundMode(){}
	public static void sendParam(){}
	public static void sendBang(){}

	public static void fullscreen(float value){
		boolean b = (value != 0);
		p4live.Interface.setFullScreen(b);
	}
	
	public static void beatSensitivity(float value){
		//p4live.Control.
	}
	
	public void execute(int id, float value){
		p.println("Action: "+ id);
		switch (id){
		case 2:
			fullscreen(value);
			break;
		case 3:
			beatSensitivity(value);
		default:
			p.println("Warning: Action unmapped");
		}
		
	}
}
