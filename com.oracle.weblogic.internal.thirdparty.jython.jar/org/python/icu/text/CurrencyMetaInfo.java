package org.python.icu.text;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.python.icu.impl.Grego;
import org.python.icu.impl.Utility;
import org.python.icu.util.Currency;

public class CurrencyMetaInfo {
   private static final CurrencyMetaInfo impl;
   private static final boolean hasData;
   /** @deprecated */
   @Deprecated
   protected static final CurrencyDigits defaultDigits = new CurrencyDigits(2, 0);

   public static CurrencyMetaInfo getInstance() {
      return impl;
   }

   public static CurrencyMetaInfo getInstance(boolean noSubstitute) {
      return hasData ? impl : null;
   }

   /** @deprecated */
   @Deprecated
   public static boolean hasData() {
      return hasData;
   }

   /** @deprecated */
   @Deprecated
   protected CurrencyMetaInfo() {
   }

   public List currencyInfo(CurrencyFilter filter) {
      return Collections.emptyList();
   }

   public List currencies(CurrencyFilter filter) {
      return Collections.emptyList();
   }

   public List regions(CurrencyFilter filter) {
      return Collections.emptyList();
   }

   public CurrencyDigits currencyDigits(String isoCode) {
      return this.currencyDigits(isoCode, Currency.CurrencyUsage.STANDARD);
   }

   public CurrencyDigits currencyDigits(String isoCode, Currency.CurrencyUsage currencyUsage) {
      return defaultDigits;
   }

   private static String dateString(long date) {
      return date != Long.MAX_VALUE && date != Long.MIN_VALUE ? Grego.timeToString(date) : null;
   }

   private static String debugString(Object o) {
      StringBuilder sb = new StringBuilder();

      try {
         Field[] var2 = o.getClass().getFields();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field f = var2[var4];
            Object v = f.get(o);
            if (v != null) {
               String s;
               if (v instanceof Date) {
                  s = dateString(((Date)v).getTime());
               } else if (v instanceof Long) {
                  s = dateString((Long)v);
               } else {
                  s = String.valueOf(v);
               }

               if (s != null) {
                  if (sb.length() > 0) {
                     sb.append(",");
                  }

                  sb.append(f.getName()).append("='").append(s).append("'");
               }
            }
         }
      } catch (Throwable var8) {
      }

