package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.logging.Level;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class LabelRenderer extends HtmlBasicInputRenderer {
   private static final String[] ATTRIBUTES;
   private static final String RENDER_END_ELEMENT = "com.sun.faces.RENDER_END_ELEMENT";

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String forClientId = null;
         String forValue = (String)component.getAttributes().get("for");
         if (forValue != null) {
            forValue = this.augmentIdReference(forValue, component);
            UIComponent forComponent = this.getForComponent(context, forValue, component);
            if (forComponent == null) {
               forClientId = this.getForComponentClientId(component, context, forValue);
            } else {
               forClientId = forComponent.getClientId(context);
            }
         }

         component.getAttributes().put("com.sun.faces.RENDER_END_ELEMENT", "yes");
         writer.startElement("label", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         if (forClientId != null) {
            writer.writeAttribute("for", forClientId, "for");
         }

         RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
         String styleClass = (String)component.getAttributes().get("styleClass");
         if (null != styleClass) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         writer.writeText("\n", component, (String)null);
         String value = this.getCurrentValue(context, component);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("Value to be rendered " + value);
         }

         if (value != null && value.length() != 0) {
            Object val = component.getAttributes().get("escape");
            boolean escape = val != null && Boolean.valueOf(val.toString());
            if (escape) {
               writer.writeText(value, component, "value");
            } else {
               writer.write(value);
            }
         }

         writer.flush();
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         String render = (String)component.getAttributes().get("com.sun.faces.RENDER_END_ELEMENT");
         if ("yes".equals(render)) {
            component.getAttributes().remove("com.sun.faces.RENDER_END_ELEMENT");
            ResponseWriter writer = context.getResponseWriter();

            assert writer != null;

            writer.endElement("label");
         }

      }
   }

   protected String getForComponentClientId(UIComponent component, FacesContext context, String forValue) {
      String result = null;

      UIComponent parent;
      for(parent = component.getParent(); parent != null && !(parent instanceof NamingContainer); parent = parent.getParent()) {
      }

      if (parent == null) {
         return result;
      } else {
         String parentClientId = parent.getClientId(context);
         result = parentClientId + ':' + forValue;
         return result;
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTLABEL);
   }
}
