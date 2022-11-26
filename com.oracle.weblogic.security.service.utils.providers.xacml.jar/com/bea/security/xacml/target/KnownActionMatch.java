package com.bea.security.xacml.target;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.policy.ActionMatch;
import com.bea.common.security.xacml.policy.AttributeDesignator;
import com.bea.common.security.xacml.policy.Match;

public class KnownActionMatch extends KnownMatch {
   public KnownActionMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, values);
   }

   public KnownActionMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, values, isAllValues);
   }

   public KnownActionMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values);
   }

   public KnownActionMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values, isAllValues);
   }

   protected boolean isMatch(Match original) {
      if (original instanceof ActionMatch) {
         ActionMatch m = (ActionMatch)original;
         AttributeDesignator ad = m.getDesignator();
         if (ad != null && this.getDesignatorAttributeId().equals(ad.getAttributeId()) && this.getDesignatorDataType().equals(ad.getDataType())) {
            String issuer = ad.getIssuer();
            return issuer == null || issuer.equals(this.getDesignatorIssuer());
         }
      }

      return false;
   }
}
