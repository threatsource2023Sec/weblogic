package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.RequestStateManager;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class SelectManyCheckboxListRenderer extends MenuRenderer {
   private static final Attribute[] ATTRIBUTES;

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
         Iterator items = RenderKitUtils.getSelectItems(context, component);
         Object currentSelections = this.getCurrentSelectedValues(component);
         Object[] submittedValues = this.getSubmittedSelectedValues(component);
         HtmlBasicRenderer.OptionComponentInfo optionInfo = new HtmlBasicRenderer.OptionComponentInfo(component);
         int idx = -1;

         while(true) {
            while(items.hasNext()) {
               SelectItem curItem = (SelectItem)items.next();
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
                  SelectItem[] var16 = itemsArray;
                  int var17 = itemsArray.length;

                  for(int var18 = 0; var18 < var17; ++var18) {
                     SelectItem element = var16[var18];
                     this.renderOption(context, component, converter, element, currentSelections, submittedValues, alignVertical, idx++, optionInfo);
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

            this.renderEndText(component, alignVertical, context);
            return;
         }
      }
   }

   protected boolean isBehaviorSource(FacesContext ctx, String behaviorSourceId, String componentClientId) {
      if (behaviorSourceId == null) {
         return false;
      } else {
         char sepChar = UINamingContainer.getSeparatorChar(ctx);
         String actualBehaviorId;
         if (behaviorSourceId.lastIndexOf(sepChar) != -1) {
            actualBehaviorId = behaviorSourceId.substring(0, behaviorSourceId.lastIndexOf(sepChar));
         } else {
            actualBehaviorId = behaviorSourceId;
         }

         return actualBehaviorId.equals(componentClientId);
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
      String valueString = this.getFormattedValue(context, component, curItem.getValue(), converter);
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
      boolean isSelected = this.isSelected(context, component, itemValue, valuesArray, converter);
      if (!optionInfo.isHideNoSelection() || !curItem.isNoSelectionOption() || currentSelections == null || isSelected) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         if (alignVertical) {
            writer.writeText("\t", component, (String)null);
            writer.startElement("tr", component);
            writer.writeText("\n", component, (String)null);
         }

         writer.startElement("td", component);
         writer.writeText("\n", component, (String)null);
         writer.startElement("input", component);
         writer.writeAttribute("name", component.getClientId(context), "clientId");
         String idString = component.getClientId(context) + UINamingContainer.getSeparatorChar(context) + Integer.toString(itemNumber);
         writer.writeAttribute("id", idString, "id");
         writer.writeAttribute("value", valueString, "value");
         writer.writeAttribute("type", "checkbox", (String)null);
         if (isSelected) {
            writer.writeAttribute(this.getSelectedTextString(), Boolean.TRUE, (String)null);
         }

         if (!optionInfo.isDisabled() && curItem.isDisabled()) {
            writer.writeAttribute("disabled", true, "disabled");
         }

         RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnClickSelectBehaviors(component));
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         RenderKitUtils.renderSelectOnclick(context, component, false);
         writer.endElement("input");
         writer.startElement("label", component);
         writer.writeAttribute("for", idString, "for");
         StringBuilder labelClass = new StringBuilder();
         String style;
         if (!optionInfo.isDisabled() && !curItem.isDisabled()) {
            style = optionInfo.getEnabledClass();
         } else {
            style = optionInfo.getDisabledClass();
         }

         if (style != null) {
            labelClass.append(style);
         }

         if (this.isSelected(context, component, itemValue, valuesArray, converter)) {
            style = optionInfo.getSelectedClass();
         } else {
            style = optionInfo.getUnselectedClass();
         }

         if (style != null) {
            if (labelClass.length() > 0) {
               labelClass.append(' ');
            }

            labelClass.append(style);
         }

         writer.writeAttribute("class", labelClass.toString(), "labelClass");
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
   }

   String getSelectedTextString() {
      return "checked";
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYCHECKBOX);
   }
}
