package p4sketch;

import java.util.ArrayList;

import p4live.EventsMidi;
import p4live.P4Constants;
import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;
import geomerative.*;
import traer.physics.*;


public class Typomorph extends Sketch {
	BubbleText bubbleText;

	float GRAVITY = 0;
	float DRAG = 0.2f;
	float POLYGONIZER_LEN=5;
	float ELLIPSE_RAD = 5;
	float LOW_SPRING=0.1f;
	float HIGH_SPRING=0.2f;
	
	//color[] ELLIPSE_COL = {0xCFF09E, 0xA8DBA8, 0x79BD9A, 0x3B8686, 0x0B486B};
	//color[] ELLIPSE_COL = {0x446B7F, 0x708695, 0x9BA2AC, 0xC7BDC2, 0xF3D8D8};
	//color[] ELLIPSE_COL = {0x84DB9C, 0xC3DE91, 0xE8DC91, 0xF2D3D4, 0xECC2F2};
	int[] ELLIPSE_COL = {0xB8E4C6, 0xC5E5BC, 0xD2E7B0, 0xDFE9A3, 0xECEB98 };
	int BUBBLE_ALPHA=100;
	boolean start = true;

	public Typomorph(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
		  // apply initial alpha values to colors
		  for (int i=0; i< ELLIPSE_COL.length; i++)
		    ELLIPSE_COL[i] |= BUBBLE_ALPHA << 24;

		  // Choice of colors
		  background(0);
		  fill(255,102,0);
		  noStroke();
		  
		  // VERY IMPORTANT: Allways initialize the library in the setup		  
		  RG.init(arg0);
		  RG.setPolygonizer(RG.UNIFORMLENGTH);
		  RG.setPolygonizerLength(POLYGONIZER_LEN);  

		  // Enable smoothing
		  smooth();
		  ellipseMode(CENTER);
		  
		  bubbleText = new BubbleText();
		  bubbleText.setText("!#@Û&0", "Impact Regular.ttf", 200, CENTER);
	}	
	public void noteOn(int channel, int pitch, int velocity){
		if (start){
					
		int op = (int) p.random(0,11);
		switch (op){
		case 0:
			setText("!#@Û%");
			break;
		case 1:
			setText("&0E%?");
			break;
		case 2:
			setText("V#=Û%");
			break;
		case 3:
			setText("#&eVo");
			break;
		case 4:
			setText("À#@Û%");
			break;
		case 5:
			setText("&VÛ%@");
			break;
		case 6:
			setText("V#LÛ%");
			break;
		case 7:
			setText("vU-ÛO");
			break;
		case 8:
			setText("V#ÛLo");
			break;
		case 9:
			setText("!Uxl0");
			break;
		case 10:
			setText("!#@l#");
			break;
		case 11:
			setText("pene");
			break;			
		}
			if (channel == P4Constants.RIFF1){
				setText("Vuelo");
				start = false;
			}
		}
		
	}

	public void parameterChanged(int param){
		if (param == 1){	
			start = true;
			setText("Vuelo");
			//int value = (int) p.map(parameter[param-1],0,1,1,10);
			//setCloth(value);
		}
	}
	public void draw() {
	//  // Set the origin to draw in the middle of the sketch
	  translate(width/2, height/2);
	  background(0);
	  bubbleText.update();
	  bubbleText.display();
	}

