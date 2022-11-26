package weblogic.diagnostics.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsTextHarvesterTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsTextHarvesterTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextLocalizer", DiagnosticsTextHarvesterTextFormatter.class.getClassLoader());
   }

   public DiagnosticsTextHarvesterTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextLocalizer", DiagnosticsTextHarvesterTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsTextHarvesterTextFormatter getInstance() {
      return new DiagnosticsTextHarvesterTextFormatter();
   }

   public static DiagnosticsTextHarvesterTextFormatter getInstance(Locale l) {
      return new DiagnosticsTextHarvesterTextFormatter(l);
   }

   public String getDomainRuntimeNamespaceWarningText(String arg0) {
      String id = "DomainRuntimeNamespaceWarningText";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidHarvesterNamespaceText(String arg0) {
      String id = "InvalidHarvesterNamespaceText";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchedValuesIdNotFoundText(int arg0) {
      String id = "WatchedValuesIdNotFoundText";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidAttributeSpecText() {
      String id = "InvalidAttributeSpecText";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCollectorNotInitializedText() {
      String id = "CollectorNotInitializedText";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterRuntimeIllegalPartitionNameSpecified(String arg0, String arg1) {
      String id = "HarvesterRuntimeIllegalPartitionNameSpecified";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionNameMissingForPartitionUser() {
      String id = "PartitionNameMissingForPartitionUser";
      String subsystem = "DiagnosticsHarvester";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
