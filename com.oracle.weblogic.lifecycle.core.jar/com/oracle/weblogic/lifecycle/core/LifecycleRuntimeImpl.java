package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.LifecycleOperationType;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.PartitionPlugin;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.RuntimePlugin;
import com.oracle.weblogic.lifecycle.config.Associations;
import com.oracle.weblogic.lifecycle.config.Environment;
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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.inject.Inject;

public class LifecycleRuntimeImpl implements LifecycleRuntime {
   private String name;
   private String runtimeType;
   String globalPartitionName;
   @Inject
   private LifecyclePluginFactory lifecyclePluginFactory;
   @Inject
   private LifecycleConfigFactory lifecycleConfigFactory;
   @Inject
   private LifecycleManager lifecycleManager;
   @Inject
   private RuntimeManager runtimeManager;
   private Properties properties;

   public LifecycleRuntimeImpl(String runtimeType, String runtimeName, Properties properties) {
      this.name = runtimeName;
      this.runtimeType = runtimeType;
      this.properties = properties;
      this.globalPartitionName = this.name + "-" + "DOMAIN";
   }

   public LifecyclePluginFactory getLifecyclePluginFactory() {
      return this.lifecyclePluginFactory;
   }

   public void setLifecyclePluginFactory(LifecyclePluginFactory lifecyclePluginFactory) {
      this.lifecyclePluginFactory = lifecyclePluginFactory;
   }

   public String getRuntimeName() {
      return this.name;
   }

   public String getRuntimeType() {
      return this.runtimeType;
   }

   public Properties getRuntimeProperties() {
      return this.properties;
   }

   public LifecyclePartition createPartition(String partitionName, Map mapProperties) throws LifecycleException {
      Map properties = this.toProperties(mapProperties);
      PartitionPlugin plugin = this.lifecyclePluginFactory.getService(this.getRuntimeType());
      if (plugin == null) {
         throw new LifecycleException(CatalogUtils.getMsgPartitionPluginNotFound(this.getRuntimeType()));
      } else {
         LifecycleContextImpl ctx = new LifecycleContextImpl(properties, this.getRuntimeType(), LifecycleOperationType.CREATE_PARTITION);
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Creating partition " + partitionName + " on runtime " + this.name);
         }

         Object partition;
         if (partitionName.equals(this.globalPartitionName)) {
            partition = new LifecyclePartitionImpl(this.getRuntimeType() + this.getRuntimeName() + "0", this.globalPartitionName, this.getRuntimeType(), this.getRuntimeName(), mapProperties);
         } else {
            partition = plugin.create(partitionName, ctx, this);
         }

         if (partition != null) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Created partition " + partitionName + " on runtime " + this.name + " with id " + ((LifecyclePartition)partition).getId());
            }

            LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();

