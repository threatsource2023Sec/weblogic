package weblogic.logging;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DomainLogBroadcasterClientService {
   void broadcast(LogEntry var1);

   void flush();

   void close();
}
