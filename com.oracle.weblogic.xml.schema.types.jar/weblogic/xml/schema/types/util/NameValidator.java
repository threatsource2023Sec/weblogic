package weblogic.xml.schema.types.util;

public final class NameValidator {
   public static final byte[] fgAsciiInitialNameChar = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
   public static final byte[] fgAsciiNameChar = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
   public static final byte[] fgAsciiInitialNCNameChar = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
   public static final byte[] fgAsciiNCNameChar = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
   public static final byte E_CharDataFlag = 1;
   public static final byte E_InitialNameCharFlag = 2;
   public static final byte E_NameCharFlag = 4;
   public static byte[] fgCharFlags = null;
   private static boolean charFlagsInited = false;
   private static final char[] fgCharDataRanges = new char[]{' ', '%', '\'', ';', '=', '\\', '^', '\ud7ff', '\ue000', '�', '\u0000', '\t', '\u0000'};
   private static final char[] fgInitialNameCharRanges = new char[]{'A', 'Z', 'a', 'z', 'À', 'Ö', 'Ø', 'ö', 'ø', 'ı', 'Ĵ', 'ľ', 'Ł', 'ň', 'Ŋ', 'ž', 'ƀ', 'ǃ', 'Ǎ', 'ǰ', 'Ǵ', 'ǵ', 'Ǻ', 'ȗ', 'ɐ', 'ʨ', 'ʻ', 'ˁ', 'Έ', 'Ί', 'Ύ', 'Ρ', 'Σ', 'ώ', 'ϐ', 'ϖ', 'Ϣ', 'ϳ', 'Ё', 'Ќ', 'Ў', 'я', 'ё', 'ќ', 'ў', 'ҁ', 'Ґ', 'ӄ', 'Ӈ', 'ӈ', 'Ӌ', 'ӌ', 'Ӑ', 'ӫ', 'Ӯ', 'ӵ', 'Ӹ', 'ӹ', 'Ա', 'Ֆ', 'ա', 'ֆ', 'א', 'ת', 'װ', 'ײ', 'ء', 'غ', 'ف', 'ي', 'ٱ', 'ڷ', 'ں', 'ھ', 'ۀ', 'ێ', 'ې', 'ۓ', 'ۥ', 'ۦ', 'अ', 'ह', 'क़', 'ॡ', 'অ', 'ঌ', 'এ', 'ঐ', 'ও', 'ন', 'প', 'র', 'শ', 'হ', 'ড়', 'ঢ়', 'য়', 'ৡ', 'ৰ', 'ৱ', 'ਅ', 'ਊ', 'ਏ', 'ਐ', 'ਓ', 'ਨ', 'ਪ', 'ਰ', 'ਲ', 'ਲ਼', 'ਵ', 'ਸ਼', 'ਸ', 'ਹ', 'ਖ਼', 'ੜ', 'ੲ', 'ੴ', 'અ', 'ઋ', 'એ', 'ઑ', 'ઓ', 'ન', 'પ', 'ર', 'લ', 'ળ', 'વ', 'હ', 'ଅ', 'ଌ', 'ଏ', 'ଐ', 'ଓ', 'ନ', 'ପ', 'ର', 'ଲ', 'ଳ', 'ଶ', 'ହ', 'ଡ଼', 'ଢ଼', 'ୟ', 'ୡ', 'அ', 'ஊ', 'எ', 'ஐ', 'ஒ', 'க', 'ங', 'ச', 'ஞ', 'ட', 'ண', 'த', 'ந', 'ப', 'ம', 'வ', 'ஷ', 'ஹ', 'అ', 'ఌ', 'ఎ', 'ఐ', 'ఒ', 'న', 'ప', 'ళ', 'వ', 'హ', 'ౠ', 'ౡ', 'ಅ', 'ಌ', 'ಎ', 'ಐ', 'ಒ', 'ನ', 'ಪ', 'ಳ', 'ವ', 'ಹ', 'ೠ', 'ೡ', 'അ', 'ഌ', 'എ', 'ഐ', 'ഒ', 'ന', 'പ', 'ഹ', 'ൠ', 'ൡ', 'ก', 'ฮ', 'า', 'ำ', 'เ', 'ๅ', 'ກ', 'ຂ', 'ງ', 'ຈ', 'ດ', 'ທ', 'ນ', 'ຟ', 'ມ', 'ຣ', 'ສ', 'ຫ', 'ອ', 'ຮ', 'າ', 'ຳ', 'ເ', 'ໄ', 'ཀ', 'ཇ', 'ཉ', 'ཀྵ', 'Ⴀ', 'Ⴥ', 'ა', 'ჶ', 'ᄂ', 'ᄃ', 'ᄅ', 'ᄇ', 'ᄋ', 'ᄌ', 'ᄎ', 'ᄒ', 'ᅔ', 'ᅕ', 'ᅟ', 'ᅡ', 'ᅭ', 'ᅮ', 'ᅲ', 'ᅳ', 'ᆮ', 'ᆯ', 'ᆷ', 'ᆸ', 'ᆼ', 'ᇂ', 'Ḁ', 'ẛ', 'Ạ', 'ỹ', 'ἀ', 'ἕ', 'Ἐ', 'Ἕ', 'ἠ', 'ὅ', 'Ὀ', 'Ὅ', 'ὐ', 'ὗ', 'Ὗ', 'ώ', 'ᾀ', 'ᾴ', 'ᾶ', 'ᾼ', 'ῂ', 'ῄ', 'ῆ', 'ῌ', 'ῐ', 'ΐ', 'ῖ', 'Ί', 'ῠ', 'Ῥ', 'ῲ', 'ῴ', 'ῶ', 'ῼ', 'K', 'Å', 'ↀ', 'ↂ', 'ぁ', 'ゔ', 'ァ', 'ヺ', 'ㄅ', 'ㄬ', '가', '힣', '〡', '〩', '一', '龥', '\u0000', ':', '_', 'Ά', 'Ό', 'Ϛ', 'Ϝ', 'Ϟ', 'Ϡ', 'ՙ', 'ە', 'ऽ', 'ল', 'ਫ਼', 'ઍ', 'ઽ', 'ૠ', 'ଽ', 'ஜ', 'ೞ', 'ะ', 'ຄ', 'ຊ', 'ຍ', 'ລ', 'ວ', 'ະ', 'ຽ', 'ᄀ', 'ᄉ', 'ᄼ', 'ᄾ', 'ᅀ', 'ᅌ', 'ᅎ', 'ᅐ', 'ᅙ', 'ᅣ', 'ᅥ', 'ᅧ', 'ᅩ', 'ᅵ', 'ᆞ', 'ᆨ', 'ᆫ', 'ᆺ', 'ᇫ', 'ᇰ', 'ᇹ', 'Ὑ', 'Ὓ', 'Ὕ', 'ι', 'Ω', '℮', '〇', '\u0000'};
   private static final char[] fgNameCharRanges = new char[]{'-', '.', '̀', 'ͅ', '͠', '͡', '҃', '҆', '֑', '֡', '֣', 'ֹ', 'ֻ', 'ֽ', 'ׁ', 'ׂ', 'ً', 'ْ', 'ۖ', 'ۜ', '\u06dd', '۟', '۠', 'ۤ', 'ۧ', 'ۨ', '۪', 'ۭ', 'ँ', 'ः', 'ा', 'ौ', '॑', '॔', 'ॢ', 'ॣ', 'ঁ', 'ঃ', 'ী', 'ৄ', 'ে', 'ৈ', 'ো', '্', 'ৢ', 'ৣ', 'ੀ', 'ੂ', 'ੇ', 'ੈ', 'ੋ', '੍', 'ੰ', 'ੱ', 'ઁ', 'ઃ', 'ા', 'ૅ', 'ે', 'ૉ', 'ો', '્', 'ଁ', 'ଃ', 'ା', 'ୃ', 'େ', 'ୈ', 'ୋ', '୍', 'ୖ', 'ୗ', 'ஂ', 'ஃ', 'ா', 'ூ', 'ெ', 'ை', 'ொ', '்', 'ఁ', 'ః', 'ా', 'ౄ', 'ె', 'ై', 'ొ', '్', 'ౕ', 'ౖ', 'ಂ', 'ಃ', 'ಾ', 'ೄ', 'ೆ', 'ೈ', 'ೊ', '್', 'ೕ', 'ೖ', 'ം', 'ഃ', 'ാ', 'ൃ', 'െ', 'ൈ', 'ൊ', '്', 'ิ', 'ฺ', '็', '๎', 'ິ', 'ູ', 'ົ', 'ຼ', '່', 'ໍ', '༘', '༙', 'ཱ', '྄', '྆', 'ྋ', 'ྐ', 'ྕ', 'ྙ', 'ྭ', 'ྱ', 'ྷ', '⃐', '⃜', '〪', '〯', '0', '9', '٠', '٩', '۰', '۹', '०', '९', '০', '৯', '੦', '੯', '૦', '૯', '୦', '୯', '௧', '௯', '౦', '౯', '೦', '೯', '൦', '൯', '๐', '๙', '໐', '໙', '༠', '༩', '〱', '〵', 'ゝ', 'ゞ', 'ー', 'ヾ', '\u0000', 'ֿ', 'ׄ', 'ٰ', '़', '्', '়', 'া', 'ি', 'ৗ', 'ਂ', '਼', 'ਾ', 'ਿ', '઼', '଼', 'ௗ', 'ൗ', 'ั', 'ັ', '༵', '༷', '༹', '༾', '༿', 'ྗ', 'ྐྵ', '⃡', '゙', '゚', '·', 'ː', 'ˑ', '·', 'ـ', 'ๆ', 'ໆ', '々', '\u0000'};

