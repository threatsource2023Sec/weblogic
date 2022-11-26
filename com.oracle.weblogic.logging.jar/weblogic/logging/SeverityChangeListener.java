package weblogic.logging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Handler;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.LogMBean;

public class SeverityChangeListener implements PropertyChangeListener {
   public static final String STDOUT_ATTR = "StdoutSeverity";
   public static final String FILE_ATTR = "LogFileSeverity";
   public static final String MEMORY_BUFFER_ATTR = "MemoryBufferSeverity";
   public static final String DOMAIN_LOG_BROADCAST_ATTR = "DomainLogBroadcastSeverity";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugLoggingConfiguration");
   private String severityAttrName;
   protected Object logDest;

   public SeverityChangeListener(LogMBean logMBean, String attrName, Object logDest) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Adding listener on " + logMBean.toString());
      }

      logMBean.addPropertyChangeListener(this);
      this.severityAttrName = attrName;
      this.logDest = logDest;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals(this.severityAttrName)) {
         String severity = (String)evt.getNewValue();
         this.setLevel(severity);
      }

   }

   public void setLevel(String severity) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Setting severity = " + severity + " on the " + this.logDest.getClass().getName() + " Handler");
      }

      int severityLevel = Severities.severityStringToNum(severity);
      ((Handler)this.logDest).setLevel(WLLevel.getLevel(severityLevel));
   }
}
