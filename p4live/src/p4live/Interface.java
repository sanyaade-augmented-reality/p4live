/**
 * 
 */
package p4live;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * @author lot
 *
 */
public class Interface {
	
	PApplet p;
	ArrayList Controls;
	Control c;
	
	Interface(PApplet parent){
		p = parent;
		c = new Control(p);
		Controls = new ArrayList();

		buildInterface();
	}

	private void buildInterface(){
		Controls.add(new ControlScreens());
	}
	
}
