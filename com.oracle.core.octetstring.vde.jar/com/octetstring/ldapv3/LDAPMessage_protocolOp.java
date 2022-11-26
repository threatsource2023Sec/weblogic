package com.octetstring.ldapv3;

import com.asn1c.core.ASN1Object;
import com.asn1c.core.Int32;
import com.asn1c.core.Null;
import com.asn1c.core.OctetString;
import java.io.PrintWriter;

public class LDAPMessage_protocolOp implements ASN1Object {
   protected byte selector;
   protected ASN1Object value;
   public static final byte BINDREQUEST_SELECTED = 0;
   public static final byte BINDRESPONSE_SELECTED = 1;
   public static final byte UNBINDREQUEST_SELECTED = 2;
   public static final byte SEARCHREQUEST_SELECTED = 3;
   public static final byte SEARCHRESENTRY_SELECTED = 4;
   public static final byte SEARCHRESDONE_SELECTED = 5;
   public static final byte MODIFYREQUEST_SELECTED = 6;
   public static final byte MODIFYRESPONSE_SELECTED = 7;
   public static final byte ADDREQUEST_SELECTED = 8;
   public static final byte ADDRESPONSE_SELECTED = 9;
   public static final byte DELREQUEST_SELECTED = 10;
   public static final byte DELRESPONSE_SELECTED = 11;
   public static final byte MODDNREQUEST_SELECTED = 12;
   public static final byte MODDNRESPONSE_SELECTED = 13;
   public static final byte COMPAREREQUEST_SELECTED = 14;
   public static final byte COMPARERESPONSE_SELECTED = 15;
   public static final byte ABANDONREQUEST_SELECTED = 16;
   public static final byte SEARCHRESREF_SELECTED = 17;
   public static final byte EXTENDEDREQ_SELECTED = 18;
   public static final byte EXTENDEDRESP_SELECTED = 19;

   public LDAPMessage_protocolOp() {
      this.selector = -1;
   }

   public LDAPMessage_protocolOp(byte selector, ASN1Object value) {
      this.selector = selector;
      this.value = value;
   }

   public LDAPMessage_protocolOp(LDAPMessage_protocolOp value) {
      this.selector = value.getSelector();
      if (this.selector >= 0) {
         this.value = value.getValue();
      }

   }

   public byte getSelector() {
      return this.selector;
   }

   public ASN1Object getValue() {
      return this.value;
   }

