package weblogic.rmi.internal;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.dgc.DGCServerImpl;
import weblogic.utils.SyncKeyTable;

public final class OIDManager implements InitialReferenceConstants {
   private static final boolean DEBUG = false;
   private final Map identityTable = Collections.synchronizedMap(new IdentityHashMap());
   private final Map activatorTable = Collections.synchronizedMap(new HashMap());
   private final SyncKeyTable oidTable = new SyncKeyTable();
   private static boolean initializeDGCServer = false;
   private static int nextOID = 256;
   private static long sweptLease = 0L;

   private static void p(String msg) {
      System.out.println("<OIDManager> " + msg);
   }

   public static OIDManager getInstance() {
      return OIDManager.SingletonMaker.singleton;
   }

   public void ensureExported(ServerReference s) {
      this.renewLease(s);
      if (s.getDescriptor().isActivatable() && s instanceof ActivatableServerReference) {
         this.addActivator((ActivatableServerReference)s);
      } else if (this.identityTable.get(s.getImplementation()) == null) {
         this.addServerRef(s);
      }

   }

   private synchronized void addActivator(ActivatableServerReference s) {
      this.validateInitialReferences(s);
      this.activatorTable.put(s.getActivator(), s);
      this.oidTable.put(s);
   }

   private synchronized void addServerRef(ServerReference s) {
      this.validateInitialReferences(s);
      if (s.getImplementation() != null) {
         this.identityTable.put(s.getImplementation(), s);
      }

      this.oidTable.put(s);
   }

   private void validateInitialReferences(ServerReference s) {
      int oid = s.getObjectID();
      if (oid <= 256) {
         if (oid >= 0 && oid <= INITIAL_CLASS_NAMES.length) {
            String className = INITIAL_CLASS_NAMES[oid];
            if (className.equals("")) {
               throw new AssertionError("OID does not correspond to a known class: " + oid);
            } else {
               ClassLoader cl = s.getApplicationClassLoader();
               ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
               Class c1 = s.getDescriptor().getRemoteClass();
               Class c2 = null;

               try {
                  if (cl == null) {
                     if (contextClassLoader != null) {
                        try {
                           c2 = contextClassLoader.loadClass(className);
                        } catch (ClassNotFoundException var9) {
                        }
                     }

                     if (c2 == null) {
                        c2 = Class.forName(className);
                     }
                  } else {
                     c2 = cl.loadClass(className);
                  }
               } catch (ClassNotFoundException var10) {
                  throw new AssertionError("Remote class not found: " + className, var10);
               }

               if (c1 != c2) {
                  throw new AssertionError("OID and class mismatch: " + c1.getName() + " not equals " + c2.getName());
               }
            }
         } else {
            throw new AssertionError("OID invalid range: " + oid);
         }
      }
   }

   public StubReference getReplacement(Object o) throws RemoteException {
      if (!initializeDGCServer) {
         initializeDGCServer();
      }

      if (o instanceof CBVWrapper) {
         CBVWrapper wrapper = (CBVWrapper)o;
         o = wrapper.getDelegate();
      }

      if (o instanceof Activatable) {
         Activatable activatable = (Activatable)o;
         Object activationID = activatable.getActivationID();
         Activator activator = activatable.getActivator();
         ActivatableServerReference server = (ActivatableServerReference)this.activatorTable.get(activator);
         if (server == null) {
            server = this.makeActivatableServerReference(o, activator);
            this.addActivator(server);
         }

         this.renewLease(server);
         return server.getStubReference(activationID);
      } else {
         ServerReference server = (ServerReference)this.identityTable.get(o);
         if (server != null) {
            this.renewLease(server);
            return server.getStubReference();
         } else {
            server = this.makeServerReference(o);
            this.renewLease(server);
            this.addServerRef(server);
            return server.getStubReference();
         }
      }
   }

   private void renewLease(ServerReference server) {
      if (server instanceof Collectable) {
         ((Collectable)server).renewLease();
      }

   }

   private ServerReference makeServerReference(Object o) throws RemoteException {
      BasicRuntimeDescriptor dd = (BasicRuntimeDescriptor)DescriptorManager.getDescriptor(o);
      return dd.createServerReference(o);
   }

   private ActivatableServerReference makeActivatableServerReference(Object o, Activator activator) throws RemoteException {
      BasicRuntimeDescriptor dd = (BasicRuntimeDescriptor)DescriptorManager.getDescriptor(o);
      return dd.createActivatableServerReference(o, activator);
   }

   public ServerReference makeActivatableServerReference(Class c, Activator activator) throws RemoteException {
      BasicRuntimeDescriptor dd = (BasicRuntimeDescriptor)DescriptorManager.getDescriptor(c);
      return dd.createActivatableServerReference(c, activator);
   }

   public synchronized void initialize() throws RemoteException {
      initializeServer();
   }

   private static synchronized void initializeDGCServer() throws RemoteException {
      if (!initializeDGCServer) {
         initializeDGCServer = true;
         RJVMEnvironment.getEnvironment().ensureInitialized();
         ServerHelper.exportObject(DGCServerImpl.getDGCServerImpl());
      }
   }

   private static void initializeServer() {
      try {
         initializeDGCServer();
      } catch (RemoteException var1) {
         RMILogger.logRunDisabled(var1);
      }

   }

   public synchronized int getNextObjectID() {
      return ++nextOID;
   }

   public ServerReference getServerReference(int oid) throws NoSuchObjectException {
      ServerReference s = (ServerReference)this.oidTable.get(oid);
      if (s == null) {
         throw new NoSuchObjectException("The object identified by: '" + oid + "' could not be found.  Either it was not exported or it has been collected by the distributed garbage collector.");
      } else {
         return s;
      }
   }

   public ServerReference findServerReference(int oid) {
      return (ServerReference)this.oidTable.get(oid);
   }

   public ServerReference getServerReference(Object o) {
      return (ServerReference)this.identityTable.get(o);
   }

   public synchronized void sweep() {
      Enumeration oidEnum = this.oidTable.elements();

      while(oidEnum.hasMoreElements()) {
         Collectable enrollee = (Collectable)oidEnum.nextElement();
         enrollee.sweep(sweptLease);
      }

      ++sweptLease;
   }

   public int size() {
      return this.oidTable.size();
   }

   public long getNewLease() {
      return sweptLease + 1L;
   }

   public synchronized ServerReference removeServerReference(ServerReference sRef) {
      ServerReference tableSRef = (ServerReference)this.oidTable.remove(sRef.getObjectID());
      if (sRef.getImplementation() != null) {
         this.identityTable.remove(sRef.getImplementation());
      }

      return tableSRef;
   }

   public synchronized ServerReference removeServerReference(Activator activator) {
      ServerReference sRef = (ServerReference)this.activatorTable.remove(activator);
      if (sRef != null) {
         this.oidTable.remove(sRef.getObjectID());
      }

      return sRef;
   }

   public static boolean isSystemObject(int oid) {
      return isInitialReferenceOid(oid) || getImplementation(oid) instanceof SystemObject;
   }

   private static boolean isInitialReferenceOid(int oid) {
      return oid <= 256;
   }

   private static Object getImplementation(int oid) {
      ServerReference reference = (ServerReference)getInstance().oidTable.get(oid);
      return reference == null ? null : reference.getImplementation();
   }

   public static boolean requiresAuthentication(int oid) {
      return !(getImplementation(oid) instanceof Insecure);
   }

   static class SingletonMaker {
      static final OIDManager singleton = new OIDManager();
   }
}
