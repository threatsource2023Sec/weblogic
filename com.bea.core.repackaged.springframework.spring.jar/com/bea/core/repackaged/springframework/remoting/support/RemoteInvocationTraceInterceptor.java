package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;

public class RemoteInvocationTraceInterceptor implements MethodInterceptor {
   protected static final Log logger = LogFactory.getLog(RemoteInvocationTraceInterceptor.class);
   private final String exporterNameClause;

   public RemoteInvocationTraceInterceptor() {
      this.exporterNameClause = "";
   }

   public RemoteInvocationTraceInterceptor(String exporterName) {
      this.exporterNameClause = exporterName + " ";
   }

   public Object invoke(MethodInvocation invocation) throws Throwable {
      Method method = invocation.getMethod();
      if (logger.isDebugEnabled()) {
         logger.debug("Incoming " + this.exporterNameClause + "remote call: " + ClassUtils.getQualifiedMethodName(method));
      }

      try {
         Object retVal = invocation.proceed();
         if (logger.isDebugEnabled()) {
            logger.debug("Finished processing of " + this.exporterNameClause + "remote call: " + ClassUtils.getQualifiedMethodName(method));
         }

         return retVal;
      } catch (Throwable var4) {
         if (!(var4 instanceof RuntimeException) && !(var4 instanceof Error)) {
            if (logger.isInfoEnabled()) {
               logger.info("Processing of " + this.exporterNameClause + "remote call resulted in exception: " + ClassUtils.getQualifiedMethodName(method), var4);
            }
         } else if (logger.isWarnEnabled()) {
            logger.warn("Processing of " + this.exporterNameClause + "remote call resulted in fatal exception: " + ClassUtils.getQualifiedMethodName(method), var4);
         }

         throw var4;
      }
   }
}
