package javax.faces.component;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

class WrapperEvent extends FacesEvent {
   private FacesEvent event = null;
   private int rowIndex = -1;

   public WrapperEvent(UIComponent component, FacesEvent event, int rowIndex) {
      super(component);
      this.event = event;
      this.rowIndex = rowIndex;
   }

   public FacesEvent getFacesEvent() {
      return this.event;
   }

   public int getRowIndex() {
      return this.rowIndex;
   }

   public PhaseId getPhaseId() {
      return this.event.getPhaseId();
   }

   public void setPhaseId(PhaseId phaseId) {
      this.event.setPhaseId(phaseId);
   }

   public boolean isAppropriateListener(FacesListener listener) {
      return false;
   }

   public void processListener(FacesListener listener) {
      throw new IllegalStateException();
   }
}
