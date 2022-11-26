package netscape.ldap;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Locale;
import java.util.PropertyResourceBundle;

public class LDAPException extends Exception implements Serializable {
   static final long serialVersionUID = -9215007872184847925L;
   public static final int SUCCESS = 0;
   public static final int OPERATION_ERROR = 1;
   public static final int PROTOCOL_ERROR = 2;
   public static final int TIME_LIMIT_EXCEEDED = 3;
   public static final int SIZE_LIMIT_EXCEEDED = 4;
   public static final int COMPARE_FALSE = 5;
   public static final int COMPARE_TRUE = 6;
   public static final int AUTH_METHOD_NOT_SUPPORTED = 7;
   public static final int STRONG_AUTH_REQUIRED = 8;
   public static final int LDAP_PARTIAL_RESULTS = 9;
   public static final int REFERRAL = 10;
   public static final int ADMIN_LIMIT_EXCEEDED = 11;
   public static final int UNAVAILABLE_CRITICAL_EXTENSION = 12;
   public static final int CONFIDENTIALITY_REQUIRED = 13;
   public static final int SASL_BIND_IN_PROGRESS = 14;
   public static final int NO_SUCH_ATTRIBUTE = 16;
   public static final int UNDEFINED_ATTRIBUTE_TYPE = 17;
   public static final int INAPPROPRIATE_MATCHING = 18;
   public static final int CONSTRAINT_VIOLATION = 19;
   public static final int ATTRIBUTE_OR_VALUE_EXISTS = 20;
   public static final int INVALID_ATTRIBUTE_SYNTAX = 21;
   public static final int NO_SUCH_OBJECT = 32;
   public static final int ALIAS_PROBLEM = 33;
   public static final int INVALID_DN_SYNTAX = 34;
   public static final int IS_LEAF = 35;
   public static final int ALIAS_DEREFERENCING_PROBLEM = 36;
   public static final int INAPPROPRIATE_AUTHENTICATION = 48;
   public static final int INVALID_CREDENTIALS = 49;
   public static final int INSUFFICIENT_ACCESS_RIGHTS = 50;
   public static final int BUSY = 51;
   public static final int UNAVAILABLE = 52;
   public static final int UNWILLING_TO_PERFORM = 53;
   public static final int LOOP_DETECT = 54;
   public static final int SORT_CONTROL_MISSING = 60;
   public static final int INDEX_RANGE_ERROR = 61;
   public static final int NAMING_VIOLATION = 64;
   public static final int OBJECT_CLASS_VIOLATION = 65;
   public static final int NOT_ALLOWED_ON_NONLEAF = 66;
   public static final int NOT_ALLOWED_ON_RDN = 67;
   public static final int ENTRY_ALREADY_EXISTS = 68;
   public static final int OBJECT_CLASS_MODS_PROHIBITED = 69;
   public static final int AFFECTS_MULTIPLE_DSAS = 71;
   public static final int OTHER = 80;
   public static final int SERVER_DOWN = 81;
   public static final int LDAP_TIMEOUT = 85;
   public static final int PARAM_ERROR = 89;
   public static final int CONNECT_ERROR = 91;
   public static final int LDAP_NOT_SUPPORTED = 92;
   public static final int CONTROL_NOT_FOUND = 93;
   public static final int NO_RESULTS_RETURNED = 94;
   public static final int MORE_RESULTS_TO_RETURN = 95;
   public static final int CLIENT_LOOP = 96;
   public static final int REFERRAL_LIMIT_EXCEEDED = 97;
   public static final int TLS_NOT_SUPPORTED = 112;
   private int resultCode = -1;
   private String errorMessage = null;
   private String extraMessage = null;
   private String matchedDN = null;
   private Locale m_locale = Locale.getDefault();
   private static Hashtable cacheResource = new Hashtable();
   private static final String baseName = "netscape/ldap/errors/ErrorCodes";

   public LDAPException() {
   }

   public LDAPException(String var1) {
      super(var1);
   }

   public LDAPException(String var1, int var2) {
      super(var1);
      this.resultCode = var2;
   }

   public LDAPException(String var1, int var2, String var3) {
      super(var1);
      this.resultCode = var2;
      this.errorMessage = var3;
   }

   public LDAPException(String var1, int var2, String var3, String var4) {
      super(var1);
      this.resultCode = var2;
      this.errorMessage = var3;
      this.matchedDN = var4;
   }

   public int getLDAPResultCode() {
      return this.resultCode;
   }

   public String getLDAPErrorMessage() {
      return this.errorMessage;
   }

   void setExtraMessage(String var1) {
      if (this.extraMessage == null) {
         this.extraMessage = var1;
      } else {
         this.extraMessage = this.extraMessage + "; " + var1;
      }

   }

   public String getMatchedDN() {
      return this.matchedDN;
   }

   public String toString() {
      String var1 = super.toString() + " (" + this.resultCode + ")";
      if (this.errorMessage != null && this.errorMessage.length() > 0) {
         var1 = var1 + "; " + this.errorMessage;
      }

      if (this.matchedDN != null && this.matchedDN.length() > 0) {
         var1 = var1 + "; matchedDN = " + this.matchedDN;
      }

      String var2 = this.errorCodeToString(this.m_locale);
      if (var2 != null && var2.length() > 0) {
         var1 = var1 + "; " + var2;
      }

      if (this.extraMessage != null) {
         var1 = var1 + "; " + this.extraMessage;
      }

      return var1;
   }

   public String errorCodeToString() {
      return errorCodeToString(this.resultCode, this.m_locale);
   }

   public String errorCodeToString(Locale var1) {
      return errorCodeToString(this.resultCode, var1);
   }

   public static String errorCodeToString(int var0) {
      return errorCodeToString(var0, Locale.getDefault());
   }

   public static synchronized String errorCodeToString(int var0, Locale var1) {
      try {
         String var2 = var1.toString();
         PropertyResourceBundle var3 = (PropertyResourceBundle)cacheResource.get(var2);
         if (var3 == null) {
            var3 = LDAPResourceBundle.getBundle("netscape/ldap/errors/ErrorCodes");
            if (var3 != null) {
               cacheResource.put(var2, var3);
            }
         }

         if (var3 != null) {
            return (String)var3.handleGetObject(Integer.toString(var0));
         }
      } catch (IOException var4) {
         System.out.println("Cannot open resource file for LDAPException netscape/ldap/errors/ErrorCodes");
      }

      return null;
   }
}
