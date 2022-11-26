package weblogic.ejb.container.deployer;

import java.util.Collection;
import javax.naming.NamingException;
import weblogic.application.naming.Environment;

public interface EnvironmentManager {
   Environment createEnvironmentFor(String var1) throws NamingException;

   Collection getEnvironments();

   Environment getEnvironmentFor(String var1);

   void cleanup();
}
