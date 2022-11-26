package javax.faces.validator;

import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class MethodExpressionValidator implements Validator, StateHolder {
   private static final String BEANS_VALIDATION_AVAILABLE = "javax.faces.private.BEANS_VALIDATION_AVAILABLE";
   private static final String VALIDATE_EMPTY_FIELDS_PARAM_NAME = "javax.faces.VALIDATE_EMPTY_FIELDS";
   private MethodExpression methodExpression = null;
   private Boolean validateEmptyFields;
   private boolean transientValue;

   public MethodExpressionValidator() {
   }

   public MethodExpressionValidator(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (context != null && component != null) {
         if (this.validateEmptyFields(context) || value != null) {
            try {
               ELContext elContext = context.getELContext();
               this.methodExpression.invoke(elContext, new Object[]{context, component, value});
            } catch (ELException var6) {
               Throwable e = var6.getCause();
               if (e instanceof ValidatorException) {
                  throw (ValidatorException)e;
               }

               throw var6;
            }
         }

      } else {
         throw new NullPointerException();
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] values = new Object[]{this.methodExpression};
         return values;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] values = (Object[])((Object[])state);
         this.methodExpression = (MethodExpression)values[0];
      }
   }

   public boolean isTransient() {
      return this.transientValue;
   }

   public void setTransient(boolean transientValue) {
      this.transientValue = transientValue;
   }

   private boolean validateEmptyFields(FacesContext ctx) {
      if (this.validateEmptyFields == null) {
         ExternalContext extCtx = ctx.getExternalContext();
         String val = extCtx.getInitParameter("javax.faces.VALIDATE_EMPTY_FIELDS");
         if (null == val) {
            val = (String)extCtx.getApplicationMap().get("javax.faces.VALIDATE_EMPTY_FIELDS");
         }

         if (val != null && !"auto".equals(val)) {
            this.validateEmptyFields = Boolean.valueOf(val);
         } else {
            this.validateEmptyFields = this.isBeansValidationAvailable(ctx);
         }
      }

      return this.validateEmptyFields;
   }

   private boolean isBeansValidationAvailable(FacesContext context) {
      boolean result = false;
      Map appMap = context.getExternalContext().getApplicationMap();
      if (appMap.containsKey("javax.faces.private.BEANS_VALIDATION_AVAILABLE")) {
         result = (Boolean)appMap.get("javax.faces.private.BEANS_VALIDATION_AVAILABLE");
      } else {
         try {
            new BeanValidator();
            appMap.put("javax.faces.private.BEANS_VALIDATION_AVAILABLE", Boolean.TRUE);
            result = true;
         } catch (Throwable var5) {
            appMap.put("javax.faces.private.BEANS_VALIDATION_AVAILABLE", Boolean.FALSE);
         }
      }

      return result;
   }
}
