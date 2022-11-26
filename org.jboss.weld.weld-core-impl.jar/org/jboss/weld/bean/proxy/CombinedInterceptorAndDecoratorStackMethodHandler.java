package org.jboss.weld.bean.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.interceptor.proxy.InterceptorMethodHandler;
import org.jboss.weld.util.reflection.Reflections;

public class CombinedInterceptorAndDecoratorStackMethodHandler implements StackAwareMethodHandler, Serializable {
   public static final CombinedInterceptorAndDecoratorStackMethodHandler NULL_INSTANCE = new CombinedInterceptorAndDecoratorStackMethodHandler() {
      public void setInterceptorMethodHandler(InterceptorMethodHandler interceptorMethodHandler) {
         throw new UnsupportedOperationException();
      }

      public void setOuterDecorator(Object outerDecorator) {
         throw new UnsupportedOperationException();
      }

      public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
         throw new UnsupportedOperationException();
      }
   };
   private InterceptorMethodHandler interceptorMethodHandler;
   private Object outerDecorator;

   public void setInterceptorMethodHandler(InterceptorMethodHandler interceptorMethodHandler) {
      this.interceptorMethodHandler = interceptorMethodHandler;
   }

   public void setOuterDecorator(Object outerDecorator) {
      this.outerDecorator = outerDecorator;
   }

   public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      throw new UnsupportedOperationException();
   }

   public Object invoke(InterceptionDecorationContext.Stack stack, Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
      if (stack == null) {
         stack = InterceptionDecorationContext.getStack();
         return this.invoke(stack, self, thisMethod, proceed, args, true, stack.startIfNotOnTop(this));
      } else {
         boolean intercept = stack.startIfNotOnTop(this);
         return this.invoke(stack, self, thisMethod, proceed, args, intercept, intercept);
      }
   }

   public Object invoke(InterceptionDecorationContext.Stack stack, Object self, Method thisMethod, Method proceed, Object[] args, boolean intercept, boolean popStack) throws Throwable {
      if (intercept) {
         label118: {
            Object var8;
            try {
               if (this.interceptorMethodHandler == null) {
                  if (this.outerDecorator == null) {
                     break label118;
                  }

                  SecurityActions.ensureAccessible(thisMethod);
                  var8 = Reflections.invokeAndUnwrap(this.outerDecorator, thisMethod, args);
                  return var8;
               }

               if (proceed == null) {
                  var8 = this.interceptorMethodHandler.invoke(stack, self, thisMethod, (Method)null, args);
                  return var8;
               }

               if (this.outerDecorator != null) {
                  var8 = this.interceptorMethodHandler.invoke(stack, this.outerDecorator, thisMethod, thisMethod, args);
                  return var8;
               }

               var8 = this.interceptorMethodHandler.invoke(stack, self, thisMethod, proceed, args);
            } finally {
               if (popStack) {
                  stack.end();
               }

            }

            return var8;
         }
      }

      SecurityActions.ensureAccessible(proceed);
      return Reflections.invokeAndUnwrap(self, proceed, args);
   }

   public InterceptorMethodHandler getInterceptorMethodHandler() {
      return this.interceptorMethodHandler;
   }

   public Object getOuterDecorator() {
      return this.outerDecorator;
   }

   public boolean isDisabledHandler() {
      return this == InterceptionDecorationContext.peekIfNotEmpty();
   }

   public boolean isDisabledHandler(InterceptionDecorationContext.Stack stack) {
      return this == stack.peek();
   }
}
