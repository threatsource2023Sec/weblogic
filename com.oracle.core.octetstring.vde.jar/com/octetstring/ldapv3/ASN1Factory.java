package com.octetstring.ldapv3;

import com.asn1c.codec.Factory;
import com.asn1c.core.ASN1Object;
import com.asn1c.core.Bool;
import com.asn1c.core.Int32;
import com.asn1c.core.Int8;
import com.asn1c.core.Null;
import com.asn1c.core.OctetString;
import java.util.Collection;

public class ASN1Factory implements Factory {
   public String getModuleName() {
      return "com.octetstring.ldapv3";
   }

   public MessageID createMessageID(int val) {
      return new MessageID(val);
   }

   public LDAPString createLDAPString(OctetString val) {
      return new LDAPString(val);
   }

   public LDAPOID createLDAPOID(OctetString val) {
      return new LDAPOID(val);
   }

   public AttributeValue createAttributeValue(OctetString val) {
      return new AttributeValue(val);
   }

   public AssertionValue createAssertionValue(OctetString val) {
      return new AssertionValue(val);
   }

   public UnbindRequest createUnbindRequest(Null val) {
      return new UnbindRequest(val);
   }

   public LDAPDN createLDAPDN(OctetString val) {
      return new LDAPDN(val);
   }

   public RelativeLDAPDN createRelativeLDAPDN(OctetString val) {
      return new RelativeLDAPDN(val);
   }

   public AttributeType createAttributeType(OctetString val) {
      return new AttributeType(val);
   }

   public AttributeDescription createAttributeDescription(OctetString val) {
      return new AttributeDescription(val);
   }

   public AttributeDescriptionList createAttributeDescriptionList(Collection val) {
      return new AttributeDescriptionList(val);
   }

   public AttributeValueAssertion createAttributeValueAssertion(OctetString val_attributeDesc, OctetString val_assertionValue) {
      return new AttributeValueAssertion(val_attributeDesc, val_assertionValue);
   }

   public MatchingRuleId createMatchingRuleId(OctetString val) {
      return new MatchingRuleId(val);
   }

   public LDAPURL createLDAPURL(OctetString val) {
      return new LDAPURL(val);
   }

   public Controls createControls(Collection val) {
      return new Controls(val);
   }

   public Control createControl(OctetString val_controlType, Bool val_criticality, OctetString val_controlValue) {
      return new Control(val_controlType, val_criticality, val_controlValue);
   }

   public SaslCredentials createSaslCredentials(OctetString val_mechanism, OctetString val_credentials) {
      return new SaslCredentials(val_mechanism, val_credentials);
   }

   public MatchingRuleAssertion createMatchingRuleAssertion(OctetString val_matchingRule, OctetString val_type, OctetString val_matchValue, Bool val_dnAttributes) {
      return new MatchingRuleAssertion(val_matchingRule, val_type, val_matchValue, val_dnAttributes);
   }

   public PartialAttributeList createPartialAttributeList(Collection val) {
      return new PartialAttributeList(val);
   }

   public SearchResultReference createSearchResultReference(Collection val) {
      return new SearchResultReference(val);
   }

   public AttributeList createAttributeList(Collection val) {
      return new AttributeList(val);
   }

   public DelRequest createDelRequest(OctetString val) {
      return new DelRequest(val);
   }

   public ModifyDNRequest createModifyDNRequest(OctetString val_entry, OctetString val_newrdn, Bool val_deleteoldrdn, OctetString val_newSuperior) {
      return new ModifyDNRequest(val_entry, val_newrdn, val_deleteoldrdn, val_newSuperior);
   }

   public CompareRequest createCompareRequest(OctetString val_entry, AttributeValueAssertion val_ava) {
      return new CompareRequest(val_entry, val_ava);
   }

   public AbandonRequest createAbandonRequest(int val) {
      return new AbandonRequest(val);
   }

   public ExtendedRequest createExtendedRequest(OctetString val_requestName, OctetString val_requestValue) {
      return new ExtendedRequest(val_requestName, val_requestValue);
   }

   public SubstringFilter_substrings createSubstringFilter_substrings(Collection val) {
      return new SubstringFilter_substrings(val);
   }

   public SubstringFilter_substrings_Seq createSubstringFilter_substrings_Seq(byte selector, ASN1Object val) {
      return new SubstringFilter_substrings_Seq(selector, val);
   }

   public AttributeTypeAndValues_vals createAttributeTypeAndValues_vals(Collection val) {
      return new AttributeTypeAndValues_vals(val);
   }

   public ModifyRequest_modification createModifyRequest_modification(Collection val) {
      return new ModifyRequest_modification(val);
   }

   public PartialAttributeList_Seq_vals createPartialAttributeList_Seq_vals(Collection val) {
      return new PartialAttributeList_Seq_vals(val);
   }

   public Filter_and createFilter_and(Collection val) {
      return new Filter_and(val);
   }

