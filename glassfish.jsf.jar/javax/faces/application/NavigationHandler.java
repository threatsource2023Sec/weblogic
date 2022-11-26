package javax.faces.application;

import javax.faces.context.FacesContext;

public abstract class NavigationHandler {
   public abstract void handleNavigation(FacesContext var1, String var2, String var3);

   public void handleNavigation(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      this.handleNavigation(context, fromAction, outcome);
   }
}
