package weblogic.transaction.internal;

import java.net.URI;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.PeerExchangeTransaction;

public class CoordinatorFactory {
   private static int contactCoordinatorWaitSecondsMin = 3;
   private static int contactCoordinatorWaitSecondsMax = 20;
   public static final Map cachedStubs = new ConcurrentHashMap();
   private static final Map connectStartTime = new ConcurrentHashMap();
   private static final Map cachedURLs = new HashMap();
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static Context getInitialContext(String serverURL) throws NamingException {
      return PlatformHelper.getPlatformHelper().getInitialContext(serverURL);
   }

   public static CoordinatorOneway getCoordinator(CoordinatorDescriptor aCoDesc, PeerExchangeTransaction tx) {
      return (CoordinatorOneway)getCachedCoordinator(aCoDesc, tx);
   }

   public static Object getCachedCoordinator(CoordinatorDescriptor aCoDesc, PeerExchangeTransaction tx) {
      if (aCoDesc == null) {
         return null;
      } else {
         String protocol = null;
         String targetURL = PlatformHelper.getPlatformHelper().getTargetChannelURL(aCoDesc);
         String key;
         if (!PlatformHelper.getPlatformHelper().isServer() && tx != null && tx.getLocalProperty("weblogic.client.handoffCoURL") != null) {
            key = (String)tx.getLocalProperty("weblogic.client.handoffCoURL");
            targetURL = CoordinatorDescriptor.getServerURL(key);
         }

         if (targetURL != null) {
            protocol = URI.create(targetURL).getScheme();
         } else {
            protocol = aCoDesc.getProtocol(tx);
         }

         key = protocol + "+" + aCoDesc.getServerID();
         if (TxDebug.JTANaming.isDebugEnabled() && TxDebug.JTANamingStackTrace.isDebugEnabled()) {
            TxDebug.debugStack(TxDebug.JTANamingStackTrace, "CoordinatorFactory.getCachedCoordinator key:" + key + " Codesc:" + aCoDesc + " tx:" + tx);
         }

         Object o = cachedStubs.get(key);
         if (o != null) {
            return o;
         } else {
            String coID = key.intern();
            Long tmpCo;
            synchronized(coID) {
               o = cachedStubs.get(coID);
               if (o != null) {
                  return o;
               }

               tmpCo = (Long)connectStartTime.get(coID);
               if (tmpCo != null) {
                  int txTimeToLiveSecs;
                  if (tx == null) {
                     txTimeToLiveSecs = 30;
                  } else {
                     txTimeToLiveSecs = tx.getTimeToLiveSeconds();
                  }

                  int timeoutSecs = getContactCoordinatorWaitSeconds(txTimeToLiveSecs);

                  try {
                     coID.wait((long)(timeoutSecs * 1000));
                  } catch (InterruptedException var41) {
                  }

                  o = cachedStubs.get(coID);
                  if (o == null && TxDebug.JTA2PC.isDebugEnabled()) {
                     TxDebug.JTA2PC.debug("Cannot get Coordinator stub after waiting for " + timeoutSecs + " seconds, timeToLive before waiting was " + txTimeToLiveSecs + "seconds");
                  }

                  return o;
               }

               connectStartTime.put(coID, System.currentTimeMillis());
            }

            String tmpSvrU = null;
            tmpCo = null;
            boolean var29 = false;

            label554: {
               String adminURL;
               label555: {
                  Object var9;
                  try {
                     label530: {
                        try {
                           var29 = true;
                           o = cachedStubs.get(coID);
                           if (o == null) {
                              String coName = aCoDesc.getServerName();
                              adminURL = null;
                              String serverURL = aCoDesc.getServerURL(tx);
                              if (PlatformHelper.getPlatformHelper().isServer()) {
                                 String coURL = aCoDesc.getCoordinatorURL();
                                 if (!aCoDesc.isCoURLExtendedWithAdminPort(coURL)) {
                                    adminURL = CoordinatorDescriptor.getAdminCoordinatorURL(coURL);
                                    if (adminURL != null) {
                                       serverURL = CoordinatorDescriptor.getServerURL(adminURL);
                                    }
                                 }
                              }

                              if (targetURL != null) {
                                 serverURL = targetURL;
                              }

                              if (TxDebug.JTANaming.isDebugEnabled()) {
                                 TxDebug.JTANaming.debug("CoordinatorFactory.getCachedCoordinator to run action key:" + key + " coName:" + coName + " serverURL:" + serverURL + " thread:" + Thread.currentThread().getName());
                              }

                              TransactionImpl curTx = getTM().internalSuspend();
                              Context ctx = null;

                              try {
                                 ctx = getInitialContext(serverURL);
                                 if (PlatformHelper.getPlatformHelper().isServer()) {
                                    o = PlatformHelper.getPlatformHelper().getCoordinatorFactory().runAction(coName, serverURL, ctx);
                                 } else {
                                    o = ctx.lookup("weblogic.transaction.coordinators." + coName);
                                 }

                                 if (o instanceof ClientTransactionManagerImpl) {
                                    o = ((ClientTransactionManagerImpl)o).getCoordinator();
                                 }

                                 cachedStubs.put(coID, o);
                                 if (TxDebug.JTANaming.isDebugEnabled()) {
                                    cachedURLs.put(coID, serverURL);
                                    if (TxDebug.JTANamingStackTrace.isDebugEnabled()) {
                                       TxDebug.debugStack(TxDebug.JTANamingStackTrace, "CoordinatorFactory.getCachedCoordinator put coID:" + coID + " o:" + o + " tx:" + tx);
                                    }
                                 }

                                 if (o instanceof Remote) {
                                    PlatformHelper.getPlatformHelper().getCoordinatorFactory().addDisconnectListenerToDisconnectMonitor((Remote)o, coID);
                                 }
                              } finally {
                                 getTM().internalResume(curTx);
                                 if (ctx != null) {
                                    ctx.close();
                                 }

                              }

                              var29 = false;
                              break label554;
                           }

                           var9 = o;
                           var29 = false;
                           break label530;
                        } catch (Exception var45) {
                           if (TxDebug.JTA2PC.isDebugEnabled()) {
                              if (TxDebug.JTA2PCStackTrace.isDebugEnabled()) {
                                 TxDebug.JTA2PCStackTrace.debug((String)("Cannot obtain Coordinator: run action key:" + key + " coName:" + tmpCo + " serverURL:" + tmpSvrU + aCoDesc.getCoordinatorURL() + " tx:" + tx), (Throwable)var45);
                              } else {
                                 TxDebug.JTA2PC.debug("Cannot obtain Coordinator: " + aCoDesc.getCoordinatorURL());
                              }
                           }
                        }

                        adminURL = null;
                        var29 = false;
                        break label555;
                     }
                  } finally {
                     if (var29) {
                        synchronized(coID) {
                           connectStartTime.remove(coID);
                           coID.notifyAll();
                        }
                     }
                  }

                  synchronized(coID) {
                     connectStartTime.remove(coID);
                     coID.notifyAll();
                     return var9;
                  }
               }

               synchronized(coID) {
                  connectStartTime.remove(coID);
                  coID.notifyAll();
                  return adminURL;
               }
            }

            synchronized(coID) {
               connectStartTime.remove(coID);
               coID.notifyAll();
               return o;
            }
         }
      }
   }

