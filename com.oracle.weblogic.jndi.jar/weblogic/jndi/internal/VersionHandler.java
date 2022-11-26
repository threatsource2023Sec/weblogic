package weblogic.jndi.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import weblogic.jndi.JNDILogger;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtils;

final class VersionHandler extends AbstractAdminModeHandler {
   private ActiveVersionInfo activeVersionInfo = null;
   private static final DebugCategory DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
   private final ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
   private static final boolean debugAppVersion;
   private static final Class[] globalResources;

   VersionHandler(ServerNamingNode node) {
      super(node);
   }

   boolean isVersioned() {
      return this.getActiveVersionInfo() != null;
   }

   private ActiveVersionInfo getActiveVersionInfo() {
      return this.activeVersionInfo;
   }

   private ActiveVersionInfo getOrCreateActiveVersionInfo(String name, String appName) {
      if (this.activeVersionInfo == null) {
         this.activeVersionInfo = new ActiveVersionInfo(name, appName);
      }

      return this.activeVersionInfo;
   }

   Object getCurrentVersion(ServerNamingNode verNode, Hashtable env) throws NoPermissionException, NamingException {
      ActiveVersionInfo info = verNode.getVersionHandler().getActiveVersionInfo();
      if (info == null) {
         return verNode;
      } else {
         String versionId = this.avus.getCurrentVersionId(info.appName);
         if (versionId == null) {
            if (this.allowExternalAppLookup(env)) {
               return this.getActiveVersionObjectAndInit(info, verNode, env);
            } else {
               versionId = this.getCurrentVersionIdFromEnv(info.appName, env);
               if (versionId != null) {
                  return this.getActiveVersionObject(info, verNode, env);
               } else {
                  if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
                     NamingDebugLogger.debug("+++ allowExternalAppLookup check failed: ActiveVersionInfo=" + info + ", CurrentApp=" + this.avus.getCurrentApplicationId() + ", CurrentWorkContext=" + this.avus.getDebugWorkContexts() + ", JNDIEnv=" + (env == null ? "" : env.toString()) + "\n" + StackTraceUtils.throwable2StackTrace(new Exception()));
                  }

                  JNDILogger.logExternalAppLookupWarning(verNode.getNameInNamespace(""));
                  return this.getActiveVersionObjectAndInit(info, verNode, env);
               }
            }
         } else {
            try {
               Object object = verNode.lookupHere(versionId, env, "");
               if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("+++ lookupHere(" + verNode.getNameInNamespace(versionId) + ") returns " + object);
               }

               return object;
            } catch (NameNotFoundException var6) {
               if (this.relaxVersionLookup(env)) {
                  return this.getActiveVersionObjectAndInit(info, verNode, env);
               } else {
                  throw this.node.newNameNotFoundException(var6.getMessage() + "  Possibly version '" + versionId + "' of application '" + info.appName + "' was retired.  To relax lookup to return the active version, set context environment property defined by weblogic.jndi.WLContext.RELAX_VERSION_LOOKUP to \"true\".", "", env);
               }
            }
         }
      }
   }

   private boolean allowExternalAppLookup(Hashtable env) {
      return env != null && "true".equalsIgnoreCase(this.node.getProperty(env, "weblogic.jndi.allowExternalAppLookup"));
   }

   private boolean relaxVersionLookup(Hashtable env) {
      return env != null && "true".equalsIgnoreCase(this.node.getProperty(env, "weblogic.jndi.relaxVersionLookup"));
   }

   private String getCurrentVersionIdFromEnv(String appName, Hashtable env) {
      if (env == null) {
         return null;
      } else {
         String curAppId = this.node.getProperty(env, "weblogic.jndi.lookupApplicationId");
         String curAppName = this.avus.getApplicationName(curAppId);
         return appName != null && appName.equals(curAppName) ? this.avus.getVersionId(curAppId) : null;
      }
   }

   private Object getActiveVersionObject(ActiveVersionInfo info, ServerNamingNode verNode, Hashtable env) throws NameNotFoundException, NamingException {
      return this.getActiveVersionObject(info, verNode, env, false);
   }

   private Object getActiveVersionObjectAndInit(ActiveVersionInfo info, ServerNamingNode verNode, Hashtable env) throws NameNotFoundException, NamingException {
      return this.getActiveVersionObject(info, verNode, env, true);
   }

   private Object getActiveVersionObject(ActiveVersionInfo info, ServerNamingNode verNode, Hashtable env, boolean initWorkContext) throws NameNotFoundException, NamingException {
      boolean adminMode = this.isAdminModeRequest(info);
      String versionId = adminMode ? info.adminModeVersionId : info.versionId;
      if (versionId == null) {
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ getActiveVersion failed, info=" + info + ", isAdmin=" + this.isAdminModeRequest(info));
         }

         throw this.node.newNameNotFoundException("Unable to resolve '" + verNode.getNameInNamespace("") + "'.  Possibly previously active version was already unbound.", "", env);
      } else {
         Object obj;
         if (adminMode) {
            obj = info.adminModeObject;
         } else {
            obj = info.object;
         }

         if (initWorkContext) {
            this.avus.setCurrentVersionId(info.appName, versionId);
            if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ setCurrentVersionId(appName=" + info.appName + ", versionId=" + versionId + ", admin=" + adminMode + ")");
            }
         }

         return obj;
      }
   }

   private boolean isAdminModeRequest(ActiveVersionInfo info) {
      return info.adminModeVersionId == null ? false : this.avus.isAdminModeRequest();
   }

   boolean isBindVersioned() {
      return this.avus.getBindApplicationId() != null;
   }

   void bindHere(String name, Object object, Hashtable env) throws NoPermissionException, NamingException {
      if (object instanceof Context) {
         throw new NamingException("Context cannot be versioned");
      } else {
         ServerNamingNode verNode = null;
         String appId = this.avus.getBindApplicationId();
         String appName = this.avus.getApplicationName(appId);

         try {
            Object obj = this.node.superLookupHere(name, env, "");
            if (!(obj instanceof ServerNamingNode)) {
               throw this.node.fillInException(new NameAlreadyBoundException(name + " is already bound"), name, obj, "");
            }

            verNode = (ServerNamingNode)obj;
            this.checkApp(verNode, appName, name);
         } catch (NameNotFoundException var8) {
            verNode = (ServerNamingNode)this.node.createSubnodeHere(name, this.getNoReplicateBindingsEnv(env));
         }

         String versionId = this.avus.getVersionId(appId);
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ >>> bindVersionHere(" + name + ", " + verNode.getNameInNamespace(versionId) + ", " + object.getClass().getName() + ")");
         }

         verNode.bindHere(versionId, object, env, false, (Object)null);
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ <<< bindVersionHere(" + name + ", " + verNode.getNameInNamespace(versionId) + ", " + object.getClass().getName() + ") succeeded");
         }

         this.updateActiveVersionInfo(verNode, appName, versionId, verNode.getBound(versionId));
      }
   }

   void rebindHere(String name, Object object, Hashtable env) throws NoPermissionException, NamingException {
      if (object instanceof Context) {
         throw new NamingException("Context cannot be versioned");
      } else {
         ServerNamingNode verNode = null;
         String appId = this.avus.getBindApplicationId();
         String appName = this.avus.getApplicationName(appId);
         String versionId = this.avus.getVersionId(appId);

         try {
            Object obj = this.node.superLookupHere(name, env, "");
            if (!(obj instanceof ServerNamingNode)) {
               String detail = this.avus.getDetailedInfoAboutVersionContext();
               throw this.node.fillInException(new NamingException(name + " was bound without version previously.  Cannot rebind with version. " + detail + "."), name, obj, "");
            }

            verNode = (ServerNamingNode)obj;
            this.checkApp(verNode, appName, name);
         } catch (NameNotFoundException var10) {
            verNode = (ServerNamingNode)this.node.createSubnodeHere(name, this.getNoReplicateBindingsEnv(env));
         }

         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ >>> rebindVersionHere(" + verNode.getNameInNamespace(versionId) + ", " + object.getClass().getName() + ")");
         }

         verNode.rebindHere(versionId, object, env, false, (Object)null);
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ <<< rebindVersionHere(" + verNode.getNameInNamespace(versionId) + ", " + object.getClass().getName() + ") succeeded");
         }

         this.updateActiveVersionInfo(verNode, appName, versionId, verNode.getBound(versionId));
      }
   }

   void unbindHere(String name, Object object, Hashtable env) throws NoPermissionException, NamingException {
      Object obj = this.node.superLookupHere(name, env, "");
      String appId = this.avus.getBindApplicationId();
      String appName = this.avus.getApplicationName(appId);
      String versionId = this.avus.getVersionId(appId);
      if (!(obj instanceof ServerNamingNode)) {
         String detail = this.avus.getDetailedInfoAboutVersionContext();
         throw this.node.fillInException(new NamingException(name + " is bound without version previously.  Cannot unbind with version. " + detail + "."), name, obj, "");
      } else {
         ServerNamingNode verNode = (ServerNamingNode)obj;
         this.checkApp(verNode, appName, name);
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ >>> unbindVersionHere(" + verNode.getNameInNamespace(versionId) + ")");
         }

         verNode.unbindHere(versionId, object, env, false);
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ <<< unbindVersionHere(" + verNode.getNameInNamespace(versionId) + ") succeeded");
         }

         ActiveVersionInfo info = verNode.getVersionHandler().getActiveVersionInfo();
         if (info != null && verNode.isUnbound(versionId)) {
            info.unsetActive(versionId);
         }

      }
   }

   void checkUnbind(String name, Hashtable env, Object object, Object bound) throws NamingException {
      String relativeName = this.node.getRelativeName();
      ServerNamingNode parent = (ServerNamingNode)this.node.getParent();
      boolean unboundComplete = this.isVersioned() && this.node.isUnbound(name);
      boolean prepareToDestroyComplete = this.isVersioned() && this.node.getNumOfBindings() == 0;
      if (prepareToDestroyComplete) {
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ No objects after unbinding " + name + ", unbindHere(" + this.node.getNameInNamespace() + ")");
         }

         if (parent != null) {
            parent.destroySubnodeHere(relativeName, this.getNoReplicateBindingsEnv(env));
         }
      }

      if (unboundComplete || prepareToDestroyComplete) {
         if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ No more this version objects after unbinding " + name + ", unbindHere(" + this.node.getNameInNamespace() + "), sending object remove notification.");
            if (unboundComplete && !prepareToDestroyComplete) {
               NamingDebugLogger.debug("+++ some objects remain under " + relativeName + ", unbound object: " + name + ". remained objects: " + this.node.getBoundObjects());
            }
         }

         if (parent != null) {
            parent.notifyObjectRemovedListeners(relativeName, env, object, bound);
         }
      }

   }

   private void checkApp(ServerNamingNode verNode, String appName, String name) throws NamingException {
      if (verNode.isVersioned()) {
         ActiveVersionInfo info = verNode.getVersionHandler().getActiveVersionInfo();
         if (info != null && info.name != null && !info.appName.equals(appName) && verNode.getNumOfBindings() > 0) {
            throw this.node.fillInException(new NamingException(name + " was previously bound from another application '" + info.appName + "'"), name, (Object)null, "");
         }
      }
   }

   private Hashtable getNoReplicateBindingsEnv(Hashtable env) {
      if (!this.node.replicateBindings(env)) {
         return env;
      } else {
         Hashtable newEnv = new Hashtable(env);
         newEnv.put("weblogic.jndi.replicateBindings", "false");
         return newEnv;
      }
   }

   private void updateActiveVersionInfo(ServerNamingNode verNode, String appName, String versionId, Object object) throws NoPermissionException, NamingException {
      ActiveVersionInfo info = verNode.getVersionHandler().getOrCreateActiveVersionInfo(verNode.getNameInNamespace(), appName);
      info.delaySetActive(versionId, object);
   }

   private boolean allowGlobalResourceLookup(Hashtable env) {
      return env != null && "true".equalsIgnoreCase(this.node.getProperty(env, "weblogic.jndi.allowGlobalResourceLookup"));
   }

   void checkGlobalResource(Object object, Hashtable env) throws NamingException {
      if (this.avus.getCurrentVersionId() != null) {
         int i = 0;

         for(int len = globalResources.length; i < len; ++i) {
            if (globalResources[i].isInstance(object)) {
               if (this.allowGlobalResourceLookup(env)) {
                  return;
               }

               if (debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
                  NamingDebugLogger.debug("+++ checkGlobalResource failed: CurrentApp=" + this.avus.getCurrentApplicationId() + ", CurrentWorkContext=" + this.avus.getDebugWorkContexts() + ", JNDIEnv=" + (env == null ? "" : env.toString()) + ", object=" + object + ", class=" + object.getClass().getName() + "\n" + StackTraceUtils.throwable2StackTrace(new Exception()));
               }

               String appName = this.avus.getDisplayName(this.avus.getCurrentApplicationId());
               JNDILogger.logGlobalResourceLookupWarning(this.node.getNameInNamespace(""), appName);
            }
         }

      }
   }

   static {
      debugAppVersion = DEBUG_APP_VERSION.isEnabled();
      globalResources = new Class[]{DataSource.class, Destination.class, XADataSource.class};
   }

   private final class ActiveVersionInfo {
      private String name;
      private String appName;
      private String versionId;
      private Object object;
      private String adminModeVersionId;
      private Object adminModeObject;
      private Hashtable listeners;

      private ActiveVersionInfo(String name, String appName) {
         this.listeners = new Hashtable();
         this.name = name;
         this.appName = appName;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("ActiveVersionInfo[name=").append(this.name).append(",appName=").append(this.appName).append(",version=").append(this.versionId).append(",adminVersion=").append(this.adminModeVersionId).append("]");
         return sb.toString();
      }

      private void setActive(String inVersionId, Object inObject, boolean adminMode) {
         if (adminMode) {
            this.adminModeVersionId = inVersionId;
            this.adminModeObject = inObject;
            VersionHandler.this.setAdminMode(inVersionId);
         } else {
            this.versionId = inVersionId;
            this.object = inObject;
         }

         if (VersionHandler.debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ ActiveVersionInfo.setActive(name=" + this.name + ", appName=" + this.appName + ", versionId=" + inVersionId + ", admin=" + adminMode + "), info=" + this);
         }

      }

      private void delaySetActive(String inVersionId, Object inObject) {
         ApplicationRuntimeMBean appRTMBean = VersionHandler.this.avus.getCurrentApplicationRuntime();
         if (appRTMBean == null) {
            this.setActive(inVersionId, inObject, false);
         } else {
            if (VersionHandler.debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ ActiveVersionInfo.delaySetActive(name=" + this.name + ", appName=" + this.appName + ", versionId=" + inVersionId + ")");
            }

            StateChangeListener listener = new StateChangeListener(this, inVersionId, inObject);
            appRTMBean.addPropertyChangeListener(listener);
            this.listeners.put(inVersionId, listener);
            int state = appRTMBean.getActiveVersionState();
            if (this.isActiveState(state)) {
               this.setActive(inVersionId, inObject, state == 1);
            }
         }

      }

      private void unsetActive(String inVersion) {
         if (VersionHandler.debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ ActiveVersionInfo.unsetActive(name=" + this.name + ", appName=" + this.appName + ", versionId=" + inVersion + ", info=" + this);
         }

         if (inVersion.equals(this.versionId)) {
            this.versionId = null;
            this.object = null;
         } else if (inVersion.equals(this.adminModeVersionId)) {
            this.adminModeVersionId = null;
            this.adminModeObject = null;
         }

         VersionHandler.this.unsetAdminMode(inVersion);
         StateChangeListener listener = (StateChangeListener)this.listeners.remove(inVersion);
         if (listener != null) {
            ApplicationRuntimeMBean appRTMBean = VersionHandler.this.avus.getCurrentApplicationRuntime();
            if (appRTMBean != null) {
               try {
                  appRTMBean.removePropertyChangeListener(listener);
               } catch (Throwable var5) {
               }

            }
         }
      }

      private void adminTransition(String inVersionId, Object inObject, boolean oldAdminMode, boolean newAdminMode) {
         if (oldAdminMode && !newAdminMode) {
            if (this.adminModeVersionId != null && this.adminModeVersionId.equals(inVersionId)) {
               this.adminModeVersionId = null;
               this.adminModeObject = null;
            }

            this.versionId = inVersionId;
            this.object = inObject;
            VersionHandler.this.unsetAdminMode(inVersionId);
         } else if (!oldAdminMode && newAdminMode) {
            if (this.versionId != null && this.versionId.equals(inVersionId)) {
               this.versionId = null;
               this.object = null;
            }

            this.adminModeVersionId = inVersionId;
            this.adminModeObject = inObject;
            VersionHandler.this.setAdminMode(inVersionId);
         }

         if (VersionHandler.debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ ActiveVersionInfo.adminTransition(name=" + this.name + ", appName=" + this.appName + ", versionId=" + inVersionId + ", admin=" + newAdminMode + "), info=" + this);
         }

      }

      private boolean isActiveState(int state) {
         return state == 2 || state == 1;
      }

      // $FF: synthetic method
      ActiveVersionInfo(String x1, String x2, Object x3) {
         this(x1, x2);
      }

      private final class StateChangeListener implements PropertyChangeListener {
         ActiveVersionInfo info;
         String versionId;
         Object object;

         private StateChangeListener(ActiveVersionInfo info, String versionId, Object object) {
            this.info = info;
            this.versionId = versionId;
            this.object = object;
         }

         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equalsIgnoreCase("ActiveVersionState")) {
               Object oldValue = evt.getOldValue();
               Object newValue = evt.getNewValue();
               if (oldValue instanceof Integer && newValue instanceof Integer) {
                  int oldState = (Integer)oldValue;
                  int newState = (Integer)newValue;
                  if (VersionHandler.debugAppVersion || NamingDebugLogger.isDebugEnabled()) {
                     NamingDebugLogger.debug("+++ StateChange for name=" + ActiveVersionInfo.this.name + ", appName=" + ActiveVersionInfo.this.appName + ", versionId=" + this.versionId + ", oldState=" + oldState + ", newState=" + newState);
                  }

                  if (oldState == newState) {
                     return;
                  }

                  if (ActiveVersionInfo.this.isActiveState(newState)) {
                     if (oldState == 0) {
                        ActiveVersionInfo.this.setActive(this.versionId, this.object, newState == 1);
                     } else if (ActiveVersionInfo.this.isActiveState(oldState)) {
                        ActiveVersionInfo.this.adminTransition(this.versionId, this.object, oldState == 1, newState == 1);
                     }
                  } else if (ActiveVersionInfo.this.isActiveState(oldState)) {
                     ActiveVersionInfo.this.unsetActive(this.versionId);
                  }
               }
            }

         }

         // $FF: synthetic method
         StateChangeListener(ActiveVersionInfo x1, String x2, Object x3, Object x4) {
            this(x1, x2, x3);
         }
      }
   }
}
