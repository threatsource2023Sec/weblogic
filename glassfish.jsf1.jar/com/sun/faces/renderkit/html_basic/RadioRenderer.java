package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.RequestStateManager;
import java.io.IOException;
import javax.el.ELException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class RadioRenderer extends SelectManyCheckboxListRenderer {
   private static final String[] ATTRIBUTES;

   protected void renderOption(FacesContext context, UIComponent component, Converter converter, SelectItem curItem, Object currentSelections, Object[] submittedValues, boolean alignVertical, int itemNumber, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      UISelectOne selectOne = (UISelectOne)component;
      Object curValue = selectOne.getSubmittedValue();
      if (curValue == null) {
         curValue = selectOne.getValue();
      }

      if (alignVertical) {
         writer.writeText("\t", component, (String)null);
         writer.startElement("tr", component);
         writer.writeText("\n", component, (String)null);
      }

      Class type = String.class;
      if (curValue != null) {
         type = curValue.getClass();
      }

      Object itemValue = curItem.getValue();
      RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);

      Object newValue;
      try {
         newValue = context.getApplication().getExpressionFactory().coerceToType(itemValue, type);
      } catch (ELException var19) {
         newValue = itemValue;
      } catch (IllegalArgumentException var20) {
         newValue = itemValue;
      }

      String labelClass;
      if (!optionInfo.isDisabled() && !curItem.isDisabled()) {
         labelClass = optionInfo.getEnabledClass();
      } else {
         labelClass = optionInfo.getDisabledClass();
      }

      writer.startElement("td", component);
      writer.writeText("\n", component, (String)null);
      writer.startElement("input", component);
      writer.writeAttribute("type", "radio", "type");
      if (null != newValue && newValue.equals(curValue)) {
         writer.writeAttribute("checked", Boolean.TRUE, (String)null);
      }

      writer.writeAttribute("name", component.getClientId(context), "clientId");
      String idString = component.getClientId(context) + ':' + Integer.toString(itemNumber);
      writer.writeAttribute("id", idString, "id");
      writer.writeAttribute("value", this.getFormattedValue(context, component, curItem.getValue(), converter), "value");
      if (!optionInfo.isDisabled() && curItem.isDisabled()) {
         writer.writeAttribute("disabled", true, "disabled");
      }

      RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      writer.endElement("input");
      writer.startElement("label", component);
      writer.writeAttribute("for", idString, "for");
      if (labelClass != null) {
         writer.writeAttribute("class", labelClass, "labelClass");
      }

      String itemLabel = curItem.getLabel();
      if (itemLabel != null) {
         writer.writeText(" ", component, (String)null);
         if (!curItem.isEscape()) {
            writer.write(itemLabel);
         } else {
            writer.writeText(itemLabel, component, "label");
         }
      }

      writer.endElement("label");
      writer.endElement("td");
      writer.writeText("\n", component, (String)null);
      if (alignVertical) {
         writer.writeText("\t", component, (String)null);
         writer.endElement("tr");
         writer.writeText("\n", component, (String)null);
      }

   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTONERADIO);
   }
}
