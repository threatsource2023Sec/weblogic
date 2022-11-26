package org.opensaml.core.config.provider;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public class SystemPropertyFilesystemConfigurationPropertiesSource extends AbstractFilesystemConfigurationPropertiesSource {
   @Nonnull
   @NotEmpty
   public static final String PROPERTY_FILE_NAME = "opensaml.config.fileName";

   protected String getFilename() {
      return System.getProperty("opensaml.config.fileName");
   }
}
