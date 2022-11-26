package weblogic.t3.srvr;

import org.jvnet.hk2.annotations.Contract;
import weblogic.server.ServiceFailureException;

@Contract
public interface ChannelListenerService {
   void createAndBindServerSockets() throws ServiceFailureException;

   void enableServerSockets();

   void closeServerSockets();
}
