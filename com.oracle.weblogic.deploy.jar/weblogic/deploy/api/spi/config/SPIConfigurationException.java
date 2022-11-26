package weblogic.deploy.api.spi.config;

import javax.enterprise.deploy.spi.exceptions.ConfigurationException;

public class SPIConfigurationException extends ConfigurationException {
   public SPIConfigurationException(String s) {
      super(s);
   }

   public SPIConfigurationException(String s, Throwable t) {
      super(s);
      this.initCause(t);
   }
}
