package javax.faces.component;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.application.Application;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;

public class UIForm extends UIComponentBase implements NamingContainer, UniqueIdVendor {
   public static final String COMPONENT_TYPE = "javax.faces.Form";
   public static final String COMPONENT_FAMILY = "javax.faces.Form";

   public UIForm() {
      this.setRendererType("javax.faces.Form");
   }

   public String getFamily() {
      return "javax.faces.Form";
   }

   public boolean isSubmitted() {
      return (Boolean)this.getTransientStateHelper().getTransient(UIForm.PropertyKeys.submitted, false);
   }

   public void setSubmitted(boolean submitted) {
      this.getTransientStateHelper().putTransient(UIForm.PropertyKeys.submitted, submitted);
   }

   public boolean isPrependId() {
      return (Boolean)this.getStateHelper().eval(UIForm.PropertyKeys.prependId, true);
   }

   public void setPrependId(boolean prependId) {
      this.getStateHelper().put(UIForm.PropertyKeys.prependId, prependId);
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.decode(context);
         if (this.isSubmitted()) {
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               ((UIComponent)kids.next()).processDecodes(context);
            }

         }
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isSubmitted()) {
         this.pushComponentToEL(context, this);
         Application application = context.getApplication();
         application.publishEvent(context, PreValidateEvent.class, this);
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            ((UIComponent)kids.next()).processValidators(context);
         }

         application.publishEvent(context, PostValidateEvent.class, this);
         this.popComponentFromEL(context);
      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isSubmitted()) {
         this.pushComponentToEL(context, this);

         try {
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               ((UIComponent)kids.next()).processUpdates(context);
            }
         } finally {
            this.popComponentFromEL(context);
         }

      }
   }

   public String createUniqueId(FacesContext context, String seed) {
      if (this.isPrependId()) {
         int lastId = (Integer)Util.coalesce(this.getLastId(), 0);
         ++lastId;
         this.setLastId(lastId);
         return "j_id" + Util.coalesce(seed, lastId);
      } else {
         UIComponent ancestorNamingContainer = this.getParent() == null ? null : this.getParent().getNamingContainer();
         return ancestorNamingContainer instanceof UniqueIdVendor ? ((UniqueIdVendor)ancestorNamingContainer).createUniqueId(context, seed) : context.getViewRoot().createUniqueId(context, seed);
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

   public boolean visitTree(VisitContext context, VisitCallback callback) {
      if (!this.isPrependId()) {
         return super.visitTree(context, callback);
      } else {
         Collection idsToVisit = context.getSubtreeIdsToVisit(this);
         if (!idsToVisit.isEmpty()) {
            return super.visitTree(context, callback);
         } else if (this.isVisitable(context)) {
            FacesContext facesContext = context.getFacesContext();
            this.pushComponentToEL(facesContext, (UIComponent)null);

            boolean var5;
            try {
               var5 = context.invokeVisitCallback(this, callback) == VisitResult.COMPLETE;
            } finally {
               this.popComponentFromEL(facesContext);
            }

            return var5;
         } else {
            return false;
         }
      }
   }

   private Integer getLastId() {
      return (Integer)this.getStateHelper().get(UIForm.PropertyKeys.lastId);
   }

   private void setLastId(Integer lastId) {
      this.getStateHelper().put(UIForm.PropertyKeys.lastId, lastId);
   }

   static enum PropertyKeys {
      prependId,
      lastId,
      submitted;
   }
}
