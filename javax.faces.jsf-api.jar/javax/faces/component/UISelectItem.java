package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UISelectItem extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.SelectItem";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectItem";
   private String itemDescription = null;
   private Boolean itemDisabled;
   private Boolean itemEscaped;
   private String itemLabel = null;
   private Object itemValue = null;
   private Object value = null;
   private Object[] values;

   public UISelectItem() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.SelectItem";
   }

   public String getItemDescription() {
      if (this.itemDescription != null) {
         return this.itemDescription;
      } else {
         ValueExpression ve = this.getValueExpression("itemDescription");
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

   public void setItemDescription(String itemDescription) {
      this.itemDescription = itemDescription;
   }

   public boolean isItemDisabled() {
      if (this.itemDisabled != null) {
         return this.itemDisabled;
      } else {
         ValueExpression ve = this.getValueExpression("itemDisabled");
         if (ve != null) {
            try {
               return Boolean.TRUE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return false;
         }
      }
   }

   public void setItemDisabled(boolean itemDisabled) {
      this.itemDisabled = itemDisabled;
   }

   public boolean isItemEscaped() {
      if (this.itemEscaped != null) {
         return this.itemEscaped;
      } else {
         ValueExpression ve = this.getValueExpression("itemEscaped");
         if (ve != null) {
            try {
               return Boolean.TRUE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return true;
         }
      }
   }

   public void setItemEscaped(boolean itemEscaped) {
      this.itemEscaped = itemEscaped;
   }

   public String getItemLabel() {
      if (this.itemLabel != null) {
         return this.itemLabel;
      } else {
         ValueExpression ve = this.getValueExpression("itemLabel");
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

   public void setItemLabel(String itemLabel) {
      this.itemLabel = itemLabel;
   }

   public Object getItemValue() {
      if (this.itemValue != null) {
         return this.itemValue;
      } else {
         ValueExpression ve = this.getValueExpression("itemValue");
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

   public void setItemValue(Object itemValue) {
      this.itemValue = itemValue;
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
         this.values = new Object[7];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.itemDescription;
      this.values[2] = this.itemDisabled;
      this.values[3] = this.itemEscaped;
      this.values[4] = this.itemLabel;
      this.values[5] = this.itemValue;
      this.values[6] = this.value;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.itemDescription = (String)this.values[1];
      this.itemDisabled = (Boolean)this.values[2];
      this.itemEscaped = (Boolean)this.values[3];
      this.itemLabel = (String)this.values[4];
      this.itemValue = this.values[5];
      this.value = this.values[6];
   }
}
