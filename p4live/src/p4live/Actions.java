/**
 * 
 */
package p4live;

import java.util.Hashtable;

import controlP5.Controller;
import controlP5.Slider;
import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Actions {
	private static PApplet p;
	//private Hashtable<String, Integer> tableEvents;
	
	Actions(PApplet parent){
		p = parent;
		//tableEvents  = new Hashtable<String, Integer>(); 
		//loadDefaultActions();
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
	
	/*
	public static void bpmVelocity(float value){
		Slider bpmV = (Slider)p4live.Control.controlP5.controller("bpmVelocity");
		float min = bpmV.min();
		float max = bpmV.max();
		
		int v =(int) p.map(value,0,1,min,max);
		
		p4live.Interface.setBPM(v);
	}*/


	/*public static void fullscreen(Controller c){
		float value = c.value();
		boolean b = (value != 0);
		fullscreen(b);
	}*/
	
	

	public static void fullscreen(float value){
		boolean b = (value != 0);
		fullscreen(b);
	}
	
	public static void fullscreen(boolean b){
		p4live.Interface.setFullScreen(b);
	}
		
	public static void beatSensitivity(float value){

	}
	
	//sin normalizar
	/*public void execute(Controller c){
		float  value=-1;
		if ( (c.min() == 0) && (c.max() == 0)){
			value = c.value();
		}else{
			value = p.norm(c.value(), c.min(), c.max());
		}	
	}*/
	
	//Ya normalizados
	public void execute(int id, float value){
		p.println("Action: "+ id);
		switch (id){
		case 2:
			fullscreen(value);
			break;
		case 3:
			beatSensitivity(value);
			break;
		case 4:
			//bpmVelocity(value);
			break;
		
		default:
			p.println("Warning: Action unmapped");
		}
	}
	
	// Name of all controller events
	/*public void loadDefaultActions(){
		tableEvents.put("Fullscreen", 1);
		tableEvents.put("OutputWindow", 2);
		tableEvents.put("BeatSensitivity", 3);
		tableEvents.put("bpmVelocity", 4);
		
	}*/	
}
