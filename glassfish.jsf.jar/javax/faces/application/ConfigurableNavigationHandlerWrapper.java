package javax.faces.application;

import java.util.Map;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;

public abstract class ConfigurableNavigationHandlerWrapper extends ConfigurableNavigationHandler implements FacesWrapper {
   private ConfigurableNavigationHandler wrapped;

   /** @deprecated */
   @Deprecated
   public ConfigurableNavigationHandlerWrapper() {
   }

   public ConfigurableNavigationHandlerWrapper(ConfigurableNavigationHandler wrapped) {
      this.wrapped = wrapped;
   }

   public ConfigurableNavigationHandler getWrapped() {
      return this.wrapped;
   }

   public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome) {
      return this.getWrapped().getNavigationCase(context, fromAction, outcome);
   }

   public Map getNavigationCases() {
      return this.getWrapped().getNavigationCases();
   }

   public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      return this.getWrapped().getNavigationCase(context, fromAction, outcome, toFlowDocumentId);
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome) {
      this.getWrapped().handleNavigation(context, fromAction, outcome);
   }

   public void performNavigation(String outcome) {
      this.getWrapped().performNavigation(outcome);
   }

   public void inspectFlow(FacesContext context, Flow flow) {
      this.getWrapped().inspectFlow(context, flow);
   }
}
