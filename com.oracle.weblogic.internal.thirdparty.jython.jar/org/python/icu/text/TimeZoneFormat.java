package org.python.icu.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SoftCache;
import org.python.icu.impl.TZDBTimeZoneNames;
import org.python.icu.impl.TextTrieMap;
import org.python.icu.impl.TimeZoneGenericNames;
import org.python.icu.impl.TimeZoneNamesImpl;
import org.python.icu.impl.ZoneMeta;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.Calendar;
import org.python.icu.util.Freezable;
import org.python.icu.util.Output;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;

public class TimeZoneFormat extends UFormat implements Freezable, Serializable {
   private static final long serialVersionUID = 2281246852693575022L;
   private static final int ISO_Z_STYLE_FLAG = 128;
   private static final int ISO_LOCAL_STYLE_FLAG = 256;
   private ULocale _locale;
   private TimeZoneNames _tznames;
   private String _gmtPattern;
   private String[] _gmtOffsetPatterns;
   private String[] _gmtOffsetDigits;
   private String _gmtZeroFormat;
   private boolean _parseAllStyles;
   private boolean _parseTZDBNames;
   private transient volatile TimeZoneGenericNames _gnames;
   private transient String _gmtPatternPrefix;
   private transient String _gmtPatternSuffix;
   private transient Object[][] _gmtOffsetPatternItems;
   private transient boolean _abuttingOffsetHoursAndMinutes;
   private transient String _region;
   private transient volatile boolean _frozen;
   private transient volatile TimeZoneNames _tzdbNames;
   private static final String TZID_GMT = "Etc/GMT";
   private static final String[] ALT_GMT_STRINGS = new String[]{"GMT", "UTC", "UT"};
   private static final String DEFAULT_GMT_PATTERN = "GMT{0}";
   private static final String DEFAULT_GMT_ZERO = "GMT";
   private static final String[] DEFAULT_GMT_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
   private static final char DEFAULT_GMT_OFFSET_SEP = ':';
   private static final String ASCII_DIGITS = "0123456789";
   private static final String ISO8601_UTC = "Z";
   private static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
   private static final String UNKNOWN_SHORT_ZONE_ID = "unk";
   private static final String UNKNOWN_LOCATION = "Unknown";
   private static final GMTOffsetPatternType[] PARSE_GMT_OFFSET_TYPES;
   private static final int MILLIS_PER_HOUR = 3600000;
   private static final int MILLIS_PER_MINUTE = 60000;
   private static final int MILLIS_PER_SECOND = 1000;
   private static final int MAX_OFFSET = 86400000;
   private static final int MAX_OFFSET_HOUR = 23;
   private static final int MAX_OFFSET_MINUTE = 59;
   private static final int MAX_OFFSET_SECOND = 59;
   private static final int UNKNOWN_OFFSET = Integer.MAX_VALUE;
   private static TimeZoneFormatCache _tzfCache;
   private static final EnumSet ALL_SIMPLE_NAME_TYPES;
   private static final EnumSet ALL_GENERIC_NAME_TYPES;
   private static volatile TextTrieMap ZONE_ID_TRIE;
   private static volatile TextTrieMap SHORT_ZONE_ID_TRIE;
   private static final ObjectStreamField[] serialPersistentFields;

