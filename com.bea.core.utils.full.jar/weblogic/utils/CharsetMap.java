package weblogic.utils;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import weblogic.utils.collections.ArrayMap;

public final class CharsetMap {
   private static final Map IANA_TO_JAVA_MAP;
   private static final Map JAVA_TO_IANA_MAP;
   private static final Map LOCALE_TO_IANA_MAP;

   private CharsetMap() {
   }

   public static String getJavaFromIANA(String ianaCharset) {
      return (String)IANA_TO_JAVA_MAP.get(ianaCharset.toUpperCase(Locale.US));
   }

   public static String getIANAFromJava(String javaEncoding) {
      return (String)JAVA_TO_IANA_MAP.get(javaEncoding.toUpperCase(Locale.US));
   }

   private static String getJavaFromLocale(String locale) {
      String ianaCharset = (String)LOCALE_TO_IANA_MAP.get(locale);
      return ianaCharset != null ? getJavaFromIANA(ianaCharset) : null;
   }

   public static String getJavaFromLocale(Locale loc) {
      String charset = getJavaFromLocale(loc.toString());
      return charset != null ? charset : getJavaFromLocale(loc.getLanguage());
   }

   static {
      Map IANA2JavaMap = new ArrayMap();
      IANA2JavaMap.put("US-ASCII", "US-ASCII");
      IANA2JavaMap.put("ISO-IR-6", "US-ASCII");
      IANA2JavaMap.put("ANSI_X3.4-1986", "US-ASCII");
      IANA2JavaMap.put("ANSI_X3.4-1968", "US-ASCII");
      IANA2JavaMap.put("ISO_646.IRV:1991", "US-ASCII");
      IANA2JavaMap.put("ASCII", "US-ASCII");
      IANA2JavaMap.put("ISO646-US", "US-ASCII");
      IANA2JavaMap.put("US", "US-ASCII");
      IANA2JavaMap.put("IBM367", "US-ASCII");
      IANA2JavaMap.put("CP367", "US-ASCII");
      IANA2JavaMap.put("CSASCII", "US-ASCII");
      IANA2JavaMap.put("IBM-367", "US-ASCII");
      IANA2JavaMap.put("ISO-8859-1", "ISO-8859-1");
      IANA2JavaMap.put("ISO-IR-100", "ISO-8859-1");
      IANA2JavaMap.put("ISO_8859-1", "ISO-8859-1");
      IANA2JavaMap.put("LATIN1", "ISO-8859-1");
      IANA2JavaMap.put("L1", "ISO-8859-1");
      IANA2JavaMap.put("IBM819", "ISO-8859-1");
      IANA2JavaMap.put("CP819", "ISO-8859-1");
      IANA2JavaMap.put("CSISOLATIN1", "ISO-8859-1");
      IANA2JavaMap.put("IBM-819", "ISO-8859-1");
      IANA2JavaMap.put("ISO-8859-2", "ISO-8859-2");
      IANA2JavaMap.put("ISO-IR-101", "ISO-8859-2");
      IANA2JavaMap.put("ISO_8859-2", "ISO-8859-2");
      IANA2JavaMap.put("LATIN2", "ISO-8859-2");
      IANA2JavaMap.put("L2", "ISO-8859-2");
      IANA2JavaMap.put("CSISOLATIN2", "ISO-8859-2");
      IANA2JavaMap.put("ISO-8859-3", "ISO-8859-3");
      IANA2JavaMap.put("ISO-IR-109", "ISO-8859-3");
      IANA2JavaMap.put("ISO_8859-3", "ISO-8859-3");
      IANA2JavaMap.put("LATIN3", "ISO-8859-3");
      IANA2JavaMap.put("L3", "ISO-8859-3");
      IANA2JavaMap.put("CSISOLATIN3", "ISO-8859-3");
      IANA2JavaMap.put("ISO-8859-4", "ISO-8859-4");
      IANA2JavaMap.put("ISO-IR-110", "ISO-8859-4");
      IANA2JavaMap.put("ISO_8859-4", "ISO-8859-4");
      IANA2JavaMap.put("LATIN4", "ISO-8859-4");
      IANA2JavaMap.put("L4", "ISO-8859-4");
      IANA2JavaMap.put("CSISOLATIN4", "ISO-8859-4");
      IANA2JavaMap.put("ISO-8859-5", "ISO-8859-5");
      IANA2JavaMap.put("ISO-IR-144", "ISO-8859-5");
      IANA2JavaMap.put("ISO_8859-5", "ISO-8859-5");
      IANA2JavaMap.put("CYRILLIC", "ISO-8859-5");
      IANA2JavaMap.put("CSISOLATINCYRILLIC", "ISO-8859-5");
      IANA2JavaMap.put("ISO-8859-6", "ISO-8859-6");
      IANA2JavaMap.put("ISO-IR-127", "ISO-8859-6");
      IANA2JavaMap.put("ISO_8859-6", "ISO-8859-6");
      IANA2JavaMap.put("ECMA-114", "ISO-8859-6");
      IANA2JavaMap.put("ASMO-708", "ISO-8859-6");
      IANA2JavaMap.put("ARABIC", "ISO-8859-6");
      IANA2JavaMap.put("CSISOLATINARABIC", "ISO-8859-6");
      IANA2JavaMap.put("ISO-8859-7", "ISO-8859-7");
      IANA2JavaMap.put("ISO-IR-126", "ISO-8859-7");
      IANA2JavaMap.put("ISO_8859-7", "ISO-8859-7");
      IANA2JavaMap.put("ELOT_928", "ISO-8859-7");
      IANA2JavaMap.put("ECMA-118", "ISO-8859-7");
      IANA2JavaMap.put("GREEK", "ISO-8859-7");
      IANA2JavaMap.put("GREEK8", "ISO-8859-7");
      IANA2JavaMap.put("CSISOLATINGREEK", "ISO-8859-7");
      IANA2JavaMap.put("ISO-8859-8", "ISO-8859-8");
      IANA2JavaMap.put("ISO-IR-138", "ISO-8859-8");
      IANA2JavaMap.put("ISO_8859-8", "ISO-8859-8");
      IANA2JavaMap.put("HEBREW", "ISO-8859-8");
      IANA2JavaMap.put("CSISOLATINHEBREW", "ISO-8859-8");
      IANA2JavaMap.put("ISO-8859-8-I", "ISO-8859-8");
      IANA2JavaMap.put("ISO_8859-8-I", "ISO-8859-8");
      IANA2JavaMap.put("CSISO88598I", "ISO-8859-8");
      IANA2JavaMap.put("ISO-8859-9", "ISO-8859-9");
      IANA2JavaMap.put("ISO-IR-148", "ISO-8859-9");
      IANA2JavaMap.put("ISO_8859-9", "ISO-8859-9");
      IANA2JavaMap.put("LATIN5", "ISO-8859-9");
      IANA2JavaMap.put("L5", "ISO-8859-9");
      IANA2JavaMap.put("CSISOLATIN5", "ISO-8859-9");
      IANA2JavaMap.put("ISO-8859-13", "ISO-8859-13");
      IANA2JavaMap.put("ISO-8859-15", "ISO-8859-15");
      IANA2JavaMap.put("ISO_8859-15", "ISO-8859-15");
      IANA2JavaMap.put("LATIN-9", "ISO-8859-15");
      IANA2JavaMap.put("GB2312", "GB2312");
      IANA2JavaMap.put("CSGB2312", "GB2312");
      IANA2JavaMap.put("GB18030", "GB18030");
      IANA2JavaMap.put("ISO-2022-CN", "ISO2022CN");
      IANA2JavaMap.put("GBK", "GBK");
      IANA2JavaMap.put("BIG5", "Big5");
      IANA2JavaMap.put("CSBIG5", "Big5");
      IANA2JavaMap.put("BIG5-HKSCS", "Big5-HKSCS");
      IANA2JavaMap.put("EUC-KR", "EUC-KR");
      IANA2JavaMap.put("CSEUCKR", "EUC-KR");
      IANA2JavaMap.put("ISO-2022-KR", "ISO-2022-KR");
      IANA2JavaMap.put("CSISO2022KR", "ISO-2022-KR");
      IANA2JavaMap.put("SHIFT_JIS", "Shift_JIS");
      IANA2JavaMap.put("SHIFT-JIS", "Shift_JIS");
      IANA2JavaMap.put("CSSHIFTJIS", "Shift_JIS");
      IANA2JavaMap.put("MS_KANJI", "Shift_JIS");
      IANA2JavaMap.put("X-SJIS", "Shift_JIS");
      IANA2JavaMap.put("SJIS", "Shift_JIS");
      IANA2JavaMap.put("WINDOWS-31J", "Windows-31J");
      IANA2JavaMap.put("CSWINDOWS31J", "Windows-31J");
      IANA2JavaMap.put("EUC-JP", "EUC-JP");
      IANA2JavaMap.put("CSEUCPKDFMTJAPANESE", "EUC-JP");
      IANA2JavaMap.put("EXTENDED_UNIX_CODE_PACKED_FORMAT_FOR_JAPANESE", "EUC-JP");
      IANA2JavaMap.put("ISO-2022-JP", "ISO-2022-JP");
      IANA2JavaMap.put("CSISO2022JP", "ISO-2022-JP");
      IANA2JavaMap.put("X0201", "JIS0201");
      IANA2JavaMap.put("JIS_X0201", "JIS0201");
      IANA2JavaMap.put("CSHALFWIDTHKATAKANA", "JIS0201");
      IANA2JavaMap.put("X0208", "JIS0208");
      IANA2JavaMap.put("JIS_C6226-1983", "JIS0208");
      IANA2JavaMap.put("ISO-IR-87", "JIS0208");
      IANA2JavaMap.put("JIS_X0208-1983", "JIS0208");
      IANA2JavaMap.put("CSISO87JISX0208", "JIS0208");
      IANA2JavaMap.put("X0212", "JIS0212");
      IANA2JavaMap.put("JIS_X0212-1990", "JIS0212");
      IANA2JavaMap.put("ISO-IR-159", "JIS0212");
      IANA2JavaMap.put("CSISO159JISX02121990", "JIS0212");
      IANA2JavaMap.put("KOI8-R", "KOI8-R");
      IANA2JavaMap.put("CSKOI8R", "KOI8-R");
      IANA2JavaMap.put("TIS-620", "TIS-620");
      IANA2JavaMap.put("CNS11643", "EUC-TW");
      IANA2JavaMap.put("EUC-TW", "EUC-TW");
      IANA2JavaMap.put("EUCTW", "EUC-TW");
      IANA2JavaMap.put("MS950", "MS950");
      IANA2JavaMap.put("CP950", "CP950");
      IANA2JavaMap.put("UTF-8", "UTF-8");
      IANA2JavaMap.put("UTF8", "UTF-8");
      IANA2JavaMap.put("UTF-16", "UTF-16");
      IANA2JavaMap.put("UTF-16BE", "UTF-16BE");
      IANA2JavaMap.put("UTF-16LE", "UTF-16LE");
      IANA2JavaMap.put("WINDOWS-1250", "Cp1250");
      IANA2JavaMap.put("WINDOWS-1251", "Cp1251");
      IANA2JavaMap.put("WINDOWS-1252", "Cp1252");
      IANA2JavaMap.put("WINDOWS-1253", "Cp1253");
      IANA2JavaMap.put("WINDOWS-1254", "Cp1254");
      IANA2JavaMap.put("WINDOWS-1255", "Cp1255");
      IANA2JavaMap.put("WINDOWS-1256", "Cp1256");
      IANA2JavaMap.put("WINDOWS-1257", "Cp1257");
      IANA2JavaMap.put("WINDOWS-1258", "Cp1258");
      IANA2JavaMap.put("IBM037", "Cp037");
      IANA2JavaMap.put("CP037", "Cp037");
      IANA2JavaMap.put("CSIBM037", "Cp037");
      IANA2JavaMap.put("EBCDIC-CP-US", "Cp037");
      IANA2JavaMap.put("EBCDIC-CP-CA", "Cp037");
      IANA2JavaMap.put("EBCDIC-CP-NL", "Cp037");
      IANA2JavaMap.put("EBCDIC-CP-WT", "Cp037");
      IANA2JavaMap.put("IBM273", "Cp273");
      IANA2JavaMap.put("CP273", "Cp273");
      IANA2JavaMap.put("CSIBM273", "Cp273");
      IANA2JavaMap.put("IBM277", "Cp277");
      IANA2JavaMap.put("CP277", "Cp277");
      IANA2JavaMap.put("CSIBM277", "Cp277");
      IANA2JavaMap.put("EBCDIC-CP-DK", "Cp277");
      IANA2JavaMap.put("EBCDIC-CP-NO", "Cp277");
      IANA2JavaMap.put("IBM278", "Cp278");
      IANA2JavaMap.put("CP278", "Cp278");
      IANA2JavaMap.put("CSIBM278", "Cp278");
      IANA2JavaMap.put("EBCDIC-CP-FI", "Cp278");
      IANA2JavaMap.put("EBCDIC-CP-SE", "Cp278");
      IANA2JavaMap.put("IBM280", "Cp280");
      IANA2JavaMap.put("CP280", "Cp280");
      IANA2JavaMap.put("CSIBM280", "Cp280");
      IANA2JavaMap.put("EBCDIC-CP-IT", "Cp280");
      IANA2JavaMap.put("IBM284", "Cp284");
      IANA2JavaMap.put("CP284", "Cp284");
      IANA2JavaMap.put("CSIBM284", "Cp284");
      IANA2JavaMap.put("EBCDIC-CP-ES", "Cp284");
      IANA2JavaMap.put("EBCDIC-CP-GB", "Cp285");
      IANA2JavaMap.put("IBM285", "Cp285");
      IANA2JavaMap.put("CP285", "Cp285");
      IANA2JavaMap.put("CSIBM285", "Cp285");
      IANA2JavaMap.put("EBCDIC-JP-KANA", "Cp290");
      IANA2JavaMap.put("IBM290", "Cp290");
      IANA2JavaMap.put("CP290", "Cp290");
      IANA2JavaMap.put("CSIBM290", "Cp290");
      IANA2JavaMap.put("EBCDIC-CP-FR", "Cp297");
      IANA2JavaMap.put("IBM297", "Cp297");
      IANA2JavaMap.put("CP297", "Cp297");
      IANA2JavaMap.put("CSIBM297", "Cp297");
      IANA2JavaMap.put("EBCDIC-CP-AR1", "Cp420");
      IANA2JavaMap.put("IBM420", "Cp420");
      IANA2JavaMap.put("CP420", "Cp420");
      IANA2JavaMap.put("CSIBM420", "Cp420");
      IANA2JavaMap.put("EBCDIC-CP-HE", "Cp424");
      IANA2JavaMap.put("IBM424", "Cp424");
      IANA2JavaMap.put("CP424", "Cp424");
      IANA2JavaMap.put("CSIBM424", "Cp424");
      IANA2JavaMap.put("IBM437", "Cp437");
      IANA2JavaMap.put("437", "Cp437");
      IANA2JavaMap.put("CP437", "Cp437");
      IANA2JavaMap.put("CSPC8CODEPAGE437", "Cp437");
      IANA2JavaMap.put("EBCDIC-CP-CH", "Cp500");
      IANA2JavaMap.put("IBM500", "Cp500");
      IANA2JavaMap.put("CP500", "Cp500");
      IANA2JavaMap.put("CSIBM500", "Cp500");
      IANA2JavaMap.put("EBCDIC-CP-CH", "Cp500");
      IANA2JavaMap.put("EBCDIC-CP-BE", "Cp500");
      IANA2JavaMap.put("IBM775", "Cp775");
      IANA2JavaMap.put("CP775", "Cp775");
      IANA2JavaMap.put("CSPC775BALTIC", "Cp775");
      IANA2JavaMap.put("IBM-THAI", "Cp838");
      IANA2JavaMap.put("CSIBMTHAI", "Cp838");
      IANA2JavaMap.put("IBM850", "Cp850");
      IANA2JavaMap.put("850", "Cp850");
      IANA2JavaMap.put("CP850", "Cp850");
      IANA2JavaMap.put("CSPC850MULTILINGUAL", "Cp850");
      IANA2JavaMap.put("IBM852", "Cp852");
      IANA2JavaMap.put("852", "Cp852");
      IANA2JavaMap.put("CP852", "Cp852");
      IANA2JavaMap.put("CSPCP852", "Cp852");
      IANA2JavaMap.put("IBM855", "Cp855");
      IANA2JavaMap.put("855", "Cp855");
      IANA2JavaMap.put("CP855", "Cp855");
      IANA2JavaMap.put("CSIBM855", "Cp855");
      IANA2JavaMap.put("IBM857", "Cp857");
      IANA2JavaMap.put("857", "Cp857");
      IANA2JavaMap.put("CP857", "Cp857");
      IANA2JavaMap.put("CSIBM857", "Cp857");
      IANA2JavaMap.put("IBM00858", "Cp858");
      IANA2JavaMap.put("CP00858", "Cp858");
      IANA2JavaMap.put("CCSID00858", "Cp858");
      IANA2JavaMap.put("IBM860", "Cp860");
      IANA2JavaMap.put("860", "Cp860");
      IANA2JavaMap.put("CP860", "Cp860");
      IANA2JavaMap.put("CSIBM860", "Cp860");
      IANA2JavaMap.put("IBM861", "Cp861");
      IANA2JavaMap.put("861", "Cp861");
      IANA2JavaMap.put("CP861", "Cp861");
      IANA2JavaMap.put("CP-IS", "Cp861");
      IANA2JavaMap.put("CSIBM861", "Cp861");
      IANA2JavaMap.put("IBM862", "Cp862");
      IANA2JavaMap.put("862", "Cp862");
      IANA2JavaMap.put("CP862", "Cp862");
      IANA2JavaMap.put("CSPC862LATINHEBREW", "Cp862");
      IANA2JavaMap.put("IBM863", "Cp863");
      IANA2JavaMap.put("863", "Cp863");
      IANA2JavaMap.put("CP863", "Cp863");
      IANA2JavaMap.put("CSIBM863", "Cp863");
      IANA2JavaMap.put("IBM864", "Cp864");
      IANA2JavaMap.put("CP864", "Cp864");
      IANA2JavaMap.put("CSIBM864", "Cp864");
      IANA2JavaMap.put("IBM865", "Cp865");
      IANA2JavaMap.put("865", "Cp865");
      IANA2JavaMap.put("CP865", "Cp865");
      IANA2JavaMap.put("CSIBM865", "Cp865");
      IANA2JavaMap.put("IBM866", "Cp866");
      IANA2JavaMap.put("866", "Cp866");
      IANA2JavaMap.put("CP866", "Cp866");
      IANA2JavaMap.put("CSIBM866", "Cp866");
      IANA2JavaMap.put("IBM868", "Cp868");
      IANA2JavaMap.put("CP868", "Cp868");
      IANA2JavaMap.put("CSIBM868", "Cp868");
      IANA2JavaMap.put("CP-AR", "Cp868");
      IANA2JavaMap.put("IBM869", "Cp869");
      IANA2JavaMap.put("CP869", "Cp869");
      IANA2JavaMap.put("CSIBM869", "Cp869");
      IANA2JavaMap.put("CP-GR", "Cp869");
      IANA2JavaMap.put("IBM870", "Cp870");
      IANA2JavaMap.put("CP870", "Cp870");
      IANA2JavaMap.put("CSIBM870", "Cp870");
      IANA2JavaMap.put("EBCDIC-CP-ROECE", "Cp870");
      IANA2JavaMap.put("EBCDIC-CP-YU", "Cp870");
      IANA2JavaMap.put("IBM871", "Cp871");
      IANA2JavaMap.put("CP871", "Cp871");
      IANA2JavaMap.put("CSIBM871", "Cp871");
      IANA2JavaMap.put("EBCDIC-CP-IS", "Cp871");
      IANA2JavaMap.put("IBM918", "Cp918");
      IANA2JavaMap.put("CP918", "Cp918");
      IANA2JavaMap.put("CSIBM918", "Cp918");
      IANA2JavaMap.put("EBCDIC-CP-AR2", "Cp918");
      IANA2JavaMap.put("IBM00924", "Cp924");
      IANA2JavaMap.put("CP00924", "Cp924");
      IANA2JavaMap.put("CCSID00924", "Cp924");
      IANA2JavaMap.put("EBCDIC-LATIN9--EURO", "Cp924");
      IANA2JavaMap.put("IBM1026", "Cp1026");
      IANA2JavaMap.put("CP1026", "Cp1026");
      IANA2JavaMap.put("CSIBM1026", "Cp1026");
      IANA2JavaMap.put("IBM01140", "Cp1140");
      IANA2JavaMap.put("CP01140", "Cp1140");
      IANA2JavaMap.put("CCSID01140", "Cp1140");
      IANA2JavaMap.put("IBM01141", "Cp1141");
      IANA2JavaMap.put("CP01141", "Cp1141");
      IANA2JavaMap.put("CCSID01141", "Cp1141");
      IANA2JavaMap.put("IBM01142", "Cp1142");
      IANA2JavaMap.put("CP01142", "Cp1142");
      IANA2JavaMap.put("CCSID01142", "Cp1142");
      IANA2JavaMap.put("IBM01143", "Cp1143");
      IANA2JavaMap.put("CP01143", "Cp1143");
      IANA2JavaMap.put("CCSID01143", "Cp1143");
      IANA2JavaMap.put("IBM01144", "Cp1144");
      IANA2JavaMap.put("CP01144", "Cp1144");
      IANA2JavaMap.put("CCSID01144", "Cp1144");
      IANA2JavaMap.put("IBM01145", "Cp1145");
      IANA2JavaMap.put("CP01145", "Cp1145");
      IANA2JavaMap.put("CCSID01145", "Cp1145");
      IANA2JavaMap.put("IBM01146", "Cp1146");
      IANA2JavaMap.put("CP01146", "Cp1146");
      IANA2JavaMap.put("CCSID01146", "Cp1146");
      IANA2JavaMap.put("IBM01147", "Cp1147");
      IANA2JavaMap.put("CP01147", "Cp1147");
      IANA2JavaMap.put("CCSID01147", "Cp1147");
      IANA2JavaMap.put("IBM01148", "Cp1148");
      IANA2JavaMap.put("CP01148", "Cp1148");
      IANA2JavaMap.put("CCSID01148", "Cp1148");
      IANA2JavaMap.put("IBM01149", "Cp1149");
      IANA2JavaMap.put("CP01149", "Cp1149");
      IANA2JavaMap.put("CCSID01149", "Cp1149");
      IANA2JavaMap.put("IBM-1047", "Cp1047");
      IANA2JavaMap.put("IBM1047", "Cp1047");
      IANA2JavaMap.put("CP1047", "Cp1047");
      IANA2JavaMap.put("IBM-37", "Cp037");
      IANA2JavaMap.put("IBM-273", "Cp273");
      IANA2JavaMap.put("IBM-277", "Cp277");
      IANA2JavaMap.put("IBM-278", "Cp278");
      IANA2JavaMap.put("IBM-280", "Cp280");
      IANA2JavaMap.put("IBM-284", "Cp284");
      IANA2JavaMap.put("IBM-285", "Cp285");
      IANA2JavaMap.put("IBM-297", "Cp297");
      IANA2JavaMap.put("IBM-420", "Cp420");
      IANA2JavaMap.put("IBM-424", "Cp424");
      IANA2JavaMap.put("IBM-437", "Cp437");
      IANA2JavaMap.put("IBM-500", "Cp500");
      IANA2JavaMap.put("IBM-775", "Cp775");
      IANA2JavaMap.put("IBM-850", "Cp850");
      IANA2JavaMap.put("IBM-852", "Cp852");
      IANA2JavaMap.put("IBM-855", "Cp855");
      IANA2JavaMap.put("IBM-857", "Cp857");
      IANA2JavaMap.put("IBM-858", "Cp858");
      IANA2JavaMap.put("IBM-860", "Cp860");
      IANA2JavaMap.put("IBM-861", "Cp861");
      IANA2JavaMap.put("IBM-862", "Cp862");
      IANA2JavaMap.put("IBM-863", "Cp863");
      IANA2JavaMap.put("IBM-864", "Cp864");
      IANA2JavaMap.put("IBM-865", "Cp865");
      IANA2JavaMap.put("IBM-866", "Cp866");
      IANA2JavaMap.put("IBM-868", "Cp868");
      IANA2JavaMap.put("IBM-869", "Cp869");
      IANA2JavaMap.put("IBM-870", "Cp870");
      IANA2JavaMap.put("IBM-871", "Cp871");
      IANA2JavaMap.put("IBM-918", "Cp918");
      IANA2JavaMap.put("IBM-924", "Cp924");
      IANA2JavaMap.put("IBM-1026", "Cp1026");
      IANA2JavaMap.put("IBM-1140", "Cp1140");
      IANA2JavaMap.put("IBM-1141", "Cp1141");
      IANA2JavaMap.put("IBM-1142", "Cp1142");
      IANA2JavaMap.put("IBM-1143", "Cp1143");
      IANA2JavaMap.put("IBM-1144", "Cp1144");
      IANA2JavaMap.put("IBM-1145", "Cp1145");
      IANA2JavaMap.put("IBM-1146", "Cp1146");
      IANA2JavaMap.put("IBM-1147", "Cp1147");
      IANA2JavaMap.put("IBM-1148", "Cp1148");
      IANA2JavaMap.put("IBM-1149", "Cp1149");
      IANA_TO_JAVA_MAP = Collections.unmodifiableMap(IANA2JavaMap);
      Map Java2IANAMap = new ArrayMap();
      Java2IANAMap.put("ASCII", "US-ASCII");
      Java2IANAMap.put("US-ASCII", "US-ASCII");
      Java2IANAMap.put("646", "US-ASCII");
      Java2IANAMap.put("ISO_646.IRV:1983", "US-ASCII");
      Java2IANAMap.put("ANSI_X3.4-1968", "US-ASCII");
      Java2IANAMap.put("ISO646-US", "US-ASCII");
      Java2IANAMap.put("DEFAULT", "US-ASCII");
      Java2IANAMap.put("ASCII7", "US-ASCII");
      Java2IANAMap.put("ISO8859_1", "ISO-8859-1");
      Java2IANAMap.put("8859_1", "ISO-8859-1");
      Java2IANAMap.put("ISO_8859-1:1987", "ISO-8859-1");
      Java2IANAMap.put("ISO-IR-100", "ISO-8859-1");
      Java2IANAMap.put("ISO_8859-1", "ISO-8859-1");
      Java2IANAMap.put("ISO-8859-1", "ISO-8859-1");
      Java2IANAMap.put("ISO8859-1", "ISO-8859-1");
      Java2IANAMap.put("LATIN1", "ISO-8859-1");
      Java2IANAMap.put("L1", "ISO-8859-1");
      Java2IANAMap.put("IBM819", "ISO-8859-1");
      Java2IANAMap.put("IBM-819", "ISO-8859-1");
      Java2IANAMap.put("CP819", "ISO-8859-1");
      Java2IANAMap.put("819", "ISO-8859-1");
      Java2IANAMap.put("CSISOLATIN1", "ISO-8859-1");
      Java2IANAMap.put("ISO8859_2", "ISO-8859-2");
      Java2IANAMap.put("8859_2", "ISO-8859-2");
      Java2IANAMap.put("ISO_8859-2:1987", "ISO-8859-2");
      Java2IANAMap.put("ISO-IR-101", "ISO-8859-2");
      Java2IANAMap.put("ISO_8859-2", "ISO-8859-2");
      Java2IANAMap.put("ISO-8859-2", "ISO-8859-2");
      Java2IANAMap.put("ISO8859-2", "ISO-8859-2");
      Java2IANAMap.put("LATIN2", "ISO-8859-2");
      Java2IANAMap.put("L2", "ISO-8859-2");
      Java2IANAMap.put("IBM912", "ISO-8859-2");
      Java2IANAMap.put("IBM-912", "ISO-8859-2");
      Java2IANAMap.put("CP912", "ISO-8859-2");
      Java2IANAMap.put("912", "ISO-8859-2");
      Java2IANAMap.put("CSISOLATIN2", "ISO-8859-2");
      Java2IANAMap.put("ISO8859_3", "ISO-8859-3");
      Java2IANAMap.put("8859_3", "ISO-8859-3");
      Java2IANAMap.put("ISO_8859-3:1988", "ISO-8859-3");
      Java2IANAMap.put("ISO-IR-109", "ISO-8859-3");
      Java2IANAMap.put("ISO_8859-3", "ISO-8859-3");
      Java2IANAMap.put("ISO-8859-3", "ISO-8859-3");
      Java2IANAMap.put("ISO8859-3", "ISO-8859-3");
      Java2IANAMap.put("LATIN3", "ISO-8859-3");
      Java2IANAMap.put("L3", "ISO-8859-3");
      Java2IANAMap.put("IBM913", "ISO-8859-3");
      Java2IANAMap.put("IBM-913", "ISO-8859-3");
      Java2IANAMap.put("CP913", "ISO-8859-3");
      Java2IANAMap.put("913", "ISO-8859-3");
      Java2IANAMap.put("CSISOLATIN3", "ISO-8859-3");
      Java2IANAMap.put("ISO8859_4", "ISO-8859-4");
      Java2IANAMap.put("8859_4", "ISO-8859-4");
      Java2IANAMap.put("ISO_8859-4:1988", "ISO-8859-4");
      Java2IANAMap.put("ISO-IR-110", "ISO-8859-4");
      Java2IANAMap.put("ISO_8859-4", "ISO-8859-4");
      Java2IANAMap.put("ISO-8859-4", "ISO-8859-4");
      Java2IANAMap.put("ISO8859-4", "ISO-8859-4");
      Java2IANAMap.put("LATIN4", "ISO-8859-4");
      Java2IANAMap.put("L4", "ISO-8859-4");
      Java2IANAMap.put("IBM914", "ISO-8859-4");
      Java2IANAMap.put("IBM-914", "ISO-8859-4");
      Java2IANAMap.put("CP914", "ISO-8859-4");
      Java2IANAMap.put("914", "ISO-8859-4");
      Java2IANAMap.put("CSISOLATIN4", "ISO-8859-4");
      Java2IANAMap.put("ISO8859_5", "ISO-8859-5");
      Java2IANAMap.put("8859_5", "ISO-8859-5");
      Java2IANAMap.put("ISO_8859-5:1988", "ISO-8859-5");
      Java2IANAMap.put("ISO-IR-144", "ISO-8859-5");
      Java2IANAMap.put("ISO_8859-5", "ISO-8859-5");
      Java2IANAMap.put("ISO-8859-5", "ISO-8859-5");
      Java2IANAMap.put("ISO8859-5", "ISO-8859-5");
      Java2IANAMap.put("CYRILLIC", "ISO-8859-5");
      Java2IANAMap.put("CSISOLATINCYRILLIC", "ISO-8859-5");
      Java2IANAMap.put("IBM915", "ISO-8859-5");
      Java2IANAMap.put("IBM-915", "ISO-8859-5");
      Java2IANAMap.put("CP915", "ISO-8859-5");
      Java2IANAMap.put("915", "ISO-8859-5");
      Java2IANAMap.put("ISO8859_6", "ISO-8859-6");
      Java2IANAMap.put("8859_6", "ISO-8859-6");
      Java2IANAMap.put("ISO_8859-6:1987", "ISO-8859-6");
      Java2IANAMap.put("ISO-IR-127", "ISO-8859-6");
      Java2IANAMap.put("ISO_8859-6", "ISO-8859-6");
      Java2IANAMap.put("ISO-8859-6", "ISO-8859-6");
      Java2IANAMap.put("ISO8859-6", "ISO-8859-6");
      Java2IANAMap.put("ECMA-114", "ISO-8859-6");
      Java2IANAMap.put("ASMO-708", "ISO-8859-6");
      Java2IANAMap.put("ARABIC", "ISO-8859-6");
      Java2IANAMap.put("CSISOLATINARABIC", "ISO-8859-6");
      Java2IANAMap.put("IBM1089", "ISO-8859-6");
      Java2IANAMap.put("IBM-1089", "ISO-8859-6");
      Java2IANAMap.put("CP1089", "ISO-8859-6");
      Java2IANAMap.put("1089", "ISO-8859-6");
      Java2IANAMap.put("ISO8859_7", "ISO-8859-7");
      Java2IANAMap.put("8859_7", "ISO-8859-7");
      Java2IANAMap.put("ISO_8859-7:1987", "ISO-8859-7");
      Java2IANAMap.put("ISO-IR-126", "ISO-8859-7");
      Java2IANAMap.put("ISO_8859-7", "ISO-8859-7");
      Java2IANAMap.put("ISO-8859-7", "ISO-8859-7");
      Java2IANAMap.put("ISO8859-7", "ISO-8859-7");
      Java2IANAMap.put("ELOT_928", "ISO-8859-7");
      Java2IANAMap.put("ECMA-118", "ISO-8859-7");
      Java2IANAMap.put("GREEK", "ISO-8859-7");
      Java2IANAMap.put("GREEK8", "ISO-8859-7");
      Java2IANAMap.put("CSISOLATINGREEK", "ISO-8859-7");
      Java2IANAMap.put("IBM813", "ISO-8859-7");
      Java2IANAMap.put("IBM-813", "ISO-8859-7");
      Java2IANAMap.put("CP813", "ISO-8859-7");
      Java2IANAMap.put("813", "ISO-8859-7");
      Java2IANAMap.put("ISO8859_8", "ISO-8859-8");
      Java2IANAMap.put("8859_8", "ISO-8859-8");
      Java2IANAMap.put("ISO_8859-8:1988", "ISO-8859-8");
      Java2IANAMap.put("ISO-IR-138", "ISO-8859-8");
      Java2IANAMap.put("ISO_8859-8", "ISO-8859-8");
      Java2IANAMap.put("ISO-8859-8", "ISO-8859-8");
      Java2IANAMap.put("ISO8859-8", "ISO-8859-8");
      Java2IANAMap.put("HEBREW", "ISO-8859-8");
      Java2IANAMap.put("CSISOLATINHEBREW", "ISO-8859-8");
      Java2IANAMap.put("IBM916", "ISO-8859-8");
      Java2IANAMap.put("IBM-916", "ISO-8859-8");
      Java2IANAMap.put("CP916", "ISO-8859-8");
      Java2IANAMap.put("916", "ISO-8859-8");
      Java2IANAMap.put("ISO8859_9", "ISO-8859-9");
      Java2IANAMap.put("8859_9", "ISO-8859-9");
      Java2IANAMap.put("ISO-IR-148", "ISO-8859-9");
      Java2IANAMap.put("ISO_8859-9", "ISO-8859-9");
      Java2IANAMap.put("ISO-8859-9", "ISO-8859-9");
      Java2IANAMap.put("ISO8859-9", "ISO-8859-9");
      Java2IANAMap.put("LATIN5", "ISO-8859-9");
      Java2IANAMap.put("L5", "ISO-8859-9");
      Java2IANAMap.put("IBM920", "ISO-8859-9");
      Java2IANAMap.put("IBM-920", "ISO-8859-9");
      Java2IANAMap.put("CP920", "ISO-8859-9");
      Java2IANAMap.put("920", "ISO-8859-9");
      Java2IANAMap.put("CSISOLATIN5", "ISO-8859-9");
      Java2IANAMap.put("ISO8859_13", "ISO-8859-13");
      Java2IANAMap.put("8859_13", "ISO-8859-13");
      Java2IANAMap.put("ISO_8859-13", "ISO-8859-13");
      Java2IANAMap.put("ISO-8859-13", "ISO-8859-13");
      Java2IANAMap.put("ISO8859-13", "ISO-8859-13");
      Java2IANAMap.put("ISO8859_15", "ISO-8859-15");
      Java2IANAMap.put("8859_15", "ISO-8859-15");
      Java2IANAMap.put("ISO-8859-15", "ISO-8859-15");
      Java2IANAMap.put("ISO_8859-15", "ISO-8859-15");
      Java2IANAMap.put("ISO8859-15", "ISO-8859-15");
      Java2IANAMap.put("IBM923", "ISO-8859-15");
      Java2IANAMap.put("IBM-923", "ISO-8859-15");
      Java2IANAMap.put("CP923", "ISO-8859-15");
      Java2IANAMap.put("923", "ISO-8859-15");
      Java2IANAMap.put("LATIN0", "ISO-8859-15");
      Java2IANAMap.put("LATIN9", "ISO-8859-15");
      Java2IANAMap.put("CSISOLATIN0", "ISO-8859-15");
      Java2IANAMap.put("CSISOLATIN9", "ISO-8859-15");
      Java2IANAMap.put("ISO8859_15_FDIS", "ISO-8859-15");
      Java2IANAMap.put("EUC_CN", "GB2312");
      Java2IANAMap.put("GB2312", "GB2312");
      Java2IANAMap.put("GB2312-80", "GB2312");
      Java2IANAMap.put("GB2312-1980", "GB2312");
      Java2IANAMap.put("EUC-CN", "GB2312");
      Java2IANAMap.put("EUCCN", "GB2312");
      Java2IANAMap.put("ISO2022CN", "ISO-2022-CN");
      Java2IANAMap.put("GB18030", "GB18030");
      Java2IANAMap.put("GBK", "GBK");
      Java2IANAMap.put("CP936", "GBK");
      Java2IANAMap.put("WINDOWS-936", "GBK");
      Java2IANAMap.put("BIG5", "Big5");
      Java2IANAMap.put("BIG5_HKSCS", "Big5-HKSCS");
      Java2IANAMap.put("BIG5-HKSCS", "Big5-HKSCS");
      Java2IANAMap.put("BIG5HK", "Big5-HKSCS");
      Java2IANAMap.put("BIG5-HKSCS:UNICODE3.0", "Big5-HKSCS");
      Java2IANAMap.put("KSC5601", "EUC-KR");
      Java2IANAMap.put("EUC_KR", "EUC-KR");
      Java2IANAMap.put("EUC-KR", "EUC-KR");
      Java2IANAMap.put("EUCKR", "EUC-KR");
      Java2IANAMap.put("KS_C_5601-1987", "EUC-KR");
      Java2IANAMap.put("KSC5601-1987", "EUC-KR");
      Java2IANAMap.put("KSC5601_1987", "EUC-KR");
      Java2IANAMap.put("KSC_5601", "EUC-KR");
      Java2IANAMap.put("5601", "EUC-KR");
      Java2IANAMap.put("ISO2022KR", "ISO-2022-KR");
      Java2IANAMap.put("ISO-2022-KR", "ISO-2022-KR");
      Java2IANAMap.put("CSISO2022KR", "ISO-2022-KR");
      Java2IANAMap.put("SJIS", "Shift_JIS");
      Java2IANAMap.put("SHIFT_JIS", "Shift_JIS");
      Java2IANAMap.put("SHIFT-JIS", "Shift_JIS");
      Java2IANAMap.put("CSSHIFTJIS", "Shift_JIS");
      Java2IANAMap.put("X-SJIS", "Shift_JIS");
      Java2IANAMap.put("MS_KANJI", "Shift_JIS");
      Java2IANAMap.put("PCK", "Shift_JIS");
      Java2IANAMap.put("MS932", "Windows-31J");
      Java2IANAMap.put("WINDOWS-31J", "Windows-31J");
      Java2IANAMap.put("CSWINDOWS31J", "Windows-31J");
      Java2IANAMap.put("EUC_JP", "EUC-JP");
      Java2IANAMap.put("EUC-JP", "EUC-JP");
      Java2IANAMap.put("EUCJIS", "EUC-JP");
      Java2IANAMap.put("EUCJP", "EUC-JP");
      Java2IANAMap.put("CSEUCPKDFMTJAPANESE", "EUC-JP");
      Java2IANAMap.put("EXTENDED_UNIX_CODE_PACKED_FORMAT_FOR_JAPANESE", "EUC-JP");
      Java2IANAMap.put("X-EUC-JP", "EUC-JP");
      Java2IANAMap.put("X-EUCJP", "EUC-JP");
      Java2IANAMap.put("ISO2022JP", "ISO-2022-JP");
      Java2IANAMap.put("JIS", "ISO-2022-JP");
      Java2IANAMap.put("ISO-2022-JP", "ISO-2022-JP");
      Java2IANAMap.put("CSISO2022JP", "ISO-2022-JP");
      Java2IANAMap.put("JIS_ENCODING", "ISO-2022-JP");
      Java2IANAMap.put("CSJISENCODING", "ISO-2022-JP");
      Java2IANAMap.put("JIS0201", "X0201");
      Java2IANAMap.put("JIS0208", "X0208");
      Java2IANAMap.put("JIS0212", "ISO-IR-159");
      Java2IANAMap.put("KOI8_R", "KOI8-R");
      Java2IANAMap.put("KOI8-R", "KOI8-R");
      Java2IANAMap.put("KOI8", "KOI8-R");
      Java2IANAMap.put("CSKOI8R", "KOI8-R");
      Java2IANAMap.put("TIS620", "TIS-620");
      Java2IANAMap.put("TIS620.2533", "TIS-620");
      Java2IANAMap.put("TIS-620", "TIS-620");
      Java2IANAMap.put("EUC_TW", "CNS11643");
      Java2IANAMap.put("CNS11643", "CNS11643");
      Java2IANAMap.put("EUC-TW", "CNS11643");
      Java2IANAMap.put("EUCTW", "CNS11643");
      Java2IANAMap.put("MS950", "MS950");
      Java2IANAMap.put("WINDOWS-950", "MS950");
      Java2IANAMap.put("X-WINDOWS-950", "MS950");
      Java2IANAMap.put("CP950", "CP950");
      Java2IANAMap.put("IBM950", "CP950");
      Java2IANAMap.put("IBM-950", "CP950");
      Java2IANAMap.put("950", "CP950");
      Java2IANAMap.put("UTF8", "UTF-8");
      Java2IANAMap.put("UTF-8", "UTF-8");
      Java2IANAMap.put("UNICODE-1-1-UTF-8", "UTF-8");
      Java2IANAMap.put("UTF16", "UTF-16");
      Java2IANAMap.put("UTF-16", "UTF-16");
      Java2IANAMap.put("UNICODE", "UTF-16");
      Java2IANAMap.put("UTF-16BE", "UTF-16BE");
      Java2IANAMap.put("UNICODEBIG", "UTF-16BE");
      Java2IANAMap.put("UTF-16LE", "UTF-16LE");
      Java2IANAMap.put("UNICODELITTLE", "UTF-16LE");
      Java2IANAMap.put("CP1250", "windows-1250");
      Java2IANAMap.put("CP1251", "windows-1251");
      Java2IANAMap.put("CP1252", "windows-1252");
      Java2IANAMap.put("CP1253", "windows-1253");
      Java2IANAMap.put("CP1254", "windows-1254");
      Java2IANAMap.put("CP1255", "windows-1255");
      Java2IANAMap.put("CP1256", "windows-1256");
      Java2IANAMap.put("CP1257", "windows-1257");
      Java2IANAMap.put("CP1258", "windows-1258");
      Java2IANAMap.put("CP037", "EBCDIC-CP-US");
      Java2IANAMap.put("IBM037", "EBCDIC-CP-US");
      Java2IANAMap.put("IBM-037", "EBCDIC-CP-US");
      Java2IANAMap.put("037", "EBCDIC-CP-US");
      Java2IANAMap.put("CP273", "IBM273");
      Java2IANAMap.put("IBM273", "IBM273");
      Java2IANAMap.put("IBM-273", "IBM273");
      Java2IANAMap.put("273", "IBM273");
      Java2IANAMap.put("CP277", "EBCDIC-CP-DK");
      Java2IANAMap.put("IBM277", "EBCDIC-CP-DK");
      Java2IANAMap.put("IBM-277", "EBCDIC-CP-DK");
      Java2IANAMap.put("277", "EBCDIC-CP-DK");
      Java2IANAMap.put("CP278", "EBCDIC-CP-FI");
      Java2IANAMap.put("IBM278", "EBCDIC-CP-FI");
      Java2IANAMap.put("IBM-278", "EBCDIC-CP-FI");
      Java2IANAMap.put("278", "EBCDIC-CP-FI");
      Java2IANAMap.put("CP280", "EBCDIC-CP-IT");
      Java2IANAMap.put("IBM280", "EBCDIC-CP-IT");
      Java2IANAMap.put("IBM-280", "EBCDIC-CP-IT");
      Java2IANAMap.put("280", "EBCDIC-CP-IT");
      Java2IANAMap.put("CP284", "EBCDIC-CP-ES");
      Java2IANAMap.put("IBM284", "EBCDIC-CP-ES");
      Java2IANAMap.put("IBM-284", "EBCDIC-CP-ES");
      Java2IANAMap.put("CP284", "EBCDIC-CP-ES");
      Java2IANAMap.put("284", "EBCDIC-CP-ES");
      Java2IANAMap.put("CP285", "EBCDIC-CP-GB");
      Java2IANAMap.put("IBM285", "EBCDIC-CP-GB");
      Java2IANAMap.put("IBM-285", "EBCDIC-CP-GB");
      Java2IANAMap.put("285", "EBCDIC-CP-GB");
      Java2IANAMap.put("CP290", "EBCDIC-JP-KANA");
      Java2IANAMap.put("CP297", "EBCDIC-CP-FR");
      Java2IANAMap.put("IBM297", "EBCDIC-CP-FR");
      Java2IANAMap.put("IBM-297", "EBCDIC-CP-FR");
      Java2IANAMap.put("297", "EBCDIC-CP-FR");
      Java2IANAMap.put("CP420", "EBCDIC-CP-AR1");
      Java2IANAMap.put("IBM420", "EBCDIC-CP-AR1");
      Java2IANAMap.put("IBM-420", "EBCDIC-CP-AR1");
      Java2IANAMap.put("420", "EBCDIC-CP-AR1");
      Java2IANAMap.put("CP424", "EBCDIC-CP-HE");
      Java2IANAMap.put("IBM424", "EBCDIC-CP-HE");
      Java2IANAMap.put("IBM-424", "EBCDIC-CP-HE");
      Java2IANAMap.put("424", "EBCDIC-CP-HE");
      Java2IANAMap.put("CP437", "IBM437");
      Java2IANAMap.put("IBM437", "IBM437");
      Java2IANAMap.put("IBM-437", "IBM437");
      Java2IANAMap.put("437", "IBM437");
      Java2IANAMap.put("CSPC8CODEPAGE437", "IBM437");
      Java2IANAMap.put("CP500", "EBCDIC-CP-CH");
      Java2IANAMap.put("IBM500", "EBCDIC-CP-CH");
      Java2IANAMap.put("IBM-500", "EBCDIC-CP-CH");
      Java2IANAMap.put("500", "EBCDIC-CP-CH");
      Java2IANAMap.put("CP775", "IBM775");
      Java2IANAMap.put("IBM775", "IBM775");
      Java2IANAMap.put("IBM-775", "IBM775");
      Java2IANAMap.put("775", "IBM775");
      Java2IANAMap.put("CP838", "IBM-Thai");
      Java2IANAMap.put("IBM838", "IBM-Thai");
      Java2IANAMap.put("IBM-838", "IBM-Thai");
      Java2IANAMap.put("838", "IBM-Thai");
      Java2IANAMap.put("CP850", "IBM850");
      Java2IANAMap.put("IBM850", "IBM850");
      Java2IANAMap.put("IBM-850", "IBM850");
      Java2IANAMap.put("850", "IBM850");
      Java2IANAMap.put("CSPC850MULTILINGUAL", "IBM850");
      Java2IANAMap.put("CP852", "IBM852");
      Java2IANAMap.put("IBM852", "IBM852");
      Java2IANAMap.put("IBM-852", "IBM852");
      Java2IANAMap.put("852", "IBM852");
      Java2IANAMap.put("CSPCP852", "IBM852");
      Java2IANAMap.put("CP855", "IBM855");
      Java2IANAMap.put("IBM855", "IBM855");
      Java2IANAMap.put("IBM-855", "IBM855");
      Java2IANAMap.put("855", "IBM855");
      Java2IANAMap.put("CSPCP855", "IBM855");
      Java2IANAMap.put("CP857", "IBM857");
      Java2IANAMap.put("IBM857", "IBM857");
      Java2IANAMap.put("IBM-857", "IBM857");
      Java2IANAMap.put("857", "IBM857");
      Java2IANAMap.put("CSIBM857", "IBM857");
      Java2IANAMap.put("CP858", "IBM00858");
      Java2IANAMap.put("CP860", "IBM860");
      Java2IANAMap.put("IBM860", "IBM860");
      Java2IANAMap.put("IBM-860", "IBM860");
      Java2IANAMap.put("860", "IBM860");
      Java2IANAMap.put("CSIBM860", "IBM860");
      Java2IANAMap.put("CP861", "IBM861");
      Java2IANAMap.put("IBM861", "IBM861");
      Java2IANAMap.put("IBM-861", "IBM861");
      Java2IANAMap.put("CP-IS", "IBM861");
      Java2IANAMap.put("861", "IBM861");
      Java2IANAMap.put("CSIBM861", "IBM861");
      Java2IANAMap.put("CP862", "IBM862");
      Java2IANAMap.put("IBM862", "IBM862");
      Java2IANAMap.put("IBM-862", "IBM862");
      Java2IANAMap.put("862", "IBM862");
      Java2IANAMap.put("CSPC862LATINHEBREW", "IBM862");
      Java2IANAMap.put("CP863", "IBM863");
      Java2IANAMap.put("IBM863", "IBM863");
      Java2IANAMap.put("IBM-863", "IBM863");
      Java2IANAMap.put("863", "IBM863");
      Java2IANAMap.put("CSIBM863", "IBM863");
      Java2IANAMap.put("CP864", "IBM864");
      Java2IANAMap.put("IBM864", "IBM864");
      Java2IANAMap.put("IBM-864", "IBM864");
      Java2IANAMap.put("CSIBM864", "IBM864");
      Java2IANAMap.put("CP865", "IBM865");
      Java2IANAMap.put("IBM865", "IBM865");
      Java2IANAMap.put("IBM-865", "IBM865");
      Java2IANAMap.put("865", "IBM865");
      Java2IANAMap.put("CSIBM865", "IBM865");
      Java2IANAMap.put("CP866", "IBM866");
      Java2IANAMap.put("IBM866", "IBM866");
      Java2IANAMap.put("IBM-866", "IBM866");
      Java2IANAMap.put("866", "IBM866");
      Java2IANAMap.put("CSIBM866", "IBM866");
      Java2IANAMap.put("CP868", "IBM868");
      Java2IANAMap.put("IBM868", "IBM868");
      Java2IANAMap.put("IBM-868", "IBM868");
      Java2IANAMap.put("868", "IBM868");
      Java2IANAMap.put("CP869", "IBM869");
      Java2IANAMap.put("IBM869", "IBM869");
      Java2IANAMap.put("IBM-869", "IBM869");
      Java2IANAMap.put("869", "IBM869");
      Java2IANAMap.put("CP-GR", "IBM869");
      Java2IANAMap.put("CSIBM869", "IBM869");
      Java2IANAMap.put("CP870", "EBCDIC-CP-ROECE");
      Java2IANAMap.put("IBM870", "EBCDIC-CP-ROECE");
      Java2IANAMap.put("IBM-870", "EBCDIC-CP-ROECE");
      Java2IANAMap.put("870", "EBCDIC-CP-ROECE");
      Java2IANAMap.put("CP871", "EBCDIC-CP-IS");
      Java2IANAMap.put("IBM871", "EBCDIC-CP-IS");
      Java2IANAMap.put("IBM-871", "EBCDIC-CP-IS");
      Java2IANAMap.put("871", "EBCDIC-CP-IS");
      Java2IANAMap.put("CP918", "EBCDIC-CP-AR2");
      Java2IANAMap.put("IBM918", "EBCDIC-CP-AR2");
      Java2IANAMap.put("IBM-918", "EBCDIC-CP-AR2");
      Java2IANAMap.put("918", "EBCDIC-CP-AR2");
      Java2IANAMap.put("CP924", "IBM00924");
      Java2IANAMap.put("CP1026", "IBM1026");
      Java2IANAMap.put("IBM1026", "IBM1026");
      Java2IANAMap.put("IBM-1026", "IBM1026");
      Java2IANAMap.put("1026", "IBM1026");
      Java2IANAMap.put("CP1140", "IBM01140");
      Java2IANAMap.put("CP1141", "IBM01141");
      Java2IANAMap.put("CP1142", "IBM01142");
      Java2IANAMap.put("CP1143", "IBM01143");
      Java2IANAMap.put("CP1144", "IBM01144");
      Java2IANAMap.put("CP1145", "IBM01145");
      Java2IANAMap.put("CP1146", "IBM01146");
      Java2IANAMap.put("CP1147", "IBM01147");
      Java2IANAMap.put("CP1148", "IBM01148");
      Java2IANAMap.put("CP1149", "IBM01149");
      Java2IANAMap.put("CP1047", "IBM1047");
      JAVA_TO_IANA_MAP = Collections.unmodifiableMap(Java2IANAMap);
      Map Locale2IANAMap = new ArrayMap();
      Locale2IANAMap.put("ar", "ISO-8859-6");
      Locale2IANAMap.put("be", "ISO-8859-5");
      Locale2IANAMap.put("bg", "ISO-8859-5");
      Locale2IANAMap.put("ca", "ISO-8859-1");
      Locale2IANAMap.put("cs", "ISO-8859-2");
      Locale2IANAMap.put("da", "ISO-8859-1");
      Locale2IANAMap.put("de", "ISO-8859-1");
      Locale2IANAMap.put("el", "ISO-8859-7");
      Locale2IANAMap.put("en", "ISO-8859-1");
      Locale2IANAMap.put("es", "ISO-8859-1");
      Locale2IANAMap.put("et", "ISO-8859-1");
      Locale2IANAMap.put("fi", "ISO-8859-1");
      Locale2IANAMap.put("fr", "ISO-8859-1");
      Locale2IANAMap.put("hr", "ISO-8859-2");
      Locale2IANAMap.put("hu", "ISO-8859-2");
      Locale2IANAMap.put("is", "ISO-8859-1");
      Locale2IANAMap.put("it", "ISO-8859-1");
      Locale2IANAMap.put("iw", "ISO-8859-8");
      Locale2IANAMap.put("ja", "Shift_JIS");
      Locale2IANAMap.put("ko", "EUC-KR");
      Locale2IANAMap.put("lt", "ISO-8859-2");
      Locale2IANAMap.put("lv", "ISO-8859-2");
      Locale2IANAMap.put("mk", "ISO-8859-5");
      Locale2IANAMap.put("nl", "ISO-8859-1");
      Locale2IANAMap.put("no", "ISO-8859-1");
      Locale2IANAMap.put("pl", "ISO-8859-2");
      Locale2IANAMap.put("pt", "ISO-8859-1");
      Locale2IANAMap.put("ro", "ISO-8859-2");
      Locale2IANAMap.put("ru", "ISO-8859-5");
      Locale2IANAMap.put("sh", "ISO-8859-5");
      Locale2IANAMap.put("sk", "ISO-8859-2");
      Locale2IANAMap.put("sl", "ISO-8859-2");
      Locale2IANAMap.put("sq", "ISO-8859-2");
      Locale2IANAMap.put("sr", "ISO-8859-5");
      Locale2IANAMap.put("sv", "ISO-8859-1");
      Locale2IANAMap.put("th", "TIS-620");
      Locale2IANAMap.put("tr", "ISO-8859-9");
      Locale2IANAMap.put("uk", "ISO-8859-5");
      Locale2IANAMap.put("zh", "GB2312");
      Locale2IANAMap.put("zh_TW", "Big5");
      LOCALE_TO_IANA_MAP = Collections.unmodifiableMap(Locale2IANAMap);
   }
}
