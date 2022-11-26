package weblogic.logging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import weblogic.i18n.logging.LoggingTextFormatter;
import weblogic.management.configuration.KernelMBean;

public class StdoutSeverityListener implements PropertyChangeListener {
   private static StdoutSeverityListener singleton = null;
   private final LoggingTextFormatter formatter = new LoggingTextFormatter();
   private final KernelMBean config;
   private final ConsoleHandler console;
   private int stdoutSeverityLevel;

   public static StdoutSeverityListener getStdoutSeverityListener(KernelMBean config) {
      if (singleton == null) {
         singleton = new StdoutSeverityListener(config);
      }

      return singleton;
   }

   private StdoutSeverityListener(KernelMBean config) {
      this.config = config;
      this.console = new ConsoleHandler(config);
      this.initializeSeverityLevel();
   }

   int getStdoutSeverityLevel() {
      return this.stdoutSeverityLevel;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      this.initializeSeverityLevel();
      if ("StdoutSeverity".equals(evt.getPropertyName())) {
         this.informUsersOfSeverityLevel();
      }

   }

   private void initializeSeverityLevel() {
      this.stdoutSeverityLevel = Severities.severityStringToNum(this.config.getLog().getStdoutSeverity());
   }

   private void informUsersOfSeverityLevel() {
      if (!this.config.isStdoutEnabled()) {
         String message = this.formatter.noConsoleSeverity2Log();
         MessageLogger.log(WLLevel.INFO, "Logging", message);
      } else {
         int severity = this.config.getStdoutSeverityLevel();
         String message = null;
         if (severity >= 64) {
            message = this.formatter.everyConsoleSeverity2Log();
         } else {
            String severityString = SeverityI18N.severityNumToString(severity, Locale.getDefault());
            message = this.formatter.someConsoleSeverity2Log(severityString);
         }

         MessageLogger.log(WLLevel.INFO, "Logging", message);
      }
   }
}
