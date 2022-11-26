package weblogic.deploy.internal;

import java.io.File;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;
import weblogic.Home;
import weblogic.deploy.api.spi.deploy.internal.InternalApp;
import weblogic.deploy.api.spi.deploy.internal.InternalAppFactory;
import weblogic.deploy.api.spi.deploy.internal.InternalAppFactoryExt;
import weblogic.deploy.common.Debug;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;
import weblogic.management.configuration.AdminConsoleMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.saml2.SAML2ServerConfig;
import weblogic.security.service.PrivilegedActions;

@Service
@Singleton
public final class InternalAppService {
   private List internalApps = null;
   private static final String COOKIE_NAME = "CookieName";
   private static final String COOKIE_PATH = "CookiePath";
   private static final String PROTECTED_COOKIE_ENABLED = "ProtectedCookieEnabled";
   private static final String SESSION_TIMEOUT = "SessionTimeout";
   private boolean areEarlyFactoriesDone = false;
   private static final boolean IS_SERVICE_ENABLED = Boolean.parseBoolean(System.getProperty("weblogic.management.deploy.internal.apps.enabled", "true"));
   public static final String LIB;
   public static final String COMMON_MODULES;
   private static final AuthenticatedSubject kernelId;
   private final IterableProvider internalAppFactories;

   @Inject
   public InternalAppService(IterableProvider iafs) {
      this.internalAppFactories = iafs;
   }

   public synchronized List getInternalApplications() {
      if (!IS_SERVICE_ENABLED) {
         return Collections.EMPTY_LIST;
      } else {
         if (!this.areEarlyFactoriesDone) {
            this.getEarlyInternalApplications().addAll(this.runtimeFactoryInternalApps());
            this.areEarlyFactoriesDone = true;
         }

         return this.internalApps;
      }
   }

   public synchronized List getEarlyInternalApplications() {
      if (!IS_SERVICE_ENABLED) {
         return Collections.EMPTY_LIST;
      } else if (this.internalApps != null) {
         return this.internalApps;
      } else {
         this.initInternalApps();
         return this.internalApps;
      }
   }

   private List runtimeFactoryInternalApps() {
      List result = new ArrayList();
      Iterator var2 = this.internalAppFactories.iterator();

      while(var2.hasNext()) {
         InternalAppFactory iaf = (InternalAppFactory)var2.next();
         if (iaf instanceof InternalAppFactoryExt && ((InternalAppFactoryExt)iaf).requiresRuntimes()) {
            result.addAll(iaf.createInternalApps());
         }
      }

      return result;
   }

   private List earlyFactoryInternalApps() {
      List result = new ArrayList();
      ServerRuntimeMBean serverRuntime = getRuntimeAccess().getServerRuntime();
      Iterator var3 = this.internalAppFactories.iterator();

      while(true) {
         InternalAppFactory iaf;
         do {
            if (!var3.hasNext()) {
               this.areEarlyFactoriesDone = serverRuntime != null;
               return result;
            }

            iaf = (InternalAppFactory)var3.next();
         } while(iaf instanceof InternalAppFactoryExt && ((InternalAppFactoryExt)iaf).requiresRuntimes());

         result.addAll(iaf.createInternalApps());
      }
   }

   private void initInternalApps() {
      DomainMBean domainMBean = getRuntimeAccess().getDomain();
      ServerRuntimeMBean serverRuntimeMBean = getRuntimeAccess().getServerRuntime();
      this.internalApps = new ArrayList();
      this.internalApps.add(new InternalApp("bea_wls_management_internal2", ".war", true, true, false, false, new String[]{"bea_wls_management_internal2"}, false));
      InternalApp readyApp = new InternalApp("weblogic", ".war", false, false, false, true, new String[]{"weblogic"}, false, LIB, true);
      this.internalApps.add(readyApp);
      readyApp.relatesToOptionalFeature(InternalApp.OptionalFeatureName.READYAPP, true);
      InternalApp iapp;
      if (domainMBean.isConsoleEnabled()) {
         String contextPath = domainMBean.getConsoleContextPath();
         iapp = new InternalApp("consoleapp", "", true, false, false, false, domainMBean.isProductionModeEnabled() ? null : new String[]{contextPath, "console-help"}, true);
         initConsoleDeploymentPlan(iapp, domainMBean);
         this.internalApps.add(iapp);
      }

      if (domainMBean.getRestfulManagementServices().isEnabled()) {
         this.internalApps.add(new InternalApp("wls-management-services", ".war", false, false, false, true, (String[])null, false, LIB, true));
      }

      this.internalApps.add(new InternalApp("bea_wls_deployment_internal", ".war", false, true, false, false, new String[]{"bea_wls_deployment_internal"}, false, LIB, true));
      ServerMBean server = getRuntimeAccess().getServer();
      if (server.getCluster() != null) {
         this.internalApps.add(new InternalApp("bea_wls_cluster_internal", ".war", false, true));
      }

      if (!server.isDefaultInternalServletsDisabled() || server.isClasspathServletSecureModeEnabled() && !server.isClasspathServletDisabled()) {
         this.internalApps.add(new InternalApp("bea_wls_internal", ".war", false, true, false, false, (String[])null, false, LIB, true));
      }

      if (this.internalAppFactories != null) {
         this.internalApps.addAll(this.earlyFactoryInternalApps());
      } else if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("No 'early' Internal Applications discovered");
      }

