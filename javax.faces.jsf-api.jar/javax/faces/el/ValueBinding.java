package javax.faces.el;

import javax.faces.context.FacesContext;

/** @deprecated */
public abstract class ValueBinding {
   public abstract Object getValue(FacesContext var1) throws EvaluationException, PropertyNotFoundException;

   public abstract void setValue(FacesContext var1, Object var2) throws EvaluationException, PropertyNotFoundException;

   public abstract boolean isReadOnly(FacesContext var1) throws EvaluationException, PropertyNotFoundException;

   public abstract Class getType(FacesContext var1) throws EvaluationException, PropertyNotFoundException;

   public String getExpressionString() {
      return null;
   }
}
