package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public abstract class LinkRenderer extends HtmlBasicRenderer {
   private static final String[] ATTRIBUTES;

   protected abstract void renderAsActive(FacesContext var1, UIComponent var2) throws IOException;

   protected void renderAsDisabled(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("span", component);
      String writtenId = this.writeIdAttributeIfNecessary(context, writer, component);
      if (null != writtenId) {
         writer.writeAttribute("name", writtenId, "name");
      }

      RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
      this.writeCommonLinkAttributes(writer, component);
      this.writeValue(component, writer);
      writer.flush();
   }

   protected void writeCommonLinkAttributes(ResponseWriter writer, UIComponent component) throws IOException {
      String styleClass = (String)component.getAttributes().get("styleClass");
      if (styleClass != null) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

   }

   protected void writeValue(UIComponent component, ResponseWriter writer) throws IOException {
      Object v = this.getValue(component);
      String label = null;
      if (v != null) {
         label = v.toString();
      }

      if (label != null && label.length() != 0) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("Value to be rendered " + label);
         }

         writer.writeText(label, component, (String)null);
      }

   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDLINK);
   }
}
