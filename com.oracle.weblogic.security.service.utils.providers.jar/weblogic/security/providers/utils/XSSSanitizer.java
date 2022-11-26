package weblogic.security.providers.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class XSSSanitizer {
   public static final String RESOURCE_URL_PREFIX = "/";
   public static final String ENCODING_PREFIX = "=?UTF-8?B?";
   public static final String ENCODING_POSTFIX = "?=";
   public static final String NULL_STR = "null";
   public static final String EMPTY_STRING = "";
   public static final String SPACE_CHAR = " ";
   public static final String NAME_VALUE_DELIMITER = "=";
   public static final String NAME_VALUE_PAIRS_DELIMETER = " ";
   public static final String COLON = ":";
   public static final String HOST = "host";
   public static final String PORT = "port";
   public static final String ENCODING_FORMAT = "UTF-8";
   protected static int minLength = 0;
   protected static int maxLength = Integer.MAX_VALUE;
   private static final Character[] FILTER_CHARS = new Character[]{'<', '>', '(', ')', '"', '\'', ' ', '/', '?', ':', ';', '\\', '%', '\r', '\n'};
   private static final String[] REPLACEMENT_CHARS = new String[]{"%3C", "%3E", "%28", "%29", "%22", "%27", "%20", "%2F", "%3F", "%3A", "%3B", "%5C", "%25", "%0d", "%0a"};
   private static final Pattern[] SCRIPT_PATTERNS = new Pattern[]{Pattern.compile("<script>(.*?)</script>", 2), Pattern.compile("<script(.*?)>", 42), Pattern.compile("</script>", 2), Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42), Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42), Pattern.compile("eval\\((.*?)\\)", 42), Pattern.compile("expression\\((.*?)\\)", 42), Pattern.compile("javascript:", 2), Pattern.compile("vbscript:", 2), Pattern.compile("onload(.*?)=", 42)};
   private static final String ABSOLUTE_REGEX = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
   private static final Pattern ABSOLUTE_PATTERN = Pattern.compile("^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$", 2);
   private static final String RELATIVE_REGEX = "((%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$";
   private static final Pattern RELATIVE_PATTERN = Pattern.compile("((%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$", 2);
   private static final Map REPLACEMENT_MAP = new HashMap();
   private static final Set FILTER_SET = new HashSet();

   public static String sanitizeStringInput(String input) {
      String value = null;
      if (input != null) {
         StringBuilder sb = new StringBuilder();
         char[] chars = input.toCharArray();

         for(int i = 0; i < chars.length; ++i) {
            if (FILTER_SET.contains(chars[i])) {
               sb.append((String)REPLACEMENT_MAP.get(chars[i]));
            } else {
               sb.append(chars[i]);
            }
         }

         value = sb.toString();
      }

      return value;
   }

   public static String stripXSS(String input) {
      String cleanValue = null;
      if (input != null) {
         cleanValue = Normalizer.normalize(input, Form.NFD);
         cleanValue = cleanValue.replaceAll("\u0000", "");

         for(int i = 0; i < SCRIPT_PATTERNS.length; ++i) {
            cleanValue = SCRIPT_PATTERNS[i].matcher(cleanValue).replaceAll("");
         }
      }

      return cleanValue;
   }

   public static boolean isStringClean(String input) {
      return input != null && !input.isEmpty() ? Normalizer.normalize(input, Form.NFD).equals(stripXSS(input)) : true;
   }

   public static boolean isValidLocation(String input) {
      boolean isValid = false;
      boolean isRelativeURL = false;

      try {
         isRelativeURL = input.startsWith("/");
         if (isRelativeURL) {
            isValid = RELATIVE_PATTERN.matcher(input).matches();
         } else {
            isValid = ABSOLUTE_PATTERN.matcher(input).matches();
         }

         return isValid;
      } catch (PatternSyntaxException var4) {
         throw new IllegalArgumentException("Redirect URL syntax is invalid.");
      }
   }

   public static String sanitizeLocation(String input) {
      String cleanValue = null;
      if (input != null) {
         cleanValue = Normalizer.normalize(input, Form.NFD);
         cleanValue = cleanValue.replaceAll("\u0000", "");

         for(int i = 0; i < SCRIPT_PATTERNS.length; ++i) {
            cleanValue = SCRIPT_PATTERNS[i].matcher(cleanValue).replaceAll("");
         }

         cleanValue = cleanValue.replaceAll("\r", "");
         cleanValue = cleanValue.replaceAll("\n", "");
      }

      return cleanValue;
   }

   public static String getValidInput(String input) throws IllegalArgumentException {
      if (input != null && input.trim().length() != 0) {
         if (input.length() < minLength) {
            throw new IllegalArgumentException("Input is less than the minimum length.");
         } else if (input.length() > maxLength) {
            throw new IllegalArgumentException("Input is more than the maximum length.");
         } else if (!isValidLocation(input)) {
            throw new IllegalArgumentException("Redirect validation failed");
         } else {
            return sanitizeLocation(input);
         }
      } else {
         throw new IllegalArgumentException("Input is null or empty.");
      }
   }

   static {
      for(int i = 0; i < FILTER_CHARS.length && i < REPLACEMENT_CHARS.length; ++i) {
         char filterChar = FILTER_CHARS[i];
         FILTER_SET.add(filterChar);
         REPLACEMENT_MAP.put(filterChar, REPLACEMENT_CHARS[i]);
      }

   }
}
