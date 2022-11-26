package com.octetstring.ldapv3;

import com.asn1c.codec.BEREncoder;
import com.asn1c.codec.Encoder;
import com.asn1c.core.BadValueException;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int8;
import com.asn1c.core.Null;
import com.asn1c.core.OctetString;
import com.asn1c.core.ValueTooLargeException;
import java.io.IOException;
import java.util.Iterator;

public class ASN1BEREncoder extends BEREncoder implements ASN1Encoder {
   private static final byte[] table = new byte[]{2, 18, 72, 4, 1, 3, 0, 4, 11, -19, 103, -24, 10, 0, 0, -56, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 50, -30, -127, 0, 0, 0, 0, 26, 55, 35, 51, 37, 112, 0, 38, 47, 53, 49, 57, 117, 120, 18, 52, 38, 68, 71, 70, 65, 64, 67, 66, 77, 76, 79, 78, 73, 72, 75, 74, 117, 116, 119, 118, 113, 112, 115, 114, 125, 124, 127, 126, 121, 120, 123, 122, 101, 100, 103, 102, 97, 96, 99, 98, 109, 108, 111, 110, 105, 104, 107, 106, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -116, -105, -48, -14, 29, 109, 3, -122};

   public ASN1BEREncoder(Encoder encoder) {
      super(encoder, "ldapv3/BER", table);
   }

   protected ASN1BEREncoder(Encoder encoder, ASN1BEREncoder parent, boolean indefiniteLength) {
      super(encoder, parent, indefiniteLength);
   }

   public BEREncoder createExtensionClone(Encoder encoder, boolean indefiniteLength) {
      return new ASN1BEREncoder(encoder, this, indefiniteLength);
   }

