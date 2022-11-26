package kodo.ee;

import javax.resource.Referenceable;
import javax.resource.cci.ConnectionFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface KodoConnectionFactory extends OpenJPAConfiguration, ConnectionFactory, Referenceable {
}
