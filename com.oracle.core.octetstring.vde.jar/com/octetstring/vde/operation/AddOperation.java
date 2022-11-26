package com.octetstring.vde.operation;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.AddResponse;
import com.octetstring.ldapv3.AttributeList;
import com.octetstring.ldapv3.AttributeList_Seq;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.LDAPURL;
import com.octetstring.ldapv3.Referral;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectorySchemaViolation;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.UnixCrypt;
import java.util.Iterator;
import java.util.Vector;

public class AddOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final Referral EMPTY_REFERRAL = new Referral();
   private static final OctetString EMPTY_OSTRING = new OctetString();
   private static final DirectoryString USERPASSWORD = new DirectoryString("userPassword");

   public AddOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() {
      this.response = new LDAPMessage();
      AddResponse addResponse = new AddResponse();
      addResponse.setMatchedDN(EMPTY_OSTRING);
      addResponse.setErrorMessage(EMPTY_OSTRING);

      try {
         Entry newEntry = this.requestToEntry();
         Consumer con = BackendHandler.getInstance().getReplica(newEntry.getName());
         if (con != null && !this.creds.getUser().equals(con.getBinddn())) {
            addResponse.setResultCode(LDAPResult.REFERRAL);
            Referral myref = new Referral();
            myref.addElement(new LDAPURL(new OctetString(con.getMasterURL().getBytes())));
            addResponse.setReferral(myref);
         } else {
            boolean oldisroot = this.creds.isRoot();
            if (con != null) {
               this.creds.setRoot(true);
            }

            addResponse.setResultCode(BackendHandler.getInstance().add(this.creds, newEntry));
            this.creds.setRoot(oldisroot);
         }
      } catch (DirectorySchemaViolation var5) {
         addResponse.setResultCode(LDAPResult.OBJECT_CLASS_VIOLATION);
         addResponse.setErrorMessage(new OctetString(var5.getMessage().getBytes()));
      } catch (InvalidDNException var6) {
         addResponse.setResultCode(LDAPResult.INVALID_DN_SYNTAX);
         if (var6.getMessage() != null) {
            addResponse.setErrorMessage(new OctetString(var6.getMessage().getBytes()));
         }
      }

      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      op.setAddResponse(addResponse);
      this.response.setMessageID(this.request.getMessageID());
      this.response.setProtocolOp(op);
   }

   public Entry requestToEntry() throws InvalidDNException {
      Entry entry = new Entry(new DirectoryString(this.request.getProtocolOp().getAddRequest().getEntry().toByteArray()));
      AttributeList attrList = this.request.getProtocolOp().getAddRequest().getAttributes();
      Iterator enumAttr = attrList.iterator();

      while(true) {
         AttributeList_Seq als;
         do {
            if (!enumAttr.hasNext()) {
               return entry;
            }

            als = (AttributeList_Seq)enumAttr.next();
         } while(als.getType() == null);

         DirectoryString type = new DirectoryString(als.getType().toByteArray());
         AttributeType at = SchemaChecker.getInstance().getAttributeType(type);
         Class valClass = null;
         if (at != null) {
            valClass = at.getSyntaxClass();
         } else {
            valClass = DirectoryString.class;
         }

         Vector values = new Vector();
         Iterator enumVal = als.getVals().iterator();

         while(enumVal.hasNext()) {
            byte[] thisVal = ((OctetString)enumVal.next()).toByteArray();
            if (thisVal.length > 0) {
               try {
                  Syntax synVal = (Syntax)valClass.newInstance();
                  synVal.setValue(thisVal);
                  values.addElement(synVal);
               } catch (InstantiationException var12) {
                  Logger.getInstance().printStackTrace(var12);
               } catch (IllegalAccessException var13) {
                  Logger.getInstance().printStackTrace(var13);
               }
            }
         }

         if (values.size() > 0) {
            entry.put(type, values, false);
         }
      }
   }

   private byte[] transform(DirectoryString type, byte[] value) {
      return type.equals(USERPASSWORD) ? ("{crypt}" + UnixCrypt.crypt(new String(value))).getBytes() : value;
   }
}
