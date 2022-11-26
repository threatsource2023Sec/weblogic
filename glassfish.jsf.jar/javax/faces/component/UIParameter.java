package javax.faces.component;

public class UIParameter extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Parameter";
   public static final String COMPONENT_FAMILY = "javax.faces.Parameter";

   public UIParameter() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.Parameter";
   }

   public String getName() {
      return (String)this.getStateHelper().eval(UIParameter.PropertyKeys.name);
   }

   public void setName(String name) {
      this.getStateHelper().put(UIParameter.PropertyKeys.name, name);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UIParameter.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UIParameter.PropertyKeys.value, value);
   }

   public boolean isDisable() {
      return (Boolean)this.getStateHelper().eval(UIParameter.PropertyKeys.disable, false);
   }

   public void setDisable(boolean disable) {
      this.getStateHelper().put(UIParameter.PropertyKeys.disable, disable);
   }

   static enum PropertyKeys {
      name,
      value,
      disable;
   }
}
