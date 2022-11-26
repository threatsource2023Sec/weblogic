package com.sun.faces.flow;

import com.sun.faces.util.Util;
import java.io.Serializable;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.ReturnNode;

public class ReturnNodeImpl extends ReturnNode implements Serializable {
   private static final long serialVersionUID = 7159675814039078231L;
   private final String id;
   private ValueExpression fromOutcome;

   public ReturnNodeImpl(String id) {
      this.id = id;
      this.fromOutcome = null;
   }

   public String getFromOutcome(FacesContext context) {
      Util.notNull("context", context);
      String result = null;
      if (null != this.fromOutcome) {
         Object objResult = this.fromOutcome.getValue(context.getELContext());
         result = null != objResult ? objResult.toString() : null;
      }

      return result;
   }

   public void setFromOutcome(String fromOutcome) {
      if (null == fromOutcome) {
         this.fromOutcome = null;
      }

      FacesContext context = FacesContext.getCurrentInstance();
      ExpressionFactory eFactory = context.getApplication().getExpressionFactory();
      this.fromOutcome = eFactory.createValueExpression(context.getELContext(), fromOutcome, Object.class);
   }

   public void setFromOutcome(ValueExpression fromOutcome) {
      this.fromOutcome = fromOutcome;
   }

   public String getId() {
      return this.id;
   }
}
