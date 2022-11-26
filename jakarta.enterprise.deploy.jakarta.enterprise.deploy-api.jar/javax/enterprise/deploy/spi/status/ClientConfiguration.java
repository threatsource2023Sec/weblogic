package javax.enterprise.deploy.spi.status;

import java.io.Serializable;
import javax.enterprise.deploy.spi.exceptions.ClientExecuteException;

public interface ClientConfiguration extends Serializable {
   void execute() throws ClientExecuteException;
}
