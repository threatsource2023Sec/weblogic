package weblogic.deploy.api.spi.status;

import javax.enterprise.deploy.spi.exceptions.ClientExecuteException;
import javax.enterprise.deploy.spi.status.ClientConfiguration;

public class ClientConfigurationImpl implements ClientConfiguration {
   public void execute() throws ClientExecuteException {
      throw new ClientExecuteException();
   }
}
