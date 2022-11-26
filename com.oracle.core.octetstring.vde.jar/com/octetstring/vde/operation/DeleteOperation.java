package com.octetstring.vde.operation;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.DelResponse;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.LDAPURL;
import com.octetstring.ldapv3.Referral;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.InvalidDNException;

public class DeleteOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final OctetString EMPTY_OSTRING = new OctetString();

   public DeleteOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() {
      this.response = new LDAPMessage();
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      DelResponse delResponse = new DelResponse();
      delResponse.setMatchedDN(EMPTY_OSTRING);
      delResponse.setErrorMessage(EMPTY_OSTRING);
      DirectoryString dn = null;

      try {
         dn = DNUtility.getInstance().normalize(new DirectoryString(this.request.getProtocolOp().getDelRequest().toByteArray()));
      } catch (InvalidDNException var6) {
         delResponse.setResultCode(LDAPResult.INVALID_DN_SYNTAX);
         op.setDelResponse(delResponse);
         this.response.setMessageID(this.request.getMessageID());
         this.response.setProtocolOp(op);
         return;
      }

      Consumer con = BackendHandler.getInstance().getReplica(dn);
      if (con != null && !con.getBinddn().equals(this.creds.getUser())) {
         delResponse.setResultCode(LDAPResult.REFERRAL);
         Referral myref = new Referral();
         delResponse.setReferral(myref);
         myref.addElement(new LDAPURL(new OctetString(con.getMasterURL().getBytes())));
      } else {
         boolean oldisroot = this.creds.isRoot();
         if (con != null) {
            this.creds.setRoot(true);
         }

         delResponse.setResultCode(BackendHandler.getInstance().delete(this.creds, dn));
         this.creds.setRoot(oldisroot);
      }

      op.setDelResponse(delResponse);
      this.response.setMessageID(this.request.getMessageID());
      this.response.setProtocolOp(op);
   }
}
