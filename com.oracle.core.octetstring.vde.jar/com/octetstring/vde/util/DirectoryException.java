package com.octetstring.vde.util;

public class DirectoryException extends Exception {
   int ldapErrorCode = 0;

   public DirectoryException() {
   }

   public DirectoryException(int code) {
      this.setLDAPErrorCode(code);
   }

   public DirectoryException(int code, String s) {
      super(s);
      this.setLDAPErrorCode(code);
   }

   public DirectoryException(String s) {
      super(s);
   }

   public int getLDAPErrorCode() {
      return this.ldapErrorCode;
   }

   public void setLDAPErrorCode(int code) {
      this.ldapErrorCode = code;
   }
}
