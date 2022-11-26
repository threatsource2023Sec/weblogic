package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class SecretRenderer extends HtmlBasicInputRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      String redisplay = String.valueOf(component.getAttributes().get("redisplay"));
      if (redisplay == null || !redisplay.equals("true")) {
         currentValue = "";
      }

      writer.startElement("input", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("type", "password", "type");
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      String autoComplete = (String)component.getAttributes().get("autocomplete");
      if (autoComplete != null && "off".equals(autoComplete)) {
         writer.writeAttribute("autocomplete", "off", "autocomplete");
      }

      if (currentValue != null) {
         writer.writeAttribute("value", currentValue, "value");
      }

      RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnChangeBehaviors(component));
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      RenderKitUtils.renderOnchange(context, component, false);
      String styleClass;
      if (null != (styleClass = (String)component.getAttributes().get("styleClass"))) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      writer.endElement("input");
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTSECRET);
   }
}
