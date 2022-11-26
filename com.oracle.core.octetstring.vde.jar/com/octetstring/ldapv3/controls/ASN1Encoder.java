package com.octetstring.ldapv3.controls;

import com.asn1c.codec.Encoder;
import com.asn1c.core.BadValueException;
import java.io.IOException;

public interface ASN1Encoder {
   Encoder getOutputEncoder();

   void writeLDAPString(LDAPString var1) throws IOException, BadValueException;

   void writePersistentSearch(PersistentSearch var1) throws IOException, BadValueException;

   void writeSortKeyList(SortKeyList var1) throws IOException, BadValueException;

   void writeVLVControlValue(VLVControlValue var1) throws IOException, BadValueException;

   void writeLDAPDN(LDAPDN var1) throws IOException, BadValueException;

   void writeAttributeDescription(AttributeDescription var1) throws IOException, BadValueException;

   void writeMatchingRuleId(MatchingRuleId var1) throws IOException, BadValueException;

   void writeSortKeyList_Seq(SortKeyList_Seq var1) throws IOException, BadValueException;

   void writeEntryChangeNotification(EntryChangeNotification var1) throws IOException, BadValueException;

   void writeSortResult(SortResult var1) throws IOException, BadValueException;
}
