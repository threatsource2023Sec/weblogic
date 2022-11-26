package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class BindResponse implements ASN1Object {
   protected Int8 resultCode;
   protected OctetString matchedDN;
   protected OctetString errorMessage;
   protected Referral referral;
   protected OctetString serverSaslCreds;

   public BindResponse() {
   }

   public BindResponse(Int8 resultCode, OctetString matchedDN, OctetString errorMessage, Referral referral, OctetString serverSaslCreds) {
      if (resultCode == null) {
         throw new IllegalArgumentException();
      } else {
         this.resultCode = resultCode;
         if (matchedDN == null) {
            throw new IllegalArgumentException();
         } else {
            this.matchedDN = matchedDN;
            if (errorMessage == null) {
               throw new IllegalArgumentException();
            } else {
               this.errorMessage = errorMessage;
               this.referral = referral;
               this.serverSaslCreds = serverSaslCreds;
            }
         }
      }
   }

   public BindResponse(BindResponse value) {
      this.resultCode = value.getResultCode();
      this.matchedDN = value.getMatchedDN();
      this.errorMessage = value.getErrorMessage();
      this.referral = value.getReferral();
      this.serverSaslCreds = value.getServerSaslCreds();
   }

   public Int8 getResultCode() {
      return this.resultCode;
   }

   public void setResultCode(Int8 val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.resultCode = val;
      }
   }

   public OctetString getMatchedDN() {
      return this.matchedDN;
   }

   public void setMatchedDN(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.matchedDN = val;
      }
   }

   public OctetString getErrorMessage() {
      return this.errorMessage;
   }

   public void setErrorMessage(OctetString val) {
      if (val == null) {
         throw new IllegalArgumentException();
      } else {
         this.errorMessage = val;
      }
   }

   public Referral getReferral() {
      return this.referral;
   }

   public void setReferral(Referral val) {
      this.referral = val;
   }

   public OctetString getServerSaslCreds() {
      return this.serverSaslCreds;
   }

   public void setServerSaslCreds(OctetString val) {
      this.serverSaslCreds = val;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("{ ");
      buf.append("resultCode ").append(this.resultCode.toString());
      buf.append(", matchedDN ").append(this.matchedDN.toString());
      buf.append(", errorMessage ").append(this.errorMessage.toString());
      if (this.referral != null) {
         buf.append(", referral ").append(this.referral.toString());
      }

      if (this.serverSaslCreds != null) {
         buf.append(", serverSaslCreds ").append(this.serverSaslCreds.toString());
      }

      return buf.append(" }").toString();
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      String newindent = indent + "    ";
      out.println(indent + prefix + "{");
      this.resultCode.print(out, newindent, "resultCode ", ",", flags);
      this.matchedDN.print(out, newindent, "matchedDN ", ",", flags);
      this.errorMessage.print(out, newindent, "errorMessage ", this.referral == null && this.serverSaslCreds == null ? "" : ",", flags);
      if (this.referral != null) {
         this.referral.print(out, newindent, "referral ", this.serverSaslCreds != null ? "," : "", flags);
      }

      if (this.serverSaslCreds != null) {
         this.serverSaslCreds.print(out, newindent, "serverSaslCreds ", "", flags);
      }

      out.println(indent + "}" + suffix);
   }
}
