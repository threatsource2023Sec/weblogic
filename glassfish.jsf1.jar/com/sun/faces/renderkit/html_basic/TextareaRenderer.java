package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class TextareaRenderer extends HtmlBasicInputRenderer {
   private static final String[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      String styleClass = (String)component.getAttributes().get("styleClass");
      writer.startElement("textarea", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      if (null != styleClass) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      if (currentValue != null) {
         writer.writeText(currentValue, component, "value");
      }

      writer.endElement("textarea");
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXTAREA);
   }
}
