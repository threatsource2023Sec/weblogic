package javax.faces.component;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UIMessage extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.Message";
   public static final String COMPONENT_FAMILY = "javax.faces.Message";
   private String forVal = null;
   private boolean showDetail = true;
   private boolean showDetailSet = false;
   private boolean showSummary = false;
   private boolean showSummarySet = false;
   private Object[] values;

   public UIMessage() {
      this.setRendererType("javax.faces.Message");
   }

   public String getFamily() {
      return "javax.faces.Message";
   }

   public String getFor() {
      if (this.forVal != null) {
         return this.forVal;
      } else {
         ValueExpression ve = this.getValueExpression("for");
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

   public void setFor(String newFor) {
      this.forVal = newFor;
   }

   public boolean isShowDetail() {
      if (this.showDetailSet) {
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
            return this.showDetail;
         }
      }
   }

   public void setShowDetail(boolean showDetail) {
      this.showDetail = showDetail;
      this.showDetailSet = true;
   }

   public boolean isShowSummary() {
      if (this.showSummarySet) {
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
            return this.showSummary;
         }
      }
   }

   public void setShowSummary(boolean showSummary) {
      this.showSummary = showSummary;
      this.showSummarySet = true;
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[6];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.forVal;
      this.values[2] = this.showDetail;
      this.values[3] = this.showDetailSet;
      this.values[4] = this.showSummary;
      this.values[5] = this.showSummarySet;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.forVal = (String)this.values[1];
      this.showDetail = (Boolean)this.values[2];
      this.showDetailSet = (Boolean)this.values[3];
      this.showSummary = (Boolean)this.values[4];
      this.showSummarySet = (Boolean)this.values[5];
   }
}
