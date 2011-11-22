/*
This file is part of P4Live :: Processing 4 Live 
by Lot Amoros 
http://p4live.feenelcaos.org

P4Live is free software:
you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License.

P4Live is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with P4Live.  If not, see <http://www.gnu.org/licenses/>.
 */

package p4live;

import processing.core.PApplet;
import controlP5.ControlP5;
//import controlP5.ControlP5XMLElement;
import controlP5.Controller;
import controlP5.Tab;

public class ControllerTexture extends Controller {
	private int textureWidth=0;
	private int textureHeight=0;
	private float aspectRatio =0;
	private float alpha = 1;
	private int channel= 0;
	
  public ControllerTexture(ControlP5 theControlP5, String theName, int theX, int theY, int theWidth, int theHeight) {
    // the super class Controller needs to be initialized with the below parameters
    super(theControlP5,  (Tab)(theControlP5.getTab("default")), theName, theX, theY, theWidth, theHeight);
    this.setCaptionLabel("Preview");
  }

  // overwrite the updateInternalEvents method to handle mouse and key inputs.
  public void updateInternalEvents(PApplet p) {
  }

  public void setAlpha(float a){
	  alpha = a;
  }
  
  // overwrite the draw method for the controller's visual representation.
  public void draw(PApplet p) {
    p.pushMatrix();
    p.pushStyle();
    p.translate(10, 10);
    // draw the background of the controller.	
    p.fill(0);
    p.rect(0,0,textureWidth,textureHeight);
    //aspectRatio = p4live.OutputWindow.getWidth() / p4live.OutputWindow.getHeight();
    
    textureWidth = width;
    textureHeight = (OutputWindow.getHeight() * textureWidth) / OutputWindow.getWidth();
    
    p.tint(255, 255);
    p.image(Interface.textureSketch(channel),0,0,textureWidth,textureHeight);
    
    captionLabel().draw(p, 0, height + 4);
    p.popStyle();
    p.popMatrix();
  }
  
  public void setChannel(int i){
	  channel = i;
  }

  public void setValue(float theValue) {
    // setValue is usually called from within updateInternalEvents
    // in case of changes, updates. the update of values or 
    // visual elements is done here.

    // broadcast triggers a ControlEvent, updates are made to the sketch, 
    // controlEvent(ControlEvent) is called.
    // the parameter (FLOAT or STRING) indicates the type of 
    // value and the type of methods to call in the main sketch.
    //broadcast(FLOAT);

  }

}