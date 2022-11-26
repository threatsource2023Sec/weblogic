package com.octetstring.ldapv3;

import com.asn1c.codec.Encoder;
import com.asn1c.core.BadValueException;
import java.io.IOException;

public interface ASN1Encoder {
   Encoder getOutputEncoder();

   void writeMessageID(MessageID var1) throws IOException, BadValueException;

   void writeLDAPString(LDAPString var1) throws IOException, BadValueException;

   void writeLDAPOID(LDAPOID var1) throws IOException, BadValueException;

   void writeAttributeValue(AttributeValue var1) throws IOException, BadValueException;

   void writeAssertionValue(AssertionValue var1) throws IOException, BadValueException;

   void writeUnbindRequest(UnbindRequest var1) throws IOException, BadValueException;

   void writeLDAPDN(LDAPDN var1) throws IOException, BadValueException;

   void writeRelativeLDAPDN(RelativeLDAPDN var1) throws IOException, BadValueException;

   void writeAttributeType(AttributeType var1) throws IOException, BadValueException;

   void writeAttributeDescription(AttributeDescription var1) throws IOException, BadValueException;

   void writeAttributeDescriptionList(AttributeDescriptionList var1) throws IOException, BadValueException;

   void writeAttributeValueAssertion(AttributeValueAssertion var1) throws IOException, BadValueException;

   void writeMatchingRuleId(MatchingRuleId var1) throws IOException, BadValueException;

   void writeLDAPURL(LDAPURL var1) throws IOException, BadValueException;

   void writeControls(Controls var1) throws IOException, BadValueException;

   void writeControl(Control var1) throws IOException, BadValueException;

   void writeSaslCredentials(SaslCredentials var1) throws IOException, BadValueException;

   void writeMatchingRuleAssertion(MatchingRuleAssertion var1) throws IOException, BadValueException;

   void writePartialAttributeList(PartialAttributeList var1) throws IOException, BadValueException;

   void writeSearchResultReference(SearchResultReference var1) throws IOException, BadValueException;

   void writeAttributeList(AttributeList var1) throws IOException, BadValueException;

   void writeDelRequest(DelRequest var1) throws IOException, BadValueException;

   void writeModifyDNRequest(ModifyDNRequest var1) throws IOException, BadValueException;

   void writeCompareRequest(CompareRequest var1) throws IOException, BadValueException;

   void writeAbandonRequest(AbandonRequest var1) throws IOException, BadValueException;

   void writeExtendedRequest(ExtendedRequest var1) throws IOException, BadValueException;

   void writeSubstringFilter_substrings(SubstringFilter_substrings var1) throws IOException, BadValueException;

   void writeSubstringFilter_substrings_Seq(SubstringFilter_substrings_Seq var1) throws IOException, BadValueException;

   void writeAttributeTypeAndValues_vals(AttributeTypeAndValues_vals var1) throws IOException, BadValueException;

   void writeModifyRequest_modification(ModifyRequest_modification var1) throws IOException, BadValueException;

   void writePartialAttributeList_Seq_vals(PartialAttributeList_Seq_vals var1) throws IOException, BadValueException;

   void writeFilter_and(Filter_and var1) throws IOException, BadValueException;

   void writeFilter_or(Filter_or var1) throws IOException, BadValueException;

   void writeAttribute_vals(Attribute_vals var1) throws IOException, BadValueException;

   void writeAttributeList_Seq_vals(AttributeList_Seq_vals var1) throws IOException, BadValueException;

   void writeAttribute(Attribute var1) throws IOException, BadValueException;

   void writeReferral(Referral var1) throws IOException, BadValueException;

   void writeAuthenticationChoice(AuthenticationChoice var1) throws IOException, BadValueException;

   void writeBindResponse(BindResponse var1) throws IOException, BadValueException;

   void writeSubstringFilter(SubstringFilter var1) throws IOException, BadValueException;

   void writeSearchResultEntry(SearchResultEntry var1) throws IOException, BadValueException;

   void writeModifyRequest(ModifyRequest var1) throws IOException, BadValueException;

   void writeAttributeTypeAndValues(AttributeTypeAndValues var1) throws IOException, BadValueException;

   void writeAddRequest(AddRequest var1) throws IOException, BadValueException;

   void writeExtendedResponse(ExtendedResponse var1) throws IOException, BadValueException;

   void writeAttributeList_Seq(AttributeList_Seq var1) throws IOException, BadValueException;

   void writePartialAttributeList_Seq(PartialAttributeList_Seq var1) throws IOException, BadValueException;

   void writeModifyRequest_modification_Seq(ModifyRequest_modification_Seq var1) throws IOException, BadValueException;

   void writeLDAPResult(LDAPResult var1) throws IOException, BadValueException;

   void writeBindRequest(BindRequest var1) throws IOException, BadValueException;

   void writeSearchResultDone(SearchResultDone var1) throws IOException, BadValueException;

   void writeModifyResponse(ModifyResponse var1) throws IOException, BadValueException;

   void writeAddResponse(AddResponse var1) throws IOException, BadValueException;

   void writeDelResponse(DelResponse var1) throws IOException, BadValueException;

   void writeModifyDNResponse(ModifyDNResponse var1) throws IOException, BadValueException;

   void writeCompareResponse(CompareResponse var1) throws IOException, BadValueException;

   void writeFilter(Filter var1) throws IOException, BadValueException;

   void writeSearchRequest(SearchRequest var1) throws IOException, BadValueException;

   void writeLDAPMessage_protocolOp(LDAPMessage_protocolOp var1) throws IOException, BadValueException;

   void writeLDAPMessage(LDAPMessage var1) throws IOException, BadValueException;
}
