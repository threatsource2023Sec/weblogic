package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

public class ButtonRenderer extends HtmlBasicRenderer {
   private static final String[] ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         if (wasClicked(context, component) && !isReset(component)) {
            component.queueEvent(new ActionEvent(component));
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("This command resulted in form submission  ActionEvent queued.");
               logger.log(Level.FINE, "End decoding component {0}", component.getId());
            }
         }

      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         String type = getButtonType(component);
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String label = "";
         Object value = ((UICommand)component).getValue();
         if (value != null) {
            label = value.toString();
         }

         String imageSrc = (String)component.getAttributes().get("image");
         writer.startElement("input", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         String clientId = component.getClientId(context);
         if (imageSrc != null) {
            writer.writeAttribute("type", "image", "type");
            writer.writeURIAttribute("src", src(context, imageSrc), "image");
            writer.writeAttribute("name", clientId, "clientId");
         } else {
            writer.writeAttribute("type", type, "type");
            writer.writeAttribute("name", clientId, "clientId");
            writer.writeAttribute("value", label, "value");
         }

         RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         String styleClass = (String)component.getAttributes().get("styleClass");
         if (styleClass != null && styleClass.length() > 0) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         writer.endElement("input");
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   private static String src(FacesContext context, String imageURI) {
      if (imageURI == null) {
         return "";
      } else {
         String u = context.getApplication().getViewHandler().getResourceURL(context, imageURI);
         return context.getExternalContext().encodeResourceURL(u);
      }
   }

   private static boolean wasClicked(FacesContext context, UIComponent component) {
      String clientId = component.getClientId(context);
      Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
      if (requestParameterMap.get(clientId) != null) {
         return true;
      } else {
         StringBuilder builder = new StringBuilder(clientId);
         String xValue = builder.append(".x").toString();
         builder.setLength(clientId.length());
         String yValue = builder.append(".y").toString();
         return requestParameterMap.get(xValue) != null && requestParameterMap.get(yValue) != null;
      }
   }

   private static boolean isReset(UIComponent component) {
      return "reset".equals(component.getAttributes().get("type"));
   }

   private static String getButtonType(UIComponent component) {
      String type = (String)component.getAttributes().get("type");
      if (type == null || !"reset".equals(type) && !"submit".equals(type)) {
         type = "submit";
         component.getAttributes().put("type", type);
      }

      return type;
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDBUTTON);
   }
}
