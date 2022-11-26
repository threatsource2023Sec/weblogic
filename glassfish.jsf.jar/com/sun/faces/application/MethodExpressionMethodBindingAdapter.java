package com.sun.faces.application;

import com.sun.faces.util.Util;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

public class MethodExpressionMethodBindingAdapter extends MethodExpression implements Serializable, StateHolder {
   private static final long serialVersionUID = 5639227653537610567L;
   private MethodBinding binding = null;
   private transient MethodInfo info = null;
   private boolean tranzient = false;

   public MethodExpressionMethodBindingAdapter() {
   }

   public MethodExpressionMethodBindingAdapter(MethodBinding binding) {
      assert null != binding;

      this.binding = binding;
   }

   public MethodInfo getMethodInfo(ELContext context) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         if (null == this.info) {
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            if (null != facesContext) {
               try {
                  this.info = new MethodInfo((String)null, this.binding.getType(facesContext), (Class[])null);
               } catch (Exception var4) {
                  throw new ELException(var4);
               }
            }
         }

         return this.info;
      }
   }

   public Object invoke(ELContext context, Object[] params) throws ELException {
      assert null != this.binding;

      if (context == null) {
         throw new NullPointerException("ELContext -> null");
      } else {
         Object result = null;
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         if (null != facesContext) {
            try {
               result = this.binding.invoke(facesContext, params);
            } catch (Exception var6) {
               throw new ELException(var6);
            }
         }

         return result;
      }
   }

   public String getExpressionString() {
      assert null != this.binding;

      return this.binding.getExpressionString();
   }

   public boolean isLiteralText() {
      assert this.binding != null;

      String expr = this.binding.getExpressionString();
      return !expr.startsWith("#{") || !expr.endsWith("}");
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof MethodExpressionMethodBindingAdapter) {
         MethodBinding ob = ((MethodExpressionMethodBindingAdapter)other).getWrapped();
         return this.binding.equals(ob);
      } else {
         if (other instanceof MethodExpression) {
            MethodExpression expression = (MethodExpression)other;
            String expr = this.binding.getExpressionString();
            int idx = expr.indexOf(46);
            String target = expr.substring(0, idx).substring(2);
            String t = expr.substring(idx + 1);
            String method = t.substring(0, t.length() - 1);
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            MethodInfo controlInfo = expression.getMethodInfo(elContext);
            if (!controlInfo.getName().equals(method)) {
               return false;
            }

            ExpressionFactory factory = context.getApplication().getExpressionFactory();
            ValueExpression ve = factory.createValueExpression(elContext, "#{" + target + '}', Object.class);
            if (ve == null) {
               return false;
            }

            Object result = ve.getValue(elContext);
            if (result == null) {
               return false;
            }

            Method[] methods = result.getClass().getMethods();
            Method[] var15 = methods;
            int var16 = methods.length;

            for(int var17 = 0; var17 < var16; ++var17) {
               Method meth = var15[var17];
               if (meth.getName().equals(method) && meth.getReturnType().equals(controlInfo.getReturnType()) && Arrays.equals(meth.getParameterTypes(), controlInfo.getParamTypes())) {
                  return true;
               }
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
      if (context == null) {
         throw new NullPointerException();
      } else {
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
            if (className != null) {
               Class toRestoreClass = Util.loadClass2(className, this);
               if (toRestoreClass != null) {
                  result = (MethodBinding)Util.newInstance(toRestoreClass);
               }

               if (!Util.isAnyNull(result, savedState)) {
                  ((StateHolder)result).restoreState(context, savedState);
               }

               this.binding = result;
            }
         } else {
            this.binding = (MethodBinding)state;
         }

      }
   }

   public boolean isTransient() {
      return this.tranzient;
   }

   public void setTransient(boolean newTransientMethod) {
      this.tranzient = newTransientMethod;
   }

   public MethodBinding getWrapped() {
      return this.binding;
   }
}
