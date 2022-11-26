package com.octetstring.ldapv3.controls;

import com.asn1c.codec.Decoder;
import com.asn1c.core.BadDataException;
import com.asn1c.core.ValueTooLargeException;
import java.io.EOFException;
import java.io.IOException;

public interface ASN1Decoder {
   Decoder getInputDecoder();

   LDAPString readLDAPString() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   PersistentSearch readPersistentSearch() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SortKeyList readSortKeyList() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   VLVControlValue readVLVControlValue() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   LDAPDN readLDAPDN() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   AttributeDescription readAttributeDescription() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   MatchingRuleId readMatchingRuleId() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SortKeyList_Seq readSortKeyList_Seq() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   EntryChangeNotification readEntryChangeNotification() throws IOException, EOFException, BadDataException, ValueTooLargeException;

   SortResult readSortResult() throws IOException, EOFException, BadDataException, ValueTooLargeException;
}
