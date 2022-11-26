package com.octetstring.vde.util;

public class DirectorySchemaViolation extends DirectoryException {
   public DirectorySchemaViolation() {
      this.setLDAPErrorCode(65);
   }

   public DirectorySchemaViolation(String s) {
      super(s);
      this.setLDAPErrorCode(65);
   }
}
