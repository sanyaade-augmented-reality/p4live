package p4sketch;

import processing.core.*; 
import p4control.Volume;

public class NoiseField extends Sketch {

int NUM_PARTICLES = 1000;
ParticleSystem ps;

public NoiseField(PApplet arg0, int arg1 ,int arg2)
{
	super(arg0,arg1,arg2);
	p = parent;
	ps = new ParticleSystem();
}

public void draw()
{


  ps.update();
  ps.render();
}

class Particle
{
  PVector position, velocity;

  Particle()
  {
    position = new PVector(p.random(width),p.random(height));
    velocity = new PVector(); 
  }
  
  public void update()
  {
    velocity.x = 20 * (p.noise((Volume.level*width)/10+position.y/100) -0.5f);
    velocity.y = 20 * (p.noise((Volume.level*height)/10+position.x/100) -0.5f);
    
    position.add(velocity);
    
    if(position.x<0)position.x+=width;
    if(position.x>width)position.x-=width;
    if(position.y<0)position.y+=height;
    if(position.y>height)position.y-=height;
  }

  public void render()
  {
    p.stroke(255);   
    p.line(position.x,position.y,position.x-velocity.x,position.y-velocity.y);
  }
}

class ParticleSystem
{
  Particle[] particles;
  
  ParticleSystem()
  {
    particles = new Particle[NUM_PARTICLES];
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i]= new Particle();
    }
  }
  
  public void update()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].update();
    }
  }
  
  public void render()
  {
    for(int i = 0; i < NUM_PARTICLES; i++)
    {
      particles[i].render();
    }
  }
}
}
