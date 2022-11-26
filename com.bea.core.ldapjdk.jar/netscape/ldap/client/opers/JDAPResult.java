package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPResult {
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
   public static final int NAMING_VIOLATION = 64;
   public static final int OBJECT_CLASS_VIOLATION = 65;
   public static final int NOT_ALLOWED_ON_NONLEAF = 66;
   public static final int NOT_ALLOWED_ON_RDN = 67;
   public static final int ENTRY_ALREADY_EXISTS = 68;
   public static final int OBJECT_CLASS_MODS_PROHIBITED = 69;
   public static final int AFFECTS_MULTIPLE_DSAS = 71;
   public static final int OTHER = 80;
   public static final int SERVER_DOWN = 81;
   public static final int PARAM_ERROR = 89;
   public static final int CONNECT_ERROR = 91;
   public static final int LDAP_NOT_SUPPORTED = 92;
   public static final int CONTROL_NOT_FOUND = 93;
   public static final int NO_RESULTS_RETURNED = 94;
   public static final int MORE_RESULTS_TO_RETURN = 95;
   public static final int CLIENT_LOOP = 96;
   public static final int REFERRAL_LIMIT_EXCEEDED = 97;
   protected BERElement m_element = null;
   protected int m_result_code;
   protected String m_matched_dn = null;
   protected String m_error_message = null;
   protected String[] m_referrals = null;

   public JDAPResult(BERElement var1) throws IOException {
      this.m_element = var1;
      BERSequence var2 = (BERSequence)var1;
      BERElement var3 = var2.elementAt(0);
      if (var3.getType() == 48) {
         var2 = (BERSequence)var3;
      }

      this.m_result_code = ((BEREnumerated)var2.elementAt(0)).getValue();
      Object var4 = null;
      byte[] var13 = ((BEROctetString)var2.elementAt(1)).getValue();
      if (var13 == null) {
         this.m_matched_dn = null;
      } else {
         try {
            this.m_matched_dn = new String(var13, "UTF8");
         } catch (Throwable var12) {
         }
      }

      var13 = ((BEROctetString)var2.elementAt(2)).getValue();
      if (var13 == null) {
         this.m_error_message = null;
      } else {
         try {
            this.m_error_message = new String(var13, "UTF8");
         } catch (Throwable var11) {
         }
      }

      if (var2.size() >= 4) {
         BERTag var5 = (BERTag)var2.elementAt(3);
         BERElement var6 = var5.getValue();
         if (var6.getType() != 2 && var6 instanceof BERSequence) {
            BERSequence var7 = (BERSequence)var6;
            if (var7.size() > 0) {
               this.m_referrals = new String[var7.size()];

               for(int var8 = 0; var8 < var7.size(); ++var8) {
                  try {
                     this.m_referrals[var8] = new String(((BEROctetString)var7.elementAt(var8)).getValue(), "UTF8");
                  } catch (Throwable var10) {
                  }
               }
            }
         }
      }

   }

   public int getResultCode() {
      return this.m_result_code;
   }

   public String getMatchedDN() {
      return this.m_matched_dn;
   }

   public String getErrorMessage() {
      return this.m_error_message;
   }

   public String[] getReferrals() {
      return this.m_referrals;
   }

   public BERElement getBERElement() {
      return this.m_element;
   }

   public String getParamString() {
      StringBuffer var1 = new StringBuffer("{resultCode=");
      var1.append(this.m_result_code);
      if (this.m_matched_dn != null) {
         var1.append(", matchedDN=");
         var1.append(this.m_matched_dn);
      }

      if (this.m_error_message != null) {
         var1.append(", errorMessage=");
         var1.append(this.m_error_message);
      }

      if (this.m_referrals != null && this.m_referrals.length > 0) {
         var1.append(", referrals=");

         for(int var2 = 0; var2 < this.m_referrals.length; ++var2) {
            var1.append(var2 == 0 ? "" : " ");
            var1.append(this.m_referrals[var2]);
         }
      }

      var1.append("}");
      return var1.toString();
   }

   public String toString() {
      return "Result " + this.getParamString();
   }
}