      sb.insert(0, o.getClass().getSimpleName() + "(");
      sb.append(")");
      return sb.toString();
   }

   static {
      CurrencyMetaInfo temp = null;
      boolean tempHasData = false;

      try {
         Class clzz = Class.forName("org.python.icu.impl.ICUCurrencyMetaInfo");
         temp = (CurrencyMetaInfo)clzz.newInstance();
         tempHasData = true;
      } catch (Throwable var3) {
         temp = new CurrencyMetaInfo();
      }

      impl = temp;
      hasData = tempHasData;
   }

   public static final class CurrencyInfo {
      public final String region;
      public final String code;
      public final long from;
      public final long to;
      public final int priority;
      private final boolean tender;

      /** @deprecated */
      @Deprecated
      public CurrencyInfo(String region, String code, long from, long to, int priority) {
         this(region, code, from, to, priority, true);
      }

      /** @deprecated */
      @Deprecated
      public CurrencyInfo(String region, String code, long from, long to, int priority, boolean tender) {
         this.region = region;
         this.code = code;
         this.from = from;
         this.to = to;
         this.priority = priority;
         this.tender = tender;
      }

      public String toString() {
         return CurrencyMetaInfo.debugString(this);
      }

      public boolean isTender() {
         return this.tender;
      }
   }

   public static final class CurrencyDigits {
      public final int fractionDigits;
      public final int roundingIncrement;

      public CurrencyDigits(int fractionDigits, int roundingIncrement) {
         this.fractionDigits = fractionDigits;
         this.roundingIncrement = roundingIncrement;
      }

      public String toString() {
         return CurrencyMetaInfo.debugString(this);
      }
   }

   public static final class CurrencyFilter {
      public final String region;
      public final String currency;
      public final long from;
      public final long to;
      /** @deprecated */
      @Deprecated
      public final boolean tenderOnly;
      private static final CurrencyFilter ALL = new CurrencyFilter((String)null, (String)null, Long.MIN_VALUE, Long.MAX_VALUE, false);

      private CurrencyFilter(String region, String currency, long from, long to, boolean tenderOnly) {
         this.region = region;
         this.currency = currency;
         this.from = from;
         this.to = to;
         this.tenderOnly = tenderOnly;
      }

      public static CurrencyFilter all() {
         return ALL;
      }

      public static CurrencyFilter now() {
         return ALL.withDate(new Date());
      }

      public static CurrencyFilter onRegion(String region) {
         return ALL.withRegion(region);
      }

      public static CurrencyFilter onCurrency(String currency) {
         return ALL.withCurrency(currency);
      }

      public static CurrencyFilter onDate(Date date) {
         return ALL.withDate(date);
      }

      public static CurrencyFilter onDateRange(Date from, Date to) {
         return ALL.withDateRange(from, to);
      }

      public static CurrencyFilter onDate(long date) {
         return ALL.withDate(date);
      }

      public static CurrencyFilter onDateRange(long from, long to) {
         return ALL.withDateRange(from, to);
      }

      public static CurrencyFilter onTender() {
         return ALL.withTender();
      }

      public CurrencyFilter withRegion(String region) {
         return new CurrencyFilter(region, this.currency, this.from, this.to, this.tenderOnly);
      }

      public CurrencyFilter withCurrency(String currency) {
         return new CurrencyFilter(this.region, currency, this.from, this.to, this.tenderOnly);
      }

      public CurrencyFilter withDate(Date date) {
         return new CurrencyFilter(this.region, this.currency, date.getTime(), date.getTime(), this.tenderOnly);
      }

      public CurrencyFilter withDateRange(Date from, Date to) {
         long fromLong = from == null ? Long.MIN_VALUE : from.getTime();
         long toLong = to == null ? Long.MAX_VALUE : to.getTime();
         return new CurrencyFilter(this.region, this.currency, fromLong, toLong, this.tenderOnly);
      }

      public CurrencyFilter withDate(long date) {
         return new CurrencyFilter(this.region, this.currency, date, date, this.tenderOnly);
      }

      public CurrencyFilter withDateRange(long from, long to) {
         return new CurrencyFilter(this.region, this.currency, from, to, this.tenderOnly);
      }

      public CurrencyFilter withTender() {
         return new CurrencyFilter(this.region, this.currency, this.from, this.to, true);
      }

      public boolean equals(Object rhs) {
         return rhs instanceof CurrencyFilter && this.equals((CurrencyFilter)rhs);
      }

      public boolean equals(CurrencyFilter rhs) {
         return Utility.sameObjects(this, rhs) || rhs != null && equals(this.region, rhs.region) && equals(this.currency, rhs.currency) && this.from == rhs.from && this.to == rhs.to && this.tenderOnly == rhs.tenderOnly;
      }

      public int hashCode() {
         int hc = 0;
         if (this.region != null) {
            hc = this.region.hashCode();
         }

         if (this.currency != null) {
            hc = hc * 31 + this.currency.hashCode();
         }

         hc = hc * 31 + (int)this.from;
         hc = hc * 31 + (int)(this.from >>> 32);
         hc = hc * 31 + (int)this.to;
         hc = hc * 31 + (int)(this.to >>> 32);
         hc = hc * 31 + (this.tenderOnly ? 1 : 0);
         return hc;
      }

      public String toString() {
         return CurrencyMetaInfo.debugString(this);
      }

      private static boolean equals(String lhs, String rhs) {
         return Utility.sameObjects(lhs, rhs) || lhs != null && lhs.equals(rhs);
      }
   }
}
