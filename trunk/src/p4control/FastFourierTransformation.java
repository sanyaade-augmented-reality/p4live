package p4control;

import controlP5.CColor;
import controlP5.Chart;
import controlP5.ChartDataSet;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;
import ddf.minim.analysis.FFT;

public class FastFourierTransformation extends Control{
	public static FFT fft;
	Chart fChart;
	public static float[] f;
	private int averages = 16;
	Slider fftGain;
	
	public FastFourierTransformation(){
		groupName="FFT";
		defaultWidth= 360;
		defaultHeight = 130;
		defaultX = 0;
		defaultY = 150;
		fft = new FFT(in.bufferSize(), in.sampleRate());
		fft.linAverages(averages);
		f = new float[averages];
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {
		//controlP5.begin(10,10);
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		fChart = controlP5.addChart("fft",10,10,300,100);
		fChart.setGroup(group);
		fChart.plugTo(this);
		fftGain = controlP5.addSlider("fftGain",0,30,1,320,10,10,100);
		fftGain.setGroup(group);
		fftGain.plugTo(this);
		//controlP5.end();
	}
	
	
	public void setPreferences(){
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);
		group.setWidth(defaultWidth);
		
		fChart.setStrokeWeight(3);
		fChart.setResolution(averages);
		fChart.setBehavior(new fftUpdate());
		
		fftGain.setDecimalPrecision(1);
		fftGain.setValue(1);
		fftGain.setMin(0);
		fftGain.setMax(30);
	}
	
	private class fftUpdate extends ControlBehavior {
		  public fftUpdate() { }
		  public void update() {
			  fft.forward(in.mix);
			  //arrayCopy
			  for(int i=0;i<fft.avgSize();i++) {
				    f[i] = fft.getAvg(i)*fftGain.value();
				  }
			  fChart.updateData(0,f);	
		  }
		}
}
