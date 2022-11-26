package javax.faces.flow;

import java.util.List;
import java.util.Map;
import javax.el.MethodExpression;
import javax.faces.lifecycle.ClientWindow;

public abstract class Flow {
   public abstract String getId();

   public abstract String getDefiningDocumentId();

   public abstract String getStartNodeId();

   public abstract MethodExpression getFinalizer();

   public abstract MethodExpression getInitializer();

   public abstract Map getInboundParameters();

   public abstract List getViews();

   public abstract Map getReturns();

   public abstract Map getSwitches();

   public abstract Map getFlowCalls();

   public abstract FlowCallNode getFlowCall(Flow var1);

   public abstract List getMethodCalls();

   public abstract FlowNode getNode(String var1);

   public abstract Map getNavigationCases();

   public abstract String getClientWindowFlowId(ClientWindow var1);
}
