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

import processing.core.PApplet;
//import sketch.Sketch;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Sketch extends GLGraphicsOffScreen {
	
	protected float[] params = new float[3];

	protected float rotX = 0.5f;
	protected float rotY = 0.5f;
	protected float rotZ = 0.5f;

	protected float speedX = 0.5f;
	protected float speedY = 0.5f;
	protected float speedZ = 0;

	protected float cameraX = 0.5f;
	protected float cameraY = 0.5f;
	protected float cameraZ = 0.5f;
	
	float cx = 0;
	float cy = 0;
	float cz = 0;

	float rx = 0;
	float ry = 0;
	float rz = 0;

	private int fov;
	
	public Sketch(PApplet arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		params[0] = 0;
		params[1] = 0;
		params[2] = 0;
	}
	
	/**
	 * You need to rewrite this method if you want to draw
	 */
	public void draw(){	}

	/**
	 * You need to rewrite this metod for receive events
	 * @param event Type of event
	 */
	public void event(int event) { }
	
	
	/**
	 * ACTIONS
	 */
	
	/**
	 * You need to rewrite this metod for see MIDI NOTE ON/OFF events
	 * @param channel The channel of the note
	 */
	public void mapNoteOn(int channel, int pitch, int velocity){ }
	
	/**
	 * You need to rewrite this metod for see MIDI NOTE ON/OFF events
	 * @param channel The channel of the note
	 */
	public void mapNoteOff(int channel, int pitch, int velocity){ }
	
	/**
	 * Call this method before draw for no problems with other sketches
	 */
	public void begin() {
		beginDraw();
		pushStyle();
		pushMatrix();
		cx = P4live.map(cameraX, 0, 1, -1000, 1000);
		cy = P4live.map(cameraY, 0, 1, -1000, 1000);
		cz = P4live.map(cameraZ, 0, 1, -1000, 1000);
		translate(cx,cy,cz);
		
		rx = P4live.map(rotX, 0, 1, -180, 180);
		ry = P4live.map(rotY, 0, 1, -180, 180);
		rz = P4live.map(rotZ, 0, 1, -180, 180);
		rotateX(P4live.radians(rx));
		rotateY(P4live.radians(ry));
		rotateZ(P4live.radians(rz));
		}
	
	/**
	 * Call this method after draw for no problems with other sketches
	 */
	public void end(){
		popMatrix();
		popStyle();
		endDraw();
	}	
	
	/**
	 * Change fov 1 to 150 degrees
	 * @param value degrees
	 */
	public void setFov(int value){
		int f = P4live.constrain(value, 1, 150);
		fov = f;
	    float cameraZ = ((height/2.0f) / P4live.tan(PI*60.0f/360.0f));
	    //float zNear = cameraZ/10.0f;//default	
		float zNear = cameraZ/2.0f;
	    float zFar = cameraZ*10.0f;
		float aspect = width/height;
		perspective(P4live.radians(fov), aspect, zNear, zFar);
	}

	/**
	 * Set fov perspective
	 * @param value nomalised [0-1]
	 */
	public void setFov(float value){
		fov = (int) (value*150);
	    float cameraZ = ((height/2.0f) / P4live.tan(PI*60.0f/360.0f));
	    //float zNear = cameraZ/10.0f;//default	
		float zNear = cameraZ/2.0f;
	    float zFar = cameraZ*10.0f;
		float aspect = width/height;
		(this).perspective(P4live.radians(fov), aspect, zNear, zFar);
	}
	
	/**
	 * @return the params
	 */
	public float[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(float[] params) {
		this.params = params;
	}

	/**
	 * @return the rotX
	 */
	public float getRotX() {
		return rotX;
	}

	/**
	 * @param rotX the rotX to set
	 */
	public void setRotationX(float rotX) {
		this.rotX = rotX;
	}

	/**
	 * @return the rotY
	 */
	public float getRotY() {
		return rotY;
	}

	/**
	 * @param rotY the rotY to set
	 */
	public void setRotationY(float rotY) {
		this.rotY = rotY;
	}

	/**
	 * @return the rotZ
	 */
	public float getRotZ() {
		return rotZ;
	}

	/**
	 * @param rotZ the rotZ to set
	 */
	public void setRotationZ(float rotZ) {
		this.rotZ = rotZ;
	}

	/**
	 * @return the speedX
	 */
	public float getSpeedX() {
		return speedX;
	}

	/**
	 * @param speedX the speedX to set
	 */
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	/**
	 * @return the speedY
	 */
	public float getSpeedY() {
		return speedY;
	}

	/**
	 * @param speedY the speedY to set
	 */
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	/**
	 * @return the speedZ
	 */
	public float getSpeedZ() {
		return speedZ;
	}

	/**
	 * @param speedZ the speedZ to set
	 */
	public void setSpeedZ(float speedZ) {
		this.speedZ = speedZ;
	}

	/**
	 * @return the zoomX
	 */
	public float getCameraX() {
		return cameraX;
	}

	/**
	 * @param zoomX the zoomX to set
	 */
	public void setCameraX(float zoomX) {
		this.cameraX = zoomX;
	}

	/**
	 * @return the zoomY
	 */
	public float getCameraY() {
		return cameraY;
	}

	/**
	 * @param zoomY the zoomY to set
	 */
	public void setCameraY(float zoomY) {
		this.cameraY = zoomY;
	}

	/**
	 * @return the zoomZ
	 */
	public float getCameraZ() {
		return cameraZ;
	}

	/**
	 * @param zoomZ the zoomZ to set
	 */
	public void setCameraZ(float zoomZ) {
		this.cameraZ = zoomZ;
	}
}
