package org.python.icu.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.Relation;
import org.python.icu.impl.Row;
import org.python.icu.impl.Utility;
import org.python.icu.impl.locale.XLocaleDistance;
import org.python.icu.impl.locale.XLocaleMatcher;

public class LocaleMatcher {
   /** @deprecated */
   @Deprecated
   public static final boolean DEBUG = false;
   private static final ULocale UNKNOWN_LOCALE = new ULocale("und");
   private static final double DEFAULT_THRESHOLD = 0.5;
   private final ULocale defaultLanguage;
   private final double threshold;
   Set localeToMaxLocaleAndWeight;
   Map desiredLanguageToPossibleLocalesToMaxLocaleToData;
   LanguageMatcherData matcherData;
   LocalePriorityList languagePriorityList;
   private static final LanguageMatcherData defaultWritten;
   private static HashMap canonicalMap = new HashMap();
   transient XLocaleMatcher xLocaleMatcher;
   transient ULocale xDefaultLanguage;
   transient boolean xFavorScript;

   public LocaleMatcher(LocalePriorityList languagePriorityList) {
      this(languagePriorityList, defaultWritten);
   }

   public LocaleMatcher(String languagePriorityListString) {
      this(LocalePriorityList.add(languagePriorityListString).build());
   }

   /** @deprecated */
   @Deprecated
   public LocaleMatcher(LocalePriorityList languagePriorityList, LanguageMatcherData matcherData) {
      this(languagePriorityList, matcherData, 0.5);
   }

   /** @deprecated */
   @Deprecated
   public LocaleMatcher(LocalePriorityList languagePriorityList, LanguageMatcherData matcherData, double threshold) {
      this.localeToMaxLocaleAndWeight = new LinkedHashSet();
      this.desiredLanguageToPossibleLocalesToMaxLocaleToData = new LinkedHashMap();
      this.xLocaleMatcher = null;
      this.xDefaultLanguage = null;
      this.xFavorScript = false;
      this.matcherData = matcherData == null ? defaultWritten : matcherData.freeze();
      this.languagePriorityList = languagePriorityList;
      Iterator it = languagePriorityList.iterator();

      while(it.hasNext()) {
         ULocale language = (ULocale)it.next();
         this.add(language, languagePriorityList.getWeight(language));
      }

      this.processMapping();
      it = languagePriorityList.iterator();
      this.defaultLanguage = it.hasNext() ? (ULocale)it.next() : null;
      this.threshold = threshold;
   }

   public double match(ULocale desired, ULocale desiredMax, ULocale supported, ULocale supportedMax) {
      return this.matcherData.match(desired, desiredMax, supported, supportedMax);
   }

   public ULocale canonicalize(ULocale ulocale) {
      String lang = ulocale.getLanguage();
      String lang2 = (String)canonicalMap.get(lang);
      String script = ulocale.getScript();
      String script2 = (String)canonicalMap.get(script);
      String region = ulocale.getCountry();
      String region2 = (String)canonicalMap.get(region);
      return lang2 == null && script2 == null && region2 == null ? ulocale : new ULocale(lang2 == null ? lang : lang2, script2 == null ? script : script2, region2 == null ? region : region2);
   }

   public ULocale getBestMatch(LocalePriorityList languageList) {
      double bestWeight = 0.0;
      ULocale bestTableMatch = null;
      double penalty = 0.0;
      OutputDouble matchWeight = new OutputDouble();

      for(Iterator var8 = languageList.iterator(); var8.hasNext(); penalty += 0.07000001) {
         ULocale language = (ULocale)var8.next();
         ULocale matchLocale = this.getBestMatchInternal(language, matchWeight);
         double weight = matchWeight.value * languageList.getWeight(language) - penalty;
         if (weight > bestWeight) {
            bestWeight = weight;
            bestTableMatch = matchLocale;
         }
      }

      if (bestWeight < this.threshold) {
         bestTableMatch = this.defaultLanguage;
      }

      return bestTableMatch;
   }

   public ULocale getBestMatch(String languageList) {
      return this.getBestMatch(LocalePriorityList.add(languageList).build());
   }

   public ULocale getBestMatch(ULocale ulocale) {
      return this.getBestMatchInternal(ulocale, (OutputDouble)null);
   }

   /** @deprecated */
   @Deprecated
   public ULocale getBestMatch(ULocale... ulocales) {
      return this.getBestMatch(LocalePriorityList.add(ulocales).build());
   }

