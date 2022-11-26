package com.octetstring.ldapv3;

import com.asn1c.core.Bool;
import com.asn1c.core.Int64;

public interface ASN1Values {
   Int64 maxInt = new Int64(2147483647L);
   Bool Control_criticality_default = new Bool(false);
   Bool MatchingRuleAssertion_dnAttributes_default = new Bool(false);
}
