package javax.faces.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class NavigationCase {
   private final String fromViewId;
   private final String fromAction;
   private final String fromOutcome;
   private final String condition;
   private final String toViewId;
   private final String toFlowDocumentId;
   private final Map parameters;
   private final boolean redirect;
   private final boolean includeViewParams;
   private ValueExpression toViewIdExpr;
   private ValueExpression conditionExpr;
   private String toString;
   private int hashCode;

   public NavigationCase(String fromViewId, String fromAction, String fromOutcome, String condition, String toViewId, Map parameters, boolean redirect, boolean includeViewParams) {
      this.fromViewId = fromViewId;
      this.fromAction = fromAction;
      this.fromOutcome = fromOutcome;
      this.condition = condition;
      this.toViewId = toViewId;
      this.toFlowDocumentId = null;
      this.parameters = parameters;
      this.redirect = redirect;
      this.includeViewParams = includeViewParams;
   }

   public NavigationCase(String fromViewId, String fromAction, String fromOutcome, String condition, String toViewId, String toFlowDocumentId, Map parameters, boolean redirect, boolean includeViewParams) {
      this.fromViewId = fromViewId;
      this.fromAction = fromAction;
      this.fromOutcome = fromOutcome;
      this.condition = condition;
      this.toViewId = toViewId;
      this.toFlowDocumentId = toFlowDocumentId;
      this.parameters = parameters;
      this.redirect = redirect;
      this.includeViewParams = includeViewParams;
   }

   public URL getActionURL(FacesContext context) throws MalformedURLException {
      ExternalContext extContext = context.getExternalContext();
      return new URL(extContext.getRequestScheme(), extContext.getRequestServerName(), extContext.getRequestServerPort(), context.getApplication().getViewHandler().getActionURL(context, this.getToViewId(context)));
   }

   public URL getResourceURL(FacesContext context) throws MalformedURLException {
      ExternalContext extContext = context.getExternalContext();
      return new URL(extContext.getRequestScheme(), extContext.getRequestServerName(), extContext.getRequestServerPort(), context.getApplication().getViewHandler().getResourceURL(context, this.getToViewId(context)));
   }

   public URL getRedirectURL(FacesContext context) throws MalformedURLException {
      ExternalContext extContext = context.getExternalContext();
      return new URL(extContext.getRequestScheme(), extContext.getRequestServerName(), extContext.getRequestServerPort(), context.getApplication().getViewHandler().getRedirectURL(context, this.getToViewId(context), SharedUtils.evaluateExpressions(context, this.getParameters()), this.isIncludeViewParams()));
   }

   public URL getBookmarkableURL(FacesContext context) throws MalformedURLException {
      ExternalContext extContext = context.getExternalContext();
      return new URL(extContext.getRequestScheme(), extContext.getRequestServerName(), extContext.getRequestServerPort(), context.getApplication().getViewHandler().getBookmarkableURL(context, this.getToViewId(context), this.getParameters(), this.isIncludeViewParams()));
   }

   public String getFromViewId() {
      return this.fromViewId;
   }

   public String getFromAction() {
      return this.fromAction;
   }

   public String getFromOutcome() {
      return this.fromOutcome;
   }

   public String getToViewId(FacesContext context) {
      if (this.toViewIdExpr == null) {
         this.toViewIdExpr = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.toViewId, String.class);
      }

      String newToViewId = (String)this.toViewIdExpr.getValue(context.getELContext());
      if (newToViewId.charAt(0) != '/') {
         newToViewId = '/' + newToViewId;
      }

      return newToViewId;
   }

   public String getToFlowDocumentId() {
      return this.toFlowDocumentId;
   }

   public boolean hasCondition() {
      return this.condition != null;
   }

   public Boolean getCondition(FacesContext context) {
      if (this.conditionExpr == null && this.condition != null) {
         this.conditionExpr = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.condition, Boolean.class);
      }

      return this.conditionExpr != null ? (Boolean)this.conditionExpr.getValue(context.getELContext()) : null;
   }

   public Map getParameters() {
      return this.parameters;
   }

   public boolean isRedirect() {
      return this.redirect;
   }

   public boolean isIncludeViewParams() {
      return this.includeViewParams;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         boolean var10000;
         label90: {
            NavigationCase that = (NavigationCase)o;
            if (this.redirect == that.redirect) {
               label84: {
                  if (this.fromAction != null) {
                     if (!this.fromAction.equals(that.fromAction)) {
                        break label84;
                     }
                  } else if (that.fromAction != null) {
                     break label84;
                  }

                  if (this.fromOutcome != null) {
                     if (!this.fromOutcome.equals(that.fromOutcome)) {
                        break label84;
                     }
                  } else if (that.fromOutcome != null) {
                     break label84;
                  }

                  if (this.condition != null) {
                     if (!this.condition.equals(that.condition)) {
                        break label84;
                     }
                  } else if (that.condition != null) {
                     break label84;
                  }

                  if (this.fromViewId != null) {
                     if (!this.fromViewId.equals(that.fromViewId)) {
                        break label84;
                     }
                  } else if (that.fromViewId != null) {
                     break label84;
                  }

                  if (this.toViewId != null) {
                     if (!this.toViewId.equals(that.toViewId)) {
                        break label84;
                     }
                  } else if (that.toViewId != null) {
                     break label84;
                  }

                  if (this.toFlowDocumentId != null) {
                     if (!this.toFlowDocumentId.equals(that.toFlowDocumentId)) {
                        break label84;
                     }
                  } else if (that.toFlowDocumentId != null) {
                     break label84;
                  }

                  if (this.parameters != null) {
                     if (this.parameters.equals(that.parameters)) {
                        break label90;
                     }
                  } else if (that.parameters == null) {
                     break label90;
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = this.fromViewId != null ? this.fromViewId.hashCode() : 0;
         result = 31 * result + (this.fromAction != null ? this.fromAction.hashCode() : 0);
         result = 31 * result + (this.fromOutcome != null ? this.fromOutcome.hashCode() : 0);
         result = 31 * result + (this.condition != null ? this.condition.hashCode() : 0);
         result = 31 * result + (this.toViewId != null ? this.toViewId.hashCode() : 0);
         result = 31 * result + (this.toFlowDocumentId != null ? this.toFlowDocumentId.hashCode() : 0);
         result = 31 * result + (this.redirect ? 1 : 0);
         result = 31 * result + (this.parameters != null ? this.parameters.hashCode() : 0);
         this.hashCode = result;
      }

      return this.hashCode;
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
