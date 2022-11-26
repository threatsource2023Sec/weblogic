package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

public class ButtonRenderer extends HtmlBasicRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = this.decodeBehaviors(context, component);
         if (wasClicked(context, component, clientId) && !isReset(component)) {
            component.queueEvent(new ActionEvent(context, component));
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

         Collection params = this.getBehaviorParameters(component);
         if (!params.isEmpty() && (type.equals("submit") || type.equals("button"))) {
            RenderKitUtils.renderJsfJsIfNecessary(context);
         }

         String imageSrc = (String)component.getAttributes().get("image");
         writer.startElement("input", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         String clientId = component.getClientId(context);
         if (imageSrc != null) {
            writer.writeAttribute("type", "image", "type");
            writer.writeURIAttribute("src", RenderKitUtils.getImageSource(context, component, "image"), "image");
            writer.writeAttribute("name", clientId, "clientId");
         } else {
            writer.writeAttribute("type", type, "type");
            writer.writeAttribute("name", clientId, "clientId");
            writer.writeAttribute("value", label, "value");
         }

         RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnClickBehaviors(component));
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         String styleClass = (String)component.getAttributes().get("styleClass");
         if (styleClass != null && styleClass.length() > 0) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         RenderKitUtils.renderOnclick(context, component, params, (String)null, false);
         if (component.getChildCount() == 0) {
            writer.endElement("input");
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (component.getChildCount() > 0) {
         context.getResponseWriter().endElement("input");
      }

   }

   private static boolean wasClicked(FacesContext context, UIComponent component, String clientId) {
      if (clientId == null) {
         clientId = component.getClientId(context);
      }

      if (context.getPartialViewContext().isAjaxRequest()) {
         return RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_SOURCE_PARAM.getValue(context).equals(clientId);
      } else {
         Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
         if (requestParameterMap.get(clientId) == null) {
            if (RenderKitUtils.isPartialOrBehaviorAction(context, clientId)) {
               return true;
            } else {
               StringBuilder builder = new StringBuilder(clientId);
               String xValue = builder.append(".x").toString();
               builder.setLength(clientId.length());
               String yValue = builder.append(".y").toString();
               return requestParameterMap.get(xValue) != null && requestParameterMap.get(yValue) != null;
            }
         } else {
            return true;
         }
      }
   }

   private static boolean isReset(UIComponent component) {
      return "reset".equals(component.getAttributes().get("type"));
   }

   private static String getButtonType(UIComponent component) {
      String type = (String)component.getAttributes().get("type");
      if (type == null || !"reset".equals(type) && !"submit".equals(type) && !"button".equals(type)) {
         type = "submit";
         component.getAttributes().put("type", type);
      }

      return type;
   }

   private static Map getNonOnClickBehaviors(UIComponent component) {
      return getPassThruBehaviors(component, "click", "action");
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDBUTTON);
   }
}
