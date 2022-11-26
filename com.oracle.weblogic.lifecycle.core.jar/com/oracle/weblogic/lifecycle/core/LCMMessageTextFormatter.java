package com.oracle.weblogic.lifecycle.core;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class LCMMessageTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public LCMMessageTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "com.oracle.weblogic.lifecycle.core.LCMMessageTextLocalizer", LCMMessageTextFormatter.class.getClassLoader());
   }

   public LCMMessageTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "com.oracle.weblogic.lifecycle.core.LCMMessageTextLocalizer", LCMMessageTextFormatter.class.getClassLoader());
   }

   public static LCMMessageTextFormatter getInstance() {
      return new LCMMessageTextFormatter();
   }

   public static LCMMessageTextFormatter getInstance(Locale l) {
      return new LCMMessageTextFormatter(l);
   }

   public String errorLoadingLCMConfig(String arg0) {
      String id = "errorLoadingLCMConfig";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidPersistenceType() {
      String id = "invalidPersistenceType";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String databaseHandlerUnavailable() {
      String id = "databaseHandlerUnavailable";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String lcmConfigFileNotFound() {
      String id = "lcmConfigFileNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String lcmConfigForDBPersistenceNotFound(String arg0) {
      String id = "lcmConfigForDBPersistenceNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String environmentExists(String arg0) {
      String id = "environmentExists";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String environmentConfigNotFound() {
      String id = "environmentConfigNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String lcmConfigNotFound() {
      String id = "lcmConfigNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionNotFound(String arg0) {
      String id = "partitionNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimeNotFound(String arg0) {
      String id = "runtimeNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimesNotFound() {
      String id = "runtimesNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String envNotFound(String arg0) {
      String id = "envNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullEnvForSync() {
      String id = "nullEnvForSync";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String pluginLoadFailed(String arg0) {
      String id = "pluginLoadFailed";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionPluginNotFound(String arg0) {
      String id = "partitionPluginNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimePluginNotFound(String arg0) {
      String id = "runtimePluginNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionExistsInLCM(String arg0, String arg1) {
      String id = "partitionExistsInLCM";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionCreatingPartitionConfiguration() {
      String id = "exceptionCreatingPartitionConfiguration";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotDeleteGlobalPartition() {
      String id = "cannotDeleteGlobalPartition";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotDeletePartitionReferencedEnv(String arg0) {
      String id = "cannotDeletePartitionReferencedEnv";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionWithSpecifiedNameNotFound(String arg0) {
      String id = "partitionWithSpecifiedNameNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidEnvironmentOperation(String arg0, String arg1) {
      String id = "invalidEnvironmentOperation";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionWhenWaitingForTaskCompletion() {
      String id = "exceptionWhenWaitingForTaskCompletion";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullPropertiesInLifecycleTaskPoll() {
      String id = "nullPropertiesInLifecycleTaskPoll";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidOperationInLifecycleTaskPoll() {
      String id = "invalidOperationInLifecycleTaskPoll";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionCallingOTDRuntimePluginStart() {
      String id = "exceptionCallingOTDRuntimePluginStart";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionCallingPartitionPluginStart() {
      String id = "exceptionCallingPartitionPluginStart";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionOnCreate() {
      String id = "exceptionOnCreate";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionOnScaling() {
      String id = "exceptionOnScaling";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionCreatingRuntime(String arg0) {
      String id = "exceptionCreatingRuntime";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionDeletingRuntime(String arg0) {
      String id = "exceptionDeletingRuntime";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimesConfigNotFound() {
      String id = "runtimesConfigNotFound";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String opNotAllowedForRuntime(String arg0) {
      String id = "opNotAllowedForRuntime";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionSettingUpFileWatcherLCMConfig() {
      String id = "exceptionSettingUpFileWatcherLCMConfig";
      String subsystem = "LCM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimeWithHostPortExists(String arg0, String arg1) {
      String id = "runtimeWithHostPortExists";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String runtimeWithNameExists(String arg0) {
      String id = "runtimeWithNameExists";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotLoadConfigFile(String arg0, long arg1) {
      String id = "cannotLoadConfigFile";
      String subsystem = "LCM";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
