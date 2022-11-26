package com.sun.faces.flow;

import java.io.Serializable;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.SwitchCase;

public class SwitchCaseImpl extends SwitchCase implements Serializable {
   private static final long serialVersionUID = -8982500105361921446L;
   private String enclosingId;
   private String fromOutcome;
   private String condition;
   private ValueExpression conditionExpr;

   public ValueExpression getConditionExpression() {
      return this.conditionExpr;
   }

   public Boolean getCondition(FacesContext context) {
      if (this.conditionExpr == null && this.condition != null) {
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         this.conditionExpr = factory.createValueExpression(context.getELContext(), this.condition, Boolean.class);
      }

      return this.conditionExpr != null ? (Boolean)this.conditionExpr.getValue(context.getELContext()) : Boolean.FALSE;
   }

   public void setCondition(String condition) {
      this.condition = condition;
   }

   public void setConditionExpression(ValueExpression conditionExpression) {
      this.conditionExpr = conditionExpression;
   }

   public String getFromOutcome() {
      return this.fromOutcome;
   }

   public void setFromOutcome(String fromOutcome) {
      this.fromOutcome = fromOutcome;
   }

   public String getEnclosingId() {
      return this.enclosingId;
   }

   public void setEnclosingId(String returnId) {
      this.enclosingId = returnId;
   }
}
