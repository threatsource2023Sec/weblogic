package com.sun.faces.flow.builder;

import com.sun.faces.flow.SwitchCaseImpl;
import com.sun.faces.util.Util;
import javax.el.ValueExpression;
import javax.faces.flow.builder.SwitchCaseBuilder;

public class SwitchCaseBuilderImpl extends SwitchCaseBuilder {
   private SwitchBuilderImpl root;
   private SwitchCaseImpl myCase;

   public SwitchCaseBuilderImpl(SwitchBuilderImpl root) {
      this.root = root;
      this.myCase = null;
   }

   public SwitchCaseImpl getNavigationCase() {
      return this.myCase;
   }

   public SwitchCaseBuilder switchCase() {
      SwitchCaseBuilderImpl result = new SwitchCaseBuilderImpl(this.root);
      result.myCase = new SwitchCaseImpl();
      this.root.getSwitchNode()._getCases().add(result.myCase);
      return result;
   }

   public SwitchCaseBuilder condition(ValueExpression expression) {
      Util.notNull("expression", expression);
      this.myCase.setConditionExpression(expression);
      return this;
   }

   public SwitchCaseBuilder condition(String expression) {
      Util.notNull("expression", expression);
      this.myCase.setCondition(expression);
      return this;
   }

   public SwitchCaseBuilder fromOutcome(String outcome) {
      Util.notNull("outcome", outcome);
      this.myCase.setFromOutcome(outcome);
      return this;
   }
}
