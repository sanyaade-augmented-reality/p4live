/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amor—s 
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
import java.util.ArrayList;

import codeanticode.glgraphics.GLTexture;

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
	private static Control c;
	
	/*private ControlScreens cScreens;
	private ControlBeat cBeat;
	private ControlVolume cVolume;
	private ControlFFT cFFT;
	private ControlMidi cMidi;*/
	//private ControlBPM cBPM;
	//private ControlOutput;
	
	Interface(PApplet parent){
		p = parent;
		c = new Control(p);
		Controls = new ArrayList();

		buildInterface();
		//loadInterface(); NO
	}

	private void buildInterface(){
		Controls.add(new Output());//0
		Controls.add(new Beat());//1
		Controls.add(new Volume());//2
		Controls.add(new FastFourierTransformation());//3 error
		Controls.add(new Midi());//4
		Controls.add(new BPM());//5
		Controls.add(new SketchControl("Sketch1",420,310));//6
		Controls.add(new SketchControl("Sketch2",622,310));//7
		Controls.add(new SketchControl("Sketch3",825,310));//8
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

	public void update(){
		((Output)Controls.get(0)).update();
		((SketchControl)Controls.get(6)).update();
		((SketchControl)Controls.get(7)).update();
		((SketchControl)Controls.get(8)).update();
	}
	
	public static void pingMidi(){
		((Midi)Controls.get(4)).pingComunication();
	}
	
	public static GLTexture sketch1(){
		return ((SketchControl)Controls.get(6)).getTexture();
	}
	
	public static GLTexture sketch2(){
		return ((SketchControl)Controls.get(7)).getTexture();
	}
	
	public static GLTexture sketch3(){
		return ((SketchControl)Controls.get(8)).getTexture();
	}
}
