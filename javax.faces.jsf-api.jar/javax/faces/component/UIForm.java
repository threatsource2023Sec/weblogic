package javax.faces.component;

import java.util.Iterator;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public class UIForm extends UIComponentBase implements NamingContainer {
   public static final String COMPONENT_TYPE = "javax.faces.Form";
   public static final String COMPONENT_FAMILY = "javax.faces.Form";
   private boolean submitted = false;
   private Boolean prependId;
   private Object[] values;

   public UIForm() {
      this.setRendererType("javax.faces.Form");
   }

   public String getFamily() {
      return "javax.faces.Form";
   }

   public boolean isSubmitted() {
      return this.submitted;
   }

   public void setSubmitted(boolean submitted) {
      this.submitted = submitted;
   }

   public boolean isPrependId() {
      if (this.prependId != null) {
         return this.prependId;
      } else {
         ValueExpression ve = this.getValueExpression("prependId");
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

   public void setPrependId(boolean prependId) {
      this.prependId = prependId;
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.decode(context);
         if (this.isSubmitted()) {
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               kid.processDecodes(context);
            }

         }
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isSubmitted()) {
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            kid.processValidators(context);
         }

      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isSubmitted()) {
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            kid.processUpdates(context);
         }

      }
   }

   public String getContainerClientId(FacesContext context) {
      if (this.isPrependId()) {
         return super.getContainerClientId(context);
      } else {
         for(UIComponent parent = this.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof NamingContainer) {
               return parent.getContainerClientId(context);
            }
         }

         return null;
      }
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[2];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.prependId;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.prependId = (Boolean)this.values[1];
   }
}
