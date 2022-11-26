package weblogic.work.concurrent;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class ContextProxyInvocationHandler implements InvocationHandler, Serializable {
   private static final long serialVersionUID = -6941924605581793420L;
   protected final ContextProvider contextSetupProcessor;
   protected final String seqID;
   protected final ContextHandle capturedContextHandle;
   protected final Object proxiedObject;
   protected Map executionProperties;

   public ContextProxyInvocationHandler(String seqID, ContextProvider contextSetupProcessor, Object proxiedObject, Map executionProperties) {
      this.contextSetupProcessor = contextSetupProcessor;
      this.proxiedObject = proxiedObject;
      this.seqID = seqID;
      this.executionProperties = executionProperties;
      this.capturedContextHandle = contextSetupProcessor.save(executionProperties);
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object result = null;
      Class methodDeclaringClass = method.getDeclaringClass();
      if (methodDeclaringClass == Object.class) {
         result = method.invoke(this.proxiedObject, args);
      } else {
         ContextHandle contextHandleForReset;
         try {
            contextHandleForReset = this.contextSetupProcessor.setup(this.capturedContextHandle);
         } catch (IllegalStateException var13) {
            ConcurrencyLogger.logProxyStateError(method.getName(), var13);
            String msg = ConcurrencyLogger.logProxyStateErrorLoggable(method.getName(), var13).getMessage();
            throw new IllegalStateException(msg, var13);
         }

         try {
            result = method.invoke(this.proxiedObject, args);
         } finally {
            this.contextSetupProcessor.reset(contextHandleForReset);
         }
      }

      return result;
   }

   public Map getExecutionProperties() {
      if (this.executionProperties == null) {
         return null;
      } else {
         Map copy = new HashMap();
         copy.putAll(this.executionProperties);
         return copy;
      }
   }

   public String getSeqID() {
      return this.seqID;
   }
}
