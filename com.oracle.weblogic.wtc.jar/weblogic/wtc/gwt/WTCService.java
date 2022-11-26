package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.EXid;
import com.bea.core.jatmi.internal.TCLicenseManager;
import com.bea.core.jatmi.internal.TCResourceHelper;
import com.bea.core.jatmi.internal.TCRouteManager;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.internal.TCTaskHelper;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.internal.TuxedoDummyXA;
import com.bea.core.jatmi.internal.TuxedoXA;
import com.bea.core.jatmi.internal.TuxedoXid;
import com.bea.core.jatmi.intf.ConfigHelperDelegate;
import com.bea.core.jatmi.intf.TCTask;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import java.lang.Thread.State;
import java.security.AccessController;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.directory.InvalidAttributesException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.Home;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.iiop.IIOPService;
import weblogic.jndi.Environment;
import weblogic.kernel.ExecuteThread;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WTCExportMBean;
import weblogic.management.configuration.WTCImportMBean;
import weblogic.management.configuration.WTCLocalTuxDomMBean;
import weblogic.management.configuration.WTCPasswordMBean;
import weblogic.management.configuration.WTCRemoteTuxDomMBean;
import weblogic.management.configuration.WTCResourcesMBean;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.configuration.WTCtBridgeGlobalMBean;
import weblogic.management.configuration.WTCtBridgeRedirectMBean;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.management.internal.ResourceDependentDeploymentHandler;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WTCRuntimeMBean;
import weblogic.management.runtime.WTCStatisticsRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TxHelper;
import weblogic.transaction.XIDFactory;
import weblogic.transaction.internal.StartupClass;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.BetaFeatures;
import weblogic.wtc.jatmi.DomainRegistry;
import weblogic.wtc.jatmi.FldTbl;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.ObjinfoImpl;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TuxXidRply;
import weblogic.wtc.jatmi.ViewHelper;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.tbridge.tBexec;
import weblogic.wtc.wls.WlsLicenseService;
import weblogic.wtc.wls.WlsLogService;
import weblogic.wtc.wls.WlsResourceService;
import weblogic.wtc.wls.WlsSecurityService;
import weblogic.wtc.wls.WlsTaskManager;
import weblogic.wtc.wls.WlsTransactionService;

public final class WTCService extends RuntimeMBeanDelegate implements ResourceDependentDeploymentHandler, WTCRuntimeMBean, BeanUpdateListener, ConfigHelperDelegate {
   private static Context myNameService;
   private static Timer myTimeService;
   private static Timer asyncTimeService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Hashtable myImportedServices;
   private Hashtable myExportedServices;
   private Hashtable tmpImportedServices;
   private Hashtable tmpExportedServices;
   private static int myUid;
   private TDMLocalTDomain[] ltd_list;
   private TDMRemoteTDomain[] rtd_list;
   private TDMPasswd[] pwd_list;
   private static TDMResources myGlobalResources = null;
   private int ltdcnt = 0;
   private int rtdcnt = 0;
   private int pwdcnt = 0;
   private int tBridgeConfig = 0;
   private boolean do_replicate = false;
   private boolean tBridgeStartup = false;
   private boolean tBridgePending = false;
   private boolean xaAffinity = true;
   private static String myPasswordKey = null;
   private static String myEncryptionType = null;
   private static Map myOutboundXidMap;
   private static Map myInboundXidMap;
   private static Map myXidRetryMap;
   private static Map myXidReadyMap;
   private static Map myOutboundTxidMap;
   private static Map recoveredXids;
   private static Map committedXids;
   private static Map preparedXids;
   private static Map committingXids;
   private static Map myXidTLogMap;
   private static HashMap myOutboundFXidMap;
   private static boolean initialized = false;
   private static BetaFeatures useBetaFeatures;
   private TuxXidRply unknownTxidRply;
   private WTCServerMBean myWtcSrvrMBean = null;
   private Context myDomainContext = null;
   private Random myRandom = null;
   private boolean registered = false;
   private TimerEventManager myTEManager;
   private static WTCService myWTCService = null;
   private static int countWTC = 0;
   private static String myName = null;
   private static String myRMNameSuffix = null;
   private static final String APPLICATION_QUEUE_NAME = "weblogic.wtc.applicationQueue";
   public static final int DFLT_APPLICATION_QUEUE_SIZE = 10;
   static int applicationQueueId = -1;
   private WTCStatisticsRuntimeMBeanImpl myWtcStatMBean = null;
   private long wtcStartTime = 0L;
   private int wtcStatus = 0;

   public WTCService() throws ManagementException {
      super("WTCService", false);
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setWTCRuntime(this);
      myWTCService = this;
      this.setWTCStatisticsRuntimeMBean(new WTCStatisticsRuntimeMBeanImpl(this));
   }

   public static synchronized WTCService getService() throws ManagementException {
      if (myWTCService == null) {
         myWTCService = new WTCService();
      }

      return myWTCService;
   }

   public static WTCService getWTCService() {
      return myWTCService;
   }

   public static Timer getAsyncTimerService() {
      return asyncTimeService;
   }

   public static String getWTCServerName() {
      return myName;
   }

   public static Timer getTimerService() {
      return myTimeService;
   }

   public static String getRMNameSuffix() {
      return myRMNameSuffix;
   }

   public static int getApplicationQueueId() {
      return applicationQueueId;
   }

   public TuxXidRply getUnknownTxidRply() {
      return this.unknownTxidRply;
   }

   public int getImplementationId() {
      return 0;
   }

   public long getImplementedCapabilities() {
      return 16383L;
   }

   public String getGlobalMBEncodingMapFile() {
      return getMBEncodingMapFile();
   }

   public String getGlobalRemoteMBEncoding() {
      return getRemoteMBEncoding();
   }

   public String getHomePath() {
      return Home.getPath();
   }

   public Objinfo createObjinfo() {
      return new ObjinfoImpl();
   }

   public OatmialServices getTuxedoServices() {
      return !initialized ? null : new OatmialServices(myNameService, myTimeService, myOutboundXidMap, myInboundXidMap, myXidRetryMap, myXidReadyMap, myOutboundFXidMap, myOutboundTxidMap, myRMNameSuffix);
   }

   public gwatmi getRAPSession(TuxedoConnectorRAP rap, boolean mode) {
      return ((TDMRemoteTDomain)rap).getTsession(mode);
   }

   void initializeCommon() {
      WlsLogService mylogger = new WlsLogService();
      ntrace.init(mylogger);
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/initializeCommon/");
      }

      WlsLicenseService p_replicate;
      try {
         p_replicate = new WlsLicenseService();
         TCLicenseManager.initialize(p_replicate);
         ConfigHelper.initialize(this);
         WlsTaskManager mytaskm = new WlsTaskManager();
         TCTaskHelper.initialize(mytaskm);
         WlsSecurityService mysec = new WlsSecurityService();
         TCSecurityManager.initialize(mysec);
         WlsRouteService myrouter = new WlsRouteService();
         TCRouteManager.initialize(myrouter);
         WlsTransactionService myts = new WlsTransactionService();
         TCTransactionHelper.initialize(myts);
         WlsResourceService myres = new WlsResourceService();
         TCResourceHelper.initialize(myres);
      } catch (TPException var9) {
         ntrace.doTrace("TPException: " + var9.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/initializeCommon return");
         }

         return;
      }

      myPasswordKey = System.getProperty("weblogic.wtc.PasswordKey");
      myEncryptionType = System.getProperty("weblogic.wtc.EncryptionType");
      this.do_replicate = false;
      p_replicate = null;
      String p_replicate = System.getProperty("weblogic.wtc.replicateBindings", "false");
      if (p_replicate != null && p_replicate.equals("true")) {
         this.do_replicate = true;
      }

      p_replicate = System.getProperty("weblogic.wtc.xaAffinity", "true");
      if (p_replicate != null && p_replicate.equals("false")) {
         this.xaAffinity = false;
      }

      int applicationQueueSize = false;
      String str = System.getProperty("weblogic.wtc.applicationQueueSize");
      if (str != null) {
         if (!Kernel.isDispatchPolicy("weblogic.wtc.applicationQueue")) {
            int applicationQueueSize = Integer.parseInt(str);
            if (applicationQueueSize > 0) {
               Kernel.addExecuteQueue("weblogic.wtc.applicationQueue", applicationQueueSize);
               applicationQueueId = Kernel.getDispatchPolicyIndex("weblogic.wtc.applicationQueue");
            }
         } else {
            applicationQueueId = Kernel.getDispatchPolicyIndex("weblogic.wtc.applicationQueue");
         }
      }

