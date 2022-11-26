package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.util.zip.CRC32;

public abstract class AbstractCacheBacking implements CacheBacking {
   protected final Trace logger = TraceFactory.getTraceFactory().getTrace(this.getClass());

   protected AbstractCacheBacking() {
   }

   public static final long crc(byte[] bytes) {
      if (bytes != null && bytes.length > 0) {
         CRC32 crc32 = new CRC32();
         crc32.update(bytes);
         return crc32.getValue();
      } else {
         return 0L;
      }
   }
}
