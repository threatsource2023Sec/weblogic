package weblogic.diagnostics.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsTextWatchTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsTextWatchTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.i18n.DiagnosticsTextWatchTextLocalizer", DiagnosticsTextWatchTextFormatter.class.getClassLoader());
   }

   public DiagnosticsTextWatchTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.i18n.DiagnosticsTextWatchTextLocalizer", DiagnosticsTextWatchTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsTextWatchTextFormatter getInstance() {
      return new DiagnosticsTextWatchTextFormatter();
   }

   public static DiagnosticsTextWatchTextFormatter getInstance(Locale l) {
      return new DiagnosticsTextWatchTextFormatter(l);
   }

   public String getUnknownWatchVariableExceptionText(String arg0, String arg1) {
      String id = "UnknownWatchVariableExceptionText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullWatchVariableAttributeNameText(String arg0, String arg1) {
      String id = "NullWatchVariableAttributeNameText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncompleteWatchVariableConsoleText() {
      String id = "IncompleteWatchVariableConsoleText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmptyWatchAttributeConsoleText() {
      String id = "EmptyWatchAttributeConsoleText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToRollbackWatchUpdateText() {
      String id = "UnableToRollbackWatchUpdateText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCalendarSchedulingNotAllowedForWLDFLanguageText() {
      String id = "CalendarSchedulingNotAllowedForWLDFLanguageText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotUseDomainBeanOnManagedServer() {
      String id = "CannotUseDomainBeanOnManagedServer";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeBeanQueryIOException(String arg0, String arg1) {
      String id = "DomainRuntimeBeanQueryIOException";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidActionConfigPropertyWriteMethodText(String arg0, int arg1) {
      String id = "InvalidActionConfigPropertyWriteMethod";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchExpressionBeanNotEnabled(String arg0) {
      String id = "WatchExpressionBeanNotEnabled";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchBeanNotFoundText(String arg0) {
      String id = "WatchBeanNotFoundText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCircularWatchRuleExpressionDetectedText(String arg0) {
      String id = "CircularWatchRuleExpressionDetectedText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalActionServiceTypeNameText(String arg0) {
      String id = "IllegalActionServiceTypeNameText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalActionPropertiesText(String arg0, String arg1, String arg2, String arg3) {
      String id = "IllegalActionPropertiesText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidScriptActionScriptPath(String arg0, String arg1) {
      String id = "InvalidScriptActionScriptPath";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScalingActionsStateErrorText(String arg0) {
      String id = "ScalingActionsStateErrorText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvocationContextNotFoundText(String arg0) {
      String id = "InvocationContextNotFoundText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCreateScalingTaskText(String arg0) {
      String id = "CouldNotCreateScalingTaskText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLDFLanguageRuleExpressionCanNotBeNullText() {
      String id = "WLDFLanguageRuleExpressionCanNotBeNullText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogActionUnknownLogLevelText(String arg0) {
      String id = "LogActionUnknownLogLevelText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLogActionConfig() {
      String id = "InvalidLogActionConfig";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogActionNoSubsystemNameSpecifiedText(String arg0) {
      String id = "LogActionNoSubsystemNameSpecifiedText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogActionSeverityNullText(String arg0) {
      String id = "LogActionSeverityNullText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalLogActionSeverityText(String arg0, String arg1) {
      String id = "IllegalLogActionSeverityText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogActionNullOrEmptyMessageText(String arg0) {
      String id = "LogActionNullOrEmptyMessageText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalWatchScheduleIncrementPattern(String arg0) {
      String id = "IllegalWatchScheduleIncrementPattern";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalWatchScheduleIntervalValue(String arg0) {
      String id = "IllegalWatchScheduleIntervalValue";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLDFExpressionLanguageNotAllowedText() {
      String id = "WLDFExpressionLanguageNotAllowedText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExpressionLanguageCanNotBeNullOrEmptyText() {
      String id = "ExpressionLanguageCanNotBeNullOrEmptyText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalPolicyExpressionLanguageText(String arg0) {
      String id = "IllegalPolicyExpressionLanguageText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToFindServerRuntimeText() {
      String id = "UnableToFindServerRuntimeText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToFindServerText() {
      String id = "UnableToFindServerText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToCreateDirText(String arg0) {
      String id = "UnableToCreateDirText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToDeleteFileText(String arg0) {
      String id = "FailedToDeleteFileText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidScalingActionClusterNameText(String arg0) {
      String id = "InvalidScalingActionClusterNameText";
      String subsystem = "DiagnosticsWatch";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
