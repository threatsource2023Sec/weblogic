package javax.faces.el;

import javax.faces.context.FacesContext;

/** @deprecated */
public abstract class MethodBinding {
   public abstract Object invoke(FacesContext var1, Object[] var2) throws EvaluationException, MethodNotFoundException;

   public abstract Class getType(FacesContext var1) throws MethodNotFoundException;

   public String getExpressionString() {
      return null;
   }
}
