package org.python.icu.impl.locale;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class UnicodeLocaleExtension extends Extension {
   public static final char SINGLETON = 'u';
   private static final SortedSet EMPTY_SORTED_SET = new TreeSet();
   private static final SortedMap EMPTY_SORTED_MAP = new TreeMap();
   private SortedSet _attributes;
   private SortedMap _keywords;
   public static final UnicodeLocaleExtension CA_JAPANESE = new UnicodeLocaleExtension();
   public static final UnicodeLocaleExtension NU_THAI;

   private UnicodeLocaleExtension() {
      super('u');
      this._attributes = EMPTY_SORTED_SET;
      this._keywords = EMPTY_SORTED_MAP;
   }

   UnicodeLocaleExtension(SortedSet attributes, SortedMap keywords) {
      this();
      if (attributes != null && attributes.size() > 0) {
         this._attributes = attributes;
      }

      if (keywords != null && keywords.size() > 0) {
         this._keywords = keywords;
      }

      if (this._attributes.size() > 0 || this._keywords.size() > 0) {
         StringBuilder sb = new StringBuilder();
         Iterator var4 = this._attributes.iterator();

         while(var4.hasNext()) {
            String attribute = (String)var4.next();
            sb.append("-").append(attribute);
         }

         var4 = this._keywords.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry keyword = (Map.Entry)var4.next();
            String key = (String)keyword.getKey();
            String value = (String)keyword.getValue();
            sb.append("-").append(key);
            if (value.length() > 0) {
               sb.append("-").append(value);
            }
         }

         this._value = sb.substring(1);
      }

   }

   public Set getUnicodeLocaleAttributes() {
      return Collections.unmodifiableSet(this._attributes);
   }

   public Set getUnicodeLocaleKeys() {
      return Collections.unmodifiableSet(this._keywords.keySet());
   }

   public String getUnicodeLocaleType(String unicodeLocaleKey) {
      return (String)this._keywords.get(unicodeLocaleKey);
   }

   public static boolean isSingletonChar(char c) {
      return 'u' == AsciiUtil.toLower(c);
   }

   public static boolean isAttribute(String s) {
      return s.length() >= 3 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
   }

   public static boolean isKey(String s) {
      return s.length() == 2 && AsciiUtil.isAlphaNumericString(s);
   }

   public static boolean isTypeSubtag(String s) {
      return s.length() >= 3 && s.length() <= 8 && AsciiUtil.isAlphaNumericString(s);
   }

   public static boolean isType(String s) {
      int startIdx = 0;
      boolean sawSubtag = false;

      while(true) {
         int idx = s.indexOf("-", startIdx);
         String subtag = idx < 0 ? s.substring(startIdx) : s.substring(startIdx, idx);
         if (!isTypeSubtag(subtag)) {
            return false;
         }

         sawSubtag = true;
         if (idx < 0) {
            return sawSubtag && startIdx < s.length();
         }

         startIdx = idx + 1;
      }
   }

   static {
      CA_JAPANESE._keywords = new TreeMap();
      CA_JAPANESE._keywords.put("ca", "japanese");
      CA_JAPANESE._value = "ca-japanese";
      NU_THAI = new UnicodeLocaleExtension();
      NU_THAI._keywords = new TreeMap();
      NU_THAI._keywords.put("nu", "thai");
      NU_THAI._value = "nu-thai";
   }
}
