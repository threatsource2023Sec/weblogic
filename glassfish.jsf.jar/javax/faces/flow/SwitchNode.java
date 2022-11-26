package javax.faces.flow;

import java.util.List;
import javax.faces.context.FacesContext;

public abstract class SwitchNode extends FlowNode {
   public abstract List getCases();

   public abstract String getDefaultOutcome(FacesContext var1);
}
