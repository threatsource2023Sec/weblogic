package com.octetstring.ldapv3.controls;

import com.asn1c.core.OctetString;

public class MatchingRuleId extends LDAPString {
   public MatchingRuleId() {
   }

   public MatchingRuleId(MatchingRuleId value) {
      super((LDAPString)value);
   }

   public MatchingRuleId(OctetString value) {
      super(value);
   }
}
