package weblogic.diagnostics.lifecycle;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorLoader;
import weblogic.diagnostics.i18n.DiagnosticsRuntimeControlLogger;
import weblogic.diagnostics.runtimecontrol.BuiltinSRDescriptorBeanHolder;
import weblogic.diagnostics.runtimecontrol.RuntimeProfileDriver;
import weblogic.diagnostics.runtimecontrol.internal.RuntimeProfileDriverImpl;
import weblogic.diagnostics.runtimecontrol.internal.WLDFControlRuntimeMBeanImpl;
import weblogic.diagnostics.runtimecontrol.internal.WLDFResourceRegistrationHandler;
import weblogic.diagnostics.runtimecontrol.internal.WLDFSystemResourceControlRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.PropertyHelper;

public class RuntimeControlService implements DiagnosticComponentLifecycle, BeanUpdateListener, WLDFResourceRegistrationHandler {
   private static final boolean DISABLED = PropertyHelper.getBoolean("weblogic.diagnostics.DisableDiagnosticRuntimeControlService");
   private static final String CHANGE_ID_KEY = "ChangeID";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticRuntimeControlService");
   private Map pendingChanges = new HashMap();
   private static String LOW = "META-INF/wldf-server-resource-low.xml";
   private static String MEDIUM = "META-INF/wldf-server-resource-medium.xml";
   private static String HIGH = "META-INF/wldf-server-resource-high.xml";
   private static RuntimeControlService INSTANCE = new RuntimeControlService();
   private int status = 4;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   WLDFControlRuntimeMBeanImpl wldfControl = null;
   BuiltinSRDescriptorBeanHolder builtinDBHolder = BuiltinSRDescriptorBeanHolder.getInstance();
   private ActiveBeanUtil activeBeanUtils;
   private String serverName;
   private String clusterName;
   private RuntimeAccess runtimeAccess;
   private String currentProfile;

   private RuntimeControlService() {
      ((RuntimeProfileDriverImpl)RuntimeProfileDriver.getInstance()).setWLDFResourceRegistrationHandler(this);
   }

   public static RuntimeControlService getInstance() {
      return INSTANCE;
   }

