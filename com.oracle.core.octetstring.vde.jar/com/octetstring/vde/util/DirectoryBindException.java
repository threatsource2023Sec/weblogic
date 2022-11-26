package com.octetstring.vde.util;

public class DirectoryBindException extends DirectoryException {
   public DirectoryBindException() {
      this.setLDAPErrorCode(49);
   }

   public DirectoryBindException(String s) {
      super(s);
      this.setLDAPErrorCode(49);
   }
}
