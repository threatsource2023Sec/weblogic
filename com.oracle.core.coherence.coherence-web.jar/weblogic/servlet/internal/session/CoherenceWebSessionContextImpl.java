package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.AbstractHttpSessionCollection;
import com.tangosol.coherence.servlet.HttpSessionCollection;
import com.tangosol.coherence.servlet.HttpSessionModel;
import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.coherence.servlet.SessionHelperManager;
import com.tangosol.coherence.servlet.SessionLocalBackingMapCacheDelegator;
import com.tangosol.coherence.servlet.TraditionalHttpSessionCollection;
import com.tangosol.io.DefaultSerializer;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tangosol.util.ConcurrentMap;
import com.tangosol.util.Filter;
import com.tangosol.util.Listeners;
import com.tangosol.util.UID;
import com.tangosol.util.filter.NotFilter;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpSession;
import weblogic.version;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.coherence.service.internal.coherenceLogger;
import weblogic.common.internal.PackageInfo;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.servlet.internal.ServletContextManager;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.WebAppServletContext;

public class CoherenceWebSessionContextImpl extends ExtendableSessionContext {
   protected WebLogicSPISessionHelper sessionHelper;
   protected CoherenceWebServletContextWrapper servletContextWrapper;
   protected final XmlDocument sessionCacheConfig;
   protected String webAppName;
   protected String sessionIdSuffix;
   private int sessionIdSuffixLength;
   private static final String CW_LOCK_ATTRIBUTE_PREFIX = "coherence-session-enterlock-";
   private boolean isCompatibilityMode;
   private boolean isNativeReplicationMode;
   ServletContextManager servletContextManager;
   protected boolean isActive = false;
   protected final String internalAttributePrefix;
   protected final Filter internalAttributeFilter;
   protected final NotFilter externalAttributeFilter;
   protected UID memberId;
   private String cacheConfigPath;

   public CoherenceWebSessionContextImpl(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);
      String name = SessionHelper.parseString(sci, "coherence-application-name", sci.getServletContextName());
      if (name == null || name.length() == 0) {
         name = sci.getApplicationContext().getApplicationId();
         if (sci.getServletContextName() != null) {
            name = name + sci.getServletContextName();
         } else {
            name = name + sci.getId();
         }
      }

