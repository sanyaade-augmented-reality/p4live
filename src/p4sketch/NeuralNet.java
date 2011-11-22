package p4sketch;

import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class NeuralNet extends Sketch {
	public NeuralNet(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
	    n = new Neuron[500];
	    
	    for(int i = 0;i<n.length;i++)
	        n[i] = new Neuron(i,p.random(width),p.random(height));
	 
	 
	    for(int i = 0;i<n.length;i++)
	        n[i].makeSynapse();
	 
	  //  rectMode(CENTER);
	 
	        for(int i = 0;i<n[0].s.length;i++)
	    n[0].makeSignal(i);
	    //n[1].makeSignal(0);
	    //n[2].makeSignal(0);
	 
	    background(0);
	}
	

Neuron n[];
Signal s[];
 
 
 
////////////////////////////////////////////////////////
/*public void event(int e){
	switch(e){
	case P4Constants.BOMBO:
		resetNetwork();
		break;
	case P4Constants.CAJA:
		int k = (int) p.random(0f,n.length);
	    n[k].changePos();
	    p.println("changed:"+k);
	    break;
	}	
}*/

public void noteOn(int channel, int pitch, int velocity){
	switch(channel){
	case P4Constants.BOMBO:
		//if (parameter[0]>0.5)
		resetNetwork();
		break;
	default:
		int init = (int) p.random(0f,n.length-10);
		for (int k = init;k<init+30;k++ )
			n[k].changePos(channel*100);
	    break;
	}
}

 
public void resetNetwork(){
	  // background(0);
	for(int i = 0;i<n.length;i++){
	      n[i].x = p.random(width);
	              n[i].y = p.random(height);
	}
 
  for(int i = 0;i<n.length;i++)
      n[i].makeSynapse();

      for(int i = 0;i<n[0].s.length;i++)
  n[0].makeSignal(i);
 
}

////////////////////////////////////////////////////////
public void draw(){
  //  background(0,10);
    pushStyle();
    //fill(11,1);
    fill(0,80);
    
   /* stroke(0,190);
    strokeWeight(20);*/
    pushMatrix();
    //translate(0,0,-10); 
    noStroke();
    rect(0,0,OutputWindow.getWidth(),OutputWindow.getHeight());//queda mejor sin
    popMatrix();
    popStyle();
 
    pushMatrix();
 
    scale(1);// ?
    for(int i = 0;i<n.length;i++)
        n[i].draw();
 
    popMatrix();
}
 
 
/////////////////////////////////////////////////////////
class  Neuron{
    int id;
    float x,y,val,xx,yy;
    float radius = 60.0f;
 
    Synapse s[];
    Signal sig[];
 
    Neuron(int _id,float _x,float _y){
        val = p.random(255);
        id=_id;
        xx = x=_x;
        yy = y=_y;
    }
 
    void makeSynapse(){
        s = new Synapse[0];
        sig = new Signal[0];
 
        for(int i = 0;i<n.length;i++){
            if(i!=id && p.dist(x,y,n[i].x,n[i].y)<=radius&&p.noise(i/100f)<0.8f){
                s = (Synapse[])p.expand(s,s.length+1);
                s[s.length-1] = new Synapse(id,i);
 
                sig = (Signal[])p.expand(sig,sig.length+1);
                sig[sig.length-1] = new Signal(s[s.length-1]);
 
            }
        }
    }
 
    void makeSignal(int which){
        int i = which;
        sig[i].x = xx;
        sig[i].y = yy;
        sig[i].running = true;
    }
 
    void drawSynapse(){
    	
    	//draw synapse
    	
        if(sig.length>0){
            for(int i = 0;i<sig.length;i+=1){
                if(sig[i].running){
                    pushStyle();
                    strokeWeight(5);
                    stroke(255,100);
                    //stroke(255,90);
                    
                    noFill();
                    line(sig[i].x,sig[i].y,sig[i].lx,sig[i].ly);
                    popStyle();
                    sig[i].step();//peta
                }
            }
        }
 
        //draw network
 
        //stroke(#ffcc11,3);
        int col1 = color(255,204,17);
        //stroke(p.lerpColor(#ffcc11,#ffffff,p.norm(val,0,255)),4);
        strokeWeight(3);
        stroke(p.lerpColor(col1,255,p.norm(val,0,255)),100);
        
        for(int i = 0;i<s.length;i+=1){
            line(n[s[i].B].xx,n[s[i].B].yy,xx,yy);
        }
    }
 
    void draw(){
        drawSynapse();
        xx += (x-xx) / 10.0;
        yy += (y-yy) / 10.0;
        //move();
    }
 
    void move(){
        x+=(p.noise(id+OutputWindow.frameCount()/8f)-.5);
        y+=(p.noise(id*5+OutputWindow.frameCount()/8f)-.5);
    }
 
    void changePos(int max){
    	x+=max*p.random(-1,1);
    	y+=max*p.random(-1,1);
    }
}
 
 
///////////////////////////////////////////////////////
class Synapse{
 
    float weight = 1.5f;
    int A,B;
 
    Synapse(int _A, int _B){
 
        A=_A;
        B=_B;
 
        weight = p.random(101f,1100f)/300.9f;
    }
 
}
 
//////////////////////////////////////////////////////////
class Signal{
 
    Synapse base;
    int cyc = 0;
    float x,y,lx,ly;
    float speed = 10.1f;
 
    boolean running = false;
    boolean visible = true;
 
    int deadnum = 200;
    int deadcount = 0;
 
    Signal(Synapse _base){
        deadnum = (int)p.random(2,400);
        base = _base;
        lx = x = n[base.A].x;
        ly = y = n[base.A].y;
        speed *= base.weight;
    }
 
    void step(){
        running = true;
 
        lx = x;
        ly = y;
 
        x += (n[base.B].xx-x) / speed;//(speed+(dist(n[base.A].x,n[base.A].y,n[base.B].x,n[base.B].y)+1)/100.0);
        y += (n[base.B].yy-y) / speed;//(speed+(dist(n[base.A].x,n[base.A].y,n[base.B].x,n[base.B].y)+1)/100.0);
 
 
 
        if(p.dist(x,y,n[base.B].x,n[base.B].y)<1f){
 
            if(deadcount<0){
                deadcount = deadnum;
 
                for(int i = 0;i<10;i++){
                    fill(255,10);
                    ellipse(x,y,1.5f*i,1.5f*i);
                }
 
                //deadnum += (int)p.random(-1,1);
                //println("run "+base.A+" : "+base.B);
 
                running = false;
                for(int i = 0; i < n[base.B].s.length;i++){
                    if(!n[base.B].sig[i].running && base.A!=n[base.B].sig[i].base.B){
                        n[base.B].makeSignal(i);
                        n[base.B].sig[i].base.weight += (base.weight-n[base.B].sig[i].base.weight)/((p.dist(x,y,n[base.A].xx,n[base.A].yy)+1.0)/200.0);
                    }
 
                }
 
 
                //base.weight = p.random(1001,3000) / 1000.0;
 
                n[base.A].xx+=((n[base.B].x-n[base.A].x)/1.1)*p.noise((OutputWindow.frameCount()+n[base.A].id)/11f);;
                n[base.A].yy+=((n[base.B].y-n[base.A].y)/1.1)*p.noise((OutputWindow.frameCount()+n[base.A].id)/10f);
 
 
 
                n[base.A].xx-=((n[base.B].x-n[base.A].x)/1.1)*p.noise((OutputWindow.frameCount()+n[base.B].id)/10f);;
                n[base.A].yy-=((n[base.B].y-n[base.A].y)/1.1)*p.noise((OutputWindow.frameCount()+n[base.B].id)/11f);
 
                lx = n[base.A].xx;
                ly = n[base.A].yy;
 
                n[base.A].val+=(n[base.B].val-n[base.A].val)/5.0;
            }else{
 
                deadcount--;
            }
        }
    }
}
}