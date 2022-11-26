package weblogic.diagnostics.l18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsServicesTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsServicesTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.l18n.DiagnosticsServicesTextTextLocalizer", DiagnosticsServicesTextTextFormatter.class.getClassLoader());
   }

   public DiagnosticsServicesTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.l18n.DiagnosticsServicesTextTextLocalizer", DiagnosticsServicesTextTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsServicesTextTextFormatter getInstance() {
      return new DiagnosticsServicesTextTextFormatter();
   }

   public static DiagnosticsServicesTextTextFormatter getInstance(Locale l) {
      return new DiagnosticsServicesTextTextFormatter(l);
   }

   public String getClusterBeanNotFoundText(String arg0) {
      String id = "ClusterBeanNotFoundText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleInstanceAlreadyExistsText(String arg0) {
      String id = "ModuleInstanceAlreadyExistsText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModulePrepareCalledBeforeInitializedText(String arg0, String arg1, String arg2) {
      String id = "ModulePrepareCalledBeforeInitializedText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleActivateCalledBeforePreparedText(String arg0, String arg1, String arg2) {
      String id = "ModuleActivateCalledBeforePreparedText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToCreateRuntimeControlText(String arg0) {
      String id = "UnableToCreateRuntimeControlText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSmartRuleClusterNameEmptyOrNullText() {
      String id = "SmartRuleClusterNameEmptyOrNullText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchModuleWatchManagerNotConfiguredText() {
      String id = "WatchModuleWatchManagerNotConfiguredText";
      String subsystem = "DiagnosticsServices";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
