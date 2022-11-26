package com.octetstring.vde.operation;

import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.LDAPURL;
import com.octetstring.ldapv3.ModifyDNResponse;
import com.octetstring.ldapv3.Referral;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DirectoryException;

public class RenameOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final OctetString EMPTY_OSTRING = new OctetString();

   public RenameOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() {
      DirectoryString name = new DirectoryString(this.request.getProtocolOp().getModDNRequest().getEntry().toByteArray());
      DirectoryString newname = new DirectoryString(this.request.getProtocolOp().getModDNRequest().getNewrdn().toByteArray());
      boolean deleterdn = this.request.getProtocolOp().getModDNRequest().getDeleteoldrdn().booleanValue();
      DirectoryString newSuperior = null;
      if (this.request.getProtocolOp().getModDNRequest().getNewSuperior() != null) {
         newSuperior = new DirectoryString(this.request.getProtocolOp().getModDNRequest().getNewSuperior().toByteArray());
      }

      this.response = new LDAPMessage();
      ModifyDNResponse modDNResponse = new ModifyDNResponse();
      modDNResponse.setMatchedDN(EMPTY_OSTRING);
      modDNResponse.setErrorMessage(EMPTY_OSTRING);
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      op.setModDNResponse(modDNResponse);
      Consumer con = BackendHandler.getInstance().getReplica(name);
      Consumer con2 = BackendHandler.getInstance().getReplica(newname);
      if ((con == null || con.getBinddn().equals(this.creds.getUser())) && (con2 == null || con2.getBinddn().equals(this.creds.getUser()))) {
         boolean oldisroot = this.creds.isRoot();
         if (con != null) {
            this.creds.setRoot(true);
         }

         try {
            modDNResponse.setResultCode(BackendHandler.getInstance().rename(this.creds, name, newname, newSuperior, deleterdn));
         } catch (DirectoryException var11) {
            modDNResponse.setResultCode(new Int8((byte)var11.getLDAPErrorCode()));
            if (var11.getMessage() != null) {
               modDNResponse.setErrorMessage(new OctetString(var11.getMessage().getBytes()));
            }
         }

         this.creds.setRoot(oldisroot);
      } else {
         modDNResponse.setResultCode(LDAPResult.REFERRAL);
         Referral myref = new Referral();
         modDNResponse.setReferral(myref);
         myref.addElement(new LDAPURL(new OctetString(con.getMasterURL().getBytes())));
      }

      this.response.setMessageID(this.request.getMessageID());
      this.response.setProtocolOp(op);
   }
}