   public static boolean validName(String name) {
      if (!charFlagsInited) {
         initCharFlags();
      }

      int len = name.length();
      if (len == 0) {
         return false;
      } else {
         char ch = name.charAt(0);
         if (ch > 'z') {
            if ((fgCharFlags[ch] & 2) == 0) {
               return false;
            }
         } else if (fgAsciiInitialNameChar[ch] == 0) {
            return false;
         }

         for(int i = 1; i < len; ++i) {
            ch = name.charAt(i);
            if (ch > 'z') {
               if ((fgCharFlags[ch] & 4) == 0) {
                  return false;
               }
            } else if (fgAsciiNameChar[ch] == 0) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean validNCNameChar(char ch) {
      if (!charFlagsInited) {
         initCharFlags();
      }

      if (ch > 'z') {
         if ((fgCharFlags[ch] & 4) == 0) {
            return false;
         }
      } else if (fgAsciiNCNameChar[ch] == 0) {
         return false;
      }

      return true;
   }

   public static boolean validNCName(String name) {
      if (!charFlagsInited) {
         initCharFlags();
      }

      int len = name.length();
      if (len == 0) {
         return false;
      } else {
         char ch = name.charAt(0);
         if (ch > 'z') {
            if ((fgCharFlags[ch] & 2) == 0) {
               return false;
            }
         } else if (fgAsciiInitialNCNameChar[ch] == 0) {
            return false;
         }

         for(int i = 1; i < len; ++i) {
            ch = name.charAt(i);
            if (ch > 'z') {
               if ((fgCharFlags[ch] & 4) == 0) {
                  return false;
               }
            } else if (fgAsciiNCNameChar[ch] == 0) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean validNmtoken(String nmtoken) {
      if (!charFlagsInited) {
         initCharFlags();
      }

      int len = nmtoken.length();
      if (len == 0) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            char ch = nmtoken.charAt(i);
            if (ch > 'z') {
               if ((fgCharFlags[ch] & 4) == 0) {
                  return false;
               }
            } else if (fgAsciiNameChar[ch] == 0) {
               return false;
            }
         }

         return true;
      }
   }

   public static synchronized void initCharFlags() {
      if (fgCharFlags == null) {
         fgCharFlags = new byte[65536];
         setFlagForRange(fgCharDataRanges, (byte)1);
         setFlagForRange(fgInitialNameCharRanges, (byte)6);
         setFlagForRange(fgNameCharRanges, (byte)4);
         charFlagsInited = true;
      }

   }

   private static void setFlagForRange(char[] ranges, byte flag) {
      byte[] var10000;
      int var10001;
      int i;
      int ch;
      for(i = 0; (ch = ranges[i]) != 0; i += 2) {
         for(int endch = ranges[i + 1]; ch <= endch; var10000[var10001] |= flag) {
            var10000 = fgCharFlags;
            var10001 = ch++;
         }
      }

      ++i;

      char ch;
      while((ch = ranges[i]) != 0) {
         var10000 = fgCharFlags;
         var10000[ch] |= flag;
         ++i;
      }

   }
}
