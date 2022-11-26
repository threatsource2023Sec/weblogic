package weblogic.jms.safclient.agent.internal;

import weblogic.jndi.ClientEnvironment;
import weblogic.jndi.ClientEnvironmentFactory;

public final class ClientEnvironmentFactoryImpl implements ClientEnvironmentFactory {
   public ClientEnvironment getNewEnvironment() {
      return new ClientEnvironmentImpl();
   }
}
