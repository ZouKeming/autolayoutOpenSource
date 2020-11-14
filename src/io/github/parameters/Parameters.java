package io.github.parameters;

import java.util.TreeMap;

public class Parameters extends TreeMap<String,Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7953252496675522955L;
	Parameters shared;
	TreeMap<String, Integer> ttlMap = null;		// time to live

	public Parameters() {
		super();
		this.put("level", 0);
		this.put("skip", 0);
	}

	// level is absolute level
	// rlevel is relative level. if skip > 0 it will not be increased
	public Parameters(Parameters src) {
		super(src);
		Integer level = src.getInteger("level");
		Integer rlevel = src.getInteger("rlevel");
		Integer skip = (Integer) src.get("skip");

		this.shared = src.shared;

		// this.livedPropertiesCheck();
		// duplicate ttl of properties
		if ( src.ttlMap != null )
			for(String key : src.ttlMap.keySet()) {
				Object value = this.get(key);
				int ttlNum = src.ttlMap.get(key);
				if ( ttlNum > 0 ) {
					this.put(key, value, ttlNum - 1);
				} else 
					this.remove(key);
			}

		if ( skip > 0 ) {
			// do nothing
		} else {
			this.put("rlevel", rlevel + 1);
		}
		this.put("level", level + 1);
		this.put("skip", skip - 1);
	}

	//	private void livedPropertiesCheck() {
	//		if ( this.ttlMap != null ) {
	////			for(String key : this.lived.keySet()) {
	////				int ttl = this.lived.get(key);
	////				if ( ttl > 0 ) {
	////					// ok. keep live
	////					this.lived.put(key, ttl - 1);
	////				} else {
	////					this.lived.remove(key);
	////					this.remove(key);
	////				}
	////			}
	//			
	//			Iterator<Entry<String, Integer>> it = this.ttlMap.entrySet().iterator();
	//			while(it.hasNext()) {
	//				Map.Entry<String,Integer> m = it.next();
	//				String key = m.getKey();
	//				Integer ttl = (Integer)m.getValue();
	//				if( ttl > 0 ) {
	//					this.ttlMap.put(key, ttl - 1);
	//				} else {
	//					it.remove();
	//					this.remove(key);
	//				}
	//			}
	//			
	//		}
	//	}

	public Object put(String key, Object value, int ttl) {
		Object retval = super.put(key, value);
		if ( this.ttlMap == null )
			this.ttlMap = new TreeMap<String, Integer>();
		this.ttlMap.put(key, ttl); 
		return retval;
	}

	public Parameters shared() {
		if ( this.shared == null ) {
			this.shared = new Parameters();
		}
		return shared;
	}

	public Parameters getShared() {
		if ( this.shared == null ) {
			this.shared = new Parameters();
		}
		return shared;
	}
	
	public void cloneShared() {
		this.shared = new Parameters(this.shared);
	}

	public void setShared(Parameters shared) {
		this.shared = shared;
	}

	public int getInteger(String key) {
		Object value = this.get(key);
		if ( value == null ) {
			return 0;
		} else if ( value instanceof String ) {
			return Integer.parseInt((String)value);
		} else 
			return (Integer)value;
	}

	public Long getLong(String key) {
		Object value = this.get(key);
		if ( value == null ) {
			return (long) 0;
		} else if ( value instanceof String ) {
			return Long.parseLong((String)value);
		} else 
			return (Long)value;
	}

	public double getDouble(String key) {
		Object value = this.get(key);
		if ( value == null ) {
			return 0;
		} else if ( value instanceof String ) {
			return Double.parseDouble((String)value);
		} else 
			return (Double) value;
	}

	public String getString(String key) {
		String value = (String) this.get(key);
		if ( value == null )
			return "";
		else 
			return value;
	}

	public boolean getBoolean(String key) {
		Object value = this.get(key);
		if ( value == null ) {
			return false;
		} else if ( value instanceof String ) {
			return Boolean.parseBoolean((String)value);
		} else
			return (Boolean) value;
	}

	public char getChar(String key) {
		Object value = this.get(key);
		if ( value == null ) {
			return (char) 0;
		} else
			return (Character) value;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : this.keySet()) {
			Object value = this.get(key);
			if ( value != null ) {
				sb.append(key + " : " + value.toString() + "\n");
			} else { 
				sb.append(key + " : null" + "\n");
			}
		}
		return sb.toString();
	}

	public Parameters getRetval() {
		Parameters retval = (Parameters) this.get("retval");
		if ( retval==null ) {
			retval = new Parameters();
			this.put("retval", retval, 0);
		}
		return retval;
	}
	
	public Parameters retval() {
		return getRetval();
	}

	public Parameters getSharedRetval() {
		Parameters retval = (Parameters) this.getShared().get("retval");
		if ( retval==null ) {
			retval = new Parameters();
			this.getShared().put("retval", retval, 0);
		}
		return retval;
	}

	public void putShared(String string, Object obj) {
		Parameters sharedParameters = this.getShared();
		sharedParameters.put(string, obj);
	}
	
	public void debug() {
		int debugCounter = this.shared().getInteger("debugCounter");
		this.shared().put("debugCounter", debugCounter + 1);
		System.out.println(debugCounter);
	}

	public boolean debug(int i) {
		return this.shared.getInteger("debugCounter") == i;
	}
	
	public Parameters local() {
		if ( this.shared!=null ) {
			LocalVar localVar = (LocalVar) this.getShared().get("localVar");
			if( localVar != null ) {
				Parameters retval = new Parameters(this);
				return localVar.update(retval);
			}
		}
		return this;
	}

	public static void main(String[] argv) {
		Parameters parameters = new Parameters();
		parameters.put("hello", "world");
		System.out.println(parameters);
	}
	
}
