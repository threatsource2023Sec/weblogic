package weblogic.diagnostics.snmp.agent.monfox;

import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.resource.spi.security.PasswordCredential;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.RemoteResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class WLSSecurityExtension implements UsmUserSecurityExtension, TimerListener {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long DEFAULT_CACHE_FLUSH_PERIOD = 300000L;
   private static WLSSecurityExtension SINGLETON;
   private CredentialManager credentialManager;
   private SnmpResource authResource = SnmpResource.getAuthenticationResource();
   private SnmpResource privResource = SnmpResource.getPrivacyResource();
   private Timer cacheFlushTimer;
   private long cacheFlushPeriod;
   private RemoteResource testResource = new RemoteResource((String)null, (String)null, (String)null, (String)null, "SNMP_AUTH");
   private ConcurrentHashMap userInfos = new ConcurrentHashMap();
   private int securityLevel = 0;
   private int authProtocol = 0;
   private int privProtocol = 3;

   public static synchronized WLSSecurityExtension getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new WLSSecurityExtension();
      }

      return SINGLETON;
   }

   private WLSSecurityExtension() {
      this.credentialManager = (CredentialManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.CREDENTIALMANAGER);
      this.setLocalizedKeyCacheInvalidationInterval(300000L);
   }

   private byte[] getPwd(String userName, boolean auth) {
      Resource res = auth ? this.authResource : this.privResource;
      Object[] creds = this.credentialManager.getCredentials(KERNEL_ID, userName, res, (ContextHandler)null, "weblogic.UserPassword");
      int len = creds != null ? creds.length : 0;

      for(int i = 0; i < len; ++i) {
         if (creds[i] instanceof PasswordCredential) {
            String pwd = new String(((PasswordCredential)creds[i]).getPassword());
            return pwd.getBytes();
         }
      }

      return null;
   }

   private boolean isValidUserInfo(WLSUserInfo ui) {
      return this.getMonfoxSecurityLevel() == ui.getSecLevel() && this.getMonfoxAuthProtocol() == ui.getAuthProtocol() && this.getMonfoxPrivProtocol() == ui.getPrivProtocol();
   }

   private WLSUserInfo getCachedUserInfo(String userName, SnmpEngineID engineID) {
      WLSUserInfo ui = null;
      synchronized(this.userInfos) {
         if (this.userInfos.containsKey(userName)) {
            HashMap keyMap = (HashMap)this.userInfos.get(userName);
            if (keyMap != null && keyMap.containsKey(engineID)) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Found cached UserInfo for user " + userName + ", engineID: " + engineID);
               }

               ui = (WLSUserInfo)keyMap.get(engineID);
               if (!this.isValidUserInfo(ui)) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Cached UserInfo for user " + userName + ", engineID " + engineID + " is invalid, removing from cache");
                  }

                  keyMap.remove(engineID);
                  if (keyMap.size() == 0) {
                     this.userInfos.remove(userName);
                  }

                  ui = null;
               }
            }
         }

         return ui;
      }
   }

   private WLSUserInfo addUserInfoToCache(String userName, SnmpEngineID engineID) {
      WLSUserInfo ui = null;

      try {
         ui = this.createUserInfo(userName, engineID);
      } catch (NoSuchAlgorithmException var8) {
         throw new RuntimeException(var8);
      }

      HashMap keyMap = null;
      synchronized(this.userInfos) {
         if (this.userInfos.containsKey(userName)) {
            keyMap = (HashMap)this.userInfos.get(userName);
         } else {
            keyMap = new HashMap();
            this.userInfos.put(userName, keyMap);
         }

         keyMap.put(engineID, ui);
         return ui;
      }
   }

   public UsmUserSecurityExtension.UserInfo getUserInfo(String userName, SnmpEngineID engineID) {
      WLSUserInfo ui = this.getCachedUserInfo(userName, engineID);
      if (ui == null) {
         ui = this.addUserInfoToCache(userName, engineID);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Returning UserInfo for " + userName + ", engineID + " + engineID.toString() + ", nonExistentUser=" + ui.isNonExistentUser());
      }

      if (ui.isNonExistentUser()) {
         ui = null;
      }

      return ui;
   }

   private WLSUserInfo createUserInfo(String userName, SnmpEngineID engineID) throws NoSuchAlgorithmException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Creating UserInfo for " + userName);
      }

      byte[] authPwd = this.getPwd(userName, true);
      byte[] privPwd = this.getPwd(userName, false);
      boolean noAuthPassword = authPwd == null;
      WLSUserInfo ui = new WLSUserInfo(engineID, userName, this.getMonfoxSecurityLevel(), this.getMonfoxAuthProtocol(), this.getMonfoxPrivProtocol(), authPwd, privPwd, noAuthPassword);
      this.clearBuffer(authPwd);
      this.clearBuffer(privPwd);
      return ui;
   }

   private void clearBuffer(byte[] buf) {
      int len = buf != null ? buf.length : 0;

      for(int i = 0; i < len; ++i) {
         buf[i] = 0;
      }

   }

   int getAuthProtocol() {
      return this.authProtocol;
   }

   void setAuthProtocol(int authProtocol) {
      this.authProtocol = authProtocol;
   }

   int getPrivProtocol() {
      return this.privProtocol;
   }

   void setPrivProtocol(int privProtocol) {
      this.privProtocol = privProtocol;
   }

   int getSecurityLevel() {
      return this.securityLevel;
   }

   void setSecurityLevel(int securityLevel) {
      this.securityLevel = securityLevel;
   }

   private int getMonfoxSecurityLevel() {
      return SecurityUtil.convertSNMPAgentToolkitSecurityLevel(this.securityLevel);
   }

   private int getMonfoxAuthProtocol() {
      switch (this.authProtocol) {
         case 0:
            return 0;
         case 1:
            return 1;
         default:
            throw new IllegalArgumentException();
      }
   }

   private int getMonfoxPrivProtocol() {
      switch (this.privProtocol) {
         case 2:
            return 2;
         case 3:
            return 4;
         default:
            throw new IllegalArgumentException();
      }
   }

   public synchronized void setLocalizedKeyCacheInvalidationInterval(long period) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLSSecurityExtension: Setting userInfo cache flush period = " + period);
      }

      if (period > 0L) {
         if (this.cacheFlushTimer != null) {
            this.cacheFlushTimer.cancel();
         }

         TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
         TimerManager timerManager = timerManagerFactory.getDefaultTimerManager();
         this.cacheFlushTimer = timerManager.scheduleAtFixedRate(this, 0L, period);
         this.cacheFlushPeriod = period;
      }
   }

   public long getLocalizedKeyCacheInvalidationInterval() {
      return this.cacheFlushPeriod;
   }

   public void timerExpired(Timer arg0) {
      this.clearUserInfos();
   }

   public void clearUserInfos() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLSSecurityExtension: Flushing user info cache");
      }

      this.userInfos.clear();
   }

   public void invalidateLocalizedKeyCache(String userName) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("WLSSecurityExtension: Flushing user info cache for " + userName);
      }

      synchronized(this.userInfos) {
         this.userInfos.remove(userName);
      }
   }
}
