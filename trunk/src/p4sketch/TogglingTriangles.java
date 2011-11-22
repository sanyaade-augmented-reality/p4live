package p4sketch;

//========================================================
//
//Toggling Triangles
//arthurG, June 2011
//
//Simple triangular grid reacting to the mouse movements.
//Note : OPENGL renderer is faster but may cause problems
//in web browsers.
//
//questions, comments : arthurgraff -at- gmail
//
// Modified by lot for p4live
//========================================================

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;
import processing.opengl.*;
import codeanticode.glgraphics.*;


//  Explanation :
//
//      A (0.0,1.0f)
//      /\
//     /  \
//    /    \
//   /      \
//  /________\ C (cos(PI/6),-sin(PI/6))
// B (-cos(PI/6),-sin(PI/6))


public class TogglingTriangles extends Sketch {

	  final int nX = 57;
	  final int nY = 19;
	  CTriangle []triangles;

	  final int dirUp = 0;
	  final int dirDown = 1;

	  final float radiusCircumCircle = 30.0f;  //  Change the size of the triangles

	  final float cosPI6 = PApplet.sqrt(3.0f)/2.0f;
	  final float sinPI6 = 0.5f;
	  final float widthTris = 2.0f*cosPI6*radiusCircumCircle;
	  final float heightTris = (sinPI6+1.0f)*radiusCircumCircle;
	  final float slopeTris = 2.0f*heightTris/widthTris;
	  int millisNow;

	
	public TogglingTriangles(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
	    //size ((int)getCoordX(nX),(int)getCoordY(nY),P2D);

	    stroke (255);
	    colorMode(HSB, 255);

	    triangles = new CTriangle[nX*nY];

	    int id=0;
	    int direction;
	    for (int j=0; j<nY; j++) {
	      direction = j%2;
	      for (int i=0; i<nX; i++,id++) {
	        triangles[id] = new CTriangle (getCoordX(i),getCoordY(j),direction);
	        direction = 1-direction;
	      }
	    }

	    smooth();
	}



  float getCoordX (int i) {
    return ((float)(i-1))*widthTris/2.0f;
  }

  float getCoordY (int j) {
    return (float)j*heightTris;
  }

  public void noteOn(int channel, int pitch, int velocity){
	  int i = (channel*pitch)%triangles.length;
	  triangles[i].push();
  }
  
  public void draw () {
    millisNow = p.millis();
    
    for (int i=0; i<triangles.length; i++) {

      /*if (triangles[i].isOver (p.mouseX,p.mouseY,2.0f))
        triangles[i].push ();*/

      triangles[i].update();
      triangles[i].draw();
    }
  }




  class CTriangle {

    float x, y;
    int dir;
    float state;
    int birthMillis;
    int birthDuration;

    int idNeighb;

    float hue, sat;

    int col;
    int colUnder;

    CTriangle (float x, float y, int dir) {
      this.x = x;
      this.y = y;
      this.dir = dir;

      this.state = 1.0f;
      this.birthDuration = 0;
      this.birthMillis = 0;
      
      this.col = color(0,0,p.random(220,255));
      this.colUnder = color(p.random (20.0f),p.random (180,220),p.random(40,60));
    }

    void update () {

      if (this.birthDuration<=0 || millisNow<this.birthMillis) {
        this.state = 1.0f;
        return;
      }

      this.state = p.min(1.0f,(float)(millisNow-this.birthMillis)/(float)this.birthDuration);
      this.state = p.sq(this.state);  //slow down at the beginning
    }

    void drawTriangle (float coef) {

      pushMatrix();

      translate (x+widthTris/2.0f,y+radiusCircumCircle,-coef);

      if (dir==dirDown) {
        translate (0.0f,-(heightTris-radiusCircumCircle));
        scale (1.0f,-1.0f);
      }

      scale (radiusCircumCircle,radiusCircumCircle);


      beginShape (TRIANGLES);

      float blend = p.sin (coef*PI/2.0f);

      if (idNeighb==0)  //blend position
        vertex (0.0f,-1.0f*blend+sinPI6*(1.0f-blend));
      else            //normal position
      vertex (0.0f,-1.0f);

      if (idNeighb==1)  //blend position
        vertex (-cosPI6*blend+(cosPI6/2.0f)*(1.0f-blend),sinPI6*blend+((sinPI6-1.0f)/2.0f)*(1.0f-blend));
      else            //normal position
      vertex (-cosPI6,sinPI6);

      if (idNeighb==2)  //blend position
        vertex (cosPI6*blend-(cosPI6/2.0f)*(1.0f-blend),sinPI6*blend+((sinPI6-1.0f)/2.0f)*(1.0f-blend));
      else            //normal position
      vertex (cosPI6,sinPI6);
      
      endShape ();

      popMatrix();
    }

    void draw () {

      //background
      fill (colUnder);
      drawTriangle (1.0f);

      //foreground
      fill (col);
      drawTriangle (this.state);

    }

    void push () {
      birthDuration = (int)p.random (500,900);
      birthMillis = millisNow;
      idNeighb = (int)p.random(3);
    }

    boolean isClose (float posX, float posY, float epsilon) {
      return posX+epsilon>=x && posX-epsilon<=x+widthTris &&
        posY+epsilon>=y && posY-epsilon<=y+heightTris;
    }

    float getDistToBorders (float posX, float posY) {
      float dx = x+widthTris/2.0f-posX;
      float dy = y+heightTris-posY;

      if (posX>x+widthTris/2.0f)  //right
        dx = -dx;

      if (dir==dirDown) {
        dx = widthTris/2.0f-dx;
        dy = heightTris-dy;
      }

      return posY-(y+dx*slopeTris);
    }

    boolean isOver (float posX, float posY, float epsilon) {

      if (!isClose(posX,posY,epsilon))
        return false;

      float dist = getDistToBorders (posX,posY);

      return (dir==dirUp && dist>-epsilon) || (dir==dirDown && dist<epsilon);
    }
  }

}