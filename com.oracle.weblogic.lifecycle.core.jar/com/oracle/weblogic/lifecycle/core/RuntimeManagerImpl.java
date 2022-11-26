package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleContext;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.LifecycleOperationType;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.LifecycleTask;
import com.oracle.weblogic.lifecycle.QuiesceContextPlugin;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.RuntimePlugin;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.utils.LocatorUtilities;

@Service
public class RuntimeManagerImpl implements RuntimeManager {
   @Inject
   private LifecycleManager lifecycleManager;
   @Inject
   private LifecycleConfigFactory lifecycleConfigFactory;
   @Inject
   private LifecyclePluginFactory lifecyclePluginFactory;
   @Inject
   private ServiceLocator locator;
   public static final String GRPS = "groups";
   public static final String CLSTR_NAME = "clusterName";
   public static final String PROTOCOL = "protocol";

   public LifecycleRuntime createRuntime(String runtimeType, String runtimeName, Map mapProperties) throws LifecycleException {
      Objects.requireNonNull(runtimeType);
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(mapProperties);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Creating runtime " + runtimeName + " of type " + runtimeType);
      }

      Properties properties = new Properties();
      if (mapProperties.containsKey((Object)null)) {
         mapProperties.remove((Object)null);
      }

      Iterator var5 = mapProperties.keySet().iterator();

      String key;
      String value;
      while(var5.hasNext()) {
         key = (String)var5.next();
         if (key != null) {
            PropertyValue propertyValue = (PropertyValue)mapProperties.get(key);
            if (key.equalsIgnoreCase("hostname")) {
               key = key.toLowerCase();
            }

            if (propertyValue instanceof StringPropertyValue) {
               value = ((StringPropertyValue)propertyValue).getValue();
            } else if (propertyValue instanceof ConfidentialPropertyValue) {
               value = ((ConfidentialPropertyValue)propertyValue).getValue();
            } else {
               value = propertyValue.toString();
            }

            properties.setProperty(key, value);
         }
      }

      properties.put("name", runtimeName);
      mapProperties.put("name", PropertyValueFactory.getStringPropertyValue(runtimeName));
      properties.put("type", runtimeType);
      mapProperties.put("type", PropertyValueFactory.getStringPropertyValue(runtimeType));
      List runtimes = this.getRuntimes(runtimeType);
      key = properties.getProperty("hostname", "");
      value = properties.getProperty("port", "");
      Iterator var15 = runtimes.iterator();

