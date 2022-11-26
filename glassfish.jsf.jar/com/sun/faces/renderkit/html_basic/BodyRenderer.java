package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.ListIterator;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class BodyRenderer extends HtmlBasicRenderer {
   private static final Attribute[] BODY_ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      writer.startElement("body", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      String styleClass = (String)component.getAttributes().get("styleClass");
      if (styleClass != null && styleClass.length() != 0) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      RenderKitUtils.renderPassThruAttributes(context, writer, component, BODY_ATTRIBUTES);
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      UIViewRoot viewRoot = context.getViewRoot();
      ListIterator iter = viewRoot.getComponentResources(context, "body").listIterator();

      while(iter.hasNext()) {
         UIComponent resource = (UIComponent)iter.next();
         resource.encodeAll(context);
      }

      RenderKitUtils.renderUnhandledMessages(context);
      writer.endElement("body");
   }

   public boolean getRendersChildren() {
      return false;
   }

   static {
      BODY_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTBODY);
   }
}
