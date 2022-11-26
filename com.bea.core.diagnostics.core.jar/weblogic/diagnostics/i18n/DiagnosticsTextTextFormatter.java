package weblogic.diagnostics.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.i18n.DiagnosticsTextTextLocalizer", DiagnosticsTextTextFormatter.class.getClassLoader());
   }

   public DiagnosticsTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.i18n.DiagnosticsTextTextLocalizer", DiagnosticsTextTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsTextTextFormatter getInstance() {
      return new DiagnosticsTextTextFormatter();
   }

   public static DiagnosticsTextTextFormatter getInstance(Locale l) {
      return new DiagnosticsTextTextFormatter(l);
   }

   public String getSMTPDefaultSubject(String arg0, String arg1, String arg2, String arg3) {
      String id = "SMTPDefaultSubject";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMismatchOperandTypesText(String arg0, String arg1, String arg2) {
      String id = "mismatchOperandTypes";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPatternSyntaxErrorText(String arg0) {
      String id = "patternSyntaxError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidQueryExpressionText(String arg0) {
      String id = "invalidQueryExpression";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownVariableMsg(String arg0) {
      String id = "unknownVariable";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMismatchedNodeTypeMsg(String arg0, String arg1) {
      String id = "mismatchedNodeType";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTHelpDesc() {
      String id = "WLSTHelpDesc";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTExportDesc() {
      String id = "WLSTExportDesc";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTExportSyntax() {
      String id = "WLSTExportSyntax";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTExportExample() {
      String id = "WLSTExportExample";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeNamespaceWarningText(String arg0) {
      String id = "DomainRuntimeNamespaceWarningText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidHarvesterNamespaceText(String arg0) {
      String id = "InvalidHarvesterNamespaceText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadHarvesterVariableName(String arg0, String arg1) {
      String id = "BadHarvesterVariableName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadHarvesterVariableType(String arg0, String arg1, String arg2) {
      String id = "BadHarvesterVariableType";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadHarvesterVariableAttr(String arg0, String arg1, String arg2) {
      String id = "BadHarvesterVariableAttr";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterVariableTypeMismatch(String arg0, String arg1, String arg2, String arg3) {
      String id = "HarvesterVariableTypeMismatch";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidObjectName(String arg0, String arg1, String arg2) {
      String id = "InvalidObjectName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmptyAttributeName(String arg0, String arg1) {
      String id = "EmptyAttributeName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMissingBothTypeAndInstanceName(String arg0, String arg1) {
      String id = "MissingBothTypeAndInstanceName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorsOcurredParsingHarvesterVariableName(String arg0, String arg1, String arg2, int arg3) {
      String id = "ErrorsOcurredParsingHarvesterVariableName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorsOccurredValidatingWatchedValues(String arg0, int arg1) {
      String id = "ErrorsOccurredValidatingWatchedValues";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceLabel() {
      String id = "InstanceLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstancesLabel() {
      String id = "InstancesLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeLabel() {
      String id = "TypeLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypesLabel() {
      String id = "TypesLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeLabel() {
      String id = "AttributeLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributesLabel() {
      String id = "AttributesLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderLabel() {
      String id = "ProviderLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProvidersLabel() {
      String id = "ProvidersLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnabledLabel() {
      String id = "EnabledLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisabledLabel() {
      String id = "DisabledLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvestedLabel() {
      String id = "HarvestedLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnharvestedLabel() {
      String id = "UnharvestedLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluginStateInitialLabel() {
      String id = "PluginStateInitialLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluginStateActiveLabel() {
      String id = "PluginStateActiveLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluginStateShutdownLabel() {
      String id = "PluginStateShutdownLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluginStateUndefinedLabel() {
      String id = "PluginStateUndefinedLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterStateActiveLabel() {
      String id = "HarvesterStateActiveLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterStateInactiveLabel() {
      String id = "HarvesterStateInactiveLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getYesLabel() {
      String id = "YesLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoLabel() {
      String id = "NoLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMaybeLabel() {
      String id = "MaybeLabel";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullParamMessage(String arg0) {
      String id = "NullParamMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidParamMessage(String arg0, String arg1) {
      String id = "InvalidParamMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigLoadingProblemMessage() {
      String id = "ConfigLoadingProblemMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPluginStateMessage(String arg0, String arg1) {
      String id = "InvalidPluginStateMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterNotAvailableMessage() {
      String id = "HarvesterNotAvailableMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAmbiguousTypeNameMessage(String arg0, String arg1, String arg2) {
      String id = "AmbiguousTypeNameMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getItemNotHarvestableMessage(String arg0, String arg1) {
      String id = "ItemNotHarvestableMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoTypeConfigMessage(String arg0) {
      String id = "NoTypeConfigMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAmbiguousInstanceNameMessage(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "AmbiguousInstanceNameMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotHarvestableMessage(String arg0, String arg1, String arg2) {
      String id = "NotHarvestableMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateProviderMessage(String arg0) {
      String id = "DuplicateProviderMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalOperationOnRunningHarvesterMessage() {
      String id = "IllegalOperationOnRunningHarvesterMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceNotRegisteredMessage() {
      String id = "InstanceNotRegisteredMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeNotDefinedMessage() {
      String id = "TypeNotDefinedMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstancesDoNotExistMessage(String arg0) {
      String id = "InstancesDoNotExistMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypesDoNotExistMessage(String arg0) {
      String id = "TypesDoNotExistMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterTypeNotFoundMessage(String arg0) {
      String id = "HarvesterTypeNotFoundMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenericHarvesterProblemMessage() {
      String id = "GenericHarvesterProblemMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoJDBCSystemResourceConfiguredText(String arg0) {
      String id = "NoJDBCSystemResourceConfigured";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCSystemResourceNotTargettedToServer(String arg0, String arg1) {
      String id = "JDBCSystemResourceNotTargettedToServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSNMPNotificationAgentUndefinedError() {
      String id = "SNMPNotificationAgentUndefinedError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMailSessionIsUndefined(String arg0) {
      String id = "MailSessionIsUndefined";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMailSessionIsNotOnServer(String arg0, String arg1) {
      String id = "MailSessionIsNotOnServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndefinedMailRecipients(String arg0, String arg1, String arg2) {
      String id = "UndefinedMailRecipients";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInconsistentTypesInWatchInstVarMessage(String arg0, String arg1, String arg2) {
      String id = "InconsistentTypesInWatchInstVarMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchInstanceMetricNotFoundMessage(String arg0, String arg1, String arg2) {
      String id = "WatchInstanceMetricNotFoundMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchTypeMetricNotFoundMessage(String arg0, String arg1) {
      String id = "WatchTypeMetricNotFoundMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemDuringWatchProcessingMessage() {
      String id = "ProblemDuringWatchProcessingMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConflictingTypesInWatchRulesMessage(String arg0, String arg1, String arg2) {
      String id = "ConflictingTypesInWatchRulesMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModelMBeanDoesNotPublishTypeMessage(String arg0) {
      String id = "ModelMBeanDoesNotPublishTypeMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeIsMarkedUnharvestableMessage(String arg0, String arg1, String arg2) {
      String id = "AttributeIsMarkedUnharvestableMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchListMessage(String arg0) {
      String id = "WatchListMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPendingTaskStatusText() {
      String id = "PendingTaskStatusText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecutingTaskStatusText() {
      String id = "ExecutingTaskStatusText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedTaskStatusText() {
      String id = "CompletedTaskStatusText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCancelledTaskStatusText() {
      String id = "CancelledTaskStatusText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedTaskStatusText() {
      String id = "FailedTaskStatusText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDataRetirementTaskDescriptionText() {
      String id = "DataRetirementTaskDescriptionText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSizeBasedDataRetirementTaskDescriptionText() {
      String id = "SizeBasedDataRetirementTaskDescriptionText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAgeBasedDataRetirementTaskDescriptionText() {
      String id = "AgeBasedDataRetirementTaskDescriptionText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateRetirementsErrorText(String arg0) {
      String id = "DuplicateRetirementsErrorText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidArchiveNameForDataRetirementText(String arg0, String arg1) {
      String id = "InvalidArchiveNameForDataRetirementText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidRetirementTimeText(String arg0, int arg1) {
      String id = "InvalidRetirementTimeText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidHarvesterInstanceNameText(String arg0) {
      String id = "InvalidHarvesterInstanceNameText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidHarvesterInstanceNamePatternText(String arg0) {
      String id = "InvalidHarvesterInstanceNamePatternText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAccessorClientIOException(int arg0) {
      String id = "AccessorClientIOException";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAccessorClientInvalidImageName(String arg0) {
      String id = "AccessorClientInvalidImageName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAccessorClientInvalidImageEntryName(String arg0, String arg1) {
      String id = "AccessorClientInvalidImageEntryName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAccessorClientImageEntryNotFound(String arg0, String arg1) {
      String id = "AccessorClientImageEntryNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRuntimeControlAlreadyExists(String arg0) {
      String id = "RuntimeControlAlreadyExists";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttemptedToDeleteDomainConfiguredResource(String arg0) {
      String id = "AttemptedToDeleteDomainConfiguredResource";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInputParamsText() {
      String id = "InputParams";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportingDiagnosticDataMsgText(String arg0) {
      String id = "ExportingDiagnosticDataMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportDiagnosticDataSuccessMsgText() {
      String id = "ExportDiagnosticDataSuccessMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoggingImageSourceTimedOutMsgText(String arg0) {
      String id = "LoggingImageSourceTimedOutMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDiagnosticDataExportFormatMsg(String arg0) {
      String id = "InvalidDiagnosticDataExportFormatMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoPatchesSpecifiedWithDebugPatchActivation() {
      String id = "NoPatchesSpecifiedWithDebugPatchActivation";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugPatchDoesnotExist(String arg0) {
      String id = "DebugPatchDoesnotExist()";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugPatchAdditionalInfo() {
      String id = "DebugPatchAdditionalInfo()";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugPatchInfoNotAvailable(String arg0) {
      String id = "DebugPatchInfoNotAvailable()";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstrumentationAgentNotAvailable() {
      String id = "InstrumentationAgentNotAvailable";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreNotAvailable(String arg0) {
      String id = "StoreNotAvailable";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getArchiveDoesNotSupportUpdate(String arg0) {
      String id = "ArchiveDoesNotSupportUpdate";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationNotFoundDuringPatchActivation(String arg0) {
      String id = "ApplicationNotFoundDuringPatchActivation";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationInPartitionNotFoundDuringPatchActivation(String arg0, String arg1) {
      String id = "ApplicationInPartitionNotFoundDuringPatchActivation";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleInApplicationNotFoundDuringPatchActivation(String arg0, String arg1) {
      String id = "ModuleInApplicationNotFoundDuringPatchActivation";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleInApplicationInPartitionNotFoundDuringPatchActivation(String arg0, String arg1, String arg2) {
      String id = "ModuleInApplicationInPartitionNotFoundDuringPatchActivation";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPatchActivationFileDoesNotExist(String arg0, String arg1) {
      String id = "PatchActivationFileDoesNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidAccessorNameForRemoval(String arg0) {
      String id = "InvalidAccessorNameForRemoval";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQueryTimestampRangeMsg(String arg0, String arg1) {
      String id = "QueryTimestampRangeMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDumpDirectory(String arg0, String arg1) {
      String id = "InvalidDumpDirectory";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageFileDoesNotExist(String arg0) {
      String id = "ImageFileDoesNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageFileNullOrEmpty() {
      String id = "ImageFileNullOrEmpty";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
