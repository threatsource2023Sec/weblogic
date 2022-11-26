package weblogic.ejb.container.deployer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.internal.MDConnectionManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.server.ActivatedService;
import weblogic.server.ServiceFailureException;

@Service
@Singleton
public final class MDBServiceImpl extends ActivatedService implements MDBService {
   private static boolean started;
   private boolean shutdown;
   private static final Set registeredBeanInfos = new HashSet();
   private final Map deployedMDBs = new HashMap();

   public synchronized void haltService() throws ServiceFailureException {
      if (!this.shutdown) {
         if (!this.deployedMDBs.isEmpty()) {
            EJBLogger.logMDBsBeingSuspended();
            Iterator var1 = this.deployedMDBs.entrySet().iterator();

            while(var1.hasNext()) {
               Map.Entry e = (Map.Entry)var1.next();
               ManagedInvocationContext mic = ((MessageDrivenBeanInfo)e.getValue()).setCIC();
               Throwable var4 = null;

               try {
                  ((MDConnectionManager)e.getKey()).suspend(true);
               } catch (Throwable var13) {
                  var4 = var13;
                  throw var13;
               } finally {
                  if (mic != null) {
                     if (var4 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var12) {
                           var4.addSuppressed(var12);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            }

            EJBLogger.logMDBsDoneSuspending();
         }

         this.shutdown = true;
      }
   }

   public synchronized void stopService() throws ServiceFailureException {
      this.haltService();
   }

   public synchronized boolean startService() throws ServiceFailureException {
      Iterator var1 = this.deployedMDBs.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry e = (Map.Entry)var1.next();
         ManagedInvocationContext mic = ((MessageDrivenBeanInfo)e.getValue()).setCIC();
         Throwable var4 = null;

         try {
            ((MDConnectionManager)e.getKey()).resume(true, true);
         } catch (Throwable var51) {
            var4 = var51;
            throw var51;
         } finally {
            if (mic != null) {
               if (var4 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var48) {
                     var4.addSuppressed(var48);
                  }
               } else {
                  mic.close();
               }
            }

         }
      }

      synchronized(registeredBeanInfos) {
         Thread currentThread = Thread.currentThread();
         Iterator var59 = registeredBeanInfos.iterator();

         while(var59.hasNext()) {
            MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)var59.next();
            ApplicationVersionUtils.setCurrentAdminMode(true);
            ClassLoader clSave = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(mdbi.getModuleClassLoader());

            try {
               ManagedInvocationContext mic = mdbi.setCIC();
               Throwable var7 = null;

               try {
                  mdbi.deployMessageDrivenBeans();
               } catch (Throwable var50) {
                  var7 = var50;
                  throw var50;
               } finally {
                  if (mic != null) {
                     if (var7 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var49) {
                           var7.addSuppressed(var49);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            } catch (RuntimeException var54) {
               throw var54;
            } catch (Exception var55) {
               EJBLogger.logErrorStartingMDB(var55);
            } finally {
               if (clSave != null) {
                  currentThread.setContextClassLoader(clSave);
               }

               ApplicationVersionUtils.setCurrentAdminMode(false);
            }
         }

         registeredBeanInfos.clear();
         started = true;
      }

      this.shutdown = false;
      return started;
   }

   public synchronized void addDeployed(MDConnectionManager connMgr, MessageDrivenBeanInfo info) {
      this.deployedMDBs.put(connMgr, info);
   }

   public synchronized void removeDeployed(MDConnectionManager connMgr) {
      this.deployedMDBs.remove(connMgr);
   }

   public boolean register(Collection infos) {
      synchronized(registeredBeanInfos) {
         if (!started) {
            registeredBeanInfos.addAll(infos);
            return true;
         } else {
            return false;
         }
      }
   }
}
