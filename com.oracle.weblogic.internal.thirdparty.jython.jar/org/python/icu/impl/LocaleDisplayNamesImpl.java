package org.python.icu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.locale.AsciiUtil;
import org.python.icu.lang.UCharacter;
import org.python.icu.lang.UScript;
import org.python.icu.text.BreakIterator;
import org.python.icu.text.DisplayContext;
import org.python.icu.text.LocaleDisplayNames;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class LocaleDisplayNamesImpl extends LocaleDisplayNames {
   private final ULocale locale;
   private final LocaleDisplayNames.DialectHandling dialectHandling;
   private final DisplayContext capitalization;
   private final DisplayContext nameLength;
   private final DisplayContext substituteHandling;
   private final DataTable langData;
   private final DataTable regionData;
   private final String separatorFormat;
   private final String format;
   private final String keyTypeFormat;
   private final char formatOpenParen;
   private final char formatReplaceOpenParen;
   private final char formatCloseParen;
   private final char formatReplaceCloseParen;
   private final CurrencyData.CurrencyDisplayInfo currencyDisplayInfo;
   private static final Cache cache = new Cache();
   private boolean[] capitalizationUsage;
   private static final Map contextUsageTypeMap = new HashMap();
   private transient BreakIterator capitalizationBrkIter;

   public static LocaleDisplayNames getInstance(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
      synchronized(cache) {
         return cache.get(locale, dialectHandling);
      }
   }

   public static LocaleDisplayNames getInstance(ULocale locale, DisplayContext... contexts) {
      synchronized(cache) {
         return cache.get(locale, contexts);
      }
   }

   public LocaleDisplayNamesImpl(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
      this(locale, dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES, DisplayContext.CAPITALIZATION_NONE);
   }

   public LocaleDisplayNamesImpl(ULocale locale, DisplayContext... contexts) {
      this.capitalizationUsage = null;
      this.capitalizationBrkIter = null;
      LocaleDisplayNames.DialectHandling dialectHandling = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
      DisplayContext capitalization = DisplayContext.CAPITALIZATION_NONE;
      DisplayContext nameLength = DisplayContext.LENGTH_FULL;
      DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;
      DisplayContext[] var7 = contexts;
      int var8 = contexts.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         DisplayContext contextItem = var7[var9];
         switch (contextItem.type()) {
            case DIALECT_HANDLING:
               dialectHandling = contextItem.value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
               break;
            case CAPITALIZATION:
               capitalization = contextItem;
               break;
            case DISPLAY_LENGTH:
               nameLength = contextItem;
               break;
            case SUBSTITUTE_HANDLING:
               substituteHandling = contextItem;
         }
      }

      this.dialectHandling = dialectHandling;
      this.capitalization = capitalization;
      this.nameLength = nameLength;
      this.substituteHandling = substituteHandling;
      this.langData = LocaleDisplayNamesImpl.LangDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
      this.regionData = LocaleDisplayNamesImpl.RegionDataTables.impl.get(locale, substituteHandling == DisplayContext.NO_SUBSTITUTE);
      this.locale = ULocale.ROOT.equals(this.langData.getLocale()) ? this.regionData.getLocale() : this.langData.getLocale();
      String sep = this.langData.get("localeDisplayPattern", "separator");
      if (sep == null || "separator".equals(sep)) {
         sep = "{0}, {1}";
      }

      StringBuilder sb = new StringBuilder();
      this.separatorFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(sep, sb, 2, 2);
      String pattern = this.langData.get("localeDisplayPattern", "pattern");
      if (pattern == null || "pattern".equals(pattern)) {
         pattern = "{0} ({1})";
      }

      this.format = SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
      if (pattern.contains("（")) {
         this.formatOpenParen = '（';
         this.formatCloseParen = '）';
         this.formatReplaceOpenParen = '［';
         this.formatReplaceCloseParen = '］';
      } else {
         this.formatOpenParen = '(';
         this.formatCloseParen = ')';
         this.formatReplaceOpenParen = '[';
         this.formatReplaceCloseParen = ']';
      }

      String keyTypePattern = this.langData.get("localeDisplayPattern", "keyTypePattern");
      if (keyTypePattern == null || "keyTypePattern".equals(keyTypePattern)) {
         keyTypePattern = "{0}={1}";
      }

      this.keyTypeFormat = SimpleFormatterImpl.compileToStringMinMaxArguments(keyTypePattern, sb, 2, 2);
      boolean needBrkIter = false;
      if (capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || capitalization == DisplayContext.CAPITALIZATION_FOR_STANDALONE) {
         this.capitalizationUsage = new boolean[LocaleDisplayNamesImpl.CapitalizationContextUsage.values().length];
         ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);
         CapitalizationContextSink sink = new CapitalizationContextSink();

         try {
            rb.getAllItemsWithFallback("contextTransforms", sink);
         } catch (MissingResourceException var15) {
         }

         needBrkIter = sink.hasCapitalizationUsage;
      }

      if (needBrkIter || capitalization == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE) {
         this.capitalizationBrkIter = BreakIterator.getSentenceInstance(locale);
      }

      this.currencyDisplayInfo = CurrencyData.provider.getInstance(locale, false);
   }

   public ULocale getLocale() {
      return this.locale;
   }

   public LocaleDisplayNames.DialectHandling getDialectHandling() {
      return this.dialectHandling;
   }

   public DisplayContext getContext(DisplayContext.Type type) {
      DisplayContext result;
      switch (type) {
         case DIALECT_HANDLING:
            result = this.dialectHandling == LocaleDisplayNames.DialectHandling.STANDARD_NAMES ? DisplayContext.STANDARD_NAMES : DisplayContext.DIALECT_NAMES;
            break;
         case CAPITALIZATION:
            result = this.capitalization;
            break;
         case DISPLAY_LENGTH:
            result = this.nameLength;
            break;
         case SUBSTITUTE_HANDLING:
            result = this.substituteHandling;
            break;
         default:
            result = DisplayContext.STANDARD_NAMES;
      }

      return result;
   }

   private String adjustForUsageAndContext(CapitalizationContextUsage usage, String name) {
      if (name == null || name.length() <= 0 || !UCharacter.isLowerCase(name.codePointAt(0)) || this.capitalization != DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE && (this.capitalizationUsage == null || !this.capitalizationUsage[usage.ordinal()])) {
         return name;
      } else {
         synchronized(this) {
            if (this.capitalizationBrkIter == null) {
               this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }

            return UCharacter.toTitleCase((ULocale)this.locale, name, this.capitalizationBrkIter, 768);
         }
      }
   }

   public String localeDisplayName(ULocale locale) {
      return this.localeDisplayNameInternal(locale);
   }

   public String localeDisplayName(Locale locale) {
      return this.localeDisplayNameInternal(ULocale.forLocale(locale));
   }

   public String localeDisplayName(String localeId) {
      return this.localeDisplayNameInternal(new ULocale(localeId));
   }

   private String localeDisplayNameInternal(ULocale locale) {
      String resultName = null;
      String lang = locale.getLanguage();
      if (locale.getBaseName().length() == 0) {
         lang = "root";
      }

      String script = locale.getScript();
      String country = locale.getCountry();
      String variant = locale.getVariant();
      boolean hasScript = script.length() > 0;
      boolean hasCountry = country.length() > 0;
      boolean hasVariant = variant.length() > 0;
      String langCountry;
      String result;
      if (this.dialectHandling == LocaleDisplayNames.DialectHandling.DIALECT_NAMES) {
         label120: {
            if (hasScript && hasCountry) {
               langCountry = lang + '_' + script + '_' + country;
               result = this.localeIdName(langCountry);
               if (result != null && !result.equals(langCountry)) {
                  resultName = result;
                  hasScript = false;
                  hasCountry = false;
                  break label120;
               }
            }

            if (hasScript) {
               langCountry = lang + '_' + script;
               result = this.localeIdName(langCountry);
               if (result != null && !result.equals(langCountry)) {
                  resultName = result;
                  hasScript = false;
                  break label120;
               }
            }

            if (hasCountry) {
               langCountry = lang + '_' + country;
               result = this.localeIdName(langCountry);
               if (result != null && !result.equals(langCountry)) {
                  resultName = result;
                  hasCountry = false;
               }
            }
         }
      }

      if (resultName == null) {
         langCountry = this.localeIdName(lang);
         if (langCountry == null) {
            return null;
         }

         resultName = langCountry.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
      }

      StringBuilder buf = new StringBuilder();
      if (hasScript) {
         result = this.scriptDisplayNameInContext(script, true);
         if (result == null) {
            return null;
         }

         buf.append(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen));
      }

      if (hasCountry) {
         result = this.regionDisplayName(country, true);
         if (result == null) {
            return null;
         }

         this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
      }

      if (hasVariant) {
         result = this.variantDisplayName(variant, true);
         if (result == null) {
            return null;
         }

         this.appendWithSep(result.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen), buf);
      }

      Iterator keys = locale.getKeywords();
      String key;
      if (keys != null) {
         while(keys.hasNext()) {
            key = (String)keys.next();
            String value = locale.getKeywordValue(key);
            String keyDisplayName = this.keyDisplayName(key, true);
            if (keyDisplayName == null) {
               return null;
            }

            keyDisplayName = keyDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
            String valueDisplayName = this.keyValueDisplayName(key, value, true);
            if (valueDisplayName == null) {
               return null;
            }

            valueDisplayName = valueDisplayName.replace(this.formatOpenParen, this.formatReplaceOpenParen).replace(this.formatCloseParen, this.formatReplaceCloseParen);
            if (!valueDisplayName.equals(value)) {
               this.appendWithSep(valueDisplayName, buf);
            } else if (!key.equals(keyDisplayName)) {
               String keyValue = SimpleFormatterImpl.formatCompiledPattern(this.keyTypeFormat, keyDisplayName, valueDisplayName);
               this.appendWithSep(keyValue, buf);
            } else {
               this.appendWithSep(keyDisplayName, buf).append("=").append(valueDisplayName);
            }
         }
      }

      key = null;
      if (buf.length() > 0) {
         key = buf.toString();
      }

      if (key != null) {
         resultName = SimpleFormatterImpl.formatCompiledPattern(this.format, resultName, key);
      }

      return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, resultName);
   }

   private String localeIdName(String localeId) {
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         String locIdName = this.langData.get("Languages%short", localeId);
         if (locIdName != null && !locIdName.equals(localeId)) {
            return locIdName;
         }
      }

      return this.langData.get("Languages", localeId);
   }

   public String languageDisplayName(String lang) {
      if (!lang.equals("root") && lang.indexOf(95) == -1) {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            String langName = this.langData.get("Languages%short", lang);
            if (langName != null && !langName.equals(lang)) {
               return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, langName);
            }
         }

         return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE, this.langData.get("Languages", lang));
      } else {
         return this.substituteHandling == DisplayContext.SUBSTITUTE ? lang : null;
      }
   }

   public String scriptDisplayName(String script) {
      String str = this.langData.get("Scripts%stand-alone", script);
      if (str == null || str.equals(script)) {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            str = this.langData.get("Scripts%short", script);
            if (str != null && !str.equals(script)) {
               return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, str);
            }
         }

         str = this.langData.get("Scripts", script);
      }

      return this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, str);
   }

   private String scriptDisplayNameInContext(String script, boolean skipAdjust) {
      String scriptName;
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         scriptName = this.langData.get("Scripts%short", script);
         if (scriptName != null && !scriptName.equals(script)) {
            return skipAdjust ? scriptName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, scriptName);
         }
      }

      scriptName = this.langData.get("Scripts", script);
      return skipAdjust ? scriptName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT, scriptName);
   }

   public String scriptDisplayNameInContext(String script) {
      return this.scriptDisplayNameInContext(script, false);
   }

   public String scriptDisplayName(int scriptCode) {
      return this.scriptDisplayName(UScript.getShortName(scriptCode));
   }

   private String regionDisplayName(String region, boolean skipAdjust) {
      String regionName;
      if (this.nameLength == DisplayContext.LENGTH_SHORT) {
         regionName = this.regionData.get("Countries%short", region);
         if (regionName != null && !regionName.equals(region)) {
            return skipAdjust ? regionName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY, regionName);
         }
      }

      regionName = this.regionData.get("Countries", region);
      return skipAdjust ? regionName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY, regionName);
   }

   public String regionDisplayName(String region) {
      return this.regionDisplayName(region, false);
   }

   private String variantDisplayName(String variant, boolean skipAdjust) {
      String variantName = this.langData.get("Variants", variant);
      return skipAdjust ? variantName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.VARIANT, variantName);
   }

   public String variantDisplayName(String variant) {
      return this.variantDisplayName(variant, false);
   }

   private String keyDisplayName(String key, boolean skipAdjust) {
      String keyName = this.langData.get("Keys", key);
      return skipAdjust ? keyName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.KEY, keyName);
   }

   public String keyDisplayName(String key) {
      return this.keyDisplayName(key, false);
   }

   private String keyValueDisplayName(String key, String value, boolean skipAdjust) {
      String keyValueName = null;
      if (key.equals("currency")) {
         keyValueName = this.currencyDisplayInfo.getName(AsciiUtil.toUpperString(value));
         if (keyValueName == null) {
            keyValueName = value;
         }
      } else {
         if (this.nameLength == DisplayContext.LENGTH_SHORT) {
            String tmp = this.langData.get("Types%short", key, value);
            if (tmp != null && !tmp.equals(value)) {
               keyValueName = tmp;
            }
         }

         if (keyValueName == null) {
            keyValueName = this.langData.get("Types", key, value);
         }
      }

      return skipAdjust ? keyValueName : this.adjustForUsageAndContext(LocaleDisplayNamesImpl.CapitalizationContextUsage.KEYVALUE, keyValueName);
   }

   public String keyValueDisplayName(String key, String value) {
      return this.keyValueDisplayName(key, value, false);
   }

   public List getUiListCompareWholeItems(Set localeSet, Comparator comparator) {
      DisplayContext capContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
      List result = new ArrayList();
      Map baseToLocales = new HashMap();
      ULocale.Builder builder = new ULocale.Builder();

      Iterator var7;
      ULocale base;
      Object locales;
      for(var7 = localeSet.iterator(); var7.hasNext(); ((Set)locales).add(base)) {
         ULocale locOriginal = (ULocale)var7.next();
         builder.setLocale(locOriginal);
         base = ULocale.addLikelySubtags(locOriginal);
         ULocale base = new ULocale(base.getLanguage());
         locales = (Set)baseToLocales.get(base);
         if (locales == null) {
            baseToLocales.put(base, locales = new HashSet());
         }
      }

      var7 = baseToLocales.entrySet().iterator();

      while(true) {
         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            base = (ULocale)entry.getKey();
            Set values = (Set)entry.getValue();
            if (values.size() == 1) {
               ULocale locale = (ULocale)values.iterator().next();
               result.add(this.newRow(ULocale.minimizeSubtags(locale, ULocale.Minimize.FAVOR_SCRIPT), capContext));
            } else {
               Set scripts = new HashSet();
               Set regions = new HashSet();
               ULocale maxBase = ULocale.addLikelySubtags(base);
               scripts.add(maxBase.getScript());
               regions.add(maxBase.getCountry());
               Iterator var14 = values.iterator();

               while(var14.hasNext()) {
                  ULocale locale = (ULocale)var14.next();
                  scripts.add(locale.getScript());
                  regions.add(locale.getCountry());
               }

               boolean hasScripts = scripts.size() > 1;
               boolean hasRegions = regions.size() > 1;

               ULocale.Builder modified;
               for(Iterator var16 = values.iterator(); var16.hasNext(); result.add(this.newRow(modified.build(), capContext))) {
                  ULocale locale = (ULocale)var16.next();
                  modified = builder.setLocale(locale);
                  if (!hasScripts) {
                     modified.setScript("");
                  }

                  if (!hasRegions) {
                     modified.setRegion("");
                  }
               }
            }
         }

         Collections.sort(result, comparator);
         return result;
      }
   }

   private LocaleDisplayNames.UiListItem newRow(ULocale modified, DisplayContext capContext) {
      ULocale minimized = ULocale.minimizeSubtags(modified, ULocale.Minimize.FAVOR_SCRIPT);
      String tempName = modified.getDisplayName(this.locale);
      boolean titlecase = capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
      String nameInDisplayLocale = titlecase ? UCharacter.toTitleFirst(this.locale, tempName) : tempName;
      tempName = modified.getDisplayName(modified);
      String nameInSelf = capContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? UCharacter.toTitleFirst(modified, tempName) : tempName;
      return new LocaleDisplayNames.UiListItem(minimized, modified, nameInDisplayLocale, nameInSelf);
   }

   public static boolean haveData(DataTableType type) {
      switch (type) {
         case LANG:
            return LocaleDisplayNamesImpl.LangDataTables.impl instanceof ICUDataTables;
         case REGION:
            return LocaleDisplayNamesImpl.RegionDataTables.impl instanceof ICUDataTables;
         default:
            throw new IllegalArgumentException("unknown type: " + type);
      }
   }

   private StringBuilder appendWithSep(String s, StringBuilder b) {
      if (b.length() == 0) {
         b.append(s);
      } else {
         SimpleFormatterImpl.formatAndReplace(this.separatorFormat, b, (int[])null, b, s);
      }

      return b;
   }

   static {
      contextUsageTypeMap.put("languages", LocaleDisplayNamesImpl.CapitalizationContextUsage.LANGUAGE);
      contextUsageTypeMap.put("script", LocaleDisplayNamesImpl.CapitalizationContextUsage.SCRIPT);
      contextUsageTypeMap.put("territory", LocaleDisplayNamesImpl.CapitalizationContextUsage.TERRITORY);
      contextUsageTypeMap.put("variant", LocaleDisplayNamesImpl.CapitalizationContextUsage.VARIANT);
      contextUsageTypeMap.put("key", LocaleDisplayNamesImpl.CapitalizationContextUsage.KEY);
      contextUsageTypeMap.put("keyValue", LocaleDisplayNamesImpl.CapitalizationContextUsage.KEYVALUE);
   }

   private static class Cache {
      private ULocale locale;
      private LocaleDisplayNames.DialectHandling dialectHandling;
      private DisplayContext capitalization;
      private DisplayContext nameLength;
      private DisplayContext substituteHandling;
      private LocaleDisplayNames cache;

      private Cache() {
      }

      public LocaleDisplayNames get(ULocale locale, LocaleDisplayNames.DialectHandling dialectHandling) {
         if (dialectHandling != this.dialectHandling || DisplayContext.CAPITALIZATION_NONE != this.capitalization || DisplayContext.LENGTH_FULL != this.nameLength || DisplayContext.SUBSTITUTE != this.substituteHandling || !locale.equals(this.locale)) {
            this.locale = locale;
            this.dialectHandling = dialectHandling;
            this.capitalization = DisplayContext.CAPITALIZATION_NONE;
            this.nameLength = DisplayContext.LENGTH_FULL;
            this.substituteHandling = DisplayContext.SUBSTITUTE;
            this.cache = new LocaleDisplayNamesImpl(locale, dialectHandling);
         }

         return this.cache;
      }

      public LocaleDisplayNames get(ULocale locale, DisplayContext... contexts) {
         LocaleDisplayNames.DialectHandling dialectHandlingIn = LocaleDisplayNames.DialectHandling.STANDARD_NAMES;
         DisplayContext capitalizationIn = DisplayContext.CAPITALIZATION_NONE;
         DisplayContext nameLengthIn = DisplayContext.LENGTH_FULL;
         DisplayContext substituteHandling = DisplayContext.SUBSTITUTE;
         DisplayContext[] var7 = contexts;
         int var8 = contexts.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            DisplayContext contextItem = var7[var9];
            switch (contextItem.type()) {
               case DIALECT_HANDLING:
                  dialectHandlingIn = contextItem.value() == DisplayContext.STANDARD_NAMES.value() ? LocaleDisplayNames.DialectHandling.STANDARD_NAMES : LocaleDisplayNames.DialectHandling.DIALECT_NAMES;
                  break;
               case CAPITALIZATION:
                  capitalizationIn = contextItem;
                  break;
               case DISPLAY_LENGTH:
                  nameLengthIn = contextItem;
                  break;
               case SUBSTITUTE_HANDLING:
                  substituteHandling = contextItem;
            }
         }

         if (dialectHandlingIn != this.dialectHandling || capitalizationIn != this.capitalization || nameLengthIn != this.nameLength || substituteHandling != this.substituteHandling || !locale.equals(this.locale)) {
            this.locale = locale;
            this.dialectHandling = dialectHandlingIn;
            this.capitalization = capitalizationIn;
            this.nameLength = nameLengthIn;
            this.substituteHandling = substituteHandling;
            this.cache = new LocaleDisplayNamesImpl(locale, contexts);
         }

         return this.cache;
      }

      // $FF: synthetic method
      Cache(Object x0) {
         this();
      }
   }

   public static enum DataTableType {
      LANG,
      REGION;
   }

   static class RegionDataTables {
      static final DataTables impl = LocaleDisplayNamesImpl.DataTables.load("org.python.icu.impl.ICURegionDataTables");
   }

   static class LangDataTables {
      static final DataTables impl = LocaleDisplayNamesImpl.DataTables.load("org.python.icu.impl.ICULangDataTables");
   }

   abstract static class ICUDataTables extends DataTables {
      private final String path;

      protected ICUDataTables(String path) {
         this.path = path;
      }

      public DataTable get(ULocale locale, boolean nullIfNotFound) {
         return new ICUDataTable(this.path, locale, nullIfNotFound);
      }
   }

   abstract static class DataTables {
      public abstract DataTable get(ULocale var1, boolean var2);

      public static DataTables load(String className) {
         try {
            return (DataTables)Class.forName(className).newInstance();
         } catch (Throwable var2) {
            return new DataTables() {
               public DataTable get(ULocale locale, boolean nullIfNotFound) {
                  return new DataTable(nullIfNotFound);
               }
            };
         }
      }
   }

   static class ICUDataTable extends DataTable {
      private final ICUResourceBundle bundle;

      public ICUDataTable(String path, ULocale locale, boolean nullIfNotFound) {
         super(nullIfNotFound);
         this.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(path, locale.getBaseName());
      }

      public ULocale getLocale() {
         return this.bundle.getULocale();
      }

      public String get(String tableName, String subTableName, String code) {
         return ICUResourceTableAccess.getTableString(this.bundle, tableName, subTableName, code, this.nullIfNotFound ? null : code);
      }
   }

   public static class DataTable {
      final boolean nullIfNotFound;

      DataTable(boolean nullIfNotFound) {
         this.nullIfNotFound = nullIfNotFound;
      }

      ULocale getLocale() {
         return ULocale.ROOT;
      }

      String get(String tableName, String code) {
         return this.get(tableName, (String)null, code);
      }

      String get(String tableName, String subTableName, String code) {
         return this.nullIfNotFound ? null : code;
      }
   }

   private final class CapitalizationContextSink extends UResource.Sink {
      boolean hasCapitalizationUsage;

      private CapitalizationContextSink() {
         this.hasCapitalizationUsage = false;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table contextsTable = value.getTable();

         for(int i = 0; contextsTable.getKeyAndValue(i, key, value); ++i) {
            CapitalizationContextUsage usage = (CapitalizationContextUsage)LocaleDisplayNamesImpl.contextUsageTypeMap.get(key.toString());
            if (usage != null) {
               int[] intVector = value.getIntVector();
               if (intVector.length >= 2) {
                  int titlecaseInt = LocaleDisplayNamesImpl.this.capitalization == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? intVector[0] : intVector[1];
                  if (titlecaseInt != 0) {
                     LocaleDisplayNamesImpl.this.capitalizationUsage[usage.ordinal()] = true;
                     this.hasCapitalizationUsage = true;
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      CapitalizationContextSink(Object x1) {
         this();
      }
   }

   private static enum CapitalizationContextUsage {
      LANGUAGE,
      SCRIPT,
      TERRITORY,
      VARIANT,
      KEY,
      KEYVALUE;
   }
}
