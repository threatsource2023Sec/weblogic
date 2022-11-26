package weblogic.jaxrs.monitoring.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceMethod.JaxrsType;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceMethodRuntimeMBean;
import weblogic.management.runtime.JaxRsSubResourceLocatorRuntimeMBean;
import weblogic.management.runtime.JaxRsUriRuntimeMBean;

class JaxRsUriMBeanImpl extends JaxRsMonitoringInfoMBeanImpl implements JaxRsUriRuntimeMBean {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugRestJersey2Integration");
   private static final int SUBNAME_CACHE_MAXIMUM_SIZE = 64;
   private static final int SUBNAME_CACHE_AGE_MINUTES = 60;
   private final String path;
   private final boolean byUriMBean;
   private final boolean extended;
   private final boolean extendedEnabled;
   private final JaxRsExecutionStatisticsMBeanImpl requestStats;
   private final JaxRsExecutionStatisticsMBeanImpl methodStats;
   private final Map methods;
   private final Map locators;
   private final LoadingCache subNameCache;

   public JaxRsUriMBeanImpl(String name, JaxRsApplicationRuntimeMBean parent, String path, ResourceStatistics stats, boolean extended, boolean extendedEnabled) throws ManagementException {
      this(name, parent, path, stats, true, extended, extendedEnabled);
   }

   public JaxRsUriMBeanImpl(String name, JaxRsApplicationRuntimeMBean parent, String path, ResourceStatistics stats, boolean byUriMBean, boolean extended, boolean extendedEnabled) throws ManagementException {
      super(name, parent);
      this.methods = new HashMap();
      this.locators = new HashMap();
      this.path = path;
      this.byUriMBean = byUriMBean;
      this.extended = extended;
      this.extendedEnabled = extendedEnabled;
      this.requestStats = new JaxRsExecutionStatisticsMBeanImpl(name + "_RequestStatistics", this, stats.getRequestExecutionStatistics());
      this.methodStats = new JaxRsExecutionStatisticsMBeanImpl(name + "_MethodsStatistics", this, stats.getResourceMethodExecutionStatistics());
      this.subNameCache = CacheBuilder.newBuilder().maximumSize(64L).expireAfterAccess(60L, TimeUnit.MINUTES).build(new CacheLoader() {
         public String load(ResourceMethod resourceMethod) throws Exception {
            return JaxRsUriMBeanImpl.this.getSubNameImpl(resourceMethod);
         }
      });
      this.update(stats);
   }

   public void update(ResourceStatistics stats) throws ManagementException {
      this.requestStats.update(stats.getRequestExecutionStatistics());
      this.methodStats.update(stats.getResourceMethodExecutionStatistics());
      Iterator var2 = stats.getResourceMethodStatistics().entrySet().iterator();

      while(true) {
         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            ResourceMethod resourceMethod = (ResourceMethod)entry.getKey();
            String subName = this.getSubName(resourceMethod);
            boolean extendedMethod = resourceMethod.isExtended();
            if (resourceMethod.getType() == JaxrsType.SUB_RESOURCE_LOCATOR) {
               if ((this.extendedEnabled || !extendedMethod) && !this.locators.containsKey(subName)) {
                  this.locators.put(subName, new JaxRsSubResourceLocatorMBeanImpl(subName, this, resourceMethod, (ResourceMethodStatistics)entry.getValue(), !this.byUriMBean, extendedMethod));
               }

               JaxRsSubResourceLocatorMBeanImpl mbean = (JaxRsSubResourceLocatorMBeanImpl)this.locators.get(subName);
               if (mbean != null) {
                  mbean.update((ResourceMethodStatistics)entry.getValue());
               }
            } else {
               if ((this.extendedEnabled || !extendedMethod) && !this.methods.containsKey(subName)) {
                  this.methods.put(subName, new JaxRsResourceMethodMBeanImpl(subName, this, resourceMethod, (ResourceMethodStatistics)entry.getValue(), !this.byUriMBean, extendedMethod));
               }

               JaxRsResourceMethodMBeanImpl mbean = (JaxRsResourceMethodMBeanImpl)this.methods.get(subName);
               if (mbean != null) {
                  mbean.update((ResourceMethodStatistics)entry.getValue());
               }
            }
         }

         return;
      }
   }

   public static String getFullPath(ResourceMethod resourceMethod) {
      StringBuilder fullPath = new StringBuilder();
      if (resourceMethod != null) {
         prefixPath(fullPath, resourceMethod.getParent());
      }

      return fullPath.toString();
   }

   private static void prefixPath(StringBuilder fullPath, Resource parent) {
      if (parent != null) {
         String path = parent.getPath();
         if (path != null) {
            if (path.startsWith("/")) {
               path = path.substring(1);
            }

            fullPath.insert(0, "/" + path);
         }

         prefixPath(fullPath, parent.getParent());
      }

   }

   private String getSubName(ResourceMethod resourceMethod) {
      try {
         return (String)this.subNameCache.get(resourceMethod);
      } catch (ExecutionException var3) {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Loading subName from cache failed for '" + resourceMethod + "' resource method.", var3);
         }

         return this.getSubNameImpl(resourceMethod);
      }
   }

   private String getSubNameImpl(ResourceMethod resourceMethod) {
      Invocable invocable = resourceMethod.getInvocable();
      String fqClassName = invocable.getHandler().getHandlerClass().getName();
      String javaMethodName = invocable.getHandlingMethod().getName();
      String fPath = getFullPath(resourceMethod);
      String httpMethod = resourceMethod.getHttpMethod();
      StringBuilder subName = (new StringBuilder(this.name)).append("_");
      if (this.byUriMBean) {
         subName.append(fPath).append("_").append(httpMethod).append("_").append(fqClassName).append("_").append(javaMethodName);
      } else {
         subName.append(fqClassName).append("_").append(javaMethodName).append("_").append(fPath).append("_").append(httpMethod);
      }

      return subName.toString();
   }

   public String getPath() {
      return this.path;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public JaxRsExecutionStatisticsRuntimeMBean getMethodsStatistics() {
      return this.methodStats;
   }

   public JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics() {
      return this.requestStats;
   }

   public JaxRsResourceMethodRuntimeMBean[] getResourceMethods() {
      return (JaxRsResourceMethodRuntimeMBean[])this.methods.values().toArray(new JaxRsResourceMethodRuntimeMBean[this.methods.size()]);
   }

   public JaxRsResourceMethodRuntimeMBean lookupResourceMethods(String name) {
      return (JaxRsResourceMethodRuntimeMBean)this.methods.get(name);
   }

   public JaxRsSubResourceLocatorRuntimeMBean[] getSubResourceLocators() {
      return (JaxRsSubResourceLocatorRuntimeMBean[])this.locators.values().toArray(new JaxRsSubResourceLocatorRuntimeMBean[this.locators.size()]);
   }

   public JaxRsSubResourceLocatorRuntimeMBean lookupSubResourceLocators(String name) {
      return (JaxRsSubResourceLocatorRuntimeMBean)this.locators.get(name);
   }
}
