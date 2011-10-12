/**
 * 
 */
package p4live;
import java.util.ArrayList;

import controlP5.ControlEvent;
import controlP5.ControllerInterface;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Interface {
	
	private static PApplet p;
	private static ArrayList Controls;
	private Control c;
	/*private ControlScreens cScreens;
	private ControlBeat cBeat;
	private ControlVolume cVolume;
	private ControlFFT cFFT;
	private ControlMidi cMidi;*/
	//private ControlBPM cBPM;
	
	Interface(PApplet parent){
		p = parent;
		c = new Control(p);
		Controls = new ArrayList();

		buildInterface();
		//loadInterface(); NO
	}

	private void buildInterface(){
		Controls.add(new ControlScreens());//0
		Controls.add(new ControlBeat());//1
		Controls.add(new ControlVolume());//2
		Controls.add(new ControlFFT());//3 error
		Controls.add(new ControlMidi());//4
		Controls.add(new ControlBPM());//5
		Controls.add(new ControlSketch("Sketch1",420,310));//6
		Controls.add(new ControlSketch("Sketch2",622,310));//7
		Controls.add(new ControlSketch("Sketch3",825,310));//8
		
	}
	
	public void loadInterface(){
		c.getControlP5().setFilePath("data/controlp5.xml");
		p.println("loading: "+c.getControlP5().filePath());

	//	c.getControlP5().setAutoInitialization(true);
	//    c.getControlP5().load("controlp5.xml");
	    c.getControlP5().load(p.dataPath("controlp5.xml")); 
	    setPreferences();
	}
	
	public void setPreferences(){
	    for(int k = 0;k<Controls.size();k++){
	    	((Control)Controls.get(k)).setGroupPreferences();
	    }
	}

	public void saveInterface(){
	    // save the current state/setup of all 
	    // controllers available.
		c.getControlP5().setFilePath("data/controlp5.xml");		
	    c.getControlP5().save();
	}
	
	public void printControls(){
		ControllerInterface[] ci;
		//c.getControlP5().
		ci =  c.getControlP5().getControllerList();
		p.println("elem: "+ ci.length);
		for (int k=0;k<ci.length;k++){
			p.println(ci[k].stringValue());
		}
	}
	
	public static int width(){
		return ((ControlScreens)Controls.get(0)).ow.getWindowWidth();
	}
	
	public static int height(){
		return ((ControlScreens)Controls.get(0)).ow.getWindowHeight();
	}

	public static void pingMidi(){
		((ControlMidi)Controls.get(4)).pingComunication();
	}
	
}
