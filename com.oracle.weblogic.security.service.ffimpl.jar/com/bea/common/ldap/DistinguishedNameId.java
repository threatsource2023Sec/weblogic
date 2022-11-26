package com.bea.common.ldap;

import org.apache.openjpa.meta.ClassMetaData;

public class DistinguishedNameId {
   private ClassMetaData meta;
   private String dn;

   public DistinguishedNameId(ClassMetaData meta, String dn) {
      this.meta = meta;
      this.dn = dn;
   }

   public Class getManagedType() {
      return this.meta.getDescribedType();
   }

   public ClassMetaData getMeta() {
      return this.meta;
   }

   public String getDN() {
      return this.dn;
   }

   public String toString() {
      return this.dn;
   }

   public int hashCode() {
      return this.dn != null ? this.dn.hashCode() : 0;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DistinguishedNameId)) {
         return false;
      } else {
         DistinguishedNameId o = (DistinguishedNameId)other;
         return this.dn == o.dn || this.dn != null && this.dn.equals(o.dn);
      }
   }
}
