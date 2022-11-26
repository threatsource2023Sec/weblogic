package com.oracle.weblogic.lifecycle.orchestration;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleAssociation;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.orchestration.commands.AddPartitionToEnvCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.AssociatePartitionsCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.CreateEnvironmentCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.CreatePartitionCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.DeleteEnvironmentCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.DeletePartitionFromEnvCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.DeprovisionComponentCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.DisassociatePartitionsCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.GetTenantInfoCommand;
import com.oracle.weblogic.lifecycle.orchestration.commands.ProvisionComponentCommand;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.WorkflowBuilder;

public class LifecycleWorkflowBuilderFactory {
   private static Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   private static LifecycleWorkflowBuilderFactory instance = null;
   public static final String OTD = "otd";
   public static final String WLS = "wls";
   public static final String DB = "db";
   public static final String ORCHESTRATION_NAME = "orchestrationName";
   public static final String VERSION = "version";
   public static final String ENVIRONMENT_NAME = "environmentName";
   public static final String PARTITION_ID = "partitionId";
   public static final String PARTITION_NAME = "partitionName";
   public static final String RUNTIME_NAME = "runtimeName";
   public static final String PARTITION_PROPERTIES = "partitionProperties";
   public static final String PROPERTIES = "properties";
   public static final String NAME = "name";
   public static final String VALUE = "value";
   public static final String VALUES = "values";
   public static final String ASSOCIATE_WITH = "associateWith";
   public static final String ASSOCIATE_PROPERTIES = "associateProperties";
   public static final String PARTITION1 = "partition1";
   public static final String PARTITION2 = "partition2";
   public static final String RUNTIME1 = "runtime1";
   public static final String RUNTIME2 = "runtime2";
   public static final String SERVICE_UUID = "serviceUUID";
   public static final String COMPONENT = "component";
   public static final String COMPONENT_NAME = "componentName";
   public static final String CONFIGURABLE_ATTRIBUTES = "configurableAttributes";

   public static LifecycleWorkflowBuilderFactory getInstance() {
      if (instance == null) {
         instance = new LifecycleWorkflowBuilderFactory();
      }

      return instance;
   }