   public int getStatus() {
      return this.status;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      if (!DISABLED) {
         this.activeBeanUtils = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
         this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         DomainMBean domain = this.runtimeAccess.getDomain();
         domain.addBeanUpdateListener(this);
         ServerMBean server = this.runtimeAccess.getServer();
         this.serverName = this.runtimeAccess.getServerName();
         ClusterMBean cluster = this.runtimeAccess.getServer().getCluster();
         if (cluster != null) {
            this.clusterName = cluster.getName();
         }

         WLDFServerDiagnosticMBean serverDiagnostic = server.getServerDiagnosticConfig();
         serverDiagnostic.addBeanUpdateListener(this);
         WLDFRuntimeMBean wldfRuntime = this.runtimeAccess.getServerRuntime().getWLDFRuntime();

         try {
            this.wldfControl = new WLDFControlRuntimeMBeanImpl(this.runtimeAccess.getServerName(), wldfRuntime);
         } catch (ManagementException var13) {
            throw new DiagnosticComponentLifecycleException(var13);
         }

         wldfRuntime.setWLDFControlRuntime(this.wldfControl);
         WLDFSystemResourceMBean[] resources = domain.getWLDFSystemResources();
         WLDFSystemResourceMBean[] var7 = resources;
         int var8 = resources.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            WLDFSystemResourceMBean resource = var7[var9];

            try {
               WLDFSystemResourceControlRuntimeMBean systemProfile = new WLDFSystemResourceControlRuntimeMBeanImpl(resource.getWLDFResource(), resource.getName(), this.wldfControl, false);
               this.wldfControl.addSystemWLDFProfileControl(systemProfile);
               this.registerSystemResourceListener(resource);
            } catch (ManagementException var12) {
               throw new DiagnosticComponentLifecycleException(var12);
            }
         }

         this.currentProfile = this.runtimeAccess.getServer().getServerDiagnosticConfig().getWLDFBuiltinSystemResourceType();
         String profileFile = this.getProfileFile(this.currentProfile);
         if (profileFile != null) {
            this.deployProfile(this.currentProfile, profileFile);
         }

         this.status = 0;
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
      this.status = 1;
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      this.status = 2;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (debugLogger.isDebugEnabled()) {
         this.debug("prepareUpdate: START processing " + event);
      }

      DescriptorBean sourceBean = event.getSourceBean();
      if (sourceBean instanceof WLDFBean) {
         WLDFResourceBean candidateChange = this.findResourceBeanParent(event.getProposedBean());
         if (candidateChange != null && !this.pendingChanges.containsKey(candidateChange.getName())) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("Adding WLDF resource " + candidateChange.getName() + " to change list for update event " + event);
            }

            synchronized(this.pendingChanges) {
               if (!this.pendingChanges.containsKey("ChangeID")) {
                  this.pendingChanges.put("ChangeID", event.getUpdateID());
               }

               this.pendingChanges.put(candidateChange.getName(), candidateChange);
            }
         } else if (debugLogger.isDebugEnabled()) {
            this.debug("Could not find parent WLDFResourceBean for update event " + event);
         }
      } else if (sourceBean instanceof WLDFSystemResourceMBean && this.isTargetAddedHere(event)) {
         this.pendingChanges.clear();
         this.pendingChanges.put("ChangeID", new Object());
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("prepareUpdate: FINISHED processing " + event);
      }

   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean sourceBean = event.getSourceBean();
      if (debugLogger.isDebugEnabled()) {
         this.debug("+++++++ Property Update " + event.getUpdateID() + " for " + sourceBean);
         BeanUpdateEvent.PropertyUpdate[] var3 = event.getUpdateList();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate update = var3[var5];
            this.debug("update: " + update.toString());
         }
      }

      if (sourceBean instanceof DomainMBean) {
         this.handleDomainUpdate(event);
      } else if (sourceBean instanceof WLDFServerDiagnosticMBean) {
         this.handleProfileChangeUpdate(event, sourceBean);
      } else if (sourceBean instanceof WLDFSystemResourceMBean) {
         this.handleSystemResourceChangeUpdate(event);
      } else {
         this.activateUpdates();
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("------ Property Update complete" + event.getUpdateID() + " for " + sourceBean);
      }

   }

   private void handleProfileChangeUpdate(BeanUpdateEvent event, DescriptorBean sourceBean) throws BeanUpdateFailedException {
      WLDFServerDiagnosticMBean source = (WLDFServerDiagnosticMBean)sourceBean;
      WLDFServerDiagnosticMBean proposed = (WLDFServerDiagnosticMBean)event.getProposedBean();
      BeanUpdateEvent.PropertyUpdate[] changes2 = event.getUpdateList();

      for(int i = 0; i < changes2.length; ++i) {
         if (changes2[i].getPropertyName().equals("WLDFBuiltinSystemResourceType")) {
            String newProfile = proposed.getWLDFBuiltinSystemResourceType();

            try {
               if ("None".equals(newProfile)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Undeploying profile " + this.currentProfile);
                  }

                  this.undeployProfile(this.currentProfile);
               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Undeploying profile " + this.currentProfile);
                  }

                  this.undeployProfile(this.currentProfile);
                  this.currentProfile = newProfile;
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Deploying profile " + this.currentProfile);
                  }

                  this.deployProfile(this.currentProfile, this.getProfileFile(this.currentProfile));
               }
            } catch (ManagementException var9) {
               throw new BeanUpdateFailedException(var9.getMessage(), var9);
            }
         }
      }

   }

   private void handleDomainUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();

      for(int i = 0; i < changes.length; ++i) {
         WLDFSystemResourceMBean removed;
         if (changes[i].getAddedObject() instanceof WLDFSystemResourceMBean) {
            removed = (WLDFSystemResourceMBean)changes[i].getAddedObject();

            try {
               if (!this.ignoreSystemResource(removed)) {
                  WLDFSystemResourceControlRuntimeMBean systemProfile = new WLDFSystemResourceControlRuntimeMBeanImpl(removed.getWLDFResource(), removed.getName(), this.wldfControl, false);
                  this.wldfControl.addSystemWLDFProfileControl(systemProfile);
                  this.registerSystemResourceListener(removed);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Ignoring resource " + removed.getName() + ", as it is either scoped to a partition or a domain-level resource-group");
               }
            } catch (ManagementException var7) {
               throw new BeanUpdateFailedException(var7.getMessage(), var7);
            }
         }

         if (changes[i].getRemovedObject() instanceof WLDFSystemResourceMBean) {
            removed = (WLDFSystemResourceMBean)changes[i].getRemovedObject();
            if (!this.ignoreSystemResource(removed)) {
               this.unregisterSystemResourceListener(removed);

               try {
                  this.wldfControl.removeSystemWLDFProfileControl(removed.getName());
               } catch (ManagementException var6) {
                  throw new BeanUpdateFailedException(var6.getMessage(), var6);
               }
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Ignoring resource " + removed.getName() + ", as it is either scoped to a partition or a domain-level resource-group");
            }
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("Changes rolled back");
      }

      this.pendingChanges.clear();
   }

   private String getProfileFile(String profile) {
      String profileFile = null;
      if (profile.equals("Low")) {
         profileFile = LOW;
      } else if (profile.equals("Medium")) {
         profileFile = MEDIUM;
      } else if (profile.equals("High")) {
         profileFile = HIGH;
      }

      return profileFile;
   }

   private void deployProfile(String profileName, String profileFile) {
      InputStream in = null;

      try {
         in = this.getClass().getClassLoader().getResourceAsStream(profileFile);
         if (in != null) {
            WLDFDescriptorLoader loader = new WLDFDescriptorLoader(in);

            try {
               WLDFResourceBean resource = (WLDFResourceBean)loader.loadDescriptorBean();
               this.builtinDBHolder.setBuildinSRDescriptorBean(resource);
               WLDFSystemResourceControlRuntimeMBean profile = new WLDFSystemResourceControlRuntimeMBeanImpl(resource, profileName, this.wldfControl, false);
               this.wldfControl.addSystemWLDFProfileControl(profile);
               profile.setEnabled(true);
            } catch (Exception var14) {
               throw new Error(var14);
            }
         } else {
            DiagnosticsRuntimeControlLogger.logBuiltinResourceDescriptorNotFound(profileName, profileFile, "None");
            this.currentProfile = "None";
         }
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var13) {
            }
         }

      }

   }

   private void undeployProfile(String currentProfile) throws ManagementException {
      if (currentProfile != null && !"None".equals(currentProfile)) {
         this.builtinDBHolder.setBuildinSRDescriptorBean((WLDFResourceBean)null);
         this.wldfControl.removeSystemWLDFProfileControl(currentProfile);
      }
   }

   private WLDFResourceBean findResourceBeanParent(DescriptorBean bean) {
      if (debugLogger.isDebugEnabled()) {
         this.debug("findResourceBeanParent: bean=" + bean + " parent=" + bean.getParentBean());
      }

      WLDFResourceBean parent = null;
      if (bean instanceof WLDFResourceBean) {
         parent = (WLDFResourceBean)bean;
      } else if (bean instanceof WLDFBean) {
         parent = this.findResourceBeanParent(bean.getParentBean());
      }

      return parent;
   }

   private void updateWLDFBeanListener(Object value, boolean add) {
      if (value != null) {
         if (value.getClass().isArray()) {
            Object[] values = (Object[])((Object[])value);
            Object[] var4 = values;
            int var5 = values.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Object v = var4[var6];
               this.updateWLDFBeanListener(v, add);
            }
         } else if (value instanceof WLDFBean) {
            if (add) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("adding listener to " + value);
               }

               ((DescriptorBean)value).addBeanUpdateListener(this);
            } else {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("removing listener from " + value);
               }

               ((DescriptorBean)value).removeBeanUpdateListener(this);
            }

            List accessors = this.getAccessors(value.getClass());
            Iterator var10 = accessors.iterator();

            while(var10.hasNext()) {
               Method accessor = (Method)var10.next();

               try {
                  Object childValue = accessor.invoke(value, (Object[])null);
                  this.updateWLDFBeanListener(childValue, add);
               } catch (Exception var8) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Exception occurred adding listener to bean", var8);
                  }
               }
            }
         }

      }
   }

   public void registerWLDFResource(String name, WLDFResourceBean wldfResource) {
      DomainMBean domain = this.runtimeAccess.getDomain();
      WLDFSystemResourceMBean sr = domain.lookupWLDFSystemResource(name);
      if (sr != null && !this.isTargetedHere(sr)) {
         this.updateWLDFBeanListener(wldfResource, true);
      }

   }

   public void unregisterWLDFResource(String name, WLDFResourceBean wldfResource) {
      this.updateWLDFBeanListener(wldfResource, false);
   }

   private List getAccessors(Class clazz) {
      Method[] methods = clazz.getMethods();
      List accessors = new ArrayList(methods.length);
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         Class returnType = m.getReturnType();
         returnType = returnType.isArray() ? returnType.getComponentType() : returnType;
         Class[] parameterTypes = m.getParameterTypes();
         if (parameterTypes.length == 0 && m.getName().startsWith("get") && !m.getName().equals("getParentBean") && WLDFBean.class.isAssignableFrom(returnType)) {
            accessors.add(m);
         }
      }

      return accessors;
   }

   private void registerSystemResourceListener(WLDFSystemResourceMBean resource) {
      if (this.ignoreSystemResource(resource)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Ignoring resource " + resource.getName() + ", as it is either scoped to a partition or a domain-level resource-group");
         }

      } else {
         resource.addBeanUpdateListener(this);
      }
   }

   private void unregisterSystemResourceListener(WLDFSystemResourceMBean resource) {
      if (this.ignoreSystemResource(resource)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Ignoring resource " + resource.getName() + ", as it is either scoped to a partition or a domain-level resource-group");
         }

      } else {
         resource.removeBeanUpdateListener(this);
         if (debugLogger.isDebugEnabled()) {
            this.debug("WLDF resource " + resource.getName() + "targeted to current server, removing bean listeners on child beans");
         }

         this.updateWLDFBeanListener(resource.getWLDFResource(), false);
         this.pendingChanges.remove(resource.getName());
      }
   }

   private boolean isTargetedHere(WLDFSystemResourceMBean sr) {
      TargetMBean[] var2 = sr.getTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         String targetName = target.getName();
         if (targetName.equals(this.serverName) || targetName.equals(this.clusterName)) {
            return true;
         }
      }

      return false;
   }

   private void debug(String msg) {
      debugLogger.debug(msg);
   }

   private void activateUpdates() throws BeanUpdateFailedException {
      if (this.pendingChanges.size() > 0) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Updating all pending runtime control changes");
         }

         Collection changes = this.pendingChanges.values();
         Iterator var2 = changes.iterator();

         while(var2.hasNext()) {
            Object bean = var2.next();

            try {
               if (bean instanceof WLDFResourceBean) {
                  WLDFResourceBean wldfBean = (WLDFResourceBean)bean;
                  if (debugLogger.isDebugEnabled()) {
                     this.debug("Redeploy " + wldfBean.getName());
                  }

                  this.wldfControl.redeploy(wldfBean);
               } else if (bean instanceof WLDFSystemResourceMBean) {
                  WLDFSystemResourceMBean sr = (WLDFSystemResourceMBean)bean;
                  if (!this.isTargetedHere(sr)) {
                     this.updateWLDFBeanListener(sr.getWLDFResource(), true);
                  } else {
                     this.updateWLDFBeanListener(sr.getWLDFResource(), false);
                  }
               }
            } catch (ManagementException var5) {
               throw new BeanUpdateFailedException(var5.getLocalizedMessage(), var5);
            }
         }

         this.pendingChanges.clear();
      }

   }

   private void handleSystemResourceChangeUpdate(BeanUpdateEvent event) {
      DescriptorBean sourceBean = event.getSourceBean();
      if (sourceBean instanceof WLDFSystemResourceMBean && this.isTargetAddedHere(event)) {
         WLDFSystemResourceMBean sr = (WLDFSystemResourceMBean)sourceBean;
         this.updateWLDFBeanListener(sr.getWLDFResource(), false);
      }

   }

   private boolean matchesTarget(TargetMBean addedTarget) {
      return this.serverName != null && this.serverName.equals(addedTarget.getName()) || this.clusterName != null && this.clusterName.equals(addedTarget.getName());
   }

   private boolean isClone(ConfigurationMBean bean) {
      return this.activeBeanUtils.toOriginalBean(bean) != bean;
   }

   private boolean ignoreSystemResource(WLDFSystemResourceMBean resource) {
      return this.isClone(resource) || !(resource.getParent() instanceof DomainMBean);
   }

   private boolean isTargetAddedHere(BeanUpdateEvent event) {
      DescriptorBean sourceBean = event.getSourceBean();
      if (sourceBean instanceof WLDFSystemResourceMBean) {
         WLDFSystemResourceMBean sr = (WLDFSystemResourceMBean)sourceBean;
         String srName = sr.getName();
         if (debugLogger.isDebugEnabled()) {
            this.debug("WLDF System resource " + srName + " targets change, event == " + event);
         }

         BeanUpdateEvent.PropertyUpdate[] var5 = event.getUpdateList();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            BeanUpdateEvent.PropertyUpdate change = var5[var7];
            if ("Targets".equals(change.getPropertyName())) {
               switch (change.getUpdateType()) {
                  case 2:
                     TargetMBean addedTarget = (TargetMBean)change.getAddedObject();
                     if (this.matchesTarget(addedTarget)) {
                        if (debugLogger.isDebugEnabled()) {
                           this.debug("System resource " + srName + " is now targeted here");
                        }

                        return true;
                     }
               }
            }
         }
      }

      return false;
   }
}
