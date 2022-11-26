package weblogic.jms.module.validators;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class JMSTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public JMSTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jms.module.validators.JMSTextTextLocalizer", JMSTextTextFormatter.class.getClassLoader());
   }

   public JMSTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.jms.module.validators.JMSTextTextLocalizer", JMSTextTextFormatter.class.getClassLoader());
   }

   public static JMSTextTextFormatter getInstance() {
      return new JMSTextTextFormatter();
   }

   public static JMSTextTextFormatter getInstance(Locale l) {
      return new JMSTextTextFormatter(l);
   }

   public String getIllegalTimeToDeliverOverride() {
      String id = "IllegalTimeToDeliverOverride";
      String subsystem = "JMS Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalTimeToDeliverOverrideWithException(String arg0) {
      String id = "IllegalTimeToDeliverOverrideWithException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidMulticastAddressException(String arg0, String arg1) {
      String id = "InvalidMulticastAddressException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFJNDIConflictWithDefaultsException(String arg0) {
      String id = "JMSCFJNDIConflictWithDefaultsException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSDestJNDINameConflictException(String arg0) {
      String id = "JMSDestJNDINameConflictException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFJNDINameConflictException(String arg0) {
      String id = "JMSCFJNDINameConflictException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSJNDINameConflictException(String arg0) {
      String id = "JMSJNDINameConflictException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidJMSMessagesMaximum() {
      String id = "InvalidJMSMessagesMaximum";
      String subsystem = "JMS Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String InvalidMulticastAddress(String arg0) {
      String id = "InvalidMulticastAddress";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFConflictWithDefaultsException(String arg0) {
      String id = "JMSCFConflictWithDefaultsException";
      String subsystem = "JMS Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidTopicSubscriptionMessagesLimit() {
      String id = "InvalidTopicSubscriptionMessagesLimit";
      String subsystem = "JMS Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
