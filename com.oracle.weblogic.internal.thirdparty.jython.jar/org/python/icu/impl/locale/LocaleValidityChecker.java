package org.python.icu.impl.locale;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import org.python.icu.impl.ValidIdentifiers;
import org.python.icu.util.IllformedLocaleException;
import org.python.icu.util.Output;
import org.python.icu.util.ULocale;

public class LocaleValidityChecker {
   private final Set datasubtypes;
   private final boolean allowsDeprecated;
   static Pattern SEPARATOR = Pattern.compile("[-_]");
   private static final Pattern VALID_X = Pattern.compile("[a-zA-Z0-9]{2,8}(-[a-zA-Z0-9]{2,8})*");
   static final Set REORDERING_INCLUDE = new HashSet(Arrays.asList("space", "punct", "symbol", "currency", "digit", "others", "zzzz"));
   static final Set REORDERING_EXCLUDE = new HashSet(Arrays.asList("zinh", "zyyy"));
   static final Set REGULAR_ONLY;

   public LocaleValidityChecker(Set datasubtypes) {
      this.datasubtypes = EnumSet.copyOf(datasubtypes);
      this.allowsDeprecated = datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
   }

   public LocaleValidityChecker(ValidIdentifiers.Datasubtype... datasubtypes) {
      this.datasubtypes = EnumSet.copyOf(Arrays.asList(datasubtypes));
      this.allowsDeprecated = this.datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
   }

   public Set getDatasubtypes() {
      return EnumSet.copyOf(this.datasubtypes);
   }

