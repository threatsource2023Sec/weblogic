package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.URI;
import java.net.URISyntaxException;

public class CombiningAlgorithm {
   public static final CombiningAlgorithm DENY_OVERRIDES = new CombiningAlgorithm("deny-overrides");
   public static final CombiningAlgorithm PERMIT_OVERRIDES = new CombiningAlgorithm("permit-overrides");
   public static final CombiningAlgorithm FIRST_APPLICABLE = new CombiningAlgorithm("first-applicable");
   public static final CombiningAlgorithm ONLY_ONE_APPLICABLE_POLICY = new CombiningAlgorithm("only-one-applicable");
   public static final CombiningAlgorithm ORDERED_DENY_OVERRIDES = new CombiningAlgorithm("ordered-deny-overrides");
   public static final CombiningAlgorithm ORDERED_PERMIT_OVERRIDES = new CombiningAlgorithm("ordered-permit-overrides");
   private static final String POLICY_PREFIX = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:";
   private static final String RULE_PREFIX = "urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:";
   private String id;

   private CombiningAlgorithm(String algorithmId) {
      this.id = algorithmId;
   }

   URI getPolicyCombiningAlgorithm() {
      try {
         return new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:" + this.id);
      } catch (URISyntaxException var2) {
         return null;
      }
   }

   URI getRuleCombiningAlgorithm() {
      try {
         return new URI("urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:" + this.id);
      } catch (URISyntaxException var2) {
         return null;
      }
   }
}
