package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

public class CommandLinkRenderer extends LinkRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = this.decodeBehaviors(context, component);
         if (wasClicked(context, component, clientId)) {
            component.queueEvent(new ActionEvent(context, component));
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("This commandLink resulted in form submission  ActionEvent queued.");
            }
         }

      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         boolean componentDisabled = Boolean.TRUE.equals(component.getAttributes().get("disabled"));
         if (componentDisabled) {
            this.renderAsDisabled(context, component);
         } else {
            RenderKitUtils.renderJsfJsIfNecessary(context);
            this.renderAsActive(context, component);
         }

      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         if (component.getChildCount() > 0) {
            Iterator var3 = component.getChildren().iterator();

            while(var3.hasNext()) {
               UIComponent kid = (UIComponent)var3.next();
               this.encodeRecursive(context, kid);
            }
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         if (Boolean.TRUE.equals(component.getAttributes().get("disabled"))) {
            writer.endElement("span");
         } else {
            writer.endElement("a");
         }

      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected Object getValue(UIComponent component) {
      return ((UICommand)component).getValue();
   }

   protected void renderAsActive(FacesContext context, UIComponent command) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("a", command);
      this.writeIdAttributeIfNecessary(context, writer, command);
      writer.writeAttribute("href", "#", "href");
      RenderKitUtils.renderPassThruAttributes(context, writer, command, ATTRIBUTES, getNonOnClickBehaviors(command));
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, command);
      String target = (String)command.getAttributes().get("target");
      if (target != null) {
         target = target.trim();
      } else {
         target = "";
      }

      Collection params = this.getBehaviorParameters(command);
      RenderKitUtils.renderOnclick(context, command, params, target, true);
      this.writeCommonLinkAttributes(writer, command);
      this.writeValue(command, writer);
      writer.flush();
   }

   private static boolean wasClicked(FacesContext context, UIComponent component, String clientId) {
      Map requestParamMap = context.getExternalContext().getRequestParameterMap();
      if (clientId == null) {
         clientId = component.getClientId(context);
      }

      return requestParamMap.containsKey(clientId) || RenderKitUtils.isPartialOrBehaviorAction(context, clientId);
   }

   private static Map getNonOnClickBehaviors(UIComponent component) {
      return getPassThruBehaviors(component, "click", "action");
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDLINK);
   }
}
