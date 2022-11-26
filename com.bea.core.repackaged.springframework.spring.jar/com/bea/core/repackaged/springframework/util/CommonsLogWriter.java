package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.apache.commons.logging.Log;
import java.io.Writer;

public class CommonsLogWriter extends Writer {
   private final Log logger;
   private final StringBuilder buffer = new StringBuilder();

   public CommonsLogWriter(Log logger) {
      Assert.notNull(logger, (String)"Logger must not be null");
      this.logger = logger;
   }

   public void write(char ch) {
      if (ch == '\n' && this.buffer.length() > 0) {
         this.logger.debug(this.buffer.toString());
         this.buffer.setLength(0);
      } else {
         this.buffer.append(ch);
      }

   }

   public void write(char[] buffer, int offset, int length) {
      for(int i = 0; i < length; ++i) {
         char ch = buffer[offset + i];
         if (ch == '\n' && this.buffer.length() > 0) {
            this.logger.debug(this.buffer.toString());
            this.buffer.setLength(0);
         } else {
            this.buffer.append(ch);
         }
      }

   }

   public void flush() {
   }

   public void close() {
   }
}
