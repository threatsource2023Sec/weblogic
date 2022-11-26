package weblogic.iiop.protocol;

import java.util.HashMap;

public final class CodeSet {
   public static final int ISO_8859_1 = 65537;
   public static final String ISO_8859_1_ENC = "iso-8859-1";
   public static final int US_ASCII = 65568;
   public static final String US_ASCII_ENC = "us-ascii";
   public static final int UTF_8 = 83951617;
   public static final String UTF_8_ENC = "utf-8";
   public static int DEFAULT_CHAR_NATIVE_CODE_SET = 65568;
   public static final int UTF_16 = 65801;
   public static final String UTF_16_ENC = "utf-16";
   public static final int UCS_2 = 65792;
   public static final String UCS_2_ENC = "ucs-2";
   public static final String UTF_16LE_ENC = "utf-16le";
   public static final String UTF_16BE_ENC = "utf-16be";
   public static int DEFAULT_WCHAR_NATIVE_CODE_SET = 65792;
   public static final HashMap codeSetTable = new HashMap();

   public static int getDefaultCharCodeSet() {
      return DEFAULT_CHAR_NATIVE_CODE_SET;
   }

   public static int getDefaultWcharCodeSet() {
      return DEFAULT_WCHAR_NATIVE_CODE_SET;
   }

   public static boolean supportedCharCodeSet(int i) {
      return i == 83951617 || i == 65568 || i == 65537;
   }

   public static boolean supportedWcharCodeSet(int i) {
      return i == 83951617 || i == 65801 || i == 65792;
   }

   public static void setDefaults(int charcs, int wcharcs) {
      DEFAULT_CHAR_NATIVE_CODE_SET = charcs;
      DEFAULT_WCHAR_NATIVE_CODE_SET = wcharcs;
   }

   public static int getOSFCodeset(String codeset) {
      Integer cs = (Integer)codeSetTable.get(codeset);
      return cs != null ? cs : 0;
   }

   static {
      codeSetTable.put("iso-8859-1", 65537);
      codeSetTable.put("us-ascii", 65568);
      codeSetTable.put("utf-8", 83951617);
      codeSetTable.put("utf-16", 65801);
      codeSetTable.put("ucs-2", 65792);
      codeSetTable.put("iso-8859-1".toUpperCase(), 65537);
      codeSetTable.put("us-ascii".toUpperCase(), 65568);
      codeSetTable.put("utf-8".toUpperCase(), 83951617);
      codeSetTable.put("utf-16".toUpperCase(), 65801);
      codeSetTable.put("ucs-2".toUpperCase(), 65792);
   }
}
