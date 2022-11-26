package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

public class CheckboxRenderer extends HtmlBasicInputRenderer {
   private static final String[] ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = component.getClientId(context);

         assert clientId != null;

         Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
         boolean isChecked = isChecked((String)requestParameterMap.get(clientId));
         this.setSubmittedValue(component, isChecked);
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "new value after decoding: {0}", isChecked);
         }

      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      return submittedValue instanceof Boolean ? submittedValue : Boolean.valueOf(submittedValue.toString());
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("input", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("type", "checkbox", "type");
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      if (Boolean.valueOf(currentValue)) {
         writer.writeAttribute("checked", Boolean.TRUE, "value");
      }

      String styleClass;
      if (null != (styleClass = (String)component.getAttributes().get("styleClass"))) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      writer.endElement("input");
   }

   private static boolean isChecked(String value) {
      return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTBOOLEANCHECKBOX);
   }
}