      if (!domainMBean.isProductionModeEnabled()) {
         this.internalApps.add(new InternalApp("wls-cat", ".war", false, false, false, true, new String[]{"wls-cat"}, true, LIB, true));
      }

      if (SAML2ServerConfig.isApplicationConfigured("saml2")) {
         iapp = new InternalApp("saml2", ".war", false, false);
         iapp.setVirtualHostName(SAML2ServerConfig.getApplicationHostName("saml2"));
         iapp.setClustered(true);
         this.internalApps.add(iapp);
      }

   }

   private static void initConsoleDeploymentPlan(InternalApp app, DomainMBean domain) {
      AdminConsoleMBean consoleBean = domain.getAdminConsole();
      if (consoleBean != null) {
         boolean cookieSet = consoleBean.isSet("CookieName");
         boolean protectedCookieEnabledSet = true;
         boolean timeoutSet = consoleBean.isSet("SessionTimeout");
         EditableDescriptorManager edm = new EditableDescriptorManager();
         DeploymentPlanBean plan = (DeploymentPlanBean)edm.createDescriptorRoot(DeploymentPlanBean.class, "UTF-8").getRootBean();
         plan.setApplicationName("consoleapp");
         VariableDefinitionBean vars = plan.getVariableDefinition();
         VariableBean var;
         if (cookieSet) {
            var = vars.createVariable();
            var.setName("CookieName");
            var.setValue(consoleBean.getCookieName());
         }

         if (protectedCookieEnabledSet) {
            var = vars.createVariable();
            var.setName("CookiePath");
            if (consoleBean.isProtectedCookieEnabled()) {
               String consoleContextPath = domain.getConsoleContextPath();
               if (consoleContextPath == null || consoleContextPath.isEmpty()) {
                  consoleContextPath = "console";
               }

               var.setValue("/" + consoleContextPath + "/");
            } else {
               var.setValue("/");
            }
         }

         if (timeoutSet) {
            var = vars.createVariable();
            var.setName("SessionTimeout");
            var.setValue("" + consoleBean.getSessionTimeout());
         }

         ModuleOverrideBean mod = plan.createModuleOverride();
         mod.setModuleName("webapp");
         mod.setModuleType("war");
         ModuleDescriptorBean desc = mod.createModuleDescriptor();
         desc.setRootElement("weblogic-web-app");
         desc.setUri("WEB-INF/weblogic.xml");
         VariableAssignmentBean var;
         if (cookieSet) {
            var = desc.createVariableAssignment();
            var.setName("CookieName");
            var.setXpath("/weblogic-web-app/session-descriptor/cookie-name");
         }

         if (protectedCookieEnabledSet) {
            var = desc.createVariableAssignment();
            var.setName("CookiePath");
            var.setXpath("/weblogic-web-app/session-descriptor/cookie-path");
         }

         if (timeoutSet) {
            var = desc.createVariableAssignment();
            var.setName("SessionTimeout");
            var.setXpath("/weblogic-web-app/session-descriptor/timeout-secs");
         }

         app.setDeploymentPlanBean(plan);
      }
   }

   private static RuntimeAccess getRuntimeAccess() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      if (ra != null) {
         return ra;
      } else {
         throw new IllegalStateException("RuntimeAccess is not initialized");
      }
   }

   static {
      LIB = Home.getPath() + File.separator + "lib";
      COMMON_MODULES = LIB + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "oracle_common" + File.separator + "modules";
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
