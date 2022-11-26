package weblogic.management.remote.common;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;
import javax.security.auth.Subject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.ThreadLocalMap;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.security.Security;

public class WLSRMIConnector extends RMIConnector implements DisconnectListener, WLSJMXConnector {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   private Subject subject;
   private final RMIServerWrapper server;
   private DisconnectMonitor monitor;
   private ClassLoader jmxLoader;
   private Map env;
   private ComponentInvocationContext cic;
   private ComponentInvocationContextManager securedCICM;
   private static Method isLocalMethod;

   public WLSRMIConnector(RMIServerWrapper rmiServer, Map map, Subject subject, ClassLoader jmxLoader) {
      this(rmiServer, map, subject, jmxLoader, (ComponentInvocationContext)null, (ComponentInvocationContextManager)null);
   }

   public WLSRMIConnector(RMIServerWrapper rmiServer, Map map, Subject subject, ClassLoader jmxLoader, ComponentInvocationContext cic, ComponentInvocationContextManager securedCICM) {
      super(rmiServer, map);
      this.subject = subject;
      this.server = rmiServer;
      this.jmxLoader = jmxLoader;
      this.env = map;
      this.cic = cic;
      this.securedCICM = securedCICM;
      if (!this.checkLocalWithReflection(rmiServer)) {
         try {
            this.monitor = DisconnectMonitorListImpl.getDisconnectMonitor();
            this.monitor.addDisconnectListener(rmiServer, this);
         } catch (DisconnectMonitorUnavailableException var8) {
         }

      }
   }

