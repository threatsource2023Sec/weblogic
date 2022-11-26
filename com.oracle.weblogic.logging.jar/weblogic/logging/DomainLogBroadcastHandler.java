package weblogic.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import weblogic.utils.LocatorUtilities;

public class DomainLogBroadcastHandler extends Handler {
   private final DomainLogBroadcasterClientService domainLogBroadcasterClient = (DomainLogBroadcasterClientService)LocatorUtilities.getService(DomainLogBroadcasterClientService.class);

   public void publish(LogRecord rec) {
      WLLogRecord logRecord = WLLogger.normalizeLogRecord(rec);
      if (this.isLoggable(logRecord)) {
         this.domainLogBroadcasterClient.broadcast(logRecord);
      }
   }

   public void flush() {
      this.domainLogBroadcasterClient.flush();
   }

   public void close() {
      this.domainLogBroadcasterClient.close();
   }
}
