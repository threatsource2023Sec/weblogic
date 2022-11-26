package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class SelectManyCheckboxListRenderer extends MenuRenderer {
   private static final String[] ATTRIBUTES;

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         boolean alignVertical = false;
         int border = 0;
         String alignStr;
         if (null != (alignStr = (String)component.getAttributes().get("layout"))) {
            alignVertical = alignStr.equalsIgnoreCase("pageDirection");
         }

         Object borderObj;
         if (null != (borderObj = component.getAttributes().get("border"))) {
            border = (Integer)borderObj;
         }

         Converter converter = null;
         if (component instanceof ValueHolder) {
            converter = ((ValueHolder)component).getConverter();
         }

         this.renderBeginText(component, border, alignVertical, context, true);
         List items = RenderKitUtils.getSelectItems(context, component);
         if (!items.isEmpty()) {
            Object currentSelections = this.getCurrentSelectedValues(component);
            Object[] submittedValues = this.getSubmittedSelectedValues(component);
            Map attributes = component.getAttributes();
            HtmlBasicRenderer.OptionComponentInfo optionInfo = new HtmlBasicRenderer.OptionComponentInfo((String)attributes.get("disabledClass"), (String)attributes.get("enabledClass"), Util.componentIsDisabled(component));
            int idx = -1;
            Iterator i$ = items.iterator();

            label61:
            while(true) {
               while(true) {
                  if (!i$.hasNext()) {
                     break label61;
                  }

                  SelectItem curItem = (SelectItem)i$.next();
                  ++idx;
                  if (curItem instanceof SelectItemGroup) {
                     if (curItem.getLabel() != null) {
                        if (alignVertical) {
                           writer.startElement("tr", component);
                        }

                        writer.startElement("td", component);
                        writer.writeText(curItem.getLabel(), component, "label");
                        writer.endElement("td");
                        if (alignVertical) {
                           writer.endElement("tr");
                        }
                     }

                     if (alignVertical) {
                        writer.startElement("tr", component);
                     }

                     writer.startElement("td", component);
                     writer.writeText("\n", component, (String)null);
                     this.renderBeginText(component, 0, alignVertical, context, false);
                     SelectItem[] itemsArray = ((SelectItemGroup)curItem).getSelectItems();

                     for(int i = 0; i < itemsArray.length; ++i) {
                        this.renderOption(context, component, converter, itemsArray[i], currentSelections, submittedValues, alignVertical, i, optionInfo);
                     }

                     this.renderEndText(component, alignVertical, context);
                     writer.endElement("td");
                     if (alignVertical) {
                        writer.endElement("tr");
                        writer.writeText("\n", component, (String)null);
                     }
                  } else {
                     this.renderOption(context, component, converter, curItem, currentSelections, submittedValues, alignVertical, idx, optionInfo);
                  }
               }
            }
         }

         this.renderEndText(component, alignVertical, context);
      }
   }

   protected void renderBeginText(UIComponent component, int border, boolean alignVertical, FacesContext context, boolean outerTable) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("table", component);
      if (border != Integer.MIN_VALUE) {
         writer.writeAttribute("border", border, "border");
      }

      if (outerTable) {
         if (this.shouldWriteIdAttribute(component)) {
            this.writeIdAttributeIfNecessary(context, writer, component);
         }

         String styleClass = (String)component.getAttributes().get("styleClass");
         String style = (String)component.getAttributes().get("style");
         if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "class");
         }

         if (style != null) {
            writer.writeAttribute("style", style, "style");
         }
      }

      writer.writeText("\n", component, (String)null);
      if (!alignVertical) {
         writer.writeText("\t", component, (String)null);
         writer.startElement("tr", component);
         writer.writeText("\n", component, (String)null);
      }

   }

   protected void renderEndText(UIComponent component, boolean alignVertical, FacesContext context) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      if (!alignVertical) {
         writer.writeText("\t", component, (String)null);
         writer.endElement("tr");
         writer.writeText("\n", component, (String)null);
      }

      writer.endElement("table");
   }

   protected void renderOption(FacesContext context, UIComponent component, Converter converter, SelectItem curItem, Object currentSelections, Object[] submittedValues, boolean alignVertical, int itemNumber, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      String labelClass;
      if (!optionInfo.isDisabled() && !curItem.isDisabled()) {
         labelClass = optionInfo.getEnabledClass();
      } else {
         labelClass = optionInfo.getDisabledClass();
      }

      if (alignVertical) {
         writer.writeText("\t", component, (String)null);
         writer.startElement("tr", component);
         writer.writeText("\n", component, (String)null);
      }

      writer.startElement("td", component);
      writer.writeText("\n", component, (String)null);
      writer.startElement("input", component);
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      String idString = component.getClientId(context) + ':' + Integer.toString(itemNumber);
      writer.writeAttribute("id", idString, "id");
      String valueString = this.getFormattedValue(context, component, curItem.getValue(), converter);
      writer.writeAttribute("value", valueString, "value");
      writer.writeAttribute("type", "checkbox", (String)null);
      Object valuesArray;
      Object itemValue;
      if (submittedValues != null) {
         valuesArray = submittedValues;
         itemValue = valueString;
      } else {
         valuesArray = currentSelections;
         itemValue = curItem.getValue();
      }

      RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);
      if (this.isSelected(context, component, itemValue, valuesArray, converter)) {
         writer.writeAttribute(this.getSelectedTextString(), Boolean.TRUE, (String)null);
      }

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
      if (itemLabel == null) {
         itemLabel = valueString;
      }

      writer.writeText(" ", component, (String)null);
      if (!curItem.isEscape()) {
         writer.write(itemLabel);
      } else {
         writer.writeText(itemLabel, component, "label");
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

   String getSelectedTextString() {
      return "checked";
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYCHECKBOX);
   }
}
