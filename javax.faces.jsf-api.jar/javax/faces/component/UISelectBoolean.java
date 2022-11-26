package javax.faces.component;

import javax.el.ValueExpression;
import javax.faces.el.ValueBinding;

public class UISelectBoolean extends UIInput {
   public static final String COMPONENT_TYPE = "javax.faces.SelectBoolean";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectBoolean";

   public UISelectBoolean() {
      this.setRendererType("javax.faces.Checkbox");
   }

   public String getFamily() {
      return "javax.faces.SelectBoolean";
   }

   public boolean isSelected() {
      Boolean value = (Boolean)this.getValue();
      return value != null ? value : false;
   }

   public void setSelected(boolean selected) {
      if (selected) {
         this.setValue(Boolean.TRUE);
      } else {
         this.setValue(Boolean.FALSE);
      }

   }

   /** @deprecated */
   public ValueBinding getValueBinding(String name) {
      return "selected".equals(name) ? super.getValueBinding("value") : super.getValueBinding(name);
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if ("selected".equals(name)) {
         super.setValueBinding("value", binding);
      } else {
         super.setValueBinding(name, binding);
      }

   }

   public ValueExpression getValueExpression(String name) {
      return "selected".equals(name) ? super.getValueExpression("value") : super.getValueExpression(name);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if ("selected".equals(name)) {
         super.setValueExpression("value", binding);
      } else {
         super.setValueExpression(name, binding);
      }

   }
}
