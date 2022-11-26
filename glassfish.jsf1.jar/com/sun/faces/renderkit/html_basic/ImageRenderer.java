package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class ImageRenderer extends HtmlBasicRenderer {
   private static final String[] ATTRIBUTES;

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
         writer.writeURIAttribute("src", src(context, component), "value");
         if (writer.getContentType().equals("application/xhtml+xml") && null == component.getAttributes().get("alt")) {
            writer.writeAttribute("alt", "", "alt");
         }

         RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
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

   private static String src(FacesContext context, UIComponent component) {
      String value = (String)((UIGraphic)component).getValue();
      if (value == null) {
         return "";
      } else {
         value = context.getApplication().getViewHandler().getResourceURL(context, value);
         return context.getExternalContext().encodeResourceURL(value);
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.GRAPHICIMAGE);
   }
}
