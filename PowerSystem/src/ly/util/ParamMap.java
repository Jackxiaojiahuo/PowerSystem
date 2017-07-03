package ly.util;

import java.util.Map;
import java.util.Set;

public class ParamMap {
	private int cmd;
	private Map otherParams;

	public ParamMap(int cmd) {
		super();
		this.cmd = cmd;
	}
	public ParamMap(int cmd,Map otherParams) {
		this(cmd);
		this.otherParams = otherParams;
	}
	
	@Override
	public String toString() {
		String ptxt="cmd="+cmd;
		if(otherParams!=null){
			Set keyset=otherParams.keySet();
			for (Object key : keyset) {
			String name=(String)key;
			String value=(String)otherParams.get(name);
			ptxt+="&"+name+"="+value;
			}
		}
		return ptxt;
	}
	
}