      DeploymentHandlerHome.addDeploymentHandler(this);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/initializeCommon/10/void: DONE");
      }

   }

   void resumeCommon() {
      if (ntrace.isTraceEnabled(2)) {
         ntrace.doTrace("[/WTCService/resumeCommon/");
         ntrace.doTrace("]/WTCService/resumeCommon/00/void: NOT USED");
      }

   }

   void suspend(boolean force) {
      if (ntrace.isTraceEnabled(2)) {
         ntrace.doTrace("[/WTCService/suspend/force = " + force);
         ntrace.doTrace("]/WTCService/suspend/00/void: NOT USED");
      }

   }

   void shutdownCommon() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/shutdownCommon/");
      }

      this.shutdownWTC();
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/shutdownCommon/10/void: DONE");
      }

   }

   void shutdownWTC() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/shutdownWTC/");
      }

      this.removeBeanListeners();
      if (this.myTEManager != null) {
         this.myTEManager.shutdown();
         this.myTEManager = null;
      }

      for(int lcv = 0; lcv < this.ltdcnt; ++lcv) {
         if (this.ltd_list[lcv] != null) {
            OatmialListener ldomListener = this.ltd_list[lcv].getOatmialListener();
            if (ldomListener != null) {
               ldomListener.shutdown();
               Thread.State ldomListenerState = ldomListener.getState();
               if (ldomListenerState != State.TERMINATED) {
                  ldomListener.interrupt();
               }
            }
         }
      }

      TCTransactionHelper.unregisterResource();
      TCTransactionHelper.unregisterResource(true);

      for(int lcv = 0; lcv < this.rtdcnt; ++lcv) {
         gwatmi myAtmi;
         if (this.rtd_list[lcv] != null && (myAtmi = this.rtd_list[lcv].getTsession(false)) != null) {
            try {
               myAtmi.tpterm();
            } catch (TPException var8) {
            }
         }
      }

      try {
         if (myNameService != null) {
            if (traceEnabled) {
               ntrace.doTrace("/WTCService/shutdownWTC/UNBINDING.");
            }

            myNameService.unbind("tuxedo.services.TuxedoConnection");
            myNameService.unbind("tuxedo.services.TuxedoCorbaConnection");
         }
      } catch (NamingException var7) {
         WTCLogger.logNEtuxConnFactory(var7);
      }

      if (this.tBridgeStartup) {
         tBexec.tBcancel();
         this.tBridgeStartup = false;
      }

      this.tBridgeConfig = 0;
      if (this.myImportedServices != null) {
         this.myImportedServices.clear();
      }

      if (this.myExportedServices != null) {
         this.myExportedServices.clear();
      }

      if (myOutboundXidMap != null) {
         myOutboundXidMap.clear();
      }

      if (myInboundXidMap != null) {
         myInboundXidMap.clear();
      }

      if (myXidRetryMap != null) {
         myXidRetryMap.clear();
      }

      if (myXidReadyMap != null) {
         myXidReadyMap.clear();
      }

      if (myTimeService != null) {
         myTimeService.cancel();
      }

      if (asyncTimeService != null) {
         asyncTimeService.cancel();
      }

      if (myOutboundFXidMap != null) {
         myOutboundFXidMap.clear();
      }

      if (myOutboundTxidMap != null) {
         myOutboundTxidMap.clear();
      }

      if (this.myWtcStatMBean != null) {
         this.myWtcStatMBean.clear();
      }

      this.wtcStatus = 0;
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/shutdownWTC/30/void: DONE.");
      }

   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (ntrace.isTraceEnabled(2)) {
         ntrace.doTrace("[/WTCService/prepareDeployment/");
         ntrace.doTrace("]/WTCService/prepareDeployment/00/void: NOT USED");
      }

   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext ctx) throws DeploymentException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (StartupClass.getMainCallCount() > 0) {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCService/activateDeployment/begin initialization");
         }

         (new WTCServiceActivateService(this, deployment, ctx)).run();
      } else {
         if (traceEnabled) {
            ntrace.doTrace("[/WTCService/activateDeployment/delay initialization");
         }

         new StartupClass(new WTCServiceActivateService(this, deployment, ctx));
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/deactivateDeployment/");
      }

      if (deployment == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/deactivateDeployment/10/null deployment");
         }

      } else if (deployment instanceof WTCServerMBean && deployment.getName().equals(myName)) {
         this.shutdownWTC();
         this.myWtcSrvrMBean = null;
         this.ltd_list = null;
         this.rtd_list = null;
         this.ltdcnt = 0;
         this.rtdcnt = 0;
         countWTC = 0;
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/deactivateDeployment/20/void:(" + deployment.getName() + ") UNDEPLOYED.");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/deactivateDeployment/20/No Deployment");
         }

      }
   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      if (ntrace.isTraceEnabled(2)) {
         ntrace.doTrace("[/WTCService/unprepareDeployment");
         ntrace.doTrace("]/WTCService/unprepareDeployment/00/void: NOT USED");
      }

   }

   public static OatmialServices getOatmialServices() {
      return new OatmialServices(myNameService, myTimeService, myOutboundXidMap, myInboundXidMap, myXidRetryMap, myXidReadyMap, myOutboundFXidMap, myOutboundTxidMap, myRMNameSuffix);
   }

   public static synchronized int getUniqueGwdsessionId() {
      return myUid++;
   }

   public static String getPasswordKey() {
      return myPasswordKey;
   }

   public static String getEncryptionType() {
      return myEncryptionType;
   }

   public static void addRecoveredXid(Xid xid, String[] remoteDomains) {
      if (recoveredXids == null) {
         recoveredXids = Collections.synchronizedMap(new HashMap());
      }

      recoveredXids.put(xid, remoteDomains);
   }

   public static void addPreparedXid(Xid xid, String remoteDomain, TuxedoLoggable myLog) {
      Object[] myValue = new Object[2];
      if (preparedXids == null) {
         preparedXids = Collections.synchronizedMap(new HashMap());
      }

      myValue[0] = remoteDomain;
      myValue[1] = myLog;
      preparedXids.put(xid, myValue);
   }

   public static void addCommittingXid(Xid xid, String remoteDomain, TuxedoLoggable myLog) {
      Object[] myValue = null;
      if (preparedXids != null && (myValue = (Object[])((Object[])preparedXids.remove(xid))) != null) {
         TuxedoLoggable log = (TuxedoLoggable)myValue[1];
         log.forget();
      }

      if (committingXids == null) {
         committingXids = Collections.synchronizedMap(new HashMap());
      }

      if (myValue == null) {
         myValue = new Object[2];
      }

      myValue[0] = remoteDomain;
      myValue[1] = myLog;
      committingXids.put(xid, myValue);
   }

   public Xid[] getRecoveredXids() {
      if (recoveredXids != null && recoveredXids.size() != 0) {
         Xid[] ret = (Xid[])((Xid[])recoveredXids.keySet().toArray(new Xid[recoveredXids.size()]));
         return ret;
      } else {
         return new Xid[0];
      }
   }

   public void forgetRecoveredXid(Xid xid) {
      if (recoveredXids != null && recoveredXids.size() != 0) {
         boolean traceEnabled = ntrace.isTraceEnabled(2);
         if (traceEnabled) {
            ntrace.doTrace("/WTCService/forgetRecoveredXid/xid=" + xid);
         }

         if (xid instanceof EXid) {
            recoveredXids.remove(xid);
         } else {
            Xid logicXid = this.getTuxedoServices().getOutboundXidAssociatedWithFXid(xid);
            EXid exid;
            if (logicXid == null) {
               exid = new EXid(xid, (Xid)null);
            } else {
               exid = new EXid(logicXid, xid);
            }

            if (traceEnabled) {
               ntrace.doTrace("[/WTCService/forgetRecoveredXid/EXid=" + exid);
            }

            recoveredXids.remove(exid);
         }

      }
   }

   public void addXidTLogMap(Xid xid, TuxedoLoggable tl) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (myXidTLogMap == null) {
         myXidTLogMap = Collections.synchronizedMap(new HashMap());
      }

      myXidTLogMap.put(xid, tl);
      if (traceEnabled) {
         ntrace.doTrace("WTCService/addXidTLogMap/xid " + xid + ", tl type " + tl.getType());
      }

   }

   public static void AddXidTLogMap(Xid xid, TuxedoLoggable tl) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (myXidTLogMap == null) {
         myXidTLogMap = Collections.synchronizedMap(new HashMap());
      }

      if (xid instanceof EXid) {
         myXidTLogMap.put(((EXid)xid).getXid(), tl);
         if (traceEnabled) {
            ntrace.doTrace("WTCService/AddXidTLogMap/xid " + ((EXid)xid).getXid() + ", tl type " + tl.getType());
         }
      } else {
         myXidTLogMap.put(xid, tl);
         if (traceEnabled) {
            ntrace.doTrace("WTCService/AddXidTLogMap/xid " + xid + ", tl type " + tl.getType());
         }
      }

   }

   public TuxedoLoggable removeXidTLogMap(Xid xid, int type) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (myXidTLogMap == null) {
         return null;
      } else {
         TuxedoLoggable tl = (TuxedoLoggable)myXidTLogMap.get(xid);
         if (tl == null) {
            if (traceEnabled) {
               ntrace.doTrace("WTCService/removeXidTLogMap/xid " + xid + " return null");
            }

            return null;
         } else if (type != -1 && tl.getType() != type) {
            return null;
         } else {
            myXidTLogMap.remove(xid);
            if (traceEnabled) {
               ntrace.doTrace("WTCService/removeXidTLogMap/xid " + xid + ", tl type " + tl.getType());
            }

            return tl;
         }
      }
   }

   public static void addCommittedXid(Xid xid, String[] remoteDomains) {
      if (committedXids == null) {
         committedXids = Collections.synchronizedMap(new HashMap());
      }

      committedXids.put(xid, remoteDomains);
   }

   public synchronized TDMPasswd getTDMPasswd(String LocalAccessPoint, String RemoteAccessPoint) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getTDMPasswd/lap " + LocalAccessPoint + ", RAP " + RemoteAccessPoint);
      }

      if (LocalAccessPoint != null && RemoteAccessPoint != null && this.pwdcnt != 0) {
         for(int i = 0; i < this.pwdcnt; ++i) {
            String lap = this.pwd_list[i].getLocalAccessPoint();
            String rap = this.pwd_list[i].getRemoteAccessPoint();
            if (lap.equals(LocalAccessPoint) && rap.equals(RemoteAccessPoint)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getTDMPasswd/20/" + i);
               }

               return this.pwd_list[i];
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getTDMPasswd/30/null, not found");
         }

         return null;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getTDMPasswd/10/null");
         }

         return null;
      }
   }

   public synchronized TDMRemoteTDomain getRemoteTDomain(String domid) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getRemoteTDomain/" + domid);
      }

      if (domid != null && this.rtdcnt != 0) {
         for(int i = 0; i < this.rtdcnt; ++i) {
            if (domid.equals(this.rtd_list[i].getAccessPointId())) {
               if (traceEnabled) {
                  ntrace.doTrace("</WTCService/getRemoteTDomain/20/" + this.rtd_list[i].getAccessPoint());
               }

               return this.rtd_list[i];
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRemoteTDomain/30/null, not found");
         }

         return null;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRemoteTDomain/10/null");
         }

         return null;
      }
   }

   public static String getAppPasswordPWD() {
      return myGlobalResources != null ? myGlobalResources.getAppPassword() : null;
   }

   public static String getAppPasswordIV() {
      return myGlobalResources != null ? myGlobalResources.getAppPasswordIV() : null;
   }

   public static String getGlobalTpUsrFile() {
      return myGlobalResources != null ? myGlobalResources.getTpUsrFile() : null;
   }

   public static String getRemoteMBEncoding() {
      return myGlobalResources != null ? myGlobalResources.getRemoteMBEncoding() : null;
   }

   public static String getMBEncodingMapFile() {
      return myGlobalResources != null ? myGlobalResources.getMBEncodingMapFile() : null;
   }

   public static FldTbl[] getFldTbls(String type) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getFldTbls/" + type);
      }

      if (null == myGlobalResources) {
         return null;
      } else {
         if (type != null) {
            if (type.equals("fml16")) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getFldTbls/10/FldTbl[]");
               }

               return myGlobalResources.getFieldTables(false);
            }

            if (type.equals("fml32")) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getFldTbls/15/FldTbl[]");
               }

               return myGlobalResources.getFieldTables(true);
            }
         }

         WTCLogger.logErrorBadGetFldTblsType(type);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getFldTbls/20/null");
         }

         return null;
      }
   }

   public boolean checkWtcSrvrMBean() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkWtcSrvrMBean/");
      }

      if (this.myWtcSrvrMBean.getTargets().length != 1) {
         if (traceEnabled) {
            ntrace.doTrace("/WTCService/checkWtcSrvrMBean/10/WTCServerMBean has multi targets");
         }

         if (traceEnabled) {
            ntrace.doTrace("/WTCService/checkWtcSrvrMBean/30/false.");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/checkWtcSrvrMBean/20/true");
         }

         return true;
      }
   }

   public void startWTC() throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startWTC/");
      }

      XAResource wlsXaResource = null;
      Xid[] wlsRecoveredXids = null;
      int found_lcv = true;
      DomainMBean mydomain = null;
      new DomainRegistry();
      this.myImportedServices = new Hashtable();
      this.myExportedServices = new Hashtable();
      this.tmpImportedServices = new Hashtable();
      this.tmpExportedServices = new Hashtable();
      TCLicenseManager.updateInstalledEncryptionInfo();
      useBetaFeatures = new BetaFeatures(true, true);
      this.unknownTxidRply = new TuxXidRply(new TxidHandlerFactory());

      try {
         WTCLogger.logInfoStartConfigParse();
         this.extractInfo();
         WTCLogger.logInfoDoneConfigParse();
      } catch (TPException var34) {
         WTCLogger.logTPEConfigError("extractInfo: " + var34);
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/10/extractInfo failure");
         }

         throw var34;
      }

      int f;
      for(f = 0; f < this.ltdcnt; ++f) {
         if (this.ltd_list[f] != null) {
            this.startOatmialListener(this.ltd_list[f]);
         }
      }

      this.myTEManager = new TimerEventManager(this);
      this.myTEManager.setDaemon(true);
      this.myTEManager.start();
      if (traceEnabled) {
         ntrace.doTrace("create tuxedo subcontext");
      }

      Loggable ltcfne;
      try {
         myNameService.createSubcontext("tuxedo");
      } catch (NameAlreadyBoundException var41) {
      } catch (InvalidAttributesException var42) {
         ltcfne = WTCLogger.logIAEcreateSubCntxtLoggable(var42);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/20/invalid attributes");
         }

         throw new TPException(12, ltcfne.getMessage());
      } catch (NamingException var43) {
         ltcfne = WTCLogger.logNEcreateSubCntxtLoggable(var43);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/30/naming exception");
         }

         throw new TPException(12, ltcfne.getMessage());
      }

      if (traceEnabled) {
         ntrace.doTrace("Setting up federation points");
      }

      try {
         if (traceEnabled) {
            ntrace.doTrace("create tuxedo.domains subcontext");
         }

         try {
            this.myDomainContext = myNameService.createSubcontext("tuxedo.domains");
         } catch (NameAlreadyBoundException var33) {
         }

         for(f = 0; f < this.rtdcnt; ++f) {
            this.federateRemoteDomain(this.myDomainContext, this.rtd_list[f]);
         }
      } catch (InvalidAttributesException var49) {
         ltcfne = WTCLogger.logIAEcreateSubCntxtLoggable(var49);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/40/invalid attribute");
         }

         throw new TPException(12, ltcfne.getMessage());
      } catch (NamingException var50) {
         ltcfne = WTCLogger.logNEcreateSubCntxtLoggable(var50);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/50/Naming Exception");
         }

         throw new TPException(12, ltcfne.getMessage());
      }

      if (traceEnabled) {
         ntrace.doTrace("create tuxedo.services subcontext");
      }

      try {
         myNameService.createSubcontext("tuxedo.services");
      } catch (NameAlreadyBoundException var35) {
      } catch (InvalidAttributesException var36) {
         ltcfne = WTCLogger.logIAEcreateSubCntxtLoggable(var36);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/60/invalid attributes");
         }

         throw new TPException(12, ltcfne.getMessage());
      } catch (NamingException var37) {
         ltcfne = WTCLogger.logNEcreateSubCntxtLoggable(var37);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/70/naming exception");
         }

         throw new TPException(12, ltcfne.getMessage());
      }

      if (traceEnabled) {
         ntrace.doTrace("bind nameservices");
      }

      TuxedoConnectionFactory tcf = new TuxedoConnectionFactory();
      TuxedoCorbaConnectionFactory tccf = new TuxedoCorbaConnectionFactoryImpl();

      try {
         myNameService.bind("tuxedo.services.TuxedoConnection", tcf);
         myNameService.bind("tuxedo.services.TuxedoCorbaConnection", tccf);
      } catch (NameAlreadyBoundException var46) {
         ltcfne = WTCLogger.logNABEtuxConnFactoryLoggable(var46);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/65/bind nameservices failed/" + var46.getMessage());
         }
      } catch (InvalidAttributesException var47) {
         ltcfne = WTCLogger.logIAEtuxConnFactoryLoggable(var47);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/70/invalid attrib");
         }

         throw new TPException(12, ltcfne.getMessage());
      } catch (NamingException var48) {
         ltcfne = WTCLogger.logNEtuxConnFactoryLoggable(var48);
         ltcfne.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/80/Naming Exception");
         }

         throw new TPException(12, ltcfne.getMessage());
      }

      f = TdomTcb.getRuntimeFeatureSupported();
      mydomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      if (mydomain == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startWTC/85/get active domain failed/null");
         }

         throw new TPException(12, "can not get active domain");
      } else {
         if (mydomain.getJTA().isTightlyCoupledTransactionsEnabled()) {
            f |= 4096;
         } else {
            f &= -4097;
         }

         f |= 65536;
         TdomTcb.setRuntimeFeatureSupported(f);
         if (myOutboundXidMap == null) {
            myOutboundXidMap = Collections.synchronizedMap(new HashMap());
         } else {
            myOutboundXidMap.clear();
         }

         if (myInboundXidMap == null) {
            myInboundXidMap = Collections.synchronizedMap(new HashMap());
         } else {
            myInboundXidMap.clear();
         }

         if (myXidRetryMap == null) {
            myXidRetryMap = Collections.synchronizedMap(new HashMap());
         } else {
            myXidRetryMap.clear();
         }

         if (myXidReadyMap == null) {
            myXidReadyMap = Collections.synchronizedMap(new HashMap());
         } else {
            myXidReadyMap.clear();
         }

         if (myOutboundFXidMap == null) {
            myOutboundFXidMap = new HashMap();
         } else {
            myOutboundFXidMap.clear();
         }

         if (myOutboundTxidMap == null) {
            myOutboundTxidMap = Collections.synchronizedMap(new HashMap());
         } else {
            myOutboundTxidMap.clear();
         }

         String diff = System.getProperty("weblogic.wtc.useDiffRMName");
         if (diff != null && diff.equals("true")) {
            myRMNameSuffix = "_" + mydomain.getName() + "_" + myName;
            if (traceEnabled) {
               ntrace.doTrace("RM registry name suffix: " + myRMNameSuffix);
            }
         }

         TuxedoXA oatmialResource = new TuxedoXA(new OatmialServices(myNameService, myTimeService, myOutboundXidMap, myInboundXidMap, myXidRetryMap, myXidReadyMap, myOutboundFXidMap, myOutboundTxidMap, myRMNameSuffix));
         if (traceEnabled) {
            ntrace.doTrace("register transaction resource");
         }

         Loggable importedXid;
         try {
            TCTransactionHelper.registerResource(oatmialResource);
         } catch (SystemException var38) {
            importedXid = WTCLogger.logSEgetTranMgrLoggable(var38);
            importedXid.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/startWTC/90/unable register with TM");
            }

            throw new TPException(12, importedXid.getMessage());
         }

         TuxedoDummyXA dummyXAResource = new TuxedoDummyXA();
         if (traceEnabled) {
            ntrace.doTrace("register dummy transaction resource");
         }

         Loggable lnr;
         try {
            TCTransactionHelper.registerResource(dummyXAResource, true);
         } catch (SystemException var39) {
            lnr = WTCLogger.logSEgetTranMgrLoggable(var39);
            lnr.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/startWTC/91/unable register with TM");
            }

            throw new TPException(12, lnr.getMessage());
         }

         Set myValueSet;
         Iterator it;
         Map.Entry myEntry;
         Xid myXid;
         String[] myRdomPoints;
         int lcv;
         int lcv2;
         OatmialServices tos;
         String searchAccessPoint;
         Xid importedXid;
         if (recoveredXids != null && recoveredXids.size() != 0) {
            if (traceEnabled) {
               ntrace.doTrace("recovering " + recoveredXids.size() + " transactions");
            }

            tos = getOatmialServices();
            myValueSet = recoveredXids.entrySet();
            it = myValueSet.iterator();

            label668:
            while(true) {
               do {
                  do {
                     if (!it.hasNext()) {
                        break label668;
                     }

                     myEntry = (Map.Entry)it.next();
                  } while((myXid = (Xid)myEntry.getKey()) == null);
               } while((myRdomPoints = (String[])((String[])myEntry.getValue())) == null);

               for(lcv = 0; lcv < myRdomPoints.length; ++lcv) {
                  searchAccessPoint = myRdomPoints[lcv];

                  for(lcv2 = 0; lcv2 < this.rtd_list.length && !searchAccessPoint.equals(this.rtd_list[lcv2].getAccessPoint()); ++lcv2) {
                  }

                  if (lcv2 >= this.rtd_list.length) {
                     WTCLogger.logErrorTranId(searchAccessPoint);
                  } else {
                     importedXid = ((EXid)myXid).getForeignXid();
                     if (importedXid == null) {
                        if (myXid instanceof EXid) {
                           tos.addOutboundRdomToXid(((EXid)myXid).getXid(), this.rtd_list[lcv2]);
                        } else {
                           tos.addOutboundRdomToXid(myXid, this.rtd_list[lcv2]);
                        }
                     } else {
                        tos.addOutboundRdomToXid(importedXid, this.rtd_list[lcv2]);
                        tos.addOutboundXidToFXid(importedXid, myXid);
                     }
                  }
               }
            }
         }

         if (committedXids != null && committedXids.size() != 0) {
            if (traceEnabled) {
               ntrace.doTrace("/WTCService/startWTC/committed " + committedXids.size() + " transactions");
            }

            tos = getOatmialServices();
            Set rdomsToConnectSet = new HashSet();
            myValueSet = committedXids.entrySet();
            it = myValueSet.iterator();

            label638:
            while(true) {
               do {
                  do {
                     if (!it.hasNext()) {
                        it = rdomsToConnectSet.iterator();

                        while(it.hasNext()) {
                           TDMRemoteTDomain myRdom = (TDMRemoteTDomain)it.next();
                           if (myRdom != null) {
                              myRdom.getTsession(true);
                           }
                        }

                        myValueSet = committedXids.entrySet();
                        it = myValueSet.iterator();

                        while(it.hasNext()) {
                           myEntry = (Map.Entry)it.next();
                           if ((myXid = (Xid)myEntry.getKey()) != null && (String[])((String[])myEntry.getValue()) != null) {
                              DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
                              OatmialCommitter myCommitter = new OatmialCommitter(myXid, oatmialResource, domain.getJTA().getAbandonTimeoutSeconds(), myTimeService);
                              TCTaskHelper.schedule(myCommitter);
                           }
                        }
                        break label638;
                     }

                     myEntry = (Map.Entry)it.next();
                  } while((myXid = (Xid)myEntry.getKey()) == null);
               } while((myRdomPoints = (String[])((String[])myEntry.getValue())) == null);

               for(lcv = 0; lcv < myRdomPoints.length; ++lcv) {
                  searchAccessPoint = myRdomPoints[lcv];

                  for(lcv2 = 0; lcv2 < this.rtd_list.length && !searchAccessPoint.equals(this.rtd_list[lcv2].getAccessPoint()); ++lcv2) {
                  }

                  if (lcv2 >= this.rtd_list.length) {
                     WTCLogger.logErrorTranId(searchAccessPoint);
                  } else {
                     Xid foreignXid = ((EXid)myXid).getForeignXid();
                     if (foreignXid == null) {
                        if (myXid instanceof EXid) {
                           tos.addOutboundRdomToXid(((EXid)myXid).getXid(), this.rtd_list[lcv2]);
                        } else {
                           tos.addOutboundRdomToXid(myXid, this.rtd_list[lcv2]);
                        }
                     } else {
                        tos.addOutboundRdomToXid(foreignXid, this.rtd_list[lcv2]);
                        tos.addOutboundXidToFXid(foreignXid, myXid);
                     }

                     rdomsToConnectSet.add(this.rtd_list[lcv2]);
                  }
               }
            }
         }

         committedXids = null;
         if ((wlsXaResource = TCTransactionHelper.getXAResource()) == null) {
            WTCLogger.logErrorNoTransactionManager();
         }

         try {
            if (wlsXaResource != null) {
               wlsRecoveredXids = wlsXaResource.recover(25165824);
            }

            if (traceEnabled) {
               if (wlsRecoveredXids != null) {
                  ntrace.doTrace("/WTCService/startWTC/recovered " + wlsRecoveredXids.length + " transactions");
               } else {
                  ntrace.doTrace("/WTCService/startWTC/recover returned null");
               }
            }
         } catch (XAException var45) {
            WTCLogger.logWarningXaRecoverFailed();
            if (traceEnabled) {
               ntrace.doTrace("/WTCService/startWTC/recover failed/" + var45);
            }

            wlsRecoveredXids = null;
         }

         int lcv3;
         if (wlsRecoveredXids != null) {
            for(lcv3 = 0; lcv3 < wlsRecoveredXids.length; ++lcv3) {
               if (wlsRecoveredXids[lcv3].getFormatId() != TCTransactionHelper.getXidFormatId() && wlsRecoveredXids[lcv3].getFormatId() != 40) {
                  if (traceEnabled) {
                     ntrace.doTrace("/WTCService/startWTC/ignore unknown xid/" + wlsRecoveredXids[lcv3]);
                  }

                  wlsRecoveredXids[lcv3] = null;
               } else if (traceEnabled) {
                  ntrace.doTrace("/WTCService/startWTC/find owned xid/" + wlsRecoveredXids[lcv3]);
               }
            }
         }

         int lcv4;
         TuxedoLoggable searchAccessPointLog;
         OatmialInboundRecover recoverObject;
         byte[] compareRGid;
         byte[] compareTGid;
         byte optype;
         Object[] myValue;
         int found_lcv;
         if (preparedXids != null && preparedXids.size() != 0) {
            if (traceEnabled) {
               ntrace.doTrace("preparing " + preparedXids.size() + " transactions");
            }

            tos = getOatmialServices();
            myValueSet = preparedXids.entrySet();
            it = myValueSet.iterator();

            label578:
            while(true) {
               do {
                  do {
                     do {
                        if (!it.hasNext()) {
                           break label578;
                        }

                        myEntry = (Map.Entry)it.next();
                     } while((myXid = (Xid)myEntry.getKey()) == null);
                  } while((myValue = (Object[])((Object[])myEntry.getValue())) == null);
               } while((searchAccessPoint = (String)myValue[0]) == null);

               searchAccessPointLog = (TuxedoLoggable)myValue[1];
               found_lcv = -1;

               for(lcv2 = 0; lcv2 < this.rtd_list.length; ++lcv2) {
                  if (searchAccessPoint.equals(this.rtd_list[lcv2].getAccessPoint())) {
                     found_lcv = lcv2;
                     break;
                  }
               }

               if (lcv2 >= this.rtd_list.length) {
                  WTCLogger.logErrorTranId(searchAccessPoint);
               } else {
                  tos.addInboundRdomToXid(myXid, this.rtd_list[lcv2]);
                  tos.addXidToReadyMap(myXid);
               }

               optype = 3;
               if (wlsRecoveredXids != null) {
                  importedXid = null;

                  for(lcv3 = 0; lcv3 < wlsRecoveredXids.length; ++lcv3) {
                     if (wlsRecoveredXids[lcv3] != null) {
                        compareRGid = wlsRecoveredXids[lcv3].getGlobalTransactionId();
                        if (myXid instanceof TuxedoXid) {
                           importedXid = ((TuxedoXid)myXid).getImportedXid();
                           if (importedXid != null) {
                              compareTGid = importedXid.getGlobalTransactionId();
                           } else {
                              compareTGid = myXid.getGlobalTransactionId();
                           }
                        } else {
                           compareTGid = myXid.getGlobalTransactionId();
                        }

                        if (compareRGid.length == compareTGid.length) {
                           for(lcv4 = 0; lcv4 < compareRGid.length && compareRGid[lcv4] == compareTGid[lcv4]; ++lcv4) {
                           }

                           if (lcv4 >= compareRGid.length) {
                              optype = 1;
                              wlsRecoveredXids[lcv3] = null;
                              break;
                           }
                        }
                     }
                  }
               }

               recoverObject = new OatmialInboundRecover(myXid, optype, this.rtd_list[found_lcv], myTimeService, searchAccessPointLog);
               this.runRecoverObject(recoverObject);
               recoverObject = null;
            }
         }

         preparedXids = null;
         if (committingXids != null && committingXids.size() != 0) {
            if (traceEnabled) {
               ntrace.doTrace("committing " + committingXids.size() + " transactions");
            }

            tos = getOatmialServices();
            myValueSet = committingXids.entrySet();
            it = myValueSet.iterator();

            label525:
            while(true) {
               do {
                  do {
                     do {
                        if (!it.hasNext()) {
                           break label525;
                        }

                        myEntry = (Map.Entry)it.next();
                     } while((myXid = (Xid)myEntry.getKey()) == null);
                  } while((myValue = (Object[])((Object[])myEntry.getValue())) == null);
               } while((searchAccessPoint = (String)myValue[0]) == null);

               searchAccessPointLog = (TuxedoLoggable)myValue[1];
               found_lcv = -1;

               for(lcv2 = 0; lcv2 < this.rtd_list.length; ++lcv2) {
                  if (searchAccessPoint.equals(this.rtd_list[lcv2].getAccessPoint())) {
                     found_lcv = lcv2;
                     break;
                  }
               }

               if (lcv2 >= this.rtd_list.length) {
                  WTCLogger.logErrorTranId(searchAccessPoint);
               } else {
                  tos.addInboundRdomToXid(myXid, this.rtd_list[lcv2]);
               }

               optype = 4;
               if (wlsRecoveredXids != null) {
                  for(lcv3 = 0; lcv3 < wlsRecoveredXids.length; ++lcv3) {
                     if (wlsRecoveredXids[lcv3] != null) {
                        compareRGid = wlsRecoveredXids[lcv3].getGlobalTransactionId();
                        compareTGid = myXid.getGlobalTransactionId();
                        if (compareRGid.length == compareTGid.length) {
                           for(lcv4 = 0; lcv4 < compareRGid.length && compareRGid[lcv4] == compareTGid[lcv4]; ++lcv4) {
                           }

                           if (lcv4 >= compareRGid.length) {
                              optype = 2;
                              wlsRecoveredXids[lcv3] = null;
                              break;
                           }
                        }
                     }
                  }
               }

               recoverObject = new OatmialInboundRecover(myXid, optype, this.rtd_list[found_lcv], myTimeService, searchAccessPointLog);
               this.runRecoverObject(recoverObject);
               recoverObject = null;
            }
         }

         committingXids = null;
         if (wlsRecoveredXids != null) {
            optype = 0;

            for(lcv3 = 0; lcv3 < wlsRecoveredXids.length; ++lcv3) {
               if (wlsRecoveredXids[lcv3] != null) {
                  recoverObject = new OatmialInboundRecover(wlsRecoveredXids[lcv3], optype, (TDMRemote)null, myTimeService, (TuxedoLoggable)null);
                  this.runRecoverObject(recoverObject);
               }
            }
         }

         if (ManagementService.getRuntimeAccess(kernelId).getServer().isTGIOPEnabled()) {
            IIOPService.setTGIOPEnabled(true);
         }

         initialized = true;
         if (traceEnabled) {
            ntrace.doTrace("tBridgeStartup = " + this.tBridgeStartup + ", tBridgeConfig = " + this.tBridgeConfig);
         }

         if (!this.tBridgeStartup && (this.tBridgeConfig & 1) == 1 && this.tBridgeConfig >= 3) {
            if (traceEnabled) {
               ntrace.doTrace("start tBridge ...");
            }

            try {
               tBexec.tBmain(this.myWtcSrvrMBean);
               if (traceEnabled) {
                  ntrace.doTrace("tBridge started.");
               }

               this.tBridgeStartup = true;
            } catch (TPException var44) {
               if (var44.gettperrno() == 4) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/startWTC/TBRIDGE config failure");
                  }

                  throw var44;
               }

               if (traceEnabled) {
                  ntrace.doTrace("/WTCService/startWTC/TBRIDGE failed to execute...");
               }
            }
         }

         try {
            this.registerBeanListeners();
         } catch (ManagementException var40) {
            lnr = WTCLogger.logErrorNotificationRegistrationLoggable();
            lnr.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/startWTC/100/Register listeners failure");
            }

            throw new TPException(12, lnr.getMessage());
         }

         Date current = new Date();
         this.wtcStartTime = current.getTime();
         this.wtcStatus = 1;
         if (traceEnabled) {
            ntrace.doTrace("/WTCService/startWTC/110/WTC Service started...");
         }

      }
   }

   private void runRecoverObject(final OatmialInboundRecover recoverObject) {
      TCTaskHelper.schedule(new TCTask() {
         public int execute() {
            try {
               recoverObject.execute((ExecuteThread)null);
               return 0;
            } catch (Exception var2) {
               throw new RuntimeException(var2);
            }
         }

         public void setTaskName(String tname) {
         }

         public String getTaskName() {
            return "runRecoverObject$unknown";
         }
      });
   }

   private void federateRemoteDomain(Context ctx, TDMRemoteTDomain rdom) throws NamingException {
      String ap = rdom.getAccessPoint();
      String tmpStr = rdom.getFederationURL();
      String tmpName = rdom.getFederationName();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String remote_url;
      if (tmpStr != null && tmpStr.length() != 0) {
         remote_url = tmpStr;
      } else {
         remote_url = "tgiop://" + rdom.getAccessPoint();
      }

      String federationPoint = ap;
      if (tmpStr != null && tmpStr.length() != 0) {
         StringTokenizer st = new StringTokenizer(tmpName, "./");
         ctx = myNameService;

         for(int j = st.countTokens(); j > 1; --j) {
            try {
               ctx = ctx.createSubcontext(st.nextToken());
            } catch (NameAlreadyBoundException var12) {
            }
         }

         federationPoint = st.nextToken();
      }

      Reference cnsRef = new Reference("javax.naming.Context", new StringRefAddr("URL", remote_url));
      ctx.rebind(federationPoint, cnsRef);
      if (traceEnabled) {
         ntrace.doTrace("Federating [" + federationPoint + "] to [" + remote_url + "]");
      }

   }

   public static boolean isInitialized() {
      return initialized;
   }

   public static BetaFeatures canUseBetaFeatures() {
      return useBetaFeatures;
   }

   private void extractInfo() throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/extractInfo/");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start LocTuxDom MBeans");
      }

      WTCServerMBean srvrmb = this.myWtcSrvrMBean;
      WTCLocalTuxDomMBean[] ltdmbeans = srvrmb.getLocalTuxDoms();
      this.ltdcnt = ltdmbeans.length;
      if (this.ltdcnt != 0) {
         this.ltd_list = new TDMLocalTDomain[this.ltdcnt];
         if (traceEnabled) {
            ntrace.doTrace("ltdcnt=" + this.ltdcnt);
         }

         for(int ltdi = 0; ltdi < this.ltdcnt; ++ltdi) {
            try {
               TDMLocalTDomain ltd = TDMLocalTDomain.create(ltdmbeans[ltdi]);
               this.ltd_list[ltdi] = ltd;
               ltd.prepareObject();
            } catch (TPException var17) {
               WTCLogger.logErrorExecMBeanDefLoggable(ltdmbeans[ltdi].getName());
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/extractInfo/40/setup error " + ltdmbeans[ltdi].getName());
               }

               throw var17;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("Done LocalTuxDom MBeans");
         }
      } else if (traceEnabled) {
         ntrace.doTrace("No LocalTuxDom MBeans");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start RemoteTuxDom MBeans");
      }

      WTCRemoteTuxDomMBean[] rtdmbeans = srvrmb.getRemoteTuxDoms();
      this.rtdcnt = rtdmbeans.length;
      if (this.rtdcnt != 0) {
         this.rtd_list = new TDMRemoteTDomain[this.rtdcnt];
         if (traceEnabled) {
            ntrace.doTrace("rtdcnt=" + this.rtdcnt);
         }
      }

      for(int rtdi = 0; rtdi < this.rtdcnt; ++rtdi) {
         try {
            TDMRemoteTDomain rtd = this.setupTDMRemoteTD(rtdmbeans[rtdi]);
            this.rtd_list[rtdi] = rtd;
            rtd.prepareObject();
            this.myWtcStatMBean.addComp(rtd);
         } catch (TPException var13) {
            WTCLogger.logErrorExecMBeanDef(rtdmbeans[rtdi].getName());
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/70/setup error " + rtdmbeans[rtdi].getName());
            }

            throw var13;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("Done RemoteTuxDom MBeans");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start Export MBeans");
      }

      WTCExportMBean[] expmbeans = srvrmb.getExports();

      for(int expi = 0; expi < expmbeans.length; ++expi) {
         try {
            TDMExport exp_svc = this.setupTDMExport(expmbeans[expi]);
            this.validateExport(exp_svc);
            this.addExport(exp_svc);
            exp_svc.prepareObject();
            this.myWtcStatMBean.addComp(exp_svc);
         } catch (TPException var15) {
            WTCLogger.logErrorExecMBeanDef(expmbeans[expi].getName());
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/90/setup error " + expmbeans[expi].getName());
            }

            throw var15;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("Done Export MBeans");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start Import MBeans");
      }

      WTCImportMBean[] impmbeans = srvrmb.getImports();

      for(int impi = 0; impi < impmbeans.length; ++impi) {
         TDMImport imp_svc;
         try {
            imp_svc = this.setupTDMImport(impmbeans[impi]);
            this.validateImport(imp_svc);
         } catch (TPException var14) {
            WTCLogger.logErrorExecMBeanDef(impmbeans[impi].getName());
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/110/setup error " + impmbeans[impi].getName());
            }

            throw var14;
         }

         if (!this.addImport(imp_svc)) {
            Loggable lia = WTCLogger.logErrorDupImpSvcLoggable(impmbeans[impi].getName());
            lia.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/115/add export error " + impmbeans[impi].getName());
            }

            throw new TPException(4, lia.getMessage());
         }

         imp_svc.prepareObject();
         this.myWtcStatMBean.addComp(imp_svc);
      }

      if (traceEnabled) {
         ntrace.doTrace("Done Import MBeans");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start Password MBeans");
      }

      WTCPasswordMBean[] pwmbeans = srvrmb.getPasswords();
      this.pwdcnt = pwmbeans.length;
      if (this.pwdcnt != 0) {
         this.pwd_list = new TDMPasswd[this.pwdcnt];
         if (traceEnabled) {
            ntrace.doTrace("pwdcnt=" + this.pwdcnt);
         }
      }

      for(int pwdi = 0; pwdi < pwmbeans.length; ++pwdi) {
         try {
            TDMPasswd tdmpw = this.setupTDMPasswd(pwmbeans[pwdi]);
            this.pwd_list[pwdi] = tdmpw;
            tdmpw.prepareObject();
         } catch (TPException var18) {
            WTCLogger.logErrorExecMBeanDef(pwmbeans[pwdi].getName());
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/140/setup error " + pwmbeans[pwdi].getName());
            }

            throw var18;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("Done Password MBeans");
      }

      if (traceEnabled) {
         ntrace.doTrace("Start Resource MBean");
      }

      WTCResourcesMBean resmbean = srvrmb.getWTCResources();
      if (resmbean != null) {
         try {
            TDMResources localRes = TDMResources.create(resmbean);
            localRes.prepareObject();
            myGlobalResources = localRes;
         } catch (TPException var16) {
            WTCLogger.logErrorExecMBeanDef(resmbean.getName());
            if (traceEnabled) {
               ntrace.doTrace("/WTCService/extractInfo/160/TPException" + var16.getMessage());
            }

            throw var16;
         }
      } else if (traceEnabled) {
         ntrace.doTrace("No Resource MBean found");
      }

      if (traceEnabled) {
         ntrace.doTrace("Done Resource MBean");
      }

      WTCtBridgeGlobalMBean tbgmbean = srvrmb.getWTCtBridgeGlobal();
      if (tbgmbean != null) {
         ++this.tBridgeConfig;
      } else if (traceEnabled) {
         ntrace.doTrace("No tBridgeGlobal found");
      }

      WTCtBridgeRedirectMBean[] redirmbeans = srvrmb.gettBridgeRedirects();
      if (redirmbeans.length != 0) {
         this.tBridgeConfig += 2 * redirmbeans.length;
      } else if (traceEnabled) {
         ntrace.doTrace("No tBridgeRedirect found");
      }

      if ((this.tBridgeConfig & 1) == 1 && this.tBridgeConfig >= 3) {
         if (traceEnabled) {
            ntrace.doTrace("tBridge Enabled.");
         }
      } else if (traceEnabled) {
         ntrace.doTrace("tBridge Not Enabled.");
      }

      if (this.rtd_list != null) {
         try {
            this.crossChecking();
         } catch (TPException var12) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/extractInfo/180/crossChecking error");
            }

            throw var12;
         }

         if (traceEnabled) {
            ntrace.doTrace("process RTD with length = " + this.rtd_list.length);
         }

         for(int i = 0; i < this.rtd_list.length; ++i) {
            this.rtd_list[i].onTerm(1);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/extractInfo/190/DONE");
      }

   }

   public synchronized TDMLocalTDomain getLocalDomain(String ltdname) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getLocalDomain/" + ltdname);
      }

      int index = this.getLTDindex(ltdname);
      if (index >= 0) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getLocalDomain/05/" + index);
         }

         return this.ltd_list[index];
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getLocalDomain/10/null");
         }

         return null;
      }
   }

   private synchronized int getLTDindex(String ltdname) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getLTDindex/");
      }

      if (ltdname == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getLTDindex/05/-1");
         }

         return -1;
      } else {
         for(int i = 0; i < this.ltdcnt; ++i) {
            if (this.ltd_list[i].getAccessPoint().equals(ltdname)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getLTDindex/10/" + i);
               }

               return i;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getLTDindex/20/-1");
         }

         return -1;
      }
   }

   private synchronized int getRTDindex(String rtdname) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getRTDindex/");
      }

      if (rtdname == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRTDindex/05/-1");
         }

         return -1;
      } else {
         for(int i = 0; i < this.rtdcnt; ++i) {
            if (this.rtd_list[i].getAccessPoint().equals(rtdname)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getRTDindex/10/" + i);
               }

               return i;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRTDindex/20/-1");
         }

         return -1;
      }
   }

   public synchronized TDMRemoteTDomain getRTDbyAP(String rtdname) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getRTDbyAP/" + rtdname + " rtdcnt " + this.rtdcnt);
      }

      if (rtdname == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRTDbyAP/05/null argument");
         }

         return null;
      } else {
         for(int i = 0; i < this.rtdcnt; ++i) {
            if (this.rtd_list[i].getAccessPoint().equals(rtdname)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getRTDbyAP/10/found it");
               }

               return this.rtd_list[i];
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getRTDbyAP/15/did not find it");
         }

         return null;
      }
   }

   public synchronized TDMRemoteTDomain getVTDomainSession(String local, String remote) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getVTDomainSession/(" + local + ", " + remote + "), rtdcnt " + this.rtdcnt);
      }

      if (local != null && remote != null) {
         for(int i = 0; i < this.rtdcnt; ++i) {
            if (this.rtd_list[i].getAccessPoint().equals(remote) && this.rtd_list[i].getLocalAccessPoint().equals(local)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getVTDomainSession/20/found it");
               }

               return this.rtd_list[i];
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getVTDomainSession/30/did not find it");
         }

         return null;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getVTDomainSession/10/null argument");
         }

         return null;
      }
   }

   private void crossChecking() throws TPException {
      HashMap domid_map = new HashMap();
      HashMap rdom_map = new HashMap();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/crossCheck/");
      }

      int i;
      RDomainListEntry entry;
      for(i = 0; i < this.rtd_list.length; ++i) {
         TDMRemoteTDomain rdom = this.rtd_list[i];

         try {
            rdom.checkConfigIntegrity();
         } catch (TPException var14) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/crossCheck/10/RDOM checkConfigInteg");
            }

            throw var14;
         }

         domid_map.put(rdom.getAccessPointId(), rdom);
         RDomainListEntry crdle = new RDomainListEntry(rdom);
         if ((entry = (RDomainListEntry)rdom_map.put(rdom.getAccessPointId(), crdle)) != null) {
            crdle.setNext(entry);
         }
      }

      for(i = 0; i < this.ltd_list.length; ++i) {
         TDMLocalTDomain ldom = this.ltd_list[i];

         try {
            ldom.checkConfigIntegrity();
         } catch (TPException var13) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/crossCheck/20/LDOM checkConfigInteg");
            }

            throw var13;
         }

         if (domid_map.put(ldom.getAccessPointId(), ldom) != null) {
            Loggable le = WTCLogger.logErrorDuplicatedLocalDomainLoggable(ldom.getAccessPointId());
            le.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/crossCheck/30/duplicated LDOM");
            }

            throw new TPException(4, le.getMessage());
         }
      }

      Iterator rdomIterator = rdom_map.values().iterator();

      while(rdomIterator.hasNext()) {
         entry = (RDomainListEntry)rdomIterator.next();
         HashMap ldom_map = new HashMap();

         while(true) {
            TDMRemoteTDomain rtde = entry.getRDom();
            TDMLocal ltde = rtde.getLocalAccessPointObject();
            if (ltde == null) {
               return;
            }

            if (ldom_map.put(ltde.getAccessPointId(), ltde) != null) {
               Loggable le = WTCLogger.logErrorDuplicatedRemoteDomainLoggable(rtde.getAccessPointId(), ltde.getAccessPointId());
               le.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/crossCheck/40/duplicated RDOM");
               }

               throw new TPException(4, le.getMessage());
            }

            if ((entry = entry.getNext()) == null) {
               break;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/crossCheck/50/success");
      }

   }

   public boolean updateRuntimeViewList(String vname, Class vclass, int mflags) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/update_runtime_viewlist/view=" + vname);
      }

      new ViewHelper();
      ViewHelper vinst = ViewHelper.getInstance();
      vinst.setViewClass(vname, vclass);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/update_runtime_viewlist/10/true");
      }

      return true;
   }

   public String[] parseCommaSeparatedList(String str) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/parseCommaSeparatedList/" + str);
      }

      String[] csl;
      if (str.indexOf(44) == -1) {
         csl = new String[]{str};
      } else {
         StringTokenizer st = new StringTokenizer(str, ",");
         csl = new String[st.countTokens()];

         for(int i = 0; st.hasMoreTokens(); ++i) {
            csl[i] = st.nextToken();
            if (traceEnabled) {
               ntrace.doTrace("token " + i + ": " + csl[i]);
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/parseCommaSeparatedList/10/");
      }

      return csl;
   }

   private boolean validateLocalTuxDom(TDMLocalTDomain ldom) {
      String ap = ldom.getAccessPoint();
      String apId = ldom.getAccessPointId();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/validateLocalTuxDom/" + ap);
      }

      for(int i = 0; i < this.ltdcnt; ++i) {
         if (ap.equals(this.ltd_list[i].getAccessPoint()) || apId.equals(this.ltd_list[i].getAccessPointId())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/validateLocalTuxDom/10/false");
            }

            return false;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/validateLocalTuxDom/10/success");
      }

      return true;
   }

   private boolean addLocalTuxDom(TDMLocalTDomain ldom) {
      String ap = ldom.getAccessPoint();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addLocalTuxDom/" + ap);
      }

      if (this.ltdcnt == 0) {
         this.ltd_list = new TDMLocalTDomain[1];
      } else {
         TDMLocalTDomain[] oldList = this.ltd_list;
         this.ltd_list = new TDMLocalTDomain[this.ltdcnt + 1];
         System.arraycopy(oldList, 0, this.ltd_list, 0, this.ltdcnt);
      }

      this.ltd_list[this.ltdcnt] = ldom;
      ++this.ltdcnt;
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/addLocalTuxDom/10/success");
      }

      return true;
   }

   private TDMLocalTDomain getLocalTuxDomByMBean(WTCLocalTuxDomMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getLocalTuxDomByMBean/" + mb.getAccessPoint());
      }

      for(int i = 0; i < this.ltdcnt; ++i) {
         if (this.ltd_list[i].getAccessPoint().equals(mb.getAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getLocalTuxDomByMBean/10/found it");
            }

            return this.ltd_list[i];
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getLocalTuxDomByMBean/20/not found");
      }

      return null;
   }

   private boolean checkLocalTuxDomInUse(TDMLocalTDomain ldom) {
      String ap = ldom.getAccessPoint();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkLocalTuxDomInUse/" + ap);
      }

      int idx;
      for(idx = 0; idx < this.rtdcnt; ++idx) {
         if (ap.equals(this.rtd_list[idx].getLocalAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/checkLocalTuxDomInUse/10/in use by RDOM: " + this.rtd_list[idx].getAccessPoint());
            }

            return true;
         }
      }

      for(idx = 0; idx < this.pwdcnt; ++idx) {
         if (ap.equals(this.pwd_list[idx].getLocalAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/checkLocalTuxDomInUse/20/in use by PWD: (" + ldom.getAccessPoint() + ", " + this.rtd_list[idx].getAccessPoint() + ")");
            }

            return true;
         }
      }

      Iterator iter = this.myImportedServices.values().iterator();

      Iterator si;
      Set hs;
      while(iter.hasNext()) {
         hs = (Set)iter.next();
         si = hs.iterator();

         while(si.hasNext()) {
            TDMImport imp = (TDMImport)si.next();
            if (ap.equals(imp.getLocalAccessPoint())) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/checkLocalTuxDomInUse/30/in use by RSVC: " + imp.getResourceName());
               }

               return true;
            }
         }
      }

      iter = this.myExportedServices.values().iterator();

      while(iter.hasNext()) {
         hs = (Set)iter.next();
         si = hs.iterator();

         while(si.hasNext()) {
            TDMExport svc = (TDMExport)si.next();
            if (ap.equals(svc.getLocalAccessPoint())) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/checkLocalTuxDomInUse/40/in use by LSVC: " + svc.getResourceName());
               }

               return true;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/checkLocalTuxDomInUse/50/not in use");
      }

      return false;
   }

   private void removeLocalTuxDom(TDMLocalTDomain ldom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeLocalTuxDom/" + ldom.getAccessPoint());
      }

      int idx;
      for(idx = 0; idx < this.ltdcnt && this.ltd_list[idx] != ldom; ++idx) {
      }

      if (idx >= this.ltdcnt) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removeLocalTuxDom/10/not found");
         }

      } else {
         TDMLocalTDomain[] newList = new TDMLocalTDomain[this.ltdcnt - 1];
         this.removeFromArray(this.ltd_list, this.ltdcnt, newList, idx);
         --this.ltdcnt;
         this.ltd_list = newList;
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removeLocalTuxDom/20/success");
         }

      }
   }

   private TDMRemoteTDomain setupTDMRemoteTD(WTCRemoteTuxDomMBean mb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/setupTDMRemoteTD/");
      }

      String accpnt = mb.getAccessPoint();
      if (accpnt == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/10/no AP");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("AccessPoint: " + accpnt);
         }

         String accpntId = mb.getAccessPointId();
         if (accpntId == null) {
            Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("AccessPointId", mb.getName());
            lua.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/20/no APId");
            }

            throw new TPException(4, lua.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("AccessPointId:" + accpntId);
            }

            String lapnm = mb.getLocalAccessPoint();
            if (lapnm == null) {
               Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("LocalAccessPoint", mb.getName());
               lua.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/30/no LAP");
               }

               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("LocalAccessPoint:" + lapnm);
               }

               String nwaddr = mb.getNWAddr();
               if (nwaddr == null) {
                  Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("NWAddr", mb.getName());
                  lua.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/40/no NWAddr");
                  }

                  throw new TPException(4, lua.getMessage());
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("NWAddr:" + nwaddr);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("create rtd from " + accpnt);
                  }

                  TDMRemoteTDomain rtd;
                  Loggable lie;
                  try {
                     rtd = new TDMRemoteTDomain(accpnt, this.unknownTxidRply, myTimeService);
                  } catch (Exception var15) {
                     lie = WTCLogger.logUEconstructTDMRemoteTDLoggable(var15.getMessage());
                     lie.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/50/create failed");
                     }

                     throw new TPException(4, lie.getMessage());
                  }

                  TDMLocalTDomain lap = this.getLocalDomain(lapnm);
                  if (null == lap) {
                     lie = WTCLogger.logErrorBadTDMRemoteLTDLoggable(lapnm);
                     lie.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/60/no LDOM");
                     }

                     throw new TPException(4, lie.getMessage());
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("valid LocalAccessPoint");
                     }

                     rtd.setLocalAccessPoint(lapnm);
                     rtd.setAccessPointId(accpntId);
                     rtd.setAclPolicy(mb.getAclPolicy());
                     rtd.setCredentialPolicy(mb.getCredentialPolicy());
                     rtd.setTpUsrFile(mb.getTpUsrFile());

                     Loggable lia;
                     try {
                        rtd.setNWAddr(nwaddr);
                     } catch (TPException var16) {
                        lia = WTCLogger.logInvalidMBeanAttrLoggable("NWAddr", mb.getName());
                        lia.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/70/" + var16.getMessage());
                        }

                        throw new TPException(4, lia.getMessage());
                     }

                     rtd.setFederationURL(mb.getFederationURL());
                     rtd.setFederationName(mb.getFederationName());

                     try {
                        rtd.setCmpLimit(mb.getCmpLimit());
                     } catch (TPException var14) {
                        lia = WTCLogger.logInvalidMBeanAttrLoggable("CmpLimit", mb.getName());
                        lia.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/80/" + var14.getMessage());
                        }

                        throw new TPException(4, lia.getMessage());
                     }

                     String tmps = mb.getMinEncryptBits();
                     if (tmps != null) {
                        rtd.setMinEncryptBits(Integer.parseInt(tmps, 10));
                     }

                     tmps = mb.getMaxEncryptBits();
                     if (tmps != null) {
                        rtd.setMaxEncryptBits(Integer.parseInt(tmps, 10));
                     }

                     rtd.setConnectionPolicy(mb.getConnectionPolicy());
                     rtd.setRetryInterval(mb.getRetryInterval());
                     rtd.setMaxRetries(mb.getMaxRetries());
                     rtd.setKeepAlive(mb.getKeepAlive());
                     rtd.setKeepAliveWait(mb.getKeepAliveWait());
                     String sel = mb.getAppKey();
                     if (sel == null && mb.getTpUsrFile() != null) {
                        sel = new String("TpUsrFile");
                        if (traceEnabled) {
                           ntrace.doTrace("Use dflt AppKey Generator");
                        }
                     }

                     rtd.setAppKey(sel);
                     rtd.setAllowAnonymous(mb.getAllowAnonymous());
                     rtd.setDefaultAppKey(mb.getDefaultAppKey());
                     if (sel != null) {
                        if (sel.equals("LDAP")) {
                           rtd.setTuxedoUidKw(mb.getTuxedoUidKw());
                           rtd.setTuxedoGidKw(mb.getTuxedoGidKw());
                           if (traceEnabled) {
                              ntrace.doTrace("LDAP, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",UID KW=" + mb.getTuxedoUidKw() + ", GID KW=" + mb.getTuxedoGidKw());
                           }
                        } else if (sel.equals("Custom")) {
                           String cls = mb.getCustomAppKeyClass();
                           String clsp = mb.getCustomAppKeyClassParam();
                           if (cls == null) {
                              Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("CustomAppKeyClass", mb.getName());
                              lua.log();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/90/no custom class defined");
                              }

                              throw new TPException(4, lua.getMessage());
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("Custom, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",Class=" + cls + ", Parm =" + clsp);
                           }

                           rtd.setCustomAppKeyClass(cls);
                           rtd.setCustomAppKeyClassParam(clsp);
                        } else {
                           if (!sel.equals("TpUsrFile")) {
                              Loggable lia = WTCLogger.logInvalidMBeanAttrLoggable("AppKey", mb.getName());
                              lia.log();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/WTCService/setupTDMRemoteTD/100/unsupported appkey");
                              }

                              throw new TPException(4, lia.getMessage());
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("TpUsrFile, allow=" + mb.getAllowAnonymous() + ",Dflt AppKey=" + mb.getDefaultAppKey() + ",File=" + mb.getTpUsrFile());
                           }
                        }
                     }

                     rtd.setMBean(mb);
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCService/setupTDMRemoteTD/140/success");
                     }

                     return rtd;
                  }
               }
            }
         }
      }
   }

   private boolean validateRemoteTuxDom(TDMRemoteTDomain rdom) {
      String lap = rdom.getLocalAccessPoint();
      String rap = rdom.getAccessPoint();
      String apId = rdom.getAccessPointId();
      String nwaddr = rdom.getNWAddr();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/validateRemoteTuxDom/(" + lap + ", " + rap + ")");
      }

      int idx;
      for(idx = 0; idx < this.rtdcnt; ++idx) {
         if (rap.equals(this.rtd_list[idx].getAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/validateRemoteTuxDom/10/false");
            }

            return false;
         }

         if (apId.equals(this.rtd_list[idx].getAccessPointId()) && lap.equals(this.rtd_list[idx].getLocalAccessPoint()) && nwaddr.equals(this.rtd_list[idx].getNWAddr())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/validateRemoteTuxDom/20/false");
            }

            return false;
         }
      }

      for(idx = 0; idx < this.ltdcnt; ++idx) {
         if (rap.equals(this.ltd_list[idx].getAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/validateRemoteTuxDom/30/false");
            }

            return false;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/validateRemoteTuxDom/40/success");
      }

      return true;
   }

   private TDMRemoteTDomain getRemoteTuxDomByMBean(WTCRemoteTuxDomMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String lap = mb.getLocalAccessPoint();
      String rap = mb.getAccessPoint();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getRemoteTuxDomByMBean/(" + lap + ", " + rap + ")");
      }

      for(int idx = 0; idx < this.rtdcnt; ++idx) {
         if (this.rtd_list[idx].getLocalAccessPoint().equals(lap) && this.rtd_list[idx].getAccessPoint().equals(rap)) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getRemoteTuxDomByMBean/10/found it");
            }

            return this.rtd_list[idx];
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getRemoteTuxDomByMBean/20/not found");
      }

      return null;
   }

   private boolean addRemoteTuxDom(TDMRemoteTDomain rdom) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addRemoteTuxDom/(" + rdom.getLocalAccessPoint() + ", " + rdom.getAccessPoint() + ")");
      }

      TDMRemoteTDomain[] oldList = this.rtd_list;
      this.rtd_list = new TDMRemoteTDomain[this.rtdcnt + 1];
      if (this.rtdcnt > 0) {
         System.arraycopy(oldList, 0, this.rtd_list, 0, this.rtdcnt);
      }

      this.rtd_list[this.rtdcnt] = rdom;
      ++this.rtdcnt;
      this.myWtcStatMBean.addComp(rdom);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/addRemoteTuxDom/10/success");
      }

      return true;
   }

   private boolean checkRemoteTuxDomInUse(TDMRemoteTDomain rdom) {
      String ap = rdom.getAccessPoint();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkRemoteTuxDomInUse/(" + rdom.getLocalAccessPoint() + ", " + ap + ")");
      }

      if (rdom.getTsession(false) != null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/checkRemoteTuxDomInUse/10/has active session");
         }

         return true;
      } else {
         for(int i = 0; i < this.pwdcnt; ++i) {
            if (ap.equals(this.pwd_list[i].getRemoteAccessPoint())) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/checkRemoteTuxDomInUse/20/pwd reference");
               }

               return true;
            }
         }

         Iterator iter = this.myImportedServices.values().iterator();

         while(iter.hasNext()) {
            Set hs = (Set)iter.next();
            Iterator si = hs.iterator();

            while(si.hasNext()) {
               TDMImport imp = (TDMImport)si.next();
               if (imp.hasRemoteDomain(rdom)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCService/checkRemoteTuxDomInUse/30/import reference");
                  }

                  return true;
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/checkRemoteTuxDomInUse/40/not in use");
         }

         return false;
      }
   }

   private void removeRemoteTuxDom(TDMRemoteTDomain rdom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeRemoteTuxDom/(" + rdom.getLocalAccessPoint() + ", " + rdom.getAccessPoint() + ")");
      }

      int idx;
      for(idx = 0; idx < this.rtdcnt && this.rtd_list[idx] != rdom; ++idx) {
      }

      if (idx >= this.rtdcnt) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removeRemoteTuxDom/10/not found");
         }

      } else {
         TDMLocal ldom = rdom.getLocalAccessPointObject();
         if (ldom != null) {
            ldom.remove_remote_domain(rdom);
         }

         TDMRemoteTDomain[] newList = new TDMRemoteTDomain[this.rtdcnt - 1];
         this.removeFromArray(this.rtd_list, this.rtdcnt, newList, idx);
         --this.rtdcnt;
         this.rtd_list = newList;
         this.myWtcStatMBean.removeComp(rdom);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removeRemoteTuxDom/20/success");
         }

      }
   }

   private TDMImport setupTDMImport(WTCImportMBean mb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/setupTDMImport/" + mb.getResourceName());
      }

      String lapnm = mb.getLocalAccessPoint();
      if (lapnm == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("LocalAccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/setupTDMImport/10/LAP equals null");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("LocalAccessPoint:" + lapnm);
         }

         TDMLocalTDomain iltd = this.getLocalDomain(lapnm);
         if (null == iltd) {
            Loggable lia = WTCLogger.logErrorBadTDMImportLTDLoggable(lapnm);
            lia.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/setupTDMImport/20/LAP not found");
            }

            throw new TPException(4, lia.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("valid LocalAccessPoint");
            }

            String resourcename = mb.getResourceName();
            if (resourcename == null) {
               Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("ResourceName", mb.getName());
               lua.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/setupTDMImport/30/Resource is null");
               }

               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("ResourceName:" + resourcename);
               }

               String raplst = mb.getRemoteAccessPointList();
               Loggable lus;
               if (raplst == null) {
                  lus = WTCLogger.logUndefinedMBeanAttrLoggable("RemoteAccessPointList", mb.getName());
                  lus.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/setupTDMImport/40/RAP List is null");
                  }

                  throw new TPException(4, lus.getMessage());
               } else {
                  TDMRemoteTDomain irtd;
                  TDMRemoteTDomain[] irdoms;
                  TDMLocal lap;
                  if (raplst.indexOf(44) == -1) {
                     irtd = this.getRTDbyAP(raplst);
                     if (null == irtd) {
                        lus = WTCLogger.logErrorBadTDMImportRTDLoggable(raplst);
                        lus.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/WTCService/setupTDMImport/41/RAP " + raplst + " not found");
                        }

                        throw new TPException(4, lus.getMessage());
                     }

                     if ((lap = irtd.getLocalAccessPointObject()) == null) {
                        irtd.setLocalAccessPoint(lapnm);
                     } else if (!lap.getAccessPoint().equals(lapnm)) {
                        lus = WTCLogger.logErrorUndefinedTDomainSessionLoggable(resourcename, lapnm, raplst);
                        lus.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/WTCService/setupTDMImport/42/TSession(" + raplst + ", " + lapnm + ") not found");
                        }

                        throw new TPException(4, lus.getMessage());
                     }

                     irdoms = new TDMRemoteTDomain[]{irtd};
                  } else {
                     StringTokenizer st = new StringTokenizer(raplst, ",");
                     irdoms = new TDMRemoteTDomain[st.countTokens()];

                     for(int j = 0; st.hasMoreTokens(); ++j) {
                        String rapnm = st.nextToken();
                        irtd = this.getRTDbyAP(rapnm);
                        Loggable lus;
                        if (null == irtd) {
                           lus = WTCLogger.logErrorBadTDMImportRTDLoggable(rapnm);
                           lus.log();
                           if (traceEnabled) {
                              ntrace.doTrace("*]/WTCService/setupTDMImport/50/RAP " + rapnm + " not found");
                           }

                           throw new TPException(4, lus.getMessage());
                        }

                        if ((lap = irtd.getLocalAccessPointObject()) == null) {
                           irtd.setLocalAccessPoint(lapnm);
                        } else if (!lap.getAccessPoint().equals(lapnm)) {
                           lus = WTCLogger.logErrorUndefinedTDomainSessionLoggable(resourcename, lapnm, rapnm);
                           lus.log();
                           if (traceEnabled) {
                              ntrace.doTrace("*]/WTCService/setupTDMImport/51/TSession(" + rapnm + ", " + lapnm + ") not found");
                           }

                           throw new TPException(4, lus.getMessage());
                        }

                        irdoms[j] = irtd;
                     }
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("valid RemoteAccessPointList");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("create imp from " + resourcename);
                  }

                  TDMImport imp_svc;
                  try {
                     imp_svc = new TDMImport(resourcename, iltd, irdoms);
                  } catch (Exception var15) {
                     Loggable le = WTCLogger.logUEconstructTDMImportLoggable(var15.getMessage());
                     le.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/setupTDMImport/50/create import failed");
                     }

                     throw new TPException(4, le.getMessage());
                  }

                  String tmps = mb.getRemoteName();
                  if (tmps != null) {
                     imp_svc.setRemoteName(tmps);
                  } else {
                     imp_svc.setRemoteName(resourcename);
                  }

                  imp_svc.setRemoteAccessPointListString(raplst);
                  imp_svc.setMBean(mb);
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCService/setupTDMImport/60/success");
                  }

                  return imp_svc;
               }
            }
         }
      }
   }

   private boolean validateImport(TDMImport rsvc) throws TPException {
      String svcname = rsvc.getResourceName();
      String lap = rsvc.getLocalAccessPoint();
      String[] rap = rsvc.getRemoteAccessPointList();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/validateImport/" + lap + "(" + svcname + ", " + rsvc.getResourceName() + ")");
      }

      Set hs = (Set)this.myImportedServices.get(svcname);
      if (hs != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMImport tmp = (TDMImport)iter.next();
            String[] tmp_rap = tmp.getRemoteAccessPointList();
            if (lap.equals(tmp.getLocalAccessPoint()) && rap[0].equals(tmp_rap[0])) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/validateImport/10/duplicate remote service name " + svcname);
               }

               throw new TPException(4, "ERROR: Duplicate imported service entry for (" + lap + "," + rap[0] + ") with resource name(" + svcname + ")!");
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/validateImport/20/success");
      }

      return true;
   }

   private TDMImport getImportByMBean(WTCImportMBean mb) {
      String rname = mb.getRemoteName();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String svcName = mb.getResourceName();
      String lap = mb.getLocalAccessPoint();
      String rapString = mb.getRemoteAccessPointList();
      String[] rapList = TDMImport.parseCommaSeparatedList(rapString);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getImportByMBean/(" + svcName + ", remote " + rname + ")");
      }

      Set hs;
      if ((hs = (Set)this.myImportedServices.get(svcName)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getImportByMBean/10/set is empty");
         }

         return null;
      } else if (hs.size() == 0) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getImportByMBean/20/set is 0 length");
         }

         return null;
      } else {
         Iterator iter = hs.iterator();

         TDMImport tmp;
         String primary;
         do {
            if (!iter.hasNext()) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getImportByMBean/40/not found");
               }

               return null;
            }

            tmp = (TDMImport)iter.next();
            primary = tmp.getPrimaryRemoteAccessPoint();
         } while(!primary.equals(rapList[0]) || !tmp.getLocalAccessPoint().equals(lap));

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getImportByMBean/30/success");
         }

         return tmp;
      }
   }

   private TDMImport getTmpImportByMBean(WTCImportMBean mb) {
      String rname = mb.getRemoteName();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String svcName = mb.getResourceName();
      String lap = mb.getLocalAccessPoint();
      String rapString = mb.getRemoteAccessPointList();
      String[] rapList = TDMImport.parseCommaSeparatedList(rapString);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getTmpImportByMBean/(" + svcName + ", remote " + rname + ")");
      }

      Set hs;
      if ((hs = (Set)this.tmpImportedServices.get(svcName)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getTmpImportByMBean/10/set is empty");
         }

         return null;
      } else if (hs.size() == 0) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getTmpImportByMBean/20/set is 0 length");
         }

         return null;
      } else {
         Iterator iter = hs.iterator();

         TDMImport tmp;
         String primary;
         do {
            if (!iter.hasNext()) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getTmpImportByMBean/40/not found");
               }

               return null;
            }

            tmp = (TDMImport)iter.next();
            primary = tmp.getPrimaryRemoteAccessPoint();
         } while(!primary.equals(rapList[0]) || !tmp.getLocalAccessPoint().equals(lap));

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/getTmpImportByMBean/30/success");
         }

         return tmp;
      }
   }

   private boolean addImport(TDMImport rsvc) {
      String rname = rsvc.getRemoteName();
      String rsvcName = rsvc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addImport/" + rsvcName + ", remote " + rname);
      }

      Set hs;
      if ((hs = (Set)this.myImportedServices.get(rsvcName)) == null) {
         String n = new String(rsvcName);
         hs = Collections.synchronizedSet(new HashSet());
         this.myImportedServices.put(n, hs);
      }

      if (!hs.add(rsvc)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addImport/10/failed to add");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addImport/20/success");
         }

         return true;
      }
   }

   private boolean addTmpImport(TDMImport rsvc) {
      String rname = rsvc.getRemoteName();
      String rsvcName = rsvc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addTmpImport/" + rsvcName + ", remote " + rname);
      }

      Set hs;
      if ((hs = (Set)this.tmpImportedServices.get(rsvcName)) == null) {
         String n = new String(rsvcName);
         hs = Collections.synchronizedSet(new HashSet());
         this.tmpImportedServices.put(n, hs);
      }

      if (!hs.add(rsvc)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addTmpImport/10/failed to add");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addTmpImport/20/success");
         }

         return true;
      }
   }

   private boolean checkImportInUse(TDMImport rsvc) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkImportInUse/" + rsvc.getResourceName() + ", remote " + rsvc.getRemoteName());
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/checkImportInUse/20/false");
      }

      return false;
   }

   private void removeImport(TDMImport rsvc) {
      String rsvcName = rsvc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeImport/" + rsvcName + ", for LAP: " + rsvc.getLocalAccessPoint());
      }

      Set hs;
      if ((hs = (Set)this.myImportedServices.get(rsvcName)) != null) {
         hs.remove(rsvc);
         if (hs.isEmpty()) {
            this.myImportedServices.remove(rsvcName);
         }
      }

      this.myWtcStatMBean.removeComp(rsvc);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/removeImport/10/false");
      }

   }

   private void removeTmpImport(TDMImport rsvc) {
      String rsvcName = rsvc.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeTmpImport/" + rsvcName + ", for LAP: " + rsvc.getLocalAccessPoint());
      }

      Set hs;
      if ((hs = (Set)this.tmpImportedServices.get(rsvcName)) != null) {
         hs.remove(rsvc);
         if (hs.isEmpty()) {
            this.tmpImportedServices.remove(rsvcName);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/removeTmpImport/10/false");
      }

   }

   private TDMExport setupTDMExport(WTCExportMBean mb) throws TPException {
      String resourcename = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/setupTDMExport/" + resourcename);
      }

      String lapnm = mb.getLocalAccessPoint();
      if (lapnm == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("LocalAccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]WTCService/setupTDMExport/10/missing LAP");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("LocalAccessPoint:" + lapnm);
         }

         TDMLocalTDomain eltd = this.getLocalDomain(lapnm);
         Loggable lua;
         if (null == eltd) {
            if (traceEnabled) {
               ntrace.doTrace("*]WTCService/setupTDMExport/20/LAP does not exist");
            }

            lua = WTCLogger.logErrorBadTDMExportLTDLoggable(lapnm);
            lua.log();
            throw new TPException(4, lua.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("valid LocalAccessPoint");
            }

            if (resourcename == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]WTCService/setupTDMExport/30/missing resource name");
               }

               lua = WTCLogger.logUndefinedMBeanAttrLoggable("ResourceName", mb.getName());
               lua.log();
               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("ResourceName:" + resourcename);
               }

               if (traceEnabled) {
                  ntrace.doTrace("create export from " + resourcename + ", lapnm:" + lapnm);
               }

               TDMExport exp_svc;
               try {
                  exp_svc = new TDMExport(resourcename, eltd);
               } catch (Exception var9) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]WTCService/setupTDMExport/40/create Export failed");
                  }

                  Loggable le = WTCLogger.logUEconstructTDMExportLoggable(var9.getMessage());
                  le.log();
                  throw new TPException(4, le.getMessage());
               }

               String tmps = mb.getRemoteName();
               if (tmps != null) {
                  exp_svc.setRemoteName(tmps);
               } else {
                  exp_svc.setRemoteName(resourcename);
               }

               tmps = mb.getEJBName();
               String tmptc = mb.getTargetClass();
               if (tmps == null && tmptc == null) {
                  tmps = "tuxedo.services." + resourcename + "Home";
               }

               exp_svc.setEJBName(tmps);
               exp_svc.setTargetClass(tmptc);
               exp_svc.setTargetJar(mb.getTargetJar());
               exp_svc.setMBean(mb);
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/setupTDMExport/50/success");
               }

               return exp_svc;
            }
         }
      }
   }

   private boolean validateExport(TDMExport lsvc) throws TPException {
      String rname = lsvc.getRemoteName();
      String lap = lsvc.getLocalAccessPoint();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/validateExport/" + lap + "(" + lsvc.getResourceName() + ", " + rname + ")");
      }

      Set hs;
      if ((hs = (Set)this.myExportedServices.get(rname)) != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMExport tmp = (TDMExport)iter.next();
            if (lap.equals(tmp.getLocalAccessPoint())) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/validateExport/10/duplicate export");
               }

               throw new TPException(4, "ERROR: Duplicate exported service entry for " + lap + " with remote name(" + rname + ")!");
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/validateExport/20/success");
      }

      return true;
   }

   private TDMExport getExportByMBean(WTCExportMBean mb) {
      String svc_name = mb.getRemoteName();
      Set hs = (Set)this.myExportedServices.get(svc_name);
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String lap = mb.getLocalAccessPoint();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getExportByMBean/(" + lap + ", " + svc_name + ")");
      }

      if (hs != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMExport tmp = (TDMExport)iter.next();
            if (tmp.getLocalAccessPoint().equals(lap)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getExportByMBean/10/found");
               }

               return tmp;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getExportByMBean/20/not found");
      }

      return null;
   }

   private TDMExport getTmpExportByMBean(WTCExportMBean mb) {
      String svc_name = mb.getRemoteName();
      Set hs = (Set)this.tmpExportedServices.get(svc_name);
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String lap = mb.getLocalAccessPoint();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getTmpExportByMBean/(" + lap + ", " + svc_name + ")");
      }

      if (hs != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMExport tmp = (TDMExport)iter.next();
            if (tmp.getLocalAccessPoint().equals(lap)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/getTmpExportByMBean/10/found");
               }

               return tmp;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getTmpExportByMBean/20/not found");
      }

      return null;
   }

   private boolean addExport(TDMExport lsvc) {
      String rname = lsvc.getRemoteName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addExport/" + lsvc.getResourceName());
      }

      Set found_set;
      if ((found_set = (Set)this.myExportedServices.get(rname)) == null) {
         found_set = Collections.synchronizedSet(new HashSet());
         String nsvc = new String(rname);
         this.myExportedServices.put(nsvc, found_set);
      }

      if (!found_set.add(lsvc)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addExport/10/add failed");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addExport/20/success");
         }

         return true;
      }
   }

   private boolean addTmpExport(TDMExport lsvc) {
      String rname = lsvc.getRemoteName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addTmpExport/" + lsvc.getResourceName());
      }

      Set found_set;
      if ((found_set = (Set)this.tmpExportedServices.get(rname)) == null) {
         found_set = Collections.synchronizedSet(new HashSet());
         String nsvc = new String(rname);
         this.tmpExportedServices.put(nsvc, found_set);
      }

      if (!found_set.add(lsvc)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addExport/10/add failed");
         }

         return false;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/addExport/20/success");
         }

         return true;
      }
   }

   private boolean checkExportInUse(TDMExport lsvc) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkExportInUse/" + lsvc.getResourceName());
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/checkExportInUse/20/not in use");
      }

      return false;
   }

   private void removeExport(TDMExport lsvc) {
      String rname = lsvc.getRemoteName();
      Set hs = (Set)this.myExportedServices.get(rname);
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeExport/" + lsvc.getResourceName() + ", remote name: " + rname + " for LAP: " + lsvc.getLocalAccessPoint());
      }

      if (hs != null) {
         hs.remove(lsvc);
         if (hs.isEmpty()) {
            this.myExportedServices.remove(rname);
         }
      }

      this.myWtcStatMBean.removeComp(lsvc);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/removeExport/10/success");
      }

   }

   private void removeTmpExport(TDMExport lsvc) {
      String rname = lsvc.getRemoteName();
      Set hs = (Set)this.tmpExportedServices.get(rname);
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeTmpExport/" + lsvc.getResourceName() + ", remote name: " + rname + " for LAP: " + lsvc.getLocalAccessPoint());
      }

      if (hs != null) {
         hs.remove(lsvc);
         if (hs.isEmpty()) {
            this.tmpExportedServices.remove(rname);
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/removeTmpExport/10/success");
      }

   }

   private TDMPasswd setupTDMPasswd(WTCPasswordMBean mb) throws TPException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/setupTDMPasswd/");
      }

      String lapnm = mb.getLocalAccessPoint();
      if (lapnm == null) {
         Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("LocalAccessPoint", mb.getName());
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/setupTDMPasswd/10/no LAP");
         }

         throw new TPException(4, lua.getMessage());
      } else {
         if (traceEnabled) {
            ntrace.doTrace("localAccessPoint:" + lapnm);
         }

         TDMLocalTDomain pwltd = this.getLocalDomain(lapnm);
         if (null == pwltd) {
            Loggable lia = WTCLogger.logErrorBadTDMPasswdLTDLoggable(lapnm);
            lia.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/setupTDMPasswd/20/no LDOM defined");
            }

            throw new TPException(4, lia.getMessage());
         } else {
            if (traceEnabled) {
               ntrace.doTrace("valid LocalAccessPoint");
            }

            String rapnm = mb.getRemoteAccessPoint();
            if (rapnm == null) {
               Loggable lua = WTCLogger.logUndefinedMBeanAttrLoggable("RemoteAccessPoint", mb.getName());
               lua.log();
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/setupTDMPasswd/30/no RAP");
               }

               throw new TPException(4, lua.getMessage());
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("RemoteAccessPoint:" + rapnm);
               }

               TDMRemoteTDomain pwrtd = this.getRTDbyAP(rapnm);
               if (null == pwrtd) {
                  Loggable lia = WTCLogger.logErrorBadTDMPasswdRTDLoggable(rapnm);
                  lia.log();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/setupTDMPasswd/40/no RDOM");
                  }

                  throw new TPException(4, lia.getMessage());
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("valid RemoteAccessPointList member");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("create tdmpwd from lapnm:" + lapnm + "and rapnm:" + rapnm);
                  }

                  TDMPasswd tdmpw;
                  try {
                     tdmpw = new TDMPasswd(lapnm, rapnm);
                  } catch (Exception var11) {
                     Loggable le = WTCLogger.logUEconstructTDMPasswdLoggable(var11.getMessage());
                     le.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/setupTDMPasswd/50/failed createion");
                     }

                     throw new TPException(4, le.getMessage());
                  }

                  String passwdiv = mb.getLocalPasswordIV();
                  String passwd = mb.getLocalPassword();
                  Loggable le;
                  if (passwd != null && passwdiv == null || passwd == null && passwdiv != null) {
                     le = WTCLogger.logErrorPasswordInfoLoggable("Local");
                     le.log();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/setupTDMPasswd/60/bad lpassword");
                     }

                     throw new TPException(4, le.getMessage());
                  } else {
                     tdmpw.setLocalPasswordIV(passwdiv);
                     tdmpw.setLocalPassword(passwd);
                     if (traceEnabled) {
                        ntrace.doTrace("checked Local Passwd,PasswdIV.");
                     }

                     passwdiv = mb.getRemotePasswordIV();
                     passwd = mb.getRemotePassword();
                     if ((passwd == null || passwdiv != null) && (passwd != null || passwdiv == null)) {
                        tdmpw.setRemotePasswordIV(passwdiv);
                        tdmpw.setRemotePassword(passwd);
                        if (traceEnabled) {
                           ntrace.doTrace("checked Remote Passwd,PasswdIV.");
                        }

                        tdmpw.setMBean(mb);
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/setupTDMPasswd/80/success");
                        }

                        return tdmpw;
                     } else {
                        le = WTCLogger.logErrorPasswordInfoLoggable("Remote");
                        le.log();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/WTCService/setupTDMPasswd/70/bad rpassword");
                        }

                        throw new TPException(4, le.getMessage());
                     }
                  }
               }
            }
         }
      }
   }

   private boolean validatePasswd(TDMPasswd pwd) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/validatePasswd/(" + pwd.getLocalAccessPoint() + ", " + pwd.getRemoteAccessPoint() + ")");
      }

      String lap = pwd.getLocalAccessPoint();
      String rap = pwd.getRemoteAccessPoint();

      for(int idx = 0; idx < this.pwdcnt; ++idx) {
         TDMPasswd tmp = this.pwd_list[idx];
         if (lap.equals(tmp.getLocalAccessPoint()) && rap.equals(tmp.getRemoteAccessPoint())) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/validatePasswd/10/duplicate");
            }

            return false;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/validatePasswd/20/success");
      }

      return true;
   }

   private TDMPasswd getPasswdByMBean(WTCPasswordMBean mb) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String lap = mb.getLocalAccessPoint();
      String rap = mb.getRemoteAccessPoint();
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getPasswdByMBean/(" + lap + ", " + rap + ")");
      }

      for(int idx = 0; idx < this.pwdcnt; ++idx) {
         if (this.pwd_list[idx].getLocalAccessPoint().equals(lap) && this.pwd_list[idx].getRemoteAccessPoint().equals(rap)) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getPasswdByMBean/10/found it");
            }

            return this.pwd_list[idx];
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getPasswdByMBean/20/null");
      }

      return null;
   }

   private boolean addPasswd(TDMPasswd pwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/addPasswd/(" + pwd.getLocalAccessPoint() + ", " + pwd.getRemoteAccessPoint() + ")");
      }

      TDMPasswd[] oldList = this.pwd_list;
      this.pwd_list = new TDMPasswd[this.pwdcnt + 1];
      if (this.pwdcnt > 0) {
         System.arraycopy(oldList, 0, this.pwd_list, 0, this.pwdcnt);
      }

      this.pwd_list[this.pwdcnt] = pwd;
      ++this.pwdcnt;
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/addPasswd/10/success");
      }

      return true;
   }

   private boolean checkPasswdInUse(TDMPasswd pwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/checkPasswdInUse/(" + pwd.getLocalAccessPoint() + ", " + pwd.getRemoteAccessPoint() + ")");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/checkPasswdInUse/10/not in use");
      }

      return false;
   }

   private void removePasswd(TDMPasswd pwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removePasswd/(" + pwd.getLocalAccessPoint() + ", " + pwd.getRemoteAccessPoint() + ")");
      }

      int idx;
      for(idx = 0; idx < this.pwdcnt && this.pwd_list[idx] != pwd; ++idx) {
      }

      if (idx >= this.pwdcnt) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removePasswd/10/not found");
         }

      } else {
         TDMPasswd[] newList = new TDMPasswd[this.pwdcnt - 1];
         this.removeFromArray(this.pwd_list, this.pwdcnt, newList, idx);
         --this.pwdcnt;
         this.pwd_list = newList;
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/removePasswd/20/success");
         }

      }
   }

   public void startAddWTCLocalTuxDom(WTCLocalTuxDomMBean mb) throws BeanUpdateRejectedException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startAddWTCLocalTuxDom");
      }

      TDMLocalTDomain lap;
      try {
         lap = TDMLocalTDomain.create(mb);
         lap.checkConfigIntegrity();
      } catch (TPException var6) {
         Loggable l = WTCLogger.logErrorExecMBeanDefLoggable(mb.getName());
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/handleLocalTDomChange/20/setup error " + mb.getName());
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      }

      if (!this.validateLocalTuxDom(lap)) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCLocalTuxDom/30/invalid");
         }

         throw new BeanUpdateRejectedException("Error: Adding duplicatedLocalTuxDom: " + mb.getAccessPoint());
      } else {
         this.addLocalTuxDom(lap);
         this.startOatmialListener(lap);
         lap.prepareObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startAddWTCLocalTuxDom/40/success");
         }

      }
   }

   public void finishAddWTCLocalTuxDom(WTCLocalTuxDomMBean mb, boolean isActivate) {
      TDMLocalTDomain ldom = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishAddWTCLocalTuxDom");
      }

      if ((ldom = this.getLocalTuxDomByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCLocalTuxDom/10, not found");
         }

      } else if (!ldom.isObjectPrepared()) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCLocalTuxDom/20, wrong state");
         }

      } else if (isActivate) {
         ldom.activateObject();
         ldom.setMBean(mb);
         ldom.registerListener();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCLocalTuxDom/30/done with activation");
         }

      } else {
         ldom.deactivateObject();
         this.removeLocalTuxDom(ldom);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCLocalTuxDom/30");
         }

      }
   }

   public void startRemoveWTCLocalTuxDom(WTCLocalTuxDomMBean mb) throws BeanUpdateRejectedException {
      boolean beingUsed = false;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startRemoveWTCLocalTuxDom");
      }

      TDMLocalTDomain ldom;
      Loggable l;
      if ((ldom = this.getLocalTuxDomByMBean(mb)) == null) {
         l = WTCLogger.logErrorNoSuchLocalDomainLoggable(mb.getAccessPointId());
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCLocalTuxDom/10, not found");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      } else if (this.checkLocalTuxDomInUse(ldom)) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCLocalTuxDom/20/beingUsed");
         }

         l = WTCLogger.logErrorLocalTDomInUseLoggable(mb.getAccessPointId());
         l.log();
         throw new BeanUpdateRejectedException(l.getMessage());
      } else {
         ldom.suspendObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCLocalTuxDom/30/suspended");
         }

      }
   }

   public void finishRemoveWTCLocalTuxDom(WTCLocalTuxDomMBean mb, boolean isActivate) {
      TDMLocalTDomain ldom = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishRemoveWTCLocalTuxDom/" + mb.getAccessPoint());
      }

      if ((ldom = this.getLocalTuxDomByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCLocalTuxDom/10/not found");
         }

      } else if (!isActivate) {
         if (ldom.isObjectSuspended()) {
            ldom.activateObject();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCLocalTuxDom/20/rollbacked");
         }

      } else {
         ldom.deactivateObject();
         ldom.unregisterListener();
         this.removeLocalTuxDom(ldom);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCLocalTuxDom/30/succeeded");
         }

      }
   }

   public void startAddWTCRemoteTuxDom(WTCRemoteTuxDomMBean mb) throws BeanUpdateRejectedException {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startAddWTCRemoteTuxDom/" + mb.getAccessPoint());
      }

      TDMRemoteTDomain rap;
      Loggable l;
      try {
         rap = this.setupTDMRemoteTD(mb);
         rap.checkConfigIntegrity();
      } catch (TPException var8) {
         l = WTCLogger.logErrorExecMBeanDefLoggable(mb.getAccessPointId());
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCRemoteTuxDom/10/" + mb.getAccessPoint());
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      }

      if (!this.validateRemoteTuxDom(rap)) {
         Loggable l = WTCLogger.logErrorDuplicateRemoteTDomLoggable(mb.getAccessPointId());
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCRemoteTuxDom/20/" + mb.getAccessPoint() + " duplicate");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      } else {
         try {
            this.federateRemoteDomain(this.myDomainContext, rap);
         } catch (NamingException var7) {
            l = WTCLogger.logNEcreateSubCntxtLoggable(var7);
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/startAddWTCRemoteTuxDom/30/" + mb.getAccessPoint() + " Naming Exception");
            }

            throw new BeanUpdateRejectedException(l.getMessage());
         }

         rap.onTerm(1);
         this.addRemoteTuxDom(rap);
         rap.prepareObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startAddWTCRemoteTuxDom/40/" + mb.getAccessPoint() + " Prepared");
         }

      }
   }

   public void finishAddWTCRemoteTuxDom(WTCRemoteTuxDomMBean mb, boolean isActivate) {
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishAddWTCRemoteTuxDom/" + mb.getAccessPoint());
      }

      TDMRemoteTDomain rap;
      if ((rap = this.getRemoteTuxDomByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCRemoteTuxDom/10/not found");
         }

      } else if (!rap.isObjectPrepared()) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCRemoteTuxDom/20/not in PREPARED state");
         }

      } else if (isActivate) {
         rap.activateObject();
         rap.setMBean(mb);
         rap.registerListener();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCRemoteTuxDom/30/" + rap.getAccessPointId() + " is activated");
         }

      } else {
         rap.deactivateObject();
         this.removeRemoteTuxDom(rap);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCRemoteTuxDom/40/" + mb.getAccessPoint() + " rolled back");
         }

      }
   }

   public void startRemoveWTCRemoteTuxDom(WTCRemoteTuxDomMBean mb) throws BeanUpdateRejectedException {
      TDMRemoteTDomain rap = null;
      String ap = mb.getAccessPoint();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startRemoveWTCRemoteTuxDom/" + mb.getAccessPoint());
      }

      if ((rap = this.getRemoteTuxDomByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCRemoteTuxDom/10/" + ap + " not found");
         }

         throw new BeanUpdateRejectedException("ERROR: RemoteTuxDom " + ap + " does not exist!");
      } else if (!this.checkRemoteTuxDomInUse(rap)) {
         Loggable l = WTCLogger.logErrorRemoteTDomInUseLoggable(mb.getAccessPointId());
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCRemoteTuxDom/20/" + ap + " in use");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      } else {
         rap.suspendObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCRemoteTuxDom/30/" + ap + " suspended");
         }

      }
   }

   public void finishRemoveWTCRemoteTuxDom(WTCRemoteTuxDomMBean mb, boolean isActivate) {
      String ap = mb.getAccessPoint();
      TDMRemoteTDomain rap = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishRemoveWTCRemoteTuxDom/" + ap);
      }

      if ((rap = this.getRemoteTuxDomByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCRemoteTuxDom/10/" + ap + " not found");
         }

      } else if (!isActivate) {
         if (rap.isObjectSuspended()) {
            if (traceEnabled) {
               ntrace.doTrace("activate remote TuxDom AP " + ap);
            }

            rap.activateObject();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCRemoteTuxDom/20/" + ap + " not removed");
         }

      } else {
         rap.deactivateObject();
         rap.unregisterListener();
         this.removeRemoteTuxDom(rap);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCRemoteTuxDom/30/" + ap + " removed");
         }

      }
   }

   public void startAddWTCImport(WTCImportMBean mb) throws BeanUpdateRejectedException {
      TDMImport imp_svc = null;
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startAddWTCImport/" + svc_name);
      }

      try {
         imp_svc = this.setupTDMImport(mb);
         this.validateImport(imp_svc);
      } catch (TPException var7) {
         Loggable l = WTCLogger.logErrorExecMBeanDefLoggable(svc_name);
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCImport/10/" + svc_name + " setup failed");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      }

      this.addImport(imp_svc);
      imp_svc.prepareObject();
      this.myWtcStatMBean.addComp(imp_svc);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/startAddWTCImport/20/" + mb.getResourceName() + " prepared");
      }

   }

   public void finishAddWTCImport(WTCImportMBean mb, boolean isActivate) {
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishAddWTCImport/" + svc_name);
      }

      TDMImport rsvc;
      if ((rsvc = this.getImportByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCImport/10/" + svc_name + " not found");
         }

      } else if (!isActivate) {
         rsvc.deactivateObject();
         this.removeImport(rsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCImport/20/" + svc_name + " setup failed");
         }

      } else if (!rsvc.isObjectPrepared()) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCImport/30/" + svc_name + " not in prepared state");
         }

      } else {
         rsvc.activateObject();
         rsvc.setMBean(mb);
         rsvc.registerListener();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCImport/40/" + mb.getResourceName() + " activated");
         }

      }
   }

   public void startRemoveWTCImport(WTCImportMBean mb) throws BeanUpdateRejectedException {
      TDMImport rsvc = null;
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startRemoveWTCImport/" + svc_name);
      }

      Loggable lua;
      if ((rsvc = this.getImportByMBean(mb)) == null) {
         lua = WTCLogger.logErrorNoSuchImportLoggable(svc_name);
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCImport/10/not found");
         }

         throw new BeanUpdateRejectedException(lua.getMessage());
      } else if (this.checkImportInUse(rsvc)) {
         lua = WTCLogger.logErrorResourceInUseLoggable(svc_name);
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCImport/20/in use");
         }

         throw new BeanUpdateRejectedException(lua.getMessage());
      } else {
         rsvc.suspendObject();
         this.addTmpImport(rsvc);
         this.removeImport(rsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCImport/30/" + mb.getResourceName() + " suspended");
         }

      }
   }

   public void finishRemoveWTCImport(WTCImportMBean mb, boolean isActivate) {
      TDMImport rsvc = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishRemoveWTCImport/" + mb.getResourceName());
      }

      if ((rsvc = this.getTmpImportByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCImport/10/" + mb.getResourceName() + " rollbacked");
         }

      } else if (!isActivate) {
         if (rsvc.isObjectSuspended()) {
            rsvc.activateObject();
         }

         this.addImport(rsvc);
         this.removeTmpImport(rsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCImport/20/" + mb.getResourceName() + " rollbacked");
         }

      } else {
         rsvc.deactivateObject();
         rsvc.unregisterListener();
         this.removeTmpImport(rsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCImport/10/" + mb.getResourceName() + " deactivated");
         }

      }
   }

   public void startAddWTCExport(WTCExportMBean mb) throws BeanUpdateRejectedException {
      TDMExport lsvc = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startAddWTCExport/" + mb.getResourceName());
      }

      try {
         lsvc = this.setupTDMExport(mb);
         this.validateExport(lsvc);
      } catch (TPException var5) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startAddWTCExport/10/" + mb.getResourceName() + " failed");
         }

         throw new BeanUpdateRejectedException(var5.getMessage());
      }

      this.addExport(lsvc);
      lsvc.prepareObject();
      this.myWtcStatMBean.addComp(lsvc);
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/startAddWTCExport/10/" + mb.getResourceName() + " prepared");
      }

   }

   public void finishAddWTCExport(WTCExportMBean mb, boolean isActivate) {
      TDMExport lsvc = null;
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishAddWTCExport/" + svc_name);
      }

      if ((lsvc = this.getExportByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCExport/10/" + svc_name + " not found");
         }

      } else if (isActivate) {
         lsvc.activateObject();
         lsvc.setMBean(mb);
         lsvc.registerListener();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCExport/20/" + svc_name + " activated");
         }

      } else {
         lsvc.deactivateObject();
         this.removeExport(lsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCExport/10/" + svc_name + " not activated");
         }

      }
   }

   public void startRemoveWTCExport(WTCExportMBean mb) throws BeanUpdateRejectedException {
      TDMExport lsvc = null;
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startRemoveWTCExport/" + svc_name);
      }

      Loggable lua;
      if ((lsvc = this.getExportByMBean(mb)) == null) {
         lua = WTCLogger.logErrorNoSuchExportLoggable(svc_name);
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCExport/10/" + svc_name + " not found");
         }

         throw new BeanUpdateRejectedException(lua.getMessage());
      } else if (this.checkExportInUse(lsvc)) {
         lua = WTCLogger.logErrorResourceInUseLoggable(svc_name);
         lua.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startRemoveWTCExport/20/" + svc_name + " is in use");
         }

         throw new BeanUpdateRejectedException(lua.getMessage());
      } else {
         lsvc.suspendObject();
         this.addTmpExport(lsvc);
         this.removeExport(lsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCExport/10/" + svc_name + " suspended");
         }

      }
   }

   public void finishRemoveWTCExport(WTCExportMBean mb, boolean isActivate) {
      TDMExport lsvc = null;
      String svc_name = mb.getResourceName();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishRemoveWTCExport/" + mb.getResourceName());
      }

      if ((lsvc = this.getTmpExportByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCExport/10/" + svc_name + " not found");
         }

      } else if (!isActivate) {
         if (lsvc.isObjectSuspended()) {
            lsvc.activateObject();
         }

         this.addExport(lsvc);
         this.removeTmpExport(lsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCExport/20/" + svc_name + " rollbacked");
         }

      } else {
         lsvc.deactivateObject();
         lsvc.unregisterListener();
         this.removeTmpExport(lsvc);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCExport/30/" + svc_name + " prepared");
         }

      }
   }

   public void startAddWTCPassword(WTCPasswordMBean mb) throws BeanUpdateRejectedException {
      TDMPasswd pwd = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startAddWTCPassword/(" + mb.getLocalAccessPoint() + ", " + mb.getRemoteAccessPoint() + ")");
      }

      try {
         pwd = this.setupTDMPasswd(mb);
      } catch (TPException var6) {
         Loggable l = WTCLogger.logErrorExecMBeanDefLoggable(mb.getName());
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCPassword/10/setup failed");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      }

      if (!this.validatePasswd(pwd)) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startAddWTCPassword/20/duplicate");
         }

         throw new BeanUpdateRejectedException("ERROR: duplicated password entry");
      } else {
         this.addPasswd(pwd);
         pwd.prepareObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startAddWTCPassword/20/prepared");
         }

      }
   }

   public void finishAddWTCPassword(WTCPasswordMBean mb, boolean isActivate) {
      TDMPasswd pwd = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishAddWTCPassword/(" + mb.getLocalAccessPoint() + ", " + mb.getRemoteAccessPoint() + ")");
      }

      if ((pwd = this.getPasswdByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCPassword/10/not found");
         }

      } else if (!isActivate) {
         this.removePasswd(pwd);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCPassword/20/rollbacked");
         }

      } else if (!pwd.isObjectPrepared()) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCPassword/30/not in prepared state");
         }

      } else {
         pwd.setMBean(mb);
         pwd.registerListener();
         pwd.activateObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishAddWTCPassword/10/activated");
         }

      }
   }

   public void startRemoveWTCPassword(WTCPasswordMBean mb) throws BeanUpdateRejectedException {
      TDMPasswd pwd = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startRemoveWTCPassword/(" + mb.getLocalAccessPoint() + ", " + mb.getRemoteAccessPoint() + ")");
      }

      if ((pwd = this.getPasswdByMBean(mb)) == null) {
         Loggable l = WTCLogger.logErrorNoSuchPasswordLoggable(mb.getName());
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCPassword/10/not found");
         }

         throw new BeanUpdateRejectedException(l.getMessage());
      } else if (this.checkPasswdInUse(pwd)) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCPassword/20/not found");
         }

         throw new BeanUpdateRejectedException("ERROR: Requested password object in use, can not be deleted.");
      } else {
         pwd.suspendObject();
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startRemoveWTCExport/30/suspended");
         }

      }
   }

   public void finishRemoveWTCPassword(WTCPasswordMBean mb, boolean isActivate) {
      TDMPasswd pwd = null;
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/finishRemoveWTCPassword/(" + mb.getLocalAccessPoint() + ", " + mb.getRemoteAccessPoint() + ")");
      }

      if ((pwd = this.getPasswdByMBean(mb)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCPassword/10/prepared");
         }

      } else if (!isActivate) {
         if (pwd.isObjectSuspended()) {
            pwd.activateObject();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCPassword/20/rollbacked");
         }

      } else {
         pwd.deactivateObject();
         pwd.unregisterListener();
         this.removePasswd(pwd);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/finishRemoveWTCExport/10/deactivated");
         }

      }
   }

   private void startOatmialListener(TDMLocalTDomain ldom) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startOatmialListener/ldom:" + ldom.getAccessPoint());
      }

      if (ldom.getOatmialListener() == null) {
         if (traceEnabled) {
            ntrace.doTrace("start OATMIAL Listener");
         }

         OatmialListener myListener = new OatmialListener(myTimeService, ldom, this, this.unknownTxidRply);
         myListener.setDaemon(true);
         myListener.start();
         ldom.setOatmialListener(myListener);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/startOatmialListener/10/Done");
      }

   }

   private void removeFromArray(Object oldList, int oldCnt, Object newList, int index) {
      if (index > 0) {
         System.arraycopy(oldList, 0, newList, 0, index);
      }

      if (index < oldCnt - 1) {
         System.arraycopy(oldList, index + 1, newList, index, oldCnt - index - 1);
      }

   }

   public synchronized void changeImportResourceName(TDMImport imp, String oldResourceName) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/changeImportResourceName/" + oldResourceName);
      }

      Set hs = (Set)this.myImportedServices.get(oldResourceName);
      TDMImport registeredImp = null;
      if (null != hs) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            registeredImp = (TDMImport)iter.next();
            if (registeredImp == imp) {
               break;
            }
         }
      }

      if (registeredImp != imp) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/changeImportResourceName/10/not found");
         }

      } else {
         hs.remove(registeredImp);
         if (hs.isEmpty()) {
            this.myImportedServices.remove(oldResourceName);
         }

         this.myWtcStatMBean.removeComp(registeredImp);
         this.addImport(registeredImp);
         this.myWtcStatMBean.addComp(registeredImp);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/changeImportResourceName/20/success");
         }

      }
   }

   public synchronized void changeExportResourceName(TDMExport exp, String oldRemoteName) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/changeExportResourceName/" + oldRemoteName);
      }

      Set hs = (Set)this.myExportedServices.get(oldRemoteName);
      TDMExport registeredExp = null;
      if (null != hs) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            registeredExp = (TDMExport)iter.next();
            if (registeredExp == exp) {
               break;
            }
         }
      }

      if (registeredExp != exp) {
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/changeExportResourceName/10/not found");
         }

      } else {
         hs.remove(registeredExp);
         if (hs.isEmpty()) {
            this.myExportedServices.remove(oldRemoteName);
         }

         this.myWtcStatMBean.removeComp(registeredExp);
         this.addExport(registeredExp);
         this.myWtcStatMBean.addComp(registeredExp);
         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/changeExportResourceName/20/success");
         }

      }
   }

   public synchronized TDMExport getExportedService(String service, String accessPoint) {
      Set set = (Set)this.myExportedServices.get(service);
      if (null == set) {
         return null;
      } else {
         Iterator iter = set.iterator();

         TDMExport exp;
         do {
            if (!iter.hasNext()) {
               return null;
            }

            exp = (TDMExport)iter.next();
         } while(!exp.getLocalAccessPoint().equals(accessPoint) || exp.getStatus() != 3);

         return exp;
      }
   }

   public TDMImport getImport(String svc, Xid xid) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getImport/(svc = " + svc + ", xid = " + xid + ")");
      }

      int available_array_size = 0;
      TDMRemoteTDomain[] affinityRdoms = null;
      if (xid != null && this.xaAffinity) {
         Xid mapXid = null;
         if (xid.getFormatId() != XIDFactory.getFormatId()) {
            mapXid = TxHelper.createXid(xid.getFormatId(), xid.getGlobalTransactionId(), (byte[])null);
         } else {
            mapXid = xid;
         }

         Set myRdomSet;
         if ((myRdomSet = (Set)myOutboundXidMap.get(mapXid)) != null && myRdomSet.size() > 0) {
            affinityRdoms = (TDMRemoteTDomain[])((TDMRemoteTDomain[])myRdomSet.toArray(new TDMRemoteTDomain[myRdomSet.size()]));
         }
      }

      Set services_set;
      if ((services_set = (Set)this.myImportedServices.get(svc)) == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getImport/10/" + services_set);
         }

         throw new TPException(6, "Could not find service " + svc);
      } else if (services_set.size() == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getImport/20/Zero length services array");
         }

         throw new TPException(12, "Invalid service array for " + svc);
      } else {
         TDMImport[] tdmImports = (TDMImport[])((TDMImport[])services_set.toArray(new TDMImport[services_set.size()]));
         TDMImport[] available_array = new TDMImport[tdmImports.length];
         TDMImport[] var10 = tdmImports;
         int i = tdmImports.length;

         int j;
         for(j = 0; j < i; ++j) {
            TDMImport service = var10[j];
            if (service.getStatus() == 3) {
               available_array[available_array_size++] = service;
            }
         }

         if (available_array_size == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getImport/30/TPENOENT");
            }

            throw new TPException(6, "No local or remote domain available for " + svc);
         } else {
            int next_ldom;
            if (available_array_size <= 1) {
               next_ldom = 0;
            } else {
               next_ldom = -1;
               if (affinityRdoms != null) {
                  int n = affinityRdoms.length;
                  int v = Integer.MAX_VALUE;
                  int st = 0;

                  for(i = 0; i < available_array_size && st < 2; ++i) {
                     TDMRemote[] rapl = available_array[i].getRemoteAccessPointObjectList();
                     int l = rapl.length;
                     int k = 0;

                     for(st = 0; k < l && st < 1; ++k) {
                        for(j = 0; j < n && st < 1; ++j) {
                           if (affinityRdoms[j] == rapl[k]) {
                              if (k == 0) {
                                 v = 0;
                                 next_ldom = i;
                                 st = 2;
                                 break;
                              }

                              if (k < v) {
                                 v = k;
                                 next_ldom = i;
                                 st = 1;
                              }
                           }
                        }
                     }
                  }
               }

               if (next_ldom == -1) {
                  if (this.myRandom == null) {
                     this.myRandom = new Random();
                  }

                  if ((i = this.myRandom.nextInt()) < 0) {
                     if (i == Integer.MIN_VALUE) {
                        i = Integer.MAX_VALUE;
                     } else {
                        i *= -1;
                     }
                  }

                  next_ldom = i % available_array_size;
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getImport/40/(" + next_ldom + ")");
            }

            return available_array[next_ldom];
         }
      }
   }

   public TDMImport getImport(String resource, String lap, String rap) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getImport/" + resource + ", remote " + rap);
      }

      Set hs = (Set)this.myImportedServices.get(resource);
      if (hs != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMImport tmp = (TDMImport)iter.next();
            String[] tmp_rap = tmp.getRemoteAccessPointList();
            if (resource.equals(tmp.getResourceName()) && lap.equals(tmp.getLocalAccessPoint()) && rap.equals(tmp_rap[0])) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/getImport/10/found import service name " + resource);
               }

               return tmp;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getImport/20/import not found service name " + resource);
      }

      return null;
   }

   public TDMImport getImport(String resource, String[] ap_info) {
      String lap = ap_info[0];
      String rap_list = ap_info[1];
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getImport/svc=" + resource + ", lap=" + lap + ", rap_list=" + rap_list);
      }

      Set hs = (Set)this.myImportedServices.get(resource);
      if (hs != null) {
         Iterator iter = hs.iterator();

         while(iter.hasNext()) {
            TDMImport tmp = (TDMImport)iter.next();
            String tmp_rap = tmp.getRemoteAccessPointListString();
            if (resource.equals(tmp.getResourceName()) && lap.equals(tmp.getLocalAccessPoint()) && rap_list.equals(tmp_rap)) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/getImport/10/found import service name " + resource);
               }

               return tmp;
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/getImport/20/import not found service name " + resource);
      }

      return null;
   }

   private synchronized TDMLocal getLocalDomainById(String accessPointId) {
      for(int i = 0; i < this.ltdcnt; ++i) {
         if (this.ltd_list[i] != null && accessPointId.equals(this.ltd_list[i].getAccessPointId())) {
            return this.ltd_list[i];
         }
      }

      return null;
   }

   public void startConnection(String lDomAccessPointId, String rDomAccessPointId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startConnection/ldom=" + lDomAccessPointId + " rdom=" + rDomAccessPointId);
      }

      TDMRemoteTDomain rdom = this.getRemoteTDomain(rDomAccessPointId);
      if (null == rdom) {
         Loggable l = WTCLogger.logErrorNoSuchRemoteDomainLoggable(rDomAccessPointId);
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startConnection/10/remote domain not found " + rDomAccessPointId);
         }

         throw new TPException(6, l.getMessage());
      } else {
         TDMLocal ldom = rdom.getLocalAccessPointObject();
         if (null != ldom && lDomAccessPointId.equals(ldom.getAccessPointId())) {
            rdom.startConnection();
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/startConnection/30/DONE");
            }

         } else {
            Loggable l = WTCLogger.logErrorNoSuchLocalDomainLoggable(lDomAccessPointId);
            l.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/startConnection/20/local domain not found " + lDomAccessPointId);
            }

            throw new TPException(6, l.getMessage());
         }
      }
   }

   public void startConnection(String lDomAccessPointId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/startConnection/ldom=" + lDomAccessPointId);
      }

      TDMLocal ldom = this.getLocalDomainById(lDomAccessPointId);
      if (null == ldom) {
         Loggable l = WTCLogger.logErrorNoSuchLocalDomainLoggable(lDomAccessPointId);
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/startConnection/210/local domain not found " + lDomAccessPointId);
         }

         throw new TPException(6, l.getMessage());
      } else {
         TDMRemote[] rdoms = ldom.get_remote_domains();

         for(int i = 0; i < rdoms.length; ++i) {
            if (traceEnabled) {
               ntrace.doTrace("[/WTCService/startConnection/rdom=" + rdoms[i].getAccessPoint());
            }

            ((TDMRemoteTDomain)rdoms[i]).startConnection();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/startConnection/DONE");
         }

      }
   }

   private void stopConnection(TDMRemoteTDomain rdom) throws TPException {
      rdom.terminateConnectingTask();
      gwatmi myAtmi;
      if ((myAtmi = rdom.getTsession(false)) != null) {
         myAtmi.tpterm();
      }

   }

   public void stopConnection(String lDomAccessPointId, String rDomAccessPointId) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/stopConnection/ldom=" + lDomAccessPointId);
      }

      TDMRemoteTDomain rdom = this.getRemoteTDomain(rDomAccessPointId);
      if (null == rdom) {
         Loggable l = WTCLogger.logErrorNoSuchRemoteDomainLoggable(rDomAccessPointId);
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/stopConnection/10/remote domain not found " + rDomAccessPointId);
         }

         throw new TPException(6, l.getMessage());
      } else {
         TDMLocal ldom = rdom.getLocalAccessPointObject();
         if (null != ldom && lDomAccessPointId.equals(ldom.getAccessPointId())) {
            this.stopConnection(rdom);
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/stopConnection/DONE");
            }

         } else {
            Loggable l = WTCLogger.logErrorNoSuchLocalDomainLoggable(lDomAccessPointId);
            l.log();
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/stopConnection/20/local domain not found " + lDomAccessPointId);
            }

            throw new TPException(6, l.getMessage());
         }
      }
   }

   public void stopConnection(String lDomAccessPointId) throws TPException {
      TDMLocal ldom = this.getLocalDomainById(lDomAccessPointId);
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/stopConnection/ldom=" + lDomAccessPointId);
      }

      if (null == ldom) {
         Loggable l = WTCLogger.logErrorNoSuchLocalDomainLoggable(lDomAccessPointId);
         l.log();
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/stopConnection/10/local domain not found " + lDomAccessPointId);
         }

         throw new TPException(6, l.getMessage());
      } else {
         TDMRemote[] rdoms = ldom.get_remote_domains();

         for(int i = 0; i < rdoms.length; ++i) {
            if (traceEnabled) {
               ntrace.doTrace("[/WTCService/stopConnection/rdom=" + rdoms[i].getAccessPoint());
            }

            this.stopConnection((TDMRemoteTDomain)rdoms[i]);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WTCService/stopConnection/DONE");
         }

      }
   }

   public DSessConnInfo[] listConnectionsConfigured() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/listConnectionsConfigured");
      }

      DSessConnInfo[] connList = new DSessConnInfo[this.rtdcnt];
      int connCount = 0;
      int save_rtdcnt;
      synchronized(this) {
         save_rtdcnt = this.rtdcnt;

         for(int i = 0; i < this.rtdcnt; ++i) {
            if (this.rtd_list[i] != null) {
               String rId = this.rtd_list[i].getAccessPointId();
               TDMLocal lap = this.rtd_list[i].getLocalAccessPointObject();
               String lId = null;
               if (lap != null) {
                  lId = lap.getAccessPointId();
               }

               boolean isConn = this.rtd_list[i].getTsession(false) != null;
               String sConn = "";
               sConn = sConn + isConn;
               if (!isConn && this.rtd_list[i].hasConnectingTask()) {
                  sConn = "retrying";
               }

               connList[connCount++] = new DSessConnInfo(lId, rId, sConn);
            }
         }
      }

      DSessConnInfo[] retList;
      if (connCount == save_rtdcnt) {
         retList = connList;
      } else {
         retList = new DSessConnInfo[connCount];
         System.arraycopy(connList, 0, retList, 0, connCount);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/listConnectionsConfigured/DONE");
      }

      return retList;
   }

   public void suspendService(String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/suspendService/svc = " + svc);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               rsvc.suspend();
            }

            found = true;
         }

         if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               lsvc.suspend();
            }

            found = true;
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/suspendService/10/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/suspendService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/suspendService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void suspendService(String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/suspendService/svc = " + svc + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if (isImport) {
            if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
               it = svc_set.iterator();

               while(it.hasNext()) {
                  TDMImport rsvc = (TDMImport)it.next();
                  rsvc.suspend();
               }

               found = true;
            }
         } else if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               lsvc.suspend();
            }

            found = true;
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/suspendService/10/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " found!");
            } else {
               throw new TPException(6, "No exported services/resources of the name " + svc + " found!");
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/suspendService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/suspendService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void suspendService(String ldom, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/suspendService/svc = " + svc + ", ldom = " + ldom);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               if (rsvc.match(ldom, (String)null)) {
                  rsvc.suspend();
                  found = true;
               }
            }
         }

         if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               if (lsvc.match(ldom)) {
                  lsvc.suspend();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/suspendService/10/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/suspendService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/suspendService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void suspendService(String ldom, String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/suspendService/svc = " + svc + ", ldom = " + ldom + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if (isImport) {
            if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
               it = svc_set.iterator();

               while(it.hasNext()) {
                  TDMImport rsvc = (TDMImport)it.next();
                  if (rsvc.match(ldom, (String)null)) {
                     rsvc.suspend();
                     found = true;
                  }
               }
            }
         } else if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               if (lsvc.match(ldom)) {
                  lsvc.suspend();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/suspendService/10/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " for local access point " + ldom + " found!");
            } else {
               throw new TPException(6, "No exported services/resources of the name " + svc + " for local access point " + ldom + " found!");
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/suspendService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/suspendService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void suspendService(String ldom, String rdomlist, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/suspendService/svc = " + svc + ", ldom = " + ldom + ", rdomlist = " + rdomlist);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            Iterator it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               if (rsvc.match(ldom, rdomlist)) {
                  rsvc.suspend();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/suspendService/10/TPENOENT");
            }

            throw new TPException(6, "No imported services/resources of the name " + svc + " for local access point " + ldom + "and remote access point list " + rdomlist + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/suspendService/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/suspendService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void resumeService(String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/resumeService/svc = " + svc);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               rsvc.resume();
            }

            found = true;
         }

         if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               lsvc.resume();
            }

            found = true;
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/resumeService/10/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/resumeService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/resumeService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void resumeService(String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/resumeService/svc = " + svc + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if (isImport) {
            if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
               it = svc_set.iterator();

               while(it.hasNext()) {
                  TDMImport rsvc = (TDMImport)it.next();
                  rsvc.resume();
               }

               found = true;
            }
         } else if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               lsvc.resume();
            }

            found = true;
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/resumeService/10/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " found!");
            } else {
               throw new TPException(6, "No exported services/resources of the name " + svc + " found!");
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/resumeService/20/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/resumeService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void resumeService(String ldom, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/resumeService/svc = " + svc + ", ldom = " + ldom);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               if (rsvc.match(ldom, (String)null)) {
                  rsvc.resume();
                  found = true;
               }
            }
         }

         if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               if (lsvc.match(ldom)) {
                  lsvc.resume();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/resumeService/10/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/resumeService/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/resumeService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void resumeService(String ldom, String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/resumeService/svc = " + svc + ", ldom = " + ldom + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         Iterator it;
         if (isImport) {
            if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
               it = svc_set.iterator();

               while(it.hasNext()) {
                  TDMImport rsvc = (TDMImport)it.next();
                  if (rsvc.match(ldom, (String)null)) {
                     rsvc.resume();
                     found = true;
                  }
               }
            }
         } else if ((svc_set = (Set)this.myExportedServices.get(svc)) != null && svc_set.size() != 0) {
            it = svc_set.iterator();

            while(it.hasNext()) {
               TDMExport lsvc = (TDMExport)it.next();
               if (lsvc.match(ldom)) {
                  lsvc.resume();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/resumeService/10/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " for local access point " + ldom + " found!");
            } else {
               throw new TPException(6, "No exported services/resources of the name " + svc + " for local access point " + ldom + " found!");
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/resumeService/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/resumeService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public void resumeService(String ldom, String rdomlist, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean found = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/resumeService/svc = " + svc + ", ldom = " + ldom + ", rdomlist = " + rdomlist);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set svc_set;
         if ((svc_set = (Set)this.myImportedServices.get(svc)) != null && svc_set.size() != 0) {
            Iterator it = svc_set.iterator();

            while(it.hasNext()) {
               TDMImport rsvc = (TDMImport)it.next();
               if (rsvc.match(ldom, rdomlist)) {
                  rsvc.resume();
                  found = true;
               }
            }
         }

         if (!found) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/resumeService/10/TPENOENT");
            }

            throw new TPException(6, "No imported services/resources of the name " + svc + " for local access point " + ldom + "and remote access point list " + rdomlist + " found!");
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/resumeService/DONE");
            }

         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/resumeService/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public DServiceInfo[] getServiceStatus() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      int lsize = 0;
      int rsize = 0;
      int i = 0;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/");
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Iterator liter;
         Set hs;
         for(liter = this.myExportedServices.values().iterator(); liter.hasNext(); lsize += hs.size()) {
            hs = (Set)liter.next();
         }

         for(liter = this.myImportedServices.values().iterator(); liter.hasNext(); rsize += hs.size()) {
            hs = (Set)liter.next();
         }

         int total = lsize + rsize;
         if (traceEnabled) {
            ntrace.doTrace("total = " + total + ", lsize = " + lsize + ", rsize = " + rsize);
         }

         if (total == 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getServiceStatus/TPENOENT");
            }

            return null;
         } else {
            DServiceInfo[] infolist = new DServiceInfo[total];
            liter = this.myImportedServices.values().iterator();

            Iterator riter;
            while(liter.hasNext()) {
               hs = (Set)liter.next();

               TDMImport rsvc;
               for(riter = hs.iterator(); riter.hasNext(); infolist[i++] = rsvc.getServiceInfo()) {
                  rsvc = (TDMImport)riter.next();
               }
            }

            liter = this.myExportedServices.values().iterator();

            while(liter.hasNext()) {
               hs = (Set)liter.next();

               TDMExport lsvc;
               for(riter = hs.iterator(); riter.hasNext(); infolist[i++] = lsvc.getServiceInfo()) {
                  lsvc = (TDMExport)riter.next();
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/DONE");
            }

            return infolist;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/null");
         }

         return null;
      }
   }

   public int getServiceStatus(String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean suspended = false;
      boolean unavailable = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/svc = " + svc);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Iterator iter;
         Set hs;
         if ((hs = (Set)this.myImportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMImport rsvc = (TDMImport)iter.next();
               switch (rsvc.getStatus()) {
                  case 2:
                     unavailable = true;
                     break;
                  case 3:
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCService/getServiceStatus/10/AVAILABLE");
                     }

                     return 3;
                  default:
                     suspended = true;
               }
            }
         }

         if ((hs = (Set)this.myExportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMExport lsvc = (TDMExport)iter.next();
               switch (lsvc.getStatus()) {
                  case 2:
                     unavailable = true;
                     break;
                  case 3:
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCService/getServiceStatus/20/AVAILABLE");
                     }

                     return 3;
                  default:
                     suspended = true;
               }
            }
         }

         if (suspended) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/30/SUSPENDED");
            }

            return 1;
         } else if (unavailable) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/40/UNAVAILABLE");
            }

            return 2;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getServiceStatus/50/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " found");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public int getServiceStatus(String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean suspended = false;
      boolean unavailable = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/svc = " + svc + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Iterator iter;
         Set hs;
         if (isImport) {
            if ((hs = (Set)this.myImportedServices.get(svc)) != null && hs.size() != 0) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  TDMImport rsvc = (TDMImport)iter.next();
                  switch (rsvc.getStatus()) {
                     case 2:
                        unavailable = true;
                        break;
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/10/AVAILABLE");
                        }

                        return 3;
                     default:
                        suspended = true;
                  }
               }
            }
         } else if ((hs = (Set)this.myExportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMExport lsvc = (TDMExport)iter.next();
               switch (lsvc.getStatus()) {
                  case 2:
                     unavailable = true;
                     break;
                  case 3:
                     if (traceEnabled) {
                        ntrace.doTrace("]/WTCService/getServiceStatus/20/AVAILABLE");
                     }

                     return 3;
                  default:
                     suspended = true;
               }
            }
         }

         if (suspended) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/30/SUSPENDED");
            }

            return 1;
         } else if (unavailable) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/40/UNAVAILABLE");
            }

            return 2;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getServiceStatus/50/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " found");
            } else {
               throw new TPException(6, "No imported services/resources of the name " + svc + " found");
            }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public int getServiceStatus(String ldom, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean suspended = false;
      boolean unavailable = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/svc = " + svc + ", ldom = " + ldom);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Iterator iter;
         Set hs;
         if ((hs = (Set)this.myImportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMImport rsvc = (TDMImport)iter.next();
               if (rsvc.match(ldom, (String)null)) {
                  switch (rsvc.getStatus()) {
                     case 2:
                        unavailable = true;
                        break;
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/10/AVAILABLE");
                        }

                        return 3;
                     default:
                        suspended = true;
                  }
               }
            }
         }

         if ((hs = (Set)this.myExportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMExport lsvc = (TDMExport)iter.next();
               if (lsvc.match(ldom)) {
                  switch (lsvc.getStatus()) {
                     case 2:
                        unavailable = true;
                        break;
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/20/AVAILABLE");
                        }

                        return 3;
                     default:
                        suspended = true;
                  }
               }
            }
         }

         if (suspended) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/30/SUSPENDED");
            }

            return 1;
         } else if (unavailable) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/40/UNAVAILABLE");
            }

            return 2;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getServiceStatus/50/TPENOENT");
            }

            throw new TPException(6, "No imported or exported services/resources of the name " + svc + " for local access point " + ldom + " found");
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public int getServiceStatus(String ldom, String svc, boolean isImport) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      boolean suspended = false;
      boolean unavailable = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/svc = " + svc + ", ldom = " + ldom + ", isImport = " + isImport);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Iterator iter;
         Set hs;
         if (isImport) {
            if ((hs = (Set)this.myImportedServices.get(svc)) != null && hs.size() != 0) {
               iter = hs.iterator();

               while(iter.hasNext()) {
                  TDMImport rsvc = (TDMImport)iter.next();
                  if (rsvc.match(ldom, (String)null)) {
                     switch (rsvc.getStatus()) {
                        case 2:
                           unavailable = true;
                           break;
                        case 3:
                           if (traceEnabled) {
                              ntrace.doTrace("]/WTCService/getServiceStatus/10/AVAILABLE");
                           }

                           return 3;
                        default:
                           suspended = true;
                     }
                  }
               }
            }
         } else if ((hs = (Set)this.myExportedServices.get(svc)) != null && hs.size() != 0) {
            iter = hs.iterator();

            while(iter.hasNext()) {
               TDMExport lsvc = (TDMExport)iter.next();
               if (lsvc.match(ldom)) {
                  switch (lsvc.getStatus()) {
                     case 2:
                        unavailable = true;
                        break;
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/20/AVAILABLE");
                        }

                        return 3;
                     default:
                        suspended = true;
                  }
               }
            }
         }

         if (suspended) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/30/SUSPENDED");
            }

            return 1;
         } else if (unavailable) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/getServiceStatus/40/UNAVAILABLE");
            }

            return 2;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/getServiceStatus/50/TPENOENT");
            }

            if (isImport) {
               throw new TPException(6, "No imported services/resources of the name " + svc + " for local access point " + ldom + " found");
            } else {
               throw new TPException(6, "No exported services/resources of the name " + svc + " for local access point " + ldom + " found");
            }
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no imported/exported servicecan be found!");
      }
   }

   public int getServiceStatus(String ldom, String rdomlist, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/getServiceStatus/svc = " + svc + ", ldom = " + ldom + ", rdomlist = " + rdomlist);
      }

      if (this.myImportedServices != null && this.myExportedServices != null) {
         Set hs;
         if ((hs = (Set)this.myExportedServices.get(svc)) != null && hs.size() != 0) {
            Iterator iter = hs.iterator();

            while(iter.hasNext()) {
               TDMImport rsvc = (TDMImport)iter.next();
               if (rsvc.match(ldom, rdomlist)) {
                  switch (rsvc.getStatus()) {
                     case 2:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/20/SUSPENDED");
                        }

                        return 1;
                     case 3:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/10/AVAILABLE");
                        }

                        return 3;
                     default:
                        if (traceEnabled) {
                           ntrace.doTrace("]/WTCService/getServiceStatus/30/UNAVAILABLE");
                        }

                        return 2;
                  }
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/40/TPENOENT");
         }

         throw new TPException(6, "No exported services/resources of the name " + svc + " for local access point " + ldom + " and remote access point list " + rdomlist + " found");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCService/getServiceStatus/5/TPENOENT");
         }

         throw new TPException(6, "WTC not deployed, no exported servicecan be found!");
      }
   }

   public void setWTCStatisticsRuntimeMBean(WTCStatisticsRuntimeMBeanImpl mb) {
      this.myWtcStatMBean = mb;
   }

   public WTCStatisticsRuntimeMBean getWTCStatisticsRuntimeMBean() {
      return this.myWtcStatMBean;
   }

   public long getWTCServerStartTime() {
      return this.wtcStartTime;
   }

   public String getWTCServerStatus() {
      return this.wtcStatus == 1 ? new String("DEPLOYED") : new String("UNDEPLOYED");
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      boolean traceEnabled = ntrace.isMixedTraceEnabled(18);
      int old_tb_cfg = this.tBridgeConfig;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/prepareUpdate/length " + updates.length);
      }

      BeanUpdateEvent.PropertyUpdate[] sortedUpdates = new BeanUpdateEvent.PropertyUpdate[updates.length];
      boolean[] removed = new boolean[updates.length];

      int sortedUpdatesCount;
      for(sortedUpdatesCount = 0; sortedUpdatesCount < updates.length; ++sortedUpdatesCount) {
         removed[sortedUpdatesCount] = false;
      }

      sortedUpdatesCount = 0;

      int i;
      for(i = 0; i < updates.length; ++i) {
         if (updates[i].getPropertyName().indexOf("WTCLocalTuxDom") >= 0) {
            sortedUpdates[sortedUpdatesCount++] = updates[i];
            removed[i] = true;
         }
      }

      for(i = 0; i < updates.length; ++i) {
         if (!removed[i] && updates[i].getPropertyName().indexOf("WTCRemoteTuxDom") >= 0) {
            sortedUpdates[sortedUpdatesCount++] = updates[i];
            removed[i] = true;
         }
      }

      for(i = 0; i < updates.length; ++i) {
         if (!removed[i]) {
            sortedUpdates[sortedUpdatesCount++] = updates[i];
         }
      }

      BeanUpdateEvent.PropertyUpdate update;
      int updType;
      boolean isAdd;
      Object value;
      String type;
      for(i = 0; i < sortedUpdates.length; ++i) {
         update = sortedUpdates[i];
         updType = update.getUpdateType();
         isAdd = updType == 2;
         value = isAdd ? update.getAddedObject() : update.getRemovedObject();
         type = update.getPropertyName();
         if (traceEnabled) {
            ntrace.doTrace("i = " + i + ", isAdd = " + isAdd + ", type = " + type);
         }

         if (type.indexOf("WTCResources") >= 0) {
            try {
               if (myGlobalResources == null) {
                  if (updType == 1) {
                     WTCServerMBean smb = (WTCServerMBean)event.getProposedBean();
                     if (traceEnabled) {
                        ntrace.doTrace("WTCServer getProposedBean = " + smb);
                     }

                     WTCResourcesMBean mb = smb.getWTCResources();
                     if (traceEnabled) {
                        ntrace.doTrace("getWTCResources = " + mb);
                     }

                     myGlobalResources = TDMResources.create(mb);
                     myGlobalResources.prepareObject();
                  }
               } else if (updType == 3) {
                  if (traceEnabled) {
                     ntrace.doTrace("remove op, bean = " + (WTCResourcesMBean)value);
                  }

                  myGlobalResources = null;
               }
            } catch (Exception var18) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/prepareUpdate/10/1/" + var18.getMessage());
               }

               throw new BeanUpdateRejectedException(var18.getMessage());
            }
         } else if (type.indexOf("WTCtBridgeGlobal") >= 0) {
            if (updType == 1) {
               if (this.tBridgeStartup) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/30/1/tBridge already started, modification is prohibited.");
                  }

                  throw new BeanUpdateRejectedException("No tBridgeGlobal modification is allowed after it is started.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("tBridgeGlobal modified");
               }
            } else if (updType == 3) {
               if ((this.tBridgeConfig & 1) == 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/40/1/tBridge MBean does not exist, nothing to delete.");
                  }

                  throw new BeanUpdateRejectedException("No tBridgeGlobal MBean to remove.");
               }

               --this.tBridgeConfig;
               if (traceEnabled) {
                  ntrace.doTrace("tBridgeGlobal is removed, tBridgeConfig = " + this.tBridgeConfig);
               }
            }
         } else if (type.indexOf("WTCtBridgeRedirect") >= 0) {
            if (updType == 1) {
               if (this.tBridgeStartup) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/60/1/tBridge already started, modification is prohibited");
                  }

                  throw new BeanUpdateRejectedException("No tBridgeRedirect modification is allowed after tBridge started.");
               }

               if (traceEnabled) {
                  ntrace.doTrace("tBridgeRedirect modified");
               }
            } else if (updType == 3) {
               if (this.tBridgeConfig < 2) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/70/1/No tBridgeRedirect configured, nothing to delete.");
                  }

                  throw new BeanUpdateRejectedException("No tBridgeRedirectMBean to remove.");
               }

               this.tBridgeConfig -= 2;
               if (traceEnabled) {
                  ntrace.doTrace("tBridgeRedirect is removed, tBridgeConfig = " + this.tBridgeConfig);
               }
            }
         } else {
            if (updType == 1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/prepareUpdate/80/1/Change type not supported");
               }

               throw new BeanUpdateRejectedException("CHANGE operation not supported by WtcSrvrMBean");
            }

            if (!isAdd) {
               if (type.indexOf("WTCLocalTuxDom") >= 0) {
                  this.startRemoveWTCLocalTuxDom((WTCLocalTuxDomMBean)value);
               } else if (type.indexOf("WTCRemoteTuxDom") >= 0) {
                  this.startRemoveWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value);
               } else if (type.indexOf("WTCImport") >= 0) {
                  if (this.getImportByMBean((WTCImportMBean)value) != null) {
                     this.startRemoveWTCImport((WTCImportMBean)value);
                  }
               } else if (type.indexOf("WTCExport") >= 0) {
                  this.startRemoveWTCExport((WTCExportMBean)value);
               } else if (type.indexOf("WTCPassword") >= 0) {
                  this.startRemoveWTCPassword((WTCPasswordMBean)value);
               } else {
                  if (type.indexOf("Targets") < 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/prepareUpdate/80/1/unknow type = " + type);
                     }

                     throw new BeanUpdateRejectedException("Unknown attribute type " + type + " for WtcSrvrMBean");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("WTCServer being untargeted");
                  }
               }
            }
         }
      }

      for(i = 0; i < sortedUpdates.length; ++i) {
         update = sortedUpdates[i];
         updType = update.getUpdateType();
         isAdd = updType == 2;
         value = isAdd ? update.getAddedObject() : update.getRemovedObject();
         type = update.getPropertyName();
         if (traceEnabled) {
            ntrace.doTrace("i = " + i + ", isAdd = " + isAdd + ", type = " + type);
         }

         if (type.indexOf("WTCResources") >= 0) {
            try {
               if (myGlobalResources == null && isAdd) {
                  if (traceEnabled) {
                     ntrace.doTrace("add op, bean = " + (WTCResourcesMBean)value);
                  }

                  TDMResources localRes = TDMResources.create((WTCResourcesMBean)value);
                  localRes.prepareObject();
                  myGlobalResources = localRes;
               }
            } catch (Exception var16) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/prepareUpdate/10/2/" + var16.getMessage());
               }

               throw new BeanUpdateRejectedException(var16.getMessage());
            }
         } else if (type.indexOf("WTCtBridgeGlobal") >= 0) {
            if (isAdd) {
               if ((this.tBridgeConfig & 1) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/20/tBridge is singleton, and already configured");
                  }

                  throw new BeanUpdateRejectedException("No duplicated tBridgeGlobal is allowed");
               }

               try {
                  tBexec.tBupdateGlobal((WTCtBridgeGlobalMBean)value);
                  ++this.tBridgeConfig;
                  if (traceEnabled) {
                     ntrace.doTrace("tBridgeGlobal being added, tBridgeConfig = " + this.tBridgeConfig);
                  }
               } catch (TPException var19) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/25/tBridge Global configuration error!");
                  }

                  throw new BeanUpdateRejectedException(var19.getMessage());
               }
            }
         } else if (type.indexOf("WTCtBridgeRedirect") >= 0) {
            if (isAdd) {
               if (this.tBridgeStartup) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/50/tBridge already deployed, no new redirect will be accepted.");
                  }

                  throw new BeanUpdateRejectedException("tBridge already started, no new tBridgeRedirect accepted.");
               }

               try {
                  tBexec.tBupdateRedirect((WTCtBridgeRedirectMBean)value);
                  this.tBridgeConfig += 2;
                  if (traceEnabled) {
                     ntrace.doTrace("tBridgeRedirect being added, tBridgeConfig = " + this.tBridgeConfig);
                  }
               } catch (TPException var17) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/prepareUpdate/35/tBridge Redirect configuration error!");
                  }

                  throw new BeanUpdateRejectedException(var17.getMessage());
               }
            }
         } else {
            if (updType == 1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/prepareUpdate/80/2/Change type not supported");
               }

               throw new BeanUpdateRejectedException("CHANGE operation not supported by WtcSrvrMBean");
            }

            if (isAdd) {
               if (type.indexOf("WTCLocalTuxDom") >= 0) {
                  this.startAddWTCLocalTuxDom((WTCLocalTuxDomMBean)value);
               } else if (type.indexOf("WTCRemoteTuxDom") >= 0) {
                  this.startAddWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value);
               } else if (type.indexOf("WTCImport") >= 0) {
                  this.startAddWTCImport((WTCImportMBean)value);
               } else if (type.indexOf("WTCExport") >= 0) {
                  this.startAddWTCExport((WTCExportMBean)value);
               } else if (type.indexOf("WTCPassword") >= 0) {
                  this.startAddWTCPassword((WTCPasswordMBean)value);
               } else {
                  if (type.indexOf("Targets") < 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/WTCService/prepareUpdate/80/2/unknow type = " + type);
                     }

                     throw new BeanUpdateRejectedException("Unknown attribute type " + type + " for WtcSrvrMBean");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("WTCServer being targeted");
                  }
               }
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("old_tb_cfg = " + old_tb_cfg + ", tBridgeConfig = " + this.tBridgeConfig);
      }

      if (old_tb_cfg != this.tBridgeConfig) {
         if (!this.tBridgeStartup) {
            if (this.tBridgeConfig >= 3 && (this.tBridgeConfig & 1) == 1) {
               this.tBridgePending = true;
            }
         } else if (this.tBridgeConfig == 0) {
            if (traceEnabled) {
               ntrace.doTrace("tBridge removed completely, shutdown tBridge.");
            }

            tBexec.tBcancel();
            this.tBridgeStartup = false;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/prepareUpdate/100/success");
      }

   }

   public void activateUpdate(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/activateUpdate");
      }

      for(int i = 0; i < updates.length; ++i) {
         BeanUpdateEvent.PropertyUpdate update = updates[i];
         int updType = update.getUpdateType();
         boolean isAdd = update.getUpdateType() == 2;
         Object value = isAdd ? update.getAddedObject() : update.getRemovedObject();
         String type = update.getPropertyName();
         if (traceEnabled) {
            ntrace.doTrace("i = " + i + ", isAdd = " + isAdd + ", type" + type);
         }

         if (type.indexOf("WTCResources") >= 0 && myGlobalResources != null && (updType == 1 || updType == 2)) {
            WTCResourcesMBean mb = this.myWtcSrvrMBean.getWTCResources();
            myGlobalResources.setMBean(mb);
            myGlobalResources.registerListener();
            myGlobalResources.activateObject();
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/activateUpdate/5/nothing to do");
            }
         } else if (type.indexOf("WTCtBridgeGlobal") < 0 && type.indexOf("WTCtBridgeRedirect") < 0) {
            if (updType == 1) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/activateUpdate/10/Change type not supported");
               }

               return;
            }

            if (type.indexOf("WTCLocalTuxDom") >= 0) {
               if (isAdd) {
                  this.finishAddWTCLocalTuxDom((WTCLocalTuxDomMBean)value, true);
               } else {
                  this.finishRemoveWTCLocalTuxDom((WTCLocalTuxDomMBean)value, true);
               }
            } else if (type.indexOf("WTCRemoteTuxDom") >= 0) {
               if (isAdd) {
                  this.finishAddWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value, true);
               } else {
                  this.finishRemoveWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value, true);
               }
            } else if (type.indexOf("WTCImport") >= 0) {
               if (isAdd) {
                  this.finishAddWTCImport((WTCImportMBean)value, true);
               } else {
                  this.finishRemoveWTCImport((WTCImportMBean)value, true);
               }
            } else if (type.indexOf("WTCExport") >= 0) {
               if (isAdd) {
                  this.finishAddWTCExport((WTCExportMBean)value, true);
               } else {
                  this.finishRemoveWTCExport((WTCExportMBean)value, true);
               }
            } else if (type.indexOf("WTCPassword") >= 0) {
               if (isAdd) {
                  this.finishAddWTCPassword((WTCPasswordMBean)value, true);
               } else {
                  this.finishRemoveWTCPassword((WTCPasswordMBean)value, true);
               }
            } else if (type.indexOf("WTCtBridgeGlobal") >= 0) {
               if (isAdd) {
                  if (traceEnabled) {
                     ntrace.doTrace("tBridgeGlobal being actually added");
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("tBridgeGlobal being actually removed");
               }
            } else {
               if (type.indexOf("Targets") < 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/WTCService/activateUpdate/20/unknow type = " + type);
                  }

                  return;
               }

               if (isAdd) {
                  if (traceEnabled) {
                     ntrace.doTrace("WTCServer being targeted");
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("WTCServer being untargeted");
               }
            }
         } else if (this.tBridgePending) {
            if (tBexec.tBactivate()) {
               this.tBridgeStartup = true;
            }

            this.tBridgePending = false;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/activateUpdate/30/success");
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/rollbackUpdate");
      }

      for(int i = 0; i < updates.length; ++i) {
         BeanUpdateEvent.PropertyUpdate update = updates[i];
         int updType = update.getUpdateType();
         boolean isAdd = update.getUpdateType() == 2;
         Object value = isAdd ? update.getAddedObject() : update.getRemovedObject();
         String type = update.getPropertyName();
         if (updType == 1) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/rollbackUpdate/10/Change type not supported");
            }

            return;
         }

         if (traceEnabled) {
            ntrace.doTrace("i = " + i + ", isAdd = " + isAdd + ", type" + type);
         }

         if (type.indexOf("WTCLocalTuxDom") >= 0) {
            if (isAdd) {
               this.finishAddWTCLocalTuxDom((WTCLocalTuxDomMBean)value, false);
            } else {
               this.finishRemoveWTCLocalTuxDom((WTCLocalTuxDomMBean)value, false);
            }
         } else if (type.indexOf("WTCRemoteTuxDom") >= 0) {
            if (isAdd) {
               this.finishAddWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value, false);
            } else {
               this.finishRemoveWTCRemoteTuxDom((WTCRemoteTuxDomMBean)value, false);
            }
         } else if (type.indexOf("WTCImport") >= 0) {
            if (isAdd) {
               this.finishAddWTCImport((WTCImportMBean)value, false);
            } else {
               this.finishRemoveWTCImport((WTCImportMBean)value, false);
            }
         } else if (type.indexOf("WTCExport") >= 0) {
            if (isAdd) {
               this.finishAddWTCExport((WTCExportMBean)value, false);
            } else {
               this.finishRemoveWTCExport((WTCExportMBean)value, false);
            }
         } else {
            if (type.indexOf("WTCPassword") < 0) {
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/rollbackUpdate/20/unknow type = " + type);
               }

               return;
            }

            if (isAdd) {
               this.finishAddWTCPassword((WTCPasswordMBean)value, false);
            } else {
               this.finishRemoveWTCPassword((WTCPasswordMBean)value, false);
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/rollbackUpdate/30/success");
      }

   }

   private void registerBeanListeners() throws ManagementException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/registerBeanListeners/");
      }

      int i;
      for(i = 0; i < this.ltdcnt; ++i) {
         if (this.ltd_list[i] != null) {
            this.ltd_list[i].registerListener();
         }
      }

      for(i = 0; i < this.rtdcnt; ++i) {
         if (this.rtd_list[i] != null) {
            this.rtd_list[i].registerListener();
         }
      }

      for(i = 0; i < this.pwdcnt; ++i) {
         if (this.pwd_list[i] != null) {
            this.pwd_list[i].registerListener();
         }
      }

      Iterator iter = this.myImportedServices.values().iterator();

      Set hs;
      Iterator si;
      while(iter.hasNext()) {
         hs = (Set)iter.next();
         si = hs.iterator();

         while(si.hasNext()) {
            TDMImport imp = (TDMImport)si.next();
            imp.registerListener();
         }
      }

      iter = this.myExportedServices.values().iterator();

      while(iter.hasNext()) {
         hs = (Set)iter.next();
         si = hs.iterator();

         while(si.hasNext()) {
            TDMExport exp = (TDMExport)si.next();
            exp.registerListener();
         }
      }

      if (myGlobalResources != null) {
         myGlobalResources.registerListener();
      }

      if (this.myWtcSrvrMBean != null && !this.registered) {
         ((AbstractDescriptorBean)this.myWtcSrvrMBean).addBeanUpdateListener(this);
         this.registered = true;
      }

      this.register();
      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/registerBeanListeners/DONE");
      }

   }

   private synchronized void removeBeanListeners() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WTCService/removeBeanListeners/");
      }

      if (this.myWtcSrvrMBean != null && this.registered) {
         ((AbstractDescriptorBean)this.myWtcSrvrMBean).removeBeanUpdateListener(this);
      }

      try {
         this.unregister();
      } catch (ManagementException var6) {
      }

      int i;
      for(i = 0; i < this.ltdcnt; ++i) {
         if (this.ltd_list[i] != null) {
            this.ltd_list[i].unregisterListener();
         }
      }

      for(i = 0; i < this.rtdcnt; ++i) {
         if (this.rtd_list[i] != null) {
            this.rtd_list[i].unregisterListener();
         }
      }

      for(i = 0; i < this.pwdcnt; ++i) {
         if (this.pwd_list[i] != null) {
            this.pwd_list[i].unregisterListener();
         }
      }

      Set hs;
      Iterator si;
      Iterator iter;
      if (this.myImportedServices != null) {
         iter = this.myImportedServices.values().iterator();

         while(iter.hasNext()) {
            hs = (Set)iter.next();
            si = hs.iterator();

            while(si.hasNext()) {
               TDMImport imp = (TDMImport)si.next();
               imp.unregisterListener();
            }
         }
      }

      if (this.myExportedServices != null) {
         iter = this.myExportedServices.values().iterator();

         while(iter.hasNext()) {
            hs = (Set)iter.next();
            si = hs.iterator();

            while(si.hasNext()) {
               TDMExport exp = (TDMExport)si.next();
               exp.unregisterListener();
            }
         }
      }

      if (myGlobalResources != null) {
         myGlobalResources.unregisterListener();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WTCService/removeBeanListeners/DONE");
      }

   }

   public void processTSessionKAEvents(long tick) {
      for(int i = 0; i < this.rtdcnt; ++i) {
         gwdsession dsess;
         if (this.rtd_list[i] != null && (dsess = this.rtd_list[i].getDomainSession()) != null && dsess.isKeepAliveAvailable() && dsess.isKATimersExpired(tick)) {
            this.rtd_list[i].onTerm(3);
         }
      }

   }

   public class WTCServiceActivateService implements Runnable {
      WTCService wtcService = null;
      DeploymentMBean deployment = null;
      DeploymentHandlerContext ctx = null;

      public WTCServiceActivateService(WTCService wtcService, DeploymentMBean deployment, DeploymentHandlerContext ctx) {
         this.wtcService = wtcService;
         this.deployment = deployment;
         this.ctx = ctx;
      }

      public void run() {
         boolean traceEnabled = ntrace.isTraceEnabled(2);
         if (traceEnabled) {
            ntrace.doTrace("[/WTCService/activateDeployment/");
         }

         if (WTCService.countWTC != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/WTCService/activateDeployment/1/duplicated server");
            }

            throw new RuntimeException("Only one WTC server is allowed per WebLogic Server!");
         } else if (this.deployment.getTargets().length == 0) {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/activateDeployment/5/target length 0");
            }

         } else if (this.deployment instanceof WTCServerMBean) {
            WTCService var10000;
            try {
               Environment env = new Environment();
               env.setCreateIntermediateContexts(true);
               env.setReplicateBindings(WTCService.this.do_replicate);
               var10000 = this.wtcService;
               WTCService.myNameService = env.getInitialContext();
            } catch (NamingException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/activateDeployment/7/naming exception");
               }

               WTCLogger.logNEConfigInfo(var4.getMessage());
               throw new RuntimeException(var4.getMessage());
            }

            this.wtcService.myWtcSrvrMBean = (WTCServerMBean)this.deployment;
            if (!WTCService.this.checkWtcSrvrMBean()) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCService/activateDeployment/10/void: No usable WTCServer");
               }

               throw new RuntimeException("Could not use WTCServerMBean.");
            } else {
               var10000 = this.wtcService;
               WTCService.myTimeService = new Timer(true);
               var10000 = this.wtcService;
               WTCService.asyncTimeService = new Timer(true);
               WTCService.myName = this.deployment.getName();

               try {
                  WTCService.this.startWTC();
               } catch (TPException var3) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/WTCService/activateDeployment/20: startWTC failure");
                  }

                  throw new RuntimeException("startWTC failure: " + var3.getMessage());
               }

               for(int lcv = 0; lcv < WTCService.this.rtdcnt; ++lcv) {
                  if (WTCService.this.rtd_list[lcv] != null) {
                     WTCService.this.rtd_list[lcv].activateObject();
                  }
               }

               WTCService.countWTC = 1;
               if (traceEnabled) {
                  ntrace.doTrace("]/WTCService/activateDeployment/30/void: WTCServerMBean(" + WTCService.myName + ") DEPLOYED.");
               }

            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/WTCService/activateDeployment/40/void: WTCServerMBean NOT DEPLOYED.");
            }

         }
      }
   }
}
