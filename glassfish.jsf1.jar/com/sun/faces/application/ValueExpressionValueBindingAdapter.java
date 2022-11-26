package com.sun.faces.application;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class ValueExpressionValueBindingAdapter extends ValueExpression implements Serializable, StateHolder {
   private static final long serialVersionUID = 2990621816592041196L;
   private ValueBinding binding = null;
   private boolean tranzient = false;

   public ValueExpressionValueBindingAdapter() {
   }

   public ValueExpressionValueBindingAdapter(ValueBinding binding) {
      assert null != binding;

      this.binding = binding;
   }

   public Object getValue(ELContext context) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         Object result = null;
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);

         assert null != facesContext;

         try {
            result = this.binding.getValue(facesContext);
            return result;
         } catch (Throwable var5) {
            throw new ELException(var5);
         }
      }
   }

   public void setValue(ELContext context, Object value) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);

         assert null != facesContext;

         try {
            this.binding.setValue(facesContext, value);
         } catch (Throwable var5) {
            throw new ELException(var5);
         }
      }
   }

   public boolean isReadOnly(ELContext context) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         boolean result = false;
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);

         assert null != facesContext;

         try {
            result = this.binding.isReadOnly(facesContext);
            return result;
         } catch (Throwable var5) {
            throw new ELException(var5);
         }
      }
   }

   public Class getType(ELContext context) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         Class result = null;
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);

         assert null != facesContext;

         try {
            result = this.binding.getType(facesContext);
            return result;
         } catch (Throwable var5) {
            throw new ELException(var5);
         }
      }
   }

   public boolean isLiteralText() {
      return false;
   }

   public Class getExpectedType() {
      assert null != this.binding;

      Class result = null;
      FacesContext context = FacesContext.getCurrentInstance();

      try {
         Object value = this.binding.getValue(context);
         result = value.getClass();
      } catch (Throwable var4) {
         result = null;
      }

      return result;
   }

   public String getExpressionString() {
      assert null != this.binding;

      return this.binding.getExpressionString();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ValueExpressionValueBindingAdapter) {
         ValueBinding vb = ((ValueExpressionValueBindingAdapter)other).getWrapped();
         return this.binding.equals(vb);
      } else {
         if (other instanceof ValueExpression) {
            FacesContext context = FacesContext.getCurrentInstance();
            ValueExpression otherVE = (ValueExpression)other;
            Class type = this.binding.getType(context);
            if (type != null) {
               return type.equals(otherVE.getType(context.getELContext()));
            }
         }

         return false;
      }
   }

   public int hashCode() {
      assert null != this.binding;

      return this.binding.hashCode();
   }

   public String getDelimiterSyntax() {
      return "";
   }

   public Object saveState(FacesContext context) {
      Object result = null;
      if (!this.tranzient) {
         if (this.binding instanceof StateHolder) {
            Object[] stateStruct = new Object[]{((StateHolder)this.binding).saveState(context), this.binding.getClass().getName()};
            result = stateStruct;
         } else {
            result = this.binding;
         }
      }

      return result;
   }

   public void restoreState(FacesContext context, Object state) {
      if (null != state) {
         if (!(state instanceof ValueBinding)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            ValueBinding result = null;
            Class toRestoreClass = null;
            if (null != className) {
               try {
                  toRestoreClass = loadClass(className, this);
               } catch (ClassNotFoundException var11) {
                  throw new IllegalStateException(var11.getMessage());
               }

               if (null != toRestoreClass) {
                  try {
                     result = (ValueBinding)toRestoreClass.newInstance();
                  } catch (InstantiationException var9) {
                     throw new IllegalStateException(var9.getMessage());
                  } catch (IllegalAccessException var10) {
                     throw new IllegalStateException(var10.getMessage());
                  }
               }

               if (null != result && null != savedState) {
                  ((StateHolder)result).restoreState(context, savedState);
               }

               this.binding = result;
            }
         } else {
            this.binding = (ValueBinding)state;
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

      return Class.forName(name, true, loader);
   }

   public ValueBinding getWrapped() {
      return this.binding;
   }
}
