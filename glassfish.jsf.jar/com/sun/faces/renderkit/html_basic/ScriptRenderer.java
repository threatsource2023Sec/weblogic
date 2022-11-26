package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class ScriptRenderer extends ScriptStyleBaseRenderer {
   protected void startInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("script", component);
      writer.writeAttribute("type", "text/javascript", "type");
   }

   protected void endInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
      writer.endElement("script");
   }

   protected void startExternalElement(ResponseWriter writer, UIComponent component) throws IOException {
      this.startInlineElement(writer, component);
   }

   protected void endExternalElement(ResponseWriter writer, UIComponent component, String resourceUrl) throws IOException {
      writer.writeURIAttribute("src", resourceUrl, "src");
      this.endInlineElement(writer, component);
   }
}
