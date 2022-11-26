package com.sun.faces.ext.render;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

public class FocusHTMLRenderer extends Renderer {
   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      String forID = (String)component.getAttributes().get("for");
      ResponseWriter writer = context.getResponseWriter();
      writer.startElement("script", component);
      writer.writeAttribute("type", "text/javascript", (String)null);
      writer.writeText("setFocus('", (String)null);
      writer.writeText(forID, (String)null);
      writer.writeText("');\n", (String)null);
      writer.writeText("function setFocus(elementId) { var element = document.getElementById(elementId); if (element && element.focus) { element.focus(); } }", (String)null);
      writer.endElement("script");
   }
}
