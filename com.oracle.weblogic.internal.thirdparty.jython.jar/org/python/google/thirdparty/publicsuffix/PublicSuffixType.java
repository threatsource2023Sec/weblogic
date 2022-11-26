package org.python.google.thirdparty.publicsuffix;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
enum PublicSuffixType {
   PRIVATE(':', ','),
   ICANN('!', '?');

   private final char innerNodeCode;
   private final char leafNodeCode;

   private PublicSuffixType(char innerNodeCode, char leafNodeCode) {
      this.innerNodeCode = innerNodeCode;
      this.leafNodeCode = leafNodeCode;
   }

   char getLeafNodeCode() {
      return this.leafNodeCode;
   }

   char getInnerNodeCode() {
      return this.innerNodeCode;
   }

   static PublicSuffixType fromCode(char code) {
      PublicSuffixType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PublicSuffixType value = var1[var3];
         if (value.getInnerNodeCode() == code || value.getLeafNodeCode() == code) {
            return value;
         }
      }

      throw new IllegalArgumentException("No enum corresponding to given code: " + code);
   }

   static PublicSuffixType fromIsPrivate(boolean isPrivate) {
      return isPrivate ? PRIVATE : ICANN;
   }
}
