package org.jboss.weld.configuration.spi;

import java.util.Map;
import org.jboss.weld.bootstrap.api.Service;

public interface ExternalConfiguration extends Service {
   Map getConfigurationProperties();
}
