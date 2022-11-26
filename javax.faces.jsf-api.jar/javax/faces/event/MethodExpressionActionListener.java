package javax.faces.event;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

public class MethodExpressionActionListener implements ActionListener, StateHolder {
   private static final Logger LOGGER = Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
   private MethodExpression methodExpression = null;
   private boolean isTransient;

   public MethodExpressionActionListener() {
   }

   public MethodExpressionActionListener(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
      if (actionEvent == null) {
         throw new NullPointerException();
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            this.methodExpression.invoke(elContext, new Object[]{actionEvent});
         } catch (ELException var5) {
            Throwable eeCause = var5.getCause();
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "severe.event.exception_invoking_processaction", new Object[]{eeCause == null ? var5.getClass().getName() : eeCause.getClass().getName(), this.methodExpression.getExpressionString(), actionEvent.getComponent().getId()});
               StringWriter writer = new StringWriter(1024);
               if (eeCause == null) {
                  var5.printStackTrace(new PrintWriter(writer));
               } else {
                  eeCause.printStackTrace(new PrintWriter(writer));
               }

               LOGGER.severe(writer.toString());
            }

            throw eeCause == null ? new AbortProcessingException(var5.getMessage(), var5) : new AbortProcessingException(var5.getMessage(), eeCause);
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
