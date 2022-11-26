package weblogic.connector.outbound;

import java.lang.ref.Reference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.AccessController;
import javax.resource.ResourceException;
import weblogic.connector.common.Debug;
import weblogic.connector.common.Utils;
import weblogic.connector.transaction.outbound.TxConnectionHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;

public final class ConnectionWrapper implements InvocationHandler {
   private ConnectionPool connPool = null;
   private ConnectionInfo connInfo = null;
   private Object connInstance = null;
   private Reference ref = null;
   private int hash;

   private ConnectionWrapper(ConnectionPool pool, ConnectionInfo connInfo, Object connInstance) {
      this.connInstance = connInstance;
      this.connPool = pool;
      this.connInfo = connInfo;
      this.connInfo.setLastUsedTime(System.currentTimeMillis());
      this.connInfo.setUsed(true);
      if (connInstance != null) {
         this.hash = connInstance.hashCode();
      } else {
         this.hash = super.hashCode();
      }

      if (pool != null && pool.getConnectionProfilingEnabled()) {
         String stkMsg = Debug.getExceptionStackTrace() + PlatformConstants.EOL;
         Throwable stackTraceSource = new Throwable(stkMsg);
         String stackTraceAtCreate = StackTraceUtils.throwable2StackTrace(stackTraceSource);

         try {
            String stringToRemove = ":";
            stackTraceAtCreate = stackTraceAtCreate.substring(stackTraceAtCreate.indexOf(stringToRemove) + stringToRemove.length());
         } catch (Exception var8) {
         }

         this.connInfo.setAllocationCallStack(stackTraceAtCreate);
      }

   }

   private ConnectionWrapper(ConnectionPool pool, Object connInstance) {
      this.connInstance = connInstance;
      this.connPool = pool;
      this.connInfo = new ConnectionInfo();
      this.connInfo.setLastUsedTime(System.currentTimeMillis());
      this.connInfo.setUsed(true);
      if (connInstance != null) {
         this.hash = connInstance.hashCode();
      } else {
         this.hash = super.hashCode();
      }

   }

   public static Object createConnectionWrapper(ConnectionPool pool, ConnectionInfo connInfo, Object connInstance) {
      Class clz = connInstance.getClass();
      Class[] classes = Utils.getInterfaces(clz);
      return Proxy.newProxyInstance(clz.getClassLoader(), classes, new ConnectionWrapper(pool, connInfo, connInstance));
   }

   public static Object createProxyTestConnectionWrapper(ConnectionPool pool, Object connInstance) {
      Class clz = connInstance.getClass();
      Class[] classes = Utils.getInterfaces(clz);
      return Proxy.newProxyInstance(clz.getClassLoader(), classes, new ConnectionWrapper(pool, connInstance));
   }

   public void setWeakReference(Reference ref) {
      this.ref = ref;
   }

   public int hashCode() {
      return this.hash;
   }

   public Object invoke(Object unusedProxy, Method method, Object[] args) throws ResourceException, Throwable {
      this.connInfo.setLastUsedTime(System.currentTimeMillis());
      this.connInfo.setUsed(true);
      ConnectionHandler connectionHandler = this.connInfo.getConnectionHandler();
      if (connectionHandler != null && connectionHandler instanceof TxConnectionHandler && !method.getName().equals("close")) {
         ((TxConnectionHandler)connectionHandler).enListResource();
      }

      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      Throwable te;
      try {
         return this.connPool.getRAInstanceManager().getAdapterLayer().invoke(method, this.connInstance, args, kernelId);
      } catch (IllegalAccessException var8) {
         throw var8;
      } catch (InvocationTargetException var9) {
         te = var9.getCause();
         if (te != null) {
            if (te instanceof ResourceException) {
               throw (ResourceException)te;
            } else {
               throw te;
            }
         } else {
            throw var9;
         }
      } catch (UndeclaredThrowableException var10) {
         te = var10.getUndeclaredThrowable();
         if (te != null) {
            if (te instanceof ResourceException) {
               throw (ResourceException)te;
            } else {
               throw te;
            }
         } else {
            throw var10;
         }
      }
   }

   protected synchronized void finalize() throws Throwable {
      Debug.enter(this, "finalize() for " + this.connInfo);

      try {
         this.connInfo.getConnectionHandler().connectionFinalized(this.ref);
      } finally {
         super.finalize();
         Debug.exit(this, "finalize() for " + this.connInfo);
      }

   }

   protected Object getConnectionInstance() {
      return this.connInstance;
   }
}
