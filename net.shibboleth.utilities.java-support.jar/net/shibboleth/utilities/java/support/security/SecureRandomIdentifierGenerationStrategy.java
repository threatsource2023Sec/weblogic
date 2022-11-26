package net.shibboleth.utilities.java.support.security;

import java.security.SecureRandom;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import org.apache.commons.codec.BinaryEncoder;

public class SecureRandomIdentifierGenerationStrategy extends RandomIdentifierGenerationStrategy {
   public SecureRandomIdentifierGenerationStrategy() {
   }

   public SecureRandomIdentifierGenerationStrategy(@ParameterName(name = "identifierSize") int identifierSize) {
      super(identifierSize);
   }

   public SecureRandomIdentifierGenerationStrategy(@ParameterName(name = "source") @Nonnull SecureRandom source, @ParameterName(name = "identifierSize") int identifierSize, @ParameterName(name = "identifierEncoder") @Nonnull BinaryEncoder identifierEncoder) {
      super(source, identifierSize, identifierEncoder);
   }
}
