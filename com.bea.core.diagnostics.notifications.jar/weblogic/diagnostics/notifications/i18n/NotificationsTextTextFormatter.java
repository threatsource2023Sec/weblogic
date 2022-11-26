package weblogic.diagnostics.notifications.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NotificationsTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NotificationsTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.notifications.i18n.NotificationsTextTextLocalizer", NotificationsTextTextFormatter.class.getClassLoader());
   }

   public NotificationsTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.notifications.i18n.NotificationsTextTextLocalizer", NotificationsTextTextFormatter.class.getClassLoader());
   }

   public static NotificationsTextTextFormatter getInstance() {
      return new NotificationsTextTextFormatter();
   }

   public static NotificationsTextTextFormatter getInstance(Locale l) {
      return new NotificationsTextTextFormatter(l);
   }

   public String getSMTPDefaultBodyLine(String arg0, String arg1) {
      String id = "SMTPDefaultBodyLine";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultJMXNotificationMessage(String arg0, long arg1) {
      String id = "DefaultJMXNotificationMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidJMSDestinationText() {
      String id = "InvalidJMSDestinationText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMXProducerMBeanNotSetText() {
      String id = "JMXProducerMBeanNotSetText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanServerNotProvidedText() {
      String id = "MBeanServerNotProvidedText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSMTPNotificationDefaultSubjectText() {
      String id = "SMTPNotificationDefaultSubjectText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidSMTPNotificationRecipientList() {
      String id = "InvalidSMTPNotificationRecipientList";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSNMPAgentUnavailableText(String arg0) {
      String id = "SNMPAgentUnavailableText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMailSessionNotSetText() {
      String id = "MailSessionNotSetText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToLookUpJMXNotifierInstanceText(String arg0) {
      String id = "UnableToLookUpJMXNotifierInstanceText";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
