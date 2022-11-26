package org.python.icu.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.python.icu.impl.CacheBase;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.ICUResourceTableAccess;
import org.python.icu.impl.LocaleIDParser;
import org.python.icu.impl.LocaleIDs;
import org.python.icu.impl.LocaleUtility;
import org.python.icu.impl.SoftCache;
import org.python.icu.impl.locale.AsciiUtil;
import org.python.icu.impl.locale.BaseLocale;
import org.python.icu.impl.locale.Extension;
import org.python.icu.impl.locale.InternalLocaleBuilder;
import org.python.icu.impl.locale.KeyTypeData;
import org.python.icu.impl.locale.LanguageTag;
import org.python.icu.impl.locale.LocaleExtensions;
import org.python.icu.impl.locale.LocaleSyntaxException;
import org.python.icu.impl.locale.ParseStatus;
import org.python.icu.impl.locale.UnicodeLocaleExtension;
import org.python.icu.lang.UScript;
import org.python.icu.text.LocaleDisplayNames;

public final class ULocale implements Serializable, Comparable {
   private static final long serialVersionUID = 3715177670352309217L;
   private static CacheBase nameCache = new SoftCache() {
      protected String createInstance(String tmpLocaleID, Void unused) {
         return (new LocaleIDParser(tmpLocaleID)).getName();
      }
   };
   public static final ULocale ENGLISH;
   public static final ULocale FRENCH;
   public static final ULocale GERMAN;
   public static final ULocale ITALIAN;
   public static final ULocale JAPANESE;
   public static final ULocale KOREAN;
   public static final ULocale CHINESE;
   public static final ULocale SIMPLIFIED_CHINESE;
   public static final ULocale TRADITIONAL_CHINESE;
   public static final ULocale FRANCE;
   public static final ULocale GERMANY;
   public static final ULocale ITALY;
   public static final ULocale JAPAN;
   public static final ULocale KOREA;
   public static final ULocale CHINA;
   public static final ULocale PRC;
   public static final ULocale TAIWAN;
   public static final ULocale UK;
   public static final ULocale US;
   public static final ULocale CANADA;
   public static final ULocale CANADA_FRENCH;
   private static final String EMPTY_STRING = "";
   private static final char UNDERSCORE = '_';
   private static final Locale EMPTY_LOCALE;
   private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
   public static final ULocale ROOT;
   private static final SoftCache CACHE;
   private transient volatile Locale locale;
   private String localeID;
   private transient volatile BaseLocale baseLocale;
   private transient volatile LocaleExtensions extensions;
   private static String[][] CANONICALIZE_MAP;
   private static String[][] variantsToKeywords;
   private static Locale defaultLocale;
   private static ULocale defaultULocale;
   private static Locale[] defaultCategoryLocales;
   private static ULocale[] defaultCategoryULocales;
   private static final String LANG_DIR_STRING = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-";
   public static Type ACTUAL_LOCALE;
   public static Type VALID_LOCALE;
   private static final String UNDEFINED_LANGUAGE = "und";
   private static final String UNDEFINED_SCRIPT = "Zzzz";
   private static final String UNDEFINED_REGION = "ZZ";
   public static final char PRIVATE_USE_EXTENSION = 'x';
   public static final char UNICODE_LOCALE_EXTENSION = 'u';

   private ULocale(String localeID, Locale locale) {
      this.localeID = localeID;
      this.locale = locale;
   }

   private ULocale(Locale loc) {
      this.localeID = getName(forLocale(loc).toString());
      this.locale = loc;
   }

   public static ULocale forLocale(Locale loc) {
      return loc == null ? null : (ULocale)CACHE.getInstance(loc, (Object)null);
   }

   public ULocale(String localeID) {
      this.localeID = getName(localeID);
   }

   public ULocale(String a, String b) {
      this(a, (String)b, (String)null);
   }

   public ULocale(String a, String b, String c) {
      this.localeID = getName(lscvToID(a, b, c, ""));
   }

   public static ULocale createCanonical(String nonCanonicalID) {
      return new ULocale(canonicalize(nonCanonicalID), (Locale)null);
   }

   private static String lscvToID(String lang, String script, String country, String variant) {
      StringBuilder buf = new StringBuilder();
      if (lang != null && lang.length() > 0) {
         buf.append(lang);
      }

      if (script != null && script.length() > 0) {
         buf.append('_');
         buf.append(script);
      }

      if (country != null && country.length() > 0) {
         buf.append('_');
         buf.append(country);
      }

      if (variant != null && variant.length() > 0) {
         if (country == null || country.length() == 0) {
            buf.append('_');
         }

         buf.append('_');
         buf.append(variant);
      }

      return buf.toString();
   }

   public Locale toLocale() {
      if (this.locale == null) {
         this.locale = ULocale.JDKLocaleHelper.toLocale(this);
      }

      return this.locale;
   }