   protected TimeZoneFormat(ULocale locale) {
      this._locale = locale;
      this._tznames = TimeZoneNames.getInstance(locale);
      String gmtPattern = null;
      String hourFormats = null;
      this._gmtZeroFormat = "GMT";

      try {
         ICUResourceBundle bundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/zone", locale);

         try {
            gmtPattern = bundle.getStringWithFallback("zoneStrings/gmtFormat");
         } catch (MissingResourceException var11) {
         }

         try {
            hourFormats = bundle.getStringWithFallback("zoneStrings/hourFormat");
         } catch (MissingResourceException var10) {
         }

         try {
            this._gmtZeroFormat = bundle.getStringWithFallback("zoneStrings/gmtZeroFormat");
         } catch (MissingResourceException var9) {
         }
      } catch (MissingResourceException var12) {
      }

      if (gmtPattern == null) {
         gmtPattern = "GMT{0}";
      }

      this.initGMTPattern(gmtPattern);
      String[] gmtOffsetPatterns = new String[TimeZoneFormat.GMTOffsetPatternType.values().length];
      if (hourFormats != null) {
         String[] hourPatterns = hourFormats.split(";", 2);
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_H.ordinal()] = truncateOffsetPattern(hourPatterns[0]);
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM.ordinal()] = hourPatterns[0];
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HMS.ordinal()] = expandOffsetPattern(hourPatterns[0]);
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_H.ordinal()] = truncateOffsetPattern(hourPatterns[1]);
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM.ordinal()] = hourPatterns[1];
         gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HMS.ordinal()] = expandOffsetPattern(hourPatterns[1]);
      } else {
         GMTOffsetPatternType[] var14 = TimeZoneFormat.GMTOffsetPatternType.values();
         int var6 = var14.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            GMTOffsetPatternType patType = var14[var7];
            gmtOffsetPatterns[patType.ordinal()] = patType.defaultPattern();
         }
      }

      this.initGMTOffsetPatterns(gmtOffsetPatterns);
      this._gmtOffsetDigits = DEFAULT_GMT_DIGITS;
      NumberingSystem ns = NumberingSystem.getInstance(locale);
      if (!ns.isAlgorithmic()) {
         this._gmtOffsetDigits = toCodePoints(ns.getDescription());
      }

   }

   public static TimeZoneFormat getInstance(ULocale locale) {
      if (locale == null) {
         throw new NullPointerException("locale is null");
      } else {
         return (TimeZoneFormat)_tzfCache.getInstance(locale, locale);
      }
   }

   public static TimeZoneFormat getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public TimeZoneNames getTimeZoneNames() {
      return this._tznames;
   }

   private TimeZoneGenericNames getTimeZoneGenericNames() {
      if (this._gnames == null) {
         synchronized(this) {
            if (this._gnames == null) {
               this._gnames = TimeZoneGenericNames.getInstance(this._locale);
            }
         }
      }

      return this._gnames;
   }

   private TimeZoneNames getTZDBTimeZoneNames() {
      if (this._tzdbNames == null) {
         synchronized(this) {
            if (this._tzdbNames == null) {
               this._tzdbNames = new TZDBTimeZoneNames(this._locale);
            }
         }
      }

      return this._tzdbNames;
   }

   public TimeZoneFormat setTimeZoneNames(TimeZoneNames tznames) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else {
         this._tznames = tznames;
         this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
         return this;
      }
   }

   public String getGMTPattern() {
      return this._gmtPattern;
   }

   public TimeZoneFormat setGMTPattern(String pattern) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else {
         this.initGMTPattern(pattern);
         return this;
      }
   }

   public String getGMTOffsetPattern(GMTOffsetPatternType type) {
      return this._gmtOffsetPatterns[type.ordinal()];
   }

   public TimeZoneFormat setGMTOffsetPattern(GMTOffsetPatternType type, String pattern) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else if (pattern == null) {
         throw new NullPointerException("Null GMT offset pattern");
      } else {
         Object[] parsedItems = parseOffsetPattern(pattern, type.required());
         this._gmtOffsetPatterns[type.ordinal()] = pattern;
         this._gmtOffsetPatternItems[type.ordinal()] = parsedItems;
         this.checkAbuttingHoursAndMinutes();
         return this;
      }
   }

   public String getGMTOffsetDigits() {
      StringBuilder buf = new StringBuilder(this._gmtOffsetDigits.length);
      String[] var2 = this._gmtOffsetDigits;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String digit = var2[var4];
         buf.append(digit);
      }

      return buf.toString();
   }

   public TimeZoneFormat setGMTOffsetDigits(String digits) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else if (digits == null) {
         throw new NullPointerException("Null GMT offset digits");
      } else {
         String[] digitArray = toCodePoints(digits);
         if (digitArray.length != 10) {
            throw new IllegalArgumentException("Length of digits must be 10");
         } else {
            this._gmtOffsetDigits = digitArray;
            return this;
         }
      }
   }

   public String getGMTZeroFormat() {
      return this._gmtZeroFormat;
   }

   public TimeZoneFormat setGMTZeroFormat(String gmtZeroFormat) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else if (gmtZeroFormat == null) {
         throw new NullPointerException("Null GMT zero format");
      } else if (gmtZeroFormat.length() == 0) {
         throw new IllegalArgumentException("Empty GMT zero format");
      } else {
         this._gmtZeroFormat = gmtZeroFormat;
         return this;
      }
   }

   public TimeZoneFormat setDefaultParseOptions(EnumSet options) {
      this._parseAllStyles = options.contains(TimeZoneFormat.ParseOption.ALL_STYLES);
      this._parseTZDBNames = options.contains(TimeZoneFormat.ParseOption.TZ_DATABASE_ABBREVIATIONS);
      return this;
   }

   public EnumSet getDefaultParseOptions() {
      if (this._parseAllStyles && this._parseTZDBNames) {
         return EnumSet.of(TimeZoneFormat.ParseOption.ALL_STYLES, TimeZoneFormat.ParseOption.TZ_DATABASE_ABBREVIATIONS);
      } else if (this._parseAllStyles) {
         return EnumSet.of(TimeZoneFormat.ParseOption.ALL_STYLES);
      } else {
         return this._parseTZDBNames ? EnumSet.of(TimeZoneFormat.ParseOption.TZ_DATABASE_ABBREVIATIONS) : EnumSet.noneOf(ParseOption.class);
      }
   }

   public final String formatOffsetISO8601Basic(int offset, boolean useUtcIndicator, boolean isShort, boolean ignoreSeconds) {
      return this.formatOffsetISO8601(offset, true, useUtcIndicator, isShort, ignoreSeconds);
   }

   public final String formatOffsetISO8601Extended(int offset, boolean useUtcIndicator, boolean isShort, boolean ignoreSeconds) {
      return this.formatOffsetISO8601(offset, false, useUtcIndicator, isShort, ignoreSeconds);
   }

   public String formatOffsetLocalizedGMT(int offset) {
      return this.formatOffsetLocalizedGMT(offset, false);
   }

   public String formatOffsetShortLocalizedGMT(int offset) {
      return this.formatOffsetLocalizedGMT(offset, true);
   }

   public final String format(Style style, TimeZone tz, long date) {
      return this.format(style, tz, date, (Output)null);
   }

   public String format(Style style, TimeZone tz, long date, Output timeType) {
      String result = null;
      if (timeType != null) {
         timeType.value = TimeZoneFormat.TimeType.UNKNOWN;
      }

      boolean noOffsetFormatFallback = false;
      switch (style) {
         case GENERIC_LOCATION:
            result = this.getTimeZoneGenericNames().getGenericLocationName(ZoneMeta.getCanonicalCLDRID(tz));
            break;
         case GENERIC_LONG:
            result = this.getTimeZoneGenericNames().getDisplayName(tz, TimeZoneGenericNames.GenericNameType.LONG, date);
            break;
         case GENERIC_SHORT:
            result = this.getTimeZoneGenericNames().getDisplayName(tz, TimeZoneGenericNames.GenericNameType.SHORT, date);
            break;
         case SPECIFIC_LONG:
            result = this.formatSpecific(tz, TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, date, timeType);
            break;
         case SPECIFIC_SHORT:
            result = this.formatSpecific(tz, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, date, timeType);
            break;
         case ZONE_ID:
            result = tz.getID();
            noOffsetFormatFallback = true;
            break;
         case ZONE_ID_SHORT:
            result = ZoneMeta.getShortID(tz);
            if (result == null) {
               result = "unk";
            }

            noOffsetFormatFallback = true;
            break;
         case EXEMPLAR_LOCATION:
            result = this.formatExemplarLocation(tz);
            noOffsetFormatFallback = true;
      }

      if (result == null && !noOffsetFormatFallback) {
         int[] offsets = new int[]{0, 0};
         tz.getOffset(date, false, offsets);
         int offset = offsets[0] + offsets[1];
         switch (style) {
            case GENERIC_LOCATION:
            case GENERIC_LONG:
            case SPECIFIC_LONG:
            case LOCALIZED_GMT:
               result = this.formatOffsetLocalizedGMT(offset);
               break;
            case GENERIC_SHORT:
            case SPECIFIC_SHORT:
            case LOCALIZED_GMT_SHORT:
               result = this.formatOffsetShortLocalizedGMT(offset);
               break;
            case ZONE_ID:
            case ZONE_ID_SHORT:
            case EXEMPLAR_LOCATION:
            default:
               assert false;
               break;
            case ISO_BASIC_SHORT:
               result = this.formatOffsetISO8601Basic(offset, true, true, true);
               break;
            case ISO_BASIC_LOCAL_SHORT:
               result = this.formatOffsetISO8601Basic(offset, false, true, true);
               break;
            case ISO_BASIC_FIXED:
               result = this.formatOffsetISO8601Basic(offset, true, false, true);
               break;
            case ISO_BASIC_LOCAL_FIXED:
               result = this.formatOffsetISO8601Basic(offset, false, false, true);
               break;
            case ISO_BASIC_FULL:
               result = this.formatOffsetISO8601Basic(offset, true, false, false);
               break;
            case ISO_BASIC_LOCAL_FULL:
               result = this.formatOffsetISO8601Basic(offset, false, false, false);
               break;
            case ISO_EXTENDED_FIXED:
               result = this.formatOffsetISO8601Extended(offset, true, false, true);
               break;
            case ISO_EXTENDED_LOCAL_FIXED:
               result = this.formatOffsetISO8601Extended(offset, false, false, true);
               break;
            case ISO_EXTENDED_FULL:
               result = this.formatOffsetISO8601Extended(offset, true, false, false);
               break;
            case ISO_EXTENDED_LOCAL_FULL:
               result = this.formatOffsetISO8601Extended(offset, false, false, false);
         }

         if (timeType != null) {
            timeType.value = offsets[1] != 0 ? TimeZoneFormat.TimeType.DAYLIGHT : TimeZoneFormat.TimeType.STANDARD;
         }
      }

      assert result != null;

      return result;
   }

   public final int parseOffsetISO8601(String text, ParsePosition pos) {
      return parseOffsetISO8601(text, pos, false, (Output)null);
   }

   public int parseOffsetLocalizedGMT(String text, ParsePosition pos) {
      return this.parseOffsetLocalizedGMT(text, pos, false, (Output)null);
   }

   public int parseOffsetShortLocalizedGMT(String text, ParsePosition pos) {
      return this.parseOffsetLocalizedGMT(text, pos, true, (Output)null);
   }

   public TimeZone parse(Style style, String text, ParsePosition pos, EnumSet options, Output timeType) {
      if (timeType == null) {
         timeType = new Output(TimeZoneFormat.TimeType.UNKNOWN);
      } else {
         timeType.value = TimeZoneFormat.TimeType.UNKNOWN;
      }

      int startIdx = pos.getIndex();
      int maxPos = text.length();
      boolean fallbackLocalizedGMT = style == TimeZoneFormat.Style.SPECIFIC_LONG || style == TimeZoneFormat.Style.GENERIC_LONG || style == TimeZoneFormat.Style.GENERIC_LOCATION;
      boolean fallbackShortLocalizedGMT = style == TimeZoneFormat.Style.SPECIFIC_SHORT || style == TimeZoneFormat.Style.GENERIC_SHORT;
      int evaluated = 0;
      ParsePosition tmpPos = new ParsePosition(startIdx);
      int parsedOffset = Integer.MAX_VALUE;
      int parsedPos = -1;
      int offset;
      if (fallbackLocalizedGMT || fallbackShortLocalizedGMT) {
         Output hasDigitOffset = new Output(false);
         offset = this.parseOffsetLocalizedGMT(text, tmpPos, fallbackShortLocalizedGMT, hasDigitOffset);
         if (tmpPos.getErrorIndex() == -1) {
            if (tmpPos.getIndex() == maxPos || (Boolean)hasDigitOffset.value) {
               pos.setIndex(tmpPos.getIndex());
               return this.getTimeZoneForOffset(offset);
            }

            parsedOffset = offset;
            parsedPos = tmpPos.getIndex();
         }

         evaluated |= TimeZoneFormat.Style.LOCALIZED_GMT.flag | TimeZoneFormat.Style.LOCALIZED_GMT_SHORT.flag;
      }

      boolean parseTZDBAbbrev = options == null ? this.getDefaultParseOptions().contains(TimeZoneFormat.ParseOption.TZ_DATABASE_ABBREVIATIONS) : options.contains(TimeZoneFormat.ParseOption.TZ_DATABASE_ABBREVIATIONS);
      Output hasDigitOffset;
      Iterator parsedTZ;
      TimeZoneNames.MatchInfo tzdbNameMatch;
      String parsedID;
      EnumSet nameTypes;
      switch (style) {
         case GENERIC_LOCATION:
         case GENERIC_LONG:
         case GENERIC_SHORT:
            nameTypes = null;
            switch (style) {
               case GENERIC_LOCATION:
                  nameTypes = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION);
                  break;
               case GENERIC_LONG:
                  nameTypes = EnumSet.of(TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.LOCATION);
                  break;
               case GENERIC_SHORT:
                  nameTypes = EnumSet.of(TimeZoneGenericNames.GenericNameType.SHORT, TimeZoneGenericNames.GenericNameType.LOCATION);
                  break;
               default:
                  assert false;
            }

            TimeZoneGenericNames.GenericMatchInfo bestGeneric = this.getTimeZoneGenericNames().findBestMatch(text, startIdx, nameTypes);
            if (bestGeneric != null && startIdx + bestGeneric.matchLength() > parsedPos) {
               timeType.value = bestGeneric.timeType();
               pos.setIndex(startIdx + bestGeneric.matchLength());
               return TimeZone.getTimeZone(bestGeneric.tzID());
            }
            break;
         case SPECIFIC_LONG:
         case SPECIFIC_SHORT:
            hasDigitOffset = null;
            if (style == TimeZoneFormat.Style.SPECIFIC_LONG) {
               nameTypes = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT);
            } else {
               assert style == TimeZoneFormat.Style.SPECIFIC_SHORT;

               nameTypes = EnumSet.of(TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT);
            }

            Collection specificMatches = this._tznames.find(text, startIdx, nameTypes);
            if (specificMatches != null) {
               TimeZoneNames.MatchInfo specificMatch = null;
               parsedTZ = specificMatches.iterator();

               while(parsedTZ.hasNext()) {
                  tzdbNameMatch = (TimeZoneNames.MatchInfo)parsedTZ.next();
                  if (startIdx + tzdbNameMatch.matchLength() > parsedPos) {
                     specificMatch = tzdbNameMatch;
                     parsedPos = startIdx + tzdbNameMatch.matchLength();
                  }
               }

               if (specificMatch != null) {
                  timeType.value = this.getTimeType(specificMatch.nameType());
                  pos.setIndex(parsedPos);
                  return TimeZone.getTimeZone(this.getTimeZoneID(specificMatch.tzID(), specificMatch.mzID()));
               }
            }

            if (parseTZDBAbbrev && style == TimeZoneFormat.Style.SPECIFIC_SHORT) {
               assert nameTypes.contains(TimeZoneNames.NameType.SHORT_STANDARD);

               assert nameTypes.contains(TimeZoneNames.NameType.SHORT_DAYLIGHT);

               Collection tzdbNameMatches = this.getTZDBTimeZoneNames().find(text, startIdx, nameTypes);
               if (tzdbNameMatches != null) {
                  TimeZoneNames.MatchInfo tzdbNameMatch = null;
                  Iterator var34 = tzdbNameMatches.iterator();

                  while(var34.hasNext()) {
                     TimeZoneNames.MatchInfo match = (TimeZoneNames.MatchInfo)var34.next();
                     if (startIdx + match.matchLength() > parsedPos) {
                        tzdbNameMatch = match;
                        parsedPos = startIdx + match.matchLength();
                     }
                  }

                  if (tzdbNameMatch != null) {
                     timeType.value = this.getTimeType(tzdbNameMatch.nameType());
                     pos.setIndex(parsedPos);
                     return TimeZone.getTimeZone(this.getTimeZoneID(tzdbNameMatch.tzID(), tzdbNameMatch.mzID()));
                  }
               }
            }
            break;
         case ZONE_ID:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            parsedID = parseZoneID(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return TimeZone.getTimeZone(parsedID);
            }
            break;
         case ZONE_ID_SHORT:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            parsedID = parseShortZoneID(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return TimeZone.getTimeZone(parsedID);
            }
            break;
         case EXEMPLAR_LOCATION:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            parsedID = this.parseExemplarLocation(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return TimeZone.getTimeZone(parsedID);
            }
            break;
         case LOCALIZED_GMT:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            offset = this.parseOffsetLocalizedGMT(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return this.getTimeZoneForOffset(offset);
            }

            evaluated |= TimeZoneFormat.Style.LOCALIZED_GMT_SHORT.flag;
            break;
         case LOCALIZED_GMT_SHORT:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            offset = this.parseOffsetShortLocalizedGMT(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return this.getTimeZoneForOffset(offset);
            }

            evaluated |= TimeZoneFormat.Style.LOCALIZED_GMT.flag;
            break;
         case ISO_BASIC_SHORT:
         case ISO_BASIC_FIXED:
         case ISO_BASIC_FULL:
         case ISO_EXTENDED_FIXED:
         case ISO_EXTENDED_FULL:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            offset = this.parseOffsetISO8601(text, tmpPos);
            if (tmpPos.getErrorIndex() == -1) {
               pos.setIndex(tmpPos.getIndex());
               return this.getTimeZoneForOffset(offset);
            }
            break;
         case ISO_BASIC_LOCAL_SHORT:
         case ISO_BASIC_LOCAL_FIXED:
         case ISO_BASIC_LOCAL_FULL:
         case ISO_EXTENDED_LOCAL_FIXED:
         case ISO_EXTENDED_LOCAL_FULL:
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            hasDigitOffset = new Output(false);
            offset = parseOffsetISO8601(text, tmpPos, false, hasDigitOffset);
            if (tmpPos.getErrorIndex() == -1 && (Boolean)hasDigitOffset.value) {
               pos.setIndex(tmpPos.getIndex());
               return this.getTimeZoneForOffset(offset);
            }
      }

      evaluated |= style.flag;
      if (parsedPos > startIdx) {
         assert parsedOffset != Integer.MAX_VALUE;

         pos.setIndex(parsedPos);
         return this.getTimeZoneForOffset(parsedOffset);
      } else {
         parsedID = null;
         TimeType parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;

         assert parsedPos < 0;

         assert parsedOffset == Integer.MAX_VALUE;

         Output hasDigitOffset;
         if (parsedPos < maxPos && ((evaluated & 128) == 0 || (evaluated & 256) == 0)) {
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            hasDigitOffset = new Output(false);
            offset = parseOffsetISO8601(text, tmpPos, false, hasDigitOffset);
            if (tmpPos.getErrorIndex() == -1) {
               if (tmpPos.getIndex() == maxPos || (Boolean)hasDigitOffset.value) {
                  pos.setIndex(tmpPos.getIndex());
                  return this.getTimeZoneForOffset(offset);
               }

               if (parsedPos < tmpPos.getIndex()) {
                  parsedOffset = offset;
                  parsedID = null;
                  parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;
                  parsedPos = tmpPos.getIndex();

                  assert parsedPos == startIdx + 1;
               }
            }
         }

         if (parsedPos < maxPos && (evaluated & TimeZoneFormat.Style.LOCALIZED_GMT.flag) == 0) {
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            hasDigitOffset = new Output(false);
            offset = this.parseOffsetLocalizedGMT(text, tmpPos, false, hasDigitOffset);
            if (tmpPos.getErrorIndex() == -1) {
               if (tmpPos.getIndex() == maxPos || (Boolean)hasDigitOffset.value) {
                  pos.setIndex(tmpPos.getIndex());
                  return this.getTimeZoneForOffset(offset);
               }

               if (parsedPos < tmpPos.getIndex()) {
                  parsedOffset = offset;
                  parsedID = null;
                  parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;
                  parsedPos = tmpPos.getIndex();
               }
            }
         }

         if (parsedPos < maxPos && (evaluated & TimeZoneFormat.Style.LOCALIZED_GMT_SHORT.flag) == 0) {
            tmpPos.setIndex(startIdx);
            tmpPos.setErrorIndex(-1);
            hasDigitOffset = new Output(false);
            offset = this.parseOffsetLocalizedGMT(text, tmpPos, true, hasDigitOffset);
            if (tmpPos.getErrorIndex() == -1) {
               if (tmpPos.getIndex() == maxPos || (Boolean)hasDigitOffset.value) {
                  pos.setIndex(tmpPos.getIndex());
                  return this.getTimeZoneForOffset(offset);
               }

               if (parsedPos < tmpPos.getIndex()) {
                  parsedOffset = offset;
                  parsedID = null;
                  parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;
                  parsedPos = tmpPos.getIndex();
               }
            }
         }

         boolean parseAllStyles = options == null ? this.getDefaultParseOptions().contains(TimeZoneFormat.ParseOption.ALL_STYLES) : options.contains(TimeZoneFormat.ParseOption.ALL_STYLES);
         if (parseAllStyles) {
            Iterator var22;
            TimeZoneNames.MatchInfo match;
            Collection tzdbNameMatches;
            int matchPos;
            if (parsedPos < maxPos) {
               tzdbNameMatches = this._tznames.find(text, startIdx, ALL_SIMPLE_NAME_TYPES);
               tzdbNameMatch = null;
               matchPos = -1;
               if (tzdbNameMatches != null) {
                  var22 = tzdbNameMatches.iterator();

                  while(var22.hasNext()) {
                     match = (TimeZoneNames.MatchInfo)var22.next();
                     if (startIdx + match.matchLength() > matchPos) {
                        tzdbNameMatch = match;
                        matchPos = startIdx + match.matchLength();
                     }
                  }
               }

               if (parsedPos < matchPos) {
                  parsedPos = matchPos;
                  parsedID = this.getTimeZoneID(tzdbNameMatch.tzID(), tzdbNameMatch.mzID());
                  parsedTimeType = this.getTimeType(tzdbNameMatch.nameType());
                  parsedOffset = Integer.MAX_VALUE;
               }
            }

            if (parseTZDBAbbrev && parsedPos < maxPos && (evaluated & TimeZoneFormat.Style.SPECIFIC_SHORT.flag) == 0) {
               tzdbNameMatches = this.getTZDBTimeZoneNames().find(text, startIdx, ALL_SIMPLE_NAME_TYPES);
               tzdbNameMatch = null;
               matchPos = -1;
               if (tzdbNameMatches != null) {
                  var22 = tzdbNameMatches.iterator();

                  while(var22.hasNext()) {
                     match = (TimeZoneNames.MatchInfo)var22.next();
                     if (startIdx + match.matchLength() > matchPos) {
                        tzdbNameMatch = match;
                        matchPos = startIdx + match.matchLength();
                     }
                  }

                  if (parsedPos < matchPos) {
                     parsedPos = matchPos;
                     parsedID = this.getTimeZoneID(tzdbNameMatch.tzID(), tzdbNameMatch.mzID());
                     parsedTimeType = this.getTimeType(tzdbNameMatch.nameType());
                     parsedOffset = Integer.MAX_VALUE;
                  }
               }
            }

            if (parsedPos < maxPos) {
               TimeZoneGenericNames.GenericMatchInfo genericMatch = this.getTimeZoneGenericNames().findBestMatch(text, startIdx, ALL_GENERIC_NAME_TYPES);
               if (genericMatch != null && parsedPos < startIdx + genericMatch.matchLength()) {
                  parsedPos = startIdx + genericMatch.matchLength();
                  parsedID = genericMatch.tzID();
                  parsedTimeType = genericMatch.timeType();
                  parsedOffset = Integer.MAX_VALUE;
               }
            }

            String id;
            if (parsedPos < maxPos && (evaluated & TimeZoneFormat.Style.ZONE_ID.flag) == 0) {
               tmpPos.setIndex(startIdx);
               tmpPos.setErrorIndex(-1);
               id = parseZoneID(text, tmpPos);
               if (tmpPos.getErrorIndex() == -1 && parsedPos < tmpPos.getIndex()) {
                  parsedPos = tmpPos.getIndex();
                  parsedID = id;
                  parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;
                  parsedOffset = Integer.MAX_VALUE;
               }
            }

            if (parsedPos < maxPos && (evaluated & TimeZoneFormat.Style.ZONE_ID_SHORT.flag) == 0) {
               tmpPos.setIndex(startIdx);
               tmpPos.setErrorIndex(-1);
               id = parseShortZoneID(text, tmpPos);
               if (tmpPos.getErrorIndex() == -1 && parsedPos < tmpPos.getIndex()) {
                  parsedPos = tmpPos.getIndex();
                  parsedID = id;
                  parsedTimeType = TimeZoneFormat.TimeType.UNKNOWN;
                  parsedOffset = Integer.MAX_VALUE;
               }
            }
         }

         if (parsedPos > startIdx) {
            parsedTZ = null;
            TimeZone parsedTZ;
            if (parsedID != null) {
               parsedTZ = TimeZone.getTimeZone(parsedID);
            } else {
               assert parsedOffset != Integer.MAX_VALUE;

               parsedTZ = this.getTimeZoneForOffset(parsedOffset);
            }

            timeType.value = parsedTimeType;
            pos.setIndex(parsedPos);
            return parsedTZ;
         } else {
            pos.setErrorIndex(startIdx);
            return null;
         }
      }
   }

   public TimeZone parse(Style style, String text, ParsePosition pos, Output timeType) {
      return this.parse(style, text, pos, (EnumSet)null, timeType);
   }

   public final TimeZone parse(String text, ParsePosition pos) {
      return this.parse(TimeZoneFormat.Style.GENERIC_LOCATION, text, pos, EnumSet.of(TimeZoneFormat.ParseOption.ALL_STYLES), (Output)null);
   }

   public final TimeZone parse(String text) throws ParseException {
      ParsePosition pos = new ParsePosition(0);
      TimeZone tz = this.parse(text, pos);
      if (pos.getErrorIndex() >= 0) {
         throw new ParseException("Unparseable time zone: \"" + text + "\"", 0);
      } else {
         assert tz != null;

         return tz;
      }
   }

   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
      TimeZone tz = null;
      long date = System.currentTimeMillis();
      if (obj instanceof TimeZone) {
         tz = (TimeZone)obj;
      } else {
         if (!(obj instanceof Calendar)) {
            throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a time zone");
         }

         tz = ((Calendar)obj).getTimeZone();
         date = ((Calendar)obj).getTimeInMillis();
      }

      assert tz != null;

      String result = this.formatOffsetLocalizedGMT(tz.getOffset(date));
      toAppendTo.append(result);
      if (pos.getFieldAttribute() == DateFormat.Field.TIME_ZONE || pos.getField() == 17) {
         pos.setBeginIndex(0);
         pos.setEndIndex(result.length());
      }

      return toAppendTo;
   }

   public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
      StringBuffer toAppendTo = new StringBuffer();
      FieldPosition pos = new FieldPosition(0);
      toAppendTo = this.format(obj, toAppendTo, pos);
      AttributedString as = new AttributedString(toAppendTo.toString());
      as.addAttribute(DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE);
      return as.getIterator();
   }

   public Object parseObject(String source, ParsePosition pos) {
      return this.parse(source, pos);
   }

   private String formatOffsetLocalizedGMT(int offset, boolean isShort) {
      if (offset == 0) {
         return this._gmtZeroFormat;
      } else {
         StringBuilder buf = new StringBuilder();
         boolean positive = true;
         if (offset < 0) {
            offset = -offset;
            positive = false;
         }

         int offsetH = offset / 3600000;
         offset %= 3600000;
         int offsetM = offset / '\uea60';
         offset %= 60000;
         int offsetS = offset / 1000;
         if (offsetH <= 23 && offsetM <= 59 && offsetS <= 59) {
            Object[] offsetPatternItems;
            if (positive) {
               if (offsetS != 0) {
                  offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HMS.ordinal()];
               } else if (offsetM == 0 && isShort) {
                  offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_H.ordinal()];
               } else {
                  offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM.ordinal()];
               }
            } else if (offsetS != 0) {
               offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HMS.ordinal()];
            } else if (offsetM == 0 && isShort) {
               offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_H.ordinal()];
            } else {
               offsetPatternItems = this._gmtOffsetPatternItems[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM.ordinal()];
            }

            buf.append(this._gmtPatternPrefix);
            Object[] var9 = offsetPatternItems;
            int var10 = offsetPatternItems.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Object item = var9[var11];
               if (item instanceof String) {
                  buf.append((String)item);
               } else if (item instanceof GMTOffsetField) {
                  GMTOffsetField field = (GMTOffsetField)item;
                  switch (field.getType()) {
                     case 'H':
                        this.appendOffsetDigits(buf, offsetH, isShort ? 1 : 2);
                        break;
                     case 'm':
                        this.appendOffsetDigits(buf, offsetM, 2);
                        break;
                     case 's':
                        this.appendOffsetDigits(buf, offsetS, 2);
                  }
               }
            }

            buf.append(this._gmtPatternSuffix);
            return buf.toString();
         } else {
            throw new IllegalArgumentException("Offset out of range :" + offset);
         }
      }
   }

   private String formatOffsetISO8601(int offset, boolean isBasic, boolean useUtcIndicator, boolean isShort, boolean ignoreSeconds) {
      int absOffset = offset < 0 ? -offset : offset;
      if (useUtcIndicator && (absOffset < 1000 || ignoreSeconds && absOffset < 60000)) {
         return "Z";
      } else {
         OffsetFields minFields = isShort ? TimeZoneFormat.OffsetFields.H : TimeZoneFormat.OffsetFields.HM;
         OffsetFields maxFields = ignoreSeconds ? TimeZoneFormat.OffsetFields.HM : TimeZoneFormat.OffsetFields.HMS;
         Character sep = isBasic ? null : ':';
         if (absOffset >= 86400000) {
            throw new IllegalArgumentException("Offset out of range :" + offset);
         } else {
            int[] fields = new int[]{absOffset / 3600000, 0, 0};
            absOffset %= 3600000;
            fields[1] = absOffset / '\uea60';
            absOffset %= 60000;
            fields[2] = absOffset / 1000;

            assert fields[0] >= 0 && fields[0] <= 23;

            assert fields[1] >= 0 && fields[1] <= 59;

            assert fields[2] >= 0 && fields[2] <= 59;

            int lastIdx;
            for(lastIdx = maxFields.ordinal(); lastIdx > minFields.ordinal() && fields[lastIdx] == 0; --lastIdx) {
            }

            StringBuilder buf = new StringBuilder();
            char sign = '+';
            int idx;
            if (offset < 0) {
               for(idx = 0; idx <= lastIdx; ++idx) {
                  if (fields[idx] != 0) {
                     sign = '-';
                     break;
                  }
               }
            }

            buf.append(sign);

            for(idx = 0; idx <= lastIdx; ++idx) {
               if (sep != null && idx != 0) {
                  buf.append(sep);
               }

               if (fields[idx] < 10) {
                  buf.append('0');
               }

               buf.append(fields[idx]);
            }

            return buf.toString();
         }
      }
   }

   private String formatSpecific(TimeZone tz, TimeZoneNames.NameType stdType, TimeZoneNames.NameType dstType, long date, Output timeType) {
      assert stdType == TimeZoneNames.NameType.LONG_STANDARD || stdType == TimeZoneNames.NameType.SHORT_STANDARD;

      assert dstType == TimeZoneNames.NameType.LONG_DAYLIGHT || dstType == TimeZoneNames.NameType.SHORT_DAYLIGHT;

      boolean isDaylight = tz.inDaylightTime(new Date(date));
      String name = isDaylight ? this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(tz), dstType, date) : this.getTimeZoneNames().getDisplayName(ZoneMeta.getCanonicalCLDRID(tz), stdType, date);
      if (name != null && timeType != null) {
         timeType.value = isDaylight ? TimeZoneFormat.TimeType.DAYLIGHT : TimeZoneFormat.TimeType.STANDARD;
      }

      return name;
   }

   private String formatExemplarLocation(TimeZone tz) {
      String location = this.getTimeZoneNames().getExemplarLocationName(ZoneMeta.getCanonicalCLDRID(tz));
      if (location == null) {
         location = this.getTimeZoneNames().getExemplarLocationName("Etc/Unknown");
         if (location == null) {
            location = "Unknown";
         }
      }

      return location;
   }

   private String getTimeZoneID(String tzID, String mzID) {
      String id = tzID;
      if (tzID == null) {
         assert mzID != null;

         id = this._tznames.getReferenceZoneID(mzID, this.getTargetRegion());
         if (id == null) {
            throw new IllegalArgumentException("Invalid mzID: " + mzID);
         }
      }

      return id;
   }

   private synchronized String getTargetRegion() {
      if (this._region == null) {
         this._region = this._locale.getCountry();
         if (this._region.length() == 0) {
            ULocale tmp = ULocale.addLikelySubtags(this._locale);
            this._region = tmp.getCountry();
            if (this._region.length() == 0) {
               this._region = "001";
            }
         }
      }

      return this._region;
   }

   private TimeType getTimeType(TimeZoneNames.NameType nameType) {
      switch (nameType) {
         case LONG_STANDARD:
         case SHORT_STANDARD:
            return TimeZoneFormat.TimeType.STANDARD;
         case LONG_DAYLIGHT:
         case SHORT_DAYLIGHT:
            return TimeZoneFormat.TimeType.DAYLIGHT;
         default:
            return TimeZoneFormat.TimeType.UNKNOWN;
      }
   }

   private void initGMTPattern(String gmtPattern) {
      int idx = gmtPattern.indexOf("{0}");
      if (idx < 0) {
         throw new IllegalArgumentException("Bad localized GMT pattern: " + gmtPattern);
      } else {
         this._gmtPattern = gmtPattern;
         this._gmtPatternPrefix = unquote(gmtPattern.substring(0, idx));
         this._gmtPatternSuffix = unquote(gmtPattern.substring(idx + 3));
      }
   }

   private static String unquote(String s) {
      if (s.indexOf(39) < 0) {
         return s;
      } else {
         boolean isPrevQuote = false;
         boolean inQuote = false;
         StringBuilder buf = new StringBuilder();

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '\'') {
               if (isPrevQuote) {
                  buf.append(c);
                  isPrevQuote = false;
               } else {
                  isPrevQuote = true;
               }

               inQuote = !inQuote;
            } else {
               isPrevQuote = false;
               buf.append(c);
            }
         }

         return buf.toString();
      }
   }

   private void initGMTOffsetPatterns(String[] gmtOffsetPatterns) {
      int size = TimeZoneFormat.GMTOffsetPatternType.values().length;
      if (gmtOffsetPatterns.length < size) {
         throw new IllegalArgumentException("Insufficient number of elements in gmtOffsetPatterns");
      } else {
         Object[][] gmtOffsetPatternItems = new Object[size][];
         GMTOffsetPatternType[] var4 = TimeZoneFormat.GMTOffsetPatternType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            GMTOffsetPatternType t = var4[var6];
            int idx = t.ordinal();
            Object[] parsedItems = parseOffsetPattern(gmtOffsetPatterns[idx], t.required());
            gmtOffsetPatternItems[idx] = parsedItems;
         }

         this._gmtOffsetPatterns = new String[size];
         System.arraycopy(gmtOffsetPatterns, 0, this._gmtOffsetPatterns, 0, size);
         this._gmtOffsetPatternItems = gmtOffsetPatternItems;
         this.checkAbuttingHoursAndMinutes();
      }
   }

   private void checkAbuttingHoursAndMinutes() {
      this._abuttingOffsetHoursAndMinutes = false;
      Object[][] var1 = this._gmtOffsetPatternItems;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object[] items = var1[var3];
         boolean afterH = false;
         Object[] var6 = items;
         int var7 = items.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Object item = var6[var8];
            if (item instanceof GMTOffsetField) {
               GMTOffsetField fld = (GMTOffsetField)item;
               if (afterH) {
                  this._abuttingOffsetHoursAndMinutes = true;
               } else if (fld.getType() == 'H') {
                  afterH = true;
               }
            } else if (afterH) {
               break;
            }
         }
      }

   }

   private static Object[] parseOffsetPattern(String pattern, String letters) {
      boolean isPrevQuote = false;
      boolean inQuote = false;
      StringBuilder text = new StringBuilder();
      char itemType = 0;
      int itemLength = 1;
      boolean invalidPattern = false;
      List items = new ArrayList();
      BitSet checkBits = new BitSet(letters.length());

      for(int i = 0; i < pattern.length(); ++i) {
         char ch = pattern.charAt(i);
         if (ch == '\'') {
            if (isPrevQuote) {
               text.append('\'');
               isPrevQuote = false;
            } else {
               isPrevQuote = true;
               if (itemType != 0) {
                  if (!TimeZoneFormat.GMTOffsetField.isValid(itemType, itemLength)) {
                     invalidPattern = true;
                     break;
                  }

                  items.add(new GMTOffsetField(itemType, itemLength));
                  itemType = 0;
               }
            }

            inQuote = !inQuote;
         } else {
            isPrevQuote = false;
            if (inQuote) {
               text.append(ch);
            } else {
               int patFieldIdx = letters.indexOf(ch);
               if (patFieldIdx >= 0) {
                  if (ch == itemType) {
                     ++itemLength;
                  } else {
                     if (itemType == 0) {
                        if (text.length() > 0) {
                           items.add(text.toString());
                           text.setLength(0);
                        }
                     } else {
                        if (!TimeZoneFormat.GMTOffsetField.isValid(itemType, itemLength)) {
                           invalidPattern = true;
                           break;
                        }

                        items.add(new GMTOffsetField(itemType, itemLength));
                     }

                     itemType = ch;
                     itemLength = 1;
                     checkBits.set(patFieldIdx);
                  }
               } else {
                  if (itemType != 0) {
                     if (!TimeZoneFormat.GMTOffsetField.isValid(itemType, itemLength)) {
                        invalidPattern = true;
                        break;
                     }

                     items.add(new GMTOffsetField(itemType, itemLength));
                     itemType = 0;
                  }

                  text.append(ch);
               }
            }
         }
      }

      if (!invalidPattern) {
         if (itemType == 0) {
            if (text.length() > 0) {
               items.add(text.toString());
               text.setLength(0);
            }
         } else if (TimeZoneFormat.GMTOffsetField.isValid(itemType, itemLength)) {
            items.add(new GMTOffsetField(itemType, itemLength));
         } else {
            invalidPattern = true;
         }
      }

      if (!invalidPattern && checkBits.cardinality() == letters.length()) {
         return items.toArray(new Object[items.size()]);
      } else {
         throw new IllegalStateException("Bad localized GMT offset pattern: " + pattern);
      }
   }

   private static String expandOffsetPattern(String offsetHM) {
      int idx_mm = offsetHM.indexOf("mm");
      if (idx_mm < 0) {
         throw new RuntimeException("Bad time zone hour pattern data");
      } else {
         String sep = ":";
         int idx_H = offsetHM.substring(0, idx_mm).lastIndexOf("H");
         if (idx_H >= 0) {
            sep = offsetHM.substring(idx_H + 1, idx_mm);
         }

         return offsetHM.substring(0, idx_mm + 2) + sep + "ss" + offsetHM.substring(idx_mm + 2);
      }
   }

   private static String truncateOffsetPattern(String offsetHM) {
      int idx_mm = offsetHM.indexOf("mm");
      if (idx_mm < 0) {
         throw new RuntimeException("Bad time zone hour pattern data");
      } else {
         int idx_HH = offsetHM.substring(0, idx_mm).lastIndexOf("HH");
         if (idx_HH >= 0) {
            return offsetHM.substring(0, idx_HH + 2);
         } else {
            int idx_H = offsetHM.substring(0, idx_mm).lastIndexOf("H");
            if (idx_H >= 0) {
               return offsetHM.substring(0, idx_H + 1);
            } else {
               throw new RuntimeException("Bad time zone hour pattern data");
            }
         }
      }
   }

   private void appendOffsetDigits(StringBuilder buf, int n, int minDigits) {
      assert n >= 0 && n < 60;

      int numDigits = n >= 10 ? 2 : 1;

      for(int i = 0; i < minDigits - numDigits; ++i) {
         buf.append(this._gmtOffsetDigits[0]);
      }

      if (numDigits == 2) {
         buf.append(this._gmtOffsetDigits[n / 10]);
      }

      buf.append(this._gmtOffsetDigits[n % 10]);
   }

   private TimeZone getTimeZoneForOffset(int offset) {
      return (TimeZone)(offset == 0 ? TimeZone.getTimeZone("Etc/GMT") : ZoneMeta.getCustomTimeZone(offset));
   }

   private int parseOffsetLocalizedGMT(String text, ParsePosition pos, boolean isShort, Output hasDigitOffset) {
      int start = pos.getIndex();
      int offset = false;
      int[] parsedLength = new int[]{0};
      if (hasDigitOffset != null) {
         hasDigitOffset.value = false;
      }

      int offset = this.parseOffsetLocalizedGMTPattern(text, start, isShort, parsedLength);
      if (parsedLength[0] > 0) {
         if (hasDigitOffset != null) {
            hasDigitOffset.value = true;
         }

         pos.setIndex(start + parsedLength[0]);
         return offset;
      } else {
         offset = this.parseOffsetDefaultLocalizedGMT(text, start, parsedLength);
         if (parsedLength[0] > 0) {
            if (hasDigitOffset != null) {
               hasDigitOffset.value = true;
            }

            pos.setIndex(start + parsedLength[0]);
            return offset;
         } else if (text.regionMatches(true, start, this._gmtZeroFormat, 0, this._gmtZeroFormat.length())) {
            pos.setIndex(start + this._gmtZeroFormat.length());
            return 0;
         } else {
            String[] var8 = ALT_GMT_STRINGS;
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String defGMTZero = var8[var10];
               if (text.regionMatches(true, start, defGMTZero, 0, defGMTZero.length())) {
                  pos.setIndex(start + defGMTZero.length());
                  return 0;
               }
            }

            pos.setErrorIndex(start);
            return 0;
         }
      }
   }

   private int parseOffsetLocalizedGMTPattern(String text, int start, boolean isShort, int[] parsedLen) {
      int idx = start;
      int offset = 0;
      boolean parsed = false;
      int len = this._gmtPatternPrefix.length();
      if (len <= 0 || text.regionMatches(true, start, this._gmtPatternPrefix, 0, len)) {
         idx = start + len;
         int[] offsetLen = new int[1];
         offset = this.parseOffsetFields(text, idx, false, offsetLen);
         if (offsetLen[0] != 0) {
            idx += offsetLen[0];
            len = this._gmtPatternSuffix.length();
            if (len <= 0 || text.regionMatches(true, idx, this._gmtPatternSuffix, 0, len)) {
               idx += len;
               parsed = true;
            }
         }
      }

      parsedLen[0] = parsed ? idx - start : 0;
      return offset;
   }

   private int parseOffsetFields(String text, int start, boolean isShort, int[] parsedLen) {
      int outLen = 0;
      int offset = 0;
      int sign = 1;
      if (parsedLen != null && parsedLen.length >= 1) {
         parsedLen[0] = 0;
      }

      int offsetS = 0;
      int offsetM = 0;
      int offsetH = 0;
      int[] fields = new int[]{0, 0, 0};
      GMTOffsetPatternType[] var12 = PARSE_GMT_OFFSET_TYPES;
      int tmpSign = var12.length;

      for(int var14 = 0; var14 < tmpSign; ++var14) {
         GMTOffsetPatternType gmtPatType = var12[var14];
         Object[] items = this._gmtOffsetPatternItems[gmtPatType.ordinal()];

         assert items != null;

         outLen = this.parseOffsetFieldsWithPattern(text, start, items, false, fields);
         if (outLen > 0) {
            sign = gmtPatType.isPositive() ? 1 : -1;
            offsetH = fields[0];
            offsetM = fields[1];
            offsetS = fields[2];
            break;
         }
      }

      if (outLen > 0 && this._abuttingOffsetHoursAndMinutes) {
         int tmpLen = 0;
         tmpSign = 1;
         GMTOffsetPatternType[] var20 = PARSE_GMT_OFFSET_TYPES;
         int var21 = var20.length;

         for(int var22 = 0; var22 < var21; ++var22) {
            GMTOffsetPatternType gmtPatType = var20[var22];
            Object[] items = this._gmtOffsetPatternItems[gmtPatType.ordinal()];

            assert items != null;

            tmpLen = this.parseOffsetFieldsWithPattern(text, start, items, true, fields);
            if (tmpLen > 0) {
               tmpSign = gmtPatType.isPositive() ? 1 : -1;
               break;
            }
         }

         if (tmpLen > outLen) {
            outLen = tmpLen;
            sign = tmpSign;
            offsetH = fields[0];
            offsetM = fields[1];
            offsetS = fields[2];
         }
      }

      if (parsedLen != null && parsedLen.length >= 1) {
         parsedLen[0] = outLen;
      }

      if (outLen > 0) {
         offset = ((offsetH * 60 + offsetM) * 60 + offsetS) * 1000 * sign;
      }

      return offset;
   }

   private int parseOffsetFieldsWithPattern(String text, int start, Object[] patternItems, boolean forceSingleHourDigit, int[] fields) {
      assert fields != null && fields.length >= 3;

      fields[0] = fields[1] = fields[2] = 0;
      boolean failed = false;
      int offsetS = 0;
      int offsetM = 0;
      int offsetH = 0;
      int idx = start;
      int[] tmpParsedLen = new int[]{0};

      for(int i = 0; i < patternItems.length; ++i) {
         if (patternItems[i] instanceof String) {
            String patStr = (String)patternItems[i];
            int len = patStr.length();
            if (!text.regionMatches(true, idx, patStr, 0, len)) {
               failed = true;
               break;
            }

            idx += len;
         } else {
            assert patternItems[i] instanceof GMTOffsetField;

            GMTOffsetField field = (GMTOffsetField)patternItems[i];
            char fieldType = field.getType();
            if (fieldType == 'H') {
               int maxDigits = forceSingleHourDigit ? 1 : 2;
               offsetH = this.parseOffsetFieldWithLocalizedDigits(text, idx, 1, maxDigits, 0, 23, tmpParsedLen);
            } else if (fieldType == 'm') {
               offsetM = this.parseOffsetFieldWithLocalizedDigits(text, idx, 2, 2, 0, 59, tmpParsedLen);
            } else if (fieldType == 's') {
               offsetS = this.parseOffsetFieldWithLocalizedDigits(text, idx, 2, 2, 0, 59, tmpParsedLen);
            }

            if (tmpParsedLen[0] == 0) {
               failed = true;
               break;
            }

            idx += tmpParsedLen[0];
         }
      }

      if (failed) {
         return 0;
      } else {
         fields[0] = offsetH;
         fields[1] = offsetM;
         fields[2] = offsetS;
         return idx - start;
      }
   }

   private int parseOffsetDefaultLocalizedGMT(String text, int start, int[] parsedLen) {
      int idx = start;
      int offset = 0;
      int parsed = 0;
      int gmtLen = 0;
      String[] var8 = ALT_GMT_STRINGS;
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         String gmt = var8[var10];
         int len = gmt.length();
         if (text.regionMatches(true, idx, gmt, 0, len)) {
            gmtLen = len;
            break;
         }
      }

      if (gmtLen != 0) {
         idx += gmtLen;
         if (idx + 1 < text.length()) {
            label45: {
               int sign = true;
               char c = text.charAt(idx);
               byte sign;
               if (c == '+') {
                  sign = 1;
               } else {
                  if (c != '-') {
                     break label45;
                  }

                  sign = -1;
               }

               ++idx;
               int[] lenWithSep = new int[]{0};
               int offsetWithSep = this.parseDefaultOffsetFields(text, idx, ':', lenWithSep);
               if (lenWithSep[0] == text.length() - idx) {
                  offset = offsetWithSep * sign;
                  idx += lenWithSep[0];
               } else {
                  int[] lenAbut = new int[]{0};
                  int offsetAbut = this.parseAbuttingOffsetFields(text, idx, lenAbut);
                  if (lenWithSep[0] > lenAbut[0]) {
                     offset = offsetWithSep * sign;
                     idx += lenWithSep[0];
                  } else {
                     offset = offsetAbut * sign;
                     idx += lenAbut[0];
                  }
               }

               parsed = idx - start;
            }
         }
      }

      parsedLen[0] = parsed;
      return offset;
   }

   private int parseDefaultOffsetFields(String text, int start, char separator, int[] parsedLen) {
      int max = text.length();
      int idx = start;
      int[] len = new int[]{0};
      int hour = false;
      int min = 0;
      int sec = 0;
      int hour = this.parseOffsetFieldWithLocalizedDigits(text, start, 1, 2, 0, 23, len);
      if (len[0] != 0) {
         idx = start + len[0];
         if (idx + 1 < max && text.charAt(idx) == separator) {
            min = this.parseOffsetFieldWithLocalizedDigits(text, idx + 1, 2, 2, 0, 59, len);
            if (len[0] != 0) {
               idx += 1 + len[0];
               if (idx + 1 < max && text.charAt(idx) == separator) {
                  sec = this.parseOffsetFieldWithLocalizedDigits(text, idx + 1, 2, 2, 0, 59, len);
                  if (len[0] != 0) {
                     idx += 1 + len[0];
                  }
               }
            }
         }
      }

      if (idx == start) {
         parsedLen[0] = 0;
         return 0;
      } else {
         parsedLen[0] = idx - start;
         return hour * 3600000 + min * '\uea60' + sec * 1000;
      }
   }

   private int parseAbuttingOffsetFields(String text, int start, int[] parsedLen) {
      int MAXDIGITS = true;
      int[] digits = new int[6];
      int[] parsed = new int[6];
      int idx = start;
      int[] len = new int[]{0};
      int numDigits = 0;

      int offset;
      for(offset = 0; offset < 6; ++offset) {
         digits[offset] = this.parseSingleLocalizedDigit(text, idx, len);
         if (digits[offset] < 0) {
            break;
         }

         idx += len[0];
         parsed[offset] = idx - start;
         ++numDigits;
      }

      if (numDigits == 0) {
         parsedLen[0] = 0;
         return 0;
      } else {
         offset = 0;

         while(true) {
            if (numDigits > 0) {
               int hour = 0;
               int min = 0;
               int sec = 0;

               assert numDigits > 0 && numDigits <= 6;

               switch (numDigits) {
                  case 1:
                     hour = digits[0];
                     break;
                  case 2:
                     hour = digits[0] * 10 + digits[1];
                     break;
                  case 3:
                     hour = digits[0];
                     min = digits[1] * 10 + digits[2];
                     break;
                  case 4:
                     hour = digits[0] * 10 + digits[1];
                     min = digits[2] * 10 + digits[3];
                     break;
                  case 5:
                     hour = digits[0];
                     min = digits[1] * 10 + digits[2];
                     sec = digits[3] * 10 + digits[4];
                     break;
                  case 6:
                     hour = digits[0] * 10 + digits[1];
                     min = digits[2] * 10 + digits[3];
                     sec = digits[4] * 10 + digits[5];
               }

               if (hour > 23 || min > 59 || sec > 59) {
                  --numDigits;
                  continue;
               }

               offset = hour * 3600000 + min * '\uea60' + sec * 1000;
               parsedLen[0] = parsed[numDigits - 1];
            }

            return offset;
         }
      }
   }

   private int parseOffsetFieldWithLocalizedDigits(String text, int start, int minDigits, int maxDigits, int minVal, int maxVal, int[] parsedLen) {
      parsedLen[0] = 0;
      int decVal = 0;
      int numDigits = 0;
      int idx = start;

      for(int[] digitLen = new int[]{0}; idx < text.length() && numDigits < maxDigits; idx += digitLen[0]) {
         int digit = this.parseSingleLocalizedDigit(text, idx, digitLen);
         if (digit < 0) {
            break;
         }

         int tmpVal = decVal * 10 + digit;
         if (tmpVal > maxVal) {
            break;
         }

         decVal = tmpVal;
         ++numDigits;
      }

      if (numDigits >= minDigits && decVal >= minVal) {
         parsedLen[0] = idx - start;
      } else {
         decVal = -1;
         int numDigits = false;
      }

      return decVal;
   }

   private int parseSingleLocalizedDigit(String text, int start, int[] len) {
      int digit = -1;
      len[0] = 0;
      if (start < text.length()) {
         int cp = Character.codePointAt(text, start);

         for(int i = 0; i < this._gmtOffsetDigits.length; ++i) {
            if (cp == this._gmtOffsetDigits[i].codePointAt(0)) {
               digit = i;
               break;
            }
         }

         if (digit < 0) {
            digit = UCharacter.digit(cp);
         }

         if (digit >= 0) {
            len[0] = Character.charCount(cp);
         }
      }

      return digit;
   }

   private static String[] toCodePoints(String str) {
      int len = str.codePointCount(0, str.length());
      String[] codePoints = new String[len];
      int i = 0;

      for(int offset = 0; i < len; ++i) {
         int code = str.codePointAt(offset);
         int codeLen = Character.charCount(code);
         codePoints[i] = str.substring(offset, offset + codeLen);
         offset += codeLen;
      }

      return codePoints;
   }

   private static int parseOffsetISO8601(String text, ParsePosition pos, boolean extendedOnly, Output hasDigitOffset) {
      if (hasDigitOffset != null) {
         hasDigitOffset.value = false;
      }

      int start = pos.getIndex();
      if (start >= text.length()) {
         pos.setErrorIndex(start);
         return 0;
      } else {
         char firstChar = text.charAt(start);
         if (Character.toUpperCase(firstChar) == "Z".charAt(0)) {
            pos.setIndex(start + 1);
            return 0;
         } else {
            byte sign;
            if (firstChar == '+') {
               sign = 1;
            } else {
               if (firstChar != '-') {
                  pos.setErrorIndex(start);
                  return 0;
               }

               sign = -1;
            }

            ParsePosition posOffset = new ParsePosition(start + 1);
            int offset = parseAsciiOffsetFields(text, posOffset, ':', TimeZoneFormat.OffsetFields.H, TimeZoneFormat.OffsetFields.HMS);
            if (posOffset.getErrorIndex() == -1 && !extendedOnly && posOffset.getIndex() - start <= 3) {
               ParsePosition posBasic = new ParsePosition(start + 1);
               int tmpOffset = parseAbuttingAsciiOffsetFields(text, posBasic, TimeZoneFormat.OffsetFields.H, TimeZoneFormat.OffsetFields.HMS, false);
               if (posBasic.getErrorIndex() == -1 && posBasic.getIndex() > posOffset.getIndex()) {
                  offset = tmpOffset;
                  posOffset.setIndex(posBasic.getIndex());
               }
            }

            if (posOffset.getErrorIndex() != -1) {
               pos.setErrorIndex(start);
               return 0;
            } else {
               pos.setIndex(posOffset.getIndex());
               if (hasDigitOffset != null) {
                  hasDigitOffset.value = true;
               }

               return sign * offset;
            }
         }
      }
   }

   private static int parseAbuttingAsciiOffsetFields(String text, ParsePosition pos, OffsetFields minFields, OffsetFields maxFields, boolean fixedHourWidth) {
      int start = pos.getIndex();
      int minDigits = 2 * (minFields.ordinal() + 1) - (fixedHourWidth ? 0 : 1);
      int maxDigits = 2 * (maxFields.ordinal() + 1);
      int[] digits = new int[maxDigits];
      int numDigits = 0;

      int hour;
      for(int idx = start; numDigits < digits.length && idx < text.length(); ++idx) {
         hour = "0123456789".indexOf(text.charAt(idx));
         if (hour < 0) {
            break;
         }

         digits[numDigits] = hour;
         ++numDigits;
      }

      if (fixedHourWidth && (numDigits & 1) != 0) {
         --numDigits;
      }

      if (numDigits < minDigits) {
         pos.setErrorIndex(start);
         return 0;
      } else {
         hour = 0;
         int min = 0;
         int sec = 0;

         boolean bParsed;
         for(bParsed = false; numDigits >= minDigits; hour = 0) {
            switch (numDigits) {
               case 1:
                  hour = digits[0];
                  break;
               case 2:
                  hour = digits[0] * 10 + digits[1];
                  break;
               case 3:
                  hour = digits[0];
                  min = digits[1] * 10 + digits[2];
                  break;
               case 4:
                  hour = digits[0] * 10 + digits[1];
                  min = digits[2] * 10 + digits[3];
                  break;
               case 5:
                  hour = digits[0];
                  min = digits[1] * 10 + digits[2];
                  sec = digits[3] * 10 + digits[4];
                  break;
               case 6:
                  hour = digits[0] * 10 + digits[1];
                  min = digits[2] * 10 + digits[3];
                  sec = digits[4] * 10 + digits[5];
            }

            if (hour <= 23 && min <= 59 && sec <= 59) {
               bParsed = true;
               break;
            }

            numDigits -= fixedHourWidth ? 2 : 1;
            sec = 0;
            min = 0;
         }

         if (!bParsed) {
            pos.setErrorIndex(start);
            return 0;
         } else {
            pos.setIndex(start + numDigits);
            return ((hour * 60 + min) * 60 + sec) * 1000;
         }
      }
   }

   private static int parseAsciiOffsetFields(String text, ParsePosition pos, char sep, OffsetFields minFields, OffsetFields maxFields) {
      int start = pos.getIndex();
      int[] fieldVal = new int[]{0, 0, 0};
      int[] fieldLen = new int[]{0, -1, -1};
      int offset = start;

      int parsedLen;
      for(parsedLen = 0; offset < text.length() && parsedLen <= maxFields.ordinal(); ++offset) {
         char c = text.charAt(offset);
         if (c == sep) {
            if (parsedLen == 0) {
               if (fieldLen[0] == 0) {
                  break;
               }

               ++parsedLen;
            } else {
               if (fieldLen[parsedLen] != -1) {
                  break;
               }

               fieldLen[parsedLen] = 0;
            }
         } else {
            if (fieldLen[parsedLen] == -1) {
               break;
            }

            int digit = "0123456789".indexOf(c);
            if (digit < 0) {
               break;
            }

            fieldVal[parsedLen] = fieldVal[parsedLen] * 10 + digit;
            int var10002 = fieldLen[parsedLen]++;
            if (fieldLen[parsedLen] >= 2) {
               ++parsedLen;
            }
         }
      }

      offset = 0;
      parsedLen = 0;
      OffsetFields parsedFields = null;
      if (fieldLen[0] != 0) {
         if (fieldVal[0] > 23) {
            offset = fieldVal[0] / 10 * 3600000;
            parsedFields = TimeZoneFormat.OffsetFields.H;
            parsedLen = 1;
         } else {
            offset = fieldVal[0] * 3600000;
            parsedLen = fieldLen[0];
            parsedFields = TimeZoneFormat.OffsetFields.H;
            if (fieldLen[1] == 2 && fieldVal[1] <= 59) {
               offset += fieldVal[1] * '\uea60';
               parsedLen += 1 + fieldLen[1];
               parsedFields = TimeZoneFormat.OffsetFields.HM;
               if (fieldLen[2] == 2 && fieldVal[2] <= 59) {
                  offset += fieldVal[2] * 1000;
                  parsedLen += 1 + fieldLen[2];
                  parsedFields = TimeZoneFormat.OffsetFields.HMS;
               }
            }
         }
      }

      if (parsedFields != null && parsedFields.ordinal() >= minFields.ordinal()) {
         pos.setIndex(start + parsedLen);
         return offset;
      } else {
         pos.setErrorIndex(start);
         return 0;
      }
   }

   private static String parseZoneID(String text, ParsePosition pos) {
      String resolvedID = null;
      if (ZONE_ID_TRIE == null) {
         Class var3 = TimeZoneFormat.class;
         synchronized(TimeZoneFormat.class) {
            if (ZONE_ID_TRIE == null) {
               TextTrieMap trie = new TextTrieMap(true);
               String[] ids = TimeZone.getAvailableIDs();
               String[] var6 = ids;
               int var7 = ids.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String id = var6[var8];
                  trie.put(id, id);
               }

               ZONE_ID_TRIE = trie;
            }
         }
      }

      int[] matchLen = new int[]{0};
      Iterator itr = ZONE_ID_TRIE.get(text, pos.getIndex(), matchLen);
      if (itr != null) {
         resolvedID = (String)itr.next();
         pos.setIndex(pos.getIndex() + matchLen[0]);
      } else {
         pos.setErrorIndex(pos.getIndex());
      }

      return resolvedID;
   }

   private static String parseShortZoneID(String text, ParsePosition pos) {
      String resolvedID = null;
      if (SHORT_ZONE_ID_TRIE == null) {
         Class var3 = TimeZoneFormat.class;
         synchronized(TimeZoneFormat.class) {
            if (SHORT_ZONE_ID_TRIE == null) {
               TextTrieMap trie = new TextTrieMap(true);
               Set canonicalIDs = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, (String)null, (Integer)null);
               Iterator var6 = canonicalIDs.iterator();

               while(var6.hasNext()) {
                  String id = (String)var6.next();
                  String shortID = ZoneMeta.getShortID(id);
                  if (shortID != null) {
                     trie.put(shortID, id);
                  }
               }

               trie.put("unk", "Etc/Unknown");
               SHORT_ZONE_ID_TRIE = trie;
            }
         }
      }

      int[] matchLen = new int[]{0};
      Iterator itr = SHORT_ZONE_ID_TRIE.get(text, pos.getIndex(), matchLen);
      if (itr != null) {
         resolvedID = (String)itr.next();
         pos.setIndex(pos.getIndex() + matchLen[0]);
      } else {
         pos.setErrorIndex(pos.getIndex());
      }

      return resolvedID;
   }

   private String parseExemplarLocation(String text, ParsePosition pos) {
      int startIdx = pos.getIndex();
      int parsedPos = -1;
      String tzID = null;
      EnumSet nameTypes = EnumSet.of(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
      Collection exemplarMatches = this._tznames.find(text, startIdx, nameTypes);
      if (exemplarMatches != null) {
         TimeZoneNames.MatchInfo exemplarMatch = null;
         Iterator var9 = exemplarMatches.iterator();

         while(var9.hasNext()) {
            TimeZoneNames.MatchInfo match = (TimeZoneNames.MatchInfo)var9.next();
            if (startIdx + match.matchLength() > parsedPos) {
               exemplarMatch = match;
               parsedPos = startIdx + match.matchLength();
            }
         }

         if (exemplarMatch != null) {
            tzID = this.getTimeZoneID(exemplarMatch.tzID(), exemplarMatch.mzID());
            pos.setIndex(parsedPos);
         }
      }

      if (tzID == null) {
         pos.setErrorIndex(startIdx);
      }

      return tzID;
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      ObjectOutputStream.PutField fields = oos.putFields();
      fields.put("_locale", this._locale);
      fields.put("_tznames", this._tznames);
      fields.put("_gmtPattern", this._gmtPattern);
      fields.put("_gmtOffsetPatterns", this._gmtOffsetPatterns);
      fields.put("_gmtOffsetDigits", this._gmtOffsetDigits);
      fields.put("_gmtZeroFormat", this._gmtZeroFormat);
      fields.put("_parseAllStyles", this._parseAllStyles);
      oos.writeFields();
   }

   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
      ObjectInputStream.GetField fields = ois.readFields();
      this._locale = (ULocale)fields.get("_locale", (Object)null);
      if (this._locale == null) {
         throw new InvalidObjectException("Missing field: locale");
      } else {
         this._tznames = (TimeZoneNames)fields.get("_tznames", (Object)null);
         if (this._tznames == null) {
            throw new InvalidObjectException("Missing field: tznames");
         } else {
            this._gmtPattern = (String)fields.get("_gmtPattern", (Object)null);
            if (this._gmtPattern == null) {
               throw new InvalidObjectException("Missing field: gmtPattern");
            } else {
               String[] tmpGmtOffsetPatterns = (String[])((String[])fields.get("_gmtOffsetPatterns", (Object)null));
               if (tmpGmtOffsetPatterns == null) {
                  throw new InvalidObjectException("Missing field: gmtOffsetPatterns");
               } else if (tmpGmtOffsetPatterns.length < 4) {
                  throw new InvalidObjectException("Incompatible field: gmtOffsetPatterns");
               } else {
                  this._gmtOffsetPatterns = new String[6];
                  if (tmpGmtOffsetPatterns.length == 4) {
                     for(int i = 0; i < 4; ++i) {
                        this._gmtOffsetPatterns[i] = tmpGmtOffsetPatterns[i];
                     }

                     this._gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_H.ordinal()] = truncateOffsetPattern(this._gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM.ordinal()]);
                     this._gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_H.ordinal()] = truncateOffsetPattern(this._gmtOffsetPatterns[TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM.ordinal()]);
                  } else {
                     this._gmtOffsetPatterns = tmpGmtOffsetPatterns;
                  }

                  this._gmtOffsetDigits = (String[])((String[])fields.get("_gmtOffsetDigits", (Object)null));
                  if (this._gmtOffsetDigits == null) {
                     throw new InvalidObjectException("Missing field: gmtOffsetDigits");
                  } else if (this._gmtOffsetDigits.length != 10) {
                     throw new InvalidObjectException("Incompatible field: gmtOffsetDigits");
                  } else {
                     this._gmtZeroFormat = (String)fields.get("_gmtZeroFormat", (Object)null);
                     if (this._gmtZeroFormat == null) {
                        throw new InvalidObjectException("Missing field: gmtZeroFormat");
                     } else {
                        this._parseAllStyles = fields.get("_parseAllStyles", false);
                        if (fields.defaulted("_parseAllStyles")) {
                           throw new InvalidObjectException("Missing field: parseAllStyles");
                        } else {
                           if (this._tznames instanceof TimeZoneNamesImpl) {
                              this._tznames = TimeZoneNames.getInstance(this._locale);
                              this._gnames = null;
                           } else {
                              this._gnames = new TimeZoneGenericNames(this._locale, this._tznames);
                           }

                           this.initGMTPattern(this._gmtPattern);
                           this.initGMTOffsetPatterns(this._gmtOffsetPatterns);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isFrozen() {
      return this._frozen;
   }

   public TimeZoneFormat freeze() {
      this._frozen = true;
      return this;
   }

   public TimeZoneFormat cloneAsThawed() {
      TimeZoneFormat copy = (TimeZoneFormat)super.clone();
      copy._frozen = false;
      return copy;
   }

   static {
      PARSE_GMT_OFFSET_TYPES = new GMTOffsetPatternType[]{TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HMS, TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HMS, TimeZoneFormat.GMTOffsetPatternType.POSITIVE_HM, TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_HM, TimeZoneFormat.GMTOffsetPatternType.POSITIVE_H, TimeZoneFormat.GMTOffsetPatternType.NEGATIVE_H};
      _tzfCache = new TimeZoneFormatCache();
      ALL_SIMPLE_NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.EXEMPLAR_LOCATION);
      ALL_GENERIC_NAME_TYPES = EnumSet.of(TimeZoneGenericNames.GenericNameType.LOCATION, TimeZoneGenericNames.GenericNameType.LONG, TimeZoneGenericNames.GenericNameType.SHORT);
      serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("_locale", ULocale.class), new ObjectStreamField("_tznames", TimeZoneNames.class), new ObjectStreamField("_gmtPattern", String.class), new ObjectStreamField("_gmtOffsetPatterns", String[].class), new ObjectStreamField("_gmtOffsetDigits", String[].class), new ObjectStreamField("_gmtZeroFormat", String.class), new ObjectStreamField("_parseAllStyles", Boolean.TYPE)};
   }

   private static class TimeZoneFormatCache extends SoftCache {
      private TimeZoneFormatCache() {
      }

      protected TimeZoneFormat createInstance(ULocale key, ULocale data) {
         TimeZoneFormat fmt = new TimeZoneFormat(data);
         fmt.freeze();
         return fmt;
      }

      // $FF: synthetic method
      TimeZoneFormatCache(Object x0) {
         this();
      }
   }

   private static class GMTOffsetField {
      final char _type;
      final int _width;

      GMTOffsetField(char type, int width) {
         this._type = type;
         this._width = width;
      }

      char getType() {
         return this._type;
      }

      int getWidth() {
         return this._width;
      }

      static boolean isValid(char type, int width) {
         return width == 1 || width == 2;
      }
   }

   private static enum OffsetFields {
      H,
      HM,
      HMS;
   }

   public static enum ParseOption {
      ALL_STYLES,
      TZ_DATABASE_ABBREVIATIONS;
   }

   public static enum TimeType {
      UNKNOWN,
      STANDARD,
      DAYLIGHT;
   }

   public static enum GMTOffsetPatternType {
      POSITIVE_HM("+H:mm", "Hm", true),
      POSITIVE_HMS("+H:mm:ss", "Hms", true),
      NEGATIVE_HM("-H:mm", "Hm", false),
      NEGATIVE_HMS("-H:mm:ss", "Hms", false),
      POSITIVE_H("+H", "H", true),
      NEGATIVE_H("-H", "H", false);

      private String _defaultPattern;
      private String _required;
      private boolean _isPositive;

      private GMTOffsetPatternType(String defaultPattern, String required, boolean isPositive) {
         this._defaultPattern = defaultPattern;
         this._required = required;
         this._isPositive = isPositive;
      }

      private String defaultPattern() {
         return this._defaultPattern;
      }

      private String required() {
         return this._required;
      }

      private boolean isPositive() {
         return this._isPositive;
      }
   }

   public static enum Style {
      GENERIC_LOCATION(1),
      GENERIC_LONG(2),
      GENERIC_SHORT(4),
      SPECIFIC_LONG(8),
      SPECIFIC_SHORT(16),
      LOCALIZED_GMT(32),
      LOCALIZED_GMT_SHORT(64),
      ISO_BASIC_SHORT(128),
      ISO_BASIC_LOCAL_SHORT(256),
      ISO_BASIC_FIXED(128),
      ISO_BASIC_LOCAL_FIXED(256),
      ISO_BASIC_FULL(128),
      ISO_BASIC_LOCAL_FULL(256),
      ISO_EXTENDED_FIXED(128),
      ISO_EXTENDED_LOCAL_FIXED(256),
      ISO_EXTENDED_FULL(128),
      ISO_EXTENDED_LOCAL_FULL(256),
      ZONE_ID(512),
      ZONE_ID_SHORT(1024),
      EXEMPLAR_LOCATION(2048);

      final int flag;

      private Style(int flag) {
         this.flag = flag;
      }
   }
}
