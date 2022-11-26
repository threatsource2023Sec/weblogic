package weblogic.transaction.internal;

import java.security.PrivilegedExceptionAction;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

public final class JNDIAdvertiser extends CoordinatorFactory {
   private static final String TX_ROOT = PlatformHelper.getPlatformHelper().getRootName();
   private static final String COORDINATORS;
   private static final String RESOURCES;
   private static final String NONXARESOURCES;
   static final String DUMMYOBJECT = "DUMMY";
   private static Context rootCtx;
   private static Context txRootCtx;
   private static Context resourceCtx;
   private static Context resourceServerCtx;
   private static Context nonXAResourceCtx;
   private static Context nonXAResourceServerCtx;
   private static Context serverCtx;
   private static JNDIAdvertiser instance;

   static void initialize(String aServerName) throws NamingException {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         rootCtx = getInitialContext((String)null);
         rootCtx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
         txRootCtx = rootCtx.createSubcontext(TX_ROOT);
         resourceCtx = rootCtx.createSubcontext(RESOURCES);
         nonXAResourceCtx = rootCtx.createSubcontext(NONXARESOURCES);
         serverCtx = rootCtx.createSubcontext(COORDINATORS);
         resourceCtx.createSubcontext(aServerName);
         nonXAResourceCtx.createSubcontext(aServerName);
      }
   }

   static boolean isResourceAdvertisedAt(String resName, String scName) {
      if (!PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         return false;
      } else {
         try {
            Context ctx = getResourceContext();
            Object o = PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction(scName + "." + resName, ctx), (String)null, "isResourceAdvertisedAt");
            if (o != null) {
               return true;
            }
         } catch (Exception var4) {
         }

         return false;
      }
   }

   public static JNDIAdvertiser getInstance() {
      return instance = instance != null ? instance : new JNDIAdvertiser();
   }

   static void advertiseResource(String resName) throws Exception {
      String name = null;
      Context ctx = getResourceContext();
      name = getServerName() + "." + resName;
      ServerTransactionManagerImpl tm = (ServerTransactionManagerImpl)getTM();
      PlatformHelper.getPlatformHelper().runKernelAction(new AdvertiseResourceAction(ctx, name, "DUMMY"), "advertiseResource");
   }

   static void unAdvertiseResource(String resName) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getResourceServerContext();
            ServerTransactionManagerImpl tm = (ServerTransactionManagerImpl)getTM();
            PlatformHelper.getPlatformHelper().runKernelAction(new UnadvertiseResourceAction(ctx, resName), "unAdvertiseResource");
         } catch (Exception var3) {
         }

      }
   }

   static boolean isNonXAResourceAdvertisedAt(String resName, String scName) {
      if (!PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         return false;
      } else {
         try {
            Context ctx = getNonXAResourceContext();
            Object o = PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction(scName + "." + resName, ctx), (String)null, "isNonXAResourceAdvertisedAt");
            if (o != null) {
               return true;
            }
         } catch (Exception var4) {
         }

         return false;
      }
   }

   static void advertiseNonXAResource(String resName) throws Exception {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         String name = null;
         Context ctx = getNonXAResourceContext();
         name = getServerName() + "." + resName;
         ServerTransactionManagerImpl tm = (ServerTransactionManagerImpl)getTM();
         PlatformHelper.getPlatformHelper().runKernelAction(new AdvertiseResourceAction(ctx, name, "DUMMY"), "advertiseNonXAResource");
      }
   }

   static void unAdvertiseNonXAResource(String resName) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getNonXAResourceServerContext();
            ServerTransactionManagerImpl tm = (ServerTransactionManagerImpl)getTM();
            PlatformHelper.getPlatformHelper().runKernelAction(new UnadvertiseResourceAction(ctx, resName), "unAdvertiseNonXAResource");
         } catch (Exception var3) {
         }

      }
   }

   static SubCoordinatorOneway getSubCoordinator(CoordinatorDescriptor aCoDesc, TransactionImpl tx) {
      return (SubCoordinatorOneway)getCachedCoordinator(aCoDesc, tx);
   }

   static void advertiseTransactionManager(TransactionManager tm) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getInitialContext((String)null, false);
            ctx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
            ctx.bind("weblogic.transaction.TransactionManager", tm);
            ctx.bind("javax.transaction.TransactionManager", tm);
         } catch (NamingException var2) {
            TXLogger.logAdvertiseTMError(var2);
         }

      }
   }

   static void advertiseUserTransaction(UserTransaction ut) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getInitialContext((String)null, false);
            ctx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
            ctx.bind("weblogic.transaction.UserTransaction", ut);
            ctx.bind("javax.transaction.UserTransaction", ut);
         } catch (NamingException var2) {
            TXLogger.logAdvertiseUTError(var2);
         }

      }
   }

   static void advertiseTransactionSynchronizationRegistry(TransactionSynchronizationRegistry tsr) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getInitialContext((String)null, false);
            ctx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
            ctx.bind("weblogic.transaction.TransactionSynchronizationRegistry", tsr);
            ctx.bind("javax.transaction.TransactionSynchronizationRegistry", tsr);
         } catch (NamingException var2) {
            TXLogger.logAdvertiseTSRError(var2);
         }

      }
   }

   static void advertiseSubCoordinator(SubCoordinatorOneway sc) {
      if (PlatformHelper.getPlatformHelper().isJNDIEnabled()) {
         try {
            Context ctx = getServerContext();
            ctx.addToEnvironment("weblogic.jndi.createUnderSharable", "true");
            ctx.bind(getServerName(), sc);
         } catch (NamingException var2) {
            TXLogger.logAdvertiseSubCoordinatorError(var2);
         }

      }
   }

   static Context getInitialContext(String serverURL) throws NamingException {
      return getInitialContext(serverURL, true);
   }

   static Context getInitialContext(String serverURL, boolean replicateBindings) throws NamingException {
      return PlatformHelper.getPlatformHelper().getInitialContext(serverURL, replicateBindings);
   }

   static Context getServerContext() throws NamingException {
      try {
         if (serverCtx == null) {
            serverCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction("coordinators", getTxRootContext()), (String)null, "getServerContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return serverCtx;
   }

   private static Context getResourceContext() throws NamingException {
      try {
         if (resourceCtx == null) {
            resourceCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction("resources", getTxRootContext()), (String)null, "getResourceContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return resourceCtx;
   }

   private static Context getResourceServerContext() throws NamingException {
      try {
         if (resourceServerCtx == null) {
            resourceServerCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction("resources." + getServerName(), getTxRootContext()), (String)null, "getResourceServerContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return resourceServerCtx;
   }

   private static Context getNonXAResourceContext() throws NamingException {
      try {
         if (nonXAResourceCtx == null) {
            nonXAResourceCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction("nonxaresources", getTxRootContext()), (String)null, "getNonXAResourceContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return nonXAResourceCtx;
   }

   private static Context getNonXAResourceServerContext() throws NamingException {
      try {
         if (nonXAResourceServerCtx == null) {
            nonXAResourceServerCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction("nonxaresources." + getServerName(), getTxRootContext()), (String)null, "getNonXAResourceServerContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return nonXAResourceServerCtx;
   }

   private static Context getTxRootContext() throws NamingException {
      try {
         if (txRootCtx == null) {
            txRootCtx = (Context)PlatformHelper.getPlatformHelper().runSecureAction(new JNDILookUpAction(TX_ROOT, rootCtx), (String)null, "getTxRootContext");
         }
      } catch (Exception var1) {
         throw (NamingException)var1;
      }

      return txRootCtx;
   }

   static String getServerName() {
      return TransactionManagerImpl.getTransactionManager().getServerName();
   }

   private static Context getRootTransactionContext() {
      return rootCtx;
   }

   static {
      COORDINATORS = TX_ROOT + ".coordinators";
      RESOURCES = TX_ROOT + ".resources";
      NONXARESOURCES = TX_ROOT + ".nonxaresources";
      rootCtx = null;
      txRootCtx = null;
      resourceCtx = null;
      resourceServerCtx = null;
      nonXAResourceCtx = null;
      nonXAResourceServerCtx = null;
      serverCtx = null;
      instance = null;
   }

   private static class JNDILookUpAction implements PrivilegedExceptionAction {
      private String objName;
      private Object obj;
      private Context ctx;

      JNDILookUpAction(String oName, Context context) {
         this.objName = oName;
         this.obj = null;
         this.ctx = context;
      }

      public Object run() throws Exception {
         this.obj = this.ctx.lookup(this.objName);
         return this.obj;
      }
   }

   private static class UnadvertiseResourceAction implements PrivilegedExceptionAction {
      private Context ctx;
      private String resourceName;

      UnadvertiseResourceAction(Context aCtx, String aResourceName) {
         this.ctx = aCtx;
         this.resourceName = aResourceName;
      }

      public Object run() throws Exception {
         this.ctx.unbind(this.resourceName);
         return null;
      }
   }

   private static class AdvertiseResourceAction implements PrivilegedExceptionAction {
      private Context ctx;
      private String serverResourceName;
      private String dummyObject;

      AdvertiseResourceAction(Context aCtx, String aServerResourceName, String aDummyObject) {
         this.ctx = aCtx;
         this.serverResourceName = aServerResourceName;
         this.dummyObject = aDummyObject;
      }

      public Object run() throws Exception {
         this.ctx.bind(this.serverResourceName, this.dummyObject);
         return null;
      }
   }
}