   public static Map getCachedURLs() {
      return (Map)((HashMap)cachedURLs).clone();
   }

   static void clearCachedEntries() {
      cachedStubs.clear();
      if (TxDebug.JTANaming.isDebugEnabled()) {
         cachedURLs.clear();
         TxDebug.JTANaming.debug("Cleared cached Coordinator stub entries");
      }

   }

   private static int getContactCoordinatorWaitSeconds(int timeout) {
      timeout = Math.min(timeout, contactCoordinatorWaitSecondsMax);
      return Math.max(timeout, contactCoordinatorWaitSecondsMin);
   }

   protected static TransactionManagerImpl getTM() {
      return TransactionManagerImpl.getTransactionManager();
   }

   public Object runAction(String coName, String serverURL, Context ctx) throws Exception {
      return SecureAction.runAction(kernelID, new JNDILookUpAction(coName, ctx), serverURL, "getCachedCoordinator");
   }

   public void addDisconnectListenerToDisconnectMonitor(Remote o, final String coID) {
      DisconnectMonitor dm = (DisconnectMonitor)PlatformHelper.getPlatformHelper().getDisconnectMonitor();

      try {
         if (dm != null) {
            dm.addDisconnectListener(o, new DisconnectListener() {
               public void onDisconnect(DisconnectEvent reason) {
                  CoordinatorFactory.cachedStubs.remove(coID);
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     CoordinatorFactory.cachedURLs.remove(coID);
                  }

               }
            });
         }
      } catch (DisconnectMonitorUnavailableException var5) {
         cachedStubs.remove(coID);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            cachedURLs.remove(coID);
         }
      }

   }

   static {
      String val = System.getProperty("weblogic.JTA.ContactCoordinatorWaitSeconds");
      if (val != null) {
         try {
            int i = Integer.parseInt(val);
            if (i > 0) {
               contactCoordinatorWaitSecondsMin = i;
               contactCoordinatorWaitSecondsMax = i;
            }
         } catch (NumberFormatException var2) {
         }
      }

   }

   protected static class JNDILookUpAction implements PrivilegedExceptionAction {
      private String objName;
      private Object obj;
      private Context ctx;

      public JNDILookUpAction(String oName, Context context) {
         this.objName = oName;
         this.obj = null;
         this.ctx = context;
      }

      public Object run() throws Exception {
         this.obj = this.ctx.lookup("weblogic.transaction.coordinators." + this.objName);
         return this.obj;
      }
   }
}
