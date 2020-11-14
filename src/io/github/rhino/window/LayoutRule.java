package io.github.rhino.window;

import io.github.parameters.Parameters;

public interface LayoutRule {
	public String getWidgetName(Parameters parameters);

	public static Parameters startParameters() {
		Parameters parameters = new Parameters();
		return parameters;
	}
}
