package p4control;

import controlP5.CColor;
import controlP5.Chart;
import controlP5.ChartDataSet;
import controlP5.ControlBehavior;
import controlP5.ControlGroup;
import controlP5.Slider;
import controlP5.Textarea;
import ddf.minim.analysis.FFT;

public class Terminal extends Control{
	private static Textarea terminal;
	
	public Terminal(){
		groupName="Terminal";
		defaultWidth= 360;
		defaultHeight = 100;
		defaultX = 0;
		defaultY = 540;
		buildInterface();
		setPreferences();
	}	
	
	private void buildInterface() {
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		terminal = controlP5.addTextarea("terminal", "", 10, 10, 330, 80);
		terminal.setGroup(group);
		terminal.setColorBackground(p.color(4, 40, 67));
		terminal.setText("");
	}
	
	public void setPreferences(){
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);
		group.setWidth(defaultWidth);
	}
	
	public static void addText(String text){
		terminal.setText(terminal.getText()+"\n"+text);	
	}
	
}
