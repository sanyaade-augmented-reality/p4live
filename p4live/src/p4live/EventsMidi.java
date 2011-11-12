/**
 * 
 */
package p4live;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import p4control.Control;
import p4control.Midi;
import processing.core.PApplet;
import themidibus.MidiBus;

/**
 * @author lot
 * All events are events.
 */
public class EventsMidi extends Events{
	private static int controlChannel=-1;
	private static int soundChannel=-1;
	private static Hashtable<Integer, String> tableControl;
	private static Hashtable<Integer, Integer> tableSound;	
	private static int controlMap = -1;
	private static String controllerMap = "";
	
	public static ArrayList<NoteState> midiState;			//List of notes ON   
	
	EventsMidi(PApplet parent){
		p = parent;
		desc="Midi Events";
		tableControl  = new Hashtable<Integer, String>(); 
		tableSound    = new Hashtable<Integer, Integer>();
		midiState = new ArrayList<NoteState>();

		//loadDefaultActions();
	}
	
	public static void restartMaping(){
		controlMap = -1;
		controllerMap="";
	}
	
	public static void setControlChannel(int ch){
		controlChannel = ch;
	}
	
	public static void setSoundChannel(int ch){
		soundChannel = ch;
	}			
	
	public static void mapControl(int number,String action){
		tableControl.put(number, action);
	}

	public static void mapSound(int number,int action){
		tableSound.put(number, action);
	}
	
	public static void noteOn(int channel, int pitch, int velocity, String bus_name){	
		//actions.execute(number, value);
		Interface.noteOn(channel, pitch, velocity);
		
		NoteState note = new NoteState();
		note.channel = channel;
		note.pitch = pitch;
		note.velocity = velocity;
		//note.state = true;

		int index = midiState.indexOf(note);

		if (index == -1)
			midiState.add(note);
		else{
			midiState.get(index).state = true;
			midiState.get(index).velocity = velocity;
		}
	}
	
	public static void noteOff(int channel, int pitch, int velocity, String bus_name){
		//float value= p.map(velocity, 0, 127, 0, 1);
		//actions.execute(number, value);
		Interface.noteOff(channel, pitch, velocity);
		NoteState note = new NoteState();
		note.channel = channel;
		note.pitch = pitch;
		note.velocity = velocity;
		//note.state = true;

		int index = midiState.indexOf(note);
		if (index != -1){
			midiState.get(index).state = false;	
		}
	}

	public static void setControllerMap(String controller){
		controllerMap = controller;
		if (controlMap != -1){
			mapControl(controlMap,controllerMap);
			Midi.endMap();
		}
	}
	
	/*public static void noteOn(int channel, int pitch, int vel){
		
	}*/
	
	public static void controllerChange(int channel, int number, int value, String bus_name){
		if (channel == controlChannel){
			Midi.pingControl();
			if (Midi.isMaping()){
				controlMap = number;
				if (!controllerMap.equals("")){
					mapControl(controlMap,controllerMap);
					Midi.endMap();
				}
			}			
			else{//No mapping
			if (tableControl.containsKey(number)){
				String action = tableControl.get(number);
				float v = p.map(value, 0, 127, 0, 1);
				Control.changeController(action, v);
			}
		}
		}else {
			//Sound
			Midi.pingSound();
			if (tableSound.containsKey(number)){
				
			}
		}	
	}
	
	/*public void captureMIDIEvent(int channel, int number, int v, String bus_name) {
		p.println("Controller Change: " + channel);
		float  value=-1;
		value = p.norm(v, 0, 127);
		
		//Hashtable t;
	//	t = tableEvents.get(channel);
		
		//actions.execute(getAction(channel,number), value);
	}*/
	
	// Name of all controller events
	/*public void loadDefaultActions(){
		mapControl(11,"Sketch1Alpha");
		mapControl(12,"Sketch2Alpha");
		
	}*/
	
    public static void saveMaping() {
        try {
            p.println("* Saving midi mapping...");
           
            FileOutputStream fileOut = new FileOutputStream("midimapping.p4live");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(tableControl);

            out.close();
            fileOut.close();
           
        } catch(FileNotFoundException e) {
            p.println("Error writing file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadMaping() {
        try {
        	tableControl.clear();
            p.println("* Loading midi mapping...");
           
            FileInputStream fileIn = new FileInputStream("midimapping.p4live");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            tableControl = (Hashtable)in.readObject();
 
            in.close();
            fileIn.close();
           
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
        	p.println("Error loading file");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
}
