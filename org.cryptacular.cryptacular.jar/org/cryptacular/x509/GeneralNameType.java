package org.cryptacular.x509;

public enum GeneralNameType {
   OtherName,
   RFC822Name,
   DNSName,
   X400Address,
   DirectoryName,
   EdiPartyName,
   UniformResourceIdentifier,
   IPAddress,
   RegisteredID;

   public static final int MIN_TAG_NUMBER = 0;
   public static final int MAX_TAG_NUMBER = 8;

   public static GeneralNameType fromTagNumber(int tagNo) {
      if (tagNo >= 0 && tagNo <= 8) {
         GeneralNameType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            GeneralNameType type = var1[var3];
            if (type.ordinal() == tagNo) {
               return type;
            }
         }

         throw new IllegalArgumentException("Invalid tag number " + tagNo);
      } else {
         throw new IllegalArgumentException("Invalid tag number " + tagNo);
      }
   }
}
