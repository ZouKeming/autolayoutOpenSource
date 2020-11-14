package io.github.rhino.window;

import io.github.parameters.Parameters;
import io.github.rhino.Path;

public class TabFirstRule implements LayoutRule {

	public String getWidgetName(Parameters parameters) {
		Path path = (Path) parameters.get("path");
		if( path.size() == 1 ) {
			return "TabSVGH";
		} else if( path.size() > 1 ) {
			return "DocumentorSVGH";
		}
		return "Default";
	}

}
