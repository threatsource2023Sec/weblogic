package org.python.icu.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.logging.Logger;
import org.python.icu.impl.Grego;
import org.python.icu.impl.ICUConfig;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.JavaTimeZone;
import org.python.icu.impl.OlsonTimeZone;
import org.python.icu.impl.TimeZoneAdapter;
import org.python.icu.impl.ZoneMeta;
import org.python.icu.text.TimeZoneFormat;
import org.python.icu.text.TimeZoneNames;

public abstract class TimeZone implements Serializable, Cloneable, Freezable {
   private static final Logger LOGGER = Logger.getLogger("org.python.icu.util.TimeZone");
   private static final long serialVersionUID = -744942128318337471L;
   public static final int TIMEZONE_ICU = 0;
   public static final int TIMEZONE_JDK = 1;
   public static final int SHORT = 0;
   public static final int LONG = 1;
   public static final int SHORT_GENERIC = 2;
   public static final int LONG_GENERIC = 3;
   public static final int SHORT_GMT = 4;
   public static final int LONG_GMT = 5;
   public static final int SHORT_COMMONLY_USED = 6;
   public static final int GENERIC_LOCATION = 7;
   public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
   static final String GMT_ZONE_ID = "Etc/GMT";
   public static final TimeZone UNKNOWN_ZONE = (new ConstantZone(0, "Etc/Unknown")).freeze();
   public static final TimeZone GMT_ZONE = (new ConstantZone(0, "Etc/GMT")).freeze();
   private String ID;
   private static volatile TimeZone defaultZone = null;
   private static int TZ_IMPL = 0;
   private static final String TZIMPL_CONFIG_KEY = "org.python.icu.util.TimeZone.DefaultTimeZoneType";
   private static final String TZIMPL_CONFIG_ICU = "ICU";
   private static final String TZIMPL_CONFIG_JDK = "JDK";

   public TimeZone() {
   }

   /** @deprecated */
   @Deprecated
   protected TimeZone(String ID) {
      if (ID == null) {
         throw new NullPointerException();
      } else {
         this.ID = ID;
      }
   }

   public abstract int getOffset(int var1, int var2, int var3, int var4, int var5, int var6);

   public int getOffset(long date) {
      int[] result = new int[2];
      this.getOffset(date, false, result);
      return result[0] + result[1];
   }

   public void getOffset(long date, boolean local, int[] offsets) {
      offsets[0] = this.getRawOffset();
      if (!local) {
         date += (long)offsets[0];
      }

      int[] fields = new int[6];
      int pass = 0;

      while(true) {
         Grego.timeToFields(date, fields);
         offsets[1] = this.getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]) - offsets[0];
         if (pass != 0 || !local || offsets[1] == 0) {
            return;
         }

