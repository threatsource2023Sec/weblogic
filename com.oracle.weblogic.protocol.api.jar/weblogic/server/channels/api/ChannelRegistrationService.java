package weblogic.server.channels.api;

import java.util.List;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.jndi.api.ServerEnvironment;

@Contract
public interface ChannelRegistrationService {
   void registerEnvironmentForever(ServerEnvironment var1) throws NamingException;

   void registerEnvironmentForever(List var1) throws NamingException;

   void setShutdownFlag();
}
