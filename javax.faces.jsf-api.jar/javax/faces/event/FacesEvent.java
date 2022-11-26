package javax.faces.event;

import java.util.EventObject;
import javax.faces.component.UIComponent;

public abstract class FacesEvent extends EventObject {
   private PhaseId phaseId;

   public FacesEvent(UIComponent component) {
      super(component);
      this.phaseId = PhaseId.ANY_PHASE;
   }

   public UIComponent getComponent() {
      return (UIComponent)this.getSource();
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
