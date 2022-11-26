package javax.faces.event;

import java.util.EventObject;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewAction;
import javax.faces.context.FacesContext;

public abstract class FacesEvent extends EventObject {
   private static final long serialVersionUID = -367663885586773794L;
   private transient FacesContext facesContext;
   private PhaseId phaseId;

   public FacesEvent(UIComponent component) {
      super(component);
      this.phaseId = PhaseId.ANY_PHASE;
   }

   public FacesEvent(FacesContext facesContext, UIComponent component) {
      this(component);
      this.facesContext = facesContext;
   }

   public UIComponent getComponent() {
      return (UIComponent)this.getSource();
   }

   public FacesContext getFacesContext() {
      return !(this.source instanceof UIViewAction) && this.facesContext != null ? this.facesContext : FacesContext.getCurrentInstance();
   }

   public PhaseId getPhaseId() {
      return this.phaseId;
   }

   public void setPhaseId(PhaseId phaseId) {
      if (null == phaseId) {
         throw new IllegalArgumentException();
      } else {
         this.phaseId = phaseId;
      }
   }

   public void queue() {
      this.getComponent().queueEvent(this);
   }

   public abstract boolean isAppropriateListener(FacesListener var1);

   public abstract void processListener(FacesListener var1);
}
