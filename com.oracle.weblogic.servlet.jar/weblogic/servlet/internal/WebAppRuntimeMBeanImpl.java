package weblogic.servlet.internal;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.j2ee.ComponentConcurrentRuntimeMBeanImpl;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.logging.j2ee.LoggingBeanAdapter;
import weblogic.logging.j2ee.ServletContextLogger;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.logging.LogRuntime;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.PageFlowsRuntimeMBean;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.management.runtime.ServletSessionRuntimeMBean;
import weblogic.management.runtime.SpringRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;
import weblogic.management.runtime.WebPubSubRuntimeMBean;
import weblogic.management.runtime.WebsocketApplicationRuntimeMBean;
import weblogic.management.runtime.WseeClientConfigurationRuntimeMBean;
import weblogic.management.runtime.WseeClientRuntimeMBean;
import weblogic.management.runtime.WseeV2RuntimeMBean;
import weblogic.servlet.ReferencedAttribute;
import weblogic.servlet.internal.session.ServletSessionRuntimeMBeanImpl;
import weblogic.servlet.internal.session.SessionContext;

public class WebAppRuntimeMBeanImpl extends ComponentConcurrentRuntimeMBeanImpl implements WebAppComponentRuntimeMBean, ReferencedAttribute, EJBRuntimeHolder {
   private static final long serialVersionUID = -1274209977754857686L;
   private WebAppServletContext context;
   private LogRuntime logRuntime;
   private PageFlowsRuntimeMBean pageFlowsRuntimeMBean;
   private LibraryRuntimeMBean[] libraryRuntimes;
   private Map runtimePersistenceUnits;
   private WebPubSubRuntimeMBean webPubSubRuntimeMBean;
   private String applicationIdentifier;
   private CoherenceClusterRuntimeMBean coherenceClusterRuntimeMBean;
   private Map jaxRsApps;
   private final Map m_runtimeMBeans;
   private WebsocketApplicationRuntimeMBean websocketApplicationRuntimeMBean;
   private int state;
   private SpringRuntimeMBean springRuntimeMBean;
   private final HashSet wseeClientRuntimes;
   private final HashSet wseeV2Runtimes;
   private final Set wseeClientConfigurationRuntimes;

   public WebAppRuntimeMBeanImpl(String name, String moduleId, WebAppServletContext ctx, RuntimeMBean parent, String applicationIdentifier) throws ManagementException {
      super(name, moduleId, parent, true, ctx.getMBean());
      this.runtimePersistenceUnits = Collections.EMPTY_MAP;
      this.jaxRsApps = new Hashtable();
      this.m_runtimeMBeans = new HashMap();
      this.state = 0;
      this.wseeClientRuntimes = new HashSet();
      this.wseeV2Runtimes = new HashSet();
      this.wseeClientConfigurationRuntimes = new HashSet();
      this.context = ctx;
      this.applicationIdentifier = applicationIdentifier;
      this.initLogRuntime();
      this.pageFlowsRuntimeMBean = new PageFlowsRuntimeMBeanImpl(ctx.getServer().getServerName(), ctx.getServer().getName(), ctx.getContextPath(), ctx.getName(), this);
   }

   private void initLogRuntime() throws ManagementException {
      if (this.context != null) {
         WebAppConfigManager configManager = this.context.getConfigManager();
         ServletContextLogger logger = configManager.getServletContextLogger();
         if (logger != null) {
            LoggingBeanAdapter adapter = logger.getLogAdapter();
            if (adapter != null) {
               this.logRuntime = new LogRuntime(adapter, this);
            }

         }
      }
   }

   public String getApplicationIdentifier() {
      return this.applicationIdentifier;
   }

   public String getComponentName() {
      return this.context.getName();
   }

   public String getContextRoot() {
      return this.context.getContextPath();
   }

   public String getConfiguredContextRoot() {
      return this.context.getConfiguredContextPath();
   }

   public String getModuleURI() {
      WebAppModule module = this.context.getWebAppModule();
      return module == null ? null : module.getModuleURI();
   }

   public String getStatus() {
      return this.context.isStarted() ? "DEPLOYED" : "UNDEPLOYED";
   }

