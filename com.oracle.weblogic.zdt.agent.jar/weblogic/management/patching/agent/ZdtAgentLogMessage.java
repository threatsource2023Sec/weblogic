package weblogic.management.patching.agent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class ZdtAgentLogMessage {
   long timeStampMillis;
   private Level level;
   private String msgContents;
   private String exceptionCause;

   public ZdtAgentLogMessage(long timeStampMillis, Level level, String msgContents) {
      this.timeStampMillis = timeStampMillis;
      this.level = level;
      this.msgContents = msgContents;
   }

   public ZdtAgentLogMessage(long timeStampMillis, Level level, String msgContents, Exception cause) {
      this.timeStampMillis = timeStampMillis;
      this.level = level;
      this.msgContents = msgContents;
      StringWriter stringWriter = new StringWriter();
      cause.printStackTrace(new PrintWriter(stringWriter));
      this.exceptionCause = stringWriter.toString();
   }

   public ZdtAgentLogMessage(long timeStampMillis, Level level, String msgContents, String cause) {
      this.timeStampMillis = timeStampMillis;
      this.level = level;
      this.msgContents = msgContents;
      this.exceptionCause = cause;
   }

   public long getTimeStampMillis() {
      return this.timeStampMillis;
   }

   public Level getLevel() {
      return this.level;
   }

   public String getMsgContents() {
      return this.msgContents;
   }

   public String getCause() {
      return this.exceptionCause;
   }
}
