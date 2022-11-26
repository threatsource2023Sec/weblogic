package javax.faces.flow.builder;

import javax.el.ValueExpression;

public abstract class SwitchBuilder implements NodeBuilder {
   public abstract SwitchCaseBuilder switchCase();

   public abstract SwitchCaseBuilder defaultOutcome(String var1);

   public abstract SwitchCaseBuilder defaultOutcome(ValueExpression var1);

   public abstract SwitchBuilder markAsStartNode();
}
