package com.octetstring.ldapv3.controls;

import com.asn1c.core.Bool;
import com.asn1c.core.Int64;

public interface ASN1Values {
   Int64 maxInt = new Int64(2147483647L);
   Bool SortKeyList_Seq_reverseOrder_default = new Bool(false);
}
