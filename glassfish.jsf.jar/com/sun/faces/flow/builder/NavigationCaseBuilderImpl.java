package com.sun.faces.flow.builder;

import com.sun.faces.util.Util;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.el.ValueExpression;
import javax.faces.flow.builder.NavigationCaseBuilder;

public class NavigationCaseBuilderImpl extends NavigationCaseBuilder {
   private FlowBuilderImpl root;
   private MutableNavigationCase navCase = new MutableNavigationCase();

   public NavigationCaseBuilderImpl(FlowBuilderImpl root) {
      this.root = root;
   }

   public NavigationCaseBuilder toFlowDocumentId(String toFlowDocumentId) {
      Util.notNull("toFlowDocumentId", toFlowDocumentId);
      this.navCase.setToFlowDocumentId(toFlowDocumentId);
      return this;
   }

   public NavigationCaseBuilder fromAction(String fromAction) {
      Util.notNull("fromAction", fromAction);
      this.navCase.setFromAction(fromAction);
      return this;
   }

   public NavigationCaseBuilder fromOutcome(String fromOutcome) {
      Util.notNull("fromOutcome", fromOutcome);
      this.navCase.setFromOutcome(fromOutcome);
      return this;
   }

   public NavigationCaseBuilder fromViewId(String fromViewId) {
      Util.notNull("fromViewId", fromViewId);
      this.navCase.setFromViewId(fromViewId);
      Map rules = this.root._getFlow()._getNavigationCases();
      Set cases = (Set)rules.get(fromViewId);
      if (null == cases) {
         cases = new CopyOnWriteArraySet();
         rules.put(fromViewId, cases);
      }

      ((Set)cases).add(this.navCase);
      return this;
   }

   public NavigationCaseBuilder toViewId(String toViewId) {
      Util.notNull("toViewId", toViewId);
      this.navCase.setToViewId(toViewId);
      return this;
   }

   public NavigationCaseBuilder condition(String condition) {
      Util.notNull("condition", condition);
      this.navCase.setCondition(condition);
      return this;
   }

   public NavigationCaseBuilder condition(ValueExpression condition) {
      Util.notNull("condition", condition);
      this.navCase.setConditionExpression(condition);
      return this;
   }

   public NavigationCaseBuilder.RedirectBuilder redirect() {
      this.navCase.setRedirect(true);
      return new RedirectBuilderImpl();
   }

   private class RedirectBuilderImpl extends NavigationCaseBuilder.RedirectBuilder {
      public RedirectBuilderImpl() {
         super();
      }

      public NavigationCaseBuilder.RedirectBuilder parameter(String name, String value) {
         Util.notNull("name", name);
         Util.notNull("value", value);
         Map redirectParams = NavigationCaseBuilderImpl.this.navCase.getParameters();
         List values = (List)redirectParams.get(name);
         if (null == values) {
            values = new CopyOnWriteArrayList();
            redirectParams.put(name, values);
         }

         ((List)values).add(value);
         return this;
      }

      public NavigationCaseBuilder.RedirectBuilder includeViewParams() {
         NavigationCaseBuilderImpl.this.navCase.isIncludeViewParams();
         return this;
      }
   }
}
