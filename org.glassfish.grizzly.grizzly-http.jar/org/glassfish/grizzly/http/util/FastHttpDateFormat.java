package org.glassfish.grizzly.http.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.grizzly.utils.Charsets;

public final class FastHttpDateFormat {
   private static final String ASCII_CHARSET_NAME;
   private static final int CACHE_SIZE = 1000;
   private static final TimeZone GMT_TIME_ZONE;
   private static final SimpleDateFormatter FORMATTER;
   private static final ThreadLocal FORMAT;
   private static final ThreadLocal FORMATS;
   private static volatile long nextGeneration;
   private static final AtomicBoolean isGeneratingNow;
   private static final StringBuffer currentDateBuffer;
   private static byte[] currentDateBytes;
   private static String cachedStringDate;
   private static volatile byte[] dateBytesForCachedStringDate;
   private static final ConcurrentMap formatCache;
   private static final ConcurrentMap parseCache;

   public static String getCurrentDate() {
      byte[] currentDateBytesNow = getCurrentDateBytes();
      if (currentDateBytesNow != dateBytesForCachedStringDate) {
         try {
            cachedStringDate = new String(currentDateBytesNow, ASCII_CHARSET_NAME);
            dateBytesForCachedStringDate = currentDateBytesNow;
         } catch (UnsupportedEncodingException var2) {
         }
      }

      return cachedStringDate;
   }

   public static byte[] getCurrentDateBytes() {
      long now = System.currentTimeMillis();
      long diff = now - nextGeneration;
      if (diff > 0L && (diff > 5000L || !isGeneratingNow.get() && isGeneratingNow.compareAndSet(false, true))) {
         synchronized(FORMAT) {
            if (now > nextGeneration) {
               currentDateBuffer.setLength(0);
               FORMATTER.formatTo(now, currentDateBuffer);
               currentDateBytes = HttpCodecUtils.toCheckedByteArray(currentDateBuffer);
               nextGeneration = now + 1000L;
            }

            isGeneratingNow.set(false);
         }
      }

      return currentDateBytes;
   }

   public static String formatDate(long value, DateFormat threadLocalFormat) {
      value = value / 1000L * 1000L;
      Long longValue = value;
      String cachedDate = (String)formatCache.get(longValue);
      if (cachedDate != null) {
         return cachedDate;
      } else {
         String newDate;
         if (threadLocalFormat != null) {
            newDate = threadLocalFormat.format(value);
         } else {
            newDate = ((SimpleDateFormatter)FORMAT.get()).format(value);
         }

         updateFormatCache(longValue, newDate);
         return newDate;
      }
   }

   public static long parseDate(String value, DateFormat[] threadLocalformats) {
      Long cachedDate = (Long)parseCache.get(value);
      if (cachedDate != null) {
         return cachedDate;
      } else {
         long date;
         if (threadLocalformats != null) {
            date = internalParseDate(value, threadLocalformats);
         } else {
            date = internalParseDate(value, (SimpleDateFormat[])((SimpleDateFormat[])FORMATS.get()));
         }

         if (date != -1L) {
            updateParseCache(value, date);
         }

         return date;
      }
   }

   private static long internalParseDate(String value, DateFormat[] formats) {
      int i = 0;

      while(i < formats.length) {
         try {
            return formats[i].parse(value).getTime();
         } catch (ParseException var4) {
            ++i;
         }
      }

      return -1L;
   }

   private static void updateFormatCache(Long key, String value) {
      if (value != null) {
         if (formatCache.size() > 1000) {
            formatCache.clear();
         }

         formatCache.put(key, value);
      }
   }

   private static void updateParseCache(String key, Long value) {
      if (parseCache.size() > 1000) {
         parseCache.clear();
      }

      parseCache.put(key, value);
   }

   static {
      ASCII_CHARSET_NAME = Charsets.ASCII_CHARSET.name();
      GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");
      FORMATTER = new SimpleDateFormatter();
      FORMAT = new ThreadLocal() {
         protected SimpleDateFormatter initialValue() {
            return new SimpleDateFormatter();
         }
      };
      FORMATS = new ThreadLocal() {
         protected Object initialValue() {
            SimpleDateFormat[] f = new SimpleDateFormat[]{new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US), null, null};
            f[0].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            f[1] = new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US);
            f[1].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            f[2] = new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US);
            f[2].setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
            return f;
         }
      };
      isGeneratingNow = new AtomicBoolean();
      currentDateBuffer = new StringBuffer();
      formatCache = new ConcurrentHashMap(1000, 0.75F, 64);
      parseCache = new ConcurrentHashMap(1000, 0.75F, 64);
   }

   private static final class SimpleDateFormatter {
      private final Date date = new Date();
      private final SimpleDateFormat f;
      private final FieldPosition pos = new FieldPosition(-1);

      public SimpleDateFormatter() {
         this.f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
         this.f.setTimeZone(FastHttpDateFormat.GMT_TIME_ZONE);
      }

      public final String format(long timeMillis) {
         this.date.setTime(timeMillis);
         return this.f.format(this.date);
      }

      public final StringBuffer formatTo(long timeMillis, StringBuffer buffer) {
         this.date.setTime(timeMillis);
         return this.f.format(this.date, buffer, this.pos);
      }
   }
}
