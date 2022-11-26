package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleContext;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleOperationType;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.LifecycleTask;
import com.oracle.weblogic.lifecycle.PartitionPlugin;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.config.Association;
import com.oracle.weblogic.lifecycle.config.Associations;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.PartitionRef;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.ListPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertiesPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;

@Service
@PerLookup
public class EnvironmentImpl implements Environment {
   String name;
   @Inject
   private LifecycleConfigFactory lifecycleConfigFactory;
   @Inject
   private RuntimeManager runtimeManager;
   @Inject
   private LifecyclePluginFactory lifecyclePluginFactory;

   public EnvironmentImpl() {
   }

   public EnvironmentImpl(String name) {
      this.name = name;
   }

   public EnvironmentImpl(String name, LifecycleConfigFactory lifecycleConfigFactory) {
      this.name = name;
      this.lifecycleConfigFactory = lifecycleConfigFactory;
   }

   public String getName() {
      return this.name;
   }

   void setName(String name) {
      this.name = name;
   }

   public void addPartition(LifecyclePartition partition) {
      Objects.requireNonNull(partition);
      String runtimeName = partition.getRuntimeName();
      String partitionName = partition.getPartitionName();
      Objects.requireNonNull(runtimeName);
      Objects.requireNonNull(partitionName);
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtime = lifecycleConfig.getRuntimes().getRuntimeByName(runtimeName);
      Partition runtimePartition = runtime.getPartitionByName(partitionName);
      com.oracle.weblogic.lifecycle.config.Environment config = this.getEnvironmentConfig();
      Objects.requireNonNull(runtimePartition);
      Objects.requireNonNull(config);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Adding partition " + runtimeName + "/" + partition.getPartitionName() + " to environment " + this.getName());
      }

