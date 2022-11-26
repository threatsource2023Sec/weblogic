package javax.faces.flow;

import javax.faces.context.FacesContext;

public abstract class ReturnNode extends FlowNode {
   public abstract String getFromOutcome(FacesContext var1);
}
