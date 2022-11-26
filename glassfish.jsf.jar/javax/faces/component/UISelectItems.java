package javax.faces.component;

public class UISelectItems extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.SelectItems";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectItems";

   public UISelectItems() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.SelectItems";
   }

   public Object getValue() {
      return this.getStateHelper().eval(UISelectItems.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UISelectItems.PropertyKeys.value, value);
   }

   static enum PropertyKeys {
      value;
   }
}
