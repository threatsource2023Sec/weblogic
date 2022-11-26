package com.bea.common.ldap;

import netscape.ldap.LDAPEntry;
import org.apache.openjpa.meta.ClassMetaData;

public final class ObjectData implements Cloneable {
   private Object oid;
   private LDAPEntry entry;
   private ClassMetaData meta;

   public ObjectData(Object oid, LDAPEntry entry, ClassMetaData meta) {
      this.oid = oid;
      this.meta = meta;
      this.entry = entry;
   }

   public Object getId() {
      return this.oid;
   }

   public ClassMetaData getMetaData() {
      return this.meta;
   }

   public LDAPEntry getEntry() {
      return this.entry;
   }
}
