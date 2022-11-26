package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.AbstractHttpSessionCollection;
import com.tangosol.coherence.servlet.HttpSessionCollection;
import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.coherence.servlet.SessionHelperFactory;
import com.tangosol.coherence.servlet.commonj.WorkManager;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.management.Registry;
import com.tangosol.run.xml.XmlDocument;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.commonj.adapter.WorkManagerAdapter;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.session.management.WebLogicHttpSessionManager;

public class WebLogicSPISessionHelper extends SessionHelper {
   public static final String CTX_INIT_SESSION_COMPATIBILITY_MODE = "coherence-session-weblogic-compatibility-mode";
   public static final String CTX_INIT_SESSION_NATIVE_REPLICATION_MODE = "weblogic-native-replication-mode";
   private static final String WORKMANAGER_NAME = "CoherenceWorkManager";
   private static final String COHERNECE_WEB_COUNT_CACHE_KEY = "CWebCount";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected NamedCache localCache;

   public WebLogicSPISessionHelper(SessionHelperFactory factory, ServletContext ctx) {
      super(factory, ctx);
   }

   protected void configure() {
      super.configure();
      this.localCache = ((AbstractHttpSessionCollection)this.getHttpSessionCollection()).getLocalCache();
      if (this.localCache == null) {
         this.localCache = this.getCache(((AbstractHttpSessionCollection)this.getHttpSessionCollection()).getLocalCacheName());
      }

      Integer count = (Integer)this.localCache.get("CWebCount");
      if (count == null) {
         this.localCache.put("CWebCount", new Integer(1));
      } else {
         this.localCache.put("CWebCount", new Integer(count + 1));
      }

   }

   protected void shutdownSessionHelperCaches(SessionHelper helper) {
      Integer count = (Integer)this.localCache.get("CWebCount");
      if (count != null) {
         if (count == 1) {
            this.localCache.remove("CWebCount");
            super.shutdownSessionHelperCaches(helper);
         } else {
            this.localCache.put("CWebCount", new Integer(count - 1));
         }
      }

   }

   protected WorkManager getWorkManager() {
      commonj.work.WorkManager wm = null;
      String workManagerLocation = "/comp/env/wm/CoherenceWorkManager";

      try {
         Context envctx = ((CoherenceWebServletContextWrapper)this.getWrappedServletContext()).getWlsServletContext().getEnvironmentContext();
         wm = (commonj.work.WorkManager)envctx.lookup(workManagerLocation);
      } catch (Exception var4) {
      }

      if (wm != null) {
         return new WorkManagerAdapter(wm);
      } else {
         CacheFactory.log("Failed to get Coherence*Web WebLogic work manager: " + workManagerLocation + " Using default work manager.", 5);
         return super.getWorkManager();
      }
   }

   protected void checkForDeadSessions() {
      CoherenceWebServletContextWrapper sc = (CoherenceWebServletContextWrapper)this.getWrappedServletContext();
      String contextPath = sc.getContextPath();
      HttpServer.SessionLogin sessionLogin = sc.getSessionLogin();
      Set sessionIds = sessionLogin.getAllIds();
      HttpSessionCollection collection = this.getHttpSessionCollection();
      Iterator iter = sessionIds.iterator();

      while(iter.hasNext()) {
         String id = (String)iter.next();
         if (!collection.isExistent(id)) {
            synchronized(sessionLogin) {
               sessionLogin.unregister(id, contextPath);
            }
         }
      }

      super.checkForDeadSessions();
   }

   public HttpSession createHttpSession() {
      return this.createHttpSession((String)null);
   }

   public HttpSession createHttpSession(String id) {
      HttpSessionCollection collection = this.getHttpSessionCollection();
      WebLogicSPIFactory factory = (WebLogicSPIFactory)this.getFactory();
      CoherenceWebSessionData session = null;
      if (id != null) {
         session = (CoherenceWebSessionData)factory.instantiateHttpSession(collection, id, true);
      } else {
         session = (CoherenceWebSessionData)factory.instantiateHttpSession(collection);
      }

      this.getHttpSessionMap().put(session.getCoherenceWebSessionId(), session);
      collection.postCreate(session);
      return session;
   }

   public HttpSession ensureHttpSession(String sId) {
      Map map = this.getHttpSessionMap();
      CoherenceWebSessionData session = (CoherenceWebSessionData)map.get(sId);
      if (session == null) {
         session = (CoherenceWebSessionData)this.getFactory().instantiateHttpSession(this.getHttpSessionCollection(), sId);
         map.put(session.getCoherenceWebSessionId(), session);
      }

      return session;
   }

   protected synchronized ConfigurableCacheFactory setCacheFactory(XmlDocument xmlDoc, ClassLoader cl) {
      if (this.m_cachefactory == null) {
         CacheFactoryBuilder cfb = CacheFactory.getCacheFactoryBuilder();
         cfb.setCacheConfiguration("session-cache-config.xml", cl, xmlDoc);
         this.m_cachefactory = cfb.getConfigurableCacheFactory("session-cache-config.xml", cl);
      }

      return this.m_cachefactory;
   }

   protected Object instantiateMBean() {
      return new WebLogicHttpSessionManager(this, this.getSessionReaperDaemon(), ((CoherenceWebServletContextWrapper)this.getWrappedServletContext()).getWlsServletContext());
   }

   protected String getMBeanObjectName() {
      Registry registry = CacheFactory.getCluster().getManagement();
      if (registry == null) {
         return null;
      } else {
         String partitionString = "";
         ComponentInvocationContext invocationContext = ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext();
         if (invocationContext != null && !invocationContext.isGlobalRuntime()) {
            partitionString = ",domainPartition=" + invocationContext.getPartitionName();
         }

         return registry.ensureGlobalName("type=WebLogicHttpSessionManager") + ",appId=" + this.getApplicationName() + partitionString;
      }
   }

   public void enterSession(String sId) {
      if (sId != null) {
         AbstractHttpSessionCollection collection = (AbstractHttpSessionCollection)this.getHttpSessionCollection();
         collection.enter(sId, true);
      }

   }

   protected void cleanUpSession(String sessionId) {
      super.cleanUpSession(sessionId);
      CoherenceWebServletContextWrapper sc = (CoherenceWebServletContextWrapper)this.getWrappedServletContext();
      ((CoherenceWebSessionContextImpl)sc.getAttribute("weblogic-session-context")).decrementOpenSessionsCount();
      String contextPath = sc.getContextPath();
      HttpServer.SessionLogin sessionLogin = sc.getSessionLogin();
      synchronized(sessionLogin) {
         sessionLogin.unregister(sessionId, contextPath);
      }
   }
}
