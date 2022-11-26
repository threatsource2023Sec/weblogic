package javax.faces.flow.builder;

import java.util.List;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public abstract class MethodCallBuilder implements NodeBuilder {
   public abstract MethodCallBuilder expression(MethodExpression var1);

   public abstract MethodCallBuilder expression(String var1);

   public abstract MethodCallBuilder expression(String var1, Class[] var2);

   public abstract MethodCallBuilder parameters(List var1);

   public abstract MethodCallBuilder defaultOutcome(String var1);

   public abstract MethodCallBuilder defaultOutcome(ValueExpression var1);

   public abstract MethodCallBuilder markAsStartNode();
}
