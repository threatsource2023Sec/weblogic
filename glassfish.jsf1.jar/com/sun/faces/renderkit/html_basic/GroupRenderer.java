package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class GroupRenderer extends HtmlBasicRenderer {
   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         String style = (String)component.getAttributes().get("style");
         String styleClass = (String)component.getAttributes().get("styleClass");
         ResponseWriter writer = context.getResponseWriter();
         if (this.divOrSpan(component)) {
            if ("block".equals(component.getAttributes().get("layout"))) {
               writer.startElement("div", component);
            } else {
               writer.startElement("span", component);
            }

            this.writeIdAttributeIfNecessary(context, writer, component);
            if (styleClass != null) {
               writer.writeAttribute("class", styleClass, "styleClass");
            }

            if (style != null) {
               writer.writeAttribute("style", style, "style");
            }
         }

      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         Iterator kids = this.getChildren(component);

         while(kids.hasNext()) {
            this.encodeRecursive(context, (UIComponent)kids.next());
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();
         if (this.divOrSpan(component)) {
            if ("block".equals(component.getAttributes().get("layout"))) {
               writer.endElement("div");
            } else {
               writer.endElement("span");
            }
         }

      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   private boolean divOrSpan(UIComponent component) {
      return this.shouldWriteIdAttribute(component) || component.getAttributes().get("style") != null || component.getAttributes().get("styleClass") != null;
   }
}
