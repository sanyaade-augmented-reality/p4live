package p4sketch;

import p4control.Beats;
import p4control.BPM;
import p4live.OutputWindow;
import processing.core.PApplet;
import processing.core.PVector;

public class DianaLange extends Sketch {
	SilkCloth silkCloth;

	boolean pause = false;
	public DianaLange(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);	
		 silkCloth = new SilkCloth (0, 0, 0, 1000, 100, 0.017f, 0.016f, 0.008f, 0.0075f, 400);
	}

	public void draw()
	{
	  background (247);  
 
	  lights();
	  directionalLight(50, 50, 50, 0, 1, 0);
	  lightSpecular(200, 200, 200);
	  emissive (50,50,50);
	  specular (12.0f);

	  silkCloth.setWind(OutputWindow.frameCount());
	  translate(width/2,height/2, -1000);
		 
	  silkCloth.display();
	}
	
	public void parameterChanged(int param){
		if (param == 1){			
			int value = (int) p.map(parameter[param-1],0,1,1,10);
			setCloth(value);
		}
	}
	
	public void setCloth(int c){
		switch(c){
		case 1:
		    silkCloth.standartMode = true;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 2:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = true;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 3:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = true;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 4:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = true;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 5:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = true;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 6:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = true;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 7:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = true;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 8:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = true;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = false;
		    break;
		case 9:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = true;
		    silkCloth.altMode9 = false;
		    break;
		case 10:
		    silkCloth.standartMode = false;
		    silkCloth.altMode1 = false;
		    silkCloth.altMode2 = false;
		    silkCloth.altMode3 = false;
		    silkCloth.altMode4 = false;
		    silkCloth.altMode5 = false;
		    silkCloth.altMode6 = false;
		    silkCloth.altMode7 = false;
		    silkCloth.altMode8 = false;
		    silkCloth.altMode9 = true;
		    break;

		  }
	}
	
	
	public class SilkCloth
	{
	  int X, Y, Z, totalSize, resolution, a;
	  float spacing, nx, ny, wx, wy, WX, WY, m;

	  PVector [] [] cloth;

	  boolean standartMode = false, altMode1 = false, altMode2 = false, altMode3 = false, altMode4 = false, altMode5 = false, altMode6 = true, altMode7 = false, altMode8 = false, altMode9 = false;
	  boolean wireFrame = false;

	  int standart, highlight;

	  public SilkCloth(int x_, int y_, int z_, int totalSize_, int resolution_, float nx_, float ny_, float wx_, float wy_, float m_)
	  {
	    X = x_;
	    Y = y_;
	    Z = z_;
	    totalSize = totalSize_;
	    resolution = resolution_;
	    spacing = (float) totalSize / (float) resolution;
	    nx = nx_;
	    ny = ny_;
	    wx = wx_;
	    wy = wy_;
	    WX = wx;
	    WY = wy;
	    m = m_;

	    p.noiseDetail ((int) spacing,nx);

	    a = 255;
	    standart = color (180,a);
	    highlight = color (13, 116, 162,a);

	    setupCloth();
	    createCloth ();
	  }

	  void display()
	  {
	    if (!wireFrame) {
	      noStroke();
	      // fill (124,11,103,255);
	      fill (standart);
	    }

	    else {
	      noLights();
	      noFill();
	      stroke (0,50);
	    }

	    for (int i = 0; i < resolution; i++)
	    { 

	      if (!wireFrame && altMode2) {
	        if (i >= 25 && i < 27) fill (highlight);
	        else fill (standart);
	      }

	      if (!wireFrame && altMode4) {
	        if (i >= 11 && i < 13) fill (highlight);
	        else fill (standart);
	      } 

	      if (!wireFrame && altMode8) {
	        if (i >= 5 && i < 7) fill (highlight);
	        else fill (standart);
	      } 

	      if (!wireFrame && altMode9) {
	        if (i >= resolution - 12 && i < resolution - 10) fill (highlight);
	        else fill (standart);
	      } 

	      for (int j = 0; j < resolution; j++)
	      {

	        if (!wireFrame && altMode1) {
	          if (i >= resolution-10 && j >  resolution-8 && j <  resolution-5) fill (highlight);
	          else fill (standart);
	        }

	        if (!wireFrame && altMode3) {
	          if (i > 80 && j >  resolution-8 && j <  resolution-5) fill (highlight);
	          else fill (standart);
	        }

	        if (!wireFrame && altMode6) {
	          if (j >  resolution-25 && j <  resolution-22) fill (highlight);
	          else fill (standart);
	        }


	        PVector p1 = cloth [i] [j];
	        PVector p2 = cloth [i+1] [j];
	        PVector p3 = cloth [i+1] [j+1];
	        PVector p4 = cloth [i] [j+1];

	        beginShape(TRIANGLE_STRIP);
	        vertex (p1.x,p1.y,p1.z * m);
	        vertex (p4.x,p4.y,p4.z * m);
	        vertex (p2.x,p2.y,p2.z * m);
	        vertex (p3.x,p3.y,p3.z * m);
	        endShape ();
	      }
	    }
	  }

	  void setWind (int frameCounter)
	  {

	    wx += WX;
	    wy += WY;
	    updateCloth ();
	  }

	  void updateCloth ()
	  {
	    float angle = 0.0f;
	    float steps = TWO_PI/totalSize;
	    if (altMode6) steps = TWO_PI/(resolution+1.2f);
	    float x = 0, y = 0, z = 0;
	    float maxD = p.dist (0,0,(resolution+1)/2, (resolution+1)/2);

	    float timeX = wx;
	    for (int i = 0; i < resolution+1; i++)
	    {
	      float timeY = wy;

	      for (int j = 0; j < resolution+1; j++)
	      {
	        float nV = p.noise (timeX, timeY, timeX+timeY); 

	        if(standartMode) {
	          x = X+i*spacing-totalSize/2;
	          y = Y+j*spacing-totalSize/2;
	          z = nV*BPM.beat()*5;
	        }

	        if(altMode1 ) {
	          x = p.cos (angle) * (totalSize*(j+1)/180+1);
	          y = p.sin (angle) * (totalSize*(j+1)/180+1);
	          //z = nV*(i+1)/40;
	          z = nV*(i+1)/(parameter[1]*60);
	        }

	        if(altMode2) {
	          x = p.cos (angle) * (totalSize*(i+1)/180+1);
	          y = p.sin (angle) * (totalSize*(i+1)/180+1);
	          z = nV*(i+1)/(30+BPM.beat()*20);//30
	        }

	        if(altMode3) {
	          x = p.cos (angle) * (totalSize*(j+1)/180+1);
	          y = p.sin (angle) * (totalSize*(i+1)/180+1);
	          z = nV*(j+i+1)/60;///60;
	        }

	        if(altMode4) {
	          x = ( ( p.cos (angle) * (totalSize/2) ) + ( X+i*spacing-totalSize/2 ) ) / 2;
	          y = ( ( p.sin (angle) * (totalSize/2) ) + ( Y+j*spacing-totalSize/2 ) ) / 2;
	          z = nV*2*p.sin (nV+(i+1)/10)*p.cos (nV+(j+1)/10);
	        }

	        if(altMode5) {
	          x = p.cos (angle) * (totalSize*nV);
	          y = p.sin (angle) * (totalSize*nV);
	          z = nV*(j+i)/50;
	        }

	        if(altMode6) {
	          x = p.cos (angle) * (totalSize/10+i)*(nV)*7;
	          y = p.sin (angle) * (totalSize/10+i)*(nV)*7;
	          z =  nV*2;
	        }

	        if(altMode7) {
	          x = p.cos (angle+angle) * (totalSize/2+nV+p.cos(angle)*20);
	          y = p.sin (angle+angle) * (totalSize/2+nV+p.sin(angle)*20);
	          z = nV*2;

	          //x = p.cos (angle+angle) * (totalSize*i+nV+p.cos(angle)*16)/20;
	          //y = p.sin (angle+angle) * (totalSize*i+nV+p.sin(angle*16))/20;

	          //x = p.cos (angle+angle) * (totalSize+i+nV+p.cos(angle)*16)/j;
	          //y = p.sin (angle+angle) * (totalSize+j+nV+p.sin(angle*16))/j;
	        }

	        if(altMode8) {
	          float d = p.dist (i,j, (resolution+1)/2,(resolution+1)/2); 
	          x = X+i*spacing-totalSize/2;
	          y = Y+j*spacing-totalSize/2;
	          z = 1-p.map (d, 0f, maxD, 0f, 1f)+nV;
	        }

	        if(altMode9) {
	          x = X+nV*(i+1)*spacing-totalSize/2+p.pow (p.cos (angle), nV) * totalSize/2;
	          y = Y+nV*(j+1)*spacing-totalSize/2+p.pow (p.sin (angle), nV) * totalSize/2;
	          z = nV*2;
	        }

	        cloth [i] [j] = new PVector (x,y,z);

	        timeY += ny;

	        if(altMode1 || altMode2 || altMode3 || altMode4 || altMode5 || altMode6 || altMode7 || altMode9 ) angle += steps;
	      }
	      timeX += nx;
	    }
	  }

	  void setupCloth()
	  {
	    cloth = new PVector [resolution+1] [resolution+1];
	  }

	  void createCloth ()
	  {
	    float x = 0, y = 0, z = 0;

	    float timeX = wx;
	    for (int i = 0; i < resolution+1; i++)
	    {

	      float timeY = wy;

	      for (int j = 0; j < resolution+1; j++)
	      {
	        float nV = p.noise (timeX, timeY, timeX+timeY); 

	        x = X+i*spacing-totalSize/2;
	        y = Y+j*spacing-totalSize/2;
	        z = nV;

	        cloth [i] [j] = new PVector (x,y,z);

	        timeY += ny;
	      }
	      timeX += nx;
	    }
	  }

	  void updateColor (int a_)
	  {
	    a = a_;
	    standart = color (180,a);
	    highlight = color (13, 116, 162,a);
	  }
	}


}