package javax.faces.application;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;

public abstract class ConfigurableNavigationHandler extends NavigationHandler {
   public abstract NavigationCase getNavigationCase(FacesContext var1, String var2, String var3);

   public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      return this.getNavigationCase(context, fromAction, outcome);
   }

   public abstract Map getNavigationCases();

   public void performNavigation(String outcome) {
      this.handleNavigation(FacesContext.getCurrentInstance(), (String)null, outcome);
   }

   public void inspectFlow(FacesContext context, Flow flow) {
   }
}
