package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class TextRenderer extends HtmlBasicInputRenderer {
   private static final String[] INPUT_ATTRIBUTES;
   private static final String[] OUTPUT_ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      boolean shouldWriteIdAttribute = false;
      boolean isOutput = false;
      String style = (String)component.getAttributes().get("style");
      String styleClass = (String)component.getAttributes().get("styleClass");
      String dir = (String)component.getAttributes().get("dir");
      String lang = (String)component.getAttributes().get("lang");
      String title = (String)component.getAttributes().get("title");
      if (component instanceof UIInput) {
         writer.startElement("input", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         writer.writeAttribute("type", "text", (String)null);
         writer.writeAttribute("name", component.getClientId(context), "clientId");
         if ("off".equals(component.getAttributes().get("autocomplete"))) {
            writer.writeAttribute("autocomplete", "off", "autocomplete");
         }

         if (currentValue != null) {
            writer.writeAttribute("value", currentValue, "value");
         }

         if (null != styleClass) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         RenderKitUtils.renderPassThruAttributes(writer, component, INPUT_ATTRIBUTES);
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         writer.endElement("input");
      } else if (isOutput = component instanceof UIOutput) {
         if (styleClass != null || style != null || dir != null || lang != null || title != null || (shouldWriteIdAttribute = this.shouldWriteIdAttribute(component))) {
            writer.startElement("span", component);
            this.writeIdAttributeIfNecessary(context, writer, component);
            if (null != styleClass) {
               writer.writeAttribute("class", styleClass, "styleClass");
            }

            RenderKitUtils.renderPassThruAttributes(writer, component, OUTPUT_ATTRIBUTES);
         }

         if (currentValue != null) {
            Object val = component.getAttributes().get("escape");
            if (val != null && Boolean.valueOf(val.toString())) {
               writer.writeText(currentValue, component, "value");
            } else {
               writer.write(currentValue);
            }
         }
      }

      if (isOutput && (styleClass != null || style != null || dir != null || lang != null || title != null || shouldWriteIdAttribute)) {
         writer.endElement("span");
      }

   }

   static {
      INPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXT);
      OUTPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTTEXT);
   }
}
