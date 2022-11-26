package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class HiddenRenderer extends HtmlBasicInputRenderer {
   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("input", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("type", "hidden", "type");
      String clientId = component.getClientId(context);
      writer.writeAttribute("name", clientId, "clientId");
      if (currentValue != null) {
         writer.writeAttribute("value", currentValue, "value");
      }

      writer.endElement("input");
   }
}
