package org.python.icu.text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.ICUCache;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.UResource;
import org.python.icu.impl.Utility;
import org.python.icu.util.Calendar;
import org.python.icu.util.Freezable;
import org.python.icu.util.ICUCloneNotSupportedException;
import org.python.icu.util.ICUException;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class DateIntervalInfo implements Cloneable, Freezable, Serializable {
   static final int currentSerialVersion = 1;
   static final String[] CALENDAR_FIELD_TO_PATTERN_LETTER = new String[]{"G", "y", "M", "w", "W", "d", "D", "E", "F", "a", "h", "H", "m", "s", "S", "z", " ", "Y", "e", "u", "g", "A", " ", " "};
   private static final long serialVersionUID = 1L;
   private static final int MINIMUM_SUPPORTED_CALENDAR_FIELD = 13;
   private static String CALENDAR_KEY = "calendar";
   private static String INTERVAL_FORMATS_KEY = "intervalFormats";
   private static String FALLBACK_STRING = "fallback";
   private static String LATEST_FIRST_PREFIX = "latestFirst:";
   private static String EARLIEST_FIRST_PREFIX = "earliestFirst:";
   private static final ICUCache DIICACHE = new SimpleCache();
   private String fFallbackIntervalPattern;
   private boolean fFirstDateInPtnIsLaterDate;
   private Map fIntervalPatterns;
   private transient volatile boolean frozen;
   private transient boolean fIntervalPatternsReadOnly;

   /** @deprecated */
   @Deprecated
   public DateIntervalInfo() {
      this.fFirstDateInPtnIsLaterDate = false;
      this.fIntervalPatterns = null;
      this.frozen = false;
      this.fIntervalPatternsReadOnly = false;
      this.fIntervalPatterns = new HashMap();
      this.fFallbackIntervalPattern = "{0} – {1}";
   }

   public DateIntervalInfo(ULocale locale) {
      this.fFirstDateInPtnIsLaterDate = false;
      this.fIntervalPatterns = null;
      this.frozen = false;
      this.fIntervalPatternsReadOnly = false;
      this.initializeData(locale);
   }

   public DateIntervalInfo(Locale locale) {
      this(ULocale.forLocale(locale));
   }

   private void initializeData(ULocale locale) {
      String key = locale.toString();
      DateIntervalInfo dii = (DateIntervalInfo)DIICACHE.get(key);
      if (dii == null) {
         this.setup(locale);
         this.fIntervalPatternsReadOnly = true;
         DIICACHE.put(key, ((DateIntervalInfo)this.clone()).freeze());
      } else {
         this.initializeFromReadOnlyPatterns(dii);
      }

   }

   private void initializeFromReadOnlyPatterns(DateIntervalInfo dii) {
      this.fFallbackIntervalPattern = dii.fFallbackIntervalPattern;
      this.fFirstDateInPtnIsLaterDate = dii.fFirstDateInPtnIsLaterDate;
      this.fIntervalPatterns = dii.fIntervalPatterns;
      this.fIntervalPatternsReadOnly = true;
   }

   private void setup(ULocale locale) {
      int DEFAULT_HASH_SIZE = 19;
      this.fIntervalPatterns = new HashMap(DEFAULT_HASH_SIZE);
      this.fFallbackIntervalPattern = "{0} – {1}";

      try {
         String calendarTypeToUse = locale.getKeywordValue("calendar");
         if (calendarTypeToUse == null) {
            String[] preferredCalendarTypes = Calendar.getKeywordValuesForLocale("calendar", locale, true);
            calendarTypeToUse = preferredCalendarTypes[0];
         }

         if (calendarTypeToUse == null) {
            calendarTypeToUse = "gregorian";
         }

         DateIntervalSink sink = new DateIntervalSink(this);
         ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);
         String fallbackPattern = resource.getStringWithFallback(CALENDAR_KEY + "/" + calendarTypeToUse + "/" + INTERVAL_FORMATS_KEY + "/" + FALLBACK_STRING);
         this.setFallbackIntervalPattern(fallbackPattern);

         for(Set loadedCalendarTypes = new HashSet(); calendarTypeToUse != null; calendarTypeToUse = sink.getAndResetNextCalendarType()) {
            if (loadedCalendarTypes.contains(calendarTypeToUse)) {
               throw new ICUException("Loop in calendar type fallback: " + calendarTypeToUse);
            }

            loadedCalendarTypes.add(calendarTypeToUse);
            String pathToIntervalFormats = CALENDAR_KEY + "/" + calendarTypeToUse;
            resource.getAllItemsWithFallback(pathToIntervalFormats, sink);
         }
      } catch (MissingResourceException var9) {
      }

   }

   private static int splitPatternInto2Part(String intervalPattern) {
      boolean inQuote = false;
      char prevCh = 0;
      int count = 0;
      int[] patternRepeated = new int[58];
      int PATTERN_CHAR_BASE = 65;
      boolean foundRepetition = false;

      int i;
      for(i = 0; i < intervalPattern.length(); ++i) {
         char ch = intervalPattern.charAt(i);
         if (ch != prevCh && count > 0) {
            int repeated = patternRepeated[prevCh - PATTERN_CHAR_BASE];
            if (repeated != 0) {
               foundRepetition = true;
               break;
            }

            patternRepeated[prevCh - PATTERN_CHAR_BASE] = 1;
            count = 0;
         }

         if (ch == '\'') {
            if (i + 1 < intervalPattern.length() && intervalPattern.charAt(i + 1) == '\'') {
               ++i;
            } else {
               inQuote = !inQuote;
            }
         } else if (!inQuote && (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')) {
            prevCh = ch;
            ++count;
         }
      }

      if (count > 0 && !foundRepetition && patternRepeated[prevCh - PATTERN_CHAR_BASE] == 0) {
         count = 0;
      }

      return i - count;
   }

   public void setIntervalPattern(String skeleton, int lrgDiffCalUnit, String intervalPattern) {
      if (this.frozen) {
         throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
      } else if (lrgDiffCalUnit > 13) {
         throw new IllegalArgumentException("calendar field is larger than MINIMUM_SUPPORTED_CALENDAR_FIELD");
      } else {
         if (this.fIntervalPatternsReadOnly) {
            this.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
            this.fIntervalPatternsReadOnly = false;
         }

         PatternInfo ptnInfo = this.setIntervalPatternInternally(skeleton, CALENDAR_FIELD_TO_PATTERN_LETTER[lrgDiffCalUnit], intervalPattern);
         if (lrgDiffCalUnit == 11) {
            this.setIntervalPattern(skeleton, CALENDAR_FIELD_TO_PATTERN_LETTER[9], ptnInfo);
            this.setIntervalPattern(skeleton, CALENDAR_FIELD_TO_PATTERN_LETTER[10], ptnInfo);
         } else if (lrgDiffCalUnit == 5 || lrgDiffCalUnit == 7) {
            this.setIntervalPattern(skeleton, CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptnInfo);
         }

      }
   }

   private PatternInfo setIntervalPatternInternally(String skeleton, String lrgDiffCalUnit, String intervalPattern) {
      Map patternsOfOneSkeleton = (Map)this.fIntervalPatterns.get(skeleton);
      boolean emptyHash = false;
      if (patternsOfOneSkeleton == null) {
         patternsOfOneSkeleton = new HashMap();
         emptyHash = true;
      }

      boolean order = this.fFirstDateInPtnIsLaterDate;
      int earliestFirstLength;
      if (intervalPattern.startsWith(LATEST_FIRST_PREFIX)) {
         order = true;
         earliestFirstLength = LATEST_FIRST_PREFIX.length();
         intervalPattern = intervalPattern.substring(earliestFirstLength, intervalPattern.length());
      } else if (intervalPattern.startsWith(EARLIEST_FIRST_PREFIX)) {
         order = false;
         earliestFirstLength = EARLIEST_FIRST_PREFIX.length();
         intervalPattern = intervalPattern.substring(earliestFirstLength, intervalPattern.length());
      }

      PatternInfo itvPtnInfo = genPatternInfo(intervalPattern, order);
      ((Map)patternsOfOneSkeleton).put(lrgDiffCalUnit, itvPtnInfo);
      if (emptyHash) {
         this.fIntervalPatterns.put(skeleton, patternsOfOneSkeleton);
      }

      return itvPtnInfo;
   }

   private void setIntervalPattern(String skeleton, String lrgDiffCalUnit, PatternInfo ptnInfo) {
      Map patternsOfOneSkeleton = (Map)this.fIntervalPatterns.get(skeleton);
      patternsOfOneSkeleton.put(lrgDiffCalUnit, ptnInfo);
   }

   /** @deprecated */
   @Deprecated
   public static PatternInfo genPatternInfo(String intervalPattern, boolean laterDateFirst) {
      int splitPoint = splitPatternInto2Part(intervalPattern);
      String firstPart = intervalPattern.substring(0, splitPoint);
      String secondPart = null;
      if (splitPoint < intervalPattern.length()) {
         secondPart = intervalPattern.substring(splitPoint, intervalPattern.length());
      }

      return new PatternInfo(firstPart, secondPart, laterDateFirst);
   }

   public PatternInfo getIntervalPattern(String skeleton, int field) {
      if (field > 13) {
         throw new IllegalArgumentException("no support for field less than SECOND");
      } else {
         Map patternsOfOneSkeleton = (Map)this.fIntervalPatterns.get(skeleton);
         if (patternsOfOneSkeleton != null) {
            PatternInfo intervalPattern = (PatternInfo)patternsOfOneSkeleton.get(CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
            if (intervalPattern != null) {
               return intervalPattern;
            }
         }

         return null;
      }
   }

   public String getFallbackIntervalPattern() {
      return this.fFallbackIntervalPattern;
   }

   public void setFallbackIntervalPattern(String fallbackPattern) {
      if (this.frozen) {
         throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
      } else {
         int firstPatternIndex = fallbackPattern.indexOf("{0}");
         int secondPatternIndex = fallbackPattern.indexOf("{1}");
         if (firstPatternIndex != -1 && secondPatternIndex != -1) {
            if (firstPatternIndex > secondPatternIndex) {
               this.fFirstDateInPtnIsLaterDate = true;
            }

            this.fFallbackIntervalPattern = fallbackPattern;
         } else {
            throw new IllegalArgumentException("no pattern {0} or pattern {1} in fallbackPattern");
         }
      }
   }

   public boolean getDefaultOrder() {
      return this.fFirstDateInPtnIsLaterDate;
   }

   public Object clone() {
      return this.frozen ? this : this.cloneUnfrozenDII();
   }

   private Object cloneUnfrozenDII() {
      try {
         DateIntervalInfo other = (DateIntervalInfo)super.clone();
         other.fFallbackIntervalPattern = this.fFallbackIntervalPattern;
         other.fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
         if (this.fIntervalPatternsReadOnly) {
            other.fIntervalPatterns = this.fIntervalPatterns;
            other.fIntervalPatternsReadOnly = true;
         } else {
            other.fIntervalPatterns = cloneIntervalPatterns(this.fIntervalPatterns);
            other.fIntervalPatternsReadOnly = false;
         }

         other.frozen = false;
         return other;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException("clone is not supported", var2);
      }
   }

   private static Map cloneIntervalPatterns(Map patterns) {
      Map result = new HashMap();
      Iterator var2 = patterns.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry skeletonEntry = (Map.Entry)var2.next();
         String skeleton = (String)skeletonEntry.getKey();
         Map patternsOfOneSkeleton = (Map)skeletonEntry.getValue();
         Map oneSetPtn = new HashMap();
         Iterator var7 = patternsOfOneSkeleton.entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry calEntry = (Map.Entry)var7.next();
            String calField = (String)calEntry.getKey();
            PatternInfo value = (PatternInfo)calEntry.getValue();
            oneSetPtn.put(calField, value);
         }

         result.put(skeleton, oneSetPtn);
      }

      return result;
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public DateIntervalInfo freeze() {
      this.fIntervalPatternsReadOnly = true;
      this.frozen = true;
      return this;
   }

   public DateIntervalInfo cloneAsThawed() {
      DateIntervalInfo result = (DateIntervalInfo)((DateIntervalInfo)this.cloneUnfrozenDII());
      return result;
   }

   static void parseSkeleton(String skeleton, int[] skeletonFieldWidth) {
      int PATTERN_CHAR_BASE = 65;

      for(int i = 0; i < skeleton.length(); ++i) {
         ++skeletonFieldWidth[skeleton.charAt(i) - PATTERN_CHAR_BASE];
      }

   }

   private static boolean stringNumeric(int fieldWidth, int anotherFieldWidth, char patternLetter) {
      return patternLetter == 'M' && (fieldWidth <= 2 && anotherFieldWidth > 2 || fieldWidth > 2 && anotherFieldWidth <= 2);
   }

   DateIntervalFormat.BestMatchInfo getBestSkeleton(String inputSkeleton) {
      String bestSkeleton = inputSkeleton;
      int[] inputSkeletonFieldWidth = new int[58];
      int[] skeletonFieldWidth = new int[58];
      int DIFFERENT_FIELD = true;
      int STRING_NUMERIC_DIFFERENCE = true;
      int BASE = true;
      boolean replaceZWithV = false;
      if (inputSkeleton.indexOf(122) != -1) {
         inputSkeleton = inputSkeleton.replace('z', 'v');
         replaceZWithV = true;
      }

      parseSkeleton(inputSkeleton, inputSkeletonFieldWidth);
      int bestDistance = Integer.MAX_VALUE;
      int bestFieldDifference = 0;
      Iterator var11 = this.fIntervalPatterns.keySet().iterator();

      while(var11.hasNext()) {
         String skeleton = (String)var11.next();

         int distance;
         for(distance = 0; distance < skeletonFieldWidth.length; ++distance) {
            skeletonFieldWidth[distance] = 0;
         }

         parseSkeleton(skeleton, skeletonFieldWidth);
         distance = 0;
         int fieldDifference = 1;

         for(int i = 0; i < inputSkeletonFieldWidth.length; ++i) {
            int inputFieldWidth = inputSkeletonFieldWidth[i];
            int fieldWidth = skeletonFieldWidth[i];
            if (inputFieldWidth != fieldWidth) {
               if (inputFieldWidth == 0) {
                  fieldDifference = -1;
                  distance += 4096;
               } else if (fieldWidth == 0) {
                  fieldDifference = -1;
                  distance += 4096;
               } else if (stringNumeric(inputFieldWidth, fieldWidth, (char)(i + 65))) {
                  distance += 256;
               } else {
                  distance += Math.abs(inputFieldWidth - fieldWidth);
               }
            }
         }

         if (distance < bestDistance) {
            bestSkeleton = skeleton;
            bestDistance = distance;
            bestFieldDifference = fieldDifference;
         }

         if (distance == 0) {
            bestFieldDifference = 0;
            break;
         }
      }

      if (replaceZWithV && bestFieldDifference != -1) {
         bestFieldDifference = 2;
      }

      return new DateIntervalFormat.BestMatchInfo(bestSkeleton, bestFieldDifference);
   }

   public boolean equals(Object a) {
      if (a instanceof DateIntervalInfo) {
         DateIntervalInfo dtInfo = (DateIntervalInfo)a;
         return this.fIntervalPatterns.equals(dtInfo.fIntervalPatterns);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.fIntervalPatterns.hashCode();
   }

   /** @deprecated */
   @Deprecated
   public Map getPatterns() {
      LinkedHashMap result = new LinkedHashMap();
      Iterator var2 = this.fIntervalPatterns.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         result.put(entry.getKey(), new LinkedHashSet(((Map)entry.getValue()).keySet()));
      }

      return result;
   }

   /** @deprecated */
   @Deprecated
   public Map getRawPatterns() {
      LinkedHashMap result = new LinkedHashMap();
      Iterator var2 = this.fIntervalPatterns.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         result.put(entry.getKey(), new LinkedHashMap((Map)entry.getValue()));
      }

      return result;
   }

   private static final class DateIntervalSink extends UResource.Sink {
      private static final String ACCEPTED_PATTERN_LETTERS = "yMdahHms";
      DateIntervalInfo dateIntervalInfo;
      String nextCalendarType;
      private static final String DATE_INTERVAL_PATH_PREFIX;
      private static final String DATE_INTERVAL_PATH_SUFFIX;

      public DateIntervalSink(DateIntervalInfo dateIntervalInfo) {
         this.dateIntervalInfo = dateIntervalInfo;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table dateIntervalData = value.getTable();

         for(int i = 0; dateIntervalData.getKeyAndValue(i, key, value); ++i) {
            if (key.contentEquals(DateIntervalInfo.INTERVAL_FORMATS_KEY)) {
               if (value.getType() == 3) {
                  this.nextCalendarType = this.getCalendarTypeFromPath(value.getAliasString());
                  break;
               }

               if (value.getType() == 2) {
                  UResource.Table skeletonData = value.getTable();

                  for(int j = 0; skeletonData.getKeyAndValue(j, key, value); ++j) {
                     if (value.getType() == 2) {
                        this.processSkeletonTable(key, value);
                     }
                  }

                  return;
               }
            }
         }

      }

      public void processSkeletonTable(UResource.Key key, UResource.Value value) {
         String currentSkeleton = key.toString();
         UResource.Table patternData = value.getTable();

         for(int k = 0; patternData.getKeyAndValue(k, key, value); ++k) {
            if (value.getType() == 0) {
               CharSequence patternLetter = this.validateAndProcessPatternLetter(key);
               if (patternLetter != null) {
                  String lrgDiffCalUnit = patternLetter.toString();
                  this.setIntervalPatternIfAbsent(currentSkeleton, lrgDiffCalUnit, value);
               }
            }
         }

      }

      public String getAndResetNextCalendarType() {
         String tmpCalendarType = this.nextCalendarType;
         this.nextCalendarType = null;
         return tmpCalendarType;
      }

      private String getCalendarTypeFromPath(String path) {
         if (path.startsWith(DATE_INTERVAL_PATH_PREFIX) && path.endsWith(DATE_INTERVAL_PATH_SUFFIX)) {
            return path.substring(DATE_INTERVAL_PATH_PREFIX.length(), path.length() - DATE_INTERVAL_PATH_SUFFIX.length());
         } else {
            throw new ICUException("Malformed 'intervalFormat' alias path: " + path);
         }
      }

      private CharSequence validateAndProcessPatternLetter(CharSequence patternLetter) {
         if (((CharSequence)patternLetter).length() != 1) {
            return null;
         } else {
            char letter = ((CharSequence)patternLetter).charAt(0);
            if ("yMdahHms".indexOf(letter) < 0) {
               return null;
            } else {
               if (letter == DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[11].charAt(0)) {
                  patternLetter = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[10];
               }

               return (CharSequence)patternLetter;
            }
         }
      }

      private void setIntervalPatternIfAbsent(String currentSkeleton, String lrgDiffCalUnit, UResource.Value intervalPattern) {
         Map patternsOfOneSkeleton = (Map)this.dateIntervalInfo.fIntervalPatterns.get(currentSkeleton);
         if (patternsOfOneSkeleton == null || !patternsOfOneSkeleton.containsKey(lrgDiffCalUnit)) {
            this.dateIntervalInfo.setIntervalPatternInternally(currentSkeleton, lrgDiffCalUnit, intervalPattern.toString());
         }

      }

      static {
         DATE_INTERVAL_PATH_PREFIX = "/LOCALE/" + DateIntervalInfo.CALENDAR_KEY + "/";
         DATE_INTERVAL_PATH_SUFFIX = "/" + DateIntervalInfo.INTERVAL_FORMATS_KEY;
      }
   }

   public static final class PatternInfo implements Cloneable, Serializable {
      static final int currentSerialVersion = 1;
      private static final long serialVersionUID = 1L;
      private final String fIntervalPatternFirstPart;
      private final String fIntervalPatternSecondPart;
      private final boolean fFirstDateInPtnIsLaterDate;

      public PatternInfo(String firstPart, String secondPart, boolean firstDateInPtnIsLaterDate) {
         this.fIntervalPatternFirstPart = firstPart;
         this.fIntervalPatternSecondPart = secondPart;
         this.fFirstDateInPtnIsLaterDate = firstDateInPtnIsLaterDate;
      }

      public String getFirstPart() {
         return this.fIntervalPatternFirstPart;
      }

      public String getSecondPart() {
         return this.fIntervalPatternSecondPart;
      }

      public boolean firstDateInPtnIsLaterDate() {
         return this.fFirstDateInPtnIsLaterDate;
      }

      public boolean equals(Object a) {
         if (!(a instanceof PatternInfo)) {
            return false;
         } else {
            PatternInfo patternInfo = (PatternInfo)a;
            return Utility.objectEquals(this.fIntervalPatternFirstPart, patternInfo.fIntervalPatternFirstPart) && Utility.objectEquals(this.fIntervalPatternSecondPart, patternInfo.fIntervalPatternSecondPart) && this.fFirstDateInPtnIsLaterDate == patternInfo.fFirstDateInPtnIsLaterDate;
         }
      }

      public int hashCode() {
         int hash = this.fIntervalPatternFirstPart != null ? this.fIntervalPatternFirstPart.hashCode() : 0;
         if (this.fIntervalPatternSecondPart != null) {
            hash ^= this.fIntervalPatternSecondPart.hashCode();
         }

         if (this.fFirstDateInPtnIsLaterDate) {
            hash = ~hash;
         }

         return hash;
      }

      /** @deprecated */
      @Deprecated
      public String toString() {
         return "{first=«" + this.fIntervalPatternFirstPart + "», second=«" + this.fIntervalPatternSecondPart + "», reversed:" + this.fFirstDateInPtnIsLaterDate + "}";
      }
   }
}
