package com.sun.faces.renderkit.html_basic;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class FormRenderer extends HtmlBasicRenderer {
   private static final Attribute[] ATTRIBUTES;
   private boolean writeStateAtEnd;

   public FormRenderer() {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      this.writeStateAtEnd = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.WriteStateAtFormEnd);
   }

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      String clientId = this.decodeBehaviors(context, component);
      if (clientId == null) {
         clientId = component.getClientId(context);
      }

      Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
      if (requestParameterMap.containsKey(clientId)) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "UIForm with client ID {0}, submitted", clientId);
         }

         ((UIForm)component).setSubmitted(true);
      } else {
         ((UIForm)component).setSubmitted(false);
      }

   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String clientId = component.getClientId(context);
         writer.write(10);
         writer.startElement("form", component);
         writer.writeAttribute("id", clientId, "clientId");
         writer.writeAttribute("name", clientId, "name");
         writer.writeAttribute("method", "post", (String)null);
         writer.writeAttribute("action", getActionStr(context), (String)null);
         String styleClass = (String)component.getAttributes().get("styleClass");
         if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         String acceptcharset = (String)component.getAttributes().get("acceptcharset");
         if (acceptcharset != null) {
            writer.writeAttribute("accept-charset", acceptcharset, "acceptcharset");
         }

         RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
         writer.writeText("\n", component, (String)null);
         writer.startElement("input", (UIComponent)null);
         writer.writeAttribute("type", "hidden", "type");
         writer.writeAttribute("name", clientId, "clientId");
         writer.writeAttribute("value", clientId, "value");
         writer.endElement("input");
         writer.write(10);
         UIViewRoot viewRoot = context.getViewRoot();
         String viewId = viewRoot.getViewId();
         String actionURL = context.getApplication().getViewHandler().getActionURL(context, viewId);
         ExternalContext externalContext = context.getExternalContext();
         String encodedActionURL = externalContext.encodeActionURL(actionURL);
         String encodedPartialActionURL = externalContext.encodePartialActionURL(actionURL);
         if (encodedPartialActionURL != null && !encodedPartialActionURL.equals(encodedActionURL)) {
            writer.startElement("input", (UIComponent)null);
            writer.writeAttribute("type", "hidden", "type");
            writer.writeAttribute("name", RenderKitUtils.getParameterName(context, "javax.faces.encodedURL"), (String)null);
            writer.writeAttribute("value", encodedPartialActionURL, "value");
            writer.endElement("input");
            writer.write(10);
         }

         if (!this.writeStateAtEnd) {
            context.getApplication().getViewHandler().writeState(context);
            writer.write(10);
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         UIViewRoot viewRoot = context.getViewRoot();
         ListIterator iter = viewRoot.getComponentResources(context, "form").listIterator();

         while(iter.hasNext()) {
            UIComponent resource = (UIComponent)iter.next();
            resource.encodeAll(context);
         }

         if (this.writeStateAtEnd) {
            context.getApplication().getViewHandler().writeState(context);
         }

         writer.writeText("\n", component, (String)null);
         writer.endElement("form");
      }
   }

   private static String getActionStr(FacesContext context) {
      String viewId = context.getViewRoot().getViewId();
      String actionURL = context.getApplication().getViewHandler().getActionURL(context, viewId);
      return context.getExternalContext().encodeActionURL(actionURL);
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.FORMFORM);
   }
}