      this.webAppName = name;
      this.internalAttributePrefix = "InternalWLSAttribute-";
      this.internalAttributeFilter = new Filter() {
         public boolean evaluate(Object o) {
            return !(o instanceof String) || !((String)o).startsWith(CoherenceWebSessionContextImpl.this.internalAttributePrefix);
         }
      };
      this.externalAttributeFilter = new NotFilter(this.internalAttributeFilter);
      if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
         CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI defined web-app name to be " + this.webAppName);
      }

      this.isCompatibilityMode = CoherenceWebUtils.isCompatibilityModeEnabled(sci, (XmlElement)null);
      this.isNativeReplicationMode = SessionHelper.parseBoolean(sci, "weblogic-native-replication-mode", false);
      this.servletContextManager = sci.getServer().getServletContextManager();
      this.servletContextWrapper = new CoherenceWebServletContextWrapper(sci, scm, this.webAppName);
      this.servletContextWrapper.setAttribute("weblogic-session-context", this);
      this.cacheConfigPath = SessionHelper.getCacheConfigPath(sci, sci.getServletClassLoader());
      this.sessionCacheConfig = DefaultConfigurableCacheFactory.loadConfig(this.cacheConfigPath, sci.getServletClassLoader());
      if (this.isNativeReplicationMode) {
         SessionHelper.log("WebLogic native session replication mode enabled.");
         this.servletContextWrapper.setContextParam("coherence-sessioncollection-class", TraditionalHttpSessionCollection.class.getName());
         this.servletContextWrapper.setContextParam("coherence-cache-delegator-class", SessionLocalBackingMapCacheDelegator.class.getName());
         this.configureCachesWithSessionLocalObjectMap();
      }

      PackageInfo packageInfo = version.getWLSPackageInfo();
      if (CoherenceWebUtils.isVersionGreaterThan(packageInfo, 10, 3, 2)) {
         this.configureCoherenceWeb(scm);
      } else {
         this.initSessionHelper(sci);
      }

      ServerMBean server = CoherenceClusterManager.getServerMBean();
      ClusterMBean cluster = server.getCluster();
      CoherenceClusterSystemResourceMBean cohSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
      if (cohSR == null) {
         coherenceLogger.logMessageCoherenceWebWithoutClusterDefinition(sci.getApplicationName());
      }

   }

   private void configureCachesWithSessionLocalObjectMap() {
      if (this.sessionCacheConfig != null) {
         XmlElement sessionStore = null;
         XmlElement elem = this.sessionCacheConfig.findElement("caching-scheme-mapping");
         XmlElement schemeName;
         if (elem != null) {
            Iterator i = elem.getElements("cache-mapping");
            if (i != null) {
               while(i.hasNext()) {
                  sessionStore = (XmlElement)i.next();
                  schemeName = sessionStore.findElement("cache-name");
                  if (schemeName != null && "session-storage".equals(schemeName.getString())) {
                     break;
                  }
               }
            }
         }

         sessionStore.getElement("scheme-name").setString("session-local-backing-map");
         XmlElement cacheSchemes = this.sessionCacheConfig.findElement("caching-schemes");
         XmlHelper.removeElement(cacheSchemes, "near-scheme");
         schemeName = cacheSchemes.addElement("distributed-scheme");
         schemeName.addElement("scheme-name").setString("session-local-backing-map");
         schemeName.addElement("service-name").setString("SessionLocalBackingMap");
         schemeName.addElement("partition-count").setInt(257);
         schemeName.addElement("backup-count").setInt(1);
         schemeName.addElement("local-storage").setString("true");
         schemeName.addElement("backup-storage").addElement("type").setString("on-heap");
         XmlElement classScheme = schemeName.addElement("backing-map-scheme").addElement("class-scheme");
         classScheme.addElement("class-name").setString("com.tangosol.net.internal.SessionLocalBackingMap");
         XmlElement initParam = classScheme.addElement("init-params").addElement("init-param");
         initParam.addElement("param-type").setString("com.tangosol.net.BackingMapManagerContext");
         initParam.addElement("param-value").setString("{manager-context}");
         schemeName.addElement("autostart").setBoolean(true);
         System.setProperty("coherence.session.localstorage", "true");
      } else if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
         CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI unable to find session cache config file.");
      }

   }

   protected void configureCoherenceWeb(SessionConfigManager scm) {
   }

   protected String getWebAppName() {
      return this.webAppName;
   }

   public void initialize(WebAppServletContext sci) {
      this.initSessionHelper(sci);
   }

   public void initialize(ServletWorkContext ctx) {
      this.initialize((WebAppServletContext)ctx);
   }

   private void initSessionHelper(WebAppServletContext sci) {
      this.sessionHelper = (WebLogicSPISessionHelper)SessionHelperManager.ensureSessionHelper(this.servletContextWrapper, false);
      this.sessionHelper.setCacheFactory(this.sessionCacheConfig, sci.getServletClassLoader());
      this.sessionHelper.configure();
      AbstractHttpSessionCollection collection = (AbstractHttpSessionCollection)this.sessionHelper.getHttpSessionCollection();
      collection.setSerializer(new DefaultSerializer(sci.getServletClassLoader()));

      try {
         CoherenceWebRegistrationListener regListener = new CoherenceWebRegistrationListener(this.sessionHelper.getHttpSessionCollection(), this, this.sessionHelper);
         sci.getEventsManager().setRegistrationListener(regListener);
      } catch (NoClassDefFoundError var6) {
         Listeners listeners = new Listeners();
         listeners.add(new CoherenceWebSessionListener(sci, this));
         this.sessionHelper.setListeners(listeners);
      }

      try {
         WebLogicSPIHttpSessionIdGenerator sessionIdGenerator = (WebLogicSPIHttpSessionIdGenerator)collection.getSessionIdGenerator();
         this.sessionIdSuffix = sessionIdGenerator.getSuffix();
         if (this.sessionIdSuffix != null) {
            this.sessionIdSuffixLength = this.sessionIdSuffix.length();
         }
      } catch (ClassCastException var5) {
      }

      this.isActive = true;
      if (this.cacheConfigPath != null && this.cacheConfigPath.equals("default-session-cache-config.xml")) {
         CoherenceWebReapingManager.getInstance().ensureReapTimer();
      }

   }

   public String getPersistentStoreType() {
      return "coherence-web";
   }

   protected void exitSession(CoherenceWebSessionData session) {
      String sessionId = session.getCoherenceWebSessionId();

      try {
         if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
            CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI executing exitSession" + this.generateSessionIDString(sessionId, (ServletRequestImpl)null));
         }

         HttpSessionCollection collection = this.sessionHelper.getHttpSessionCollection();
         collection.exit(sessionId, true);
      } catch (RuntimeException var4) {
         if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
            CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI was unable to exit session" + this.generateSessionIDString(sessionId, (ServletRequestImpl)null), var4);
         }
      }

   }

   public void sync(HttpSession session) {
      this.syncCoherenceSession((CoherenceWebSessionData)session);
   }

   protected void afterSync(SessionData session) {
      this.syncCoherenceSession((CoherenceWebSessionData)session);
   }

   protected void syncCoherenceSession(CoherenceWebSessionData cwSession) {
      if (cwSession.isNew()) {
         HttpSessionModel model = this.sessionHelper.getHttpSessionCollection().get(cwSession.getCoherenceWebSessionId());
         if (model != null) {
            model.sessionReturned();
         }
      }

      this.exitSession(cwSession);
      cwSession.finishedRequest();
   }

   public void enter(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
      if (session != null && this.isActive) {
         CoherenceWebSessionData cwSession = (CoherenceWebSessionData)session;
         cwSession.setValid(true);
         this.enterSession(cwSession.getCoherenceWebSessionId(), req, res);
      }

   }

   public void exit(ServletRequestImpl req, ServletResponseImpl res, HttpSession session) {
      if (session != null && this.isActive) {
         CoherenceWebSessionData cwSession = (CoherenceWebSessionData)session;
         this.exitSession(cwSession);
         String enteredAttribute = "coherence-session-enterlock-" + cwSession.getCoherenceWebSessionId();
         req.removeAttribute(enteredAttribute);
         cwSession.setValid(false);
      }

   }

   public HttpSession getNewSession(String id, ServletRequestImpl req, ServletResponseImpl res) {
      if (!this.isActive) {
         return null;
      } else {
         if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
            CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI getNewSession is executed" + this.generateSessionIDString(id, req));
         }

         SessionData session = null;
         WebLogicSPISessionHelper sh = this.sessionHelper;
         if (id != null) {
            session = this.getSessionInternal(id, req, res);
            if (this.isCompatibilityMode && session == null && this.reuseSessionId(id)) {
               session = (CoherenceWebSessionData)sh.createHttpSession(this.adjustIdForCoherenceWeb(id));
            }
         }

         if (session == null) {
            session = (CoherenceWebSessionData)sh.createHttpSession();
            if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
               CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI created a new session in session cache for request: " + req.hashCode() + this.generateSessionIDString(((SessionData)session).getId(), req));
            }
         }

         this.postCreateSession(req, (SessionData)session);
         return (HttpSession)session;
      }
   }

   public SessionData getSessionInternal(String sessionId, ServletRequestImpl req, ServletResponseImpl res) {
      if (sessionId != null && this.isActive) {
         SessionHelper sh = this.sessionHelper;
         HttpSessionCollection collection = sh.getHttpSessionCollection();
         sessionId = this.adjustIdForCoherenceWeb(sessionId);
         if ((req != null || this.isCompatibilityMode) && collection.isExistent(sessionId)) {
            try {
               SessionData httpSession = this.retrieveSession(sessionId, req, res);
               HttpSessionModel model = collection.get(sessionId);
               if (model != null && model.isExpired()) {
                  CoherenceWebSessionData cohWebSession = (CoherenceWebSessionData)httpSession;
                  this.exitSession(cohWebSession);
                  String enteredAttribute = "coherence-session-enterlock-" + cohWebSession.getCoherenceWebSessionId();
                  req.removeAttribute(enteredAttribute);
                  return null;
               }

               if (model != null) {
                  model.touch();
               }

               return httpSession;
            } catch (IllegalStateException var10) {
               if (req != null) {
                  throw var10;
               }
            }
         } else if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
            CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI retrieve session was unable to find  session in cache " + this.generateSessionIDString(sessionId, req));
         }
      }

      return null;
   }

   private SessionData retrieveSession(String sessionId, ServletRequestImpl req, ServletResponseImpl res) {
      SessionHelper sh = this.sessionHelper;
      if (req != null) {
         HttpSession session = req.getSessionHelper().getSession(false);
         if (session != null && sessionId.equals(this.adjustIdForCoherenceWeb(session.getId()))) {
            return (SessionData)session;
         }

         this.enterSession(sessionId, req, res);
         if (this.sessionHelper.getHttpSessionCollection().get(sessionId) == null) {
            this.sessionHelper.getHttpSessionCollection().exit(sessionId, false);
            return null;
         }
      }

      if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
         CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI retrieving session from session cache " + this.generateSessionIDString(sessionId, req));
      }

      CoherenceWebSessionData cwSession = (CoherenceWebSessionData)sh.ensureHttpSession(sessionId);
      cwSession.setValid(true);
      return cwSession;
   }

   private void enterSession(String sessionId, ServletRequestImpl req, ServletResponseImpl res) {
      SessionHelper sh = this.sessionHelper;
      synchronized(res) {
         String enteredAttribute = "coherence-session-enterlock-" + sessionId;
         if (req.getAttribute(enteredAttribute) == null) {
            if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
               CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI entering session id: " + this.generateSessionIDString(sessionId, req));
            }

            sh.enterSession(sessionId);
            req.setAttribute(enteredAttribute, Boolean.TRUE);
         }

      }
   }

   public void destroy(boolean serverShutdown) {
      super.destroy(serverShutdown);
      if (CoherenceWebSessionData.DEBUG_SESSIONS.isDebugEnabled()) {
         CoherenceWebSessionData.DEBUG_SESSIONS.debug("Coherence*Web SPI shutting down Coherence*Web instance since the SessionContext is being destroyed in webapp: " + this.webAppName);
      }

      if (this.sessionHelper != null) {
         this.sessionHelper.contextDestroyed();
      }

      this.isActive = false;
   }

   public boolean isNativeReplicationMode() {
      return this.isNativeReplicationMode;
   }

   protected boolean reuseSessionId(String id) {
      WebAppServletContext[] allServletContexts = this.servletContextManager.getAllContexts();
      if (allServletContexts != null && allServletContexts.length >= 2) {
         String cookieName = this.getConfigMgr().getCookieName();

         for(int i = 0; i < allServletContexts.length; ++i) {
            if (allServletContexts[i] != this.getServletContext()) {
               SessionContext sessionCtx = allServletContexts[i].getSessionContext();
               if (sessionCtx != null && sessionCtx.getConfigMgr().getCookieName().equals(cookieName) && sessionCtx.getConfigMgr().isDefaultCookiePath()) {
                  if (sessionCtx instanceof CoherenceWebSessionContextImpl) {
                     if (this.sessionHelper.getHttpSessionCollection().isExistent(((CoherenceWebSessionContextImpl)sessionCtx).adjustIdForCoherenceWeb(id))) {
                        return true;
                     }
                  } else if (sessionCtx.getSessionInternal(id, (ServletRequestImpl)null, (ServletResponseImpl)null) != null) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private String generateSessionIDString(String sessionId, ServletRequestImpl req) {
      StringBuilder sb = (new StringBuilder(" with session id: ")).append(sessionId).append(" in webapp: ").append(this.webAppName).append(" using thread: ").append(Thread.currentThread().getName());
      if (req != null) {
         sb = sb.append(" for request: ").append(req.hashCode());
      }

      return sb.toString();
   }

   public int getCurrOpenSessionsCount() {
      if (!this.isActive) {
         return 0;
      } else {
         int c = this.sessionHelper.getLocalSessionIds().size();
         this.setCurrOpenSessionsCount(c);
         return c;
      }
   }

   public int getMaxOpenSessionsCount() {
      if (!this.isActive) {
         return 0;
      } else {
         int c = this.getCurrOpenSessionsCount();
         int mOld = super.getMaxOpenSessionsCount();
         int mNew = c > mOld ? c : mOld;
         this.setMaxOpenSessionsCount(mNew);
         return mNew;
      }
   }

   public int getNonPersistedSessionCount() {
      if (!this.isActive) {
         return 0;
      } else {
         ConcurrentMap cache = ((AbstractHttpSessionCollection)this.sessionHelper.getHttpSessionCollection()).getLocalCache();
         return cache == null ? 0 : cache.size();
      }
   }

   public String[] getIdsInternal() {
      if (!this.isActive) {
         return null;
      } else {
         Set ids = this.sessionHelper.getLocalSessionIds();
         String[] res = (String[])ids.toArray(new String[ids.size()]);

         for(int i = 0; i < res.length; ++i) {
            res[i] = this.adjustIdForWebLogic(res[i]);
         }

         return res;
      }
   }

   public String lookupAppVersionIdForSession(String requestedSID, ServletRequestImpl req, ServletResponseImpl res) {
      if (!this.isActive) {
         return null;
      } else {
         String versionId = null;
         HttpSessionCollection collection = this.sessionHelper.getHttpSessionCollection();
         String sessionId = this.adjustIdForCoherenceWeb(requestedSID);
         if (collection.isExistent(sessionId)) {
            SessionData session = this.retrieveSession(sessionId, (ServletRequestImpl)null, (ServletResponseImpl)null);
            if (session != null) {
               versionId = session.getVersionId();
            }
         }

         return versionId;
      }
   }

   boolean invalidateSession(SessionData session, boolean flag) {
      return false;
   }

   protected String adjustIdForCoherenceWeb(String id) {
      id = RSID.getID(id);
      return this.sessionIdSuffix != null ? id + this.sessionIdSuffix : id;
   }

   protected String adjustIdForWebLogic(String id) {
      return id != null && this.isCompatibilityMode ? id.substring(0, id.length() - this.sessionIdSuffixLength) : id;
   }
}
