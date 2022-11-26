package org.opensaml.xmlsec.config;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaCryptoValidationInitializer implements Initializer {
   public static final String CONFIG_PROPERTY_FAIL_IS_FATAL = "opensaml.config.xmlsec.cryptoValidationIsFatal";
   private Logger log = LoggerFactory.getLogger(JavaCryptoValidationInitializer.class);

   public void init() throws InitializationException {
      boolean valid = true;
      String errorMsgAESPadding = "The JCE providers currently configured in the JVM do not support\nrequired capabilities for XML Encryption, either the 'AES' cipher algorithm\nor the 'ISO10126Padding' padding scheme\n";

      try {
         Cipher.getInstance("AES/CBC/ISO10126Padding");
      } catch (NoSuchAlgorithmException var5) {
         this.log.warn(errorMsgAESPadding);
         valid = false;
      } catch (NoSuchPaddingException var6) {
         this.log.warn(errorMsgAESPadding);
         valid = false;
      }

      if (!valid) {
         Properties props = ConfigurationService.getConfigurationProperties();
         String isFatal = props != null ? props.getProperty("opensaml.config.xmlsec.cryptoValidationIsFatal", "false") : "false";
         if ("true".equalsIgnoreCase(isFatal) || "1".equals(isFatal)) {
            this.log.warn("Configuration indicates an invalid crypto configuration should be fatal");
            throw new InitializationException("A fatal error was encountered validating required crypto capabilities");
         }
      }

   }
}
