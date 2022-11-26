package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import javax.el.ELException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

public class VariableResolverImpl extends VariableResolver {
   private VariableResolver delegate;

   public Object resolveVariable(FacesContext context, String name) throws EvaluationException {
      String message;
      if (context == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (name == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name");
         throw new NullPointerException(message);
      } else {
         Object result;
         if (this.delegate != null) {
            result = this.delegate.resolveVariable(context, name);
         } else {
            try {
               result = context.getApplication().getELResolver().getValue(context.getELContext(), (Object)null, name);
            } catch (ELException var5) {
               throw new EvaluationException(var5);
            }
         }

         return result;
      }
   }

   public void setDelegate(VariableResolver delegate) {
      this.delegate = delegate;
   }

   public VariableResolver getDelegate() {
      return this.delegate;
   }
}
