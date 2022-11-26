package org.opensaml.xmlsec.criterion;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public class EncryptionOptionalCriterion implements Criterion {
   private Boolean encryptionOptional;

   public EncryptionOptionalCriterion() {
      this.encryptionOptional = Boolean.FALSE;
   }

   public EncryptionOptionalCriterion(boolean value) {
      this.encryptionOptional = value;
   }

   public boolean isEncryptionOptional() {
      return this.encryptionOptional;
   }

   public int hashCode() {
      return this.encryptionOptional.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof EncryptionOptionalCriterion ? Objects.equals(this.encryptionOptional, ((EncryptionOptionalCriterion)other).encryptionOptional) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).addValue(this.encryptionOptional).toString();
   }
}