   public boolean isValid(ULocale locale, Where where) {
      where.set((ValidIdentifiers.Datatype)null, (String)null);
      String language = locale.getLanguage();
      String script = locale.getScript();
      String region = locale.getCountry();
      String variantString = locale.getVariant();
      Set extensionKeys = locale.getExtensionKeys();
      if (!this.isValid(ValidIdentifiers.Datatype.language, language, where)) {
         if (language.equals("x")) {
            where.set((ValidIdentifiers.Datatype)null, (String)null);
            return true;
         } else {
            return false;
         }
      } else if (!this.isValid(ValidIdentifiers.Datatype.script, script, where)) {
         return false;
      } else if (!this.isValid(ValidIdentifiers.Datatype.region, region, where)) {
         return false;
      } else {
         if (!variantString.isEmpty()) {
            String[] var8 = SEPARATOR.split(variantString);
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String variant = var8[var10];
               if (!this.isValid(ValidIdentifiers.Datatype.variant, variant, where)) {
                  return false;
               }
            }
         }

         Iterator var13 = extensionKeys.iterator();

         while(var13.hasNext()) {
            Character c = (Character)var13.next();

            try {
               ValidIdentifiers.Datatype datatype = ValidIdentifiers.Datatype.valueOf(c + "");
               switch (datatype) {
                  case x:
                     return true;
                  case t:
                  case u:
                     if (!this.isValidU(locale, datatype, locale.getExtension(c), where)) {
                        return false;
                     }
               }
            } catch (Exception var12) {
               return where.set(ValidIdentifiers.Datatype.illegal, c + "");
            }
         }

         return true;
      }
   }

   private boolean isValidU(ULocale locale, ValidIdentifiers.Datatype datatype, String extensionString, Where where) {
      String key = "";
      int typeCount = 0;
      KeyTypeData.ValueType valueType = null;
      SpecialCase specialCase = null;
      StringBuilder prefix = new StringBuilder();
      Set seen = new HashSet();
      StringBuilder tBuffer = datatype == ValidIdentifiers.Datatype.t ? new StringBuilder() : null;
      String[] var12 = SEPARATOR.split(extensionString);
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         String subtag = var12[var14];
         if (subtag.length() == 2 && (tBuffer == null || subtag.charAt(1) <= '9')) {
            if (tBuffer != null) {
               if (tBuffer.length() != 0 && !this.isValidLocale(tBuffer.toString(), where)) {
                  return false;
               }

               tBuffer = null;
            }

            key = KeyTypeData.toBcpKey(subtag);
            if (key == null) {
               return where.set(datatype, subtag);
            }

            if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key)) {
               return where.set(datatype, key);
            }

            valueType = KeyTypeData.getValueType(key);
            specialCase = LocaleValidityChecker.SpecialCase.get(key);
            typeCount = 0;
         } else if (tBuffer != null) {
            if (tBuffer.length() != 0) {
               tBuffer.append('-');
            }

            tBuffer.append(subtag);
         } else {
            ++typeCount;
            switch (valueType) {
               case single:
                  if (typeCount > 1) {
                     return where.set(datatype, key + "-" + subtag);
                  }
                  break;
               case incremental:
                  if (typeCount == 1) {
                     prefix.setLength(0);
                     prefix.append(subtag);
                  } else {
                     prefix.append('-').append(subtag);
                     subtag = prefix.toString();
                  }
                  break;
               case multiple:
                  if (typeCount == 1) {
                     seen.clear();
                  }
            }

            switch (specialCase) {
               case anything:
                  break;
               case codepoints:
                  try {
                     if (Integer.parseInt(subtag, 16) > 1114111) {
                        return where.set(datatype, key + "-" + subtag);
                     }
                     break;
                  } catch (NumberFormatException var19) {
                     return where.set(datatype, key + "-" + subtag);
                  }
               case reorder:
                  boolean newlyAdded = seen.add(subtag.equals("zzzz") ? "others" : subtag);
                  if (newlyAdded && this.isScriptReorder(subtag)) {
                     break;
                  }

                  return where.set(datatype, key + "-" + subtag);
               case subdivision:
                  if (!this.isSubdivision(locale, subtag)) {
                     return where.set(datatype, key + "-" + subtag);
                  }
                  break;
               case rgKey:
                  if (subtag.length() < 6 || !subtag.endsWith("zzzz")) {
                     return where.set(datatype, subtag);
                  }

                  if (!this.isValid(ValidIdentifiers.Datatype.region, subtag.substring(0, subtag.length() - 4), where)) {
                     return false;
                  }
                  break;
               default:
                  Output isKnownKey = new Output();
                  Output isSpecialType = new Output();
                  String type = KeyTypeData.toBcpType(key, subtag, isKnownKey, isSpecialType);
                  if (type == null) {
                     return where.set(datatype, key + "-" + subtag);
                  }

                  if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key, subtag)) {
                     return where.set(datatype, key + "-" + subtag);
                  }
            }
         }
      }

      if (tBuffer != null && tBuffer.length() != 0 && !this.isValidLocale(tBuffer.toString(), where)) {
         return false;
      } else {
         return true;
      }
   }

   private boolean isSubdivision(ULocale locale, String subtag) {
      if (subtag.length() < 3) {
         return false;
      } else {
         String region = subtag.substring(0, subtag.charAt(0) <= '9' ? 3 : 2);
         String subdivision = subtag.substring(region.length());
         if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.subdivision, this.datasubtypes, region, subdivision) == null) {
            return false;
         } else {
            String localeRegion = locale.getCountry();
            if (localeRegion.isEmpty()) {
               ULocale max = ULocale.addLikelySubtags(locale);
               localeRegion = max.getCountry();
            }

            return region.equalsIgnoreCase(localeRegion);
         }
      }
   }

   private boolean isScriptReorder(String subtag) {
      subtag = AsciiUtil.toLowerString(subtag);
      if (REORDERING_INCLUDE.contains(subtag)) {
         return true;
      } else if (REORDERING_EXCLUDE.contains(subtag)) {
         return false;
      } else {
         return ValidIdentifiers.isValid(ValidIdentifiers.Datatype.script, REGULAR_ONLY, subtag) != null;
      }
   }

   private boolean isValidLocale(String extensionString, Where where) {
      try {
         ULocale locale = (new ULocale.Builder()).setLanguageTag(extensionString).build();
         return this.isValid(locale, where);
      } catch (IllformedLocaleException var6) {
         int startIndex = var6.getErrorIndex();
         String[] list = SEPARATOR.split(extensionString.substring(startIndex));
         return where.set(ValidIdentifiers.Datatype.t, list[0]);
      } catch (Exception var7) {
         return where.set(ValidIdentifiers.Datatype.t, var7.getMessage());
      }
   }

   private boolean isValid(ValidIdentifiers.Datatype datatype, String code, Where where) {
      if (code.isEmpty()) {
         return true;
      } else if (datatype == ValidIdentifiers.Datatype.variant && "posix".equalsIgnoreCase(code)) {
         return true;
      } else {
         return ValidIdentifiers.isValid(datatype, this.datasubtypes, code) != null ? true : (where == null ? false : where.set(datatype, code));
      }
   }

   static {
      REGULAR_ONLY = EnumSet.of(ValidIdentifiers.Datasubtype.regular);
   }

   static enum SpecialCase {
      normal,
      anything,
      reorder,
      codepoints,
      subdivision,
      rgKey;

      static SpecialCase get(String key) {
         if (key.equals("kr")) {
            return reorder;
         } else if (key.equals("vt")) {
            return codepoints;
         } else if (key.equals("sd")) {
            return subdivision;
         } else if (key.equals("rg")) {
            return rgKey;
         } else {
            return key.equals("x0") ? anything : normal;
         }
      }
   }

   public static class Where {
      public ValidIdentifiers.Datatype fieldFailure;
      public String codeFailure;

      public boolean set(ValidIdentifiers.Datatype datatype, String code) {
         this.fieldFailure = datatype;
         this.codeFailure = code;
         return false;
      }

      public String toString() {
         return this.fieldFailure == null ? "OK" : "{" + this.fieldFailure + ", " + this.codeFailure + "}";
      }
   }
}
