package p4control;

import java.util.ArrayList;

import codeanticode.glgraphics.GLTexture;

import p4blend.LayerBlend;
import p4blend.ScalarParam;
import p4live.OutputWindow;

import controlP5.ControlBehavior;
import controlP5.DropdownList;
import controlP5.Slider;
import controlP5.Toggle;
import themidibus.MidiBus;


public class Mixer extends Control{
	private DropdownList blendSelector;
	private Slider mixFactor;
	private static ArrayList<LayerBlend> BlendModes; // will be an arraylist of LayerBlends
	private static LayerBlend current;
	private ScalarParam opacity;
	public static GLTexture layerOutput;
	
	public Mixer(){
		groupName = "Mixer";
		BlendModes = new ArrayList<LayerBlend>();
		opacity = new ScalarParam(1f,0.0f,0.5f,0.01f);
		defaultX = 200;
		defaultY = 460;
		defaultWidth = 200;
		loadFilters();
		buildInterface();
		setPreferences();
		current = BlendModes.get(0);
		layerOutput = new GLTexture(p,OutputWindow.getWidth(),OutputWindow.getHeight());
		current.filter.setParameterValue("Opacity",opacity.value);
		current.apply();
	}	
	
	private void loadFilters(){
		p.println("* Loading blending filters...");
		BlendModes.add(new LayerBlend(p,"Linear Dodge (Add)","BlendAdd.xml"));
		  BlendModes.add(new LayerBlend(p,"Color","BlendColor.xml"));
		  BlendModes.add(new LayerBlend(p,"Luminance","BlendLuminance.xml"));
		  /*BlendModes.add(new LayerBlend(p,"Multiply","BlendMultiply.xml"));
		  BlendModes.add(new LayerBlend(p,"Subtract","BlendSubtract.xml"));	  
		  BlendModes.add(new LayerBlend(p,"ColorDodge","BlendColorDodge.xml"));
		  BlendModes.add(new LayerBlend(p,"ColorBurn","BlendColorBurn.xml"));
		  BlendModes.add(new LayerBlend(p,"Darken","BlendDarken.xml"));
		  BlendModes.add(new LayerBlend(p,"Lighten","BlendLighten.xml"));
		  BlendModes.add(new LayerBlend(p,"Difference","BlendDifference.xml"));
		  BlendModes.add(new LayerBlend(p,"InverseDifference","BlendInverseDifference.xml"));
		  BlendModes.add(new LayerBlend(p,"Exclusion","BlendExclusion.xml"));
		  BlendModes.add(new LayerBlend(p,"Overlay","BlendOverlay.xml"));
		  BlendModes.add(new LayerBlend(p,"Screen","BlendScreen.xml"));
		  BlendModes.add(new LayerBlend(p,"HardLight","BlendHardLight.xml"));
		  BlendModes.add(new LayerBlend(p,"SoftLight","BlendSoftLight.xml"));
		  BlendModes.add(new LayerBlend(p,"Normal (Unpremultiplied, Photo Mask)","BlendUnmultiplied.xml"));
		  BlendModes.add(new LayerBlend(p,"Normal (Premultiplied, CG Alpha)","BlendPremultiplied.xml"));*/

		  if (BlendModes.size()==0)
			  p.println("[Warning] No blending filters loaded");
		  else
			  p.println("Loaded "+BlendModes.size()+" blending filters.");
	}
	
	private void buildInterface() {		
		group = controlP5.addGroup(groupName, defaultX, defaultY, defaultWidth);
		//controlP5.addButton("Map",0, 10,70,30,20).setGroup(group);
		mixFactor = controlP5.addSlider("mixFactor",0,1,1,165,10,10,76);
		mixFactor.setGroup(group);
		mixFactor.plugTo(this);
		//controlP5.addSlider(theName, theMin, theMax, theDefaultValue, theX, theY, theW, theH)

		blendSelector = controlP5.addDropdownList("Blend_Mix",20,20,120,70);
		blendSelector.setGroup(group);
		blendSelector.setId(1);

		  for(int i=0;i<BlendModes.size();i++) {
			    blendSelector.addItem(BlendModes.get(i).name(), i);
			  }
	}
	
	public static void setCurrentFilter(int i){
		current = BlendModes.get(i);
	}
	public void setPreferences(){	
		group.setBackgroundColor(p.color(255, 100));
		group.setBackgroundHeight(defaultHeight);	
		group.setWidth(defaultWidth);	
	}
	
	public void mixFactor(float v){
		opacity.SetValue(v);
		current.filter.setParameterValue("Opacity",opacity.value);
	}
	
	public static void update(){
		  current.apply(); 
	}
	
	public static void resetTexture(){
		layerOutput = null;
		layerOutput = new GLTexture(p,OutputWindow.getWidth(),OutputWindow.getHeight());
	}
}
