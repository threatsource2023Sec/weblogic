package weblogic.jndi.internal;

import weblogic.jndi.ClientEnvironment;
import weblogic.jndi.ClientEnvironmentFactory;
import weblogic.jndi.Environment;

public class ClientEnvironmentFactoryImpl implements ClientEnvironmentFactory {
   public ClientEnvironment getNewEnvironment() {
      return new Environment();
   }
}
