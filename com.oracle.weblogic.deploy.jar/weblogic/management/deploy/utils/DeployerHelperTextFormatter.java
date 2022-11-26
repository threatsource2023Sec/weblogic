package weblogic.management.deploy.utils;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DeployerHelperTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DeployerHelperTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.utils.DeployerHelperTextLocalizer", DeployerHelperTextFormatter.class.getClassLoader());
   }

   public DeployerHelperTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.deploy.utils.DeployerHelperTextLocalizer", DeployerHelperTextFormatter.class.getClassLoader());
   }

   public static DeployerHelperTextFormatter getInstance() {
      return new DeployerHelperTextFormatter();
   }

   public static DeployerHelperTextFormatter getInstance(Locale l) {
      return new DeployerHelperTextFormatter(l);
   }

   public String exceptionNoSuchSource(String arg0) {
      String id = "EXCEPTION_NO_SUCH_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionArchivingFile(String arg0, String arg1) {
      String id = "EXCEPTION_ARCHIVING_FILE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionUploadingSource(String arg0, String arg1, String arg2) {
      String id = "EXCEPTION_UPLOADING_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionUploadingFiles(String arg0, String arg1) {
      String id = "EXCEPTION_UPLOADING_FILES";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorUploading(String arg0) {
      String id = "ERROR_UPLOADING";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
