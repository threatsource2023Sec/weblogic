package com.octetstring.ldapv3.controls;

import com.asn1c.codec.BERDecoder;
import com.asn1c.codec.Decoder;
import com.asn1c.codec.FactoryMap;
import com.asn1c.core.BadDataException;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int64;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class ASN1BERDecoder extends BERDecoder implements ASN1Decoder {
   protected ASN1Factory factory;
   private static final byte[] table = new byte[]{2, 18, 72, 4, 1, 3, 0, 4, 11, -19, 103, -24, 10, 0, 0, -56, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 50, -30, -127, 0, 0, 0, 0, 26, 55, 35, 51, 37, 112, 0, 38, 47, 53, 49, 57, 117, 120, 18, 52, 38, 68, 71, 70, 65, 64, 67, 66, 77, 76, 79, 78, 73, 72, 75, 74, 117, 116, 119, 118, 113, 112, 115, 114, 125, 124, 127, 126, 121, 120, 123, 122, 101, 100, 103, 102, 97, 96, 99, 98, 109, 108, 111, 110, 105, 104, 107, 106, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -105, -48, -14, 29, 109, 3, -122};

   public ASN1BERDecoder(Decoder decoder) {
      this(decoder, (FactoryMap)null);
   }

   public ASN1BERDecoder(Decoder decoder, FactoryMap factories) {
      super(decoder, "controls/BER", factories, table);
      this.factory = (ASN1Factory)this.getFactory("com.octetstring.ldapv3.controls");
      if (this.factory == null) {
         this.getFactoryMap().put(this.factory = new ASN1Factory());
      }

   }

   protected ASN1BERDecoder(Decoder decoder, ASN1BERDecoder parent, boolean indefiniteLength) {
      super(decoder, parent, indefiniteLength);
      this.factory = (ASN1Factory)this.getFactory("com.octetstring.ldapv3.controls");
   }

   public ASN1Factory getFactory() {
      return this.factory;
   }

   public BERDecoder createExtensionClone(Decoder decoder, boolean indefiniteLength) {
      return new ASN1BERDecoder(decoder, this, indefiniteLength);
   }

   public LDAPString readLDAPString() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPString val = this.getLDAPString(0);
      this.flushIn();
      return val;
   }

   public LDAPString getLDAPString(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      LDAPString val = this.getFactory().createLDAPString(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public PersistentSearch readPersistentSearch() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      PersistentSearch val = this.getPersistentSearch(0);
      this.flushIn();
      return val;
   }

   public PersistentSearch getPersistentSearch(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int64 val_changeTypes = this.baseTypeFactory.createInt64(dec0.readIntegerS64(2));
      Bool val_changesOnly = this.baseTypeFactory.createBool(dec0.readBoolean(1));
      Bool val_returnECs = this.baseTypeFactory.createBool(dec0.readBoolean(1));
      this.readEndOfContents(dec0);
      PersistentSearch val = this.getFactory().createPersistentSearch(val_changeTypes, val_changesOnly, val_returnECs);
      return val;
   }

   public SortKeyList readSortKeyList() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SortKeyList val = this.getSortKeyList(0);
      this.flushIn();
      return val;
   }

   public SortKeyList getSortKeyList(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         SortKeyList_Seq subval = dec0.getSortKeyList_Seq(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      SortKeyList val = this.getFactory().createSortKeyList(list);
      return val;
   }

   public VLVControlValue readVLVControlValue() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      VLVControlValue val = this.getVLVControlValue(0);
      this.flushIn();
      return val;
   }

   public VLVControlValue getVLVControlValue(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int32 val_size = this.baseTypeFactory.createInt32(dec0.readIntegerS32(2));
      OctetString val_cookie = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      this.readEndOfContents(dec0);
      VLVControlValue val = this.getFactory().createVLVControlValue(val_size, val_cookie);
      return val;
   }

   public LDAPDN readLDAPDN() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPDN val = this.getLDAPDN(0);
      this.flushIn();
      return val;
   }

   public LDAPDN getLDAPDN(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      LDAPDN val = this.getFactory().createLDAPDN(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public AttributeDescription readAttributeDescription() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeDescription val = this.getAttributeDescription(0);
      this.flushIn();
      return val;
   }

   public AttributeDescription getAttributeDescription(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AttributeDescription val = this.getFactory().createAttributeDescription(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public MatchingRuleId readMatchingRuleId() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      MatchingRuleId val = this.getMatchingRuleId(0);
      this.flushIn();
      return val;
   }

   public MatchingRuleId getMatchingRuleId(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      MatchingRuleId val = this.getFactory().createMatchingRuleId(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public SortKeyList_Seq readSortKeyList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SortKeyList_Seq val = this.getSortKeyList_Seq(0);
      this.flushIn();
      return val;
   }

   public SortKeyList_Seq getSortKeyList_Seq(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_attributeType = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_orderingRule;
      switch (dec0.peekTag()) {
         case Integer.MIN_VALUE:
            val_orderingRule = this.baseTypeFactory.createOctetString(dec0.readOctetString(Integer.MIN_VALUE));
            break;
         default:
            val_orderingRule = null;
      }

      Bool val_reverseOrder;
      switch (dec0.peekTag()) {
         case -2147483647:
            val_reverseOrder = this.baseTypeFactory.createBool(dec0.readBoolean(-2147483647));
            break;
         default:
            val_reverseOrder = null;
      }

      this.readEndOfContents(dec0);
      SortKeyList_Seq val = this.getFactory().createSortKeyList_Seq(val_attributeType, val_orderingRule, val_reverseOrder);
      return val;
   }

   public EntryChangeNotification readEntryChangeNotification() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      EntryChangeNotification val = this.getEntryChangeNotification(0);
      this.flushIn();
      return val;
   }

   public EntryChangeNotification getEntryChangeNotification(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int8 val_changeType = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      OctetString val_previousDN;
      switch (dec0.peekTag()) {
         case 4:
            val_previousDN = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
            break;
         default:
            val_previousDN = null;
      }

      Int64 val_changeNumber;
      switch (dec0.peekTag()) {
         case 2:
            val_changeNumber = this.baseTypeFactory.createInt64(dec0.readIntegerS64(2));
            break;
         default:
            val_changeNumber = null;
      }

      this.readEndOfContents(dec0);
      EntryChangeNotification val = this.getFactory().createEntryChangeNotification(val_changeType, val_previousDN, val_changeNumber);
      return val;
   }

   public SortResult readSortResult() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SortResult val = this.getSortResult(0);
      this.flushIn();
      return val;
   }

   public SortResult getSortResult(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int8 val_sortResult = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      OctetString val_attributeType;
      switch (dec0.peekTag()) {
         case Integer.MIN_VALUE:
            val_attributeType = this.baseTypeFactory.createOctetString(dec0.readOctetString(Integer.MIN_VALUE));
            break;
         default:
            val_attributeType = null;
      }

      this.readEndOfContents(dec0);
      SortResult val = this.getFactory().createSortResult(val_sortResult, val_attributeType);
      return val;
   }
}
