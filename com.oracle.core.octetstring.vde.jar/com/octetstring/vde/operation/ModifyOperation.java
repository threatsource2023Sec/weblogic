package com.octetstring.vde.operation;

import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.AttributeTypeAndValues;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.LDAPURL;
import com.octetstring.ldapv3.ModifyRequest_modification_Seq;
import com.octetstring.ldapv3.ModifyResponse;
import com.octetstring.ldapv3.Referral;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.replication.Consumer;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.UnixCrypt;
import java.util.Iterator;
import java.util.Vector;

public class ModifyOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final OctetString EMPTY_OSTRING = new OctetString();
   private static final Referral EMPTY_REFERRAL = new Referral();
   private static final DirectoryString USERPASSWORD = new DirectoryString("userPassword");

   public ModifyOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   public void perform() {
      DirectoryString name = null;

      try {
         name = DNUtility.getInstance().normalize(new DirectoryString(this.request.getProtocolOp().getModifyRequest().getObject().toByteArray()));
      } catch (InvalidDNException var16) {
      }

      Vector changeVector = new Vector();
      Iterator enumMods = this.request.getProtocolOp().getModifyRequest().getModification().iterator();

      while(enumMods.hasNext()) {
         ModifyRequest_modification_Seq oneMod = (ModifyRequest_modification_Seq)enumMods.next();
         int modType = oneMod.getOperation().intValue();
         AttributeTypeAndValues modification = oneMod.getModification();
         DirectoryString modAttr = new DirectoryString(modification.getType().toByteArray());
         AttributeType at = SchemaChecker.getInstance().getAttributeType(modAttr);
         Class valClass = null;
         if (at != null) {
            valClass = at.getSyntaxClass();
         } else {
            valClass = DirectoryString.class;
         }

         Vector modValues = new Vector();
         Iterator enumVals = modification.getVals().iterator();

         while(enumVals.hasNext()) {
            byte[] thisVal = ((OctetString)enumVals.next()).toByteArray();
            if (thisVal.length > 0) {
               try {
                  Syntax modValue = (Syntax)valClass.newInstance();
                  modValue.setValue(thisVal);
                  modValues.addElement(modValue);
               } catch (InstantiationException var14) {
                  Logger.getInstance().printStackTrace(var14);
               } catch (IllegalAccessException var15) {
                  Logger.getInstance().printStackTrace(var15);
               }
            }
         }

         EntryChange oneChange = new EntryChange(modType, modAttr, modValues);
         changeVector.addElement(oneChange);
      }

      this.response = new LDAPMessage();
      ModifyResponse modifyResponse = new ModifyResponse();
      modifyResponse.setResultCode(LDAPResult.SUCCESS);
      modifyResponse.setMatchedDN(EMPTY_OSTRING);
      modifyResponse.setErrorMessage(EMPTY_OSTRING);
      if (name == null) {
         modifyResponse.setResultCode(LDAPResult.INVALID_DN_SYNTAX);
         LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
         op.setModifyResponse(modifyResponse);
         this.response.setMessageID(this.request.getMessageID());
         this.response.setProtocolOp(op);
      } else {
         Consumer con = BackendHandler.getInstance().getReplica(name);
         if (con != null && !con.getBinddn().equals(this.creds.getUser())) {
            modifyResponse.setResultCode(LDAPResult.REFERRAL);
            Referral myref = new Referral();
            modifyResponse.setReferral(myref);
            myref.addElement(new LDAPURL(new OctetString(con.getMasterURL().getBytes())));
         } else {
            boolean oldisroot = this.creds.isRoot();
            if (con != null) {
               this.creds.setRoot(true);
            }

            try {
               BackendHandler.getInstance().modify(this.creds, name, changeVector);
            } catch (DirectoryException var17) {
               modifyResponse.setResultCode(new Int8((byte)var17.getLDAPErrorCode()));
               if (var17.getMessage() != null) {
                  modifyResponse.setErrorMessage(new OctetString(var17.getMessage().getBytes()));
               }
            }

            this.creds.setRoot(oldisroot);
         }

         LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
         op.setModifyResponse(modifyResponse);
         this.response.setMessageID(this.request.getMessageID());
         this.response.setProtocolOp(op);
      }
   }

   private byte[] transform(DirectoryString type, byte[] value) {
      return type.equals(USERPASSWORD) ? ("{crypt}" + UnixCrypt.crypt(new String(value))).getBytes() : value;
   }
}
