package p4sketch;

import java.util.Iterator;

import p4control.Beats;
import p4control.Volume;
import processing.core.*;

import toxi.physics2d.constraints.*;
import toxi.physics2d.*;

import toxi.geom.*;
import toxi.math.*;

/* 
 * Copyright (c) 2008-2009 Karsten Schmidt
 * 
 * This demo & library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * http://creativecommons.org/licenses/LGPL/2.1/
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

public class CadenaSonora extends Sketch {
	int NUM_PARTICLES = 127;
	int REST_LENGTH=10;

	VerletPhysics2D physicsV, physicsB,physicsU;
	VerletParticle2D headV,tailV;
	VerletParticle2D headB,tailB;
	VerletParticle2D headU,tailU;
	
	boolean isTailLocked;

	public CadenaSonora(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		//VerletPhysics2D(toxi.geom.Vec2D gravity, int numIterations, float drag, float timeStep) 
		
	  physicsV=new VerletPhysics2D();
	  physicsB=new VerletPhysics2D();
	  physicsU=new VerletPhysics2D();
	  
	  Vec2D stepDir=new Vec2D(1,1).normalizeTo(REST_LENGTH);
	  
	  ParticleString2D v=new ParticleString2D(physicsV, new Vec2D(), stepDir, NUM_PARTICLES, 1, 0.1f);
	  ParticleString2D b=new ParticleString2D(physicsB, new Vec2D(), stepDir, NUM_PARTICLES, 2, 0.2f);
	  ParticleString2D u=new ParticleString2D(physicsU, new Vec2D(), stepDir, NUM_PARTICLES, 3, 0.3f);
	  
	  /*public ParticleString2D(VerletPhysics2D physics,
              toxi.geom.Vec2D pos,
              toxi.geom.Vec2D step,
              int num,
              float mass,
              float strength)*/
	  
	  headV=v.getHead();
	  headV.lock();
	  tailV=v.getTail();

	  headB=b.getHead();
	  headB.lock();
	  tailB=b.getTail();

	  headU=u.getHead();
	  headU.lock();
	  tailU=u.getTail();
	  
	}
	

	public void draw() {

	  stroke(100*Beats.kick+155,10,0);	  
	  noFill();
	  
	  //headV.set(p4vj.eye.getCentroidX(),p4vj.eye.getCentroidY());
	  //headV.set(width*Volume.level,height*Volume.level);
	  headV.set(p.mouseX,p.mouseY);
	  
	  physicsV.update();
	  beginShape();
	  int j=0;
	  for(Iterator i=physicsV.particles.iterator(); i.hasNext();) {
	    VerletParticle2D a=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
	    	vertex(a.x,a.y);
	    j++;
	    }
	  }
	  endShape();
	  j=0;
	  for(Iterator i=physicsV.particles.iterator(); i.hasNext();) {
	    VerletParticle2D b=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
		    fill(100*Beats.kick+155,10,0);	  
		    ellipse(b.x,b.y,30*Beats.kick,30*Beats.kick);
		    j++;
	    }
	  }
	  
	  
	  stroke(10,20,100*Beats.hat+155);	  
	  noFill();
	  
	  headB.set(width/2,height/2);
	  physicsB.update();
	  beginShape();
	  j=0;
	  for(Iterator i=physicsB.particles.iterator(); i.hasNext();) {
	    VerletParticle2D a=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
	    vertex(a.x,a.y);
	    j++;
	    }
	  }
	  endShape();
	  j=0;
	  for(Iterator i=physicsB.particles.iterator(); i.hasNext();) {
	    VerletParticle2D b=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
	    fill(10,20,100*Beats.hat+155);
	    ellipse(b.x,b.y,40*Beats.hat,40*Beats.hat);
	    j++;
	    }
	  }
	  
	  stroke(10,100*Beats.hat+155,20);	  
	  noFill();
	  
	  headU.set(width/2,height/2);
	  physicsU.update();
	  beginShape();
	  j=0;
	  for(Iterator i=physicsU.particles.iterator(); i.hasNext();) {
	    VerletParticle2D a=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
	    vertex(a.x,a.y);
	    j++;
	    }
	  }
	  endShape();
	  j=0;
	  for(Iterator i=physicsU.particles.iterator(); i.hasNext();) {
	    VerletParticle2D b=(VerletParticle2D)i.next();
	    if (j<NUM_PARTICLES*Volume.level){
		  fill(10,100*Beats.hat+155,20);	
	    ellipse(b.x,b.y,50*Beats.snare,50*Beats.snare);
	    j++;
	    }
	  }  
	}



}