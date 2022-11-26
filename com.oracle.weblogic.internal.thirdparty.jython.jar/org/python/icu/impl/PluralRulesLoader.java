package org.python.icu.impl;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import org.python.icu.text.PluralRanges;
import org.python.icu.text.PluralRules;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class PluralRulesLoader extends PluralRules.Factory {
   private final Map rulesIdToRules = new HashMap();
   private Map localeIdToCardinalRulesId;
   private Map localeIdToOrdinalRulesId;
   private Map rulesIdToEquivalentULocale;
   private static Map localeIdToPluralRanges;
   public static final PluralRulesLoader loader = new PluralRulesLoader();
   private static final PluralRanges UNKNOWN_RANGE = (new PluralRanges()).freeze();

   private PluralRulesLoader() {
   }

   public ULocale[] getAvailableULocales() {
      Set keys = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
      ULocale[] locales = new ULocale[keys.size()];
      int n = 0;

      for(Iterator iter = keys.iterator(); iter.hasNext(); locales[n++] = ULocale.createCanonical((String)iter.next())) {
      }

      return locales;
   }

   public ULocale getFunctionalEquivalent(ULocale locale, boolean[] isAvailable) {
      String rulesId;
      if (isAvailable != null && isAvailable.length > 0) {
         rulesId = ULocale.canonicalize(locale.getBaseName());
         Map idMap = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL);
         isAvailable[0] = idMap.containsKey(rulesId);
      }

      rulesId = this.getRulesIdForLocale(locale, PluralRules.PluralType.CARDINAL);
      if (rulesId != null && rulesId.trim().length() != 0) {
         ULocale result = (ULocale)this.getRulesIdToEquivalentULocaleMap().get(rulesId);
         return result == null ? ULocale.ROOT : result;
      } else {
         return ULocale.ROOT;
      }
   }

   private Map getLocaleIdToRulesIdMap(PluralRules.PluralType type) {
      this.checkBuildRulesIdMaps();
      return type == PluralRules.PluralType.CARDINAL ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
   }

   private Map getRulesIdToEquivalentULocaleMap() {
      this.checkBuildRulesIdMaps();
      return this.rulesIdToEquivalentULocale;
   }

   private void checkBuildRulesIdMaps() {
      boolean haveMap;
      synchronized(this) {
         haveMap = this.localeIdToCardinalRulesId != null;
      }

      if (!haveMap) {
         Object tempLocaleIdToCardinalRulesId;
         Object tempLocaleIdToOrdinalRulesId;
         Object tempRulesIdToEquivalentULocale;
         try {
            UResourceBundle pluralb = this.getPluralBundle();
            UResourceBundle localeb = pluralb.get("locales");
            tempLocaleIdToCardinalRulesId = new TreeMap();
            tempRulesIdToEquivalentULocale = new HashMap();

            int i;
            UResourceBundle b;
            String id;
            String value;
            for(i = 0; i < localeb.getSize(); ++i) {
               b = localeb.get(i);
               id = b.getKey();
               value = b.getString().intern();
               ((Map)tempLocaleIdToCardinalRulesId).put(id, value);
               if (!((Map)tempRulesIdToEquivalentULocale).containsKey(value)) {
                  ((Map)tempRulesIdToEquivalentULocale).put(value, new ULocale(id));
               }
            }

            localeb = pluralb.get("locales_ordinals");
            tempLocaleIdToOrdinalRulesId = new TreeMap();

            for(i = 0; i < localeb.getSize(); ++i) {
               b = localeb.get(i);
               id = b.getKey();
               value = b.getString().intern();
               ((Map)tempLocaleIdToOrdinalRulesId).put(id, value);
            }
         } catch (MissingResourceException var14) {
            tempLocaleIdToCardinalRulesId = Collections.emptyMap();
            tempLocaleIdToOrdinalRulesId = Collections.emptyMap();
            tempRulesIdToEquivalentULocale = Collections.emptyMap();
         }

         synchronized(this) {
            if (this.localeIdToCardinalRulesId == null) {
               this.localeIdToCardinalRulesId = (Map)tempLocaleIdToCardinalRulesId;
               this.localeIdToOrdinalRulesId = (Map)tempLocaleIdToOrdinalRulesId;
               this.rulesIdToEquivalentULocale = (Map)tempRulesIdToEquivalentULocale;
            }
         }
      }

   }

   public String getRulesIdForLocale(ULocale locale, PluralRules.PluralType type) {
      Map idMap = this.getLocaleIdToRulesIdMap(type);
      String localeId = ULocale.canonicalize(locale.getBaseName());

      String rulesId;
      int ix;
      for(rulesId = null; null == (rulesId = (String)idMap.get(localeId)); localeId = localeId.substring(0, ix)) {
         ix = localeId.lastIndexOf("_");
         if (ix == -1) {
            break;
         }
      }

      return rulesId;
   }

   public PluralRules getRulesForRulesId(String rulesId) {
      PluralRules rules = null;
      boolean hasRules;
      synchronized(this.rulesIdToRules) {
         hasRules = this.rulesIdToRules.containsKey(rulesId);
         if (hasRules) {
            rules = (PluralRules)this.rulesIdToRules.get(rulesId);
         }
      }

      if (!hasRules) {
         try {
            UResourceBundle pluralb = this.getPluralBundle();
            UResourceBundle rulesb = pluralb.get("rules");
            UResourceBundle setb = rulesb.get(rulesId);
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < setb.getSize(); ++i) {
               UResourceBundle b = setb.get(i);
               if (i > 0) {
                  sb.append("; ");
               }

               sb.append(b.getKey());
               sb.append(": ");
               sb.append(b.getString());
            }

            rules = PluralRules.parseDescription(sb.toString());
         } catch (ParseException var12) {
         } catch (MissingResourceException var13) {
         }

         synchronized(this.rulesIdToRules) {
            if (this.rulesIdToRules.containsKey(rulesId)) {
               rules = (PluralRules)this.rulesIdToRules.get(rulesId);
            } else {
               this.rulesIdToRules.put(rulesId, rules);
            }
         }
      }

      return rules;
   }

   public UResourceBundle getPluralBundle() throws MissingResourceException {
      return ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
   }

   public PluralRules forLocale(ULocale locale, PluralRules.PluralType type) {
      String rulesId = this.getRulesIdForLocale(locale, type);
      if (rulesId != null && rulesId.trim().length() != 0) {
         PluralRules rules = this.getRulesForRulesId(rulesId);
         if (rules == null) {
            rules = PluralRules.DEFAULT;
         }

         return rules;
      } else {
         return PluralRules.DEFAULT;
      }
   }

   public boolean hasOverride(ULocale locale) {
      return false;
   }

   public PluralRanges getPluralRanges(ULocale locale) {
      PluralRanges result;
      int ix;
      for(String localeId = ULocale.canonicalize(locale.getBaseName()); null == (result = (PluralRanges)localeIdToPluralRanges.get(localeId)); localeId = localeId.substring(0, ix)) {
         ix = localeId.lastIndexOf("_");
         if (ix == -1) {
            result = UNKNOWN_RANGE;
            break;
         }
      }

      return result;
   }

   public boolean isPluralRangesAvailable(ULocale locale) {
      return this.getPluralRanges(locale) == UNKNOWN_RANGE;
   }

   static {
      String[][] pluralRangeData = new String[][]{{"locales", "id ja km ko lo ms my th vi zh"}, {"other", "other", "other"}, {"locales", "am bn fr gu hi hy kn mr pa zu"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "other", "other"}, {"locales", "fa"}, {"one", "one", "other"}, {"one", "other", "other"}, {"other", "other", "other"}, {"locales", "ka"}, {"one", "other", "one"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "az de el gl hu it kk ky ml mn ne nl pt sq sw ta te tr ug uz"}, {"one", "other", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "af bg ca en es et eu fi nb sv ur"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "da fil is"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "si"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "mk"}, {"one", "one", "other"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "lv"}, {"zero", "zero", "other"}, {"zero", "one", "one"}, {"zero", "other", "other"}, {"one", "zero", "other"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "zero", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "ro"}, {"one", "few", "few"}, {"one", "other", "other"}, {"few", "one", "few"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "hr sr bs"}, {"one", "one", "one"}, {"one", "few", "few"}, {"one", "other", "other"}, {"few", "one", "one"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "sl"}, {"one", "one", "few"}, {"one", "two", "two"}, {"one", "few", "few"}, {"one", "other", "other"}, {"two", "one", "few"}, {"two", "two", "two"}, {"two", "few", "few"}, {"two", "other", "other"}, {"few", "one", "few"}, {"few", "two", "two"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "one", "few"}, {"other", "two", "two"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "he"}, {"one", "two", "other"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "many", "other"}, {"two", "other", "other"}, {"many", "many", "many"}, {"many", "other", "many"}, {"other", "one", "other"}, {"other", "two", "other"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "cs pl sk"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "one", "one"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "lt ru uk"}, {"one", "one", "one"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"few", "one", "one"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "one", "one"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "cy"}, {"zero", "one", "one"}, {"zero", "two", "two"}, {"zero", "few", "few"}, {"zero", "many", "many"}, {"zero", "other", "other"}, {"one", "two", "two"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "few", "few"}, {"two", "many", "many"}, {"two", "other", "other"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "two", "two"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "ar"}, {"zero", "one", "zero"}, {"zero", "two", "zero"}, {"zero", "few", "few"}, {"zero", "many", "many"}, {"zero", "other", "other"}, {"one", "two", "other"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "few", "few"}, {"two", "many", "many"}, {"two", "other", "other"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "other"}, {"other", "two", "other"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}};
      PluralRanges pr = null;
      String[] locales = null;
      HashMap tempLocaleIdToPluralRanges = new HashMap();
      String[][] var4 = pluralRangeData;
      int var5 = pluralRangeData.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         String[] row = var4[var6];
         if (!row[0].equals("locales")) {
            pr.add(StandardPlural.fromString(row[0]), StandardPlural.fromString(row[1]), StandardPlural.fromString(row[2]));
         } else {
            if (pr != null) {
               pr.freeze();
               String[] var8 = locales;
               int var9 = locales.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  String locale = var8[var10];
                  tempLocaleIdToPluralRanges.put(locale, pr);
               }
            }

            locales = row[1].split(" ");
            pr = new PluralRanges();
         }
      }

      String[] var12 = locales;
      var5 = locales.length;

      for(var6 = 0; var6 < var5; ++var6) {
         String locale = var12[var6];
         tempLocaleIdToPluralRanges.put(locale, pr);
      }

      localeIdToPluralRanges = Collections.unmodifiableMap(tempLocaleIdToPluralRanges);
   }
}
