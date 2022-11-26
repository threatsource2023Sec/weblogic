package javax.faces.component;

public class UIOutcomeTarget extends UIOutput {
   public static final String COMPONENT_TYPE = "javax.faces.OutcomeTarget";
   public static final String COMPONENT_FAMILY = "javax.faces.OutcomeTarget";

   public UIOutcomeTarget() {
      this.setRendererType("javax.faces.Link");
   }

   public String getFamily() {
      return "javax.faces.OutcomeTarget";
   }

   public boolean isIncludeViewParams() {
      return (Boolean)this.getStateHelper().eval(UIOutcomeTarget.PropertyKeys.includeViewParams, false);
   }

   public void setIncludeViewParams(boolean includeViewParams) {
      this.getStateHelper().put(UIOutcomeTarget.PropertyKeys.includeViewParams, includeViewParams);
   }

   public boolean isDisableClientWindow() {
      return (Boolean)this.getStateHelper().eval(UIOutcomeTarget.PropertyKeys.disableClientWindow, false);
   }

   public void setDisableClientWindow(boolean disableClientWindow) {
      this.getStateHelper().put(UIOutcomeTarget.PropertyKeys.disableClientWindow, disableClientWindow);
   }

   public String getOutcome() {
      return (String)this.getStateHelper().eval(UIOutcomeTarget.PropertyKeys.outcome);
   }

   public void setOutcome(String outcome) {
      this.getStateHelper().put(UIOutcomeTarget.PropertyKeys.outcome, outcome);
   }

   static enum PropertyKeys {
      includeViewParams,
      outcome,
      disableClientWindow;
   }
}
