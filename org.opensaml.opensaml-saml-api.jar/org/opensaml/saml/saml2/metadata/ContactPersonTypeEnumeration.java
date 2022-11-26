package org.opensaml.saml.saml2.metadata;

public final class ContactPersonTypeEnumeration {
   public static final ContactPersonTypeEnumeration TECHNICAL = new ContactPersonTypeEnumeration("technical");
   public static final ContactPersonTypeEnumeration SUPPORT = new ContactPersonTypeEnumeration("support");
   public static final ContactPersonTypeEnumeration ADMINISTRATIVE = new ContactPersonTypeEnumeration("administrative");
   public static final ContactPersonTypeEnumeration BILLING = new ContactPersonTypeEnumeration("billing");
   public static final ContactPersonTypeEnumeration OTHER = new ContactPersonTypeEnumeration("other");
   private String type;

   protected ContactPersonTypeEnumeration(String providedType) {
      this.type = providedType;
   }

   public String toString() {
      return this.type;
   }
}
