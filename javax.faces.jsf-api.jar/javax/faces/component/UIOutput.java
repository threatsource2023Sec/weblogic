package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class UIOutput extends UIComponentBase implements ValueHolder {
   public static final String COMPONENT_TYPE = "javax.faces.Output";
   public static final String COMPONENT_FAMILY = "javax.faces.Output";
   private Converter converter = null;
   private Object value = null;
   private Object[] values;

   public UIOutput() {
      this.setRendererType("javax.faces.Text");
   }

   public String getFamily() {
      return "javax.faces.Output";
   }

   public Converter getConverter() {
      if (this.converter != null) {
         return this.converter;
      } else {
         ValueExpression ve = this.getValueExpression("converter");
         if (ve != null) {
            try {
               return (Converter)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setConverter(Converter converter) {
      this.converter = converter;
   }

   public Object getLocalValue() {
      return this.value;
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
      this.values[1] = saveAttachedState(context, this.converter);
      this.values[2] = this.value;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.converter = (Converter)restoreAttachedState(context, this.values[1]);
      this.value = this.values[2];
   }
}
