package org.opensaml.xmlsec.keyinfo;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.xmlsec.signature.KeyInfo;

public final class KeyInfoCriterion implements Criterion {
   private KeyInfo keyInfo;

   public KeyInfoCriterion(@Nullable KeyInfo newKeyInfo) {
      this.setKeyInfo(newKeyInfo);
   }

   @Nullable
   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(@Nullable KeyInfo newKeyInfo) {
      this.keyInfo = newKeyInfo;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("KeyInfoCriterion [keyInfo=");
      builder.append("<contents not displayable>");
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.keyInfo != null ? this.keyInfo.hashCode() : super.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof KeyInfoCriterion ? this.keyInfo.equals(((KeyInfoCriterion)obj).keyInfo) : false;
      }
   }
}