   public Filter_or createFilter_or(Collection val) {
      return new Filter_or(val);
   }

   public Attribute_vals createAttribute_vals(Collection val) {
      return new Attribute_vals(val);
   }

   public AttributeList_Seq_vals createAttributeList_Seq_vals(Collection val) {
      return new AttributeList_Seq_vals(val);
   }

   public Attribute createAttribute(OctetString val_type, Attribute_vals val_vals) {
      return new Attribute(val_type, val_vals);
   }

   public Referral createReferral(Collection val) {
      return new Referral(val);
   }

   public AuthenticationChoice createAuthenticationChoice(byte selector, ASN1Object val) {
      return new AuthenticationChoice(selector, val);
   }

   public BindResponse createBindResponse(Int8 val_resultCode, OctetString val_matchedDN, OctetString val_errorMessage, Referral val_referral, OctetString val_serverSaslCreds) {
      return new BindResponse(val_resultCode, val_matchedDN, val_errorMessage, val_referral, val_serverSaslCreds);
   }

   public SubstringFilter createSubstringFilter(OctetString val_type, SubstringFilter_substrings val_substrings) {
      return new SubstringFilter(val_type, val_substrings);
   }

   public SearchResultEntry createSearchResultEntry(OctetString val_objectName, PartialAttributeList val_attributes) {
      return new SearchResultEntry(val_objectName, val_attributes);
   }

   public ModifyRequest createModifyRequest(OctetString val_object, ModifyRequest_modification val_modification) {
      return new ModifyRequest(val_object, val_modification);
   }

   public AttributeTypeAndValues createAttributeTypeAndValues(OctetString val_type, AttributeTypeAndValues_vals val_vals) {
      return new AttributeTypeAndValues(val_type, val_vals);
   }

   public AddRequest createAddRequest(OctetString val_entry, AttributeList val_attributes) {
      return new AddRequest(val_entry, val_attributes);
   }

   public ExtendedResponse createExtendedResponse(Int8 val_resultCode, OctetString val_matchedDN, OctetString val_errorMessage, Referral val_referral, OctetString val_responseName, OctetString val_response) {
      return new ExtendedResponse(val_resultCode, val_matchedDN, val_errorMessage, val_referral, val_responseName, val_response);
   }

   public AttributeList_Seq createAttributeList_Seq(OctetString val_type, AttributeList_Seq_vals val_vals) {
      return new AttributeList_Seq(val_type, val_vals);
   }

   public PartialAttributeList_Seq createPartialAttributeList_Seq(OctetString val_type, PartialAttributeList_Seq_vals val_vals) {
      return new PartialAttributeList_Seq(val_type, val_vals);
   }

   public ModifyRequest_modification_Seq createModifyRequest_modification_Seq(Int8 val_operation, AttributeTypeAndValues val_modification) {
      return new ModifyRequest_modification_Seq(val_operation, val_modification);
   }

   public LDAPResult createLDAPResult(Int8 val_resultCode, OctetString val_matchedDN, OctetString val_errorMessage, Referral val_referral) {
      return new LDAPResult(val_resultCode, val_matchedDN, val_errorMessage, val_referral);
   }

   public BindRequest createBindRequest(Int8 val_version, OctetString val_name, AuthenticationChoice val_authentication) {
      return new BindRequest(val_version, val_name, val_authentication);
   }

   public SearchResultDone createSearchResultDone(LDAPResult val) {
      return new SearchResultDone(val);
   }

   public ModifyResponse createModifyResponse(LDAPResult val) {
      return new ModifyResponse(val);
   }

   public AddResponse createAddResponse(LDAPResult val) {
      return new AddResponse(val);
   }

   public DelResponse createDelResponse(LDAPResult val) {
      return new DelResponse(val);
   }

   public ModifyDNResponse createModifyDNResponse(LDAPResult val) {
      return new ModifyDNResponse(val);
   }

   public CompareResponse createCompareResponse(LDAPResult val) {
      return new CompareResponse(val);
   }

   public Filter createFilter(byte selector, ASN1Object val) {
      return new Filter(selector, val);
   }

   public SearchRequest createSearchRequest(OctetString val_baseObject, Int8 val_scope, Int8 val_derefAliases, Int32 val_sizeLimit, Int32 val_timeLimit, Bool val_typesOnly, Filter val_filter, AttributeDescriptionList val_attributes) {
      return new SearchRequest(val_baseObject, val_scope, val_derefAliases, val_sizeLimit, val_timeLimit, val_typesOnly, val_filter, val_attributes);
   }

   public LDAPMessage_protocolOp createLDAPMessage_protocolOp(byte selector, ASN1Object val) {
      return new LDAPMessage_protocolOp(selector, val);
   }

   public LDAPMessage createLDAPMessage(Int32 val_messageID, LDAPMessage_protocolOp val_protocolOp, Controls val_controls) {
      return new LDAPMessage(val_messageID, val_protocolOp, val_controls);
   }
}
