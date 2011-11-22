package p4sketch;

import p4control.Beats;
import p4control.Volume;
import processing.core.PApplet;

/** <strong>Figuras de Lissajous</strong>
Based in Sketch from H. Spencer - 2007<br/>
*/

public class Lissajous extends Sketch {
	int w, h;
	float cx, cy; // centrox, centroy
	int px, py; // períodos de onda 'x' e 'y'
	int largoEstela; 
	float x, y, r, rot, theta, rx, ry, lx, ly, inc;
	int verde, verdeOscuro; //colores
	float[][] estela;// puntos de la estela
	boolean dibujaEstela;

	//Falta constructor
	
	public Lissajous(PApplet arg0, int arg1 ,int arg2){
	  super(arg0,arg1,arg2);
	  defineVariables();
	  ellipseMode(CENTER);
	  rectMode(CENTER);
	  strokeCap(ROUND);

	}

	void defineVariables(){
		  w = width/2;//800;
		  h = height/2;//800;
		  //r = float(w)/5;
		  rx = w/5f;
		  ry = w/5f;
		  cx = (float) (1.5*rx);
		  cy = h - cx;
		  theta = 0;
		  px = 1;
		  py = 1;
		  lx = 0;
		  ly = 0;
		  inc = 0.025f;
		  verde = p.color(200);
		  verdeOscuro = p.color(255);
		  largoEstela = 80;
		  dibujaEstela = true;
		  estela = new float[largoEstela][2];
		  for(int i = 0; i < largoEstela; i++){
		  estela[i][0] = r;
		  estela[i][1] = 0;
		  }
		}	
	
	public void draw(){
		colorMode(RGB, 255);
	  theta += inc; // incremento negativo constante del ‡ngulo
	  
	  rx = w / (5 * Volume.level);
	  ry = w / (5 * Volume.level);
	  
	  px = (int) (1 + 10 * Beats.kick);
	  py = (int) (1 + 10 * Beats.hat);
	  inc = 0.01f + (0.025f * Beats.snare);

	  
	  // identidad circular
	  x = p.cos(theta*py)*rx; // x pos del punto
	  y = p.sin(theta*px)*ry; // y pos del punto


	  scale(1.2f);
	//  translate(0, p4vj.heightVisual/3);
	 // translate(p4vj.zoomX,p4vj.zoomY,p4vj.zoomZ);
	  //rotateX(p4vj.rotX);
	  //rotateY(p4vj.rotY);
	  //rotateZ(p4vj.rotZ);
	  	  
	  
	  grilla(cx, cy, rx, ry); // dibuja la grilla
	  grilla(cx, cy-2*ry, rx, ry);
	  grilla(cx + 2*rx, cy, rx, ry);
	  
	  puntoVerde(cx, cy);

	  seno(cx+rx, cy); // dibuja la proyecci—n del seno en y
	  coseno(cx,cy - ry); // dibuja la proyecci—n del coseno en x
	  
	  lissajous(cx, cy);
	  dibujaEjes();
	}

	void grilla(float xpos, float ypos, float rx, float ry){
	  pushMatrix();
	  translate(xpos,ypos);
	  noFill();
	  stroke(verdeOscuro);
	  strokeWeight(3);
	  rect(0,0, 2*rx, 2*ry);
	  line(-1.1f*rx,0f, 1.1f*rx, 0f);
	  line(0, -1.1f*ry, 0, 1.1f*ry);
	  ellipse(0, 0, 2*r, 2*r);//new
	  stroke(verdeOscuro);
	  for (int i = (int) (-ry); i < ry; i += (int)(ry/10)){
	    line(-rx, i, rx, i);
	    line(i, -r, i, r);//new
	  }
	  popMatrix();
	}

	void puntoVerde(float xpos, float ypos){
	  pushMatrix();
	  translate(xpos, ypos);
	  noFill();
	  stroke(verde);
	  line(0,0, x,y);
	  fill(verde);
	  ellipse (x, y, rx/30, rx/30);// dibuja el punto que se mueve

	  // la estela:
	  // primero hay que mover todos los valores del [] a la izquierda
	  for(int i = 1; i < largoEstela; i++){
	    estela[i-1][0] = estela[i][0];
	    estela[i-1][1] = estela[i][1];
	  }
	  //hay que poner un valor nuevo al final del []
	  estela[largoEstela-1][0] = x;
	  estela[largoEstela-1][1] = y;

	  //dibujar la estela:
	  if(dibujaEstela){
	    for(int i = 1; i < largoEstela; i++){
	      stroke(verde, (float)(i)/largoEstela * 255);
	      strokeWeight(6);
	      line(estela[i-1][0],estela[i-1][1],estela[i][0],estela[i][1]);  
	    }
	  }
	  popMatrix();
	  strokeWeight(4);
	}

	void seno(float xpos, float ypos){
	  stroke(verde);
	  pushMatrix();
	  translate(xpos,ypos);
	  stroke(verde);
	  noFill();
	  beginShape();
	  for (float i = 0; i < 2*rx; i++){
	    y = p.sin(theta*px+(i/(ry/p.PI)))*ry;
	    vertex(i , y);
	  }
	  endShape();
	  popMatrix();
	}

	void coseno(float xpos, float ypos){
	  stroke(verde);
	  pushMatrix();
	  translate(xpos,ypos);
	  stroke(verde);
	  noFill();
	  beginShape();
	  for (float i = 0; i < 2*ry; i++){
	    x = p.cos(theta*py+(i/(rx/p.PI)))*rx;
	    vertex(x , i - 2*ry);
	  }
	  endShape();
	  popMatrix();
	}

	void lissajous(float xpos, float ypos){
	  pushMatrix();
	  translate(xpos, ypos);
	  beginShape();
	  for(float i = 0; i < p.TWO_PI; i+=p.TWO_PI/1000){
	    lx = rx * p.cos(py*i);
	    ly = ry * p.sin(px*i);
	    stroke(verde);
	    vertex(lx,ly);
	  }
	  endShape();
	  popMatrix();
	}

	void dibujaEjes(){
	  pushMatrix();
	  translate(cx,cy);
	  line(x,y, rx, y);
	  line(x,y, x, -ry);
	  popMatrix();
	}

  
}
