package org.glassfish.grizzly.http.server.accesslog;

import java.text.SimpleDateFormat;

class SimpleDateFormatThreadLocal extends ThreadLocal {
   private final SimpleDateFormat format;

   SimpleDateFormatThreadLocal(String format) {
      this.format = new SimpleDateFormat(format);
   }

   protected SimpleDateFormat initialValue() {
      return (SimpleDateFormat)this.format.clone();
   }
}
