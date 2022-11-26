package org.hibernate.validator.internal.util;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DomainNameUtil {
   private static final int MAX_DOMAIN_PART_LENGTH = 255;
   private static final String DOMAIN_CHARS_WITHOUT_DASH = "[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]";
   private static final String DOMAIN_LABEL = "([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]+";
   private static final String DOMAIN = "([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++(\\.([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++)*";
   private static final String IP_DOMAIN = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
   private static final String IP_V6_DOMAIN = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";
   private static final Pattern DOMAIN_PATTERN = Pattern.compile("([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++(\\.([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++)*|\\[(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\\]", 2);
   private static final Pattern EMAIL_DOMAIN_PATTERN = Pattern.compile("([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++(\\.([a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]-*)*[a-z\u0080-\uffff0-9!#$%&'*+/=?^_`{|}~]++)*|\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]|\\[IPv6:(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\\]", 2);

   private DomainNameUtil() {
   }

   public static boolean isValidEmailDomainAddress(String domain) {
      return isValidDomainAddress(domain, EMAIL_DOMAIN_PATTERN);
   }

   public static boolean isValidDomainAddress(String domain) {
      return isValidDomainAddress(domain, DOMAIN_PATTERN);
   }

   private static boolean isValidDomainAddress(String domain, Pattern pattern) {
      if (domain.endsWith(".")) {
         return false;
      } else {
         Matcher matcher = pattern.matcher(domain);
         if (!matcher.matches()) {
            return false;
         } else {
            String asciiString;
            try {
               asciiString = IDN.toASCII(domain);
            } catch (IllegalArgumentException var5) {
               return false;
            }

            return asciiString.length() <= 255;
         }
      }
   }
}
