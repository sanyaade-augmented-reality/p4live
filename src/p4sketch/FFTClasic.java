package p4sketch;

import p4control.FastFourierTransformation;
import processing.core.PApplet;

// 3d audio spectrum 
// andrŽ sier 071222
// Modified by lot

public class FFTClasic extends Sketch {
  int specsize;
  int strid;
  float ostrid;
  
  //astriday data
  int ax; //> specsize
  int az;
  float astriday[][] ;//= new float[ax][az];
  int astriCol[];
  
  int om;
  int dom;

  public FFTClasic(PApplet arg0, int arg1 ,int arg2) {
	 super(arg0,arg1,arg2);
    
    specsize=0;
    strid=0;
    ostrid=0;
    
    //astriday data
    ax=16; //> specsize
    az=128;
    
    astriday = new float[ax][az];
    astriCol = new int[az];
        
    om=1;
    dom=0;
       
    specsize = FastFourierTransformation.f.length;//fft.specSize();
    strid = (int)((float)specsize / ax);
    ostrid=1.0f/strid;
    p.println(specsize);
  }
  
  public void draw() {
	  background(0);
	  /*p.translate(p4vj.widthVisual/2, p4vj.heightVisual,zoomZ);
	  p.translate(p4vj.zoomX, p4vj.zoomY,p4vj.zoomZ);
	  
		p.rotateX(p4vj.rotX);
		p.rotateY(p4vj.rotY);
		p.rotateZ(p4vj.rotZ);*/		
		
		//float angleX = p.map( p4vj.oscVision.blob.getyCentroid() - p4vj.heightVisual/2, 0, p4vj.heightVisual/2, -60, 60);
		//p.rotateX(p.radians(-angleX));
		
		//float angleY = p.map(p4vj.oscVision.blob.getxCentroid(), 0, p4vj.widthVisual, 0, 180);
		//p.rotateY(p.radians(90+angleY));
		
    float sum=0F;
    int index=0;
  
    if(om>0) {
    //copy to firstline
    for(int i=0; i < specsize;i++) {
      sum+=(FastFourierTransformation.f[i]*50);
      if((i%strid==0) && (i!=0)) {
        astriday[index++][0]= sum * ostrid;
        sum=0;
      }
    }
  
    //fifo ter
      for (int j =az-1; j>0;j--)
        for (int i =0; i<ax;i++)
          astriday[i][j] = astriday[i][j-1]; 
    }
    

    //p.stroke(0,255,10, alpha255);
    
      //draw
  
    /*if(dom==0) {
      for (int j =0; j<az;j++) {
        p.beginShape(p.LINES);
        //p.stroke(astriday)
        for (int i =0; i<ax-1;i++) {       
          p.vertex((i-ax/2)*3, -(astriday[i][j]), j*10);
          p.vertex((i-ax/2)*3, -(astriday[i+1][j]), j*10);
        }
        p.endShape();
      }
    } else {*/
      
    p.strokeWeight(2);//3*p4vj.sound.volume);
    
      for (int j =0; j<az;j++) {
    	//p.stroke(0,128 + astriday[0][j],10, alpha255);
        noFill();
        beginShape(p.LINES);                                            //The real FFT
        for (int i =0; i<ax-1;i++) { 
        	stroke(0,255 * astriday[i][j],10);
        	//p.println(astriday[i][j]);
          vertex((i-ax/2)*100*parameter[0], -(astriday[i][j])*100*parameter[1], j*1000*parameter[2]);
          p.vertex((i-ax/2)*100*parameter[0], -(astriday[i+1][j])*100*parameter[1], j*1000*parameter[2]);
        }
        endShape();
        
        beginShape();                                           //The inverted FFT
        for (int i =0; i<ax-1;i++) {   
        	stroke(0,255 * astriday[i][j],10);
          vertex((i-ax/2)*-100*parameter[0], -(astriday[i][j])*100*parameter[1], j*1000*parameter[2]);
          vertex((i-ax/2)*-100*parameter[0], -(astriday[i+1][j])*100*parameter[1], j*1000*parameter[2]);
        }
        endShape();       
      }
     
    }
  
  }
  

