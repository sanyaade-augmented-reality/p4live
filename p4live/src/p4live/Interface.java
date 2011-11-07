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

import p4control.BPM;
import p4control.Beat;
import p4control.Mixer;
import p4control.Control;
import p4control.FastFourierTransformation;
import p4control.Midi;
import p4control.Output;
import p4control.SketchControl;
import p4control.Volume;
import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Interface {
	
	private static PApplet p;
	private static ArrayList<Control> Controls;
	private static ArrayList<SketchControl> Channels;
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
		Controls = new ArrayList<Control>();
		Channels = new ArrayList<SketchControl>();
				
		//loadInterface(); NO
		
		SketchControl c1 = new SketchControl(1, 420, 310);
		SketchControl c2 = new SketchControl(2,622,310);
		SketchControl c3 = new SketchControl(3,825,310);
		
		Channels.add(0, c1);
		Channels.add(1, c2);
		Channels.add(2, c3);		
		buildInterface();
	}

	private void buildInterface(){
		Controls.add(new Mixer());//0
		Controls.add(new Output());//0
		Controls.add(new Beat());//1
		Controls.add(new Volume());//2
		Controls.add(new FastFourierTransformation());//3 
		Controls.add(new Midi());//4
		Controls.add(new BPM());//5
		//Controls.add(new Blend());//6
		
	}
	
	public void loadInterface(){
		//c.getControlP5().setFilePath("data/controlp5.xml");
		//p.println("loading: "+c.getControlP5().filePath());

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

/*
 * 	    save the current state/setup of all 
 * 	    controllers available.
 */
	public void saveInterface(){
		//c.getControlP5().setFilePath("data/controlp5.xml");		
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
		Output.update();
		
		for (int k=0;k<Channels.size();k++)
			Channels.get(k).update();
		
		Mixer.update();
	}
	
	public static GLTexture textureSketch(int channel){
		if (channel == 0)
			return Mixer.layerOutput;
		else
			return Channels.get(channel-1).getTexture();
	}

	public static void setSketch(int channel,int sketch){
		Channels.get(channel-1).setSketch(sketch);
	}
}
