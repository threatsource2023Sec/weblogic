package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.framework.ReflectiveMethodInvocation;
import com.oracle.pitchfork.interfaces.ContextDataProvider;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.interceptor.InvocationContext;

public class JeeInterceptorInterceptor implements MethodInterceptor {
   private static final String INVOCATION_CONTEXT_KEY = InvocationContext.class.getName();
   private final InterceptorMetadataI im;
   private final InterceptionMetadata iim;
   private final Method aroundInvokeMethod;
   private final Object jeeInterceptorInstance;
   private final ContextDataProvider dProvider;

   private JeeInterceptorInterceptor(InterceptorMetadataI im, InterceptionMetadata iim, Object jeeInterceptorInstance, Method aroundInvokeMethod, ContextDataProvider dProvider) {
      this.im = im;
      this.iim = iim;
      this.aroundInvokeMethod = aroundInvokeMethod;
      this.jeeInterceptorInstance = jeeInterceptorInstance;
      if (iim == null) {
         this.dProvider = dProvider;
      } else {
         this.dProvider = iim.getContextDataProvider();
      }

   }

   public JeeInterceptorInterceptor(InterceptorMetadataI im, Object jeeInterceptorInstance, Method aroundInvoke, ContextDataProvider cdp) {
      this(im, (InterceptionMetadata)null, jeeInterceptorInstance, aroundInvoke, cdp);
   }

   public JeeInterceptorInterceptor(InterceptionMetadata iim, Object jeeInterceptorInstance, Method aroundInvoke) {
      this((InterceptorMetadataI)null, iim, jeeInterceptorInstance, aroundInvoke, (ContextDataProvider)null);
   }

   public JeeInterceptorInterceptor(Object jeeInterceptorInstance, Method aroundInvoke, ContextDataProvider cdp) {
      this((InterceptorMetadataI)null, (InterceptionMetadata)null, jeeInterceptorInstance, aroundInvoke, cdp);
   }

   public InterceptorMetadataI getInterceptorMetadata() {
      return this.im;
   }

   public InterceptionMetadata getInterceptionMetadata() {
      return this.iim;
   }

   public Object getJeeInterceptorInstance() {
      return this.jeeInterceptorInstance;
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      InvocationContext ic = this.lazyCreateInvocationContext((ReflectiveMethodInvocation)mi);
      if (this.aroundInvokeMethod != null) {
         try {
            return this.aroundInvokeMethod.invoke(this.jeeInterceptorInstance, ic);
         } catch (InvocationTargetException var4) {
            throw var4.getTargetException();
         } catch (IllegalAccessException var5) {
            throw new AssertionError(var5);
         }
      } else {
         return ic.proceed();
      }
   }

   private InvocationContext lazyCreateInvocationContext(ReflectiveMethodInvocation mi) {
      InvocationContext ic = (InvocationContext)mi.getUserAttributes().get(INVOCATION_CONTEXT_KEY);
      if (ic == null) {
         ic = new MethodInvocationInvocationContext(mi, this.dProvider);
         mi.getUserAttributes().put(INVOCATION_CONTEXT_KEY, ic);
      }

      return (InvocationContext)ic;
   }

   public String toString() {
      return "JeeInterceptorInterceptor: Wrapping <" + this.jeeInterceptorInstance + ">";
   }
}