   public BindRequest getBindRequest() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return (BindRequest)this.value;
      }
   }

   public void setBindRequest(BindRequest val) {
      this.selector = 0;
      this.value = val;
   }

   public BindResponse getBindResponse() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return (BindResponse)this.value;
      }
   }

   public void setBindResponse(BindResponse val) {
      this.selector = 1;
      this.value = val;
   }

   public Null getUnbindRequest() {
      if (this.selector != 2) {
         throw new IllegalStateException();
      } else {
         return (Null)this.value;
      }
   }

   public void setUnbindRequest(Null val) {
      this.selector = 2;
      this.value = val;
   }

   public SearchRequest getSearchRequest() {
      if (this.selector != 3) {
         throw new IllegalStateException();
      } else {
         return (SearchRequest)this.value;
      }
   }

   public void setSearchRequest(SearchRequest val) {
      this.selector = 3;
      this.value = val;
   }

   public SearchResultEntry getSearchResEntry() {
      if (this.selector != 4) {
         throw new IllegalStateException();
      } else {
         return (SearchResultEntry)this.value;
      }
   }

   public void setSearchResEntry(SearchResultEntry val) {
      this.selector = 4;
      this.value = val;
   }

   public SearchResultDone getSearchResDone() {
      if (this.selector != 5) {
         throw new IllegalStateException();
      } else {
         return (SearchResultDone)this.value;
      }
   }

   public void setSearchResDone(SearchResultDone val) {
      this.selector = 5;
      this.value = val;
   }

   public ModifyRequest getModifyRequest() {
      if (this.selector != 6) {
         throw new IllegalStateException();
      } else {
         return (ModifyRequest)this.value;
      }
   }

   public void setModifyRequest(ModifyRequest val) {
      this.selector = 6;
      this.value = val;
   }

   public ModifyResponse getModifyResponse() {
      if (this.selector != 7) {
         throw new IllegalStateException();
      } else {
         return (ModifyResponse)this.value;
      }
   }

   public void setModifyResponse(ModifyResponse val) {
      this.selector = 7;
      this.value = val;
   }

   public AddRequest getAddRequest() {
      if (this.selector != 8) {
         throw new IllegalStateException();
      } else {
         return (AddRequest)this.value;
      }
   }

   public void setAddRequest(AddRequest val) {
      this.selector = 8;
      this.value = val;
   }

   public AddResponse getAddResponse() {
      if (this.selector != 9) {
         throw new IllegalStateException();
      } else {
         return (AddResponse)this.value;
      }
   }

   public void setAddResponse(AddResponse val) {
      this.selector = 9;
      this.value = val;
   }

   public OctetString getDelRequest() {
      if (this.selector != 10) {
         throw new IllegalStateException();
      } else {
         return (OctetString)this.value;
      }
   }

   public void setDelRequest(OctetString val) {
      this.selector = 10;
      this.value = val;
   }

   public DelResponse getDelResponse() {
      if (this.selector != 11) {
         throw new IllegalStateException();
      } else {
         return (DelResponse)this.value;
      }
   }

   public void setDelResponse(DelResponse val) {
      this.selector = 11;
      this.value = val;
   }

   public ModifyDNRequest getModDNRequest() {
      if (this.selector != 12) {
         throw new IllegalStateException();
      } else {
         return (ModifyDNRequest)this.value;
      }
   }

   public void setModDNRequest(ModifyDNRequest val) {
      this.selector = 12;
      this.value = val;
   }

   public ModifyDNResponse getModDNResponse() {
      if (this.selector != 13) {
         throw new IllegalStateException();
      } else {
         return (ModifyDNResponse)this.value;
      }
   }

   public void setModDNResponse(ModifyDNResponse val) {
      this.selector = 13;
      this.value = val;
   }

   public CompareRequest getCompareRequest() {
      if (this.selector != 14) {
         throw new IllegalStateException();
      } else {
         return (CompareRequest)this.value;
      }
   }

   public void setCompareRequest(CompareRequest val) {
      this.selector = 14;
      this.value = val;
   }

   public CompareResponse getCompareResponse() {
      if (this.selector != 15) {
         throw new IllegalStateException();
      } else {
         return (CompareResponse)this.value;
      }
   }

   public void setCompareResponse(CompareResponse val) {
      this.selector = 15;
      this.value = val;
   }

   public Int32 getAbandonRequest() {
      if (this.selector != 16) {
         throw new IllegalStateException();
      } else {
         return (Int32)this.value;
      }
   }

   public void setAbandonRequest(Int32 val) {
      this.selector = 16;
      this.value = val;
   }

   public SearchResultReference getSearchResRef() {
      if (this.selector != 17) {
         throw new IllegalStateException();
      } else {
         return (SearchResultReference)this.value;
      }
   }

   public void setSearchResRef(SearchResultReference val) {
      this.selector = 17;
      this.value = val;
   }

   public ExtendedRequest getExtendedReq() {
      if (this.selector != 18) {
         throw new IllegalStateException();
      } else {
         return (ExtendedRequest)this.value;
      }
   }

   public void setExtendedReq(ExtendedRequest val) {
      this.selector = 18;
      this.value = val;
   }

   public ExtendedResponse getExtendedResp() {
      if (this.selector != 19) {
         throw new IllegalStateException();
      } else {
         return (ExtendedResponse)this.value;
      }
   }

   public void setExtendedResp(ExtendedResponse val) {
      this.selector = 19;
      this.value = val;
   }

   public String toString() {
      switch (this.selector) {
         case 0:
            return "bindRequest: " + this.value.toString();
         case 1:
            return "bindResponse: " + this.value.toString();
         case 2:
            return "unbindRequest: " + this.value.toString();
         case 3:
            return "searchRequest: " + this.value.toString();
         case 4:
            return "searchResEntry: " + this.value.toString();
         case 5:
            return "searchResDone: " + this.value.toString();
         case 6:
            return "modifyRequest: " + this.value.toString();
         case 7:
            return "modifyResponse: " + this.value.toString();
         case 8:
            return "addRequest: " + this.value.toString();
         case 9:
            return "addResponse: " + this.value.toString();
         case 10:
            return "delRequest: " + this.value.toString();
         case 11:
            return "delResponse: " + this.value.toString();
         case 12:
            return "modDNRequest: " + this.value.toString();
         case 13:
            return "modDNResponse: " + this.value.toString();
         case 14:
            return "compareRequest: " + this.value.toString();
         case 15:
            return "compareResponse: " + this.value.toString();
         case 16:
            return "abandonRequest: " + this.value.toString();
         case 17:
            return "searchResRef: " + this.value.toString();
         case 18:
            return "extendedReq: " + this.value.toString();
         case 19:
            return "extendedResp: " + this.value.toString();
         default:
            return "UNDEFINED";
      }
   }

   public void print(PrintWriter out, String indent, String prefix, String suffix, int flags) {
      switch (this.selector) {
         case 0:
            this.value.print(out, indent, prefix + "bindRequest: ", suffix, flags);
            break;
         case 1:
            this.value.print(out, indent, prefix + "bindResponse: ", suffix, flags);
            break;
         case 2:
            this.value.print(out, indent, prefix + "unbindRequest: ", suffix, flags);
            break;
         case 3:
            this.value.print(out, indent, prefix + "searchRequest: ", suffix, flags);
            break;
         case 4:
            this.value.print(out, indent, prefix + "searchResEntry: ", suffix, flags);
            break;
         case 5:
            this.value.print(out, indent, prefix + "searchResDone: ", suffix, flags);
            break;
         case 6:
            this.value.print(out, indent, prefix + "modifyRequest: ", suffix, flags);
            break;
         case 7:
            this.value.print(out, indent, prefix + "modifyResponse: ", suffix, flags);
            break;
         case 8:
            this.value.print(out, indent, prefix + "addRequest: ", suffix, flags);
            break;
         case 9:
            this.value.print(out, indent, prefix + "addResponse: ", suffix, flags);
            break;
         case 10:
            this.value.print(out, indent, prefix + "delRequest: ", suffix, flags);
            break;
         case 11:
            this.value.print(out, indent, prefix + "delResponse: ", suffix, flags);
            break;
         case 12:
            this.value.print(out, indent, prefix + "modDNRequest: ", suffix, flags);
            break;
         case 13:
            this.value.print(out, indent, prefix + "modDNResponse: ", suffix, flags);
            break;
         case 14:
            this.value.print(out, indent, prefix + "compareRequest: ", suffix, flags);
            break;
         case 15:
            this.value.print(out, indent, prefix + "compareResponse: ", suffix, flags);
            break;
         case 16:
            this.value.print(out, indent, prefix + "abandonRequest: ", suffix, flags);
            break;
         case 17:
            this.value.print(out, indent, prefix + "searchResRef: ", suffix, flags);
            break;
         case 18:
            this.value.print(out, indent, prefix + "extendedReq: ", suffix, flags);
            break;
         case 19:
            this.value.print(out, indent, prefix + "extendedResp: ", suffix, flags);
            break;
         default:
            out.println(indent + prefix + "UNDEFINED" + suffix);
      }

   }
}
