package weblogic.xml.schema.types.util;

import java.util.Arrays;
import java.util.Locale;

public class LanguageValidator {
   private static boolean sorted = false;
   private static final String[] languageTokens = new String[]{"AA", "AB", "AF", "AM", "AR", "AS", "AY", "AZ", "BA", "BE", "BG", "BH", "BI", "BN", "BO", "BR", "CA", "CO", "CS", "CY", "DA", "DE", "DZ", "EL", "EN", "EO", "ES", "ET", "EU", "FA", "FI", "FJ", "FO", "FR", "FY", "GA", "GD", "GL", "GN", "GU", "HA", "HI", "HR", "HU", "HY", "IA", "IE", "IK", "IN", "IS", "IT", "IW", "JA", "JI", "JW", "KA", "KK", "KL", "KM", "KN", "KO", "KS", "KU", "KY", "LA", "LN", "LO", "LT", "LV", "MG", "MI", "MK", "ML", "MN", "MO", "MR", "MS", "MT", "MY", "NA", "NE", "NL", "NO", "OC", "OM", "OR", "PA", "PL", "PS", "PT", "QU", "RM", "RN", "RO", "RU", "RW", "SA", "SD", "SG", "SH", "SI", "SK", "SL", "SM", "SN", "SO", "SQ", "SR", "SS", "ST", "SU", "SV", "SW", "TA", "TE", "TG", "TH", "TI", "TK", "TL", "TN", "TO", "TR", "TS", "TT", "TW", "UK", "UR", "UZ", "VI", "VO", "WO", "XH", "YO", "ZH", "ZU"};
   private static final String[] countryTokens = new String[]{"AF", "AL", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BA", "BW", "BV", "BR", "IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO", "KM", "CG", "CD", "CK", "CR", "CI", "HR", "CU", "CY", "CZ", "DK", "DJ", "DM", "DO", "DZ", "TP", "EC", "EG", "SV", "GQ", "ER", "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE", "GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "MS", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "AN", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "RE", "RO", "RU", "RW", "SH", "KN", "LC", "PM", "VC", "WS", "SM", "ST", "SA", "SN", "SC", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "GS", "ES", "LK", "SD", "SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TG", "TK", "TO", "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "US", "UM", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE", "YU", "ZM", "ZW"};
   private static final String[] ianaTokens = new String[]{"CEL-GAULISH", "EN-SCOUSE", "I-AMI", "I-BNN", "I-DEFAULT", "I-HAK", "I-KLINGON", "I-LUX", "I-MINGO", "I-NAVAJO", "I-PWN", "I-TAO", "I-TAY", "I-TSU", "NO-BOK", "NO-NYN", "SGN-GB", "SGN-IE", "SGN-NI", "SGN-US", "ZH-GAN", "ZH-GUOYU", "ZH-HAKKA", "ZH-MIN", "ZH-MIN-NAN", "ZH-WUU", "ZH-XIANG", "ZH-YUE"};

   public static boolean validLanguage(String language) {
      if (!sorted) {
         sort();
      }

      language = language.toUpperCase(Locale.ENGLISH);
      int length = language.length();
      int pos = language.indexOf(45);
      if (length == 2) {
         return validLanguageToken(language);
      } else if (length == 5 && pos == 2) {
         return validLanguageToken(language.substring(0, 2)) && validCountryToken(language.substring(3));
      } else {
         return validIanaToken(language);
      }
   }

   private static boolean validLanguageToken(String token) {
      return Arrays.binarySearch(languageTokens, token) >= 0;
   }

   private static boolean validCountryToken(String token) {
      return Arrays.binarySearch(countryTokens, token) >= 0;
   }

   private static boolean validIanaToken(String token) {
      return Arrays.binarySearch(ianaTokens, token) >= 0;
   }

   private static void sort() {
      Arrays.sort(languageTokens);
      Arrays.sort(countryTokens);
      Arrays.sort(ianaTokens);
      sorted = true;
   }
}
