package com.sun.faces.flow;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.SwitchNode;

public class SwitchNodeImpl extends SwitchNode implements Serializable {
   private static final long serialVersionUID = -9203493858518714933L;
   private final String id;
   private ValueExpression defaultOutcome;
   private CopyOnWriteArrayList _cases;
   private List cases;

   public SwitchNodeImpl(String id) {
      this.id = id;
      this.defaultOutcome = null;
      this._cases = new CopyOnWriteArrayList();
      this.cases = Collections.unmodifiableList(this._cases);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         SwitchNodeImpl other;
         label44: {
            other = (SwitchNodeImpl)obj;
            if (this.id == null) {
               if (other.id == null) {
                  break label44;
               }
            } else if (this.id.equals(other.id)) {
               break label44;
            }

            return false;
         }

         if (this.defaultOutcome == other.defaultOutcome || this.defaultOutcome != null && this.defaultOutcome.equals(other.defaultOutcome)) {
            return this._cases == other._cases || this._cases != null && this._cases.equals(other._cases);
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int hash = 5;
      hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
      hash = 47 * hash + (this.defaultOutcome != null ? this.defaultOutcome.hashCode() : 0);
      hash = 47 * hash + (this._cases != null ? this._cases.hashCode() : 0);
      return hash;
   }

   public String getId() {
      return this.id;
   }

   public List getCases() {
      return this.cases;
   }

   public List _getCases() {
      return this._cases;
   }

   public String getDefaultOutcome(FacesContext context) {
      String result = null;
      if (null != this.defaultOutcome) {
         Object objResult = this.defaultOutcome.getValue(context.getELContext());
         result = null != objResult ? objResult.toString() : null;
      }

      return result;
   }

   public void setDefaultOutcome(String defaultOutcome) {
      if (null == defaultOutcome) {
         this.defaultOutcome = null;
      }

      FacesContext context = FacesContext.getCurrentInstance();
      ExpressionFactory eFactory = context.getApplication().getExpressionFactory();
      this.defaultOutcome = eFactory.createValueExpression(context.getELContext(), defaultOutcome, Object.class);
   }

   public void setDefaultOutcome(ValueExpression defaultOutcome) {
      this.defaultOutcome = defaultOutcome;
   }
}
