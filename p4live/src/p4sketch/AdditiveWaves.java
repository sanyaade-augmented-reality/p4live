package p4sketch;

import p4control.Beats;
import p4control.Volume;
import p4live.OutputWindow;
import p4live.P4live;
import processing.core.*; 
import toxi.math.waves.*;  

public class AdditiveWaves extends Sketch {


AbstractWave waveX,waveY;

int STEP = 10;
int DIM = 800;
int D2 = DIM/2;
int AMP = 150;

public AdditiveWaves(PApplet arg0, int arg1, int arg2) {
	super(arg0, arg1, arg2);
	
	  waveX=createRandomWave(arg0);
	  waveY=createRandomWave(arg0);

	  STEP = 10;	//Paso
	  DIM = 800;	//Tama–o
	  D2 = DIM/2;	//Dimension
	  AMP = 80;		//Amplitud
}


public void draw() {
	background(0);
	noStroke();
  fill(255);
  lights();
  pushMatrix();
  translate(OutputWindow.getWidth()*0.5f, OutputWindow.getHeight()*0.5f);
  
  scale(0.2f+2*Volume.level);
  float prevY = waveY.update();
  float colPrevY = 0;
  waveY.push();
  for(int y = 0; y < DIM; y += STEP) {
    float valueY = waveY.update();
    float colY = valueY * 128 * Beats.hat + 128;
    waveX.push();
    beginShape(TRIANGLE_STRIP);
    for(int x = 0; x < DIM; x += STEP) {
      float valueX = waveX.update();
      float colX = valueX * 200 * Beats.kick + 55;
      fill(colX, Volume.level*128, colPrevY);
      vertex(x - D2, y - STEP - D2, (valueX + prevY) * AMP);
      fill(colX, Volume.level*128, colY);
      vertex(x - D2, y - D2, (valueX + valueY) * AMP);
    }
    endShape();
    waveX.pop();
    prevY = valueY;
    colPrevY = colY;
  }
  waveY.pop();
  waveX.update();
  waveY.update();
  popMatrix();
}

/*
public void event(int evento){
	switch(evento){
	case 1:
		  waveX=createRandomWave(arg0);
		  break;
	case 3:
		  waveY=createRandomWave(arg0);
		  break;
	}
}
*/
public AbstractWave createRandomWave(PApplet p) {
  AbstractWave w=null;
  AbstractWave fmod = new SineWave(0, p.random(0.005f, 0.02f), p.random(0.1f, 0.5f), 0);
  float freq=p.random(0.005f, 0.05f);
  
  switch((int)p.random(7)) {
  case 0:
    w = new FMTriangleWave(0, freq, 1, 0, fmod);
    break;
  case 1:
    w = new FMSawtoothWave(0, freq, 1, 0, fmod);
    break;
  case 2:
    w = new FMSquareWave(0, freq, 1, 0, fmod);
    break;
  case 3:
    w = new FMHarmonicSquareWave(0, freq, 1, 0, fmod);
    ((FMHarmonicSquareWave)w).maxHarmonics=(int)p.random(3,30);
    break;
  case 4:
    w = new FMSineWave(0, freq, 1, 0, fmod);
    break;
  case 5:
    w = new AMFMSineWave(0, freq, 0, fmod, new SineWave(0, p.random(0.01f,0.2f), p.random(2, 3), 0));
    break;
  case 6:
    w = new ConstantWave(p.random(-1,1));
    break;
  }
  return w;
}

}
