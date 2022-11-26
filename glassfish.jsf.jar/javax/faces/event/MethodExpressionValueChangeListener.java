package javax.faces.event;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

public class MethodExpressionValueChangeListener implements ValueChangeListener, StateHolder {
   private MethodExpression methodExpressionOneArg = null;
   private MethodExpression methodExpressionZeroArg = null;
   private boolean isTransient;
   private static final Class[] VALUECHANGE_LISTENER_ZEROARG_SIG = new Class[0];

   public MethodExpressionValueChangeListener() {
   }

   public MethodExpressionValueChangeListener(MethodExpression methodExpressionOneArg) {
      this.methodExpressionOneArg = methodExpressionOneArg;
      FacesContext context = FacesContext.getCurrentInstance();
      ELContext elContext = context.getELContext();
      this.methodExpressionZeroArg = context.getApplication().getExpressionFactory().createMethodExpression(elContext, methodExpressionOneArg.getExpressionString(), Void.class, VALUECHANGE_LISTENER_ZEROARG_SIG);
   }

   public MethodExpressionValueChangeListener(MethodExpression methodExpressionOneArg, MethodExpression methodExpressionZeroArg) {
      this.methodExpressionOneArg = methodExpressionOneArg;
      this.methodExpressionZeroArg = methodExpressionZeroArg;
   }

   public void processValueChange(ValueChangeEvent valueChangeEvent) throws AbortProcessingException {
      if (valueChangeEvent == null) {
         throw new NullPointerException();
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         ELContext elContext = context.getELContext();

         try {
            this.methodExpressionOneArg.invoke(elContext, new Object[]{valueChangeEvent});
         } catch (MethodNotFoundException var5) {
            if (null != this.methodExpressionZeroArg) {
               this.methodExpressionZeroArg.invoke(elContext, new Object[0]);
            }
         }

      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return new Object[]{this.methodExpressionOneArg, this.methodExpressionZeroArg};
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         this.methodExpressionOneArg = (MethodExpression)((Object[])((Object[])state))[0];
         this.methodExpressionZeroArg = (MethodExpression)((Object[])((Object[])state))[1];
      }
   }

   public boolean isTransient() {
      return this.isTransient;
   }

   public void setTransient(boolean newTransientValue) {
      this.isTransient = newTransientValue;
   }
}
