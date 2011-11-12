package p4sketch;

import java.util.ArrayList;

import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class ByeByeMorgan extends Sketch {	
	
	public ByeByeMorgan(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		p = arg0;
	}
	
	public void draw(){
		background(0);
		text("Bye bye Morgan",50,50);
		drawState();
	}
	
	private void drawState() {
		for (int i=0;i<EventsMidi.midiState.size();i++){
			if(EventsMidi.midiState.get(i).state){
				int channel = EventsMidi.midiState.get(i).channel;
				int pitch = EventsMidi.midiState.get(i).pitch;
				int velocity = EventsMidi.midiState.get(i).velocity;
				int color;
				
				switch(channel){	
				case P4Constants.BOMBO:
					color = color(50,pitch,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.CAJA:
					color = color(pitch,50,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.CHARLES:
					color = color(pitch,50,100);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.PERCUSION1:
					color = color(30,50,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.PERCUSION2:
					color = color(30,pitch,50);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.PERCUSION3:
					color = color(pitch,50,200);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.BAJO1:
					color = color(pitch,100,200);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.BAJO2:
					color = color(200,100,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;				
				case P4Constants.ACOMPA1:
					color = color(pitch,100,velocity);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;				
				case P4Constants.ACOMPA2:
					color = color(100,velocity,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;				
				case P4Constants.RIFF1:
					color = color(velocity,100,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.RIFF2:
					color = color(100,velocity,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;
				case P4Constants.RIFF3:
					color = color(velocity,150,pitch);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;				
				case P4Constants.SOUNDS:
					color = color(pitch,100,velocity);
					fill(color);
					ellipse(p.random(width), p.random(height), channel*30, channel*30);
				break;			
				default:
					p.println("* Warning Note ON unmpaped");
					break;
			}

			}
		}
	}


	
}