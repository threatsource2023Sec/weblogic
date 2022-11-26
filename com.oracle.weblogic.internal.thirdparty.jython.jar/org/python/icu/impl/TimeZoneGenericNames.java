package org.python.icu.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.python.icu.text.LocaleDisplayNames;
import org.python.icu.text.TimeZoneFormat;
import org.python.icu.text.TimeZoneNames;
import org.python.icu.util.BasicTimeZone;
import org.python.icu.util.Freezable;
import org.python.icu.util.Output;
import org.python.icu.util.TimeZone;
import org.python.icu.util.TimeZoneTransition;
import org.python.icu.util.ULocale;

public class TimeZoneGenericNames implements Serializable, Freezable {
   private static final long serialVersionUID = 2729910342063468417L;
   private final ULocale _locale;
   private TimeZoneNames _tznames;
   private transient volatile boolean _frozen;
   private transient String _region;
   private transient WeakReference _localeDisplayNamesRef;
   private transient MessageFormat[] _patternFormatters;
   private transient ConcurrentHashMap _genericLocationNamesMap;
   private transient ConcurrentHashMap _genericPartialLocationNamesMap;
   private transient TextTrieMap _gnamesTrie;
   private transient boolean _gnamesTrieFullyLoaded;
   private static Cache GENERIC_NAMES_CACHE = new Cache();
   private static final long DST_CHECK_RANGE = 15897600000L;
   private static final TimeZoneNames.NameType[] GENERIC_NON_LOCATION_TYPES;

   public TimeZoneGenericNames(ULocale locale, TimeZoneNames tznames) {
      this._locale = locale;
      this._tznames = tznames;
      this.init();
   }

   private void init() {
      if (this._tznames == null) {
         this._tznames = TimeZoneNames.getInstance(this._locale);
      }

      this._genericLocationNamesMap = new ConcurrentHashMap();
      this._genericPartialLocationNamesMap = new ConcurrentHashMap();
      this._gnamesTrie = new TextTrieMap(true);
      this._gnamesTrieFullyLoaded = false;
      TimeZone tz = TimeZone.getDefault();
      String tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
      if (tzCanonicalID != null) {
         this.loadStrings(tzCanonicalID);
      }

   }

   private TimeZoneGenericNames(ULocale locale) {
      this(locale, (TimeZoneNames)null);
   }

   public static TimeZoneGenericNames getInstance(ULocale locale) {
      String key = locale.getBaseName();
      return (TimeZoneGenericNames)GENERIC_NAMES_CACHE.getInstance(key, locale);
   }

   public String getDisplayName(TimeZone tz, GenericNameType type, long date) {
      String name = null;
      String tzCanonicalID = null;
      switch (type) {
         case LOCATION:
            tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
            if (tzCanonicalID != null) {
               name = this.getGenericLocationName(tzCanonicalID);
            }
            break;
         case LONG:
         case SHORT:
            name = this.formatGenericNonLocationName(tz, type, date);
            if (name == null) {
               tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
               if (tzCanonicalID != null) {
                  name = this.getGenericLocationName(tzCanonicalID);
               }
            }
      }

      return name;
   }

   public String getGenericLocationName(String canonicalTzID) {
      if (canonicalTzID != null && canonicalTzID.length() != 0) {
         String name = (String)this._genericLocationNamesMap.get(canonicalTzID);
         if (name != null) {
            return name.length() == 0 ? null : name;
         } else {
            Output isPrimary = new Output();
            String countryCode = ZoneMeta.getCanonicalCountry(canonicalTzID, isPrimary);
            if (countryCode != null) {
               String country;
               if ((Boolean)isPrimary.value) {
                  country = this.getLocaleDisplayNames().regionDisplayName(countryCode);
                  name = this.formatPattern(TimeZoneGenericNames.Pattern.REGION_FORMAT, country);
               } else {
                  country = this._tznames.getExemplarLocationName(canonicalTzID);
                  name = this.formatPattern(TimeZoneGenericNames.Pattern.REGION_FORMAT, country);
               }
            }

            if (name == null) {
               this._genericLocationNamesMap.putIfAbsent(canonicalTzID.intern(), "");
            } else {
               synchronized(this) {
                  canonicalTzID = canonicalTzID.intern();
                  String tmp = (String)this._genericLocationNamesMap.putIfAbsent(canonicalTzID, name.intern());
                  if (tmp == null) {
                     NameInfo info = new NameInfo(canonicalTzID, TimeZoneGenericNames.GenericNameType.LOCATION);
                     this._gnamesTrie.put(name, info);
                  } else {
                     name = tmp;
                  }
               }
            }

            return name;
         }
      } else {
         return null;
      }
   }

