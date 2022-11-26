package org.python.icu.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.python.icu.impl.ICUCache;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.PatternTokenizer;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.impl.UResource;
import org.python.icu.util.Calendar;
import org.python.icu.util.Freezable;
import org.python.icu.util.ICUCloneNotSupportedException;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class DateTimePatternGenerator implements Freezable, Cloneable {
   private static final boolean DEBUG = false;
   private static final String[] LAST_RESORT_ALLOWED_HOUR_FORMAT = new String[]{"H"};
   static final Map LOCALE_TO_ALLOWED_HOUR;
   public static final int ERA = 0;
   public static final int YEAR = 1;
   public static final int QUARTER = 2;
   public static final int MONTH = 3;
   public static final int WEEK_OF_YEAR = 4;
   public static final int WEEK_OF_MONTH = 5;
   public static final int WEEKDAY = 6;
   public static final int DAY = 7;
   public static final int DAY_OF_YEAR = 8;
   public static final int DAY_OF_WEEK_IN_MONTH = 9;
   public static final int DAYPERIOD = 10;
   public static final int HOUR = 11;
   public static final int MINUTE = 12;
   public static final int SECOND = 13;
   public static final int FRACTIONAL_SECOND = 14;
   public static final int ZONE = 15;
   /** @deprecated */
   @Deprecated
   public static final int TYPE_LIMIT = 16;
   public static final int MATCH_NO_OPTIONS = 0;
   public static final int MATCH_HOUR_FIELD_LENGTH = 2048;
   /** @deprecated */
   @Deprecated
   public static final int MATCH_MINUTE_FIELD_LENGTH = 4096;
   /** @deprecated */
   @Deprecated
   public static final int MATCH_SECOND_FIELD_LENGTH = 8192;
   public static final int MATCH_ALL_FIELDS_LENGTH = 65535;
   private TreeMap skeleton2pattern = new TreeMap();
   private TreeMap basePattern_pattern = new TreeMap();
   private String decimal = "?";
   private String dateTimeFormat = "{1} {0}";
   private String[] appendItemFormats = new String[16];
   private String[] appendItemNames = new String[16];
   private char defaultHourFormatChar = 'H';
   private volatile boolean frozen = false;
   private transient DateTimeMatcher current = new DateTimeMatcher();
   private transient FormatParser fp = new FormatParser();
   private transient DistanceInfo _distanceInfo = new DistanceInfo();
   private String[] allowedHourFormats;
   private static final int FRACTIONAL_MASK = 16384;
   private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
   private static ICUCache DTPNG_CACHE;
   private static final String[] CLDR_FIELD_APPEND;
   private static final String[] CLDR_FIELD_NAME;
   private static final String[] FIELD_NAME;
   private static final String[] CANONICAL_ITEMS;
   private static final Set CANONICAL_SET;
   private Set cldrAvailableFormatKeys = new HashSet(20);
   private static final int DATE_MASK = 1023;
   private static final int TIME_MASK = 64512;
   private static final int DELTA = 16;
   private static final int NUMERIC = 256;
   private static final int NONE = 0;
   private static final int NARROW = -257;
   private static final int SHORT = -258;
   private static final int LONG = -259;
   private static final int EXTRA_FIELD = 65536;
   private static final int MISSING_FIELD = 4096;
   private static final int[][] types;

   public static DateTimePatternGenerator getEmptyInstance() {
      DateTimePatternGenerator instance = new DateTimePatternGenerator();
      instance.addCanonicalItems();
      instance.fillInMissing();
      return instance;
   }

   protected DateTimePatternGenerator() {
   }

   public static DateTimePatternGenerator getInstance() {
      return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static DateTimePatternGenerator getInstance(ULocale uLocale) {
      return getFrozenInstance(uLocale).cloneAsThawed();
   }

   public static DateTimePatternGenerator getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   /** @deprecated */
   @Deprecated
   public static DateTimePatternGenerator getFrozenInstance(ULocale uLocale) {
      String localeKey = uLocale.toString();
      DateTimePatternGenerator result = (DateTimePatternGenerator)DTPNG_CACHE.get(localeKey);
      if (result != null) {
         return result;
      } else {
         result = new DateTimePatternGenerator();
         result.initData(uLocale);
         result.freeze();
         DTPNG_CACHE.put(localeKey, result);
         return result;
      }
   }

   private void initData(ULocale uLocale) {
      PatternInfo returnInfo = new PatternInfo();
      this.addCanonicalItems();
      this.addICUPatterns(returnInfo, uLocale);
      this.addCLDRData(returnInfo, uLocale);
      this.setDateTimeFromCalendar(uLocale);
      this.setDecimalSymbols(uLocale);
      this.getAllowedHourFormats(uLocale);
      this.fillInMissing();
   }

   private void addICUPatterns(PatternInfo returnInfo, ULocale uLocale) {
      for(int i = 0; i <= 3; ++i) {
         SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance(i, uLocale);
         this.addPattern(df.toPattern(), false, returnInfo);
         df = (SimpleDateFormat)DateFormat.getTimeInstance(i, uLocale);
         this.addPattern(df.toPattern(), false, returnInfo);
         if (i == 3) {
            this.consumeShortTimePattern(df.toPattern(), returnInfo);
         }
      }

   }

   private String getCalendarTypeToUse(ULocale uLocale) {
      String calendarTypeToUse = uLocale.getKeywordValue("calendar");
      if (calendarTypeToUse == null) {
         String[] preferredCalendarTypes = Calendar.getKeywordValuesForLocale("calendar", uLocale, true);
         calendarTypeToUse = preferredCalendarTypes[0];
      }

      if (calendarTypeToUse == null) {
         calendarTypeToUse = "gregorian";
      }

      return calendarTypeToUse;
   }

   private void consumeShortTimePattern(String shortTimePattern, PatternInfo returnInfo) {
      FormatParser fp = new FormatParser();
      fp.set(shortTimePattern);
      List items = fp.getItems();

      for(int idx = 0; idx < items.size(); ++idx) {
         Object item = items.get(idx);
         if (item instanceof VariableField) {
            VariableField fld = (VariableField)item;
            if (fld.getType() == 11) {
               this.defaultHourFormatChar = fld.toString().charAt(0);
               break;
            }
         }
      }

      this.hackTimes(returnInfo, shortTimePattern);
   }

   private void fillInMissing() {
      for(int i = 0; i < 16; ++i) {
         if (this.getAppendItemFormat(i) == null) {
            this.setAppendItemFormat(i, "{0} ├{2}: {1}┤");
         }

         if (this.getAppendItemName(i) == null) {
            this.setAppendItemName(i, "F" + i);
         }
      }

   }

   private void addCLDRData(PatternInfo returnInfo, ULocale uLocale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", uLocale);
      String calendarTypeToUse = this.getCalendarTypeToUse(uLocale);
      AppendItemFormatsSink appendItemFormatsSink = new AppendItemFormatsSink();

      try {
         rb.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/appendItems", appendItemFormatsSink);
      } catch (MissingResourceException var11) {
      }

      AppendItemNamesSink appendItemNamesSink = new AppendItemNamesSink();

      try {
         rb.getAllItemsWithFallback("fields", appendItemNamesSink);
      } catch (MissingResourceException var10) {
      }

      AvailableFormatsSink availableFormatsSink = new AvailableFormatsSink(returnInfo);

      try {
         rb.getAllItemsWithFallback("calendar/" + calendarTypeToUse + "/availableFormats", availableFormatsSink);
      } catch (MissingResourceException var9) {
      }

   }

   private void setDateTimeFromCalendar(ULocale uLocale) {
      String dateTimeFormat = Calendar.getDateTimePattern(Calendar.getInstance(uLocale), uLocale, 2);
      this.setDateTimeFormat(dateTimeFormat);
   }

   private void setDecimalSymbols(ULocale uLocale) {
      DecimalFormatSymbols dfs = new DecimalFormatSymbols(uLocale);
      this.setDecimal(String.valueOf(dfs.getDecimalSeparator()));
   }

   private void getAllowedHourFormats(ULocale uLocale) {
      ULocale max = ULocale.addLikelySubtags(uLocale);
      String country = max.getCountry();
      if (country.isEmpty()) {
         country = "001";
      }

      String langCountry = max.getLanguage() + "_" + country;
      String[] list = (String[])LOCALE_TO_ALLOWED_HOUR.get(langCountry);
      if (list == null) {
         list = (String[])LOCALE_TO_ALLOWED_HOUR.get(country);
         if (list == null) {
            list = LAST_RESORT_ALLOWED_HOUR_FORMAT;
         }
      }

      this.allowedHourFormats = list;
   }

   /** @deprecated */
   @Deprecated
   public char getDefaultHourFormatChar() {
      return this.defaultHourFormatChar;
   }

   /** @deprecated */
   @Deprecated
   public void setDefaultHourFormatChar(char defaultHourFormatChar) {
      this.defaultHourFormatChar = defaultHourFormatChar;
   }

   private void hackTimes(PatternInfo returnInfo, String shortTimePattern) {
      this.fp.set(shortTimePattern);
      StringBuilder mmss = new StringBuilder();
      boolean gotMm = false;

      int i;
      for(int i = 0; i < this.fp.items.size(); ++i) {
         Object item = this.fp.items.get(i);
         if (item instanceof String) {
            if (gotMm) {
               mmss.append(this.fp.quoteLiteral(item.toString()));
            }
         } else {
            i = item.toString().charAt(0);
            if (i == 109) {
               gotMm = true;
               mmss.append(item);
            } else {
               if (i == 115) {
                  if (gotMm) {
                     mmss.append(item);
                     this.addPattern(mmss.toString(), false, returnInfo);
                  }
                  break;
               }

               if (gotMm || i == 122 || i == 90 || i == 118 || i == 86) {
                  break;
               }
            }
         }
      }

      BitSet variables = new BitSet();
      BitSet nuke = new BitSet();

      for(i = 0; i < this.fp.items.size(); ++i) {
         Object item = this.fp.items.get(i);
         if (item instanceof VariableField) {
            variables.set(i);
            char ch = item.toString().charAt(0);
            if (ch == 's' || ch == 'S') {
               nuke.set(i);

               for(int j = i - 1; j >= 0 && !variables.get(j); ++j) {
                  nuke.set(i);
               }
            }
         }
      }

      String hhmm = getFilteredPattern(this.fp, nuke);
      this.addPattern(hhmm, false, returnInfo);
   }

   private static String getFilteredPattern(FormatParser fp, BitSet nuke) {
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < fp.items.size(); ++i) {
         if (!nuke.get(i)) {
            Object item = fp.items.get(i);
            if (item instanceof String) {
               result.append(fp.quoteLiteral(item.toString()));
            } else {
               result.append(item.toString());
            }
         }
      }

      return result.toString();
   }

   /** @deprecated */
   @Deprecated
   public static int getAppendFormatNumber(UResource.Key key) {
      for(int i = 0; i < CLDR_FIELD_APPEND.length; ++i) {
         if (key.contentEquals(CLDR_FIELD_APPEND[i])) {
            return i;
         }
      }

      return -1;
   }

   /** @deprecated */
   @Deprecated
   public static int getAppendFormatNumber(String string) {
      for(int i = 0; i < CLDR_FIELD_APPEND.length; ++i) {
         if (CLDR_FIELD_APPEND[i].equals(string)) {
            return i;
         }
      }

      return -1;
   }

   private static int getCLDRFieldNumber(UResource.Key key) {
      for(int i = 0; i < CLDR_FIELD_NAME.length; ++i) {
         if (key.contentEquals(CLDR_FIELD_NAME[i])) {
            return i;
         }
      }

      return -1;
   }

   public String getBestPattern(String skeleton) {
      return this.getBestPattern(skeleton, (DateTimeMatcher)null, 0);
   }

   public String getBestPattern(String skeleton, int options) {
      return this.getBestPattern(skeleton, (DateTimeMatcher)null, options);
   }

   private String getBestPattern(String skeleton, DateTimeMatcher skipMatcher, int options) {
      EnumSet flags = EnumSet.noneOf(DTPGflags.class);
      StringBuilder skeletonCopy = new StringBuilder(skeleton);
      boolean inQuoted = false;

      for(int patPos = 0; patPos < skeleton.length(); ++patPos) {
         char patChr = skeleton.charAt(patPos);
         if (patChr == '\'') {
            inQuoted = !inQuoted;
         } else if (!inQuoted) {
            if (patChr == 'j') {
               skeletonCopy.setCharAt(patPos, this.defaultHourFormatChar);
            } else if (patChr == 'C') {
               String preferred = this.allowedHourFormats[0];
               skeletonCopy.setCharAt(patPos, preferred.charAt(0));
               DTPGflags alt = DateTimePatternGenerator.DTPGflags.getFlag(preferred);
               if (alt != null) {
                  flags.add(alt);
               }
            } else if (patChr == 'J') {
               skeletonCopy.setCharAt(patPos, 'H');
               flags.add(DateTimePatternGenerator.DTPGflags.SKELETON_USES_CAP_J);
            }
         }
      }

      String datePattern;
      String timePattern;
      synchronized(this) {
         this.current.set(skeletonCopy.toString(), this.fp, false);
         PatternWithMatcher bestWithMatcher = this.getBestRaw(this.current, -1, this._distanceInfo, skipMatcher);
         if (this._distanceInfo.missingFieldMask == 0 && this._distanceInfo.extraFieldMask == 0) {
            return this.adjustFieldTypes(bestWithMatcher, this.current, flags, options);
         }

         int neededFields = this.current.getFieldMask();
         datePattern = this.getBestAppending(this.current, neededFields & 1023, this._distanceInfo, skipMatcher, flags, options);
         timePattern = this.getBestAppending(this.current, neededFields & 'ﰀ', this._distanceInfo, skipMatcher, flags, options);
      }

      if (datePattern == null) {
         return timePattern == null ? "" : timePattern;
      } else {
         return timePattern == null ? datePattern : SimpleFormatterImpl.formatRawPattern(this.getDateTimeFormat(), 2, 2, timePattern, datePattern);
      }
   }

   public DateTimePatternGenerator addPattern(String pattern, boolean override, PatternInfo returnInfo) {
      return this.addPatternWithSkeleton(pattern, (String)null, override, returnInfo);
   }

   /** @deprecated */
   @Deprecated
   public DateTimePatternGenerator addPatternWithSkeleton(String pattern, String skeletonToUse, boolean override, PatternInfo returnInfo) {
      this.checkFrozen();
      DateTimeMatcher matcher;
      if (skeletonToUse == null) {
         matcher = (new DateTimeMatcher()).set(pattern, this.fp, false);
      } else {
         matcher = (new DateTimeMatcher()).set(skeletonToUse, this.fp, false);
      }

      String basePattern = matcher.getBasePattern();
      PatternWithSkeletonFlag previousPatternWithSameBase = (PatternWithSkeletonFlag)this.basePattern_pattern.get(basePattern);
      if (previousPatternWithSameBase != null && (!previousPatternWithSameBase.skeletonWasSpecified || skeletonToUse != null && !override)) {
         returnInfo.status = 1;
         returnInfo.conflictingPattern = previousPatternWithSameBase.pattern;
         if (!override) {
            return this;
         }
      }

      PatternWithSkeletonFlag previousValue = (PatternWithSkeletonFlag)this.skeleton2pattern.get(matcher);
      if (previousValue != null) {
         returnInfo.status = 2;
         returnInfo.conflictingPattern = previousValue.pattern;
         if (!override || skeletonToUse != null && previousValue.skeletonWasSpecified) {
            return this;
         }
      }

      returnInfo.status = 0;
      returnInfo.conflictingPattern = "";
      PatternWithSkeletonFlag patWithSkelFlag = new PatternWithSkeletonFlag(pattern, skeletonToUse != null);
      this.skeleton2pattern.put(matcher, patWithSkelFlag);
      this.basePattern_pattern.put(basePattern, patWithSkelFlag);
      return this;
   }

   public String getSkeleton(String pattern) {
      synchronized(this) {
         this.current.set(pattern, this.fp, false);
         return this.current.toString();
      }
   }

   /** @deprecated */
   @Deprecated
   public String getSkeletonAllowingDuplicates(String pattern) {
      synchronized(this) {
         this.current.set(pattern, this.fp, true);
         return this.current.toString();
      }
   }

   /** @deprecated */
   @Deprecated
   public String getCanonicalSkeletonAllowingDuplicates(String pattern) {
      synchronized(this) {
         this.current.set(pattern, this.fp, true);
         return this.current.toCanonicalString();
      }
   }

   public String getBaseSkeleton(String pattern) {
      synchronized(this) {
         this.current.set(pattern, this.fp, false);
         return this.current.getBasePattern();
      }
   }

   public Map getSkeletons(Map result) {
      if (result == null) {
         result = new LinkedHashMap();
      }

      Iterator var2 = this.skeleton2pattern.keySet().iterator();

      while(var2.hasNext()) {
         DateTimeMatcher item = (DateTimeMatcher)var2.next();
         PatternWithSkeletonFlag patternWithSkelFlag = (PatternWithSkeletonFlag)this.skeleton2pattern.get(item);
         String pattern = patternWithSkelFlag.pattern;
         if (!CANONICAL_SET.contains(pattern)) {
            ((Map)result).put(item.toString(), pattern);
         }
      }

      return (Map)result;
   }

   public Set getBaseSkeletons(Set result) {
      if (result == null) {
         result = new HashSet();
      }

      ((Set)result).addAll(this.basePattern_pattern.keySet());
      return (Set)result;
   }

   public String replaceFieldTypes(String pattern, String skeleton) {
      return this.replaceFieldTypes(pattern, skeleton, 0);
   }

   public String replaceFieldTypes(String pattern, String skeleton, int options) {
      synchronized(this) {
         PatternWithMatcher patternNoMatcher = new PatternWithMatcher(pattern, (DateTimeMatcher)null);
         return this.adjustFieldTypes(patternNoMatcher, this.current.set(skeleton, this.fp, false), EnumSet.noneOf(DTPGflags.class), options);
      }
   }

   public void setDateTimeFormat(String dateTimeFormat) {
      this.checkFrozen();
      this.dateTimeFormat = dateTimeFormat;
   }

   public String getDateTimeFormat() {
      return this.dateTimeFormat;
   }

   public void setDecimal(String decimal) {
      this.checkFrozen();
      this.decimal = decimal;
   }

   public String getDecimal() {
      return this.decimal;
   }

   /** @deprecated */
   @Deprecated
   public Collection getRedundants(Collection output) {
      synchronized(this) {
         if (output == null) {
            output = new LinkedHashSet();
         }

         Iterator var3 = this.skeleton2pattern.keySet().iterator();

         while(var3.hasNext()) {
            DateTimeMatcher cur = (DateTimeMatcher)var3.next();
            PatternWithSkeletonFlag patternWithSkelFlag = (PatternWithSkeletonFlag)this.skeleton2pattern.get(cur);
            String pattern = patternWithSkelFlag.pattern;
            if (!CANONICAL_SET.contains(pattern)) {
               String trial = this.getBestPattern(cur.toString(), cur, 0);
               if (trial.equals(pattern)) {
                  ((Collection)output).add(pattern);
               }
            }
         }

         return (Collection)output;
      }
   }

   public void setAppendItemFormat(int field, String value) {
      this.checkFrozen();
      this.appendItemFormats[field] = value;
   }

   public String getAppendItemFormat(int field) {
      return this.appendItemFormats[field];
   }

   public void setAppendItemName(int field, String value) {
      this.checkFrozen();
      this.appendItemNames[field] = value;
   }

   public String getAppendItemName(int field) {
      return this.appendItemNames[field];
   }

   /** @deprecated */
   @Deprecated
   public static boolean isSingleField(String skeleton) {
      char first = skeleton.charAt(0);

      for(int i = 1; i < skeleton.length(); ++i) {
         if (skeleton.charAt(i) != first) {
            return false;
         }
      }

      return true;
   }

   private void setAvailableFormat(String key) {
      this.checkFrozen();
      this.cldrAvailableFormatKeys.add(key);
   }

   private boolean isAvailableFormatSet(String key) {
      return this.cldrAvailableFormatKeys.contains(key);
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public DateTimePatternGenerator freeze() {
      this.frozen = true;
      return this;
   }

   public DateTimePatternGenerator cloneAsThawed() {
      DateTimePatternGenerator result = (DateTimePatternGenerator)((DateTimePatternGenerator)this.clone());
      this.frozen = false;
      return result;
   }

   public Object clone() {
      try {
         DateTimePatternGenerator result = (DateTimePatternGenerator)((DateTimePatternGenerator)super.clone());
         result.skeleton2pattern = (TreeMap)this.skeleton2pattern.clone();
         result.basePattern_pattern = (TreeMap)this.basePattern_pattern.clone();
         result.appendItemFormats = (String[])this.appendItemFormats.clone();
         result.appendItemNames = (String[])this.appendItemNames.clone();
         result.current = new DateTimeMatcher();
         result.fp = new FormatParser();
         result._distanceInfo = new DistanceInfo();
         result.frozen = false;
         return result;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException("Internal Error", var2);
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean skeletonsAreSimilar(String id, String skeleton) {
      if (id.equals(skeleton)) {
         return true;
      } else {
         TreeSet parser1 = this.getSet(id);
         TreeSet parser2 = this.getSet(skeleton);
         if (parser1.size() != parser2.size()) {
            return false;
         } else {
            Iterator it2 = parser2.iterator();
            Iterator var6 = parser1.iterator();

            int index1;
            int index2;
            do {
               if (!var6.hasNext()) {
                  return true;
               }

               String item = (String)var6.next();
               index1 = getCanonicalIndex(item, false);
               String item2 = (String)it2.next();
               index2 = getCanonicalIndex(item2, false);
            } while(types[index1][1] == types[index2][1]);

            return false;
         }
      }
   }

   private TreeSet getSet(String id) {
      List items = this.fp.set(id).getItems();
      TreeSet result = new TreeSet();
      Iterator var4 = items.iterator();

      while(var4.hasNext()) {
         Object obj = var4.next();
         String item = obj.toString();
         if (!item.startsWith("G") && !item.startsWith("a")) {
            result.add(item);
         }
      }

      return result;
   }

   private void checkFrozen() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      }
   }

   private String getBestAppending(DateTimeMatcher source, int missingFields, DistanceInfo distInfo, DateTimeMatcher skipMatcher, EnumSet flags, int options) {
      String resultPattern = null;
      if (missingFields != 0) {
         PatternWithMatcher resultPatternWithMatcher = this.getBestRaw(source, missingFields, distInfo, skipMatcher);
         resultPattern = this.adjustFieldTypes(resultPatternWithMatcher, source, flags, options);

         while(true) {
            while(distInfo.missingFieldMask != 0) {
               if ((distInfo.missingFieldMask & 24576) == 16384 && (missingFields & 24576) == 24576) {
                  resultPatternWithMatcher.pattern = resultPattern;
                  flags = EnumSet.copyOf(flags);
                  flags.add(DateTimePatternGenerator.DTPGflags.FIX_FRACTIONAL_SECONDS);
                  resultPattern = this.adjustFieldTypes(resultPatternWithMatcher, source, flags, options);
                  distInfo.missingFieldMask &= -16385;
               } else {
                  int startingMask = distInfo.missingFieldMask;
                  PatternWithMatcher tempWithMatcher = this.getBestRaw(source, distInfo.missingFieldMask, distInfo, skipMatcher);
                  String temp = this.adjustFieldTypes(tempWithMatcher, source, flags, options);
                  int foundMask = startingMask & ~distInfo.missingFieldMask;
                  int topField = this.getTopBitNumber(foundMask);
                  resultPattern = SimpleFormatterImpl.formatRawPattern(this.getAppendFormat(topField), 2, 3, resultPattern, temp, this.getAppendName(topField));
               }
            }

            return resultPattern;
         }
      } else {
         return resultPattern;
      }
   }

   private String getAppendName(int foundMask) {
      return "'" + this.appendItemNames[foundMask] + "'";
   }

   private String getAppendFormat(int foundMask) {
      return this.appendItemFormats[foundMask];
   }

   private int getTopBitNumber(int foundMask) {
      int i;
      for(i = 0; foundMask != 0; ++i) {
         foundMask >>>= 1;
      }

      return i - 1;
   }

   private void addCanonicalItems() {
      PatternInfo patternInfo = new PatternInfo();

      for(int i = 0; i < CANONICAL_ITEMS.length; ++i) {
         this.addPattern(String.valueOf(CANONICAL_ITEMS[i]), false, patternInfo);
      }

   }

   private PatternWithMatcher getBestRaw(DateTimeMatcher source, int includeMask, DistanceInfo missingFields, DateTimeMatcher skipMatcher) {
      int bestDistance = Integer.MAX_VALUE;
      PatternWithMatcher bestPatternWithMatcher = new PatternWithMatcher("", (DateTimeMatcher)null);
      DistanceInfo tempInfo = new DistanceInfo();
      Iterator var8 = this.skeleton2pattern.keySet().iterator();

      while(var8.hasNext()) {
         DateTimeMatcher trial = (DateTimeMatcher)var8.next();
         if (!trial.equals(skipMatcher)) {
            int distance = source.getDistance(trial, includeMask, tempInfo);
            if (distance < bestDistance) {
               bestDistance = distance;
               PatternWithSkeletonFlag patternWithSkelFlag = (PatternWithSkeletonFlag)this.skeleton2pattern.get(trial);
               bestPatternWithMatcher.pattern = patternWithSkelFlag.pattern;
               if (patternWithSkelFlag.skeletonWasSpecified) {
                  bestPatternWithMatcher.matcherWithSkeleton = trial;
               } else {
                  bestPatternWithMatcher.matcherWithSkeleton = null;
               }

               missingFields.setTo(tempInfo);
               if (distance == 0) {
                  break;
               }
            }
         }
      }

      return bestPatternWithMatcher;
   }

   private String adjustFieldTypes(PatternWithMatcher patternWithMatcher, DateTimeMatcher inputRequest, EnumSet flags, int options) {
      this.fp.set(patternWithMatcher.pattern);
      StringBuilder newPattern = new StringBuilder();
      Iterator var6 = this.fp.getItems().iterator();

      while(true) {
         while(var6.hasNext()) {
            Object item = var6.next();
            if (item instanceof String) {
               newPattern.append(this.fp.quoteLiteral((String)item));
            } else {
               VariableField variableField = (VariableField)item;
               StringBuilder fieldBuilder = new StringBuilder(variableField.toString());
               int type = variableField.getType();
               int reqFieldLen;
               int adjFieldLen;
               if (type == 10 && !flags.isEmpty()) {
                  char c = flags.contains(DateTimePatternGenerator.DTPGflags.SKELETON_USES_b) ? 98 : (flags.contains(DateTimePatternGenerator.DTPGflags.SKELETON_USES_B) ? 66 : 48);
                  if (c != 48) {
                     reqFieldLen = fieldBuilder.length();
                     fieldBuilder.setLength(0);

                     for(adjFieldLen = reqFieldLen; adjFieldLen > 0; --adjFieldLen) {
                        fieldBuilder.append((char)c);
                     }
                  }
               }

               if (flags.contains(DateTimePatternGenerator.DTPGflags.FIX_FRACTIONAL_SECONDS) && type == 13) {
                  fieldBuilder.append(this.decimal);
                  inputRequest.original.appendFieldTo(14, fieldBuilder);
               } else if (inputRequest.type[type] != 0) {
                  char reqFieldChar = inputRequest.original.getFieldChar(type);
                  reqFieldLen = inputRequest.original.getFieldLength(type);
                  if (reqFieldChar == 'E' && reqFieldLen < 3) {
                     reqFieldLen = 3;
                  }

                  adjFieldLen = reqFieldLen;
                  DateTimeMatcher matcherWithSkeleton = patternWithMatcher.matcherWithSkeleton;
                  if ((type != 11 || (options & 2048) != 0) && (type != 12 || (options & 4096) != 0) && (type != 13 || (options & 8192) != 0)) {
                     if (matcherWithSkeleton != null) {
                        int skelFieldLen = matcherWithSkeleton.original.getFieldLength(type);
                        boolean patFieldIsNumeric = variableField.isNumeric();
                        boolean skelFieldIsNumeric = matcherWithSkeleton.fieldIsNumeric(type);
                        if (skelFieldLen == reqFieldLen || patFieldIsNumeric && !skelFieldIsNumeric || skelFieldIsNumeric && !patFieldIsNumeric) {
                           adjFieldLen = fieldBuilder.length();
                        }
                     }
                  } else {
                     adjFieldLen = fieldBuilder.length();
                  }

                  char c = type == 11 || type == 3 || type == 6 || type == 1 && reqFieldChar != 'Y' ? fieldBuilder.charAt(0) : reqFieldChar;
                  if (type == 11 && flags.contains(DateTimePatternGenerator.DTPGflags.SKELETON_USES_CAP_J)) {
                     c = this.defaultHourFormatChar;
                  }

                  fieldBuilder = new StringBuilder();

                  for(int i = adjFieldLen; i > 0; --i) {
                     fieldBuilder.append(c);
                  }
               }

               newPattern.append(fieldBuilder);
            }
         }

         return newPattern.toString();
      }
   }

   /** @deprecated */
   @Deprecated
   public String getFields(String pattern) {
      this.fp.set(pattern);
      StringBuilder newPattern = new StringBuilder();
      Iterator var3 = this.fp.getItems().iterator();

      while(var3.hasNext()) {
         Object item = var3.next();
         if (item instanceof String) {
            newPattern.append(this.fp.quoteLiteral((String)item));
         } else {
            newPattern.append("{" + getName(item.toString()) + "}");
         }
      }

      return newPattern.toString();
   }

   private static String showMask(int mask) {
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < 16; ++i) {
         if ((mask & 1 << i) != 0) {
            if (result.length() != 0) {
               result.append(" | ");
            }

            result.append(FIELD_NAME[i]);
            result.append(" ");
         }
      }

      return result.toString();
   }

   private static String getName(String s) {
      int i = getCanonicalIndex(s, true);
      String name = FIELD_NAME[types[i][1]];
      if (types[i][2] < 0) {
         name = name + ":S";
      } else {
         name = name + ":N";
      }

      return name;
   }

   private static int getCanonicalIndex(String s, boolean strict) {
      int len = s.length();
      if (len == 0) {
         return -1;
      } else {
         int ch = s.charAt(0);

         int bestRow;
         for(bestRow = 1; bestRow < len; ++bestRow) {
            if (s.charAt(bestRow) != ch) {
               return -1;
            }
         }

         bestRow = -1;

         for(int i = 0; i < types.length; ++i) {
            int[] row = types[i];
            if (row[0] == ch) {
               bestRow = i;
               if (row[3] <= len && row[row.length - 1] >= len) {
                  return i;
               }
            }
         }

         return strict ? -1 : bestRow;
      }
   }

   private static char getCanonicalChar(int field, char reference) {
      if (reference != 'h' && reference != 'K') {
         for(int i = 0; i < types.length; ++i) {
            int[] row = types[i];
            if (row[1] == field) {
               return (char)row[0];
            }
         }

         throw new IllegalArgumentException("Could not find field " + field);
      } else {
         return 'h';
      }
   }

   static {
      HashMap temp = new HashMap();
      ICUResourceBundle suppData = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
      DayPeriodAllowedHoursSink allowedHoursSink = new DayPeriodAllowedHoursSink(temp);
      suppData.getAllItemsWithFallback("timeData", allowedHoursSink);
      LOCALE_TO_ALLOWED_HOUR = Collections.unmodifiableMap(temp);
      DTPNG_CACHE = new SimpleCache();
      CLDR_FIELD_APPEND = new String[]{"Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone"};
      CLDR_FIELD_NAME = new String[]{"era", "year", "*", "month", "week", "*", "weekday", "day", "*", "*", "dayperiod", "hour", "minute", "second", "*", "zone"};
      FIELD_NAME = new String[]{"Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone"};
      CANONICAL_ITEMS = new String[]{"G", "y", "Q", "M", "w", "W", "E", "d", "D", "F", "H", "m", "s", "S", "v"};
      CANONICAL_SET = new HashSet(Arrays.asList(CANONICAL_ITEMS));
      types = new int[][]{{71, 0, -258, 1, 3}, {71, 0, -259, 4}, {121, 1, 256, 1, 20}, {89, 1, 272, 1, 20}, {117, 1, 288, 1, 20}, {114, 1, 304, 1, 20}, {85, 1, -258, 1, 3}, {85, 1, -259, 4}, {85, 1, -257, 5}, {81, 2, 256, 1, 2}, {81, 2, -258, 3}, {81, 2, -259, 4}, {113, 2, 272, 1, 2}, {113, 2, -242, 3}, {113, 2, -243, 4}, {77, 3, 256, 1, 2}, {77, 3, -258, 3}, {77, 3, -259, 4}, {77, 3, -257, 5}, {76, 3, 272, 1, 2}, {76, 3, -274, 3}, {76, 3, -275, 4}, {76, 3, -273, 5}, {108, 3, 272, 1, 1}, {119, 4, 256, 1, 2}, {87, 5, 272, 1}, {69, 6, -258, 1, 3}, {69, 6, -259, 4}, {69, 6, -257, 5}, {99, 6, 288, 1, 2}, {99, 6, -290, 3}, {99, 6, -291, 4}, {99, 6, -289, 5}, {101, 6, 272, 1, 2}, {101, 6, -274, 3}, {101, 6, -275, 4}, {101, 6, -273, 5}, {100, 7, 256, 1, 2}, {68, 8, 272, 1, 3}, {70, 9, 288, 1}, {103, 7, 304, 1, 20}, {97, 10, -258, 1}, {72, 11, 416, 1, 2}, {107, 11, 432, 1, 2}, {104, 11, 256, 1, 2}, {75, 11, 272, 1, 2}, {109, 12, 256, 1, 2}, {115, 13, 256, 1, 2}, {83, 14, 272, 1, 1000}, {65, 13, 288, 1, 1000}, {118, 15, -290, 1}, {118, 15, -291, 4}, {122, 15, -258, 1, 3}, {122, 15, -259, 4}, {90, 15, -273, 1, 3}, {90, 15, -275, 4}, {90, 15, -274, 5}, {79, 15, -274, 1}, {79, 15, -275, 4}, {86, 15, -274, 1}, {86, 15, -275, 2}, {88, 15, -273, 1}, {88, 15, -274, 2}, {88, 15, -275, 4}, {120, 15, -273, 1}, {120, 15, -274, 2}, {120, 15, -275, 4}};
   }

   private static class DistanceInfo {
      int missingFieldMask;
      int extraFieldMask;

      private DistanceInfo() {
      }

      void clear() {
         this.missingFieldMask = this.extraFieldMask = 0;
      }

      void setTo(DistanceInfo other) {
         this.missingFieldMask = other.missingFieldMask;
         this.extraFieldMask = other.extraFieldMask;
      }

      void addMissing(int field) {
         this.missingFieldMask |= 1 << field;
      }

      void addExtra(int field) {
         this.extraFieldMask |= 1 << field;
      }

      public String toString() {
         return "missingFieldMask: " + DateTimePatternGenerator.showMask(this.missingFieldMask) + ", extraFieldMask: " + DateTimePatternGenerator.showMask(this.extraFieldMask);
      }

      // $FF: synthetic method
      DistanceInfo(Object x0) {
         this();
      }
   }

   private static class DateTimeMatcher implements Comparable {
      private int[] type;
      private SkeletonFields original;
      private SkeletonFields baseOriginal;

      private DateTimeMatcher() {
         this.type = new int[16];
         this.original = new SkeletonFields();
         this.baseOriginal = new SkeletonFields();
      }

      public boolean fieldIsNumeric(int field) {
         return this.type[field] > 0;
      }

      public String toString() {
         return this.original.toString();
      }

      public String toCanonicalString() {
         return this.original.toCanonicalString();
      }

      String getBasePattern() {
         return this.baseOriginal.toString();
      }

      DateTimeMatcher set(String pattern, FormatParser fp, boolean allowDuplicateFields) {
         Arrays.fill(this.type, 0);
         this.original.clear();
         this.baseOriginal.clear();
         fp.set(pattern);
         Iterator var4 = fp.getItems().iterator();

         String value;
         char repeatChar;
         char ch2;
         do {
            do {
               do {
                  while(true) {
                     VariableField item;
                     do {
                        Object obj;
                        do {
                           if (!var4.hasNext()) {
                              return this;
                           }

                           obj = var4.next();
                        } while(!(obj instanceof VariableField));

                        item = (VariableField)obj;
                        value = item.toString();
                     } while(value.charAt(0) == 'a');

                     int canonicalIndex = item.getCanonicalIndex();
                     int[] row = DateTimePatternGenerator.types[canonicalIndex];
                     int field = row[1];
                     if (!this.original.isFieldEmpty(field)) {
                        repeatChar = this.original.getFieldChar(field);
                        ch2 = value.charAt(0);
                        break;
                     }

                     this.original.populate(field, value);
                     repeatChar = (char)row[0];
                     int repeatCount = row[3];
                     if ("GEzvQ".indexOf(repeatChar) >= 0) {
                        repeatCount = 1;
                     }

                     this.baseOriginal.populate(field, repeatChar, repeatCount);
                     int subField = row[2];
                     if (subField > 0) {
                        subField += value.length();
                     }

                     this.type[field] = subField;
                  }
               } while(allowDuplicateFields);
            } while(repeatChar == 'r' && ch2 == 'U');
         } while(repeatChar == 'U' && ch2 == 'r');

         throw new IllegalArgumentException("Conflicting fields:\t" + repeatChar + ", " + value + "\t in " + pattern);
      }

      int getFieldMask() {
         int result = 0;

         for(int i = 0; i < this.type.length; ++i) {
            if (this.type[i] != 0) {
               result |= 1 << i;
            }
         }

         return result;
      }

      void extractFrom(DateTimeMatcher source, int fieldMask) {
         for(int i = 0; i < this.type.length; ++i) {
            if ((fieldMask & 1 << i) != 0) {
               this.type[i] = source.type[i];
               this.original.copyFieldFrom(source.original, i);
            } else {
               this.type[i] = 0;
               this.original.clearField(i);
            }
         }

      }

      int getDistance(DateTimeMatcher other, int includeMask, DistanceInfo distanceInfo) {
         int result = 0;
         distanceInfo.clear();

         for(int i = 0; i < 16; ++i) {
            int myType = (includeMask & 1 << i) == 0 ? 0 : this.type[i];
            int otherType = other.type[i];
            if (myType != otherType) {
               if (myType == 0) {
                  result += 65536;
                  distanceInfo.addExtra(i);
               } else if (otherType == 0) {
                  result += 4096;
                  distanceInfo.addMissing(i);
               } else {
                  result += Math.abs(myType - otherType);
               }
            }
         }

         return result;
      }

      public int compareTo(DateTimeMatcher that) {
         int result = this.original.compareTo(that.original);
         return result > 0 ? -1 : (result < 0 ? 1 : 0);
      }

      public boolean equals(Object other) {
         return this == other || other != null && other instanceof DateTimeMatcher && this.original.equals(((DateTimeMatcher)other).original);
      }

      public int hashCode() {
         return this.original.hashCode();
      }

      // $FF: synthetic method
      DateTimeMatcher(Object x0) {
         this();
      }
   }

   private static class SkeletonFields {
      private byte[] chars;
      private byte[] lengths;
      private static final byte DEFAULT_CHAR = 0;
      private static final byte DEFAULT_LENGTH = 0;

      private SkeletonFields() {
         this.chars = new byte[16];
         this.lengths = new byte[16];
      }

      public void clear() {
         Arrays.fill(this.chars, (byte)0);
         Arrays.fill(this.lengths, (byte)0);
      }

      void copyFieldFrom(SkeletonFields other, int field) {
         this.chars[field] = other.chars[field];
         this.lengths[field] = other.lengths[field];
      }

      void clearField(int field) {
         this.chars[field] = 0;
         this.lengths[field] = 0;
      }

      char getFieldChar(int field) {
         return (char)this.chars[field];
      }

      int getFieldLength(int field) {
         return this.lengths[field];
      }

      void populate(int field, String value) {
         char[] var3 = value.toCharArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            char ch = var3[var5];

            assert ch == value.charAt(0);
         }

         this.populate(field, value.charAt(0), value.length());
      }

      void populate(int field, char ch, int length) {
         assert ch <= 127;

         assert length <= 127;

         this.chars[field] = (byte)ch;
         this.lengths[field] = (byte)length;
      }

      public boolean isFieldEmpty(int field) {
         return this.lengths[field] == 0;
      }

      public String toString() {
         return this.appendTo(new StringBuilder()).toString();
      }

      public String toCanonicalString() {
         return this.appendTo(new StringBuilder(), true).toString();
      }

      public StringBuilder appendTo(StringBuilder sb) {
         return this.appendTo(sb, false);
      }

      private StringBuilder appendTo(StringBuilder sb, boolean canonical) {
         for(int i = 0; i < 16; ++i) {
            this.appendFieldTo(i, sb, canonical);
         }

         return sb;
      }

      public StringBuilder appendFieldTo(int field, StringBuilder sb) {
         return this.appendFieldTo(field, sb, false);
      }

      private StringBuilder appendFieldTo(int field, StringBuilder sb, boolean canonical) {
         char ch = (char)this.chars[field];
         int length = this.lengths[field];
         if (canonical) {
            ch = DateTimePatternGenerator.getCanonicalChar(field, ch);
         }

         for(int i = 0; i < length; ++i) {
            sb.append(ch);
         }

         return sb;
      }

      public int compareTo(SkeletonFields other) {
         for(int i = 0; i < 16; ++i) {
            int charDiff = this.chars[i] - other.chars[i];
            if (charDiff != 0) {
               return charDiff;
            }

            int lengthDiff = this.lengths[i] - other.lengths[i];
            if (lengthDiff != 0) {
               return lengthDiff;
            }
         }

         return 0;
      }

      public boolean equals(Object other) {
         return this == other || other != null && other instanceof SkeletonFields && this.compareTo((SkeletonFields)other) == 0;
      }

      public int hashCode() {
         return Arrays.hashCode(this.chars) ^ Arrays.hashCode(this.lengths);
      }

      // $FF: synthetic method
      SkeletonFields(Object x0) {
         this();
      }
   }

   private static enum DTPGflags {
      FIX_FRACTIONAL_SECONDS,
      SKELETON_USES_CAP_J,
      SKELETON_USES_b,
      SKELETON_USES_B;

      public static DTPGflags getFlag(String preferred) {
         char last = preferred.charAt(preferred.length() - 1);
         switch (last) {
            case 'B':
               return SKELETON_USES_B;
            case 'b':
               return SKELETON_USES_b;
            default:
               return null;
         }
      }
   }

   private static class PatternWithSkeletonFlag {
      public String pattern;
      public boolean skeletonWasSpecified;

      public PatternWithSkeletonFlag(String pat, boolean skelSpecified) {
         this.pattern = pat;
         this.skeletonWasSpecified = skelSpecified;
      }

      public String toString() {
         return this.pattern + "," + this.skeletonWasSpecified;
      }
   }

   private static class PatternWithMatcher {
      public String pattern;
      public DateTimeMatcher matcherWithSkeleton;

      public PatternWithMatcher(String pat, DateTimeMatcher matcher) {
         this.pattern = pat;
         this.matcherWithSkeleton = matcher;
      }
   }

   /** @deprecated */
   @Deprecated
   public static class FormatParser {
      private static final UnicodeSet SYNTAX_CHARS = (new UnicodeSet("[a-zA-Z]")).freeze();
      private static final UnicodeSet QUOTING_CHARS = (new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]")).freeze();
      private transient PatternTokenizer tokenizer;
      private List items;

      /** @deprecated */
      @Deprecated
      public FormatParser() {
         this.tokenizer = (new PatternTokenizer()).setSyntaxCharacters(SYNTAX_CHARS).setExtraQuotingCharacters(QUOTING_CHARS).setUsingQuote(true);
         this.items = new ArrayList();
      }

      /** @deprecated */
      @Deprecated
      public final FormatParser set(String string) {
         return this.set(string, false);
      }

      /** @deprecated */
      @Deprecated
      public FormatParser set(String string, boolean strict) {
         this.items.clear();
         if (string.length() == 0) {
            return this;
         } else {
            this.tokenizer.setPattern(string);
            StringBuffer buffer = new StringBuffer();
            StringBuffer variable = new StringBuffer();

            while(true) {
               buffer.setLength(0);
               int status = this.tokenizer.next(buffer);
               if (status == 0) {
                  this.addVariable(variable, false);
                  return this;
               }

               if (status == 1) {
                  if (variable.length() != 0 && buffer.charAt(0) != variable.charAt(0)) {
                     this.addVariable(variable, false);
                  }

                  variable.append(buffer);
               } else {
                  this.addVariable(variable, false);
                  this.items.add(buffer.toString());
               }
            }
         }
      }

      private void addVariable(StringBuffer variable, boolean strict) {
         if (variable.length() != 0) {
            this.items.add(new VariableField(variable.toString(), strict));
            variable.setLength(0);
         }

      }

      /** @deprecated */
      @Deprecated
      public List getItems() {
         return this.items;
      }

      /** @deprecated */
      @Deprecated
      public String toString() {
         return this.toString(0, this.items.size());
      }

      /** @deprecated */
      @Deprecated
      public String toString(int start, int limit) {
         StringBuilder result = new StringBuilder();

         for(int i = start; i < limit; ++i) {
            Object item = this.items.get(i);
            if (item instanceof String) {
               String itemString = (String)item;
               result.append(this.tokenizer.quoteLiteral(itemString));
            } else {
               result.append(this.items.get(i).toString());
            }
         }

         return result.toString();
      }

      /** @deprecated */
      @Deprecated
      public boolean hasDateAndTimeFields() {
         int foundMask = 0;
         Iterator var2 = this.items.iterator();

         while(var2.hasNext()) {
            Object item = var2.next();
            if (item instanceof VariableField) {
               int type = ((VariableField)item).getType();
               foundMask |= 1 << type;
            }
         }

         boolean isDate = (foundMask & 1023) != 0;
         boolean isTime = (foundMask & 'ﰀ') != 0;
         return isDate && isTime;
      }

      /** @deprecated */
      @Deprecated
      public Object quoteLiteral(String string) {
         return this.tokenizer.quoteLiteral(string);
      }
   }

   /** @deprecated */
   @Deprecated
   public static class VariableField {
      private final String string;
      private final int canonicalIndex;

      /** @deprecated */
      @Deprecated
      public VariableField(String string) {
         this(string, false);
      }

      /** @deprecated */
      @Deprecated
      public VariableField(String string, boolean strict) {
         this.canonicalIndex = DateTimePatternGenerator.getCanonicalIndex(string, strict);
         if (this.canonicalIndex < 0) {
            throw new IllegalArgumentException("Illegal datetime field:\t" + string);
         } else {
            this.string = string;
         }
      }

      /** @deprecated */
      @Deprecated
      public int getType() {
         return DateTimePatternGenerator.types[this.canonicalIndex][1];
      }

      /** @deprecated */
      @Deprecated
      public static String getCanonicalCode(int type) {
         try {
            return DateTimePatternGenerator.CANONICAL_ITEMS[type];
         } catch (Exception var2) {
            return String.valueOf(type);
         }
      }

      /** @deprecated */
      @Deprecated
      public boolean isNumeric() {
         return DateTimePatternGenerator.types[this.canonicalIndex][2] > 0;
      }

      private int getCanonicalIndex() {
         return this.canonicalIndex;
      }

      /** @deprecated */
      @Deprecated
      public String toString() {
         return this.string;
      }
   }

   public static final class PatternInfo {
      public static final int OK = 0;
      public static final int BASE_CONFLICT = 1;
      public static final int CONFLICT = 2;
      public int status;
      public String conflictingPattern;
   }

   private static class DayPeriodAllowedHoursSink extends UResource.Sink {
      HashMap tempMap;

      private DayPeriodAllowedHoursSink(HashMap tempMap) {
         this.tempMap = tempMap;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table timeData = value.getTable();

         for(int i = 0; timeData.getKeyAndValue(i, key, value); ++i) {
            String regionOrLocale = key.toString();
            UResource.Table formatList = value.getTable();

            for(int j = 0; formatList.getKeyAndValue(j, key, value); ++j) {
               if (key.contentEquals("allowed")) {
                  this.tempMap.put(regionOrLocale, value.getStringArrayOrStringAsArray());
               }
            }
         }

      }

      // $FF: synthetic method
      DayPeriodAllowedHoursSink(HashMap x0, Object x1) {
         this(x0);
      }
   }

   private class AvailableFormatsSink extends UResource.Sink {
      PatternInfo returnInfo;

      public AvailableFormatsSink(PatternInfo returnInfo) {
         this.returnInfo = returnInfo;
      }

      public void put(UResource.Key key, UResource.Value value, boolean isRoot) {
         UResource.Table formatsTable = value.getTable();

         for(int i = 0; formatsTable.getKeyAndValue(i, key, value); ++i) {
            String formatKey = key.toString();
            if (!DateTimePatternGenerator.this.isAvailableFormatSet(formatKey)) {
               DateTimePatternGenerator.this.setAvailableFormat(formatKey);
               String formatValue = value.toString();
               DateTimePatternGenerator.this.addPatternWithSkeleton(formatValue, formatKey, !isRoot, this.returnInfo);
            }
         }

      }
   }

   private class AppendItemNamesSink extends UResource.Sink {
      private AppendItemNamesSink() {
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table itemsTable = value.getTable();

         for(int i = 0; itemsTable.getKeyAndValue(i, key, value); ++i) {
            int field = DateTimePatternGenerator.getCLDRFieldNumber(key);
            if (field != -1) {
               UResource.Table detailsTable = value.getTable();

               for(int j = 0; detailsTable.getKeyAndValue(j, key, value); ++j) {
                  if (key.contentEquals("dn")) {
                     if (DateTimePatternGenerator.this.getAppendItemName(field) == null) {
                        DateTimePatternGenerator.this.setAppendItemName(field, value.toString());
                     }
                     break;
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      AppendItemNamesSink(Object x1) {
         this();
      }
   }

   private class AppendItemFormatsSink extends UResource.Sink {
      private AppendItemFormatsSink() {
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table itemsTable = value.getTable();

         for(int i = 0; itemsTable.getKeyAndValue(i, key, value); ++i) {
            int field = DateTimePatternGenerator.getAppendFormatNumber(key);

            assert field != -1;

            if (DateTimePatternGenerator.this.getAppendItemFormat(field) == null) {
               DateTimePatternGenerator.this.setAppendItemFormat(field, value.toString());
            }
         }

      }

      // $FF: synthetic method
      AppendItemFormatsSink(Object x1) {
         this();
      }
   }
}
