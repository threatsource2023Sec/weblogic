package org.opensaml.core.xml.util;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.commons.codec.binary.Hex;

public class XMLObjectSource {
   @Nonnull
   @NotEmpty
   private byte[] source;

   public XMLObjectSource(@Nonnull @NotEmpty byte[] objectSource) {
      this.source = (byte[])Constraint.isNotNull(objectSource, "Object source byte[] may not be null");
      Constraint.isGreaterThan(0L, (long)this.source.length, "Object source byte[] length must be greater than zero");
   }

   @Nonnull
   public byte[] getObjectSource() {
      return this.source;
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("source", new String(Hex.encodeHex(this.source, true))).toString();
   }

   public int hashCode() {
      return Arrays.hashCode(this.source);
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return obj instanceof XMLObjectSource ? Arrays.equals(this.source, ((XMLObjectSource)obj).source) : false;
      }
   }
}
