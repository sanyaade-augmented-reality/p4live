package p4sketch;

/** <! -- (c) subpixel 2009 - http://subpixels.com -->
*
* <h1>SpaceBoxes_by_spxl</h1>
* <h2 class="byline">by <a href="http://subpixels.com">subpixel</a></h2>
* <p>Version 1.2.1, 2009-01-24</p>
*
* <p><em>Original idea and code ased on Falling Box by takumi</em></p>
* <p>Flying through an asteroid field, kind of.</p>
* <p>Autopilot (no controls!)</p>
* <h2>Keys:</h2>
* <ul>
*  <li>[space] toggle paused</li>
*  <li>[a] toggle auto mode (random background changes)</li>
*  <li>[b] toggle background on/off, [n] next background image</li>
*  <li>[e] effect shot (2 seconds) on random channel (not master)</li>
*  <li>[E] effect toggle on random channel (not master)</li>
*  <li>[r] toggle regular/random block sizes</li>
*  <li>[s] toggle scene spin, [S] reset spin rate to zero</li>
*  <li>[t] toggle objects tumble</li>
*  <li>[w] toggle scene spin wobble</li>
*  <li>[LEFT] [RIGHT] [UP] [DOWN] change scene orientation (texture warps strangely with the P3D renderer, try OPENGL</li>
*  <li>[PGUP] [PGDOWN] adjust scene spin rate clockwise/anticlockwise</li>
*  <li>[HOME] [END] reset scene orientation</li>
*  <li>[,] [.] [/] halve, double, toggle speed adjustment</li>
*  <li>[<] [>] [/] slow down, speed up, reset speed adjustment</li>
*  <li>[1]..[8] random action on numbered channel ([`] for master)</li>
*  <li>SHIFT + [1]..[8] disable display of numbered channel ([~] for master)</li>
* </ul>
*/


import java.util.ArrayList;

