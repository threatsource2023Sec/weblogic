package org.python.icu.impl.locale;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.Utility;
import org.python.icu.util.ICUException;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class XLikelySubtags {
   private static final XLikelySubtags DEFAULT = new XLikelySubtags();
   final Map langTable;

   public static final XLikelySubtags getDefault() {
      return DEFAULT;
   }

   public XLikelySubtags() {
      this(getDefaultRawData(), true);
   }

   private static Map getDefaultRawData() {
      Map rawData = new TreeMap();
      UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "likelySubtags");
      Enumeration enumer = bundle.getKeys();

      while(enumer.hasMoreElements()) {
         String key = (String)enumer.nextElement();
         rawData.put(key, bundle.getString(key));
      }

      return rawData;
   }

   public XLikelySubtags(Map rawData, boolean skipNoncanonical) {
      this.langTable = this.init(rawData, skipNoncanonical);
   }

   private Map init(Map rawData, boolean skipNoncanonical) {
      Maker maker = XLikelySubtags.Maker.TREEMAP;
      Map result = (Map)maker.make();
      Map internCache = new HashMap();
      Iterator var6 = rawData.entrySet().iterator();

      String lang;
      String script;
      while(var6.hasNext()) {
         Map.Entry sourceTarget = (Map.Entry)var6.next();
         LSR ltp = XLikelySubtags.LSR.from((String)sourceTarget.getKey());
         String language = ltp.language;
         lang = ltp.script;
         String region = ltp.region;
         ltp = XLikelySubtags.LSR.from((String)sourceTarget.getValue());
         String languageTarget = ltp.language;
         String scriptTarget = ltp.script;
         script = ltp.region;
         this.set(result, language, lang, region, languageTarget, scriptTarget, script, internCache);
         Collection languageAliases = XLikelySubtags.LSR.LANGUAGE_ALIASES.getAliases(language);
         Collection regionAliases = XLikelySubtags.LSR.REGION_ALIASES.getAliases(region);
         Iterator var17 = languageAliases.iterator();

         label68:
         while(var17.hasNext()) {
            String languageAlias = (String)var17.next();
            Iterator var19 = regionAliases.iterator();

            while(true) {
               String regionAlias;
               do {
                  if (!var19.hasNext()) {
                     continue label68;
                  }

                  regionAlias = (String)var19.next();
               } while(languageAlias.equals(language) && regionAlias.equals(region));

               this.set(result, languageAlias, lang, regionAlias, languageTarget, scriptTarget, script, internCache);
            }
         }
      }

      this.set(result, "und", "Latn", "", "en", "Latn", "US", internCache);
      Map undScriptMap = (Map)result.get("und");
      Map undEmptyRegionMap = (Map)undScriptMap.get("");
      Iterator var23 = undEmptyRegionMap.entrySet().iterator();

      Map.Entry langEntry;
      while(var23.hasNext()) {
         langEntry = (Map.Entry)var23.next();
         LSR value = (LSR)langEntry.getValue();
         this.set(result, "und", value.script, value.region, value);
      }

      if (!result.containsKey("und")) {
         throw new IllegalArgumentException("failure: base");
      } else {
         var23 = result.entrySet().iterator();

         while(var23.hasNext()) {
            langEntry = (Map.Entry)var23.next();
            lang = (String)langEntry.getKey();
            Map scriptMap = (Map)langEntry.getValue();
            if (!scriptMap.containsKey("")) {
               throw new IllegalArgumentException("failure: " + lang);
            }

            Iterator var27 = scriptMap.entrySet().iterator();

            while(var27.hasNext()) {
               Map.Entry scriptEntry = (Map.Entry)var27.next();
               script = (String)scriptEntry.getKey();
               Map regionMap = (Map)scriptEntry.getValue();
               if (!regionMap.containsKey("")) {
                  throw new IllegalArgumentException("failure: " + lang + "-" + script);
               }
            }
         }

         return result;
      }
   }

   private void set(Map langTable, String language, String script, String region, String languageTarget, String scriptTarget, String regionTarget, Map internCache) {
      LSR newValue = new LSR(languageTarget, scriptTarget, regionTarget);
      LSR oldValue = (LSR)internCache.get(newValue);
      if (oldValue == null) {
         internCache.put(newValue, newValue);
         oldValue = newValue;
      }

      this.set(langTable, language, script, region, oldValue);
   }

   private void set(Map langTable, String language, String script, String region, LSR newValue) {
      Map scriptTable = (Map)XLikelySubtags.Maker.TREEMAP.getSubtable(langTable, language);
      Map regionTable = (Map)XLikelySubtags.Maker.TREEMAP.getSubtable(scriptTable, script);
      regionTable.put(region, newValue);
   }

   public LSR maximize(String source) {
      return this.maximize(ULocale.forLanguageTag(source));
   }

   public LSR maximize(ULocale source) {
      return this.maximize(source.getLanguage(), source.getScript(), source.getCountry());
   }

   public LSR maximize(LSR source) {
      return this.maximize(source.language, source.script, source.region);
   }

   public LSR maximize(String language, String script, String region) {
      int retainOldMask = 0;
      Map scriptTable = (Map)this.langTable.get(language);
      if (scriptTable == null) {
         retainOldMask |= 4;
         scriptTable = (Map)this.langTable.get("und");
      } else if (!language.equals("und")) {
         retainOldMask |= 4;
      }

      if (script.equals("Zzzz")) {
         script = "";
      }

      Map regionTable = (Map)scriptTable.get(script);
      if (regionTable == null) {
         retainOldMask |= 2;
         regionTable = (Map)scriptTable.get("");
      } else if (!script.isEmpty()) {
         retainOldMask |= 2;
      }

      if (region.equals("ZZ")) {
         region = "";
      }

      LSR result = (LSR)regionTable.get(region);
      if (result == null) {
         retainOldMask |= 1;
         result = (LSR)regionTable.get("");
         if (result == null) {
            return null;
         }
      } else if (!region.isEmpty()) {
         retainOldMask |= 1;
      }

      switch (retainOldMask) {
         case 0:
         default:
            return result;
         case 1:
            return result.replace((String)null, (String)null, region);
         case 2:
            return result.replace((String)null, script, (String)null);
         case 3:
            return result.replace((String)null, script, region);
         case 4:
            return result.replace(language, (String)null, (String)null);
         case 5:
            return result.replace(language, (String)null, region);
         case 6:
            return result.replace(language, script, (String)null);
         case 7:
            return result.replace(language, script, region);
      }
   }

   private LSR minimizeSubtags(String languageIn, String scriptIn, String regionIn, ULocale.Minimize fieldToFavor) {
      LSR result = this.maximize(languageIn, scriptIn, regionIn);
      Map scriptTable = (Map)this.langTable.get(result.language);
      Map regionTable0 = (Map)scriptTable.get("");
      LSR value00 = (LSR)regionTable0.get("");
      boolean favorRegionOk = false;
      if (result.script.equals(value00.script)) {
         if (result.region.equals(value00.region)) {
            return result.replace((String)null, "", "");
         }

         if (fieldToFavor == ULocale.Minimize.FAVOR_REGION) {
            return result.replace((String)null, "", (String)null);
         }

         favorRegionOk = true;
      }

      LSR result2 = this.maximize(languageIn, scriptIn, "");
      if (result2.equals(result)) {
         return result.replace((String)null, (String)null, "");
      } else {
         return favorRegionOk ? result.replace((String)null, "", (String)null) : result;
      }
   }

   private static StringBuilder show(Map map, String indent, StringBuilder output) {
      String first = indent.isEmpty() ? "" : "\t";

      for(Iterator var4 = map.entrySet().iterator(); var4.hasNext(); first = indent) {
         Map.Entry e = (Map.Entry)var4.next();
         String key = e.getKey().toString();
         Object value = e.getValue();
         output.append(first + (key.isEmpty() ? "∅" : key));
         if (value instanceof Map) {
            show((Map)value, indent + "\t", output);
         } else {
            output.append("\t" + Utility.toString(value)).append("\n");
         }
      }

      return output;
   }

   public String toString() {
      return show(this.langTable, "", new StringBuilder()).toString();
   }

   public static class LSR {
      public final String language;
      public final String script;
      public final String region;
      public static Aliases LANGUAGE_ALIASES = new Aliases("language");
      public static Aliases REGION_ALIASES = new Aliases("territory");

      public static LSR from(String language, String script, String region) {
         return new LSR(language, script, region);
      }

      static LSR from(String languageIdentifier) {
         String[] parts = languageIdentifier.split("[-_]");
         if (parts.length >= 1 && parts.length <= 3) {
            String lang = parts[0].toLowerCase();
            String p2 = parts.length < 2 ? "" : parts[1];
            String p3 = parts.length < 3 ? "" : parts[2];
            return p2.length() < 4 ? new LSR(lang, "", p2) : new LSR(lang, p2, p3);
         } else {
            throw new ICUException("too many subtags");
         }
      }

      public static LSR from(ULocale locale) {
         return new LSR(locale.getLanguage(), locale.getScript(), locale.getCountry());
      }

      public static LSR fromMaximalized(ULocale locale) {
         return fromMaximalized(locale.getLanguage(), locale.getScript(), locale.getCountry());
      }

      public static LSR fromMaximalized(String language, String script, String region) {
         String canonicalLanguage = LANGUAGE_ALIASES.getCanonical(language);
         String canonicalRegion = REGION_ALIASES.getCanonical(region);
         return XLikelySubtags.DEFAULT.maximize(canonicalLanguage, script, canonicalRegion);
      }

      public LSR(String language, String script, String region) {
         this.language = language;
         this.script = script;
         this.region = region;
      }

      public String toString() {
         StringBuilder result = new StringBuilder(this.language);
         if (!this.script.isEmpty()) {
            result.append('-').append(this.script);
         }

         if (!this.region.isEmpty()) {
            result.append('-').append(this.region);
         }

         return result.toString();
      }

      public LSR replace(String language2, String script2, String region2) {
         return language2 == null && script2 == null && region2 == null ? this : new LSR(language2 == null ? this.language : language2, script2 == null ? this.script : script2, region2 == null ? this.region : region2);
      }

      public boolean equals(Object obj) {
         LSR other;
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.language.equals((other = (LSR)obj).language) && this.script.equals(other.script) && this.region.equals(other.region);
      }

      public int hashCode() {
         return Utility.hash(this.language, this.script, this.region);
      }
   }

   public static class Aliases {
      final Map toCanonical;
      final XCldrStub.Multimap toAliases;

      public String getCanonical(String alias) {
         String canonical = (String)this.toCanonical.get(alias);
         return canonical == null ? alias : canonical;
      }

      public Set getAliases(String canonical) {
         Set aliases = this.toAliases.get(canonical);
         return aliases == null ? Collections.singleton(canonical) : aliases;
      }

      public Aliases(String key) {
         UResourceBundle metadata = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         UResourceBundle metadataAlias = metadata.get("alias");
         UResourceBundle territoryAlias = metadataAlias.get(key);
         Map toCanonical1 = new HashMap();

         for(int i = 0; i < territoryAlias.getSize(); ++i) {
            UResourceBundle res = territoryAlias.get(i);
            String aliasFrom = res.getKey();
            if (!aliasFrom.contains("_")) {
               String aliasReason = res.get("reason").getString();
               if (!aliasReason.equals("overlong")) {
                  String aliasTo = res.get("replacement").getString();
                  int spacePos = aliasTo.indexOf(32);
                  String aliasFirst = spacePos < 0 ? aliasTo : aliasTo.substring(0, spacePos);
                  if (!aliasFirst.contains("_")) {
                     toCanonical1.put(aliasFrom, aliasFirst);
                  }
               }
            }
         }

         if (key.equals("language")) {
            toCanonical1.put("mo", "ro");
         }

         this.toCanonical = Collections.unmodifiableMap(toCanonical1);
         this.toAliases = XCldrStub.Multimaps.invertFrom((Map)toCanonical1, XCldrStub.HashMultimap.create());
      }
   }

   abstract static class Maker {
      static final Maker HASHMAP = new Maker() {
         public Map make() {
            return new HashMap();
         }
      };
      static final Maker TREEMAP = new Maker() {
         public Map make() {
            return new TreeMap();
         }
      };

      abstract Object make();

      public Object getSubtable(Map langTable, Object language) {
         Object scriptTable = langTable.get(language);
         if (scriptTable == null) {
            langTable.put(language, scriptTable = this.make());
         }

         return scriptTable;
      }
   }
}
