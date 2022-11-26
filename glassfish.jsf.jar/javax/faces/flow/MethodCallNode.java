package javax.faces.flow;

import java.util.List;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public abstract class MethodCallNode extends FlowNode {
   public abstract MethodExpression getMethodExpression();

   public abstract ValueExpression getOutcome();

   public abstract List getParameters();
}
