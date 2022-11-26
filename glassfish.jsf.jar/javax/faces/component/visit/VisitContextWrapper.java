package javax.faces.component.visit;

import java.util.Collection;
import java.util.Set;
import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class VisitContextWrapper extends VisitContext implements FacesWrapper {
   private VisitContext wrapped;

   /** @deprecated */
   @Deprecated
   public VisitContextWrapper() {
   }

   public VisitContextWrapper(VisitContext wrapped) {
      this.wrapped = wrapped;
   }

   public VisitContext getWrapped() {
      return this.wrapped;
   }

   public FacesContext getFacesContext() {
      return this.getWrapped().getFacesContext();
   }

   public Set getHints() {
      return this.getWrapped().getHints();
   }

   public Collection getIdsToVisit() {
      return this.getWrapped().getIdsToVisit();
   }

   public Collection getSubtreeIdsToVisit(UIComponent component) {
      return this.getWrapped().getSubtreeIdsToVisit(component);
   }

   public VisitResult invokeVisitCallback(UIComponent component, VisitCallback callback) {
      return this.getWrapped().invokeVisitCallback(component, callback);
   }
}
