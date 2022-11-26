package weblogic.t3.srvr;

import java.io.IOException;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.ServerChannel;
import weblogic.server.ServiceFailureException;

@Contract
public interface AdminPortLifeCycleService {
   boolean isServerSocketsBound();

   void createAndBindServerSockets() throws ServiceFailureException;

   void enableServerSockets() throws ServiceFailureException;

   void updateServerSocket(ServerChannel var1) throws IOException;

   void closeServerSocket(ServerChannel var1) throws IOException;

   void addServerSocket(List var1) throws IOException;

   void halt();
}
