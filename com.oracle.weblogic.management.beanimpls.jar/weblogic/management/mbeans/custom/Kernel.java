package weblogic.management.mbeans.custom;

import weblogic.common.internal.VersionInfo;
import weblogic.logging.LoggingConfigurationProcessor;
import weblogic.logging.Severities;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.KernelDebugMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class Kernel extends ConfigurationMBeanCustomizer {
   private static final boolean DEBUG = false;
   private static final String STDOUT_SEVERITY = "StdoutSeverity";
   private int stdoutSeverityLevel = 32;
   private boolean stdoutDebugEnabled = false;
   private boolean stdoutEnabled = true;
   private String stdoutFormat = "standard";
   private boolean stdoutLogStack = true;
   static final VersionInfo diabloVersion = new VersionInfo("9.0.0.0");

   public Kernel(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public int getStdoutSeverityLevel() {
      return this.isDelegateModeEnabled() ? Severities.severityStringToNum(this.getLogMBean().getStdoutSeverity()) : this.stdoutSeverityLevel;
   }

   public void setStdoutSeverityLevel(int severityLevel) {
      this.stdoutSeverityLevel = severityLevel;
      if (this.isDelegateModeEnabled()) {
         this.updateLogMBeanStdoutSeverity();
      }

   }

   public boolean isStdoutDebugEnabled() {
      if (this.isDelegateModeEnabled()) {
         return Severities.severityStringToNum(this.getLogMBean().getStdoutSeverity()) >= 128;
      } else {
         return this.stdoutDebugEnabled;
      }
   }

   public void setStdoutDebugEnabled(boolean choice) {
      this.stdoutDebugEnabled = choice;
      if (this.isDelegateModeEnabled()) {
         this.updateLogMBeanStdoutSeverity();
      }

   }

   public boolean isStdoutEnabled() {
      if (this.isDelegateModeEnabled()) {
         return Severities.severityStringToNum(this.getLogMBean().getStdoutSeverity()) > 0;
      } else {
         return this.stdoutEnabled;
      }
   }

   public void setStdoutEnabled(boolean choice) {
      this.stdoutEnabled = choice;
      if (this.isDelegateModeEnabled()) {
         this.updateLogMBeanStdoutSeverity();
      }

   }

   private void updateLogMBeanStdoutSeverity() {
      LogMBean log = ((KernelMBean)this.getMbean()).getLog();
      String severity = LoggingConfigurationProcessor.getNormalizedStdoutSeverity(this.stdoutEnabled, this.stdoutSeverityLevel, this.stdoutDebugEnabled);
      log.setStdoutSeverity(severity);
      if (severity.equals("Notice")) {
         log.unSet("StdoutSeverity");
      }

   }

   public String getStdoutFormat() {
      return this.isDelegateModeEnabled() ? this.getLogMBean().getStdoutFormat() : this.stdoutFormat;
   }

   public void setStdoutFormat(String format) {
      this.stdoutFormat = format;
      if (this.isDelegateModeEnabled()) {
         LogMBean log = ((KernelMBean)this.getMbean()).getLog();
         log.setStdoutFormat(format);
         if (format.equals("standard")) {
            log.unSet("StdoutFormat");
         }
      }

   }

   public boolean isStdoutLogStack() {
      return this.isDelegateModeEnabled() ? this.getLogMBean().isStdoutLogStack() : this.stdoutLogStack;
   }

   public void setStdoutLogStack(boolean stack) {
      this.stdoutLogStack = stack;
      if (this.isDelegateModeEnabled()) {
         LogMBean log = ((KernelMBean)this.getMbean()).getLog();
         log.setStdoutLogStack(stack);
         if (stack) {
            log.unSet("StdoutLogStack");
         }
      }

   }

   private LogMBean getLogMBean() {
      return ((KernelMBean)this.getMbean()).getLog();
   }

   public KernelDebugMBean getKernelDebug() {
      return ((ServerTemplateMBean)this.getMbean()).getServerDebug();
   }

   protected boolean isDelegateModeEnabled() {
      DomainMBean root = (DomainMBean)this.getMbean().getDescriptor().getRootBean();
      String configurationVersionString = root.getConfigurationVersion();
      if (configurationVersionString == null) {
         return false;
      } else {
         VersionInfo configurationVersion = new VersionInfo(configurationVersionString);
         return !configurationVersion.earlierThan(diabloVersion);
      }
   }
}
