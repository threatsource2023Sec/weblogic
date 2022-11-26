package com.octetstring.vde.operation;

import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.AttributeValue;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.ldapv3.LDAPMessage_protocolOp;
import com.octetstring.ldapv3.PartialAttributeList;
import com.octetstring.ldapv3.PartialAttributeList_Seq;
import com.octetstring.ldapv3.PartialAttributeList_Seq_vals;
import com.octetstring.ldapv3.SearchResultDone;
import com.octetstring.ldapv3.SearchResultEntry;
import com.octetstring.vde.Attribute;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.ServerConfig;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class SearchOperation implements Operation {
   LDAPMessage request = null;
   LDAPMessage response = null;
   Credentials creds = null;
   boolean more = true;
   Vector entries = null;
   DirectoryString base = null;
   int nextEntry = 0;
   Enumeration backEnum;
   Vector attributes;
   boolean typesOnly;
   EntrySet currentEntrySet = null;
   static int anonsearchlimit = -1;
   static int authsearchlimit = -1;
   int entrycount = 0;
   int mysearchlimit = 0;
   private static final byte[] EMPTY_BYTES = new byte[0];
   private static final OctetString EMPTY_OSTRING = new OctetString();
   private static final DirectoryString EMPTY_DS = new DirectoryString("");
   private static final DirectoryString AT_ENTRYLDAPACI = new DirectoryString("entryACI");
   private static final DirectoryString AT_SUBTREELDAPACI = new DirectoryString("subtreeACI");
   private static final DirectoryString SEMICOLON = new DirectoryString(";");

   public SearchOperation(Credentials creds, LDAPMessage request) {
      this.request = request;
      this.creds = creds;
      if (anonsearchlimit == -1) {
         synchronized(this) {
            String stranonsl = (String)ServerConfig.getInstance().get("vde.searchlimit.anon");
            if (stranonsl == null) {
               anonsearchlimit = 100;
            } else {
               anonsearchlimit = new Integer(stranonsl);
            }

            String strauthsl = (String)ServerConfig.getInstance().get("vde.searchlimit.auth");
            if (strauthsl == null) {
               authsearchlimit = 1000;
            } else {
               authsearchlimit = new Integer(strauthsl);
            }
         }
      }

      if (creds.isRoot()) {
         this.mysearchlimit = 0;
      } else if (!creds.getUser().equals(EMPTY_DS)) {
         this.mysearchlimit = authsearchlimit;
      } else {
         this.mysearchlimit = anonsearchlimit;
      }

   }

   public boolean isMore() {
      return this.more;
   }

   public LDAPMessage getResponse() {
      return this.response;
   }

   private PartialAttributeList attributeListFromEntry(Entry entry, boolean returnValues, Vector attrs) {
      PartialAttributeList pal = new PartialAttributeList();
      PartialAttributeList_Seq pals = null;
      Attribute curAttr = null;
      Vector values = null;
      Syntax curval = null;
      Vector attributes = entry.getAttributes();
      int size = attributes.size();

      for(int i = 0; i < size; ++i) {
         curAttr = (Attribute)attributes.elementAt(i);
         pals = new PartialAttributeList_Seq();
         if (this.creds.isLdap2()) {
            if (curAttr.type.indexOf(SEMICOLON) < 0) {
               pals.setType(new OctetString(curAttr.type.getBytes()));
            } else {
               pals.setType(new OctetString(curAttr.type.substring(0, curAttr.type.indexOf(SEMICOLON)).getBytes()));
            }
         }

         pals.setType(new OctetString(curAttr.type.getBytes()));
         PartialAttributeList_Seq_vals palsv = new PartialAttributeList_Seq_vals();
         pals.setVals(palsv);
         if (!returnValues) {
            int vsize = curAttr.values.size();

            for(int j = 0; j < vsize; ++j) {
               curval = (Syntax)curAttr.values.elementAt(j);
               if (curval != null) {
                  palsv.addElement(new AttributeValue(new OctetString(curval.getValue())));
               }
            }
         }

         pal.addElement(pals);
      }

      return pal;
   }

   public void perform() {
      this.response = new LDAPMessage();
      LDAPMessage_protocolOp op = new LDAPMessage_protocolOp();
      this.response.setMessageID(this.request.getMessageID());
      SearchResultDone currentEntry;
      SearchResultDone srd;
      if (this.entries == null) {
         try {
            this.base = DNUtility.getInstance().normalize(new DirectoryString(this.request.getProtocolOp().getSearchRequest().getBaseObject().toByteArray()));
         } catch (InvalidDNException var8) {
            currentEntry = new SearchResultDone();
            currentEntry.setResultCode(new Int8((byte)var8.getLDAPErrorCode()));
            currentEntry.setMatchedDN(EMPTY_OSTRING);
            if (var8.getMessage() != null) {
               currentEntry.setErrorMessage(new OctetString(var8.getMessage().getBytes()));
            } else {
               currentEntry.setErrorMessage(EMPTY_OSTRING);
            }

            op.setSearchResDone(currentEntry);
            this.response.setProtocolOp(op);
            this.more = false;
            return;
         }

         int scope = this.request.getProtocolOp().getSearchRequest().getScope().intValue();
         Filter filter = this.request.getProtocolOp().getSearchRequest().getFilter();
         this.typesOnly = this.request.getProtocolOp().getSearchRequest().getTypesOnly().booleanValue();
         this.attributes = new Vector();
         Iterator enumAttrs = this.request.getProtocolOp().getSearchRequest().getAttributes().iterator();

         while(enumAttrs.hasNext()) {
            byte[] anAttr = ((OctetString)enumAttrs.next()).toByteArray();
            if (anAttr.length > 0) {
               DirectoryString oneAttr = new DirectoryString(anAttr);
               this.attributes.addElement(oneAttr);
            }
         }

         try {
            if (this.creds == null) {
               this.creds = new Credentials();
            }

            this.entries = BackendHandler.getInstance().get(this.creds.getUser(), this.base, scope, filter, this.typesOnly, this.attributes);
         } catch (DirectoryException var7) {
            srd = new SearchResultDone();
            srd.setResultCode(new Int8((byte)var7.getLDAPErrorCode()));
            srd.setMatchedDN(EMPTY_OSTRING);
            if (var7.getMessage() != null) {
               srd.setErrorMessage(new OctetString(var7.getMessage().getBytes()));
            } else {
               srd.setErrorMessage(EMPTY_OSTRING);
            }

            op.setSearchResDone(srd);
            this.response.setProtocolOp(op);
            this.more = false;
            return;
         }
      }

      if (this.backEnum == null) {
         this.backEnum = this.entries.elements();
         if (this.backEnum.hasMoreElements()) {
            this.currentEntrySet = (EntrySet)this.backEnum.nextElement();
         }

         this.nextEntry = 0;
      }

      for(boolean canProvideResult = false; !canProvideResult; ++this.nextEntry) {
         if (this.currentEntrySet == null || !this.currentEntrySet.hasMore() && !this.backEnum.hasMoreElements()) {
            currentEntry = new SearchResultDone();
            currentEntry.setResultCode(LDAPResult.SUCCESS);
            currentEntry.setMatchedDN(EMPTY_OSTRING);
            currentEntry.setErrorMessage(EMPTY_OSTRING);
            op.setSearchResDone(currentEntry);
            this.response.setProtocolOp(op);
            this.more = false;
            return;
         }

         if (this.mysearchlimit != 0 && this.entrycount >= this.mysearchlimit) {
            currentEntry = new SearchResultDone();
            currentEntry.setResultCode(LDAPResult.SIZE_LIMIT_EXCEEDED);
            currentEntry.setMatchedDN(EMPTY_OSTRING);
            currentEntry.setErrorMessage(EMPTY_OSTRING);
            op.setSearchResDone(currentEntry);
            this.response.setProtocolOp(op);
            this.more = false;
            return;
         }

         if (!this.currentEntrySet.hasMore()) {
            this.currentEntrySet = (EntrySet)this.backEnum.nextElement();
            this.nextEntry = 0;
         }

         currentEntry = null;

         Entry currentEntry;
         try {
            currentEntry = this.currentEntrySet.getNext();
            if (currentEntry == null) {
               if (this.backEnum.hasMoreElements()) {
                  this.currentEntrySet = (EntrySet)this.backEnum.nextElement();
                  this.nextEntry = 0;
               } else {
                  this.currentEntrySet = null;
               }
            }
         } catch (DirectoryException var9) {
            srd = new SearchResultDone();
            srd.setResultCode(new Int8((byte)var9.getLDAPErrorCode()));
            srd.setMatchedDN(EMPTY_OSTRING);
            srd.setErrorMessage(EMPTY_OSTRING);
            op.setSearchResDone(srd);
            this.response.setProtocolOp(op);
            this.more = false;
            return;
         }

         if (currentEntry != null) {
            currentEntry = BackendHandler.getInstance().postSearch(this.creds, currentEntry, this.attributes, this.request.getProtocolOp().getSearchRequest().getFilter(), this.request.getProtocolOp().getSearchRequest().getScope().intValue(), this.base);
            if (currentEntry != null) {
               ++this.entrycount;
               SearchResultEntry sre = new SearchResultEntry();
               sre.setObjectName(new OctetString(currentEntry.getName().getBytes()));
               sre.setAttributes(this.attributeListFromEntry(currentEntry, this.typesOnly, this.attributes));
               op.setSearchResEntry(sre);
               this.response.setProtocolOp(op);
               ++this.nextEntry;
               return;
            }
         }
      }

   }
}