   public String getSourceInfo() {
      String source = this.context.getURI();
      return source == null ? "" : source;
   }

   public ServletRuntimeMBean[] getServlets() {
      return this.context.getServletRuntimeMBeans();
   }

   public int getOpenSessionsCurrentCount() {
      SessionContext sessctx = this.context.getSessionContext();
      return sessctx == null ? 0 : sessctx.getCurrOpenSessionsCount();
   }

   public int getOpenSessionsHighCount() {
      SessionContext sessctx = this.context.getSessionContext();
      return sessctx == null ? 0 : sessctx.getMaxOpenSessionsCount();
   }

   public int getSessionsOpenedTotalCount() {
      SessionContext sessctx = this.context.getSessionContext();
      return sessctx == null ? 0 : sessctx.getTotalOpenSessionsCount();
   }

   public Set getAllServletSessions() {
      return this.context.getSessionContext().getAllServletSessions();
   }

   public ServletSessionRuntimeMBean[] getServletSessions() {
      return this.context.getSessionContext().getServletSessionRuntimeMBeans();
   }

   public String[] getServletSessionsMonitoringIds() {
      return this.context.getSessionContext().getServletSessionsMonitoringIds();
   }

   public void invalidateServletSession(String monitoringId) throws IllegalStateException {
      this.context.getSessionContext().invalidateServletSession(monitoringId);
   }

   public long getSessionLastAccessedTime(String monitoringId) throws IllegalStateException {
      return this.context.getSessionContext().getSessionLastAccessedTime(monitoringId);
   }

   public long getSessionMaxInactiveInterval(String monitoringId) throws IllegalStateException {
      return this.context.getSessionContext().getSessionMaxInactiveInterval(monitoringId);
   }

   public String getMonitoringId(String sessionId) throws IllegalStateException {
      return this.context.getSessionContext().getMonitoringId(sessionId);
   }

   public ServletSessionRuntimeMBean getServletSession(String sessionID) {
      return this.context.getSessionContext().getServletSessionRuntimeMBean(sessionID);
   }

   public int getSessionTimeoutSecs() {
      return this.context.getSessionContext().getConfigMgr().getSessionTimeoutSecs();
   }

   public int getSessionInvalidationIntervalSecs() {
      return this.context.getSessionContext().getConfigMgr().getInvalidationIntervalSecs();
   }

   public int getSessionIDLength() {
      return this.context.getSessionContext().getConfigMgr().getIDLength();
   }

   public int getSessionCookieMaxAgeSecs() {
      return this.context.getSessionContext().getConfigMgr().getCookieMaxAgeSecs();
   }

   public String getSessionCookieComment() {
      return this.context.getSessionContext().getConfigMgr().getCookieComment();
   }

   public String getSessionCookieName() {
      return this.context.getSessionContext().getConfigMgr().getCookieName();
   }

   public String getSessionCookieDomain() {
      return this.context.getSessionContext().getConfigMgr().getCookieDomain();
   }

   public String getSessionCookiePath() {
      return this.context.getSessionContext().getConfigMgr().getCookiePath();
   }

   public boolean isIndexDirectoryEnabled() {
      return this.context.getConfigManager().isIndexDirectoryEnabled();
   }

   public boolean isFilterDispatchedRequestsEnabled() {
      return this.context.getConfigManager().isFilterDispatchedRequestsEnabled();
   }

   public int getServletReloadCheckSecs() {
      return this.context.getConfigManager().getServletReloadCheckSecs();
   }

   public int getSingleThreadedServletPoolSize() {
      return this.context.getConfigManager().getSingleThreadedServletPoolSize();
   }

   public boolean isSessionMonitoringEnabled() {
      return this.context.getSessionContext().getConfigMgr().isMonitoringEnabled();
   }

   public boolean isJSPKeepGenerated() {
      return "true".equalsIgnoreCase(this.get(this.context.getJSPManager().getJspConfigArgs(), "keepgenerated"));
   }

   public boolean isJSPVerbose() {
      return "true".equalsIgnoreCase(this.get(this.context.getJSPManager().getJspConfigArgs(), "verbose"));
   }

