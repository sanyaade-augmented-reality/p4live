package p4sketch;

import java.util.Iterator;

import p4control.Beats;
import p4live.P4Constants;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import codeanticode.glgraphics.GLGraphicsOffScreen;

import toxi.math.waves.*;
import toxi.geom.*;
import toxi.geom.mesh.*;



public class SfericalArmonics extends Sketch {
	

TriangleMesh mesh = new TriangleMesh();

boolean isWireFrame;
boolean showNormals;
boolean doSave;

Matrix4x4 normalMap = new Matrix4x4().translateSelf(128,128,128).scaleSelf(127);


	public SfericalArmonics(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		  randomizeMesh();
	}
	
	public void draw(){
		  background(0);
		  translate(width / 2, height / 2, 0);
		 /* rotateX(mouseY * 0.01f);
		  rotateY(mouseX * 0.01f);*/
		  lights();
		  shininess(16);
		  directionalLight(255,255,255,0,-1,1);
		  specular(255);
		  //drawAxes(400);
		  if (isWireFrame) {
		    noFill();
		    stroke(255);
		  } 
		  else {
		    fill(255);
		    noStroke();
		  }
		  drawMesh(this, mesh, !isWireFrame, showNormals);
	}
	
	public void event(int e){
		if (e == P4Constants.BEAT)
			randomizeMesh();
	}
	

void drawMesh(PGraphics gfx, TriangleMesh mesh, boolean vertexNormals, boolean showNormals) {
  gfx.beginShape(PConstants.TRIANGLES);
  AABB bounds=mesh.getBoundingBox();
  Vec3D min=bounds.getMin();
  Vec3D max=bounds.getMax();
  if (vertexNormals) {
    for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
      Face f=(Face)i.next();
      Vec3D n = normalMap.applyTo(f.a.normal);
      //gfx.fill(n.x, n.y, n.z);
      gfx.fill(n.x+Beats.kick*100, n.y+Beats.snare*100, n.z+Beats.hat*100);
      gfx.normal(f.a.normal.x, f.a.normal.y, f.a.normal.z);
      gfx.vertex(f.a.x, f.a.y, f.a.z);
      n = normalMap.applyTo(f.b.normal);
      //gfx.fill(n.x, n.y, n.z);
      gfx.fill(n.x+Beats.snare*100, n.y+Beats.hat*100, n.z+Beats.kick*100);
      gfx.normal(f.b.normal.x, f.b.normal.y, f.b.normal.z);
      gfx.vertex(f.b.x, f.b.y, f.b.z);
      n = normalMap.applyTo(f.c.normal);
      //gfx.fill(n.x, n.y, n.z);
      gfx.fill(n.x+Beats.hat*100, n.y+Beats.kick*100, n.z+Beats.snare*100);
      gfx.normal(f.c.normal.x, f.c.normal.y, f.c.normal.z);
      gfx.vertex(f.c.x, f.c.y, f.c.z);
    }
  } 
  else {
    for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
      Face f=(Face)i.next();
      gfx.normal(f.normal.x, f.normal.y, f.normal.z);
      gfx.vertex(f.a.x, f.a.y, f.a.z);
      gfx.vertex(f.b.x, f.b.y, f.b.z);
      gfx.vertex(f.c.x, f.c.y, f.c.z);
    }
  }
  gfx.endShape();
  if (showNormals) {
    if (vertexNormals) {
      for (Iterator i=mesh.vertices.values().iterator(); i.hasNext();) {
        Vertex v=(Vertex)i.next();
        Vec3D w = v.add(v.normal.scale(10));
        Vec3D n = v.normal.scale(127);
        gfx.stroke(n.x + 128, n.y + 128, n.z + 128);
        gfx.line(v.x, v.y, v.z, w.x, w.y, w.z);
      }
    } 
    else {
      for (Iterator i=mesh.faces.iterator(); i.hasNext();) {
        Face f=(Face)i.next();
        Vec3D c = f.a.add(f.b).addSelf(f.c).scaleSelf(1f / 3);
        Vec3D d = c.add(f.normal.scale(20));
        Vec3D n = f.normal.scale(127);
        gfx.stroke(n.x + 128, n.y + 128, n.z + 128);
        gfx.line(c.x, c.y, c.z, d.x, d.y, d.z);
      }
    }
  }
}


void randomizeMesh() {
  float[] m=new float[8];
  for(int i=0; i<8; i++) {
    m[i]=(int)p.random(9);
  }
  SurfaceMeshBuilder b = new SurfaceMeshBuilder(new SphericalHarmonics(m));
  mesh = (TriangleMesh)b.createMesh(null,80, 60);
}
	
}