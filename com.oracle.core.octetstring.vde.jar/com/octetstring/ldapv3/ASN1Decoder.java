package com.octetstring.ldapv3;

import com.asn1c.codec.Decoder;
import com.asn1c.core.BadDataException;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;

public interface ASN1Decoder {
   Decoder getInputDecoder();

   MessageID readMessageID() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPString readLDAPString() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPOID readLDAPOID() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeValue readAttributeValue() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AssertionValue readAssertionValue() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   UnbindRequest readUnbindRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPDN readLDAPDN() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   RelativeLDAPDN readRelativeLDAPDN() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeType readAttributeType() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeDescription readAttributeDescription() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeDescriptionList readAttributeDescriptionList() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeValueAssertion readAttributeValueAssertion() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   MatchingRuleId readMatchingRuleId() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPURL readLDAPURL() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Controls readControls() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Control readControl() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SaslCredentials readSaslCredentials() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   MatchingRuleAssertion readMatchingRuleAssertion() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   PartialAttributeList readPartialAttributeList() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SearchResultReference readSearchResultReference() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeList readAttributeList() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   DelRequest readDelRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyDNRequest readModifyDNRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   CompareRequest readCompareRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AbandonRequest readAbandonRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ExtendedRequest readExtendedRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SubstringFilter_substrings readSubstringFilter_substrings() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SubstringFilter_substrings_Seq readSubstringFilter_substrings_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeTypeAndValues_vals readAttributeTypeAndValues_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyRequest_modification readModifyRequest_modification() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   PartialAttributeList_Seq_vals readPartialAttributeList_Seq_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Filter_and readFilter_and() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Filter_or readFilter_or() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Attribute_vals readAttribute_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeList_Seq_vals readAttributeList_Seq_vals() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Attribute readAttribute() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Referral readReferral() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AuthenticationChoice readAuthenticationChoice() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   BindResponse readBindResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SubstringFilter readSubstringFilter() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SearchResultEntry readSearchResultEntry() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyRequest readModifyRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeTypeAndValues readAttributeTypeAndValues() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AddRequest readAddRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ExtendedResponse readExtendedResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeList_Seq readAttributeList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   PartialAttributeList_Seq readPartialAttributeList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyRequest_modification_Seq readModifyRequest_modification_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPResult readLDAPResult() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   BindRequest readBindRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SearchResultDone readSearchResultDone() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyResponse readModifyResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AddResponse readAddResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   DelResponse readDelResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   ModifyDNResponse readModifyDNResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   CompareResponse readCompareResponse() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   Filter readFilter() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SearchRequest readSearchRequest() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPMessage_protocolOp readLDAPMessage_protocolOp() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPMessage readLDAPMessage() throws IOException, EOFException, BadDataException, ValueTooLargeException;
}
