package javax.faces.flow;

import java.util.Map;
import javax.faces.context.FacesContext;

public abstract class FlowHandler {
   public static final String FLOW_ID_REQUEST_PARAM_NAME = "jffi";
   public static final String TO_FLOW_DOCUMENT_ID_REQUEST_PARAM_NAME = "jftfdi";
   public static final String NULL_FLOW = "javax.faces.flow.NullFlow";

   public abstract Map getCurrentFlowScope();

   public abstract Flow getFlow(FacesContext var1, String var2, String var3);

   public abstract void addFlow(FacesContext var1, Flow var2);

   public abstract Flow getCurrentFlow(FacesContext var1);

   public Flow getCurrentFlow() {
      return this.getCurrentFlow(FacesContext.getCurrentInstance());
   }

   public abstract String getLastDisplayedViewId(FacesContext var1);

   public abstract void pushReturnMode(FacesContext var1);

   public abstract void popReturnMode(FacesContext var1);

   public abstract void transition(FacesContext var1, Flow var2, Flow var3, FlowCallNode var4, String var5);

   public abstract void clientWindowTransition(FacesContext var1);

   public abstract boolean isActive(FacesContext var1, String var2, String var3);
}
