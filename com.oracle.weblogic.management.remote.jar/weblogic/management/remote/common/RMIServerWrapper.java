package weblogic.management.remote.common;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServer;
import javax.security.auth.Subject;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.jndi.ThreadLocalMap;

class RMIServerWrapper implements RMIServer {
   private RMIServer rmiServer;
   private Subject subject;
   private Locale locale;
   private final List connectionReferenceList;
   private Hashtable env;
   private ComponentInvocationContext cic;

   public RMIServerWrapper(RMIServer rmiServer, Subject subject, Hashtable env, Locale locale) {
      this(rmiServer, subject, env, locale, (ComponentInvocationContext)null);
   }

   public RMIServerWrapper(RMIServer rmiServer, Subject subject, Hashtable env, Locale locale, ComponentInvocationContext cic) {
      this.connectionReferenceList = new ArrayList();
      this.rmiServer = rmiServer;
      this.subject = subject;
      this.env = env;
      this.locale = locale;
      this.cic = cic;
   }

   public String getVersion() throws RemoteException {
      return this.rmiServer.getVersion();
   }

   Iterable getConnections() {
      ArrayList connections = new ArrayList();
      Iterator var2 = this.connectionReferenceList.iterator();

      while(var2.hasNext()) {
         WeakReference reference = (WeakReference)var2.next();
         this.addNonNullWrapper(connections, (RMIConnectionWrapper)reference.get());
      }

      return connections;
   }

   private void addNonNullWrapper(ArrayList collection, RMIConnectionWrapper wrapper) {
      if (wrapper != null) {
         collection.add(wrapper);
      }

   }

   public RMIConnection newClient(Object credentials) throws IOException {
      this.clearClientConnection((RMIConnectionWrapper)null);
      Hashtable origThreadInstance = RMIConnectionWrapper.pushEnvironment(this.env);

      RMIConnection var7;
      try {
         RMIConnection connection = this.rmiServer.newClient(credentials);
         RMIConnectionWrapper connectionWrapper = new RMIConnectionWrapper(connection, this.subject, this.locale, this, this.env);
         InvocationContextProxyHandler handler;
         if (this.cic != null) {
            handler = new InvocationContextProxyHandler(connectionWrapper, this.cic);
         } else {
            handler = new InvocationContextProxyHandler(connectionWrapper);
         }

         RMIConnection rmiConnectionWrapperProxy = (RMIConnection)Proxy.newProxyInstance(RMIConnection.class.getClassLoader(), new Class[]{RMIConnection.class}, handler);
         synchronized(this.connectionReferenceList) {
            this.connectionReferenceList.add(new WeakReference(connectionWrapper));
         }

         var7 = rmiConnectionWrapperProxy;
      } finally {
         RMIConnectionWrapper.popEnvironment(origThreadInstance, this.env);
      }

      return var7;
   }

   boolean setEnvOnThreadContext() {
      if (this.env == null) {
         return false;
      } else if (this.env.size() > 0) {
         ThreadLocalMap.push(this.env);
         return true;
      } else {
         return false;
      }
   }

   public void disconnected() {
      synchronized(this.connectionReferenceList) {
         Iterator var2 = this.connectionReferenceList.iterator();

         while(var2.hasNext()) {
            WeakReference aConnectionList = (WeakReference)var2.next();
            RMIConnectionWrapper rmiConnectionWrapper = (RMIConnectionWrapper)aConnectionList.get();
            if (rmiConnectionWrapper != null) {
               rmiConnectionWrapper.disconnected();
            }
         }

         this.connectionReferenceList.clear();
      }
   }

   void clearClientConnection(RMIConnectionWrapper clientConnection) {
      synchronized(this.connectionReferenceList) {
         Iterator iterator = this.connectionReferenceList.iterator();

         while(true) {
            RMIConnectionWrapper rmiConnectionWrapper;
            do {
               if (!iterator.hasNext()) {
                  return;
               }

               rmiConnectionWrapper = (RMIConnectionWrapper)((WeakReference)iterator.next()).get();
            } while(rmiConnectionWrapper != null && !rmiConnectionWrapper.equals(clientConnection));

            iterator.remove();
         }
      }
   }

   public void clearTimeouts() {
      if (this.env != null) {
         this.env.clear();
      }

   }
}
