package weblogic.servlet.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;
import weblogic.i18n.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.utils.ServletMapping;
import weblogic.utils.http.HttpParsing;

public final class ServletContextManager implements PropertyChangeListener {
   private final Hashtable preservedState = new Hashtable();
   private ServletMapping contextTable;
   private String name;

   ServletContextManager(String name) {
      this.name = name;
      this.contextTable = new ServletMapping();
   }

   public WebAppServletContext getContext(String uri) {
      if (uri == null) {
         return null;
      } else {
         uri = HttpParsing.ensureStartingSlash(uri);
         return this.getContextForURI(uri);
      }
   }

   ContextVersionManager resolveVersionManagerForURI(String uri) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("ServletContextManager: resolving VersionManager for URI : " + uri);
      }

      ContextVersionManager ctxManager = this.lookupVersionManager(uri);
      if (HTTPDebugLogger.isEnabled()) {
         if (ctxManager == null) {
            HTTPDebugLogger.debug("Context not found for uri " + uri);
         } else {
            HTTPDebugLogger.debug("Found context: " + ctxManager.toString() + " for: " + uri);
         }
      }

      return ctxManager;
   }

   ContextVersionManager lookupVersionManager(String uri) {
      return (ContextVersionManager)this.contextTable.get(uri);
   }

   public WebAppServletContext getContextForContextPath(String contextPath) {
      ContextVersionManager cm = this.lookupVersionManagerForContextPath(contextPath);
      return cm == null ? null : cm.getContext((String)null);
   }

   public WebAppServletContext getContextForContextPath(String contextPath, String versionId) {
      ContextVersionManager cm = this.lookupVersionManagerForContextPath(contextPath);
      return cm == null ? null : cm.getContext(versionId);
   }

   private WebAppServletContext getContextForURI(String uri) {
      if (uri == null) {
         return null;
      } else {
         uri = HttpParsing.ensureStartingSlash(uri);
         ContextVersionManager mgr = this.lookupVersionManager(uri);
         return mgr == null ? null : mgr.getContext(false);
      }
   }

   ContextVersionManager lookupVersionManagerForContextPath(String contextPath) {
      if (contextPath == null) {
         return null;
      } else {
         int l = contextPath.length();
         if (l == 1 && contextPath.charAt(0) == '/') {
            contextPath = "";
         } else if (l > 0) {
            contextPath = HttpParsing.ensureStartingSlash(contextPath);
            if (contextPath.endsWith("/")) {
               contextPath = contextPath.substring(0, l - 1);
            }
         }

         ContextVersionManager cm = this.lookupVersionManager(contextPath);
         if (cm == null) {
            return null;
         } else {
            return !cm.getContextPath().equals(contextPath) ? null : cm;
         }
      }
   }

   public WebAppServletContext getDefaultContext() {
      ContextVersionManager cm = (ContextVersionManager)this.contextTable.getDefault();
      return cm == null ? null : cm.getActiveContext(false);
   }

   public WebAppServletContext[] getAllContexts() {
      ArrayList contextList = new ArrayList();
      Object[] values = this.contextTable.values();

      for(int i = 0; i < values.length; ++i) {
         contextList.addAll(((ContextVersionManager)values[i]).getServletContexts());
      }

      WebAppServletContext[] contexts = new WebAppServletContext[contextList.size()];
      return (WebAppServletContext[])((WebAppServletContext[])contextList.toArray(contexts));
   }

   public WebAppServletContext getServletContextFor(WebAppComponentMBean mbean) {
      WebAppServletContext[] contexts = this.getAllContexts();

      for(int i = 0; i < contexts.length; ++i) {
         WebAppServletContext sc = contexts[i];
         if (sc != null) {
            WebAppComponentMBean mb = sc.getMBean();
            if (mb != null && mb.getName().equals(mbean.getName())) {
               return sc;
            }
         }
      }

      return null;
   }

   synchronized void registerContext(WebAppServletContext ctx) throws DeploymentException {
      String contextPath = ctx.getContextPath();
      this.registerContext(ctx, contextPath);
   }

   synchronized void registerContext(WebAppServletContext ctx, String contextPath) throws DeploymentException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Registering ServletContext with context-root: '" + contextPath + "'");
      }

      this.reinstateSessionState(ctx);
      synchronized(this.contextTable) {
         ContextVersionManager existingCVM = (ContextVersionManager)this.contextTable.get(contextPath);
         ContextVersionManager cvm = null;
         if (existingCVM != null && existingCVM.getContextPath().equals(contextPath) && (existingCVM != this.contextTable.getDefault() || toPattern(contextPath).equals("/"))) {
            if (!existingCVM.getAppName().equals(ctx.getAppName())) {
               Loggable l = HTTPLogger.logContextAlreadyRegisteredLoggable(ctx.toString(), ctx.getDocroot(), this.name, existingCVM.getContext().toString(), existingCVM.getContext().getDocroot(), contextPath);
               l.log();
               throw new DeploymentException(l.getMessage());
            }

            WebAppServletContext context = existingCVM.getContext(ctx.getVersionId());
            if (context == null && ctx.getVersionId() != null) {
               WebAppServletContext anyContext = existingCVM.getContext();
               if (anyContext != null && anyContext.getVersionId() == null) {
                  Loggable l = HTTPLogger.logNonVersionedContextAlreadyRegisteredLoggable(ctx.toString(), ctx.getDocroot(), this.name, anyContext.toString(), anyContext.getDocroot(), contextPath);
                  l.log();
                  throw new DeploymentException(l.getMessage());
               }
            }

            cvm = existingCVM;
         }

         ContextVersionManager newCM = new ContextVersionManager(cvm, ctx.getApplicationName(), ctx.getContextPath());
         newCM.putContext(ctx.getVersionId(), ctx);
         ServletMapping newTable = (ServletMapping)this.contextTable.clone();
         newTable.put(toPattern(contextPath), newCM);
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("WASC registered: " + toPattern(contextPath) + " with versionId=" + ctx.getVersionId() + ", ctx=" + ctx + ", newCtxManager=" + newCM);
         }

         this.contextTable = newTable;
      }

      ctx.getContextManager();
   }

   synchronized void removeContext(String contextPath) {
      this.contextTable.remove(contextPath);
   }

   private static String toPattern(String path) {
      if (!path.equals("") && !path.equals("/")) {
         return path.endsWith("/") ? path + "*" : path + "/*";
      } else {
         return "/";
      }
   }

   void destroyContext(WebAppServletContext sci, String versionId) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Destroying ServletContext with context-root: '" + sci.getContextPath() + "'");
      }

      this.preserveSessionState(sci);
      String cp = sci.getContextPath();
      synchronized(this.contextTable) {
         ContextVersionManager cm = (ContextVersionManager)this.contextTable.get(cp);
         if (cm == null) {
            return;
         }

         ServletMapping newTable = (ServletMapping)this.contextTable.clone();
         if (cm.getServletContexts().size() == 1) {
            newTable.remove(toPattern(cp));
            cm.removeContext((String)null);
         } else {
            cm.removeContext(versionId);
         }

         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("WASC destroyed: " + toPattern(cp) + " with versionId=" + sci.getVersionId() + ", ctx=" + sci + ", oldCtxManager=" + cm);
         }

         this.contextTable = newTable;
      }

      sci.destroy();
   }

   private void preserveSessionState(WebAppServletContext sci) {
      WebAppModule module = sci.getWebAppModule();
      if (module != null && sci.getSessionContext().getConfigMgr().isSaveSessionsOnRedeployEnabled()) {
         SessionContext sc = sci.getSessionContext();
         sc.storeAttributesInBytes();
         this.preservedState.put(sci.getContextPath(), new PreservedState(sc.getSessionsMap(), sc.getCurrOpenSessionsCount(), sc.getTotalOpenSessionsCount(), sc.getMaxOpenSessionsCount(), sc.getPersistentStoreType()));
      }
   }

   private void reinstateSessionState(WebAppServletContext ctx) {
      WebAppModule module = ctx.getWebAppModule();
      if (module != null) {
         String cp = ctx.getContextPath();
         PreservedState state = (PreservedState)this.preservedState.get(cp);
         if (state != null) {
            SessionContext sc = ctx.getSessionContext();
            if (state.storeType.equals(sc.getPersistentStoreType())) {
               sc.setSessionsMap(state.sessions);
               sc.setCurrOpenSessionsCount(state.curSessCnt);
               sc.setTotalOpenSessionsCount(state.totalSessCnt);
               sc.setMaxOpenSessionsCount(state.maxSessCnt);
            }

            this.preservedState.remove(cp);
         }
      }
   }

   void removeSavedSessionState(String contextPath) {
      this.preservedState.remove(contextPath);
   }

   public String toString() {
      return "ServletContextManager(" + this.name + ")";
   }

   public synchronized void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equalsIgnoreCase("DefaultWebAppContextRoot")) {
         Object oldValue = evt.getOldValue();
         Object newValue = evt.getNewValue();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Received a PropertyChangeEvent with oldvalue=" + oldValue + " and newValue=" + newValue);
         }

         if (newValue != null) {
            if (this.contextTable.getDefault() != null) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("We already have a default webapp, cannot assign a new default webapp");
               }

            } else {
               String newCtxRoot = (String)newValue;
               if (newCtxRoot.length() >= 2) {
                  if (oldValue != null) {
                     String oldCtxRoot = (String)oldValue;
                     if (newCtxRoot.equals(oldCtxRoot)) {
                        return;
                     }
                  }

                  WebAppServletContext ctx = this.getContextForContextPath(newCtxRoot);
                  if (ctx == null) {
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPDebugLogger.debug("Didn't find a ServletContext with context-root=" + newCtxRoot);
                     }

                  } else {
                     ctx.setDefaultContext();
                     this.contextTable.remove(toPattern(newCtxRoot));
                     ContextVersionManager cm = ctx.getContextManager();
                     if (cm == null) {
                        cm = new ContextVersionManager((ContextVersionManager)null, ctx.getApplicationName(), ctx.getContextPath());
                     }

                     this.contextTable.put(toPattern(ctx.getContextPath()), cm);
                  }
               }
            }
         }
      }
   }

   private static final class PreservedState {
      Hashtable sessions = null;
      int curSessCnt = 0;
      int totalSessCnt = 0;
      int maxSessCnt = 0;
      String storeType = null;

      PreservedState(Hashtable s, int cur, int tot, int max, String type) {
         this.sessions = s;
         this.curSessCnt = cur;
         this.totalSessCnt = tot;
         this.maxSessCnt = max;
         this.storeType = type;
      }
   }
}
