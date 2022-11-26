package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

class MethodBindingValidator extends MethodBindingAdapterBase implements Validator, StateHolder {
   private MethodBinding methodBinding = null;
   private boolean tranzient = false;

   public MethodBindingValidator() {
   }

   public MethodBindingValidator(MethodBinding methodBinding) {
      this.methodBinding = methodBinding;
   }

   public MethodBinding getWrapped() {
      return this.methodBinding;
   }

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      if (null != context && null != component) {
         try {
            this.methodBinding.invoke(context, new Object[]{context, component, value});
         } catch (EvaluationException var6) {
            Throwable cause = this.getExpectedCause(ValidatorException.class, var6);
            if (cause instanceof ValidatorException) {
               throw (ValidatorException)cause;
            } else if (cause instanceof RuntimeException) {
               throw (RuntimeException)cause;
            } else {
               throw new IllegalStateException(var6);
            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   public Object saveState(FacesContext context) {
      Object result = null;
      if (!this.tranzient) {
         if (this.methodBinding instanceof StateHolder) {
            Object[] stateStruct = new Object[]{((StateHolder)this.methodBinding).saveState(context), this.methodBinding.getClass().getName()};
            result = stateStruct;
         } else {
            result = this.methodBinding;
         }
      }

      return result;
   }

   public void restoreState(FacesContext context, Object state) {
      if (null != state) {
         if (!(state instanceof MethodBinding)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodBinding result = null;
            if (null != className) {
               Class toRestoreClass;
               try {
                  toRestoreClass = loadClass(className, this);
               } catch (ClassNotFoundException var11) {
                  throw new IllegalStateException(var11);
               }

               if (null != toRestoreClass) {
                  try {
                     result = (MethodBinding)toRestoreClass.newInstance();
                  } catch (InstantiationException var9) {
                     throw new IllegalStateException(var9);
                  } catch (IllegalAccessException var10) {
                     throw new IllegalStateException(var10);
                  }
               }

               if (null != result && null != savedState) {
                  ((StateHolder)result).restoreState(context, savedState);
               }

               this.methodBinding = result;
            }
         } else {
            this.methodBinding = (MethodBinding)state;
         }

      }
   }

   public boolean isTransient() {
      return this.tranzient;
   }

   public void setTransient(boolean newTransientValue) {
      this.tranzient = newTransientValue;
   }

   private static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return Class.forName(name, false, loader);
   }
}
