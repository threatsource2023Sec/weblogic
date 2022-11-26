package com.octetstring.ldapv3.controls;

import com.asn1c.codec.BEREncoder;
import com.asn1c.codec.Encoder;
import com.asn1c.core.BadValueException;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int64;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.asn1c.core.ValueTooLargeException;
import java.io.IOException;
import java.util.Iterator;

public class ASN1BEREncoder extends BEREncoder implements ASN1Encoder {
   private static final byte[] table = new byte[]{2, 18, 72, 4, 1, 3, 0, 4, 11, -19, 103, -24, 10, 0, 0, -56, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 50, -30, -127, 0, 0, 0, 0, 26, 55, 35, 51, 37, 112, 0, 38, 47, 53, 49, 57, 117, 120, 18, 52, 38, 68, 71, 70, 65, 64, 67, 66, 77, 76, 79, 78, 73, 72, 75, 74, 117, 116, 119, 118, 113, 112, 115, 114, 125, 124, 127, 126, 121, 120, 123, 122, 101, 100, 103, 102, 97, 96, 99, 98, 109, 108, 111, 110, 105, 104, 107, 106, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -105, -48, -14, 29, 109, 3, -122};

   public ASN1BEREncoder(Encoder encoder) {
      super(encoder, "controls/BER", table);
   }

   protected ASN1BEREncoder(Encoder encoder, ASN1BEREncoder parent, boolean indefiniteLength) {
      super(encoder, parent, indefiniteLength);
   }

   public BEREncoder createExtensionClone(Encoder encoder, boolean indefiniteLength) {
      return new ASN1BEREncoder(encoder, this, indefiniteLength);
   }

   public void writeLDAPString(LDAPString val) throws IOException, BadValueException {
      try {
         this.putLDAPString(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPString(LDAPString val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writePersistentSearch(PersistentSearch val) throws IOException, BadValueException {
      try {
         this.putPersistentSearch(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putPersistentSearch(PersistentSearch val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int64 val_changeTypes = val.getChangeTypes();
      Bool val_changesOnly = val.getChangesOnly();
      Bool val_returnECs = val.getReturnECs();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS64(val_changeTypes.longValue(), 2);
      enc0.writeBoolean(val_changesOnly.booleanValue(), 1);
      enc0.writeBoolean(val_returnECs.booleanValue(), 1);
      super.writeEndOfContents(enc0);
   }

   public void writeSortKeyList(SortKeyList val) throws IOException, BadValueException {
      try {
         this.putSortKeyList(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSortKeyList(SortKeyList val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         SortKeyList_Seq subval = (SortKeyList_Seq)iterator.next();
         enc0.putSortKeyList_Seq(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeVLVControlValue(VLVControlValue val) throws IOException, BadValueException {
      try {
         this.putVLVControlValue(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putVLVControlValue(VLVControlValue val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int32 val_size = val.getSize();
      OctetString val_cookie = val.getCookie();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS32(val_size.intValue(), 2);
      enc0.writeOctetString(val_cookie, 4);
      super.writeEndOfContents(enc0);
   }

   public void writeLDAPDN(LDAPDN val) throws IOException, BadValueException {
      try {
         this.putLDAPDN(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPDN(LDAPDN val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeAttributeDescription(AttributeDescription val) throws IOException, BadValueException {
      try {
         this.putAttributeDescription(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeDescription(AttributeDescription val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeMatchingRuleId(MatchingRuleId val) throws IOException, BadValueException {
      try {
         this.putMatchingRuleId(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putMatchingRuleId(MatchingRuleId val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeSortKeyList_Seq(SortKeyList_Seq val) throws IOException, BadValueException {
      try {
         this.putSortKeyList_Seq(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSortKeyList_Seq(SortKeyList_Seq val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_attributeType = val.getAttributeType();
      OctetString val_orderingRule = val.getOrderingRule();
      Bool val_reverseOrder = val.getReverseOrder();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      if (val_reverseOrder != null && val_reverseOrder.compareTo(ASN1Values.SortKeyList_Seq_reverseOrder_default) == 0) {
         val_reverseOrder = null;
      }

      enc0.writeOctetString(val_attributeType, 4);
      if (val_orderingRule != null) {
         enc0.writeOctetString(val_orderingRule, Integer.MIN_VALUE);
      }

      if (val_reverseOrder != null) {
         enc0.writeBoolean(val_reverseOrder.booleanValue(), -2147483647);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeEntryChangeNotification(EntryChangeNotification val) throws IOException, BadValueException {
      try {
         this.putEntryChangeNotification(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putEntryChangeNotification(EntryChangeNotification val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_changeType = val.getChangeType();
      OctetString val_previousDN = val.getPreviousDN();
      Int64 val_changeNumber = val.getChangeNumber();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS8(val_changeType.byteValue(), 10);
      if (val_previousDN != null) {
         enc0.writeOctetString(val_previousDN, 4);
      }

      if (val_changeNumber != null) {
         enc0.writeIntegerS64(val_changeNumber.longValue(), 2);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeSortResult(SortResult val) throws IOException, BadValueException {
      try {
         this.putSortResult(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSortResult(SortResult val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_sortResult = val.getSortResult();
      OctetString val_attributeType = val.getAttributeType();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS8(val_sortResult.byteValue(), 10);
      if (val_attributeType != null) {
         enc0.writeOctetString(val_attributeType, Integer.MIN_VALUE);
      }

      super.writeEndOfContents(enc0);
   }
}
