package p4live;

//import p4live.ControlVolume.SoundUpdate;
import controlP5.CColor;
import controlP5.Chart;
import controlP5.ChartDataSet;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;
import ddf.minim.analysis.FFT;

public class ControlFFT extends Control{
	private FFT fft;
	Chart fChart;
	private float[] f;
	private int averages = 128;
	//public int FFTLevel = 1;
	
	ControlFFT(){
		groupName="FFT";
		defaultWidth= 400;
		defaultHeight = 140;
		defaultX=200;
		defaultY=100;
		fft = new FFT(in.bufferSize(), in.sampleRate());
		fft.linAverages(averages);
		f = new float[128];
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		//controlP5.addChart("hello",20,20,300,100);//.setGroup(group);
		//fChart.setGroup(group);
		controlP5.addSlider("fftLevel",0,200,128,20,100,10,100).setGroup(group);
		//fftChart = (Chart)controlP5.controller("fftChart");
	}
	
	
	public void setPreferences(){
		//group = (ControlGroup)controlP5.group("group");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);
		group.setWidth(defaultWidth);
		
		//Chart fftChart = (Chart)controlP5.controller("fftChart");
		//fChart.setStrokeWeight(3);
		//fChart.setBehavior(new fftUpdate());
		
		//Slider fftLevel = (Slider)controlP5.controller("FFTLevel");
		//if (fftLevel == null) p.println("NULL");
		/*fftLevel.setDecimalPrecision(1);
		fftLevel.setMin(1);
		fftLevel.setMax(5);*/
	}
	
	/*private class fftUpdate extends ControlBehavior {
		  public fftUpdate() { }
		  public void update() {
			  //Chart fftChart = (Chart)controlP5.controller("fftChart");
			  fft.forward(in.mix);
			  //arrayCopy
			  for(int i=0;i<fft.avgSize();i++) {
				    f[i] = fft.getAvg(i);//*FFTLevel;
				  }
			  fChart.updateData(0,f);	
			  //p.println(FFTLevel);
		  }
		}*/
}