   public WorkflowBuilder getLifecycleWorkflowBuilder(boolean isCreateNewEnv, String envName, JsonObject orchestrationObject, RuntimeManager runtimeManager) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (logger != null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "getLifecycleWorkflowBuilder called with param: envName {0}, orchestrationObject", envName);
         }

         logger.log(Level.INFO, "getLifecycleWorkflowBuilder called with param: orchestration JsonObject {0}", orchestrationObject.toString());
      }

      this.assertString("environmentName", envName);
      this.validateOrchestrationJsonObject(orchestrationObject);
      JsonObject otdObject = orchestrationObject.getJsonObject("otd");
      JsonObject wlsObject = orchestrationObject.getJsonObject("wls");
      JsonObject dbObject = orchestrationObject.getJsonObject("db");
      Map map = new HashMap();
      map.put("environmentName", envName);
      map.put("partitionId", new MutableString());
      builder.add(map);
      if (isCreateNewEnv) {
         builder.add(this.getCreateEnvironmentWorkflowBuilder());
      }

      builder.add(this.getPartitionsWorkflowBuilder(otdObject, wlsObject, dbObject, runtimeManager));
      builder.add(this.getAssociateWorkflowBuilder(orchestrationObject));
      if (wlsObject != null) {
         JsonArray args = wlsObject.getJsonArray("component");
         if (args != null) {
            String componentName = null;
            JsonArray properties = null;
            HashMap confAttrs = null;

            for(int i = 0; i < args.size(); ++i) {
               JsonObject arg = args.getJsonObject(i);
               String type = arg.getString("name");
               if ("componentName".equals(type)) {
                  componentName = arg.getString("value");
               } else if ("configurableAttributes".equals(type)) {
                  confAttrs = this.getConfigurableAttributeMap(arg.getJsonArray("properties"));
               }
            }

            builder.add(this.getProvisioningWorkflowBuilder(wlsObject, componentName, confAttrs));
         }
      }

      return builder;
   }

   private HashMap getConfigurableAttributeMap(JsonArray properties) {
      HashMap map = new HashMap();
      if (properties != null) {
         String namespace = null;
         Map compMap = new HashMap();

         for(int i = 0; i < properties.size(); ++i) {
            JsonObject arg = properties.getJsonObject(i);
            String type = arg.getString("name");
            if ("name".equals(type)) {
               namespace = arg.getString("value");
               compMap = new HashMap();
               map.put(namespace, compMap);
            } else if ("configurableAttributes".equals(type)) {
               JsonArray confAttr = arg.getJsonArray("properties");

               for(int j = 0; j < confAttr.size(); ++j) {
                  JsonObject attr = confAttr.getJsonObject(j);
                  String key = attr.getString("name");
                  String value = attr.getString("value");
                  compMap.put(key, value);
               }
            }
         }
      }

      return map;
   }

   public WorkflowBuilder getLifecycleWorkflowDeleteAllBuilder(Environment env) throws LifecycleException {
      return this.getLifecycleWorkflowDeleteAllBuilder(env, (JsonObject)null);
   }

   public WorkflowBuilder getLifecycleWorkflowDeleteAllBuilder(Environment env, JsonObject orchestrationObject) throws LifecycleException {
      Objects.requireNonNull(env);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String envName = env.getName();
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "getLifecycleWorkflowDeletAllBuilder called with param: envName {0}", envName);
      }

      Map map = new HashMap();
      map.put("partitionId", new MutableString());
      map.put("environmentName", envName);
      builder.add(map);
      if (orchestrationObject != null) {
         JsonObject wlsObject = orchestrationObject.getJsonObject("wls");
         if (wlsObject != null) {
            JsonArray args = wlsObject.getJsonArray("component");
            if (args != null) {
               String componentName = null;
               JsonArray properties = null;
               HashMap confAttrs = null;

               for(int i = 0; i < args.size(); ++i) {
                  JsonObject arg = args.getJsonObject(i);
                  String type = arg.getString("name");
                  if ("componentName".equals(type)) {
                     componentName = arg.getString("value");
                  } else if ("configurableAttributes".equals(type)) {
                     confAttrs = this.getConfigurableAttributeMap(arg.getJsonArray("properties"));
                  }
               }

               builder.add(this.getPartitionDeprovisionerWorkflowBuilder(wlsObject, componentName, confAttrs));
            }
         }
      }

      builder.add(this.getDisassociateAllWorkflowBuilder(env));
      builder.add(this.getDeleteAllPartitionsWorkflowBuilder(env));
      builder.add(this.getDeleteEnvironmentWorkflowBuilder());
      return builder;
   }

   public WorkflowBuilder getLifecycleWorkflowDeleteBuilder(Environment env, String partitionName, String type) throws LifecycleException {
      Objects.requireNonNull(env);
      Objects.requireNonNull(partitionName);
      Objects.requireNonNull(type);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      String envName = env.getName();
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "getLifecycleWorkflowDeletBuilder called with param: partitionName {0}, type {1}", new String[]{partitionName, type});
      }

      Map map = new HashMap();
      map.put("partitionId", new MutableString());
      map.put("environmentName", envName);
      builder.add(map);
      builder.add(this.getDisassociatePartitionsWorkflowBuilder(env, partitionName, type));
      builder.add(this.getDeletePartitionsWorkflowBuilder(env, partitionName, type));
      builder.add(this.getDeleteEnvironmentWorkflowBuilder());
      return builder;
   }

   WorkflowBuilder getCreateEnvironmentWorkflowBuilder() {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      builder.add(CreateEnvironmentCommand.class);
      return builder;
   }

   WorkflowBuilder getDeleteEnvironmentWorkflowBuilder() {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      builder.add(DeleteEnvironmentCommand.class);
      return builder;
   }

   WorkflowBuilder getPartitionsWorkflowBuilder(JsonObject otdObject, JsonObject wlsObject, JsonObject dbObject, RuntimeManager runtimeManager) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      if (otdObject != null) {
         builder.add(this.getCreatePartitionWorkflowBuilder(otdObject, runtimeManager));
      }

      if (wlsObject != null) {
         builder.add(this.getCreatePartitionWorkflowBuilder(wlsObject, runtimeManager));
      }

      if (dbObject != null) {
         builder.add(this.getCreatePartitionWorkflowBuilder(dbObject, runtimeManager));
      }

      return builder;
   }

   WorkflowBuilder getCreatePartitionWorkflowBuilder(JsonObject runtimeObject, RuntimeManager runtimeManager) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      this.assertJsonObject("runtimeObject", runtimeObject);
      JsonString partitionName = runtimeObject.getJsonString("partitionName");
      JsonString runtimeName = runtimeObject.getJsonString("runtimeName");
      JsonArray partitionPropsArray = runtimeObject.getJsonArray("partitionProperties");
      this.assertJsonString("partitionName", partitionName);
      this.assertJsonString("runtimeName", runtimeName);
      LifecycleRuntime runtime = runtimeManager.getRuntime(runtimeName.getString());
      if (runtime == null) {
         throw new LifecycleException("Lifecycle runtime does not exist for " + runtimeName.getString() + ". The runtime must be registered with LCM before calling Orchestrator orchestrate");
      } else {
         LifecyclePartition partition = runtime.getPartition(partitionName.getString());
         if (partition != null) {
            throw new LifecycleException("Lifecycle partition already exists for " + partitionName.getString() + " on the runtime " + runtimeName.getString() + ". The partition must be removed with LCM before calling Lifecycle Orchestrate orchestrate to create the partition. ");
         } else {
            HashMap partitionPropsMap = this.convertJsonPropsArrayToHashMap(partitionPropsArray);
            Map map = new HashMap();
            map.put("partitionName", partitionName.getString());
            map.put("partitionProperties", partitionPropsMap);
            map.put("runtimeName", runtimeName.getString());
            builder.add(CreatePartitionCommand.class, map);
            builder.add(this.getAddPartitionToEnvWorkflowBuilder(map));
            return builder;
         }
      }
   }

   WorkflowBuilder getProvisioningWorkflowBuilder(JsonObject wlsObject, String componentName, HashMap confAttr) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();

      assert builder != null;

      Map map = new HashMap();
      map.put("serviceUUID", new MutableString());
      builder.add(map);
      builder.add(GetTenantInfoCommand.class);
      builder.add(this.getPartitionProvisionerWorkflowBuilder(wlsObject, componentName, confAttr));
      return builder;
   }

   WorkflowBuilder getPartitionProvisionerWorkflowBuilder(JsonObject wlsObject, String componentName, HashMap confAttr) throws LifecycleException {
      String className = LifecycleWorkflowBuilderFactory.class.getName();
      String methodName = "getPartitionProvisionerWorkflowBuilder";
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getPartitionProvisionerWorkflowBuilder", new Object[]{wlsObject, componentName, confAttr});
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();

      assert builder != null;

      String partitionName = wlsObject.getJsonString("partitionName").getString();
      Map map = new HashMap();
      map.put("partitionName", partitionName);
      map.put("componentName", componentName);
      map.put("configurableAttributes", confAttr);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "getPartitionProvisionerWorkflowBuilder", "Adding {0} with {1} to {2}", new Object[]{ProvisionComponentCommand.class, map, builder});
      }

      builder.add(ProvisionComponentCommand.class, map);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getPartitionProvisionerWorkflowBuilder", builder);
      }

      return builder;
   }

   WorkflowBuilder getPartitionDeprovisionerWorkflowBuilder(JsonObject wlsObject, String componentName, HashMap confAttr) throws LifecycleException {
      String className = LifecycleWorkflowBuilderFactory.class.getName();
      String methodName = "getPartitionDeprovisionerWorkflowBuilder";
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getPartitionDeprovisionerWorkflowBuilder", new Object[]{wlsObject, componentName, confAttr});
      }

      WorkflowBuilder builder = WorkflowBuilder.newInstance();

      assert builder != null;

      String partitionName = wlsObject.getJsonString("partitionName").getString();
      Map map = new HashMap();
      map.put("partitionName", partitionName);
      map.put("componentName", componentName);
      map.put("configurableAttributes", confAttr);
      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "getPartitionDeprovisionerWorkflowBuilder", "Adding {0} with {1} to {2}", new Object[]{DeprovisionComponentCommand.class, map, builder});
      }

      builder.add(DeprovisionComponentCommand.class, map);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getPartitionDeprovisionerWorkflowBuilder", builder);
      }

      return builder;
   }

   WorkflowBuilder getDeleteAllPartitionsWorkflowBuilder(Environment env) throws LifecycleException {
      Objects.requireNonNull(env);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      return builder;
   }

   WorkflowBuilder getDeletePartitionsWorkflowBuilder(Environment env, String partitionName, String type) throws LifecycleException {
      Objects.requireNonNull(env);
      Objects.requireNonNull(partitionName);
      Objects.requireNonNull(type);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      return builder;
   }

   WorkflowBuilder getAddPartitionToEnvWorkflowBuilder(Map map) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      builder.add(AddPartitionToEnvCommand.class, map);
      return builder;
   }

   WorkflowBuilder getRemovePartitionFromEnvWorkflowBuilder(Map map) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      builder.add(DeletePartitionFromEnvCommand.class, map);
      return builder;
   }

   WorkflowBuilder getAssociateWorkflowBuilder(JsonObject orchestrationObject) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      this.assertJsonObject("orchestrationObject", orchestrationObject);
      JsonObject otdObject = orchestrationObject.getJsonObject("otd");
      JsonObject wlsObject = orchestrationObject.getJsonObject("wls");
      JsonObject dbObject = orchestrationObject.getJsonObject("db");
      if (otdObject != null) {
         builder.add(this.getAssociatePartitionsWorkflowBuilder(otdObject, orchestrationObject));
      }

      if (wlsObject != null) {
         builder.add(this.getAssociatePartitionsWorkflowBuilder(wlsObject, orchestrationObject));
      }

      if (dbObject != null) {
         builder.add(this.getAssociatePartitionsWorkflowBuilder(dbObject, orchestrationObject));
      }

      return builder;
   }

   WorkflowBuilder getAssociatePartitionsWorkflowBuilder(JsonObject runtimeObject, JsonObject orchestrationObject) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      this.assertJsonObject("runtimeObject", runtimeObject);
      this.assertJsonObject("orchestrationObject", orchestrationObject);
      JsonString associateWith = runtimeObject.getJsonString("associateWith");
      if (associateWith != null && !associateWith.getString().equals("")) {
         JsonString partition1 = runtimeObject.getJsonString("partitionName");
         JsonString runtime1 = runtimeObject.getJsonString("runtimeName");
         this.assertJsonString("partition1", partition1);
         this.assertJsonString("runtime1", runtime1);
         JsonString partition2 = null;
         JsonString runtime2 = null;
         switch (associateWith.getString()) {
            case "otd":
               JsonObject otdObject = orchestrationObject.getJsonObject("otd");
               partition2 = otdObject.getJsonString("partitionName");
               runtime2 = otdObject.getJsonString("runtimeName");
               break;
            case "wls":
               JsonObject wlsObject = orchestrationObject.getJsonObject("wls");
               partition2 = wlsObject.getJsonString("partitionName");
               runtime2 = wlsObject.getJsonString("runtimeName");
               break;
            case "db":
               JsonObject dbObject = orchestrationObject.getJsonObject("db");
               partition2 = dbObject.getJsonString("partitionName");
               runtime2 = dbObject.getJsonString("runtimeName");
         }

         if (partition2 != null) {
            JsonArray associatePropsArray = runtimeObject.getJsonArray("associateProperties");
            HashMap associateProperties = this.convertJsonPropsArrayToSimpleHashMap(associatePropsArray);
            this.assertJsonString("runtime2", runtime2);
            Map map = new HashMap();
            map.put("partition1", partition1.getString());
            map.put("runtime1", runtime1.getString());
            map.put("partition2", partition2.getString());
            map.put("runtime2", runtime2.getString());
            map.put("associateProperties", associateProperties);
            builder.add(AssociatePartitionsCommand.class, map);
         }

         return builder;
      } else {
         return builder;
      }
   }

   WorkflowBuilder getDisassociateAllWorkflowBuilder(Environment env) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      List laList = env.getAssociations();
      Iterator var4 = laList.iterator();

      while(var4.hasNext()) {
         LifecycleAssociation la = (LifecycleAssociation)var4.next();
         LifecyclePartition lp1 = la.getPartition1();
         LifecyclePartition lp2 = la.getPartition2();
         String partition1 = lp1.getPartitionName();
         String partition2 = lp2.getPartitionName();
         String runtime1 = lp1.getRuntimeName();
         String runtime2 = lp2.getRuntimeName();
         HashMap props1 = (HashMap)lp1.getPartitionProperties();
         HashMap props2 = (HashMap)lp2.getPartitionProperties();
         Map map = new HashMap();
         map.put("partition1", partition1);
         map.put("runtime1", runtime1);
         map.put("partition2", partition2);
         map.put("runtime2", runtime2);
         map.put("associateProperties", props1);
         builder.add(DisassociatePartitionsCommand.class, map);
      }

      return builder;
   }

   WorkflowBuilder getDisassociatePartitionsWorkflowBuilder(Environment env, String partitionName, String type) throws LifecycleException {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      List laList = env.getAssociations();
      Iterator var6 = laList.iterator();

      while(true) {
         LifecyclePartition lp1;
         LifecyclePartition lp2;
         String partition1;
         String partition2;
         String type1;
         String type2;
         do {
            if (!var6.hasNext()) {
               return builder;
            }

            LifecycleAssociation la = (LifecycleAssociation)var6.next();
            lp1 = la.getPartition1();
            lp2 = la.getPartition2();
            partition1 = lp1.getPartitionName();
            partition2 = lp2.getPartitionName();
            type1 = lp1.getPartitionType();
            type2 = lp2.getPartitionType();
         } while((!partition1.equals(partitionName) || !type1.equals(type)) && (!partition2.equals(partitionName) || !type2.equals(type)));

         String runtime1 = lp1.getRuntimeName();
         String runtime2 = lp2.getRuntimeName();
         HashMap props1 = (HashMap)lp1.getPartitionProperties();
         HashMap props2 = (HashMap)lp2.getPartitionProperties();
         Map map = new HashMap();
         map.put("partition1", partition1);
         map.put("runtime1", runtime1);
         map.put("partition2", partition2);
         map.put("runtime2", runtime2);
         map.put("associateProperties", props1);
         builder.add(DisassociatePartitionsCommand.class, map);
      }
   }

   private HashMap convertJsonPropsArrayToHashMap(JsonArray jsonPropsArray) {
      HashMap partitionProperties = new HashMap();
      if (jsonPropsArray != null) {
         for(int i = 0; i < jsonPropsArray.size(); ++i) {
            JsonObject partitionProp = jsonPropsArray.getJsonObject(i);
            if (partitionProp != null) {
               JsonString propName = partitionProp.getJsonString("name");
               if (propName == null || propName.getString().equals("")) {
                  throw new IllegalArgumentException("Invalid parititonProperties: Must have arg defined for name");
               }

               JsonString propVal = partitionProp.getJsonString("value");
               JsonArray propValues = partitionProp.getJsonArray("values");
               if (propVal != null && !propVal.getString().equals("")) {
                  partitionProperties.put(propName.getString(), PropertyValueFactory.getStringPropertyValue(propVal.getString()));
               } else if (propValues != null && !propValues.isEmpty()) {
                  List pvList = new ArrayList();
                  Iterator var9 = propValues.iterator();

                  while(var9.hasNext()) {
                     JsonValue jv = (JsonValue)var9.next();
                     if (jv instanceof JsonString) {
                        JsonString js = (JsonString)jv;
                        pvList.add(PropertyValueFactory.getStringPropertyValue(js.getString()));
                     } else if (logger != null && logger.isLoggable(Level.WARNING)) {
                        logger.log(Level.WARNING, "partitionProperties (name = {0}, value = {1}, type = {2}) has a value that is not a JsonString so will be ignored.", new String[]{propName.toString(), jv.toString(), jv.getValueType().toString()});
                     }
                  }

                  partitionProperties.put(propName.getString(), PropertyValueFactory.getListPropertyValue(pvList));
               } else {
                  JsonArray propArray = partitionProp.getJsonArray("properties");
                  if (propArray != null) {
                     partitionProperties.put(propName.getString(), PropertyValueFactory.getPropertiesPropertyValue(this.convertJsonPropsArrayToHashMap(propArray)));
                  }
               }
            }
         }
      }

      return partitionProperties;
   }

   private HashMap convertJsonPropsArrayToSimpleHashMap(JsonArray jsonPropsArray) {
      HashMap partitionProperties = new HashMap();
      if (jsonPropsArray != null) {
         for(int i = 0; i < jsonPropsArray.size(); ++i) {
            JsonObject partitionProp = jsonPropsArray.getJsonObject(i);
            if (partitionProp != null) {
               JsonString propName = partitionProp.getJsonString("name");
               if (propName == null || propName.getString().equals("")) {
                  throw new IllegalArgumentException("Invalid parititonProperties: Must have arg defined for name");
               }

               JsonString propVal = partitionProp.getJsonString("value");
               JsonArray propValues = partitionProp.getJsonArray("values");
               if (propVal != null && !propVal.getString().equals("")) {
                  partitionProperties.put(propName.getString(), propVal.getString());
               } else if (propValues != null && !propValues.isEmpty()) {
                  List pvList = new ArrayList();
                  Iterator var9 = propValues.iterator();

                  while(var9.hasNext()) {
                     JsonValue jv = (JsonValue)var9.next();
                     if (jv instanceof JsonString) {
                        JsonString js = (JsonString)jv;
                        pvList.add(js.getString());
                     } else if (logger != null && logger.isLoggable(Level.WARNING)) {
                        logger.log(Level.WARNING, "partitionProperties (name = {0}, value = {1}, type = {2}) has a value that is not a JsonString so will be ignored.", new String[]{propName.toString(), jv.toString(), jv.getValueType().toString()});
                     }
                  }

                  partitionProperties.put(propName.getString(), pvList);
               } else {
                  JsonArray propArray = partitionProp.getJsonArray("properties");
                  if (propArray != null) {
                     new HashMap();
                     partitionProperties.put(propName.getString(), this.convertJsonPropsArrayToSimpleHashMap(propArray));
                  }
               }
            }
         }
      }

      return partitionProperties;
   }

   public void assertString(String paramName, String str) {
      if (str == null || str.length() == 0) {
         throw new IllegalArgumentException("Parameter not defined: " + paramName);
      }
   }

   public void assertJsonString(String paramName, JsonString str) {
      if (str == null || str.getString().equals("")) {
         throw new IllegalArgumentException("Parameter not defined: " + paramName);
      }
   }

   public void assertJsonObject(String paramName, JsonObject jsonObject) {
      if (jsonObject == null) {
         throw new IllegalArgumentException("Parameter not defined: " + paramName);
      }
   }

   private void validateOrchestrationJsonObject(JsonObject orchestrationObject) {
      this.assertJsonObject("orchestrationObject", orchestrationObject);
      JsonObject otdObject = orchestrationObject.getJsonObject("otd");
      JsonObject wlsObject = orchestrationObject.getJsonObject("wls");
      JsonObject dbObject = orchestrationObject.getJsonObject("db");
      JsonString associateWith;
      byte var7;
      if (otdObject != null) {
         this.validateRuntimeJsonObject(otdObject);
         associateWith = otdObject.getJsonString("associateWith");
         if (associateWith != null && !associateWith.getString().equals("")) {
            switch (associateWith.getString()) {
               case "otd":
                  throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate otd to iteself " + associateWith);
               case "wls":
                  if (wlsObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate otd to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(wlsObject);
                  break;
               case "db":
                  if (dbObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate otd to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(dbObject);
            }
         }
      }

      if (wlsObject != null) {
         this.validateRuntimeJsonObject(wlsObject);
         associateWith = wlsObject.getJsonString("associateWith");
         if (associateWith != null && !associateWith.getString().equals("")) {
            switch (associateWith.getString()) {
               case "otd":
                  if (otdObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate wls to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(otdObject);
                  break;
               case "wls":
                  throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate wls to iteself " + associateWith);
               case "db":
                  if (dbObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate wls to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(dbObject);
            }
         }
      }

      if (dbObject != null) {
         this.validateRuntimeJsonObject(dbObject);
         associateWith = dbObject.getJsonString("associateWith");
         if (associateWith != null && !associateWith.getString().equals("")) {
            switch (associateWith.getString()) {
               case "otd":
                  if (otdObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot assoicate db to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(otdObject);
                  break;
               case "wls":
                  if (wlsObject == null) {
                     throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate db to " + associateWith + ". " + associateWith + " is not defined in orchestration JsonObject");
                  }

                  this.validateRuntimeJsonObject(wlsObject);
                  break;
               case "db":
                  throw new IllegalArgumentException("Invalid orchestration JsonObject: Cannot associate db to iteself " + associateWith);
            }
         }
      }

   }

   private void validateRuntimeJsonObject(JsonObject runtimeObject) {
      if (runtimeObject != null) {
         JsonString runtimeName = runtimeObject.getJsonString("runtimeName");
         if (runtimeName != null && !runtimeName.getString().equals("")) {
            JsonString partitionName = runtimeObject.getJsonString("partitionName");
            if (partitionName != null && !partitionName.getString().equals("")) {
               JsonString associateWith = runtimeObject.getJsonString("associateWith");
               if (associateWith != null && !this.isAssociateWithValid(associateWith)) {
                  throw new IllegalArgumentException("Invalid arg associateWith for " + runtimeObject.toString());
               }
            } else {
               throw new IllegalArgumentException("Invalid orchestration JsonObject: Must define a valid partitionName for " + runtimeObject.toString());
            }
         } else {
            throw new IllegalArgumentException("Invalid orchestration JsonObject: Must define a valid runtimeName for " + runtimeObject.toString());
         }
      }
   }

   private boolean isAssociateWithValid(JsonString associateWith) {
      boolean isValid = true;
      String associateWithStr = associateWith.getString();
      return !"otd".equals(associateWithStr) && !"wls".equals(associateWithStr) && !"db".equals(associateWithStr) ? false : isValid;
   }
}
