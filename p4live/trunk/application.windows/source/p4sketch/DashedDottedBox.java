package p4sketch;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.geom.AABB;
import toxi.geom.Line3D;
import toxi.geom.Vec3D;
import toxi.geom.mesh.WETriangleMesh;
import toxi.geom.mesh.WingedEdge;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class DashedDottedBox extends Sketch {
	List<Line3D> edges = new ArrayList<Line3D>();
	// cube radius
	float s=100;
	// step size between points on each edge
	float step=10;
	// marching ants animation phase (will count from 0.0 ... 1.0)
	float phase;
	
	public DashedDottedBox(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
		  // create a cube mesh
		  WETriangleMesh box=new WETriangleMesh();
		  box.addMesh(new AABB(new Vec3D(),100).toMesh());
		  // scan all edges and only pick out the major axes
		  // put them all in our edge/line list for drawing later
		  for(WingedEdge e : box.edges.values()) {
		    if (e.getDirection().isMajorAxis(0.01f)) {
		      edges.add(e);
		    }
		  }
	}
	

	public void draw() {
	  background(0);
	  translate(width/2,height/2,0);
	  //stroke(255);
	  stroke(250,10,0);
	  fill(250,10,0);	  
	  phase=(phase+0.05f)%100;
	  for(Line3D l : edges) {
	      drawDottedLine(l,phase);
	  }
	}



	void drawDottedLine(Line3D l, float phase) {
	  l=new Line3D(l.a.add(l.getDirection().normalizeTo(phase*step)),l.b);
	  // compute inbetween points every "STEP" units and iterate over them
	  for(Vec3D p : l.splitIntoSegments(null,step,true)) {
	    point(p.x,p.y,p.z);
	    //pushMatrix();
	    //translate(p.x,p.y,p.z);
	    //sphere(10);
	    //popMatrix();
	    
	  }
	}

}

