package p4sketch;

import p4live.OutputWindow;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class Shvartsman extends Sketch {
	
	Particle particleSystem;
	float numFrames = 5000;
	float lon;
	float lat;
	int energy;
	float direction;
	float pressure;
	float x;
	float y;
	float z;
	float x1;
	float y1;
	float z1;

	//declare data arrays
	float[] dataDEC;  //y
	float[] dataRA;  //x
	int[] dataNRG;
	float[] dataDIR;
	float[] dataPR;
	
	//no para el ultimo
	public Shvartsman(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		  smooth();

		  //strokeWeight(0);
		  //noFill();
		  //stroke(255);
		  strokeWeight(1);

		  particleSystem = new Particle(lon,lat,energy,direction,pressure,x,y,z,x1,y1,z1);

		  //Load text file as a String
		  String[] DEC = p.loadStrings("DEC.txt");
		  String[] RA = p.loadStrings("RA.txt");
		  String[] NRG = p.loadStrings("NRG.txt");
		  String[] DIR = p.loadStrings("DIR.TXT");
		  String[] PR = p.loadStrings("PR.txt");
		  
		  String[] DEC1;
		  String[] RA1;
		  String[] NRG1;
		  String[] DIR1;
		  String[] PR1;

		  //Convert string into an array of integers
		  //using ',' as a delimiter
		  
		  /*	  dataDEC = (float[]) p.split(DEC[0],',');
		  dataRA = (float)p.split(RA[0],',');
		  dataNRG = (int)p.split(NRG[0],',');
		  dataDIR = (float)p.split(DIR[0],',');
		  dataPR = (float)p.split(PR[0],',');*/
		  
		  DEC1 =  p.split(DEC[0],',');
		  RA1 = p.split(RA[0],',');
		  NRG1 = p.split(NRG[0],',');
		  DIR1 = p.split(DIR[0],',');
		  PR1 = p.split(PR[0],',');
		  
		  dataDEC = new float[DEC1.length];
		  dataRA = new float[RA.length];
		  dataNRG = new int[NRG.length];
		  dataDIR = new float[DIR.length];
		  dataPR = new float[PR.length];
		  
		  for (int k=0; k<DEC1.length;k++){
		//	  dataDEC[k] = DEC1[k];
		  }
		  
	}

	public void draw() {  
	  //noLoop();
	  //rotateX(-.5);
	  //rotateY(-.5);
	  background(0);
	  fill(255,0,0);
	  pushMatrix();
	  fill(0,0,255);
	  popMatrix();
	  background(0);
	  particleSystem.drawData();
	  }
	
	class Particle {

		  float lat;
		  float lon;
		  int energy;
		  float direction;
		  float pressure;
		  float x;
		  float y;
		  float z;
		  float x1;
		  float y1;
		  float z1;

		  Particle(float lat_, float lon_, int energy_, float direction_, float pressure_, float x_, float y_, float z_, float x1_, float y1_, float z1_) {
		    lat_ = lat;
		    lon_ = lon;
		    energy_ = energy;
		    direction_ = direction;
		    pressure_ = pressure;
		    x_= x;
		    y_= y;
		    z_= z;
		    x1_= x1;
		    y1_= y1;
		    z1_= z1;
		  }

		  void drawData() {

		    //scale system according to mouse position
		    float di = p.dist(width,height,width/2,height/2);
		    float scaleAmt = p.map(di,0,636f,.1f,2.0f);
		    //scale(scaleAmt,scaleAmt,scaleAmt);

		    //loop through data elements
		    for (int i = 0; i<6594; i+=20) {
		      float lon = dataDEC[i];  //y
		      float lat = dataRA[i];  //x
		      int energy = dataNRG[i];
		      float direction = dataDIR[i];
		      float pressure = dataPR[i];

		      //convert degrees to radians
		      float radLon = p.radians(lon); //y
		      float radLat = p.radians(lat);  //x
		      float radDir = p.radians(direction);
		      //println(radLat);


		      //first remap energy values
		      float zPressure = p.map(pressure,714.0f,764.8f,-204,204);
		      float zEnergy = p.map(energy,13,221,-100,100);

		      //convert to spherical coordinates
		      float p1 = (zEnergy) * p.sin(radLat) * p.cos(radLon);
		      float p2 = (zEnergy) * p.sin(radLat) * p.sin(radLon);
		      float p3 = (zEnergy) * p.cos(radLat);

		      float m1 = (zPressure) * p.cos(radLat) * p.sin(radLon);
		      float m2 = (zPressure) * p.cos(radLat) * p.cos(radLon);
		      float m3 = (zPressure) * p.sin(radLat);

		      float m = p.noise(i * .05f, (float)OutputWindow.frameCount() * .05f) * 20;

		      //create neutrino points based on new spherical data coordinates
		      //      stroke(255,255,255,100); //white
		      //      beginShape(POINTS);
		      //      vertex(p1 + m,p2 + m,p3 + m);
		      //      endShape();


		      //create neutron points based on new spherical data coordinates
		      //      stroke(160,0,0); //red
		      //      beginShape(POINTS);
		      //      vertex(m1+m,m2+m,m3+m);
		      //      endShape();

		      //create proton based on previous two systems
		      float L1 = p1 - m1;
		      float L2 = p2 - m2;
		      float L3 = p3 - m3;

		      //      stroke(0,245,245,50); //cyan
		      //      beginShape(POINTS);
		      //      vertex(L1+m,L2+m,L3+m);
		      //      endShape();

		      //create electron based on previous two systems
		      float n1 = m1 - L1;
		      float n2 = m2 - L2;
		      float n3 = m3 - L3;

		      //      stroke(255,0,255,50); //pink
		      //      beginShape(POINTS);
		      //      vertex(n1+m,n2+m,n3+m);
		      //      endShape();

		      //create muon based on previous two systems
		      float o1 = L1 - n1;
		      float o2 = L2 - n2;
		      float o3 = L3 - n3;

		      //      stroke(160,190,200); //lavender
		      //      beginShape(POINTS);
		      //      vertex(o1+m,o2+m,o3+m);
		      //      endShape();

		      //create lines from neutrino to proton and neutrino to neutron
		      stroke(255,255,255,100);
		      line(p1+m,p2+m,p3+m,L1+m,L2+m,L3+m);
		      line(p1+m,p2+m,p3+m,m1+m,m2+m,m3+m);

		      //create lines from proton to electron and neutron to electron
		      stroke(0,245,245,50);
		      line(L1+m,L2+m,L3+m,n1+m,n2+m,n3+m);
		      stroke(160,0,0,150);
		      line(m1+m,m2+m,m3+m,n1+m,n2+m,n3+m);

		      //create lines from electron to muon
		      stroke(255,0,255,50);
		      line(n1+m,n2+m,n3+m,o1+m,o2+m,o3+m);

		      //move points based on their angular resolution
		      dataDEC[i]=dataDEC[i]+radDir*10;
		      dataRA[i]=dataRA[i]+radDir*10;
		    }
		  }
		}

}