package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UIMessages extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Messages";
   public static final String COMPONENT_FAMILY = "javax.faces.Messages";
   private Boolean globalOnly;
   private Boolean showDetail;
   private Boolean showSummary;
   private Object[] values;

   public UIMessages() {
      this.setRendererType("javax.faces.Messages");
   }

   public String getFamily() {
      return "javax.faces.Messages";
   }

   public boolean isGlobalOnly() {
      if (this.globalOnly != null) {
         return this.globalOnly;
      } else {
         ValueExpression ve = this.getValueExpression("globalOnly");
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

   public void setGlobalOnly(boolean globalOnly) {
      this.globalOnly = globalOnly;
   }

   public boolean isShowDetail() {
      if (this.showDetail != null) {
         return this.showDetail;
      } else {
         ValueExpression ve = this.getValueExpression("showDetail");
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

   public void setShowDetail(boolean showDetail) {
      this.showDetail = showDetail;
   }

   public boolean isShowSummary() {
      if (this.showSummary != null) {
         return this.showSummary;
      } else {
         ValueExpression ve = this.getValueExpression("showSummary");
         if (ve != null) {
            try {
               return !Boolean.FALSE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return true;
         }
      }
   }

   public void setShowSummary(boolean showSummary) {
      this.showSummary = showSummary;
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[4];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.globalOnly;
      this.values[2] = this.showDetail;
      this.values[3] = this.showSummary;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.globalOnly = (Boolean)this.values[1];
      this.showDetail = (Boolean)this.values[2];
      this.showSummary = (Boolean)this.values[3];
   }
}