   public boolean isJSPDebug() {
      return "true".equalsIgnoreCase(this.get(this.context.getJSPManager().getJspConfigArgs(), "debug"));
   }

   public long getJSPPageCheckSecs() {
      String s = this.get(this.context.getJSPManager().getJspConfigArgs(), "pageCheckSeconds");
      if (s != null) {
         try {
            return Long.parseLong(s);
         } catch (NumberFormatException var3) {
         }
      }

      return 1L;
   }

   public String getJSPCompileCommand() {
      return this.get(this.context.getJSPManager().getJspConfigArgs(), "compileCommand");
   }

   public String getLogFilename() {
      WebAppModule module = this.context.getWebAppModule();
      if (module == null) {
         return null;
      } else {
         WeblogicWebAppBean wlBean = module.getWlWebAppBean();
         if (wlBean == null) {
            return null;
         } else {
            LoggingBean logging = (LoggingBean)DescriptorUtils.getFirstChildOrDefaultBean(wlBean, wlBean.getLoggings(), "Logging");
            return logging == null ? null : logging.getLogFilename();
         }
      }
   }

   private String get(Map args, String name) {
      return this.trim((String)args.get(name));
   }

   private String trim(String ret) {
      if (ret == null) {
         return null;
      } else {
         ret = ret.trim();
         return ret.length() == 0 ? null : ret;
      }
   }

   public void deleteInvalidSessions() {
      SessionContext sessctx = this.context.getSessionContext();
      sessctx.deleteInvalidSessions();
   }

   public int getDeploymentState() {
      return this.state;
   }

   public void setDeploymentState(int newState) {
      this.state = newState;
   }

   public static void dumpRuntime(PrintStream p, WebAppRuntimeMBeanImpl runtime) {
      println(p, "========== DUMP ==============");
      println(p, "STATUS: " + runtime.getStatus());
      println(p, "SOURCE INFO: " + runtime.getSourceInfo());
      ServletRuntimeMBean[] servlets = runtime.getServlets();
      if (servlets == null) {
         println(p, "SERVLETS IS NULL");
      } else if (servlets.length == 0) {
         println(p, "SERVLETS IS EMPTY");
      } else {
         println(p, "SERVLETS:");

         for(int i = 0; i < servlets.length; ++i) {
            ServletRuntimeMBeanImpl.dumpServlet(p, servlets[i]);
         }
      }

      println(p, "OPEN SESSIONS: " + runtime.getOpenSessionsCurrentCount());
      println(p, "OpenSessionsHighCount: " + runtime.getOpenSessionsHighCount());
      println(p, "SessionsOpenedTotalCount: " + runtime.getSessionsOpenedTotalCount());
      ServletSessionRuntimeMBean[] sessions = runtime.getServletSessions();
      if (sessions == null) {
         println(p, "SESSIONS IS NULL");
      } else if (sessions.length == 0) {
         println(p, "SESSIONS IS EMPTY");
      } else {
         println(p, "SESSIONS:");

         for(int i = 0; i < sessions.length; ++i) {
            ServletSessionRuntimeMBeanImpl.dumpSession(p, sessions[i]);
         }
      }

   }

   private static void println(PrintStream p, String s) {
      p.println(s + "<br>");
   }

   public void registerServlet(String servletName, String servletClassName, String[] urlPatterns, Map initParams, int loadOnStartup) throws DeploymentException {
      this.context.registerServlet(servletName, servletClassName, urlPatterns, initParams, loadOnStartup);
   }

