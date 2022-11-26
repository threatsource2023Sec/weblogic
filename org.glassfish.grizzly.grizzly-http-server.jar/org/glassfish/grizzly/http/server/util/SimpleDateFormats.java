package org.glassfish.grizzly.http.server.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.glassfish.grizzly.ThreadCache;

public final class SimpleDateFormats {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(SimpleDateFormats.class, 1);
   private final SimpleDateFormat[] f = new SimpleDateFormat[3];

   public static SimpleDateFormats create() {
      SimpleDateFormats formats = (SimpleDateFormats)ThreadCache.takeFromCache(CACHE_IDX);
      return formats != null ? formats : new SimpleDateFormats();
   }

   public SimpleDateFormats() {
      this.f[0] = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
      this.f[1] = new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US);
      this.f[2] = new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US);
      this.f[0].setTimeZone(TimeZone.getTimeZone("GMT"));
      this.f[1].setTimeZone(TimeZone.getTimeZone("GMT"));
      this.f[2].setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   public SimpleDateFormat[] getFormats() {
      return this.f;
   }

   public void recycle() {
      ThreadCache.putToCache(CACHE_IDX, this);
   }
}