import processing.core.PApplet;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class SpaceBoxes extends Sketch {
	 float time;  //TODO: create beat-synchronised clock
	  
	  // Rotational orientation
	  float targetThetaX;
	  float targetThetaY;
	  float targetThetaZ;
	  
	  float thetaX;
	  float thetaY;
	  float thetaZ;

	  // Angular veloocity
	  float omegaX;
	  float omegaY;
	  float omegaZ;

	  // The effects available for use in the scene
	  EffectInfo[] effectsInfo;      
	  int      numEffects; // The number of efefcts registered
	  
	  Channel[] channels;  // Independent control channels
	  Channel master;      // The master control channel (mapped to channels[0] for now)	  
	  //Flier[] fliers;      // The flying objects
	  ArrayList<Flier> fliers = new ArrayList<Flier>();
	    
	  // User controlled parameters  
	  boolean ctrlPaused     = false;  // Want to be able to toggle paused/unpaused
	  boolean ctrlAuto       = true;   // Auto mode?
	  boolean ctrlBackground = true;   // Show background?
	  boolean ctrlRegular    = false;  // Regular size/spacing or random?
	  boolean ctrlSpin       = true;   // Use base spin rate?
	  boolean ctrlSpinNoise  = true;   // Apply noise to the spin?
	  boolean ctrlTumble     = true;   // Objects tumble as they fly?
	  
	  // Time warping: eg 1 = not warped. 0 = stopped. -1 = reversed. 2 = double speed.
	  float timeWarp = 2.0f;	  
	  int autoCountdown = 0;  // Frames to go until next random action (if auto==true)

	  float   targetSpinRate;      // Desired spinRate (ease to this value)
	  float   spinRate;            // Base angular velocity for spin
	  boolean spinAdjust;          // Adjust the scene spin?
	  float   spinAdjustment;      // Factor to adjust the spin (rate?) by
	  
	  float   spinNoiseScale = 0.1f;      // How much the spin is affected by noise
	  float   spinNoiseTimescale = 0.001f;  // How quickly the spin is affected

	  float   spinDamping = 0.92f;  // Drag/damping factor for scene rotation

	static final int numFliers   = 96;  // The number of flying objects
	static final int numChannels = 10;  // Number of independent control channels
	static final int numFlierChannels = 8;  // Number of channels to use for fliers
	static final int maxEffects = 10;   // Number of effects available

	static final float defaultSpin = 0.1f;
	static final float spinRateStep = 0.01f;
	static final float tiltStep = PI / 10;

	public SpaceBoxes(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		
	     colorMode(HSB, 100);
	    effectsInfo = new EffectInfo[]
	    {
	      new EffectInfo("Yellow Sphere",
	                     "Draws a sphere around the central box"),
	      new EffectInfo("Yellow Boxes",
	                     "Colours the boxes yellow instead of white"),
	      new EffectInfo("Heli Blades",
	                     "HeliCcoptor blades above box"),
	      new EffectInfo("Heli Cockpit",
	                     "HeliCcoptor cockpit in front of box")
	    };

	    numEffects = effectsInfo.length;

	    // ------------------------------------------------------------------------
	    // Set up control channels
	    // ------------------------------------------------------------------------
	    channels = new Channel[numChannels + 1];  // The control channels
	    for (int i = 0; i <= numChannels; i++)
	    {
	      String id = i == 0 ? "MASTER CHANNEL" : "Channel_" + i;
	      channels[i] = new Channel( id);
	    }

	    master = channels[0];  // Ha! :o)

	    // ------------------------------------------------------------------------
	    // Initialise all of the flying objects
	    // ------------------------------------------------------------------------
	    //fliers = new Flier[numFliers];
	  /*  for(int i = 0; i < numFliers; i++)
	    {
	      Channel channel = channels[i % numFlierChannels + 1];
	      //fliers[i] = new Flier( channel);
	      fliers.add(new Flier( channel));
	    }*/
	}
	  
	public void noteOn(int channel, int pit, int vel){
	  fliers.add(new Flier(master));
	}
	  // --------------------------------------------------------------------------
	  // Main update method
	  // --------------------------------------------------------------------------
	  void update()
	  {
	    // ------------------------------------------------------------------------
	    // Timing considerations
	    // ------------------------------------------------------------------------
	    time = p.millis();  //TODO: create beat-sychronised clock

	    // ------------------------------------------------------------------------
	    // Scene orientation
	    // ------------------------------------------------------------------------
	    if (ctrlSpin)
	    {
	      spinRate = p.lerp(spinRate, targetSpinRate, 0.05f);	      
	      omegaZ = spinRate;
	      if (ctrlSpinNoise)  // Spin the entire scene
	      {
	        // Take a noise sample based on time elapsed. Manipulate the [0,1) value
	        // to a [-0.5, 0.5) value, scale it and add to scene spin
	        omegaZ += (p.noise(time * spinNoiseTimescale) - 0.5) * spinNoiseScale;
	      }
	      
	      if (spinAdjust)
	        omegaZ *= spinAdjustment;
	    }
	    else
	    {
	      omegaZ *= spinDamping;
	    }

	    // Apply a drag/damping factor to the X and Y spin so it comes to rest
	    omegaX *= spinDamping;
	    omegaY *= spinDamping;
	  
	    targetThetaX += omegaX;
	    targetThetaY += omegaY;
	    targetThetaZ += omegaZ;

	    thetaX = p.lerp(thetaX, targetThetaX, 0.1f);  //TODO: magic number
	    thetaY = p.lerp(thetaY, targetThetaY, 0.1f);  //TODO: magic number
	    thetaZ = p.lerp(thetaZ, targetThetaZ, 0.1f);  //TODO: magic number
	    
	    // ------------------------------------------------------------------------
	    // Effect channel updates
	    // ------------------------------------------------------------------------
	    for (int i = 0; i < numChannels; i++)
	    {
	      channels[i].update();
	    }
	    
	    // ------------------------------------------------------------------------
	    // Update flying objects
	    // ------------------------------------------------------------------------
	    for (int i = 0; i < fliers.size(); i++)
	    {
	      //fliers[i].update();
	      fliers.get(i).update();
	    }
	  }

	  // --------------------------------------------------------------------------
	  // Main draw method
	  // --------------------------------------------------------------------------
	  public void draw()
	  {

	    background(0);
	      update();
	    sphereDetail(7);
	    
	    // Camera at (0, 0, 2000) looking at the origin (0, 0, 0), Y-axis is "up"
	    // This puts (0, 0, z) at the centre of the window
	    camera(0, 0, 2000, 0, 0, 0, 0, 1, 0);

	    rotateX(thetaX);  // Orient the scene
	    rotateY(thetaY);  // Orient the scene
	    rotateZ(thetaZ);  // Orient the scene
	  
	    // Set up lighting for the scene
	    // A light pointing straight up and another pointing straight down
	    directionalLight(51, 102, 126, 0, 1, 0);
	    directionalLight(10, 20, 30, 0, -1, 0);
	    
	    for (int i = 0; i < fliers.size(); i++)
	    {
	    	if (fliers.get(i).toKill())
	    		fliers.remove(i);
	    	else
	    		fliers.get(i).draw();
	    }
	  } 

	  // --------------------------------------------------------------------------
	  // Handle keyboard input
	  // --------------------------------------------------------------------------
	/*  void keyPressed()
	  {
	    final int PAGE_UP   = 33;
	    final int PAGE_DOWN = 34;
	    final int END       = 35;
	    final int HOME      = 36;
	    

	println("keyPressed(): keyCode: " + keyCode + ", key: [" + key + "] (" + (int)key + ")");
	    if (key == CODED) switch (keyCode)
	    {
	      case UP:    targetThetaX += tiltStep;
	                  break;
	  
	      case DOWN:  targetThetaX -= tiltStep;
	                  break;
	  
	      case LEFT:  targetThetaY -= tiltStep;
	                  break;
	  
	      case RIGHT: targetThetaY += tiltStep;
	                  break;

	      case END:
	      case HOME:  // Return to initial orientation;
	                  targetThetaX = 0;
	                  targetThetaY = 0;
	                  break;
	  
	      case PAGE_DOWN: spinRateDecrease();
	                      spinOn();
	                      break;
	  
	      case PAGE_UP:   spinRateIncrease();
	                      spinOn();
	                      break;
	  
	    }
	    else switch(key)
	    {
	      case ' ': togglePaused();
	                break;
	  
	      case 'A':
	      case 'a': toggleAuto();
	                break;
	  
	      case 'B':
	      case 'b': toggleBackground();
	                break;

	      case 'E': // p.random EFFECT TOGGLE on p.random CHANNEL
	                randomFlierChannel().randomEffectToggle();
	                break;
	      case 'e': // RANDOM EFFECT SHOT on RANDOM CHANNEL
	                randomFlierChannel().randomEffectShot();
	                break;

	      case 'N':
	      case 'n': bgImageIndex++;
	                if (bgImageIndex >= numImages)
	                   bgImageIndex = 0;
	                setBgImage(bgImageIndex);
	                break;

	      case 'R':
	      case 'r': master.toggleForceRegular();
	                break;
	  
	      case 'S': targetSpinRate = 0;
	                break;

	      case 's': toggleSpin();
	                break;
	  
	      case 'W':
	      case 'w': toggleSpinNoise();  // "wiggle" :o)
	                break;
	  
	      case 'T':
	      case 't': master.toggleTumble();
	                break;
	  
	      case ',': master.velocityHalve();
	                break;
	  
	      case '.': master.velocityDouble();
	                break;
	  
	      case '/': master.toggleVelocityAdjust();
	                break;
	  
	      case '<': master.velocityDecrease();
	                master.velocityAdjustOn();
	                break;

	      case '>': master.velocityIncrease();
	                break;

	      case '?': master.velocityAdjustmentReset();
	                break;

	      case '`': channels[0].randomAction(); break; // MASTER CHANNEL
	      case '1': channels[1].randomAction(); break;
	      case '2': channels[2].randomAction(); break;
	      case '3': channels[3].randomAction(); break;
	      case '4': channels[4].randomAction(); break;
	      case '5': channels[5].randomAction(); break;
	      case '6': channels[6].randomAction(); break;
	      case '7': channels[7].randomAction(); break;
	      case '8': channels[8].randomAction(); break;
	      case '9': channels[9].randomAction(); break;
	      case '0': channels[10].randomAction(); break;

	      case '~': channels[0].display = false; break; // MASTER CHANNEL
	      case '!': channels[1].display = false; break;
	      case '@': channels[2].display = false; break;
	      case '#': channels[3].display = false; break;
	      case '$': channels[4].display = false; break;
	      case '%': channels[5].display = false; break;
	      case '^': channels[6].display = false; break;
	      case '&': channels[7].display = false; break;
	      case '*': channels[8].display = false; break;
	      case '(': channels[9].display = false; break;
	      case ')': channels[10].display = false; break;

	    }
	  }*/
	  
	  // --------------------------------------------------------------------------
	  // Toggle methods
	  // --------------------------------------------------------------------------
	  
	  void toggleAuto()
	  {
	    ctrlAuto = !ctrlAuto;
	    if (ctrlAuto) newAutoCountdown();
	  }
	  
	  void toggleSpin()
	  {
	    ctrlSpin = !ctrlSpin;
	  }
	  
	  void spinOn()
	  {
	    ctrlSpin = true;
	  }
	  
	  void spinOff()
	  {
	    ctrlSpin = false;
	  }
	  
	  void toggleSpinNoise()
	  {
	    ctrlSpinNoise = !ctrlSpinNoise;
	  }
	  
	  // --------------------------------------------------------------------------
	  // Helper methods
	  // --------------------------------------------------------------------------
	  
	  void newAutoCountdown()
	  {
	    //TODO: this would be better using a synchronised oscilator
	    // Use a shorter countdown if the background isn't being shown now
	    autoCountdown = ctrlBackground ? (int)p.random(100) + 50 : 10;
	  }
	  
	  void spinRateIncrease()
	  {
	    if (ctrlSpin)
	      targetSpinRate += spinRateStep;
	    
	    ctrlSpin = true;
	  }

	  void spinRateDecrease()
	  {
	    if (ctrlSpin)
	      targetSpinRate -= spinRateStep;
	    
	    ctrlSpin = true;
	  }
	  
	  Channel randomFlierChannel()
	  {
	    int channelNo = (int)p.random(numFlierChannels) + 1;
	    return channels[channelNo];
	  }

float   minTumbleAdjustment = 0.125f;
float   maxTumbleAdjustment = 4;

class Channel
{
  String  id;                           // ident

  EffectParameters[] effectParams;      // Array of channel effects
  boolean[]          effectActive;      // Computed effective values for each effect

  boolean display             = true;   // Display?
  boolean doUpdate            = true;   // Update?

  boolean automate            = false;  // Automate? (Random channel changes?)
  int     autoBeats           = 4;      // Number of beats per automation
  int     autoBeatOffset      = 0;      // Beat offset for automation (0 = on main beat, 1 = next beat, etc)

  boolean travel              = true;   // Travel? (Move?)
  boolean velocityAdjust      = true;   // Use the velocity adjustment?
  float   velocityAdjustment  = 1.0f;    // Factor to adjust the velocity by
  float   velocityAdjustmentStep = 0.1f; // Number added to or subtractef from velocityAdjustment

  boolean tumble              = true;   // Tumble?
  boolean tumbleAdjust        = true;   // Use the tumble adjustment?
  float   tumbleAdjustment    = 1.0f;    // Factor to adjust the tumble rate by
  float   tumbleAdjustmentStep = 0.05f;  // Number added to or subtracted from tumbleAdjustment

  boolean forceRegular        = false;   // Force regular shape/size?

  boolean scaleAdjust         = false;   // Use the scale adjustment?
  float   scaleAdjustAmount   = 1.0f;     // Factor to adjust the scale by

  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------

  Channel( String id)
  {
   this.id = id;
    
    effectParams = new EffectParameters[maxEffects];
    effectActive = new boolean[maxEffects];
    for (int i = 0; i < maxEffects; i++)
    {
      effectParams[i] = new EffectParameters(this);
    }
  }

  // --------------------------------------------------------------------------
  // Update effect parameters, etc
  // --------------------------------------------------------------------------
  void update()
  {
    for (int i = 0; i < numEffects; i++)
    {
      effectParams[i].update();
      
      if (this == master)  // Master channel
        effectActive[i] = effectParams[i].active;
      else
        effectActive[i] = effectParams[i].active ^ master.effectParams[i].active;
    }
    
  }

  // --------------------------------------------------------------------------

  void toggleDisplay()           { display = !display; }
  void displayOn()               { display = true; }
  void displayOff()              { display = false; }
  
  void toggleDoUpdate()          { doUpdate = !doUpdate; }
  
  // --------------------------------------------------------------------------

  void toggleTravel()            { travel = !travel; }
  void toggleVelocityAdjust()    { velocityAdjust = !velocityAdjust; }
  void velocityAdjustOn()        { velocityAdjust = true; }
  void velocityAdjustOff()       { velocityAdjust = false; }
  void velocityReverse()         { velocityAdjustment *= -1.0;
                                   velocityAdjust = true; }
  void velocityHalve()
  {
    if (velocityAdjust)
      velocityAdjustment *= 0.5;

    velocityAdjust = true;  // always want to be moving now
  }
  void velocityDouble()
  {
    if (!velocityAdjust)
      velocityAdjust = true;  // since moving is faster than not moving
    else
      velocityAdjustment *= 2.0;
  }
  void velocityDecrease()        { velocityAdjustment -= velocityAdjustmentStep;
                                   velocityAdjust = true; }
  void velocityIncrease()        { velocityAdjustment += velocityAdjustmentStep;
                                   velocityAdjust = true; }
  void velocityAdjustmentReset() { velocityAdjustment = 1.0f; }

  // --------------------------------------------------------------------------

  void toggleTumble()            { tumble = !tumble; }
  void toggleTumbleAdjust()      { tumbleAdjust = !tumbleAdjust; }
  void tumbleReverse()           { tumbleAdjustment *= -1.0; }
  void tumbleHalve()             { tumbleAdjustment *= 0.5; }
  void tumbleDouble()            { tumbleAdjustment *= 2.0; }
  void tumbleDecrease()          { tumbleAdjustment -= tumbleAdjustmentStep; }
  void tumbleIncrease()          { tumbleAdjustment += tumbleAdjustmentStep; }
  void tumbleAdjustmentReset()   { tumbleAdjustment = 1.0f; }

  // --------------------------------------------------------------------------

  void toggleForceRegular()      { forceRegular = !forceRegular; }

  // --------------------------------------------------------------------------
  // Initiate a "random" action on the channel.
  // This isn't entirely random, since there are things we might want,
  // preferentially, to change before considering anything else, such
  // as re-enabling updates or the display of that channel.
  // --------------------------------------------------------------------------
  void randomAction()
  {
    p.println("---- randomAction() on " + id + " ----------");
    p.println("BEFORE: tumble: "+tumble+", Adjust: "+tumbleAdjust+", factor: "+tumbleAdjustment);
    if (!doUpdate)
    {
      p.println("* randomAction forced doUpdate");
      toggleDoUpdate();
      return;
    }

    if (!travel)
    {
      p.println("* randomAction forced travel");
      toggleTravel();
      return;
    }

    if (velocityAdjust && velocityAdjustment < 0)
    {
      p.println("* randomAction forced positive velocity");
      velocityReverse();
      return;
    }

    if (!tumble)
    {
      p.println("* randomAction forced tumble");
      toggleTumble();
      return;
    }
   
    if (!display)
    {
      p.println("* randomAction forced display");
      toggleDisplay();
      return;
    }
    
    if (tumbleAdjust && p.abs(tumbleAdjustment) > maxTumbleAdjustment/2)
    {
      p.println("* randomAction forced halve tumble adjustment");
      tumbleHalve();
      return;
    }
    
    
    boolean prevTumbleAdjust = tumbleAdjust;
    float prevTumbleAdjustment = tumbleAdjustment;

    int n = (int)p.random(13);
    
p.println("switch(" + n + ") ...");
    switch(n)
    {
      case 0:
      case 1:  // TOGGLE (turn off) TUMBLING
               toggleTumble();
               break;

      case 2:  // TOGGLE TUMBLE ADJUSTMENT
               toggleTumbleAdjust();
               break;

      case 3:
      case 4:
      case 5:
      case 6:  // REVERSE TUMBLE
               tumbleAdjust = true;
               tumbleReverse();
               // If the change is small, make it bigger!
               if (p.abs(tumbleAdjustment) < 0.3) tumbleDouble();
               break;

      case 7:
      case 8:
      case 9:  // HALVE OR DOUBLE TUMBLE ADJUSTMENT
               tumbleAdjust = true;
               if (p.abs(tumbleAdjustment) > maxTumbleAdjustment/2)
                 tumbleHalve();
               else if (p.abs(tumbleAdjustment) < minTumbleAdjustment*2 ||
                        p.random(1) < 0.5)  // forced double or p.random double
                 tumbleDouble();
               else
                 tumbleHalve();

               break;

      case 10: // p.random TUMBLE ADJUSTMENT
               {
                 tumbleAdjust = true;
                 float prev = tumbleAdjustment; 
                 tumbleAdjustment = p.pow(2, (int)p.random(-2, 3)); // 0.25 to 4
                 tumbleAdjustment *= (p.random(1) < 0.5) ? -1 : 1;
                 if (tumbleAdjustment == prev) tumbleAdjustment *= -1;
                 break;
               }

      case 11: // TOGGLE (turn off) DISPLAY
               toggleDisplay();
               break;

      case 12: // APPLY p.random EFFECT
               randomEffectShot();
               break;
    } // end switch()
    
   // Don't want to go from not using adjustment to using
   // adjustment of 1 (or from using adjustment of 1 to not
   // using adjustment)
   if ((!prevTumbleAdjust && tumbleAdjustment == 1) ||
       (prevTumbleAdjust && prevTumbleAdjustment == 1))
   {
     tumbleAdjust = true;
     tumbleDouble();
   }

p.println("AFTER: tumble: "+tumble+", Adjust: "+tumbleAdjust+", factor: "+tumbleAdjustment);
  } // end randomAction()

  void randomEffectShot()
  {
    int effectNo = randomEffectNumber();
p.println("Random effect shot [" + effectNo + "] " + effectsInfo[effectNo].name + " to " + id);
    effectParams[effectNo].shot();
    displayOn();
  }

  void randomEffectToggle()
  {
    int effectNo = randomEffectNumber();
p.println("Random effect shot [" + effectNo + "] " + effectsInfo[effectNo].name + " to " + id);
    effectParams[effectNo].toggle();
    displayOn();
  }

  int randomEffectNumber()
  {
    return (int)p.random(numEffects);
  }
}

public class EffectInfo
{
  String name;
  String description;
  
  public EffectInfo(String name, String description)
  {
    this.name = name;
    this.description = description;
  }
}


static int shotDuration = 2000;  // 2 seconds

public class EffectParameters
{
  Object  parent;            // Effect owner

  boolean active = false;    // Is this effect active? (boolean XOR levels)
  boolean complete = false;  // Set true when progress reaches 100% (1.0)
  
  float startTime;    //TODO: not yet well defined
  float stopTime;     //TODO: not yet well defined
  float duration;     // Total play time for effect (0 for continuous)

  float elapsedTime;  // Current play time since start
  float progress;     // 0 to 1, 

  float float1;
  float float2;
  int   int1;
  int   int2;

  //TODO: more parameters to come?
  
  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------
  EffectParameters(Object parent)
  {
    this.parent = parent;
  }

  // --------------------------------------------------------------------------
  // Update efefct parameters, LFO's, etc
  // --------------------------------------------------------------------------
  void update()
  {
    float time = p.millis();

    if (!active) return;
    
    if (complete)  // ie we finished last update
    {
      stop();
      return;
    }
    
    elapsedTime = time - startTime;

    progress = 0;
    if (duration != 0)
    {
      progress = elapsedTime / duration;
      
      if (progress >= 1)
      {
        progress = 1; // in case of overshoot
        complete = true;
      }
    }

//println("progress: " + progress);

    //TODO: looping effects, LFO's, etc
    //if (restartTime != 0 && time >= restartTime)
    //  start();
  }

  // --------------------------------------------------------------------------
  // Start the effect
  // --------------------------------------------------------------------------
  void start()
  {
p.println("start()");
    startTime = p.millis();  //TODO: replace with scene clock?
    duration = 0;
    stopTime = 0;
    active = true;
    complete = false;
  }
  
  // --------------------------------------------------------------------------
  // Stop the effect
  // --------------------------------------------------------------------------
  void stop()
  {
p.println("stop()");
    stopTime = p.millis();  //TODO: replace with scene clock?
    active = false;
  }
  
  // --------------------------------------------------------------------------
  // Start/stop the effect. Return whether the effect is then active.
  // --------------------------------------------------------------------------
  boolean toggle()
  {
p.println(parentName() + " toggle()");
    if (active)
      stop();
    else
      start();
      
    return active;
  }
  
  // --------------------------------------------------------------------------
  // Start the effect with a limited duration
  // --------------------------------------------------------------------------
  void shot()
  {
p.println(parentName() + " shot()");
    start();
    //TODO: turn on display of channel or object?
    duration = shotDuration;
    stopTime = startTime + duration;
  }
  
  String parentName()
  {
    if (parent instanceof Channel)
    {
      return ((Channel)parent).id;
    }
    
    return "(?effectParent?)";
  }
}

public class Flier
{
//  Scene scene;      // The scene this flier belongs to
  Channel channel;  // The effect channel controlling this flier

  // Position
  float X;
  float Y; 
  float Z; 

  // Velocity
  float vX;
  float vY;
  float vZ;

  // Rotation
  float thetaX; 
  float thetaY; 
  float thetaZ; 

  // Angular velocity
  float omegaX;
  float omegaY;
  float omegaZ;

  // Size of central box
  float szCentral;

  // Size of surrounding boxes
  float s1;
  float s2;
  float s3;
  float s4;
  float s5;
  float s6;

  // Distance of surrounding boxes from central box (measured from cenres)
  float d1;
  float d2;
  float d3;
  float d4;
  float d5;
  float d6;

  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------
  Flier( Channel channel)
  {
   // this.scene = scene;
    this.channel = channel;
    randomise();
  }

  // --------------------------------------------------------------------------
  // Set new location etc with randomised values
  // --------------------------------------------------------------------------
  void randomise()
  {   
    X = 0;
    Y = 0;
    Z = -p.random(1000);
  
    vX = p.random(-1, 1);
    vY = p.random(-1, 1);
    vZ = p.random(10) + 0.2f;
  
    thetaX = p.random(TWO_PI); 
    thetaY = p.random(TWO_PI); 
    thetaZ = 0;  // Already as (dis-)oriented as one can be from 2 axes. :o)
  
    omegaX = p.random(0.01f, 0.1f);
    omegaY = p.random(0.01f, 0.1f);
    omegaZ = 0;  // Spinning on a 3rd axis doesn't help.. gives strange motion
  
    float sz = p.random(1, 4) * 10;
    float sz_2 = sz * 0.5f;
    
    szCentral = sz;  // Set size of the central box
    
    if (master.forceRegular || channel.forceRegular)
    {
        s1 = s2 = s3 = s4 = s5 = s6 = sz_2;  // Half central box size
        d1 = d2 = d3 = d4 = d5 = d6 = sz;    // Spaced by central box size
    }
    else
    {
      // Size of 6 boxes surrounding central box
      s1 = p.random(sz_2, sz);
      s2 = p.random(sz_2, sz);
      s3 = p.random(sz_2, sz);
      s4 = p.random(sz_2, sz);
      s5 = p.random(sz_2, sz);
      s6 = p.random(sz_2, sz);
    
      // Distance of 6 surrounding boxes from central box (measured from centres)
      d1 = sz_2 + p.random(s1 / 2, s1 * 2);
      d2 = sz_2 + p.random(s2 / 2, s2 * 2);
      d3 = sz_2 + p.random(s3 / 2, s3 * 2);
      d4 = sz_2 + p.random(s4 / 2, s4 * 2);
      d5 = sz_2 + p.random(s5 / 2, s5 * 2);
      d6 = sz_2 + p.random(s6 / 2, s6 * 2);
    }
  }

  // --------------------------------------------------------------------------
  // Paint to screen
  // --------------------------------------------------------------------------
  void draw()
  {
    if (!master.display || !channel.display) return;
    
    pushMatrix();  // For entre method

    // ------------------------------------------------------------------------
    // Base settings for flier: location, orientation, fill colour
    // ------------------------------------------------------------------------    
    translate(X, Y, Z);  // Central box position

    rotateX(thetaX);  // Orientation
    rotateY(thetaY);  // ...
    rotateZ(thetaZ);  // ...
    
    int fillColour = color(100);  // White 100% opacity
    
    if (channel.effectActive[1])  // Yellow boxes
      fillColour = color(17, 90, 100);  // Yellow 100% opacity

    // Don't stroke edges.
    noStroke();

    // ------------------------------------------------------------------------    
    // Central box
    // ------------------------------------------------------------------------    
    pushMatrix();
    if (channel.effectActive[0])  // Yellow sphere centre
    {
      fill(17, 100, 100, 90);  // Yello 90% opacity
      sphere(szCentral / 2);
    }
    else  // Usual box
    {
      fill(fillColour);
      box(szCentral);
    }
    popMatrix();

    // ------------------------------------------------------------------------    
    // RIGHT SIDE
    // ------------------------------------------------------------------------    
    pushMatrix();
    translate(d1, 0, 0);
    fill(fillColour);
    box(s1);
    popMatrix();

    // ------------------------------------------------------------------------    
    // LEFT SIDE
    // ------------------------------------------------------------------------    
    pushMatrix();
    translate(-d2, 0, 0);
    fill(fillColour);
    box(s2);
    popMatrix();

    // ------------------------------------------------------------------------    
    // BOTTOM SIDE
    // ------------------------------------------------------------------------    
    pushMatrix();
    translate(0, d3, 0);
    fill(fillColour);
    box(s3);
    popMatrix();

    // ------------------------------------------------------------------------    
    // TOP SIDE
    // ------------------------------------------------------------------------    
    pushMatrix();
    if (channel.effectActive[2])  // Heli blades head
    {
      EffectParameters ep = channel.effectParams[2];
      
      translate(0, -d4, 0);
      rotateY(ep.elapsedTime * 0.015f);  // Spin the blades
      fill(240, 90, 100);  // Blue 100% opacity
      box(s4*0.75f);  // Wing-nut ;o)

      // Heli blades
      float wingspan = szCentral * 8;
      fill(100);  // White 100% opacity
      box(wingspan, -s4/8, s4/2);
      box(s4/2, -s4/8, wingspan);
    }
    else  // Usual box
    {
      translate(0, -d4, 0);  // TOP SIDE
      fill(fillColour);
      box(s4);
    }
    popMatrix();

    // ------------------------------------------------------------------------    
    // FRONT SIDE
    // ------------------------------------------------------------------------    
    if (channel.effectActive[3])  // Heli cockpit
    {
      pushMatrix();
      translate(0, d4/2, d5);
      fill(67, 75, 100, 80);  // Some pale blue, 80% opacity
      box(s5*2.5f, s5, s5*3);
      popMatrix();
    }
    else
    {
      pushMatrix();
      translate(0, 0, d5);
      fill(fillColour);
      box(s5);
      popMatrix();
    }
    // ------------------------------------------------------------------------    
    // BACK SIDE
    // ------------------------------------------------------------------------    
    pushMatrix();
    translate(0, 0, -d6);
    fill(fillColour);
    box(s6);
    popMatrix();
    
 
    popMatrix();
  }
  
  public boolean toKill(){
	    if(Z > 2000)  // Flown past us ("through the screen") already
	    	return true;
	    else
	    	return false;
  }
  
  // --------------------------------------------------------------------------
  // update state
  // --------------------------------------------------------------------------
  public void update()
  {
    if (!master.doUpdate || !channel.doUpdate) return;
    
    // Update position and rotation
    
      if (master.travel && channel.travel)
      {
        float velocityAdjustment =
          master.velocityAdjust ? master.velocityAdjustment : 1.0f;
        if (channel.velocityAdjust)
          velocityAdjustment *= channel.velocityAdjustment;
        
        if (velocityAdjustment != 0) // Move (constant velocity)
        {
          X += vX * velocityAdjustment;
          Y += vY * velocityAdjustment;
          Z += vZ * velocityAdjustment;
        }
      }
      
      if (master.tumble && channel.tumble)
      {
        float tumbleAdjustment =
          master.tumbleAdjust ? master.tumbleAdjustment : 1.0f;
        if (channel.tumbleAdjust)
          tumbleAdjustment *= channel.tumbleAdjustment;

        // Limit the adjustment factor; too fast looks rubbish        
        if (tumbleAdjustment < -maxTumbleAdjustment)
          tumbleAdjustment = -maxTumbleAdjustment;
        else if (tumbleAdjustment > maxTumbleAdjustment)
          tumbleAdjustment = maxTumbleAdjustment;
  
        if (tumbleAdjustment != 0)  // Rotate
        {
          thetaX += omegaX * tumbleAdjustment;
          thetaY += omegaY * tumbleAdjustment;
          thetaZ += omegaZ * tumbleAdjustment;
        }
      }
    
  }
}




}