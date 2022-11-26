package weblogic.jms.safclient;

import java.io.File;
import java.util.HashMap;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.extensions.ClientSAF;
import weblogic.jms.safclient.transaction.jta.SimpleTransactionHelper;
import weblogic.jms.safclient.transaction.jta.SimpleTransactionManager;
import weblogic.kernel.KernelInitializer;

public final class ClientSAFManager {
   private static ClientSAFManager singleton;
   private static boolean properlyInitialized = false;
   private static AssertionError initializationError;
   private static SimpleTransactionHelper clientSAFTxHelper;
   private HashMap delegates = new HashMap();

   private static void initialize() throws AssertionError {
      CrossDomainSecurityManager.ensureSubjectManagerInitialized();
      KernelInitializer.initializeWebLogicKernel();
      clientSAFTxHelper = new SimpleTransactionHelper(new SimpleTransactionManager());
   }

   static ClientSAFManager getManager() {
      if (!properlyInitialized) {
         throw initializationError;
      } else if (singleton != null) {
         return singleton;
      } else {
         singleton = new ClientSAFManager();
         return singleton;
      }
   }

   private ClientSAFManager() {
   }

   ClientSAFDelegate createDelegate(File storeDirectory, ClientSAF userObj) {
      synchronized(this.delegates) {
         ClientSAFDelegate retVal = (ClientSAFDelegate)this.delegates.get(storeDirectory);
         if (retVal != null) {
            return retVal;
         } else {
            retVal = new ClientSAFDelegate(userObj);
            this.delegates.put(storeDirectory, retVal);
            return retVal;
         }
      }
   }

   ClientSAFDelegate getDelegate(File storeDirectory) {
      synchronized(this.delegates) {
         return (ClientSAFDelegate)this.delegates.get(storeDirectory);
      }
   }

   void removeDelegate(File storeDirectory) {
      synchronized(this.delegates) {
         this.delegates.remove(storeDirectory);
      }
   }

   public static SimpleTransactionHelper getTxHelper() {
      return clientSAFTxHelper;
   }

   static {
      try {
         initialize();
         properlyInitialized = true;
      } catch (AssertionError var1) {
         initializationError = var1;
      }

   }
}
