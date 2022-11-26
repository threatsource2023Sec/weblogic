package javax.faces.flow.builder;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.flow.Flow;

public abstract class FlowBuilder {
   public abstract FlowBuilder id(String var1, String var2);

   public abstract ViewBuilder viewNode(String var1, String var2);

   public abstract NavigationCaseBuilder navigationCase();

   public abstract SwitchBuilder switchNode(String var1);

   public abstract ReturnBuilder returnNode(String var1);

   public abstract MethodCallBuilder methodCallNode(String var1);

   public abstract FlowCallBuilder flowCallNode(String var1);

   public abstract FlowBuilder initializer(MethodExpression var1);

   public abstract FlowBuilder initializer(String var1);

   public abstract FlowBuilder finalizer(MethodExpression var1);

   public abstract FlowBuilder finalizer(String var1);

   public abstract FlowBuilder inboundParameter(String var1, ValueExpression var2);

   public abstract FlowBuilder inboundParameter(String var1, String var2);

   public abstract Flow getFlow();
}
