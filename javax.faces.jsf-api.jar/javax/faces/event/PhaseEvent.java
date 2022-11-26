package javax.faces.event;

import java.util.EventObject;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

public class PhaseEvent extends EventObject {
   private FacesContext context = null;
   private PhaseId phaseId = null;

   public PhaseEvent(FacesContext context, PhaseId phaseId, Lifecycle lifecycle) {
      super(lifecycle);
      if (phaseId != null && context != null && lifecycle != null) {
         this.phaseId = phaseId;
         this.context = context;
      } else {
         throw new NullPointerException();
      }
   }

   public FacesContext getFacesContext() {
      return this.context;
   }

   public PhaseId getPhaseId() {
      return this.phaseId;
   }
}
