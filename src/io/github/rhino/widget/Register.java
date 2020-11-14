package io.github.rhino.widget;

import java.util.HashMap;
import java.util.Map;

public class Register {
	public static Register DEFAULT = new Register();
	
	Map<String, Object> map;
	Map<Object, String> rmap;

	public Register() {
		map = new HashMap<String, Object>();
		rmap = new HashMap<Object, String>();
	}
	
	public void register(String widgetName, Object widget) {
		map.put(widgetName, widget);
		rmap.put(widget, widgetName);
	}
	
	public String queryName(Object widget) {
		return rmap.get(widget);
	}
	
	public Object queryWidget(String widgetName) {
		return map.get(widgetName);
	}
}
