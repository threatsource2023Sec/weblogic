package com.octetstring.ldapv3.controls;

import com.asn1c.codec.Factory;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int64;
import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import java.util.Collection;

public class ASN1Factory implements Factory {
   public String getModuleName() {
      return "com.octetstring.ldapv3.controls";
   }

   public LDAPString createLDAPString(OctetString val) {
      return new LDAPString(val);
   }

   public PersistentSearch createPersistentSearch(Int64 val_changeTypes, Bool val_changesOnly, Bool val_returnECs) {
      return new PersistentSearch(val_changeTypes, val_changesOnly, val_returnECs);
   }

   public SortKeyList createSortKeyList(Collection val) {
      return new SortKeyList(val);
   }

   public VLVControlValue createVLVControlValue(Int32 val_size, OctetString val_cookie) {
      return new VLVControlValue(val_size, val_cookie);
   }

   public LDAPDN createLDAPDN(OctetString val) {
      return new LDAPDN(val);
   }

   public AttributeDescription createAttributeDescription(OctetString val) {
      return new AttributeDescription(val);
   }

   public MatchingRuleId createMatchingRuleId(OctetString val) {
      return new MatchingRuleId(val);
   }

   public SortKeyList_Seq createSortKeyList_Seq(OctetString val_attributeType, OctetString val_orderingRule, Bool val_reverseOrder) {
      return new SortKeyList_Seq(val_attributeType, val_orderingRule, val_reverseOrder);
   }

   public EntryChangeNotification createEntryChangeNotification(Int8 val_changeType, OctetString val_previousDN, Int64 val_changeNumber) {
      return new EntryChangeNotification(val_changeType, val_previousDN, val_changeNumber);
   }

   public SortResult createSortResult(Int8 val_sortResult, OctetString val_attributeType) {
      return new SortResult(val_sortResult, val_attributeType);
   }
}
