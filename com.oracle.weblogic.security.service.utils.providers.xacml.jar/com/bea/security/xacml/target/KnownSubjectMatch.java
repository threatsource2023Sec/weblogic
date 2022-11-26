package com.bea.security.xacml.target;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.policy.Match;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.common.security.xacml.policy.SubjectMatch;

public class KnownSubjectMatch extends KnownMatch {
   private static final String ACCESS_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   private URI designatorSubjectCategory;

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, values);
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, URI designatorSubjectCategory, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, values);
      this.designatorSubjectCategory = designatorSubjectCategory;
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, values, isAllValues);
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, URI designatorSubjectCategory, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, values, isAllValues);
      this.designatorSubjectCategory = designatorSubjectCategory;
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values);
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, URI designatorSubjectCategory, String designatorIssuer, Bag values) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values);
      this.designatorSubjectCategory = designatorSubjectCategory;
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, String designatorIssuer, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values, isAllValues);
   }

   public KnownSubjectMatch(URI matchId, URI designatorAttributeId, URI designatorDataType, URI designatorSubjectCategory, String designatorIssuer, Bag values, boolean isAllValues) {
      super(matchId, designatorAttributeId, designatorDataType, designatorIssuer, values, isAllValues);
      this.designatorSubjectCategory = designatorSubjectCategory;
   }

   public URI getDesignatorSubjectCategory() {
      return this.designatorSubjectCategory;
   }

   protected boolean isMatch(Match original) {
      if (original instanceof SubjectMatch) {
         SubjectMatch m = (SubjectMatch)original;
         SubjectAttributeDesignator ad = m.getDesignator();
         if (ad != null && this.getDesignatorAttributeId().equals(ad.getAttributeId()) && this.getDesignatorDataType().equals(ad.getDataType())) {
            URI subjectCategory = ad.getSubjectCategory();
            if (subjectCategory == null || this.designatorSubjectCategory == null && subjectCategory.toString().equals("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject") || subjectCategory.equals(this.designatorSubjectCategory)) {
               String issuer = ad.getIssuer();
               return issuer == null || issuer.equals(this.getDesignatorIssuer());
            }
         }
      }

      return false;
   }
}
