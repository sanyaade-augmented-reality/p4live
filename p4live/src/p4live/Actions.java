/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amoros 
http://p4live.feenelcaos.org

P4Live is free software:
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
			//fullscreen(value);
			break;
		case 3:
			//beatSensitivity(value);
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
