package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

public class CommandLinkRenderer extends LinkRenderer {
   private static final String[] ATTRIBUTES;
   private static final String SCRIPT_STATE = "com.sun.faces.scriptState";

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         if (wasClicked(context, component)) {
            component.queueEvent(new ActionEvent(component));
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
         String formClientId = getFormClientId(component, context);
         if (formClientId == null && logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "Component {0} must be enclosed inside a form", component.getId());
         }

         if (!componentDisabled && formClientId != null) {
            if (!hasScriptBeenRendered(context)) {
               RenderKitUtils.renderFormInitScript(context.getResponseWriter(), context);
               setScriptAsRendered(context);
            }

            this.renderAsActive(context, component);
         } else {
            this.renderAsDisabled(context, component);
         }

      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         if (component.getChildCount() > 0) {
            Iterator i$ = component.getChildren().iterator();

            while(i$.hasNext()) {
               UIComponent kid = (UIComponent)i$.next();
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

         String formClientId = getFormClientId(component, context);
         if (formClientId == null) {
            writer.write(MessageUtils.getExceptionMessageString("com.sun.faces.COMMAND_LINK_NO_FORM_MESSAGE"));
            writer.endElement("span");
         } else {
            if (Boolean.TRUE.equals(component.getAttributes().get("disabled"))) {
               writer.endElement("span");
            } else {
               writer.endElement("a");
            }

         }
      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected Object getValue(UIComponent component) {
      return ((UICommand)component).getValue();
   }

   protected String getOnClickScript(String formClientId, String commandClientId, String target, HtmlBasicRenderer.Param[] params) {
      return RenderKitUtils.getCommandLinkOnClickScript(formClientId, commandClientId, target, params);
   }

   protected void renderAsActive(FacesContext context, UIComponent command) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      String formClientId = getFormClientId(command, context);
      if (formClientId != null) {
         writer.startElement("a", command);
         this.writeIdAttributeIfNecessary(context, writer, command);
         writer.writeAttribute("href", "#", "href");
         RenderKitUtils.renderPassThruAttributes(writer, command, ATTRIBUTES);
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, command);
         String userOnclick = (String)command.getAttributes().get("onclick");
         StringBuffer sb = new StringBuffer(128);
         boolean userSpecifiedOnclick = userOnclick != null && !"".equals(userOnclick);
         if (userSpecifiedOnclick) {
            sb.append("var a=function(){");
            userOnclick = userOnclick.trim();
            sb.append(userOnclick);
            if (userOnclick.charAt(userOnclick.length() - 1) != ';') {
               sb.append(';');
            }

            sb.append("};var b=function(){");
         }

         HtmlBasicRenderer.Param[] params = this.getParamList(command);
         String commandClientId = command.getClientId(context);
         String target = (String)command.getAttributes().get("target");
         if (target != null) {
            target = target.trim();
         } else {
            target = "";
         }

         sb.append(this.getOnClickScript(formClientId, commandClientId, target, params));
         if (userSpecifiedOnclick) {
            sb.append("};return (a()==false) ? false : b();");
         }

         writer.writeAttribute("onclick", sb.toString(), "onclick");
         this.writeCommonLinkAttributes(writer, command);
         this.writeValue(command, writer);
         writer.flush();
      }
   }

   private static boolean hasScriptBeenRendered(FacesContext context) {
      return context.getExternalContext().getRequestMap().get("com.sun.faces.scriptState") != null;
   }

   private static void setScriptAsRendered(FacesContext context) {
      context.getExternalContext().getRequestMap().put("com.sun.faces.scriptState", Boolean.TRUE);
   }

   private static String getFormClientId(UIComponent component, FacesContext context) {
      UIForm form = getMyForm(component);
      return form != null ? form.getClientId(context) : null;
   }

   private static UIForm getMyForm(UIComponent component) {
      UIComponent parent;
      for(parent = component.getParent(); parent != null && !(parent instanceof UIForm); parent = parent.getParent()) {
      }

      return (UIForm)parent;
   }

   private static boolean wasClicked(FacesContext context, UIComponent component) {
      Map requestParamMap = context.getExternalContext().getRequestParameterMap();
      return requestParamMap.containsKey(component.getClientId(context));
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDLINK);
   }
}
