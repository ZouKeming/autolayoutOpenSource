package io.github.rhino.window;

import io.github.parameters.Parameters;
import io.github.rhino.Path;

public class TabEmbedRule implements LayoutRule {

	public String getWidgetName(Parameters parameters) {
//		Path path = (Path) parameters.get("path");
//		
//		if( path.size() % 3 == 0 ) {
//			return "TabSVGH";
//		} else {
//			return "DocumentorSVGH";
//		}
		
		int uiLevel = parameters.getInteger("uiLevel");
		if( uiLevel % 4 == 1 ) {
			return "TabSVGH";
		} else {
			return "DocumentorSVGH";
		}
	}

}
