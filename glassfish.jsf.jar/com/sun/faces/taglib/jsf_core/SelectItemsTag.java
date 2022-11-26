package com.sun.faces.taglib.jsf_core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

public class SelectItemsTag extends UIComponentELTag {
   private ValueExpression value;
   private String var;
   private ValueExpression itemValue;
   private ValueExpression itemLabel;
   private ValueExpression itemDescription;
   private ValueExpression itemDisabled;
   private ValueExpression itemLabelEscaped;
   private ValueExpression noSelectionOption;

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setItemValue(ValueExpression itemValue) {
      this.itemValue = itemValue;
   }

   public void setItemLabel(ValueExpression itemLabel) {
      this.itemLabel = itemLabel;
   }

   public void setItemDescription(ValueExpression itemDescription) {
      this.itemDescription = itemDescription;
   }

   public void setItemDisabled(ValueExpression itemDisabled) {
      this.itemDisabled = itemDisabled;
   }

   public void setItemLabelEscaped(ValueExpression itemLabelEscaped) {
      this.itemLabelEscaped = itemLabelEscaped;
   }

   public void setNoSelectionOption(ValueExpression noSelectionOption) {
      this.noSelectionOption = noSelectionOption;
   }

   public String getRendererType() {
      return null;
   }

   public String getComponentType() {
      return "javax.faces.SelectItems";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      if (this.value != null) {
         component.setValueExpression("value", this.value);
      }

      if (this.var != null) {
         component.getAttributes().put("var", this.var);
      }

      if (this.itemValue != null) {
         component.setValueExpression("itemValue", this.itemValue);
      }

      if (this.itemLabel != null) {
         component.setValueExpression("itemLabel", this.itemLabel);
      }

      if (this.itemDescription != null) {
         component.setValueExpression("itemDescription", this.itemDescription);
      }

      if (this.itemDisabled != null) {
         component.setValueExpression("itemDisabled", this.itemDisabled);
      }

      if (this.itemLabelEscaped != null) {
         component.setValueExpression("itemLabelEscaped", this.itemLabelEscaped);
      }

      if (this.noSelectionOption != null) {
         component.setValueExpression("noSelectionOption", this.noSelectionOption);
      }

   }
}