   public static ULocale getDefault() {
      Class var0 = ULocale.class;
      synchronized(ULocale.class) {
         if (defaultULocale == null) {
            return ROOT;
         } else {
            Locale currentDefault = Locale.getDefault();
            if (!defaultLocale.equals(currentDefault)) {
               defaultLocale = currentDefault;
               defaultULocale = forLocale(currentDefault);
               if (!ULocale.JDKLocaleHelper.hasLocaleCategories()) {
                  Category[] var2 = ULocale.Category.values();
                  int var3 = var2.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                     Category cat = var2[var4];
                     int idx = cat.ordinal();
                     defaultCategoryLocales[idx] = currentDefault;
                     defaultCategoryULocales[idx] = forLocale(currentDefault);
                  }
               }
            }

            return defaultULocale;
         }
      }
   }

   public static synchronized void setDefault(ULocale newLocale) {
      defaultLocale = newLocale.toLocale();
      Locale.setDefault(defaultLocale);
      defaultULocale = newLocale;
      Category[] var1 = ULocale.Category.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Category cat = var1[var3];
         setDefault(cat, newLocale);
      }

   }

   public static ULocale getDefault(Category category) {
      Class var1 = ULocale.class;
      synchronized(ULocale.class) {
         int idx = category.ordinal();
         if (defaultCategoryULocales[idx] == null) {
            return ROOT;
         } else {
            Locale currentDefault;
            if (ULocale.JDKLocaleHelper.hasLocaleCategories()) {
               currentDefault = ULocale.JDKLocaleHelper.getDefault(category);
               if (!defaultCategoryLocales[idx].equals(currentDefault)) {
                  defaultCategoryLocales[idx] = currentDefault;
                  defaultCategoryULocales[idx] = forLocale(currentDefault);
               }
            } else {
               currentDefault = Locale.getDefault();
               if (!defaultLocale.equals(currentDefault)) {
                  defaultLocale = currentDefault;
                  defaultULocale = forLocale(currentDefault);
                  Category[] var4 = ULocale.Category.values();
                  int var5 = var4.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     Category cat = var4[var6];
                     int tmpIdx = cat.ordinal();
                     defaultCategoryLocales[tmpIdx] = currentDefault;
                     defaultCategoryULocales[tmpIdx] = forLocale(currentDefault);
                  }
               }
            }

            return defaultCategoryULocales[idx];
         }
      }
   }

   public static synchronized void setDefault(Category category, ULocale newLocale) {
      Locale newJavaDefault = newLocale.toLocale();
      int idx = category.ordinal();
      defaultCategoryULocales[idx] = newLocale;
      defaultCategoryLocales[idx] = newJavaDefault;
      ULocale.JDKLocaleHelper.setDefault(category, newJavaDefault);
   }

   public Object clone() {
      return this;
   }

   public int hashCode() {
      return this.localeID.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof ULocale ? this.localeID.equals(((ULocale)obj).localeID) : false;
      }
   }

   public int compareTo(ULocale other) {
      if (this == other) {
         return 0;
      } else {
         int cmp = false;
         int cmp = this.getLanguage().compareTo(other.getLanguage());
         if (cmp == 0) {
            cmp = this.getScript().compareTo(other.getScript());
            if (cmp == 0) {
               cmp = this.getCountry().compareTo(other.getCountry());
               if (cmp == 0) {
                  cmp = this.getVariant().compareTo(other.getVariant());
                  if (cmp == 0) {
                     Iterator thisKwdItr = this.getKeywords();
                     Iterator otherKwdItr = other.getKeywords();
                     if (thisKwdItr == null) {
                        cmp = otherKwdItr == null ? 0 : -1;
                     } else if (otherKwdItr == null) {
                        cmp = 1;
                     } else {
                        while(cmp == 0 && thisKwdItr.hasNext()) {
                           if (!otherKwdItr.hasNext()) {
                              cmp = 1;
                              break;
                           }

                           String thisKey = (String)thisKwdItr.next();
                           String otherKey = (String)otherKwdItr.next();
                           cmp = thisKey.compareTo(otherKey);
                           if (cmp == 0) {
                              String thisVal = this.getKeywordValue(thisKey);
                              String otherVal = other.getKeywordValue(otherKey);
                              if (thisVal == null) {
                                 cmp = otherVal == null ? 0 : -1;
                              } else if (otherVal == null) {
                                 cmp = 1;
                              } else {
                                 cmp = thisVal.compareTo(otherVal);
                              }
                           }
                        }

                        if (cmp == 0 && otherKwdItr.hasNext()) {
                           cmp = -1;
                        }
                     }
                  }
               }
            }
         }

         return cmp < 0 ? -1 : (cmp > 0 ? 1 : 0);
      }
   }

   public static ULocale[] getAvailableLocales() {
      return ICUResourceBundle.getAvailableULocales();
   }

   public static String[] getISOCountries() {
      return LocaleIDs.getISOCountries();
   }

   public static String[] getISOLanguages() {
      return LocaleIDs.getISOLanguages();
   }

   public String getLanguage() {
      return this.base().getLanguage();
   }

   public static String getLanguage(String localeID) {
      return (new LocaleIDParser(localeID)).getLanguage();
   }

   public String getScript() {
      return this.base().getScript();
   }

   public static String getScript(String localeID) {
      return (new LocaleIDParser(localeID)).getScript();
   }

   public String getCountry() {
      return this.base().getRegion();
   }

   public static String getCountry(String localeID) {
      return (new LocaleIDParser(localeID)).getCountry();
   }

   /** @deprecated */
   @Deprecated
   public static String getRegionForSupplementalData(ULocale locale, boolean inferRegion) {
      String region = locale.getKeywordValue("rg");
      if (region != null && region.length() == 6) {
         String regionUpper = AsciiUtil.toUpperString(region);
         if (regionUpper.endsWith("ZZZZ")) {
            return regionUpper.substring(0, 2);
         }
      }

      region = locale.getCountry();
      if (region.length() == 0 && inferRegion) {
         ULocale maximized = addLikelySubtags(locale);
         region = maximized.getCountry();
      }

      return region;
   }

   public String getVariant() {
      return this.base().getVariant();
   }

   public static String getVariant(String localeID) {
      return (new LocaleIDParser(localeID)).getVariant();
   }

   public static String getFallback(String localeID) {
      return getFallbackString(getName(localeID));
   }

   public ULocale getFallback() {
      return this.localeID.length() != 0 && this.localeID.charAt(0) != '@' ? new ULocale(getFallbackString(this.localeID), (Locale)null) : null;
   }

   private static String getFallbackString(String fallback) {
      int extStart = fallback.indexOf(64);
      if (extStart == -1) {
         extStart = fallback.length();
      }

      int last = fallback.lastIndexOf(95, extStart);
      if (last == -1) {
         last = 0;
      } else {
         while(last > 0 && fallback.charAt(last - 1) == '_') {
            --last;
         }
      }

      return fallback.substring(0, last) + fallback.substring(extStart);
   }

   public String getBaseName() {
      return getBaseName(this.localeID);
   }

   public static String getBaseName(String localeID) {
      return localeID.indexOf(64) == -1 ? localeID : (new LocaleIDParser(localeID)).getBaseName();
   }

   public String getName() {
      return this.localeID;
   }

   private static int getShortestSubtagLength(String localeID) {
      int localeIDLength = localeID.length();
      int length = localeIDLength;
      boolean reset = true;
      int tmpLength = 0;

      for(int i = 0; i < localeIDLength; ++i) {
         if (localeID.charAt(i) != '_' && localeID.charAt(i) != '-') {
            if (reset) {
               reset = false;
               tmpLength = 0;
            }

            ++tmpLength;
         } else {
            if (tmpLength != 0 && tmpLength < length) {
               length = tmpLength;
            }

            reset = true;
         }
      }

      return length;
   }

   public static String getName(String localeID) {
      String tmpLocaleID;
      if (localeID != null && !localeID.contains("@") && getShortestSubtagLength(localeID) == 1) {
         tmpLocaleID = forLanguageTag(localeID).getName();
         if (tmpLocaleID.length() == 0) {
            tmpLocaleID = localeID;
         }
      } else {
         tmpLocaleID = localeID;
      }

      return (String)nameCache.getInstance(tmpLocaleID, (Object)null);
   }

   public String toString() {
      return this.localeID;
   }

   public Iterator getKeywords() {
      return getKeywords(this.localeID);
   }

   public static Iterator getKeywords(String localeID) {
      return (new LocaleIDParser(localeID)).getKeywords();
   }

   public String getKeywordValue(String keywordName) {
      return getKeywordValue(this.localeID, keywordName);
   }

   public static String getKeywordValue(String localeID, String keywordName) {
      return (new LocaleIDParser(localeID)).getKeywordValue(keywordName);
   }

   public static String canonicalize(String localeID) {
      LocaleIDParser parser = new LocaleIDParser(localeID, true);
      String baseName = parser.getBaseName();
      boolean foundVariant = false;
      if (localeID.equals("")) {
         return "";
      } else {
         int i;
         String[] vals;
         for(i = 0; i < variantsToKeywords.length; ++i) {
            vals = variantsToKeywords[i];
            int idx = baseName.lastIndexOf("_" + vals[0]);
            if (idx > -1) {
               foundVariant = true;
               baseName = baseName.substring(0, idx);
               if (baseName.endsWith("_")) {
                  --idx;
                  baseName = baseName.substring(0, idx);
               }

               parser.setBaseName(baseName);
               parser.defaultKeywordValue(vals[1], vals[2]);
               break;
            }
         }

         for(i = 0; i < CANONICALIZE_MAP.length; ++i) {
            if (CANONICALIZE_MAP[i][0].equals(baseName)) {
               foundVariant = true;
               vals = CANONICALIZE_MAP[i];
               parser.setBaseName(vals[1]);
               if (vals[2] != null) {
                  parser.defaultKeywordValue(vals[2], vals[3]);
               }
               break;
            }
         }

         if (!foundVariant && parser.getLanguage().equals("nb") && parser.getVariant().equals("NY")) {
            parser.setBaseName(lscvToID("nn", parser.getScript(), parser.getCountry(), (String)null));
         }

         return parser.getName();
      }
   }

   public ULocale setKeywordValue(String keyword, String value) {
      return new ULocale(setKeywordValue(this.localeID, keyword, value), (Locale)null);
   }

   public static String setKeywordValue(String localeID, String keyword, String value) {
      LocaleIDParser parser = new LocaleIDParser(localeID);
      parser.setKeywordValue(keyword, value);
      return parser.getName();
   }

   public String getISO3Language() {
      return getISO3Language(this.localeID);
   }

   public static String getISO3Language(String localeID) {
      return LocaleIDs.getISO3Language(getLanguage(localeID));
   }

   public String getISO3Country() {
      return getISO3Country(this.localeID);
   }

   public static String getISO3Country(String localeID) {
      return LocaleIDs.getISO3Country(getCountry(localeID));
   }

   public boolean isRightToLeft() {
      String script = this.getScript();
      if (script.length() == 0) {
         String lang = this.getLanguage();
         if (lang.length() == 0) {
            return false;
         }

         int langIndex = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-".indexOf(lang);
         if (langIndex >= 0) {
            switch ("root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-".charAt(langIndex + lang.length())) {
               case '+':
                  return true;
               case '-':
                  return false;
            }
         }

         ULocale likely = addLikelySubtags(this);
         script = likely.getScript();
         if (script.length() == 0) {
            return false;
         }
      }

      int scriptCode = UScript.getCodeFromName(script);
      return UScript.isRightToLeft(scriptCode);
   }

   public String getDisplayLanguage() {
      return getDisplayLanguageInternal(this, getDefault(ULocale.Category.DISPLAY), false);
   }

   public String getDisplayLanguage(ULocale displayLocale) {
      return getDisplayLanguageInternal(this, displayLocale, false);
   }

   public static String getDisplayLanguage(String localeID, String displayLocaleID) {
      return getDisplayLanguageInternal(new ULocale(localeID), new ULocale(displayLocaleID), false);
   }

   public static String getDisplayLanguage(String localeID, ULocale displayLocale) {
      return getDisplayLanguageInternal(new ULocale(localeID), displayLocale, false);
   }

   public String getDisplayLanguageWithDialect() {
      return getDisplayLanguageInternal(this, getDefault(ULocale.Category.DISPLAY), true);
   }

   public String getDisplayLanguageWithDialect(ULocale displayLocale) {
      return getDisplayLanguageInternal(this, displayLocale, true);
   }

   public static String getDisplayLanguageWithDialect(String localeID, String displayLocaleID) {
      return getDisplayLanguageInternal(new ULocale(localeID), new ULocale(displayLocaleID), true);
   }

   public static String getDisplayLanguageWithDialect(String localeID, ULocale displayLocale) {
      return getDisplayLanguageInternal(new ULocale(localeID), displayLocale, true);
   }

   private static String getDisplayLanguageInternal(ULocale locale, ULocale displayLocale, boolean useDialect) {
      String lang = useDialect ? locale.getBaseName() : locale.getLanguage();
      return LocaleDisplayNames.getInstance(displayLocale).languageDisplayName(lang);
   }

   public String getDisplayScript() {
      return getDisplayScriptInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   /** @deprecated */
   @Deprecated
   public String getDisplayScriptInContext() {
      return getDisplayScriptInContextInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayScript(ULocale displayLocale) {
      return getDisplayScriptInternal(this, displayLocale);
   }

   /** @deprecated */
   @Deprecated
   public String getDisplayScriptInContext(ULocale displayLocale) {
      return getDisplayScriptInContextInternal(this, displayLocale);
   }

   public static String getDisplayScript(String localeID, String displayLocaleID) {
      return getDisplayScriptInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   /** @deprecated */
   @Deprecated
   public static String getDisplayScriptInContext(String localeID, String displayLocaleID) {
      return getDisplayScriptInContextInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayScript(String localeID, ULocale displayLocale) {
      return getDisplayScriptInternal(new ULocale(localeID), displayLocale);
   }

   /** @deprecated */
   @Deprecated
   public static String getDisplayScriptInContext(String localeID, ULocale displayLocale) {
      return getDisplayScriptInContextInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayScriptInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).scriptDisplayName(locale.getScript());
   }

   private static String getDisplayScriptInContextInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).scriptDisplayNameInContext(locale.getScript());
   }

   public String getDisplayCountry() {
      return getDisplayCountryInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayCountry(ULocale displayLocale) {
      return getDisplayCountryInternal(this, displayLocale);
   }

   public static String getDisplayCountry(String localeID, String displayLocaleID) {
      return getDisplayCountryInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayCountry(String localeID, ULocale displayLocale) {
      return getDisplayCountryInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayCountryInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).regionDisplayName(locale.getCountry());
   }

   public String getDisplayVariant() {
      return getDisplayVariantInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayVariant(ULocale displayLocale) {
      return getDisplayVariantInternal(this, displayLocale);
   }

   public static String getDisplayVariant(String localeID, String displayLocaleID) {
      return getDisplayVariantInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayVariant(String localeID, ULocale displayLocale) {
      return getDisplayVariantInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayVariantInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).variantDisplayName(locale.getVariant());
   }

   public static String getDisplayKeyword(String keyword) {
      return getDisplayKeywordInternal(keyword, getDefault(ULocale.Category.DISPLAY));
   }

   public static String getDisplayKeyword(String keyword, String displayLocaleID) {
      return getDisplayKeywordInternal(keyword, new ULocale(displayLocaleID));
   }

   public static String getDisplayKeyword(String keyword, ULocale displayLocale) {
      return getDisplayKeywordInternal(keyword, displayLocale);
   }

   private static String getDisplayKeywordInternal(String keyword, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).keyDisplayName(keyword);
   }

   public String getDisplayKeywordValue(String keyword) {
      return getDisplayKeywordValueInternal(this, keyword, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayKeywordValue(String keyword, ULocale displayLocale) {
      return getDisplayKeywordValueInternal(this, keyword, displayLocale);
   }

   public static String getDisplayKeywordValue(String localeID, String keyword, String displayLocaleID) {
      return getDisplayKeywordValueInternal(new ULocale(localeID), keyword, new ULocale(displayLocaleID));
   }

   public static String getDisplayKeywordValue(String localeID, String keyword, ULocale displayLocale) {
      return getDisplayKeywordValueInternal(new ULocale(localeID), keyword, displayLocale);
   }

   private static String getDisplayKeywordValueInternal(ULocale locale, String keyword, ULocale displayLocale) {
      keyword = AsciiUtil.toLowerString(keyword.trim());
      String value = locale.getKeywordValue(keyword);
      return LocaleDisplayNames.getInstance(displayLocale).keyValueDisplayName(keyword, value);
   }

   public String getDisplayName() {
      return getDisplayNameInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayName(ULocale displayLocale) {
      return getDisplayNameInternal(this, displayLocale);
   }

   public static String getDisplayName(String localeID, String displayLocaleID) {
      return getDisplayNameInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayName(String localeID, ULocale displayLocale) {
      return getDisplayNameInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayNameInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale).localeDisplayName(locale);
   }

   public String getDisplayNameWithDialect() {
      return getDisplayNameWithDialectInternal(this, getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayNameWithDialect(ULocale displayLocale) {
      return getDisplayNameWithDialectInternal(this, displayLocale);
   }

   public static String getDisplayNameWithDialect(String localeID, String displayLocaleID) {
      return getDisplayNameWithDialectInternal(new ULocale(localeID), new ULocale(displayLocaleID));
   }

   public static String getDisplayNameWithDialect(String localeID, ULocale displayLocale) {
      return getDisplayNameWithDialectInternal(new ULocale(localeID), displayLocale);
   }

   private static String getDisplayNameWithDialectInternal(ULocale locale, ULocale displayLocale) {
      return LocaleDisplayNames.getInstance(displayLocale, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(locale);
   }

   public String getCharacterOrientation() {
      return ICUResourceTableAccess.getTableString("org/python/icu/impl/data/icudt59b", this, "layout", "characters", "characters");
   }

   public String getLineOrientation() {
      return ICUResourceTableAccess.getTableString("org/python/icu/impl/data/icudt59b", this, "layout", "lines", "lines");
   }

   public static ULocale acceptLanguage(String acceptLanguageList, ULocale[] availableLocales, boolean[] fallback) {
      if (acceptLanguageList == null) {
         throw new NullPointerException();
      } else {
         ULocale[] acceptList = null;

         try {
            acceptList = parseAcceptLanguage(acceptLanguageList, true);
         } catch (ParseException var5) {
            acceptList = null;
         }

         return acceptList == null ? null : acceptLanguage(acceptList, availableLocales, fallback);
      }
   }

   public static ULocale acceptLanguage(ULocale[] acceptLanguageList, ULocale[] availableLocales, boolean[] fallback) {
      if (fallback != null) {
         fallback[0] = true;
      }

      for(int i = 0; i < acceptLanguageList.length; ++i) {
         ULocale aLocale = acceptLanguageList[i];
         boolean[] setFallback = fallback;

         do {
            for(int j = 0; j < availableLocales.length; ++j) {
               if (availableLocales[j].equals(aLocale)) {
                  if (setFallback != null) {
                     setFallback[0] = false;
                  }

                  return availableLocales[j];
               }

               if (aLocale.getScript().length() == 0 && availableLocales[j].getScript().length() > 0 && availableLocales[j].getLanguage().equals(aLocale.getLanguage()) && availableLocales[j].getCountry().equals(aLocale.getCountry()) && availableLocales[j].getVariant().equals(aLocale.getVariant())) {
                  ULocale minAvail = minimizeSubtags(availableLocales[j]);
                  if (minAvail.getScript().length() == 0) {
                     if (setFallback != null) {
                        setFallback[0] = false;
                     }

                     return aLocale;
                  }
               }
            }

            Locale loc = aLocale.toLocale();
            Locale parent = LocaleUtility.fallback(loc);
            if (parent != null) {
               aLocale = new ULocale(parent);
            } else {
               aLocale = null;
            }

            setFallback = null;
         } while(aLocale != null);
      }

      return null;
   }

   public static ULocale acceptLanguage(String acceptLanguageList, boolean[] fallback) {
      return acceptLanguage(acceptLanguageList, getAvailableLocales(), fallback);
   }

   public static ULocale acceptLanguage(ULocale[] acceptLanguageList, boolean[] fallback) {
      return acceptLanguage(acceptLanguageList, getAvailableLocales(), fallback);
   }

   static ULocale[] parseAcceptLanguage(String acceptLanguage, boolean isLenient) throws ParseException {
      TreeMap map = new TreeMap();
      StringBuilder languageRangeBuf = new StringBuilder();
      StringBuilder qvalBuf = new StringBuilder();
      int state = 0;
      acceptLanguage = acceptLanguage + ",";
      boolean subTag = false;
      boolean q1 = false;

      int n;
      for(n = 0; n < acceptLanguage.length(); ++n) {
         boolean gotLanguageQ = false;
         char c = acceptLanguage.charAt(n);
         switch (state) {
            case 0:
               if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z') {
                  languageRangeBuf.append(c);
                  state = 1;
                  subTag = false;
               } else if (c == '*') {
                  languageRangeBuf.append(c);
                  state = 2;
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
               break;
            case 1:
               if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z') {
                  languageRangeBuf.append(c);
               } else if (c == '-') {
                  subTag = true;
                  languageRangeBuf.append(c);
               } else if (c == '_') {
                  if (isLenient) {
                     subTag = true;
                     languageRangeBuf.append(c);
                  } else {
                     state = -1;
                  }
               } else if ('0' <= c && c <= '9') {
                  if (subTag) {
                     languageRangeBuf.append(c);
                  } else {
                     state = -1;
                  }
               } else if (c == ',') {
                  gotLanguageQ = true;
               } else {
                  if (c != ' ' && c != '\t') {
                     if (c == ';') {
                        state = 4;
                     } else {
                        state = -1;
                     }
                     break;
                  }

                  state = 3;
               }
               break;
            case 2:
               if (c == ',') {
                  gotLanguageQ = true;
               } else {
                  if (c != ' ' && c != '\t') {
                     if (c == ';') {
                        state = 4;
                     } else {
                        state = -1;
                     }
                     break;
                  }

                  state = 3;
               }
               break;
            case 3:
               if (c == ',') {
                  gotLanguageQ = true;
               } else if (c == ';') {
                  state = 4;
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
               break;
            case 4:
               if (c == 'q') {
                  state = 5;
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
               break;
            case 5:
               if (c == '=') {
                  state = 6;
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
               break;
            case 6:
               if (c == '0') {
                  q1 = false;
                  qvalBuf.append(c);
                  state = 7;
               } else if (c == '1') {
                  qvalBuf.append(c);
                  state = 7;
               } else if (c == '.') {
                  if (isLenient) {
                     qvalBuf.append(c);
                     state = 8;
                  } else {
                     state = -1;
                  }
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
               break;
            case 7:
               if (c == '.') {
                  qvalBuf.append(c);
                  state = 8;
               } else if (c == ',') {
                  gotLanguageQ = true;
               } else {
                  if (c != ' ' && c != '\t') {
                     state = -1;
                     break;
                  }

                  state = 10;
               }
               break;
            case 8:
               if ('0' <= c && c <= '9') {
                  if (q1 && c != '0' && !isLenient) {
                     state = -1;
                     break;
                  }

                  qvalBuf.append(c);
                  state = 9;
                  break;
               }

               state = -1;
               break;
            case 9:
               if ('0' <= c && c <= '9') {
                  if (q1 && c != '0') {
                     state = -1;
                     break;
                  }

                  qvalBuf.append(c);
               } else if (c == ',') {
                  gotLanguageQ = true;
               } else {
                  if (c != ' ' && c != '\t') {
                     state = -1;
                     break;
                  }

                  state = 10;
               }
               break;
            case 10:
               if (c == ',') {
                  gotLanguageQ = true;
               } else if (c != ' ' && c != '\t') {
                  state = -1;
               }
         }

         if (state == -1) {
            throw new ParseException("Invalid Accept-Language", n);
         }

         if (gotLanguageQ) {
            double q = 1.0;
            if (qvalBuf.length() != 0) {
               try {
                  q = Double.parseDouble(qvalBuf.toString());
               } catch (NumberFormatException var15) {
                  q = 1.0;
               }

               if (q > 1.0) {
                  q = 1.0;
               }
            }

            if (languageRangeBuf.charAt(0) != '*') {
               int serial = map.size();

               class ULocaleAcceptLanguageQ implements Comparable {
                  private double q;
                  private double serial;

                  public ULocaleAcceptLanguageQ(double theq, int theserial) {
                     this.q = theq;
                     this.serial = (double)theserial;
                  }

                  public int compareTo(ULocaleAcceptLanguageQ other) {
                     if (this.q > other.q) {
                        return -1;
                     } else if (this.q < other.q) {
                        return 1;
                     } else if (this.serial < other.serial) {
                        return -1;
                     } else {
                        return this.serial > other.serial ? 1 : 0;
                     }
                  }
               }

               ULocaleAcceptLanguageQ entry = new ULocaleAcceptLanguageQ(q, serial);
               map.put(entry, new ULocale(canonicalize(languageRangeBuf.toString())));
            }

            languageRangeBuf.setLength(0);
            qvalBuf.setLength(0);
            state = 0;
         }
      }

      if (state != 0) {
         throw new ParseException("Invalid AcceptlLanguage", n);
      } else {
         ULocale[] acceptList = (ULocale[])map.values().toArray(new ULocale[map.size()]);
         return acceptList;
      }
   }

   public static ULocale addLikelySubtags(ULocale loc) {
      String[] tags = new String[3];
      String trailing = null;
      int trailingIndex = parseTagString(loc.localeID, tags);
      if (trailingIndex < loc.localeID.length()) {
         trailing = loc.localeID.substring(trailingIndex);
      }

      String newLocaleID = createLikelySubtagsString(tags[0], tags[1], tags[2], trailing);
      return newLocaleID == null ? loc : new ULocale(newLocaleID);
   }

   public static ULocale minimizeSubtags(ULocale loc) {
      return minimizeSubtags(loc, ULocale.Minimize.FAVOR_REGION);
   }

   /** @deprecated */
   @Deprecated
   public static ULocale minimizeSubtags(ULocale loc, Minimize fieldToFavor) {
      String[] tags = new String[3];
      int trailingIndex = parseTagString(loc.localeID, tags);
      String originalLang = tags[0];
      String originalScript = tags[1];
      String originalRegion = tags[2];
      String originalTrailing = null;
      if (trailingIndex < loc.localeID.length()) {
         originalTrailing = loc.localeID.substring(trailingIndex);
      }

      String maximizedLocaleID = createLikelySubtagsString(originalLang, originalScript, originalRegion, (String)null);
      if (isEmptyString(maximizedLocaleID)) {
         return loc;
      } else {
         String tag = createLikelySubtagsString(originalLang, (String)null, (String)null, (String)null);
         String newLocaleID;
         if (tag.equals(maximizedLocaleID)) {
            newLocaleID = createTagString(originalLang, (String)null, (String)null, originalTrailing);
            return new ULocale(newLocaleID);
         } else {
            if (fieldToFavor == ULocale.Minimize.FAVOR_REGION) {
               if (originalRegion.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, (String)null, originalRegion, (String)null);
                  if (tag.equals(maximizedLocaleID)) {
                     newLocaleID = createTagString(originalLang, (String)null, originalRegion, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }

               if (originalScript.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, originalScript, (String)null, (String)null);
                  if (tag.equals(maximizedLocaleID)) {
                     newLocaleID = createTagString(originalLang, originalScript, (String)null, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }
            } else {
               if (originalScript.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, originalScript, (String)null, (String)null);
                  if (tag.equals(maximizedLocaleID)) {
                     newLocaleID = createTagString(originalLang, originalScript, (String)null, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }

               if (originalRegion.length() != 0) {
                  tag = createLikelySubtagsString(originalLang, (String)null, originalRegion, (String)null);
                  if (tag.equals(maximizedLocaleID)) {
                     newLocaleID = createTagString(originalLang, (String)null, originalRegion, originalTrailing);
                     return new ULocale(newLocaleID);
                  }
               }
            }

            return loc;
         }
      }
   }

   private static boolean isEmptyString(String string) {
      return string == null || string.length() == 0;
   }

   private static void appendTag(String tag, StringBuilder buffer) {
      if (buffer.length() != 0) {
         buffer.append('_');
      }

      buffer.append(tag);
   }

   private static String createTagString(String lang, String script, String region, String trailing, String alternateTags) {
      LocaleIDParser parser = null;
      boolean regionAppended = false;
      StringBuilder tag = new StringBuilder();
      String alternateRegion;
      if (!isEmptyString(lang)) {
         appendTag(lang, tag);
      } else if (isEmptyString(alternateTags)) {
         appendTag("und", tag);
      } else {
         parser = new LocaleIDParser(alternateTags);
         alternateRegion = parser.getLanguage();
         appendTag(!isEmptyString(alternateRegion) ? alternateRegion : "und", tag);
      }

      if (!isEmptyString(script)) {
         appendTag(script, tag);
      } else if (!isEmptyString(alternateTags)) {
         if (parser == null) {
            parser = new LocaleIDParser(alternateTags);
         }

         alternateRegion = parser.getScript();
         if (!isEmptyString(alternateRegion)) {
            appendTag(alternateRegion, tag);
         }
      }

      if (!isEmptyString(region)) {
         appendTag(region, tag);
         regionAppended = true;
      } else if (!isEmptyString(alternateTags)) {
         if (parser == null) {
            parser = new LocaleIDParser(alternateTags);
         }

         alternateRegion = parser.getCountry();
         if (!isEmptyString(alternateRegion)) {
            appendTag(alternateRegion, tag);
            regionAppended = true;
         }
      }

      if (trailing != null && trailing.length() > 1) {
         int separators = 0;
         if (trailing.charAt(0) == '_') {
            if (trailing.charAt(1) == '_') {
               separators = 2;
            }
         } else {
            separators = 1;
         }

         if (regionAppended) {
            if (separators == 2) {
               tag.append(trailing.substring(1));
            } else {
               tag.append(trailing);
            }
         } else {
            if (separators == 1) {
               tag.append('_');
            }

            tag.append(trailing);
         }
      }

      return tag.toString();
   }

   static String createTagString(String lang, String script, String region, String trailing) {
      return createTagString(lang, script, region, trailing, (String)null);
   }

   private static int parseTagString(String localeID, String[] tags) {
      LocaleIDParser parser = new LocaleIDParser(localeID);
      String lang = parser.getLanguage();
      String script = parser.getScript();
      String region = parser.getCountry();
      if (isEmptyString(lang)) {
         tags[0] = "und";
      } else {
         tags[0] = lang;
      }

      if (script.equals("Zzzz")) {
         tags[1] = "";
      } else {
         tags[1] = script;
      }

      if (region.equals("ZZ")) {
         tags[2] = "";
      } else {
         tags[2] = region;
      }

      String variant = parser.getVariant();
      int index;
      if (!isEmptyString(variant)) {
         index = localeID.indexOf(variant);
         return index > 0 ? index - 1 : index;
      } else {
         index = localeID.indexOf(64);
         return index == -1 ? localeID.length() : index;
      }
   }

   private static String lookupLikelySubtags(String localeId) {
      UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "likelySubtags");

      try {
         return bundle.getString(localeId);
      } catch (MissingResourceException var3) {
         return null;
      }
   }

   private static String createLikelySubtagsString(String lang, String script, String region, String variants) {
      String searchTag;
      String likelySubtags;
      if (!isEmptyString(script) && !isEmptyString(region)) {
         searchTag = createTagString(lang, script, region, (String)null);
         likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString((String)null, (String)null, (String)null, variants, likelySubtags);
         }
      }

      if (!isEmptyString(script)) {
         searchTag = createTagString(lang, script, (String)null, (String)null);
         likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString((String)null, (String)null, region, variants, likelySubtags);
         }
      }

      if (!isEmptyString(region)) {
         searchTag = createTagString(lang, (String)null, region, (String)null);
         likelySubtags = lookupLikelySubtags(searchTag);
         if (likelySubtags != null) {
            return createTagString((String)null, script, (String)null, variants, likelySubtags);
         }
      }

      searchTag = createTagString(lang, (String)null, (String)null, (String)null);
      likelySubtags = lookupLikelySubtags(searchTag);
      return likelySubtags != null ? createTagString((String)null, script, region, variants, likelySubtags) : null;
   }

   public String getExtension(char key) {
      if (!LocaleExtensions.isValidKey(key)) {
         throw new IllegalArgumentException("Invalid extension key: " + key);
      } else {
         return this.extensions().getExtensionValue(key);
      }
   }

   public Set getExtensionKeys() {
      return this.extensions().getKeys();
   }

   public Set getUnicodeLocaleAttributes() {
      return this.extensions().getUnicodeLocaleAttributes();
   }

   public String getUnicodeLocaleType(String key) {
      if (!LocaleExtensions.isValidUnicodeLocaleKey(key)) {
         throw new IllegalArgumentException("Invalid Unicode locale key: " + key);
      } else {
         return this.extensions().getUnicodeLocaleType(key);
      }
   }

   public Set getUnicodeLocaleKeys() {
      return this.extensions().getUnicodeLocaleKeys();
   }

   public String toLanguageTag() {
      BaseLocale base = this.base();
      LocaleExtensions exts = this.extensions();
      if (base.getVariant().equalsIgnoreCase("POSIX")) {
         base = BaseLocale.getInstance(base.getLanguage(), base.getScript(), base.getRegion(), "");
         if (exts.getUnicodeLocaleType("va") == null) {
            InternalLocaleBuilder ilocbld = new InternalLocaleBuilder();

            try {
               ilocbld.setLocale(BaseLocale.ROOT, exts);
               ilocbld.setUnicodeLocaleKeyword("va", "posix");
               exts = ilocbld.getLocaleExtensions();
            } catch (LocaleSyntaxException var9) {
               throw new RuntimeException(var9);
            }
         }
      }

      LanguageTag tag = LanguageTag.parseLocale(base, exts);
      StringBuilder buf = new StringBuilder();
      String subtag = tag.getLanguage();
      if (subtag.length() > 0) {
         buf.append(LanguageTag.canonicalizeLanguage(subtag));
      }

      subtag = tag.getScript();
      if (subtag.length() > 0) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeScript(subtag));
      }

      subtag = tag.getRegion();
      if (subtag.length() > 0) {
         buf.append("-");
         buf.append(LanguageTag.canonicalizeRegion(subtag));
      }

      List subtags = tag.getVariants();
      Iterator var7 = subtags.iterator();

      String s;
      while(var7.hasNext()) {
         s = (String)var7.next();
         buf.append("-");
         buf.append(LanguageTag.canonicalizeVariant(s));
      }

      subtags = tag.getExtensions();
      var7 = subtags.iterator();

      while(var7.hasNext()) {
         s = (String)var7.next();
         buf.append("-");
         buf.append(LanguageTag.canonicalizeExtension(s));
      }

      subtag = tag.getPrivateuse();
      if (subtag.length() > 0) {
         if (buf.length() > 0) {
            buf.append("-");
         }

         buf.append("x").append("-");
         buf.append(LanguageTag.canonicalizePrivateuse(subtag));
      }

      return buf.toString();
   }

   public static ULocale forLanguageTag(String languageTag) {
      LanguageTag tag = LanguageTag.parse(languageTag, (ParseStatus)null);
      InternalLocaleBuilder bldr = new InternalLocaleBuilder();
      bldr.setLanguageTag(tag);
      return getInstance(bldr.getBaseLocale(), bldr.getLocaleExtensions());
   }

   public static String toUnicodeLocaleKey(String keyword) {
      String bcpKey = KeyTypeData.toBcpKey(keyword);
      if (bcpKey == null && UnicodeLocaleExtension.isKey(keyword)) {
         bcpKey = AsciiUtil.toLowerString(keyword);
      }

      return bcpKey;
   }

   public static String toUnicodeLocaleType(String keyword, String value) {
      String bcpType = KeyTypeData.toBcpType(keyword, value, (Output)null, (Output)null);
      if (bcpType == null && UnicodeLocaleExtension.isType(value)) {
         bcpType = AsciiUtil.toLowerString(value);
      }

      return bcpType;
   }

   public static String toLegacyKey(String keyword) {
      String legacyKey = KeyTypeData.toLegacyKey(keyword);
      if (legacyKey == null && keyword.matches("[0-9a-zA-Z]+")) {
         legacyKey = AsciiUtil.toLowerString(keyword);
      }

      return legacyKey;
   }

   public static String toLegacyType(String keyword, String value) {
      String legacyType = KeyTypeData.toLegacyType(keyword, value, (Output)null, (Output)null);
      if (legacyType == null && value.matches("[0-9a-zA-Z]+([_/\\-][0-9a-zA-Z]+)*")) {
         legacyType = AsciiUtil.toLowerString(value);
      }

      return legacyType;
   }

   private static ULocale getInstance(BaseLocale base, LocaleExtensions exts) {
      String id = lscvToID(base.getLanguage(), base.getScript(), base.getRegion(), base.getVariant());
      Set extKeys = exts.getKeys();
      if (!extKeys.isEmpty()) {
         TreeMap kwds = new TreeMap();
         Iterator var5 = extKeys.iterator();

         while(true) {
            String attr;
            Set uattributes;
            label74:
            do {
               while(var5.hasNext()) {
                  Character key = (Character)var5.next();
                  Extension ext = exts.getExtension(key);
                  if (ext instanceof UnicodeLocaleExtension) {
                     UnicodeLocaleExtension uext = (UnicodeLocaleExtension)ext;
                     Set ukeys = uext.getUnicodeLocaleKeys();
                     Iterator var10 = ukeys.iterator();

                     while(true) {
                        while(var10.hasNext()) {
                           String bcpKey = (String)var10.next();
                           String bcpType = uext.getUnicodeLocaleType(bcpKey);
                           attr = toLegacyKey(bcpKey);
                           String ltype = toLegacyType(bcpKey, bcpType.length() == 0 ? "yes" : bcpType);
                           if (attr.equals("va") && ltype.equals("posix") && base.getVariant().length() == 0) {
                              id = id + "_POSIX";
                           } else {
                              kwds.put(attr, ltype);
                           }
                        }

                        uattributes = uext.getUnicodeLocaleAttributes();
                        continue label74;
                     }
                  }

                  kwds.put(String.valueOf(key), ext.getValue());
               }

               if (!kwds.isEmpty()) {
                  StringBuilder buf = new StringBuilder(id);
                  buf.append("@");
                  Set kset = kwds.entrySet();
                  boolean insertSep = false;
                  Iterator var18 = kset.iterator();

                  while(var18.hasNext()) {
                     Map.Entry kwd = (Map.Entry)var18.next();
                     if (insertSep) {
                        buf.append(";");
                     } else {
                        insertSep = true;
                     }

                     buf.append((String)kwd.getKey());
                     buf.append("=");
                     buf.append((String)kwd.getValue());
                  }

                  id = buf.toString();
               }

               return new ULocale(id);
            } while(uattributes.size() <= 0);

            StringBuilder attrbuf = new StringBuilder();

            for(Iterator var22 = uattributes.iterator(); var22.hasNext(); attrbuf.append(attr)) {
               attr = (String)var22.next();
               if (attrbuf.length() > 0) {
                  attrbuf.append('-');
               }
            }

            kwds.put("attribute", attrbuf.toString());
         }
      } else {
         return new ULocale(id);
      }
   }

   private BaseLocale base() {
      if (this.baseLocale == null) {
         String variant = "";
         String region = "";
         String script = "";
         String language = "";
         if (!this.equals(ROOT)) {
            LocaleIDParser lp = new LocaleIDParser(this.localeID);
            language = lp.getLanguage();
            script = lp.getScript();
            region = lp.getCountry();
            variant = lp.getVariant();
         }

         this.baseLocale = BaseLocale.getInstance(language, script, region, variant);
      }

      return this.baseLocale;
   }

   private LocaleExtensions extensions() {
      if (this.extensions == null) {
         Iterator kwitr = this.getKeywords();
         if (kwitr == null) {
            this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
         } else {
            InternalLocaleBuilder intbld = new InternalLocaleBuilder();

            while(true) {
               while(kwitr.hasNext()) {
                  String key = (String)kwitr.next();
                  if (key.equals("attribute")) {
                     String[] uattributes = this.getKeywordValue(key).split("[-_]");
                     String[] var14 = uattributes;
                     int var6 = uattributes.length;

                     for(int var7 = 0; var7 < var6; ++var7) {
                        String uattr = var14[var7];

                        try {
                           intbld.addUnicodeLocaleAttribute(uattr);
                        } catch (LocaleSyntaxException var12) {
                        }
                     }
                  } else if (key.length() >= 2) {
                     String bcpKey = toUnicodeLocaleKey(key);
                     String bcpType = toUnicodeLocaleType(key, this.getKeywordValue(key));
                     if (bcpKey != null && bcpType != null) {
                        try {
                           intbld.setUnicodeLocaleKeyword(bcpKey, bcpType);
                        } catch (LocaleSyntaxException var11) {
                        }
                     }
                  } else if (key.length() == 1 && key.charAt(0) != 'u') {
                     try {
                        intbld.setExtension(key.charAt(0), this.getKeywordValue(key).replace("_", "-"));
                     } catch (LocaleSyntaxException var10) {
                     }
                  }
               }

               this.extensions = intbld.getLocaleExtensions();
               break;
            }
         }
      }

      return this.extensions;
   }

   // $FF: synthetic method
   ULocale(String x0, Locale x1, Object x2) {
      this(x0, x1);
   }

   static {
      ENGLISH = new ULocale("en", Locale.ENGLISH);
      FRENCH = new ULocale("fr", Locale.FRENCH);
      GERMAN = new ULocale("de", Locale.GERMAN);
      ITALIAN = new ULocale("it", Locale.ITALIAN);
      JAPANESE = new ULocale("ja", Locale.JAPANESE);
      KOREAN = new ULocale("ko", Locale.KOREAN);
      CHINESE = new ULocale("zh", Locale.CHINESE);
      SIMPLIFIED_CHINESE = new ULocale("zh_Hans");
      TRADITIONAL_CHINESE = new ULocale("zh_Hant");
      FRANCE = new ULocale("fr_FR", Locale.FRANCE);
      GERMANY = new ULocale("de_DE", Locale.GERMANY);
      ITALY = new ULocale("it_IT", Locale.ITALY);
      JAPAN = new ULocale("ja_JP", Locale.JAPAN);
      KOREA = new ULocale("ko_KR", Locale.KOREA);
      CHINA = new ULocale("zh_Hans_CN");
      PRC = CHINA;
      TAIWAN = new ULocale("zh_Hant_TW");
      UK = new ULocale("en_GB", Locale.UK);
      US = new ULocale("en_US", Locale.US);
      CANADA = new ULocale("en_CA", Locale.CANADA);
      CANADA_FRENCH = new ULocale("fr_CA", Locale.CANADA_FRENCH);
      EMPTY_LOCALE = new Locale("", "");
      ROOT = new ULocale("", EMPTY_LOCALE);
      CACHE = new SoftCache() {
         protected ULocale createInstance(Locale key, Void unused) {
            return ULocale.JDKLocaleHelper.toULocale(key);
         }
      };
      CANONICALIZE_MAP = new String[][]{{"C", "en_US_POSIX", null, null}, {"art_LOJBAN", "jbo", null, null}, {"az_AZ_CYRL", "az_Cyrl_AZ", null, null}, {"az_AZ_LATN", "az_Latn_AZ", null, null}, {"ca_ES_PREEURO", "ca_ES", "currency", "ESP"}, {"cel_GAULISH", "cel__GAULISH", null, null}, {"de_1901", "de__1901", null, null}, {"de_1906", "de__1906", null, null}, {"de__PHONEBOOK", "de", "collation", "phonebook"}, {"de_AT_PREEURO", "de_AT", "currency", "ATS"}, {"de_DE_PREEURO", "de_DE", "currency", "DEM"}, {"de_LU_PREEURO", "de_LU", "currency", "EUR"}, {"el_GR_PREEURO", "el_GR", "currency", "GRD"}, {"en_BOONT", "en__BOONT", null, null}, {"en_SCOUSE", "en__SCOUSE", null, null}, {"en_BE_PREEURO", "en_BE", "currency", "BEF"}, {"en_IE_PREEURO", "en_IE", "currency", "IEP"}, {"es__TRADITIONAL", "es", "collation", "traditional"}, {"es_ES_PREEURO", "es_ES", "currency", "ESP"}, {"eu_ES_PREEURO", "eu_ES", "currency", "ESP"}, {"fi_FI_PREEURO", "fi_FI", "currency", "FIM"}, {"fr_BE_PREEURO", "fr_BE", "currency", "BEF"}, {"fr_FR_PREEURO", "fr_FR", "currency", "FRF"}, {"fr_LU_PREEURO", "fr_LU", "currency", "LUF"}, {"ga_IE_PREEURO", "ga_IE", "currency", "IEP"}, {"gl_ES_PREEURO", "gl_ES", "currency", "ESP"}, {"hi__DIRECT", "hi", "collation", "direct"}, {"it_IT_PREEURO", "it_IT", "currency", "ITL"}, {"ja_JP_TRADITIONAL", "ja_JP", "calendar", "japanese"}, {"nl_BE_PREEURO", "nl_BE", "currency", "BEF"}, {"nl_NL_PREEURO", "nl_NL", "currency", "NLG"}, {"pt_PT_PREEURO", "pt_PT", "currency", "PTE"}, {"sl_ROZAJ", "sl__ROZAJ", null, null}, {"sr_SP_CYRL", "sr_Cyrl_RS", null, null}, {"sr_SP_LATN", "sr_Latn_RS", null, null}, {"sr_YU_CYRILLIC", "sr_Cyrl_RS", null, null}, {"th_TH_TRADITIONAL", "th_TH", "calendar", "buddhist"}, {"uz_UZ_CYRILLIC", "uz_Cyrl_UZ", null, null}, {"uz_UZ_CYRL", "uz_Cyrl_UZ", null, null}, {"uz_UZ_LATN", "uz_Latn_UZ", null, null}, {"zh_CHS", "zh_Hans", null, null}, {"zh_CHT", "zh_Hant", null, null}, {"zh_GAN", "zh__GAN", null, null}, {"zh_GUOYU", "zh", null, null}, {"zh_HAKKA", "zh__HAKKA", null, null}, {"zh_MIN", "zh__MIN", null, null}, {"zh_MIN_NAN", "zh__MINNAN", null, null}, {"zh_WUU", "zh__WUU", null, null}, {"zh_XIANG", "zh__XIANG", null, null}, {"zh_YUE", "zh__YUE", null, null}};
      variantsToKeywords = new String[][]{{"EURO", "currency", "EUR"}, {"PINYIN", "collation", "pinyin"}, {"STROKE", "collation", "stroke"}};
      defaultLocale = Locale.getDefault();
      defaultCategoryLocales = new Locale[ULocale.Category.values().length];
      defaultCategoryULocales = new ULocale[ULocale.Category.values().length];
      defaultULocale = forLocale(defaultLocale);
      Category[] var0;
      int var1;
      int var2;
      Category cat;
      int idx;
      if (ULocale.JDKLocaleHelper.hasLocaleCategories()) {
         var0 = ULocale.Category.values();
         var1 = var0.length;

         for(var2 = 0; var2 < var1; ++var2) {
            cat = var0[var2];
            idx = cat.ordinal();
            defaultCategoryLocales[idx] = ULocale.JDKLocaleHelper.getDefault(cat);
            defaultCategoryULocales[idx] = forLocale(defaultCategoryLocales[idx]);
         }
      } else {
         if (ULocale.JDKLocaleHelper.isOriginalDefaultLocale(defaultLocale)) {
            String userScript = ULocale.JDKLocaleHelper.getSystemProperty("user.script");
            if (userScript != null && LanguageTag.isScript(userScript)) {
               BaseLocale base = defaultULocale.base();
               BaseLocale newBase = BaseLocale.getInstance(base.getLanguage(), userScript, base.getRegion(), base.getVariant());
               defaultULocale = getInstance(newBase, defaultULocale.extensions());
            }
         }

         var0 = ULocale.Category.values();
         var1 = var0.length;

         for(var2 = 0; var2 < var1; ++var2) {
            cat = var0[var2];
            idx = cat.ordinal();
            defaultCategoryLocales[idx] = defaultLocale;
            defaultCategoryULocales[idx] = defaultULocale;
         }
      }

      ACTUAL_LOCALE = new Type();
      VALID_LOCALE = new Type();
   }

   private static final class JDKLocaleHelper {
      private static boolean hasScriptsAndUnicodeExtensions = false;
      private static boolean hasLocaleCategories = false;
      private static Method mGetScript;
      private static Method mGetExtensionKeys;
      private static Method mGetExtension;
      private static Method mGetUnicodeLocaleKeys;
      private static Method mGetUnicodeLocaleAttributes;
      private static Method mGetUnicodeLocaleType;
      private static Method mForLanguageTag;
      private static Method mGetDefault;
      private static Method mSetDefault;
      private static Object eDISPLAY;
      private static Object eFORMAT;
      private static final String[][] JAVA6_MAPDATA = new String[][]{{"ja_JP_JP", "ja_JP", "calendar", "japanese", "ja"}, {"no_NO_NY", "nn_NO", null, null, "nn"}, {"th_TH_TH", "th_TH", "numbers", "thai", "th"}};

      public static boolean hasLocaleCategories() {
         return hasLocaleCategories;
      }

      public static ULocale toULocale(Locale loc) {
         return hasScriptsAndUnicodeExtensions ? toULocale7(loc) : toULocale6(loc);
      }

      public static Locale toLocale(ULocale uloc) {
         return hasScriptsAndUnicodeExtensions ? toLocale7(uloc) : toLocale6(uloc);
      }

      private static ULocale toULocale7(Locale loc) {
         String language = loc.getLanguage();
         String script = "";
         String country = loc.getCountry();
         String variant = loc.getVariant();
         Set attributes = null;
         Map keywords = null;

         String attr;
         String kwVal;
         try {
            script = (String)mGetScript.invoke(loc, (Object[])null);
            Set extKeys = (Set)mGetExtensionKeys.invoke(loc, (Object[])null);
            if (!extKeys.isEmpty()) {
               Iterator var8 = extKeys.iterator();

               label138:
               while(true) {
                  while(true) {
                     if (!var8.hasNext()) {
                        break label138;
                     }

                     Character extKey = (Character)var8.next();
                     if (extKey == 'u') {
                        Set uAttributes = (Set)mGetUnicodeLocaleAttributes.invoke(loc, (Object[])null);
                        if (!uAttributes.isEmpty()) {
                           attributes = new TreeSet();
                           Iterator var11 = uAttributes.iterator();

                           while(var11.hasNext()) {
                              kwVal = (String)var11.next();
                              attributes.add(kwVal);
                           }
                        }

                        Set uKeys = (Set)mGetUnicodeLocaleKeys.invoke(loc, (Object[])null);
                        Iterator var25 = uKeys.iterator();

                        while(var25.hasNext()) {
                           String kwKey = (String)var25.next();
                           String kwVal = (String)mGetUnicodeLocaleType.invoke(loc, kwKey);
                           if (kwVal != null) {
                              if (kwKey.equals("va")) {
                                 variant = variant.length() == 0 ? kwVal : kwVal + "_" + variant;
                              } else {
                                 if (keywords == null) {
                                    keywords = new TreeMap();
                                 }

                                 keywords.put(kwKey, kwVal);
                              }
                           }
                        }
                     } else {
                        attr = (String)mGetExtension.invoke(loc, extKey);
                        if (attr != null) {
                           if (keywords == null) {
                              keywords = new TreeMap();
                           }

                           keywords.put(String.valueOf(extKey), attr);
                        }
                     }
                  }
               }
            }
         } catch (IllegalAccessException var15) {
            throw new RuntimeException(var15);
         } catch (InvocationTargetException var16) {
            throw new RuntimeException(var16);
         }

         if (language.equals("no") && country.equals("NO") && variant.equals("NY")) {
            language = "nn";
            variant = "";
         }

         StringBuilder buf = new StringBuilder(language);
         if (script.length() > 0) {
            buf.append('_');
            buf.append(script);
         }

         if (country.length() > 0) {
            buf.append('_');
            buf.append(country);
         }

         if (variant.length() > 0) {
            if (country.length() == 0) {
               buf.append('_');
            }

            buf.append('_');
            buf.append(variant);
         }

         Iterator var20;
         if (attributes != null) {
            StringBuilder attrBuf = new StringBuilder();

            for(var20 = attributes.iterator(); var20.hasNext(); attrBuf.append(attr)) {
               attr = (String)var20.next();
               if (attrBuf.length() != 0) {
                  attrBuf.append('-');
               }
            }

            if (keywords == null) {
               keywords = new TreeMap();
            }

            keywords.put("attribute", attrBuf.toString());
         }

         if (keywords != null) {
            buf.append('@');
            boolean addSep = false;
            var20 = keywords.entrySet().iterator();

            while(var20.hasNext()) {
               Map.Entry kwEntry = (Map.Entry)var20.next();
               String kwKey = (String)kwEntry.getKey();
               kwVal = (String)kwEntry.getValue();
               if (kwKey.length() != 1) {
                  kwKey = ULocale.toLegacyKey(kwKey);
                  kwVal = ULocale.toLegacyType(kwKey, kwVal.length() == 0 ? "yes" : kwVal);
               }

               if (addSep) {
                  buf.append(';');
               } else {
                  addSep = true;
               }

               buf.append(kwKey);
               buf.append('=');
               buf.append(kwVal);
            }
         }

         return new ULocale(ULocale.getName(buf.toString()), loc);
      }

      private static ULocale toULocale6(Locale loc) {
         ULocale uloc = null;
         String locStr = loc.toString();
         if (locStr.length() == 0) {
            uloc = ULocale.ROOT;
         } else {
            for(int i = 0; i < JAVA6_MAPDATA.length; ++i) {
               if (JAVA6_MAPDATA[i][0].equals(locStr)) {
                  LocaleIDParser p = new LocaleIDParser(JAVA6_MAPDATA[i][1]);
                  p.setKeywordValue(JAVA6_MAPDATA[i][2], JAVA6_MAPDATA[i][3]);
                  locStr = p.getName();
                  break;
               }
            }

            uloc = new ULocale(ULocale.getName(locStr), loc);
         }

         return uloc;
      }

      private static Locale toLocale7(ULocale uloc) {
         Locale loc = null;
         String ulocStr = uloc.getName();
         if (uloc.getScript().length() > 0 || ulocStr.contains("@")) {
            String tag = uloc.toLanguageTag();
            tag = AsciiUtil.toUpperString(tag);

            try {
               loc = (Locale)mForLanguageTag.invoke((Object)null, tag);
            } catch (IllegalAccessException var5) {
               throw new RuntimeException(var5);
            } catch (InvocationTargetException var6) {
               throw new RuntimeException(var6);
            }
         }

         if (loc == null) {
            loc = new Locale(uloc.getLanguage(), uloc.getCountry(), uloc.getVariant());
         }

         return loc;
      }

      private static Locale toLocale6(ULocale uloc) {
         String locstr = uloc.getBaseName();

         for(int i = 0; i < JAVA6_MAPDATA.length; ++i) {
            if (locstr.equals(JAVA6_MAPDATA[i][1]) || locstr.equals(JAVA6_MAPDATA[i][4])) {
               if (JAVA6_MAPDATA[i][2] == null) {
                  locstr = JAVA6_MAPDATA[i][0];
                  break;
               }

               String val = uloc.getKeywordValue(JAVA6_MAPDATA[i][2]);
               if (val != null && val.equals(JAVA6_MAPDATA[i][3])) {
                  locstr = JAVA6_MAPDATA[i][0];
                  break;
               }
            }
         }

         LocaleIDParser p = new LocaleIDParser(locstr);
         String[] names = p.getLanguageScriptCountryVariant();
         return new Locale(names[0], names[2], names[3]);
      }

      public static Locale getDefault(Category category) {
         Locale loc = Locale.getDefault();
         if (hasLocaleCategories) {
            Object cat = null;
            switch (category) {
               case DISPLAY:
                  cat = eDISPLAY;
                  break;
               case FORMAT:
                  cat = eFORMAT;
            }

            if (cat != null) {
               try {
                  loc = (Locale)mGetDefault.invoke((Object)null, cat);
               } catch (InvocationTargetException var4) {
               } catch (IllegalArgumentException var5) {
               } catch (IllegalAccessException var6) {
               }
            }
         }

         return loc;
      }

      public static void setDefault(Category category, Locale newLocale) {
         if (hasLocaleCategories) {
            Object cat = null;
            switch (category) {
               case DISPLAY:
                  cat = eDISPLAY;
                  break;
               case FORMAT:
                  cat = eFORMAT;
            }

            if (cat != null) {
               try {
                  mSetDefault.invoke((Object)null, cat, newLocale);
               } catch (InvocationTargetException var4) {
               } catch (IllegalArgumentException var5) {
               } catch (IllegalAccessException var6) {
               }
            }
         }

      }

      public static boolean isOriginalDefaultLocale(Locale loc) {
         if (hasScriptsAndUnicodeExtensions) {
            String script = "";

            try {
               script = (String)mGetScript.invoke(loc, (Object[])null);
            } catch (Exception var3) {
               return false;
            }

            return loc.getLanguage().equals(getSystemProperty("user.language")) && loc.getCountry().equals(getSystemProperty("user.country")) && loc.getVariant().equals(getSystemProperty("user.variant")) && script.equals(getSystemProperty("user.script"));
         } else {
            return loc.getLanguage().equals(getSystemProperty("user.language")) && loc.getCountry().equals(getSystemProperty("user.country")) && loc.getVariant().equals(getSystemProperty("user.variant"));
         }
      }

      public static String getSystemProperty(String key) {
         String val = null;
         final String fkey = key;
         if (System.getSecurityManager() != null) {
            try {
               val = (String)AccessController.doPrivileged(new PrivilegedAction() {
                  public String run() {
                     return System.getProperty(fkey);
                  }
               });
            } catch (AccessControlException var4) {
            }
         } else {
            val = System.getProperty(key);
         }

         return val;
      }

      static {
         try {
            mGetScript = Locale.class.getMethod("getScript", (Class[])null);
            mGetExtensionKeys = Locale.class.getMethod("getExtensionKeys", (Class[])null);
            mGetExtension = Locale.class.getMethod("getExtension", Character.TYPE);
            mGetUnicodeLocaleKeys = Locale.class.getMethod("getUnicodeLocaleKeys", (Class[])null);
            mGetUnicodeLocaleAttributes = Locale.class.getMethod("getUnicodeLocaleAttributes", (Class[])null);
            mGetUnicodeLocaleType = Locale.class.getMethod("getUnicodeLocaleType", String.class);
            mForLanguageTag = Locale.class.getMethod("forLanguageTag", String.class);
            hasScriptsAndUnicodeExtensions = true;
         } catch (NoSuchMethodException var9) {
         } catch (IllegalArgumentException var10) {
         } catch (SecurityException var11) {
         }

         try {
            Class cCategory = null;
            Class[] classes = Locale.class.getDeclaredClasses();
            Class[] var2 = classes;
            int var3 = classes.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Class c = var2[var4];
               if (c.getName().equals("java.util.Locale$Category")) {
                  cCategory = c;
                  break;
               }
            }

            if (cCategory != null) {
               mGetDefault = Locale.class.getDeclaredMethod("getDefault", cCategory);
               mSetDefault = Locale.class.getDeclaredMethod("setDefault", cCategory, Locale.class);
               Method mName = cCategory.getMethod("name", (Class[])null);
               Object[] enumConstants = cCategory.getEnumConstants();
               Object[] var19 = enumConstants;
               int var20 = enumConstants.length;

               for(int var6 = 0; var6 < var20; ++var6) {
                  Object e = var19[var6];
                  String catVal = (String)mName.invoke(e, (Object[])null);
                  if (catVal.equals("DISPLAY")) {
                     eDISPLAY = e;
                  } else if (catVal.equals("FORMAT")) {
                     eFORMAT = e;
                  }
               }

               if (eDISPLAY != null && eFORMAT != null) {
                  hasLocaleCategories = true;
               }
            }
         } catch (NoSuchMethodException var12) {
         } catch (IllegalArgumentException var13) {
         } catch (IllegalAccessException var14) {
         } catch (InvocationTargetException var15) {
         } catch (SecurityException var16) {
         }

      }
   }

   public static final class Builder {
      private final InternalLocaleBuilder _locbld = new InternalLocaleBuilder();

      public Builder setLocale(ULocale locale) {
         try {
            this._locbld.setLocale(locale.base(), locale.extensions());
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder setLanguageTag(String languageTag) {
         ParseStatus sts = new ParseStatus();
         LanguageTag tag = LanguageTag.parse(languageTag, sts);
         if (sts.isError()) {
            throw new IllformedLocaleException(sts.getErrorMessage(), sts.getErrorIndex());
         } else {
            this._locbld.setLanguageTag(tag);
            return this;
         }
      }

      public Builder setLanguage(String language) {
         try {
            this._locbld.setLanguage(language);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder setScript(String script) {
         try {
            this._locbld.setScript(script);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder setRegion(String region) {
         try {
            this._locbld.setRegion(region);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder setVariant(String variant) {
         try {
            this._locbld.setVariant(variant);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder setExtension(char key, String value) {
         try {
            this._locbld.setExtension(key, value);
            return this;
         } catch (LocaleSyntaxException var4) {
            throw new IllformedLocaleException(var4.getMessage(), var4.getErrorIndex());
         }
      }

      public Builder setUnicodeLocaleKeyword(String key, String type) {
         try {
            this._locbld.setUnicodeLocaleKeyword(key, type);
            return this;
         } catch (LocaleSyntaxException var4) {
            throw new IllformedLocaleException(var4.getMessage(), var4.getErrorIndex());
         }
      }

      public Builder addUnicodeLocaleAttribute(String attribute) {
         try {
            this._locbld.addUnicodeLocaleAttribute(attribute);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder removeUnicodeLocaleAttribute(String attribute) {
         try {
            this._locbld.removeUnicodeLocaleAttribute(attribute);
            return this;
         } catch (LocaleSyntaxException var3) {
            throw new IllformedLocaleException(var3.getMessage(), var3.getErrorIndex());
         }
      }

      public Builder clear() {
         this._locbld.clear();
         return this;
      }

      public Builder clearExtensions() {
         this._locbld.clearExtensions();
         return this;
      }

      public ULocale build() {
         return ULocale.getInstance(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
      }
   }

   /** @deprecated */
   @Deprecated
   public static enum Minimize {
      /** @deprecated */
      @Deprecated
      FAVOR_SCRIPT,
      /** @deprecated */
      @Deprecated
      FAVOR_REGION;
   }

   public static final class Type {
      private Type() {
      }

      // $FF: synthetic method
      Type(Object x0) {
         this();
      }
   }

   public static enum Category {
      DISPLAY,
      FORMAT;
   }
}
