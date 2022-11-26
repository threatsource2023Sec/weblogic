package kodo.ee;

import javax.resource.spi.ManagedConnectionFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface KodoManagedConnectionFactory extends OpenJPAConfiguration, ManagedConnectionFactory {
}