	public void setText(String theText) {
	  // receiving text from controller texting
	  try {
		bubbleText.setText(theText, "Impact Regular.ttf", 200, CENTER);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
	}
	
	class BubbleText {
		  BubbleLetter[] bLetter, oldBLetter;
		  RShape grp, oldGrp;
		  ArrayList spreadOutStartParticles, spreadOutTargetParticles;
		  ParticleSystem spreadOutSystem;
		  
		  BubbleText() {
		    spreadOutSystem = new ParticleSystem(GRAVITY, DRAG);  
		    spreadOutStartParticles = new ArrayList();
		    spreadOutTargetParticles = new ArrayList();
		  }
		  
		  BubbleText(String _bText, String _fontFileName, int _fontSize, int _align) {
		    spreadOutSystem = new ParticleSystem(GRAVITY, DRAG);  
		    spreadOutStartParticles = new ArrayList();
		    spreadOutTargetParticles = new ArrayList();
		    setText(_bText, _fontFileName, _fontSize, _align);
		  }
		  
		  void setText(String _bText, String _fontFileName, int _fontSize, int _align) {
		    grp = RG.getText(_bText, _fontFileName, _fontSize, _align);
		    bLetter = new BubbleLetter[grp.children.length];
		    if (oldGrp==null) {
		      for (int i=0; i<grp.children.length; i++) {
		        bLetter[i]= new BubbleLetter(grp.children[i]);       
		        bLetter[i].drawLetter();
		      }
		    } else {
		        ArrayList excessParticles = new ArrayList(oldGrp.getPoints().length);
		        //delete excess particles arrived to the edge
		        if (spreadOutStartParticles.size()!=0) {
		          for(int i=0; i<spreadOutStartParticles.size(); i++) {
		            Particle p = (Particle)spreadOutStartParticles.get(i);
		            Particle tp = (Particle)spreadOutTargetParticles.get(i);            
		            if (PApplet.dist(p.position().x(), p.position().y(), p.position().z(), tp.position().x(), 
		              tp.position().y(), tp.position().z())<2*ELLIPSE_RAD) {
		              spreadOutSystem.removeParticle(p);
		              spreadOutSystem.removeParticle(tp);              
		              spreadOutStartParticles.remove(i);
		              spreadOutTargetParticles.remove(i);              
		            }            
		          }
		        }

		        if (grp.children.length<=oldGrp.children.length) {
		          int offset = (int)((oldGrp.children.length-grp.children.length)/2);
		          for (int i=0; i<grp.children.length; i++) {
		            // create new letters
		            bLetter[i]= new BubbleLetter(grp.children[i], oldBLetter[offset+i]);
		            
		            // fill excess particles pool for new letters
		            Particle[] bLetterExcessParticles = bLetter[i].findExcessParticles();
		            if (bLetterExcessParticles!=null) {
		              for (int p=0; p<bLetterExcessParticles.length; p++)
		                excessParticles.add(bLetterExcessParticles[p]);
		            }
		          }
		          // fill excess particles pool from old redundant letters
		          for (int i=0; i<offset; i++) {
		              for (int p=0; p<oldBLetter[i].startParticles.length; p++)
		                excessParticles.add(oldBLetter[i].startParticles[p]);
		          }
		          for (int i=grp.children.length-1; i>oldGrp.children.length+offset-1; i--) {
		              for (int p=0; p<oldBLetter[i].startParticles.length; p++)
		                excessParticles.add(oldBLetter[i].startParticles[p]);
		          }

		          // take free particles from pool and begin morphing
		          for (int i=0; i<grp.children.length; i++) {
		            bLetter[i].takeFreeParticles(excessParticles);
		            bLetter[i].morph();
		          }
		          spreadOut(excessParticles);
		            
		        } else {
		          int offset = (int)((grp.children.length-oldGrp.children.length)/2);
		          for (int i=0; i<oldGrp.children.length; i++) {
		            bLetter[offset+i]= new BubbleLetter(grp.children[offset+i], oldBLetter[i]);
		            
		            // fill excess particles pool for new letters
		            Particle[] bLetterExcessParticles = bLetter[offset+i].findExcessParticles();
		            if (bLetterExcessParticles!=null) {
		              for (int p=0; p<bLetterExcessParticles.length; p++)
		                excessParticles.add(bLetterExcessParticles[p]);
		            }

		            bLetter[offset+i].takeFreeParticles(excessParticles);                
		            bLetter[offset+i].morph();
		          }
		          for (int i=0; i<offset; i++) {
		            bLetter[i]= new BubbleLetter(grp.children[i]);                 
		            bLetter[i].takeFreeParticles(excessParticles);                
		            bLetter[i].drawLetter();
		          }
		          for (int i=grp.children.length-1; i>oldGrp.children.length+offset-1; i--) {
		            bLetter[i]= new BubbleLetter(grp.children[i]);       
		            bLetter[i].takeFreeParticles(excessParticles);                
		            bLetter[i].drawLetter();
		          }
		          spreadOut(excessParticles);
		        }
		    }
		    oldGrp = grp;
		    oldBLetter = bLetter;

		  }

		  void update() {
		    for (int i=0; i<bLetter.length; i++)
				try {
					bLetter[i].update();
					spreadOutSystem.tick();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		    
		    
		  }
		  
		  void display() {
		    for (int i=0; i<bLetter.length; i++)
				try {
					bLetter[i].display();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		    
		    /*if (spreadOutStartParticles!=null) {
		      for (int i=0; i<spreadOutStartParticles.size(); i++) {
		        Particle spreadOutStartParticle = (Particle) spreadOutStartParticles.get(i);
		       // spreadOutStartParticle.display();  
		       // ellipse(spreadOutStartParticle.position().x(), spreadOutStartParticle.position().y(), 10, 10);  
		      }
		    }*/
		  }
		  
		  void spreadOut(ArrayList excessParticles) {
		    // set spread out free particles here
		    for (int i=0; i<excessParticles.size(); i++) {
		      Particle excessParticle = (Particle) excessParticles.get(i);
		      spreadOutStartParticles.add(spreadOutSystem.makeParticle(1f, excessParticle.position().x(),
		        excessParticle.position().y(), 0));//, excessParticle.getOriginalColor()));   
		        if ((int)p.random(2)>0)
		          spreadOutTargetParticles.add(spreadOutSystem.makeParticle(1f, p.map((int)p.random(2), 0, 1, -width/2-ELLIPSE_RAD, width/2+ELLIPSE_RAD), p.random(-height/2-ELLIPSE_RAD, height/2+ELLIPSE_RAD),0));
		        else
		          spreadOutTargetParticles.add(spreadOutSystem.makeParticle(1f, p.random(-width/2-ELLIPSE_RAD, width/2+ELLIPSE_RAD), p.map((int)p.random(2), 0, 1, -height/2-ELLIPSE_RAD, height/2+ELLIPSE_RAD),0));
		      Particle spreadOutStartParticle = (Particle) spreadOutStartParticles.get(i);            
		     /* if (excessParticle.isShining()) {
		        spreadOutStartParticle.makeShine(map(p.(1), 0, 1, ELLIPSE_RAD*1.5, ELLIPSE_RAD*2.5));
		      }*/
		      Particle spreadOutTargetParticle = (Particle) spreadOutTargetParticles.get(i);
		      spreadOutTargetParticle.makeFixed();
		      spreadOutSystem.makeSpring(spreadOutStartParticle, spreadOutTargetParticle, p.random(LOW_SPRING, HIGH_SPRING), p.random(1), 0);
		    }
		    // re-spring excess particles from past iteration
		    if (excessParticles.size()<spreadOutStartParticles.size()) {
		        for (int i=excessParticles.size(); i<spreadOutStartParticles.size(); i++) {
		          Particle spreadOutStartParticle = (Particle) spreadOutStartParticles.get(i);            
		          Particle spreadOutTargetParticle = (Particle) spreadOutTargetParticles.get(i);
		          spreadOutTargetParticle.makeFixed();
		          spreadOutSystem.makeSpring(spreadOutStartParticle, spreadOutTargetParticle, p.random(LOW_SPRING, HIGH_SPRING), p.random(1), 0);
		      }
		    }
		  }
		}

	class BubbleLetter {
		  RPoint[] points;
		  ParticleSystem letterSystem;
		  Particle[] startParticles;
		  Particle[] targetParticles;
		  BubbleLetter oldBLetter;
		  int freeParticlesTaken=0; 
		  
		  BubbleLetter(RShape _letter) {
		    points = _letter.getPoints();
		    targetParticles = new Particle[points.length];
		    startParticles = new Particle[points.length];
		    oldBLetter=null;
		    letterSystem = new ParticleSystem(GRAVITY, DRAG);       
		  }
		  BubbleLetter(RShape _letter, BubbleLetter _oldBLetter) {
		    points = _letter.getPoints();
		    targetParticles = new Particle[points.length];
		    startParticles = new Particle[points.length];
		    oldBLetter=_oldBLetter;
		    letterSystem = new ParticleSystem(GRAVITY, DRAG);       
		  }  

		  void drawLetter() {
		    //letterSystem = new ParticleSystem(GRAVITY, DRAG);
		    
		    for (int i=0; i<points.length; i++) {
		      targetParticles[i] = letterSystem.makeParticle(1f, points[i].x, points[i].y,0);
		      targetParticles[i].makeFixed();

		      if (i>freeParticlesTaken-1) {
		        if ((int)p.random(2)>0)
		          startParticles[i] = letterSystem.makeParticle(1f, p.map((int)p.random(2), 0, 1, -width/2-ELLIPSE_RAD, width/2+ELLIPSE_RAD), p.random(-height/2, height/2),0);//,  ELLIPSE_COL[(int)p.random(ELLIPSE_COL.length)]);
		        else
		          startParticles[i] = letterSystem.makeParticle(1f, p.random(-width/2, width/2), p.map((int)p.random(2), 0, 1, -height/2-ELLIPSE_RAD, height/2+ELLIPSE_RAD),0);//,  ELLIPSE_COL[(int)p.random(ELLIPSE_COL.length)]);
		      }

		      letterSystem.makeSpring(startParticles[i], targetParticles[i], p.random(LOW_SPRING, HIGH_SPRING), p.random(1), 0);
		    }
		  }
		  
		  Particle[] findExcessParticles() {
		    if (oldBLetter!=null) {
		      if(points.length<oldBLetter.points.length) {
		        Particle[] freeParticles = new Particle[oldBLetter.points.length-points.length];      
		        for (int i=0; i<oldBLetter.points.length-points.length; i++)
		          freeParticles[i] = oldBLetter.startParticles[i+points.length];
		        return freeParticles;
		      }
		    }
		    return null;
		  }
		 
		  boolean takeFreeParticles(ArrayList freeParticles) {
		    //Particle[] freeParticles = (Particle[]) _freeParticles.toArray();
		    if (oldBLetter!=null) {
		      if(points.length>oldBLetter.points.length) {
		        for (int i=0; i<points.length-oldBLetter.points.length; i++) {
		          try {
		            Particle freeParticle = (Particle) freeParticles.get(0);
		            freeParticles.remove(0);
		            startParticles[oldBLetter.points.length+i] = letterSystem.makeParticle(1f, freeParticle.position().x(),
		              freeParticle.position().y(),0);//, freeParticle.getOriginalColor());
		          /*  if (freeParticle.isShining())
		              startParticles[oldBLetter.points.length+i].makeShine(p.map(p.random(1), 0, 1, ELLIPSE_RAD*1.5, ELLIPSE_RAD*2.5)); */  
		          } catch (IndexOutOfBoundsException e) {
		            return false; //gimme more particles!
		          } 
		          freeParticlesTaken++;
		        }
		        return true; //enough      
		      }
		    } else {
		      for (int i=0; i<points.length; i++) {
		        try {
		          Particle freeParticle = (Particle) freeParticles.get(0);
		          freeParticles.remove(0);
		          startParticles[i] = letterSystem.makeParticle(1f, freeParticle.position().x(),
		            freeParticle.position().y(),0);//, freeParticle.getOriginalColor());
		        /*  if (freeParticle.isShining())
		              startParticles[i].makeShine(p.map(p.random(1), 0, 1, ELLIPSE_RAD*1.5, ELLIPSE_RAD*2.5));*/    
		        } catch (IndexOutOfBoundsException e) {
		          return false; //gimme more particles!
		        } 
		        freeParticlesTaken++;
		      }
		      return true; //enough      
		    }
		    return true;
		  }
		  
		  // morph current letter from previous letter
		  void morph() {
		    while(oldBLetter.letterSystem.numberOfSprings()>0)
		      oldBLetter.letterSystem.removeSpring(0);
		      
		    for (int i=0; i<p.min(points.length, oldBLetter.points.length); i++) {
		      targetParticles[i] = letterSystem.makeParticle(1f, points[i].x, points[i].y,0);
		      targetParticles[i].makeFixed();
		      startParticles[i]= letterSystem.makeParticle(1f, oldBLetter.startParticles[i].position().x(), 
		        oldBLetter.startParticles[i].position().y(),0);//,oldBLetter.startParticles[i].getOriginalColor());
		     /* if (oldBLetter.startParticles[i].isShining())
		        startParticles[i].makeShine(p.map(p.random(1), 0, 1, ELLIPSE_RAD*1.5, ELLIPSE_RAD*2.5));   */
		      letterSystem.makeSpring(startParticles[i], targetParticles[i], p.random(LOW_SPRING, HIGH_SPRING), p.random(1), 0);
		    }
		    if(points.length>=oldBLetter.points.length) {
		        for (int i=oldBLetter.points.length; i<points.length; i++) {
		          targetParticles[i] = letterSystem.makeParticle(1f, points[i].x, points[i].y,0);
		          targetParticles[i].makeFixed();
		 
		          if (i>oldBLetter.points.length+freeParticlesTaken-1) {
		            startParticles[i] = letterSystem.makeParticle(1f, p.random(-width/2, width/2), p.map((int)p.random(2), 0, 1, -height/2-ELLIPSE_RAD, height/2+ELLIPSE_RAD),0);//, 
		            //ELLIPSE_COL[(int)p.random(ELLIPSE_COL.length)]);
		          }
		          letterSystem.makeSpring(startParticles[i], targetParticles[i], p.random(LOW_SPRING, HIGH_SPRING), p.random(1), 0);
		        }
		    }   
		  }
		  
		  void update() {   
		    letterSystem.tick();
		  }
		  
		  void display() {
			int targetRadius=10;
			int radius = targetRadius;
			
		    for (int i=0; i<startParticles.length; i++){
				/*for (int k=0;k<EventsMidi.midiState.size();k++){
					if(EventsMidi.midiState.get(k).state){
						int can = EventsMidi.midiState.get(k).channel;				
						int pitch = EventsMidi.midiState.get(k).pitch;
						int vel = EventsMidi.midiState.get(k).velocity;	
					      if (can+pitch == i)
					    	  targetRadius += 50 ;//pitch;
					      else
					    	  targetRadius = 10;//+can;
					}	
				}*/
				fill(ELLIPSE_COL[i%ELLIPSE_COL.length]);
				try {
					radius += (targetRadius-radius) * .30;
					ellipse(startParticles[i].position().x(), startParticles[i].position().y(), radius, radius);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		    }		    
		  }
		}


	
	

}