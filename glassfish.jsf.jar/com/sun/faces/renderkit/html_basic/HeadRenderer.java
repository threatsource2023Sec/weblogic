package com.sun.faces.renderkit.html_basic;

import com.sun.faces.config.FaceletsConfiguration;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

public class HeadRenderer extends Renderer {
   private static final Attribute[] HEAD_ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      writer.startElement("head", component);
      RenderKitUtils.renderPassThruAttributes(context, writer, component, HEAD_ATTRIBUTES);
      WebConfiguration webConfig = WebConfiguration.getInstance(context.getExternalContext());
      FaceletsConfiguration faceletsConfig = webConfig.getFaceletsConfiguration();
      if (faceletsConfig.isOutputHtml5Doctype(context.getViewRoot().getViewId())) {
         String clientId = component.getClientId(context);
         writer.writeAttribute("id", clientId, "clientId");
      }

   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      this.encodeHeadResources(context);
      writer.endElement("head");
   }

   private void encodeHeadResources(FacesContext context) throws IOException {
      UIViewRoot viewRoot = context.getViewRoot();
      Iterator var3 = viewRoot.getComponentResources(context, "head").iterator();

      while(var3.hasNext()) {
         UIComponent resource = (UIComponent)var3.next();
         resource.encodeAll(context);
      }

   }

   static {
      HEAD_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTHEAD);
   }
}
