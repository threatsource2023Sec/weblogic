package com.sun.faces.flow.builder;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

public class MutableNavigationCase extends NavigationCase {
   private String fromViewId;
   private String fromAction;
   private String fromOutcome;
   private String condition;
   private String toViewId;
   private String toFlowDocumentId;
   private Map parameters;
   private boolean redirect;
   private boolean includeViewParams;
   private ValueExpression toViewIdExpr;
   private ValueExpression conditionExpr;
   private String toString;
   private int hashCode;

   public MutableNavigationCase() {
      this((String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (Map)null, false, false);
      this.parameters = new ConcurrentHashMap();
   }

   public MutableNavigationCase(String fromViewId, String fromAction, String fromOutcome, String condition, String toViewId, String toFlowDocumentId, Map parameters, boolean redirect, boolean includeViewParams) {
      super(fromViewId, fromAction, fromOutcome, condition, toViewId, toFlowDocumentId, parameters, redirect, includeViewParams);
      this.fromViewId = fromViewId;
      this.fromAction = fromAction;
      this.fromOutcome = fromOutcome;
      this.condition = condition;
      this.toViewId = toViewId;
      this.toFlowDocumentId = toFlowDocumentId;
      this.parameters = (Map)(null != parameters ? parameters : new ConcurrentHashMap());
      this.redirect = redirect;
      this.includeViewParams = includeViewParams;
   }

   public MutableNavigationCase(String fromViewId, String fromAction, String fromOutcome, String condition, String toViewId, String toFlowDocumentId, boolean redirect, boolean includeViewParams) {
      super(fromViewId, fromAction, fromOutcome, condition, toViewId, toFlowDocumentId, Collections.EMPTY_MAP, redirect, includeViewParams);
      this.fromViewId = fromViewId;
      this.fromAction = fromAction;
      this.fromOutcome = fromOutcome;
      this.condition = condition;
      this.toViewId = toViewId;
      this.toFlowDocumentId = toFlowDocumentId;
      this.parameters = Collections.emptyMap();
      this.redirect = redirect;
      this.includeViewParams = includeViewParams;
   }

   public String getFromViewId() {
      return this.fromViewId;
   }

   public void setFromViewId(String fromViewId) {
      this.fromViewId = fromViewId;
   }

   public String getFromAction() {
      return this.fromAction;
   }

   public void setFromAction(String fromAction) {
      this.fromAction = fromAction;
   }

   public String getFromOutcome() {
      return this.fromOutcome;
   }

   public void setFromOutcome(String fromOutcome) {
      this.fromOutcome = fromOutcome;
   }

   public String getToViewId(FacesContext context) {
      if (this.toViewIdExpr == null) {
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         this.toViewIdExpr = factory.createValueExpression(context.getELContext(), this.toViewId, String.class);
      }

      String result = (String)this.toViewIdExpr.getValue(context.getELContext());
      if (result.charAt(0) != '/') {
         result = '/' + result;
      }

      return result;
   }

   public void setToViewId(String toViewId) {
      this.toViewId = toViewId;
      this.toViewIdExpr = null;
   }

   public String getToFlowDocumentId() {
      return this.toFlowDocumentId;
   }

   public void setToFlowDocumentId(String toFlowDocumentId) {
      this.toFlowDocumentId = toFlowDocumentId;
   }

   public boolean hasCondition() {
      return this.condition != null;
   }

   public Boolean getCondition(FacesContext context) {
      if (this.conditionExpr == null && this.condition != null) {
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         this.conditionExpr = factory.createValueExpression(context.getELContext(), this.condition, Boolean.class);
      }

      return this.conditionExpr != null ? (Boolean)this.conditionExpr.getValue(context.getELContext()) : null;
   }

   public void setCondition(String condition) {
      this.condition = condition;
      this.conditionExpr = null;
   }

   public void setConditionExpression(ValueExpression conditionExpression) {
      this.conditionExpr = conditionExpression;
   }

   public Map getParameters() {
      return this.parameters;
   }

   public boolean isRedirect() {
      return this.redirect;
   }

   public void setRedirect(boolean redirect) {
      this.redirect = redirect;
   }

   public boolean isIncludeViewParams() {
      return this.includeViewParams;
   }

   public void setIncludeViewParams(boolean includeViewParams) {
      this.includeViewParams = includeViewParams;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         MutableNavigationCase other = (MutableNavigationCase)obj;
         if (this.fromViewId == null) {
            if (other.fromViewId != null) {
               return false;
            }
         } else if (!this.fromViewId.equals(other.fromViewId)) {
            return false;
         }

         label93: {
            if (this.fromAction == null) {
               if (other.fromAction == null) {
                  break label93;
               }
            } else if (this.fromAction.equals(other.fromAction)) {
               break label93;
            }

            return false;
         }

         label86: {
            if (this.fromOutcome == null) {
               if (other.fromOutcome == null) {
                  break label86;
               }
            } else if (this.fromOutcome.equals(other.fromOutcome)) {
               break label86;
            }

            return false;
         }

         if (this.condition == null) {
            if (other.condition != null) {
               return false;
            }
         } else if (!this.condition.equals(other.condition)) {
            return false;
         }

         label72: {
            if (this.toViewId == null) {
               if (other.toViewId == null) {
                  break label72;
               }
            } else if (this.toViewId.equals(other.toViewId)) {
               break label72;
            }

            return false;
         }

         if (this.toFlowDocumentId == null) {
            if (other.toFlowDocumentId != null) {
               return false;
            }
         } else if (!this.toFlowDocumentId.equals(other.toFlowDocumentId)) {
            return false;
         }

         if (this.parameters != other.parameters && (this.parameters == null || !this.parameters.equals(other.parameters))) {
            return false;
         } else if (this.redirect != other.redirect) {
            return false;
         } else if (this.includeViewParams != other.includeViewParams) {
            return false;
         } else {
            return true;
         }
      }
   }

   public int hashCode() {
      int hash = 3;
      hash = 29 * hash + (this.fromViewId != null ? this.fromViewId.hashCode() : 0);
      hash = 29 * hash + (this.fromAction != null ? this.fromAction.hashCode() : 0);
      hash = 29 * hash + (this.fromOutcome != null ? this.fromOutcome.hashCode() : 0);
      hash = 29 * hash + (this.condition != null ? this.condition.hashCode() : 0);
      hash = 29 * hash + (this.toViewId != null ? this.toViewId.hashCode() : 0);
      hash = 29 * hash + (this.toFlowDocumentId != null ? this.toFlowDocumentId.hashCode() : 0);
      hash = 29 * hash + (this.parameters != null ? this.parameters.hashCode() : 0);
      hash = 29 * hash + (this.redirect ? 1 : 0);
      hash = 29 * hash + (this.includeViewParams ? 1 : 0);
      return hash;
   }

   public String toString() {
      if (this.toString == null) {
         StringBuilder sb = new StringBuilder(64);
         sb.append("NavigationCase{");
         sb.append("fromViewId='").append(this.fromViewId).append('\'');
         sb.append(", fromAction='").append(this.fromAction).append('\'');
         sb.append(", fromOutcome='").append(this.fromOutcome).append('\'');
         sb.append(", if='").append(this.condition).append('\'');
         sb.append(", toViewId='").append(this.toViewId).append('\'');
         sb.append(", faces-redirect=").append(this.redirect);
         sb.append(", includeViewParams=").append(this.includeViewParams).append('\'');
         sb.append(", parameters=").append(this.parameters != null ? this.parameters.toString() : "");
         sb.append('}');
         this.toString = sb.toString();
      }

      return this.toString;
   }
}
