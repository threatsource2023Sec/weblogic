package weblogic.servlet.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class ContextVersionManager {
   private static final String DEFAULT_VERSION_ID = "weblogic.default_version";
   private final String appName;
   private String contextPath;
   private WebAppServletContext activeServletContext;
   private WebAppServletContext activeAdminModeServletContext;
   private Map versionIdWASCMap;
   private final Map sessionIdWASCMap;
   private Map versionIdChgListenerMap;
   private boolean isOld = false;
   private static final DebugCategory DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");

   ContextVersionManager(ContextVersionManager cm, String appName, String cp) {
      this.contextPath = cp;
      if (cm == null) {
         this.versionIdWASCMap = new HashMap();
         this.sessionIdWASCMap = new Hashtable();
         this.versionIdChgListenerMap = new HashMap();
         this.appName = appName;
      } else {
         this.versionIdWASCMap = new HashMap(cm.versionIdWASCMap);
         this.versionIdChgListenerMap = new HashMap(cm.versionIdChgListenerMap);
         this.sessionIdWASCMap = cm.sessionIdWASCMap;
         this.activeServletContext = cm.activeServletContext;
         this.activeAdminModeServletContext = cm.activeAdminModeServletContext;
         this.appName = cm.appName;
         cm.isOld = true;
      }

   }

   public String toString() {
      return "CVM[appName=" + this.appName + ", contextPath=" + this.contextPath + ", activeServletContext={" + this.activeServletContext + "}, activeAdminServletContext={" + this.activeAdminModeServletContext + "}, versionIdWASCMap.size=" + this.versionIdWASCMap.size() + ", sessionIdWASCMap.size=" + this.sessionIdWASCMap.size() + ", versionIdChgListenerMap.size=" + this.versionIdChgListenerMap.size() + "]";
   }

   public boolean isVersioned() {
      if (this.activeServletContext != null) {
         return this.activeServletContext.getVersionId() != null;
      } else if (this.activeAdminModeServletContext != null) {
         return this.activeAdminModeServletContext.getVersionId() != null;
      } else if (!this.versionIdWASCMap.isEmpty()) {
         ServletWorkContext ctx = (ServletWorkContext)this.versionIdWASCMap.values().iterator().next();
         return ctx.getVersionId() != null;
      } else {
         return false;
      }
   }

   String getAppName() {
      return this.appName;
   }

   String getContextPath() {
      return this.contextPath;
   }

   void setDefaultContext() {
      this.contextPath = "";
   }

   public void putContext(String versionId, WebAppServletContext ctx) {
      if (versionId == null) {
         this.versionIdWASCMap.put("weblogic.default_version", ctx);
      } else {
         this.versionIdWASCMap.put(versionId, ctx);
      }

      this.delaySetActive(versionId, ctx);
   }

   public void removeContext(String versionId) {
      WebAppServletContext ctx = null;
      StateChangeListener scl = null;
      Map newMap = new HashMap(this.versionIdWASCMap);
      if (versionId == null) {
         ctx = (WebAppServletContext)newMap.remove("weblogic.default_version");
         scl = (StateChangeListener)this.versionIdChgListenerMap.remove("weblogic.default_version");
      } else {
         ctx = (WebAppServletContext)newMap.remove(versionId);
         scl = (StateChangeListener)this.versionIdChgListenerMap.remove(versionId);
      }

      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("CVM.removeContext: version=" + versionId + ", CVM=" + this);
      }

      this.versionIdWASCMap = newMap;
      this.unsetActive(ctx);
      ApplicationRuntimeMBean appRTMBean = ApplicationVersionUtils.getCurrentApplicationRuntime();
      if (appRTMBean != null && scl != null) {
         appRTMBean.removePropertyChangeListener(scl);
      }

   }

   public WebAppServletContext getContext(String versionId) {
      if (versionId == null) {
         return this.activeServletContext != null ? this.activeServletContext : (WebAppServletContext)this.versionIdWASCMap.get("weblogic.default_version");
      } else {
         return (WebAppServletContext)this.versionIdWASCMap.get(versionId);
      }
   }

   public WebAppServletContext getActiveContext(boolean isAdminMode) {
      return isAdminMode ? this.activeAdminModeServletContext : this.activeServletContext;
   }

   public WebAppServletContext getCurrentOrActiveContext(boolean isAdminMode) {
      return this.getContext(ApplicationVersionUtils.getCurrentVersionId(this.appName));
   }

   public WebAppServletContext getContext() {
      Iterator iter = this.getServletContexts().iterator();
      return iter.hasNext() ? (WebAppServletContext)iter.next() : null;
   }

   public WebAppServletContext getContext(boolean isAdminMode) {
      Iterator iter = this.getServletContexts(isAdminMode);
      if (iter.hasNext()) {
         return (WebAppServletContext)iter.next();
      } else {
         WebAppServletContext ctx = this.getContext();
         return ctx == null || !ctx.isInternalApp() && !ctx.getConfigManager().isRequireAdminTraffic() ? null : ctx;
      }
   }

   public Collection getServletContexts() {
      return this.versionIdWASCMap.values();
   }

   public Iterator getServletContexts(boolean isAdminMode) {
      return new AdminModeAwareIterator(isAdminMode);
   }

   public void putContextForSession(String sessionId, WebAppServletContext ctx) {
      this.sessionIdWASCMap.put(sessionId, ctx);
      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("CVM.putServletContextForSession(sessionID=" + sessionId + ", WASC=" + ctx + ")");
      }

   }

   public void removeContextForSession(String sessionId) {
      this.sessionIdWASCMap.remove(sessionId);
      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("CVM.removeServletContextForSession(sessionID=" + sessionId + ")");
      }

   }

   public WebAppServletContext getContextForSession(String sessionId) {
      return (WebAppServletContext)this.sessionIdWASCMap.get(sessionId);
   }

   public WorkManager getWorkManager(boolean isAdminMode) {
      WebAppServletContext ctx = this.getActiveContext(isAdminMode);
      return ctx != null ? ctx.getConfigManager().getWorkManager() : WorkManagerFactory.getInstance().getDefault();
   }

   private void setActive(String versionId, boolean adminMode) {
      this.setActive(this.getContext(versionId), adminMode);
   }

   private void setActive(WebAppServletContext ctx, boolean adminMode) {
      if (ctx != null) {
         if (adminMode) {
            this.activeAdminModeServletContext = ctx;
         } else {
            this.activeServletContext = ctx;
         }

         ctx.setAdminMode(adminMode);
         if (DEBUG_APP_VERSION.isEnabled()) {
            HTTPLogger.logDebug("CVM.setActive: version=" + ctx.getVersionId() + ", admin=" + adminMode + ", CVM=" + this);
         }

      }
   }

   private void delaySetActive(String versionId, WebAppServletContext ctx) {
      ApplicationRuntimeMBean appRTMBean = ApplicationVersionUtils.getCurrentApplicationRuntime();
      if (appRTMBean == null) {
         this.setActive(ctx, false);
      } else {
         StateChangeListener scl = new StateChangeListener(ctx);
         appRTMBean.addPropertyChangeListener(scl);
         if (versionId == null) {
            versionId = "weblogic.default_version";
         }

         this.versionIdChgListenerMap.put(versionId, scl);
         if (DEBUG_APP_VERSION.isEnabled()) {
            HTTPLogger.logDebug("CVM.delaySetActive: version=" + ctx.getVersionId() + ", CVM=" + this);
         }
      }

   }

   private void unsetActive(WebAppServletContext ctx) {
      if (ctx != null) {
         if (ctx == this.activeServletContext) {
            this.activeServletContext = null;
         } else if (ctx == this.activeAdminModeServletContext) {
            this.activeAdminModeServletContext = null;
            ctx.setAdminMode(false);
         }

         if (DEBUG_APP_VERSION.isEnabled()) {
            HTTPLogger.logDebug("CVM.unsetActive: app=" + this.appName + ", version=" + ctx.getVersionId() + ", CVM=" + this);
         }

      }
   }

   private void adminTransition(WebAppServletContext ctx, boolean oldAdminMode, boolean newAdminMode) {
      if (oldAdminMode && !newAdminMode) {
         if (ctx == this.activeAdminModeServletContext) {
            this.activeAdminModeServletContext = null;
            this.activeServletContext = ctx;
         }
      } else if (!oldAdminMode && newAdminMode && ctx == this.activeServletContext) {
         this.activeServletContext = null;
         this.activeAdminModeServletContext = ctx;
      }

      ctx.setAdminMode(newAdminMode);
      if (DEBUG_APP_VERSION.isEnabled()) {
         HTTPLogger.logDebug("CVM.adminTransition: app=" + this.appName + ", version=" + ctx.getVersionId() + ", admin=" + newAdminMode);
      }

   }

   boolean isOld() {
      return this.isOld;
   }

   private final class AdminModeAwareIterator implements Iterator {
      Iterator iter;
      boolean isAdminMode;
      ServletWorkContext nextContext;

      private AdminModeAwareIterator(boolean isAdminMode) {
         this.isAdminMode = isAdminMode;
         this.iter = ContextVersionManager.this.versionIdWASCMap.values().iterator();
      }

      public void remove() {
         this.iter.remove();
      }

      public boolean hasNext() {
         while(true) {
            if (this.iter.hasNext()) {
               this.nextContext = (ServletWorkContext)this.iter.next();
               if (this.nextContext.isAdminMode() != this.isAdminMode) {
                  continue;
               }

               return true;
            }

            return false;
         }
      }

      public Object next() {
         return this.nextContext;
      }

      // $FF: synthetic method
      AdminModeAwareIterator(boolean x1, Object x2) {
         this(x1);
      }
   }

   private final class StateChangeListener implements PropertyChangeListener {
      private WebAppServletContext ctx;

      private StateChangeListener(WebAppServletContext ctx) {
         this.ctx = ctx;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equalsIgnoreCase("ActiveVersionState")) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            if (oldValue instanceof Integer && newValue instanceof Integer) {
               int oldState = (Integer)oldValue;
               int newState = (Integer)newValue;
               if (ContextVersionManager.DEBUG_APP_VERSION.isEnabled()) {
                  HTTPLogger.logDebug("+++ StateChange oldState=" + oldState + ", newState=" + newState);
               }

               ApplicationRuntimeMBean appRTMBean = (ApplicationRuntimeMBean)evt.getSource();
               String evtVersion = appRTMBean.getApplicationVersion();
               if (newState == 2 && appRTMBean.getApplicationName().equals(ContextVersionManager.this.appName) && evtVersion != null && !evtVersion.equals(this.ctx.getVersionId())) {
                  ContextVersionManager.this.setActive(appRTMBean.getApplicationVersion(), newState == 1);

                  try {
                     ((RuntimeMBeanDelegate)appRTMBean).removePropertyChangeListener(this);
                  } catch (Throwable var9) {
                     if (ContextVersionManager.DEBUG_APP_VERSION.isEnabled()) {
                        HTTPLogger.logDebug(StackTraceUtils.throwable2StackTrace(var9));
                     }
                  }

                  return;
               }

               if (oldState == newState) {
                  return;
               }

               if (this.isActiveState(newState)) {
                  if (oldState == 0) {
                     ContextVersionManager.this.setActive(this.ctx, newState == 1);
                  } else if (this.isActiveState(oldState)) {
                     ContextVersionManager.this.adminTransition(this.ctx, oldState == 1, newState == 1);
                  }
               } else if (this.isActiveState(oldState) && (evtVersion == null && this.ctx.getVersionId() == null || evtVersion != null && evtVersion.equals(this.ctx.getVersionId()))) {
                  ContextVersionManager.this.unsetActive(this.ctx);

                  try {
                     ((RuntimeMBeanDelegate)appRTMBean).removePropertyChangeListener(this);
                  } catch (Throwable var10) {
                     if (ContextVersionManager.DEBUG_APP_VERSION.isEnabled()) {
                        HTTPLogger.logDebug(StackTraceUtils.throwable2StackTrace(var10));
                     }
                  }
               }
            }
         }

      }

      private boolean isActiveState(int state) {
         return state == 2 || state == 1;
      }

      // $FF: synthetic method
      StateChangeListener(WebAppServletContext x1, Object x2) {
         this(x1);
      }
   }
}
