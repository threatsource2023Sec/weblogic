package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

public class StylesheetRenderer extends ScriptStyleBaseRenderer {
   protected void startInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("style", component);
      writer.writeAttribute("type", "text/css", "type");
   }

   protected void endInlineElement(ResponseWriter writer, UIComponent component) throws IOException {
      writer.endElement("style");
   }

   protected void startExternalElement(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("link", component);
      writer.writeAttribute("type", "text/css", "type");
      writer.writeAttribute("rel", "stylesheet", "rel");
   }

   protected void endExternalElement(ResponseWriter writer, UIComponent component, String resourceUrl) throws IOException {
      writer.writeURIAttribute("href", resourceUrl, "href");
      String media = (String)component.getAttributes().get("media");
      if (media != null) {
         writer.writeAttribute("media", media, "media");
      }

      writer.endElement("link");
   }

   protected String verifyTarget(String toVerify) {
      return "head";
   }
}