      LifecycleRuntime runtime;
      while(var15.hasNext()) {
         runtime = (LifecycleRuntime)var15.next();
         if (runtimeType.equalsIgnoreCase("wls") && key.equals(runtime.getRuntimeProperties().getProperty("hostname")) && value.equals(runtime.getRuntimeProperties().getProperty("port"))) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimeWithHostPortExists(key, value));
         }
      }

      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();

      try {
         Runtime var17 = config.getRuntimes().createRuntime(mapProperties);
      } catch (Exception var12) {
         String msg = var12.getMessage();
         if (msg == null) {
            Throwable cause = var12.getCause();
            msg = cause.getMessage();
         }

         if (msg.startsWith("Keys cannot be duplicate.")) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Runtime with the same name already exists");
            }

            throw new LifecycleException(CatalogUtils.getMsgRuntimeWithNameExists(runtimeName), var12);
         }

         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Unable to register runtime");
         }

         throw new LifecycleException(CatalogUtils.getMsgExceptionCreatingRuntime(runtimeName), var12);
      }

      runtime = this.createRuntime(runtimeType, runtimeName, properties);

      try {
         LifecyclePartition globalPartition;
         if (runtimeType.equalsIgnoreCase("wls")) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Creating global wls partition");
            }

            globalPartition = runtime.createPartition(this.getGlobalPartitionName(runtimeName), new HashMap());
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Associating global wls partition with otd");
            }

            this.addAndAssociateGlobalPartitions(globalPartition, "otd");
         }

         if (runtimeType.equalsIgnoreCase("otd")) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Creating global otd partition with otd");
            }

            globalPartition = runtime.createPartition(this.getGlobalPartitionName(runtimeName), new HashMap());
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Associating global otd partition with wls");
            }

            this.addAndAssociateGlobalPartitions(globalPartition, "wls");
         }
      } catch (Exception var13) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Error creating global partitions");
         }
      }

      if (runtimeType.equalsIgnoreCase("wls")) {
         this.runtimeCreate(runtimeName, runtimeType, properties);
      }

      return runtime;
   }

   private void runtimeCreate(String runtimeName, String runtimeType, Properties properties) {
      LifecycleContext context = new LifecycleContextImpl(properties);
      RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
      if (plugin != null) {
         try {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("runtimeCreate calling Plugin for type= " + runtimeType + "and runtimeName=" + runtimeName);
            }

            plugin.create(runtimeName, context);
         } catch (Exception var7) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Caught Exception when calling RuntimePlugin create for runtime= " + runtimeName, var7);
            }
         }
      }

   }

   private String getGlobalPartitionName(String runtimeName) {
      return runtimeName + "-" + "DOMAIN";
   }

   private void addAndAssociateGlobalPartitions(LifecyclePartition globalPartition, String associateRuntimeType) throws LifecycleException {
      List runtimes = this.getRuntimes(associateRuntimeType);
      Environment env;
      if (runtimes.isEmpty()) {
         Random random = new Random();
         env = this.lifecycleManager.createEnvironment("DOMAIN" + random.nextInt());
         env.addPartition(globalPartition);
      } else {
         Iterator var9 = runtimes.iterator();

         while(var9.hasNext()) {
            LifecycleRuntime lr = (LifecycleRuntime)var9.next();
            env = this.lifecycleManager.getEnvironment(lr.getRuntimeType() + lr.getRuntimeName() + "0", lr.getRuntimeName());
            LifecyclePartition otdGlobalPartition = lr.getPartition(this.getGlobalPartitionName(lr.getRuntimeName()));
            if (otdGlobalPartition != null) {
               if (env == null) {
                  Random random = new Random();
                  env = this.lifecycleManager.createEnvironment("DOMAIN" + random.nextInt());
                  env.addPartition(otdGlobalPartition);
               }

               env.addPartition(globalPartition);
            }
         }
      }

   }

   public void deleteRuntime(String runtimeType, String runtimeName) throws LifecycleException {
      Objects.requireNonNull(runtimeType);
      Objects.requireNonNull(runtimeName);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Deleting runtime " + runtimeName + " of type " + runtimeType);
      }

      LifecycleRuntime lifecycleRuntime = this.getRuntime(runtimeName);

      try {
         if (lifecycleRuntime != null && runtimeType != null && (runtimeType.equals("wls") || runtimeType.equals("otd"))) {
            LifecyclePartition lifecyclePartition = lifecycleRuntime.getPartition(this.getGlobalPartitionName(lifecycleRuntime.getRuntimeName()));
            if (lifecyclePartition != null) {
               Map mapProperties = new HashMap();
               mapProperties.put("isDeleteRuntime", PropertyValueFactory.getStringPropertyValue("isDeleteRuntime"));
               lifecycleRuntime.deletePartition(this.getGlobalPartitionName(runtimeName), mapProperties);
            }
         }
      } catch (Exception var9) {
         throw new LifecycleException(CatalogUtils.getMsgExceptionDeletingRuntime(runtimeName), var9);
      }

      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtimes runtimes = config.getRuntimes();
      if (runtimes == null) {
         throw new LifecycleException(CatalogUtils.getMsgRuntimesNotFound());
      } else {
         Runtime rtc = runtimes.getRuntimeByName(runtimeName);
         if (rtc == null) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimeNotFound(runtimeName));
         } else {
            try {
               runtimes.deleteRuntime(rtc);
            } catch (UndeclaredThrowableException var8) {
               throw new LifecycleException(CatalogUtils.getMsgExceptionDeletingRuntime(runtimeName), var8);
            }
         }
      }
   }

   public LifecycleTask startRuntime(@NotNull String runtimeType, @NotNull String runtimeName, String phase, String source, @NotNull Map properties) throws LifecycleException {
      if (!runtimeType.equalsIgnoreCase("wls")) {
         throw new IllegalArgumentException(CatalogUtils.getMsgOpNotAllowedForRuntime(LifecycleOperationType.START_RUNTIME.toString()));
      } else {
         LifecycleRuntime runtime = this.getRuntime(runtimeName);
         properties.put("source", source);
         if (runtime == null) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimeNotFound(runtimeName));
         } else {
            RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
            if (plugin == null) {
               throw new LifecycleException(CatalogUtils.getMsgRuntimePluginNotFound(runtimeType));
            } else {
               LifecycleContext context = new LifecycleContextImpl(properties);
               LifecycleTask task = plugin.start(runtime.getRuntimeName(), phase, context);
               List runtimeList = this.getAllRegisteredRuntimes("otd");
               if (runtimeList == null) {
                  throw new IllegalStateException("Should never happen");
               } else {
                  if (runtimeList.size() == 0 && LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("No OTD registered with Lifecycle module");
                  }

                  if (!this.isSourceWLS(source)) {
                     this.startWlsInstance(phase, runtime, context, task, runtimeList);
                  } else {
                     RuntimePlugin otdplugin = this.lifecyclePluginFactory.getRuntimePlugin("otd");
                     if (otdplugin != null && properties.get("address") != null) {
                        Iterator var12 = runtimeList.iterator();

                        while(var12.hasNext()) {
                           LifecycleRuntime lifecycleRuntime = (LifecycleRuntime)var12.next();
                           otdplugin.scaleUp(lifecycleRuntime.getRuntimeName(), 1, this.createScaleDownContext(context));
                           otdplugin.start(lifecycleRuntime.getRuntimeName(), phase, context);
                        }
                     }
                  }

                  return task;
               }
            }
         }
      }
   }

   private void startWlsInstance(String phase, LifecycleRuntime runtime, LifecycleContext context, LifecycleTask task, List runtimeList) {
      ExecutorService executor = Executors.newFixedThreadPool(1);
      Map taskPollProperties = new HashMap();
      taskPollProperties.put("operationtype", LifecycleOperationType.START_RUNTIME);
      taskPollProperties.put("phase", phase);
      taskPollProperties.put("otdlist", runtimeList);
      Runnable task1 = new LifecycleTaskPollThread(this.lifecyclePluginFactory, runtime, task, context, taskPollProperties, this);
      executor.execute(task1);
      executor.shutdown();
   }

   private boolean isSourceWLS(String source) {
      return source != null && source.equalsIgnoreCase("wls");
   }

   public LifecycleRuntime updateRuntime(String runtimeType, String runtimeName, Map mapProperties) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      Objects.requireNonNull(mapProperties);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Updating runtime " + runtimeName + " of type " + runtimeType);
      }

      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtimes runtimes = config.getRuntimes();
      if (runtimes == null) {
         throw new LifecycleException(CatalogUtils.getMsgRuntimesNotFound());
      } else {
         Runtime rtc = runtimes.getRuntimeByName(runtimeName);
         if (rtc != null) {
            RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
            if (plugin != null) {
               plugin.update(runtimeName, new LifecycleContextImpl(mapProperties));
            }

            rtc.update(mapProperties);
            return this.getRuntime(runtimeName);
         } else {
            throw new LifecycleException(CatalogUtils.getMsgRuntimeNotFound(runtimeName));
         }
      }
   }

   public LifecycleTask quiesceRuntime(String runtimeType, String runtimeName, String phase, String source, Map properties) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      Objects.requireNonNull(properties);
      if (!runtimeType.equalsIgnoreCase("wls")) {
         throw new IllegalArgumentException(CatalogUtils.getMsgOpNotAllowedForRuntime(LifecycleOperationType.QUIESCE_RUNTIME.toString()));
      } else {
         LifecycleRuntime runtime = this.getRuntime(runtimeName);
         if (runtime == null) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimeNotFound(runtimeName));
         } else {
            properties.put("source", source);
            RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
            LifecycleContext context = new LifecycleContextImpl(properties);
            if (plugin == null) {
               throw new LifecycleException(CatalogUtils.getMsgRuntimePluginNotFound(runtimeType));
            } else {
               if (plugin instanceof QuiesceContextPlugin) {
                  ((QuiesceContextPlugin)plugin).updateContext(runtimeName, context);
               }

               RuntimePlugin otdplugin = this.lifecyclePluginFactory.getRuntimePlugin("otd");
               if (otdplugin == null) {
                  throw new LifecycleException(CatalogUtils.getMsgRuntimeNotFound("otd"));
               } else {
                  LifecycleTask task = null;
                  Iterator var11 = this.getAllRegisteredRuntimes("otd").iterator();

                  while(var11.hasNext()) {
                     LifecycleRuntime lifecycleRuntime = (LifecycleRuntime)var11.next();
                     if ("elasticity".equals(source)) {
                        otdplugin.quiesce(lifecycleRuntime.getRuntimeName(), phase, context);
                        task = plugin.quiesce(runtime.getRuntimeName(), phase, context);
                        otdplugin.scaleDown(lifecycleRuntime.getRuntimeName(), 1, this.createScaleDownContext(context));
                     } else {
                        task = plugin.quiesce(runtime.getRuntimeName(), phase, context);
                        otdplugin.quiesce(lifecycleRuntime.getRuntimeName(), phase, context);
                     }
                  }

                  if (task == null) {
                     task = plugin.quiesce(runtime.getRuntimeName(), phase, context);
                  }

                  return task;
               }
            }
         }
      }
   }

   private LifecycleContext createScaleDownContext(LifecycleContext context) {
      Map map = context.getProperties();
      Object ms = map.get("managedserver");
      Map map1 = new HashMap();
      if (ms != null) {
         String serverName = ms.toString();
         ensureClusterName(map, serverName);
         map1.put(serverName, map);
      }

      return new LifecycleContextImpl(map1);
   }

   private static void ensureClusterName(Map serverInfo, String serverName) {
      if (serverInfo != null && !serverInfo.containsKey("clusterName")) {
         RuntimeAccess runtimeAccess = (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);
         DomainMBean domainMBean = runtimeAccess.getDomain();
         ServerMBean serverMBean = domainMBean.lookupServer(serverName);
         if (serverMBean != null) {
            ClusterMBean clusterMBean = serverMBean.getCluster();
            if (clusterMBean != null) {
               serverInfo.put("clusterName", clusterMBean.getName());
            }
         }

      }
   }

   public void registerRuntime(String runtimeType, String runtimeName, Map properties) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Registering runtime " + runtimeName + " of type " + runtimeType);
      }

      this.createRuntime(runtimeType, runtimeName, properties);
   }

   public void unregisterRuntime(String runtimeType, String runtimeName) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Unregistering runtime " + runtimeName + " of type " + runtimeType);
      }

      this.deleteRuntime(runtimeType, runtimeName);
   }

   public LifecycleTask scaleUp(String runtimeType, String runtimeName, int scaleFactor, Map properties) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Scaling up runtime " + runtimeName + " of type " + runtimeType);
      }

      if (!runtimeType.equals("wls")) {
         throw new IllegalArgumentException(CatalogUtils.getMsgOpNotAllowedForRuntime(LifecycleOperationType.SCALE_UP_RUNTIME.toString()));
      } else {
         RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
         if (plugin == null) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimePluginNotFound(runtimeType));
         } else {
            LifecycleTask scalingTask = plugin.scaleUp(runtimeName, scaleFactor, new LifecycleContextImpl(properties));
            this.runScalingTask(runtimeName, scalingTask, LifecycleOperationType.SCALE_UP_RUNTIME);
            if (scalingTask != null && scalingTask.getProperties() != null && LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Completed initiation of scaleUp. Task Information " + scalingTask.getProperties().toString());
            }

            return scalingTask;
         }
      }
   }

   public LifecycleTask scaleDown(String runtimeType, String runtimeName, int scaleFactor, Map properties) throws LifecycleException {
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(runtimeType);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Scaling down runtime " + runtimeName + " of type " + runtimeType);
      }

      if (!runtimeType.equals("wls")) {
         throw new IllegalArgumentException(CatalogUtils.getMsgOpNotAllowedForRuntime(LifecycleOperationType.SCALE_DOWN_RUNTIME.toString()));
      } else {
         RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
         if (plugin == null) {
            throw new LifecycleException(CatalogUtils.getMsgRuntimePluginNotFound(runtimeType));
         } else {
            LifecycleTask scalingTask = plugin.scaleDown(runtimeName, scaleFactor, new LifecycleContextImpl(properties));
            this.runScalingTask(runtimeName, scalingTask, LifecycleOperationType.SCALE_DOWN_RUNTIME);
            if (scalingTask != null && scalingTask.getProperties() != null && LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Completed initiation of scale down. Task Information " + scalingTask.getProperties().toString());
            }

            return scalingTask;
         }
      }
   }

   public LifecycleRuntime selectRuntime(String runtimeType) {
      return null;
   }

   public LifecycleRuntime getRuntime(String runtimeName) {
      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime rtc = config.getRuntimes().getRuntimeByName(runtimeName);
      if (rtc == null) {
         return null;
      } else {
         List props = rtc.getProperty();
         Properties runtimeProperties = new Properties();
         Iterator list = props.iterator();
         this.addHostPort(rtc, runtimeProperties);

         while(list.hasNext()) {
            PropertyBean prop = (PropertyBean)list.next();
            String key = prop.getName();
            String mayBeEncrypted = prop.getValue();
            String value = PropertyValueFactory.getConfidentialPropertyValue(mayBeEncrypted).getValue();
            runtimeProperties.put(key, value);
         }

         LifecycleRuntime runtime = this.createRuntime(rtc.getType(), rtc.getName(), runtimeProperties);
         return runtime;
      }
   }

   public List getRuntimes(String runtimeType) {
      List runtimeList = new ArrayList();
      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      List runtimes = config.getRuntimes().getRuntimes();
      Iterator runtimeIterator = runtimes.iterator();

      while(true) {
         Runtime runtime;
         String runtimeTypeStr;
         do {
            if (!runtimeIterator.hasNext()) {
               return runtimeList;
            }

            runtime = (Runtime)runtimeIterator.next();
            runtimeTypeStr = runtime.getType();
         } while(!runtimeTypeStr.equals(runtimeType));

         List props = runtime.getProperty();
         Properties runtimeProperties = new Properties();
         if (props != null) {
            Iterator list = props.iterator();
            this.addHostPort(runtime, runtimeProperties);

            while(list.hasNext()) {
               PropertyBean prop = (PropertyBean)list.next();
               String key = prop.getName();
               String value = prop.getValue();
               runtimeProperties.put(key, value);
            }
         }

         LifecycleRuntime runtimeObject = this.createRuntime(runtimeTypeStr, runtime.getName(), runtimeProperties);
         runtimeList.add(runtimeObject);
      }
   }

   public List getRuntimes() {
      List runtimeList = new ArrayList();
      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      List runtimes = config.getRuntimes().getRuntimes();
      Iterator runtimeIterator = runtimes.iterator();

      while(runtimeIterator.hasNext()) {
         Runtime runtime = (Runtime)runtimeIterator.next();
         List props = runtime.getProperty();
         Properties runtimeProperties = new Properties();
         if (props != null) {
            Iterator list = props.iterator();
            this.addHostPort(runtime, runtimeProperties);

            while(list.hasNext()) {
               PropertyBean prop = (PropertyBean)list.next();
               String key = prop.getName();
               String value = prop.getValue();
               runtimeProperties.put(key, value);
            }
         }

         LifecycleRuntime runtimeObject = this.createRuntime(runtime.getType(), runtime.getName(), runtimeProperties);
         runtimeList.add(runtimeObject);
      }

      return runtimeList;
   }

   private List getAllRegisteredRuntimes(String runtimeType) {
      LifecycleConfig config = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtimes runtimes = config.getRuntimes();
      List runtimeObjectList = new ArrayList();
      List runtimeList = runtimes.getRuntimes();
      Iterator runtimeIterator = runtimeList.iterator();

      while(runtimeIterator.hasNext()) {
         Runtime runtimeConfig = (Runtime)runtimeIterator.next();
         if (runtimeConfig.getType().trim().equalsIgnoreCase(runtimeType)) {
            runtimeObjectList.add(this.getLifecycleRuntimefromConfig(runtimeConfig));
         }
      }

      return runtimeObjectList;
   }

   private LifecycleRuntime getLifecycleRuntimefromConfig(Runtime runtimeConfig) {
      String runtimeName = runtimeConfig.getName();
      String host = runtimeConfig.getHostname();
      String port = runtimeConfig.getPort();
      String runtimeType = runtimeConfig.getType();
      List propertyList = runtimeConfig.getProperty();
      Iterator propertyIterator = propertyList.iterator();
      Properties runtimeProperties = new Properties();
      this.addHostPort(runtimeConfig, runtimeProperties);

      while(propertyIterator.hasNext()) {
         PropertyBean prop = (PropertyBean)propertyIterator.next();
         String key = prop.getName();
         String value = prop.getValue();
         runtimeProperties.put(key, value);
      }

      LifecycleRuntime runtime = this.createRuntime(runtimeType, runtimeName, runtimeProperties);
      return runtime;
   }

   private LifecycleRuntime createRuntime(String runtimeType, String runtimeName, Properties properties) {
      LifecycleRuntime runtimeObject = new LifecycleRuntimeImpl(runtimeType, runtimeName, properties);
      this.locator.inject(runtimeObject);
      return runtimeObject;
   }

   private void runScalingTask(String runtimeName, LifecycleTask scalingTask, LifecycleOperationType operationType) {
      List otdRuntimes = this.getAllRegisteredRuntimes("otd");
      LifecycleRuntime runtime = this.getRuntime(runtimeName);
      ExecutorService executor = Executors.newFixedThreadPool(1);
      LifecycleContext context = new LifecycleContextImpl(runtime.getRuntimeProperties());
      Map taskPollProperties = new HashMap();
      taskPollProperties.put("operationtype", operationType);
      taskPollProperties.put("otdlist", otdRuntimes);
      Runnable task1 = new LifecycleTaskPollThread(this.lifecyclePluginFactory, runtime, scalingTask, context, taskPollProperties, (RuntimeManager)null);
      executor.execute(task1);
      executor.shutdown();
   }

   private void addHostPort(Runtime runtime, Properties props) {
      String hostName = runtime.getHostname();
      String port = runtime.getPort();
      String protocol = runtime.getProtocol();
      if (hostName != null) {
         props.put("hostname", hostName);
      }

      if (port != null) {
         props.put("port", port);
      }

      if (protocol != null) {
         props.put("protocol", protocol);
      }

   }
}
