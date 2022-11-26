package javax.faces.flow;

import java.util.Map;
import javax.faces.context.FacesContext;

public abstract class FlowCallNode extends FlowNode {
   public abstract Map getOutboundParameters();

   public abstract String getCalledFlowDocumentId(FacesContext var1);

   public abstract String getCalledFlowId(FacesContext var1);
}
