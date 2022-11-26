package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleOperationType;

public class CatalogUtils {
   public static final String PARTITION_PLUGIN = "Partition Plugin of type";
   public static final String NOT_FOUND = " not found";
   public static final String NOT_FOUND_IN_LCMCONFIG = " not found in LCM Configuration";
   public static final String RUNTIME_PLUGIN = "Runtime Plugin of type";
   public static final String PARTITION = "Partition";
   public static final String RUNTIMES = "Runtimes";
   public static final String CONFIG = "Config";
   public static final String REGISTERED_WITH_ID = "registered in LCM Config with ID =";
   public static final String GLOBAL_PARTITION_DELETE = "Global Partition cannot be explicitly deleted.";
   public static final String PARTITION_REFERENCE_CONSTRAINT = "A Runtime Partition cannot be deleted as it is referenced by Environment.";
   public static final String PARTITION_CONFIG_CREATE_FAILED = "Exception creating partition Configuration.";
   public static final String SEPARATOR = " ";
   public static final String ID = "ID";
   public static final String ENV = "Environment";
   public static final String EXISTS = "Exists";
   public static final String ENVIRONMENTS_NOT_FOUND = "Environments config does not exist in LCM Configuration";
   public static final String LCMCONFIG_NOT_FOUND = "Environments config does not exist in LCM Configuration";
   public static final String NULL_ENV_SYNC = "Null Environment Specified for Sync";
   public static final String PLUGIN_FAIL = "No relevant classes to load in Plugin ";
   public static final String CAUGHT_EXCEPTION = "Caught an Exception ";
   public static final String WAITING_ASYNC_TASK = "waiting for a LCM asynchronous task to complete";
   public static final String INVALID = "Invalid";
   public static final String OPERATION = "Operation";
   public static final String RUNTIME = "Runtime";
   public static final String NULL = "null";
   public static final String IN = "in";
   public static final String LIFECYCLETASKPOLL = "LifecyleTaskPollThread";
   public static final String PROPS = "Properties";
   public static final String START = "START";
   public static final String OTD = "OTD";
   public static final String REG = "Registration";
   public static final String CREATE = "Create";
   public static final String NOT_ALLOWED = "Not Allowed";
   public static final String SAMEHOSTPORT = "With Same Host and Port.";
   public static final String SAMENAME = "With Same Name";
   public static final String HOST = "Hostname";
   public static final String PORT = "Port";
   public static final String NAME = "Name";
   public static final String WITH = "With";
   private static final LCMMessageTextFormatter lcmMessageTextFormatter = LCMMessageTextFormatter.getInstance();
   public static final String EXCEPTION_WATCHER = "Exception setting up Watcher for LCM Configuration.";

