package javax.faces.component;

import javax.el.ValueExpression;

public class UIImportConstants extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.ImportConstants";
   public static final String COMPONENT_FAMILY = "javax.faces.ImportConstants";

   public UIImportConstants() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.ImportConstants";
   }

   public String getType() {
      return (String)this.getStateHelper().eval(UIImportConstants.PropertyKeys.type);
   }

   public void setType(String type) {
      this.getStateHelper().put(UIImportConstants.PropertyKeys.type, type);
   }

   public String getVar() {
      return (String)this.getStateHelper().eval(UIImportConstants.PropertyKeys.var);
   }

   public void setVar(String var) {
      this.getStateHelper().put(UIImportConstants.PropertyKeys.var, var);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (UIImportConstants.PropertyKeys.var.toString().equals(name)) {
         throw new IllegalArgumentException(name);
      } else {
         super.setValueExpression(name, binding);
      }
   }

   static enum PropertyKeys {
      type,
      var;
   }
}
