package weblogic.i18n.logging;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;

public class LoggingTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public LoggingTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18n.logging.LoggingTextLocalizer", LoggingTextFormatter.class.getClassLoader());
   }

   public LoggingTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.i18n.logging.LoggingTextLocalizer", LoggingTextFormatter.class.getClassLoader());
   }

   public static LoggingTextFormatter getInstance() {
      return new LoggingTextFormatter();
   }

   public static LoggingTextFormatter getInstance(Locale l) {
      return new LoggingTextFormatter(l);
   }

   public String getTextEmergency() {
      String id = "Emergency";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextAlert() {
      String id = "Alert";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextCritical() {
      String id = "Critical";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextNotice() {
      String id = "Notice";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextError() {
      String id = "Error";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextWarning() {
      String id = "Warning";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextInfo() {
      String id = "Info";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextDebug() {
      String id = "Debug";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String consoleSeverityControlHint(String arg0, String arg1) {
      String id = "consoleSeverityControlHint";
      String subsystem = "Logging";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String someConsoleSeverity2Console(String arg0) {
      String id = "someConsoleSeverity2Console";
      String subsystem = "Logging";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String someConsoleSeverity2Log(String arg0) {
      String id = "someConsoleSeverity2Log";
      String subsystem = "Logging";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String everyConsoleSeverity2Console() {
      String id = "everyConsoleSeverity2Console";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String everyConsoleSeverity2Log() {
      String id = "everyConsoleSeverity2Log";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noConsoleSeverity2Console() {
      String id = "noConsoleSeverity2Console";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noConsoleSeverity2Log() {
      String id = "noConsoleSeverity2Log";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainLoggerDoesNotExistMsg() {
      String id = "domainLoggerDoesNotExist";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLog4jLoggerNotAvailableText() {
      String id = "log4jLoggerNotAvailable";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextOff() {
      String id = "Off";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTextTrace() {
      String id = "Trace";
      String subsystem = "Logging";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
