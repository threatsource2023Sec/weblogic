package javax.faces.flow.builder;

import javax.el.ValueExpression;

public abstract class ReturnBuilder implements NodeBuilder {
   public abstract ReturnBuilder fromOutcome(String var1);

   public abstract ReturnBuilder fromOutcome(ValueExpression var1);

   public abstract ReturnBuilder markAsStartNode();
}
