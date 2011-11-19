package p4live;

public class NoteState{
	public int channel;//=0;
	public int velocity;//=0;
	public int pitch;//=0;
	public boolean state;// = false;

	/*NoteState(int channel,int velocity,int pitch){
		this.channel = channel;
		this.velocity = velocity;
		this.pitch = pitch;
//		this.state = true;
	}*/

	public boolean equals(Object obj) {
		/*if (this == obj) {
			return true;
		}*/

		if (obj instanceof NoteState) {
			NoteState n = (NoteState) obj;				
			if ( this.channel != n.channel ) return false;
			if ( this.pitch != n.pitch ) return false;
			return true;
		}
		else
			return false;
	}
}