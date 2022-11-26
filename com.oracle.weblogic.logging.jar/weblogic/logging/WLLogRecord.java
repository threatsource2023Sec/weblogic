package weblogic.logging;

import com.bea.logging.BaseLogRecord;
import com.bea.logging.LogLevel;
import com.bea.logging.ThrowableWrapper;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import weblogic.i18n.logging.LogMessage;

public class WLLogRecord extends BaseLogRecord implements LogEntry, Externalizable {
   private static final long serialVersionUID = -8930788966766077378L;
   private transient ThrowableInfo thInfo;

   public WLLogRecord() {
      this(WLLevel.INFO, "", true);
   }

   public WLLogRecord(Level level, String msg) {
      this(level, msg, true);
   }

   public WLLogRecord(Level level, String msg, boolean initialize) {
      super(LogLevel.getSeverity(level), msg);
      this.thInfo = null;
      if (initialize) {
         LogEntryInitializer.initializeLogEntry(this);
      }

   }

   public WLLogRecord(Level level, String msg, Throwable th) {
      this(level, msg);
      this.setThrown(th);
   }

   public WLLogRecord(LogMessage logMsg) {
      super(logMsg);
      this.thInfo = null;
      LogEntryInitializer.initializeLogEntry(this);
   }

   public ThrowableInfo getThrowableInfo() {
      ThrowableWrapper thWrapper = this.getThrowableWrapper();
      if (thWrapper != null && thWrapper.getThrowable() != null) {
         this.thInfo = new ThrowableInfo(thWrapper.getThrowable());
      }

      return this.thInfo;
   }

   public void setThrowableInfo(ThrowableInfo thInfo) {
      this.thInfo = thInfo;
      if (thInfo != null && thInfo.getThrowable() != null) {
         super.setThrown(thInfo.getThrowable());
      }

   }

   /** @deprecated */
   @Deprecated
   public static WLLogRecord normalizeLogRecord(LogRecord lr) {
      return WLLogger.normalizeLogRecord(lr);
   }

   public String toString() {
      return this.getMessage();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.getSubsystem());
      out.writeUTF(this.getId());
      String msg = this.getLogMessage();
      byte[] messageBytes = msg != null ? msg.getBytes("UTF-8") : new byte[0];
      out.writeInt(messageBytes.length);
      out.write(messageBytes);
      out.writeInt(this.getSeverity());
      out.writeLong(this.getTimestamp());
      out.writeObject(this.getThrowableWrapper());
      out.writeUTF(this.getMachineName());
      out.writeUTF(this.getServerName());
      out.writeUTF(this.getThreadName());
      out.writeUTF(this.getTransactionId());
      out.writeUTF(this.getDiagnosticContextId());
      out.writeUTF(this.getUserId());
      out.writeObject(this.getSupplementalAttributes());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.setLoggerName(in.readUTF());
      this.setId(in.readUTF());
      int size = in.readInt();
      byte[] messageBytes = new byte[size];
      in.readFully(messageBytes);
      String msg = new String(messageBytes, "UTF-8");
      this.setMessage(msg);
      this.setLevel(WLLevel.getLevel(in.readInt()));
      this.setMillis(in.readLong());
      this.setThrowableWrapper((ThrowableWrapper)in.readObject());
      this.setMachineName(in.readUTF());
      this.setServerName(in.readUTF());
      this.setThreadName(in.readUTF());
      this.setTransactionId(in.readUTF());
      this.setDiagnosticContextId(in.readUTF());
      this.setUserId(in.readUTF());
      this.getSupplementalAttributes().putAll((Properties)in.readObject());
   }
}
