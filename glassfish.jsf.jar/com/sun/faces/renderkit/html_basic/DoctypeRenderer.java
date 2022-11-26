package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

public class DoctypeRenderer extends Renderer {
   private static final Attribute[] DOCTYPE_ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      Map attrs = component.getAttributes();
      writer.append("<!DOCTYPE ");
      writer.append(attrs.get("rootElement").toString());
      if (attrs.containsKey("public")) {
         writer.append(" PUBLIC \"").append((String)attrs.get("public")).append("\"");
      }

      if (attrs.containsKey("system")) {
         writer.append(" \"").append((String)attrs.get("system")).append("\"");
      }

      writer.append(">");
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      context.getResponseWriter();
   }

   static {
      DOCTYPE_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTDOCTYPE);
   }
}
