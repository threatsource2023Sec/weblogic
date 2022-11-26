package javax.faces.component;

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
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

class MethodBindingMethodExpressionAdapter extends MethodBinding implements StateHolder, Serializable {
   private static final long serialVersionUID = 7334926223014401689L;
   private MethodExpression methodExpression = null;
   private boolean tranzient;

   public MethodBindingMethodExpressionAdapter() {
   }

   MethodBindingMethodExpressionAdapter(MethodExpression methodExpression) {
      this.methodExpression = methodExpression;
   }

   public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
      assert null != this.methodExpression;

      if (context == null) {
         throw new NullPointerException("FacesConext -> null");
      } else {
         Object result = null;

         try {
            result = this.methodExpression.invoke(context.getELContext(), params);
            return result;
         } catch (javax.el.MethodNotFoundException var6) {
            throw new MethodNotFoundException(var6);
         } catch (PropertyNotFoundException var7) {
            throw new EvaluationException(var7);
         } catch (ELException var8) {
            Throwable cause = var8.getCause();
            if (cause == null) {
               cause = var8;
            }

            throw new EvaluationException((Throwable)cause);
         } catch (NullPointerException var9) {
            throw new MethodNotFoundException(var9);
         }
      }
   }

   public Class getType(FacesContext context) throws MethodNotFoundException {
      assert null != this.methodExpression;

      if (context == null) {
         throw new NullPointerException("FacesConext -> null");
      } else {
         Class result = null;
         if (context == null) {
            throw new NullPointerException();
         } else {
            try {
               MethodInfo mi = this.methodExpression.getMethodInfo(context.getELContext());
               result = mi.getReturnType();
               return result;
            } catch (PropertyNotFoundException var4) {
               throw new MethodNotFoundException(var4);
            } catch (javax.el.MethodNotFoundException var5) {
               throw new MethodNotFoundException(var5);
            } catch (ELException var6) {
               throw new MethodNotFoundException(var6);
            }
         }
      }
   }

   public String getExpressionString() {
      assert null != this.methodExpression;

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
      assert null != this.methodExpression;

      return this.methodExpression.hashCode();
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
         if (this.methodExpression instanceof StateHolder) {
            Object[] stateStruct = new Object[]{((StateHolder)this.methodExpression).saveState(context), this.methodExpression.getClass().getName()};
            result = stateStruct;
         } else {
            result = this.methodExpression;
         }
      }

      return result;
   }

   public void restoreState(FacesContext context, Object state) {
      if (null != state) {
         if (!(state instanceof MethodExpression)) {
            Object[] stateStruct = (Object[])((Object[])state);
            Object savedState = stateStruct[0];
            String className = stateStruct[1].toString();
            MethodExpression result = null;
            Class toRestoreClass = null;
            if (null != className) {
               try {
                  toRestoreClass = loadClass(className, this);
               } catch (ClassNotFoundException var11) {
                  throw new IllegalStateException(var11.getMessage());
               }

               if (null != toRestoreClass) {
                  try {
                     result = (MethodExpression)toRestoreClass.newInstance();
                  } catch (InstantiationException var9) {
                     throw new IllegalStateException(var9.getMessage());
                  } catch (IllegalAccessException var10) {
                     throw new IllegalStateException(var10.getMessage());
                  }
               }

               if (null != result && null != savedState) {
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

   private static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return Class.forName(name, true, loader);
   }
}
