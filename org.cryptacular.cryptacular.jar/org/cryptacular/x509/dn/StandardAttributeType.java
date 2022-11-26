package org.cryptacular.x509.dn;

public enum StandardAttributeType implements AttributeType {
   CommonName("2.5.4.3", "CN"),
   CountryName("2.5.4.6", "C"),
   Description("2.5.4.13", "DESCRIPTION"),
   DnQualifier("2.5.4.46", "DNQUALIFIER"),
   DomainComponent("0.9.2342.19200300.100.1.25", "DC"),
   EmailAddress("1.2.840.113549.1.9.1", "EMAILADDRESS"),
   GenerationQualifier("2.5.4.44", "GENERATIONQUALIFIER"),
   GivenName("2.5.4.42", "GIVENNAME"),
   Initials("2.5.4.43", "INITIALS"),
   LocalityName("2.5.4.7", "L"),
   Mail("0.9.2342.19200300.100.1.3", "MAIL"),
   Name("2.5.4.41", "NAME"),
   OrganizationName("2.5.4.10", "O"),
   OrganizationalUnitName("2.5.4.11", "OU"),
   PostalAddress("2.5.4.16", "POSTALADDRESS"),
   PostalCode("2.5.4.17", "POSTALCODE"),
   PostOfficeBox("2.5.4.18", "POSTOFFICEBOX"),
   SerialNumber("2.5.4.5", "SERIALNUMBER"),
   StateOrProvinceName("2.5.4.8", "ST"),
   StreetAddress("2.5.4.9", "STREET"),
   Surname("2.5.4.4", "SN"),
   TelephoneNumber("2.5.4.20", "TELEPHONENUMBER"),
   Title("2.5.4.12", "TITLE"),
   UniqueIdentifier("0.9.2342.19200300.100.1.44", "UNIQUEIDENTIFIER"),
   UserId("0.9.2342.19200300.100.1.1", "UID");

   private final String oid;
   private final String name;

   private StandardAttributeType(String attributeTypeOid, String shortName) {
      this.oid = attributeTypeOid;
      this.name = shortName;
   }

   public String getOid() {
      return this.oid;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public static StandardAttributeType fromOid(String oid) {
      StandardAttributeType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         StandardAttributeType t = var1[var3];
         if (t.getOid().equals(oid)) {
            return t;
         }
      }

      return null;
   }

   public static AttributeType fromName(String name) {
      StandardAttributeType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         AttributeType t = var1[var3];
         if (t.getName().equals(name)) {
            return t;
         }
      }

      return null;
   }
}
