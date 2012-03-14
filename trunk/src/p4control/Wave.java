package p4control;

import controlP5.CColor;
import controlP5.Chart;
import controlP5.ChartDataSet;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;
import ddf.minim.analysis.FFT;

public class Wave extends Control{
	private Chart leftWave;
	private Chart rightWave;
	
	private static float[] f;
	private int bufferSize = in.bufferSize();
	private Slider waveGain;
	
	public Wave(){
		groupName="Wave";
		defaultWidth= 360;
		defaultHeight = 130;
		defaultX = 0;
		defaultY = 290;
		f = new float[bufferSize];
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);

		rightWave = controlP5.addChart("rightWave",10,70,300,50);
		rightWave.setGroup(group);
		rightWave.plugTo(this);
		
		leftWave = controlP5.addChart("leftWave",10,10,300,50);
		leftWave.setGroup(group);
		leftWave.plugTo(this);
		
		waveGain = controlP5.addSlider("waveGain",0,300,10,320,10,10,100);
		waveGain.setDecimalPrecision(1);
		waveGain.setGroup(group);
		waveGain.plugTo(this);
	}
	
	public void setPreferences(){
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);
		group.setWidth(defaultWidth);
		
		leftWave.setStrokeWeight(3);
		leftWave.setResolution(bufferSize);
		leftWave.setBehavior(new leftUpdate());
		rightWave.setStrokeWeight(3);
		rightWave.setResolution(bufferSize);
		rightWave.setBehavior(new rightUpdate());
		
		/*waveGain.setDecimalPrecision(1);
		waveGain.setValue(1);
		waveGain.setMin(0);
		waveGain.setMax(30);*/
	}
	
	public static float[] getWave(){
		return f;
	}
	
	private class leftUpdate extends ControlBehavior {
		  public leftUpdate() { }
		  public void update() {
			  //arrayCopy
			  for(int i=0;i<bufferSize;i++) {
				    f[i] = in.left.get(i) * waveGain.value();
				  }
			  leftWave.updateData(0,f);	
		  }
		}
	private class rightUpdate extends ControlBehavior {
		  public rightUpdate() { }
		  public void update() {
			  //arrayCopy
			  for(int i=0;i<bufferSize;i++) {
				    f[i] = in.right.get(i) * waveGain.value();
				  }
			  rightWave.updateData(0,f);	
		  }
		}
	
}
