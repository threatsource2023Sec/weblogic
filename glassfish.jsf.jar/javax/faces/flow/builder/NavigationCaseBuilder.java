package javax.faces.flow.builder;

import javax.el.ValueExpression;

public abstract class NavigationCaseBuilder {
   public abstract NavigationCaseBuilder fromViewId(String var1);

   public abstract NavigationCaseBuilder fromAction(String var1);

   public abstract NavigationCaseBuilder fromOutcome(String var1);

   public abstract NavigationCaseBuilder toViewId(String var1);

   public abstract NavigationCaseBuilder toFlowDocumentId(String var1);

   public abstract NavigationCaseBuilder condition(String var1);

   public abstract NavigationCaseBuilder condition(ValueExpression var1);

   public abstract RedirectBuilder redirect();

   public abstract class RedirectBuilder {
      public abstract RedirectBuilder parameter(String var1, String var2);

      public abstract RedirectBuilder includeViewParams();
   }
}
