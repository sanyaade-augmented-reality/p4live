package p4blend;

import java.net.URL;
import java.security.CodeSource;

//import com.sun.java.util.jar.pack.Package.File;

import p4control.Mixer;
import p4live.P4live;
import p4live.Interface;
import p4live.OutputWindow;
import p4sketch.Sketch;
import processing.core.PApplet;
import codeanticode.glgraphics.GLTextureFilter;
import codeanticode.glgraphics.GLTexture;

public class LayerBlend {
	private String name;
	public GLTextureFilter filter;

	public LayerBlend(PApplet Parent, String Name, String XmlFile) {
		name = Name;
		String folder = Parent.dataPath("GLFilters/");
		
		//String folder = Parent.sketchPath +"/P4live.app/Contents/Resources/Java/p4blend/";
		//CodeSource src = Sketch.class.getProtectionDomain().getCodeSource();
		//URL jar = src.getLocation();
		//file:/Users/lot/Documents/workspace/p4live/application.macosx/P4live.app/Contents/Resources/Java/P4live.jar
		// /Users/lot/Documents/workspace/p4live/application.macosx/src/p4blend/BlendAdd.xm
		//Parent.println(jar);
		try {
			filter = new GLTextureFilter(Parent, folder+XmlFile);
			//filter = new GLTextureFilter(Parent, jar);
		} catch (Exception e) {
			Parent.println("* Warning: blending filter not found.");
			//e.printStackTrace();
		}
	}

	public void apply(GLTexture in1, GLTexture in2, GLTexture out) {
		filter.apply(
				new GLTexture[] { in1, in2 },out); // all are called the same way
	}
	
	public String name(){
		return name;
	}
}
