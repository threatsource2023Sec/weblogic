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
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

public class MethodBindingMethodExpressionAdapter extends MethodBinding implements StateHolder, Serializable {
   private static final long serialVersionUID = 6351778415298720238L;
   private MethodExpression methodExpression = null;
   private boolean tranzient;

   public MethodBindingMethodExpressionAdapter() {
   }

   public MethodBindingMethodExpressionAdapter(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesConext -> null");
      } else {
         try {
            return this.methodExpression.invoke(context.getELContext(), params);
         } catch (NullPointerException | javax.el.MethodNotFoundException var5) {
            throw new MethodNotFoundException(var5);
         } catch (PropertyNotFoundException var6) {
            throw new EvaluationException(var6);
         } catch (ELException var7) {
            Throwable cause = var7.getCause();
            if (cause == null) {
               cause = var7;
            }

            throw new EvaluationException((Throwable)cause);
         }
      }
   }

   public Class getType(FacesContext context) throws MethodNotFoundException {
      if (context == null) {
         throw new NullPointerException("FacesConext -> null");
      } else {
         try {
            return this.methodExpression.getMethodInfo(context.getELContext()).getReturnType();
         } catch (ELException var3) {
            throw new MethodNotFoundException(var3);
         }
      }
   }

   public String getExpressionString() {
      return this.methodExpression.getExpressionString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof MethodBindingMethodExpressionAdapter) {
         return this.methodExpression.equals(((MethodBindingMethodExpressionAdapter)other).getWrapped());
      } else {
         if (other instanceof MethodBinding) {
            MethodBinding binding = (MethodBinding)other;
            String expr = binding.getExpressionString();
            int idx = expr.indexOf(46);
            String target = expr.substring(0, idx).substring(2);
            String t = expr.substring(idx + 1);
            String method = t.substring(0, t.length() - 1);
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            MethodInfo controlInfo = this.methodExpression.getMethodInfo(elContext);
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

            Class type = binding.getType(context);
            Method[] methods = result.getClass().getMethods();
            Method[] var16 = methods;
            int var17 = methods.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               Method meth = var16[var18];
               if (meth.getName().equals(method) && type.equals(controlInfo.getReturnType()) && Arrays.equals(meth.getParameterTypes(), controlInfo.getParamTypes())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return this.methodExpression.hashCode();
   }

   public boolean isTransient() {
      return this.tranzient;
   }

   public void setTransient(boolean tranzient) {
      this.tranzient = tranzient;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object result = null;
         if (!this.tranzient) {
            if (this.methodExpression instanceof StateHolder) {
               Object[] stateStruct = new Object[]{((StateHolder)this.methodExpression).saveState(context), this.methodExpression.getClass().getName()};
               result = stateStruct;
            } else {
               result = this.methodExpression;
            }
         }

         return result;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         if (!(state instanceof MethodExpression)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodExpression result = null;
            if (className != null) {
               Class toRestoreClass = Util.loadClass2(className, this);
               if (toRestoreClass != null) {
                  result = (MethodExpression)Util.newInstance(toRestoreClass);
               }

               if (!Util.isAnyNull(result, savedState)) {
                  ((StateHolder)result).restoreState(context, savedState);
               }

               this.methodExpression = result;
            }
         } else {
            this.methodExpression = (MethodExpression)state;
         }

      }
   }

   public MethodExpression getWrapped() {
      return this.methodExpression;
   }
}
