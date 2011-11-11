package p4sketch;

import processing.core.*;
import p4live.OutputWindow;
import p4control.Volume;
import p4live.*;

public class CiudadCubo extends Sketch {
	static int GRIDSIZE;
	int POINTNUM;
	int nCubos;
	boolean center = true;
	int posX,realX;
	int posY,realY;

	public Gridpoint[] g ;
	Gridpoint realcube;
	
	public CiudadCubo(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
		   posX = OutputWindow.getWidth()/2;
		   posY = OutputWindow.getHeight()/2;
		   realX = OutputWindow.getWidth()/2;
		   realY = OutputWindow.getHeight()/2;
		   
			GRIDSIZE = 5;
			POINTNUM = 1000;
			nCubos = 0;
			g = new Gridpoint[POINTNUM];

			rectMode(CENTER);
			realcube = new Gridpoint(0,0,0,0);

			for (int i = 0; i < POINTNUM; i++) {
				int x = (int) p.random((OutputWindow.getWidth()) / GRIDSIZE) * GRIDSIZE;
				int y = (int) p.random((OutputWindow.getHeight()) / GRIDSIZE) * GRIDSIZE;
				//int x = (int)realcube.x;
				//int y = (int)realcube.y;				
				
				int dir = (int) p.random(2);
				float vel = p.random(-3, 3);
				g[i] = new Gridpoint(x, y, dir, vel);
			}
		
	}
	

	   
	public void event(int tipo) {
		switch (tipo){
		case 1:
		case 2:
		case 3:	
			if (nCubos < POINTNUM) {
				g[nCubos].type = tipo;
				nCubos++;
			}
			break;
		case 0:

				setCenter(OutputWindow.getWidth()/2, OutputWindow.getHeight()/2);
				
			center = !center;
			break;
		}
	}

	private void setCenter(int x, int y) {
		posX = x;
		posY = y;
	}

	public void draw() {
		
		realX += (int) ((posX - realX) * .1);
		realY += (int) ((posY - realY) * .1);
		
		translate(realX, realY);
		
		/*rotateX(p4vj.rotX);
		rotateY(p4vj.rotY);
		rotateZ(p4vj.rotZ);*/
		//translate(-p4vj.xCube,-p4vj.yCube,p4vj.zoomZ);
		
		rectMode(CENTER);
		strokeWeight(3);
		strokeCap(PROJECT);
		
		for (int i = 0; i < nCubos; i++) {
			g[i].update();
			g[i].draw();
		}
		//realcube.draw();
	}

	public class Gridpoint {
		int dir;
		float vel;
		float x, y, z;
		int type;

		Gridpoint(int xIn, int yIn, int dirIn, float velIn) {
			x = xIn;
			y = yIn;
			dir = dirIn;
			vel = velIn;
			z = 0;
			type = 0;
		}

		public void update() {
			if (dir == 1) {
				y += vel + Volume.level * 5;
				//x = x + map(noise(x,y),0,1,-OutputWindow.getWidth()/2,OutputWindow.getWidth()/2);
				x = x + vel * p.noise(x);  
				
				if (y < -OutputWindow.getHeight())
					y += OutputWindow.getHeight();
				else if (y > OutputWindow.getHeight())
					y -= OutputWindow.getHeight();
			} else
			{
				x += vel + Volume.level * 5;
				int yNoise = (int) (vel * p.noise(x,y));
				
				y = y + yNoise;

				if (x < -OutputWindow.getWidth())
					x += OutputWindow.getWidth();
				else if (x > OutputWindow.getWidth())
					x -= OutputWindow.getWidth();
			}
			//x = noise(x,y) * OutputWindow.getWidth();
			//y = noise(x,y) * OutputWindow.getHeight();
			
			z =  OutputWindow.getHeight()*Volume.level*vel*50;
			
		}
		public void draw(){
			pushMatrix();
			switch (type){
			case 0:
				fill(255);
				stroke(0);
				translate(x, y, 0);
				//box(p4vj.wCube);
			break;
				/*default:
				case 1:
					fill(0,128);
					stroke(color(255));
					translate(x, y, z);
					//box(p4vj.wCube/3+p4vj.sound.kickLevel*100);
					break;
				case 2:
					fill(128,128*alpha01);
					stroke(color(255,alpha255));
					translate(x, y, z+p4vj.sound.snareLevel*vel*100);
					box(p4vj.wCube/3+p4vj.sound.snareLevel*100);
					break;
				case 3:
					fill(200,128*alpha01);
					stroke(color(0),alpha255);
					translate(x, y, z+p4vj.sound.hatLevel*vel*150);
					box(p4vj.wCube/3+p4vj.sound.hatLevel*100);
					break;*/
			}
			//fill(type);
			//rect(x, y, (vel * 5) + P4VJ.sound.volume * 100, (vel * 5) + P4VJ.sound.volume * 100);

			//translate(x, y);
			//box((vel * 5) + p4vj.sound.volume * 100 );

			popMatrix();
	
		}
	}
}
