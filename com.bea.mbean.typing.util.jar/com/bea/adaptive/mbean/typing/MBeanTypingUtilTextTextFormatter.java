package com.bea.adaptive.mbean.typing;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class MBeanTypingUtilTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public MBeanTypingUtilTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.mbean.typing.MBeanTypingUtilTextTextLocalizer", MBeanTypingUtilTextTextFormatter.class.getClassLoader());
   }

   public MBeanTypingUtilTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "com.bea.adaptive.mbean.typing.MBeanTypingUtilTextTextLocalizer", MBeanTypingUtilTextTextFormatter.class.getClassLoader());
   }

   public static MBeanTypingUtilTextTextFormatter getInstance() {
      return new MBeanTypingUtilTextTextFormatter();
   }

   public static MBeanTypingUtilTextTextFormatter getInstance(Locale l) {
      return new MBeanTypingUtilTextTextFormatter(l);
   }

   public String getDebugMessageLabel() {
      String id = "DebugMessageLabel";
      String subsystem = "ObserverJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugModuleLabel() {
      String id = "DebugModuleLabel";
      String subsystem = "ObserverJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRegistrationLabel() {
      String id = "RegistrationLabel";
      String subsystem = "ObserverJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeregistrationLabel() {
      String id = "DeregistrationLabel";
      String subsystem = "ObserverJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoExecutorProvidedText() {
      String id = "NoExecutorProvidedText";
      String subsystem = "ObserverJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