   public void registerFilter(String name, String filterClassName, String[] urlPatterns, String[] servletNames, Map initParams, String[] dispatcher) throws DeploymentException {
      this.context.registerFilter(name, filterClassName, urlPatterns, servletNames, initParams, dispatcher);
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public PageFlowsRuntimeMBean getPageFlows() {
      return this.pageFlowsRuntimeMBean;
   }

   public LibraryRuntimeMBean[] getLibraryRuntimes() {
      return this.libraryRuntimes;
   }

   public void setLibraryRuntimes(LibraryRuntimeMBean[] runtimes) {
      this.libraryRuntimes = runtimes;
   }

   public void setSpringRuntimeMBean(SpringRuntimeMBean springRuntimeMBean) {
      this.springRuntimeMBean = springRuntimeMBean;
   }

   public SpringRuntimeMBean getSpringRuntimeMBean() {
      return this.springRuntimeMBean;
   }

   public KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes() {
      Collection kodoMBeans = new ArrayList();
      Iterator var2 = this.runtimePersistenceUnits.values().iterator();

      while(var2.hasNext()) {
         Object each = var2.next();
         if (each instanceof KodoPersistenceUnitRuntimeMBean) {
            kodoMBeans.add(each);
         }
      }

      KodoPersistenceUnitRuntimeMBean[] result = new KodoPersistenceUnitRuntimeMBean[kodoMBeans.size()];
      return (KodoPersistenceUnitRuntimeMBean[])((KodoPersistenceUnitRuntimeMBean[])kodoMBeans.toArray(result));
   }

   public KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String name) {
      Object result = this.runtimePersistenceUnits.get(name);
      return result != null && result instanceof KodoPersistenceUnitRuntimeMBean ? (KodoPersistenceUnitRuntimeMBean)result : null;
   }

   void setKodoPersistenceUnitRuntimeMBeans(Map runtimePersistenceUnits) {
      this.runtimePersistenceUnits = runtimePersistenceUnits;
   }

   public PersistenceUnitRuntimeMBean[] getPersistenceUnitRuntimes() {
      PersistenceUnitRuntimeMBean[] result = new PersistenceUnitRuntimeMBean[this.runtimePersistenceUnits.size()];
      result = (PersistenceUnitRuntimeMBean[])((PersistenceUnitRuntimeMBean[])this.runtimePersistenceUnits.values().toArray(result));
      return result;
   }

   public PersistenceUnitRuntimeMBean getPersistenceUnitRuntime(String name) {
      return (PersistenceUnitRuntimeMBean)this.runtimePersistenceUnits.get(name);
   }

   public WebPubSubRuntimeMBean getWebPubSubRuntime() {
      return this.webPubSubRuntimeMBean;
   }

   public void setWebPubSubRuntime(WebPubSubRuntimeMBean webPubSubRuntimeMBean) {
      this.webPubSubRuntimeMBean = webPubSubRuntimeMBean;
   }

   public CoherenceClusterRuntimeMBean getCoherenceClusterRuntime() {
      return this.coherenceClusterRuntimeMBean;
   }