   public void writeMessageID(MessageID val) throws IOException, BadValueException {
      try {
         this.putMessageID(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putMessageID(MessageID val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeIntegerS32(val.intValue(), tag != 0 ? tag : 2);
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

   public void writeLDAPOID(LDAPOID val) throws IOException, BadValueException {
      try {
         this.putLDAPOID(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPOID(LDAPOID val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeAttributeValue(AttributeValue val) throws IOException, BadValueException {
      try {
         this.putAttributeValue(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeValue(AttributeValue val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeAssertionValue(AssertionValue val) throws IOException, BadValueException {
      try {
         this.putAssertionValue(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAssertionValue(AssertionValue val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeUnbindRequest(UnbindRequest val) throws IOException, BadValueException {
      try {
         this.putUnbindRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putUnbindRequest(UnbindRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeNull(val, tag != 0 ? tag : 1073741826);
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

   public void writeRelativeLDAPDN(RelativeLDAPDN val) throws IOException, BadValueException {
      try {
         this.putRelativeLDAPDN(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putRelativeLDAPDN(RelativeLDAPDN val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeAttributeType(AttributeType val) throws IOException, BadValueException {
      try {
         this.putAttributeType(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeType(AttributeType val, int tag) throws IOException, BadValueException, ValueTooLargeException {
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

   public void writeAttributeDescriptionList(AttributeDescriptionList val) throws IOException, BadValueException {
      try {
         this.putAttributeDescriptionList(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeDescriptionList(AttributeDescriptionList val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAttributeValueAssertion(AttributeValueAssertion val) throws IOException, BadValueException {
      try {
         this.putAttributeValueAssertion(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeValueAssertion(AttributeValueAssertion val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_attributeDesc = val.getAttributeDesc();
      OctetString val_assertionValue = val.getAssertionValue();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_attributeDesc, 4);
      enc0.writeOctetString(val_assertionValue, 4);
      super.writeEndOfContents(enc0);
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

   public void writeLDAPURL(LDAPURL val) throws IOException, BadValueException {
      try {
         this.putLDAPURL(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPURL(LDAPURL val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 4);
   }

   public void writeControls(Controls val) throws IOException, BadValueException {
      try {
         this.putControls(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putControls(Controls val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         Control subval = (Control)iterator.next();
         enc0.putControl(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeControl(Control val) throws IOException, BadValueException {
      try {
         this.putControl(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putControl(Control val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_controlType = val.getControlType();
      Bool val_criticality = val.getCriticality();
      OctetString val_controlValue = val.getControlValue();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      if (val_criticality != null && val_criticality.compareTo(ASN1Values.Control_criticality_default) == 0) {
         val_criticality = null;
      }

      enc0.writeOctetString(val_controlType, 4);
      if (val_criticality != null) {
         enc0.writeBoolean(val_criticality.booleanValue(), 1);
      }

      if (val_controlValue != null) {
         enc0.writeOctetString(val_controlValue, 4);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeSaslCredentials(SaslCredentials val) throws IOException, BadValueException {
      try {
         this.putSaslCredentials(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSaslCredentials(SaslCredentials val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_mechanism = val.getMechanism();
      OctetString val_credentials = val.getCredentials();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_mechanism, 4);
      if (val_credentials != null) {
         enc0.writeOctetString(val_credentials, 4);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeMatchingRuleAssertion(MatchingRuleAssertion val) throws IOException, BadValueException {
      try {
         this.putMatchingRuleAssertion(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putMatchingRuleAssertion(MatchingRuleAssertion val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_matchingRule = val.getMatchingRule();
      OctetString val_type = val.getType();
      OctetString val_matchValue = val.getMatchValue();
      Bool val_dnAttributes = val.getDnAttributes();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      if (val_dnAttributes != null && val_dnAttributes.compareTo(ASN1Values.MatchingRuleAssertion_dnAttributes_default) == 0) {
         val_dnAttributes = null;
      }

      if (val_matchingRule != null) {
         enc0.writeOctetString(val_matchingRule, -2147483647);
      }

      if (val_type != null) {
         enc0.writeOctetString(val_type, -2147483646);
      }

      enc0.writeOctetString(val_matchValue, -2147483645);
      if (val_dnAttributes != null) {
         enc0.writeBoolean(val_dnAttributes.booleanValue(), -2147483644);
      }

      super.writeEndOfContents(enc0);
   }

   public void writePartialAttributeList(PartialAttributeList val) throws IOException, BadValueException {
      try {
         this.putPartialAttributeList(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putPartialAttributeList(PartialAttributeList val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         PartialAttributeList_Seq subval = (PartialAttributeList_Seq)iterator.next();
         enc0.putPartialAttributeList_Seq(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeSearchResultReference(SearchResultReference val) throws IOException, BadValueException {
      try {
         this.putSearchResultReference(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSearchResultReference(SearchResultReference val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741843, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAttributeList(AttributeList val) throws IOException, BadValueException {
      try {
         this.putAttributeList(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeList(AttributeList val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         AttributeList_Seq subval = (AttributeList_Seq)iterator.next();
         enc0.putAttributeList_Seq(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeDelRequest(DelRequest val) throws IOException, BadValueException {
      try {
         this.putDelRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putDelRequest(DelRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeOctetString(val, tag != 0 ? tag : 1073741834);
   }

   public void writeModifyDNRequest(ModifyDNRequest val) throws IOException, BadValueException {
      try {
         this.putModifyDNRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyDNRequest(ModifyDNRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_entry = val.getEntry();
      OctetString val_newrdn = val.getNewrdn();
      Bool val_deleteoldrdn = val.getDeleteoldrdn();
      OctetString val_newSuperior = val.getNewSuperior();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741836, false);
      enc0.writeOctetString(val_entry, 4);
      enc0.writeOctetString(val_newrdn, 4);
      enc0.writeBoolean(val_deleteoldrdn.booleanValue(), 1);
      if (val_newSuperior != null) {
         enc0.writeOctetString(val_newSuperior, Integer.MIN_VALUE);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeCompareRequest(CompareRequest val) throws IOException, BadValueException {
      try {
         this.putCompareRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putCompareRequest(CompareRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_entry = val.getEntry();
      AttributeValueAssertion val_ava = val.getAva();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741838, false);
      enc0.writeOctetString(val_entry, 4);
      enc0.putAttributeValueAssertion(val_ava, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeAbandonRequest(AbandonRequest val) throws IOException, BadValueException {
      try {
         this.putAbandonRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAbandonRequest(AbandonRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.writeIntegerS32(val.intValue(), tag != 0 ? tag : 1073741840);
   }

   public void writeExtendedRequest(ExtendedRequest val) throws IOException, BadValueException {
      try {
         this.putExtendedRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putExtendedRequest(ExtendedRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_requestName = val.getRequestName();
      OctetString val_requestValue = val.getRequestValue();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741847, false);
      enc0.writeOctetString(val_requestName, Integer.MIN_VALUE);
      if (val_requestValue != null) {
         enc0.writeOctetString(val_requestValue, -2147483647);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeSubstringFilter_substrings(SubstringFilter_substrings val) throws IOException, BadValueException {
      try {
         this.putSubstringFilter_substrings(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSubstringFilter_substrings(SubstringFilter_substrings val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         SubstringFilter_substrings_Seq subval = (SubstringFilter_substrings_Seq)iterator.next();
         enc0.putSubstringFilter_substrings_Seq(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeSubstringFilter_substrings_Seq(SubstringFilter_substrings_Seq val) throws IOException, BadValueException {
      try {
         this.putSubstringFilter_substrings_Seq(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSubstringFilter_substrings_Seq(SubstringFilter_substrings_Seq val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      switch (val.getSelector()) {
         case 0:
            OctetString val_initial = (OctetString)val.getValue();
            this.writeOctetString(val_initial, Integer.MIN_VALUE);
            break;
         case 1:
            OctetString val_any = (OctetString)val.getValue();
            this.writeOctetString(val_any, -2147483647);
            break;
         case 2:
            OctetString val_final_ = (OctetString)val.getValue();
            this.writeOctetString(val_final_, -2147483646);
            break;
         default:
            throw new BadValueException();
      }

   }

   public void writeAttributeTypeAndValues_vals(AttributeTypeAndValues_vals val) throws IOException, BadValueException {
      try {
         this.putAttributeTypeAndValues_vals(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeTypeAndValues_vals(AttributeTypeAndValues_vals val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 17, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeModifyRequest_modification(ModifyRequest_modification val) throws IOException, BadValueException {
      try {
         this.putModifyRequest_modification(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyRequest_modification(ModifyRequest_modification val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         ModifyRequest_modification_Seq subval = (ModifyRequest_modification_Seq)iterator.next();
         enc0.putModifyRequest_modification_Seq(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writePartialAttributeList_Seq_vals(PartialAttributeList_Seq_vals val) throws IOException, BadValueException {
      try {
         this.putPartialAttributeList_Seq_vals(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putPartialAttributeList_Seq_vals(PartialAttributeList_Seq_vals val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 17, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeFilter_and(Filter_and val) throws IOException, BadValueException {
      try {
         this.putFilter_and(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putFilter_and(Filter_and val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : Integer.MIN_VALUE, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         Filter subval = (Filter)iterator.next();
         enc0.putFilter(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeFilter_or(Filter_or val) throws IOException, BadValueException {
      try {
         this.putFilter_or(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putFilter_or(Filter_or val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : -2147483647, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         Filter subval = (Filter)iterator.next();
         enc0.putFilter(subval, 0);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAttribute_vals(Attribute_vals val) throws IOException, BadValueException {
      try {
         this.putAttribute_vals(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttribute_vals(Attribute_vals val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 17, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAttributeList_Seq_vals(AttributeList_Seq_vals val) throws IOException, BadValueException {
      try {
         this.putAttributeList_Seq_vals(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeList_Seq_vals(AttributeList_Seq_vals val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 17, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAttribute(Attribute val) throws IOException, BadValueException {
      try {
         this.putAttribute(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttribute(Attribute val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_type = val.getType();
      Attribute_vals val_vals = val.getVals();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_type, 4);
      enc0.putAttribute_vals(val_vals, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeReferral(Referral val) throws IOException, BadValueException {
      try {
         this.putReferral(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putReferral(Referral val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      Iterator iterator = val.iterator();
      int len = val.size();

      for(int i = 0; i < len; ++i) {
         OctetString subval = (OctetString)iterator.next();
         enc0.writeOctetString(subval, 4);
      }

      this.writeEndOfContents(enc0);
   }

   public void writeAuthenticationChoice(AuthenticationChoice val) throws IOException, BadValueException {
      try {
         this.putAuthenticationChoice(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAuthenticationChoice(AuthenticationChoice val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      switch (val.getSelector()) {
         case 0:
            OctetString val_simple = (OctetString)val.getValue();
            this.writeOctetString(val_simple, Integer.MIN_VALUE);
            break;
         case 1:
            SaslCredentials val_sasl = (SaslCredentials)val.getValue();
            this.putSaslCredentials(val_sasl, -2147483645);
            break;
         default:
            throw new BadValueException();
      }

   }

   public void writeBindResponse(BindResponse val) throws IOException, BadValueException {
      try {
         this.putBindResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putBindResponse(BindResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_resultCode = val.getResultCode();
      OctetString val_matchedDN = val.getMatchedDN();
      OctetString val_errorMessage = val.getErrorMessage();
      Referral val_referral = val.getReferral();
      OctetString val_serverSaslCreds = val.getServerSaslCreds();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741825, false);
      enc0.writeIntegerS8(val_resultCode.byteValue(), 10);
      enc0.writeOctetString(val_matchedDN, 4);
      enc0.writeOctetString(val_errorMessage, 4);
      if (val_referral != null) {
         enc0.putReferral(val_referral, -2147483645);
      }

      if (val_serverSaslCreds != null) {
         enc0.writeOctetString(val_serverSaslCreds, -2147483641);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeSubstringFilter(SubstringFilter val) throws IOException, BadValueException {
      try {
         this.putSubstringFilter(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSubstringFilter(SubstringFilter val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_type = val.getType();
      SubstringFilter_substrings val_substrings = val.getSubstrings();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_type, 4);
      enc0.putSubstringFilter_substrings(val_substrings, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeSearchResultEntry(SearchResultEntry val) throws IOException, BadValueException {
      try {
         this.putSearchResultEntry(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSearchResultEntry(SearchResultEntry val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_objectName = val.getObjectName();
      PartialAttributeList val_attributes = val.getAttributes();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741828, false);
      enc0.writeOctetString(val_objectName, 4);
      enc0.putPartialAttributeList(val_attributes, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeModifyRequest(ModifyRequest val) throws IOException, BadValueException {
      try {
         this.putModifyRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyRequest(ModifyRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_object = val.getObject();
      ModifyRequest_modification val_modification = val.getModification();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741830, false);
      enc0.writeOctetString(val_object, 4);
      enc0.putModifyRequest_modification(val_modification, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeAttributeTypeAndValues(AttributeTypeAndValues val) throws IOException, BadValueException {
      try {
         this.putAttributeTypeAndValues(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeTypeAndValues(AttributeTypeAndValues val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_type = val.getType();
      AttributeTypeAndValues_vals val_vals = val.getVals();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_type, 4);
      enc0.putAttributeTypeAndValues_vals(val_vals, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeAddRequest(AddRequest val) throws IOException, BadValueException {
      try {
         this.putAddRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAddRequest(AddRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_entry = val.getEntry();
      AttributeList val_attributes = val.getAttributes();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741832, false);
      enc0.writeOctetString(val_entry, 4);
      enc0.putAttributeList(val_attributes, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeExtendedResponse(ExtendedResponse val) throws IOException, BadValueException {
      try {
         this.putExtendedResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putExtendedResponse(ExtendedResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_resultCode = val.getResultCode();
      OctetString val_matchedDN = val.getMatchedDN();
      OctetString val_errorMessage = val.getErrorMessage();
      Referral val_referral = val.getReferral();
      OctetString val_responseName = val.getResponseName();
      OctetString val_response = val.getResponse();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741848, false);
      enc0.writeIntegerS8(val_resultCode.byteValue(), 10);
      enc0.writeOctetString(val_matchedDN, 4);
      enc0.writeOctetString(val_errorMessage, 4);
      if (val_referral != null) {
         enc0.putReferral(val_referral, -2147483645);
      }

      if (val_responseName != null) {
         enc0.writeOctetString(val_responseName, -2147483638);
      }

      if (val_response != null) {
         enc0.writeOctetString(val_response, -2147483637);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeAttributeList_Seq(AttributeList_Seq val) throws IOException, BadValueException {
      try {
         this.putAttributeList_Seq(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAttributeList_Seq(AttributeList_Seq val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_type = val.getType();
      AttributeList_Seq_vals val_vals = val.getVals();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_type, 4);
      enc0.putAttributeList_Seq_vals(val_vals, 0);
      super.writeEndOfContents(enc0);
   }

   public void writePartialAttributeList_Seq(PartialAttributeList_Seq val) throws IOException, BadValueException {
      try {
         this.putPartialAttributeList_Seq(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putPartialAttributeList_Seq(PartialAttributeList_Seq val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_type = val.getType();
      PartialAttributeList_Seq_vals val_vals = val.getVals();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeOctetString(val_type, 4);
      enc0.putPartialAttributeList_Seq_vals(val_vals, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeModifyRequest_modification_Seq(ModifyRequest_modification_Seq val) throws IOException, BadValueException {
      try {
         this.putModifyRequest_modification_Seq(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyRequest_modification_Seq(ModifyRequest_modification_Seq val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_operation = val.getOperation();
      AttributeTypeAndValues val_modification = val.getModification();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS8(val_operation.byteValue(), 10);
      enc0.putAttributeTypeAndValues(val_modification, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeLDAPResult(LDAPResult val) throws IOException, BadValueException {
      try {
         this.putLDAPResult(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPResult(LDAPResult val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_resultCode = val.getResultCode();
      OctetString val_matchedDN = val.getMatchedDN();
      OctetString val_errorMessage = val.getErrorMessage();
      Referral val_referral = val.getReferral();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS8(val_resultCode.byteValue(), 10);
      enc0.writeOctetString(val_matchedDN, 4);
      enc0.writeOctetString(val_errorMessage, 4);
      if (val_referral != null) {
         enc0.putReferral(val_referral, -2147483645);
      }

      super.writeEndOfContents(enc0);
   }

   public void writeBindRequest(BindRequest val) throws IOException, BadValueException {
      try {
         this.putBindRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putBindRequest(BindRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int8 val_version = val.getVersion();
      OctetString val_name = val.getName();
      AuthenticationChoice val_authentication = val.getAuthentication();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741824, false);
      enc0.writeIntegerS8(val_version.byteValue(), 2);
      enc0.writeOctetString(val_name, 4);
      enc0.putAuthenticationChoice(val_authentication, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeSearchResultDone(SearchResultDone val) throws IOException, BadValueException {
      try {
         this.putSearchResultDone(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSearchResultDone(SearchResultDone val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741829);
   }

   public void writeModifyResponse(ModifyResponse val) throws IOException, BadValueException {
      try {
         this.putModifyResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyResponse(ModifyResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741831);
   }

   public void writeAddResponse(AddResponse val) throws IOException, BadValueException {
      try {
         this.putAddResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putAddResponse(AddResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741833);
   }

   public void writeDelResponse(DelResponse val) throws IOException, BadValueException {
      try {
         this.putDelResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putDelResponse(DelResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741835);
   }

   public void writeModifyDNResponse(ModifyDNResponse val) throws IOException, BadValueException {
      try {
         this.putModifyDNResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putModifyDNResponse(ModifyDNResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741837);
   }

   public void writeCompareResponse(CompareResponse val) throws IOException, BadValueException {
      try {
         this.putCompareResponse(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putCompareResponse(CompareResponse val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      this.putLDAPResult(val, tag != 0 ? tag : 1073741839);
   }

   public void writeFilter(Filter val) throws IOException, BadValueException {
      try {
         this.putFilter(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putFilter(Filter val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      switch (val.getSelector()) {
         case 0:
            Filter_and val_and = (Filter_and)val.getValue();
            this.putFilter_and(val_and, 0);
            break;
         case 1:
            Filter_or val_or = (Filter_or)val.getValue();
            this.putFilter_or(val_or, 0);
            break;
         case 2:
            Filter val_not = (Filter)val.getValue();
            ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(-2147483646, false);
            enc0.putFilter(val_not, 0);
            super.writeEndOfContents(enc0);
            break;
         case 3:
            AttributeValueAssertion val_equalityMatch = (AttributeValueAssertion)val.getValue();
            this.putAttributeValueAssertion(val_equalityMatch, -2147483645);
            break;
         case 4:
            SubstringFilter val_substrings = (SubstringFilter)val.getValue();
            this.putSubstringFilter(val_substrings, -2147483644);
            break;
         case 5:
            AttributeValueAssertion val_greaterOrEqual = (AttributeValueAssertion)val.getValue();
            this.putAttributeValueAssertion(val_greaterOrEqual, -2147483643);
            break;
         case 6:
            AttributeValueAssertion val_lessOrEqual = (AttributeValueAssertion)val.getValue();
            this.putAttributeValueAssertion(val_lessOrEqual, -2147483642);
            break;
         case 7:
            OctetString val_present = (OctetString)val.getValue();
            this.writeOctetString(val_present, -2147483641);
            break;
         case 8:
            AttributeValueAssertion val_approxMatch = (AttributeValueAssertion)val.getValue();
            this.putAttributeValueAssertion(val_approxMatch, -2147483640);
            break;
         case 9:
            MatchingRuleAssertion val_extensibleMatch = (MatchingRuleAssertion)val.getValue();
            this.putMatchingRuleAssertion(val_extensibleMatch, -2147483639);
            break;
         default:
            throw new BadValueException();
      }

   }

   public void writeSearchRequest(SearchRequest val) throws IOException, BadValueException {
      try {
         this.putSearchRequest(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putSearchRequest(SearchRequest val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      OctetString val_baseObject = val.getBaseObject();
      Int8 val_scope = val.getScope();
      Int8 val_derefAliases = val.getDerefAliases();
      Int32 val_sizeLimit = val.getSizeLimit();
      Int32 val_timeLimit = val.getTimeLimit();
      Bool val_typesOnly = val.getTypesOnly();
      Filter val_filter = val.getFilter();
      AttributeDescriptionList val_attributes = val.getAttributes();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 1073741827, false);
      enc0.writeOctetString(val_baseObject, 4);
      enc0.writeIntegerS8(val_scope.byteValue(), 10);
      enc0.writeIntegerS8(val_derefAliases.byteValue(), 10);
      enc0.writeIntegerS32(val_sizeLimit.intValue(), 2);
      enc0.writeIntegerS32(val_timeLimit.intValue(), 2);
      enc0.writeBoolean(val_typesOnly.booleanValue(), 1);
      enc0.putFilter(val_filter, 0);
      enc0.putAttributeDescriptionList(val_attributes, 0);
      super.writeEndOfContents(enc0);
   }

   public void writeLDAPMessage_protocolOp(LDAPMessage_protocolOp val) throws IOException, BadValueException {
      try {
         this.putLDAPMessage_protocolOp(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPMessage_protocolOp(LDAPMessage_protocolOp val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      switch (val.getSelector()) {
         case 0:
            BindRequest val_bindRequest = (BindRequest)val.getValue();
            this.putBindRequest(val_bindRequest, 0);
            break;
         case 1:
            BindResponse val_bindResponse = (BindResponse)val.getValue();
            this.putBindResponse(val_bindResponse, 0);
            break;
         case 2:
            Null val_unbindRequest = (Null)val.getValue();
            this.writeNull(val_unbindRequest, 1073741826);
            break;
         case 3:
            SearchRequest val_searchRequest = (SearchRequest)val.getValue();
            this.putSearchRequest(val_searchRequest, 0);
            break;
         case 4:
            SearchResultEntry val_searchResEntry = (SearchResultEntry)val.getValue();
            this.putSearchResultEntry(val_searchResEntry, 0);
            break;
         case 5:
            SearchResultDone val_searchResDone = (SearchResultDone)val.getValue();
            this.putSearchResultDone(val_searchResDone, 0);
            break;
         case 6:
            ModifyRequest val_modifyRequest = (ModifyRequest)val.getValue();
            this.putModifyRequest(val_modifyRequest, 0);
            break;
         case 7:
            ModifyResponse val_modifyResponse = (ModifyResponse)val.getValue();
            this.putModifyResponse(val_modifyResponse, 0);
            break;
         case 8:
            AddRequest val_addRequest = (AddRequest)val.getValue();
            this.putAddRequest(val_addRequest, 0);
            break;
         case 9:
            AddResponse val_addResponse = (AddResponse)val.getValue();
            this.putAddResponse(val_addResponse, 0);
            break;
         case 10:
            OctetString val_delRequest = (OctetString)val.getValue();
            this.writeOctetString(val_delRequest, 1073741834);
            break;
         case 11:
            DelResponse val_delResponse = (DelResponse)val.getValue();
            this.putDelResponse(val_delResponse, 0);
            break;
         case 12:
            ModifyDNRequest val_modDNRequest = (ModifyDNRequest)val.getValue();
            this.putModifyDNRequest(val_modDNRequest, 0);
            break;
         case 13:
            ModifyDNResponse val_modDNResponse = (ModifyDNResponse)val.getValue();
            this.putModifyDNResponse(val_modDNResponse, 0);
            break;
         case 14:
            CompareRequest val_compareRequest = (CompareRequest)val.getValue();
            this.putCompareRequest(val_compareRequest, 0);
            break;
         case 15:
            CompareResponse val_compareResponse = (CompareResponse)val.getValue();
            this.putCompareResponse(val_compareResponse, 0);
            break;
         case 16:
            Int32 val_abandonRequest = (Int32)val.getValue();
            this.writeIntegerS32(val_abandonRequest.intValue(), 1073741840);
            break;
         case 17:
            SearchResultReference val_searchResRef = (SearchResultReference)val.getValue();
            this.putSearchResultReference(val_searchResRef, 0);
            break;
         case 18:
            ExtendedRequest val_extendedReq = (ExtendedRequest)val.getValue();
            this.putExtendedRequest(val_extendedReq, 0);
            break;
         case 19:
            ExtendedResponse val_extendedResp = (ExtendedResponse)val.getValue();
            this.putExtendedResponse(val_extendedResp, 0);
            break;
         default:
            throw new BadValueException();
      }

   }

   public void writeLDAPMessage(LDAPMessage val) throws IOException, BadValueException {
      try {
         this.putLDAPMessage(val, 0);
      } catch (ValueTooLargeException var3) {
         throw new BadValueException(var3.getMessage());
      }

      this.flushOut();
   }

   public void putLDAPMessage(LDAPMessage val, int tag) throws IOException, BadValueException, ValueTooLargeException {
      Int32 val_messageID = val.getMessageID();
      LDAPMessage_protocolOp val_protocolOp = val.getProtocolOp();
      Controls val_controls = val.getControls();
      ASN1BEREncoder enc0 = (ASN1BEREncoder)this.createExplicitTagEncoder(tag != 0 ? tag : 16, false);
      enc0.writeIntegerS32(val_messageID.intValue(), 2);
      enc0.putLDAPMessage_protocolOp(val_protocolOp, 0);
      if (val_controls != null) {
         enc0.putControls(val_controls, Integer.MIN_VALUE);
      }

      super.writeEndOfContents(enc0);
   }
}
