package org.python.icu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.python.icu.text.CurrencyMetaInfo;
import org.python.icu.util.Currency;

public class ICUCurrencyMetaInfo extends CurrencyMetaInfo {
   private ICUResourceBundle regionInfo;
   private ICUResourceBundle digitInfo;
   private static final long MASK = 4294967295L;
   private static final int Region = 1;
   private static final int Currency = 2;
   private static final int Date = 4;
   private static final int Tender = 8;
   private static final int Everything = Integer.MAX_VALUE;

   public ICUCurrencyMetaInfo() {
      ICUResourceBundle bundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
      this.regionInfo = bundle.findTopLevel("CurrencyMap");
      this.digitInfo = bundle.findTopLevel("CurrencyMeta");
   }

   public List currencyInfo(CurrencyMetaInfo.CurrencyFilter filter) {
      return this.collect(new InfoCollector(), filter);
   }

   public List currencies(CurrencyMetaInfo.CurrencyFilter filter) {
      return this.collect(new CurrencyCollector(), filter);
   }

   public List regions(CurrencyMetaInfo.CurrencyFilter filter) {
      return this.collect(new RegionCollector(), filter);
   }

   public CurrencyMetaInfo.CurrencyDigits currencyDigits(String isoCode) {
      return this.currencyDigits(isoCode, Currency.CurrencyUsage.STANDARD);
   }

   public CurrencyMetaInfo.CurrencyDigits currencyDigits(String isoCode, Currency.CurrencyUsage currencyPurpose) {
      ICUResourceBundle b = this.digitInfo.findWithFallback(isoCode);
      if (b == null) {
         b = this.digitInfo.findWithFallback("DEFAULT");
      }

      int[] data = b.getIntVector();
      if (currencyPurpose == Currency.CurrencyUsage.CASH) {
         return new CurrencyMetaInfo.CurrencyDigits(data[2], data[3]);
      } else {
         return currencyPurpose == Currency.CurrencyUsage.STANDARD ? new CurrencyMetaInfo.CurrencyDigits(data[0], data[1]) : new CurrencyMetaInfo.CurrencyDigits(data[0], data[1]);
      }
   }

   private List collect(Collector collector, CurrencyMetaInfo.CurrencyFilter filter) {
      if (filter == null) {
         filter = CurrencyMetaInfo.CurrencyFilter.all();
      }

      int needed = collector.collects();
      if (filter.region != null) {
         needed |= 1;
      }

      if (filter.currency != null) {
         needed |= 2;
      }

      if (filter.from != Long.MIN_VALUE || filter.to != Long.MAX_VALUE) {
         needed |= 4;
      }

      if (filter.tenderOnly) {
         needed |= 8;
      }

      if (needed != 0) {
         if (filter.region != null) {
            ICUResourceBundle b = this.regionInfo.findWithFallback(filter.region);
            if (b != null) {
               this.collectRegion(collector, filter, needed, b);
            }
         } else {
            for(int i = 0; i < this.regionInfo.getSize(); ++i) {
               this.collectRegion(collector, filter, needed, this.regionInfo.at(i));
            }
         }
      }

      return collector.getList();
   }

   private void collectRegion(Collector collector, CurrencyMetaInfo.CurrencyFilter filter, int needed, ICUResourceBundle b) {
      String region = b.getKey();
      if (needed == 1) {
         collector.collect(b.getKey(), (String)null, 0L, 0L, -1, false);
      } else {
         for(int i = 0; i < b.getSize(); ++i) {
            ICUResourceBundle r = b.at(i);
            if (r.getSize() != 0) {
               String currency = null;
               long from = Long.MIN_VALUE;
               long to = Long.MAX_VALUE;
               boolean tender = true;
               ICUResourceBundle tenderBundle;
               if ((needed & 2) != 0) {
                  tenderBundle = r.at("id");
                  currency = tenderBundle.getString();
                  if (filter.currency != null && !filter.currency.equals(currency)) {
                     continue;
                  }
               }

               if ((needed & 4) != 0) {
                  from = this.getDate(r.at("from"), Long.MIN_VALUE, false);
                  to = this.getDate(r.at("to"), Long.MAX_VALUE, true);
                  if (filter.from > to || filter.to < from) {
                     continue;
                  }
               }

               if ((needed & 8) != 0) {
                  tenderBundle = r.at("tender");
                  tender = tenderBundle == null || "true".equals(tenderBundle.getString());
                  if (filter.tenderOnly && !tender) {
                     continue;
                  }
               }

               collector.collect(region, currency, from, to, i, tender);
            }
         }

      }
   }

   private long getDate(ICUResourceBundle b, long defaultValue, boolean endOfDay) {
      if (b == null) {
         return defaultValue;
      } else {
         int[] values = b.getIntVector();
         return (long)values[0] << 32 | (long)values[1] & 4294967295L;
      }
   }

   private interface Collector {
      int collects();

      void collect(String var1, String var2, long var3, long var5, int var7, boolean var8);

      List getList();
   }

   private static class CurrencyCollector implements Collector {
      private final UniqueList result;

      private CurrencyCollector() {
         this.result = ICUCurrencyMetaInfo.UniqueList.create();
      }

      public void collect(String region, String currency, long from, long to, int priority, boolean tender) {
         this.result.add(currency);
      }

      public int collects() {
         return 2;
      }

      public List getList() {
         return this.result.list();
      }

      // $FF: synthetic method
      CurrencyCollector(Object x0) {
         this();
      }
   }

   private static class RegionCollector implements Collector {
      private final UniqueList result;

      private RegionCollector() {
         this.result = ICUCurrencyMetaInfo.UniqueList.create();
      }

      public void collect(String region, String currency, long from, long to, int priority, boolean tender) {
         this.result.add(region);
      }

      public int collects() {
         return 1;
      }

      public List getList() {
         return this.result.list();
      }

      // $FF: synthetic method
      RegionCollector(Object x0) {
         this();
      }
   }

   private static class InfoCollector implements Collector {
      private List result;

      private InfoCollector() {
         this.result = new ArrayList();
      }

      public void collect(String region, String currency, long from, long to, int priority, boolean tender) {
         this.result.add(new CurrencyMetaInfo.CurrencyInfo(region, currency, from, to, priority, tender));
      }

      public List getList() {
         return Collections.unmodifiableList(this.result);
      }

      public int collects() {
         return Integer.MAX_VALUE;
      }

      // $FF: synthetic method
      InfoCollector(Object x0) {
         this();
      }
   }

   private static class UniqueList {
      private Set seen = new HashSet();
      private List list = new ArrayList();

      private static UniqueList create() {
         return new UniqueList();
      }

      void add(Object value) {
         if (!this.seen.contains(value)) {
            this.list.add(value);
            this.seen.add(value);
         }

      }

      List list() {
         return Collections.unmodifiableList(this.list);
      }
   }
}
