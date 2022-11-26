package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.RemoteAccessException;
import com.bea.core.repackaged.springframework.remoting.RemoteConnectFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteProxyFailureException;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.UnknownHostException;

public abstract class RmiClientInterceptorUtils {
   private static final Log logger = LogFactory.getLog(RmiClientInterceptorUtils.class);

   @Nullable
   public static Object invokeRemoteMethod(MethodInvocation invocation, Object stub) throws InvocationTargetException {
      Method method = invocation.getMethod();

      try {
         if (method.getDeclaringClass().isInstance(stub)) {
            return method.invoke(stub, invocation.getArguments());
         } else {
            Method stubMethod = stub.getClass().getMethod(method.getName(), method.getParameterTypes());
            return stubMethod.invoke(stub, invocation.getArguments());
         }
      } catch (InvocationTargetException var4) {
         throw var4;
      } catch (NoSuchMethodException var5) {
         throw new RemoteProxyFailureException("No matching RMI stub method found for: " + method, var5);
      } catch (Throwable var6) {
         throw new RemoteProxyFailureException("Invocation of RMI stub method failed: " + method, var6);
      }
   }

   public static Exception convertRmiAccessException(Method method, Throwable ex, String message) {
      if (logger.isDebugEnabled()) {
         logger.debug(message, ex);
      }

      return (Exception)(ReflectionUtils.declaresException(method, RemoteException.class) ? new RemoteException(message, ex) : new RemoteAccessException(message, ex));
   }

   public static Exception convertRmiAccessException(Method method, RemoteException ex, String serviceName) {
      return convertRmiAccessException(method, ex, isConnectFailure(ex), serviceName);
   }

   public static Exception convertRmiAccessException(Method method, RemoteException ex, boolean isConnectFailure, String serviceName) {
      if (logger.isDebugEnabled()) {
         logger.debug("Remote service [" + serviceName + "] threw exception", ex);
      }

      if (ReflectionUtils.declaresException(method, ex.getClass())) {
         return ex;
      } else {
         return (Exception)(isConnectFailure ? new RemoteConnectFailureException("Could not connect to remote service [" + serviceName + "]", ex) : new RemoteAccessException("Could not access remote service [" + serviceName + "]", ex));
      }
   }

   public static boolean isConnectFailure(RemoteException ex) {
      return ex instanceof ConnectException || ex instanceof ConnectIOException || ex instanceof UnknownHostException || ex instanceof NoSuchObjectException || ex instanceof StubNotFoundException || ex.getCause() instanceof SocketException;
   }
}
