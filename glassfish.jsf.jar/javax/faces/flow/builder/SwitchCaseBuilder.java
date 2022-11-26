package javax.faces.flow.builder;

import javax.el.ValueExpression;

public abstract class SwitchCaseBuilder {
   public abstract SwitchCaseBuilder switchCase();

   public abstract SwitchCaseBuilder condition(ValueExpression var1);

   public abstract SwitchCaseBuilder condition(String var1);

   public abstract SwitchCaseBuilder fromOutcome(String var1);
}