   static String getMsgPartitionPluginNotFound(String type) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.partitionPluginNotFound(type) : "Partition Plugin of type " + type + " " + " not found";
   }

   static String getMsgRuntimePluginNotFound(String type) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimePluginNotFound(type) : "Runtime Plugin of type " + type + " " + " not found";
   }

   static String getMsgPartitionExistsInLCM(String name, String id) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.partitionExistsInLCM(name, id) : "Partition " + name + " " + "registered in LCM Config with ID =" + id;
   }

   static String getMsgInvalidArgDeletePartition() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.cannotDeleteGlobalPartition() : "Global Partition cannot be explicitly deleted.";
   }

   static String getMsgUnableToDeletePartitionReferenceConstraints(String environmentName) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.cannotDeletePartitionReferencedEnv(environmentName) : "A Runtime Partition cannot be deleted as it is referenced by Environment. " + environmentName;
   }

   static String getMsgExceptionCreatingPartitionConfiguration() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionCreatingPartitionConfiguration() : "Exception creating partition Configuration.";
   }

   static String getMsgPartitionWithSpecifiedNameNotFound(String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.partitionWithSpecifiedNameNotFound(name) : "Partition " + name + " " + " not found in LCM Configuration";
   }

   static String getMsgPartitionNotFound(String partitionId) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.partitionNotFound(partitionId) : "Partition ID " + partitionId + " " + " not found in LCM Configuration";
   }

   static String getMsgRuntimesNotFound() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimesNotFound() : "Runtimes Config  not found in LCM Configuration";
   }

   static String getMsgRuntimeNotFound(String runtimeName) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimeNotFound(runtimeName) : "Runtime " + runtimeName + " " + " not found in LCM Configuration";
   }

   static String getMsgEnvNotFound(String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.envNotFound(name) : "Environment " + name + " " + " not found in LCM Configuration";
   }

   static String getMsgEnvExists(String environmentName) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.environmentExists(environmentName) : "Environment " + environmentName + " " + "Exists";
   }

   static String getMsgEnvConfigNotFound() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.environmentConfigNotFound() : "Environments config does not exist in LCM Configuration";
   }

   static String getMsgLCMConfigNotFound() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.lcmConfigNotFound() : "Environments config does not exist in LCM Configuration";
   }

   static String getMsgPluginLoadFailed(String path) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.pluginLoadFailed(path) : "No relevant classes to load in Plugin  " + path;
   }

   static String getMsgNullEnvForSync() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.nullEnvForSync() : "Null Environment Specified for Sync";
   }

   static String getMsgInvalidEnvironmentOp(String operation, String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.invalidEnvironmentOperation(operation, name) : "An invalid operation" + operation + " was specified on the Environment " + name;
   }

   static String getMsgExceptionWhenWaitingForTaskCompletion() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionWhenWaitingForTaskCompletion() : "Caught an Exception  waiting for a LCM asynchronous task to complete";
   }

   static String getMsgNullOperationInLifecycleTaskPoll() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.invalidOperationInLifecycleTaskPoll() : "null Operation in LifecyleTaskPollThread";
   }

   static String getMsgNullPropertiesInLifecycleTaskPoll() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.nullPropertiesInLifecycleTaskPoll() : "null Properties in LifecyleTaskPollThread";
   }

   static String getMsgExceptionCallingOTDRuntimePluginStart() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionCallingOTDRuntimePluginStart() : "Caught an Exception  OTD Runtime Plugin of type START";
   }

   static String getMsgExceptionCallingPartitionPluginStart() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionCallingPartitionPluginStart() : "Caught an Exception  Partition Plugin of type START";
   }

   static String getMsgExceptionOnCreate() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionOnCreate() : "Caught an Exception  in Create";
   }

   static String getMsgExceptionOnScaling() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionOnScaling() : "Caught an Exception  in Operation " + LifecycleOperationType.SCALE_UP_RUNTIME;
   }

   static String getMsgExceptionCreatingRuntime(String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionCreatingRuntime(name) : "Caught an Exception  in Operation " + LifecycleOperationType.CREATE_RUNTIME;
   }

   static String getMsgExceptionDeletingRuntime(String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionDeletingRuntime(name) : "Caught an Exception  in Operation " + LifecycleOperationType.DELETE_RUNTIME;
   }

   static String getMsgRuntimesConfigNotFound() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimesConfigNotFound() : "Runtimes  not found in LCM Configuration";
   }

   static String getMsgOpNotAllowedForRuntime(String operation) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.opNotAllowedForRuntime(operation) : "Operation" + operation + "Not Allowed";
   }

   static String getMsgExceptionSettingUpFileWatcherLCMConfig() {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.exceptionSettingUpFileWatcherLCMConfig() : "Exception setting up Watcher for LCM Configuration.";
   }

   static String getMsgRuntimeWithHostPortExists(String host, String port) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimeWithHostPortExists(host, port) : "Runtime Exists With Same Host and Port.Hostname:" + host + " " + "Port" + ":" + port;
   }

   static String getMsgRuntimeWithNameExists(String name) {
      return LifecycleUtils.isAppServer() ? lcmMessageTextFormatter.runtimeWithNameExists(name) : "Runtime With Name " + name + "Exists";
   }
}
