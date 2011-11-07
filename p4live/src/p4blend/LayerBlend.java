package p4blend;

import p4control.Mixer;
import p4live.P4live;
import p4live.Interface;
import p4live.OutputWindow;
import processing.core.PApplet;
import codeanticode.glgraphics.GLTextureFilter;
import codeanticode.glgraphics.GLTexture;

public class LayerBlend {
	private String name;
	public GLTextureFilter filter;

	public LayerBlend(PApplet Parent, String Name, String XmlFile) {
		name = Name;
		String folder = p4live.P4live.p4Folder+"/src/p4blend/";
		filter = new GLTextureFilter(Parent, folder+XmlFile);
	}

	public void apply() {
		filter.apply(
				new GLTexture[] { Interface.textureSketch(1), Interface.textureSketch(2) },
				Mixer.layerOutput); // all are called the same way
	}
	
	public String name(){
		return name;
	}
}
