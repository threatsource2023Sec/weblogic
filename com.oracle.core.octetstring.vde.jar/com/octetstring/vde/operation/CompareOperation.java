package com.octetstring.vde.operation;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.CompareResponse;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.Logger;
import java.util.Vector;

public class CompareOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final OctetString EMPTY_OSTRING = new OctetString();

   public CompareOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() {
      DirectoryString comparedn = new DirectoryString(this.request.getProtocolOp().getCompareRequest().getEntry().toByteArray());
      this.response = new LDAPMessage();
      CompareResponse cr = new CompareResponse();
      cr.setResultCode(LDAPResult.SUCCESS);
      cr.setMatchedDN(EMPTY_OSTRING);
      cr.setErrorMessage(EMPTY_OSTRING);
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      op.setCompareResponse(cr);
      this.response.setMessageID(this.request.getMessageID());
      this.response.setProtocolOp(op);
      if (this.creds == null) {
         this.creds = new Credentials();
      }

      Entry anEntry = null;

      try {
         anEntry = BackendHandler.getInstance().getByDN(this.creds.getUser(), comparedn);
      } catch (DirectoryException var13) {
         Logger.getInstance().log(0, this, Messages.getString("Error_getting_entry_____1") + comparedn + "': " + var13.getMessage());
         cr.setResultCode(LDAPResult.UNWILLING_TO_PERFORM);
         return;
      }

      if (anEntry == null) {
         cr.setResultCode(LDAPResult.NO_SUCH_OBJECT);
      } else {
         DirectoryString type = new DirectoryString(this.request.getProtocolOp().getCompareRequest().getAva().getAttributeDesc().toByteArray());
         if (!ACLChecker.getInstance().isAllowed(this.creds, ACLChecker.PERM_COMPARE, comparedn, type)) {
            cr.setResultCode(LDAPResult.INSUFFICIENT_ACCESS_RIGHTS);
         } else {
            Vector vals = anEntry.get(type);
            if (vals == null) {
               cr.setResultCode(LDAPResult.COMPARE_FALSE);
            } else {
               AttributeType at = SchemaChecker.getInstance().getAttributeType(type);
               Class valClass = null;
               Syntax compval = null;

               try {
                  if (at != null) {
                     valClass = at.getSyntaxClass();
                  } else {
                     valClass = DirectoryString.class;
                  }

                  compval = (Syntax)valClass.newInstance();
                  ((Syntax)compval).setValue(this.request.getProtocolOp().getCompareRequest().getAva().getAssertionValue().toByteArray());
               } catch (InstantiationException var11) {
                  compval = new DirectoryString(this.request.getProtocolOp().getCompareRequest().getAva().getAssertionValue().toByteArray());
               } catch (IllegalAccessException var12) {
                  compval = new DirectoryString(this.request.getProtocolOp().getCompareRequest().getAva().getAssertionValue().toByteArray());
               }

               if (vals.contains(compval)) {
                  cr.setResultCode(LDAPResult.COMPARE_TRUE);
               } else {
                  cr.setResultCode(LDAPResult.COMPARE_FALSE);
               }

            }
         }
      }
   }
}
