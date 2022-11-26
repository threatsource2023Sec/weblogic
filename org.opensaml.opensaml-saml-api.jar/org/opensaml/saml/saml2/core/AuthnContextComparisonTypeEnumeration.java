package org.opensaml.saml.saml2.core;

public final class AuthnContextComparisonTypeEnumeration {
   public static final AuthnContextComparisonTypeEnumeration EXACT = new AuthnContextComparisonTypeEnumeration("exact");
   public static final AuthnContextComparisonTypeEnumeration MINIMUM = new AuthnContextComparisonTypeEnumeration("minimum");
   public static final AuthnContextComparisonTypeEnumeration MAXIMUM = new AuthnContextComparisonTypeEnumeration("maximum");
   public static final AuthnContextComparisonTypeEnumeration BETTER = new AuthnContextComparisonTypeEnumeration("better");
   private String comparisonType;

   protected AuthnContextComparisonTypeEnumeration(String newComparisonType) {
      this.comparisonType = newComparisonType;
   }

   public String toString() {
      return this.comparisonType;
   }
}
