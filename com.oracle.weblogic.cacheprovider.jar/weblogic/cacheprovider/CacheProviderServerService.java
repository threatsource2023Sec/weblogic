package weblogic.cacheprovider;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.cacheprovider.coherence.CoherenceClusterSystemResourceDeploymentFactory;
import weblogic.cacheprovider.coherence.application.CoherenceApplicationDeploymentExtensionFactory;
import weblogic.cacheprovider.coherence.management.CoherenceClusterMetricsRuntimeMBeanImpl;
import weblogic.cacheprovider.coherence.management.CoherenceMBeanListener;
import weblogic.cacheprovider.coherence.security.GridResource;
import weblogic.coherence.api.internal.CoherenceException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceManagementClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.ResourceUtils;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
@Rank(100)
public class CacheProviderServerService extends AbstractServerService {
   @Inject
   @Named("RuntimeServerService")
   private ServerService dependencyOnRuntimeServerService;
   private Map m_mapCohMetricClusterRuntimeMB = Collections.synchronizedMap(new HashMap());
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String CWEB_LOCAL_STORAGE = "coherence.session.localstorage";
   public static final String CWEB_FEDERATED_LOCAL_STORAGE = "coherence.session.federatedstorage";

   public void start() throws ServiceFailureException {
      this.initialize();
   }

   private void initialize() throws ServiceFailureException {
      ResourceUtils.registerResourceType(kernelId, GridResource.getResourceIdInfo());
      this.initFactories();
      this.initCoherence(Thread.currentThread().getContextClassLoader());
      this.initCoherenceWeb(Thread.currentThread().getContextClassLoader());
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         this.initCoherenceManagement(Thread.currentThread().getContextClassLoader());
      }

   }

   private void initFactories() {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addDeploymentFactory(new CoherenceClusterSystemResourceDeploymentFactory());
      afm.addAppDeploymentExtensionFactory(new CoherenceApplicationDeploymentExtensionFactory());
   }

   private void initCoherence(ClassLoader loader) throws ServiceFailureException {
      ServerMBean server = CoherenceClusterManager.getServerMBean();
      if (server != null && !bootCoherenceFromWLSCluster(loader, server)) {
         CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
         CoherenceClusterSystemResourceMBean mbean = server.getCoherenceClusterSystemResource();
         if (mbean != null) {
            try {
               removeOverrideProperty();
               mgr.configureClusterService(loader, mbean, (ClusterMBean)null, server);
            } catch (CoherenceException var6) {
               throw new ServiceFailureException(var6.getMessage(), var6);
            }
         } else if (mgr.isCoherenceClusterSystemResource()) {
            mgr.configureCoherenceIdentityTransformer(loader);
         }
      }

   }

   private void initCoherenceWeb(ClassLoader loader) throws ServiceFailureException {
      ServerMBean server = CoherenceClusterManager.getServerMBean();
      ClusterMBean cluster = server.getCluster();
      boolean configureCWeb = false;
      boolean configureCWebFederated = false;
      if (cluster != null) {
         if (cluster.getCoherenceTier() != null) {
            configureCWeb = cluster.getCoherenceTier().isCoherenceWebLocalStorageEnabled();
            configureCWebFederated = cluster.getCoherenceTier().isCoherenceWebFederatedStorageEnabled();
         }
      } else if (server != null && server.getCoherenceMemberConfig() != null) {
         configureCWeb = server.getCoherenceMemberConfig().isCoherenceWebLocalStorageEnabled();
         configureCWebFederated = server.getCoherenceMemberConfig().isCoherenceWebFederatedStorageEnabled();
      }

      if (configureCWeb || configureCWebFederated) {
         System.setProperty("coherence.session.localstorage", configureCWeb + "");
         System.setProperty("coherence.session.federatedstorage", configureCWebFederated + "");

         try {
            Class klass = Class.forName("weblogic.servlet.internal.session.CoherenceWebUtils", true, loader);
            Class[] parameterTypes = new Class[]{ClassLoader.class, Boolean.TYPE, Boolean.TYPE};
            Method getCacheFactoryBuilderMethod = klass.getDeclaredMethod("configureCoherenceWeb", parameterTypes);
            Object[] args = new Object[]{loader, configureCWeb, configureCWebFederated};
            getCacheFactoryBuilderMethod.invoke((Object)null, args);
         } catch (Exception var10) {
            throw new ServiceFailureException(var10.getMessage(), var10);
         }
      }

   }

   private void initCoherenceManagement(ClassLoader loader) throws ServiceFailureException {
      try {
         DomainMBean domainMBean = CoherenceClusterManager.getDomainMBean();
         CoherenceClusterSystemResourceMBean[] aCcsr = domainMBean.getCoherenceClusterSystemResources();
         CoherenceClusterSystemResourceMBean[] var4 = aCcsr;
         int var5 = aCcsr.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            CoherenceClusterSystemResourceMBean ccsr = var4[var6];
            this.registerCoherenceClusterSystemResourceMBean(ccsr);
         }

         CoherenceManagementClusterMBean[] aCohManagementClusterMB = domainMBean.getCoherenceManagementClusters();
         if (aCohManagementClusterMB != null && aCohManagementClusterMB.length > 0) {
            CoherenceManagementClusterMBean[] var11 = aCohManagementClusterMB;
            var6 = aCohManagementClusterMB.length;

            for(int var13 = 0; var13 < var6; ++var13) {
               CoherenceManagementClusterMBean cohManagementClusterMB = var11[var13];
               this.registerCoherenceMgmtClusterMBean(cohManagementClusterMB);
            }
         }

         CoherenceMBeanListener listener = new CoherenceMBeanListener(this);
         domainMBean.addBeanUpdateListener(listener);
      } catch (ManagementException var9) {
         throw new ServiceFailureException(var9.getMessage(), var9);
      }
   }

   public void registerCoherenceMgmtClusterMBean(CoherenceManagementClusterMBean cmcb) throws ManagementException {
      this.m_mapCohMetricClusterRuntimeMB.put(cmcb.getName(), new CoherenceClusterMetricsRuntimeMBeanImpl(cmcb));
   }

   public void registerCoherenceClusterSystemResourceMBean(CoherenceClusterSystemResourceMBean ccsr) throws ManagementException {
      this.m_mapCohMetricClusterRuntimeMB.put(ccsr.getName(), new CoherenceClusterMetricsRuntimeMBeanImpl(ccsr));
   }

   public void unregisterCoherenceClusterMetricsRuntimeMBean(String sName) throws ManagementException {
      CoherenceClusterMetricsRuntimeMBeanImpl mbeanImpl = (CoherenceClusterMetricsRuntimeMBeanImpl)this.m_mapCohMetricClusterRuntimeMB.remove(sName);
      mbeanImpl.unregister();
   }

   private static boolean bootCoherenceFromWLSCluster(ClassLoader loader, ServerMBean server) throws ServiceFailureException {
      ClusterMBean cluster = server.getCluster();
      if (cluster != null) {
         CoherenceClusterSystemResourceMBean mbean = cluster.getCoherenceClusterSystemResource();
         if (mbean != null) {
            try {
               removeOverrideProperty();
               CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
               mgr.configureClusterService(loader, mbean, cluster, server);
               return true;
            } catch (CoherenceException var5) {
               throw new ServiceFailureException(var5.getMessage(), var5);
            }
         }
      }

      return false;
   }

   private static void removeOverrideProperty() {
      System.getProperties().remove("coherence.override");
   }
}
