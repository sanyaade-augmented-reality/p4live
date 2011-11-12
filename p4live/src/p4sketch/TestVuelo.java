package p4sketch;

import java.util.ArrayList;

import p4live.EventsMidi;
import p4live.NoteState;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class TestVuelo extends Sketch {	
	
	public TestVuelo(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		p = arg0;
	}
	
	public void draw(){
		background(0);
		text("Test Vuelo",30,30);
		drawState();
	}
	
	private void drawState() {
		for (int i=0;i<EventsMidi.midiState.size();i++){
			if(EventsMidi.midiState.get(i).state){
				int v = EventsMidi.midiState.get(i).velocity;
				int can = EventsMidi.midiState.get(i).channel;
				int col = (int) p.map(v, 0, 127, 0, 255);
				int pitch = EventsMidi.midiState.get(i).pitch;
				
				fill(255);
				stroke(255);
				textSize(20);
				switch(can){	
				case P4Constants.BOMBO:
					//p.println("BOMBO ON");
					text("BOMBO", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.CAJA:
					//p.println("CAJA ON");
					text("CAJA", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.CHARLES:
					p.text("CHARLES", 30, 30+can*20);
					p.ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.PERCUSION1:
					text("PER1", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.PERCUSION2:
					text("PER2", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.PERCUSION3:
					text("PER3", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.BAJO1:
					text("BAJO1", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.BAJO2:
					text("BAJO2", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;				
				case P4Constants.ACOMPA1:
					text("ACOMPA1", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;				
				case P4Constants.ACOMPA2:
					text("ACOMPA2", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;				
				case P4Constants.RIFF1:
					text("RIFF1", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.RIFF2:
					text("RIFF2", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;
				case P4Constants.RIFF3:
					text("RIFF3", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;				
				case P4Constants.SOUNDS:
					text("SOUNDS", 30, 30+can*20);
					ellipse(50+pitch*10, can*20, 20, 20);
				break;			
				default:
					p.println("* Warning Note ON unmpaped");
					break;
			}

			}
		}
	}


	
}