package com.sun.faces.taglib.jsf_core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.webapp.UIComponentELTag;

public class SelectItemTag extends UIComponentELTag {
   protected ValueExpression itemValue;
   protected ValueExpression itemLabel;
   protected ValueExpression itemDescription;
   protected ValueExpression itemDisabled;
   protected ValueExpression noSelectionOption = null;
   protected ValueExpression value;
   private ValueExpression escape;

   public void setItemValue(ValueExpression value) {
      this.itemValue = value;
   }

   public void setItemLabel(ValueExpression label) {
      this.itemLabel = label;
   }

   public void setItemDescription(ValueExpression itemDescription) {
      this.itemDescription = itemDescription;
   }

   public void setItemDisabled(ValueExpression itemDisabled) {
      this.itemDisabled = itemDisabled;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setNoSelectionOption(ValueExpression noSelectionOption) {
      this.noSelectionOption = noSelectionOption;
   }

   public String getRendererType() {
      return null;
   }

   public String getComponentType() {
      return "javax.faces.SelectItem";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UISelectItem selectItem = (UISelectItem)component;
      if (null != this.value) {
         if (!this.value.isLiteralText()) {
            selectItem.setValueExpression("value", this.value);
         } else {
            selectItem.setValue(this.value.getExpressionString());
         }
      }

      if (null != this.itemValue) {
         if (!this.itemValue.isLiteralText()) {
            selectItem.setValueExpression("itemValue", this.itemValue);
         } else {
            selectItem.setItemValue(this.itemValue.getExpressionString());
         }
      }

      if (null != this.itemLabel) {
         if (!this.itemLabel.isLiteralText()) {
            selectItem.setValueExpression("itemLabel", this.itemLabel);
         } else {
            selectItem.setItemLabel(this.itemLabel.getExpressionString());
         }
      }

      if (null != this.itemDescription) {
         if (!this.itemDescription.isLiteralText()) {
            selectItem.setValueExpression("itemDescription", this.itemDescription);
         } else {
            selectItem.setItemDescription(this.itemDescription.getExpressionString());
         }
      }

      if (null != this.itemDisabled) {
         if (!this.itemDisabled.isLiteralText()) {
            selectItem.setValueExpression("itemDisabled", this.itemDisabled);
         } else {
            selectItem.setItemDisabled(Boolean.valueOf(this.itemDisabled.getExpressionString()));
         }
      }

      if (null != this.noSelectionOption) {
         if (!this.noSelectionOption.isLiteralText()) {
            selectItem.setValueExpression("noSelectionOption", this.noSelectionOption);
         } else {
            selectItem.setNoSelectionOption(Boolean.valueOf(this.noSelectionOption.getExpressionString()));
         }
      }

      if (null != this.escape) {
         if (!this.escape.isLiteralText()) {
            selectItem.setValueExpression("escape", this.escape);
         } else {
            selectItem.setItemEscaped(Boolean.valueOf(this.escape.getExpressionString()));
         }
      }

   }

   public ValueExpression getEscape() {
      return this.escape;
   }

   public void setEscape(ValueExpression escape) {
      this.escape = escape;
   }
}
