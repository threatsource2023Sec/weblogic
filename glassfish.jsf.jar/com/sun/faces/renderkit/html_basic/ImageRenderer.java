package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class ImageRenderer extends HtmlBasicRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         writer.startElement("img", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         writer.writeURIAttribute("src", RenderKitUtils.getImageSource(context, component, "value"), "value");
         if (writer.getContentType().equals("application/xhtml+xml") && null == component.getAttributes().get("alt")) {
            writer.writeAttribute("alt", "", "alt");
         }

         RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         String styleClass;
         if (null != (styleClass = (String)component.getAttributes().get("styleClass"))) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         writer.endElement("img");
         if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "End encoding component " + component.getId());
         }

      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.GRAPHICIMAGE);
   }
}
