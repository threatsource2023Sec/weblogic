package com.bea.common.ldap.exps;

import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

public class ExpPath extends Val implements Path {
   private static final String HEADER = "ExpPath: ";
   private FieldMetaData field = null;
   private boolean containsScopingKey = false;
   private Log log;

   public ExpPath(ClassMetaData type, Log log) {
      super(type);
      this.log = log;
   }

   public void get(FieldMetaData field, boolean nullTraversal) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("ExpPath: accessing field: " + field.getName());
      }

      this.field = field;
      this.containsScopingKey = this.isScopingField(field);
   }

   private boolean isScopingField(FieldMetaData field) {
      int index = field.getPrimaryKeyIndex();
      if (index >= 0) {
         FieldMetaData[] pks = this.getMetaData().getPrimaryKeyFields();

         for(int i = pks.length - 1; i > index; --i) {
            String rdnc = pks[i].getStringExtension("com.bea.common.security", "ldap-rdn-cont");
            if (rdnc == null || !Boolean.valueOf(rdnc)) {
               return true;
            }
         }
      }

      return false;
   }

   public FieldMetaData last() {
      return this.field;
   }

   public boolean isVariable() {
      return false;
   }

   public String getAttribute() {
      if (this.field != null) {
         String attr = this.field.getStringExtension("com.bea.common.security", "ldap-attr");
         return attr != null ? attr : this.field.getName();
      } else {
         return "";
      }
   }

   public boolean containsScopingKey() {
      return this.containsScopingKey;
   }
}
