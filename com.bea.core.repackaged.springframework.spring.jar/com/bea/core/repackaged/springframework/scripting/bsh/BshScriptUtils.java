package com.bea.core.repackaged.springframework.scripting.bsh;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.Primitive;
import bsh.XThis;
import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class BshScriptUtils {
   public static Object createBshObject(String scriptSource) throws EvalError {
      return createBshObject(scriptSource, (Class[])null, (ClassLoader)null);
   }

   public static Object createBshObject(String scriptSource, @Nullable Class... scriptInterfaces) throws EvalError {
      return createBshObject(scriptSource, scriptInterfaces, ClassUtils.getDefaultClassLoader());
   }

   public static Object createBshObject(String scriptSource, @Nullable Class[] scriptInterfaces, @Nullable ClassLoader classLoader) throws EvalError {
      Object result = evaluateBshScript(scriptSource, scriptInterfaces, classLoader);
      if (result instanceof Class) {
         Class clazz = (Class)result;

         try {
            return ReflectionUtils.accessibleConstructor(clazz).newInstance();
         } catch (Throwable var6) {
            throw new IllegalStateException("Could not instantiate script class: " + clazz.getName(), var6);
         }
      } else {
         return result;
      }
   }

   @Nullable
   static Class determineBshObjectType(String scriptSource, @Nullable ClassLoader classLoader) throws EvalError {
      Assert.hasText(scriptSource, "Script source must not be empty");
      Interpreter interpreter = new Interpreter();
      if (classLoader != null) {
         interpreter.setClassLoader(classLoader);
      }

      Object result = interpreter.eval(scriptSource);
      if (result instanceof Class) {
         return (Class)result;
      } else {
         return result != null ? result.getClass() : null;
      }
   }

   static Object evaluateBshScript(String scriptSource, @Nullable Class[] scriptInterfaces, @Nullable ClassLoader classLoader) throws EvalError {
      Assert.hasText(scriptSource, "Script source must not be empty");
      Interpreter interpreter = new Interpreter();
      interpreter.setClassLoader(classLoader);
      Object result = interpreter.eval(scriptSource);
      if (result != null) {
         return result;
      } else if (ObjectUtils.isEmpty((Object[])scriptInterfaces)) {
         throw new IllegalArgumentException("Given script requires a script proxy: At least one script interface is required.\nScript: " + scriptSource);
      } else {
         XThis xt = (XThis)interpreter.eval("return this");
         return Proxy.newProxyInstance(classLoader, scriptInterfaces, new BshObjectInvocationHandler(xt));
      }
   }

   public static final class BshExecutionException extends NestedRuntimeException {
      private BshExecutionException(EvalError ex) {
         super("BeanShell script execution failed", ex);
      }

      // $FF: synthetic method
      BshExecutionException(EvalError x0, Object x1) {
         this(x0);
      }
   }

   private static class BshObjectInvocationHandler implements InvocationHandler {
      private final XThis xt;

      public BshObjectInvocationHandler(XThis xt) {
         this.xt = xt;
      }

      @Nullable
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (ReflectionUtils.isEqualsMethod(method)) {
            return this.isProxyForSameBshObject(args[0]);
         } else if (ReflectionUtils.isHashCodeMethod(method)) {
            return this.xt.hashCode();
         } else if (ReflectionUtils.isToStringMethod(method)) {
            return "BeanShell object [" + this.xt + "]";
         } else {
            try {
               Object result = this.xt.invokeMethod(method.getName(), args);
               if (result != Primitive.NULL && result != Primitive.VOID) {
                  return result instanceof Primitive ? ((Primitive)result).getValue() : result;
               } else {
                  return null;
               }
            } catch (EvalError var5) {
               throw new BshExecutionException(var5);
            }
         }
      }

      private boolean isProxyForSameBshObject(Object other) {
         if (!Proxy.isProxyClass(other.getClass())) {
            return false;
         } else {
            InvocationHandler ih = Proxy.getInvocationHandler(other);
            return ih instanceof BshObjectInvocationHandler && this.xt.equals(((BshObjectInvocationHandler)ih).xt);
         }
      }
   }
}
