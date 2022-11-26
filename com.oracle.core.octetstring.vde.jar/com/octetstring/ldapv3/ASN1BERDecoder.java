package com.octetstring.ldapv3;

import com.asn1c.codec.BERDecoder;
import com.asn1c.codec.Decoder;
import com.asn1c.codec.FactoryMap;
import com.asn1c.core.BadDataException;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int8;
import com.asn1c.core.Null;
import com.asn1c.core.OctetString;
import com.asn1c.core.UnknownExtensionException;
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
      super(decoder, "ldapv3/BER", factories, table);
      this.factory = (ASN1Factory)this.getFactory("com.octetstring.ldapv3");
      if (this.factory == null) {
         this.getFactoryMap().put(this.factory = new ASN1Factory());
      }

   }

   protected ASN1BERDecoder(Decoder decoder, ASN1BERDecoder parent, boolean indefiniteLength) {
      super(decoder, parent, indefiniteLength);
      this.factory = (ASN1Factory)this.getFactory("com.octetstring.ldapv3");
   }

   public ASN1Factory getFactory() {
      return this.factory;
   }

   public BERDecoder createExtensionClone(Decoder decoder, boolean indefiniteLength) {
      return new ASN1BERDecoder(decoder, this, indefiniteLength);
   }

   public MessageID readMessageID() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      MessageID val = this.getMessageID(0);
      this.flushIn();
      return val;
   }

   public MessageID getMessageID(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      MessageID val = this.getFactory().createMessageID(this.readIntegerS32(tag != 0 ? tag : 2));
      return val;
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

   public LDAPOID readLDAPOID() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPOID val = this.getLDAPOID(0);
      this.flushIn();
      return val;
   }

   public LDAPOID getLDAPOID(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      LDAPOID val = this.getFactory().createLDAPOID(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public AttributeValue readAttributeValue() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeValue val = this.getAttributeValue(0);
      this.flushIn();
      return val;
   }

   public AttributeValue getAttributeValue(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AttributeValue val = this.getFactory().createAttributeValue(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public AssertionValue readAssertionValue() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AssertionValue val = this.getAssertionValue(0);
      this.flushIn();
      return val;
   }

   public AssertionValue getAssertionValue(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AssertionValue val = this.getFactory().createAssertionValue(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public UnbindRequest readUnbindRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      UnbindRequest val = this.getUnbindRequest(0);
      this.flushIn();
      return val;
   }

   public UnbindRequest getUnbindRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      UnbindRequest val = this.getFactory().createUnbindRequest(this.readNull(tag != 0 ? tag : 1073741826));
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

   public RelativeLDAPDN readRelativeLDAPDN() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      RelativeLDAPDN val = this.getRelativeLDAPDN(0);
      this.flushIn();
      return val;
   }

   public RelativeLDAPDN getRelativeLDAPDN(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      RelativeLDAPDN val = this.getFactory().createRelativeLDAPDN(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public AttributeType readAttributeType() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeType val = this.getAttributeType(0);
      this.flushIn();
      return val;
   }

   public AttributeType getAttributeType(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AttributeType val = this.getFactory().createAttributeType(this.readOctetString(tag != 0 ? tag : 4));
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

   public AttributeDescriptionList readAttributeDescriptionList() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeDescriptionList val = this.getAttributeDescriptionList(0);
      this.flushIn();
      return val;
   }

   public AttributeDescriptionList getAttributeDescriptionList(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      AttributeDescriptionList val = this.getFactory().createAttributeDescriptionList(list);
      return val;
   }

   public AttributeValueAssertion readAttributeValueAssertion() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeValueAssertion val = this.getAttributeValueAssertion(0);
      this.flushIn();
      return val;
   }

   public AttributeValueAssertion getAttributeValueAssertion(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_attributeDesc = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_assertionValue = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      this.readEndOfContents(dec0);
      AttributeValueAssertion val = this.getFactory().createAttributeValueAssertion(val_attributeDesc, val_assertionValue);
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

   public LDAPURL readLDAPURL() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPURL val = this.getLDAPURL(0);
      this.flushIn();
      return val;
   }

   public LDAPURL getLDAPURL(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      LDAPURL val = this.getFactory().createLDAPURL(this.readOctetString(tag != 0 ? tag : 4));
      return val;
   }

   public Controls readControls() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Controls val = this.getControls(0);
      this.flushIn();
      return val;
   }

   public Controls getControls(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         Control subval = dec0.getControl(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      Controls val = this.getFactory().createControls(list);
      return val;
   }

   public Control readControl() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Control val = this.getControl(0);
      this.flushIn();
      return val;
   }

   public Control getControl(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_controlType = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Bool val_criticality;
      switch (dec0.peekTag()) {
         case 1:
            val_criticality = this.baseTypeFactory.createBool(dec0.readBoolean(1));
            break;
         default:
            val_criticality = null;
      }

      OctetString val_controlValue;
      switch (dec0.peekTag()) {
         case 4:
            val_controlValue = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
            break;
         default:
            val_controlValue = null;
      }

      this.readEndOfContents(dec0);
      Control val = this.getFactory().createControl(val_controlType, val_criticality, val_controlValue);
      return val;
   }

   public SaslCredentials readSaslCredentials() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SaslCredentials val = this.getSaslCredentials(0);
      this.flushIn();
      return val;
   }

   public SaslCredentials getSaslCredentials(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_mechanism = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_credentials;
      switch (dec0.peekTag()) {
         case 4:
            val_credentials = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
            break;
         default:
            val_credentials = null;
      }

      this.readEndOfContents(dec0);
      SaslCredentials val = this.getFactory().createSaslCredentials(val_mechanism, val_credentials);
      return val;
   }

   public MatchingRuleAssertion readMatchingRuleAssertion() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      MatchingRuleAssertion val = this.getMatchingRuleAssertion(0);
      this.flushIn();
      return val;
   }

   public MatchingRuleAssertion getMatchingRuleAssertion(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_matchingRule;
      switch (dec0.peekTag()) {
         case -2147483647:
            val_matchingRule = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483647));
            break;
         default:
            val_matchingRule = null;
      }

      OctetString val_type;
      switch (dec0.peekTag()) {
         case -2147483646:
            val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483646));
            break;
         default:
            val_type = null;
      }

      OctetString val_matchValue = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483645));
      Bool val_dnAttributes;
      switch (dec0.peekTag()) {
         case -2147483644:
            val_dnAttributes = this.baseTypeFactory.createBool(dec0.readBoolean(-2147483644));
            break;
         default:
            val_dnAttributes = null;
      }

      this.readEndOfContents(dec0);
      MatchingRuleAssertion val = this.getFactory().createMatchingRuleAssertion(val_matchingRule, val_type, val_matchValue, val_dnAttributes);
      return val;
   }

   public PartialAttributeList readPartialAttributeList() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      PartialAttributeList val = this.getPartialAttributeList(0);
      this.flushIn();
      return val;
   }

   public PartialAttributeList getPartialAttributeList(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         PartialAttributeList_Seq subval = dec0.getPartialAttributeList_Seq(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      PartialAttributeList val = this.getFactory().createPartialAttributeList(list);
      return val;
   }

   public SearchResultReference readSearchResultReference() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SearchResultReference val = this.getSearchResultReference(0);
      this.flushIn();
      return val;
   }

   public SearchResultReference getSearchResultReference(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741843);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      SearchResultReference val = this.getFactory().createSearchResultReference(list);
      return val;
   }

   public AttributeList readAttributeList() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeList val = this.getAttributeList(0);
      this.flushIn();
      return val;
   }

   public AttributeList getAttributeList(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         AttributeList_Seq subval = dec0.getAttributeList_Seq(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      AttributeList val = this.getFactory().createAttributeList(list);
      return val;
   }

   public DelRequest readDelRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      DelRequest val = this.getDelRequest(0);
      this.flushIn();
      return val;
   }

   public DelRequest getDelRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      DelRequest val = this.getFactory().createDelRequest(this.readOctetString(tag != 0 ? tag : 1073741834));
      return val;
   }

   public ModifyDNRequest readModifyDNRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyDNRequest val = this.getModifyDNRequest(0);
      this.flushIn();
      return val;
   }

   public ModifyDNRequest getModifyDNRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741836);
      OctetString val_entry = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_newrdn = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Bool val_deleteoldrdn = this.baseTypeFactory.createBool(dec0.readBoolean(1));
      OctetString val_newSuperior;
      switch (dec0.peekTag()) {
         case Integer.MIN_VALUE:
            val_newSuperior = this.baseTypeFactory.createOctetString(dec0.readOctetString(Integer.MIN_VALUE));
            break;
         default:
            val_newSuperior = null;
      }

      this.readEndOfContents(dec0);
      ModifyDNRequest val = this.getFactory().createModifyDNRequest(val_entry, val_newrdn, val_deleteoldrdn, val_newSuperior);
      return val;
   }

   public CompareRequest readCompareRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      CompareRequest val = this.getCompareRequest(0);
      this.flushIn();
      return val;
   }

   public CompareRequest getCompareRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741838);
      OctetString val_entry = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      AttributeValueAssertion val_ava = dec0.getAttributeValueAssertion(0);
      this.readEndOfContents(dec0);
      CompareRequest val = this.getFactory().createCompareRequest(val_entry, val_ava);
      return val;
   }

   public AbandonRequest readAbandonRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AbandonRequest val = this.getAbandonRequest(0);
      this.flushIn();
      return val;
   }

   public AbandonRequest getAbandonRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AbandonRequest val = this.getFactory().createAbandonRequest(this.readIntegerS32(tag != 0 ? tag : 1073741840));
      return val;
   }

   public ExtendedRequest readExtendedRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ExtendedRequest val = this.getExtendedRequest(0);
      this.flushIn();
      return val;
   }

   public ExtendedRequest getExtendedRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741847);
      OctetString val_requestName = this.baseTypeFactory.createOctetString(dec0.readOctetString(Integer.MIN_VALUE));
      OctetString val_requestValue;
      switch (dec0.peekTag()) {
         case -2147483647:
            val_requestValue = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483647));
            break;
         default:
            val_requestValue = null;
      }

      this.readEndOfContents(dec0);
      ExtendedRequest val = this.getFactory().createExtendedRequest(val_requestName, val_requestValue);
      return val;
   }

   public SubstringFilter_substrings readSubstringFilter_substrings() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SubstringFilter_substrings val = this.getSubstringFilter_substrings(0);
      this.flushIn();
      return val;
   }

   public SubstringFilter_substrings getSubstringFilter_substrings(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         SubstringFilter_substrings_Seq subval = dec0.getSubstringFilter_substrings_Seq(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      SubstringFilter_substrings val = this.getFactory().createSubstringFilter_substrings(list);
      return val;
   }

   public SubstringFilter_substrings_Seq readSubstringFilter_substrings_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SubstringFilter_substrings_Seq val = this.getSubstringFilter_substrings_Seq(0);
      this.flushIn();
      return val;
   }

   public SubstringFilter_substrings_Seq getSubstringFilter_substrings_Seq(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      SubstringFilter_substrings_Seq val;
      switch (this.peekTag()) {
         case Integer.MIN_VALUE:
            OctetString val_initial = this.baseTypeFactory.createOctetString(this.readOctetString(Integer.MIN_VALUE));
            val = this.getFactory().createSubstringFilter_substrings_Seq((byte)0, val_initial);
            break;
         case -2147483647:
            OctetString val_any = this.baseTypeFactory.createOctetString(this.readOctetString(-2147483647));
            val = this.getFactory().createSubstringFilter_substrings_Seq((byte)1, val_any);
            break;
         case -2147483646:
            OctetString val_final_ = this.baseTypeFactory.createOctetString(this.readOctetString(-2147483646));
            val = this.getFactory().createSubstringFilter_substrings_Seq((byte)2, val_final_);
            break;
         default:
            throw new UnknownExtensionException();
      }

      return val;
   }

   public AttributeTypeAndValues_vals readAttributeTypeAndValues_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeTypeAndValues_vals val = this.getAttributeTypeAndValues_vals(0);
      this.flushIn();
      return val;
   }

   public AttributeTypeAndValues_vals getAttributeTypeAndValues_vals(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 17);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      AttributeTypeAndValues_vals val = this.getFactory().createAttributeTypeAndValues_vals(list);
      return val;
   }

   public ModifyRequest_modification readModifyRequest_modification() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyRequest_modification val = this.getModifyRequest_modification(0);
      this.flushIn();
      return val;
   }

   public ModifyRequest_modification getModifyRequest_modification(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         ModifyRequest_modification_Seq subval = dec0.getModifyRequest_modification_Seq(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      ModifyRequest_modification val = this.getFactory().createModifyRequest_modification(list);
      return val;
   }

   public PartialAttributeList_Seq_vals readPartialAttributeList_Seq_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      PartialAttributeList_Seq_vals val = this.getPartialAttributeList_Seq_vals(0);
      this.flushIn();
      return val;
   }

   public PartialAttributeList_Seq_vals getPartialAttributeList_Seq_vals(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 17);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      PartialAttributeList_Seq_vals val = this.getFactory().createPartialAttributeList_Seq_vals(list);
      return val;
   }

   public Filter_and readFilter_and() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Filter_and val = this.getFilter_and(0);
      this.flushIn();
      return val;
   }

   public Filter_and getFilter_and(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : Integer.MIN_VALUE);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         Filter subval = dec0.getFilter(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      Filter_and val = this.getFactory().createFilter_and(list);
      return val;
   }

   public Filter_or readFilter_or() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Filter_or val = this.getFilter_or(0);
      this.flushIn();
      return val;
   }

   public Filter_or getFilter_or(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : -2147483647);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         Filter subval = dec0.getFilter(0);
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      Filter_or val = this.getFactory().createFilter_or(list);
      return val;
   }

   public Attribute_vals readAttribute_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Attribute_vals val = this.getAttribute_vals(0);
      this.flushIn();
      return val;
   }

   public Attribute_vals getAttribute_vals(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 17);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      Attribute_vals val = this.getFactory().createAttribute_vals(list);
      return val;
   }

   public AttributeList_Seq_vals readAttributeList_Seq_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeList_Seq_vals val = this.getAttributeList_Seq_vals(0);
      this.flushIn();
      return val;
   }

   public AttributeList_Seq_vals getAttributeList_Seq_vals(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 17);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      AttributeList_Seq_vals val = this.getFactory().createAttributeList_Seq_vals(list);
      return val;
   }

   public Attribute readAttribute() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Attribute val = this.getAttribute(0);
      this.flushIn();
      return val;
   }

   public Attribute getAttribute(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Attribute_vals val_vals = dec0.getAttribute_vals(0);
      this.readEndOfContents(dec0);
      Attribute val = this.getFactory().createAttribute(val_type, val_vals);
      return val;
   }

   public Referral readReferral() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Referral val = this.getReferral(0);
      this.flushIn();
      return val;
   }

   public Referral getReferral(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      ArrayList list = new ArrayList();

      while(dec0.notEndOfContents()) {
         OctetString subval = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
         list.add(subval);
      }

      this.readEndOfContents(dec0);
      Referral val = this.getFactory().createReferral(list);
      return val;
   }

   public AuthenticationChoice readAuthenticationChoice() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AuthenticationChoice val = this.getAuthenticationChoice(0);
      this.flushIn();
      return val;
   }

   public AuthenticationChoice getAuthenticationChoice(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AuthenticationChoice val;
      switch (this.peekTag()) {
         case Integer.MIN_VALUE:
            OctetString val_simple = this.baseTypeFactory.createOctetString(this.readOctetString(Integer.MIN_VALUE));
            val = this.getFactory().createAuthenticationChoice((byte)0, val_simple);
            break;
         case -2147483645:
            SaslCredentials val_sasl = this.getSaslCredentials(-2147483645);
            val = this.getFactory().createAuthenticationChoice((byte)1, val_sasl);
            break;
         default:
            throw new UnknownExtensionException();
      }

      return val;
   }

   public BindResponse readBindResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      BindResponse val = this.getBindResponse(0);
      this.flushIn();
      return val;
   }

   public BindResponse getBindResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741825);
      Int8 val_resultCode = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      OctetString val_matchedDN = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_errorMessage = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Referral val_referral;
      switch (dec0.peekTag()) {
         case -2147483645:
            val_referral = dec0.getReferral(-2147483645);
            break;
         default:
            val_referral = null;
      }

      OctetString val_serverSaslCreds;
      switch (dec0.peekTag()) {
         case -2147483641:
            val_serverSaslCreds = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483641));
            break;
         default:
            val_serverSaslCreds = null;
      }

      this.readEndOfContents(dec0);
      BindResponse val = this.getFactory().createBindResponse(val_resultCode, val_matchedDN, val_errorMessage, val_referral, val_serverSaslCreds);
      return val;
   }

   public SubstringFilter readSubstringFilter() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SubstringFilter val = this.getSubstringFilter(0);
      this.flushIn();
      return val;
   }

   public SubstringFilter getSubstringFilter(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      SubstringFilter_substrings val_substrings = dec0.getSubstringFilter_substrings(0);
      this.readEndOfContents(dec0);
      SubstringFilter val = this.getFactory().createSubstringFilter(val_type, val_substrings);
      return val;
   }

   public SearchResultEntry readSearchResultEntry() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SearchResultEntry val = this.getSearchResultEntry(0);
      this.flushIn();
      return val;
   }

   public SearchResultEntry getSearchResultEntry(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741828);
      OctetString val_objectName = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      PartialAttributeList val_attributes = dec0.getPartialAttributeList(0);
      this.readEndOfContents(dec0);
      SearchResultEntry val = this.getFactory().createSearchResultEntry(val_objectName, val_attributes);
      return val;
   }

   public ModifyRequest readModifyRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyRequest val = this.getModifyRequest(0);
      this.flushIn();
      return val;
   }

   public ModifyRequest getModifyRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741830);
      OctetString val_object = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      ModifyRequest_modification val_modification = dec0.getModifyRequest_modification(0);
      this.readEndOfContents(dec0);
      ModifyRequest val = this.getFactory().createModifyRequest(val_object, val_modification);
      return val;
   }

   public AttributeTypeAndValues readAttributeTypeAndValues() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeTypeAndValues val = this.getAttributeTypeAndValues(0);
      this.flushIn();
      return val;
   }

   public AttributeTypeAndValues getAttributeTypeAndValues(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      AttributeTypeAndValues_vals val_vals = dec0.getAttributeTypeAndValues_vals(0);
      this.readEndOfContents(dec0);
      AttributeTypeAndValues val = this.getFactory().createAttributeTypeAndValues(val_type, val_vals);
      return val;
   }

   public AddRequest readAddRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AddRequest val = this.getAddRequest(0);
      this.flushIn();
      return val;
   }

   public AddRequest getAddRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741832);
      OctetString val_entry = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      AttributeList val_attributes = dec0.getAttributeList(0);
      this.readEndOfContents(dec0);
      AddRequest val = this.getFactory().createAddRequest(val_entry, val_attributes);
      return val;
   }

   public ExtendedResponse readExtendedResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ExtendedResponse val = this.getExtendedResponse(0);
      this.flushIn();
      return val;
   }

   public ExtendedResponse getExtendedResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741848);
      Int8 val_resultCode = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      OctetString val_matchedDN = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_errorMessage = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Referral val_referral;
      switch (dec0.peekTag()) {
         case -2147483645:
            val_referral = dec0.getReferral(-2147483645);
            break;
         default:
            val_referral = null;
      }

      OctetString val_responseName;
      switch (dec0.peekTag()) {
         case -2147483638:
            val_responseName = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483638));
            break;
         default:
            val_responseName = null;
      }

      OctetString val_response;
      switch (dec0.peekTag()) {
         case -2147483637:
            val_response = this.baseTypeFactory.createOctetString(dec0.readOctetString(-2147483637));
            break;
         default:
            val_response = null;
      }

      this.readEndOfContents(dec0);
      ExtendedResponse val = this.getFactory().createExtendedResponse(val_resultCode, val_matchedDN, val_errorMessage, val_referral, val_responseName, val_response);
      return val;
   }

   public AttributeList_Seq readAttributeList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AttributeList_Seq val = this.getAttributeList_Seq(0);
      this.flushIn();
      return val;
   }

   public AttributeList_Seq getAttributeList_Seq(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      AttributeList_Seq_vals val_vals = dec0.getAttributeList_Seq_vals(0);
      this.readEndOfContents(dec0);
      AttributeList_Seq val = this.getFactory().createAttributeList_Seq(val_type, val_vals);
      return val;
   }

   public PartialAttributeList_Seq readPartialAttributeList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      PartialAttributeList_Seq val = this.getPartialAttributeList_Seq(0);
      this.flushIn();
      return val;
   }

   public PartialAttributeList_Seq getPartialAttributeList_Seq(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      OctetString val_type = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      PartialAttributeList_Seq_vals val_vals = dec0.getPartialAttributeList_Seq_vals(0);
      this.readEndOfContents(dec0);
      PartialAttributeList_Seq val = this.getFactory().createPartialAttributeList_Seq(val_type, val_vals);
      return val;
   }

   public ModifyRequest_modification_Seq readModifyRequest_modification_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyRequest_modification_Seq val = this.getModifyRequest_modification_Seq(0);
      this.flushIn();
      return val;
   }

   public ModifyRequest_modification_Seq getModifyRequest_modification_Seq(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int8 val_operation = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      AttributeTypeAndValues val_modification = dec0.getAttributeTypeAndValues(0);
      this.readEndOfContents(dec0);
      ModifyRequest_modification_Seq val = this.getFactory().createModifyRequest_modification_Seq(val_operation, val_modification);
      return val;
   }

   public LDAPResult readLDAPResult() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPResult val = this.getLDAPResult(0);
      this.flushIn();
      return val;
   }

   public LDAPResult getLDAPResult(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int8 val_resultCode = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      OctetString val_matchedDN = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      OctetString val_errorMessage = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Referral val_referral;
      switch (dec0.peekTag()) {
         case -2147483645:
            val_referral = dec0.getReferral(-2147483645);
            break;
         default:
            val_referral = null;
      }

      this.readEndOfContents(dec0);
      LDAPResult val = this.getFactory().createLDAPResult(val_resultCode, val_matchedDN, val_errorMessage, val_referral);
      return val;
   }

   public BindRequest readBindRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      BindRequest val = this.getBindRequest(0);
      this.flushIn();
      return val;
   }

   public BindRequest getBindRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741824);
      Int8 val_version = this.baseTypeFactory.createInt8(dec0.readIntegerS8(2));
      OctetString val_name = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      AuthenticationChoice val_authentication = dec0.getAuthenticationChoice(0);
      this.readEndOfContents(dec0);
      BindRequest val = this.getFactory().createBindRequest(val_version, val_name, val_authentication);
      return val;
   }

   public SearchResultDone readSearchResultDone() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SearchResultDone val = this.getSearchResultDone(0);
      this.flushIn();
      return val;
   }

   public SearchResultDone getSearchResultDone(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      SearchResultDone val = this.getFactory().createSearchResultDone(this.getLDAPResult(tag != 0 ? tag : 1073741829));
      return val;
   }

   public ModifyResponse readModifyResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyResponse val = this.getModifyResponse(0);
      this.flushIn();
      return val;
   }

   public ModifyResponse getModifyResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ModifyResponse val = this.getFactory().createModifyResponse(this.getLDAPResult(tag != 0 ? tag : 1073741831));
      return val;
   }

   public AddResponse readAddResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      AddResponse val = this.getAddResponse(0);
      this.flushIn();
      return val;
   }

   public AddResponse getAddResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      AddResponse val = this.getFactory().createAddResponse(this.getLDAPResult(tag != 0 ? tag : 1073741833));
      return val;
   }

   public DelResponse readDelResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      DelResponse val = this.getDelResponse(0);
      this.flushIn();
      return val;
   }

   public DelResponse getDelResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      DelResponse val = this.getFactory().createDelResponse(this.getLDAPResult(tag != 0 ? tag : 1073741835));
      return val;
   }

   public ModifyDNResponse readModifyDNResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      ModifyDNResponse val = this.getModifyDNResponse(0);
      this.flushIn();
      return val;
   }

   public ModifyDNResponse getModifyDNResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ModifyDNResponse val = this.getFactory().createModifyDNResponse(this.getLDAPResult(tag != 0 ? tag : 1073741837));
      return val;
   }

   public CompareResponse readCompareResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      CompareResponse val = this.getCompareResponse(0);
      this.flushIn();
      return val;
   }

   public CompareResponse getCompareResponse(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      CompareResponse val = this.getFactory().createCompareResponse(this.getLDAPResult(tag != 0 ? tag : 1073741839));
      return val;
   }

   public Filter readFilter() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      Filter val = this.getFilter(0);
      this.flushIn();
      return val;
   }

   public Filter getFilter(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      Filter val;
      switch (this.peekTag()) {
         case Integer.MIN_VALUE:
            Filter_and val_and = this.getFilter_and(0);
            val = this.getFactory().createFilter((byte)0, val_and);
            break;
         case -2147483647:
            Filter_or val_or = this.getFilter_or(0);
            val = this.getFactory().createFilter((byte)1, val_or);
            break;
         case -2147483646:
            ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(-2147483646);
            Filter val_not = dec0.getFilter(0);
            this.readEndOfContents(dec0);
            val = this.getFactory().createFilter((byte)2, val_not);
            break;
         case -2147483645:
            AttributeValueAssertion val_equalityMatch = this.getAttributeValueAssertion(-2147483645);
            val = this.getFactory().createFilter((byte)3, val_equalityMatch);
            break;
         case -2147483644:
            SubstringFilter val_substrings = this.getSubstringFilter(-2147483644);
            val = this.getFactory().createFilter((byte)4, val_substrings);
            break;
         case -2147483643:
            AttributeValueAssertion val_greaterOrEqual = this.getAttributeValueAssertion(-2147483643);
            val = this.getFactory().createFilter((byte)5, val_greaterOrEqual);
            break;
         case -2147483642:
            AttributeValueAssertion val_lessOrEqual = this.getAttributeValueAssertion(-2147483642);
            val = this.getFactory().createFilter((byte)6, val_lessOrEqual);
            break;
         case -2147483641:
            OctetString val_present = this.baseTypeFactory.createOctetString(this.readOctetString(-2147483641));
            val = this.getFactory().createFilter((byte)7, val_present);
            break;
         case -2147483640:
            AttributeValueAssertion val_approxMatch = this.getAttributeValueAssertion(-2147483640);
            val = this.getFactory().createFilter((byte)8, val_approxMatch);
            break;
         case -2147483639:
            MatchingRuleAssertion val_extensibleMatch = this.getMatchingRuleAssertion(-2147483639);
            val = this.getFactory().createFilter((byte)9, val_extensibleMatch);
            break;
         default:
            throw new UnknownExtensionException();
      }

      return val;
   }

   public SearchRequest readSearchRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      SearchRequest val = this.getSearchRequest(0);
      this.flushIn();
      return val;
   }

   public SearchRequest getSearchRequest(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 1073741827);
      OctetString val_baseObject = this.baseTypeFactory.createOctetString(dec0.readOctetString(4));
      Int8 val_scope = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      Int8 val_derefAliases = this.baseTypeFactory.createInt8(dec0.readIntegerS8(10));
      Int32 val_sizeLimit = this.baseTypeFactory.createInt32(dec0.readIntegerS32(2));
      Int32 val_timeLimit = this.baseTypeFactory.createInt32(dec0.readIntegerS32(2));
      Bool val_typesOnly = this.baseTypeFactory.createBool(dec0.readBoolean(1));
      Filter val_filter = dec0.getFilter(0);
      AttributeDescriptionList val_attributes = dec0.getAttributeDescriptionList(0);
      this.readEndOfContents(dec0);
      SearchRequest val = this.getFactory().createSearchRequest(val_baseObject, val_scope, val_derefAliases, val_sizeLimit, val_timeLimit, val_typesOnly, val_filter, val_attributes);
      return val;
   }

   public LDAPMessage_protocolOp readLDAPMessage_protocolOp() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPMessage_protocolOp val = this.getLDAPMessage_protocolOp(0);
      this.flushIn();
      return val;
   }

   public LDAPMessage_protocolOp getLDAPMessage_protocolOp(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      LDAPMessage_protocolOp val;
      switch (this.peekTag()) {
         case 1073741824:
            BindRequest val_bindRequest = this.getBindRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)0, val_bindRequest);
            break;
         case 1073741825:
            BindResponse val_bindResponse = this.getBindResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)1, val_bindResponse);
            break;
         case 1073741826:
            Null val_unbindRequest = this.baseTypeFactory.createNull(this.readNull(1073741826));
            val = this.getFactory().createLDAPMessage_protocolOp((byte)2, val_unbindRequest);
            break;
         case 1073741827:
            SearchRequest val_searchRequest = this.getSearchRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)3, val_searchRequest);
            break;
         case 1073741828:
            SearchResultEntry val_searchResEntry = this.getSearchResultEntry(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)4, val_searchResEntry);
            break;
         case 1073741829:
            SearchResultDone val_searchResDone = this.getSearchResultDone(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)5, val_searchResDone);
            break;
         case 1073741830:
            ModifyRequest val_modifyRequest = this.getModifyRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)6, val_modifyRequest);
            break;
         case 1073741831:
            ModifyResponse val_modifyResponse = this.getModifyResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)7, val_modifyResponse);
            break;
         case 1073741832:
            AddRequest val_addRequest = this.getAddRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)8, val_addRequest);
            break;
         case 1073741833:
            AddResponse val_addResponse = this.getAddResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)9, val_addResponse);
            break;
         case 1073741834:
            OctetString val_delRequest = this.baseTypeFactory.createOctetString(this.readOctetString(1073741834));
            val = this.getFactory().createLDAPMessage_protocolOp((byte)10, val_delRequest);
            break;
         case 1073741835:
            DelResponse val_delResponse = this.getDelResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)11, val_delResponse);
            break;
         case 1073741836:
            ModifyDNRequest val_modDNRequest = this.getModifyDNRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)12, val_modDNRequest);
            break;
         case 1073741837:
            ModifyDNResponse val_modDNResponse = this.getModifyDNResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)13, val_modDNResponse);
            break;
         case 1073741838:
            CompareRequest val_compareRequest = this.getCompareRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)14, val_compareRequest);
            break;
         case 1073741839:
            CompareResponse val_compareResponse = this.getCompareResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)15, val_compareResponse);
            break;
         case 1073741840:
            Int32 val_abandonRequest = this.baseTypeFactory.createInt32(this.readIntegerS32(1073741840));
            val = this.getFactory().createLDAPMessage_protocolOp((byte)16, val_abandonRequest);
            break;
         case 1073741841:
         case 1073741842:
         case 1073741844:
         case 1073741845:
         case 1073741846:
         default:
            throw new UnknownExtensionException();
         case 1073741843:
            SearchResultReference val_searchResRef = this.getSearchResultReference(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)17, val_searchResRef);
            break;
         case 1073741847:
            ExtendedRequest val_extendedReq = this.getExtendedRequest(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)18, val_extendedReq);
            break;
         case 1073741848:
            ExtendedResponse val_extendedResp = this.getExtendedResponse(0);
            val = this.getFactory().createLDAPMessage_protocolOp((byte)19, val_extendedResp);
      }

      return val;
   }

   public LDAPMessage readLDAPMessage() throws IOException, EOFException, BadDataException, ValueTooLargeException {
      this.startup();
      LDAPMessage val = this.getLDAPMessage(0);
      this.flushIn();
      return val;
   }

   public LDAPMessage getLDAPMessage(int tag) throws IOException, EOFException, BadDataException, ValueTooLargeException {
      ASN1BERDecoder dec0 = (ASN1BERDecoder)this.createExplicitTagDecoder(tag != 0 ? tag : 16);
      Int32 val_messageID = this.baseTypeFactory.createInt32(dec0.readIntegerS32(2));
      LDAPMessage_protocolOp val_protocolOp = dec0.getLDAPMessage_protocolOp(0);
      Controls val_controls;
      switch (dec0.peekTag()) {
         case Integer.MIN_VALUE:
            val_controls = dec0.getControls(Integer.MIN_VALUE);
            break;
         default:
            val_controls = null;
      }

      this.readEndOfContents(dec0);
      LDAPMessage val = this.getFactory().createLDAPMessage(val_messageID, val_protocolOp, val_controls);
      return val;
   }
}
