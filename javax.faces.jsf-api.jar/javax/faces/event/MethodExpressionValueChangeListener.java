package javax.faces.event;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

public class MethodExpressionValueChangeListener implements ValueChangeListener, StateHolder {
   private MethodExpression methodExpression = null;
   private boolean isTransient;

   public MethodExpressionValueChangeListener() {
   }

   public MethodExpressionValueChangeListener(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public void processValueChange(ValueChangeEvent valueChangeEvent) throws AbortProcessingException {
      if (valueChangeEvent == null) {
         throw new NullPointerException();
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            this.methodExpression.invoke(elContext, new Object[]{valueChangeEvent});
         } catch (ELException var4) {
            throw new AbortProcessingException(var4.getMessage(), var4.getCause());
         }
      }
   }

   public Object saveState(FacesContext context) {
      return new Object[]{this.methodExpression};
   }

   public void restoreState(FacesContext context, Object state) {
      this.methodExpression = (MethodExpression)((Object[])((Object[])state))[0];
   }

   public boolean isTransient() {
      return this.isTransient;
   }

   public void setTransient(boolean newTransientValue) {
      this.isTransient = newTransientValue;
   }
}
