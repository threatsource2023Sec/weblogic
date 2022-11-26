package com.sun.faces.facelets.el;

import java.io.Serializable;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/** @deprecated */
public final class LegacyMethodBinding extends MethodBinding implements Serializable {
   private static final long serialVersionUID = 1L;
   private final MethodExpression m;

   public LegacyMethodBinding(MethodExpression m) {
      this.m = m;
   }

   public Class getType(FacesContext context) throws MethodNotFoundException {
      try {
         return this.m.getMethodInfo(context.getELContext()).getReturnType();
      } catch (javax.el.MethodNotFoundException var3) {
         throw new MethodNotFoundException(var3.getMessage(), var3.getCause());
      } catch (ELException var4) {
         throw new EvaluationException(var4.getMessage(), var4.getCause());
      }
   }

   public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
      try {
         return this.m.invoke(context.getELContext(), params);
      } catch (javax.el.MethodNotFoundException var4) {
         throw new MethodNotFoundException(var4.getMessage(), var4.getCause());
      } catch (ELException var5) {
         throw new EvaluationException(var5.getMessage(), var5.getCause());
      }
   }

   public String getExpressionString() {
      return this.m.getExpressionString();
   }
}
