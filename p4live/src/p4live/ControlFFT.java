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
	Slider fftLevel;
	
	ControlFFT(){
		groupName="FFT";
		defaultWidth= 400;
		defaultHeight = 140;
		defaultX = 0;
		defaultY = 170;
		fft = new FFT(in.bufferSize(), in.sampleRate());
		fft.linAverages(averages);
		f = new float[128];
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {
		//controlP5.begin(10,10);
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		fChart = (Chart)controlP5.addChart("fft",20,20,300,100);
		fChart.setGroup(group);
		fftLevel = controlP5.addSlider("fftLevel",0,30,1,340,20,10,100);
		//fftLevel = controlP5.addSlider("fftLevel",0,30);
		fftLevel.setGroup(group);
		//controlP5.end();
	}
	
	
	public void setPreferences(){
		//group = (ControlGroup)controlP5.group("group");
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);
		group.setWidth(defaultWidth);
		
		fChart.setStrokeWeight(3);
		fChart.setBehavior(new fftUpdate());
		
		fftLevel.setDecimalPrecision(1);
		fftLevel.setValue(1);
		fftLevel.setMin(0);
		fftLevel.setMax(30);
	}
	
	private class fftUpdate extends ControlBehavior {
		  public fftUpdate() { }
		  public void update() {
			  fft.forward(in.mix);
			  //arrayCopy
			  for(int i=0;i<fft.avgSize();i++) {
				    f[i] = fft.getAvg(i)*fftLevel.value();
				  }
			  fChart.updateData(0,f);	
		  }
		}
}
