package com.sun.faces.facelets.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

public class RepeatRenderer extends Renderer {
   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      if (component.getChildCount() > 0) {
         Map a = component.getAttributes();
         String tag = (String)a.get("alias.element");
         if (tag != null) {
            ResponseWriter out = context.getResponseWriter();
            out.startElement(tag, component);
            String[] attrs = (String[])((String[])a.get("alias.attributes"));
            if (attrs != null) {
               for(int i = 0; i < attrs.length; ++i) {
                  String attr = attrs[i];
                  if ("styleClass".equals(attr)) {
                     attr = "class";
                  }

                  out.writeAttribute(attr, a.get(attrs[i]), attrs[i]);
               }
            }
         }

         Iterator itr = component.getChildren().iterator();

         while(itr.hasNext()) {
            UIComponent c = (UIComponent)itr.next();
            c.encodeAll(context);
         }

         if (tag != null) {
            context.getResponseWriter().endElement(tag);
         }
      }

   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
   }

   public boolean getRendersChildren() {
      return true;
   }
}
