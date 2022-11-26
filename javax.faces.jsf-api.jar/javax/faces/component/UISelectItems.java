package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UISelectItems extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.SelectItems";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectItems";
   private Object value = null;
   private Object[] values;

   public UISelectItems() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.SelectItems";
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
