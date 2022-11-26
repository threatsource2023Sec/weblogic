package javax.resource.spi;

import java.util.Set;
import javax.resource.ResourceException;

public interface ValidatingManagedConnectionFactory {
   Set getInvalidConnections(Set var1) throws ResourceException;
}
