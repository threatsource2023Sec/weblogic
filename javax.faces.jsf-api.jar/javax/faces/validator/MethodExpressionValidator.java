package javax.faces.validator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class MethodExpressionValidator implements Validator, StateHolder {
   private MethodExpression methodExpression = null;
   private boolean transientValue = false;

   public MethodExpressionValidator() {
   }

   public MethodExpressionValidator(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (context != null && component != null) {
         if (value != null) {
            try {
               ELContext elContext = context.getELContext();
               this.methodExpression.invoke(elContext, new Object[]{context, component, value});
            } catch (ELException var8) {
               Throwable e = var8.getCause();
               if (e instanceof ValidatorException) {
                  throw (ValidatorException)e;
               }

               String errInfo = var8.getMessage();
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errInfo, errInfo);
               throw new ValidatorException(message, var8.getCause());
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public Object saveState(FacesContext context) {
      Object[] values = new Object[]{this.methodExpression};
      return values;
   }

   public void restoreState(FacesContext context, Object state) {
      Object[] values = (Object[])((Object[])state);
      this.methodExpression = (MethodExpression)values[0];
   }

   public boolean isTransient() {
      return this.transientValue;
   }

   public void setTransient(boolean transientValue) {
      this.transientValue = transientValue;
   }
}
