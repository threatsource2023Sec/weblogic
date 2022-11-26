package net.shibboleth.utilities.java.support.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class RandomIdentifierGenerationStrategy implements IdentifierGenerationStrategy {
   private final Random random;
   private final int sizeOfIdentifier;
   private final BinaryEncoder encoder;

   public RandomIdentifierGenerationStrategy() {
      try {
         this.random = SecureRandom.getInstance("SHA1PRNG");
         this.sizeOfIdentifier = 16;
         this.encoder = new Hex();
      } catch (NoSuchAlgorithmException var2) {
         throw new RuntimeException("SHA1PRNG is required to be supported by the JVM but is not", var2);
      }
   }

   public RandomIdentifierGenerationStrategy(int identifierSize) {
      try {
         this.random = SecureRandom.getInstance("SHA1PRNG");
         this.sizeOfIdentifier = (int)Constraint.isGreaterThan(0L, (long)identifierSize, "Number of bytes in the identifier must be greater than 0");
         this.encoder = new Hex();
      } catch (NoSuchAlgorithmException var3) {
         throw new RuntimeException("SHA1PRNG is required to be supported by the JVM but is not", var3);
      }
   }

   public RandomIdentifierGenerationStrategy(@Nonnull Random source, int identifierSize, @Nonnull BinaryEncoder identifierEncoder) {
      this.random = (Random)Constraint.isNotNull(source, "Random number source can not be null");
      this.sizeOfIdentifier = (int)Constraint.isGreaterThan(0L, (long)identifierSize, "Number of bytes in the identifier must be greater than 0");
      this.encoder = (BinaryEncoder)Constraint.isNotNull(identifierEncoder, "Identifier is encoder can not be null");
   }

   @Nonnull
   public String generateIdentifier() {
      return this.generateIdentifier(true);
   }

   public String generateIdentifier(boolean xmlSafe) {
      byte[] buf = new byte[this.sizeOfIdentifier];
      this.random.nextBytes(buf);

      try {
         return xmlSafe ? "_" + StringUtils.newStringUsAscii(this.encoder.encode(buf)) : StringUtils.newStringUsAscii(this.encoder.encode(buf));
      } catch (EncoderException var4) {
         throw new RuntimeException(var4);
      }
   }
}
