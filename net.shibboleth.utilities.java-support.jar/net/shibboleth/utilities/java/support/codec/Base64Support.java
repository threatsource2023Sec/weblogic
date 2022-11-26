package net.shibboleth.utilities.java.support.codec;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.commons.codec.binary.Base64;

public final class Base64Support {
   public static final boolean CHUNKED = true;
   public static final boolean UNCHUNKED = false;
   private static final Base64 CHUNKED_ENCODER = new Base64(76, new byte[]{10});
   private static final Base64 UNCHUNKED_ENCODER = new Base64(0, new byte[]{10});

   private Base64Support() {
   }

   @Nonnull
   public static String encode(@Nonnull byte[] data, boolean chunked) {
      Constraint.isNotNull(data, "Binary data to be encoded can not be null");
      return chunked ? StringSupport.trim(CHUNKED_ENCODER.encodeToString(data)) : StringSupport.trim(UNCHUNKED_ENCODER.encodeToString(data));
   }

   @Nonnull
   public static byte[] decode(@Nonnull String data) {
      Constraint.isNotNull(data, "Base64 encoded data can not be null");
      return CHUNKED_ENCODER.decode(data);
   }
}
