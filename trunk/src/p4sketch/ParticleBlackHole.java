package p4sketch;

import p4control.Volume;
import p4live.EventsMidi;
import p4live.OutputWindow;
import p4live.P4Constants;
import processing.core.PApplet;
import processing.core.PVector;
import codeanticode.glgraphics.GLGraphicsOffScreen;

public class ParticleBlackHole extends Sketch {
	int iScene = 0;
	float fSceneTime = 0;
	int iNumScenes = 6;
	boolean bAutoSceneAdvance = true;
	int iNumParticles = 5000;
	float fMaxSpeed = 20.0f;
	float fDamping = 0.85f;
	Particle[] aParticles = new Particle[iNumParticles];

	public ParticleBlackHole(PApplet arg0, int arg1 ,int arg2) {
		super(arg0,arg1,arg2);
		smooth();
		for (int i = 0; i < iNumParticles; i++)
		{
			aParticles[i] = new Particle();
		}
	}

	public void draw()
	{
		//background(0);
		fill(0,255-(Volume.level*255));
		rect(0,0,width,height);
		
		//applyMidiState();
		
		// Update and render all particles
		for (int i = 0; i < iNumParticles; i++)
		{
			aParticles[i].Update();
		}
	}
	
	public void noteOn(int channel, int pitch, int velocity){
		if (channel == P4Constants.RIFF1)
			iScene = (int)p.random(iNumScenes);
	}

	/*public void applyMidiState(){
		for (int k=0;k<EventsMidi.midiState.size();k++){
			
			float can = EventsMidi.midiState.get(k).channel;
			int pit = EventsMidi.midiState.get(k).pitch;
			int vel = EventsMidi.midiState.get(k).velocity;		
			
			for (int i = 0; i < iNumParticles; i++)
			{
				int x =  (int) p.map(can,1f,16f,0,500);
				int y = (int)p.map(pit,0,127,0, 500);
				
				int r = (int)p.map(can,0,16,300,2000);
				int f = (int) p.map(vel,0,127,-2,2);		
				int a = (int) p.map(pit,0,127,-2,2);						
				//PVector vecOrigin, float fRange, float fMaxAttractForce, float fMaxAngleForce)
				aParticles[i].ApplyForce(new PVector(x, y), r, f, a);
			}
		}
	}*/
	

	///////////////////////////////////
	public class Particle
	{
		Particle()
		{
			Spawn();
		}

		public void Update()
		{
			// Damp
			m_vecVelocity.mult(fDamping);

			// Add forces
			if (iScene == 0)
			{
				ApplyForce(new PVector(256, 256), 500, 0, 1);
			}
			else if (iScene == 1)
			{
				ApplyForce(new PVector(256, 406), 800, -1, 0);
			}
			else if (iScene == 2)
			{
				ApplyForce(new PVector(0+100, 156), 250, -0.5f, 2);
				ApplyForce(new PVector(512-100, 156), 250, -0.5f, -2);
				ApplyForce(new PVector(256, 250), 550, -1, 0);
			}
			else if (iScene == 3)
			{
				ApplyForce(new PVector(100, 100), 500, -1, 0);
				ApplyForce(new PVector(512-200, 512-200), 200, 1, 2);
			}
			else if (iScene == 4)
			{
				ApplyForce(new PVector(180, 200), 200, 0, 2);
				ApplyForce(new PVector(460, 450), 600f, -2f, 0.5f);
			}
			else if (iScene == 5)
			{
				ApplyForce(new PVector(180, 200), 200, 0, 2);
				ApplyForce(new PVector(420, 40), 100, 2.1f, 3);
				ApplyForce(new PVector(220, 400), 600, -2, 0);
				ApplyForce(new PVector(460, 450), 600, -2.2f, 0.5f);
			}

			// Update position
			m_vecOldPos = m_vecPos.get();
			m_vecVelocity.limit(fMaxSpeed);
			m_vecPos.add(m_vecVelocity);

			// Get particle speed
			PVector vecLength = m_vecPos.get();
			vecLength.sub(m_vecOldPos);
			float fSpeed = vecLength.mag();
			if (fSpeed < 0.5)
			{
				// Particle is pretty stationary
				m_iIdleFrames++;

				// Should we kill it yet?
				if (m_iIdleFrames > 10)
				{
					Spawn();
				}
			}
			else
			{
				// How far is particle from center of screen
				PVector vecDistance = new PVector(width/2, height/2, 0);
				vecDistance.sub(m_vecPos);
				if(vecDistance.mag() > (width * 2))
				{
					// Too far off screen - kill it
					Spawn();
				}
				else
				{
					// Particle is moving and not too far off screen
					m_iIdleFrames = 0;

					// Render it
					PVector vecDirNorm = vecLength.get();
					vecDirNorm.normalize();
					stroke(255,
							150 + 100 * p.sin(vecDirNorm.x * 10),
							120 + 90 * p.sin(vecDirNorm.y * 5),
							fSpeed / fMaxSpeed * 200 + 55);
					line(m_vecPos.x, m_vecPos.y, m_vecOldPos.x, m_vecOldPos.y);
				}
			}
		}

		void ApplyForce(PVector vecOrigin, float fRange, float fMaxAttractForce, float fMaxAngleForce)
		{ 
			// Are we close enough to be influenced?
			PVector vecToOrigin = m_vecPos.get();
			vecToOrigin.sub(vecOrigin);
			float fDist = vecToOrigin.mag();
			if (fDist < fRange)
			{
				float fDistAlpha = (fRange - fDist) / fRange;
				fDistAlpha = fDistAlpha * fDistAlpha;
				if (fDistAlpha > 0.95)
				{
					// If particle is too close to origin then kill it
					Spawn();
				}
				else
				{
					// Apply attraction/replusion force
					PVector vecNormal = vecToOrigin.get();
					vecNormal.normalize();
					PVector vecForce = vecNormal.get();
					vecForce.mult(fDistAlpha * fMaxAttractForce);
					m_vecVelocity.add(vecForce);

					// Apply spin force
					PVector vecTangentForce = new PVector(vecNormal.y, -vecNormal.x, 0);
					vecTangentForce.mult(fDistAlpha * fMaxAngleForce);
					m_vecVelocity.add(vecTangentForce);
				}
			}
		}

		void Spawn()
		{
			m_vecPos = new PVector(p.random(-OutputWindow.getWidth()/2, OutputWindow.getWidth()+OutputWindow.getWidth()/2), p.random(-OutputWindow.getHeight()/2, OutputWindow.getHeight()+OutputWindow.getHeight()/2), 0);
			m_vecOldPos = m_vecPos.get();
			m_vecVelocity = new PVector(0, 0, 0);
			m_iIdleFrames = 0;
		}

		PVector m_vecPos;
		PVector m_vecOldPos;
		PVector m_vecVelocity;
		int m_iIdleFrames;
	}


}