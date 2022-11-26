package javax.faces.event;

import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

public class MethodExpressionActionListener implements ActionListener, StateHolder {
   private static final Logger LOGGER = Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
   private MethodExpression methodExpressionOneArg = null;
   private MethodExpression methodExpressionZeroArg = null;
   private boolean isTransient;
   private static final Class[] ACTION_LISTENER_ZEROARG_SIG = new Class[0];

   public MethodExpressionActionListener() {
   }

   public MethodExpressionActionListener(MethodExpression methodExpressionOneArg) {
      this.methodExpressionOneArg = methodExpressionOneArg;
      FacesContext context = FacesContext.getCurrentInstance();
      ELContext elContext = context.getELContext();
      this.methodExpressionZeroArg = context.getApplication().getExpressionFactory().createMethodExpression(elContext, methodExpressionOneArg.getExpressionString(), Void.class, ACTION_LISTENER_ZEROARG_SIG);
   }

   public MethodExpressionActionListener(MethodExpression methodExpressionOneArg, MethodExpression methodExpressionZeroArg) {
      this.methodExpressionOneArg = methodExpressionOneArg;
      this.methodExpressionZeroArg = methodExpressionZeroArg;
   }

   public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
      if (actionEvent == null) {
         throw new NullPointerException();
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         ELContext elContext = context.getELContext();

         try {
            try {
               this.methodExpressionOneArg.invoke(elContext, new Object[]{actionEvent});
            } catch (MethodNotFoundException var5) {
               this.methodExpressionZeroArg.invoke(elContext, new Object[0]);
            }

         } catch (ELException var6) {
            if (var6.getCause() instanceof AbortProcessingException) {
               throw (AbortProcessingException)var6.getCause();
            } else {
               throw var6;
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
