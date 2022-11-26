package weblogic.jndi.api;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ThreadEnvironmentService {
   void push(ServerEnvironment var1);

   ServerEnvironment pop();

   ServerEnvironment get();

   Map getEnvironmentProperties();
}
