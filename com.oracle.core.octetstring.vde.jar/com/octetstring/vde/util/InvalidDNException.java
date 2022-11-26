package com.octetstring.vde.util;

public class InvalidDNException extends DirectoryException {
   public InvalidDNException() {
      this.setLDAPErrorCode(34);
   }

   public InvalidDNException(String s) {
      super(s);
      this.setLDAPErrorCode(34);
   }
}