   public TimeZoneGenericNames setFormatPattern(Pattern patType, String patStr) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else {
         if (!this._genericLocationNamesMap.isEmpty()) {
            this._genericLocationNamesMap = new ConcurrentHashMap();
         }

         if (!this._genericPartialLocationNamesMap.isEmpty()) {
            this._genericPartialLocationNamesMap = new ConcurrentHashMap();
         }

         this._gnamesTrie = null;
         this._gnamesTrieFullyLoaded = false;
         if (this._patternFormatters == null) {
            this._patternFormatters = new MessageFormat[TimeZoneGenericNames.Pattern.values().length];
         }

         this._patternFormatters[patType.ordinal()] = new MessageFormat(patStr);
         return this;
      }
   }

   private String formatGenericNonLocationName(TimeZone tz, GenericNameType type, long date) {
      assert type == TimeZoneGenericNames.GenericNameType.LONG || type == TimeZoneGenericNames.GenericNameType.SHORT;

      String tzID = ZoneMeta.getCanonicalCLDRID(tz);
      if (tzID == null) {
         return null;
      } else {
         TimeZoneNames.NameType nameType = type == TimeZoneGenericNames.GenericNameType.LONG ? TimeZoneNames.NameType.LONG_GENERIC : TimeZoneNames.NameType.SHORT_GENERIC;
         String name = this._tznames.getTimeZoneDisplayName(tzID, nameType);
         if (name != null) {
            return name;
         } else {
            String mzID = this._tznames.getMetaZoneID(tzID, date);
            if (mzID != null) {
               boolean useStandard = false;
               int[] offsets = new int[]{0, 0};
               tz.getOffset(date, false, offsets);
               if (offsets[1] == 0) {
                  useStandard = true;
                  if (tz instanceof BasicTimeZone) {
                     BasicTimeZone btz = (BasicTimeZone)tz;
                     TimeZoneTransition before = btz.getPreviousTransition(date, true);
                     if (before != null && date - before.getTime() < 15897600000L && before.getFrom().getDSTSavings() != 0) {
                        useStandard = false;
                     } else {
                        TimeZoneTransition after = btz.getNextTransition(date, false);
                        if (after != null && after.getTime() - date < 15897600000L && after.getTo().getDSTSavings() != 0) {
                           useStandard = false;
                        }
                     }
                  } else {
                     int[] tmpOffsets = new int[2];
                     tz.getOffset(date - 15897600000L, false, tmpOffsets);
                     if (tmpOffsets[1] != 0) {
                        useStandard = false;
                     } else {
                        tz.getOffset(date + 15897600000L, false, tmpOffsets);
                        if (tmpOffsets[1] != 0) {
                           useStandard = false;
                        }
                     }
                  }
               }

               String goldenID;
               if (useStandard) {
                  TimeZoneNames.NameType stdNameType = nameType == TimeZoneNames.NameType.LONG_GENERIC ? TimeZoneNames.NameType.LONG_STANDARD : TimeZoneNames.NameType.SHORT_STANDARD;
                  goldenID = this._tznames.getDisplayName(tzID, stdNameType, date);
                  if (goldenID != null) {
                     name = goldenID;
                     String mzGenericName = this._tznames.getMetaZoneDisplayName(mzID, nameType);
                     if (goldenID.equalsIgnoreCase(mzGenericName)) {
                        name = null;
                     }
                  }
               }

               if (name == null) {
                  String mzName = this._tznames.getMetaZoneDisplayName(mzID, nameType);
                  if (mzName != null) {
                     goldenID = this._tznames.getReferenceZoneID(mzID, this.getTargetRegion());
                     if (goldenID != null && !goldenID.equals(tzID)) {
                        TimeZone goldenZone = TimeZone.getFrozenTimeZone(goldenID);
                        int[] offsets1 = new int[]{0, 0};
                        goldenZone.getOffset(date + (long)offsets[0] + (long)offsets[1], true, offsets1);
                        if (offsets[0] == offsets1[0] && offsets[1] == offsets1[1]) {
                           name = mzName;
                        } else {
                           name = this.getPartialLocationName(tzID, mzID, nameType == TimeZoneNames.NameType.LONG_GENERIC, mzName);
                        }
                     } else {
                        name = mzName;
                     }
                  }
               }
            }

            return name;
         }
      }
   }

   private synchronized String formatPattern(Pattern pat, String... args) {
      if (this._patternFormatters == null) {
         this._patternFormatters = new MessageFormat[TimeZoneGenericNames.Pattern.values().length];
      }

      int idx = pat.ordinal();
      if (this._patternFormatters[idx] == null) {
         String patText;
         try {
            ICUResourceBundle bundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/zone", this._locale);
            patText = bundle.getStringWithFallback("zoneStrings/" + pat.key());
         } catch (MissingResourceException var6) {
            patText = pat.defaultValue();
         }

         this._patternFormatters[idx] = new MessageFormat(patText);
      }

      return this._patternFormatters[idx].format(args);
   }

   private synchronized LocaleDisplayNames getLocaleDisplayNames() {
      LocaleDisplayNames locNames = null;
      if (this._localeDisplayNamesRef != null) {
         locNames = (LocaleDisplayNames)this._localeDisplayNamesRef.get();
      }

      if (locNames == null) {
         locNames = LocaleDisplayNames.getInstance(this._locale);
         this._localeDisplayNamesRef = new WeakReference(locNames);
      }

      return locNames;
   }

   private synchronized void loadStrings(String tzCanonicalID) {
      if (tzCanonicalID != null && tzCanonicalID.length() != 0) {
         this.getGenericLocationName(tzCanonicalID);
         Set mzIDs = this._tznames.getAvailableMetaZoneIDs(tzCanonicalID);
         Iterator var3 = mzIDs.iterator();

         while(true) {
            String mzID;
            String goldenID;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               mzID = (String)var3.next();
               goldenID = this._tznames.getReferenceZoneID(mzID, this.getTargetRegion());
            } while(tzCanonicalID.equals(goldenID));

            TimeZoneNames.NameType[] var6 = GENERIC_NON_LOCATION_TYPES;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               TimeZoneNames.NameType genNonLocType = var6[var8];
               String mzGenName = this._tznames.getMetaZoneDisplayName(mzID, genNonLocType);
               if (mzGenName != null) {
                  this.getPartialLocationName(tzCanonicalID, mzID, genNonLocType == TimeZoneNames.NameType.LONG_GENERIC, mzGenName);
               }
            }
         }
      }
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

   private String getPartialLocationName(String tzID, String mzID, boolean isLong, String mzDisplayName) {
      String letter = isLong ? "L" : "S";
      String key = tzID + "&" + mzID + "#" + letter;
      String name = (String)this._genericPartialLocationNamesMap.get(key);
      if (name != null) {
         return name;
      } else {
         String location = null;
         String countryCode = ZoneMeta.getCanonicalCountry(tzID);
         if (countryCode != null) {
            String regionalGolden = this._tznames.getReferenceZoneID(mzID, countryCode);
            if (tzID.equals(regionalGolden)) {
               location = this.getLocaleDisplayNames().regionDisplayName(countryCode);
            } else {
               location = this._tznames.getExemplarLocationName(tzID);
            }
         } else {
            location = this._tznames.getExemplarLocationName(tzID);
            if (location == null) {
               location = tzID;
            }
         }

         name = this.formatPattern(TimeZoneGenericNames.Pattern.FALLBACK_FORMAT, location, mzDisplayName);
         synchronized(this) {
            String tmp = (String)this._genericPartialLocationNamesMap.putIfAbsent(key.intern(), name.intern());
            if (tmp == null) {
               NameInfo info = new NameInfo(tzID.intern(), isLong ? TimeZoneGenericNames.GenericNameType.LONG : TimeZoneGenericNames.GenericNameType.SHORT);
               this._gnamesTrie.put(name, info);
            } else {
               name = tmp;
            }

            return name;
         }
      }
   }

   public GenericMatchInfo findBestMatch(String text, int start, EnumSet genericTypes) {
      if (text != null && text.length() != 0 && start >= 0 && start < text.length()) {
         GenericMatchInfo bestMatch = null;
         Collection tznamesMatches = this.findTimeZoneNames(text, start, genericTypes);
         Iterator var7;
         if (tznamesMatches != null) {
            TimeZoneNames.MatchInfo longestMatch = null;
            var7 = tznamesMatches.iterator();

            label53:
            while(true) {
               TimeZoneNames.MatchInfo match;
               do {
                  if (!var7.hasNext()) {
                     if (longestMatch != null) {
                        bestMatch = this.createGenericMatchInfo(longestMatch);
                        if (bestMatch.matchLength() == text.length() - start && bestMatch.timeType != TimeZoneFormat.TimeType.STANDARD) {
                           return bestMatch;
                        }
                     }
                     break label53;
                  }

                  match = (TimeZoneNames.MatchInfo)var7.next();
               } while(longestMatch != null && match.matchLength() <= longestMatch.matchLength());

               longestMatch = match;
            }
         }

         Collection localMatches = this.findLocal(text, start, genericTypes);
         if (localMatches != null) {
            var7 = localMatches.iterator();

            while(true) {
               GenericMatchInfo match;
               do {
                  if (!var7.hasNext()) {
                     return bestMatch;
                  }

                  match = (GenericMatchInfo)var7.next();
               } while(bestMatch != null && match.matchLength() < bestMatch.matchLength());

               bestMatch = match;
            }
         } else {
            return bestMatch;
         }
      } else {
         throw new IllegalArgumentException("bad input text or range");
      }
   }

   public Collection find(String text, int start, EnumSet genericTypes) {
      if (text != null && text.length() != 0 && start >= 0 && start < text.length()) {
         Collection results = this.findLocal(text, start, genericTypes);
         Collection tznamesMatches = this.findTimeZoneNames(text, start, genericTypes);
         TimeZoneNames.MatchInfo match;
         if (tznamesMatches != null) {
            for(Iterator var6 = tznamesMatches.iterator(); var6.hasNext(); ((Collection)results).add(this.createGenericMatchInfo(match))) {
               match = (TimeZoneNames.MatchInfo)var6.next();
               if (results == null) {
                  results = new LinkedList();
               }
            }
         }

         return (Collection)results;
      } else {
         throw new IllegalArgumentException("bad input text or range");
      }
   }

   private GenericMatchInfo createGenericMatchInfo(TimeZoneNames.MatchInfo matchInfo) {
      GenericNameType nameType = null;
      TimeZoneFormat.TimeType timeType = TimeZoneFormat.TimeType.UNKNOWN;
      switch (matchInfo.nameType()) {
         case LONG_STANDARD:
            nameType = TimeZoneGenericNames.GenericNameType.LONG;
            timeType = TimeZoneFormat.TimeType.STANDARD;
            break;
         case LONG_GENERIC:
            nameType = TimeZoneGenericNames.GenericNameType.LONG;
            break;
         case SHORT_STANDARD:
            nameType = TimeZoneGenericNames.GenericNameType.SHORT;
            timeType = TimeZoneFormat.TimeType.STANDARD;
            break;
         case SHORT_GENERIC:
            nameType = TimeZoneGenericNames.GenericNameType.SHORT;
            break;
         default:
            throw new IllegalArgumentException("Unexpected MatchInfo name type - " + matchInfo.nameType());
      }

      String tzID = matchInfo.tzID();
      if (tzID == null) {
         String mzID = matchInfo.mzID();

         assert mzID != null;

         tzID = this._tznames.getReferenceZoneID(mzID, this.getTargetRegion());
      }

      assert tzID != null;

      GenericMatchInfo gmatch = new GenericMatchInfo(nameType, tzID, matchInfo.matchLength(), timeType);
      return gmatch;
   }

   private Collection findTimeZoneNames(String text, int start, EnumSet types) {
      Collection tznamesMatches = null;
      EnumSet nameTypes = EnumSet.noneOf(TimeZoneNames.NameType.class);
      if (types.contains(TimeZoneGenericNames.GenericNameType.LONG)) {
         nameTypes.add(TimeZoneNames.NameType.LONG_GENERIC);
         nameTypes.add(TimeZoneNames.NameType.LONG_STANDARD);
      }

      if (types.contains(TimeZoneGenericNames.GenericNameType.SHORT)) {
         nameTypes.add(TimeZoneNames.NameType.SHORT_GENERIC);
         nameTypes.add(TimeZoneNames.NameType.SHORT_STANDARD);
      }

      if (!nameTypes.isEmpty()) {
         tznamesMatches = this._tznames.find(text, start, nameTypes);
      }

      return tznamesMatches;
   }

   private synchronized Collection findLocal(String text, int start, EnumSet types) {
      GenericNameSearchHandler handler = new GenericNameSearchHandler(types);
      this._gnamesTrie.find(text, start, handler);
      if (handler.getMaxMatchLen() != text.length() - start && !this._gnamesTrieFullyLoaded) {
         Set tzIDs = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, (String)null, (Integer)null);
         Iterator var6 = tzIDs.iterator();

         while(var6.hasNext()) {
            String tzID = (String)var6.next();
            this.loadStrings(tzID);
         }

         this._gnamesTrieFullyLoaded = true;
         handler.resetResults();
         this._gnamesTrie.find(text, start, handler);
         return handler.getMatches();
      } else {
         return handler.getMatches();
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.init();
   }

   public boolean isFrozen() {
      return this._frozen;
   }

   public TimeZoneGenericNames freeze() {
      this._frozen = true;
      return this;
   }

   public TimeZoneGenericNames cloneAsThawed() {
      TimeZoneGenericNames copy = null;

      try {
         copy = (TimeZoneGenericNames)super.clone();
         copy._frozen = false;
      } catch (Throwable var3) {
      }

      return copy;
   }

   // $FF: synthetic method
   TimeZoneGenericNames(ULocale x0, Object x1) {
      this(x0);
   }

   static {
      GENERIC_NON_LOCATION_TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC};
   }

   private static class Cache extends SoftCache {
      private Cache() {
      }

      protected TimeZoneGenericNames createInstance(String key, ULocale data) {
         return (new TimeZoneGenericNames(data)).freeze();
      }

      // $FF: synthetic method
      Cache(Object x0) {
         this();
      }
   }

   private static class GenericNameSearchHandler implements TextTrieMap.ResultHandler {
      private EnumSet _types;
      private Collection _matches;
      private int _maxMatchLen;

      GenericNameSearchHandler(EnumSet types) {
         this._types = types;
      }

      public boolean handlePrefixMatch(int matchLength, Iterator values) {
         while(values.hasNext()) {
            NameInfo info = (NameInfo)values.next();
            if (this._types == null || this._types.contains(info.type)) {
               GenericMatchInfo matchInfo = new GenericMatchInfo(info.type, info.tzID, matchLength);
               if (this._matches == null) {
                  this._matches = new LinkedList();
               }

               this._matches.add(matchInfo);
               if (matchLength > this._maxMatchLen) {
                  this._maxMatchLen = matchLength;
               }
            }
         }

         return true;
      }

      public Collection getMatches() {
         return this._matches;
      }

      public int getMaxMatchLen() {
         return this._maxMatchLen;
      }

      public void resetResults() {
         this._matches = null;
         this._maxMatchLen = 0;
      }
   }

   public static class GenericMatchInfo {
      final GenericNameType nameType;
      final String tzID;
      final int matchLength;
      final TimeZoneFormat.TimeType timeType;

      private GenericMatchInfo(GenericNameType nameType, String tzID, int matchLength) {
         this(nameType, tzID, matchLength, TimeZoneFormat.TimeType.UNKNOWN);
      }

      private GenericMatchInfo(GenericNameType nameType, String tzID, int matchLength, TimeZoneFormat.TimeType timeType) {
         this.nameType = nameType;
         this.tzID = tzID;
         this.matchLength = matchLength;
         this.timeType = timeType;
      }

      public GenericNameType nameType() {
         return this.nameType;
      }

      public String tzID() {
         return this.tzID;
      }

      public TimeZoneFormat.TimeType timeType() {
         return this.timeType;
      }

      public int matchLength() {
         return this.matchLength;
      }

      // $FF: synthetic method
      GenericMatchInfo(GenericNameType x0, String x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      // $FF: synthetic method
      GenericMatchInfo(GenericNameType x0, String x1, int x2, TimeZoneFormat.TimeType x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static class NameInfo {
      final String tzID;
      final GenericNameType type;

      NameInfo(String tzID, GenericNameType type) {
         this.tzID = tzID;
         this.type = type;
      }
   }

   public static enum Pattern {
      REGION_FORMAT("regionFormat", "({0})"),
      FALLBACK_FORMAT("fallbackFormat", "{1} ({0})");

      String _key;
      String _defaultVal;

      private Pattern(String key, String defaultVal) {
         this._key = key;
         this._defaultVal = defaultVal;
      }

      String key() {
         return this._key;
      }

      String defaultValue() {
         return this._defaultVal;
      }
   }

   public static enum GenericNameType {
      LOCATION(new String[]{"LONG", "SHORT"}),
      LONG(new String[0]),
      SHORT(new String[0]);

      String[] _fallbackTypeOf;

      private GenericNameType(String... fallbackTypeOf) {
         this._fallbackTypeOf = fallbackTypeOf;
      }

      public boolean isFallbackTypeOf(GenericNameType type) {
         String typeStr = type.toString();
         String[] var3 = this._fallbackTypeOf;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String t = var3[var5];
            if (t.equals(typeStr)) {
               return true;
            }
         }

         return false;
      }
   }
}
