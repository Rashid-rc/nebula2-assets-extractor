package me.vinceh121.n2ae.script.json;

import java.io.PrintStream;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WikiBoxGenerator {
	public void write(final PrintStream out, final WikiBox box, final ObjectNode node) {
		out.print("{{");
		out.println(box.getTemplateName());
		for (final String prop : box.getProperties().keySet()) {
			final JsonPointer ptr = box.getProperties().get(prop);
			final JsonNode val = node.at(ptr);

			if (val.isMissingNode()) {
				throw new IllegalStateException("Pointer " + ptr + " missing");
			}

			out.print("| ");
			out.print(prop);
			out.print("=");
			if (val.isFloat()) {
				// use default decimal format to print decimal part only when required
				out.println(new DecimalFormat().format(val.asDouble()));
			} else {
				out.println(val);
			}
		}
		out.println("}}");
	}
}