   public void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean mbean) {
      this.coherenceClusterRuntimeMBean = mbean;
   }

   public WseeClientRuntimeMBean[] getWseeClientRuntimes() {
      synchronized(this.wseeClientRuntimes) {
         int len = this.wseeClientRuntimes.size();
         return (WseeClientRuntimeMBean[])this.wseeClientRuntimes.toArray(new WseeClientRuntimeMBean[len]);
      }
   }

   public WseeClientRuntimeMBean lookupWseeClientRuntime(String rawClientId) {
      WseeClientRuntimeMBean client = null;
      synchronized(this.wseeClientRuntimes) {
         Iterator var4 = this.wseeClientRuntimes.iterator();

         while(var4.hasNext()) {
            WseeClientRuntimeMBean temp = (WseeClientRuntimeMBean)var4.next();
            if (temp.getName().equals(rawClientId)) {
               client = temp;
               break;
            }
         }

         return client;
      }
   }

   public void addWseeClientRuntime(WseeClientRuntimeMBean wseeClientRuntime) {
      synchronized(this.wseeClientRuntimes) {
         this.wseeClientRuntimes.add(wseeClientRuntime);
      }
   }

   public void removeWseeClientRuntime(WseeClientRuntimeMBean wseeClientRuntime) {
      synchronized(this.wseeClientRuntimes) {
         this.wseeClientRuntimes.remove(wseeClientRuntime);
      }
   }

   public WseeV2RuntimeMBean[] getWseeV2Runtimes() {
      synchronized(this.wseeV2Runtimes) {
         int len = this.wseeV2Runtimes.size();
         return (WseeV2RuntimeMBean[])this.wseeV2Runtimes.toArray(new WseeV2RuntimeMBean[len]);
      }
   }

   public WseeV2RuntimeMBean lookupWseeV2Runtime(String name) {
      WseeV2RuntimeMBean mbean = null;
      synchronized(this.wseeV2Runtimes) {
         Iterator var4 = this.wseeV2Runtimes.iterator();

         while(var4.hasNext()) {
            WseeV2RuntimeMBean temp = (WseeV2RuntimeMBean)var4.next();
            if (temp.getName().equals(name)) {
               mbean = temp;
               break;
            }
         }

         return mbean;
      }
   }

   public void addWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.add(mbean);
      }
   }

   public void removeWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.remove(mbean);
      }
   }

   public WseeClientConfigurationRuntimeMBean[] getWseeClientConfigurationRuntimes() {
      synchronized(this.wseeClientConfigurationRuntimes) {
         return (WseeClientConfigurationRuntimeMBean[])this.wseeClientConfigurationRuntimes.toArray(new WseeClientConfigurationRuntimeMBean[this.wseeClientConfigurationRuntimes.size()]);
      }
   }

   public WseeClientConfigurationRuntimeMBean lookupWseeClientConfigurationRuntime(String name) {
      WseeClientConfigurationRuntimeMBean mbean = null;
      synchronized(this.wseeClientConfigurationRuntimes) {
         Iterator var4 = this.wseeClientConfigurationRuntimes.iterator();

         while(var4.hasNext()) {
            WseeClientConfigurationRuntimeMBean temp = (WseeClientConfigurationRuntimeMBean)var4.next();
            if (temp.getName().equals(name)) {
               mbean = temp;
               break;
            }
         }

         return mbean;
      }
   }

   public void addWseeClientConfigurationRuntime(WseeClientConfigurationRuntimeMBean mbean) {
      synchronized(this.wseeClientConfigurationRuntimes) {
         this.wseeClientConfigurationRuntimes.add(mbean);
      }
   }

   public void removeWseeClientConfigurationRuntime(WseeClientConfigurationRuntimeMBean mbean) {
      synchronized(this.wseeClientConfigurationRuntimes) {
         this.wseeClientConfigurationRuntimes.remove(mbean);
      }
   }

   public JaxRsApplicationRuntimeMBean[] getJaxRsApplications() {
      return (JaxRsApplicationRuntimeMBean[])this.jaxRsApps.values().toArray(new JaxRsApplicationRuntimeMBean[0]);
   }

   public JaxRsApplicationRuntimeMBean lookupJaxRsApplication(String name) {
      return (JaxRsApplicationRuntimeMBean)this.jaxRsApps.get(name);
   }

   public void addJaxRsApplication(JaxRsApplicationRuntimeMBean app) {
      this.jaxRsApps.put(app.getName(), app);
   }

   public WebsocketApplicationRuntimeMBean getWebsocketApplicationRuntimeMBean() {
      return this.websocketApplicationRuntimeMBean;
   }

   public void setWebsocketApplicationRuntimeMBean(WebsocketApplicationRuntimeMBean websocketApplicationRuntimeMBean) {
      this.websocketApplicationRuntimeMBean = websocketApplicationRuntimeMBean;
   }

   public EJBRuntimeMBean[] getEJBRuntimes() {
      return (EJBRuntimeMBean[])this.m_runtimeMBeans.values().toArray(new EJBRuntimeMBean[this.m_runtimeMBeans.size()]);
   }

   public EJBRuntimeMBean getEJBRuntime(String ejbName) {
      return (EJBRuntimeMBean)this.m_runtimeMBeans.get(ejbName);
   }

   public void addEJBRuntimeMBean(String ejbName, EJBRuntimeMBean mbean) {
      this.m_runtimeMBeans.put(ejbName, mbean);
   }

   public void removeEJBRuntimeMBean(String ejbName) {
      this.m_runtimeMBeans.remove(ejbName);
   }

   public void removeAllEJBRuntimeMBeans() {
      this.m_runtimeMBeans.clear();
   }
}