            try {
               Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
               Partition existingPartition = runtimeConfig.getPartitionByName(((LifecyclePartition)partition).getPartitionName());
               if (existingPartition != null) {
                  if (existingPartition.getId().equals(((LifecyclePartition)partition).getId())) {
                     return (LifecyclePartition)partition;
                  }

                  this.deletePartitionConfig(partitionName, false);
               }

               this.createPartitionConfig((LifecyclePartition)partition);
            } catch (LifecycleException var12) {
               LifecycleException ex = var12;

               try {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Error after creating partition " + partitionName, ex);
                     LifecycleUtils.debug("Calling syncPartitions to make partitions in sync for " + this.getRuntimeName());
                  }

                  this.syncPartitions();
               } catch (LifecycleException var11) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Error synchronizing partitions after failure to create partition configuration", var11);
                  }

                  throw var12;
               }
            }
         }

         return (LifecyclePartition)partition;
      }
   }

   private Map toProperties(Map mapProperties) {
      Map properties = new HashMap();

      String key;
      Object value;
      for(Iterator var3 = mapProperties.keySet().iterator(); var3.hasNext(); properties.put(key, value)) {
         key = (String)var3.next();
         PropertyValue propertyValue = (PropertyValue)mapProperties.get(key);
         if (propertyValue instanceof StringPropertyValue) {
            value = ((StringPropertyValue)propertyValue).getValue();
         } else if (propertyValue instanceof ConfidentialPropertyValue) {
            value = ((ConfidentialPropertyValue)propertyValue).getValue();
         } else if (propertyValue instanceof ListPropertyValue) {
            value = this.toProperties(((ListPropertyValue)propertyValue).getValue());
         } else if (propertyValue instanceof PropertiesPropertyValue) {
            value = this.toProperties(((PropertiesPropertyValue)propertyValue).getValue());
         } else {
            value = propertyValue.toString();
         }
      }

      return properties;
   }

   private List toProperties(List listProperties) {
      List properties = new ArrayList(listProperties.size());

      Object value;
      for(Iterator var3 = listProperties.iterator(); var3.hasNext(); properties.add(value)) {
         PropertyValue propertyValue = (PropertyValue)var3.next();
         if (propertyValue instanceof StringPropertyValue) {
            value = ((StringPropertyValue)propertyValue).getValue();
         } else if (propertyValue instanceof ConfidentialPropertyValue) {
            value = ((ConfidentialPropertyValue)propertyValue).getValue();
         } else if (propertyValue instanceof ListPropertyValue) {
            value = this.toProperties(((ListPropertyValue)propertyValue).getValue());
         } else if (propertyValue instanceof PropertiesPropertyValue) {
            value = this.toProperties(((PropertiesPropertyValue)propertyValue).getValue());
         } else {
            value = propertyValue.toString();
         }
      }

      return properties;
   }

   public void deletePartition(String partitionName, Map mapProperties) throws LifecycleException {
      PropertyValue isDeleteRuntime = (PropertyValue)mapProperties.get("isDeleteRuntime");
      if (partitionName.equals(this.globalPartitionName) && isDeleteRuntime == null) {
         throw new LifecycleException(CatalogUtils.getMsgInvalidArgDeletePartition());
      } else {
         PartitionPlugin plugin = this.lifecyclePluginFactory.getService(this.getRuntimeType());
         if (plugin == null) {
            throw new LifecycleException(CatalogUtils.getMsgPartitionPluginNotFound(this.getRuntimeType()));
         } else {
            Partition partition;
            if (!partitionName.equals(this.globalPartitionName)) {
               LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
               Environments environments = lifecycleConfig.getEnvironments();
               Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
               partition = runtimeConfig.getPartitionByName(partitionName);
               Environment environmentConfig = null;
               if (environments != null && partition != null) {
                  environmentConfig = environments.getReferencedEnvironment(partition);
                  if (environmentConfig != null) {
                     throw new LifecycleException(CatalogUtils.getMsgUnableToDeletePartitionReferenceConstraints(environmentConfig.getName()));
                  }
               }
            }

            Map properties = this.toProperties(mapProperties);
            boolean isSourceWLS = this.isSourceWLS(properties);
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Deleting partition " + partitionName + " on runtime " + this.name + ", isSourceWLS: " + isSourceWLS);
            }

            LifecycleContextImpl ctx = new LifecycleContextImpl(properties, this.getRuntimeType(), LifecycleOperationType.DELETE_PARTITION);
            if (!isSourceWLS && !partitionName.equals(this.globalPartitionName)) {
               plugin.delete(partitionName, ctx, this);
            }

            partition = null;

            try {
               LifecyclePartition partition = this.getPartition(partitionName);
               if (partition != null && partition.getPartitionType().equalsIgnoreCase("wls") && !partitionName.equals(this.globalPartitionName) && isSourceWLS) {
                  this.dissociateAssociatedPartitions(partitionName);
               }

               this.deletePartitionConfig(partitionName, isSourceWLS);
            } catch (LifecycleException var12) {
               LifecycleException ex = var12;

               try {
                  if (partition != null) {
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug("Error after deleting partition " + partitionName, ex);
                        LifecycleUtils.debug("Calling syncPartitions to make partitions in sync for " + this.getRuntimeName());
                     }

                     this.syncPartitions();
                  }
               } catch (LifecycleException var11) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Error syncing environment after failure to delete partition :  " + partitionName, var11);
                  }

                  throw var12;
               }
            }

         }
      }
   }

   private boolean isSourceWLS(Map properties) {
      boolean isSourceWLS = false;
      if (properties != null) {
         String source = (String)properties.get("source");
         if (source != null && source.equalsIgnoreCase("wls")) {
            isSourceWLS = true;
         }
      }

      return isSourceWLS;
   }

   private void deletePartitionConfig(String partitionName, boolean isSourceWLS) {
      Class var3 = LifecycleRuntimeImpl.class;
      synchronized(LifecycleRuntimeImpl.class) {
         LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
         Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
         Partition partitionConfig = runtimeConfig.getPartitionByName(partitionName);
         Environments environments = lifecycleConfig.getEnvironments();
         if (environments != null) {
            Environment environmentConfig = null;
            if (partitionConfig != null) {
               environmentConfig = environments.getReferencedEnvironment(partitionConfig);
            }

            if (environmentConfig != null) {
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Cleaning up environment config " + environmentConfig.getName());
               }

               if (partitionName.equals(this.globalPartitionName) && environmentConfig.getName().startsWith("DOMAIN")) {
                  this.deletePartitionRef(environmentConfig, partitionName);
                  this.deleteGlobalPartitionEnv(environments, environmentConfig);
               } else if (isSourceWLS) {
                  this.deletePartitionRef(environmentConfig, partitionName);
               }
            }
         }

         if (partitionConfig != null) {
            LifecycleUtils.debug("Deleting partition config");
            this.removePartitionConfig(runtimeConfig, partitionConfig);
         }

      }
   }

   private void deletePartitionRef(Environment environmentConfig, String partitionName) {
      PartitionRef partitionRef = environmentConfig.getPartitionRefByTypeAndName(this.getRuntimeType(), partitionName);
      if (partitionRef != null) {
         Partition partitionObject = environmentConfig.getPartitionById(partitionRef.getId());
         environmentConfig.removeAssociations(partitionObject);
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Deleting partition reference " + partitionRef.getId());
         }

         environmentConfig.deletePartitionRef(partitionRef);
      }

   }

   private void deleteGlobalPartitionEnv(Environments environments, Environment environmentConfig) {
      boolean isEnvEmpty = true;
      Associations associations = environmentConfig.getAssociations();
      List partitionRefs = environmentConfig.getPartitionRefs();
      if (associations != null) {
         List associationList = associations.getAssociations();
         if (associationList != null && !associationList.isEmpty()) {
            isEnvEmpty = false;
         }
      }

      if (partitionRefs != null && !partitionRefs.isEmpty()) {
         isEnvEmpty = false;
      }

      if (isEnvEmpty) {
         LifecycleUtils.debug("Environment is empty, deleting");
         environments.deleteEnvironment(environmentConfig);
      }

   }

   private void removePartitionConfig(Runtime runtimeConfig, Partition partitionConfig) {
      if (partitionConfig != null) {
         Partition partition = runtimeConfig.getPartitionByName(partitionConfig.getName());
         if (partition != null) {
            runtimeConfig.deletePartition(partitionConfig);
         }

      }
   }

   private void dissociateAssociatedPartitions(String partitionName) throws LifecycleException {
      Class var2 = LifecycleRuntimeImpl.class;
      synchronized(LifecycleRuntimeImpl.class) {
         LifecyclePartition partition = this.getPartition(partitionName);
         com.oracle.weblogic.lifecycle.Environment environment = null;
         if (partition != null) {
            environment = this.lifecycleManager.getEnvironment(partition.getId(), partition.getRuntimeName());
         }

         if (environment != null && partition.getPartitionType().equalsIgnoreCase("wls") && !partitionName.equals(this.globalPartitionName)) {
            Set associatedPartitions = ((EnvironmentImpl)environment).getAssociatedPartitions(partition);

            LifecyclePartition associatedPartition;
            for(Iterator var6 = associatedPartitions.iterator(); var6.hasNext(); environment.dissociate(partition, associatedPartition, new HashMap())) {
               associatedPartition = (LifecyclePartition)var6.next();
               LifecycleRuntime runtime = this.runtimeManager.getRuntime(associatedPartition.getRuntimeName());
               String associatedPartitionName = associatedPartition.getPartitionName();
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Notifying associated partition " + associatedPartitionName + " on runtime " + runtime.getRuntimeName());
               }
            }
         }

      }
   }

   public LifecyclePartition updatePartition(String partitionName, Map mapProperties) throws LifecycleException {
      return null;
   }

   private synchronized Partition createPartitionConfig(LifecyclePartition partition) throws LifecycleException {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
      Partition existingPartition = runtimeConfig.getPartitionByName(partition.getPartitionName());
      if (existingPartition == null) {
         Map partitionProperties = partition.getPartitionProperties();
         partitionProperties.put("name", PropertyValueFactory.getStringPropertyValue(partition.getPartitionName()));
         partitionProperties.put("id", PropertyValueFactory.getStringPropertyValue(partition.getId()));

         try {
            return runtimeConfig.createPartition(partitionProperties);
         } catch (IllegalArgumentException var8) {
            Partition partitionConfig = runtimeConfig.getPartitionByName(partition.getPartitionName());
            throw new LifecycleException(CatalogUtils.getMsgPartitionExistsInLCM(partitionConfig.getName(), partitionConfig.getId()), var8);
         } catch (Exception var9) {
            throw new LifecycleException(CatalogUtils.getMsgExceptionCreatingPartitionConfiguration(), var9);
         }
      } else if (!existingPartition.getId().equals(partition.getId())) {
         throw new LifecycleException(CatalogUtils.getMsgPartitionExistsInLCM(existingPartition.getName(), existingPartition.getId()));
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Partition already exists");
         }

         return null;
      }
   }

   public List getPartitions() {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtime = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
      List result = new ArrayList();
      return result;
   }

   public LifecyclePartition getPartition(String partitionName) {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
      if (runtimeConfig != null) {
         Partition partitionConfig = runtimeConfig.getPartitionByName(partitionName);
         if (partitionConfig != null) {
            LifecyclePartition partition = this.getPartitionFromConfig(partitionConfig);
            return partition;
         }
      }

      return null;
   }

   private LifecyclePartition getPartitionFromConfig(Partition config) {
      String id = config.getId();
      String name = config.getName();
      String type = config.getRuntime().getType();
      String runtime = config.getRuntime().getName();
      List properties = config.getProperty();
      Map map = new HashMap();
      if (properties != null) {
         Iterator var8 = properties.iterator();

         while(var8.hasNext()) {
            PropertyBean property = (PropertyBean)var8.next();
            String mayBeEncryptedValue = property.getValue();
            PropertyValue value = PropertyValueFactory.getStringOrConfidentialPropertyValue(mayBeEncryptedValue);
            map.put(property.getName(), value);
         }
      }

      LifecyclePartition partition = new LifecyclePartitionImpl(id, name, type, runtime, map);
      return partition;
   }

   public LifecyclePartition registerPartition(String partitionName, String partitionId) throws LifecycleException {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Registering partition " + partitionName + " on runtime " + this.name);
      }

      LifecyclePartition partition = new LifecyclePartitionImpl(partitionId, partitionName, this.runtimeType, this.getRuntimeName());
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
      Partition existingPartition = runtimeConfig.getPartitionByName(partition.getPartitionName());
      if (existingPartition == null) {
         Partition partitionConfig = this.createPartitionConfig(partition);
         if (partitionConfig != null) {
            if (partitionConfig.getId().equals(partitionId)) {
               return partition;
            } else {
               throw new LifecycleException(CatalogUtils.getMsgPartitionExistsInLCM(partitionConfig.getName(), partitionConfig.getId()));
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public void unregisterPartition(String partitionName) throws LifecycleException {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      Runtime runtimeConfig = lifecycleConfig.getRuntimes().getRuntimeByName(this.name);
      Partition existingPartition = runtimeConfig.getPartitionByName(partitionName);
      Class var5 = LifecycleRuntimeImpl.class;
      synchronized(LifecycleRuntimeImpl.class) {
         if (existingPartition != null) {
            PartitionRef ref = lifecycleConfig.getEnvironments().getPartitionRef(existingPartition);
            if (ref != null) {
               this.dissociateAssociatedPartitions(partitionName);
               Environment env = ref.getEnvironment();
               env.deletePartitionRef(ref);
            }

            this.removePartitionConfig(runtimeConfig, existingPartition);
         } else {
            throw new IllegalArgumentException(CatalogUtils.getMsgPartitionWithSpecifiedNameNotFound(partitionName));
         }
      }
   }

   public void applyPartitionTemplate(File partitonTemplate) {
      throw new UnsupportedOperationException();
   }

   public void syncPartitions() throws LifecycleException {
      List partitionList = null;
      String runtimeType = this.getRuntimeType();
      RuntimePlugin plugin = this.lifecyclePluginFactory.getRuntimePlugin(runtimeType);
      if (plugin == null) {
         throw new LifecycleException(CatalogUtils.getMsgRuntimePluginNotFound(this.getRuntimeType()));
      }
   }

   private void deletePartitions(List currentPartitionList, List masterPartitionList) throws LifecycleException {
      List masterPartitionNameList = new ArrayList();
      Iterator var4 = masterPartitionList.iterator();

      LifecyclePartition lpartition;
      while(var4.hasNext()) {
         lpartition = (LifecyclePartition)var4.next();
         masterPartitionNameList.add(lpartition.getPartitionName());
      }

      var4 = currentPartitionList.iterator();

      while(var4.hasNext()) {
         lpartition = (LifecyclePartition)var4.next();
         String runtimepartitionName = lpartition.getPartitionName();
         if (!masterPartitionNameList.contains(runtimepartitionName) && !runtimepartitionName.equalsIgnoreCase(this.getGlobalPartitionName(lpartition.getRuntimeName()))) {
            this.unregisterPartition(runtimepartitionName);
         }
      }

   }

   private String getGlobalPartitionName(String runtimeName) {
      return runtimeName + "-" + "DOMAIN";
   }

   private void registerNewPartitions(List partitions) throws LifecycleException {
      Iterator var2 = partitions.iterator();

      while(var2.hasNext()) {
         LifecyclePartition partition = (LifecyclePartition)var2.next();
         if (null == this.getPartition(partition.getPartitionName())) {
            this.registerPartition(partition.getPartitionName(), partition.getId());
         }
      }

   }
}