   public String toString() {
      return "{" + this.defaultLanguage + ", " + this.localeToMaxLocaleAndWeight + "}";
   }

   private ULocale getBestMatchInternal(ULocale languageCode, OutputDouble outputWeight) {
      languageCode = this.canonicalize(languageCode);
      ULocale maximized = this.addLikelySubtags(languageCode);
      double bestWeight = 0.0;
      ULocale bestTableMatch = null;
      String baseLanguage = maximized.getLanguage();
      Set searchTable = (Set)this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(baseLanguage);
      if (searchTable != null) {
         Iterator var9 = searchTable.iterator();

         while(var9.hasNext()) {
            Row.R3 tableKeyValue = (Row.R3)var9.next();
            ULocale tableKey = (ULocale)tableKeyValue.get0();
            ULocale maxLocale = (ULocale)tableKeyValue.get1();
            Double matchedWeight = (Double)tableKeyValue.get2();
            double match = this.match(languageCode, maximized, tableKey, maxLocale);
            double weight = match * matchedWeight;
            if (weight > bestWeight) {
               bestWeight = weight;
               bestTableMatch = tableKey;
               if (weight > 0.999) {
                  break;
               }
            }
         }
      }

      if (bestWeight < this.threshold) {
         bestTableMatch = this.defaultLanguage;
      }

      if (outputWeight != null) {
         outputWeight.value = bestWeight;
      }

      return bestTableMatch;
   }

   private void add(ULocale language, Double weight) {
      language = this.canonicalize(language);
      Row.R3 row = Row.of(language, this.addLikelySubtags(language), weight);
      row.freeze();
      this.localeToMaxLocaleAndWeight.add(row);
   }

   private void processMapping() {
      Iterator var1 = this.matcherData.matchingLanguages().keyValuesSet().iterator();

      while(var1.hasNext()) {
         Map.Entry desiredToMatchingLanguages = (Map.Entry)var1.next();
         String desired = (String)desiredToMatchingLanguages.getKey();
         Set supported = (Set)desiredToMatchingLanguages.getValue();
         Iterator var5 = this.localeToMaxLocaleAndWeight.iterator();

         while(var5.hasNext()) {
            Row.R3 localeToMaxAndWeight = (Row.R3)var5.next();
            ULocale key = (ULocale)localeToMaxAndWeight.get0();
            String lang = key.getLanguage();
            if (supported.contains(lang)) {
               this.addFiltered(desired, localeToMaxAndWeight);
            }
         }
      }

      var1 = this.localeToMaxLocaleAndWeight.iterator();

      while(var1.hasNext()) {
         Row.R3 localeToMaxAndWeight = (Row.R3)var1.next();
         ULocale key = (ULocale)localeToMaxAndWeight.get0();
         String lang = key.getLanguage();
         this.addFiltered(lang, localeToMaxAndWeight);
      }

   }

   private void addFiltered(String desired, Row.R3 localeToMaxAndWeight) {
      Set map = (Set)this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(desired);
      if (map == null) {
         this.desiredLanguageToPossibleLocalesToMaxLocaleToData.put(desired, map = new LinkedHashSet());
      }

      ((Set)map).add(localeToMaxAndWeight);
   }

