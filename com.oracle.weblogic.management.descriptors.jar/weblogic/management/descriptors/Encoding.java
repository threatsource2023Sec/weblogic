package weblogic.management.descriptors;

import java.util.Locale;
import weblogic.apache.xerces.util.EncodingMap;

public final class Encoding {
   public static String getIANA2JavaMapping(String ianaEncoding) {
      return EncodingMap.getIANA2JavaMapping(ianaEncoding.toUpperCase(Locale.ENGLISH));
   }

   public static String getJava2IANAMapping(String javaEncoding) {
      return EncodingMap.getJava2IANAMapping(javaEncoding.toUpperCase(Locale.ENGLISH));
   }
}
