package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UIParameter extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Parameter";
   public static final String COMPONENT_FAMILY = "javax.faces.Parameter";
   private String name = null;
   private Object value = null;
   private Object[] values;

   public UIParameter() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.Parameter";
   }

   public String getName() {
      if (this.name != null) {
         return this.name;
      } else {
         ValueExpression ve = this.getValueExpression("name");
         if (ve != null) {
            try {
               return (String)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setName(String name) {
      this.name = name;
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
         this.values = new Object[3];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.name;
      this.values[2] = this.value;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.name = (String)this.values[1];
      this.value = this.values[2];
   }
}