      Properties props = new Properties();
      config.createPartitionRef(runtimePartition, props);
   }

   private Environments getEnvironmentConfigs() {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Environments environmentConfigs = lifecycleConfig.getEnvironments();
      return environmentConfigs;
   }

   private com.oracle.weblogic.lifecycle.config.Environment getEnvironmentConfig() {
      Environments environmentConfigs = this.getEnvironmentConfigs();
      if (environmentConfigs != null) {
         com.oracle.weblogic.lifecycle.config.Environment environmentConfig = environmentConfigs.getEnvironmentByName(this.getName());
         return environmentConfig;
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Env Config " + this.getName() + " is NULL");
         }

         return null;
      }
   }

   private PartitionRef getPartition(String partitionType, String partitionName) {
      com.oracle.weblogic.lifecycle.config.Environment environmentConfig = this.getEnvironmentConfig();
      return environmentConfig.getPartitionRefByTypeAndName(partitionType, partitionName);
   }

   public void removePartition(String partitionType, String partitionName) {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Removing partition " + partitionName + " of type " + partitionType + " from environment " + this.getName());
      }

      PartitionRef partitionRef = this.getPartition(partitionType, partitionName);
      this.getEnvironmentConfig().deletePartitionRef(partitionRef);
   }

   public List getPartitions() {
      List partitions = new ArrayList();
      return partitions;
   }

   public List getPartitions(String partitionType) {
      List partitions = new ArrayList();
      return partitions;
   }

   private LifecyclePartition getPartitionsFromConfig(Partition config) {
      String id = config.getId();
      String name = config.getName();
      String type = config.getRuntime().getType();
      String runtime = config.getRuntime().getName();
      List props = config.getProperty();
      Map properties = new HashMap();
      Iterator list = props.iterator();

      while(list.hasNext()) {
         PropertyBean prop = (PropertyBean)list.next();
         String key = prop.getName();
         String value = prop.getValue();
         properties.put(key, PropertyValueFactory.getStringOrConfidentialPropertyValue(value));
      }

      LifecyclePartition partition = new LifecyclePartitionImpl(id, name, type, runtime, properties);
      return partition;
   }

   public void associate(LifecyclePartition p1, LifecyclePartition p2, Map partitionProperties) throws LifecycleException {
      Objects.requireNonNull(p1, "Partition1 is null");
      Objects.requireNonNull(p2, "Partition2 is null");
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("EnvironmentImpl.associate Partition  1 Name " + p1.getPartitionName() + " ,Id " + p1.getId() + " ,Type " + p1.getPartitionType());
         LifecycleUtils.debug("EnvironmentImpl.associate Partition 2 Name " + p1.getPartitionName() + " ,Id " + p1.getId() + " ,Type " + p1.getPartitionType());
      }

      buildPropertyMap(p1, partitionProperties);
      buildPropertyMap(p2, partitionProperties);
      LifecyclePartition partition1;
      LifecyclePartition partition2;
      String partition1Type;
      String partition2Type;
      if (!"otd".equalsIgnoreCase(p1.getPartitionType()) && !"database".equalsIgnoreCase(p2.getPartitionType())) {
         partition1 = p1;
         partition2 = p2;
         partition1Type = p1.getPartitionType();
         partition2Type = p2.getPartitionType();
      } else {
         partition1 = p2;
         partition2 = p1;
         partition1Type = p2.getPartitionType();
         partition2Type = p1.getPartitionType();
      }

      LifecycleContext partitionCtx = new LifecycleContextImpl(partitionProperties);
      LifecycleRuntime runtimeObject1 = null;
      String runtime1 = partition1.getRuntimeName();
      if (runtime1 != null) {
         runtimeObject1 = this.runtimeManager.getRuntime(runtime1);
      }

      LifecycleRuntime runtimeObject2 = null;
      String runtime2 = partition2.getRuntimeName();
      if (runtime2 != null) {
         runtimeObject2 = this.runtimeManager.getRuntime(runtime2);
      }

      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Associating " + partition1.getRuntimeName() + "/" + partition1.getPartitionName() + " with " + partition2.getRuntimeName() + "/" + partition2.getPartitionName());
      }

      if (!partition1.getPartitionName().equals("DOMAIN") && !partition2.getPartitionName().equals("DOMAIN")) {
         PartitionPlugin plugin1 = this.getPlugin(partition1Type);
         plugin1.associate(partitionCtx, partition1, partition2, runtimeObject1);
         PartitionPlugin plugin2 = this.getPlugin(partition2Type);
         plugin2.associate(partitionCtx, partition2, partition1, runtimeObject2);
      }

      com.oracle.weblogic.lifecycle.config.Environment environment = this.getEnvironmentConfig();
      environment.createAssociation(partition1.getId(), partition2.getId());
   }

   public void dissociate(LifecyclePartition partition1, LifecyclePartition partition2, Map partitionProperties) throws LifecycleException {
      Objects.requireNonNull(partition1, "Partition1 is null");
      Objects.requireNonNull(partition2, "Partition2 is null");
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("EnvironmentImpl.dissociate Partition  1 Name " + partition1.getPartitionName() + " ,Id " + partition1.getId() + " ,Type " + partition1.getPartitionType());
         LifecycleUtils.debug("EnvironmentImpl.dissociate Partition 2 Name " + partition2.getPartitionName() + " ,Id " + partition2.getId() + " ,Type " + partition2.getPartitionType());
      }

      buildPropertyMap(partition1, partitionProperties);
      buildPropertyMap(partition2, partitionProperties);
      String partition1Type = partition1.getPartitionType();
      String partition2Type = partition2.getPartitionType();
      LifecycleContext partitionCtx = new LifecycleContextImpl(partitionProperties);
      LifecycleRuntime runtimeObject1 = null;
      String runtime1 = partition1.getRuntimeName();
      if (runtime1 != null) {
         runtimeObject1 = this.runtimeManager.getRuntime(runtime1);
      }

      LifecycleRuntime runtimeObject2 = null;
      String runtime2 = partition2.getRuntimeName();
      if (runtime2 != null) {
         runtimeObject2 = this.runtimeManager.getRuntime(runtime2);
      }

      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Dissociating " + partition1.getRuntimeName() + "/" + partition1.getPartitionName() + " with " + partition2.getRuntimeName() + "/" + partition2.getPartitionName());
      }

      if (!partition1.getPartitionName().equals("DOMAIN") && !partition2.getPartitionName().equals("DOMAIN")) {
         PartitionPlugin plugin1 = this.getPlugin(partition1Type);
         plugin1.dissociate(partitionCtx, partition1, partition2, runtimeObject1);
         PartitionPlugin plugin2 = this.getPlugin(partition2Type);
         plugin2.dissociate(partitionCtx, partition2, partition1, runtimeObject2);
      }

      com.oracle.weblogic.lifecycle.config.Environment environment = this.getEnvironmentConfig();
      environment.removeAssociation(partition1.getId(), partition2.getId());
   }

   public List getAssociations() throws LifecycleException {
      List lifecycleAssociationList = new ArrayList();
      List associationConfigList = this.getEnvironmentConfig().getAssociations().getAssociations();
      Iterator var3 = associationConfigList.iterator();

      while(var3.hasNext()) {
         Association associationConfig = (Association)var3.next();
         AssociationImpl lifecycleAssociationImpl = new AssociationImpl();
         lifecycleAssociationImpl.setPartition1(this.getPartitionsFromConfig(associationConfig.getPartition1()));
         lifecycleAssociationImpl.setPartition2(this.getPartitionsFromConfig(associationConfig.getPartition2()));
         lifecycleAssociationList.add(lifecycleAssociationImpl);
      }

      return lifecycleAssociationList;
   }

   public void migratePartition(LifecyclePartition lifecyclePartition, LifecycleRuntime runtime, String phase, Map properties) throws LifecycleException {
      Objects.requireNonNull(lifecyclePartition, "Partition is null");
      String partitionId = lifecyclePartition.getId();
      LifecycleContext ctx = new LifecycleContextImpl(properties);
      PartitionPlugin plugin = this.getPlugin(lifecyclePartition.getPartitionType());
      String runtimeName = lifecyclePartition.getRuntimeName();
      LifecycleRuntime runtimeObject = null;
      if (runtimeName != null) {
         runtimeObject = this.runtimeManager.getRuntime(runtimeName);
      }

      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Migrating partition " + runtimeName + "/" + lifecyclePartition.getPartitionName() + " to " + runtime.getRuntimeName());
      }

      plugin.migrate(lifecyclePartition.getPartitionName(), phase, ctx, runtimeObject);
      Set partitionSet = this.getAssociatedPartitions(lifecyclePartition);
      Iterator iterator = partitionSet.iterator();
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Associated partition set size=" + partitionSet.size());
      }

      while(iterator.hasNext()) {
         LifecyclePartition partition = (LifecyclePartition)iterator.next();
         if (!partition.getId().equals(partitionId)) {
            plugin = this.lifecyclePluginFactory.getService(partition.getPartitionType());
            runtimeName = partition.getRuntimeName();
            runtimeObject = null;
            if (runtimeName != null) {
               runtimeObject = this.runtimeManager.getRuntime(runtimeName);
            }

            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Migrating associated partition " + runtimeName + "/" + partition.getPartitionName());
            }

            plugin.migrate(partition.getPartitionName(), phase, ctx, runtimeObject);
         }
      }

   }

   Set getAssociatedPartitions(LifecyclePartition lifecyclePartition) {
      com.oracle.weblogic.lifecycle.config.Environment environment = this.getEnvironmentConfig();
      Associations associations = environment.getAssociations();
      List associationList = associations.getAssociations();
      Iterator associationIterator = associationList.iterator();
      Set partitionHashSet = new HashSet();

      while(associationIterator.hasNext()) {
         Association association = (Association)associationIterator.next();
         if (association.getPartition1().getName().equals(lifecyclePartition.getPartitionName())) {
            partitionHashSet.add(this.getPartitionsFromConfig(association.getPartition2()));
         }

         if (association.getPartition2().getName().equals(lifecyclePartition.getPartitionName())) {
            partitionHashSet.add(this.getPartitionsFromConfig(association.getPartition1()));
         }
      }

      return partitionHashSet;
   }

   public Map quiesce(LifecyclePartition lifecyclePartition, String phase, Map properties) throws LifecycleException {
      Objects.requireNonNull(lifecyclePartition, "Partition is null");
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Quiscing partition " + lifecyclePartition.getPartitionName() + (properties != null && properties.containsKey("source") ? ", source = " + properties.get("source") : ""));
      }

      return this.performOperation(EnvironmentImpl.EnvironmentOperation.QUIESCE, lifecyclePartition, phase, properties);
   }

   public Map start(LifecyclePartition lifecyclePartition, String phase, Map properties) throws LifecycleException {
      Objects.requireNonNull(lifecyclePartition, "Partition is null");
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Starting partition " + lifecyclePartition.getPartitionName() + (properties != null && properties.containsKey("source") ? ", source = " + properties.get("source") : ""));
      }

      return this.performOperation(EnvironmentImpl.EnvironmentOperation.START, lifecyclePartition, phase, properties);
   }

   public Map restart(LifecyclePartition lifecyclePartition, String phase, Map properties) throws LifecycleException {
      Objects.requireNonNull(lifecyclePartition, "Partition is null");
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Restarting partition " + lifecyclePartition.getPartitionName() + (properties != null && properties.containsKey("source") ? ", source = " + properties.get("source") : ""));
      }

      return this.performOperation(EnvironmentImpl.EnvironmentOperation.RESTART, lifecyclePartition, phase, properties);
   }

   private List performOperation(EnvironmentOperation operation, PartitionPlugin plugin, LifecyclePartition lifecyclePartition, String phase, LifecycleContext ctx, LifecycleRuntime runtime) throws LifecycleException {
      if (operation == null) {
         throw new IllegalArgumentException(CatalogUtils.getMsgInvalidEnvironmentOp((String)null, this.getName()));
      } else {
         switch (operation) {
            case QUIESCE:
               return plugin.quiesce(lifecyclePartition.getPartitionName(), phase, ctx, runtime);
            case START:
               return plugin.start(lifecyclePartition.getPartitionName(), phase, ctx, runtime);
            case RESTART:
               return plugin.restart(lifecyclePartition.getPartitionName(), phase, ctx, runtime);
            default:
               throw new IllegalArgumentException(CatalogUtils.getMsgInvalidEnvironmentOp(operation.toString(), this.getName()));
         }
      }
   }

   private Map performOperation(EnvironmentOperation operation, LifecyclePartition lifecyclePartition, String phase, Map properties) throws LifecycleException {
      String partitionId = lifecyclePartition.getId();
      LifecycleContext ctx = new LifecycleContextImpl(properties);
      PartitionPlugin plugin = this.getPlugin(lifecyclePartition.getPartitionType());
      Map map = new HashMap();
      String runtimeName = lifecyclePartition.getRuntimeName();
      if (runtimeName != null) {
         LifecycleTask wlsTask = null;
         LifecycleRuntime wlsRuntime = null;
         LifecycleRuntime runtimeObject = this.runtimeManager.getRuntime(runtimeName);
         List result = this.performOperation(operation, plugin, lifecyclePartition, phase, ctx, runtimeObject);
         if (result != null && result.size() > 0) {
            map.put(lifecyclePartition.getId(), result);
            if (lifecyclePartition.getPartitionType().equalsIgnoreCase("wls")) {
               wlsRuntime = runtimeObject;
               wlsTask = (LifecycleTask)result.get(0);
            }
         }

         Set partitionSet = this.getAssociatedPartitions(lifecyclePartition);
         String source = (String)properties.get("source");
         boolean isSourceWLS = Boolean.valueOf(source);
         if (!isSourceWLS && wlsTask != null && operation == EnvironmentImpl.EnvironmentOperation.START) {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Map taskPollProperties = new HashMap();
            taskPollProperties.put("operationtype", LifecycleOperationType.START_PARTITION);
            taskPollProperties.put("phase", phase);
            taskPollProperties.put("partitionSet", partitionSet);
            taskPollProperties.put("originatingPartition", lifecyclePartition);
            Runnable task1 = new LifecycleTaskPollThread(this.lifecyclePluginFactory, wlsRuntime, wlsTask, ctx, taskPollProperties, this.runtimeManager);
            executor.execute(task1);
            executor.shutdown();
         } else {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Not a WLS partition started, start associated partitions right now");
            }

            Iterator iterator = partitionSet.iterator();

            while(iterator.hasNext()) {
               LifecyclePartition partition = (LifecyclePartition)iterator.next();
               if (!partition.getId().equals(partitionId)) {
                  plugin = this.lifecyclePluginFactory.getService(partition.getPartitionType());
                  runtimeName = partition.getRuntimeName();
                  runtimeObject = null;
                  if (runtimeName != null) {
                     runtimeObject = this.runtimeManager.getRuntime(runtimeName);
                  }

                  result = this.performOperation(operation, plugin, partition, phase, ctx, runtimeObject);
                  if (result != null) {
                     map.put(partition.getId(), result);
                  }
               }
            }
         }
      }

      return map;
   }

   private PartitionPlugin getPlugin(String type) throws LifecycleException {
      PartitionPlugin plugin = this.lifecyclePluginFactory.getService(type);
      if (plugin == null) {
         throw new LifecycleException(CatalogUtils.getMsgPartitionPluginNotFound(type));
      } else {
         return plugin;
      }
   }

   private static Map buildPropertyMap(LifecyclePartition partition, Map map) {
      if (partition == null) {
         return map;
      } else {
         Map properties = partition.getPartitionProperties();
         if (properties != null) {
            buildPropertyMap(properties, map);
         }

         return map;
      }
   }

   private static void buildPropertyMap(Map properties, Map resultMap) {
      Iterator var2 = properties.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry propertyEntry = (Map.Entry)var2.next();
         PropertyValue propValue = (PropertyValue)propertyEntry.getValue();
         String propName = (String)propertyEntry.getKey();
         String strValue;
         if (propValue instanceof StringPropertyValue) {
            strValue = ((StringPropertyValue)propValue).getValue();
            resultMap.put(propName, strValue);
         } else if (propValue instanceof ConfidentialPropertyValue) {
            strValue = ((ConfidentialPropertyValue)propValue).getValue();
            resultMap.put(propName, strValue);
         } else if (propValue instanceof PropertiesPropertyValue) {
            Map mapValue = ((PropertiesPropertyValue)propValue).getValue();
            Map nestedProperties = new TreeMap();
            resultMap.put(propName, nestedProperties);
            buildPropertyMap((Map)mapValue, nestedProperties);
         } else if (propValue instanceof ListPropertyValue) {
            List listValue = ((ListPropertyValue)propValue).getValue();
            List resultList = new ArrayList();
            buildPropertyList(listValue, resultList);
            resultMap.put(propName, resultList);
         } else {
            resultMap.put(propertyEntry.getKey(), propValue);
         }
      }

   }

   private static void buildPropertyList(List properties, List resultList) {
      Iterator var2 = properties.iterator();

      while(var2.hasNext()) {
         PropertyValue pv = (PropertyValue)var2.next();
         String strValue;
         if (pv instanceof StringPropertyValue) {
            strValue = ((StringPropertyValue)pv).getValue();
            resultList.add(strValue);
         } else if (pv instanceof ConfidentialPropertyValue) {
            strValue = ((ConfidentialPropertyValue)pv).getValue();
            resultList.add(strValue);
         } else if (pv instanceof PropertiesPropertyValue) {
            Map pv2mapValue = ((PropertiesPropertyValue)pv).getValue();
            Map nestedMap = new TreeMap();
            resultList.add(nestedMap);
            buildPropertyMap((Map)pv2mapValue, nestedMap);
         } else if (pv instanceof ListPropertyValue) {
            List listValue = ((ListPropertyValue)pv).getValue();
            List nestedList = new ArrayList();
            buildPropertyList(listValue, nestedList);
         }
      }

   }

   private static enum EnvironmentOperation {
      QUIESCE,
      START,
      RESTART;
   }
}
