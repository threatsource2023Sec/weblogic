package org.opensaml.core.config.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFilesystemConfigurationPropertiesSource implements ConfigurationPropertiesSource {
   private Properties cachedProperties;
   private Logger log = LoggerFactory.getLogger(AbstractFilesystemConfigurationPropertiesSource.class);

   public Properties getProperties() {
      String fileName = StringSupport.trimOrNull(this.getFilename());
      if (fileName == null) {
         this.log.warn("No filename was supplied, unable to load properties");
         return null;
      } else {
         synchronized(this) {
            if (this.cachedProperties == null) {
               File file = new File(fileName);
               if (file.exists()) {
                  try {
                     InputStream is = new FileInputStream(fileName);
                     Throwable var5 = null;

                     try {
                        Properties props = new Properties();
                        props.load(is);
                        this.cachedProperties = props;
                     } catch (Throwable var18) {
                        var5 = var18;
                        throw var18;
                     } finally {
                        if (is != null) {
                           if (var5 != null) {
                              try {
                                 is.close();
                              } catch (Throwable var17) {
                                 var5.addSuppressed(var17);
                              }
                           } else {
                              is.close();
                           }
                        }

                     }
                  } catch (FileNotFoundException var20) {
                     this.log.warn("File not found attempting to load configuration properties '" + fileName + "' from filesystem");
                  } catch (IOException var21) {
                     this.log.warn("I/O problem attempting to load configuration properties '" + fileName + "' from filesystem", var21);
                  }
               }
            }

            return this.cachedProperties;
         }
      }
   }

   protected abstract String getFilename();
}