   private ULocale addLikelySubtags(ULocale languageCode) {
      if (languageCode.equals(UNKNOWN_LOCALE)) {
         return UNKNOWN_LOCALE;
      } else {
         ULocale result = ULocale.addLikelySubtags(languageCode);
         if (result != null && !result.equals(languageCode)) {
            return result;
         } else {
            String language = languageCode.getLanguage();
            String script = languageCode.getScript();
            String region = languageCode.getCountry();
            return new ULocale((language.length() == 0 ? "und" : language) + "_" + (script.length() == 0 ? "Zzzz" : script) + "_" + (region.length() == 0 ? "ZZ" : region));
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static ICUResourceBundle getICUSupplementalData() {
      ICUResourceBundle suppData = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
      return suppData;
   }

   /** @deprecated */
   @Deprecated
   public static double match(ULocale a, ULocale b) {
      LocaleMatcher matcher = new LocaleMatcher("");
      return matcher.match(a, matcher.addLikelySubtags(a), b, matcher.addLikelySubtags(b));
   }

   /** @deprecated */
   @Deprecated
   public int distance(ULocale desired, ULocale supported) {
      return this.getLocaleMatcher().distance(desired, supported);
   }

   private synchronized XLocaleMatcher getLocaleMatcher() {
      if (this.xLocaleMatcher == null) {
         XLocaleMatcher.Builder builder = XLocaleMatcher.builder();
         builder.setSupportedLocales(this.languagePriorityList);
         if (this.xDefaultLanguage != null) {
            builder.setDefaultLanguage(this.xDefaultLanguage);
         }

         if (this.xFavorScript) {
            builder.setDistanceOption(XLocaleDistance.DistanceOption.SCRIPT_FIRST);
         }

         this.xLocaleMatcher = builder.build();
      }

      return this.xLocaleMatcher;
   }

   /** @deprecated */
   @Deprecated
   public ULocale getBestMatch(LinkedHashSet desiredLanguages, Output outputBestDesired) {
      return this.getLocaleMatcher().getBestMatch((Set)desiredLanguages, outputBestDesired);
   }

   /** @deprecated */
   @Deprecated
   public synchronized LocaleMatcher setDefaultLanguage(ULocale defaultLanguage) {
      this.xDefaultLanguage = defaultLanguage;
      this.xLocaleMatcher = null;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public synchronized LocaleMatcher setFavorScript(boolean favorScript) {
      this.xFavorScript = favorScript;
      this.xLocaleMatcher = null;
      return this;
   }

   static {
      canonicalMap.put("iw", "he");
      canonicalMap.put("mo", "ro");
      canonicalMap.put("tl", "fil");
      ICUResourceBundle suppData = getICUSupplementalData();
      ICUResourceBundle languageMatching = suppData.findTopLevel("languageMatching");
      ICUResourceBundle written = (ICUResourceBundle)languageMatching.get("written");
      defaultWritten = new LanguageMatcherData();
      UResourceBundleIterator iter = written.getIterator();

      while(iter.hasNext()) {
         ICUResourceBundle item = (ICUResourceBundle)iter.next();
         boolean oneway = item.getSize() > 3 && "1".equals(item.getString(3));
         defaultWritten.addDistance(item.getString(0), item.getString(1), Integer.parseInt(item.getString(2)), oneway);
      }

      defaultWritten.freeze();
   }

   /** @deprecated */
   @Deprecated
   public static class LanguageMatcherData implements Freezable {
      private ScoreData languageScores;
      private ScoreData scriptScores;
      private ScoreData regionScores;
      private Relation matchingLanguages;
      private volatile boolean frozen;

      /** @deprecated */
      @Deprecated
      public LanguageMatcherData() {
         this.languageScores = new ScoreData(LocaleMatcher.Level.language);
         this.scriptScores = new ScoreData(LocaleMatcher.Level.script);
         this.regionScores = new ScoreData(LocaleMatcher.Level.region);
         this.frozen = false;
      }

      /** @deprecated */
      @Deprecated
      public Relation matchingLanguages() {
         return this.matchingLanguages;
      }

      /** @deprecated */
      @Deprecated
      public String toString() {
         return this.languageScores + "\n\t" + this.scriptScores + "\n\t" + this.regionScores;
      }

      /** @deprecated */
      @Deprecated
      public double match(ULocale a, ULocale aMax, ULocale b, ULocale bMax) {
         double diff = 0.0;
         diff += this.languageScores.getScore(aMax, a.getLanguage(), aMax.getLanguage(), bMax, b.getLanguage(), bMax.getLanguage());
         if (diff > 0.999) {
            return 0.0;
         } else {
            diff += this.scriptScores.getScore(aMax, a.getScript(), aMax.getScript(), bMax, b.getScript(), bMax.getScript());
            diff += this.regionScores.getScore(aMax, a.getCountry(), aMax.getCountry(), bMax, b.getCountry(), bMax.getCountry());
            if (!a.getVariant().equals(b.getVariant())) {
               diff += 0.01;
            }

            if (diff < 0.0) {
               diff = 0.0;
            } else if (diff > 1.0) {
               diff = 1.0;
            }

            return 1.0 - diff;
         }
      }

      /** @deprecated */
      @Deprecated
      public LanguageMatcherData addDistance(String desired, String supported, int percent, String comment) {
         return this.addDistance(desired, supported, percent, false, comment);
      }

      /** @deprecated */
      @Deprecated
      public LanguageMatcherData addDistance(String desired, String supported, int percent, boolean oneway) {
         return this.addDistance(desired, supported, percent, oneway, (String)null);
      }

      private LanguageMatcherData addDistance(String desired, String supported, int percent, boolean oneway, String comment) {
         double score = 1.0 - (double)percent / 100.0;
         LocalePatternMatcher desiredMatcher = new LocalePatternMatcher(desired);
         Level desiredLen = desiredMatcher.getLevel();
         LocalePatternMatcher supportedMatcher = new LocalePatternMatcher(supported);
         Level supportedLen = supportedMatcher.getLevel();
         if (desiredLen != supportedLen) {
            throw new IllegalArgumentException("Lengths unequal: " + desired + ", " + supported);
         } else {
            Row.R3 data = Row.of(desiredMatcher, supportedMatcher, score);
            Row.R3 data2 = oneway ? null : Row.of(supportedMatcher, desiredMatcher, score);
            boolean desiredEqualsSupported = desiredMatcher.equals(supportedMatcher);
            switch (desiredLen) {
               case language:
                  String dlanguage = desiredMatcher.getLanguage();
                  String slanguage = supportedMatcher.getLanguage();
                  this.languageScores.addDataToScores(dlanguage, slanguage, data);
                  if (!oneway && !desiredEqualsSupported) {
                     this.languageScores.addDataToScores(slanguage, dlanguage, data2);
                  }
                  break;
               case script:
                  String dscript = desiredMatcher.getScript();
                  String sscript = supportedMatcher.getScript();
                  this.scriptScores.addDataToScores(dscript, sscript, data);
                  if (!oneway && !desiredEqualsSupported) {
                     this.scriptScores.addDataToScores(sscript, dscript, data2);
                  }
                  break;
               case region:
                  String dregion = desiredMatcher.getRegion();
                  String sregion = supportedMatcher.getRegion();
                  this.regionScores.addDataToScores(dregion, sregion, data);
                  if (!oneway && !desiredEqualsSupported) {
                     this.regionScores.addDataToScores(sregion, dregion, data2);
                  }
            }

            return this;
         }
      }

      /** @deprecated */
      @Deprecated
      public LanguageMatcherData cloneAsThawed() {
         try {
            LanguageMatcherData result = (LanguageMatcherData)this.clone();
            result.languageScores = this.languageScores.cloneAsThawed();
            result.scriptScores = this.scriptScores.cloneAsThawed();
            result.regionScores = this.regionScores.cloneAsThawed();
            result.frozen = false;
            return result;
         } catch (CloneNotSupportedException var3) {
            throw new ICUCloneNotSupportedException(var3);
         }
      }

      /** @deprecated */
      @Deprecated
      public LanguageMatcherData freeze() {
         this.languageScores.freeze();
         this.regionScores.freeze();
         this.scriptScores.freeze();
         this.matchingLanguages = this.languageScores.getMatchingLanguages();
         this.frozen = true;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public boolean isFrozen() {
         return this.frozen;
      }
   }

   private static class ScoreData implements Freezable {
      private static final double maxUnequal_changeD_sameS = 0.5;
      private static final double maxUnequal_changeEqual = 0.75;
      LinkedHashSet scores = new LinkedHashSet();
      final Level level;
      private volatile boolean frozen = false;

      public ScoreData(Level level) {
         this.level = level;
      }

      void addDataToScores(String desired, String supported, Row.R3 data) {
         boolean added = this.scores.add(data);
         if (!added) {
            throw new ICUException("trying to add duplicate data: " + data);
         }
      }

      double getScore(ULocale dMax, String desiredRaw, String desiredMax, ULocale sMax, String supportedRaw, String supportedMax) {
         double distance = 0.0;
         if (!desiredMax.equals(supportedMax)) {
            distance = this.getRawScore(dMax, sMax);
         } else if (!desiredRaw.equals(supportedRaw)) {
            distance += 0.001;
         }

         return distance;
      }

      private double getRawScore(ULocale desiredLocale, ULocale supportedLocale) {
         Iterator var3 = this.scores.iterator();

         Row.R3 datum;
         do {
            if (!var3.hasNext()) {
               return this.level.worst;
            }

            datum = (Row.R3)var3.next();
         } while(!((LocalePatternMatcher)datum.get0()).matches(desiredLocale) || !((LocalePatternMatcher)datum.get1()).matches(supportedLocale));

         return (Double)datum.get2();
      }

      public String toString() {
         StringBuilder result = (new StringBuilder()).append(this.level);
         Iterator var2 = this.scores.iterator();

         while(var2.hasNext()) {
            Row.R3 score = (Row.R3)var2.next();
            result.append("\n\t\t").append(score);
         }

         return result.toString();
      }

      public ScoreData cloneAsThawed() {
         try {
            ScoreData result = (ScoreData)this.clone();
            result.scores = (LinkedHashSet)result.scores.clone();
            result.frozen = false;
            return result;
         } catch (CloneNotSupportedException var2) {
            throw new ICUCloneNotSupportedException(var2);
         }
      }

      public ScoreData freeze() {
         return this;
      }

      public boolean isFrozen() {
         return this.frozen;
      }

      public Relation getMatchingLanguages() {
         Relation desiredToSupported = Relation.of(new LinkedHashMap(), HashSet.class);
         Iterator var2 = this.scores.iterator();

         while(var2.hasNext()) {
            Row.R3 item = (Row.R3)var2.next();
            LocalePatternMatcher desired = (LocalePatternMatcher)item.get0();
            LocalePatternMatcher supported = (LocalePatternMatcher)item.get1();
            if (desired.lang != null && supported.lang != null) {
               desiredToSupported.put(desired.lang, supported.lang);
            }
         }

         desiredToSupported.freeze();
         return desiredToSupported;
      }
   }

   static enum Level {
      language(0.99),
      script(0.2),
      region(0.04);

      final double worst;

      private Level(double d) {
         this.worst = d;
      }
   }

   private static class LocalePatternMatcher {
      private String lang;
      private String script;
      private String region;
      private Level level;
      static Pattern pattern = Pattern.compile("([a-z]{1,8}|\\*)(?:[_-]([A-Z][a-z]{3}|\\*))?(?:[_-]([A-Z]{2}|[0-9]{3}|\\*))?");

      public LocalePatternMatcher(String toMatch) {
         Matcher matcher = pattern.matcher(toMatch);
         if (!matcher.matches()) {
            throw new IllegalArgumentException("Bad pattern: " + toMatch);
         } else {
            this.lang = matcher.group(1);
            this.script = matcher.group(2);
            this.region = matcher.group(3);
            this.level = this.region != null ? LocaleMatcher.Level.region : (this.script != null ? LocaleMatcher.Level.script : LocaleMatcher.Level.language);
            if (this.lang.equals("*")) {
               this.lang = null;
            }

            if (this.script != null && this.script.equals("*")) {
               this.script = null;
            }

            if (this.region != null && this.region.equals("*")) {
               this.region = null;
            }

         }
      }

      boolean matches(ULocale ulocale) {
         if (this.lang != null && !this.lang.equals(ulocale.getLanguage())) {
            return false;
         } else if (this.script != null && !this.script.equals(ulocale.getScript())) {
            return false;
         } else {
            return this.region == null || this.region.equals(ulocale.getCountry());
         }
      }

      public Level getLevel() {
         return this.level;
      }

      public String getLanguage() {
         return this.lang == null ? "*" : this.lang;
      }

      public String getScript() {
         return this.script == null ? "*" : this.script;
      }

      public String getRegion() {
         return this.region == null ? "*" : this.region;
      }

      public String toString() {
         String result = this.getLanguage();
         if (this.level != LocaleMatcher.Level.language) {
            result = result + "-" + this.getScript();
            if (this.level != LocaleMatcher.Level.script) {
               result = result + "-" + this.getRegion();
            }
         }

         return result;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (obj != null && obj instanceof LocalePatternMatcher) {
            LocalePatternMatcher other = (LocalePatternMatcher)obj;
            return Utility.objectEquals(this.level, other.level) && Utility.objectEquals(this.lang, other.lang) && Utility.objectEquals(this.script, other.script) && Utility.objectEquals(this.region, other.region);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.level.ordinal() ^ (this.lang == null ? 0 : this.lang.hashCode()) ^ (this.script == null ? 0 : this.script.hashCode()) ^ (this.region == null ? 0 : this.region.hashCode());
      }
   }

   /** @deprecated */
   @Deprecated
   private static class OutputDouble {
      double value;

      private OutputDouble() {
      }

      // $FF: synthetic method
      OutputDouble(Object x0) {
         this();
      }
   }
}
