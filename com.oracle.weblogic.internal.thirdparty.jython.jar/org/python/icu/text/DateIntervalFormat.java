package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.python.icu.impl.ICUCache;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.util.Calendar;
import org.python.icu.util.DateInterval;
import org.python.icu.util.Output;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class DateIntervalFormat extends UFormat {
   private static final long serialVersionUID = 1L;
   private static ICUCache LOCAL_PATTERN_CACHE = new SimpleCache();
   private DateIntervalInfo fInfo;
   private SimpleDateFormat fDateFormat;
   private Calendar fFromCalendar;
   private Calendar fToCalendar;
   private String fSkeleton = null;
   private boolean isDateIntervalInfoDefault;
   private transient Map fIntervalPatterns = null;
   private String fDatePattern = null;
   private String fTimePattern = null;
   private String fDateTimeFormat = null;

   private DateIntervalFormat() {
   }

   /** @deprecated */
   @Deprecated
   public DateIntervalFormat(String skeleton, DateIntervalInfo dtItvInfo, SimpleDateFormat simpleDateFormat) {
      this.fDateFormat = simpleDateFormat;
      dtItvInfo.freeze();
      this.fSkeleton = skeleton;
      this.fInfo = dtItvInfo;
      this.isDateIntervalInfoDefault = false;
      this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.initializePattern((ICUCache)null);
   }

   private DateIntervalFormat(String skeleton, ULocale locale, SimpleDateFormat simpleDateFormat) {
      this.fDateFormat = simpleDateFormat;
      this.fSkeleton = skeleton;
      this.fInfo = (new DateIntervalInfo(locale)).freeze();
      this.isDateIntervalInfoDefault = true;
      this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.initializePattern(LOCAL_PATTERN_CACHE);
   }

   public static final DateIntervalFormat getInstance(String skeleton) {
      return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateIntervalFormat getInstance(String skeleton, Locale locale) {
      return getInstance(skeleton, ULocale.forLocale(locale));
   }

   public static final DateIntervalFormat getInstance(String skeleton, ULocale locale) {
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      return new DateIntervalFormat(skeleton, locale, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
   }

   public static final DateIntervalFormat getInstance(String skeleton, DateIntervalInfo dtitvinf) {
      return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT), dtitvinf);
   }

   public static final DateIntervalFormat getInstance(String skeleton, Locale locale, DateIntervalInfo dtitvinf) {
      return getInstance(skeleton, ULocale.forLocale(locale), dtitvinf);
   }

   public static final DateIntervalFormat getInstance(String skeleton, ULocale locale, DateIntervalInfo dtitvinf) {
      dtitvinf = (DateIntervalInfo)dtitvinf.clone();
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      return new DateIntervalFormat(skeleton, dtitvinf, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
   }

   public synchronized Object clone() {
      DateIntervalFormat other = (DateIntervalFormat)super.clone();
      other.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
      other.fInfo = (DateIntervalInfo)this.fInfo.clone();
      other.fFromCalendar = (Calendar)this.fFromCalendar.clone();
      other.fToCalendar = (Calendar)this.fToCalendar.clone();
      other.fDatePattern = this.fDatePattern;
      other.fTimePattern = this.fTimePattern;
      other.fDateTimeFormat = this.fDateTimeFormat;
      return other;
   }

   public final StringBuffer format(Object obj, StringBuffer appendTo, FieldPosition fieldPosition) {
      if (obj instanceof DateInterval) {
         return this.format((DateInterval)obj, appendTo, fieldPosition);
      } else {
         throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a DateInterval");
      }
   }

   public final synchronized StringBuffer format(DateInterval dtInterval, StringBuffer appendTo, FieldPosition fieldPosition) {
      this.fFromCalendar.setTimeInMillis(dtInterval.getFromDate());
      this.fToCalendar.setTimeInMillis(dtInterval.getToDate());
      return this.format(this.fFromCalendar, this.fToCalendar, appendTo, fieldPosition);
   }

   /** @deprecated */
   @Deprecated
   public String getPatterns(Calendar fromCalendar, Calendar toCalendar, Output part2) {
      byte field;
      if (fromCalendar.get(0) != toCalendar.get(0)) {
         field = 0;
      } else if (fromCalendar.get(1) != toCalendar.get(1)) {
         field = 1;
      } else if (fromCalendar.get(2) != toCalendar.get(2)) {
         field = 2;
      } else if (fromCalendar.get(5) != toCalendar.get(5)) {
         field = 5;
      } else if (fromCalendar.get(9) != toCalendar.get(9)) {
         field = 9;
      } else if (fromCalendar.get(10) != toCalendar.get(10)) {
         field = 10;
      } else if (fromCalendar.get(12) != toCalendar.get(12)) {
         field = 12;
      } else {
         if (fromCalendar.get(13) == toCalendar.get(13)) {
            return null;
         }

         field = 13;
      }

      DateIntervalInfo.PatternInfo intervalPattern = (DateIntervalInfo.PatternInfo)this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
      part2.value = intervalPattern.getSecondPart();
      return intervalPattern.getFirstPart();
   }

   public final synchronized StringBuffer format(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) {
      if (!fromCalendar.isEquivalentTo(toCalendar)) {
         throw new IllegalArgumentException("can not format on two different calendars");
      } else {
         int field = true;
         byte field;
         if (fromCalendar.get(0) != toCalendar.get(0)) {
            field = 0;
         } else if (fromCalendar.get(1) != toCalendar.get(1)) {
            field = 1;
         } else if (fromCalendar.get(2) != toCalendar.get(2)) {
            field = 2;
         } else if (fromCalendar.get(5) != toCalendar.get(5)) {
            field = 5;
         } else if (fromCalendar.get(9) != toCalendar.get(9)) {
            field = 9;
         } else if (fromCalendar.get(10) != toCalendar.get(10)) {
            field = 10;
         } else if (fromCalendar.get(12) != toCalendar.get(12)) {
            field = 12;
         } else {
            if (fromCalendar.get(13) == toCalendar.get(13)) {
               return this.fDateFormat.format(fromCalendar, appendTo, pos);
            }

            field = 13;
         }

         boolean fromToOnSameDay = field == 9 || field == 10 || field == 12 || field == 13;
         DateIntervalInfo.PatternInfo intervalPattern = (DateIntervalInfo.PatternInfo)this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
         if (intervalPattern == null) {
            return this.fDateFormat.isFieldUnitIgnored(field) ? this.fDateFormat.format(fromCalendar, appendTo, pos) : this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos);
         } else if (intervalPattern.getFirstPart() == null) {
            return this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos, intervalPattern.getSecondPart());
         } else {
            Calendar firstCal;
            Calendar secondCal;
            if (intervalPattern.firstDateInPtnIsLaterDate()) {
               firstCal = toCalendar;
               secondCal = fromCalendar;
            } else {
               firstCal = fromCalendar;
               secondCal = toCalendar;
            }

            String originalPattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(intervalPattern.getFirstPart());
            this.fDateFormat.format(firstCal, appendTo, pos);
            if (intervalPattern.getSecondPart() != null) {
               this.fDateFormat.applyPattern(intervalPattern.getSecondPart());
               FieldPosition otherPos = new FieldPosition(pos.getField());
               this.fDateFormat.format(secondCal, appendTo, otherPos);
               if (pos.getEndIndex() == 0 && otherPos.getEndIndex() > 0) {
                  pos.setBeginIndex(otherPos.getBeginIndex());
                  pos.setEndIndex(otherPos.getEndIndex());
               }
            }

            this.fDateFormat.applyPattern(originalPattern);
            return appendTo;
         }
      }
   }

   private void adjustPosition(String combiningPattern, String pat0, FieldPosition pos0, String pat1, FieldPosition pos1, FieldPosition posResult) {
      int index0 = combiningPattern.indexOf("{0}");
      int index1 = combiningPattern.indexOf("{1}");
      if (index0 >= 0 && index1 >= 0) {
         int placeholderLen = 3;
         if (index0 < index1) {
            if (pos0.getEndIndex() > 0) {
               posResult.setBeginIndex(pos0.getBeginIndex() + index0);
               posResult.setEndIndex(pos0.getEndIndex() + index0);
            } else if (pos1.getEndIndex() > 0) {
               index1 += pat0.length() - placeholderLen;
               posResult.setBeginIndex(pos1.getBeginIndex() + index1);
               posResult.setEndIndex(pos1.getEndIndex() + index1);
            }
         } else if (pos1.getEndIndex() > 0) {
            posResult.setBeginIndex(pos1.getBeginIndex() + index1);
            posResult.setEndIndex(pos1.getEndIndex() + index1);
         } else if (pos0.getEndIndex() > 0) {
            index0 += pat1.length() - placeholderLen;
            posResult.setBeginIndex(pos0.getBeginIndex() + index0);
            posResult.setEndIndex(pos0.getEndIndex() + index0);
         }

      }
   }

   private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos) {
      String fullPattern = null;
      boolean formatDatePlusTimeRange = fromToOnSameDay && this.fDatePattern != null && this.fTimePattern != null;
      if (formatDatePlusTimeRange) {
         fullPattern = this.fDateFormat.toPattern();
         this.fDateFormat.applyPattern(this.fTimePattern);
      }

      FieldPosition otherPos = new FieldPosition(pos.getField());
      StringBuffer earlierDate = new StringBuffer(64);
      earlierDate = this.fDateFormat.format(fromCalendar, earlierDate, pos);
      StringBuffer laterDate = new StringBuffer(64);
      laterDate = this.fDateFormat.format(toCalendar, laterDate, otherPos);
      String fallbackPattern = this.fInfo.getFallbackIntervalPattern();
      this.adjustPosition(fallbackPattern, earlierDate.toString(), pos, laterDate.toString(), otherPos, pos);
      String fallbackRange = SimpleFormatterImpl.formatRawPattern(fallbackPattern, 2, 2, earlierDate, laterDate);
      if (formatDatePlusTimeRange) {
         this.fDateFormat.applyPattern(this.fDatePattern);
         StringBuffer datePortion = new StringBuffer(64);
         otherPos.setBeginIndex(0);
         otherPos.setEndIndex(0);
         datePortion = this.fDateFormat.format(fromCalendar, datePortion, otherPos);
         this.adjustPosition(this.fDateTimeFormat, fallbackRange, pos, datePortion.toString(), otherPos, pos);
         fallbackRange = SimpleFormatterImpl.formatRawPattern(this.fDateTimeFormat, 2, 2, fallbackRange, datePortion);
      }

      appendTo.append(fallbackRange);
      if (formatDatePlusTimeRange) {
         this.fDateFormat.applyPattern(fullPattern);
      }

      return appendTo;
   }

   private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, boolean fromToOnSameDay, StringBuffer appendTo, FieldPosition pos, String fullPattern) {
      String originalPattern = this.fDateFormat.toPattern();
      this.fDateFormat.applyPattern(fullPattern);
      this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos);
      this.fDateFormat.applyPattern(originalPattern);
      return appendTo;
   }

   /** @deprecated */
   @Deprecated
   public Object parseObject(String source, ParsePosition parse_pos) {
      throw new UnsupportedOperationException("parsing is not supported");
   }

   public DateIntervalInfo getDateIntervalInfo() {
      return (DateIntervalInfo)this.fInfo.clone();
   }

   public void setDateIntervalInfo(DateIntervalInfo newItvPattern) {
      this.fInfo = (DateIntervalInfo)newItvPattern.clone();
      this.isDateIntervalInfoDefault = false;
      this.fInfo.freeze();
      if (this.fDateFormat != null) {
         this.initializePattern((ICUCache)null);
      }

   }

   public TimeZone getTimeZone() {
      return this.fDateFormat != null ? (TimeZone)((TimeZone)this.fDateFormat.getTimeZone().clone()) : TimeZone.getDefault();
   }

   public void setTimeZone(TimeZone zone) {
      TimeZone zoneToSet = (TimeZone)zone.clone();
      if (this.fDateFormat != null) {
         this.fDateFormat.setTimeZone(zoneToSet);
      }

      if (this.fFromCalendar != null) {
         this.fFromCalendar.setTimeZone(zoneToSet);
      }

      if (this.fToCalendar != null) {
         this.fToCalendar.setTimeZone(zoneToSet);
      }

   }

   public synchronized DateFormat getDateFormat() {
      return (DateFormat)this.fDateFormat.clone();
   }

   private void initializePattern(ICUCache cache) {
      String fullPattern = this.fDateFormat.toPattern();
      ULocale locale = this.fDateFormat.getLocale();
      String key = null;
      Map patterns = null;
      if (cache != null) {
         if (this.fSkeleton != null) {
            key = locale.toString() + "+" + fullPattern + "+" + this.fSkeleton;
         } else {
            key = locale.toString() + "+" + fullPattern;
         }

         patterns = (Map)cache.get(key);
      }

      if (patterns == null) {
         Map intervalPatterns = this.initializeIntervalPattern(fullPattern, locale);
         patterns = Collections.unmodifiableMap(intervalPatterns);
         if (cache != null) {
            cache.put(key, patterns);
         }
      }

      this.fIntervalPatterns = patterns;
   }

   private Map initializeIntervalPattern(String fullPattern, ULocale locale) {
      DateTimePatternGenerator dtpng = DateTimePatternGenerator.getInstance(locale);
      if (this.fSkeleton == null) {
         this.fSkeleton = dtpng.getSkeleton(fullPattern);
      }

      String skeleton = this.fSkeleton;
      HashMap intervalPatterns = new HashMap();
      StringBuilder date = new StringBuilder(skeleton.length());
      StringBuilder normalizedDate = new StringBuilder(skeleton.length());
      StringBuilder time = new StringBuilder(skeleton.length());
      StringBuilder normalizedTime = new StringBuilder(skeleton.length());
      getDateTimeSkeleton(skeleton, date, normalizedDate, time, normalizedTime);
      String dateSkeleton = date.toString();
      String timeSkeleton = time.toString();
      String normalizedDateSkeleton = normalizedDate.toString();
      String normalizedTimeSkeleton = normalizedTime.toString();
      if (time.length() != 0 && date.length() != 0) {
         this.fDateTimeFormat = this.getConcatenationPattern(locale);
      }

      boolean found = this.genSeparateDateTimePtn(normalizedDateSkeleton, normalizedTimeSkeleton, intervalPatterns, dtpng);
      String datePattern;
      DateIntervalInfo.PatternInfo ptn;
      if (!found) {
         if (time.length() != 0 && date.length() == 0) {
            timeSkeleton = "yMd" + timeSkeleton;
            datePattern = dtpng.getBestPattern(timeSkeleton);
            ptn = new DateIntervalInfo.PatternInfo((String)null, datePattern, this.fInfo.getDefaultOrder());
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
         }

         return intervalPatterns;
      } else {
         if (time.length() != 0) {
            if (date.length() == 0) {
               timeSkeleton = "yMd" + timeSkeleton;
               datePattern = dtpng.getBestPattern(timeSkeleton);
               ptn = new DateIntervalInfo.PatternInfo((String)null, datePattern, this.fInfo.getDefaultOrder());
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
            } else {
               if (!fieldExistsInSkeleton(5, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + skeleton;
                  this.genFallbackPattern(5, skeleton, intervalPatterns, dtpng);
               }

               if (!fieldExistsInSkeleton(2, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + skeleton;
                  this.genFallbackPattern(2, skeleton, intervalPatterns, dtpng);
               }

               if (!fieldExistsInSkeleton(1, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + skeleton;
                  this.genFallbackPattern(1, skeleton, intervalPatterns, dtpng);
               }

               if (this.fDateTimeFormat == null) {
                  this.fDateTimeFormat = "{1} {0}";
               }

               datePattern = dtpng.getBestPattern(dateSkeleton);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 9, intervalPatterns);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 10, intervalPatterns);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 12, intervalPatterns);
            }
         }

         return intervalPatterns;
      }
   }

   private String getConcatenationPattern(ULocale locale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);
      ICUResourceBundle dtPatternsRb = rb.getWithFallback("calendar/gregorian/DateTimePatterns");
      ICUResourceBundle concatenationPatternRb = (ICUResourceBundle)dtPatternsRb.get(8);
      return concatenationPatternRb.getType() == 0 ? concatenationPatternRb.getString() : concatenationPatternRb.getString(0);
   }

   private void genFallbackPattern(int field, String skeleton, Map intervalPatterns, DateTimePatternGenerator dtpng) {
      String pattern = dtpng.getBestPattern(skeleton);
      DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo((String)null, pattern, this.fInfo.getDefaultOrder());
      intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptn);
   }

   private static void getDateTimeSkeleton(String skeleton, StringBuilder dateSkeleton, StringBuilder normalizedDateSkeleton, StringBuilder timeSkeleton, StringBuilder normalizedTimeSkeleton) {
      int ECount = 0;
      int dCount = 0;
      int MCount = 0;
      int yCount = 0;
      int hCount = 0;
      int HCount = 0;
      int mCount = 0;
      int vCount = 0;
      int zCount = 0;

      int i;
      for(i = 0; i < skeleton.length(); ++i) {
         char ch = skeleton.charAt(i);
         switch (ch) {
            case 'A':
            case 'K':
            case 'S':
            case 'V':
            case 'Z':
            case 'j':
            case 'k':
            case 's':
               timeSkeleton.append(ch);
               normalizedTimeSkeleton.append(ch);
            case 'B':
            case 'C':
            case 'I':
            case 'J':
            case 'N':
            case 'O':
            case 'P':
            case 'R':
            case 'T':
            case 'X':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'b':
            case 'f':
            case 'i':
            case 'n':
            case 'o':
            case 'p':
            case 't':
            case 'x':
            default:
               break;
            case 'D':
            case 'F':
            case 'G':
            case 'L':
            case 'Q':
            case 'U':
            case 'W':
            case 'Y':
            case 'c':
            case 'e':
            case 'g':
            case 'l':
            case 'q':
            case 'r':
            case 'u':
            case 'w':
               normalizedDateSkeleton.append(ch);
               dateSkeleton.append(ch);
               break;
            case 'E':
               dateSkeleton.append(ch);
               ++ECount;
               break;
            case 'H':
               timeSkeleton.append(ch);
               ++HCount;
               break;
            case 'M':
               dateSkeleton.append(ch);
               ++MCount;
               break;
            case 'a':
               timeSkeleton.append(ch);
               break;
            case 'd':
               dateSkeleton.append(ch);
               ++dCount;
               break;
            case 'h':
               timeSkeleton.append(ch);
               ++hCount;
               break;
            case 'm':
               timeSkeleton.append(ch);
               ++mCount;
               break;
            case 'v':
               ++vCount;
               timeSkeleton.append(ch);
               break;
            case 'y':
               dateSkeleton.append(ch);
               ++yCount;
               break;
            case 'z':
               ++zCount;
               timeSkeleton.append(ch);
         }
      }

      if (yCount != 0) {
         for(i = 0; i < yCount; ++i) {
            normalizedDateSkeleton.append('y');
         }
      }

      if (MCount != 0) {
         if (MCount < 3) {
            normalizedDateSkeleton.append('M');
         } else {
            for(i = 0; i < MCount && i < 5; ++i) {
               normalizedDateSkeleton.append('M');
            }
         }
      }

      if (ECount != 0) {
         if (ECount <= 3) {
            normalizedDateSkeleton.append('E');
         } else {
            for(i = 0; i < ECount && i < 5; ++i) {
               normalizedDateSkeleton.append('E');
            }
         }
      }

      if (dCount != 0) {
         normalizedDateSkeleton.append('d');
      }

      if (HCount != 0) {
         normalizedTimeSkeleton.append('H');
      } else if (hCount != 0) {
         normalizedTimeSkeleton.append('h');
      }

      if (mCount != 0) {
         normalizedTimeSkeleton.append('m');
      }

      if (zCount != 0) {
         normalizedTimeSkeleton.append('z');
      }

      if (vCount != 0) {
         normalizedTimeSkeleton.append('v');
      }

   }

   private boolean genSeparateDateTimePtn(String dateSkeleton, String timeSkeleton, Map intervalPatterns, DateTimePatternGenerator dtpng) {
      String skeleton;
      if (timeSkeleton.length() != 0) {
         skeleton = timeSkeleton;
      } else {
         skeleton = dateSkeleton;
      }

      BestMatchInfo retValue = this.fInfo.getBestSkeleton(skeleton);
      String bestSkeleton = retValue.bestMatchSkeleton;
      int differenceInfo = retValue.bestMatchDistanceInfo;
      if (dateSkeleton.length() != 0) {
         this.fDatePattern = dtpng.getBestPattern(dateSkeleton);
      }

      if (timeSkeleton.length() != 0) {
         this.fTimePattern = dtpng.getBestPattern(timeSkeleton);
      }

      if (differenceInfo == -1) {
         return false;
      } else {
         if (timeSkeleton.length() == 0) {
            this.genIntervalPattern(5, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            SkeletonAndItsBestMatch skeletons = this.genIntervalPattern(2, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            if (skeletons != null) {
               bestSkeleton = skeletons.skeleton;
               skeleton = skeletons.bestMatchSkeleton;
            }

            this.genIntervalPattern(1, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
         } else {
            this.genIntervalPattern(12, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            this.genIntervalPattern(10, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            this.genIntervalPattern(9, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
         }

         return true;
      }
   }

   private SkeletonAndItsBestMatch genIntervalPattern(int field, String skeleton, String bestSkeleton, int differenceInfo, Map intervalPatterns) {
      SkeletonAndItsBestMatch retValue = null;
      DateIntervalInfo.PatternInfo pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
      String part1;
      if (pattern == null) {
         if (SimpleDateFormat.isFieldUnitIgnored(bestSkeleton, field)) {
            DateIntervalInfo.PatternInfo ptnInfo = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), (String)null, this.fInfo.getDefaultOrder());
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptnInfo);
            return null;
         }

         if (field == 9) {
            pattern = this.fInfo.getIntervalPattern(bestSkeleton, 10);
            if (pattern != null) {
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
            }

            return null;
         }

         part1 = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
         bestSkeleton = part1 + bestSkeleton;
         skeleton = part1 + skeleton;
         pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
         if (pattern == null && differenceInfo == 0) {
            BestMatchInfo tmpRetValue = this.fInfo.getBestSkeleton(skeleton);
            String tmpBestSkeleton = tmpRetValue.bestMatchSkeleton;
            differenceInfo = tmpRetValue.bestMatchDistanceInfo;
            if (tmpBestSkeleton.length() != 0 && differenceInfo != -1) {
               pattern = this.fInfo.getIntervalPattern(tmpBestSkeleton, field);
               bestSkeleton = tmpBestSkeleton;
            }
         }

         if (pattern != null) {
            retValue = new SkeletonAndItsBestMatch(skeleton, bestSkeleton);
         }
      }

      if (pattern != null) {
         if (differenceInfo != 0) {
            part1 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo);
            String part2 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo);
            pattern = new DateIntervalInfo.PatternInfo(part1, part2, pattern.firstDateInPtnIsLaterDate());
         }

         intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
      }

      return retValue;
   }

   private static String adjustFieldWidth(String inputSkeleton, String bestMatchSkeleton, String bestMatchIntervalPattern, int differenceInfo) {
      if (bestMatchIntervalPattern == null) {
         return null;
      } else {
         int[] inputSkeletonFieldWidth = new int[58];
         int[] bestMatchSkeletonFieldWidth = new int[58];
         DateIntervalInfo.parseSkeleton(inputSkeleton, inputSkeletonFieldWidth);
         DateIntervalInfo.parseSkeleton(bestMatchSkeleton, bestMatchSkeletonFieldWidth);
         if (differenceInfo == 2) {
            bestMatchIntervalPattern = bestMatchIntervalPattern.replace('v', 'z');
         }

         StringBuilder adjustedPtn = new StringBuilder(bestMatchIntervalPattern);
         boolean inQuote = false;
         char prevCh = 0;
         int count = 0;
         int PATTERN_CHAR_BASE = 65;
         int adjustedPtnLength = adjustedPtn.length();

         int fieldCount;
         int inputFieldCount;
         int j;
         for(int i = 0; i < adjustedPtnLength; ++i) {
            fieldCount = adjustedPtn.charAt(i);
            if (fieldCount != prevCh && count > 0) {
               inputFieldCount = prevCh;
               if (prevCh == 'L') {
                  inputFieldCount = 77;
               }

               j = bestMatchSkeletonFieldWidth[inputFieldCount - PATTERN_CHAR_BASE];
               int inputFieldCount = inputSkeletonFieldWidth[inputFieldCount - PATTERN_CHAR_BASE];
               if (j == count && inputFieldCount > j) {
                  count = inputFieldCount - j;

                  for(int j = 0; j < count; ++j) {
                     adjustedPtn.insert(i, prevCh);
                  }

                  i += count;
                  adjustedPtnLength += count;
               }

               count = 0;
            }

            if (fieldCount == 39) {
               if (i + 1 < adjustedPtn.length() && adjustedPtn.charAt(i + 1) == '\'') {
                  ++i;
               } else {
                  inQuote = !inQuote;
               }
            } else if (!inQuote && (fieldCount >= 97 && fieldCount <= 122 || fieldCount >= 65 && fieldCount <= 90)) {
               prevCh = (char)fieldCount;
               ++count;
            }
         }

         if (count > 0) {
            char skeletonChar = prevCh;
            if (prevCh == 'L') {
               skeletonChar = 'M';
            }

            fieldCount = bestMatchSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
            inputFieldCount = inputSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
            if (fieldCount == count && inputFieldCount > fieldCount) {
               count = inputFieldCount - fieldCount;

               for(j = 0; j < count; ++j) {
                  adjustedPtn.append(prevCh);
               }
            }
         }

         return adjustedPtn.toString();
      }
   }

   private void concatSingleDate2TimeInterval(String dtfmt, String datePattern, int field, Map intervalPatterns) {
      DateIntervalInfo.PatternInfo timeItvPtnInfo = (DateIntervalInfo.PatternInfo)intervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
      if (timeItvPtnInfo != null) {
         String timeIntervalPattern = timeItvPtnInfo.getFirstPart() + timeItvPtnInfo.getSecondPart();
         String pattern = SimpleFormatterImpl.formatRawPattern(dtfmt, 2, 2, timeIntervalPattern, datePattern);
         timeItvPtnInfo = DateIntervalInfo.genPatternInfo(pattern, timeItvPtnInfo.firstDateInPtnIsLaterDate());
         intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], timeItvPtnInfo);
      }

   }

   private static boolean fieldExistsInSkeleton(int field, String skeleton) {
      String fieldChar = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
      return skeleton.indexOf(fieldChar) != -1;
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
   }

   /** @deprecated */
   @Deprecated
   public Map getRawPatterns() {
      return this.fIntervalPatterns;
   }

   private static final class SkeletonAndItsBestMatch {
      final String skeleton;
      final String bestMatchSkeleton;

      SkeletonAndItsBestMatch(String skeleton, String bestMatch) {
         this.skeleton = skeleton;
         this.bestMatchSkeleton = bestMatch;
      }
   }

   static final class BestMatchInfo {
      final String bestMatchSkeleton;
      final int bestMatchDistanceInfo;

      BestMatchInfo(String bestSkeleton, int difference) {
         this.bestMatchSkeleton = bestSkeleton;
         this.bestMatchDistanceInfo = difference;
      }
   }
}
