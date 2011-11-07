package p4blend;

public class ScalarParam {
	  public float value;
	  public float minValue;
	  public float maxValue;
	  public float step;
	  
	  public ScalarParam(float v,float mn, float mx, float s) {
	    value = v;
	    minValue = mn;
	    maxValue = mx;
	    step = s;
	  }
	  
	  public void SetValue(float v){
		  value = v;
	  }
	  
	  public void Increment() {
	    value += step;
	    if (value > maxValue) value=maxValue;
	  }
	  
	  public void Decrement() {
	    value -= step;
	    if (value < minValue) value=minValue;
	  }
	}
