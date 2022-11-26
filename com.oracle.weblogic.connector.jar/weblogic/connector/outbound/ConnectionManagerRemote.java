package weblogic.connector.outbound;

import java.rmi.Remote;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.LazyAssociatableConnectionManager;
import javax.resource.spi.LazyEnlistableConnectionManager;

public interface ConnectionManagerRemote extends ConnectionManager, LazyAssociatableConnectionManager, LazyEnlistableConnectionManager, Remote {
}
