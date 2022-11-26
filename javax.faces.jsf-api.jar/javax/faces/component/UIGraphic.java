package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class UIGraphic extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Graphic";
   public static final String COMPONENT_FAMILY = "javax.faces.Graphic";
   private Object value = null;
   private Object[] values;

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
      if (this.value != null) {
         return this.value;
      } else {
         ValueExpression ve = this.getValueExpression("value");
         if (ve != null) {
            try {
               return ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setValue(Object value) {
      this.value = value;
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

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[2];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.value;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.value = this.values[1];
   }
}