   private boolean checkLocalWithReflection(RMIServer rmiServer) {
      if (isLocalMethod != null) {
         try {
            return (Boolean)isLocalMethod.invoke((Object)null, rmiServer);
         } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4);
         }
      } else {
         return false;
      }
   }

   public void onDisconnect(DisconnectEvent event) {
      ClassLoader old = this.pushJMXClassLoader();

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLSRMIConnector: onDisconnect ");
         }

         this.server.disconnected();
         this.close();
      } catch (IOException var7) {
      } finally {
         this.popJMXClassLoader(old);
      }

   }

   private ClassLoader pushJMXClassLoader() {
      final Thread t = Thread.currentThread();
      ClassLoader old = t.getContextClassLoader();
      if (this.jmxLoader != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               t.setContextClassLoader(WLSRMIConnector.this.jmxLoader);
               return null;
            }
         });
      }

      return old;
   }

   private void popJMXClassLoader(final ClassLoader old) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            Thread.currentThread().setContextClassLoader(old);
            return null;
         }
      });
   }

   public void connect() throws IOException {
      ClassLoader old = this.pushJMXClassLoader();

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSRMIConnector.this.doConnect();
               return null;
            }
         });
      } catch (PrivilegedActionException var6) {
         throw (IOException)var6.getException();
      } finally {
         this.popJMXClassLoader(old);
      }

   }

   private void doConnect() throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLSRMIConnector: doConnect ");
      }

      super.connect();
   }

   public synchronized void connect(Map map) throws IOException {
      final Map finalMap = map;
      ClassLoader old = this.pushJMXClassLoader();

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSRMIConnector.this.doConnect(finalMap);
               return null;
            }
         });
      } catch (PrivilegedActionException var8) {
         throw (IOException)var8.getException();
      } finally {
         this.popJMXClassLoader(old);
      }

   }

   private synchronized void doConnect(Map map) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLSRMIConnector: doConnect ");
      }

      Long timeout = null;
      String providerUrl = null;
      if (map != null) {
         timeout = (Long)map.get("jmx.remote.x.request.waiting.timeout");
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLSRMIConnector: doConnect - jmx.remote.x.request.waiting.timeout = " + timeout);
         }

         providerUrl = (String)map.get("java.naming.provider.url");
      }

      try {
         Hashtable ht = new Hashtable();
         if (timeout != null || providerUrl != null) {
            if (timeout != null) {
               ht.put("weblogic.jndi.responseReadTimeout", timeout);
               ht.put("weblogic.jndi.connectTimeout", timeout);
            }

            if (providerUrl != null) {
               ht.put("java.naming.provider.url", providerUrl);
            }

            ThreadLocalMap.push(ht);
         }

         if (this.cic != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("WLSRMIConnector: Register ComponentInvocationContext: cic = " + this.cic);
            }

            ManagedInvocationContext mic = this.securedCICM.setCurrentComponentInvocationContext(this.cic);
            Throwable var6 = null;

            try {
               super.connect(map);
            } catch (Throwable var22) {
               var6 = var22;
               throw var22;
            } finally {
               if (mic != null) {
                  if (var6 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var21) {
                        var6.addSuppressed(var21);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            super.connect(map);
         }
      } finally {
         if (timeout != null) {
            ThreadLocalMap.pop();
         }

      }

   }

   public void close() throws IOException {
      ClassLoader old = this.pushJMXClassLoader();

      try {
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WLSRMIConnector.this.removeDisconnectListener();
               WLSRMIConnector.this.doClose();
               return null;
            }
         });
         this.server.clearTimeouts();
      } catch (PrivilegedActionException var6) {
         throw (IOException)var6.getException();
      } finally {
         this.popJMXClassLoader(old);
      }

   }

   private void doClose() throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WLSRMIConnector: doClose ");
      }

      super.close();

      try {
         Class cls = Class.forName("weblogic.corba.j2ee.naming.ORBHelper");
         Method meth = cls.getDeclaredMethod("removeCurrentClientSecurityContext");
         meth.invoke(cls);
      } catch (NoClassDefFoundError var3) {
      } catch (Exception var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WLSRMIConnector: doClose: exception when removeCurrentClientSecurityContext: " + var4);
         }
      }

   }

   private void removeDisconnectListener() {
      try {
         if (this.monitor != null) {
            this.monitor.removeDisconnectListener(this.server, this);
         }
      } catch (DisconnectMonitorUnavailableException var2) {
      }

   }

   public synchronized MBeanServerConnection getMBeanServerConnection(Locale locale) throws IOException {
      ClassLoader old = this.pushJMXClassLoader();

      MBeanServerConnection var4;
      try {
         MBeanServerConnection connection = super.getMBeanServerConnection();
         if (this.server != null) {
            String connId = this.getConnectionId();
            synchronized(this.server) {
               Iterator var6 = this.server.getConnections().iterator();

               while(var6.hasNext()) {
                  RMIConnectionWrapper rmiConnWrapper = (RMIConnectionWrapper)var6.next();
                  if (rmiConnWrapper != null && connId.equals(rmiConnWrapper.getConnectionId())) {
                     rmiConnWrapper.setLocale(locale);
                     break;
                  }
               }
            }

            MBeanServerConnection var5 = connection;
            return var5;
         }

         var4 = connection;
      } finally {
         this.popJMXClassLoader(old);
      }

      return var4;
   }

   public synchronized MBeanServerConnection getMBeanServerConnection(Subject delegationSubject, Locale locale) throws IOException {
      ClassLoader old = this.pushJMXClassLoader();

      try {
         MBeanServerConnection connection = super.getMBeanServerConnection(delegationSubject);
         if (this.server == null) {
            MBeanServerConnection var11 = connection;
            return var11;
         } else {
            String connId = this.getConnectionId();
            Iterator var6 = this.server.getConnections().iterator();

            while(true) {
               if (var6.hasNext()) {
                  RMIConnectionWrapper rmiConnWrapper = (RMIConnectionWrapper)var6.next();
                  if (!connId.equals(rmiConnWrapper.getConnectionId())) {
                     continue;
                  }

                  rmiConnWrapper.setLocale(delegationSubject, locale);
               }

               MBeanServerConnection var12 = connection;
               return var12;
            }
         }
      } finally {
         this.popJMXClassLoader(old);
      }
   }

   static {
      Class serverHelperClass = null;

      try {
         serverHelperClass = Class.forName("weblogic.rmi.extensions.server.ServerHelper");
      } catch (ClassNotFoundException var4) {
         isLocalMethod = null;
      }

      if (serverHelperClass != null) {
         Class[] arguments = new Class[]{Remote.class};

         try {
            isLocalMethod = serverHelperClass.getDeclaredMethod("isLocal", arguments);
         } catch (NoSuchMethodException var3) {
            isLocalMethod = null;
         }
      }

   }
}
