package weblogic.logging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.logging.LogVariablesImpl;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryException;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.configuration.LogMBean;

public class LogFilter implements Filter, PropertyChangeListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugLoggingConfiguration");
   public static final String FILE_FILTER_ATTR = "LogFileFilter";
   public static final String STDOUT_FILTER_ATTR = "StdoutFilter";
   public static final String MEMORY_FILTER_ATTR = "MemoryBufferFilter";
   public static final String DOMAIN_FILTER_ATTR = "DomainLogBroadcastFilter";
   private static final String FILTER_ATTR = "FilterExpression";
   private Query filterQuery = null;
   private String filterAttrName;
   private LogFilterMBean filterConfig = null;

   public LogFilter(LogMBean log, String logFilterAttr, LogFilterMBean filterMBean) {
      this.filterConfig = filterMBean;
      if (this.filterConfig != null) {
         this.filterConfig.addPropertyChangeListener(this);
      }

      this.filterAttrName = logFilterAttr;
      log.addPropertyChangeListener(this);
      this.initialize();
   }

   public boolean isLoggable(LogRecord record) {
      WLLogRecord rec = WLLogger.normalizeLogRecord(record);
      return this.filterLogEntry(rec);
   }

   public boolean filterLogEntry(LogEntry logEntry) {
      if (this.filterQuery != null) {
         try {
            return this.filterQuery.executeQuery(LogVariablesImpl.getInstance().getLogVariablesResolver(logEntry));
         } catch (QueryException var3) {
            return true;
         }
      } else {
         return true;
      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      String attrName = evt.getPropertyName();
      Object oldValue = evt.getOldValue();
      Object newValue = evt.getNewValue();
      if (attrName.equals("FilterExpression")) {
         this.initialize();
      } else if (attrName.equals(this.filterAttrName)) {
         if (this.filterConfig != null) {
            this.filterConfig.removePropertyChangeListener(this);
         }

         this.filterConfig = (LogFilterMBean)newValue;
         if (this.filterConfig != null) {
            this.filterConfig.addPropertyChangeListener(this);
         }

         this.initialize();
      }

   }

   private void initialize() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Initializing filter for " + this.filterAttrName);
      }

      if (this.filterConfig != null) {
         this.filterQuery = (Query)this.filterConfig.getQuery();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Filter expression is " + this.filterConfig.getFilterExpression());
         }
      } else {
         this.filterQuery = null;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Filter expression is null");
         }
      }

   }

   public String toString() {
      String expr = this.filterConfig == null ? "" : this.filterConfig.getFilterExpression();
      StringBuilder buf = new StringBuilder();
      buf.append("The filter is for attribute = " + this.filterAttrName + " with expression " + expr);
      return buf.toString();
   }
}
