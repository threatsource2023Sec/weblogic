package javax.faces.component;

import javax.el.ValueExpression;
import javax.faces.el.ValueBinding;

public class UIGraphic extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Graphic";
   public static final String COMPONENT_FAMILY = "javax.faces.Graphic";

   public UIGraphic() {
      this.setRendererType("javax.faces.Image");
   }

   public String getFamily() {
      return "javax.faces.Graphic";
   }

   public String getUrl() {
      return (String)this.getValue();
   }

   public void setUrl(String url) {
      this.setValue(url);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UIGraphic.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.getStateHelper().put(UIGraphic.PropertyKeys.value, value);
   }

   /** @deprecated */
   public ValueBinding getValueBinding(String name) {
      return "url".equals(name) ? super.getValueBinding("value") : super.getValueBinding(name);
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if ("url".equals(name)) {
         super.setValueBinding("value", binding);
      } else {
         super.setValueBinding(name, binding);
      }

   }

   public ValueExpression getValueExpression(String name) {
      return "url".equals(name) ? super.getValueExpression("value") : super.getValueExpression(name);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if ("url".equals(name)) {
         super.setValueExpression("value", binding);
      } else {
         super.setValueExpression(name, binding);
      }

   }

   static enum PropertyKeys {
      value;
   }
}
