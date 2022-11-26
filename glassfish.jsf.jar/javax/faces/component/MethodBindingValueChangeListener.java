package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

class MethodBindingValueChangeListener extends MethodBindingAdapterBase implements ValueChangeListener, StateHolder {
   private MethodBinding methodBinding = null;
   private boolean tranzient = false;

   public MethodBindingValueChangeListener() {
   }

   public MethodBindingValueChangeListener(MethodBinding methodBinding) {
      this.methodBinding = methodBinding;
   }

   public MethodBinding getWrapped() {
      return this.methodBinding;
   }

   public void processValueChange(ValueChangeEvent actionEvent) throws AbortProcessingException {
      if (actionEvent == null) {
         throw new NullPointerException();
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            this.methodBinding.invoke(context, new Object[]{actionEvent});
         } catch (EvaluationException var4) {
            Throwable cause = this.getExpectedCause(AbortProcessingException.class, var4);
            if (cause instanceof AbortProcessingException) {
               throw (AbortProcessingException)cause;
            } else if (cause instanceof RuntimeException) {
               throw (RuntimeException)cause;
            } else {
               throw new IllegalStateException(var4);
            }
         }
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
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
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (null != state) {
         if (!(state instanceof MethodBinding)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodBinding result = null;
            if (null != className) {
               Class toRestoreClass;
               try {
                  toRestoreClass = loadClass(className, this);
               } catch (ClassNotFoundException var10) {
                  throw new IllegalStateException(var10);
               }

               if (null != toRestoreClass) {
                  try {
                     result = (MethodBinding)toRestoreClass.newInstance();
                  } catch (IllegalAccessException | InstantiationException var9) {
                     throw new IllegalStateException(var9);
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
