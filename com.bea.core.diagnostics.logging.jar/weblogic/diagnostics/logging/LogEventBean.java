package weblogic.diagnostics.logging;

import com.bea.logging.BaseLogEntry;
import com.bea.logging.ThrowableWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import weblogic.utils.PlatformConstants;

public class LogEventBean implements LogVariablesConstants {
   private BaseLogEntry delegate = null;

   public void setBaseLogEntry(BaseLogEntry logEntry) {
      this.delegate = logEntry;
   }

   public String getFormattedDate() {
      return this.delegate != null ? this.delegate.getFormattedDate() : "";
   }

   public String getMessageId() {
      return this.delegate != null ? this.delegate.getId() : "";
   }

   public String getMachineName() {
      return this.delegate != null ? this.delegate.getMachineName() : "";
   }

   public String getServerName() {
      return this.delegate != null ? this.delegate.getServerName() : "";
   }

   public String getThreadName() {
      return this.delegate != null ? this.delegate.getThreadName() : "";
   }

   public String getUserId() {
      return this.delegate != null ? this.delegate.getUserId() : "";
   }

   public String getTransactionId() {
      return this.delegate != null ? this.delegate.getTransactionId() : "";
   }

   public int getSeverity() {
      return this.delegate != null ? this.delegate.getSeverity() : -1;
   }

   public String getSeverityString() {
      return this.delegate != null ? this.delegate.getSeverityString() : "";
   }

   public String getSubsystem() {
      return this.delegate != null ? this.delegate.getSubsystem() : "";
   }

   public long getTimestamp() {
      return this.delegate != null ? this.delegate.getTimestamp() : -1L;
   }

   public String getLogMessage() {
      return this.delegate != null ? this.delegate.getLogMessage() : "";
   }

   public String getDiagnosticContextId() {
      return this.delegate != null ? this.delegate.getDiagnosticContextId() : "";
   }

   public Properties getSupplementalAttributes() {
      return this.delegate != null ? this.delegate.getSupplementalAttributes() : new Properties();
   }

   public String getPartitionId() {
      return this.delegate != null ? this.delegate.getPartitionId() : "";
   }

   public String getPartitionName() {
      return this.delegate != null ? this.delegate.getPartitionName() : "";
   }

   public Map getBeanData() {
      HashMap props = new HashMap();
      props.put("DATE", this.getFormattedDate());
      props.put("MSGID", this.getMessageId());
      props.put("MACHINE", this.getMachineName());
      props.put("SERVER", this.getServerName());
      props.put("THREAD", this.getThreadName());
      props.put("USERID", this.getUserId());
      props.put("TXID", this.getTransactionId());
      props.put("SEVERITY", this.getSeverityString());
      props.put("SUBSYSTEM", this.getSubsystem());
      props.put("TIMESTAMP", Long.toString(this.getTimestamp()));
      String message = this.getLogMessage();
      ThrowableWrapper wrapper = this.delegate != null ? this.delegate.getThrowableWrapper() : null;
      if (wrapper != null) {
         message = message + PlatformConstants.EOL + wrapper;
      }

      props.put("MESSAGE", message);
      props.put("CONTEXTID", this.getDiagnosticContextId());
      props.put("SUPP_ATTRS", this.getSupplementalAttributes().toString());
      return props;
   }
}
