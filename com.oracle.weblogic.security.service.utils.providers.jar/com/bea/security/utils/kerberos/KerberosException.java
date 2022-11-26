package com.bea.security.utils.kerberos;

public class KerberosException extends Exception {
   private static final long serialVersionUID = -180896847504177214L;

   public KerberosException() {
   }

   public KerberosException(String message) {
      super(message);
   }

   public KerberosException(String message, Throwable cause) {
      super(message, cause);
   }

   public KerberosException(Throwable cause) {
      super(cause);
   }
}
