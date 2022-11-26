package javax.faces.component;

import java.io.Serializable;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

class ValueBindingValueExpressionAdapter extends ValueBinding implements StateHolder, Serializable {
   private static final long serialVersionUID = -8015491904782686906L;
   private ValueExpression valueExpression = null;
   private boolean tranzient;

   public ValueBindingValueExpressionAdapter() {
   }

   ValueBindingValueExpressionAdapter(ValueExpression valueExpression) {
      this.valueExpression = valueExpression;
   }

   public String getExpressionString() {
      assert null != this.valueExpression;

      return this.valueExpression.getExpressionString();
   }

   public Class getType(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesContext -> null");
      } else {
         Class result = null;

         try {
            result = this.valueExpression.getType(context.getELContext());
            return result;
         } catch (javax.el.PropertyNotFoundException var4) {
            throw new PropertyNotFoundException(var4);
         } catch (ELException var5) {
            throw new EvaluationException(var5);
         }
      }
   }

   public Object getValue(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesContext -> null");
      } else {
         Object result = null;

         try {
            result = this.valueExpression.getValue(context.getELContext());
            return result;
         } catch (javax.el.PropertyNotFoundException var4) {
            throw new PropertyNotFoundException(var4);
         } catch (ELException var5) {
            throw new EvaluationException(var5);
         }
      }
   }

   public boolean isReadOnly(FacesContext context) throws EvaluationException, PropertyNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesContext -> null");
      } else {
         boolean result = false;

         try {
            result = this.valueExpression.isReadOnly(context.getELContext());
            return result;
         } catch (ELException var4) {
            throw new EvaluationException(var4);
         }
      }
   }

   public void setValue(FacesContext context, Object value) throws EvaluationException, PropertyNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesContext -> null");
      } else {
         try {
            this.valueExpression.setValue(context.getELContext(), value);
         } catch (javax.el.PropertyNotFoundException var4) {
            throw new PropertyNotFoundException(var4);
         } catch (PropertyNotWritableException var5) {
            throw new PropertyNotFoundException(var5);
         } catch (ELException var6) {
            throw new EvaluationException(var6);
         }
      }
   }

   public boolean isTransient() {
      return this.tranzient;
   }

   public void setTransient(boolean tranzient) {
      this.tranzient = tranzient;
   }

   public Object saveState(FacesContext context) {
      Object result = null;
      if (!this.tranzient) {
         if (this.valueExpression instanceof StateHolder) {
            Object[] stateStruct = new Object[]{((StateHolder)this.valueExpression).saveState(context), this.valueExpression.getClass().getName()};
            result = stateStruct;
         } else {
            result = this.valueExpression;
         }
      }

      return result;
   }

   public void restoreState(FacesContext context, Object state) {
      if (null != state) {
         if (!(state instanceof ValueExpression)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            ValueExpression result = null;
            Class toRestoreClass = null;
            if (null != className) {
               try {
                  toRestoreClass = loadClass(className, this);
               } catch (ClassNotFoundException var11) {
                  throw new IllegalStateException(var11.getMessage());
               }

               if (null != toRestoreClass) {
                  try {
                     result = (ValueExpression)toRestoreClass.newInstance();
                  } catch (InstantiationException var9) {
                     throw new IllegalStateException(var9.getMessage());
                  } catch (IllegalAccessException var10) {
                     throw new IllegalStateException(var10.getMessage());
                  }
               }

               if (null != result && null != savedState) {
                  ((StateHolder)result).restoreState(context, savedState);
               }

               this.valueExpression = result;
            }
         } else {
            this.valueExpression = (ValueExpression)state;
         }

      }
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ValueBindingValueExpressionAdapter) {
         ValueExpression expr = ((ValueBindingValueExpressionAdapter)other).getWrapped();
         return this.valueExpression.equals(expr);
      } else {
         if (other instanceof ValueBinding) {
            FacesContext context = FacesContext.getCurrentInstance();
            ValueBinding otherVB = (ValueBinding)other;
            Class type = otherVB.getType(context);
            if (type != null) {
               return type.equals(this.valueExpression.getType(context.getELContext()));
            }
         }

         return false;
      }
   }

   public int hashCode() {
      assert null != this.valueExpression;

      return this.valueExpression.hashCode();
   }

   public ValueExpression getWrapped() {
      return this.valueExpression;
   }

   private static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return Class.forName(name, true, loader);
   }
}
