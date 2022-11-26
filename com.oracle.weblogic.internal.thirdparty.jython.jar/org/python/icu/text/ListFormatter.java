package org.python.icu.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import org.python.icu.impl.ICUCache;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public final class ListFormatter {
   private final String two;
   private final String start;
   private final String middle;
   private final String end;
   private final ULocale locale;
   static Cache cache = new Cache();

   /** @deprecated */
   @Deprecated
   public ListFormatter(String two, String start, String middle, String end) {
      this(compilePattern(two, new StringBuilder()), compilePattern(start, new StringBuilder()), compilePattern(middle, new StringBuilder()), compilePattern(end, new StringBuilder()), (ULocale)null);
   }

   private ListFormatter(String two, String start, String middle, String end, ULocale locale) {
      this.two = two;
      this.start = start;
      this.middle = middle;
      this.end = end;
      this.locale = locale;
   }

   private static String compilePattern(String pattern, StringBuilder sb) {
      return SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, sb, 2, 2);
   }

   public static ListFormatter getInstance(ULocale locale) {
      return getInstance(locale, ListFormatter.Style.STANDARD);
   }

   public static ListFormatter getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale), ListFormatter.Style.STANDARD);
   }

   /** @deprecated */
   @Deprecated
   public static ListFormatter getInstance(ULocale locale, Style style) {
      return cache.get(locale, style.getName());
   }

   public static ListFormatter getInstance() {
      return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public String format(Object... items) {
      return this.format((Collection)Arrays.asList(items));
   }

   public String format(Collection items) {
      return this.format(items, -1).toString();
   }

   FormattedListBuilder format(Collection items, int index) {
      Iterator it = items.iterator();
      int count = items.size();
      switch (count) {
         case 0:
            return new FormattedListBuilder("", false);
         case 1:
            return new FormattedListBuilder(it.next(), index == 0);
         case 2:
            return (new FormattedListBuilder(it.next(), index == 0)).append(this.two, it.next(), index == 1);
         default:
            FormattedListBuilder builder = new FormattedListBuilder(it.next(), index == 0);
            builder.append(this.start, it.next(), index == 1);

            for(int idx = 2; idx < count - 1; ++idx) {
               builder.append(this.middle, it.next(), index == idx);
            }

            return builder.append(this.end, it.next(), index == count - 1);
      }
   }

   public String getPatternForNumItems(int count) {
      if (count <= 0) {
         throw new IllegalArgumentException("count must be > 0");
      } else {
         ArrayList list = new ArrayList();

         for(int i = 0; i < count; ++i) {
            list.add(String.format("{%d}", i));
         }

         return this.format((Collection)list);
      }
   }

   /** @deprecated */
   @Deprecated
   public ULocale getLocale() {
      return this.locale;
   }

   // $FF: synthetic method
   ListFormatter(String x0, String x1, String x2, String x3, ULocale x4, Object x5) {
      this(x0, x1, x2, x3, x4);
   }

   private static class Cache {
      private final ICUCache cache;

      private Cache() {
         this.cache = new SimpleCache();
      }

      public ListFormatter get(ULocale locale, String style) {
         String key = String.format("%s:%s", locale.toString(), style);
         ListFormatter result = (ListFormatter)this.cache.get(key);
         if (result == null) {
            result = load(locale, style);
            this.cache.put(key, result);
         }

         return result;
      }

      private static ListFormatter load(ULocale ulocale, String style) {
         ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", ulocale);
         StringBuilder sb = new StringBuilder();
         return new ListFormatter(ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/2").getString(), sb), ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/start").getString(), sb), ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/middle").getString(), sb), ListFormatter.compilePattern(r.getWithFallback("listPattern/" + style + "/end").getString(), sb), ulocale);
      }

      // $FF: synthetic method
      Cache(Object x0) {
         this();
      }
   }

   static class FormattedListBuilder {
      private StringBuilder current;
      private int offset;

      public FormattedListBuilder(Object start, boolean recordOffset) {
         this.current = new StringBuilder(start.toString());
         this.offset = recordOffset ? 0 : -1;
      }

      public FormattedListBuilder append(String pattern, Object next, boolean recordOffset) {
         int[] offsets = !recordOffset && !this.offsetRecorded() ? null : new int[2];
         SimpleFormatterImpl.formatAndReplace(pattern, this.current, offsets, this.current, next.toString());
         if (offsets != null) {
            if (offsets[0] == -1 || offsets[1] == -1) {
               throw new IllegalArgumentException("{0} or {1} missing from pattern " + pattern);
            }

            if (recordOffset) {
               this.offset = offsets[1];
            } else {
               this.offset += offsets[0];
            }
         }

         return this;
      }

      public String toString() {
         return this.current.toString();
      }

      public int getOffset() {
         return this.offset;
      }

      private boolean offsetRecorded() {
         return this.offset >= 0;
      }
   }

   /** @deprecated */
   @Deprecated
   public static enum Style {
      /** @deprecated */
      @Deprecated
      STANDARD("standard"),
      /** @deprecated */
      @Deprecated
      DURATION("unit"),
      /** @deprecated */
      @Deprecated
      DURATION_SHORT("unit-short"),
      /** @deprecated */
      @Deprecated
      DURATION_NARROW("unit-narrow");

      private final String name;

      private Style(String name) {
         this.name = name;
      }

      /** @deprecated */
      @Deprecated
      public String getName() {
         return this.name;
      }
   }
}
