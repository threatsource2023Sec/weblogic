package org.python.icu.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.python.icu.text.TimeZoneNames;
import org.python.icu.util.TimeZone;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class TimeZoneNamesImpl extends TimeZoneNames {
   private static final long serialVersionUID = -2179814848495897472L;
   private static final String ZONE_STRINGS_BUNDLE = "zoneStrings";
   private static final String MZ_PREFIX = "meta:";
   private static volatile Set METAZONE_IDS;
   private static final TZ2MZsCache TZ_TO_MZS_CACHE = new TZ2MZsCache();
   private static final MZ2TZsCache MZ_TO_TZS_CACHE = new MZ2TZsCache();
   private transient ICUResourceBundle _zoneStrings;
   private transient ConcurrentHashMap _mzNamesMap;
   private transient ConcurrentHashMap _tzNamesMap;
   private transient boolean _namesFullyLoaded;
   private transient TextTrieMap _namesTrie;
   private transient boolean _namesTrieFullyLoaded;
   private static final Pattern LOC_EXCLUSION_PATTERN = Pattern.compile("Etc/.*|SystemV/.*|.*/Riyadh8[7-9]");

   public TimeZoneNamesImpl(ULocale locale) {
      this.initialize(locale);
   }

   public Set getAvailableMetaZoneIDs() {
      return _getAvailableMetaZoneIDs();
   }

   static Set _getAvailableMetaZoneIDs() {
      if (METAZONE_IDS == null) {
         Class var0 = TimeZoneNamesImpl.class;
         synchronized(TimeZoneNamesImpl.class) {
            if (METAZONE_IDS == null) {
               UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "metaZones");
               UResourceBundle mapTimezones = bundle.get("mapTimezones");
               Set keys = mapTimezones.keySet();
               METAZONE_IDS = Collections.unmodifiableSet(keys);
            }
         }
      }

      return METAZONE_IDS;
   }

   public Set getAvailableMetaZoneIDs(String tzID) {
      return _getAvailableMetaZoneIDs(tzID);
   }

   static Set _getAvailableMetaZoneIDs(String tzID) {
      if (tzID != null && tzID.length() != 0) {
         List maps = (List)TZ_TO_MZS_CACHE.getInstance(tzID, tzID);
         if (maps.isEmpty()) {
            return Collections.emptySet();
         } else {
            Set mzIDs = new HashSet(maps.size());
            Iterator var3 = maps.iterator();

            while(var3.hasNext()) {
               MZMapEntry map = (MZMapEntry)var3.next();
               mzIDs.add(map.mzID());
            }

            return Collections.unmodifiableSet(mzIDs);
         }
      } else {
         return Collections.emptySet();
      }
   }

   public String getMetaZoneID(String tzID, long date) {
      return _getMetaZoneID(tzID, date);
   }

   static String _getMetaZoneID(String tzID, long date) {
      if (tzID != null && tzID.length() != 0) {
         String mzID = null;
         List maps = (List)TZ_TO_MZS_CACHE.getInstance(tzID, tzID);
         Iterator var5 = maps.iterator();

         while(var5.hasNext()) {
            MZMapEntry map = (MZMapEntry)var5.next();
            if (date >= map.from() && date < map.to()) {
               mzID = map.mzID();
               break;
            }
         }

         return mzID;
      } else {
         return null;
      }
   }

   public String getReferenceZoneID(String mzID, String region) {
      return _getReferenceZoneID(mzID, region);
   }

   static String _getReferenceZoneID(String mzID, String region) {
      if (mzID != null && mzID.length() != 0) {
         String refID = null;
         Map regionTzMap = (Map)MZ_TO_TZS_CACHE.getInstance(mzID, mzID);
         if (!regionTzMap.isEmpty()) {
            refID = (String)regionTzMap.get(region);
            if (refID == null) {
               refID = (String)regionTzMap.get("001");
            }
         }

         return refID;
      } else {
         return null;
      }
   }

   public String getMetaZoneDisplayName(String mzID, TimeZoneNames.NameType type) {
      return mzID != null && mzID.length() != 0 ? this.loadMetaZoneNames(mzID).getName(type) : null;
   }

   public String getTimeZoneDisplayName(String tzID, TimeZoneNames.NameType type) {
      return tzID != null && tzID.length() != 0 ? this.loadTimeZoneNames(tzID).getName(type) : null;
   }

   public String getExemplarLocationName(String tzID) {
      if (tzID != null && tzID.length() != 0) {
         String locName = this.loadTimeZoneNames(tzID).getName(TimeZoneNames.NameType.EXEMPLAR_LOCATION);
         return locName;
      } else {
         return null;
      }
   }

   public synchronized Collection find(CharSequence text, int start, EnumSet nameTypes) {
      if (text != null && text.length() != 0 && start >= 0 && start < text.length()) {
         NameSearchHandler handler = new NameSearchHandler(nameTypes);
         Collection matches = this.doFind(handler, text, start);
         if (matches != null) {
            return matches;
         } else {
            this.addAllNamesIntoTrie();
            matches = this.doFind(handler, text, start);
            if (matches != null) {
               return matches;
            } else {
               this.internalLoadAllDisplayNames();
               Set tzIDs = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, (String)null, (Integer)null);
               Iterator var7 = tzIDs.iterator();

               while(var7.hasNext()) {
                  String tzID = (String)var7.next();
                  if (!this._tzNamesMap.containsKey(tzID)) {
                     TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, (String[])null, tzID);
                  }
               }

               this.addAllNamesIntoTrie();
               this._namesTrieFullyLoaded = true;
               return this.doFind(handler, text, start);
            }
         }
      } else {
         throw new IllegalArgumentException("bad input text or range");
      }
   }

   private Collection doFind(NameSearchHandler handler, CharSequence text, int start) {
      handler.resetResults();
      this._namesTrie.find(text, start, handler);
      return handler.getMaxMatchLen() != text.length() - start && !this._namesTrieFullyLoaded ? null : handler.getMatches();
   }

   public synchronized void loadAllDisplayNames() {
      this.internalLoadAllDisplayNames();
   }

   public void getDisplayNames(String tzID, TimeZoneNames.NameType[] types, long date, String[] dest, int destOffset) {
      if (tzID != null && tzID.length() != 0) {
         ZNames tzNames = this.loadTimeZoneNames(tzID);
         ZNames mzNames = null;

         for(int i = 0; i < types.length; ++i) {
            TimeZoneNames.NameType type = types[i];
            String name = tzNames.getName(type);
            if (name == null) {
               if (mzNames == null) {
                  String mzID = this.getMetaZoneID(tzID, date);
                  if (mzID != null && mzID.length() != 0) {
                     mzNames = this.loadMetaZoneNames(mzID);
                  } else {
                     mzNames = TimeZoneNamesImpl.ZNames.EMPTY_ZNAMES;
                  }
               }

               name = mzNames.getName(type);
            }

            dest[destOffset + i] = name;
         }

      }
   }

   private void internalLoadAllDisplayNames() {
      if (!this._namesFullyLoaded) {
         this._namesFullyLoaded = true;
         (new ZoneStringsLoader()).load();
      }

   }

   private void addAllNamesIntoTrie() {
      Iterator var1 = this._tzNamesMap.entrySet().iterator();

      Map.Entry entry;
      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         ((ZNames)entry.getValue()).addAsTimeZoneIntoTrie((String)entry.getKey(), this._namesTrie);
      }

      var1 = this._mzNamesMap.entrySet().iterator();

      while(var1.hasNext()) {
         entry = (Map.Entry)var1.next();
         ((ZNames)entry.getValue()).addAsMetaZoneIntoTrie((String)entry.getKey(), this._namesTrie);
      }

   }

   private void initialize(ULocale locale) {
      ICUResourceBundle bundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/zone", locale);
      this._zoneStrings = (ICUResourceBundle)bundle.get("zoneStrings");
      this._tzNamesMap = new ConcurrentHashMap();
      this._mzNamesMap = new ConcurrentHashMap();
      this._namesFullyLoaded = false;
      this._namesTrie = new TextTrieMap(true);
      this._namesTrieFullyLoaded = false;
      TimeZone tz = TimeZone.getDefault();
      String tzCanonicalID = ZoneMeta.getCanonicalCLDRID(tz);
      if (tzCanonicalID != null) {
         this.loadStrings(tzCanonicalID);
      }

   }

   private synchronized void loadStrings(String tzCanonicalID) {
      if (tzCanonicalID != null && tzCanonicalID.length() != 0) {
         this.loadTimeZoneNames(tzCanonicalID);
         Set mzIDs = this.getAvailableMetaZoneIDs(tzCanonicalID);
         Iterator var3 = mzIDs.iterator();

         while(var3.hasNext()) {
            String mzID = (String)var3.next();
            this.loadMetaZoneNames(mzID);
         }

      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      ULocale locale = this._zoneStrings.getULocale();
      out.writeObject(locale);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      ULocale locale = (ULocale)in.readObject();
      this.initialize(locale);
   }

   private synchronized ZNames loadMetaZoneNames(String mzID) {
      ZNames mznames = (ZNames)this._mzNamesMap.get(mzID);
      if (mznames == null) {
         ZNamesLoader loader = new ZNamesLoader();
         loader.loadMetaZone(this._zoneStrings, mzID);
         mznames = TimeZoneNamesImpl.ZNames.createMetaZoneAndPutInCache(this._mzNamesMap, loader.getNames(), mzID);
      }

      return mznames;
   }

   private synchronized ZNames loadTimeZoneNames(String tzID) {
      ZNames tznames = (ZNames)this._tzNamesMap.get(tzID);
      if (tznames == null) {
         ZNamesLoader loader = new ZNamesLoader();
         loader.loadTimeZone(this._zoneStrings, tzID);
         tznames = TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(this._tzNamesMap, loader.getNames(), tzID);
      }

      return tznames;
   }

   public static String getDefaultExemplarLocationName(String tzID) {
      if (tzID != null && tzID.length() != 0 && !LOC_EXCLUSION_PATTERN.matcher(tzID).matches()) {
         String location = null;
         int sep = tzID.lastIndexOf(47);
         if (sep > 0 && sep + 1 < tzID.length()) {
            location = tzID.substring(sep + 1).replace('_', ' ');
         }

         return location;
      } else {
         return null;
      }
   }

   private static class MZ2TZsCache extends SoftCache {
      private MZ2TZsCache() {
      }

      protected Map createInstance(String key, String data) {
         Map map = null;
         UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "metaZones");
         UResourceBundle mapTimezones = bundle.get("mapTimezones");

         try {
            UResourceBundle regionMap = mapTimezones.get(key);
            Set regions = regionMap.keySet();
            map = new HashMap(regions.size());
            Iterator var8 = regions.iterator();

            while(var8.hasNext()) {
               String region = (String)var8.next();
               String tzID = regionMap.getString(region).intern();
               ((Map)map).put(region.intern(), tzID);
            }
         } catch (MissingResourceException var11) {
            map = Collections.emptyMap();
         }

         return (Map)map;
      }

      // $FF: synthetic method
      MZ2TZsCache(Object x0) {
         this();
      }
   }

   private static class TZ2MZsCache extends SoftCache {
      private TZ2MZsCache() {
      }

      protected List createInstance(String key, String data) {
         List mzMaps = null;
         UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "metaZones");
         UResourceBundle metazoneInfoBundle = bundle.get("metazoneInfo");
         String tzkey = data.replace('/', ':');

         try {
            UResourceBundle zoneBundle = metazoneInfoBundle.get(tzkey);
            mzMaps = new ArrayList(zoneBundle.getSize());

            for(int idx = 0; idx < zoneBundle.getSize(); ++idx) {
               UResourceBundle mz = zoneBundle.get(idx);
               String mzid = mz.getString(0);
               String fromStr = "1970-01-01 00:00";
               String toStr = "9999-12-31 23:59";
               if (mz.getSize() == 3) {
                  fromStr = mz.getString(1);
                  toStr = mz.getString(2);
               }

               long from = parseDate(fromStr);
               long to = parseDate(toStr);
               ((List)mzMaps).add(new MZMapEntry(mzid, from, to));
            }
         } catch (MissingResourceException var17) {
            mzMaps = Collections.emptyList();
         }

         return (List)mzMaps;
      }

      private static long parseDate(String text) {
         int year = 0;
         int month = 0;
         int day = 0;
         int hour = 0;
         int min = 0;

         int idx;
         int n;
         for(idx = 0; idx <= 3; ++idx) {
            n = text.charAt(idx) - 48;
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad year");
            }

            year = 10 * year + n;
         }

         for(idx = 5; idx <= 6; ++idx) {
            n = text.charAt(idx) - 48;
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad month");
            }

            month = 10 * month + n;
         }

         for(idx = 8; idx <= 9; ++idx) {
            n = text.charAt(idx) - 48;
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad day");
            }

            day = 10 * day + n;
         }

         for(idx = 11; idx <= 12; ++idx) {
            n = text.charAt(idx) - 48;
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad hour");
            }

            hour = 10 * hour + n;
         }

         for(idx = 14; idx <= 15; ++idx) {
            n = text.charAt(idx) - 48;
            if (n < 0 || n >= 10) {
               throw new IllegalArgumentException("Bad minute");
            }

            min = 10 * min + n;
         }

         long date = Grego.fieldsToDay(year, month - 1, day) * 86400000L + (long)hour * 3600000L + (long)min * 60000L;
         return date;
      }

      // $FF: synthetic method
      TZ2MZsCache(Object x0) {
         this();
      }
   }

   private static class MZMapEntry {
      private String _mzID;
      private long _from;
      private long _to;

      MZMapEntry(String mzID, long from, long to) {
         this._mzID = mzID;
         this._from = from;
         this._to = to;
      }

      String mzID() {
         return this._mzID;
      }

      long from() {
         return this._from;
      }

      long to() {
         return this._to;
      }
   }

   private static class ZNames {
      public static final int NUM_NAME_TYPES = 7;
      static final ZNames EMPTY_ZNAMES = new ZNames((String[])null);
      private static final int EX_LOC_INDEX;
      private String[] _names;
      private boolean didAddIntoTrie;

      private static int getNameTypeIndex(TimeZoneNames.NameType type) {
         switch (type) {
            case EXEMPLAR_LOCATION:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
            case LONG_GENERIC:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_GENERIC.ordinal();
            case LONG_STANDARD:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_STANDARD.ordinal();
            case LONG_DAYLIGHT:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_DAYLIGHT.ordinal();
            case SHORT_GENERIC:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_GENERIC.ordinal();
            case SHORT_STANDARD:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_STANDARD.ordinal();
            case SHORT_DAYLIGHT:
               return TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_DAYLIGHT.ordinal();
            default:
               throw new AssertionError("No NameTypeIndex match for " + type);
         }
      }

      private static TimeZoneNames.NameType getNameType(int index) {
         switch (TimeZoneNamesImpl.ZNames.NameTypeIndex.values[index]) {
            case EXEMPLAR_LOCATION:
               return TimeZoneNames.NameType.EXEMPLAR_LOCATION;
            case LONG_GENERIC:
               return TimeZoneNames.NameType.LONG_GENERIC;
            case LONG_STANDARD:
               return TimeZoneNames.NameType.LONG_STANDARD;
            case LONG_DAYLIGHT:
               return TimeZoneNames.NameType.LONG_DAYLIGHT;
            case SHORT_GENERIC:
               return TimeZoneNames.NameType.SHORT_GENERIC;
            case SHORT_STANDARD:
               return TimeZoneNames.NameType.SHORT_STANDARD;
            case SHORT_DAYLIGHT:
               return TimeZoneNames.NameType.SHORT_DAYLIGHT;
            default:
               throw new AssertionError("No NameType match for " + index);
         }
      }

      protected ZNames(String[] names) {
         this._names = names;
         this.didAddIntoTrie = names == null;
      }

      public static ZNames createMetaZoneAndPutInCache(Map cache, String[] names, String mzID) {
         String key = mzID.intern();
         ZNames value;
         if (names == null) {
            value = EMPTY_ZNAMES;
         } else {
            value = new ZNames(names);
         }

         cache.put(key, value);
         return value;
      }

      public static ZNames createTimeZoneAndPutInCache(Map cache, String[] names, String tzID) {
         names = names == null ? new String[EX_LOC_INDEX + 1] : names;
         if (names[EX_LOC_INDEX] == null) {
            names[EX_LOC_INDEX] = TimeZoneNamesImpl.getDefaultExemplarLocationName(tzID);
         }

         String key = tzID.intern();
         ZNames value = new ZNames(names);
         cache.put(key, value);
         return value;
      }

      public String getName(TimeZoneNames.NameType type) {
         int index = getNameTypeIndex(type);
         return this._names != null && index < this._names.length ? this._names[index] : null;
      }

      public void addAsMetaZoneIntoTrie(String mzID, TextTrieMap trie) {
         this.addNamesIntoTrie(mzID, (String)null, trie);
      }

      public void addAsTimeZoneIntoTrie(String tzID, TextTrieMap trie) {
         this.addNamesIntoTrie((String)null, tzID, trie);
      }

      private void addNamesIntoTrie(String mzID, String tzID, TextTrieMap trie) {
         if (this._names != null && !this.didAddIntoTrie) {
            this.didAddIntoTrie = true;

            for(int i = 0; i < this._names.length; ++i) {
               String name = this._names[i];
               if (name != null) {
                  NameInfo info = new NameInfo();
                  info.mzID = mzID;
                  info.tzID = tzID;
                  info.type = getNameType(i);
                  trie.put(name, info);
               }
            }

         }
      }

      static {
         EX_LOC_INDEX = TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION.ordinal();
      }

      private static enum NameTypeIndex {
         EXEMPLAR_LOCATION,
         LONG_GENERIC,
         LONG_STANDARD,
         LONG_DAYLIGHT,
         SHORT_GENERIC,
         SHORT_STANDARD,
         SHORT_DAYLIGHT;

         static final NameTypeIndex[] values = values();
      }
   }

   private static final class ZNamesLoader extends UResource.Sink {
      private String[] names;
      private static ZNamesLoader DUMMY_LOADER = new ZNamesLoader();

      private ZNamesLoader() {
      }

      void loadMetaZone(ICUResourceBundle zoneStrings, String mzID) {
         String key = "meta:" + mzID;
         this.loadNames(zoneStrings, key);
      }

      void loadTimeZone(ICUResourceBundle zoneStrings, String tzID) {
         String key = tzID.replace('/', ':');
         this.loadNames(zoneStrings, key);
      }

      void loadNames(ICUResourceBundle zoneStrings, String key) {
         assert zoneStrings != null;

         assert key != null;

         assert key.length() > 0;

         this.names = null;

         try {
            zoneStrings.getAllItemsWithFallback(key, this);
         } catch (MissingResourceException var4) {
         }

      }

      private static ZNames.NameTypeIndex nameTypeIndexFromKey(UResource.Key key) {
         if (key.length() != 2) {
            return null;
         } else {
            char c0 = key.charAt(0);
            char c1 = key.charAt(1);
            if (c0 == 'l') {
               return c1 == 'g' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_GENERIC : (c1 == 's' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_STANDARD : (c1 == 'd' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.LONG_DAYLIGHT : null));
            } else if (c0 == 's') {
               return c1 == 'g' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_GENERIC : (c1 == 's' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_STANDARD : (c1 == 'd' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.SHORT_DAYLIGHT : null));
            } else {
               return c0 == 'e' && c1 == 'c' ? TimeZoneNamesImpl.ZNames.NameTypeIndex.EXEMPLAR_LOCATION : null;
            }
         }
      }

      private void setNameIfEmpty(UResource.Key key, UResource.Value value) {
         if (this.names == null) {
            this.names = new String[7];
         }

         ZNames.NameTypeIndex index = nameTypeIndexFromKey(key);
         if (index != null) {
            assert index.ordinal() < 7;

            if (this.names[index.ordinal()] == null) {
               this.names[index.ordinal()] = value.getString();
            }

         }
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table namesTable = value.getTable();

         for(int i = 0; namesTable.getKeyAndValue(i, key, value); ++i) {
            assert value.getType() == 0;

            this.setNameIfEmpty(key, value);
         }

      }

      private String[] getNames() {
         if (Utility.sameObjects(this.names, (Object)null)) {
            return null;
         } else {
            int length = 0;

            for(int i = 0; i < 7; ++i) {
               String name = this.names[i];
               if (name != null) {
                  if (name.equals("∅∅∅")) {
                     this.names[i] = null;
                  } else {
                     length = i + 1;
                  }
               }
            }

            String[] result;
            if (length == 7) {
               result = this.names;
            } else if (length == 0) {
               result = null;
            } else {
               result = (String[])Arrays.copyOfRange(this.names, 0, length);
            }

            return result;
         }
      }

      // $FF: synthetic method
      ZNamesLoader(Object x0) {
         this();
      }
   }

   private static class NameSearchHandler implements TextTrieMap.ResultHandler {
      private EnumSet _nameTypes;
      private Collection _matches;
      private int _maxMatchLen;

      NameSearchHandler(EnumSet nameTypes) {
         this._nameTypes = nameTypes;
      }

      public boolean handlePrefixMatch(int matchLength, Iterator values) {
         while(values.hasNext()) {
            NameInfo ninfo = (NameInfo)values.next();
            if (this._nameTypes == null || this._nameTypes.contains(ninfo.type)) {
               TimeZoneNames.MatchInfo minfo;
               if (ninfo.tzID != null) {
                  minfo = new TimeZoneNames.MatchInfo(ninfo.type, ninfo.tzID, (String)null, matchLength);
               } else {
                  assert ninfo.mzID != null;

                  minfo = new TimeZoneNames.MatchInfo(ninfo.type, (String)null, ninfo.mzID, matchLength);
               }

               if (this._matches == null) {
                  this._matches = new LinkedList();
               }

               this._matches.add(minfo);
               if (matchLength > this._maxMatchLen) {
                  this._maxMatchLen = matchLength;
               }
            }
         }

         return true;
      }

      public Collection getMatches() {
         return (Collection)(this._matches == null ? Collections.emptyList() : this._matches);
      }

      public int getMaxMatchLen() {
         return this._maxMatchLen;
      }

      public void resetResults() {
         this._matches = null;
         this._maxMatchLen = 0;
      }
   }

   private static class NameInfo {
      String tzID;
      String mzID;
      TimeZoneNames.NameType type;

      private NameInfo() {
      }

      // $FF: synthetic method
      NameInfo(Object x0) {
         this();
      }
   }

   private final class ZoneStringsLoader extends UResource.Sink {
      private static final int INITIAL_NUM_ZONES = 300;
      private HashMap keyToLoader;
      private StringBuilder sb;

      private ZoneStringsLoader() {
         this.keyToLoader = new HashMap(300);
         this.sb = new StringBuilder(32);
      }

      void load() {
         TimeZoneNamesImpl.this._zoneStrings.getAllItemsWithFallback("", this);
         Iterator var1 = this.keyToLoader.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            ZNamesLoader loader = (ZNamesLoader)entry.getValue();
            if (loader != TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER) {
               UResource.Key key = (UResource.Key)entry.getKey();
               String mzID;
               if (this.isMetaZone(key)) {
                  mzID = this.mzIDFromKey(key);
                  TimeZoneNamesImpl.ZNames.createMetaZoneAndPutInCache(TimeZoneNamesImpl.this._mzNamesMap, loader.getNames(), mzID);
               } else {
                  mzID = this.tzIDFromKey(key);
                  TimeZoneNamesImpl.ZNames.createTimeZoneAndPutInCache(TimeZoneNamesImpl.this._tzNamesMap, loader.getNames(), mzID);
               }
            }
         }

      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table timeZonesTable = value.getTable();

         for(int j = 0; timeZonesTable.getKeyAndValue(j, key, value); ++j) {
            assert !value.isNoInheritanceMarker();

            if (value.getType() == 2) {
               this.consumeNamesTable(key, value, noFallback);
            }
         }

      }

      private void consumeNamesTable(UResource.Key key, UResource.Value value, boolean noFallback) {
         ZNamesLoader loader = (ZNamesLoader)this.keyToLoader.get(key);
         if (loader == null) {
            String mzID;
            if (this.isMetaZone(key)) {
               mzID = this.mzIDFromKey(key);
               if (TimeZoneNamesImpl.this._mzNamesMap.containsKey(mzID)) {
                  loader = TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER;
               } else {
                  loader = new ZNamesLoader();
               }
            } else {
               mzID = this.tzIDFromKey(key);
               if (TimeZoneNamesImpl.this._tzNamesMap.containsKey(mzID)) {
                  loader = TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER;
               } else {
                  loader = new ZNamesLoader();
               }
            }

            UResource.Key newKey = this.createKey(key);
            this.keyToLoader.put(newKey, loader);
         }

         if (loader != TimeZoneNamesImpl.ZNamesLoader.DUMMY_LOADER) {
            loader.put(key, value, noFallback);
         }

      }

      UResource.Key createKey(UResource.Key key) {
         return key.clone();
      }

      boolean isMetaZone(UResource.Key key) {
         return key.startsWith("meta:");
      }

      private String mzIDFromKey(UResource.Key key) {
         this.sb.setLength(0);

         for(int i = "meta:".length(); i < key.length(); ++i) {
            this.sb.append(key.charAt(i));
         }

         return this.sb.toString();
      }

      private String tzIDFromKey(UResource.Key key) {
         this.sb.setLength(0);

         for(int i = 0; i < key.length(); ++i) {
            char c = key.charAt(i);
            if (c == ':') {
               c = '/';
            }

            this.sb.append(c);
         }

         return this.sb.toString();
      }

      // $FF: synthetic method
      ZoneStringsLoader(Object x1) {
         this();
      }
   }
}
