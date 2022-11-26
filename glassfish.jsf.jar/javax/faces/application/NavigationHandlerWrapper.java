package javax.faces.application;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class NavigationHandlerWrapper extends NavigationHandler implements FacesWrapper {
   private NavigationHandler wrapped;

   /** @deprecated */
   @Deprecated
   public NavigationHandlerWrapper() {
   }

   public NavigationHandlerWrapper(NavigationHandler wrapped) {
      this.wrapped = wrapped;
   }

   public NavigationHandler getWrapped() {
      return this.wrapped;
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome) {
      this.getWrapped().handleNavigation(context, fromAction, outcome);
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      this.getWrapped().handleNavigation(context, fromAction, outcome, toFlowDocumentId);
   }
}
