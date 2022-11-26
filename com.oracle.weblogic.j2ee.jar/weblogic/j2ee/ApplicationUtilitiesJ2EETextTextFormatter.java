package weblogic.j2ee;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ApplicationUtilitiesJ2EETextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ApplicationUtilitiesJ2EETextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2ee.ApplicationUtilitiesJ2EETextTextLocalizer", ApplicationUtilitiesJ2EETextTextFormatter.class.getClassLoader());
   }

   public ApplicationUtilitiesJ2EETextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.j2ee.ApplicationUtilitiesJ2EETextTextLocalizer", ApplicationUtilitiesJ2EETextTextFormatter.class.getClassLoader());
   }

   public static ApplicationUtilitiesJ2EETextTextFormatter getInstance() {
      return new ApplicationUtilitiesJ2EETextTextFormatter();
   }

   public static ApplicationUtilitiesJ2EETextTextFormatter getInstance(Locale l) {
      return new ApplicationUtilitiesJ2EETextTextFormatter(l);
   }

   public String incompatibleAnnotationAttributes(String arg0, String arg1, String arg2) {
      String id = "INCOMPATIBLE_ANNOTATION_ATTRIBUTES";
      String subsystem = "J2EE Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incompatibleAnnotations(String arg0, String arg1) {
      String id = "INCOMPATIBLE_ANNOTATIONS";
      String subsystem = "J2EE Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incompatibleAnnotationProperties(String arg0, String arg1, String arg2) {
      String id = "INCOMPATIBLE_ANNOTATION_PROPERTIES";
      String subsystem = "J2EE Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
