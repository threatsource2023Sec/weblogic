package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.List;
import javax.faces.application.NavigationCase;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class OutcomeTargetButtonRenderer extends OutcomeTargetRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         writer.startElement("input", component);
         this.writeIdAttributeIfNecessary(context, writer, component);
         String imageSrc = (String)component.getAttributes().get("image");
         if (imageSrc != null) {
            writer.writeAttribute("type", "image", "type");
            writer.writeURIAttribute("src", RenderKitUtils.getImageSource(context, component, "image"), "image");
         } else {
            writer.writeAttribute("type", "button", "type");
         }

         String label = this.getLabel(component);
         if (!Util.componentIsDisabled(component)) {
            NavigationCase navCase = this.getNavigationCase(context, component);
            if (navCase == null) {
               label = label + MessageUtils.getExceptionMessageString("com.sun.faces.OUTCOME_TARGET_BUTTON_NO_MATCH");
               writer.writeAttribute("disabled", "true", "disabled");
            } else {
               String hrefVal = this.getEncodedTargetURL(context, component, navCase);
               hrefVal = hrefVal + this.getFragment(component);
               writer.writeAttribute("onclick", this.getOnclick(component, hrefVal), "onclick");
            }
         }

         writer.writeAttribute("value", label, "value");
         String styleClass = (String)component.getAttributes().get("styleClass");
         if (styleClass != null && styleClass.length() > 0) {
            writer.writeAttribute("class", styleClass, "styleClass");
         }

         this.renderPassThruAttributes(context, writer, component, ATTRIBUTES, (List)null);
         if (component.getChildCount() == 0) {
            writer.endElement("input");
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (component.getChildCount() > 0) {
         context.getResponseWriter().endElement("input");
      }

   }

   protected String getOnclick(UIComponent component, String targetURI) {
      String onclick = (String)component.getAttributes().get("onclick");
      if (onclick != null) {
         onclick = onclick.trim();
         if (onclick.length() > 0 && !onclick.endsWith(";")) {
            onclick = onclick + "; ";
         }
      } else {
         onclick = "";
      }

      if (targetURI != null) {
         onclick = onclick + "window.location.href='" + targetURI + "'; ";
      }

      onclick = onclick + "return false;";
      return onclick;
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.COMMANDBUTTON);
   }
}
