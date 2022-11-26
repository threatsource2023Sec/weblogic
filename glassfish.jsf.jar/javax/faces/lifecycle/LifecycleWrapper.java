package javax.faces.lifecycle;

import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

public abstract class LifecycleWrapper extends Lifecycle implements FacesWrapper {
   private Lifecycle wrapped;

   /** @deprecated */
   @Deprecated
   public LifecycleWrapper() {
   }

   public LifecycleWrapper(Lifecycle wrapped) {
      this.wrapped = wrapped;
   }

   public Lifecycle getWrapped() {
      return this.wrapped;
   }

   public void addPhaseListener(PhaseListener listener) {
      this.getWrapped().addPhaseListener(listener);
   }

   public void execute(FacesContext context) throws FacesException {
      this.getWrapped().execute(context);
   }

   public PhaseListener[] getPhaseListeners() {
      return this.getWrapped().getPhaseListeners();
   }

   public void removePhaseListener(PhaseListener listener) {
      this.getWrapped().removePhaseListener(listener);
   }

   public void render(FacesContext context) throws FacesException {
      this.getWrapped().render(context);
   }

   public void attachWindow(FacesContext context) {
      this.getWrapped().attachWindow(context);
   }
}
