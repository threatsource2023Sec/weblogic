package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class PassthroughRenderer extends HtmlBasicRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         Map attrs = component.getPassThroughAttributes();
         String localName = (String)attrs.get("elementName");
         if (null == localName) {
            String clientId = component.getClientId(context);
            throw new FacesException("Unable to determine localName for component with clientId " + clientId);
         } else {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement(localName, component);
            this.writeIdAttributeIfNecessary(context, writer, component);
            RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
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
         Map attrs = component.getPassThroughAttributes();
         String localName = (String)attrs.get("elementName");
         context.getResponseWriter().endElement(localName);
      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.PANELGROUP);
   }
}