         date -= (long)offsets[1];
         ++pass;
      }
   }

   public abstract void setRawOffset(int var1);

   public abstract int getRawOffset();

   public String getID() {
      return this.ID;
   }

   public void setID(String ID) {
      if (ID == null) {
         throw new NullPointerException();
      } else if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
      } else {
         this.ID = ID;
      }
   }

   public final String getDisplayName() {
      return this._getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public final String getDisplayName(Locale locale) {
      return this._getDisplayName(3, false, ULocale.forLocale(locale));
   }

   public final String getDisplayName(ULocale locale) {
      return this._getDisplayName(3, false, locale);
   }

   public final String getDisplayName(boolean daylight, int style) {
      return this.getDisplayName(daylight, style, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getDisplayName(boolean daylight, int style, Locale locale) {
      return this.getDisplayName(daylight, style, ULocale.forLocale(locale));
   }

   public String getDisplayName(boolean daylight, int style, ULocale locale) {
      if (style >= 0 && style <= 7) {
         return this._getDisplayName(style, daylight, locale);
      } else {
         throw new IllegalArgumentException("Illegal style: " + style);
      }
   }

   private String _getDisplayName(int style, boolean daylight, ULocale locale) {
      if (locale == null) {
         throw new NullPointerException("locale is null");
      } else {
         String result = null;
         TimeZoneFormat tzfmt;
         if (style != 7 && style != 3 && style != 2) {
            if (style != 5 && style != 4) {
               assert style == 1 || style == 0 || style == 6;

               long date = System.currentTimeMillis();
               TimeZoneNames tznames = TimeZoneNames.getInstance(locale);
               TimeZoneNames.NameType nameType = null;
               switch (style) {
                  case 0:
                  case 6:
                     nameType = daylight ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD;
                     break;
                  case 1:
                     nameType = daylight ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD;
               }

               result = tznames.getDisplayName(ZoneMeta.getCanonicalCLDRID(this), nameType, date);
               if (result == null) {
                  TimeZoneFormat tzfmt = TimeZoneFormat.getInstance(locale);
                  int offset = daylight && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
                  result = style == 1 ? tzfmt.formatOffsetLocalizedGMT(offset) : tzfmt.formatOffsetShortLocalizedGMT(offset);
               }
            } else {
               tzfmt = TimeZoneFormat.getInstance(locale);
               int offset = daylight && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
               switch (style) {
                  case 4:
                     result = tzfmt.formatOffsetISO8601Basic(offset, false, false, false);
                     break;
                  case 5:
                     result = tzfmt.formatOffsetLocalizedGMT(offset);
               }
            }
         } else {
            tzfmt = TimeZoneFormat.getInstance(locale);
            long date = System.currentTimeMillis();
            Output timeType = new Output(TimeZoneFormat.TimeType.UNKNOWN);
            switch (style) {
               case 2:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_SHORT, this, date, timeType);
                  break;
               case 3:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_LONG, this, date, timeType);
                  break;
               case 7:
                  result = tzfmt.format(TimeZoneFormat.Style.GENERIC_LOCATION, this, date, timeType);
            }

            if (daylight && timeType.value == TimeZoneFormat.TimeType.STANDARD || !daylight && timeType.value == TimeZoneFormat.TimeType.DAYLIGHT) {
               int offset = daylight ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
               result = style == 2 ? tzfmt.formatOffsetShortLocalizedGMT(offset) : tzfmt.formatOffsetLocalizedGMT(offset);
            }
         }

         assert result != null;

         return result;
      }
   }

   public int getDSTSavings() {
      return this.useDaylightTime() ? 3600000 : 0;
   }

   public abstract boolean useDaylightTime();

   public boolean observesDaylightTime() {
      return this.useDaylightTime() || this.inDaylightTime(new Date());
   }

   public abstract boolean inDaylightTime(Date var1);

   public static TimeZone getTimeZone(String ID) {
      return getTimeZone(ID, TZ_IMPL, false);
   }

   public static TimeZone getFrozenTimeZone(String ID) {
      return getTimeZone(ID, TZ_IMPL, true);
   }

   public static TimeZone getTimeZone(String ID, int type) {
      return getTimeZone(ID, type, false);
   }

   private static TimeZone getTimeZone(String id, int type, boolean frozen) {
      Object result;
      if (type == 1) {
         TimeZone result = JavaTimeZone.createTimeZone(id);
         if (result != null) {
            return (TimeZone)(frozen ? result.freeze() : result);
         }

         result = getFrozenICUTimeZone(id, false);
      } else {
         result = getFrozenICUTimeZone(id, true);
      }

      if (result == null) {
         LOGGER.fine("\"" + id + "\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
         result = UNKNOWN_ZONE;
      }

      return (TimeZone)(frozen ? result : ((TimeZone)result).cloneAsThawed());
   }

   static BasicTimeZone getFrozenICUTimeZone(String id, boolean trySystem) {
      BasicTimeZone result = null;
      if (trySystem) {
         result = ZoneMeta.getSystemTimeZone(id);
      }

      if (result == null) {
         result = ZoneMeta.getCustomTimeZone(id);
      }

      return (BasicTimeZone)result;
   }

   public static synchronized void setDefaultTimeZoneType(int type) {
      if (type != 0 && type != 1) {
         throw new IllegalArgumentException("Invalid timezone type");
      } else {
         TZ_IMPL = type;
      }
   }

   public static int getDefaultTimeZoneType() {
      return TZ_IMPL;
   }

   public static Set getAvailableIDs(SystemTimeZoneType zoneType, String region, Integer rawOffset) {
      return ZoneMeta.getAvailableIDs(zoneType, region, rawOffset);
   }

   public static String[] getAvailableIDs(int rawOffset) {
      Set ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, (String)null, rawOffset);
      return (String[])ids.toArray(new String[0]);
   }

   public static String[] getAvailableIDs(String country) {
      Set ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, country, (Integer)null);
      return (String[])ids.toArray(new String[0]);
   }

   public static String[] getAvailableIDs() {
      Set ids = getAvailableIDs(TimeZone.SystemTimeZoneType.ANY, (String)null, (Integer)null);
      return (String[])ids.toArray(new String[0]);
   }

   public static int countEquivalentIDs(String id) {
      return ZoneMeta.countEquivalentIDs(id);
   }

   public static String getEquivalentID(String id, int index) {
      return ZoneMeta.getEquivalentID(id, index);
   }

   public static TimeZone getDefault() {
      TimeZone tmpDefaultZone = defaultZone;
      if (tmpDefaultZone == null) {
         Class var1 = java.util.TimeZone.class;
         synchronized(java.util.TimeZone.class) {
            Class var2 = TimeZone.class;
            synchronized(TimeZone.class) {
               tmpDefaultZone = defaultZone;
               if (tmpDefaultZone == null) {
                  if (TZ_IMPL == 1) {
                     tmpDefaultZone = new JavaTimeZone();
                  } else {
                     java.util.TimeZone temp = java.util.TimeZone.getDefault();
                     tmpDefaultZone = getFrozenTimeZone(temp.getID());
                  }

                  defaultZone = (TimeZone)tmpDefaultZone;
               }
            }
         }
      }

      return ((TimeZone)tmpDefaultZone).cloneAsThawed();
   }

   public static synchronized void setDefault(TimeZone tz) {
      defaultZone = tz;
      java.util.TimeZone jdkZone = null;
      if (defaultZone instanceof JavaTimeZone) {
         jdkZone = ((JavaTimeZone)defaultZone).unwrap();
      } else if (tz != null) {
         if (tz instanceof OlsonTimeZone) {
            String icuID = tz.getID();
            jdkZone = java.util.TimeZone.getTimeZone(icuID);
            if (!icuID.equals(jdkZone.getID())) {
               icuID = getCanonicalID(icuID);
               jdkZone = java.util.TimeZone.getTimeZone(icuID);
               if (!icuID.equals(jdkZone.getID())) {
                  jdkZone = null;
               }
            }
         }

         if (jdkZone == null) {
            jdkZone = TimeZoneAdapter.wrap(tz);
         }
      }

      java.util.TimeZone.setDefault(jdkZone);
   }

   public boolean hasSameRules(TimeZone other) {
      return other != null && this.getRawOffset() == other.getRawOffset() && this.useDaylightTime() == other.useDaylightTime();
   }

   public Object clone() {
      return this.isFrozen() ? this : this.cloneAsThawed();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.ID.equals(((TimeZone)obj).ID) : false;
      }
   }

   public int hashCode() {
      return this.ID.hashCode();
   }

   public static String getTZDataVersion() {
      return VersionInfo.getTZDataVersion();
   }

   public static String getCanonicalID(String id) {
      return getCanonicalID(id, (boolean[])null);
   }

   public static String getCanonicalID(String id, boolean[] isSystemID) {
      String canonicalID = null;
      boolean systemTzid = false;
      if (id != null && id.length() != 0) {
         if (id.equals("Etc/Unknown")) {
            canonicalID = "Etc/Unknown";
            systemTzid = false;
         } else {
            canonicalID = ZoneMeta.getCanonicalCLDRID(id);
            if (canonicalID != null) {
               systemTzid = true;
            } else {
               canonicalID = ZoneMeta.getCustomID(id);
            }
         }
      }

      if (isSystemID != null) {
         isSystemID[0] = systemTzid;
      }

      return canonicalID;
   }

   public static String getRegion(String id) {
      String region = null;
      if (!id.equals("Etc/Unknown")) {
         region = ZoneMeta.getRegion(id);
      }

      if (region == null) {
         throw new IllegalArgumentException("Unknown system zone id: " + id);
      } else {
         return region;
      }
   }

   public static String getWindowsID(String id) {
      boolean[] isSystemID = new boolean[]{false};
      id = getCanonicalID(id, isSystemID);
      if (!isSystemID[0]) {
         return null;
      } else {
         UResourceBundle top = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         UResourceBundle mapTimezones = top.get("mapTimezones");
         UResourceBundleIterator resitr = mapTimezones.getIterator();

         label46:
         while(true) {
            UResourceBundle winzone;
            do {
               if (!resitr.hasNext()) {
                  return null;
               }

               winzone = resitr.next();
            } while(winzone.getType() != 2);

            UResourceBundleIterator rgitr = winzone.getIterator();

            while(true) {
               UResourceBundle regionalData;
               do {
                  if (!rgitr.hasNext()) {
                     continue label46;
                  }

                  regionalData = rgitr.next();
               } while(regionalData.getType() != 0);

               String[] tzids = regionalData.getString().split(" ");
               String[] var9 = tzids;
               int var10 = tzids.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String tzid = var9[var11];
                  if (tzid.equals(id)) {
                     return winzone.getKey();
                  }
               }
            }
         }
      }
   }

   public static String getIDForWindowsID(String winid, String region) {
      String id = null;
      UResourceBundle top = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
      UResourceBundle mapTimezones = top.get("mapTimezones");

      try {
         UResourceBundle zones = mapTimezones.get(winid);
         if (region != null) {
            try {
               id = zones.getString(region);
               if (id != null) {
                  int endIdx = id.indexOf(32);
                  if (endIdx > 0) {
                     id = id.substring(0, endIdx);
                  }
               }
            } catch (MissingResourceException var7) {
            }
         }

         if (id == null) {
            id = zones.getString("001");
         }
      } catch (MissingResourceException var8) {
      }

      return id;
   }

   public boolean isFrozen() {
      return false;
   }

   public TimeZone freeze() {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public TimeZone cloneAsThawed() {
      try {
         TimeZone other = (TimeZone)super.clone();
         return other;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }

   static {
      String type = ICUConfig.get("org.python.icu.util.TimeZone.DefaultTimeZoneType", "ICU");
      if (type.equalsIgnoreCase("JDK")) {
         TZ_IMPL = 1;
      }

   }

   private static final class ConstantZone extends TimeZone {
      private static final long serialVersionUID = 1L;
      private int rawOffset;
      private transient volatile boolean isFrozen;

      private ConstantZone(int rawOffset, String ID) {
         super(ID);
         this.isFrozen = false;
         this.rawOffset = rawOffset;
      }

      public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
         return this.rawOffset;
      }

      public void setRawOffset(int offsetMillis) {
         if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
         } else {
            this.rawOffset = offsetMillis;
         }
      }

      public int getRawOffset() {
         return this.rawOffset;
      }

      public boolean useDaylightTime() {
         return false;
      }

      public boolean inDaylightTime(Date date) {
         return false;
      }

      public boolean isFrozen() {
         return this.isFrozen;
      }

      public TimeZone freeze() {
         this.isFrozen = true;
         return this;
      }

      public TimeZone cloneAsThawed() {
         ConstantZone tz = (ConstantZone)super.cloneAsThawed();
         tz.isFrozen = false;
         return tz;
      }

      // $FF: synthetic method
      ConstantZone(int x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   public static enum SystemTimeZoneType {
      ANY,
      CANONICAL,
      CANONICAL_LOCATION;
   }
}
