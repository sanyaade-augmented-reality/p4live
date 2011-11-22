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
package p4control;

import controlP5.ControlGroup;
import controlP5.Textlabel;
import controlP5.Toggle;
import p4live.ControllerTexture;
import p4live.OutputWindow;

public class Output extends Control {

	private static OutputWindow ow;
	private ControllerTexture imageController;

	public Output() {
		groupName="Output";
		defaultX = 486;
		defaultY = defaultY+10;
		defaultWidth = 540;
		defaultHeight = 290;
		//Set Output window
		ow = new OutputWindow(p); 
		buildInterface();
		setPreferences();
	}

	private void buildInterface() {
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(150);
		
		imageController = new ControllerTexture(controlP5,groupName+"Preview",10,10,260,260);
		imageController.setGroup(group);
		imageController.setChannel(0);
		controlP5.register(imageController);
	
		controlP5.addToggle("OutputWindow", true,280, 10, 20, 20).setGroup(group).plugTo(this);
		controlP5.addToggle("Fullscreen",false,280, 50, 20,20).setGroup(group).plugTo(this);//setMode(controlP5.SWITCH)
		controlP5.addToggle("TestSketch",false, 280,90, 20,20).setGroup(group).plugTo(this);//setMode(controlP5.SWITCH)
		
		controlP5.addTextlabel("control","Control Window: "+p.width+"px x "+p.height+"px @ "+ p.frameRate + " FPS", 280,260).setGroup(group);
		controlP5.addTextlabel("output", "Output Window:  "+ow.getWidth()+"px x "+ow.getHeight()+"px @ "+ow.frameRate()+" FPS", 280, 270).setGroup(group);	
	}

	public void setPreferences(){
		group = (ControlGroup)controlP5.group(groupName);
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);	
		/*controlP5.controller("OutputWindow").plugTo(this);
		controlP5.controller("Fullscreen").plugTo(this);
		controlP5.controller("TestSketch").plugTo(this);*/
		
		((Textlabel)controlP5.controller("control")).setWidth(defaultWidth);
		((Textlabel)controlP5.controller("output")).setWidth(defaultWidth);
	}
	
	public void OutputWindow(int value){
		boolean b = (value != 0);
		if (b)
			ow.enableOutput();
		else
			ow.disableOutput();
	}
	
	public void Fullscreen(int value){
		boolean b = (value != 0);
		if (b){
			ow.enableFullscreen();
			controlP5.controller("TestSketch").setValue(0);
		}
		else{
			ow.disableFullscreen();
			controlP5.controller("TestSketch").setValue(0);
		}
		String s = "Output window:          "+ow.getWidth()+" x "+ow.getHeight()+" @ "+" FPS";
		
		((Textlabel)controlP5.controller("output")).setValue(s);
	}
	
	public void TestSketch(int value){
		boolean b = (value != 0);
		if (b)
			ow.enableTest();
		else
			ow.disableTest();	
	}

	public static void update() {
		ow.update();
	}
}
