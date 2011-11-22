package p4sketch;

import p4live.OutputWindow;
import processing.core.PApplet;
import processing.core.PImage;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Mesh5 extends Sketch {
	PImage shadow;
	PImage cast;
	Mesh[] meshes;
	boolean[] active;
	
	public Mesh5(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
		  shadow = p.loadImage("shadow.bmp");
		  cast = p.loadImage("cast.png");
		  textureMode(NORMALIZED);
		  meshes = new Mesh[15];
		  active = new boolean[15];
		  for(int i=0;i<meshes.length;i++)
		  {
		    meshes[i] = new Mesh(0,0,0);
		    active[i] = false;
		  }
	}
	
	public void noteOn(int channel, int pitch, int velocity){
		active[channel] = true;
	}
	
	public void noteOff(int channel, int pitch, int velocity){
		active[channel] = false;
	}
	
	
public void draw()
{
  background(0);
  lights();

  pushMatrix();
  translate(width/2,height/2);
  //rotateY(OutputWindow.frameCount()*0.01f);
  //rotateX(-0.5);

  fill(247);
  tint(247,100);
  stroke(247,80);
  for(int i=0;i<meshes.length;i++)
  {
	if (active[i]){  
	    pushMatrix();
	    rotateY(i%3 * (float)Math.PI/5);
	    rotateX(p.floor(i/3)*1);
	    translate(0,-30,0);
	    meshes[i].update(i);
	    popMatrix();
	}
  }
  popMatrix();
}

void vertex(Vertex input)
{
  vertex(input.x,input.y,input.z);
}

void vertex(Vertex input,float _2,float _3)
{
  vertex(input.x,input.y,input.z,_2,_3);
}

class Layer
{
  Vertex[] vertice;
  Vertex center;
  float radius;
  
  Layer(int input)
  {
    vertice = new Vertex[input];
    center = null;
  }
  
  void compute(Vertex _center, float r, float rx, float ry, float rz)
  {
    center = _center;
    radius = r;
    
    float seg = (float)Math.PI*2 / vertice.length;
    for(int i=0;i<vertice.length;i++)
    {
      float _x = r * p.sin(ry + seg*i) + r * p.cos(rz*i);
      float _z = r * p.cos(ry + seg*i) +r * p.cos(rx*i);
      float _y = r * p.sin(rx*i) + r * p.sin(rz*i);
      vertice[i] = new Vertex((_x + center.x),(_y+center.y),(_z + center.z)); 
    }
  }
  
  void render()
  {
    beginShape(TRIANGLE_FAN);
    for(int i=0;i<vertice.length;i++)
    {
      vertex(vertice[i].x,vertice[i].y,vertice[i].z);
    }
    endShape();
  }
}

class Mesh
{
  Layer[] layers;
  Vertex center;
  float radius;
  float spacingY;
  float spacingX;
  float h;
  
  int numLayers;
  int numVertice;
  float decay;
  float rx,ry,rz;
  float aaa = 0.1f;
  float px,py;

  float seedx,seedy,seedz;

  int startCount;
  float layerNum;

  Mesh(float _x, float _y, float _z)
  {
    startCount = (int)p.random(0,200);
    layerNum = 0.5f;
    center = new Vertex(_x,_y,_z);
    decay = p.random(0.94f,0.99f);
    radius = p.random(5,10);
    numVertice = (int)p.random(3,5);
    numLayers = (int)p.random(30,50);
    rx = p.random(-0.1f,0.1f);
    ry = p.random(-1,1);
    rz = 0;
    spacingX = ry/numVertice;
    h = p.random(5,9);
    spacingY = h * layerNum;

    seedx = p.random(100);
    seedy = p.random(100);
    seedz = p.random(100);

    layers = new Layer[numLayers];

    float temRad = radius;

    px = p.random(-aaa,aaa);
    py = p.random(-aaa,aaa);
    for(int i=0;i<layers.length;i++)
    {
      layers[i] = new Layer(numVertice);
      Vertex tempV = new Vertex(center.x+(-i)*spacingY+p.cos(px)*spacingY*i,center.y+(-i)*spacingY+p.sin(px)*spacingY*i+p.sin(py)*spacingY*i,center.z+(-i)*spacingY+p.cos(py)*spacingY*i);

      layers[i].compute(tempV,temRad,rx,spacingX* i,rz);
      temRad *=decay;
    }
  }

  Mesh(float _x, float _y, float _z, float _rx ,float _ry,float _rz,int _layers,int _n, float _r,float _h,float _decay)
  {
    startCount = (int)p.random(0,200);
    layerNum = 0;
    decay = _decay;
    radius = _r;
    spacingY = _h;
    spacingX = ry/_n;
    numLayers = _layers;
    numVertice = _n;
    rx = _rx;
    ry = _ry;
    rz = _rz;

    seedx = p.random(100);
    seedy = p.random(100);
    seedz = p.random(100);

    center = new Vertex(_x,_y,_z);
    layers = new Layer[numLayers];

    float temRad = radius;
    px = p.random(-aaa,aaa);
    py = p.random(-aaa,aaa);
    for(int i=0;i<layers.length;i++)
    {
      layers[i] = new Layer(numVertice);
      Vertex tempV = new Vertex(center.x+(-i)*spacingY+p.cos(px*i)*spacingY*i,center.y+(-i)*spacingY+p.sin(px*i)*spacingY*i+p.sin(py*i)*spacingY*i,center.z+(-i)*spacingY+p.cos(py*i)*spacingY*i);

      layers[i].compute(tempV,temRad,rx,spacingX* i,rz);
      temRad *=decay;
    }
  }

  void update(int id)
  {
    if(startCount>0)
      startCount--;
    else
    {
      if(layerNum<1)  
        layerNum+=0.01;

      seedx += 0.01;
      seedy += 0.01;
      seedz += 0.01;

      rx=(p.noise(seedx)-0.5f)*20;
      ry=(p.noise(seedy)-0.5f)*20;

      spacingX = ry/numVertice;
      spacingY =  h* layerNum;

      float temRad = radius;
      float aaa = 0.1f;
      px = (p.noise(seedx)-0.5f)*0.2f;
      px = 0;
      py = (p.noise(seedy)-0.5f)*0.2f;

      for(int i=0;i<layers.length;i++)
      {
        layers[i] = new Layer(numVertice);
        Vertex tempV = new Vertex(center.x+(-i)*spacingY+p.cos(px*i)*spacingY*i,center.y+(-i)*spacingY+p.sin(px*i)*spacingY*i+p.sin(py*i)*spacingY*i,center.z+(-i)*spacingY+p.cos(py*i)*spacingY*i);

        layers[i].compute(tempV,temRad,rx,spacingX* i,rz);
        temRad *=decay;
      }

      render();
    }
  }

  void render()
  {
    pushMatrix();
    translate(center.x,center.y,center.z);
    rotateX((float)Math.PI/2);
    popMatrix();
    layers[0].render();
    layers[numLayers-1].render();
    for(int i=0;i<numLayers-1;i++)
    {
      for(int j=0;j<numVertice;j++)
      {
        beginShape();
        texture(shadow);
        vertex(layers[i+1].vertice[j],1,0);
        vertex(layers[i].vertice[(j+1)%numVertice],1,1);
        vertex(layers[i+1].vertice[(j+1)%numVertice],0,0);
        endShape();

        beginShape();
        texture(shadow);
        vertex(layers[i].vertice[(j+2)%numVertice],1,0);
        vertex(layers[i].vertice[(j+1)%numVertice],1,1);
        vertex(layers[i+1].vertice[(j+1)%numVertice],0,0);
        endShape();
      }
    }
  }

}

class Vertex
{
  float x,y,z;

  Vertex(float _x,float _y, float _z)
  {
    x = _x;
    y = _y;
    z = _z;
  }
  
  float direction(Vertex input)
  {
    return p.atan2(x-input.x,z-input.z);
  }
}

}