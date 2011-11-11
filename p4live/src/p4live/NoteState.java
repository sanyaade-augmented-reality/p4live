package p4live;

public class NoteState{
	public int channel;
	public int velocity;
	public int pitch;
	public boolean state;
//	int pos;

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

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