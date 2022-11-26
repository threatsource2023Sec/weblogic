package com.bea.security.xacml.target;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.policy.Match;

public abstract class KnownMatch {
   public static final int UNCHANGED = 0;
   public static final int MATCH = 1;
   public static final int NO_MATCH = 2;
   private URI matchId;
   private URI designatorAttributeId;
   private URI designatorDataType;
   private String designatorIssuer;
   private Bag values;
   private boolean isAllValues;

   public KnownMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values) {
      this(matchId, designatorAttributeId, designatorDataType, values, true);
   }

   public KnownMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values, boolean isAllValues) {
      this(matchId, designatorAttributeId, designatorDataType, (String)null, values, isAllValues);
   }

   public KnownMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values) {
      this(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values, true);
   }

   public KnownMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values, boolean isAllValues) {
      this.matchId = matchId;
      this.designatorAttributeId = designatorAttributeId;
      this.designatorDataType = designatorDataType;
      this.designatorIssuer = designatorIssuer;
      this.values = values;
      this.isAllValues = isAllValues;
   }

   public URI getMatchId() {
      return this.matchId;
   }

   public URI getDesignatorAttributeId() {
      return this.designatorAttributeId;
   }

   public URI getDesignatorDataType() {
      return this.designatorDataType;
   }

   public String getDesignatorIssuer() {
      return this.designatorIssuer;
   }

   public Bag getValues() {
      return this.values;
   }

   public boolean isAllValues() {
      return this.isAllValues;
   }

   public int filterMatch(Match original) {
      if (this.matchId.equals(original.getMatchId()) && this.isMatch(original)) {
         if (this.values.contains(original.getAttributeValue().getValue())) {
            return 1;
         }

         if (this.isAllValues) {
            return 2;
         }
      }

      return 0;
   }

   protected abstract boolean isMatch(Match var1);
}
