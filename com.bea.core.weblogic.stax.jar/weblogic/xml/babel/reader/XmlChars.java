package weblogic.xml.babel.reader;

public class XmlChars {
   private XmlChars() {
   }

   public static boolean isChar(int ucs4char) {
      return ucs4char >= 32 && ucs4char <= 55295 || ucs4char == 10 || ucs4char == 9 || ucs4char == 13 || ucs4char >= 57344 && ucs4char <= 65533 || ucs4char >= 65536 && ucs4char <= 1114111;
   }

   public static boolean isHighSurrogate(int c) {
      return 55296 <= c && c <= 56319;
   }

   public static boolean isLowSurrogate(int c) {
      return 56320 <= c && c <= 57343;
   }

   public static boolean isNameChar(char c) {
      if (isLetter2(c)) {
         return true;
      } else if (c == '>') {
         return false;
      } else {
         return c == '.' || c == '-' || c == '_' || c == ':' || isExtender(c);
      }
   }

   public static boolean isNCNameChar(char c) {
      return c != ':' && isNameChar(c);
   }

   public static boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\n' || c == '\r';
   }

   public static boolean isLetter(char var0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean isCompatibilityChar(char c) {
      switch (c >> 8 & 255) {
         case 0:
            return c == 170 || c == 181 || c == 186;
         case 1:
            return c >= 306 && c <= 307 || c >= 319 && c <= 320 || c == 329 || c == 383 || c >= 452 && c <= 460 || c >= 497 && c <= 499;
         case 2:
            return c >= 688 && c <= 696 || c >= 736 && c <= 740;
         case 3:
            return c == 890;
         case 5:
            return c == 1415;
         case 14:
            return c >= 3804 && c <= 3805;
         case 17:
            return c == 4353 || c == 4356 || c == 4360 || c == 4362 || c == 4365 || c >= 4371 && c <= 4411 || c == 4413 || c == 4415 || c >= 4417 && c <= 4427 || c == 4429 || c == 4431 || c >= 4433 && c <= 4435 || c >= 4438 && c <= 4440 || c == 4450 || c == 4452 || c == 4454 || c == 4456 || c >= 4458 && c <= 4460 || c >= 4463 && c <= 4465 || c == 4468 || c >= 4470 && c <= 4509 || c >= 4511 && c <= 4514 || c >= 4521 && c <= 4522 || c >= 4524 && c <= 4525 || c >= 4528 && c <= 4534 || c == 4537 || c == 4539 || c >= 4547 && c <= 4586 || c >= 4588 && c <= 4591 || c >= 4593 && c <= 4600;
         case 32:
            return c == 8319;
         case 33:
            return c == 8450 || c == 8455 || c >= 8458 && c <= 8467 || c == 8469 || c >= 8472 && c <= 8477 || c == 8484 || c == 8488 || c >= 8492 && c <= 8493 || c >= 8495 && c <= 8504 || c >= 8544 && c <= 8575;
         case 48:
            return c >= 12443 && c <= 12444;
         case 49:
            return c >= 12593 && c <= 12686;
         case 249:
         case 250:
         case 251:
         case 252:
         case 253:
         case 254:
         case 255:
            return true;
         default:
            return false;
      }
   }

   private static boolean isLetter2(char var0) {
      // $FF: Couldn't be decompiled
   }

   private static boolean isDigit(char c) {
      return Character.isDigit(c) && (c < '０' || c > '９');
   }

   private static boolean isExtender(char c) {
      return c == 183 || c == 720 || c == 721 || c == 903 || c == 1600 || c == 3654 || c == 3782 || c == 12293 || c >= 12337 && c <= 12341 || c >= 12445 && c <= 12446 || c >= 12540 && c <= 12542;
   }
}
