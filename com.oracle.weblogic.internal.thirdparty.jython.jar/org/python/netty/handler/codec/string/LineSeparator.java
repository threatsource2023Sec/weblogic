package org.python.netty.handler.codec.string;

import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.StringUtil;

public final class LineSeparator {
   public static final LineSeparator DEFAULT;
   public static final LineSeparator UNIX;
   public static final LineSeparator WINDOWS;
   private final String value;

   public LineSeparator(String lineSeparator) {
      this.value = (String)ObjectUtil.checkNotNull(lineSeparator, "lineSeparator");
   }

   public String value() {
      return this.value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof LineSeparator)) {
         return false;
      } else {
         LineSeparator that = (LineSeparator)o;
         return this.value != null ? this.value.equals(that.value) : that.value == null;
      }
   }

   public int hashCode() {
      return this.value != null ? this.value.hashCode() : 0;
   }

   public String toString() {
      return ByteBufUtil.hexDump(this.value.getBytes(CharsetUtil.UTF_8));
   }

   static {
      DEFAULT = new LineSeparator(StringUtil.NEWLINE);
      UNIX = new LineSeparator("\n");
      WINDOWS = new LineSeparator("\r\n");
   }
}
