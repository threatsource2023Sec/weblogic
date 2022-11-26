package javax.faces.component;

public class UISelectItem extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.SelectItem";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectItem";

   public UISelectItem() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.SelectItem";
   }

   public String getItemDescription() {
      return (String)this.getStateHelper().eval(UISelectItem.PropertyKeys.itemDescription);
   }

   public void setItemDescription(String itemDescription) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.itemDescription, itemDescription);
   }

   public boolean isItemDisabled() {
      return (Boolean)this.getStateHelper().eval(UISelectItem.PropertyKeys.itemDisabled, false);
   }

   public void setItemDisabled(boolean itemDisabled) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.itemDisabled, itemDisabled);
   }

   public boolean isItemEscaped() {
      return (Boolean)this.getStateHelper().eval(UISelectItem.PropertyKeys.itemEscaped, true);
   }

   public void setItemEscaped(boolean itemEscaped) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.itemEscaped, itemEscaped);
   }

   public String getItemLabel() {
      return (String)this.getStateHelper().eval(UISelectItem.PropertyKeys.itemLabel);
   }

   public void setItemLabel(String itemLabel) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.itemLabel, itemLabel);
   }

   public Object getItemValue() {
      return this.getStateHelper().eval(UISelectItem.PropertyKeys.itemValue);
   }

   public void setItemValue(Object itemValue) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.itemValue, itemValue);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UISelectItem.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.value, value);
   }

   public boolean isNoSelectionOption() {
      return (Boolean)this.getStateHelper().eval(UISelectItem.PropertyKeys.noSelectionOption, false);
   }

   public void setNoSelectionOption(boolean noSelectionOption) {
      this.getStateHelper().put(UISelectItem.PropertyKeys.noSelectionOption, noSelectionOption);
   }

   static enum PropertyKeys {
      itemDescription,
      itemDisabled,
      itemEscaped,
      itemLabel,
      itemValue,
      value,
      noSelectionOption;
   }
}
